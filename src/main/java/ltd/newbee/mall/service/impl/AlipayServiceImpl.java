package ltd.newbee.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.api.mall.vo.NewBeeMallOrderDetailVO;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.service.AlipayService;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * @author : [wangminan]
 * @description : [支付宝支付实现类]
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private Environment config;

    @Resource
    private NewBeeMallOrderService orderService;

    @Override
    public String tradeCreate(String orderNo, Long userId) {

        log.info("收到订单号为：{}的支付宝支付请求，开始提交支付", orderNo);

        // 调用支付宝接口 可能alt+enter没有候选项，需要自己写import
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        request.setNotifyUrl(config.getProperty("alipay.notify-url"));
        request.setReturnUrl(config.getProperty("alipay.return-url"));

        // 以上是配置所有的公共参数，下面是配置请求参数集合

        JSONObject bizContent = new JSONObject();
        // 商户订单号
        bizContent.put("out_trade_no", orderNo);

        NewBeeMallOrderDetailVO order = orderService.getOrderDetailByOrderNo(orderNo, userId);

        /*
         * 对于BigDecimal,这是一个在金融项目中常见的类，它的精度是可控的，可以控制小数点后的位数，
         * 这里的ROUND_HALF_EVEN是一个枚举值，表示四舍五入
         * 在构造时需要传入一个用于表示数字的字符串 (而不是一个double类型的数字)
         */
        BigDecimal totalFee = new BigDecimal(order.getTotalPrice().toString());
        // 订单总金额 单位为元，精确到小数点后两位
        bizContent.put("total_amount", totalFee);
        bizContent.put("subject", order.getOrderNo());
        // 目前电脑端场景仅支持该FAST_INSTANT_TRADE_PAY值
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        /*
         * 阿里的示例代码是这么写的
         * AlipayTradePagePayModel model = new AlipayTradePagePayModel();
         * model.setOutTradeNo("20150320010101001");
         * model.setTotalAmount("88.88");
         * model.setSubject("Iphone6 16G");
         * model.setProductCode("FAST_INSTANT_TRADE_PAY");
         * 这样不容易出错 下次可以这么写
         */

        // 实际情况下若一个订单中存在多个商品，则需要新进一个名为goodsDetail的json数组来存放每一个商品的信息

        request.setBizContent(bizContent.toString());

        // 调用远程接口
        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            // try-catch与Transaction注解配合使用时需要手动抛出RuntimeException，否则事务不会回滚
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败,连接异常");
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
            log.info("调用成功,返回结果为==>" + response.getBody());
        } else {
            log.info("调用失败,返回结果为==> " + response.getCode() + " " + response.getMsg());
            throw new RuntimeException("创建支付交易失败,对方接口异常");
        }
        return response.getBody();
    }

    @Override
    public String checkSign(Map<String, String> notifyParams) {
        return null;
    }

    @Override
    public void cancelOrder(String orderNo) {

    }

    @Override
    public String queryOrder(String orderNo) {
        return null;
    }

    @Override
    public void checkOrderStatus(String orderNo) {

    }

    @Override
    public void refund(String orderNo, String reason) {

    }

    @Override
    public String queryRefund(String orderNo) {
        return null;
    }
}

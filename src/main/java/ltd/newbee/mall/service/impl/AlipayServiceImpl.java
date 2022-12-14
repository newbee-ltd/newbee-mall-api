package ltd.newbee.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.api.mall.vo.NewBeeMallOrderDetailVO;
import ltd.newbee.mall.common.AlipayTradeState;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.NewBeeMallOrderStatusEnum;
import ltd.newbee.mall.common.PayStatusEnum;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.service.AlipayService;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

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

    private final ReentrantLock lock = new ReentrantLock();

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
        /*
         * 回调示例如下:
         * 通知参数 ====>
         * {
         * gmt_create=2022-12-05 17:18:37,
         * charset=UTF-8,
         * gmt_payment=2022-12-05 17:18:46,
         * notify_time=2022-12-05 17:18:49,
         * subject=Java课程,
         * sign=FmKXT9HjUhWlbCImoG4zrTUD3fFGYAmAo+2bPBNJi2WqiGNzRHDJ7FT6EOpZULlWlQhg8dKfkJ5WtnxIMNYyoLx3g8t73MsWlzFRvfvT1lVAbzj4G69gs57bOPrE5wuJq6gDpWJ4wHDgMPUTABbulHzy0IGcsEAwdrCmtzmt5Jjrube2HgDxm84LIDzbilxl1TVoCVsxaLJj1WAZ2jmVxm2VcM0h9+e8bu/NacRZktofA5DpC7SYf03TMjWLRc4ih5EYGz2tAX9nt3i1LVyxmlJpvPuMobXgPMRcuzNAqDxcMo/CUEvIVmaDT4iiIU4qgPZ1sqAdrs67mFQep6VZ8A==,
         * buyer_id=2088722003402347,
         * invoice_amount=0.01,
         * version=1.0,
         * notify_id=2022120500222171848002340521336546,
         * fund_bill_list=[{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}],
         * notify_type=trade_status_sync,
         * out_trade_no=ORDER_20221205171809023,
         * total_amount=0.01,
         * trade_status=TRADE_SUCCESS,
         * trade_no=2022120522001402340501695232,
         * auth_app_id=2021000121691518,
         * receipt_amount=0.01,
         * point_amount=0.00,
         * app_id=2021000121691518,
         * buyer_pay_amount=0.01,
         * sign_type=RSA2,
         * seller_id=2088621993877332
         * }
         */
        String result = "failure";
        // 1.验签 如果成功返回success，否则返回failure(支付宝要求)
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(
                    notifyParams,
                    config.getProperty("alipay.alipay-public-key"),
                    AlipayConstants.CHARSET_UTF8,
                    AlipayConstants.SIGN_TYPE_RSA2);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if(signVerified){
            /*
                验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，
                 校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            */
            /*
             * 程序执行完后必须打印输出“success”（不包含引号）。
             * 如果商家反馈给支付宝的字符不是 success 这7个字符，支付宝服务器会不断重发通知，直到超过 24 小时 22 分钟。
             * 一般情况下，25 小时以内完成 8 次通知()
             */
            log.info("支付宝异步通知验签成功");
            /*
             * 进一步校验以下4项业务参数
             * 1.商家需要验证该通知数据中的 out_trade_no 是否为商家系统中创建的订单号。
             * 2.判断 total_amount 是否确实为该订单的实际金额（即商家订单创建时的金额）。
             * 3.校验通知中的 seller_id（或者 seller_email) 是否为 out_trade_no 这笔单据的对应的操作方
             * （有的时候，一个商家可能有多个 seller_id/seller_email）。
             * 4.验证 app_id 是否为该商家本身。
             */

            // out_trade_no
            String outTradeNo = notifyParams.get("out_trade_no");
            // 获取订单对象
            NewBeeMallOrder orderInfo = orderService.getOrderByOrderNo(outTradeNo);


            if(orderInfo == null){
                log.error("订单不存在，订单号：{}", outTradeNo);
                return result;
            }

            // total_amount
            String totalAmount = notifyParams.get("total_amount");
            // total_amount中的金额以元为单位，需要转换为分(数据库中记录的单位为分)
            int totalAmountInt = new BigDecimal(totalAmount).intValue();
            if(orderInfo.getTotalPrice() != totalAmountInt){
                log.error("订单金额不一致，订单号：{}，订单金额：{}，支付宝金额：{}",
                        outTradeNo, orderInfo.getTotalPrice(), totalAmountInt);
                return result;
            }

            // seller_id
            String sellerId = notifyParams.get("seller_id");
            if(!sellerId.equals(config.getProperty("alipay.seller-id"))){
                log.error("商家pid不一致，订单号：{}，商家id：{}，支付宝商家id：{}",
                        outTradeNo, sellerId, config.getProperty("alipay.seller-id"));
                return result;
            }

            // app_id
            String appId = notifyParams.get("app_id");
            if(!appId.equals(config.getProperty("alipay.app-id"))){
                log.error("app-id不一致，订单号：{}，商家app-id：{}，支付宝app-id：{}",
                        outTradeNo, appId, config.getProperty("alipay.app-id"));
                return result;
            }

            // 只有交易通知状态为 TRADE_SUCCESS 或 TRADE_FINISHED 时，支付宝才会认定为买家付款成功。
            // 前者支持退款 后者不支持退款
            String tradeStatus = notifyParams.get("trade_status");
            if(!tradeStatus.equals(AlipayTradeState.SUCCESS.getType())){
                log.error("交易状态不正确，订单号：{}，交易状态：{}", outTradeNo, tradeStatus);
                return result;
            }

            // 加锁,并发控制,防止重复回调
            /*
             * 注意：锁与@Transaction注解不兼容，如果使用了@Transaction注解，那么锁将失效
             * 1.如果使用了@Transaction注解，那么在方法执行完后，会自动提交事务，此时锁将自动释放
             * 2.如果没有使用@Transaction注解，那么在方法执行完后，需要手动提交事务，此时锁才会释放
             * 参考 https://blog.csdn.net/fal1230/article/details/113392123
             */
            if(lock.tryLock()){
                try{
                    // 处理重复通知
                    // 无论接口被调用多少次 以下业务只执行一次
                    // 接口调用的幂等性
                    Byte orderStatus = orderInfo.getOrderStatus();
                    if(!((orderStatus & 0xFF) == NewBeeMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus())){
                        log.info("非未支付订单，订单状态将不会被更新，订单号：{}", outTradeNo);
                        // if外将重新发送success
                    }

                    // 处理自身业务
                    log.info("处理订单");
                    // 修改订单状态
                    orderService.updatePayAndOrderStatusByOrderNo(outTradeNo,
                            (byte) PayStatusEnum.PAY_SUCCESS.getPayStatus(),
                            (byte) NewBeeMallOrderStatusEnum.ORDER_PAID.getOrderStatus());
                    // 记录支付日志
                    log.info("订单号：{} 回调已收到，参数正常，状态已更新", outTradeNo);
                } finally {
                    lock.unlock();
                }
            }
            result = "success";
        }else{
            log.error("支付宝异步通知验签失败");
        }
        return result;
    }

    @Override
    public void cancelOrder(String orderNo) {
        // 调用支付宝提供的统一收单交易关闭接口
        this.closeOrder(orderNo);
        // 更新用户的订单状态
        orderService.updatePayAndOrderStatusByOrderNo(
                orderNo,
                (byte) PayStatusEnum.DEFAULT.getPayStatus(),
                (byte) NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
        );
        log.info("订单号为：{}的订单已被取消", orderNo);
    }

    @Override
    public String queryOrder(String orderNo) {
        log.info("查询订单状态，订单号：{}", orderNo);

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("查单接口调用失败");
        }
        if(response.isSuccess()){
            log.info("调用成功,返回结果为==>" + response.getBody());
            return response.getBody();
        } else {
            log.info("调用失败,返回结果为==> " + response.getCode() + " " + response.getMsg());
            Map result = new HashMap<>();
            result = JSON.parseObject(response.getBody(), result.getClass());
            Map error = (Map) result.get("alipay_trade_query_response");
            if (error.get("sub_code").equals("ACQ.TRADE_NOT_EXIST")) {
                log.error("订单于远端不存在，无法查询，订单号：{}", orderNo);
                return null;
            } else {
                log.error("关单接口调用失败");
                throw new RuntimeException("关单接口调用失败,对方接口异常");
            }
        }
    }

    @Override
    public void checkOrderStatus(String orderNo) {
        log.info("检查订单状态，订单号：{}", orderNo);
        String result = this.queryOrder(orderNo);
        if (result == null) {
            log.info("订单于远端不存在，无法查询，订单号：{}，订单将被超时关闭", orderNo);
            // 关闭该订单
            orderService.updatePayAndOrderStatusByOrderNo(
                    orderNo,
                    (byte) PayStatusEnum.DEFAULT.getPayStatus(),
                    (byte) NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
            );
            return;
        }
        Map resultObj = new HashMap<>();
        resultObj = JSON.parseObject(result, resultObj.getClass());
        Map response = (Map) resultObj.get("alipay_trade_query_response");
        String tradeStatus = (String) response.get("trade_status");
        if (tradeStatus.equals("TRADE_SUCCESS")) {
            log.info("订单已支付，订单号：{}", orderNo);
            // 更新订单状态
            orderService.updatePayAndOrderStatusByOrderNo(
                    orderNo,
                    (byte) PayStatusEnum.PAY_SUCCESS.getPayStatus(),
                    (byte) NewBeeMallOrderStatusEnum.ORDER_PAID.getOrderStatus()
            );
        } else if (tradeStatus.equals("TRADE_CLOSED")) {
            log.info("订单已关闭，订单号：{}", orderNo);
            // 更新订单状态
            orderService.updatePayAndOrderStatusByOrderNo(
                    orderNo,
                    (byte) PayStatusEnum.DEFAULT.getPayStatus(),
                    (byte) NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
            );
        } else {
            log.info("订单回调异常，订单号：{}，订单将被关闭", orderNo);
            // 关闭该订单
            orderService.updatePayAndOrderStatusByOrderNo(
                    orderNo,
                    (byte) PayStatusEnum.DEFAULT.getPayStatus(),
                    (byte) NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
            );
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refund(String orderNo, String reason) {
        try {
            log.info("调用退款API");

            //创建退款单
            NewBeeMallOrder order = orderService.getOrderByOrderNo(orderNo);

            if(!(order.getOrderStatus() ==
                    NewBeeMallOrderStatusEnum.ORDER_PAID.getOrderStatus()) &&
                !(order.getOrderStatus() ==
                            NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus())){
                log.error("订单{}不符合退款条件,该订单不属于正常已支付订单",orderNo);
                throw new NewBeeMallException("非正常已支付订单无法退款");
            }

            //调用统一收单交易退款接口
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest ();

            //组装当前业务方法的请求参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);//订单编号
            BigDecimal refund = new BigDecimal(order.getTotalPrice().toString());
            //BigDecimal refund = new BigDecimal("2").divide(new BigDecimal("100"));
            bizContent.put("refund_amount", refund);//退款金额：不能大于支付金额
            bizContent.put("refund_reason", reason);//退款原因(可选)

            request.setBizContent(bizContent.toString());

            //执行请求，调用支付宝接口 同步返回结果 可直接进行调用
            AlipayTradeRefundResponse response = alipayClient.execute(request);

            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());

                //更新订单状态
                order.setOrderStatus((byte) NewBeeMallOrderStatusEnum.ORDER_REFUNDED.getOrderStatus());
                order.setPayStatus((byte) PayStatusEnum.PAY_REFUNDED.getPayStatus());

                //更新退款信息
                orderService.updateOrderInfo(order);
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());

                //更新订单状态
                order.setOrderStatus((byte) NewBeeMallOrderStatusEnum.DEFAULT.getOrderStatus());
                order.setPayStatus((byte) PayStatusEnum.PAY_ERROR.getPayStatus());

                //更新退款单
                orderService.updateOrderInfo(order);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("创建退款申请失败");
        }
    }

    @Override
    public String queryRefund(String orderNo) {
        try {
            log.info("查询退款接口调用 ===> {}", orderNo);

            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            // 如果退款请求没有传退款单编号，执行全额退款，则直接填订单号即可
            bizContent.put("out_request_no", orderNo);
            request.setBizContent(bizContent.toString());

            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                log.info("调用成功，返回结果 ===> " + response.getBody());
                return response.getBody();
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                //throw new RuntimeException("查单接口的调用失败");
                return null;//订单不存在
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("查单接口的调用失败");
        }
    }

    /**
     * 关闭订单与核验订单状态接口调用
     * @param orderNo 订单号
     */
    private void closeOrder(String orderNo){

        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);

        //// 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());
        AlipayTradeCloseResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("关单接口调用失败");
        }
        if(response.isSuccess()){
            log.info("调用成功,返回结果为==>" + response.getBody());
        } else {
            log.info("调用失败,返回结果为==> " + response.getCode() + " " + response.getMsg());
            Map result = new HashMap<>();
            result = JSON.parseObject(response.getBody(), result.getClass());
            Map error = (Map) result.get("alipay_trade_close_response");
            if (error.get("sub_code").equals("ACQ.TRADE_NOT_EXIST")) {
                log.info("订单于远端不存在，无需关单，本地订单状态将被更新为已关闭，订单号：{}", orderNo);
            } else {
                log.error("关单接口调用失败");
                throw new RuntimeException("关单接口调用失败,对方接口异常");
            }
        }
    }
}

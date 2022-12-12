package ltd.newbee.mall.api.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.config.annotation.TokenToMallUser;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.service.AlipayService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : [wangminan]
 * @description : [支付宝控制类]
 */
@RestController
@RequestMapping("/api/v1/ali-pay")
@Api(tags = "网站支付宝支付")
@Slf4j
public class AlipayController {

    @Resource
    private AlipayService alipayService;

    /**
     * 支付宝开放平台接收request对象后，会为开发者生成HTML形式的表单
     * 包含自动提交的脚本，开发者将表单内容直接返回给前端即可，之后前端可以调用自动提交脚本进行表单提交
     * 此时表单会自动提交到action属性指向的支付宝开放平台网关，从而为用户展示一个支付页面
     * @param orderNo 订单编号
     * @return 表单
     */
    @ApiOperation("统一收单下单并支付页面接口")
    @PostMapping("/trade/page/pay/{orderNo}")
    public Result<String> tradePagePay(@PathVariable String orderNo,
                                       @TokenToMallUser MallUser loginMallUser){
        log.info("统一收单下单并支付页面接口");
        String returnString = alipayService.tradeCreate(orderNo, loginMallUser.getUserId());
        // 我现在自己写了才觉得ResultGenerator这个类是在脱裤子放屁 太烂了
        return ResultGenerator.genSuccessResult((Object) returnString);
    }

    /**
     * 支付通知回调接口，该接口由支付宝开放平台调用，与前端无关
     * @param notifyParams 支付宝开放平台回调参数
     * @return 对支付宝开放平台响应校验后的结果
     */
    @ApiOperation("支付通知回调,非前端调用接口")
    @PostMapping("/trade/notify")
    public String tradeNotify(@RequestParam Map<String, String> notifyParams){
        log.info("支付通知回调");
        log.info("通知参数 ====> {}", notifyParams);

        return alipayService.checkSign(notifyParams);
    }

    /**
     * 用户取消订单
     * @param orderNo 订单号
     * @return 订单状态
     */
    @ApiOperation(value = "取消订单")
    @PostMapping("/trade/close/{orderNo}")
    public Result<String> cancel(@PathVariable String orderNo){
        log.info("取消订单，订单号：{}", orderNo);
        alipayService.cancelOrder(orderNo);
        return ResultGenerator.genSuccessResult("订单取消成功");
    }

    /**
     * 查询订单
     * @param orderNo 订单号
     * @return 订单状态
     */
    @ApiOperation("查询订单：测试订单状态用")
    @GetMapping("/trade/query/{orderNo}")
    public Result<String> queryOrder(@PathVariable String orderNo){
        log.info("查询订单");

        String result =alipayService.queryOrder(orderNo);
        return ResultGenerator.genSuccessResult("订单状态查询成功", result);
    }


    /**
     * 订单退款
     * @param orderNo 订单号
     * @param reason 退款原因
     * @return 退款状态
     */
    @ApiOperation("申请退款")
    @PostMapping("/trade/refund/{orderNo}/{reason}")
    public Result<String> refunds(@PathVariable String orderNo, @PathVariable String reason){
        log.info("退款");
        alipayService.refund(orderNo, reason);
        return ResultGenerator.genSuccessResult("退款成功");
    }

    /**
     * 查询退款 与支付宝交互测试用 非前端调用接口
     * @param orderNo 订单号
     * @return 退款状态
     */
    @ApiOperation("查询退款：测试用,非前端调用接口")
    @GetMapping("/trade/fastpay/refund/{orderNo}")
    public Result<String> queryRefund(@PathVariable String orderNo) {

        log.info("查询退款");

        String result = alipayService.queryRefund(orderNo);
        return ResultGenerator.genSuccessResult("退款查询成功", result);
    }
}

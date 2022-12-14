package ltd.newbee.mall.task;

import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.common.PayTypeEnum;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.service.AlipayService;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : [wangminan]
 * @description : 支付宝支付的定时任务
 * 需要先手动关闭微信支付的定时任务，否则会对本类中定时任务造成干扰
 */
@Slf4j
@Component
public class AlipayTask {

    @Resource
    private NewBeeMallOrderService orderService;

    @Resource
    private AlipayService alipayService;

    // 设定订单过期确认时间为 10 分钟
    private static final int ORDER_EXPIRE_TIME = 10;

    /*
     * 秒 分 时 日 月 周
     * 以秒为例
     * *：每秒都执行
     * 1-3：从第1秒开始执行，到第3秒结束执行
     * 0/3：从第0秒开始，每隔3秒执行1次
     * 1,2,3：在指定的第1、2、3秒执行
     * ?：不指定
     * 日和周不能同时制定，指定其中之一，则另一个设置为?
     */

    /**
     * 从第0秒开始每隔60秒执行1次
     * 查询创建超过10分钟且未支付的清单
     */
    @Scheduled(cron = "0/60 * * * * ?")
    public void orderConfirm(){
        log.info("orderConfirm 被执行......");

        List<NewBeeMallOrder> orderList = orderService.getNoPayOrderByDuration(
                ORDER_EXPIRE_TIME,
                PayTypeEnum.ALI_PAY.getPayType());

        for (NewBeeMallOrder orderInfo : orderList) {
            String orderNo = orderInfo.getOrderNo();
            log.warn("超时订单 ===> {}", orderNo);

            // 核实订单状态并调用支付宝查单接口
            alipayService.checkOrderStatus(orderNo);
        }
    }
}

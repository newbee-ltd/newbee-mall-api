package ltd.newbee.mall.service;

import java.util.Map;

/**
 * @author wangminan
 * @description 支付宝服务接口
 */
public interface AlipayService {
    String tradeCreate(String orderNo, Long userId);

    // 验签
    String checkSign(Map<String,String> notifyParams);

    void cancelOrder(String orderNo);

    String queryOrder(String orderNo);

    void checkOrderStatus(String orderNo);

    void refund(String orderNo, String reason);

    String queryRefund(String orderNo);
}

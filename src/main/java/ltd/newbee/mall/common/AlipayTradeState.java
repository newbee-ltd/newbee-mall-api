package ltd.newbee.mall.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlipayTradeState {

    /**
     * 支付成功
     */
    SUCCESS("TRADE_SUCCESS"),

    /**
     * 未支付
     */
    NOTPAY("WAIT_BUYER_PAY"),

    /**
     * 已关闭
     */
    CLOSED("TRADE_CLOSED"),

    /**
     * 退款成功
     */
    REFUND_SUCCESS("REFUND_SUCCESS"),

    /**
     * 退款失败
     */
    REFUND_ERROR("REFUND_ERROR");

    /**
     * 类型
     */
    private final String type;
}

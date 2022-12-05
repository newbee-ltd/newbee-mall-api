/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.common;

/**
 * 该类为支付状态枚举类，可以获取、设置支付状态，并根据参数payStatus返回订单状态
 *
 * @author 13
 * @apiNote 订单状态:0.支付中 1.支付成功 -1.支付失败
 */
public enum PayStatusEnum {

    DEFAULT(-1, "支付失败"),
    PAY_ING(0, "支付中"),
    PAY_SUCCESS(1, "支付成功");

    private int payStatus;

    private String name;

    /**
     * 构造方法
     * @param payStatus
     * @param name
     */
    PayStatusEnum(int payStatus, String name) {
        this.payStatus = payStatus;
        this.name = name;
    }

    /**
     * 根据payStatus参数返回支付状态，有误则返回DEFAULT
     * @param payStatus
     * @return 支付状态
     */
    public static PayStatusEnum getPayStatusEnumByStatus(int payStatus) {
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            if (payStatusEnum.getPayStatus() == payStatus) {
                return payStatusEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 获取支付状态码
     * @return 支付状态码
     */
    public int getPayStatus() {
        return payStatus;
    }


    /**
     * 获取支付状态描述
     * @return 支付状态描述
     */
    public String getName() {
        return name;
    }

}

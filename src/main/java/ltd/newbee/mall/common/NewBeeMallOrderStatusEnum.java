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
 * 该类为订单状态枚举类，可以获取、设置订单状态，并根据参数orderStatus返回订单状态
 *
 * @author 13
 * @apiNote 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
 */
public enum NewBeeMallOrderStatusEnum {

    DEFAULT(-9, "ERROR"),
    ORDER_PRE_PAY(0, "待支付"),
    ORDER_PAID(1, "已支付"),
    ORDER_PACKAGED(2, "配货完成"),
    ORDER_EXPRESS(3, "出库成功"),
    ORDER_SUCCESS(4, "交易成功"),
    ORDER_CLOSED_BY_MALLUSER(-1, "手动关闭"),
    ORDER_CLOSED_BY_EXPIRED(-2, "超时关闭"),
    ORDER_CLOSED_BY_JUDGE(-3, "商家关闭");

    private int orderStatus;

    private String name;

    /**
     * 构造方法
     * @param orderStatus
     * @param name
     */
    NewBeeMallOrderStatusEnum(int orderStatus, String name) {
        this.orderStatus = orderStatus;
        this.name = name;
    }

    /**
     * 根据orderStatus参数返回订单状态，有误则返回DEFAULT
     * @param orderStatus
     * @return 订单状态
     */
    public static NewBeeMallOrderStatusEnum getNewBeeMallOrderStatusEnumByStatus(int orderStatus) {
        for (NewBeeMallOrderStatusEnum newBeeMallOrderStatusEnum : NewBeeMallOrderStatusEnum.values()) {
            if (newBeeMallOrderStatusEnum.getOrderStatus() == orderStatus) {
                return newBeeMallOrderStatusEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 获取订单状态码
     * @return 订单状态码
     */
    public int getOrderStatus() {
        return orderStatus;
    }

//    /**
//     * 设置订单状态码
//     * @param orderStatus
//     */
//    public void setOrderStatus(int orderStatus) {
//        this.orderStatus = orderStatus;
//    }

    /**
     * 获取订单状态描述
     * @return 订单状态描述
     */
    public String getName() {
        return name;
    }

//    /**
//     * 设置订单状态描述
//     * @param name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
}

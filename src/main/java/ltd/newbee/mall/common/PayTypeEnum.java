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
 * 该类为支付方式枚举类，可以获取、设置支付方式，并根据参数payType返回支付方式
 *
 * @author 13
 * @apiNote 支付方式:0.无 1.支付宝 2.微信支付
 */
public enum PayTypeEnum {

    DEFAULT(-1, "ERROR"),
    NOT_PAY(0, "无"),
    ALI_PAY(1, "支付宝"),
    WEIXIN_PAY(2, "微信支付");

    private int payType;

    private String name;

    /**
     * 构造方法
     * @param payType
     * @param name
     */
    PayTypeEnum(int payType, String name) {
        this.payType = payType;
        this.name = name;
    }

    /**
     * 根据payType参数返回支付方式，有误则返回DEFAULT
     * @param payType
     * @return 支付方式
     */
    public static PayTypeEnum getPayTypeEnumByType(int payType) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getPayType() == payType) {
                return payTypeEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 获取支付方式码
     * @return 支付方式码
     */
    public int getPayType() {
        return payType;
    }

//    /**
//     * 设置支付方式码
//     * @param payType
//     */
//    public void setPayType(int payType) {
//        this.payType = payType;
//    }

    /**
     * 获取支付方式描述
     * @return 支付方式描述
     */
    public String getName() {
        return name;
    }

//    /**
//     * 设置支付方式描述
//     * @param name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
}

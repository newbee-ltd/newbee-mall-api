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
 * 该类为分类级别枚举类，可以获取、设置分类级别，并根据参数level返回分类级别
 *
 * @author 13
 * @apiNote 分类级别
 */
public enum NewBeeMallCategoryLevelEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_ONE(1, "一级分类"),
    LEVEL_TWO(2, "二级分类"),
    LEVEL_THREE(3, "三级分类");

    private int level;

    private String name;

    /**
     * 构造方法
     * @param level
     * @param name
     */
    NewBeeMallCategoryLevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }

    /**
     * 根据level参数返回分类级别，有误则返回DEFAULT
     * @param level
     * @return 分类级别
     */
    public static NewBeeMallCategoryLevelEnum getNewBeeMallOrderStatusEnumByLevel(int level) {
        for (NewBeeMallCategoryLevelEnum newBeeMallCategoryLevelEnum : NewBeeMallCategoryLevelEnum.values()) {
            if (newBeeMallCategoryLevelEnum.getLevel() == level) {
                return newBeeMallCategoryLevelEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 获取分类level
     * @return 分类level
     */
    public int getLevel() {
        return level;
    }

//    /**
//     * 设置分类level
//     * @param level
//     */
//    private void setLevel(int level) {
//        this.level = level;
//    }

    /**
     * 获取分类名（一级、二级、三级OR ERROR）
     * @return 分类名
     */
    public String getName() {
        return name;
    }

//    /**
//     * 设置分类名（一级、二级、三级OR ERROR）
//     * @param name
//     */
//    private void setName(String name) {
//        this.name = name;
//    }
}

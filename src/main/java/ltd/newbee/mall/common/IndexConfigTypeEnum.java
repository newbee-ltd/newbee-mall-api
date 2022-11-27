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
 * 该类为首页配置枚举类，可以获取、设置配置项，并根据参数type返回配置项
 *
 * @author 13
 * @apiNote 首页配置项 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
 */
public enum IndexConfigTypeEnum {

    DEFAULT(0, "DEFAULT"),
    INDEX_SEARCH_HOTS(1, "INDEX_SEARCH_HOTS"),
    INDEX_SEARCH_DOWN_HOTS(2, "INDEX_SEARCH_DOWN_HOTS"),
    INDEX_GOODS_HOT(3, "INDEX_GOODS_HOTS"),
    INDEX_GOODS_NEW(4, "INDEX_GOODS_NEW"),
    INDEX_GOODS_RECOMMOND(5, "INDEX_GOODS_RECOMMOND");

    private int type;

    private String name;

    /**
     * 构造方法
     * @param type
     * @param name
     */
    IndexConfigTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 根据参数返回首页配置项，有误则返回DEFAULT
     * @param type
     * @return 首页配置项
     */
    public static IndexConfigTypeEnum getIndexConfigTypeEnumByType(int type) {
        for (IndexConfigTypeEnum indexConfigTypeEnum : IndexConfigTypeEnum.values()) {
            if (indexConfigTypeEnum.getType() == type) {
                return indexConfigTypeEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 获取配置类型
     * @return 配置类型
     */
    public int getType() {
        return type;
    }

    /**
     * 设置配置类型
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 获取配置名称
     * @return 配置名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置配置名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}

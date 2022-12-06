/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.mall.vo;

import ltd.newbee.mall.entity.GoodsCategory;

import java.io.Serializable;
import java.util.List;

/**
 * 此类为搜索页面分类数据VO，包括一级、二级、三级分类，以及当前分类
 *
 * @author 十三
 */
public class SearchPageCategoryVO implements Serializable {

    private String firstLevelCategoryName;

    private List<GoodsCategory> secondLevelCategoryList;

    private String secondLevelCategoryName;

    private List<GoodsCategory> thirdLevelCategoryList;

    private String currentCategoryName;

    /**
     * 获取一级分类名
     * @return 一级分类名
     */
    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    /**
     * 设置一级分类名
     * @param firstLevelCategoryName
     */
    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    /**
     * 获取二级分类
     * @return 二级分类列表
     */
    public List<GoodsCategory> getSecondLevelCategoryList() {
        return secondLevelCategoryList;
    }

    /**
     * 设置二级分类列表
     * @param secondLevelCategoryList
     */
    public void setSecondLevelCategoryList(List<GoodsCategory> secondLevelCategoryList) {
        this.secondLevelCategoryList = secondLevelCategoryList;
    }

    /**
     * 获取二级分类名
     * @return 二级分类名
     */
    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    /**
     * 设置二级分类名
     * @param secondLevelCategoryName
     */
    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    /**
     * 获取三级分类列表
     * @return 三级分类列表
     */
    public List<GoodsCategory> getThirdLevelCategoryList() {
        return thirdLevelCategoryList;
    }

    /**
     * 设置三级分类列表
     * @param thirdLevelCategoryList
     */
    public void setThirdLevelCategoryList(List<GoodsCategory> thirdLevelCategoryList) {
        this.thirdLevelCategoryList = thirdLevelCategoryList;
    }

    /**
     * 获取当前分类名
     * @return 当前分类名
     */
    public String getCurrentCategoryName() {
        return currentCategoryName;
    }

    /**
     * 设置当前分类名
     * @param currentCategoryName
     */
    public void setCurrentCategoryName(String currentCategoryName) {
        this.currentCategoryName = currentCategoryName;
    }
}

/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service;

import ltd.newbee.mall.api.mall.vo.NewBeeMallIndexCategoryVO;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;

/**
 * 该类为商品分类业务层接口
 *
 * @author 十三
 */
public interface NewBeeMallCategoryService {

    /**
     * 新增分类
     * @param goodsCategory
     * @return
     */
    String saveCategory(GoodsCategory goodsCategory);

    /**
     * 修改分类
     * @param goodsCategory
     * @return
     */
    String updateGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 根据id返回分类
     * @param id
     * @return
     */
    GoodsCategory getGoodsCategoryById(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean deleteBatch(Long[] ids);

    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<NewBeeMallIndexCategoryVO> getCategoriesForIndex();

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    /**
     * 根据parentId和level获取分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
}

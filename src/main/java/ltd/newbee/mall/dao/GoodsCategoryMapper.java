/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 该类为商品分类的数据访问接口
 *
 * @author 13
 */
@Mapper
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    /**
     * 根据id删除分类
     * @param categoryId
     * @return
     */
    int deleteByPrimaryKey(Long categoryId);

    /**
     * 新增分类
     * @param record
     * @return
     */
    int insert(GoodsCategory record);

    /**
     * 新增分类
     * @param record
     * @return
     */
    int insertSelective(GoodsCategory record);

    /**
     * 根据id返回分类
     * @param categoryId
     * @return 分类
     */
    GoodsCategory selectByPrimaryKey(Long categoryId);

    /**
     * 根据分类级别和分类名称获取分类
     * @param categoryLevel
     * @param categoryName
     * @return 分类
     */
    GoodsCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    /**
     * 更新分类信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(GoodsCategory record);

    /**
     * 更新分类信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(GoodsCategory record);

    /**
     * 根据分页查询参数获取分类列表
     * @param pageUtil
     * @return 分类列表
     */
    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageUtil);

    /**
     * 根据分页查询参数获取总分类数量
     * @param pageUtil
     * @return 总分类数量
     */
    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Long[] ids);

    /**
     * 根据分类等级、父分类id和数量获取分类列表
     * @param parentIds
     * @param categoryLevel
     * @param number
     * @return 分类列表
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);
}

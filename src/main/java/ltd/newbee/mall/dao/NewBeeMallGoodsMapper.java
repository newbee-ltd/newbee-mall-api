/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.StockNumDTO;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 该类为商品的数据访问接口
 *
 * @author 13
 */
@Mapper
public interface NewBeeMallGoodsMapper {

    /**
     * 根据id删除商品
     * @param goodsId
     * @return
     */
    int deleteByPrimaryKey(Long goodsId);

    /**
     * 新增商品
     * @param record
     * @return
     */
    int insert(NewBeeMallGoods record);

    /**
     * 新增商品
     * @param record
     * @return
     */
    int insertSelective(NewBeeMallGoods record);

    /**
     * 根据id获取商品
     * @param goodsId
     * @return
     */
    NewBeeMallGoods selectByPrimaryKey(Long goodsId);

    /**
     * 根据分类id和商品名称获取商品
     * @param goodsName
     * @param goodsCategoryId
     * @return
     */
    NewBeeMallGoods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    /**
     * 更新商品信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(NewBeeMallGoods record);

    /**
     * 更新商品信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(NewBeeMallGoods record);

    /**
     * 根据分页查询参数获取商品列表
     * @param pageUtil
     * @return 商品列表
     */
    List<NewBeeMallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    /**
     * 根据分页查询参数获取总商品数
     * @param pageUtil
     * @return 商品数
     */
    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    /**
     * 根据多个id获取商品列表
     * @param goodsIds
     * @return 商品列表
     */
    List<NewBeeMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    /**
     * 根据搜索获取商品列表
     * @param pageUtil
     * @return 商品列表
     */
    List<NewBeeMallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

    /**
     * 搜所获取的商品总数
     * @param pageUtil
     * @return 商品总数
     */
    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

    /**
     * 批量新增商品
     * @param newBeeMallGoodsList
     * @return
     */
    int batchInsert(@Param("newBeeMallGoodsList") List<NewBeeMallGoods> newBeeMallGoodsList);

    /**
     * 批量更新库存
     * @param stockNumDTOS
     * @return
     */
    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    /**
     * 批量恢复库存
     * @param stockNumDTOS
     * @return
     */
    int recoverStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    /**
     * 批量更新商品上架下架状态
     * @param orderIds
     * @param sellStatus
     * @return
     */
    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}

/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallShoppingCartItem;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实现购物车项在数据库中的操作方法
 */
@Mapper
public interface NewBeeMallShoppingCartItemMapper {
    /**
     * 通过id删除购物车项
     * @param cartItemId 购物车项id
     * @return 0失败，1成功
     */
    int deleteByPrimaryKey(Long cartItemId);

    /**
     * 根据全部购物车项信息保存购物车项
     * @param record 要保存的购物车项
     * @return 0失败，1成功
     */
    int insert(NewBeeMallShoppingCartItem record);

    /**
     * 根据部分购物车项信息保存购物车项
     * @param record 要保存的购物车项
     * @return 0失败，1成功
     */
    int insertSelective(NewBeeMallShoppingCartItem record);

    /**
     * 根据购物车项id查询购物车项
     * @param cartItemId 购物车项id
     * @return 查询到的购物车项
     */
    NewBeeMallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    /**
     * 通过用户名与商品id找到购物车项
     * @param newBeeMallUserId 用户名
     * @param goodsId 商品id
     * @return 购物车项
     */
    NewBeeMallShoppingCartItem selectByUserIdAndGoodsId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("goodsId") Long goodsId);

    /**
     * 查询用户的若干个购物车项
     * @param newBeeMallUserId 用户名
     * @param number 选择返回的行数
     * @return 用户的number个购物车项
     */
    List<NewBeeMallShoppingCartItem> selectByUserId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("number") int number);

    /**
     * 通过用户名和给出的多个购物车项来查询购物车项
     * @param newBeeMallUserId 用户名
     * @param cartItemIds 多个购物车项id
     * @return 该用户购物车的多个购物车项id的购物车项
     */
    List<NewBeeMallShoppingCartItem> selectByUserIdAndCartItemIds(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("cartItemIds") List<Long> cartItemIds);

    /**
     * 通过用户名查询购物车项总量
     * @param newBeeMallUserId 用户名
     * @return 用户购物车总量
     */
    int selectCountByUserId(Long newBeeMallUserId);

    /**
     * 通过部分购物车项信息更新购物车项
     * @param record 需要更新到的购物车项
     * @return 0失败1成功
     */
    int updateByPrimaryKeySelective(NewBeeMallShoppingCartItem record);

    /**
     * 通过部分购物车项信息更新购物车项
     * @param record 需要更新到的购物车项
     * @return 0失败1成功
     */
    int updateByPrimaryKey(NewBeeMallShoppingCartItem record);

    /**
     * 需要删除的多个购物车项id
     * @param ids 需要删除的多个购物车项id
     * @return 0失败1成功
     */
    int deleteBatch(List<Long> ids);

    /**
     * 查询我的购物车项
     * @param pageUtil 分页查询参数
     * @return 我的购物车项(分页)
     */
    List<NewBeeMallShoppingCartItem> findMyNewBeeMallCartItems(PageQueryUtil pageUtil);

    /**
     * 分页参数查询购物车项总数量
     * @param pageUtil 分页查询参数
     * @return 购物车总数量
     */
    int getTotalMyNewBeeMallCartItems(PageQueryUtil pageUtil);
}

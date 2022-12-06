/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewBeeMallOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(NewBeeMallOrder record);

    int insertSelective(NewBeeMallOrder record);

    NewBeeMallOrder selectByPrimaryKey(Long orderId);

    NewBeeMallOrder selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(NewBeeMallOrder record);

    int updateByPrimaryKey(NewBeeMallOrder record);

    List<NewBeeMallOrder> findNewBeeMallOrderList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallOrders(PageQueryUtil pageUtil);

    /*
     * 注解@Param用于映射属性与xml中的select标签的parameterType属性
     * 当使用了@Param注解来声明参数的时候，SQL语句取值使用#{}，${}取值都可以。
     * 当不使用@Param注解声明参数的时候，必须使用的是#{}来取参数。使用${}方式取值会报错。
     * 不使用@Param注解时，参数只能有一个，并且是Javabean。在SQL语句里可以引用JavaBean的属性，而且只能引用JavaBean的属性。
     */
    List<NewBeeMallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);
}

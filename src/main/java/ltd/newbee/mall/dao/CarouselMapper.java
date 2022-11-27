/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 该类为轮播图的数据访问接口
 *
 * @author 13
 */
@Mapper
public interface CarouselMapper {
    int deleteByPrimaryKey(Integer carouselId);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectByPrimaryKey(Integer carouselId);

    int updateByPrimaryKeySelective(Carousel record);

    int updateByPrimaryKey(Carousel record);

    /**
     * 根据分页查询参数获取轮播图列表
     * @param pageUtil
     * @return 轮播图列表
     */
    List<Carousel> findCarouselList(PageQueryUtil pageUtil);

    /**
     * 根据分页查询参数获取总轮播图数
     * @param pageUtil
     * @return 轮播图数
     */
    int getTotalCarousels(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

    /**
     * 根据数量获取轮播图列表
     * @param number
     * @return 轮播图列表
     */
    List<Carousel> findCarouselsByNum(@Param("number") int number);
}

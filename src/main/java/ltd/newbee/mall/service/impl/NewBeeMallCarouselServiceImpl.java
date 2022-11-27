/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.api.mall.vo.NewBeeMallIndexCarouselVO;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dao.CarouselMapper;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.service.NewBeeMallCarouselService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 该类为轮播图业务逻辑的实现
 *
 * @author 13
 */
@Service
public class NewBeeMallCarouselServiceImpl implements NewBeeMallCarouselService {

    @Autowired
    private CarouselMapper carouselMapper;


    /**
     * 后台分页
     * @param pageUtil
     * @return
     */
    @Override
    public PageResult getCarouselPage(PageQueryUtil pageUtil) {
        List<Carousel> carousels = carouselMapper.findCarouselList(pageUtil);
        int total = carouselMapper.getTotalCarousels(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     * 新增轮播图
     * @param carousel
     * @return 操作结果是否成功
     */
    @Override
    public String saveCarousel(Carousel carousel) {
        /**
         * 如果轮播图新增成功，返回success，否则返回数据库error
         */
        if (carouselMapper.insertSelective(carousel) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 修改轮播图信息
     * @param carousel
     * @return 操作结果是否成功
     */
    @Override
    public String updateCarousel(Carousel carousel) {
        //根据id查找轮播图
        Carousel temp = carouselMapper.selectByPrimaryKey(carousel.getCarouselId());
        /**
         * 如果轮播图为空，返回操作结果为数据不存在
         */
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        /**
         * 更新修改时间
         */
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setRedirectUrl(carousel.getRedirectUrl());
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setUpdateTime(new Date());
        /**
         * 如果更新成功，返回success，否则返回数据库error
         */
        if (carouselMapper.updateByPrimaryKeySelective(temp) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 根据id返回轮播图
     * @param id
     * @return 轮播图
     */
    @Override
    public Carousel getCarouselById(Integer id) {
        return carouselMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除轮播图
     * @param ids
     * @return 删除结果
     */
    @Override
    public Boolean deleteBatch(Long[] ids) {
        //数组大小小于1，返回错误
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return carouselMapper.deleteBatch(ids) > 0;
    }

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    @Override
    public List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number) {
        //创建首页轮播图VO列表
        List<NewBeeMallIndexCarouselVO> newBeeMallIndexCarouselVOS = new ArrayList<>(number);
        //设置轮播图列表
        List<Carousel> carousels = carouselMapper.findCarouselsByNum(number);
        /**
         * 如果轮播图列表不为空，进行类型转换
         */
        if (!CollectionUtils.isEmpty(carousels)) {
            newBeeMallIndexCarouselVOS = BeanUtil.copyList(carousels, NewBeeMallIndexCarouselVO.class);
        }
        return newBeeMallIndexCarouselVOS;
    }
}

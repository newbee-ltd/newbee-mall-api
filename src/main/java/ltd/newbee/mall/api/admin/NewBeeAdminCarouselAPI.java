/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.newbee.mall.api.admin.param.BatchIdParam;
import ltd.newbee.mall.api.admin.param.CarouselAddParam;
import ltd.newbee.mall.api.admin.param.CarouselEditParam;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.config.annotation.TokenToAdminUser;
import ltd.newbee.mall.entity.AdminUserToken;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.service.NewBeeMallCarouselService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类为后台管理系统轮播图模块接口
 *
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@RestController
@Api(value = "v1", tags = "8-1.后台管理系统轮播图模块接口")
@RequestMapping("/manage-api/v1")
public class NewBeeAdminCarouselAPI {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeAdminCarouselAPI.class);

    @Resource
    NewBeeMallCarouselService newBeeMallCarouselService;

    private static final String ADMIN_USER = "adminUser:{}";

    /**
     * 获取轮播图列表
     * @param pageNumber
     * @param pageSize
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.GET)
    @ApiOperation(value = "轮播图列表", notes = "轮播图列表")
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize, @TokenToAdminUser AdminUserToken adminUser) {
        //日志生成
        logger.info(ADMIN_USER, adminUser.toString());
        //分页参数异常
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        Map params = new HashMap(4);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        //封装轮播图数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallCarouselService.getCarouselPage(pageUtil));
    }

    /**
     * 添加轮播图
     * @param carouselAddParam
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.POST)
    @ApiOperation(value = "新增轮播图", notes = "新增轮播图")
    public Result save(@RequestBody @Valid CarouselAddParam carouselAddParam, @TokenToAdminUser AdminUserToken adminUser) {
        //日志生成
        logger.info(ADMIN_USER, adminUser.toString());
        Carousel carousel = new Carousel();
        //将轮播图添加参数对象转换为轮播图
        BeanUtil.copyProperties(carouselAddParam, carousel);
        //获取轮播图添加结果
        String result = newBeeMallCarouselService.saveCarousel(carousel);
        /**
         * 如果添加成功，返回成功响应，否则返回失败响应
         */
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改轮播图信息
     * @param carouselEditParam
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.PUT)
    @ApiOperation(value = "修改轮播图信息", notes = "修改轮播图信息")
    public Result update(@RequestBody CarouselEditParam carouselEditParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info(ADMIN_USER, adminUser.toString());
        Carousel carousel = new Carousel();
        //将轮播图修改信息参数对象转换为轮播图
        BeanUtil.copyProperties(carouselEditParam, carousel);
        //获取轮播图修改结果
        String result = newBeeMallCarouselService.updateCarousel(carousel);
        /**
         * 如果修改成功，返回成功响应，否则返回失败响应
         */
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 根据id获取单条轮播图详情
     * @param id
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/carousels/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条轮播图信息", notes = "根据id查询")
    public Result info(@PathVariable("id") Integer id, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info(ADMIN_USER, adminUser.toString());
        //根据id获取单条轮播图
        Carousel carousel = newBeeMallCarouselService.getCarouselById(id);
        /**
         * 如果轮播图为空，返回数据不存在错误响应，否则返回成功响应
         */
        if (carousel == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(carousel);
    }

    /**
     * 批量删除轮播图
     * @param batchIdParam
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除轮播图信息", notes = "批量删除轮播图信息")
    public Result delete(@RequestBody BatchIdParam batchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info(ADMIN_USER, adminUser.toString());
        /**
         * 如果批量处理参数为空或数组长度小于1，返回参数异常失败响应
         */
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        /**
         * 如果批量删除成功，返回成功响应，否则返回失败响应
         */
        if (newBeeMallCarouselService.deleteBatch(batchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
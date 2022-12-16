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
import ltd.newbee.mall.api.admin.param.GoodsCategoryAddParam;
import ltd.newbee.mall.api.admin.param.GoodsCategoryEditParam;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.config.annotation.TokenToAdminUser;
import ltd.newbee.mall.entity.AdminUserToken;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类为后台管理系统分类模块接口
 *
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@RestController
@Api(value = "v1", tags = "8-2.后台管理系统分类模块接口")
@RequestMapping("/manage-api/v1")
public class NewBeeAdminGoodsCategoryAPI {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeAdminGoodsCategoryAPI.class);

    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    private static final String ADMIN_USER = "adminUser:{}";

    /**
     * 根据级别和上级分类id获取商品分类列表
     * @param pageNumber
     * @param pageSize
     * @param categoryLevel
     * @param parentId
     * @param adminUser
     * @return
     */
    @GetMapping(value = "/categories")
    @ApiOperation(value = "商品分类列表", notes = "根据级别和上级分类id查询")
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "分类级别") Integer categoryLevel,
                       @RequestParam(required = false) @ApiParam(value = "上级分类的id") Long parentId, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        //分页参数异常
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10 || categoryLevel == null || categoryLevel < 0 || categoryLevel > 3 || parentId == null || parentId < 0) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        Map params = new HashMap(8);
        /**
         * 设置分页查询参数
         */
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        params.put("categoryLevel", categoryLevel);
        params.put("parentId", parentId);
        //封装商品分类数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallCategoryService.getCategorisPage(pageUtil));
    }

    /**
     * 三级分类联动获取分类列表
     * @param categoryId
     * @param adminUser
     * @return
     */
    @GetMapping(value = "/categories4Select")
    @ApiOperation(value = "商品分类列表", notes = "用于三级分类联动效果制作")
    public Result listForSelect(@RequestParam("categoryId") Long categoryId, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        if (categoryId == null || categoryId < 1) {
            return ResultGenerator.genFailResult("缺少参数！");
        }
        GoodsCategory category = newBeeMallCategoryService.getGoodsCategoryById(categoryId);
        //既不是一级分类也不是二级分类则为不返回数据
        if (category == null || category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        Map categoryResult = new HashMap(4);
        if (category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel()) {
            //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel()) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        return ResultGenerator.genSuccessResult(categoryResult);
    }

    /**
     * 添加分类项
     * @param goodsCategoryAddParam
     * @param adminUser
     * @return
     */
    @PostMapping(value = "/categories")
    @ApiOperation(value = "新增分类", notes = "新增分类")
    public Result save(@RequestBody @Valid GoodsCategoryAddParam goodsCategoryAddParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        GoodsCategory goodsCategory = new GoodsCategory();
        //将分类添加参数对象转换为分类
        BeanUtil.copyProperties(goodsCategoryAddParam, goodsCategory);
        //获取分类添加结果
        String result = newBeeMallCategoryService.saveCategory(goodsCategory);
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
     * 修改分类信息
     * @param goodsCategoryEditParam
     * @param adminUser
     * @return
     */
    @PutMapping(value = "/categories")
    @ApiOperation(value = "修改分类信息", notes = "修改分类信息")
    public Result update(@RequestBody @Valid GoodsCategoryEditParam goodsCategoryEditParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        GoodsCategory goodsCategory = new GoodsCategory();
        //将分类修改参数对象转换为分类
        BeanUtil.copyProperties(goodsCategoryEditParam, goodsCategory);
        //获取分类修改结果
        String result = newBeeMallCategoryService.updateGoodsCategory(goodsCategory);
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
     * 根据id获取单条分类信息
     * @param id
     * @param adminUser
     * @return
     */
    @GetMapping(value = "/categories/{id}")
    @ApiOperation(value = "获取单条分类信息", notes = "根据id查询")
    public Result info(@PathVariable("id") Long id, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        //根据id获取单条分类信息
        GoodsCategory goodsCategory = newBeeMallCategoryService.getGoodsCategoryById(id);
        /**
         * 如果分类为空，返回数据不存在错误响应，否则返回成功响应
         */
        if (goodsCategory == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(goodsCategory);
    }

    /**
     * 批量删除分类信息
     * @param batchIdParam
     * @param adminUser
     * @return
     */
    @DeleteMapping(value = "/categories")
    @ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息")
    public Result delete(@RequestBody BatchIdParam batchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        /**
         * 如果批量处理参数为空或数组长度小于1，返回参数异常失败响应
         */
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        /**
         * 如果批量删除成功，返回成功响应，否则返回失败响应
         */
        if (newBeeMallCategoryService.deleteBatch(batchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}

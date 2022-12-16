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
import ltd.newbee.mall.api.admin.param.IndexConfigAddParam;
import ltd.newbee.mall.api.admin.param.IndexConfigEditParam;
import ltd.newbee.mall.common.IndexConfigTypeEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.config.annotation.TokenToAdminUser;
import ltd.newbee.mall.entity.AdminUserToken;
import ltd.newbee.mall.entity.IndexConfig;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
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
 * 该类为后台管理系统首页配置模块接口
 *
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@RestController
@Api(value = "v1", tags = "8-4.后台管理系统首页配置模块接口")
@RequestMapping("/manage-api/v1")
public class NewBeeAdminIndexConfigAPI {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeAdminIndexConfigAPI.class);

    @Resource
    private NewBeeMallIndexConfigService newBeeMallIndexConfigService;

    private static final String ADMIN_USER = "adminUser:{}";

    /**
     * 获取首页配置列表
     * @param pageNumber
     * @param pageSize
     * @param configType
     * @param adminUser
     * @return
     */
    @GetMapping(value = "/indexConfigs")
    @ApiOperation(value = "首页配置列表", notes = "首页配置列表")
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐") Integer configType, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        //分页参数异常
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        //如果首页配置项类型错误，返回非法参数失败响应
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            return ResultGenerator.genFailResult("非法参数！");
        }
        Map params = new HashMap(8);
        /**
         * 设置分页查询参数
         */
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        params.put("configType", configType);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallIndexConfigService.getConfigsPage(pageUtil));
    }

    /**
     * 添加首页配置项
     * @param indexConfigAddParam
     * @param adminUser
     * @return
     */
    @PostMapping(value = "/indexConfigs")
    @ApiOperation(value = "新增首页配置项", notes = "新增首页配置项")
    public Result save(@RequestBody @Valid IndexConfigAddParam indexConfigAddParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        IndexConfig indexConfig = new IndexConfig();
        //将首页配置项添加参数对象转换为首页配置项
        BeanUtil.copyProperties(indexConfigAddParam, indexConfig);
        //获取首页配置项添加结果
        String result = newBeeMallIndexConfigService.saveIndexConfig(indexConfig);
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
     * 修改首页配置项
     * @param indexConfigEditParam
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/indexConfigs", method = RequestMethod.PUT)
    @ApiOperation(value = "修改首页配置项", notes = "修改首页配置项")
    public Result update(@RequestBody @Valid IndexConfigEditParam indexConfigEditParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        IndexConfig indexConfig = new IndexConfig();
        //将首页配置项信息修改参数对象转换为首页配置项
        BeanUtil.copyProperties(indexConfigEditParam, indexConfig);
        //获取首页配置项修改结果
        String result = newBeeMallIndexConfigService.updateIndexConfig(indexConfig);
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
     * 根据id获取单条首页配置项信息
     * @param id
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/indexConfigs/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条首页配置项信息", notes = "根据id查询")
    public Result info(@PathVariable("id") Long id, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("{}", ADMIN_USER);
        //根据id获取配置项
        IndexConfig config = newBeeMallIndexConfigService.getIndexConfigById(id);
        /**
         * 如果首页配置项为空，返回错误响应，否则返回成功响应
         */
        if (config == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(config);
    }

    /**
     * 批量删除首页配置项
     * @param batchIdParam
     * @param adminUser
     * @return
     */
    @RequestMapping(value = "/indexConfigs", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除首页配置项信息", notes = "批量删除首页配置项信息")
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
        if (newBeeMallIndexConfigService.deleteBatch(batchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}

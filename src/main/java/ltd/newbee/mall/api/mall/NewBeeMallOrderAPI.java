/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.newbee.mall.api.mall.param.SaveOrderParam;
import ltd.newbee.mall.api.mall.vo.NewBeeMallOrderDetailVO;
import ltd.newbee.mall.api.mall.vo.NewBeeMallOrderListVO;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.config.annotation.TokenToMallUser;
import ltd.newbee.mall.api.mall.vo.NewBeeMallShoppingCartItemVO;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.MallUserAddress;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.service.NewBeeMallUserAddressService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单相关接口
 * 他的电脑屏幕到底是得有多宽 这么不乐意换行
 * 他自己造轮子的劣势在这种Controller里面就显然体现出来了
 * 每次请求都需要传入一个token，然后根据token去数据库里面查询用户信息，然后再去查询购物车信息
 * 这在SpringSecurity里面一般是 LoginUser = authentication.getPrincipal()，然后就可以直接获取用户信息了
 * 本类基本上遵循了RESTful风格
 */
@RestController
@Api(value = "v1", tags = "7.新蜂商城订单操作相关接口")
@RequestMapping("/api/v1")
public class NewBeeMallOrderAPI {

    @Resource
    private NewBeeMallShoppingCartService newBeeMallShoppingCartService;
    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;
    @Resource
    private NewBeeMallUserAddressService newBeeMallUserAddressService;

    /**
     * 生成订单的接口
     * @param saveOrderParam 包含订单id与地址id
     * @param loginMallUser 当前登录用户 是一个基本用户 在MallUser类中他只用了@Data注释，我很好奇这玩意是怎么自动装配上的
     * @return 生成订单的结果 包含在一个字符串中
     */
    @PostMapping("/saveOrder")
    @ApiOperation(value = "生成订单接口", notes = "传参为地址id和待结算的购物项id数组")
    public Result<String> saveOrder(
            @ApiParam(value = "订单参数")
            @RequestBody SaveOrderParam saveOrderParam,
            @TokenToMallUser MallUser loginMallUser) {
        int priceTotal = 0;
        // if语句检查各条参数是否合法
        if (saveOrderParam == null || saveOrderParam.getCartItemIds() == null ||
                saveOrderParam.getAddressId() == null) {
            NewBeeMallException.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        if (saveOrderParam.getCartItemIds().length < 1) {
            NewBeeMallException.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        List<NewBeeMallShoppingCartItemVO> itemsForSave =
                newBeeMallShoppingCartService.getCartItemsForSettle(
                        Arrays.asList(saveOrderParam.getCartItemIds()),
                        loginMallUser.getUserId());
        if (CollectionUtils.isEmpty(itemsForSave)) {
            //无数据
            NewBeeMallException.fail("参数异常");
        } else {
            //总价
            for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : itemsForSave) {
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount()
                        * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                NewBeeMallException.fail("价格异常");
            }
            MallUserAddress address =
                    newBeeMallUserAddressService.getMallUserAddressById(saveOrderParam.getAddressId());
            // 核对当前用户id与地址id是否匹配
            if (!loginMallUser.getUserId().equals(address.getUserId())) {
                return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
            }
            //保存订单并返回订单号
            String saveOrderResult = newBeeMallOrderService.saveOrder(loginMallUser, address, itemsForSave);
            Result result = ResultGenerator.genSuccessResult();
            result.setData(saveOrderResult);
            return result;
        }
        return ResultGenerator.genFailResult("生成订单失败");
    }

    /**
     * 获取订单详情的接口
     * @param orderNo 订单号
     * @param loginMallUser 当前登录用户
     * @return 订单详情
     */
    @GetMapping("/order/{orderNo}")
    @ApiOperation(value = "订单详情接口", notes = "传参为订单号")
    public Result<NewBeeMallOrderDetailVO> orderDetailPage(
            @ApiParam(value = "订单号")
            @PathVariable("orderNo") String orderNo,
            @TokenToMallUser MallUser loginMallUser) {
        return ResultGenerator.genSuccessResult(
                newBeeMallOrderService.getOrderDetailByOrderNo(orderNo, loginMallUser.getUserId()));
    }

    /**
     * 分页获取订单列表的接口
     * @param pageNumber 页码
     * @param loginMallUser 当前登录用户
     * @return 订单列表
     */
    @GetMapping("/order")
    @ApiOperation(value = "订单列表接口", notes = "传参为页码")
    public Result<PageResult<List<NewBeeMallOrderListVO>>> orderList(
            @ApiParam(value = "页码")
            @RequestParam(required = false) Integer pageNumber,
            @ApiParam(value = "订单状态:0.待支付 1.待确认 2.待发货 3:已发货 4.交易成功")
            @RequestParam(required = false) Integer status,
            @TokenToMallUser MallUser loginMallUser) {
        Map params = new HashMap(8);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("userId", loginMallUser.getUserId());
        params.put("orderStatus", status);
        params.put("page", pageNumber);
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装分页请求参数
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallOrderService.getMyOrders(pageUtil));
    }

    /**
     * 取消订单的接口
     * @param orderNo 订单号
     * @param loginMallUser 当前登录用户
     * @return 取消订单的结果
     */
    @PutMapping("/order/{orderNo}/cancel")
    @ApiOperation(value = "订单取消接口", notes = "传参为订单号")
    public Result cancelOrder(
            @ApiParam(value = "订单号")
            @PathVariable("orderNo") String orderNo,
            @TokenToMallUser MallUser loginMallUser) {
        String cancelOrderResult = newBeeMallOrderService.cancelOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    /**
     * 确认收货的接口
     * @param orderNo 订单号
     * @param loginMallUser 当前登录用户
     * @return 确认收货的结果
     */
    @PutMapping("/order/{orderNo}/finish")
    @ApiOperation(value = "确认收货接口", notes = "传参为订单号")
    public Result finishOrder(
            @ApiParam(value = "订单号")
            @PathVariable("orderNo") String orderNo,
            @TokenToMallUser MallUser loginMallUser) {
        String finishOrderResult = newBeeMallOrderService.finishOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    /**
     * 获取订单支付的接口
     * @param orderNo 订单号
     * @param payType 支付方式
     * @return 订单支付的结果
     */
    @GetMapping("/paySuccess")
    @ApiOperation(value = "模拟支付成功回调的接口", notes = "传参为订单号和支付方式")
    public Result paySuccess(
            @ApiParam(value = "订单号")
            @RequestParam("orderNo") String orderNo,
            @ApiParam(value = "支付方式")
            @RequestParam("payType") int payType) {
        String payResult = newBeeMallOrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}

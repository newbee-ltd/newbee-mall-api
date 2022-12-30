/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.mall;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import ltd.newbee.mall.api.mall.vo.NewBeeMallSearchGoodsVO;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.config.annotation.TokenToMallUser;
import ltd.newbee.mall.api.mall.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(description = "v1", name = "新蜂商城商品相关接口")
@RequestMapping("/api/v1")
public class NewBeeMallGoodsAPI {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeMallGoodsAPI.class);

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;

    @GetMapping("/search")
    @Operation(summary = "商品搜索接口", description = "根据关键字和分类id进行搜索")
    public Result<PageResult<List<NewBeeMallSearchGoodsVO>>> search(@RequestParam(required = false) @Parameter(description = "搜索关键字") String keyword,
                                                                    @RequestParam(required = false) @Parameter(description = "分类id") Long goodsCategoryId,
                                                                    @RequestParam(required = false) @Parameter(description = "orderBy") String orderBy,
                                                                    @RequestParam(required = false) @Parameter(description = "页码") Integer pageNumber,
                                                                    @TokenToMallUser @Parameter(hidden = true) MallUser loginMallUser) {
        
        logger.info("goods search api,keyword={},goodsCategoryId={},orderBy={},pageNumber={},userId={}", keyword, goodsCategoryId, orderBy, pageNumber, loginMallUser.getUserId());

        Map params = new HashMap(8);
        //两个搜索参数都为空，直接返回异常
        if (goodsCategoryId == null && !StringUtils.hasText(keyword)) {
            NewBeeMallException.fail("非法的搜索参数");
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("goodsCategoryId", goodsCategoryId);
        params.put("page", pageNumber);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //对keyword做过滤 去掉空格
        if (StringUtils.hasText(keyword)) {
            params.put("keyword", keyword);
        }
        if (StringUtils.hasText(orderBy)) {
            params.put("orderBy", orderBy);
        }
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil));
    }

    @GetMapping("/goods/detail/{goodsId}")
    @Operation(summary = "商品详情接口", description = "传参为商品id")
    public Result<NewBeeMallGoodsDetailVO> goodsDetail(@Parameter(description = "商品id") @PathVariable("goodsId") Long goodsId, @TokenToMallUser @Parameter(hidden = true) MallUser loginMallUser) {
        logger.info("goods detail api,goodsId={},userId={}", goodsId, loginMallUser.getUserId());
        if (goodsId < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            NewBeeMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        NewBeeMallGoodsDetailVO goodsDetailVO = new NewBeeMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }

}

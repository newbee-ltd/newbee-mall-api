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
import ltd.newbee.mall.api.mall.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.api.mall.vo.NewBeeMallSearchGoodsVO;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.config.annotation.TokenToMallUser;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.IndexService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.service.SearchService;
import ltd.newbee.mall.util.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类为商城商品相关接口
 *
 * @author 十三
 */
@RestController
@Api(value = "v1", tags = "4.新蜂商城商品相关接口")
@RequestMapping("/api/v1")
public class NewBeeMallGoodsAPI {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeMallGoodsAPI.class);

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;

    @Resource
    private IndexService indexService;

    @Resource
    private SearchService searchService;

    /**
     * 实现根据关键字和分类id进行搜索的商品搜索接口
     * @param keyword
     * @param pageNumber
     * @param loginMallUser
     * @return 商品搜索响应结果
     */
    @GetMapping("/luceneSearch")
    @ApiOperation(value = "商品搜索接口", notes = "基于lucene的模糊搜索")
    public Result<PageResult<List<NewBeeMallSearchGoodsVO>>> search(
            @RequestParam(required = false) @ApiParam(value = "搜索关键字") String keyword,
            @RequestParam(required = false) @ApiParam(value = "分类id") Long goodsCategoryId,
            @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
            @TokenToMallUser MallUser loginMallUser) throws IOException,ParseException {

        logger.info("goods search api,keyword={},pageNumber={},userId={}", keyword, pageNumber, loginMallUser.getUserId());

        //判定关键词是否为空，为空返回全部商品
        if (keyword.isEmpty()) {
            NewBeeMallException.fail("非法的搜索参数");
        }

        //没有当前页码，当前页码默认为1
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }

        // 如果categoryId为空
        if (goodsCategoryId == null) {
            // 创建索引库
            indexService.createIndex(null);
        } else {
            // 创建索引库
            indexService.createIndex(goodsCategoryId);
        }

        //通过索引查询结果
        List<NewBeeMallGoods> list = searchService.search(keyword);
        //创建视图对象集合用于返回
        List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();

        if (!CollectionUtils.isEmpty(list)) {
            newBeeMallSearchGoodsVOS = BeanUtil.copyList(list, NewBeeMallSearchGoodsVO.class);
            /**
             * 对每一个商品VO，如果字符串过长，则重新设置搜索VO中的商品名称和商品简介
             */
            for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {
                String goodsName = newBeeMallSearchGoodsVO.getGoodsName();
                String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
                //字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    newBeeMallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        //将视图表装进分页返回
        PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS,
                list.size(),
                Constants.GOODS_SEARCH_PAGE_LIMIT,
                pageNumber);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索接口", notes = "根据关键字和分类id进行搜索")
    public Result<PageResult<List<NewBeeMallSearchGoodsVO>>> search(
            @RequestParam(required = false) @ApiParam(value = "搜索关键字") String keyword,
            @RequestParam(required = false) @ApiParam(value = "分类id") Long goodsCategoryId,
            @RequestParam(required = false) @ApiParam(value = "orderBy") String orderBy,
            @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
            @TokenToMallUser MallUser loginMallUser) {

        logger.info("goods search api,keyword={},goodsCategoryId={},orderBy={},pageNumber={},userId={}", keyword, goodsCategoryId, orderBy, pageNumber, loginMallUser.getUserId());

        Map params = new HashMap(8);
        //两个搜索参数都为空，直接返回异常
        if (goodsCategoryId == null && !StringUtils.hasLength(keyword)) {
            NewBeeMallException.fail("非法的搜索参数");
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("goodsCategoryId", goodsCategoryId);
        params.put("page", pageNumber);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //对keyword做过滤 去掉空格
        if (StringUtils.hasLength(keyword)) {
            params.put("keyword", keyword);
        }
        if (StringUtils.hasLength(orderBy)) {
            params.put("orderBy", orderBy);
        }
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil));
    }

    /**
     * 实现商品详情接口
     * @param goodsId
     * @param loginMallUser
     * @return 商品详情成功响应结果
     */
    @GetMapping("/goods/detail/{goodsId}")
    @ApiOperation(value = "商品详情接口", notes = "传参为商品id")
    public Result<NewBeeMallGoodsDetailVO> goodsDetail(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId, @TokenToMallUser MallUser loginMallUser) {
        logger.info("goods detail api,goodsId={},userId={}", goodsId, loginMallUser.getUserId());
        if (goodsId < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        //根据id获取商品详情
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        /*
         * 如果商品状态为下架，则抛出异常
         */
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            NewBeeMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        NewBeeMallGoodsDetailVO goodsDetailVO = new NewBeeMallGoodsDetailVO();
        //将商品实体类转换为商品详情页VO
        BeanUtil.copyProperties(goods, goodsDetailVO);
        //将商品实体类中的详情图放到VO的详情图中，用“，”作为分割符
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }

}

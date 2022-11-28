/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.api.mall.vo.NewBeeMallIndexConfigGoodsVO;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dao.IndexConfigMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.IndexConfig;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallIndexConfigService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 该类为首页配置项业务逻辑的实现
 *
 * @author 13
 */
@Service
public class NewBeeMallIndexConfigServiceImpl implements NewBeeMallIndexConfigService {

    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    @Override
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        //根据分页查询参数获取首页配置项列表
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        //获取首页配置项列表总数
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        //封装分页结果并返回
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     * 新增首页配置项
     * @param indexConfig
     * @return
     */
    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        /**
         * 如果该首页配置项对应的商品id不存在，返回商品不存在错误
         */
        if (goodsMapper.selectByPrimaryKey(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        /**
         * 如果已经存在有相同商品id和配置项类型的首页配置项，则添加失败
         */
        if (indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId()) != null) {
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        /**
         * 如果添加成功，返回success，否则返回数据库错误
         */
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 修改首页配置项信息
     * @param indexConfig
     * @return
     */
    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        /**
         * 如果该首页配置项对应的商品id不存在，返回商品不存在错误
         */
        if (goodsMapper.selectByPrimaryKey(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        /**
         * 如果首页配置项不存在，返回错误
         */
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        IndexConfig temp2 = indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            //goodsId相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        //设置修改时间
        indexConfig.setUpdateTime(new Date());
        /**
         * 如果修改成功，返回success，否则返回数据库错误
         */
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 根据id返回首页配置项
     * @param id
     * @return
     */
    @Override
    public IndexConfig getIndexConfigById(Long id) {
        return indexConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    @Override
    public List<NewBeeMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
        //创建指定数量的首页配置商品VO
        List<NewBeeMallIndexConfigGoodsVO> newBeeMallIndexConfigGoodsVOS = new ArrayList<>(number);
        //根据类别和数量获取首页配置商品
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        /**
         * 如果首页配置商品不为空，根据商品id将每一个商品类型转换为首页配置商品VO
         */
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId
            List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
            //根据商品id获取商品
            List<NewBeeMallGoods> newBeeMallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
            newBeeMallIndexConfigGoodsVOS = BeanUtil.copyList(newBeeMallGoods, NewBeeMallIndexConfigGoodsVO.class);
            /**
             * 对每一个首页配置商品VO，如果字符串过长，则重新设置首页配置项VO中的商品名称和商品简介
             */
            for (NewBeeMallIndexConfigGoodsVO newBeeMallIndexConfigGoodsVO : newBeeMallIndexConfigGoodsVOS) {
                String goodsName = newBeeMallIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = newBeeMallIndexConfigGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    newBeeMallIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    newBeeMallIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        //返回首页配置商品VO列表
        return newBeeMallIndexConfigGoodsVOS;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}

/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.entity.IndexConfig;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 该类为首页配置项的数据访问接口
 *
 * @author 13
 */
@Mapper
public interface IndexConfigMapper extends BaseMapper<IndexConfig> {

    /**
     * 根据id删除首页配置项
     * @param configId
     * @return
     */
    int deleteByPrimaryKey(Long configId);

    /**
     * 新增首页配置项
     * @param record
     * @return
     */
    int insert(IndexConfig record);

    /**
     * 新增首页配置项
     * @param record
     * @return
     */
    int insertSelective(IndexConfig record);

    /**
     * 根据id返回首页配置项
     * @param configId
     * @return
     */
    IndexConfig selectByPrimaryKey(Long configId);

    /**
     * 根据配置项属性和商品id返回首页配置项
     * @param configType
     * @param goodsId
     * @return 首页配置项
     */
    IndexConfig selectByTypeAndGoodsId(@Param("configType") int configType, @Param("goodsId") Long goodsId);

    /**
     * 更新首页配置项信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(IndexConfig record);

    /**
     * 更新首页配置项信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(IndexConfig record);

    /**
     * 根据分页查询参数获取首页配置项列表
     * @param pageUtil
     * @return 首页配置项列表
     */
    List<IndexConfig> findIndexConfigList(PageQueryUtil pageUtil);

    /**
     * 根据分页查询参数获取首页配置项总数
     * @param pageUtil
     * @return
     */
    int getTotalIndexConfigs(PageQueryUtil pageUtil);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Long[] ids);

    /**
     * 根据配置项属性和数量获取配置项列表
     * @param configType
     * @param number
     * @return 首页配置项列表
     */
    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
}

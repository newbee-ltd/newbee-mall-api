/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 该类为商品实体类
 *
 * @author 13
 */
@Data
public class NewBeeMallGoods {
    //商品id
    protected Long goodsId;

    //商品名称
    protected String goodsName;

    //商品简介
    protected String goodsIntro;

    //商品分类id
    protected Long goodsCategoryId;

    //商品主图
    protected String goodsCoverImg;

    //商品轮播图
    protected String goodsCarousel;

    //商品原价
    protected Integer originalPrice;

    //商品实际售价
    protected Integer sellingPrice;

    //商品库存数量
    protected Integer stockNum;

    //商品标签
    protected String tag;

    //上架状态
    protected Byte goodsSellStatus;

    //创建人id
    protected Integer createUser;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    //修改人id
    protected Integer updateUser;

    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateTime;

    //商品详情
    protected String goodsDetailContent;
}

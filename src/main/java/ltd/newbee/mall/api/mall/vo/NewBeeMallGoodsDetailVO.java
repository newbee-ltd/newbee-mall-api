/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 该类为商品详情页VO，进入具体的商品界面需要的页面数据
 *
 * @author 十三
 */
@Data
public class NewBeeMallGoodsDetailVO extends NewBeeMallSearchGoodsVO implements Serializable {


    @ApiModelProperty("商品标签")
    public String tag;

    @ApiModelProperty("商品图片")
    public String[] goodsCarouselList;

    @ApiModelProperty("商品原价")
    public Integer originalPrice;

    @ApiModelProperty("商品详情字段")
    public String goodsDetailContent;
}

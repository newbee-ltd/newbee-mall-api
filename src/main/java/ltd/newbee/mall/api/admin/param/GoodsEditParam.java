/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.admin.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class GoodsEditParam {

    @Schema(title = "待修改商品id")
    @NotNull(message = "商品id不能为空")
    @Min(value = 1, message = "商品id不能为空")
    private Long goodsId;

    @Schema(title = "商品名称")
    @NotEmpty(message = "商品名称不能为空")
    @Length(max = 128,message = "商品名称内容过长")
    private String goodsName;

    @Schema(title = "商品简介")
    @NotEmpty(message = "商品简介不能为空")
    @Length(max = 200,message = "商品简介内容过长")
    private String goodsIntro;

    @Schema(title = "分类id")
    @NotNull(message = "分类id不能为空")
    @Min(value = 1, message = "分类id最低为1")
    private Long goodsCategoryId;

    @Schema(title = "商品主图")
    @NotEmpty(message = "商品主图不能为空")
    private String goodsCoverImg;

    @Schema(title = "originalPrice")
    @NotNull(message = "originalPrice不能为空")
    @Min(value = 1, message = "originalPrice最低为1")
    @Max(value = 1000000, message = "originalPrice最高为1000000")
    private Integer originalPrice;

    @Schema(title = "sellingPrice")
    @NotNull(message = "sellingPrice不能为空")
    @Min(value = 1, message = "sellingPrice最低为1")
    @Max(value = 1000000, message = "sellingPrice最高为1000000")
    private Integer sellingPrice;

    @Schema(title = "库存")
    @NotNull(message = "库存不能为空")
    @Min(value = 1, message = "库存最低为1")
    @Max(value = 100000, message = "库存最高为100000")
    private Integer stockNum;

    @Schema(title = "商品标签")
    @NotEmpty(message = "商品标签不能为空")
    @Length(max = 16,message = "商品标签内容过长")
    private String tag;

    private Byte goodsSellStatus;

    @Schema(title = "商品详情")
    @NotEmpty(message = "商品详情不能为空")
    private String goodsDetailContent;
}
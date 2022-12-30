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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarouselEditParam {

    @Schema(title = "待修改轮播图id")
    @NotNull(message = "轮播图id不能为空")
    @Min(1)
    private Integer carouselId;

    @Schema(title = "轮播图URL地址")
    @NotEmpty(message = "轮播图URL不能为空")
    private String carouselUrl;

    @Schema(title = "轮播图跳转地址")
    @NotEmpty(message = "轮播图跳转地址不能为空")
    private String redirectUrl;

    @Schema(title = "排序值")
    @Min(value = 1, message = "carouselRank最低为1")
    @Max(value = 200, message = "carouselRank最高为200")
    @NotNull(message = "carouselRank不能为空")
    private Integer carouselRank;
}

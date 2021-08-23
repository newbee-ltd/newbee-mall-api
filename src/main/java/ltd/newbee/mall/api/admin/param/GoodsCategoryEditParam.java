/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GoodsCategoryEditParam {

    @ApiModelProperty("待修改分类id")
    @NotNull(message = "分类id不能为空")
    @Min(value = 1, message = "分类id不能为空")
    private Long categoryId;

    @ApiModelProperty("分类层级")
    @NotNull(message = "categoryLevel不能为空")
    @Min(value = 1, message = "分类级别最低为1")
    @Max(value = 3, message = "分类级别最高为3")
    private Byte categoryLevel;

    @ApiModelProperty("父类id")
    @NotNull(message = "parentId不能为空")
    @Min(value = 0, message = "parentId最低为0")
    private Long parentId;

    @ApiModelProperty("分类名称")
    @NotEmpty(message = "categoryName不能为空")
    @Length(max = 16,message = "分类名称过长")
    private String categoryName;

    @ApiModelProperty("排序值")
    @Min(value = 1, message = "categoryRank最低为1")
    @Max(value = 200, message = "categoryRank最高为200")
    @NotNull(message = "categoryRank不能为空")
    private Integer categoryRank;
}
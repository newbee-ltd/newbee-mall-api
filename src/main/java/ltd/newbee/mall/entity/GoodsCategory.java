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
 * 该类为商品分类项实体类
 *
 * @author 13
 */
@Data
public class GoodsCategory {
    //分类id
    private Long categoryId;

    //分类级别
    private Byte categoryLevel;

    //父分类id
    private Long parentId;

    //分类名称
    private String categoryName;

    //排序值
    private Integer categoryRank;

    //删除标记，0未删除，1删除
    private Byte isDeleted;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //创建人id
    private Integer createUser;

    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    //修改人id
    private Integer updateUser;
}
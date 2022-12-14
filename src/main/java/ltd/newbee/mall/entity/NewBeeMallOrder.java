/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 订单实体类 这个订单是买东西下单之后生成的那个东西 需要和其他实体类做好区分
 * 这时候他倒记起来自己有lombok了
 */
@Data
@TableName("tb_newbee_mall_order")
public class NewBeeMallOrder{

    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    private String orderNo;

    private Long userId;

    private Integer totalPrice;

    private Byte payStatus;

    private Byte payType;

    private Date payTime;

    private Byte orderStatus;

    private String extraInfo;

    @TableLogic(value = "0", delval = "1")
    private Byte isDeleted;

    /*
     * 关于注解@JsonFormat：<br>
     * 这玩意自己写数据库也能用。这玩意在fasterxml.jackson包里面，应该自带了。用于格式化日期，比如把日期格式化成yyyy-MM-dd HH:mm:ss这种格式。<br>
     * 使用这个注解可以使MySQL返回的日期与Java实体类中的日期格式一致，否则会出现日期格式不一致的情况。<br>
     * <a href="https://blog.csdn.net/asd26655/article/details/125252564">可以看看这个</a>
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}

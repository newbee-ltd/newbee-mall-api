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
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 购物车项包含一种多个商品
 */
@Data
@TableName("tb_newbee_mall_shopping_cart_item")
public class NewBeeMallShoppingCartItem {
    /**
     * 购物车项id
     */
    @TableId(value = "cart_item_id", type = IdType.AUTO)
    private Long cartItemId;

    /**
     * 该购物车项所属用户的id
     */
    private Long userId;

    /**
     * 购物车项所含有的商品id
     */
    private Long goodsId;

    /**
     * 该购物车项含有商品的数量
     */
    private Integer goodsCount;

    /**
     * 标记购物车项是否删除
     */
    private Byte isDeleted;

    /**
     * 购物车项创建时间
     */
    private Date createTime;

    /**
     * 购物车项更新时间
     */
    private Date updateTime;
}

/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_newbee_mall_admin_user")
public class AdminUser {
    /**
     * 管理员用户Id
     * */
    @TableId(value = "admin_user_id", type = IdType.AUTO)
    private Long adminUserId;
    /**
     * 不明所以的名字，不知道和nickname的区别在哪里
     */
    private String loginUserName;
    /**
     * 管理员用户登录密码
     * */
    private String loginPassword;
    /**
     * 管理员用户昵称
     * */
    private String nickName;

    private Byte locked;
}

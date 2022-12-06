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

@Data
public class MallUser {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * Md5加密的密码
     */
    private String passwordMd5;
    /**
     * 用户个性签名
     */
    private String introduceSign;
    /**
     * 是否被删除的标记位
     */
    private Byte isDeleted;
    /**
     *
     */
    private Byte lockedFlag;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service;

import ltd.newbee.mall.api.mall.param.MallUserUpdateParam;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

public interface NewBeeMallUserService {

    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);


    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @return
     */
    String login(String loginName, String passwordMD5);

    /**
     * 用户信息修改
     *
     * @param mallUser
     * @return
     */
    Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId);

    /**
     * 登出接口
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Long[] ids, int lockStatus);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil);
}

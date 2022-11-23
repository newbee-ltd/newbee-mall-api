/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {
    /**
     * 未发现service使用，管理员早已在数据库中添加完成
     * 提供完整的5个属性添加用户
     * @param record
     * @return
     */
    int insert(AdminUser record);

    /**
     * 提供修改的属性插添加用户
     * 截止Push前未发现用法，存在缺陷，可以只输入1个属性进行用户添加，但目前该用法仅存在于dao层，service层未调用该方法
     * 怀疑是测试炫技的方法，没有任何实际价值，估计只是为了介绍xml中prefix等等语法
     * @param record
     * @return
     */
    int insertSelective(AdminUser record);

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    /**
     * 输入id返回用户
     * @param adminUserId
     * @return
     */
    AdminUser selectByPrimaryKey(Long adminUserId);

    /**
     * 提供id，并改动想改动的
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AdminUser record);

    /**
     * 提供id，改动所有用户属性
     * @param record
     * @return
     */
    int updateByPrimaryKey(AdminUser record);
}
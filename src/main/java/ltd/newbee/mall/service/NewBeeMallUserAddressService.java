/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service;

import ltd.newbee.mall.api.vo.NewBeeMallUserAddressVO;
import ltd.newbee.mall.entity.MallUserAddress;

import java.util.List;

public interface NewBeeMallUserAddressService {

    /**
     * 获取我的收货地址
     *
     * @param userId
     * @return
     */
    List<NewBeeMallUserAddressVO> getMyAddresses(Long userId);

    /**
     * 保存收货地址
     *
     * @param mallUserAddress
     * @return
     */
    Boolean saveUserAddress(MallUserAddress mallUserAddress);

    /**
     * 修改收货地址
     *
     * @param mallUserAddress
     * @return
     */
    Boolean updateMallUserAddress(MallUserAddress mallUserAddress);

    /**
     * 获取收货地址详情
     *
     * @param addressId
     * @return
     */
    MallUserAddress getMallUserAddressById(Long addressId);

    /**
     * 获取我的默认收货地址
     *
     * @param userId
     * @return
     */
    MallUserAddress getMyDefaultAddressByUserId(Long userId);

    /**
     * 删除收货地址
     *
     * @param addressId
     * @return
     */
    Boolean deleteById(Long addressId);
}

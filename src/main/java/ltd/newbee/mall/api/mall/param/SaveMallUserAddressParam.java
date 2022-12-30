/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.mall.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 添加收货地址param
 */
@Data
public class SaveMallUserAddressParam {

    @Schema(title = "收件人名称")
    private String userName;

    @Schema(title = "收件人联系方式")
    private String userPhone;

    @Schema(title = "是否默认地址 0-不是 1-是")
    private Byte defaultFlag;

    @Schema(title = "省")
    private String provinceName;

    @Schema(title = "市")
    private String cityName;

    @Schema(title = "区/县")
    private String regionName;

    @Schema(title = "详细地址")
    private String detailAddress;
}

package ltd.newbee.mall.api.admin.param;

import lombok.Data;

@Data
public class UpdateAdminPasswordParam {

    private String originalPassword;

    private String newPassword;
}

package ltd.newbee.mall.api.admin.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class DeleteAdminUserParam {

    @NotNull(message = "管理员Id不能为空")
    private Long adminUserId;
}

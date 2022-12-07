package ltd.newbee.mall.api.admin.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class DeleteUserParam {

    @NotNull(message = "用户Id不能为空")
    private Long userId;
}

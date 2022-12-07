package ltd.newbee.mall.api.admin.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class DeleteUserParam {

    @NotNull
    private Long userId;
}

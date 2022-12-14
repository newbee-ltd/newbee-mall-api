package ltd.newbee.mall.util;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LuceneUtil {

    /**
     * 默认的索引位置
     * 不可为空，可以自己调用set方法更改
     */
    @NotBlank
    @ApiModelProperty("索引保存位置")
    private String lucenePath = "E:\\Lucene\\indexDir";
}

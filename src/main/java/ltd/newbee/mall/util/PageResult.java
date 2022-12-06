package ltd.newbee.mall.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * 这玩意是一个手写的分页工具类 这就是他不上mybatisPlus的坏处了 本来人家有插件的结果搁这儿重新造轮子
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@Data // 他既然上了lombok然后后面再写一坨get set方法是在想什么 很有祖传的味道
public class PageResult<T> implements Serializable {

    @ApiModelProperty("总记录数")
    private int totalCount;

    @ApiModelProperty("每页记录数")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int totalPage;

    @ApiModelProperty("当前页数")
    private int currPage;

    @ApiModelProperty("列表数据")
    private List<T> list;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public PageResult(List<T> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }
}

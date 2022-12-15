package ltd.newbee.mall.service;


import ltd.newbee.mall.entity.NewBeeMallGoods;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    /**
     * 具体查询
     * @param keyword
     */
    List<NewBeeMallGoods> search(String keyword) throws IOException, ParseException;
}

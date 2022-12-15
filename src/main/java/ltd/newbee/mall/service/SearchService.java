package ltd.newbee.mall.service;


import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface SearchService {

    /**
     * 具体查询
     * @param keyword
     */
    void searchDocumentByIndex(String keyword);

    public PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    public List<NewBeeMallGoods> getNewBeeMallGoodsByPage(PageQueryUtil pageUtil);

    public void createIndex() throws ClassNotFoundException, SQLException, IOException;

    public  List<NewBeeMallGoods> search(String keyword) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException;
}

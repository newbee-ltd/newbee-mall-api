package ltd.newbee.mall.service.impl;

import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.common.MyIKAnalyzer;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.SearchService;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private Environment config;

    /**
     * 搜索实现
     * @param keyword 关键字
     * @return 搜索结果
     * @throws IOException IO异常
     * @throws ParseException 解析异常
     */
    public List<NewBeeMallGoods> search(@Valid String keyword) throws IOException, ParseException {
        Directory directory1=FSDirectory.open(FileSystems.getDefault().getPath(
                Objects.requireNonNull(config.getProperty("lucene.index.path"))
        ));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory1);
        // 索引搜索工具
        IndexSearcher searcher=new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器

        String[] fields = {"goods_name", "goods_intro"};
        MultiFieldQueryParser parser= new MultiFieldQueryParser(fields,new MyIKAnalyzer());
        // 创建查询对象
        Query query=parser.parse(keyword);

        // 获取前n条记录
        TopDocs topDocs=searcher.search(query,500);

        // 获取总条数
        log.info("本次搜索共找到"+topDocs.totalHits+"条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[]scoreDocs=topDocs.scoreDocs;

        List<NewBeeMallGoods>list = new ArrayList<>();
        for(ScoreDoc scoreDoc:scoreDocs){
            // 取出文档编号
            int docID=scoreDoc.doc;
            // 根据编号去找文档
            Document doc=reader.document(docID);
            //ltd.newbee.mall.api.random.Content content = contentMapper.selectByPrimaryKey(doc.get("id"));
            NewBeeMallGoods good=new NewBeeMallGoods();

            good.setGoodsId(Long.parseLong(doc.get("goods_id")));
            good.setGoodsName(doc.get("goods_name"));
            good.setGoodsIntro(doc.get("goods_intro"));
            good.setGoodsCoverImg(doc.get("goods_cover_img"));
            good.setSellingPrice(Integer.valueOf(doc.get("selling_price")));


            list.add(good);
        }
        return list;
    }



}


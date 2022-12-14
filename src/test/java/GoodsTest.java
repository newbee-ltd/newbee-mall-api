import ltd.newbee.mall.common.MyIKAnalyzer;
import ltd.newbee.mall.dao.GoodsDao;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class GoodsTest {
    @Test
    public void getAll(){
        GoodsDao dao = new GoodsDao();
        List<NewBeeMallGoods> goods = dao.getAll();
        for (NewBeeMallGoods good : goods) {
            System.out.println("商品id:" + good.getGoodsId() + ",商品名称:" + good.getGoodsName());
        }
    }

    @Test
    public void createIndex(){
        try {
            GoodsDao dao = new GoodsDao();
            // 创建文档的集合
//            Collection<Document> docs = new ArrayList<>();
//            List<NewBeeMallGoods> list = dao.getAll();
//            for (NewBeeMallGoods newBeeMallGoods : list) {
//                //contentMapper.insertSelective(list1.get(i));
//                // 创建文档对象
//                Document document = new Document();
//                //StringField会创建索引，但是不会被分词，TextField，即创建索引又会被分词。
//                document.add(new TextField("goods_id", newBeeMallGoods.getGoodsId().toString(), Field.Store.YES));
//                document.add(new TextField("goods_name", newBeeMallGoods.getGoodsName(), Field.Store.YES));
//                document.add(new TextField("goods_intro", newBeeMallGoods.getGoodsIntro(), Field.Store.YES));
//                document.add(new TextField("goods_cover_img", newBeeMallGoods.getGoodsCoverImg(), Field.Store.YES));
//                document.add(new TextField("selling_price", newBeeMallGoods.getSellingPrice().toString(), Field.Store.YES));
//                docs.add(document);
//            }
            // 创建索引
            // 索引目录类,指定索引在硬盘中的位置，我的设置为D盘的indexDir文件夹
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
            // 引入IK分词器
            Analyzer analyzer = new MyIKAnalyzer();
            // 索引写出工具的配置对象，这个地方就是最上面报错的问题解决方案
            IndexWriterConfig conf = new IndexWriterConfig(analyzer);
            // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
            conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            // 创建索引的写出工具类。参数：索引的目录和配置信息
            IndexWriter indexWriter = new IndexWriter(directory, conf);
            // 把文档集合交给IndexWriter
            indexWriter.addDocuments(dao.getDocuments(dao.getAll()));
            // 提交
            indexWriter.commit();
            // 关闭
            indexWriter.close();
            System.out.println("创建索引库成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


import ltd.newbee.mall.common.MyIKAnalyzer;
import ltd.newbee.mall.dao.GoodsDao;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
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

    @Test
    public void searchDocumentByIndex(){
        try {
// 2、 执行搜索
// a) 指定索引库目录
            //创建搜索属性区间
            String[] str = {"goods_name", "goods_intro"};

            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
            // 索引读取工具
            IndexReader reader = DirectoryReader.open(directory);
            // 索引搜索工具
            IndexSearcher searcher = new IndexSearcher(reader);
            // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
            // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
            MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());

// d) 通过IndexSearcher对象执行查询索引库，返回TopDocs对象
// 第一个参数：查询对象
            Query query = parser.parse("ml");
// 第二个参数：最大的n条记录
// 获取前n条记录
            TopDocs topDocs = searcher.search(query, 10);
            // 获取总条数
            System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
            // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 取出文档编号
                int docID = scoreDoc.doc;
                // 根据编号去找文档
                Document doc = reader.document(docID);
// f) 输出文档内容
                System.out.println("===============================");
                System.out.println("文档id:" + docID);
                System.out.println("商品id:" + doc.get("goods_id"));
                System.out.println("商品name:" + doc.get("goods_name"));
                System.out.println("商品简介:" + doc.get("goods_intro"));
                System.out.println("商品图片:" + doc.get("goods_cover_img"));
                System.out.println("商品价格:" + doc.get("goods_sell_status"));
            }
// g) 关闭IndexReader
            reader.close();
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


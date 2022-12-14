import ltd.newbee.mall.dao.GoodsDao;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
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

// 分析文档，对文档中的field域进行分词

            Analyzer analyzer = new StandardAnalyzer();

// 创建索引

// 1) 创建索引库目录

            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
            // 索引读取工具

// 2) 创建IndexWriterConfig对象

            IndexWriterConfig cfg = new IndexWriterConfig(Version.LATEST, analyzer);

// 3) 创建IndexWriter对象

            IndexWriter writer = new IndexWriter(directory, cfg);

// 4) 通过IndexWriter对象添加文档对象(document)

            writer.addDocuments(dao.getDocuments(dao.getAll()));

// 5) 关闭IndexWriter

            writer.close();

            System.out.println("创建索引库成功");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}


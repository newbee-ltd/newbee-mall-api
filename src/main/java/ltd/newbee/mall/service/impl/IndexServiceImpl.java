package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.MyIKAnalyzer;
import ltd.newbee.mall.dao.GoodsDao;
import ltd.newbee.mall.service.IndexService;
import ltd.newbee.mall.util.LuceneUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;

@Service
public class IndexServiceImpl implements IndexService {
    /**
     * 创建索引
     */
    @Override
    public void createIndex() {
        try {
            GoodsDao dao = new GoodsDao();
            // 创建索引
            // 索引目录类,指定索引在硬盘中的位置，我的设置为E盘的indexDir文件夹
            LuceneUtil luceneUtil = new LuceneUtil();
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(luceneUtil.getLucenePath()));
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


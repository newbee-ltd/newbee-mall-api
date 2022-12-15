package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.MyIKAnalyzer;
import ltd.newbee.mall.dao.GoodsCategoryMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.SearchService;
import ltd.newbee.mall.util.LuceneUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;

    /**
     * 索引库建立
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public void createIndex() throws ClassNotFoundException, SQLException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<NewBeeMallGoods> list = new ArrayList<>();
        Class.forName("com.mysql.jdbc.Driver");

// 连接数据库

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newbee_mall_db_v2", "root", "123456");

// SQL语句

        String sql = "SELECT * FROM tb_newbee_mall_goods_info";

// 创建preparedStatement

        preparedStatement = connection.prepareStatement(sql);
        // 获取结果集

        resultSet = preparedStatement.executeQuery();

// 结果集解析

        while (resultSet.next()) {

            NewBeeMallGoods goods = new NewBeeMallGoods();

            goods.setGoodsId(resultSet.getLong("goods_id"));
            goods.setGoodsName(resultSet.getString("goods_name"));
            goods.setGoodsIntro(resultSet.getString("goods_intro"));
            goods.setGoodsCoverImg(resultSet.getString("goods_cover_img"));
            goods.setSellingPrice(resultSet.getInt("selling_price"));
            goods.setGoodsSellStatus(resultSet.getByte("goods_sell_status"));



            list.add(goods);

        }

        Collection<Document> docList = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            //contentMapper.insertSelective(list1.get(i));
            // 创建文档对象
            Document doc = new Document();
            //StringField会创建索引，但是不会被分词，TextField，即创建索引又会被分词。
            TextField goodsName = new TextField("goods_name", list.get(i).getGoodsName(), Field.Store.YES);
            TextField goodsIntro = new TextField("goods_intro", list.get(i).getGoodsIntro(), Field.Store.YES);
            TextField goodsCoverImg = new TextField("goods_cover_img", list.get(i).getGoodsCoverImg(), Field.Store.YES);
            TextField sellingPrice = new TextField("selling_price", list.get(i).getSellingPrice().toString(), Field.Store.YES);
            TextField goodsSellStatus = new TextField("goods_sell_status", list.get(i).getGoodsSellStatus().toString(), Field.Store.YES);

// 把域(Field)添加到文档(Document)中

            doc.add(new StringField("goods_id", list.get(i).getGoodsId().toString(), Field.Store.YES));
            doc.add(goodsName);
            doc.add(goodsIntro);
            doc.add(goodsCoverImg);
            doc.add(sellingPrice);
            doc.add(goodsSellStatus);


            docList.add(doc);
        }

        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\ind"));
        // 引入IK分词器
        Analyzer analyzer = new MyIKAnalyzer();
        // 索引写出工具的配置对象，这个地方就是最上面报错的问题解决方案
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        // 把文档集合交给IndexWriter
        indexWriter.addDocuments(docList);
        // 提交
        indexWriter.commit();
        // 关闭
        indexWriter.close();
        System.out.println("createIndex success");
    }

    /**
     * 搜索实现
     * @param keyword
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws org.apache.lucene.queryparser.classic.ParseException
     */
    public List<NewBeeMallGoods> search(String keyword) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
        Directory directory1=FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\ind"));
        // 索引读取工具
        IndexReader reader= DirectoryReader.open(directory1);
        // 索引搜索工具
        IndexSearcher searcher=new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器

        String[] fields = {"goods_name", "goods_intro"};
        MultiFieldQueryParser parser= new MultiFieldQueryParser(fields,new MyIKAnalyzer());
        // 创建查询对象
        Query query=parser.parse(keyword);

        // 获取前n条记录
        TopDocs topDocs=searcher.search(query,50);

        // 获取总条数
        System.out.println("本次搜索共找到"+topDocs.totalHits+"条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[]scoreDocs=topDocs.scoreDocs;

        List<NewBeeMallGoods>list1=new ArrayList<>();
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
            good.setGoodsSellStatus(Byte.parseByte(doc.get("goods_sell_status")));





            list1.add(good);
        }
        return list1;
    }



}


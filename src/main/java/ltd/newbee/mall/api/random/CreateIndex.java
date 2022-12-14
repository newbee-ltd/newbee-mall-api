package ltd.newbee.mall.api.random;

import ltd.newbee.mall.common.MyIKAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

@RestController
public class CreateIndex {
    @RequestMapping("/createIndex")
    public String createIndex() throws IOException {
        List<Content> list1 = new ArrayList<>();
        list1.add(new Content(null, "Java面向对象", "10", null, "Java面向对象从入门到精通,简单上手"));
        list1.add(new Content(null, "Java面向对象java", "10", null, "Java面向对象从入门到精通,简单上手"));
        list1.add(new Content(null, "Java面向编程", "15", null, "Java面向对象编程书籍"));
        list1.add(new Content(null, "JavaScript入门", "18", null, "JavaScript入门编程书籍"));
        list1.add(new Content(null, "深入理解Java编程", "13", null, "十三四天掌握Java基础"));
        list1.add(new Content(null, "从入门到放弃_Java", "20", null, "一门从入门到放弃的书籍"));
        list1.add(new Content(null, "Head First Java", "30", null, "《Head First Java》是一本完整地面向对象(object-oriented，OO)程序设计和Java的学习指导用书"));
        list1.add(new Content(null, "Java 核心技术：卷1 基础知识", "22", null, "全书共14章，包括Java基本的程序结构、对象与类、继承、接口与内部类、图形程序设计、事件处理、Swing用户界面组件"));
        list1.add(new Content(null, "Java 编程思想", "12", null, "本书赢得了全球程序员的广泛赞誉，即使是最晦涩的概念，在Bruce Eckel的文字亲和力和小而直接的编程示例面前也会化解于无形"));
        list1.add(new Content(null, "Java开发实战经典", "51", null, "本书是一本综合讲解Java核心技术的书籍，在书中使用大量的代码及案例进行知识点的分析与运用"));
        list1.add(new Content(null, "Effective Java", "10", null, "本书介绍了在Java编程中57条极具实用价值的经验规则，这些经验规则涵盖了大多数开发人员每天所面临的问题的解决方案"));
        list1.add(new Content(null, "分布式 Java 应用：基础与实践", "14", null, "本书介绍了编写分布式Java应用涉及的众多知识点，分为了基于Java实现网络通信、RPC;基于SOA实现大型分布式Java应用"));
        list1.add(new Content(null, "http权威指南", "11", null, "超文本传输协议(Hypertext Transfer Protocol，HTTP)是在万维网上进行通信时所使用的协议方案"));
        list1.add(new Content(null, "Spring", "15", null, "这是啥，还需要学习吗？Java程序员必备书籍"));
        list1.add(new Content(null, "深入理解 Java 虚拟机", "18", null, "作为一位Java程序员，你是否也曾经想深入理解Java虚拟机，但是却被它的复杂和深奥拒之门外"));
        list1.add(new Content(null, "springboot实战", "11", null, "完成对于springboot的理解，是每个Java程序员必备的姿势"));
        list1.add(new Content(null, "springmvc学习", "72", null, "springmvc学习指南"));
        list1.add(new Content(null, "vue入门到放弃", "20", null, "vue入门到放弃书籍信息"));
        list1.add(new Content(null, "vue入门到精通", "20", null, "vue入门到精通相关书籍信息"));
        list1.add(new Content(null, "vue之旅", "20", null, "由浅入深地全面介绍vue技术，包含大量案例与代码"));
        list1.add(new Content(null, "vue实战", "20", null, "以实战为导向，系统讲解如何使用 "));
        list1.add(new Content(null, "vue入门与实践", "20", null, "现已得到苹果、微软、谷歌等主流厂商全面支持"));
        list1.add(new Content(null, "Vue.js应用测试", "20", null, "Vue.js创始人尤雨溪鼎力推荐！Vue官方测试工具作者亲笔撰写，Vue.js应用测试完全学习指南"));
        list1.add(new Content(null, "PHP和MySQL Web开发", "20", null, "本书是利用PHP和MySQL构建数据库驱动的Web应用程序的权威指南"));
        list1.add(new Content(null, "Web高效编程与优化实践", "20", null, "从思想提升和内容修炼两个维度，围绕前端工程师必备的前端技术和编程基础"));
        list1.add(new Content(null, "Vue.js 2.x实践指南", "20", null, "本书旨在让初学者能够快速上手vue技术栈，并能够利用所学知识独立动手进行项目开发"));
        list1.add(new Content(null, "初始vue", "20", null, "解开vue的面纱"));
        list1.add(new Content(null, "什么是vue", "20", null, "一步一步的了解vue相关信息"));
        list1.add(new Content(null, "深入浅出vue", "20", null, "深入浅出vue，慢慢掌握"));
        list1.add(new Content(null, "三天vue实战", "20", null, "三天掌握vue开发"));
        list1.add(new Content(null, "不知火舞", "20", null, "不知名的vue"));
        list1.add(new Content(null, "娜可露露", "20", null, "一招秒人"));
        list1.add(new Content(null, "宫本武藏", "20", null, "我就是一个超级兵"));
        list1.add(new Content(null, "只有title有马", "20", null, "我就是一个超级兵"));
        list1.add(new Content(null, "title和des都有马", "20", null, "title和des都有马"));
        list1.add(new Content(null, "宫本", "20", null, "title无马des有马"));

        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            //contentMapper.insertSelective(list1.get(i));
            // 创建文档对象
            Document document = new Document();
            //StringField会创建索引，但是不会被分词，TextField，即创建索引又会被分词。
            document.add(new StringField("id", (i + 1) + "", Field.Store.YES));
            document.add(new TextField("title", list1.get(i).getTitle(), Field.Store.YES));
            document.add(new TextField("price", list1.get(i).getPrice(), Field.Store.YES));
            document.add(new TextField("descs", list1.get(i).getDescs(), Field.Store.YES));
            docs.add(document);
        }

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
        indexWriter.addDocuments(docs);
        // 提交
        indexWriter.commit();
        // 关闭
        indexWriter.close();
        return "success";
    }

    @RequestMapping("/updateIndex")
    public String update(String age) throws IOException {
        // 创建目录对象
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
        // 创建配置对象
        IndexWriterConfig conf = new IndexWriterConfig(new MyIKAnalyzer());
        // 创建索引写出工具
        IndexWriter writer = new IndexWriter(directory, conf);
        // 创建新的文档数据
        Document doc = new Document();
        doc.add(new StringField("id", "34", Field.Store.YES));
        //ltd.newbee.mall.api.random.Content content = contentMapper.selectByPrimaryKey("34");
        //content.setTitle("宫本武藏超级兵");
        //contentMapper.updateByPrimaryKeySelective(content);
        Content content = new Content(34, "宫本武藏超级兵", "", "", "");
        doc.add(new TextField("title", content.getTitle(), Field.Store.YES));
        doc.add(new TextField("price", content.getPrice(), Field.Store.YES));
        doc.add(new TextField("descs", content.getDescs(), Field.Store.YES));
        writer.updateDocument(new Term("id", "34"), doc);
        // 提交
        writer.commit();
        // 关闭
        writer.close();
        return "success";
    }

    @RequestMapping("/deleteIndex")
    public String deleteIndex() throws IOException {
        // 创建目录对象
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
        // 创建配置对象
        IndexWriterConfig conf = new IndexWriterConfig(new IKAnalyzer());
        // 创建索引写出工具
        IndexWriter writer = new IndexWriter(directory, conf);
        // 根据词条进行删除
        writer.deleteDocuments(new Term("id", "34"));
        // 提交
        writer.commit();
        // 关闭
        writer.close();
        return "success";
    }

    /**
     * des有关键词就返回
     * @param text
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping("/searchText")
    public Object searchText(String text, HttpServletRequest request) throws IOException, ParseException {
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser parser = new QueryParser("descs", new MyIKAnalyzer());
        // 创建查询对象

        Query query = parser.parse(text);
        // 获取前n条记录
        TopDocs topDocs = searcher.search(query, 30);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Content> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docID);
            //ltd.newbee.mall.api.random.Content content = contentMapper.selectByPrimaryKey(doc.get("id"));
            Content content = new Content();
            content.setId(Integer.valueOf(doc.get("id")));
            content.setTitle(doc.get("title"));
            content.setDescs(doc.get("descs"));
            list.add(content);
        }
        return list;
    }

    /**
     * title里面有关键词就能返回
     * @param text
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping("/searchText1")
    public Object searchText1(String text, HttpServletRequest request) throws IOException, ParseException {
        String[] str = {"title", "descs"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 100);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Content> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docID);
            //ltd.newbee.mall.api.random.Content content = contentMapper.selectByPrimaryKey(doc.get("id"));
            Content content = new Content();
            content.setId(Integer.valueOf(doc.get("id")));
            content.setTitle(doc.get("title"));
            content.setDescs(doc.get("descs"));
            list.add(content);
        }
        return list;
    }

    @RequestMapping("/searchText2")
    public Object searchText2(String text, HttpServletRequest request) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "descs"};
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        TopDocs topDocs = searcher.search(query, 100);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");

        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮后的段落范围在100字内
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Content> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docID);
            //ltd.newbee.mall.api.random.Content content = contentMapper.selectByPrimaryKey(doc.get("id"));
            Content content = new Content();
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new MyIKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String descs = highlighter.getBestFragment(new MyIKAnalyzer(), "descs", doc.get("descs"));
            if (descs == null) {
                descs = content.getDescs();
            }
            content.setDescs(descs);
            content.setTitle(title);
            list.add(content);
        }
        request.setAttribute("list", list);
        return "index";
    }

    /**
     * 开起来是要分页查询
     * 需要服务器
     * TODO后期再改
     * @param text
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     * @throws InvalidTokenOffsetsException
     */
    @RequestMapping("/searchText3")
    public String searchText3(String text, HttpServletRequest request) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "descs"};
        int page = 1;
        int pageSize = 10;
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("E:\\Lucene\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);
        // 获取前十条记录
        //TopDocs topDocs = searcher.search(query, 100);

        TopDocs topDocs = searchByPage(page, pageSize, searcher, query);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");

        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮后的段落范围在100字内
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Content> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docID);
            //ltd.newbee.mall.api.random.Content content = contentMapper.selectByPrimaryKey(doc.get("id"));
            Content content = new Content();
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new MyIKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String descs = highlighter.getBestFragment(new MyIKAnalyzer(), "descs", doc.get("descs"));
            if (descs == null) {
                descs = content.getDescs();
            }
            content.setDescs(descs);
            content.setTitle(title);
            list.add(content);
        }
        System.err.println("list的长度：" + list.size());
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("list", list);
        return "index";
    }

    private TopDocs searchByPage(int page, int perPage, IndexSearcher searcher, Query query) throws IOException {
        TopDocs result = null;
        if (query == null) {
            System.out.println(" Query is null return null ");
            return null;
        }
        ScoreDoc before = null;
        if (page != 1) {
            TopDocs docsBefore = searcher.search(query, (page - 1) * perPage);
            ScoreDoc[] scoreDocs = docsBefore.scoreDocs;
            if (scoreDocs.length > 0) {
                before = scoreDocs[scoreDocs.length - 1];
            }
        }
        result = searcher.searchAfter(before, query, perPage);
        return result;
    }

    @RequestMapping("/searchText4")
    public String searchText4(String text, HttpServletRequest request) throws IOException, ParseException, InvalidTokenOffsetsException {
        String[] str = {"title", "descs"};
        int page = 1;
        int pageSize = 100;
        IndexSearcher searcher = getMoreSearch("E:\\Lucene\\indexDir");
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(str, new MyIKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(text);

        TopDocs topDocs = searchByPage(page, pageSize, searcher, query);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");

        //高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮后的段落范围在100字内
        highlighter.setTextFragmenter(fragmenter);

        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Content> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            //Document doc = reader.document(docID);
            Document doc = searcher.doc(docID);//多索引找文档要用searcher找了，reader容易报错
            //ltd.newbee.mall.api.random.Content content = contentMapper.selectByPrimaryKey(doc.get("id"));
            Content content = new Content();
            //处理高亮字段显示
            String title = highlighter.getBestFragment(new MyIKAnalyzer(), "title", doc.get("title"));
            if (title == null) {
                title = content.getTitle();
            }
            String descs = highlighter.getBestFragment(new MyIKAnalyzer(), "descs", doc.get("descs"));
            if (descs == null) {
                descs = content.getDescs();
            }
            content.setDescs(descs);
            content.setTitle(title);
            list.add(content);
        }
        System.err.println("list的长度：" + list.size());
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("list", list);
        return "index";
    }

    private IndexSearcher getMoreSearch(String string) {
        MultiReader reader = null;
        //设置
        try {
            File[] files = new File(string).listFiles();

            IndexReader[] readers = new IndexReader[files.length];
            for (int i = 0; i < files.length; i++) {
                readers[i] = DirectoryReader.open(FSDirectory.open(Paths.get(files[i].getPath(), new String[0])));
            }
            reader = new MultiReader(readers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IndexSearcher(reader);
        //如果索引文件过多，可以这样加快效率
        /**
         ExecutorService service = Executors.newCachedThreadPool();
         return new IndexSearcher(reader,service);
         */
    }

}

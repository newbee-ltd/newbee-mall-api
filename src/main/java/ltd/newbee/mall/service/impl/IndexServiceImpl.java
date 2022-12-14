//package ltd.newbee.mall.service.impl;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Set;
//
//@Service
//public class IndexServiceImpl {
//
//
//        /**
//         * 创建索引
//         *
//         * @param indexName
//         */
//        public void createIndex(String indexName, String jsonDoc) {
//            IndexWriter writer = null;
//            try {
//                //String indexDir = "D:\\Lucene\\indexDir\\" + indexName;
//                String indexDir = "E:\\Lucene\\indexDir";
//                //准备目录
//                Directory directory = FSDirectory.open(Paths.get((indexDir)));
//                //准备分词器
//                Analyzer analyzer = new StandardAnalyzer();
//                //准备config
//                IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
//                //创建索引的实例
//                writer = new IndexWriter(directory, iwConfig);
//                //添加索引文档
//                //Document doc = json2Doc(jsonDoc);
//                Document doc = new Document();
//                doc.add(new TextField("content", jsonDoc, Field.Store.YES));
//                writer.addDocument(doc);
//                System.out.println("indexed doc success...");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (writer != null) {
//                    try {
//                        writer.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }
//
//        public void deleteIndex() {
//            throw new UnsupportedOperationException();
//        }
//    }
//

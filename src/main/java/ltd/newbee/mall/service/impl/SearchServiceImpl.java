//package ltd.newbee.mall.service.impl;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexableField;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.List;
//
//@Service
//public class SearchServiceImpl {
//
//
//        /**
//         * 查询文档
//         *
//         * @param indexName
//         * @param queryStr
//         * @return
//         */
//        public String query(String indexName, String queryStr) {
//            String result = "";
//            IndexReader reader = null;
//            try {
//                //String indexDir = "D:\\Lucene\\indexDir\\" + indexName;
//                String indexDir = "D:\\Lucene\\indexDir";
//                //准备目录
//                Directory directory = FSDirectory.open(Paths.get((indexDir)));
//                //拿到reader
//                reader = DirectoryReader.open(directory);
//                //创建indexSearcher实例
//                IndexSearcher searcher = new IndexSearcher(reader);
//                //准备分词器
//                Analyzer analyzer = new StandardAnalyzer();
//
//                //创建解析器
//                QueryParser parser = new QueryParser("songName", analyzer);
//
//                Query query = parser.parse(queryStr);
//                TopDocs hits = searcher.search(query, 10);
//                System.out.println("hits.size:" + hits.totalHits);
//
//                for (ScoreDoc scoreDoc : hits.scoreDocs) {
//                    //拿到文档实例
//                    Document doc = searcher.doc(scoreDoc.doc);
//                    //拿到所有文档字段
//                    List<IndexableField> fields = doc.getFields();
//
//                    //处理文档字段
//                    for (IndexableField f : fields) {
//                        result += f.name() + ":" + f.stringValue() + ",\r\n";
//                    }
//                    System.out.println(result);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } finally {
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return result;
//        }
//
//    }
//

package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallGoods;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 这个里面有我的数据库私钥，推的时候注意一下
 */
public class GoodsDao {
    /**
     * 采集数据
     * @return
     */
    public List<NewBeeMallGoods> getAll() {
// 数据库链接
        Connection connection = null;
// 预编译statement
        PreparedStatement preparedStatement = null;
// 结果集
        ResultSet resultSet = null;
// 图书列表
        List list = new ArrayList();
        try {
// 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
// 连接数据库
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/newbee-mall-datasource", "root", "45403888");
// SQL语句
            String sql = "SELECT goods_id, goods_name, goods_intro, goods_cover_img, selling_price FROM tb_newbee_mall_goods_info";
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
                list.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=resultSet){
                try {
                    resultSet.close();
                } catch (SQLException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(null!=preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(null!=connection){
                try {
                    connection.close();
                } catch (SQLException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    /**
     * 将数据转化为Lucene文档
     * @param goods
     * @return
     */
    public List getDocuments(List<NewBeeMallGoods> goods){
// Document对象集合
        List docList = new ArrayList();
// Document对象
        Document doc = null;
        for (NewBeeMallGoods good : goods) {
// 创建Document对象，同时要创建field对象
            doc = new Document();
// 根据需求创建不同的Field
            Field id = new TextField("goods_id", good.getGoodsId().toString(), Field.Store.YES);
            Field name = new TextField("goods_name", good.getGoodsName(), Field.Store.YES);
            Field intro = new TextField("goods_intro", good.getGoodsIntro(), Field.Store.YES);
            Field img = new TextField("goods_cover_img", good.getGoodsCoverImg(), Field.Store.YES);
            Field price = new TextField("selling_price", good.getSellingPrice().toString(), Field.Store.YES);
// 把域(Field)添加到文档(Document)中
            doc.add(id);
            doc.add(name);
            doc.add(intro);
            doc.add(img);
            doc.add(price);
            docList.add(doc);
        }
        return docList;
    }
}
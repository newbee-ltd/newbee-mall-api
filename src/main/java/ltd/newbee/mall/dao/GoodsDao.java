package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallGoods;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDao {

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

}
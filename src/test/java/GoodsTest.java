import ltd.newbee.mall.dao.GoodsDao;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import org.junit.jupiter.api.Test;

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

}


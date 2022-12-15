package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.NewBeeMallGoods;

import java.util.List;

public interface LuceneGoodsService {

    List<NewBeeMallGoods> getAllGoods();

    List getDocuments(List<NewBeeMallGoods> goods);
}

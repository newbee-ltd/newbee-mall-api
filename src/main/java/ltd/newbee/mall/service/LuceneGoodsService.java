package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.NewBeeMallGoods;

import java.util.List;

public interface LuceneGoodsService {

    List<NewBeeMallGoods> getAll();

    List getDocuments(List<NewBeeMallGoods> goods);
}

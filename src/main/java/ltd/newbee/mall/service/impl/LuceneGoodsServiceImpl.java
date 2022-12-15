package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.LuceneGoodsService;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneGoodsServiceImpl implements LuceneGoodsService {

    @Autowired
    private NewBeeMallGoodsMapper mapper;

    /**
     * 采集数据
     * @return
     */
    @Override
    public List<NewBeeMallGoods> getAllGoods() {
        QueryWrapper<NewBeeMallGoods> wrapper = new QueryWrapper<>();
        // 列表
        return mapper.selectList(wrapper);
    }
    /**
     * 将数据转化为Lucene文档
     * @param goods
     * @return
     */
    @Override
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

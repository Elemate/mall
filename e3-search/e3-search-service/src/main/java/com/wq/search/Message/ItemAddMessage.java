package com.wq.search.Message;

import com.wq.common.pojo.SearchItem;
import com.wq.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemAddMessage implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;

            //防止消息发过来，商品添加的事务还未提交，所以等待一秒
            Thread.sleep(1000);

            Long itemId = Long.parseLong(textMessage.getText());

            //根据itemId查询出SearchItem，然后加入索引库
            SearchItem item = itemMapper.getItemById(itemId);

            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",item.getId());
            document.addField("item_image",item.getImage());
            document.addField("item_category_name",item.getCategory_name());
            document.addField("item_sell_point",item.getSell_point());
            document.addField("item_title",item.getTitle());
            document.addField("item_price",item.getPrice());

            solrServer.add(document);
            solrServer.commit();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

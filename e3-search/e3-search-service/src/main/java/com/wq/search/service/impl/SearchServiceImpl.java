package com.wq.search.service.impl;

import com.wq.common.pojo.SearchItem;
import com.wq.common.pojo.SearchResult;
import com.wq.common.utils.TaotaoResult;
import com.wq.search.dao.SearchDao;
import com.wq.search.mapper.ItemMapper;
import com.wq.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchDao searchDao;

    @Override
    public TaotaoResult importItemToSolr() {

        try {
            List<SearchItem> items = itemMapper.getItemList();
            List<SolrInputDocument> solrInputDocuments = new ArrayList<>();
            for (SearchItem item : items){
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id",item.getId());
                document.addField("item_image",item.getImage());
                document.addField("item_category_name",item.getCategory_name());
                document.addField("item_sell_point",item.getSell_point());
                document.addField("item_title",item.getTitle());
                document.addField("item_price",item.getPrice());
                solrInputDocuments.add(document);
            }

            solrServer.add(solrInputDocuments);
            solrServer.commit();
        } catch (Exception e){
            e.printStackTrace();
            return new TaotaoResult().build(500,"导入出错");
        }

        return TaotaoResult.ok();
    }

    @Override
    public SearchResult search (String keyword) throws Exception {
        SolrQuery query = new SolrQuery();
        int rows = 30;

        query.setQuery(keyword);
        query.set("df","item_title");
        query.setRows(30);

        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style='color:red'>");
        query.setHighlightSimplePost("</em>");

        SearchResult result = searchDao.search(query);
        long totalCount = result.getRecourdCount();
        int totalPages = (int) (totalCount % rows == 0 ? totalCount/rows : totalCount/rows + 1);

        result.setTotalPages(totalPages);
        return result;
    }
}

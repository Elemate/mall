package com.wq.search.dao;

import com.wq.common.pojo.SearchItem;
import com.wq.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery solrQuery) throws Exception{



        QueryResponse response = solrServer.query(solrQuery);

        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        List<SearchItem> searchItems = new ArrayList<>();
        SolrDocumentList documents = response.getResults();

        for (SolrDocument document : documents){

            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) document.get("id"));
            searchItem.setCategory_name((String) document.get("item_category_name"));
            searchItem.setImage((String) document.get("item_image"));
            searchItem.setPrice((Long) document.get("item_price"));
            searchItem.setSell_point((String) document.get("item_sell_point"));
            searchItems.add(searchItem);

            String title;
            List<String> titles = highlighting.get(document.get("id")).get("item_title");
            if (titles!=null && titles.size()>0){
                title = titles.get(0);
            } else {
                title = (String) document.get("item_title");
            }
            searchItem.setTitle(title);

        }



        SearchResult result = new SearchResult();
        result.setSearchItems(searchItems);
        result.setRecourdCount(documents.getNumFound());
        return result;

    }

}

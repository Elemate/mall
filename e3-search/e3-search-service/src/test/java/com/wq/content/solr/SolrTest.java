package com.wq.content.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.List;
import java.util.Map;

public class SolrTest {

    public void addDocument() throws Exception{
        //第一步引入solr jar包
        //第二步创建一个solrServer对象
        SolrServer solrServer = new HttpSolrServer("http://10.1.1.179:8081/solr");
        //第三步创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //第四步像文档中添加域，域必须定义在solrhome/collection1/conf下的schema.xml文件中已经定义了
        document.addField("id","123");
        document.addField("item_title","会话");
        document.addField("item_category_name","不惜哦啊的");
        //第五步把文档添加到索引库
        solrServer.add(document);
        //第六步提交
        solrServer.commit();
    }


    public void deleteDocument() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://10.1.1.179:8081/solr/collection1");
//        solrServer.deleteById("123");
        solrServer.deleteByQuery("item_title:会话");
        solrServer.commit();
    }

    public void query() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://10.1.1.179:8081/solr/collection1");

        SolrQuery query = new SolrQuery();
        query.set("q","*:*");

        QueryResponse response = solrServer.query(query);
        SolrDocumentList documentList = response.getResults();
        long total = documentList.getNumFound();
        System.out.println("total:"+total);
        for (SolrDocument document : documentList){
            System.out.println(document.getFieldValue("id"));
            System.out.println(document.getFieldValue("item_title"));
            System.out.println(document.getFieldValue("item_image"));
            System.out.println(document.getFieldValue("item_price"));
            System.out.println(document.getFieldValue("item_category_name"));
            System.out.println(document.getFieldValue("item_sell_point"));
        }

    }

    public void queryFuza() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://10.1.1.179:8081/solr/collection1");

        SolrQuery query = new SolrQuery();
        query.setQuery("手机");
        query.setStart(10);
        query.setRows(6);
        query.set("df","item_title");

        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");

        QueryResponse response = solrServer.query(query);
        SolrDocumentList documentList = response.getResults();
        long total = documentList.getNumFound();
        System.out.println("total:"+total);

        for (SolrDocument document : documentList){
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> titles = highlighting.get(document.get("id")).get("item_title");
            String title;
            if (titles!=null && titles.size()>0){
                title = titles.get(0);
            } else
                title = (String) document.get("item_title");

            System.out.println(document.getFieldValue("id"));
            System.out.println(document.getFieldValue("item_title"));
            System.out.println(document.getFieldValue("item_image"));
            System.out.println(document.getFieldValue("item_price"));
            System.out.println(document.getFieldValue("item_category_name"));
            System.out.println(document.getFieldValue("item_sell_point"));
        }

    }
}










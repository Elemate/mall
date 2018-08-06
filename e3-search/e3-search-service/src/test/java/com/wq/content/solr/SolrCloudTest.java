package com.wq.content.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrCloudTest {


    public void testSolrCloudAddDocument() throws Exception{
        //第一步，加入solrJ相关jar包
        //第二步，创建一个SolrServer对象，需要使用其子类CloudSolrServer。构造方法参数是zookeeper的地址列表
        CloudSolrServer cloudSolrServer = new CloudSolrServer("10.1.1.179:2182,10.1.1.179:2183,10.1.1.179:2184");

        //第三步，设置默认collection
        cloudSolrServer.setDefaultCollection("collection2");

        //第四步创建文档
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id","solrCloud2");
        document.setField("item_title","solrCloud1");

        //第五步，将文档添加到索引库
        cloudSolrServer.add(document);
        cloudSolrServer.commit();
    }

    public void testSolrCloudQuery() throws Exception {
        //第一步，加入solrJ相关jar包
        //第二步，创建一个SolrServer对象，需要使用其子类CloudSolrServer。构造方法参数是zookeeper的地址列表
        CloudSolrServer cloudSolrServer = new CloudSolrServer("10.1.1.179:2182,10.1.1.179:2183,10.1.1.179:2184");

        //第三步，设置默认collection
        cloudSolrServer.setDefaultCollection("collection2");

        //第四步创建查询对象
        SolrQuery query = new SolrQuery();
        query.setQuery("solrCloud");    //这里好想把solrCloud当作一个单独的词不可分割
        query.set("df","item_title");
        //获得查询结果
        QueryResponse response = cloudSolrServer.query(query);
        SolrDocumentList documentList = response.getResults();
        for (SolrDocument document : documentList) {
            System.out.println(document.getFieldValue("item_title"));
            System.out.println(document.getFieldValue("id"));
        }

    }
}

package com.wq.controller;

import com.wq.common.utils.TaotaoResult;
import com.wq.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solr")
public class SolrController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/import")
    @ResponseBody
    public TaotaoResult importItems(){
        TaotaoResult result = searchService.importItemToSolr();
        return result;
    }
}

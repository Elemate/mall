package com.wq.search.controller;

import com.wq.common.pojo.SearchResult;
import com.wq.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(String keyword, Model model) throws Exception{
        keyword = new String(keyword.getBytes("ISO8859-1"),"utf-8");
        SearchResult result = searchService.search(keyword);
        model.addAttribute("totalPages",result.getTotalPages());
        model.addAttribute("itemList",result.getSearchItems());
        model.addAttribute("query",keyword);

        return "search";
    }
}

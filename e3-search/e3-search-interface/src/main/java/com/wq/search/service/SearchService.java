package com.wq.search.service;

import com.wq.common.pojo.SearchResult;
import com.wq.common.utils.TaotaoResult;

public interface SearchService {

    public TaotaoResult importItemToSolr();

    public SearchResult search(String keyword) throws Exception;

}

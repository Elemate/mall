package com.wq.search.mapper;

import com.wq.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {

    public List<SearchItem> getItemList();

    public SearchItem getItemById(long itemId);
}

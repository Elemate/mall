package com.wq.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

    private long totalPages;        //总页数
    private long recourdCount;  //总符合条件记录数
    private List<SearchItem> searchItems;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }


    public long getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(long recourdCount) {
        this.recourdCount = recourdCount;
    }

    public List<SearchItem> getSearchItems() {
        return searchItems;
    }

    public void setSearchItems(List<SearchItem> searchItems) {
        this.searchItems = searchItems;
    }
}

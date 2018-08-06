package com.wq.service;

import com.wq.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {

    public List<TbItemCat> getItemCatList(long parentId);
}

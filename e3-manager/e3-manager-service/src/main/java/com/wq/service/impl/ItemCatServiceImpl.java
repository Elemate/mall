package com.wq.service.impl;

import com.wq.mapper.TbItemCatMapper;
import com.wq.pojo.TbItemCat;
import com.wq.pojo.TbItemCatExample;
import com.wq.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<TbItemCat> getItemCatList(long parentId) {

        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        return list;
    }
}

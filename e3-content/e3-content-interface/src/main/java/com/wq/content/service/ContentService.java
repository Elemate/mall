package com.wq.content.service;

import com.wq.common.pojo.EasyuiDataGridResult;
import com.wq.common.utils.TaotaoResult;
import com.wq.pojo.TbContent;

import java.util.List;

public interface ContentService {

    public EasyuiDataGridResult getContentListByContentCatId(int page, int rows, long categoryId);

    public TaotaoResult addContent(TbContent tbcontent);

    public TaotaoResult updateContent(TbContent tbContent);

    public TaotaoResult deleteContent(String ids);

    //首野轮播图展示
    public List<TbContent> getContentListByCid(long cid);
}

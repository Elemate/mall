package com.wq.content.service;

/*
    内容分类管理接口
*/

import com.wq.common.pojo.CatJsonResutl;
import com.wq.common.utils.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    //获得内容分类一级目录
    public List<CatJsonResutl> getContentCategoryList(Long parentId);
    //添加目录
    public TaotaoResult addContentCategory(Long parentId, String name);
    //更新目录
    public void updateContentCategory(long id, String name);
    //删除目录
    public void deleteContentCategory(long id);
}

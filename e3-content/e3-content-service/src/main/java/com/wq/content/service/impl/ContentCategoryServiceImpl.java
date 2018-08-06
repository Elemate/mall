package com.wq.content.service.impl;

import com.wq.common.pojo.CatJsonResutl;
import com.wq.common.utils.TaotaoResult;
import com.wq.content.service.ContentCategoryService;
import com.wq.mapper.TbContentCategoryMapper;
import com.wq.pojo.TbContentCategory;
import com.wq.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<CatJsonResutl> getContentCategoryList(Long parentId) {

        TbContentCategoryExample example =  new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

        List<CatJsonResutl> catJsonResutls = new ArrayList<>();
        for (TbContentCategory contentCategory : list) {
            CatJsonResutl resutl = new CatJsonResutl();
            resutl.setText(contentCategory.getName());
            resutl.setId(contentCategory.getId());
            resutl.setIsParent(contentCategory.getIsParent());
            catJsonResutls.add(resutl);
        }
        return catJsonResutls;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        TaotaoResult result = new TaotaoResult();
        try {
            TbContentCategory contentCategory = new TbContentCategory();
            Date date = new Date();
            contentCategory.setCreated(date);
            contentCategory.setUpdated(date);
            contentCategory.setName(name);
            contentCategory.setParentId(parentId);
            contentCategory.setSortOrder(1);
            contentCategory.setStatus(1);  // 状态。可选值:1(正常),2(删除)'
            contentCategory.setIsParent(false);
            contentCategoryMapper.insert(contentCategory);
            result.setData(contentCategory);
            result.setStatus(200);

            //修改父母录属性
            TbContentCategory contentCategoryParent = contentCategoryMapper.selectByPrimaryKey(parentId);
            contentCategoryParent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(contentCategoryParent);
        } catch (Exception e){
            e.printStackTrace();
            return new TaotaoResult(500);
        }


        return result;
    }

    @Override
    public void updateContentCategory(long id, String name) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKey(contentCategory);
    }

    @Override
    public void deleteContentCategory(long id) {
        //判读该目录是否为父目录,如果不为父目录直接删除
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        if (!contentCategory.getIsParent()){
            contentCategoryMapper.deleteByPrimaryKey(id);
            //然后查询父母录是否还有子目录，如果没有将父目录isParent改为false
            Long parentId = contentCategory.getParentId();
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(parentId);
            int count = contentCategoryMapper.countByExample(example);
            if (!(count>0)){
                TbContentCategory parentContenCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
                parentContenCategory.setIsParent(false);
                contentCategoryMapper.updateByPrimaryKey(parentContenCategory);
            }
        } else {            //是父母录的话删除所有下级目录
            deleteContentCategoryById(id);
            //然后查询其上级目录是否还有子目录，没有将上级目录IsParent改为false
           TbContentCategoryExample example = new TbContentCategoryExample();
           example.createCriteria().andParentIdEqualTo(contentCategory.getParentId());
           int count = contentCategoryMapper.countByExample(example);
           if (count==0){
               TbContentCategory contentCategoryParent = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
               contentCategoryParent.setIsParent(false);
               contentCategoryMapper.updateByPrimaryKey(contentCategoryParent);
           }

        }
    }

    //递归删除所有子目录
    public void deleteContentCategoryById(Long id){
        contentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        int count = contentCategoryMapper.countByExample(example);
        if (count!=0){
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory contentCategory : list){
                deleteContentCategoryById(contentCategory.getId());
            }
        }

    }
}

package com.wq.controller;

import com.wq.common.pojo.EasyuiDataGridResult;
import com.wq.common.utils.TaotaoResult;
import com.wq.content.service.ContentService;
import com.wq.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EasyuiDataGridResult getContentListByContentCatId(long categoryId, int page, int rows){

        EasyuiDataGridResult result = contentService.getContentListByContentCatId(page, rows, categoryId);
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent tbContent){
        TaotaoResult result = contentService.addContent(tbContent);
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(String ids){
        TaotaoResult result = contentService.deleteContent(ids);
        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult updateContent(TbContent tbContent){
        TaotaoResult result = contentService.updateContent(tbContent);
        return result;
    }


}

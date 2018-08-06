package com.wq.controller;

import com.wq.common.pojo.CatJsonResutl;
import com.wq.common.utils.TaotaoResult;
import com.wq.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content")
public class ContenCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/categorylist")
    @ResponseBody
    public List<CatJsonResutl> getContentCategoryList(@RequestParam(name = "id", defaultValue = "0")long parentId){
        List<CatJsonResutl> resutls = contentCategoryService.getContentCategoryList(parentId);
        return resutls;
    }

    @RequestMapping("/category/create")
    @ResponseBody
    public TaotaoResult addContentCategory(String name, Long parentId){
        TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }

    @RequestMapping("/category/update")
    public void updateContentCategory(long id, String name){
        contentCategoryService.updateContentCategory(id,name);
    }

    @RequestMapping("/category/delete")
    public String delteContentCategory(long id){
        contentCategoryService.deleteContentCategory(id);
        return "forward:list";
    }
}

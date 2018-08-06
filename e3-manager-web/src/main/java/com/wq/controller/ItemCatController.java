package com.wq.controller;

import com.wq.common.pojo.CatJsonResutl;
import com.wq.pojo.TbItem;
import com.wq.pojo.TbItemCat;
import com.wq.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/*
        商品目錄Controller
*/

@Controller
@RequestMapping("/itemCat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/list")
    @ResponseBody
    public List<CatJsonResutl> getItemCatList(@RequestParam(name = "id",defaultValue = "0")long parentId){

       List<TbItemCat> tbItemCat = itemCatService.getItemCatList(parentId);
       List<CatJsonResutl> catJsonResutls = new ArrayList<>();
       for (TbItemCat itemCat : tbItemCat){

           CatJsonResutl catJsonResutl = new CatJsonResutl();
           catJsonResutl.setId(itemCat.getId());
           catJsonResutl.setText(itemCat.getName());
           catJsonResutl.setIsParent(itemCat.getIsParent());

           catJsonResutls.add(catJsonResutl);
       }
        return catJsonResutls;
    }
}

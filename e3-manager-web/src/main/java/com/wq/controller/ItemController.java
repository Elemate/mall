package com.wq.controller;

import com.wq.common.pojo.EasyuiDataGridResult;
import com.wq.common.pojo.ItemParamsPoJo;
import com.wq.common.utils.TaotaoResult;
import com.wq.pojo.TbItem;
import com.wq.service.ItemService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem item = itemService.getItemById(itemId);
        return item;
    }

    //获取商品列表信息
    @RequestMapping("/list")
    @ResponseBody
    public EasyuiDataGridResult getItemList(int page, int rows){

        EasyuiDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    //新增商品信息
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String desc){
        TaotaoResult result = itemService.addItem(tbItem, desc);
        return result;
    }

    //查询商品描述
    @RequestMapping("/query/item/desc/{id}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable long id){
        TaotaoResult result = itemService.getItemDesc(id);

        return result;
    }

    //获取商品规格
    @RequestMapping("/param/item/query/{id}")
    @ResponseBody
    public TaotaoResult getItemParam(@PathVariable long id){
        TaotaoResult result = itemService.getItemParamData(id);
        result.setStatus(200);
        return result;
    }

    //更新商品信息
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateItem(TbItem item, String desc, String itemParams, long itemParamId){
        //可以这样解析为对象
        /*JSONArray jsonArray = JSONArray.fromObject(itemParams);
        List<ItemParamsPoJo> list = (List<ItemParamsPoJo>) jsonArray.toCollection(jsonArray, ItemParamsPoJo.class);*/
        TaotaoResult result = itemService.updateItem(item, desc, itemParams, itemParamId);
        return result;
    }

    //批量删除商品信息
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteItem(String ids){
        TaotaoResult result = itemService.deleteItem(ids);
        return result;
    }
}

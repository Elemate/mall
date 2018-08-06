package com.wq.item.controller;

import com.wq.common.utils.TaotaoResult;
import com.wq.item.pojo.Item;
import com.wq.pojo.TbItem;
import com.wq.pojo.TbItemDesc;
import com.wq.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item-web")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo( @PathVariable long itemId,Model model){

        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }
}

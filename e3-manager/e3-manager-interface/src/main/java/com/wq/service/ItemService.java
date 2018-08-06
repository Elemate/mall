package com.wq.service;

import com.wq.common.pojo.EasyuiDataGridResult;
import com.wq.common.utils.TaotaoResult;
import com.wq.pojo.TbItem;
import com.wq.pojo.TbItemDesc;

public interface ItemService {

    TbItem getItemById(long id);

    TbItemDesc getItemDescByItemId(long itemId);

    EasyuiDataGridResult getItemList(int page, int rows);
    TaotaoResult addItem(TbItem tbItem, String desc);

    TaotaoResult getItemDesc(long itemId);
    TaotaoResult getItemParamData(long itemId);

    TaotaoResult updateItem(TbItem item, String desc, String itemParamItem, long itemParamId);

    TaotaoResult deleteItem(String ids);

}

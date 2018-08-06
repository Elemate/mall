package com.wq.item.pojo;

import com.wq.pojo.TbItem;

/*
    展示商品详情页面所需要的pojo
*/

public class Item extends TbItem {

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
        this.setStatus(tbItem.getStatus());
        this.setPrice(tbItem.getPrice());
        this.setBarcode(tbItem.getBarcode());
        this.setNum(tbItem.getNum());
        this.setCid(tbItem.getCid());
        this.setImage(tbItem.getImage());
    }

    public String[] getImages(){
        String iamges = this.getImage();
        if (iamges!=null && !"".equals(iamges)){
            return iamges.split(",");
        }
        return null;
    }
}

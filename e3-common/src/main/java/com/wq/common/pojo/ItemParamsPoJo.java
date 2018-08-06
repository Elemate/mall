package com.wq.common.pojo;

/*
    商品规格参数
*/

import java.util.List;

public class ItemParamsPoJo {
    private String group;
    private List<ParamsPojo> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ParamsPojo> getParams() {
        return params;
    }

    public void setParams(List<ParamsPojo> params) {
        this.params = params;
    }
}

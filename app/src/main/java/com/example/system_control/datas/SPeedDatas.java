package com.example.system_control.datas;

import com.alibaba.fastjson.annotation.JSONField;

public class SPeedDatas {

    /**
     * creat_time : 2023-03-06 22:13:18
     * zhuansu : 300.0
     * zhuansu2 : 0.0
     */
    @JSONField(name = "creat_time")
    private String creat_time;
    @JSONField(name = "zhuansu")
    private String zhuansu;
    @JSONField(name = "zhuansu2")
    private String zhuansu2;

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getZhuansu() {
        return zhuansu;
    }

    public void setZhuansu(String zhuansu) {
        this.zhuansu = zhuansu;
    }

    public String getZhuansu2() {
        return zhuansu2;
    }

    public void setZhuansu2(String zhuansu2) {
        this.zhuansu2 = zhuansu2;
    }
}

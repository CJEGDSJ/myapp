package com.example.system_control.datas;

import com.alibaba.fastjson.annotation.JSONField;

public class CurrentDatas {

    /**
     * creat_time : 2023-03-06 22:13:18
     * dianliu : 1.0
     * dianliu2 : 1.0
     */
    @JSONField(name = "creat_time")
    private String creat_time;
    @JSONField(name = "dianliu")
    private String dianliu;
    @JSONField(name = "dianliu2")
    private String dianliu2;

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getDianliu() {
        return dianliu;
    }

    public void setDianliu(String dianliu) {
        this.dianliu = dianliu;
    }

    public String getDianliu2() {
        return dianliu2;
    }

    public void setDianliu2(String dianliu2) {
        this.dianliu2 = dianliu2;
    }
}

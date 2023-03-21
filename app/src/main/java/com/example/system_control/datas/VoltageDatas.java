package com.example.system_control.datas;

import com.alibaba.fastjson.annotation.JSONField;

public class VoltageDatas {

    /**
     * creat_time : 2023-03-06 22:13:18
     * dianya : 1.7843
     * dianya2 : 0.0
     */
    @JSONField(name = "creat_time")
    private String creat_time;
    @JSONField(name = "dian")
    private String dian;
    @JSONField(name = "dian2")
    private String dian2;
    @JSONField(name = "dianya")
    private String dianya;
    @JSONField(name = "dianya2")
    private String dianya2;

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getDian() {
        return dian;
    }

    public void setDian(String dian) {
        this.dian = dian;
    }

    public String getDian2() {
        return dian2;
    }

    public void setDian2(String dian2) {
        this.dian2 = dian2;
    }

    public String getDianya() {
        return dianya;
    }

    public void setDianya(String dianya) {
        this.dianya = dianya;
    }

    public String getDianya2() {
        return dianya2;
    }

    public void setDianya2(String dianya2) {
        this.dianya2 = dianya2;
    }
}

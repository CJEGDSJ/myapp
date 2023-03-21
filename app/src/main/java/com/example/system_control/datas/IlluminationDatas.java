package com.example.system_control.datas;

import com.alibaba.fastjson.annotation.JSONField;

public class IlluminationDatas {

    /**
     * creat_time : 2021-10-11 18:02:10
     * guangzhao : 123.0
     * guangzhao2 : 123.0
     */
    @JSONField(name = "creat_time")
    private String creat_time;
    @JSONField(name = "guangzhao")
    private String guangzhao;
    @JSONField(name = "guangzhao2")
    private String guangzhao2;

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getGuangzhao() {
        return guangzhao;
    }

    public void setGuangzhao(String guangzhao) {
        this.guangzhao = guangzhao;
    }

    public String getGuangzhao2() {
        return guangzhao2;
    }

    public void setGuangzhao2(String guangzhao2) {
        this.guangzhao2 = guangzhao2;
    }

    @Override
    public String toString() {
        return "IlluminationDatas{" +
                "creat_time='" + creat_time + '\'' +
                ", guangzhao='" + guangzhao + '\'' +
                ", guangzhao2='" + guangzhao2 + '\'' +
                '}';
    }
}

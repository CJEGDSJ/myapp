package com.example.system_control.datas;

import com.alibaba.fastjson.annotation.JSONField;

public class StateDatas {
    /**
     * elce : 1
     * fan : 1
     * light : 0
     * twofan : 0
     */
    @JSONField(name = "elec")
    private String elec;
    @JSONField(name = "fan")
    private String fan;
    @JSONField(name = "light")
    private String light;
    @JSONField(name = "twofan")
    private String twofan;

    public String getElec() {
        return elec;
    }

    public void setElec(String elec) {
        this.elec = elec;
    }

    public String getFan() {
        return fan;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getTwofan() {
        return twofan;
    }

    public void setTwofan(String twofan) {
        this.twofan = twofan;
    }
}

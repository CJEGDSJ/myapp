package com.example.system_control.datas;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Auto-generated: 2023-03-01 16:28:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TemperatureDatas {
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    /*@JSONField(name = "creat_time")*/
    private Date creat_time;
    @JSONField(name = "wen")
    private String wen;
    @JSONField(name = "wen2")
    private String wen2;
    @JSONField(name = "wendu")
    private String wendu;
    @JSONField(name = "wendu2")
    private String wendu2;

    public void setCreat_time(Date creat_time) {
        this.creat_time = creat_time;
    }

    public Date getCreat_time() {
        return creat_time;
    }

    public String getWen() {
        return wen;
    }

    public void setWen(String wen) {
        this.wen = wen;
    }

    public String getWen2() {
        return wen2;
    }

    public void setWen2(String wen2) {
        this.wen2 = wen2;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu2(String wendu2) {
        this.wendu2 = wendu2;
    }

    public String getWendu2() {
        return wendu2;
    }

    @Override
    public String toString() {
        return "TemperatureDatas{" +
                "creat_time=" + creat_time +
                ", wendu='" + wendu + '\'' +
                ", wendu2='" + wendu2 + '\'' +
                '}';
        /*return JSONObject.toJSONString(this);*/
    }
}
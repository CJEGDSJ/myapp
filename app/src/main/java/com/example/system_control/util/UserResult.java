package com.example.system_control.util;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class UserResult {
    /**
     * Auto-generated: 2023-02-28 19:49:27
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "password")
    private String password;
    @JSONField(name = "username")
    private String username;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @NonNull
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}


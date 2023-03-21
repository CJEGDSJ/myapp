package com.example.system_control.util;


import okhttp3.ResponseBody;
import retrofit2.Callback;

public class Network {
    final private ServerInterface serverInterface;

    public Network(String baseUrl) {
        this.serverInterface = Tools.buildService(baseUrl);
    }


    public void getnewdatas(Callback<ResponseBody> callback) {
        serverInterface.newdada("").enqueue(callback);
    }

    public void latestdata(Callback<AllNewDatas> callBack) {
        serverInterface.newdadas("").enqueue(callBack);
    }

    public void sign(String username, String password, Callback<UserResult> callback) {
        serverInterface.sign(username, password).enqueue(callback);
    }

    public void getallthreshold(Callback<ResponseBody> callback) {
        serverInterface.querythreshold("").enqueue(callback);
    }

    public void queryguangzhao(Callback<ResponseBody> callback) {
        serverInterface.queryguangzhao("").enqueue(callback);
    }


    public void getalldatas(Callback<ResponseBody> callback) {
        serverInterface.getalldatas("").enqueue(callback);
    }

    public void queryTemperature(Callback<ResponseBody> callback) {
        serverInterface.queryTemperature("").enqueue(callback);
    }

    public void queryIllumination(Callback<ResponseBody> callback) {
        serverInterface.queryIllumination("").enqueue(callback);
    }

    public void querySpeed(Callback<ResponseBody> callback) {
        serverInterface.querySpeed("").enqueue(callback);
    }

    public void queryVoltage(Callback<ResponseBody> callback) {
        serverInterface.queryVoltage("").enqueue(callback);
    }

    public void getallwendu(Callback<ResponseBody> callback) {
        serverInterface.getallwendu("").enqueue(callback);
    }

    public void getallzhuasu(Callback<ResponseBody> callback) {
        serverInterface.getallzhuansu("").enqueue(callback);
    }

    public void getallguangzhao(Callback<ResponseBody> callback) {
        serverInterface.getallguangzhao("").enqueue(callback);
    }

    public void getalldianya(Callback<ResponseBody> callback) {
        serverInterface.getalldianya("").enqueue(callback);
    }

    public void getalldianliu(Callback<ResponseBody> callback) {
        serverInterface.getalldianliu("").enqueue(callback);
    }

    public void light_off(Callback<ResponseBody> callback) {
        serverInterface.stitch("0").enqueue(callback);
    }

    public void light_on(Callback<ResponseBody> callback) {
        serverInterface.stitch("1").enqueue(callback);
    }

    public void fan_on(Callback<ResponseBody> callback) {
        serverInterface.fan("1").enqueue(callback);
    }

    public void fan_off(Callback<ResponseBody> callback) {
        serverInterface.fan("0").enqueue(callback);
    }

    public void twofan_on(Callback<ResponseBody> callback) {
        serverInterface.tfan("1").enqueue(callback);
    }

    public void twofan_off(Callback<ResponseBody> callback) {
        serverInterface.tfan("0").enqueue(callback);
    }

    public void getallstat(Callback<ResponseBody> callback) {
        serverInterface.queryallstate("").enqueue(callback);

    }
    public void changespeed(Callback<ResponseBody> callback) {
        serverInterface.changeSpeed("").enqueue(callback);

    }
    public void changespeed2(Callback<ResponseBody> callback) {
        serverInterface.changeSpeed2("").enqueue(callback);

    }

}
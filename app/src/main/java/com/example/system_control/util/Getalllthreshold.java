package com.example.system_control.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.system_control.datas.AllthresholdDatas;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Getalllthreshold {
    private String baseurl = "http://192.168.100.174:9000/";
    private Network network = new Network(baseurl);
    private AllthresholdDatas allthresholdDatas;
    private double wfazhigao;
    private float wgao;

    private void getallthreshold() {

    }

    public float getwfazhigao() {
        network.getallthreshold(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        allthresholdDatas = JSON.parseObject(string, AllthresholdDatas.class);
                        wfazhigao = allthresholdDatas.getWfazhigao();
                        wgao = (float)wfazhigao;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });
        Log.e("TAG", "wfazhigao: " + wgao);
        return wgao;
    }

    public void wfazhidi() {
        double wfazhidi = allthresholdDatas.getWfazhidi();

    }
}

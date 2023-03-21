package com.example.system_control.service;

import android.app.IntentService;
import android.content.Intent;

import com.alibaba.fastjson.JSONArray;
import com.example.system_control.datas.TemperatureDatas;
import com.example.system_control.util.Network;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyIntentService extends IntentService {

    private String baseurl = "http://192.168.100.174:9000";
    private Network network;

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            // 在这里执行网络请求等耗时操作
            network = new Network(baseurl);
            String result = fetchDataFromNetwork();
            // 将结果发送给广播接收器
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("com.example.RESULT_ACTION");
            broadcastIntent.putExtra("result", result);
            sendBroadcast(broadcastIntent);

        }
    }

    private String fetchDataFromNetwork() {


        // 创建一个URL对象，用于获取数据
/*        String urlString = baseurl;
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }*/

        // 创建一个HttpURLConnection对象，连接到URL并获取数据
/*        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();

            // 将获取的数据转换为字符串
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
        return null;
    }
    private void getTemperature() {
        network.getallwendu(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<TemperatureDatas> temperatureDatas1 = JSONArray.parseArray(string, TemperatureDatas.class);
                        int size = temperatureDatas1.size();

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
    }

}
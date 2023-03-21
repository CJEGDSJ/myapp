package com.example.system_control.util;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tools {

    public static ServerInterface buildService(String baseUrl) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置连接超时时间为 10 秒
                .readTimeout(30, TimeUnit.SECONDS) // 设置读取超时时间为 30 秒
                .writeTimeout(30, TimeUnit.SECONDS); // 设置写入超时时间为 30 秒;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServerInterface serverInterface = retrofit.create(ServerInterface.class);
        /*.build();*/
        return serverInterface;
/*
        ApiService apiService = retrofit.create(ApiService.class);
*/

/*        Call<ResponseBody> call = serverInterface.getData();
        try {
            Response<ResponseBody> response = call.execute();

            // 处理响应
        } catch (IOException e) {
            // 处理异常
        }*/
/*
                new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build().create(ServerInterface.class);
*/
    }

    public static String getObjectFieldMsg(Object object, int spaceNum) {
        if (object == null) {
            return "";
        }
        String spaceStr = "";
        for (int i = 0; i < spaceNum; i++) {
            spaceStr = spaceStr + "" + "\t";
        }
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                stringBuffer.append(spaceStr).append(field.getName()).append(" : ").append(field.get(object)).append("\n");
            } catch (IllegalAccessException ex) {
                return "";
            }

        }
        return String.valueOf(stringBuffer);
    }

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void printJson(TextView textView, String msg, boolean clear) {
        if (clear) {
            textView.setText("");
        }
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        message = LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            textView.append(line + LINE_SEPARATOR);
        }

    }

    public static void printJson(TextView textView, String msg) {
        printJson(textView, msg, true);
    }

}

package com.example.system_control.OtherActivity.Fomatter;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.system_control.datas.TemperatureDatas;
import com.example.system_control.util.DateUtil;
import com.example.system_control.util.Network;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayValueFormatter extends ValueFormatter {
    private DecimalFormat mFormat;

    private String baseurl = new String("http://192.168.100.145:9000/");
    private Network network = new Network(baseurl);
    int length = 50;
    String times[] = new String[length];
    String[] caculate = new String[length];
    String ss;
    private final BarLineChartBase<?> chart;

    public DayValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;

        mFormat = new DecimalFormat("0.00");
    }

    public String getFormattedValue(float value) {

        delay();
        return ss;
    }

    private String[] GetDates() {
        network.getallwendu(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<TemperatureDatas> temperatureDatas = JSONArray.parseArray(string, TemperatureDatas.class);
                        int size = temperatureDatas.size();
                        caculate = caculate(size, temperatureDatas, times);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return caculate;
    }

    private String[] caculate(int size, List<TemperatureDatas> temperatureDatas, String[] strings) {
        if (size <= length) {
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size - 1; j++) {
                    Date creat_time = temperatureDatas.get(size - (1 + j)).getCreat_time();
                    String time = DateUtil.dateToString(creat_time, DateUtil.DatePattern.ALL_TIME);
                    strings[j] = time;
                    Log.e("TAG", "time" + time);
                }
            }
        } else {
            for (int i = size - 1; i >= size - (size - length); i--) {
                for (int j = 0; j < length; j++) {
                    Date creat_time = temperatureDatas.get(size - (1 + j)).getCreat_time();
                    String time = DateUtil.dateToString(creat_time, DateUtil.DatePattern.ALL_TIME);
                    strings[j] = time;
                }
            }
        }
        return strings;
    }

    void delay() {
        for (int i = 0; i < length; i++) {
            String[] strings = GetDates();
            ss = strings[i];
        }
    }


}

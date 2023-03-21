package com.example.system_control.OtherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.system_control.OtherActivity.Fomatter.CurrentValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.LightValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.SpeedValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.TValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.VoltagevalueFormatter;
import com.example.system_control.R;
import com.example.system_control.datas.AllthresholdDatas;
import com.example.system_control.datas.CurrentDatas;
import com.example.system_control.datas.IlluminationDatas;
import com.example.system_control.datas.SPeedDatas;
import com.example.system_control.datas.TemperatureDatas;
import com.example.system_control.datas.VoltageDatas;
import com.example.system_control.util.CaculateDatas;
import com.example.system_control.util.Network;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Line_chart2_Activity extends AppCompatActivity implements OnChartValueSelectedListener {
    private LineChart LineChart_Temperature;
    private LineChart LineChart_Temperature2;
    private LineChart LineChart_Illumination;
    private LineChart LineChart_Speed;
    private LineChart LineChart_Speed2;
    private LineChart LineChart_Voltage;
    private LineChart LineChart_Voltage2;
    private LineChart LineChart_Current;
    private EditText et_input_ID;
    private Network network;
    private String[] wenduarry = new String[50];
    private String[] wenduarry2 = new String[50];
    private String[] guangzhaoarry = new String[50];
    private String[] dianyaarry = new String[50];
    private String[] dianyaarry2 = new String[50];
    private String[] dianliuarry = new String[50];
    private String[] zhuansuarry = new String[50];
    private String[] zhuansuarry2 = new String[50];
    private Button btn_update;
    private String errormassage = "Internal Server Error";
    List<Entry> TemperaturebarEntries = new ArrayList<>();
    List<Entry> TemperaturebarEntries2 = new ArrayList<>();
    List<Entry> IlluminationEntries = new ArrayList<>();
    List<Entry> VoltageEntries = new ArrayList<>();
    List<Entry> VoltageEntries2 = new ArrayList<>();

    List<Entry> CurrentEntries = new ArrayList<>();
    List<Entry> SpeedEntries = new ArrayList<>();
    List<Entry> SpeedEntries2 = new ArrayList<>();
    private TValueFormatter TemperatureValueFormatter = new TValueFormatter();
    private LightValueFormatter IlluminationValueFormatter = new LightValueFormatter();
    private SpeedValueFormatter speedValueFormatter = new SpeedValueFormatter();
    private CurrentValueFormatter currentValueFormatter = new CurrentValueFormatter();
    private VoltagevalueFormatter voltagevalueFormatter = new VoltagevalueFormatter();
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_line_chart2);
        /*network = new Network("http://192.168.100.145:9000/");*/
        network = new Network("http://192.168.0.199:9000/");
        init();
    }

    private void init() {
        /*LineChart_Temperature = findViewById(R.id.Temperature_LineChart2);
        LineChart_Temperature2 = findViewById(R.id.Temperature_LineChart2_2);
        LineChart_Illumination = findViewById(R.id.Illumination_LineChart2);*/
        LineChart_Speed = findViewById(R.id.Speed_LineChart_2);
        LineChart_Speed2 = findViewById(R.id.Speed_LineChart2_2);
        /*LineChart_Voltage = findViewById(R.id.Voltage_LineChart2);
        LineChart_Voltage2 = findViewById(R.id.Voltage_LineChart2_2);
        LineChart_Current = findViewById(R.id.Current_LineChart2);*/
        toolbar = findViewById(R.id.linetool2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        et_input_ID = findViewById(R.id.et_change_ID);
        btn_update = findViewById(R.id.btn_InquireID);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkID();

            }
        });
        selected();


    }

    private void selected() {
        bottomNavigationView.setSelectedItemId(R.id.Line_chart);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent_toL = new Intent(getApplicationContext(), DispalyActivity.class);
                        intent_toL.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_toL);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Line_chart:

                        return true;
                    case R.id.Bar_chart:
                        Intent intent_toB = new Intent(getApplicationContext(), Bar_chart_Activity.class);
                        intent_toB.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_toB);
                        overridePendingTransition(0, 0);

                        return true;
/*                    case R.id.menu:
                        Intent intent_tom = new Intent(getApplicationContext(), ControlActivity.class);
                        intent_tom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_tom);
                        overridePendingTransition(0, 0);
                        return true;*/
                }
                return false;
            }
        });
    }

    private void init_linechart(LineChart lineChart, ValueFormatter valueFormatter, String lable, float percent) {
        //设置X轴的属性
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置放大时轴的最小间隔。轴不允许低于该限制。这可用于在放大时避免标签重复。
        xAxis.setGranularity(1f);
        //X轴的适配器
        /*Myformatter myformatter = new Myformatter();
        xAxis.setValueFormatter(myformatter);*/
        xAxis.setAvoidFirstLastClipping(false);

        xAxis.setGranularityEnabled(true);
        xAxis.setDrawLabels(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setGranularityEnabled(true);
        //设置放大时轴的最小间隔。轴不允许低于该限制。这可用于在放大时避免标签重复。
        leftAxis.setGranularity(0);
        leftAxis.setLabelCount(10, false);
        leftAxis.setValueFormatter(valueFormatter);

        //设置 y 标签的位置
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(percent);

        setlimitline(leftAxis, lable);
        leftAxis.setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
//        lineChart.animateXY(500,500);
//        getTemperature();

    }

    private void init_linechart(LineChart lineChart, ValueFormatter valueFormatter) {
        //设置X轴的属性
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置放大时轴的最小间隔。轴不允许低于该限制。这可用于在放大时避免标签重复。
        xAxis.setGranularity(1f);
        //X轴的适配器
        /*Myformatter myformatter = new Myformatter();
        xAxis.setValueFormatter(myformatter);*/
        xAxis.setAvoidFirstLastClipping(false);

        xAxis.setGranularityEnabled(true);
        xAxis.setDrawLabels(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setGranularityEnabled(true);
        //设置放大时轴的最小间隔。轴不允许低于该限制。这可用于在放大时避免标签重复。
        leftAxis.setGranularity(0);
        leftAxis.setLabelCount(10, false);
        leftAxis.setValueFormatter(valueFormatter);

        //设置 y 标签的位置
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(5000f);

        /* setlimitline(leftAxis, lable);*/
        leftAxis.setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
//        lineChart.animateXY(500,500);
//        getTemperature();

    }

    private void init_linechart(LineChart lineChart, ValueFormatter valueFormatter, float percnet) {
        //设置X轴的属性
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置放大时轴的最小间隔。轴不允许低于该限制。这可用于在放大时避免标签重复。
        xAxis.setGranularity(1f);
        //X轴的适配器
        /*Myformatter myformatter = new Myformatter();
        xAxis.setValueFormatter(myformatter);*/
        xAxis.setAvoidFirstLastClipping(false);

        xAxis.setGranularityEnabled(true);
        xAxis.setDrawLabels(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setGranularityEnabled(true);
        //设置放大时轴的最小间隔。轴不允许低于该限制。这可用于在放大时避免标签重复。
        leftAxis.setGranularity(0);
        leftAxis.setLabelCount(10, false);
        leftAxis.setValueFormatter(valueFormatter);

        //设置 y 标签的位置
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(percnet);

        /* setlimitline(leftAxis, lable);*/
        leftAxis.setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
//        lineChart.animateXY(500,500);
//        getTemperature();

    }

    private void updata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "数据正在查询，请稍后", Toast.LENGTH_SHORT);
                        Invalidate_chart();
/*
                        getTemperature(TemperaturebarEntries, wenduarry, LineChart_Temperature, "Temperature", "#40e0d0",3);
                        init_linechart(LineChart_Temperature, TemperatureValueFormatter, "Temperature",500f);
                        getTemperature(TemperaturebarEntries2, wenduarry2, LineChart_Temperature2, "Temperature2","#00ff7f",0);
                        init_linechart(LineChart_Temperature2, TemperatureValueFormatter, "Temperature2",500f);
                        getIllumination();
                        init_linechart(LineChart_Illumination, IlluminationValueFormatter, "Illumination",1000f);

                        getVoltage(VoltageEntries, dianyaarry, LineChart_Voltage, "Voltage", "#00008b",2);
                        init_linechart(LineChart_Voltage, voltagevalueFormatter, "Voltage",2000f);
                        getVoltage(VoltageEntries2, dianyaarry2, LineChart_Voltage2, "Voltage2", "#DDA0DD",2);
                        init_linechart(LineChart_Voltage2, voltagevalueFormatter, "Voltage2",2000f);
                        getCurrent();
                        init_linechart(LineChart_Current, currentValueFormatter);
                        getSpeed();
                        init_linechart(LineChart_Speed, speedValueFormatter, "Speed",1000f);
*/

                    }
                });
            }
        }).start();
    }

    private void setlimitline(YAxis yAxis, String label) {
        network.getallthreshold(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        AllthresholdDatas allthresholdDatas1 = JSON.parseObject(string, AllthresholdDatas.class);
                        double limitline = 0.0;
                        switch (label) {
                            case "TemperatureUp":
                                limitline = allthresholdDatas1.getWfazhigao();
                                break;
                            case "TemperatureDown":
                                limitline = allthresholdDatas1.getWfazhidi();
                                break;
                            case "IlluminationUp":
                                limitline = allthresholdDatas1.getGfazhigao();
                                break;
                            case "IlluminationDown":
                                limitline = allthresholdDatas1.getGfazhidi();
                                break;
                            case "VoltageUp":
                                limitline = allthresholdDatas1.getDfazhigao();
                                break;
                            case "VoltageDown":
                                limitline = allthresholdDatas1.getDfazhidi();
                                break;
                            case "SpeedUp":
                                limitline = allthresholdDatas1.getZfazhigao();
                                break;
                            case "SpeedDown":
                                limitline = allthresholdDatas1.getZfazhidi();
                                break;
                            case "TemperatureUp2":
                                limitline = allthresholdDatas1.getWfazhigao2();
                                break;
                            case "TemperatureDown2":
                                limitline = allthresholdDatas1.getWfazhidi2();
                                break;
                            case "IlluminationUp2":
                                limitline = allthresholdDatas1.getGfazhigao2();
                                break;
                            case "IlluminationDown2":
                                limitline = allthresholdDatas1.getGfazhidi2();
                                break;
                            case "VoltageUp2":
                                limitline = allthresholdDatas1.getDfazhigao2();
                                break;
                            case "VoltageDown2":
                                limitline = allthresholdDatas1.getDfazhidi2();
                                break;
                            case "SpeedUp2":
                                limitline = allthresholdDatas1.getZfazhigao2();
                                break;
                            case "SpeedDown2":
                                limitline = allthresholdDatas1.getZfazhidi2();
                        }
                        LimitLine line = new LimitLine((float) limitline, label);
                        //限制线的宽度
                        line.setLineWidth(3f);
                        //限制线的颜色
                        line.setLineColor(ContextCompat.getColor(Line_chart2_Activity.this, android.R.color.holo_red_light));
                        line.enableDashedLine(5f, 5f, 0f);//虚线
                        line.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);//设置标签显示的位置
                        //限制线的文本颜色
                        line.setTextColor(ContextCompat.getColor(Line_chart2_Activity.this, android.R.color.background_dark));
                        //限制线的文本宽度
                        line.setTextSize(10f);
                        //添加限制线
                        yAxis.addLimitLine(line);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                judgement(t);
            }
        });

    }

    private void getTemperature(List<Entry> list, String[] arry, LineChart lineChart, String label, String color, int number) {
        network.getallwendu(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<TemperatureDatas> temperatureDatas1 = JSONArray.parseArray(string, TemperatureDatas.class);
                        int size = temperatureDatas1.size();
                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculate_Temperature(size, temperatureDatas1, arry, number);
                        caculateDatas.Setdatas(list, arry, lineChart, label, color);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                judgement(t);
            }
        });
    }

    private void getIllumination() {
        network.getallguangzhao(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<IlluminationDatas> IlluminationDatas = JSONArray.parseArray(string, IlluminationDatas.class);
                        int size = IlluminationDatas.size();
                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculata_Illumination(size, IlluminationDatas, guangzhaoarry, 0);
                        caculateDatas.Setdatas(IlluminationEntries, guangzhaoarry, LineChart_Illumination, "Illumination", "#DDA0DD");
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

    private void getVoltage(List<Entry> list, String[] arry, LineChart lineChart, String label, String color, int number) {
        network.getalldianya(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<VoltageDatas> voltageDatas = JSONArray.parseArray(string, VoltageDatas.class);
                        int size = voltageDatas.size();

                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculata_voltage(size, voltageDatas, arry, number);
                        caculateDatas.Setdatas(list, arry, lineChart, label, color);
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

    private void getCurrent() {
        network.getalldianliu(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<CurrentDatas> currentDatas = JSONArray.parseArray(string, CurrentDatas.class);
                        int size = currentDatas.size();

                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculate_current(size, currentDatas, dianliuarry, 0);
                        caculateDatas.Setdatas(CurrentEntries, dianliuarry, LineChart_Current, "Current", "#7fff00");
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

    private void getSpeed() {
        network.getallzhuasu(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<SPeedDatas> sPeedDatas = JSONArray.parseArray(string, SPeedDatas.class);
                        int size = sPeedDatas.size();

                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculate_speed(size, sPeedDatas, zhuansuarry, 1);
                        caculateDatas.Setdatas(SpeedEntries, zhuansuarry, LineChart_Speed, "Speed", "#4169e1");
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
    private void getSpeed(List<Entry> list, String[] arry, LineChart lineChart, String label, String color) {
        network.getallzhuasu(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<SPeedDatas> sPeedDatas = JSONArray.parseArray(string, SPeedDatas.class);
                        int size = sPeedDatas.size();
                        Log.e("TAG", "size" + size);
                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculate_speed(size, sPeedDatas, arry, 1);
                        caculateDatas.Setdatas(list, arry, lineChart, label, color);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从menu_overflow.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void judgement(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void Invalidate_chart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "数据查询中，请稍后", Toast.LENGTH_SHORT).show();
                        updatachart();
                    /*    LineChart_Temperature.invalidate();
                        LineChart_Temperature.animateXY(100,100);
                        LineChart_Temperature2.invalidate();
                        LineChart_Temperature2.animateXY(100,100);
                        LineChart_Illumination.invalidate();
                        LineChart_Illumination.animateXY(100,100);
                        LineChart_Voltage.invalidate();
                        LineChart_Voltage.animateXY(100,100);
                        LineChart_Voltage2.invalidate();
                        LineChart_Voltage2.animateXY(100,100);
                        LineChart_Current.invalidate();
                        LineChart_Current.animateXY(100,100);*/
                        LineChart_Speed.invalidate();
                        LineChart_Speed.animateXY(100, 100);
                    }
                });
            }
        }).start();

    }

    private void updatachart() {
        // 创建第一个异步线程
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 执行异步操作1
                // ...
                /*getTemperature(TemperaturebarEntries, wenduarry, LineChart_Temperature, "TemperatureDown", "#e6e6fa",1);
                getTemperature(TemperaturebarEntries2, wenduarry2, LineChart_Temperature2, "TemperatureDown2", "#dc143c",2);
                getIllumination();
                getVoltage(VoltageEntries, dianyaarry, LineChart_Voltage, "VoltageDown","#afeeee",1);
                getVoltage(VoltageEntries2, dianyaarry2, LineChart_Voltage2, "VoltageDown2", "#90ee90",2);
                getCurrent();*/
                getSpeed(SpeedEntries, zhuansuarry, LineChart_Speed, "Speed", "#ff8c00");
                getSpeed(SpeedEntries2, zhuansuarry2, LineChart_Speed2, "Speed", "#4169e1");
            }
        });

        // 创建第二个异步线程
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 执行异步操作2
                // ...
                /*init_linechart(LineChart_Temperature, TemperatureValueFormatter, "TemperatureDown",500f);
                init_linechart(LineChart_Temperature2, TemperatureValueFormatter, "TemperatureDown2",500f);
                init_linechart(LineChart_Illumination, IlluminationValueFormatter, "IlluminationDown",1000f);
                init_linechart(LineChart_Voltage, voltagevalueFormatter, "VoltageDown",2000f);
                init_linechart(LineChart_Voltage2, voltagevalueFormatter, "VoltageDown2",2000f);
                init_linechart(LineChart_Current, currentValueFormatter,10f);*/
                init_linechart(LineChart_Speed, speedValueFormatter, 1000f);
                init_linechart(LineChart_Speed2, speedValueFormatter, 1000f);
            }
        });

        // 启动异步线程
        thread1.start();
        thread2.start();
    }

    private void checkID() {
        final String ID = et_input_ID.getText().toString();
        if (ID.isEmpty()) {
            Toast.makeText(getApplicationContext(), "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else {
            updata();
        }

    }

}
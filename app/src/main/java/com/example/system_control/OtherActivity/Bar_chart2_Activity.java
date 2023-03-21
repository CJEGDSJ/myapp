package com.example.system_control.OtherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
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

import com.alibaba.fastjson.JSONArray;
import com.example.system_control.OtherActivity.Fomatter.CurrentValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.LightValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.Myformatter;
import com.example.system_control.OtherActivity.Fomatter.SpeedValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.TValueFormatter;
import com.example.system_control.OtherActivity.Fomatter.VoltagevalueFormatter;
import com.example.system_control.R;
import com.example.system_control.datas.CurrentDatas;
import com.example.system_control.datas.IlluminationDatas;
import com.example.system_control.datas.SPeedDatas;
import com.example.system_control.datas.TemperatureDatas;
import com.example.system_control.datas.VoltageDatas;
import com.example.system_control.util.CaculateDatas;
import com.example.system_control.util.Network;
import com.example.system_control.view.XYMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
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

public class Bar_chart2_Activity extends AppCompatActivity implements OnChartValueSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private BarChart barChart_Temperature;
    private BarChart barChart_Temperature2;
    private BarChart barChart_Illumination;
    private BarChart barChart_Speed;
    private BarChart barChart_Speed2;
    private BarChart barChart_Voltage;
    private BarChart barChart_Voltage2;
    private BarChart barChart_Current;

    private String[] wenduarry = new String[50];
    private String[] wenduarry2 = new String[50];
    private EditText et_input_ID2;
    private String[] guangzhaoarry = new String[50];
    private String[] dianyaarry = new String[50];
    private String[] dianyaarry2 = new String[50];
    private String[] dianliuarry = new String[50];
    private String[] zhuansuarry = new String[50];

    private String errormassage = "Internal Server Error";
    private Button btn_update;
    List<BarEntry> TemperaturebarEntries = new ArrayList<>();
    List<BarEntry> TemperaturebarEntries2 = new ArrayList<>();
    List<BarEntry> IlluminationEntries = new ArrayList<>();
    List<BarEntry> SpeedEntries = new ArrayList<>();
    List<BarEntry> VoltageEntries = new ArrayList<>();
    List<BarEntry> VoltageEntries2 = new ArrayList<>();
    List<BarEntry> CurrentEntries = new ArrayList<>();
    private Network network;
    private TValueFormatter TemperatureValueFormatter = new TValueFormatter();
    private LightValueFormatter IlluminationValueFormatter = new LightValueFormatter();
    private SpeedValueFormatter speedValueFormatter = new SpeedValueFormatter();
    private CurrentValueFormatter currentValueFormatter = new CurrentValueFormatter();
    private VoltagevalueFormatter voltagevalueFormatter = new VoltagevalueFormatter();
    private Handler mainHandler, workHandler;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_bar_chart2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        /*network = new Network("http://192.168.100.145:9000/");*/
        network = new Network("http://192.168.0.199:9000/");
        // 创建与主线程关联的Handler
        mainHandler = new Handler();
        init();

    }

    private void init() {
        /*barChart_Temperature = findViewById(R.id.Temperature_BarChart2);
        barChart_Temperature2 = findViewById(R.id.Temperature_BarChart2_2);
        barChart_Illumination = findViewById(R.id.Illumination_BarChart2);*/
        barChart_Speed = findViewById(R.id.Speed_BarChart2);
        barChart_Speed2 = findViewById(R.id.Speed_BarChart2_2);
        /*barChart_Voltage = findViewById(R.id.Voltage_BarChart2);
        barChart_Voltage2 = findViewById(R.id.Voltage_BarChart2_2);
        barChart_Current = findViewById(R.id.Current_BarChart2);*/
        toolbar = findViewById(R.id.bartool2);
        btn_update = findViewById(R.id.btn_InquireID2);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkID();
                updata();
            }
        });
        et_input_ID2 = findViewById(R.id.et_change_ID2);
        selected();
//        updata();
//        newtask();
//        sendmassage();
    }

    private void selected() {
        bottomNavigationView.setSelectedItemId(R.id.Bar_chart);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent_toB = new Intent(getApplicationContext(), DispalyActivity.class);
                        intent_toB.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_toB);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Line_chart:
                        Intent intent_toL = new Intent(getApplicationContext(), Line_chart_Activity.class);
                        intent_toL.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_toL);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Bar_chart:

                        return true;
                    /*case R.id.menu:
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

    private void initBarChart(BarChart barChart, ValueFormatter valueFormatter, float percent) {

        barChart.setOnChartValueSelectedListener(this);

        barChart.getDescription().setEnabled(false);
        //设置最大值条目，超出之后不会有值
        barChart.setMaxVisibleValueCount(60);
        //分别在x轴和y轴上进行缩放
        barChart.setPinchZoom(false);
        //设置剩余统计图的阴影
        barChart.setDrawBarShadow(false);
        //设置网格布局
        barChart.setDrawGridBackground(true);

        Myformatter valueFormatter1 = new Myformatter();
        /*     DayValueFormatter valueFormatter2 = new DayValueFormatter(barChart);*/
        //获取x轴线
        XAxis xAxis = barChart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置网格布局
        xAxis.setDrawGridLines(false);
        //图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setAvoidFirstLastClipping(false);
        //绘制标签  指x轴上的对应数值 默认true
        xAxis.setDrawLabels(true);
        //设置X轴上文本大小
        xAxis.setTextSize(12);
//        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        //缩放后x 轴数据重叠问题
        xAxis.setGranularityEnabled(true);
//        xAxis.setAxisMinimum(0);
        //获取左边y轴的标签
        YAxis axisLeft = barChart.getAxisLeft();
//        axisLeft.addLimitLine(line);
        //设置Y轴数值 从零开始
        axisLeft.setAxisMinimum(0f);
//        axisLeft.setDrawGridLines(true);
        axisLeft.setValueFormatter(valueFormatter);
        axisLeft.setLabelCount(8, false);
        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeft.setSpaceTop(percent);
        barChart.getAxisLeft().setDrawGridLines(true);
        barChart.getAxisRight().setEnabled(false);

        //设置动画时间（X轴和Y轴都设置）
        barChart.animateXY(1000, 1000);

        XYMarkerView mv = new XYMarkerView(this, valueFormatter1);
        mv.setChartView(barChart); // For bounds control
        barChart.setMarker(mv); // Set the marker to the chart
        barChart.getLegend().setEnabled(true);

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
                        /*String time = DateUtil.dateToString(temperatureDatas1.get(0).getCreat_time(), DateUtil.DatePattern.ALL_TIME);
                        Log.e("TAG", "time" + time);*/
                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculate_Temperature(size, temperatureDatas1, wenduarry, 3);
                        caculateDatas.Setdatas(TemperaturebarEntries, wenduarry, barChart_Temperature, "Temperature", "#ADD8E6");

                        /*Log.e("TAG", "onResponse: " + size);*/
                    } catch (IOException e) {
                        /* Log.e("TAG", "message" + e.getMessage());*/
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onResponse: " + t.getMessage());
                t.printStackTrace();
                judgement(t);
            }
        });
    }

    private void getTemperature(List<BarEntry> list, String[] arry, BarChart barChart, String label, String color, int number) {
        network.getallwendu(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {


                    try {
                        String string = response.body().string();
                        /*if (string == "[]") {
                            Toast.makeText(Bar_chart_Activity.this, "数据库中无数据，请添加后查询", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.e("body", "body   " + string);*/
                        List<TemperatureDatas> temperatureDatas1 = JSONArray.parseArray(string, TemperatureDatas.class);
                        int size = temperatureDatas1.size();
                        /*String time = DateUtil.dateToString(temperatureDatas1.get(0).getCreat_time(), DateUtil.DatePattern.ALL_TIME);
                        Log.e("TAG", "time" + time);*/
                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculate_Temperature(size, temperatureDatas1, arry, number);
                        caculateDatas.Setdatas(list, arry, barChart, label, color);

                        /*Log.e("TAG", "onResponse: " + size);*/
                    } catch (IOException e) {
                        /* Log.e("TAG", "message" + e.getMessage());*/
                        e.printStackTrace();

                    }


                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onResponse: " + t.getMessage());
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
                        caculateDatas.Setdatas(IlluminationEntries, guangzhaoarry, barChart_Illumination, "Illumination", "#FFD700");
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

    private void getVoltage() {
        network.getalldianya(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        List<VoltageDatas> voltageDatas = JSONArray.parseArray(string, VoltageDatas.class);
                        int size = voltageDatas.size();

                        CaculateDatas caculateDatas = new CaculateDatas();
                        caculateDatas.Caculata_voltage(size, voltageDatas, dianyaarry, 3);
                        caculateDatas.Setdatas(VoltageEntries, dianyaarry, barChart_Voltage, "Voltage", "#ffffbb33");
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

    private void getVoltage(List<BarEntry> list, String[] arry, BarChart barChart, String label, String color, int number) {
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
                        caculateDatas.Setdatas(list, arry, barChart, label, color);
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
                        caculateDatas.Setdatas(CurrentEntries, dianliuarry, barChart_Current, "Current", "#ff99cc00");
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
                        caculateDatas.Caculate_speed(size, sPeedDatas, zhuansuarry, 0);
                        caculateDatas.Setdatas(SpeedEntries, zhuansuarry, barChart_Speed, "Speed", "#ffff4444");
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
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    private void newtask() {
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        try { //延时操作
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // 更新 UI
                                /*getTemperature();
                                initBarChart(barChart_Temperature, TemperatureValueFormatter,200f);
                                getIllumination();
                                initBarChart(barChart_Illumination, IlluminationValueFormatter,1000f);
                                getVoltage();
                                initBarChart(barChart_Voltage, voltagevalueFormatter,300f);
                                getCurrent();
                                initBarChart(barChart_Current, currentValueFormatter,300f);
                                getSpeed();*/
                                initBarChart(barChart_Speed, speedValueFormatter, 3000f);
                            }

                        });

                }
            }

        };
        workHandler.sendEmptyMessage(0);
        workHandler.sendEmptyMessage(1);
        // 关闭 HandlerThread
        handlerThread.quitSafely();
        // 等待 HandlerThread 关闭完成
        workHandler.removeCallbacks(null); // 防止Handler内存泄露 清空消息队列
        try {
            handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void sendmassage() {
        Message msg = Message.obtain();
        msg.what = 1;//消息的标识
        msg.obj = "A"; // 消息的存放
        workHandler.sendMessage(msg);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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
                        /*getTemperature();
                        initBarChart(barChart_Temperature, TemperatureValueFormatter);
                        getIllumination();
                        initBarChart(barChart_Illumination, IlluminationValueFormatter);*/
                        Toast.makeText(getApplicationContext(), "数据查询中，请稍后", Toast.LENGTH_SHORT).show();
                       /* getTemperature();
                        getTemperature(TemperaturebarEntries2,wenduarry2,barChart_Temperature2,"Temperature2","#FF03DAC5",0);
                        getIllumination();
                        getVoltage();
                        getVoltage(VoltageEntries2,dianyaarry2,barChart_Voltage2,"Voltage2","#9370DB",0);
                        getCurrent();*/
                        getSpeed();
                        /*initBarChart(barChart_Temperature, TemperatureValueFormatter, 200f);
                        initBarChart(barChart_Temperature2, TemperatureValueFormatter, 200f);
                        initBarChart(barChart_Illumination, IlluminationValueFormatter, 1000f);
                        initBarChart(barChart_Voltage, voltagevalueFormatter, 300f);
                        initBarChart(barChart_Voltage2, voltagevalueFormatter, 300f);
                        initBarChart(barChart_Current, currentValueFormatter, 300f);*/
                        initBarChart(barChart_Speed, speedValueFormatter, 3000f);
                    }
                });
            }
        }).start();

    }

    private void judgement(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkID() {
        final String ID = et_input_ID2.getText().toString();
        if (ID.isEmpty()) {
            Toast.makeText(getApplicationContext(), "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
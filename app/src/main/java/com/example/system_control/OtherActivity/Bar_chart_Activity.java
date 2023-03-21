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
import android.view.WindowManager;
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
import com.github.mikephil.charting.utils.MPPointF;
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

public class Bar_chart_Activity extends AppCompatActivity implements OnChartValueSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private BarChart barChart_Temperature;
    private BarChart barChart_Temperature2;
    private BarChart barChart_Illumination;
    private BarChart barChart_Speed;
    private BarChart barChart_Voltage;
    private BarChart barChart_Voltage2;
    private BarChart barChart_Current;

    private String[] wenduarry = new String[50];
    private String[] wenduarry2 = new String[50];

    private String[] guangzhaoarry = new String[50];
    private String[] dianyaarry = new String[50];
    private String[] dianyaarry2 = new String[50];
    private String[] dianliuarry = new String[50];
    private String[] zhuansuarry = new String[50];

    private String errormassage = "Internal Server Error";
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
        setContentView(R.layout.activity_bar_chart);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        /*network = new Network("http://192.168.100.145:9000/");*/
        network = new Network("http://192.168.0.199:9000/");
        // 创建与主线程关联的Handler
        mainHandler = new Handler();
        init();
//        forceShowActionBarOverflowMenu();

    }

    private void init() {
        /*barChart_Temperature = findViewById(R.id.Temperature_BarChart);
        barChart_Temperature2 = findViewById(R.id.Temperature_BarChart2);
        barChart_Illumination = findViewById(R.id.Illumination_BarChart);*/
        barChart_Speed = findViewById(R.id.Speed_BarChart);
        /*barChart_Voltage = findViewById(R.id.Voltage_BarChart);
        barChart_Voltage2 = findViewById(R.id.Voltage_BarChart2);
        barChart_Current = findViewById(R.id.Current_BarChart);*/
        toolbar = findViewById(R.id.bartool);

        selected();
        updata();
/*        newtask();
        sendmassage();*/

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
                        caculateDatas.Caculate_Temperature(size, temperatureDatas1, wenduarry, 1);
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
                        caculateDatas.Caculata_Illumination(size, IlluminationDatas, guangzhaoarry, 2);
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
                        caculateDatas.Caculata_voltage(size, voltageDatas, dianyaarry, 1);
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
                        caculateDatas.Caculate_current(size, currentDatas, dianliuarry, 2);
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
                        caculateDatas.Caculate_speed(size, sPeedDatas, zhuansuarry, 1);
                        caculateDatas.Setdatas(SpeedEntries, zhuansuarry, barChart_Speed, "Speed", "#ffff4444");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(Bar_chart_Activity.this, message, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_refresh:
                        updata();
                        Toast.makeText(Bar_chart_Activity.this, "更新成功", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_query_by_CardID:
                        Intent equipment2 = new Intent(getApplicationContext(), Bar_chart2_Activity.class);
                        equipment2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(equipment2);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
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
                        /*Toast.makeText(getApplicationContext(), "数据查询中，请稍后", Toast.LENGTH_SHORT).show();
                        getTemperature();
                        getTemperature(TemperaturebarEntries2, wenduarry2, barChart_Temperature2, "Temperature2", "#FF03DAC5", 2);
                        getIllumination();
                        getVoltage();
                        getVoltage(VoltageEntries2, dianyaarry2, barChart_Voltage2, "Voltage2", "#9370DB", 2);
                        getCurrent();*/
                        getSpeed();
                       /* initBarChart(barChart_Temperature, TemperatureValueFormatter, 200f);
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        MPPointF position = barChart_Temperature.getPosition(e, YAxis.AxisDependency.LEFT);
        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

/*    private void setBarTemperature(List<BarEntry> list, String[] wenduarry, BarChart barChart_Temperature) {
        int length = 20;
        for (int index = 0; index < length; index++) {
            String s = wenduarry[index];
            Log.e("", "error" + "  " + s + "  " + index);
            if (s.equals("null")) {
                BarEntry barEntry = new BarEntry(index, 0);
                list.add(barEntry);
                continue;
            }
            Float[] ints = new Float[wenduarry.length];
            ints[index] = Float.parseFloat(s);
            BarEntry barEntry = new BarEntry(index, ints[index]);
            list.add(barEntry);

            BarDataSet set1 = new BarDataSet(list, "Temperature");
            if (barChart_Temperature.getData() != null &&
                    barChart_Temperature.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) barChart_Temperature.getData().getDataSetByIndex(0);
                set1.setValues(list);
                set1.setDrawIcons(false);
                barChart_Temperature.getData().notifyDataChanged();
                barChart_Temperature.notifyDataSetChanged();
            } else {

//                set1.setColor(100);
//                set1.setColor(Color.parseColor("#FF0000"));
                set1.setColor(Color.parseColor("#008080"));
                set1.setDrawValues(true);
                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                BarData data = new BarData(dataSets);
                data.setBarWidth(0.9f);
                barChart_Temperature.setData(data);
                barChart_Temperature.setVisibleXRangeMaximum(10);
                barChart_Temperature.setFitBars(false);
                barChart_Temperature.invalidate();
            }
            //设置柱形统计  柱上的文本大小
            barChart_Temperature.getData().setValueTextSize(10);
            for (IDataSet set : barChart_Temperature.getData().getDataSets()) {
                set.setDrawValues(!set.isDrawValuesEnabled());
            }
        }
    }*/


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

/*
    private void service() {
        // 创建一个Intent对象，并指定要启动的服务
        Intent intent = new Intent(this, MyIntentService.class);
        // 启动服务
        startService(intent);

    }

    // 创建一个广播接收器
    private BroadcastReceiver resultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 从Intent中获取结果
            String result = intent.getStringExtra("result");

            // 在UI线程中更新UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    updateUI(result);
                }
            });
        }
    };
*/

    // 在onResume()方法中注册广播接收器
/*    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.RESULT_ACTION");
        registerReceiver(resultReceiver, filter);
    }*/
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
                                getTemperature();
                                initBarChart(barChart_Temperature, TemperatureValueFormatter, 5000f);
                                getIllumination();
                                initBarChart(barChart_Illumination, IlluminationValueFormatter, 1000f);
                                getVoltage();
                                initBarChart(barChart_Voltage, voltagevalueFormatter, 5000f);
                                getCurrent();
                                initBarChart(barChart_Current, currentValueFormatter, 300f);
                                getSpeed();
                                initBarChart(barChart_Speed, speedValueFormatter, 5000f);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从menu_overflow.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // 在onPause()方法中注销广播接收器
/*    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(resultReceiver);
    }*/
/*    private void forceShowActionBarOverflowMenu() {

        try {

            ViewConfiguration config = ViewConfiguration.get(this);

            Field menuKeyField = Bar_chart_Activity.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {

                menuKeyField.setAccessible(true);

                menuKeyField.setBoolean(config, false);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }*/
    private void judgement(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatachart() {
        // 创建第一个异步线程
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 执行异步操作1
                // ...
                getTemperature();
                getTemperature(TemperaturebarEntries2, wenduarry2, barChart_Temperature2, "Temperature2", "#FF03DAC5", 2);
                getIllumination();
                getVoltage();
                getVoltage(VoltageEntries2, dianyaarry2, barChart_Voltage2, "Voltage2", "#9370DB", 2);
                getCurrent();
                getSpeed();

            }
        });

        // 创建第二个异步线程
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 执行异步操作2
                // ...
                initBarChart(barChart_Temperature, TemperatureValueFormatter, 200f);
                initBarChart(barChart_Temperature2, TemperatureValueFormatter, 200f);
                initBarChart(barChart_Illumination, IlluminationValueFormatter, 1000f);
                initBarChart(barChart_Voltage, voltagevalueFormatter, 300f);
                initBarChart(barChart_Voltage2, voltagevalueFormatter, 300f);
                initBarChart(barChart_Current, currentValueFormatter, 300f);
                initBarChart(barChart_Speed, speedValueFormatter, 3000f);
//              LineChart_Temperature.postInvalidate();
/*                LineChart_Temperature.notifyDataSetChanged();
                LineChart_Temperature.postInvalidate();
//                LineChart_Temperature0.postInvalidate();
                LineChart_Temperature0.notifyDataSetChanged();
                LineChart_Temperature0.postInvalidate();
//                LineChart_Illumination.postInvalidate();
                LineChart_Illumination.notifyDataSetChanged();
                LineChart_Illumination.postInvalidate();
//                LineChart_Voltage.postInvalidate();
                LineChart_Voltage.notifyDataSetChanged();
                LineChart_Voltage.postInvalidate();
//                LineChart_Voltage0.postInvalidate();
                LineChart_Voltage0.notifyDataSetChanged();
                LineChart_Voltage0.postInvalidate();
//                LineChart_Current.postInvalidate();
                LineChart_Current.notifyDataSetChanged();
                LineChart_Current.postInvalidate();
//                LineChart_Speed.postInvalidate();
                LineChart_Speed.notifyDataSetChanged();
                LineChart_Speed.postInvalidate();*/
            }
        });

        // 启动异步线程
        thread1.start();
        thread2.start();
    }
}
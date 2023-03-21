package com.example.system_control.OtherActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.system_control.R;
import com.example.system_control.datas.StateDatas;
import com.example.system_control.util.AllNewDatas2;
import com.example.system_control.util.Network;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispalyActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView tv_showTemperature;//温度
    private TextView tv_showTemperature0;//温度
    private TextView tv_difference;//温差
    private TextView tv_showIllumination;//光照
    private TextView tv_showHumidity;//湿度
    private TextView tv_showSpeed;//速度
    private TextView tv_showLongitude;//经度
    private TextView tv_showLatitude;//纬度
    private TextView tv_showVoltage;//电压
    private TextView tv_showVoltage0;//电压
    private TextView tv_showCurrent;//电流
    private TextView tv_showElevation;//海拔
    private TextView tv_ID;//ID
    private CheckBox checkBox_S;//开关
    private TextView tv_showTemperature_2;//温度
    private TextView tv_showTemperature_0;//温度
    private TextView tv_difference2;//温差
    private TextView tv_showIllumination_2;//光照
    private TextView tv_showHumidity_2;//湿度
    private TextView tv_showSpeed_2;//速度
    private TextView tv_showLongitude_2;//经度
    private TextView tv_showLatitude_2;//纬度
    private TextView tv_showVoltage_2;//电压
    private TextView tv_showVoltage_0;//电压
    private TextView tv_showCurrent_2;//电流
    private TextView tv_showElevation_2;//海拔
    private TextView tv_ID2;//ID
    private TextView showCardID;//CardID
    private TextView showCardID2;//CardID2
    private TextView show_shuiyuan;
    private TextView show_shuiyuan2;
    private TextView show_shuizhi;
    private TextView show_shuizhi2;
    private TextView shwoLocation;
    private TextView shwoLocation2;
    private TextView show_Warningmsg;
    private TextView show_Warningmsg2;
    private CheckBox checkBox_S2;//开关
    private Button btn_latestdatas;
    private Network network;
    private Handler handler;
    private HandlerThread handlerThread;
    private Thread newThread;
    private BottomNavigationView bottomNavigationView;
    private String error = "INTERNAL SERVER ERROR";
    private int elec;
    private int fan;
    private int light;
    private int twofan;
    private StateDatas stateDatas;
    private UpdateTask mUpdateTask;
    private boolean isonline = false;
    private AllNewDatas2 allNewDatas2;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispaly);
  /*      Bundle bundle = getIntent().getExtras();
        String IP = bundle.getString("IP");//读出数据*/
        /*network = new Network("http://192.168.100.145:9000/");*/
        network = new Network("http://192.168.0.199:9000/");
        /*network = new Network("http://192.168.0.197:9000/");*/
        /*    network = new Network("http://192.168.1.111:9000/")                                               ;*/
        init();
//        updata();
        mUpdateTask = new UpdateTask();
//        mUpdateTask.execute();
    }

    void init() {
        toolbar = findViewById(R.id.tback);
        toolbar.setOnClickListener(this);
        showCardID = findViewById(R.id.tv_showCardID);
        showCardID2 = findViewById(R.id.tv_showCardID2);
        show_shuiyuan = findViewById(R.id.tv_showshuiyuan);
        show_shuiyuan2= findViewById(R.id.tv_showshuiyuan2);
        show_shuizhi = findViewById(R.id.tv_showshuizhi);
        show_shuizhi2 = findViewById(R.id.tv_showshuizhi2);
        show_Warningmsg = findViewById(R.id.tv_Warningmsg);
        show_Warningmsg2 = findViewById(R.id.tv_Warningmsg2);
        shwoLocation = findViewById(R.id.shwoLocation);
        shwoLocation2 = findViewById(R.id.shwoLocation2);
 /*       tv_showTemperature = findViewById(R.id.tv_showTemperature);
        tv_showTemperature0 = findViewById(R.id.tv_showTemperature0);
        tv_difference = findViewById(R.id.difference);
        tv_showIllumination = findViewById(R.id.tv_showIllumination);
        tv_showHumidity = findViewById(R.id.tv_showHumidity);
        tv_showLongitude = findViewById(R.id.tv_showLongitude);
        tv_showLatitude = findViewById(R.id.tv_showLatitude);
        tv_showVoltage = findViewById(R.id.tv_showVoltage);
        tv_showVoltage0 = findViewById(R.id.tv_showVoltage0);
        tv_showCurrent = findViewById(R.id.tv_showCurrent);
        tv_showElevation = findViewById(R.id.tv_showEvaluation);
        tv_showSpeed = findViewById(R.id.tv_showSpeed);*/
        checkBox_S = findViewById(R.id.Ck_switch);
        tv_ID = findViewById(R.id.tv_showID);
/*        tv_showTemperature_2 = findViewById(R.id.tv_showTemperature_2);
        tv_showTemperature_0 = findViewById(R.id.tv_showTemperature_0);
        tv_difference2 = findViewById(R.id.difference2);
        tv_showIllumination_2 = findViewById(R.id.tv_showIllumination_2);
        tv_showHumidity_2 = findViewById(R.id.tv_showHumidity_2);
        tv_showLongitude_2 = findViewById(R.id.tv_showLongitude_2);
        tv_showLatitude_2 = findViewById(R.id.tv_showLatitude_2);
        tv_showVoltage_2 = findViewById(R.id.tv_showVoltage_2);
        tv_showVoltage_0 = findViewById(R.id.tv_showVoltage_0);
        tv_showCurrent_2 = findViewById(R.id.tv_showCurrent_2);
        tv_showElevation_2 = findViewById(R.id.tv_showEvaluation_2);
        tv_showSpeed_2 = findViewById(R.id.tv_showSpeed_2);*/
        checkBox_S2 = findViewById(R.id.Ck_switch_2);
        tv_ID2 = findViewById(R.id.tv_showID_2);
        btn_latestdatas = findViewById(R.id.btn_Inquire);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btn_latestdatas.setOnClickListener(this);
        selected();
        execute();
        checkstate();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tback:
                finish();
                break;
            case R.id.btn_Inquire:
                updata();
                Toast.makeText(getApplicationContext(), "查询成功", Toast.LENGTH_SHORT).show();

//                Query_latest_data();
        }
    }

    private void Query_latest_data() {
        network.getnewdatas(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.code() != 500) {

                    try {
                        String string = response.body().string();
                        /*Log.e("TAG", "string" + string);*/
                        AllNewDatas2 allNewDatas2 = JSON.parseObject(string, AllNewDatas2.class);
                        String id1 = allNewDatas2.getID1();
                        String id2 = allNewDatas2.getID2();
                        String dian = allNewDatas2.getDian();
                        String dian2 = allNewDatas2.getDian2();
                        float v = Float.parseFloat(dian);
                        float v1 = Float.parseFloat(dian2);
                        String TDS1 = 110 * ((5 - v) / v) + "ppm";
                        String TDS2 = 110 * ((5 - v1) / v1) + "ppm";
                        String dianya = allNewDatas2.getDianya();
                        String dianya2 = allNewDatas2.getDianya2();
                        float v2 = Float.parseFloat(dianya);
                        float v3 = Float.parseFloat(dianya2);
                        String TDS3 = 110 * ((5 - v2) / v) + "ppm";
                        String TDS4 = 110 * ((5 - v3) / v1) + "ppm";
                        String rfid1 = allNewDatas2.getRFID1();
                        String rfid2 = allNewDatas2.getRFID2();
                        String weizhi = allNewDatas2.getWeizhi();
                        String weizhi2 = allNewDatas2.getWeizhi2();

                        /*String s = DateUtil.dateToString(creat_time, DateUtil.DatePattern.ALL_TIME);
                        if (s.equals(date)) {
                            isonline = true;
                        }*/
                        /*Log.e("TAG", "isonline" + isonline);*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_ID.setText(id1);
                                tv_ID2.setText(id2);
                                showCardID.setText(rfid1);
                                showCardID2.setText(rfid2);
                                show_shuiyuan.setText(TDS1);
                                show_shuiyuan2.setText(TDS2);
                                show_shuizhi.setText(TDS3);
                                show_shuizhi2.setText(TDS4);
                                shwoLocation.setText(weizhi);
                                shwoLocation2.setText(weizhi2);

                            }
                        });
  /*                      if (id1.isEmpty() && id2.isEmpty()) {
                            Toast.makeText(DispalyActivity.this, "设备未上线", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (id1.isEmpty() && !id2.isEmpty()) {
                            Toast.makeText(DispalyActivity.this, "设备一未上线", Toast.LENGTH_SHORT).show();
                            tv_ID2.setText(id2);
                            tv_showTemperature_2.setText(wendu2 + "℃");
                            tv_showTemperature_0.setText(wen2 + "℃");
                            tv_difference2.setText(difference2 + "℃");
                            tv_showIllumination_2.setText(guangzhao2 + "lux");
                            tv_showLatitude_2.setText(weidu2);
                            tv_showLongitude_2.setText(jingdu2);
                            tv_showVoltage_2.setText(dianya2 + "V");
                            tv_showVoltage_0.setText(dian2 + "V");
                            tv_showCurrent_2.setText(dianliu2 + "A");
                            tv_showSpeed_2.setText(zhuansu2 + "r/s");
                            tv_showElevation_2.setText(haiba2 + "m");
                            Toast.makeText(DispalyActivity.this, "设备二数据查询成功", Toast.LENGTH_SHORT).show();
                        } else if (!id1.isEmpty() && id2.isEmpty()) {
                            Toast.makeText(DispalyActivity.this, "设备二未上线", Toast.LENGTH_SHORT).show();
                            tv_ID.setText(id1);
                            tv_showTemperature.setText(wendu + "℃");
                            tv_showTemperature0.setText(wen + "℃");
                            tv_difference.setText(difference + "℃");
                            tv_showIllumination.setText(guangzhao + "lux");
                            tv_showLatitude.setText(weidu);
                            tv_showLongitude.setText(jingdu);
                            tv_showVoltage.setText(dianya + "V");
                            tv_showVoltage0.setText(dian + "V");
                            tv_showCurrent.setText(dianliu + "A");
                            tv_showSpeed.setText(zhuansu + "r/s");
                            tv_showElevation.setText(haiba + "m");
                            Toast.makeText(DispalyActivity.this, "设备一数据查询成功", Toast.LENGTH_SHORT).show();
                        }else {*/
        /*                    tv_ID.setText(id1);
                            tv_showTemperature.setText(wendu + "℃");
                            tv_showTemperature0.setText(wen + "℃");
                            tv_difference.setText(difference + "℃");
                            tv_showIllumination.setText(guangzhao + "lux");
                            tv_showLatitude.setText(weidu);
                            tv_showLongitude.setText(jingdu);
                            tv_showVoltage.setText(dianya + "V");
                            tv_showVoltage0.setText(dian + "V");
                            tv_showCurrent.setText(dianliu + "A");
                            tv_showSpeed.setText(zhuansu + "r/s");
                            tv_showElevation.setText(haiba + "m");
                            tv_ID2.setText(id2);
                            tv_showTemperature_2.setText(wendu2 + "℃");
                            tv_showTemperature_0.setText(wen2 + "℃");
                            tv_difference2.setText(difference2 + "℃");
                            tv_showIllumination_2.setText(guangzhao2 + "lux");
                            tv_showLatitude_2.setText(weidu2);
                            tv_showLongitude_2.setText(jingdu2);
                            tv_showVoltage_2.setText(dianya2 + "V");
                            tv_showVoltage_0.setText(dian2 + "V");
                            tv_showCurrent_2.setText(dianliu2 + "A");
                            tv_showSpeed_2.setText(zhuansu2 + "r/s");
                            tv_showElevation_2.setText(haiba2 + "m");*/

//                        }
                        /*date = DateUtil.dateToString(creat_time, DateUtil.DatePattern.ALL_TIME);*/
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(DispalyActivity.this, "数据库中无数据，请添加后查询", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("failure", "failure" + t.getMessage());
                if (t instanceof SocketTimeoutException) {
                    //网络超时异常
                    Toast.makeText(DispalyActivity.this, "服务器请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    //其他异常
                    Toast.makeText(DispalyActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selected() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.Line_chart:
                        Intent intent_toL = new Intent(getApplicationContext(), Line_chart_Activity.class);
                        intent_toL.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_toL);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Bar_chart:
                        Intent intent_toB = new Intent(getApplicationContext(), Bar_chart_Activity.class);
                        intent_toB.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_toB);
                        overridePendingTransition(0, 0);

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
      /*  toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_speedchange:
                        Intent speedchange = new Intent(getApplicationContext(), SpeedchangeActivity.class);
                        speedchange.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(speedchange);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });*/
    }

    private void testmetheod() {

/*        network.getnewdatas(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

*//*                Gson gson = new Gson();
                String s = gson.toJson(call);
                latestdatas = gson.fromJson(s, AllNewDatas.class);
                String dianya1 = latestdatas.getDianya();*//*
         *//*                JsonObject bsqJson = new JsonParser().parse(response.body().getJsonData().toString()).getAsJsonObject();
                AllNewDatas bsqModel = new Gson().fromJson(bsqJson, AllNewDatas.class);*//*
                try {
                    String s = response.body().string();
                    *//*JSONObject j1 = new JSONObject(s);*//*
         *//*          Gson gson = new Gson();
                    latestdatas = gson.fromJson(s, AllNewDatas.class);
                    String dianya1 = latestdatas.getDianya();
                    Log.e("TAG", "onResponse: " + dianya1);*//*
//                    latestdatas = gson.fromJson(s, AllNewDatas.class)
                    AllNewDatas allNewDatas = JSON.parseObject(s, AllNewDatas.class);
                    String dianya = allNewDatas.getDianya();
                    Log.e("TAG", "onResponse: " + dianya);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });*/
/*        network.latestdata(new Callback<AllNewDatas>() {
            @Override
            public void onResponse(Call<AllNewDatas> call, Response<AllNewDatas> response) {
                String s = response.body().toString();
                AllNewDatas allNewDatas = JSON.parseObject(response.body().toString(), AllNewDatas.class);
                String dianliu = allNewDatas.getDianliu();
                *//*tv_showCurrent.setText(dianliu + "mA");*//*
                Log.e("TAG", s);
 *//*               if (response.isSuccessful()) {
                    //请求成功
                    String s = response.body().toString();
                    AllNewDatas allNewDatas = JSON.parseObject(response.body().toString(), AllNewDatas.class);
                    String dianliu = allNewDatas.getDianliu();
                    *//**//*tv_showCurrent.setText(dianliu + "mA");*//**//*
                    Log.e("TAG", s);
                    //...
                } else {
                    //请求失败
                }*//*
            }

            @Override
            public void onFailure(Call<AllNewDatas> call, Throwable t) {

            }
        });*/
    }

    private void updata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*testmetheod();*/
                        Query_latest_data();

                    }
                });
            }
        }).start();

    }

    private void execute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    switchs();
                    switchs2();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void switchs() {
        checkBox_S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.e("TAG", "onCheckedChanged: " + isChecked);
                /* Log.e("TAG", "s_state2: "+ s_state(fan) );*/
                if (isChecked) {
                    checkBox_S.setBackground(getDrawable(R.drawable.switch_on1));
                    fan_on();
                } else {
                    checkBox_S.setBackground(getDrawable(R.drawable.switch_off2));
                    fan_off();
                }
            }
        });
    }

    private void fan_on() {
        network.fan_on(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        Log.e("TAG", "result  " + string);
                        JSONObject jsonObject = JSON.parseObject(string);
                        String result = jsonObject.getString("result");
                        if (result.equals("1")) {
                            Toast.makeText(DispalyActivity.this, "开启成功", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(DispalyActivity.this, "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DispalyActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fan_off() {
        network.fan_off(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        Log.e("TAG", "off" + string);
                        JSONObject jsonObject = JSON.parseObject(string);
                        String result = jsonObject.getString("result");
                        if (result.equals("0")) {
                            Toast.makeText(DispalyActivity.this, "关闭成功", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(DispalyActivity.this, "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DispalyActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void switchs2() {
        checkBox_S2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox_S2.setBackground(getDrawable(R.drawable.switch_on1));
                    fan_on_2();
                } else {
                    checkBox_S2.setBackground(getDrawable(R.drawable.switch_off2));
                    fan_off_2();
                }
            }
        });
    }

    private void fan_off_2() {
        network.twofan_off(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        Log.e("TAG", "result  " + string);
                        JSONObject jsonObject = JSON.parseObject(string);
                        String result = jsonObject.getString("result");
                        if (result.equals("0")) {
                            Toast.makeText(DispalyActivity.this, "关闭成功", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(DispalyActivity.this, "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DispalyActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                }
                t.printStackTrace();
            }
        });
    }

    private void fan_on_2() {
        network.twofan_on(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        Log.e("TAG", "result  " + string);
                        JSONObject jsonObject = JSON.parseObject(string);
                        String result = jsonObject.getString("result");
                        if (result.equals("1")) {
                            Toast.makeText(DispalyActivity.this, "开启成功", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(DispalyActivity.this, "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DispalyActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                }
                t.printStackTrace();
            }
        });
    }

    private Handler handlerSend = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            // 重写handleMessage方法
            super.handleMessage(msg);
            if (msg.what == 1) {
                updata();
                Toast.makeText(getApplicationContext(), "页面刷新成功！", Toast.LENGTH_SHORT).show();
                // 再次使用handler发送信息
                handlerSend.sendEmptyMessageDelayed(1, 5000);
            }
        }
    };

    private void refresh() {
        newThread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        newThread.start();
    }

    private void checkstate() {
        network.getallstat(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        stateDatas = JSON.parseObject(string, StateDatas.class);
                        elec = Integer.parseInt(stateDatas.getElec());
                        fan = Integer.parseInt(stateDatas.getFan());
                        light = Integer.parseInt(stateDatas.getLight());
                        twofan = Integer.parseInt(stateDatas.getTwofan());
                        s_state(fan);
                        s2_state(twofan);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(DispalyActivity.this, "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DispalyActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean s_state(int t) {
        if (t == 1) {
            Log.e("TAG", "on");
            checkBox_S.setBackground(getDrawable(R.drawable.switch_on1));
            checkBox_S.setChecked(true);
            return true;
        } else if (t == 0) {
            Log.e("TAG", "off");
            checkBox_S.setBackground(getDrawable(R.drawable.switch_off2));
            /*checkBox_S.setChecked(false);*/
            return false;
        }
        return false;

    }

    private boolean s2_state(int t) {
        if (t == 1) {
            Log.e("TAG", "on");
            checkBox_S2.setBackground(getDrawable(R.drawable.switch_on1));
            checkBox_S2.setChecked(true);
            return true;
        } else if (t == 0) {
            Log.e("TAG", "off");
            checkBox_S2.setBackground(getDrawable(R.drawable.switch_off2));
            /*checkBox_S.setChecked(false);*/
            return false;
        }
        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从menu_overflow.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_overflow_speedchange, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class UpdateTask extends AsyncTask<Void, String, Void> {
        private static final long UPDATE_INTERVAL = 8000; // 更新间隔 5 秒
        private boolean mIsRunning;

        @Override
        protected void onPreExecute() {
            mIsRunning = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (mIsRunning && !isCancelled()) {
                try {
                    String data = fetchDataFromServer(); // 从服务器获取数据
                    publishProgress(data); // 将数据发布到 UI 线程
                    Thread.sleep(UPDATE_INTERVAL); // 休眠一段时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (isonline) {
                updata();// 更新 UI 界面
                Toast.makeText(getApplicationContext(), "自动更新成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "自动更新失败,设备已经掉线", Toast.LENGTH_SHORT).show();
                mUpdateTask.cancel(true);
            }
 /*           do{
                updata();// 更新 UI 界面
                Toast.makeText(getApplicationContext(), "自动更新成功", Toast.LENGTH_SHORT).show();
            }while (isonline);
            Toast.makeText(getApplicationContext(), "自动更新失败,设备已经掉线", Toast.LENGTH_SHORT).show();*/
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mIsRunning = false;
        }

    }

    private String fetchDataFromServer() {
        // 实现从服务器获取数据的代码
        return "实时数据：" + System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mUpdateTask != null) {
            mUpdateTask.cancel(true);
        }
    }


}
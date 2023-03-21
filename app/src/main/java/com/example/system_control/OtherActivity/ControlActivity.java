package com.example.system_control.OtherActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.example.system_control.R;
import com.example.system_control.datas.AllthresholdDatas;
import com.example.system_control.util.Network;
import com.example.system_control.util.ServerInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_change_up;
    private EditText et_change_down;
    private Button btn_change_dianya;
    private Button btn_change_guangzhao;
    private Button btn_change_zhuansu;
    private Button btn_change_wendu;
    private TextView tv_dianya_upthreshold;
    private TextView tv_dianya_downthreshold;
    private TextView tv_guangzhao_upthreshold;
    private TextView tv_guangzhao_downthreshold;
    private TextView tv_zhuansu_upthreshold;
    private TextView tv_zhuansu_downthreshold;
    private TextView tv_wendu_upthreshold;
    private TextView tv_wendu_downthreshold;
    private EditText et_change_up2;
    private EditText et_change_down2;
    private Button btn_change_dianya2;
    private Button btn_change_guangzhao2;
    private Button btn_change_zhuansu2;
    private Button btn_change_wendu2;
    private TextView tv_dianya_upthreshold2;
    private TextView tv_dianya_downthreshold2;
    private TextView tv_guangzhao_upthreshold2;
    private TextView tv_guangzhao_downthreshold2;
    private TextView tv_zhuansu_upthreshold2;
    private TextView tv_zhuansu_downthreshold2;
    private TextView tv_wendu_upthreshold2;
    private TextView tv_wendu_downthreshold2;
    private Network network;
    private ServerInterface serverInterface;
    private Retrofit retrofit;
    private String baseurl;
    private Toolbar toolbar;

//    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_control);
        /*baseurl = "http://192.168.100.145:9000";*/
        baseurl = "http://192.168.0.199:9000";
        network = new Network(baseurl);
        retrofit = new Retrofit.Builder().baseUrl(baseurl).build();
        serverInterface = retrofit.create(ServerInterface.class);
        init();

    }

    private void init() {
        et_change_up = findViewById(R.id.et_change_upthreshold);
        et_change_down = findViewById(R.id.et_change_downthreshold);
        btn_change_dianya = findViewById(R.id.btn_setdianya);
        btn_change_guangzhao = findViewById(R.id.btn_setguangzhao);
        btn_change_zhuansu = findViewById(R.id.btn_setzhuansu);
        btn_change_wendu = findViewById(R.id.btn_setwendu);
        tv_dianya_upthreshold = findViewById(R.id.tv_dianya_upthreshold);
        tv_dianya_downthreshold = findViewById(R.id.tv_dianya_downthreshold);
        tv_guangzhao_upthreshold = findViewById(R.id.tv_guangzhao_upthreshold);
        tv_guangzhao_downthreshold = findViewById(R.id.tv_guangzhao_downthreshold);
        tv_zhuansu_upthreshold = findViewById(R.id.tv_zhuansu_upthreshold);
        tv_zhuansu_downthreshold = findViewById(R.id.tv_zhuansu_downthreshold);
        tv_wendu_upthreshold = findViewById(R.id.tv_wendu_upthreshold);
        tv_wendu_downthreshold = findViewById(R.id.tv_wendu_downthreshold);
        btn_change_dianya.setOnClickListener(this);
        btn_change_wendu.setOnClickListener(this);
        btn_change_zhuansu.setOnClickListener(this);
        btn_change_guangzhao.setOnClickListener(this);
        et_change_up2 = findViewById(R.id.et_change_upthreshold2);
        et_change_down2 = findViewById(R.id.et_change_downthreshold2);
        btn_change_dianya2 = findViewById(R.id.btn_setdianya2);
        btn_change_guangzhao2 = findViewById(R.id.btn_setguangzhao2);
        btn_change_zhuansu2 = findViewById(R.id.btn_setzhuansu2);
        btn_change_wendu2 = findViewById(R.id.btn_setwendu2);
        tv_dianya_upthreshold2 = findViewById(R.id.tv_dianya_upthreshold2);
        tv_dianya_downthreshold2 = findViewById(R.id.tv_dianya_downthreshold2);
        tv_guangzhao_upthreshold2 = findViewById(R.id.tv_guangzhao_upthreshold2);
        tv_guangzhao_downthreshold2 = findViewById(R.id.tv_guangzhao_downthreshold2);
        tv_zhuansu_upthreshold2 = findViewById(R.id.tv_zhuansu_upthreshold2);
        tv_zhuansu_downthreshold2 = findViewById(R.id.tv_zhuansu_downthreshold2);
        tv_wendu_upthreshold2 = findViewById(R.id.tv_wendu_upthreshold2);
        tv_wendu_downthreshold2 = findViewById(R.id.tv_wendu_downthreshold2);
        btn_change_dianya2.setOnClickListener(this);
        btn_change_wendu2.setOnClickListener(this);
        btn_change_zhuansu2.setOnClickListener(this);
        btn_change_guangzhao2.setOnClickListener(this);
//        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.control);
//        selected();
        display();
    }

   /* private void selected() {

        bottomNavigationView.setSelectedItemId(R.id.menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent_tom = new Intent(getApplicationContext(), DispalyActivity.class);
                        intent_tom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_tom);
                        overridePendingTransition(0, 0);
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
                    case R.id.menu:

                        return true;
                }
                return false;
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_refresh1:
                        display();
                        Toast.makeText(ControlActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }
*/
    private void updata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setdianya:
                setBtn_change_dianya();
                /*change_dianya();*/
                break;
            case R.id.btn_setwendu:
                /*change_wendu();*/
                setBtn_change_wendu();
                break;
            case R.id.btn_setguangzhao:
                /*change_guangzhao();*/
                setBtn_change_guangzhao();
                break;
            case R.id.btn_setzhuansu:
                /*change_zhuansu();*/
                setBtn_change_zhuansu();
                break;
            case R.id.btn_setdianya2:
                setBtn_change_dianya2();
                break;
            case R.id.btn_setwendu2:
                setBtn_change_wendu2();
                /*change_wendu();*/
                break;
            case R.id.btn_setguangzhao2:
                /*change_guangzhao();*/
                setBtn_change_guangzhao2();
                break;
            case R.id.btn_setzhuansu2:
                setBtn_change_zhuansu2();
                break;
        }
    }

    private void display() {
        network.getallthreshold(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        AllthresholdDatas allthresholdDatas = JSON.parseObject(string, AllthresholdDatas.class);
                        String wenfazhidi = String.valueOf(allthresholdDatas.getWfazhidi());
                        String wenfazhigao = String.valueOf(allthresholdDatas.getWfazhigao());
                        String guangfazhidi = String.valueOf(allthresholdDatas.getGfazhidi());
                        String guangfazhigao = String.valueOf(allthresholdDatas.getGfazhigao());
                        String dianfazhidi = String.valueOf(allthresholdDatas.getDfazhidi());
                        String dianfazhigao = String.valueOf(allthresholdDatas.getDfazhigao());
                        String zhuanfazhidi = String.valueOf(allthresholdDatas.getZfazhidi());
                        String zhuanfazhigao = String.valueOf(allthresholdDatas.getZfazhigao());
                        String wenfazhidi2 = String.valueOf(allthresholdDatas.getWfazhidi2());
                        String wenfazhigao2 = String.valueOf(allthresholdDatas.getWfazhigao2());
                        String guangfazhidi2 = String.valueOf(allthresholdDatas.getGfazhidi2());
                        String guangfazhigao2 = String.valueOf(allthresholdDatas.getGfazhigao2());
                        String dianfazhidi2 = String.valueOf(allthresholdDatas.getDfazhidi2());
                        String dianfazhigao2 = String.valueOf(allthresholdDatas.getDfazhigao2());
                        String zhuanfazhidi2 = String.valueOf(allthresholdDatas.getZfazhidi2());
                        String zhuanfazhigao2 = String.valueOf(allthresholdDatas.getZfazhigao2());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_dianya_upthreshold.setText(dianfazhigao + "V");
                                tv_dianya_downthreshold.setText(dianfazhidi + "V");
                                tv_guangzhao_upthreshold.setText(guangfazhigao + "lux");
                                tv_guangzhao_downthreshold.setText(guangfazhidi + "lux");
                                tv_zhuansu_upthreshold.setText(zhuanfazhigao + "r/s");
                                tv_zhuansu_downthreshold.setText(zhuanfazhidi + "r/s");
                                tv_wendu_upthreshold.setText(wenfazhigao + "℃");
                                tv_wendu_downthreshold.setText(wenfazhidi + "℃");
                                tv_dianya_upthreshold2.setText(dianfazhigao2 + "V");
                                tv_dianya_downthreshold2.setText(dianfazhidi2 + "V");
                                tv_guangzhao_upthreshold2.setText(guangfazhigao2 + "lux");
                                tv_guangzhao_downthreshold2.setText(guangfazhidi2 + "lux");
                                tv_zhuansu_upthreshold2.setText(zhuanfazhigao2 + "r/s");
                                tv_zhuansu_downthreshold2.setText(zhuanfazhidi2 + "r/s");
                                tv_wendu_upthreshold2.setText(wenfazhigao2 + "℃");
                                tv_wendu_downthreshold2.setText(wenfazhidi2 + "℃");
                            }
                        });
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

    private void setBtn_change_dianya() {
        final String up = et_change_up.getText().toString();
        final String down = et_change_down.getText().toString();

        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changedianya(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changedianyaup(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changedianyadown(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        }


    }

    private void setBtn_change_dianya2() {
        final String up = et_change_up2.getText().toString();
        final String down = et_change_down2.getText().toString();
        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changedianya2(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changedianyaup2(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changedianyadown2(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }

            });
        }


    }

    private void setBtn_change_guangzhao() {
        final String up = et_change_up.getText().toString();
        final String down = et_change_down.getText().toString();
        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changeguangzhao(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changeguangzhaoup(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changeguangzhaodown(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        }
    }

    private void setBtn_change_guangzhao2() {
        final String up = et_change_up2.getText().toString();
        final String down = et_change_down2.getText().toString();
        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changeguangzhao2(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changeguangzhaoup2(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changeguangzhaodown2(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        }
    }

    private void setBtn_change_wendu() {
        final String up = et_change_up.getText().toString();
        final String down = et_change_down.getText().toString();
        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changewendu(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changewenduup(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changewendudown(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        }
    }

    private void setBtn_change_wendu2() {
        final String up = et_change_up2.getText().toString();
        final String down = et_change_down2.getText().toString();
        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changewendu2(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changewenduup2(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changewendudown2(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        }
    }

    private void setBtn_change_zhuansu() {
        final String up = et_change_up.getText().toString();
        final String down = et_change_down.getText().toString();
        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changezhuansu(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changezhuansuup(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changezhuansudown(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    judgement(t);
                    t.printStackTrace();
                }
            });
        }
    }

    private void setBtn_change_zhuansu2() {
        final String up = et_change_up2.getText().toString();
        final String down = et_change_down2.getText().toString();
        if (up.isEmpty() && down.isEmpty()) {
            Toast.makeText(ControlActivity.this, "输入的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (Float.parseFloat(up) < Float.parseFloat(down)) {
                Toast.makeText(ControlActivity.this, "高阈值不能低于低阈值", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if (!up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changezhuansu2(up, down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        } else if (!up.isEmpty() && down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changezhuansuup2(up);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }


            });
        } else if (up.isEmpty() && !down.isEmpty()) {
            Call<ResponseBody> call = serverInterface.changezhuansudown2(down);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    extracted(response);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    judgement(t);
                }
            });
        }
    }

    private void extracted(Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            try {
                String string = response.body().string();
                if (string.equals("-1")) {
                    Toast.makeText(ControlActivity.this, "更改失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ControlActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                    display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从menu_overflow.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_overflow2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void judgement(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            Toast.makeText(ControlActivity.this, "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ControlActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
        }
    }
}
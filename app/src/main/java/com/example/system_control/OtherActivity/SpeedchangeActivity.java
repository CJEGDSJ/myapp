package com.example.system_control.OtherActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.system_control.R;
import com.example.system_control.util.Network;
import com.example.system_control.util.ServerInterface;

import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SpeedchangeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_speedchange;
    private Toolbar toolbar;
    private Button btn_speedchange;
    private Button btn_speedchange2;
    private Network network;
    private ServerInterface serverInterface;
    private Retrofit retrofit;
    private String baseurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedchange);
        /*baseurl = "http://192.168.100.145:9000";*/
        baseurl = "http://192.168.0.199:9000";
        network = new Network(baseurl);
        retrofit = new Retrofit.Builder().baseUrl(baseurl).build();
        serverInterface = retrofit.create(ServerInterface.class);
        init();
    }

    private void init() {
        toolbar= findViewById(R.id.speedback);
        et_speedchange = findViewById(R.id.et_changespeed);
        btn_speedchange = findViewById(R.id.btn_speedchange);
        btn_speedchange2 = findViewById(R.id.btn_speedchange2);
        btn_speedchange.setOnClickListener(this);
        btn_speedchange2.setOnClickListener(this);
        toolbar.setOnClickListener(this);
    }

    private void speedchange1() {
        final String speed = et_speedchange.getText().toString();
        if (speed.isEmpty()) {
            Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<ResponseBody> call = serverInterface.changeSpeed(speed);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "更改转速成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
  /*      network.changespeed(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        JSONObject jsonObject = JSON.parseObject(string);
                        Toast.makeText(getApplicationContext(),"更改转速成功",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    private void speedchange2() {
        final String speed = et_speedchange.getText().toString();
        if (speed.isEmpty()) {
            Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<ResponseBody> call = serverInterface.changeSpeed2(speed);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "更改转速成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                judgement(t);
            }
        });
/*        network.changespeed2(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"更改转速成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_speedchange) {
            speedchange1();
        } else if (v.getId() == R.id.btn_speedchange2) {
            speedchange2();
        } else if (v.getId() == R.id.speedback) {
            finish();
        }
    }
    private void judgement(Throwable t) {
        if (t instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
        }
    }
}
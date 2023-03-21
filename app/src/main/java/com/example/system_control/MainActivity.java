package com.example.system_control;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.example.system_control.Helper.UserDBHelper;
import com.example.system_control.OtherActivity.DispalyActivity;
import com.example.system_control.UserInfo.DateUtil;
import com.example.system_control.UserInfo.UserInfo;
import com.example.system_control.util.Network;
import com.example.system_control.util.PermissionUtil;
import com.example.system_control.util.UserResult;
import com.example.system_control.util.ViewUtil;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private Button btn_Userlogin;
    private Button btn_Night_mode;
    private EditText User;
    private EditText Password;
    private Network network;
    private String bodystring;
    private CheckBox showpassword;
    private CheckBox remberpassword;
    private TextView tv_user;
    private TextView tv_password; // 声明一个文本视图对象
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;
    private boolean isConnected;
    private String networkout = "Failed to connect to /192.168.1.102:9000";
    private String networkouts = "Failed to connect to /192.168.100.145:9000";
    private String networkoutss = "Failed to connect to /192.168.0.197:9000";
    private String networkoutss2 = "Failed to connect to /192.168.0.199:9000";
    String regex;
    private static final int REQUEST_ALL = 1;
    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象
    private boolean isRemember = false; // 是否记住密码
    private String mPassword = "111111"; // 默认密码
    private RadioGroup rg_login; // 声明一个单选组对象
    private String mVerifyCode; // 验证码
    private int mRequestCode = 0; // 跳转页面时的请求代码
    private RadioButton rb_password; // 声明一个单选按钮对象
    private RadioButton rb_verifycode; // 声明一个单选按钮对象
    private final String[] PERMISSION_ALL = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.checkPermission(this, PERMISSION_ALL, REQUEST_ALL);
        setContentView(R.layout.activity_main);
        // 在 onCreate 方法中初始化 ConnectivityManager 对象
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        /*network = new Network("http://192.168.1.102:9000/");*/
        /*network = new Network("http://192.168.100.145:9000/");*/
        network = new Network("http://192.168.0.199:9000/");
        /*network = new Network("http://192.168.0.197:9000/");*/
        init();

    }

    private void init() {
        btn_Userlogin = findViewById(R.id.btn_Userlogin);
        User = findViewById(R.id.et_User);
        Password = findViewById(R.id.et_password);
        // 给密码编辑框注册一个焦点变化监听器，一旦焦点发生变化，就触发监听器的onFocusChange方法
        Password.setOnFocusChangeListener(this);
        showpassword = findViewById(R.id.checkBoxShowPassword);
        remberpassword = findViewById(R.id.remberBoxShowPassword);
//        btn_Night_mode = findViewById(R.id.btn_Night_theme);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        tv_user = findViewById(R.id.tv_phone);
        tv_password = findViewById(R.id.tv_password);
        rg_login = findViewById(R.id.rg_login);
        // 给rg_login设置单选监听器
        rg_login.setOnCheckedChangeListener(new RadioListener());

        btn_Userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lg();
                login();
            }
        });
//        btn_Night_mode.setOnClickListener(this);

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // 用户想要显示密码
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 用户想要隐藏密码
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                CharSequence charSequence = Password.getText();
                if (charSequence != null) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });

        // 给ck_remember设置勾选监听器
        remberpassword.setOnCheckedChangeListener((buttonView, isChecked) -> isRemember = isChecked);
        // 给et_phone添加文本变更监听器
        User.addTextChangedListener(new HideTextWatcher(User, 11));
        // 给et_password添加文本变更监听器
        Password.addTextChangedListener(new HideTextWatcher(Password, 6));
        Password.setOnFocusChangeListener(this);
//        network_state();
//        netwwork_state();
        regex ="^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$";
    }


    public void returnclick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.baseline_west_24)
                .setTitle("")
                .setMessage("确认退出吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    private void login() {
        final String login_UserName = User.getText().toString();
        final String login_Password = Password.getText().toString();
        boolean matches = login_UserName.matches(regex);
/*        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(login_UserName);
        boolean matches = m.matches();*/
        Log.e("TAG", "login: " + matches);
        if (login_UserName.equals("") || login_Password.equals("")) {
            Toast.makeText(this, "请输入账号和密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        remberpassword();
        network.sign(login_UserName, login_Password, new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                if (response.isSuccessful()) {
                    UserResult userResult = response.body();
                    bodystring = response.body().toString();
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DispalyActivity.class);
                /*Bundle mBundle = new Bundle();
                    mBundle.putString("IP", "http://192.168.100.174:9000/");//压入数据*/
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);//开启下一个活动
                    Log.e("TAG", "onResponse: " + userResult);
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(MainActivity.this, "网络连接超时或服务器请求失败", Toast.LENGTH_SHORT).show();
                } else if (t.getMessage().equals(networkoutss2)) {
                    Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                } else {
                    //其他异常
                    Toast.makeText(MainActivity.this, "账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.btn_Night_theme) {
            *//*toggleTheme();*//*
        }*/
    }

    private void network_state() {
        // 检查网络连接状态
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            // 网络已连接
            int networkType = activeNetworkInfo.getType();
            /*String networkTypeName = activeNetworkInfo.getTypeName();*/
            // 根据网络类型判断具体类型，如下所示
            if (networkType == ConnectivityManager.TYPE_WIFI) {

                // 当前网络为 WIFI 网络
            } else if (networkType == ConnectivityManager.TYPE_MOBILE) {
                // 当前网络为移动数据网络，需要进一步判断具体类型
                method();
            }
        }
    }

    private void toggleTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                // 默认情况下，系统会将 UI 模式设置为未定义。在这种情况下，
                // 我们可以使用系统设置来判断是否启用了黑夜模式。
                if ((getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK) ==
                        Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO);
                }
                break;
        }
        recreate();
    }

    private void method() {
        // 当前网络为移动数据网络，需要进一步判断具体类型
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        // 已经获得了 ACCESS_NETWORK_STATE 权限，继续执行操作
        // ...
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            int networkType = telephonyManager.getDataNetworkType();
            if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE ||
                    networkType == TelephonyManager.NETWORK_TYPE_CDMA || networkType == TelephonyManager.NETWORK_TYPE_1xRTT ||
                    networkType == TelephonyManager.NETWORK_TYPE_IDEN) {
                Toast.makeText(MainActivity.this, "请注意当前使用的是流量", Toast.LENGTH_SHORT).show();
                // 当前网络为 2G 网络
            } else if (networkType == TelephonyManager.NETWORK_TYPE_UMTS || networkType == TelephonyManager.NETWORK_TYPE_EVDO_0 ||
                    networkType == TelephonyManager.NETWORK_TYPE_EVDO_A || networkType == TelephonyManager.NETWORK_TYPE_HSDPA ||
                    networkType == TelephonyManager.NETWORK_TYPE_HSUPA || networkType == TelephonyManager.NETWORK_TYPE_HSPA ||
                    networkType == TelephonyManager.NETWORK_TYPE_EVDO_B || networkType == TelephonyManager.NETWORK_TYPE_EHRPD ||
                    networkType == TelephonyManager.NETWORK_TYPE_HSPAP) {
                Toast.makeText(MainActivity.this, "请注意当前使用的是流量", Toast.LENGTH_SHORT).show();
                // 当前网络为 3G 网络
            } else if (networkType == TelephonyManager.NETWORK_TYPE_LTE || networkType == TelephonyManager.NETWORK_TYPE_IWLAN) {
                // 当前网络为 4G 网络
                Toast.makeText(MainActivity.this, "请注意当前使用的是流量", Toast.LENGTH_SHORT).show();
            } else {
                // 无法确定当前移动数据网络类型
            }
        } else {
            // 未获得 ACCESS_NETWORK_STATE 权限，请求该权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            Toast.makeText(this, "请授予应用程序 ACCESS_NETWORK_STATE 权限", Toast.LENGTH_SHORT).show();
        }

    }


    private void netwwork_state() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        SubscriptionManager sm = SubscriptionManager.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            /* int networkType = tm.getDataNetworkType();*/
            List<SubscriptionInfo> subs = sm.getActiveSubscriptionInfoList();
            if (subs != null && subs.size() > 0) {
                // select the first active subscription
                int subId = subs.get(0).getSubscriptionId();
                int networkType = tm.getDataNetworkType();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        // do something
                        break;
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        // do something
                        Toast.makeText(MainActivity.this, "请注意当前使用的是流量", Toast.LENGTH_SHORT).show();
                        break;
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        // do something
                        break;
                    // add more cases for other network types
                    default:
                        Toast.makeText(MainActivity.this, "请注意当前使用的是流量", Toast.LENGTH_SHORT).show();
                        // do something
                        break;
                }
            }

        }

    }

    private void lg() {
        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, DispalyActivity.class);
                /*Bundle mBundle = new Bundle();
                    mBundle.putString("IP", "http://192.168.100.174:9000/");//压入数据*/
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);//开启下一个活动
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (PermissionUtil.checkGrant(grantResults)) {
                // 用户授予了 ACCESS_NETWORK_STATE 权限，继续执行操作

            } else {
                // 用户拒绝了 ACCESS_NETWORK_STATE 权限，提示用户并停止执行操作
                Toast.makeText(this, "请授予应用程序必要的权限！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String phone = User.getText().toString();
        // 判断是否是密码编辑框发生焦点变化
        if (v.getId() == R.id.et_password) {
            // 用户已输入手机号码，且密码框获得焦点
            if (phone.length() > 0 && hasFocus) {
                // 根据手机号码到数据库中查询用户记录
                UserInfo info = mHelper.queryByPhone(phone);
                if (info != null) {
                    // 找到用户记录，则自动在密码框中填写该用户的密码
                    Password.setText(info.password);
                } else {
                    Log.e("TAG", "error" + "1111");
                }
            }
        }

    }

    private class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_password) { // 选择了密码登录
                tv_user.setText("用户名称：");
                tv_password.setText("登录密码：");
                User.setHint("请输入用户名");
                Password.setHint("请输入密码");
                showpassword.setVisibility(View.VISIBLE);
                remberpassword.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rb_verifycode) { // 选择了验证码登录
                tv_user.setText("手机号码：");
                tv_password.setText("　验证码：");
                User.setHint("请输入手机号码");
                Password.setHint("请输入验证码");
                showpassword.setVisibility(View.GONE);
                remberpassword.setVisibility(View.GONE);
            }
        }
    }

    private class HideTextWatcher implements TextWatcher {
        private EditText mView; // 声明一个编辑框对象
        private int mMaxLength; // 声明一个最大长度变量

        public HideTextWatcher(EditText v, int maxLength) {
            super();
            mView = v;
            mMaxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // 在编辑框的输入文本变化前触发
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // 在编辑框的输入文本变化时触发
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString(); // 获得已输入的文本字符串
            // 输入文本达到11位（如手机号码），或者达到6位（如登录密码）时关闭输入法
            if ((str.length() == 11 && mMaxLength == 11)
                    || (str.length() == 6 && mMaxLength == 6)) {
                ViewUtil.hideOneInputMethod(MainActivity.this, mView); // 隐藏输入法软键盘
            }
        }
    }

    private void Captcha() {
        // 生成六位随机数字的验证码
        mVerifyCode = String.format("%06d", new Random().nextInt(999999));
        // 以下弹出提醒对话框，提示用户记住六位验证码数字
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请记住验证码");
        builder.setMessage("手机号" + "uid" + "，本次验证码是" + mVerifyCode + "，请输入验证码");
        builder.setPositiveButton("好的", null);
        AlertDialog alert = builder.create();
        alert.show(); // 显示提醒对话框
    }

    private void remberpassword() {
        // 如果勾选了“记住密码”，则把手机号码和密码保存为数据库的用户表记录
        if (isRemember) {
            UserInfo info = new UserInfo(); // 创建一个用户信息对象
            info.uid = User.getText().toString();
            info.password = Password.getText().toString();
            info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
            mHelper.insert(info); // 往用户数据库添加登录成功的用户信息
        }
    }

    @Override
    protected void onResume() {
        mHelper = UserDBHelper.getInstance(this, 1); // 获得用户数据库帮助器的实例
        mHelper.openWriteLink(); // 恢复页面，则打开数据库连接
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
        mHelper.closeLink(); // 暂停页面，则关闭数据库连接
    }
    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    // 为什么光标进入密码框事件不选onClick？因为要点两下才会触发onClick动作（第一下是切换焦点动作）

}
package com.example.system_control.util;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServerInterface {
    //登录接口
    @POST("login")
    @FormUrlEncoded
    Call<UserResult> sign(@Field("username") String username, @Field("password") String password);

    //查询所有数据
    @POST("data/check_all")
    @FormUrlEncoded
    Call<ResponseBody> queryalldatas(@Field("") String s);

    //查询所有最新数据
    @POST("data/allNews")
    @FormUrlEncoded
    Call<ResponseBody> newdada(@Field("") String s);
    @POST("data/allNews")
    @FormUrlEncoded
    Call<AllNewDatas> newdadas(@Field("") String s);

    //查询所有阈值
    @POST("data/check_all_threshold")
    @FormUrlEncoded
    Call<ResponseBody> querythreshold(@Field("") String s);

    //查询转速高低阈值
    @POST("data/check_zfazhiall")
    @FormUrlEncoded
    Call<ResponseBody> queryguangzhao(@Field("") String s);

    //查询温度高低阈值
    @POST("data/check_wfazhiall")
    @FormUrlEncoded
    Call<ResponseBody> queryTemperature(@Field("") String s);

    //查询光照高低阈值
    @POST("data/check_gfazhiall")
    @FormUrlEncoded
    Call<ResponseBody> queryIllumination(@Field("") String s);

    //查询转速高低阈值
    @POST("data/check_zfazhiall")
    @FormUrlEncoded
    Call<ResponseBody> querySpeed(@Field("") String s);

    //查询电压高低阈值
    @POST("data/check_dfazhiall")
    @FormUrlEncoded
    Call<ResponseBody> queryVoltage(@Field("") String s);

    //更改光照高阈值
    @POST("data/update_gfazhigao")
    @FormUrlEncoded
    Call<ResponseBody> changeguangzhaoup(@Field("gfazhigao") String s);

    //更改光照低阈值
    @POST("data/update_gfazhidi")
    @FormUrlEncoded
    Call<ResponseBody> changeguangzhaodown(@Field("gfazhidi") String s);

    //更新光照高低阈值
    @POST("data/update_lux")
    @FormUrlEncoded
    Call<ResponseBody> changeguangzhao(@Field("gfazhigao") String up,@Field("gfazhidi")String down);

    //更改转速高阈值
    @POST("data/update_zfazhigao")
    @FormUrlEncoded
    Call<ResponseBody> changezhuansuup(@Field("zfazhigao") String s);

    //更改转速低阈值
    @POST("data/update_zfazhidi")
    @FormUrlEncoded
    Call<ResponseBody> changezhuansudown(@Field("zfazhidi") String s);

    //更新转速高低阈值
    @POST("data/update_rev")
    @FormUrlEncoded
    Call<ResponseBody> changezhuansu(@Field("zfazhigao") String up,@Field("zfazhidi")String down);

    //更改电压高阈值
    @POST("data/update_dfazhigao")
    @FormUrlEncoded
    Call<ResponseBody> changedianyaup(@Field("dfazhigao") String s);

    //更改电压低阈值
    @POST("data/update_dfazhidi")
    @FormUrlEncoded
    Call<ResponseBody> changedianyadown(@Field("dfazhidi") String s);

    //更新电压高低阈值
    @POST("data/update_volt")
    @FormUrlEncoded
    Call<ResponseBody> changedianya(@Field("dfazhigao") String up,@Field("dfazhidi")String down);

    //更改温度高阈值
    @POST("data/update_wfazhigao")
    @FormUrlEncoded
    Call<ResponseBody> changewenduup(@Field("wfazhigao") String s);

    //更改温度低阈值
    @POST("data/update_wfazhidi")
    @FormUrlEncoded
    Call<ResponseBody> changewendudown(@Field("wfazhidi") String s);
    //更新温度高低阈值
    @POST("data/update_temp")
    @FormUrlEncoded
    Call<ResponseBody> changewendu(@Field("wfazhigao") String up,@Field("wfazhidi")String down);

    @POST("data/check_all")
    @FormUrlEncoded
    Call<ResponseBody> getalldatas(@Field("") String s);

    //查询所有温度值
    @POST("data/wendu_checkall")
    @FormUrlEncoded
    Call<ResponseBody> getallwendu(@Field("") String s);
    //查询所有电压值
    @POST("data/dianya_checkall")
    @FormUrlEncoded
    Call<ResponseBody> getalldianya(@Field("") String s);
    //查询所有电流值
    @POST("data/dianliu_checkall")
    @FormUrlEncoded
    Call<ResponseBody> getalldianliu(@Field("") String s);
    //查询所有转速值
    @POST("data/zhuansu_checkall")
    @FormUrlEncoded
    Call<ResponseBody> getallzhuansu(@Field("") String s);
    //查询所有光照值
    @POST("data/guangzhao_checkall")
    @FormUrlEncoded
    Call<ResponseBody> getallguangzhao(@Field("") String s);

    //控制开关
    @POST("state_mylight")
    @FormUrlEncoded
    Call<ResponseBody> stitch(@Field("light") String s);

    //控制风扇1
    @POST("state_fan")
    @FormUrlEncoded
    Call<ResponseBody> fan(@Field("fan") String s);

    //控制风扇2
    @POST("state_twofan")
    @FormUrlEncoded
    Call<ResponseBody> tfan(@Field("twofan") String s);

    //控制电机
    @POST("state_elec")
    @FormUrlEncoded
    Call<ResponseBody> changeengine(@Field("elec") String s);

    //更改设备2光照高阈值
    @POST("data/update_gfazhigao2")
    @FormUrlEncoded
    Call<ResponseBody> changeguangzhaoup2(@Field("gfazhigao2") String s);

    //更改设备2光照低阈值
    @POST("data/update_gfazhidi2")
    @FormUrlEncoded
    Call<ResponseBody> changeguangzhaodown2(@Field("gfazhidi2") String s);
    //更改光照2高低阈值
    @POST("data/update_lux2")
    @FormUrlEncoded
    Call<ResponseBody> changeguangzhao2(@Field("gfazhigao2") String up,@Field("gfazhidi2")String down);

    //更改设备2转速高阈值
    @POST("data/update_zfazhigao2")
    @FormUrlEncoded
    Call<ResponseBody> changezhuansuup2(@Field("zfazhigao2") String s);

    //更改设备2转速低阈值
    @POST("data/update_zfazhidi2")
    @FormUrlEncoded
    Call<ResponseBody> changezhuansudown2(@Field("zfazhidi2") String s);

    //更改温度2高低阈值
    @POST("data/update_rev2")
    @FormUrlEncoded
    Call<ResponseBody> changezhuansu2(@Field("zfazhigao2") String up,@Field("zfazhidi2")String down);

    //更改设备2电压高阈值
    @POST("data/update_dfazhigao2")
    @FormUrlEncoded
    Call<ResponseBody> changedianyaup2(@Field("dfazhigao2") String s);

    //更改设备2电压低阈值
    @POST("data/update_dfazhidi2")
    @FormUrlEncoded
    Call<ResponseBody> changedianyadown2(@Field("dfazhidi2") String s);

    //更改电压2高低阈值
    @POST("data/update_volt2")
    @FormUrlEncoded
    Call<ResponseBody> changedianya2(@Field("dfazhigao2") String up,@Field("dfazhidi2")String down);

    //更改设备2温度高阈值
    @POST("data/update_wfazhigao2")
    @FormUrlEncoded
    Call<ResponseBody> changewenduup2(@Field("wfazhigao2") String s);

    //更改设备2温度低阈值
    @POST("data/update_wfazhidi2")
    @FormUrlEncoded
    Call<ResponseBody> changewendudown2(@Field("wfazhidi2") String s);
    //更改温度2高低阈值
    @POST("data/update_temp2")
    @FormUrlEncoded
    Call<ResponseBody> changewendu2(@Field("wfazhigao2") String up,@Field("wfazhidi2")String down);

    //更改转速
    @POST("state/update_speed")
    @FormUrlEncoded
    Call<ResponseBody> changeSpeed(@Field("speed") String s);

    //更改设备2转速
    @POST("state/update_speed2")
    @FormUrlEncoded
    Call<ResponseBody> changeSpeed2(@Field("speed2") String s);
    @POST("/checkwendu")
    @FormUrlEncoded
    Call<ResponseBody> checkwendu(@Field("") String s);
    @POST("/state_all")
    @FormUrlEncoded
    Call<ResponseBody> queryallstate(@Field("") String s);


}

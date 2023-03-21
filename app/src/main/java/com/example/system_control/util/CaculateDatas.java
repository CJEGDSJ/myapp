package com.example.system_control.util;

import android.graphics.Color;

import com.example.system_control.datas.CurrentDatas;
import com.example.system_control.datas.IlluminationDatas;
import com.example.system_control.datas.SPeedDatas;
import com.example.system_control.datas.TemperatureDatas;
import com.example.system_control.datas.VoltageDatas;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class CaculateDatas {
    int lengths = 50;

    public void Caculate_Temperature(int size, List<TemperatureDatas> temperatureDatas, String[] wenduarry, int number) {
        if (size <= lengths) {
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size - 1; j++) {
                    extracted_tem(size, temperatureDatas, wenduarry, number, j);

                }
            }
        } else {
            for (int i = size - 1; i >= size - (size - lengths); i--) {
                for (int j = 0; j < lengths; j++) {
                    extracted_tem(size, temperatureDatas, wenduarry, number, j);
                    /*wendu = temperatureDatas.get(size - (1 + j)).getWendu();
                     *//*wendu2 = temperatureDatas.get(size - (1 + j)).getWendu2();*//*
                    wenduarry[j] = wendu;*/
                    /*wenduarry2[j] = wendu2;*/
                }
            }
        }
    }

    private void extracted_tem(int size, List<TemperatureDatas> temperatureDatas, String[] wenduarry, int number, int j) {
        String wendu;
        if (number == 1) {
            wendu = temperatureDatas.get(size - 1 - j).getWen();
            wenduarry[j] = wendu;
        } else if(number==2){
            wendu = temperatureDatas.get(size - 1 - j).getWendu();
            wenduarry[j] = wendu;
        }else if(number==3){
            wendu = temperatureDatas.get(size - 1 - j).getWen2();
            wenduarry[j] = wendu;
        }else{
            wendu = temperatureDatas.get(size - 1 - j).getWendu2();
            wenduarry[j] = wendu;
        }
    }

    private void extracted_illu(int size, List<IlluminationDatas> illuminationDatas, String[] guangzhaoarry, int number, int j) {
        String guangzhao;
        if (number == 1) {
            guangzhao = illuminationDatas.get(size - 1 - j).getGuangzhao();
            guangzhaoarry[j] = guangzhao;
        } else {
            guangzhao = illuminationDatas.get(size - 1 - j).getGuangzhao2();
            guangzhaoarry[j] = guangzhao;
        }
    }

    private void extracted_speed(int size, List<SPeedDatas> sPeedDatas, String[] speedarry, int number, int j) {
        String zhuansu;
        if (number == 1) {
            zhuansu = sPeedDatas.get(size - 1 - j).getZhuansu();
            speedarry[j] = zhuansu;
        } else {
            zhuansu = sPeedDatas.get(size - 1 - j).getZhuansu2();
            speedarry[j] = zhuansu;
        }
    }

    private void extracted_vol(int size, List<VoltageDatas> voltageDatas, String[] voltagearry, int number, int j) {
        String dianya;
        if (number == 1) {
            dianya = voltageDatas.get(size - 1 - j).getDian();
            voltagearry[j] = dianya;
        } else if (number==2){
            dianya = voltageDatas.get(size - 1 - j).getDianya();
            voltagearry[j] = dianya;
        } else if (number==3){
            dianya = voltageDatas.get(size - 1 - j).getDian2();
            voltagearry[j] = dianya;
        } else{
            dianya = voltageDatas.get(size - 1 - j).getDianya2();
            voltagearry[j] = dianya;
        }
    }

    private void extracted_cur(int size, List<CurrentDatas> currentDatas, String[] currentaay, int number, int j) {
        String dianliu;
        if (number == 1) {
            dianliu = currentDatas.get(size - 1 - j).getDianliu();
            currentaay[j] = dianliu;
        } else {
            dianliu = currentDatas.get(size - 1 - j).getDianliu2();
            currentaay[j] = dianliu;
        }
    }

    public void  Setdatas(List<BarEntry> list, String[] arry, BarChart barChart, String label, String color) {
        for (int index = 0; index < lengths; index++) {
            String s = arry[index];
            /*Log.e("TAG", "Setdatas: " + s);*/
            if (s == null) {
                BarEntry barEntry = new BarEntry(index, 0);
                list.add(barEntry);
                continue;
            }
            Float[] ints = new Float[arry.length];
            ints[index] = Float.parseFloat(s);
            BarEntry barEntry = new BarEntry(index, ints[index]);
            list.add(barEntry);
        }


        BarDataSet set1 = new BarDataSet(list, label);
        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(list);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1.setColor(Color.parseColor(color));
            set1.setDrawIcons(false);
            set1.setDrawValues(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.9f);
            data.setValueTextSize(10f);
            barChart.setData(data);
            barChart.setVisibleXRangeMaximum(10);
            barChart.setFitBars(true);
            barChart.invalidate();
        }
        //设置柱形统计  柱上的文本大小
        barChart.getData().setValueTextSize(10);
        for (IDataSet set : barChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
        }


    }

    public void Setdatas(List<Entry> list, String[] arry, LineChart lineChart, String label, String color) {
        for (int index = 0; index < lengths; index++) {
            String s = arry[index];
            if (s == null) {
                Entry entry = new Entry(index, 0);
                list.add(entry);
                continue;
            }

            Float[] ints = new Float[arry.length];
            ints[index] = Float.parseFloat(s);
            Entry entry = new Entry(index, ints[index]);
            list.add(entry);
        }

        LineDataSet lineDataSet = new LineDataSet(list, label);
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(list);
            lineDataSet.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {

            //设置应绘制此 DataSet 的 y 轴（左或右）。默认值：左
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            //数据的颜色
//            lineDataSet.setColor(Color.parseColor(color));
            lineDataSet.setColor(Color.parseColor(color));

            //数据线的宽度
            lineDataSet.setLineWidth(4f);

            lineDataSet.setCircleColor(Color.BLACK);


            lineDataSet.setDrawCircleHole(false);

            //设置数据点是否为圆点
            lineDataSet.setDrawCircles(true);
            //设置数据线的展现方式
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            List<ILineDataSet> dataSets = new ArrayList<>();
            //将数据添加进容器
            dataSets.add(lineDataSet);
            LineData lineData = new LineData(dataSets);
            //将数据容器添加进布局
            lineChart.setData(lineData);
            //设置界面最大显示个数，超过该数目后需要左右滑动查看其它数据
            lineChart.setVisibleXRangeMaximum(8);
            //设置动画时长
            lineChart.animateX(100);
        }

        //刷新界面
//        lineChart.invalidate();
    }

    public void Caculata_Illumination(int size, List<IlluminationDatas> illuminationDatas, String[] guangzhaoarry, int number) {
        if (size <= lengths) {
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size - 1; j++) {
                    extracted_illu(size, illuminationDatas, guangzhaoarry, number, j);
                    /*guangzhao = illuminationDatas.get(size - 1 - j).getGuangzhao();
                    guangzhaoarry[j] = guangzhao;*/
                }
            }
        } else {
            for (int i = size - 1; i >= size - (size - lengths); i--) {
                for (int j = 0; j < lengths; j++) {
                    extracted_illu(size, illuminationDatas, guangzhaoarry, number, j);
                    /*String wendu = illuminationDatas.get(size - (1 + j)).getGuangzhao();
                    guangzhaoarry[j] = wendu;*/
                }
            }
        }
    }

    public void Caculata_voltage(int size, List<VoltageDatas> voltageDatas, String[] voltagearry, int number) {

        if (size <= lengths) {
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size - 1; j++) {
                    extracted_vol(size, voltageDatas, voltagearry, number, j);
                }
            }
        } else {
            for (int i = size - 1; i >= size - (size - lengths); i--) {
                for (int j = 0; j < lengths; j++) {
                    extracted_vol(size, voltageDatas, voltagearry, number, j);
                }
            }
        }
    }
    public void Caculate_current(int size,List<CurrentDatas> currentDatas,String[] currentarry,int number){
        if (size <= lengths) {
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size - 1; j++) {
                    extracted_cur(size, currentDatas, currentarry, number, j);
                }
            }
        } else {
            for (int i = size - 1; i >= size - (size - lengths); i--) {
                for (int j = 0; j < lengths; j++) {
                    extracted_cur(size, currentDatas, currentarry, number, j);
                }
            }
        }
    }
    public void Caculate_speed(int size,List<SPeedDatas> sPeedDatas,String[] speedarry,int number){
        if (size <= lengths) {
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size - 1; j++) {
                    extracted_speed(size, sPeedDatas, speedarry, number, j);
                }
            }
        } else {
            for (int i = size - 1; i >= size - (size - lengths); i--) {
                for (int j = 0; j < lengths; j++) {
                    extracted_speed(size, sPeedDatas, speedarry, number, j);
                }
            }
        }
    }

}

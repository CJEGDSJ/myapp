package com.example.system_control.OtherActivity.Fomatter;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class TValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;
    public TValueFormatter() {
        mFormat = new DecimalFormat("0.00");
    }
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "℃";
    }
}

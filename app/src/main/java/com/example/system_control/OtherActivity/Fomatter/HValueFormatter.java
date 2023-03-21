package com.example.system_control.OtherActivity.Fomatter;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class HValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public HValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    public String getFormattedValue(float value) {
        return mFormat.format(value) + "%RH";
    }
}

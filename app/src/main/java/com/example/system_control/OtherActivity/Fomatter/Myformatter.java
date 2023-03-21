package com.example.system_control.OtherActivity.Fomatter;

import android.icu.text.DecimalFormat;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class Myformatter extends ValueFormatter {
    private DecimalFormat mFormat;

    public Myformatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");

    }
    public String getFormattedValue(float value) {
        return mFormat.format(value) ;
    }
}

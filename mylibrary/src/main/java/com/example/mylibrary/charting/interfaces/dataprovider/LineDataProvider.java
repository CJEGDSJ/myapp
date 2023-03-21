package com.example.mylibrary.charting.interfaces.dataprovider;

import com.example.mylibrary.charting.components.YAxis;
import com.example.mylibrary.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}

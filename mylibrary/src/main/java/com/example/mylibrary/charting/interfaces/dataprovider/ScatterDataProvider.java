package com.example.mylibrary.charting.interfaces.dataprovider;

import com.example.mylibrary.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}

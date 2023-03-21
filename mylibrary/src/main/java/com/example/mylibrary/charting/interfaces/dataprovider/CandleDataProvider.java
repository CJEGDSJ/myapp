package com.example.mylibrary.charting.interfaces.dataprovider;

import com.example.mylibrary.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}

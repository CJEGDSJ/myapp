package com.example.mychartlibrary.charting.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}

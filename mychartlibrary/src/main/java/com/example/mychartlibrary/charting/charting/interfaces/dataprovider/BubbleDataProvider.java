package com.example.mychartlibrary.charting.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}

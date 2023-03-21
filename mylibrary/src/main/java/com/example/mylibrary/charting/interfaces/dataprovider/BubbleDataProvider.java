package com.example.mylibrary.charting.interfaces.dataprovider;

import com.example.mylibrary.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}

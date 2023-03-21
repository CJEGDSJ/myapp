package com.example.mylibrary.charting.interfaces.dataprovider;

import com.example.mylibrary.charting.components.YAxis.AxisDependency;
import com.example.mylibrary.charting.data.BarLineScatterCandleBubbleData;
import com.example.mylibrary.charting.utils.Transformer;
	   
public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}

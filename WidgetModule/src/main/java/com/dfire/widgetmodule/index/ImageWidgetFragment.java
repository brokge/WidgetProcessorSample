package com.dfire.widgetmodule.index;

import android.os.Bundle;

import com.dfire.basewidgetfactory.BaseWidget;
import com.dfire.widgetprocessor.Widget;

@Widget("ImageWidgetFragment")
public class ImageWidgetFragment extends BaseWidget {
    public static ImageWidgetFragment instance(String aa) {

        Bundle args = new Bundle();

        ImageWidgetFragment fragment = new ImageWidgetFragment();
        return fragment;
    }

    @Override
    public String toString() {
        return "这是ImageWidgetFragment";
    }
}

package com.dfire.widgetmodule;

import com.dfire.basewidgetfactory.BaseWidget;
import com.dfire.widgetprocessor.Widget;

@Widget("TextWidgetFragment")
public class TextWidgetFragment extends BaseWidget {
    public static TextWidgetFragment instance(String aa) {

        TextWidgetFragment fragment = new TextWidgetFragment();

        return fragment;
    }

    @Override
    public String toString() {
        return "这是TextWidgetFragment";
    }
}

package com.dfire.widgetprocessorsample;

import android.os.Bundle;

import com.dfire.basewidgetfactory.BaseWidget;
import com.dfire.widgetprocessor.Widget;

@Widget("TestWidgetFragment")
public class TestWidgetFragment extends BaseWidget {
    public static TestWidgetFragment instance(String aa) {

        Bundle args = new Bundle();

        TestWidgetFragment fragment = new TestWidgetFragment();
        return fragment;
    }

    @Override
    public String toString() {
        return "这是TestWidgetFragment";
    }
}

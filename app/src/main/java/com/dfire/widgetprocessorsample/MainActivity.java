package com.dfire.widgetprocessorsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dfire.basewidgetfactory.BaseWidget;
import com.dfire.basewidgetfactory.WidgetFactory;


public class MainActivity extends AppCompatActivity {

    protected Button btnGet;
    protected TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWidgetInfo("TextWidgetFragment");
            }
        });
    }

    private void getWidgetInfo(String widgetValue) {
        BaseWidget baseWidget = WidgetFactory.createWidgetFragment(widgetValue, "");
        tvInfo.setText(baseWidget.toString());
    }

    private void initView() {
        btnGet = findViewById(R.id.btn_get);
        tvInfo = findViewById(R.id.tv_info);
    }
}

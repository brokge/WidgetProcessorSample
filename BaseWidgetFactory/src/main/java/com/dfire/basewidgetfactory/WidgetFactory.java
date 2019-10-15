package com.dfire.basewidgetfactory;

import java.util.Set;

public class WidgetFactory {
    public static BaseWidget createWidgetFragment(String component, String jsonNode) {
        Set<String> names = WidgetFactoryManager.getWidgetFactoryNames();
        for (String name : names) {
            IWidgetFactory widgetFactory = WidgetFactoryManager.getWidgetFactory(name);
            BaseWidget baseWidget = null;
            try {
                baseWidget = (BaseWidget) widgetFactory.create(component, jsonNode);
            } catch (IllegalAccessException e) {

            } catch (InstantiationException e) {
                
            }
            if (baseWidget != null) {
                return baseWidget;
            }
        }
        return null;
    }
}

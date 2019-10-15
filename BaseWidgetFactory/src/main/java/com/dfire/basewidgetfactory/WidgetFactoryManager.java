package com.dfire.basewidgetfactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WidgetFactoryManager {

    private static Map<String, IWidgetFactory> WIDGET_FACTORY = new HashMap<>();

    public static void register(IWidgetFactory widgetFactory) {
        if (widgetFactory != null) {
            WIDGET_FACTORY.put(widgetFactory.getClass().getName(), widgetFactory);
        }
    }

    public static Set<String> getWidgetFactoryNames() {
        return WIDGET_FACTORY.keySet();
    }

    public static IWidgetFactory getWidgetFactory(String name) {
        if (WIDGET_FACTORY != null && WIDGET_FACTORY.size() > 0) {
            return WIDGET_FACTORY.get(name);
        }
        return null;
    }
}

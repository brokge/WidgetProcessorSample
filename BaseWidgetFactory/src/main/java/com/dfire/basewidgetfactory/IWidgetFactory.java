package com.dfire.basewidgetfactory;

public interface IWidgetFactory {
    Object create(String component, String jsonNode) throws IllegalAccessException,
            InstantiationException;
}

package com.dfire.widgetprocessor;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Widget {
	/**
	 * 用来表示生成哪个对象的唯一id
	 */
	String value() default "";
}

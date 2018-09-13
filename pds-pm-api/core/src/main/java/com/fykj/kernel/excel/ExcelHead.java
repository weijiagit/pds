package com.fykj.kernel.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:05
 * Description:
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelHead {
    String value() default "";
}

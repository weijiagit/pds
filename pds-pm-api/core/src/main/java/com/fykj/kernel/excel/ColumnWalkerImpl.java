package com.fykj.kernel.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:00
 * Description:
 **/
public abstract class ColumnWalkerImpl implements ColumnWalker{
    private static final Logger LOGGER = LoggerFactory.getLogger(ColumnWalkerImpl.class);

    protected int i = 0;

    /**
     * (non-Javadoc)
     *
     */
    @Override
    public boolean hasNext() {
        List<Field> actualFields = getActualFields();
        if (i >= actualFields.size()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * (non-Javadoc)
     *
     */
    @Override
    public String next() {
        List<Field> actualFields = getActualFields();
        Field field = actualFields.get(i);
        String value = "";
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), getClass());
            Method rM = pd.getReadMethod();
            value = (String) rM.invoke(this);
			/*LOGGER.info(value);*/
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (IntrospectionException e) {
            LOGGER.error(e.getMessage(),e);
        }
        i++;
        return value;
    }

    private List<Field> getActualFields() {
        Field[] fields = getClass().getDeclaredFields();
        List<Field> actualFields = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getAnnotation(ExcelColumn.class) != null) {
                actualFields.add(fields[i]);
            }
        }
        return actualFields;
    }
}

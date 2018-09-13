package com.fykj.kernel.controller;


import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import java.beans.PropertyEditorSupport;

/**
 * Author: songzhonglin
 * Date: 2017/12/8
 * Time: 15:23
 * Description:
 **/
public class StringEscapeEditor extends PropertyEditorSupport {
    private boolean escapeHTML;// 编码HTML
    private boolean escapeJavaScript;// 编码javascript
    private boolean escapeSQL; //定义是否是SQL注入

    public StringEscapeEditor() {
        super();
    }

    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript,boolean escapeSQL) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
        this.escapeSQL = escapeSQL;
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                value = HtmlUtils.htmlEscape(value);
                System.out.println("escapeHTMLvalue:" + value);
            }
            if (escapeJavaScript) {
                value = JavaScriptUtils.javaScriptEscape(value);
                System.out.println("escapeJavaScriptvalue:" + value);
            }
            if(escapeSQL){
                value = StringEscapeUtils.escapeSql(value);
                System.out.println("escapeSQLvalue:" + value);
            }

            setValue(value);
        }
    }
}

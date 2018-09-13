package com.fykj.kernel.excel;

import com.fykj.kernel._c.model.JModel;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:11
 * Description:
 **/
public class ExportExcelFile implements JModel {

    private String relativePath;
    private String nginxPath;

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getNginxPath() {
        return nginxPath;
    }

    public void setNginxPath(String nginxPath) {
        this.nginxPath = nginxPath;
    }
}

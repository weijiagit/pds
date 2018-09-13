package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JModel;
import com.fykj.pds.project.model.AttractProject;

import java.util.List;


public class AttractProjectInfo extends AttractProject implements JModel {
    /**
     *入院协议扫描件list
     */
    private List<AttractProjectAttachment> protocolScanAttachmentList;

    public List<AttractProjectAttachment> getProtocolScanAttachmentList() {
        return protocolScanAttachmentList;
    }

    public void setProtocolScanAttachmentList(List<AttractProjectAttachment> protocolScanAttachmentList) {
        this.protocolScanAttachmentList = protocolScanAttachmentList;
    }
}

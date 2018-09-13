package com.fykj._b._core.attachment.service.impl;

import com.fykj._b._core.attachment.model.Attachment;
import com.fykj._b._core.attachment.service.AttachmentService;
import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj._b._core.attachment.vo.AttachmentRecordVO;
import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.util.Copy;
import com.fykj.util.JStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AttachmentServiceImpl extends ServiceSupport implements AttachmentService {

    private SingleEntityManager<Attachment> attachmentEntityManager = SingleEntityManagerGetter.get()
            .getInstance(Attachment.class);

    @Autowired
    private _Cfg cfg;

    @Override
    public String saveAttachment(AttachmentInfo attachmentInfo) {
        Attachment attachment = Copy.simpleCopy(attachmentInfo, Attachment.class);
        attachmentEntityManager.saveOnly(attachment);
        return attachment.getId();
    }


    private <T extends AttachmentInfo> T then(T attachmentInfo) {
        String path = attachmentInfo.getPath();
        if (JStringUtils.isNotNullOrEmpty(path)) {
            attachmentInfo.setUri(cfg.getFile().getHost() + "/" + attachmentInfo.getPath());
        }
        return attachmentInfo;
    }

    @Override
    public AttachmentRecordVO getAttachment(String id) {
        AttachmentRecordVO attachmentRecordVO = attachmentEntityManager.singleEntityQuery2()
                .active(id)
                .ready().model(AttachmentRecordVO.class);
        return then(attachmentRecordVO);

    }

    /**
     * 删除附件
     *
     * @param id
     */
    @Override
    public void deleteAttachmentById(String id) {
        if (StringUtils.isNotEmpty(id)) {
            Attachment attachment = attachmentEntityManager.getById(id);
            attachmentEntityManager.delete(attachment);
        }
    }

    @Override
    public void deleteAttachmentByFkId(String fkId) {
        Map<String, Object> mp = new HashMap<>();
        String sql = " DELETE FROM t_attachment WHERE fk_id =:fkId";
        mp.put("fkId", fkId);
        nativeQuery().setSql(sql).setParams(mp).setUpdate(true).model();
    }

    @Override
    public void deleteAttachById(String id) {
        Map<String, Object> mp = new HashMap<>();
        String sql = " DELETE FROM t_attachment WHERE id =:id";
        mp.put("id", id);
        nativeQuery().setSql(sql).setParams(mp).setUpdate(true).model();
    }

    /**
     * 根据主键ID查询附件列表
     *
     * @param fkId
     * @return
     */
    @Override
    public List<Attachment> selectAttachmentList(String fkId) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id,t.name as name,t.path as path ,t.suffix as suffix,t.fk_id as fkId");
        sql.append("  from t_attachment t ");
        sql.append(" where t.fk_id = :fkId and t.is_available = :isAvailable ");

        Map<String, Object> params = new WeakHashMap<String, Object>();
        params.put("fkId", fkId);
        params.put("isAvailable", Availability.available.ordinal());

        return nativeQuery().setSql(sql.toString()).setParams(params).models(Attachment.class);
    }

    /**
     * 根据主键ID查询附件列表
     *
     * @param fkId
     * @return
     */
    @Override
    public List<AttachmentInfo> getAttachmentList(String fkId) {

        List<AttachmentInfo> attachmentInfoLists = new ArrayList<>();

        List<Attachment> attachmentLists = selectAttachmentList(fkId);
        Optional.ofNullable(attachmentLists).ifPresent(attachmentList -> {
            for (Attachment attachment : attachmentList) {
                if (JStringUtils.isNotNullOrEmpty(attachment.getId())) {
                    AttachmentInfo attach = getAttachment(attachment.getId());
                    attachmentInfoLists.add(attach);
                }
            }
        });

        return attachmentInfoLists;
    }

    /**
     * 共通附件处理
     *
     * @param attachmentIds
     * @param pkId
     */
    @Override
    public void uploadFiles(String attachmentIds, String pkId) {
        Optional.ofNullable(attachmentIds).ifPresent(attachmentId -> {
            for (String id : attachmentId.split(",")) {
                if (JStringUtils.isNotNullOrEmpty(id)) {
                    Attachment attachment = attachmentEntityManager.getById(id);
                    if (attachment != null) {
                        attachment.setFkId(pkId);
                        attachmentEntityManager.updateOnly(attachment);
                    }

                }
            }
        });
    }

}

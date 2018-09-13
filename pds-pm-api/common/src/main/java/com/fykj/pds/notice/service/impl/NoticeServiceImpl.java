package com.fykj.pds.notice.service.impl;

import com.fykj._b._core.Constants;
import com.fykj._b._core.attachment.model.Attachment;
import com.fykj._b._core.attachment.service.AttachmentService;
import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.notice.constant.NoticeConst;
import com.fykj.pds.notice.model.Notice;
import com.fykj.pds.notice.service.NoticeService;
import com.fykj.pds.notice.vo.NoticeAddInVO;
import com.fykj.pds.notice.vo.NoticeEditInVO;
import com.fykj.pds.notice.vo.NoticePageInVO;
import com.fykj.pds.notice.vo.NoticePageOutVO;
import com.fykj.util.Copy;
import com.fykj.util.JStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Author: songzhonglin
 * Date: 2017/11/7
 * Time: 14:57
 * Description: 通知公告实现类
 **/

@Service
@Transactional
public class NoticeServiceImpl extends ServiceSupport implements NoticeService {

    private SingleEntityManager<Notice> internalNoticeServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(Notice.class);

    private SingleEntityManager<Attachment> attachmentEntityManager = SingleEntityManagerGetter.get()
            .getInstance(Attachment.class);

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private LoginMessageService loginMessageService;

    /**
     * 后台通知公告查询
     *
     * @param vo
     * @param page
     * @return
     */
    @Override
    public JPage<NoticePageOutVO> getNoticePage(NoticePageInVO vo, SimplePageRequest page) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.content as content, DATE_FORMAT(t.create_date,'%Y-%m-%d') as createDateFormat, t.is_top as isTop, t.state as state, t.user_name as userName ");
        sql.append("  from t_notice t ");
        sql.append(" where t.is_available = :isAvailable ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        if (StringUtils.isNotBlank(vo.getContent())) {
            sql.append(" and t.content like :content ");
            params.put("content", "%" + vo.getContent() + "%");
        }

        sql.append(" order by  t.state asc,t.is_top desc , t.modify_date desc ");


        params.put("isAvailable", Availability.available.ordinal());

        return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, NoticePageOutVO.class);
    }

    /**
     * 新增通知公告
     *
     * @param pageNotice
     */
    @Override
    public Notice addNotice(NoticeAddInVO pageNotice) {
        Notice notice = new Notice();
        Copy.simpleCopyExcludeNull(pageNotice, notice);
        // 未置顶
        notice.setIsTop(NoticeConst.TOP_OFF.getVal());
        // 未发布
        notice.setState(NoticeConst.UNPUBLISHED.getVal());
        // 公告提交人
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        notice.setUserName(sessionUser.getUserName());
        internalNoticeServiceImpl.saveOnly(notice);
        // 保存附件
        attachmentService.uploadFiles(pageNotice.getAttachmentIds(), notice.getId());

        return notice;
    }

    /**
     * 编辑通知公告
     *
     * @param pageNotice
     */
    @Override
    public void editNotice(NoticeEditInVO pageNotice) {
        Notice notice = internalNoticeServiceImpl.getById(pageNotice.getId());
        // 内容
        notice.setContent(pageNotice.getContent());
        // 公告提交人
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        notice.setUserName(sessionUser.getUserName());
        internalNoticeServiceImpl.updateOnly(notice);

        // 修改附件之前，先删除之前的附件
        Optional.ofNullable(pageNotice.getDelImageIds()).ifPresent(delImageIds -> {
            for (String imageId : delImageIds.split(",")) {
                if (JStringUtils.isNotNullOrEmpty(imageId)) {
                    attachmentService.deleteAttachById(imageId);
                }
            }
        });
        // 重新上传附件
        attachmentService.uploadFiles(pageNotice.getAttachmentIds(), notice.getId());
    }


    /**
     * 发布通知公告
     *
     * @param id
     */
    @Override
    public void publishNotice(String id) {
        Notice notice = internalNoticeServiceImpl.getById(id);
        // 已发布
        notice.setState(NoticeConst.PUBLISHED.getVal());
        internalNoticeServiceImpl.updateOnly(notice);
    }

    /**
     * 通知公告置顶
     *
     * @param id
     */
    @Override
    public void topNotice(String id) {
        Notice notice = internalNoticeServiceImpl.getById(id);
        // 置顶
        notice.setIsTop(NoticeConst.TOP_ON.getVal());
        internalNoticeServiceImpl.updateOnly(notice);
    }

    /**
     * 取消置顶
     *
     * @param id
     */
    @Override
    public void cancelTopNotice(String id) {
        Notice notice = internalNoticeServiceImpl.getById(id);
        // 置顶
        notice.setIsTop(NoticeConst.TOP_OFF.getVal());
        internalNoticeServiceImpl.updateOnly(notice);
    }

    /**
     * 删除通知公告
     *
     * @param ids
     */
    @Override
    public void deleteNoticeById(String[] ids) {

        for (String id : ids) {
            deleteNoticeById(id);
        }

    }

    public void deleteNoticeById(String id) {
        if (StringUtils.isNotBlank(id)) {
            Notice notice = internalNoticeServiceImpl.getById(id);
            internalNoticeServiceImpl.delete(notice);
            //记录删除日志
            SessionUser sessionUser = ServerSessionHolder.getSessionUser();
            loginMessageService.saveLoginMessage(Constants.logType.DEL_TYPE,sessionUser,notice.getContent(),Constants.projectModule.NOTICE);


        }
    }

    /**
     * 查看通知公告
     *
     * @param id
     * @return
     */
    @Override
    public NoticePageOutVO getNoticeById(String id) {
        Notice notice = internalNoticeServiceImpl.getById(id);
        NoticePageOutVO vo = new NoticePageOutVO();
        Copy.simpleCopyExcludeNull(notice, vo);
        List<AttachmentInfo> attachmentInfoLists = attachmentService.getAttachmentList(id);
        vo.setAttachmentInfoList(attachmentInfoLists);
        return vo;
    }

    /**
     * 通知公告 首页显示前五条(已发布)
     *
     * @return
     */
    @Override
    public JPage<NoticePageOutVO> selectNoticeForFront(SimplePageRequest page) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(" t.id as id, t.content as content, DATE_FORMAT(t.create_date,'%Y-%m-%d') as createDateFormat, t.is_top as isTop, t.state as state, t.user_name as userName ");
        sql.append("  from t_notice t ");
        sql.append(" where t.state =:state and t.is_available = :isAvailable ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        sql.append(" order by  t.is_top desc , t.modify_date desc ");

        page.setPageSize(3);

        params.put("state", NoticeConst.PUBLISHED.getVal());
        params.put("isAvailable", Availability.available.ordinal());

        return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, NoticePageOutVO.class);
    }
}

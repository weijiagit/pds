package com.fykj.pds.notice.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.notice.model.Notice;
import com.fykj.pds.notice.vo.NoticeAddInVO;
import com.fykj.pds.notice.vo.NoticeEditInVO;
import com.fykj.pds.notice.vo.NoticePageInVO;
import com.fykj.pds.notice.vo.NoticePageOutVO;

/**
 * Author: songzhonglin
 * Date: 2017/11/7
 * Time: 14:57
 * Description: 通知公告接口
 **/
public interface NoticeService {

    /**
     * 通知公告查询
     *
     * @param vo
     * @param page
     * @return
     */
    public JPage<NoticePageOutVO> getNoticePage(NoticePageInVO vo, SimplePageRequest page);

    /**
     * 新增通知公告
     *
     * @param pageNotice
     */
    public Notice addNotice(NoticeAddInVO pageNotice);

    /**
     * 修改通知公告
     *
     * @param pageNotice
     */
    public void editNotice(NoticeEditInVO pageNotice);

    /**
     * 发布通知公告
     *
     * @param id
     */
    public void publishNotice(String id);

    /**
     * 通知公告置顶
     *
     * @param id
     */
    public void topNotice(String id);

    /**
     * 取消置顶
     *
     * @param id
     */
    public void cancelTopNotice(String id);

    /**
     * 删除通知公告
     *
     * @param ids
     */
    public void deleteNoticeById(String[] ids);

    /**
     * 查看通知公告
     *
     * @param id
     * @return
     */
    public NoticePageOutVO getNoticeById(String id);

    /**
     * 通知公告首页显示前五条
     *
     * @return
     */
    public JPage<NoticePageOutVO> selectNoticeForFront(SimplePageRequest page);


}

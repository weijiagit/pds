package com.fykj.pds.leaderMessage.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.leaderMessage.model.LeaderMessage;
import com.fykj.pds.leaderMessage.vo.LeaderMessageAddInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessageEditInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageOutVO;

/**
 * Author: songzhonglin
 * Date: 2017/11/13
 * Time: 14:45
 * Description:
 **/
public interface LeaderMessageService {
    /**
     * 领导留言查询
     *
     * @param vo
     * @param page
     * @return
     */
    public JPage<LeaderMessagePageOutVO> getLeaderMessagePage(LeaderMessagePageInVO vo, SimplePageRequest page);

    /**
     * 新增领导留言
     *
     * @param pageLeaderMessageAddInVO
     */
    public LeaderMessage addLeaderMessage(LeaderMessageAddInVO pageLeaderMessageAddInVO);

    /**
     * 修改领导留言
     *
     * @param pageLeaderMessageEditInVO
     */
    public void editLeaderMessage(LeaderMessageEditInVO pageLeaderMessageEditInVO);

    /**
     * 删除领导留言
     *
     * @param ids
     */
    public void deleteLeaderMessageById(String[] ids);

    /**
     * 查看领导留言
     *
     * @param id
     * @return
     */
    public LeaderMessagePageOutVO getLeaderMessageById(String id);

    /**
     * 通知公告首页显示前五条
     *
     * @return
     */
    public JPage<LeaderMessagePageOutVO> selectLeaderMessageForFront(SimplePageRequest page);

    /**
     * 完成情况
     * @param id
     * @param state
     */
    public void updateFinishStateById(String id,String state);
}

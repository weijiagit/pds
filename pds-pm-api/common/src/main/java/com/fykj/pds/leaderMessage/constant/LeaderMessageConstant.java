package com.fykj.pds.leaderMessage.constant;

/**
 * Author: songzhonglin
 * Date: 2017/12/18
 * Time: 10:52
 * Description:
 **/
public interface LeaderMessageConstant {
    interface LeaderMessageStatus {

        String CODE_TYPE = "LeaderMessageStatus";

        // 完成情况
        String FINISH_OFF = "0";
        String FINISH_ON = "1";
    }

    interface LeaderMessageName {
        String LEAVE_MESSAGE ="指示：";
    }

    interface DelOrUpdateFlag {
        String YES ="1";
        String NO ="2";
    }
}

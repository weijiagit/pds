package com.fykj.pds.work.personWork.service.impl;

import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.work.personWork.constant.PersonWorkConstant;
import com.fykj.pds.work.personWork.model.PersonWork;
import com.fykj.pds.work.personWork.service.PersonWorkService;
import com.fykj.pds.work.personWork.vo.PersonWorkAddInVO;
import com.fykj.pds.work.personWork.vo.PersonWorkPageInVO;
import com.fykj.pds.work.personWork.vo.PersonWorkPageOutVO;
import com.fykj.util.Copy;
import com.fykj.util.JDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: songzhonglin
 * Date: 2017/12/1
 * Time: 14:57
 * Description:
 **/
@Service
@Transactional
public class PersonWorkServiceImpl extends ServiceSupport implements PersonWorkService {

    private SingleEntityManager<PersonWork> internalPersonWorkServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(PersonWork.class);

    /**
     * 个人办公任务查询
     *
     * @param vo
     * @param page
     * @return
     */
    @Override
    public JPage<PersonWorkPageOutVO> getPersonWorkPage(PersonWorkPageInVO vo, SimplePageRequest page) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.task_content as taskContent,t.state as state ," +
                        " DATE_FORMAT(t.task_date,'%Y-%m-%d') as taskDateFormat");
        sql.append("  from t_person_work t ");
        sql.append(" where t.is_available = :isAvailable and t.user_id = :userId ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        if (StringUtils.isNotBlank(vo.getTaskContent())) {
            sql.append(" and t.task_content like :taskContent ");
            params.put("taskContent", "%" + vo.getTaskContent() + "%");
        }

        sql.append(" order by  t.create_date desc ");


        params.put("isAvailable", Availability.available.ordinal());
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        params.put("userId", sessionUser.getId());

        return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, PersonWorkPageOutVO.class);
    }

    /**
     * 新增个人办公任务
     *
     * @param pagePersonWork
     * @return
     */
    @Override
    public PersonWork addPersonWork(PersonWorkAddInVO pagePersonWork) {
        PersonWork personWork = new PersonWork();
        Copy.simpleCopyExcludeNull(pagePersonWork, personWork);
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        personWork.setState(PersonWorkConstant.PersonWorkStatus.STATE_OFF);
        personWork.setTaskDate(JDateUtils.parseDate(pagePersonWork.getTaskDateStr()));
        personWork.setUserId(sessionUser.getId());
        internalPersonWorkServiceImpl.saveOnly(personWork);
        return personWork;
    }

    /**
     * 删除个人办公任务
     *
     * @param ids
     */
    @Override
    public void deletePersonWorkById(String[] ids) {
        for (String id : ids) {
            deletePersonWorkById(id);
        }
    }

    public void deletePersonWorkById(String id) {
        if (StringUtils.isNotBlank(id)) {
            PersonWork personWork = internalPersonWorkServiceImpl.getById(id);
            internalPersonWorkServiceImpl.delete(personWork);
        }
    }

    /**
     * 查看个人办公任务
     *
     * @param id
     * @return
     */
    @Override
    public PersonWorkPageOutVO getPersonWorkById(String id) {
        PersonWork personWork = internalPersonWorkServiceImpl.getById(id);
        PersonWorkPageOutVO vo = new PersonWorkPageOutVO();
        Copy.simpleCopyExcludeNull(personWork, vo);

        return vo;
    }

    /**
     * 个人办公任务状态
     *
     * @param id
     */
    @Override
    public void updateStatusById(String id) {
        PersonWork personWork = internalPersonWorkServiceImpl.getById(id);
        personWork.setState(PersonWorkConstant.PersonWorkStatus.STATE_ON);

        internalPersonWorkServiceImpl.updateOnly(personWork);
    }
}

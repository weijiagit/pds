package com.fykj._b._core.departGovernment.service.impl;

import com.fykj._b._core.cache.DictionaryCache;
import com.fykj._b._core.departGovernment.DepartGovernmentCodesTable;
import com.fykj._b._core.departGovernment.model.DepartGovernment;
import com.fykj._b._core.departGovernment.service.DepartGovernmentService;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentAddInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentEditInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentPageInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentPageOutVO;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.department.service.impl.DepartmentServiceImpl;
import com.fykj.util.Copy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by weijia on 2017/12/21.
 *
 */
@Service
@Transactional
public class DepartGovernmentServiceImpl extends ServiceSupport implements DepartGovernmentService {

    private SingleEntityManager<DepartGovernment> internalDepartGovernmentServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(DepartGovernment.class);

    private SingleEntityManager<Department> internalDepartmentServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(Department.class);

    @Autowired
    private DictionaryCache dictionaryCache;

    @Autowired
    private DepartmentSerive departmentSerive;

    /**
     * 查询部门行政审批列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @Override
    public JPage<DepartGovernmentPageOutVO> getDepartGovernmentPage(DepartGovernmentPageInVO vo, SimplePageRequest pageVo) {

        StringBuilder jpql = new StringBuilder("select");
        jpql.append(
                " t.id as id,t.depart_id as departId,t.image_path as imagePath,t.image_text as imageText,t.show_pic as showPic,t.sequence as sequence");
        jpql.append(" from t_depart_government t");
        jpql.append(" where t.is_available = :isAvailable");
        Map<String, Object> params = new HashMap<>();
        params.put("isAvailable", Availability.available.ordinal());
        if (StringUtils.isNotBlank(vo.getImageText())) {
            jpql.append(" AND t.image_text like :imageText");
            params.put("imageText", "%"+vo.getImageText()+"%");
        }

        jpql.append(" order by t.sequence");

        JPage<DepartGovernmentPageOutVO> page= nativeQuery().setSql(jpql.toString()).setParams(params).modelPage(pageVo, DepartGovernmentPageOutVO.class);

        page.getContent().forEach(departGovernmentPageOutVO -> {
            then(departGovernmentPageOutVO);
        });
        return page;
    }

    /**
     * the return one is same as the parameter
     *
     * @param departGovernmentPageOutVO
     * @return
     */
    private DepartGovernmentPageOutVO then(DepartGovernmentPageOutVO departGovernmentPageOutVO) {
        Department department = departmentSerive.getDepartmentByRemoteId(departGovernmentPageOutVO.getDepartId());
        departGovernmentPageOutVO.setDepartName(department.getName());
        return departGovernmentPageOutVO;
    }
    /**
     * 保存部门行政审批
     *
     * @param departGovernment
     * @return
     */
    @Override
    public void saveDepartGovernment(DepartGovernment departGovernment) {
        internalDepartGovernmentServiceImpl.saveOnly(departGovernment);
    }

    /**
     * 逻辑删除部门行政审批
     *
     * @param ids
     * @return
     */
    @Override
    public void removeSysUser(String[] ids) {
        DepartGovernment departGovernment = null;
        for (String id : ids) {
            if (StringUtils.isNotBlank(id)) {
                departGovernment = internalDepartGovernmentServiceImpl.getById(id);
                internalDepartGovernmentServiceImpl.delete(departGovernment);
            }
        }
    }

    /**
     * 根据id获取部门审批信息
     *
     * @param id
     * @return
     */
    @Override
    public DepartGovernmentPageOutVO getDepartGovernmentById(String id) {
        DepartGovernmentPageOutVO departGovernmentPageOutVO = new DepartGovernmentPageOutVO();
        DepartGovernment departGovernment = internalDepartGovernmentServiceImpl.getById(id);
        Copy.simpleCopyExcludeNull(departGovernment, departGovernmentPageOutVO);

        Department department = departmentSerive.getDepartmentByRemoteId(departGovernment.getDepartId());
        departGovernmentPageOutVO.setDepartName(department.getName());
        departGovernmentPageOutVO.setDepartId(department.getRemoteId());
        //字典转换
        departGovernmentPageOutVO.setShowPicStr(dictionaryCache.getDictDataName(DepartGovernmentCodesTable.ShowPic.CODE, departGovernment.getShowPic()));

        return departGovernmentPageOutVO;
    }

    /**
     * 编辑部门审批信息
     *
     * @param InVO
     * @return
     */
    @Override
    public void editDepartGovernment(DepartGovernmentEditInVO InVO){
        DepartGovernment departGovernment = internalDepartGovernmentServiceImpl.getById(InVO.getId());
        departGovernment.setImageText(InVO.getImageText());
        departGovernment.setDepartId(InVO.getDepartId());
        departGovernment.setSequence(InVO.getSequence());
        departGovernment.setShowPic(InVO.getShowPic());
        internalDepartGovernmentServiceImpl.updateOnly(departGovernment);
    }
}

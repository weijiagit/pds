package com.fykj.pds.task.service.impl;

import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.task.constant.TaskConstant;
import com.fykj.pds.task.model.TableField;
import com.fykj.pds.task.service.TableFieldService;
import com.fykj.pds.task.vo.TableFieldPageInVO;
import com.fykj.pds.task.vo.TableFieldPageOutVO;
import com.fykj.util.JStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 16:16
 * Description:
 **/
@Service
@Transactional
public class TableFieldServiceImpl extends ServiceSupport implements TableFieldService {

    private SingleEntityManager<TableField> internalTableFieldServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(TableField.class);

    /**
     * 表字段查询
     *
     * @return
     */
    @Override
    public List<TableFieldPageOutVO> queryTableFieldList() {

        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.table_name as tableName, t.field_name as fieldName , t.is_chk as isChk");
        sql.append("  from t_table_field t ");
        sql.append(" where t.is_available = :isAvailable ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        sql.append(" order by t.create_date asc ");

        params.put("isAvailable", Availability.available.ordinal());

        return nativeQuery().setSql(sql.toString()).setParams(params).models(TableFieldPageOutVO.class);
    }

    /**
     * 首页选择显示列
     *
     * @param pageTableField
     * @return
     */
    @Override
    public void addTableField(TableFieldPageInVO pageTableField) {
        // 选中的
        processTableField(pageTableField.getSelectedList(), TaskConstant.TaskStatus.IS_CHK_ON);
        // 未选中
        processTableField(pageTableField.getUnSelectedList(), TaskConstant.TaskStatus.IS_CHK_OFF);

    }

    /**
     * 显示表字段
     *
     * @return
     */
    @Override
    public List<TableFieldPageOutVO> showTableFieldList() {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.table_name as tableName, t.field_name as fieldName , t.is_chk as isChk");
        sql.append("  from t_table_field t ");
        sql.append(" where t.is_available = :isAvailable ");

        sql.append(" and is_chk = :isChk");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        sql.append(" order by t.create_date asc ");

        params.put("isAvailable", Availability.available.ordinal());
        params.put("isChk", Availability.available.ordinal());

        return nativeQuery().setSql(sql.toString()).setParams(params).models(TableFieldPageOutVO.class);
    }

    /**
     * 首页选择显示列处理
     *
     * @param checkBoxListId
     * @param isChk
     */
    private void processTableField(String checkBoxListId, String isChk) {
        Optional.ofNullable(checkBoxListId).ifPresent(checkBoxListIds -> {
            for (String id : checkBoxListIds.split(",")) {
                if (JStringUtils.isNotNullOrEmpty(id)) {
                    TableField tableField = internalTableFieldServiceImpl.getById(id);
                    tableField.setIsChk(isChk);
                    internalTableFieldServiceImpl.updateOnly(tableField);
                }
            }
        });
    }
}

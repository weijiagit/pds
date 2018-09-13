package com.fykj.pds.task.controller;

import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.controller.baseController;
import com.fykj.kernel.excel.ExcelHelper;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.task.constant.TaskConstant;
import com.fykj.pds.task.dto.TaskExcel;
import com.fykj.pds.task.model.Task;
import com.fykj.pds.task.service.TableFieldService;
import com.fykj.pds.task.service.TaskService;
import com.fykj.pds.task.vo.*;
import com.fykj.util.JStringUtils;
import com.fykj.web.model.SimplePageRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 16:53
 * Description:
 **/
@Controller
@RequestMapping("/task")
public class TaskController extends baseController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskService taskService;

    @Autowired
    private TableFieldService tableFieldService;

    @Autowired
    private DepartmentSerive departmentSerive;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private _Cfg cfg;


    /**
     * 保存工作进展
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/saveTask", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveTask(TaskAddInVO vo) {
        Task task = taskService.addTask(vo);
        return InvokeResult.success(task);
    }

    /**
     * 获取工作进展列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getTaskPage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getTaskPage(TaskPageInVO vo, SimplePageRequestVO pageVo) {
        JPage<TaskPageOutVO> page = taskService.getTaskPage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 删除工作进展
     *
     * @param ids
     * @return
     */
    @RequestMapping(path = "/deleteTask", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteTask(String ids) {
        if (!StringUtils.isNotBlank(ids)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        String[] arr = ids.split(",");

        taskService.deleteTaskById(arr);
        return InvokeResult.success(true);

    }

    /**
     * 工作进展详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/detailTask", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult detailTask(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        TaskPageOutVO taskPageOutVO = taskService.getTaskById(id);
        return InvokeResult.success(taskPageOutVO);
    }

    /**
     * 工作进展 首页显示前五条
     *
     * @return
     */
    @RequestMapping(path = "/queryTaskFront", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult queryTaskFront(SimplePageRequestVO pageVo) {

        JPage<TaskPageOutVO> page = taskService.selectTaskForFront(
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));

        return InvokeResult.success(page);
    }

    /**
     * 首页工作进展选择列table名称查询
     *
     * @return
     */
    @RequestMapping(path = "/queryTaskTableFieldName", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult queryTaskTableFieldName() {
        List<TableFieldPageOutVO> tableFieldPageOutVOList = tableFieldService.queryTableFieldList();

        return InvokeResult.success(tableFieldPageOutVOList);
    }

    /**
     * 首页工作进展table名称查询
     *
     * @return
     */
    @RequestMapping(path = "/showTableFieldName", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult showTableFieldName() {
        List<TableFieldPageOutVO> tableFieldPageOutVOList = tableFieldService.showTableFieldList();

        return InvokeResult.success(tableFieldPageOutVOList);
    }


    /**
     * 首页 工作进展 选择保存列
     *
     * @param tableFieldPageInVO
     * @return
     */
    @RequestMapping(path = "/addTableField", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult addTableField(TableFieldPageInVO tableFieldPageInVO) {
        tableFieldService.addTableField(tableFieldPageInVO);
        return InvokeResult.success(true);
    }


    /**
     * 根据部门Id查找用户
     *
     * @return
     */
    @RequestMapping(path = "/selectUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult selectUserInfo(String deptId) {
        List<SysUserPageOutVO> sysUserPageOutList = taskService.selectUserInfo(deptId);
        return InvokeResult.success(sysUserPageOutList);
    }

    /**
     * 完成情况
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/updateTaskFinishStatusById", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult updateTaskFinishStatusById(String id) {
        taskService.updateTaskFinishStatusById(id);
        return InvokeResult.success(true);

    }

    /**
     * 完成情况
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/editTaskFinishStatusById", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult editTaskFinishStatusById(String id) {
        taskService.updateFinishStatusById(id);
        return InvokeResult.success(true);

    }

    /**
     * 核查情况
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/editTaskInspectStatusById", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult editTaskInspectStatusById(String id) {
        taskService.updateInspectStatusById(id);
        return InvokeResult.success(true);

    }

    /**
     * 核查情况
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/updateTaskInspectStatusById", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult updateTaskInspectStatusById(String id) {
        taskService.updateTaskInspectStatusById(id);
        return InvokeResult.success(true);

    }

    /**
     * 导出excel验证
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/validExportExcel", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult validExportExcel(TaskPageInVO vo) {
        List<TaskPageOutVO> taskPageOutVOList = taskService.exportExcelQuery(vo);
        if (taskPageOutVOList == null || taskPageOutVOList.size() <= 0) {
            return InvokeResult.bys(TaskConstant.TaskName.FILE_EXPORT_SIZE);
        }
        return InvokeResult.success(true);

    }

    /**
     * 导出excel
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/taskExportExcel", method = RequestMethod.POST)
    @ResponseBody
    public void taskExportExcel(TaskPageInVO vo, HttpServletResponse response) {
        List<TaskPageOutVO> taskPageOutVOList = taskService.exportExcelQuery(vo);
        List<TaskExcel> taskExcelList = new ArrayList<>();
        for (TaskPageOutVO taskPageOutVO : taskPageOutVOList) {
            Department  department = departmentSerive.getDepartmentById(taskPageOutVO.getResponsibleDepartment());
            String departmentName ="";
            if(department != null){
                departmentName = department.getName();
            }
            if(!StringUtils.isNotBlank(taskPageOutVO.getEndDateFormat())){
                taskPageOutVO.setEndDateFormat(TaskConstant.TaskStatus.SPIRT);
            }
            String userName = "";
            if(StringUtils.isNoneBlank(taskPageOutVO.getResponsiblePeople())){
                userName = sysUserService.getSysUserById(taskPageOutVO.getResponsiblePeople()).getName();
            }else{
                userName = TaskConstant.TaskStatus.SPIRT;
            }
            TaskExcel taskExcel = new TaskExcel(JStringUtils.deCode(taskPageOutVO.getProjectName()), JStringUtils.deCode(taskPageOutVO.getSubcontractLeader()),
                    JStringUtils.deCode(taskPageOutVO.getWorkContent()), departmentName,
                    userName, taskPageOutVO.getEndDateFormat(),
                    taskPageOutVO.getCreateDateFormat(),JStringUtils.deCode(taskPageOutVO.getProgressWork()),JStringUtils.deCode(taskPageOutVO.getBatch()),
                    taskPageOutVO.getIsDepartFinish(), taskPageOutVO.getIsFinish());
            taskExcelList.add(taskExcel);
        }
        ExcelHelper excelHelper = new ExcelHelper();
        byte[] bytes = excelHelper.excel2003(excelHelper.readRecord(taskExcelList), TaskConstant.TaskName.SHEET_NAME);
        String fileName = UUID.randomUUID() + TaskConstant.TaskName.SUFFIX;
        try {
            excelHelper.excelHelper(response, bytes, fileName);
        } catch (Exception e) {

        }
    }

    /**
     * 查询批次
     *
     * @return
     */
    @RequestMapping(path = "/queryBatch", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult queryBatch() {
       List<TaskPageOutVO> taskPageOutVOList = taskService.queryBatch();
        return InvokeResult.success(taskPageOutVOList);
    }
}

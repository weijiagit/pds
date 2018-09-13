package com.fykj.pds.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.fykj.util.JStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel.excel.ExcelHelper;
import com.fykj.pds.project.ProjectReport;
import com.fykj.pds.project.excelVo.ProjectReportExcel;
import com.fykj.pds.project.service.ProjectReportService;
import com.fykj.pds.project.vo.ProjectReportInVO;
import com.fykj.pds.project.vo.ProjectReportOutVO;
import com.fykj.pds.project.vo.ProjectReportTextVo;
import com.fykj.pds.project.vo.ProjectTotalOutVO;



@Controller
@RequestMapping("/projectReport")
public class ProjectReportController {
	
	@Autowired
	private ProjectReportService projectReportService;
	
	@RequestMapping(path="/pageProjectReport",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult pageProjectReport(ProjectReportInVO projectReportInVo) {
		List<ProjectReportOutVO> page = projectReportService.pageProjectReport(projectReportInVo);
		return InvokeResult.success(page);
	}
	
	@RequestMapping(path="/getTotal",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getTotal(ProjectReportInVO projectReportInVo) {
		List<ProjectTotalOutVO> page = projectReportService.getTotal(projectReportInVo);
		return InvokeResult.success(page);
	}
	
	@RequestMapping(path="/getText",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getText(ProjectReportInVO projectReportInVo) {
		List<ProjectReportTextVo> page = projectReportService.getText(projectReportInVo);
		return InvokeResult.success(page);
	}
	
	/**
     * 导出excel
     *
     * @param projectReportInVo
	 * @param response
     * @return
     */
    @RequestMapping(path = "/projectReportExportExcel", method = RequestMethod.POST)
    @ResponseBody
    public void projectReportExportExcel(ProjectReportInVO projectReportInVo, HttpServletResponse response) {
    	List<ProjectReportOutVO> list = projectReportService.pageProjectReport(projectReportInVo);
        List<ProjectReportExcel> proReportExcelList = new ArrayList<>();
        for (ProjectReportOutVO projectReport : list) {
        	ProjectReportExcel projectReportExcel = new ProjectReportExcel(projectReport.getIndustryClassificationStr(),
					JStringUtils.deCode(projectReport.getCompanyName()),JStringUtils.deCode(projectReport.getProjectName()),JStringUtils.deCode(projectReport.getConstructionContentScale()),
        			projectReport.getTotalInvestmentStr(),projectReport.getImplementScheduleStr(),projectReport.getProjectAttributeStr());
        	proReportExcelList.add(projectReportExcel);
        }
        ExcelHelper excelHelper = new ExcelHelper();
        byte[] bytes = excelHelper.excel2003(excelHelper.readRecord(proReportExcelList), ProjectReport.SHEET_NAME);
//        String fileName = ProjectReport.SHEET_NAME + ProjectReport.SUFFIX;
		String fileName = UUID.randomUUID() + ProjectReport.SUFFIX;
        try {
            excelHelper.excelHelper(response, bytes, fileName);
        } catch (Exception e) {

        }
    }
}

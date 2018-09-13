package com.fykj.pds.project.service;

import java.util.List;

import com.fykj.pds.project.vo.ProjectReportInVO;
import com.fykj.pds.project.vo.ProjectReportOutVO;
import com.fykj.pds.project.vo.ProjectReportTextVo;
import com.fykj.pds.project.vo.ProjectTotalOutVO;


public interface ProjectReportService {
	List<ProjectReportOutVO> pageProjectReport(ProjectReportInVO projectReportInVo);
	
	List<ProjectTotalOutVO> getTotal(ProjectReportInVO projectReportInVo);
	
	List<ProjectReportTextVo> getText(ProjectReportInVO projectReportInVo);
}

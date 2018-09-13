package com.fykj.pds.project.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.cache.DictionaryCache;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.pds.project.ProjectCodesTable.ConstructionNature;
import com.fykj.pds.project.ProjectCodesTable.ImplementSchedule;
import com.fykj.pds.project.ProjectCodesTable.ImportentProject;
import com.fykj.pds.project.ProjectCodesTable.IndustryClassification;
import com.fykj.pds.project.ProjectCodesTable.Iscityimp;
import com.fykj.pds.project.ProjectCodesTable.Isdistrictimp;
import com.fykj.pds.project.ProjectCodesTable.Isprovinceimp;
import com.fykj.pds.project.ProjectCodesTable.ProjectAttribute;
import com.fykj.pds.project.ProjectReport;
import com.fykj.pds.project.service.ProjectReportService;
import com.fykj.pds.project.vo.ProjectReportInVO;
import com.fykj.pds.project.vo.ProjectReportOutVO;
import com.fykj.pds.project.vo.ProjectReportTextVo;
import com.fykj.pds.project.vo.ProjectTotalOutVO;


@Service
@Transactional
public class ProjectReportServiceImpl extends ServiceSupport implements ProjectReportService {

    @Autowired
    private DictionaryCache dictionaryCache;
	
	@Override
	public List<ProjectReportOutVO> pageProjectReport(ProjectReportInVO projectReportInVo) {
		Map<String, Object> map = new HashMap<String, Object>();
        String sql = generateSql(projectReportInVo,map);
		DecimalFormat format = new DecimalFormat("#.00");
        List<ProjectReportOutVO> reportOutVo = nativeQuery().setSql(sql).setParams(map).models(ProjectReportOutVO.class);
        for (ProjectReportOutVO projectReportOutVO : reportOutVo) {
        	projectReportOutVO.setProjectAttributeStr(dictionaryCache.getDictDataName(ProjectAttribute.CODE, projectReportOutVO.getProjectAttribute()));
        	projectReportOutVO.setIndustryClassificationStr(dictionaryCache.getDictDataName(IndustryClassification.CODE, projectReportOutVO.getIndustryClassification()));
        	projectReportOutVO.setImplementScheduleStr(dictionaryCache.getDictDataName(ImplementSchedule.CODE, projectReportOutVO.getImplementSchedule()));
        	projectReportOutVO.setTotalInvestmentStr(format.format(projectReportOutVO.getTotalInvestment()));

		}
        return reportOutVo;
	}

	@Override
	public List<ProjectTotalOutVO> getTotal(ProjectReportInVO projectReportInVo) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 String sql=generateSql(projectReportInVo,map);
		 String sqltotal="select count(1) as totalProject,'total' as staticYear,"+
		 		  "sum(a.totalInvestment) as totalInvestment from ( "+
		 		 sql+" ) as a";
		 ProjectTotalOutVO totalProject = nativeQuery().setSql(sqltotal).setParams(map).model(ProjectTotalOutVO.class);
		 if(totalProject.getTotalInvestment()==null){
			 totalProject.setTotalInvestment(0D);
		 }
		 String sqlYear="select count(1) as totalProject,a.staticYear as staticYear,"+
		 		  "sum(a.totalInvestment) as totalInvestment from ( "+
		 		 sql+" ) as a GROUP BY a.staticYear";
		 List<ProjectTotalOutVO> yearProject = nativeQuery().setSql(sqlYear).setParams(map).models(ProjectTotalOutVO.class);
		 yearProject.add(0, totalProject);
		 return yearProject;
	}
	
	@Override
	public List<ProjectReportTextVo> getText(ProjectReportInVO projectReportInVo) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 List<ProjectReportTextVo> textList=new ArrayList<ProjectReportTextVo>();
		 String sql=generateSql(projectReportInVo,map);
		 String sqltotal="select count(1) as totalProject,'total' as staticYear,"+
		 		  "sum(a.totalInvestment) as totalInvestment from ( "+
		 		 sql+" ) as a";
		 ProjectTotalOutVO totalProject = nativeQuery().setSql(sqltotal).setParams(map).model(ProjectTotalOutVO.class);
		
		 ProjectReportTextVo textVo=new ProjectReportTextVo();
		 if(totalProject.getTotalProject()!=null && totalProject.getTotalProject()!=0L){
			 textVo.setType(ProjectReport.TOTAL_TYPE);
			 textVo.setYearText(projectReportInVo.getStartTime()+"——"+projectReportInVo.getEndTime());
			 if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.PROVINCE)) {
				 textVo.setTitle("省重点项目"+totalProject.getTotalProject().toString()+"个");
	         } else if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.CITY)) {
	        	 textVo.setTitle("市重点项目"+totalProject.getTotalProject().toString()+"个");
	         } else if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.DISTRICT)) {
	        	 textVo.setTitle("区重点项目"+totalProject.getTotalProject().toString()+"个");
	         }else{
	        	 textVo.setTitle("非竣工项目"+totalProject.getTotalProject().toString()+"个");
	         }
//			 textVo.setTotalInvestmentText("总投资"+totalProject.getTotalInvestment().toString()+"元");
			 textVo.setTotalInvestmentText(totalProject.getTotalInvestment().toString());
			 textList.add(textVo);
		 }

		 
		 
		 
		 String implementSql=generateSql2(projectReportInVo, map, ProjectReport.IMPLEMENT_TYPE);
		 List<ProjectTotalOutVO> implementProject = nativeQuery().setSql(implementSql).setParams(map).models(ProjectTotalOutVO.class);
		 for (ProjectTotalOutVO projectTotalOutVO : implementProject) {
			 if(projectTotalOutVO.getTotalProject()!=null && totalProject.getTotalProject()!=0L){
				 textVo=new ProjectReportTextVo();
				 textVo.setType(ProjectReport.IMPLEMENT_TYPE);
				 String type=projectTotalOutVO.getType();
//				 textVo.setTotalInvestmentText("总投资"+projectTotalOutVO.getTotalInvestment().toString()+"元");
				 textVo.setTotalInvestmentText(projectTotalOutVO.getTotalInvestment().toString());
				 switch (type) {
					case ImplementSchedule.BEFORE:
						textVo.setTitle("前期项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case ImplementSchedule.PROCEED:
						textVo.setTitle("新开工项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case ImplementSchedule.CONTINUE:
						textVo.setTitle("续建项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case ImplementSchedule.END:
						textVo.setTitle("竣工项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					 case ImplementSchedule.NOTSTARTED:
						 textVo.setTitle("未开始项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						 break;
					default:
						break;
				}
				textList.add(textVo);
			 }
		}
		 
		 
		 String industrySql=generateSql2(projectReportInVo, map, ProjectReport.INDUSTRY_TYPE);
		 List<ProjectTotalOutVO> instryProject = nativeQuery().setSql(industrySql).setParams(map).models(ProjectTotalOutVO.class);
		 for (ProjectTotalOutVO projectTotalOutVO : instryProject) {
			 if(projectTotalOutVO.getTotalProject()!=null && totalProject.getTotalProject()!=0L){
				 textVo=new ProjectReportTextVo();
				 textVo.setType(ProjectReport.INDUSTRY_TYPE);
				 String type=projectTotalOutVO.getType();
//				 textVo.setTotalInvestmentText("总投资"+projectTotalOutVO.getTotalInvestment().toString()+"元");
				 textVo.setTotalInvestmentText(projectTotalOutVO.getTotalInvestment().toString());
				 switch (type) {
					case IndustryClassification.INDUSTRY:
						textVo.setTitle("工业项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.BASE:
						textVo.setTitle("基础设施项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.SERVICE:
						textVo.setTitle("服务业项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.PPP:
						textVo.setTitle("PPP项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.REALTY:
						textVo.setTitle("房地产项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.MEDICAL:
						textVo.setTitle("医疗卫生项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.EDUCATIOn:
						textVo.setTitle("教育项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.AGRICULTURE:
						textVo.setTitle("农林水利项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					case IndustryClassification.OTHER:
						textVo.setTitle("其他项目"+projectTotalOutVO.getTotalProject().toString()+"个");
						break;
					default:
						break;
				}
				textList.add(textVo);
			 }
		}
		 
		return textList;
	}
	
	
	
	private String generateSql(ProjectReportInVO projectReportInVo,Map<String, Object> map ){
		String sql = "SELECT t.id as id, t.project_number as projectNumber," +
                "t.project_name as projectName," +
                "t.company_name as companyName," +
                "t.total_investment as totalInvestment ," +
                "t.construction_content_scale as constructionContentScale ," +
                "t.implement_schedule as implementSchedule ," +
                "t.project_attribute as projectAttribute ," +
                "t.industry_classification as industryClassification ," +
                "DATE_FORMAT(t.actual_begin_date, '%Y') as staticYear " +
                "from t_project t " +
                "where t.is_available = 1 and project_fill_status=1 " ;
//               + "and :startTime <=DATE_FORMAT(t.actual_begin_date, '%Y-%m-%d') "+
//                "and DATE_FORMAT(t.actual_begin_date, '%Y-%m-%d')<=:endTime ";
		// 实施进度
        if(StringUtils.isNotBlank(projectReportInVo.getImplementSchedule())){
        	sql=sql+" and t.implement_schedule=:implementSchedule ";
        	map.put("implementSchedule", projectReportInVo.getImplementSchedule());
        }else{
			sql=sql+" and t.implement_schedule !=:implementSchedule ";
			map.put("implementSchedule", ImplementSchedule.END);
		}
        if(StringUtils.isNotBlank(projectReportInVo.getIndustryClassification())){
        	sql=sql+" and t.industry_classification=:industryClassification ";
        	map.put("industryClassification", projectReportInVo.getIndustryClassification());
        }
        if(StringUtils.isNotBlank(projectReportInVo.getImportentProject())){
        	if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.PROVINCE)) {
        		sql = sql + " and t.isprovinceimp = :isprovinceimp ";
        		map.put("isprovinceimp", Isprovinceimp.YES);
            } else if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.CITY)) {
            	sql = sql + " and t.iscityimp = :iscityimp ";
            	map.put("iscityimp", Iscityimp.YES);
            } else if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.DISTRICT)) {
            	sql = sql + " and t.isdistrictimp = :isdistrictimp ";
            	map.put("isdistrictimp", Isdistrictimp.YES);
            }
        }
//        if(StringUtils.isNotBlank(projectReportInVo.getIsZs()) && projectReportInVo.getIsZs().equals(IS_ZS.YES)){
//        	sql=sql+" UNION "+
//        			"SELECT '/' AS projectNumber," +
//	                "'/' AS projectName," +
//	                "z.company_name as companyName," +
//	                "z.total_investment as totalInvestment ," +
//	                "DATE_FORMAT(z.create_date, '%Y') as staticYear ," +
//	                "'是' as isZs " +
//	                " from t_project_attract z " +
//	                " where z.is_available = 1 and z.back_flag=0 "+
//	                "and :startTime <=DATE_FORMAT(z.create_date, '%Y-%m-%d') "+
//	                "and DATE_FORMAT(z.create_date, '%Y-%m-%d')<=:endTime  ";
//        }
//        if(StringUtils.isNotBlank(projectReportInVo.getStartTime())){
//        	map.put("startTime", projectReportInVo.getStartTime());
//        }
//        if(StringUtils.isNotBlank(projectReportInVo.getEndTime())){
//        	map.put("endTime", projectReportInVo.getEndTime());
//        }
        sql=sql+" or transfer_status=1";
        return sql;
	}
	
	private String generateSql2(ProjectReportInVO projectReportInVo,Map<String, Object> map,String type ){
		String sql = "SELECT count(1) as totalProject," +
                "sum(t.total_investment) as totalInvestment,";
		if(type.equals(ProjectReport.INDUSTRY_TYPE)){
			sql=sql+"t.industry_classification as type ";
		}else{
			sql=sql+"t.implement_schedule as type ";
		}
        sql=sql+ "from t_project t " +
                "where t.is_available = 1 and project_fill_status=1 ";
//                "and :startTime <=DATE_FORMAT(t.actual_begin_date, '%Y-%m-%d') "+
//                "and DATE_FORMAT(t.actual_begin_date, '%Y-%m-%d')<=:endTime ";
        if(StringUtils.isNotBlank(projectReportInVo.getImplementSchedule())){
        	sql=sql+" and t.implement_schedule=:implementSchedule ";
        	map.put("implementSchedule", projectReportInVo.getImplementSchedule());
        }else {
			sql=sql+" and t.implement_schedule !=:implementSchedule ";
			map.put("implementSchedule", ImplementSchedule.END);
		}
		if(StringUtils.isNotBlank(projectReportInVo.getImportentProject())){
			if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.PROVINCE)) {
				sql = sql + " and t.isprovinceimp = :isprovinceimp ";
				map.put("isprovinceimp", Isprovinceimp.YES);
			} else if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.CITY)) {
				sql = sql + " and t.iscityimp = :iscityimp ";
				map.put("iscityimp", Iscityimp.YES);
			} else if (projectReportInVo.getImportentProject().trim().equals(ImportentProject.DISTRICT)) {
				sql = sql + " and t.isdistrictimp = :isdistrictimp ";
				map.put("isdistrictimp", Isdistrictimp.YES);
			}
		}

//        if(StringUtils.isNotBlank(projectReportInVo.getStartTime())){
//        	map.put("startTime", projectReportInVo.getStartTime());
//        }
//        if(StringUtils.isNotBlank(projectReportInVo.getEndTime())){
//        	map.put("endTime", projectReportInVo.getEndTime());
//        }
        if(type.equals(ProjectReport.INDUSTRY_TYPE)){
			sql=sql+" or transfer_status=1 group by t.industry_classification ";
		}else{
			sql=sql+" or transfer_status=1 group by t.implement_schedule ";
		}
        return sql;
	}

}

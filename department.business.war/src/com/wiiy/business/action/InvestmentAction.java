package com.wiiy.business.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.entity.Investment;
import com.wiiy.business.entity.InvestmentDirector;
import com.wiiy.business.entity.InvestmentInvestor;
import com.wiiy.business.entity.InvestmentProcessAtt;
import com.wiiy.business.entity.InvestmentRegInfo;
import com.wiiy.business.entity.InvestmentVentureType;
import com.wiiy.business.preferences.R;
import com.wiiy.business.preferences.enums.InvestmentStatusEnum;
import com.wiiy.business.preferences.enums.PriorityEnum;
import com.wiiy.business.service.InvestmentDirectorService;
import com.wiiy.business.service.InvestmentInvestorService;
import com.wiiy.business.service.InvestmentProcessAttService;
import com.wiiy.business.service.InvestmentProcessService;
import com.wiiy.business.service.InvestmentService;
import com.wiiy.business.service.InvestmentVentureTypeService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.core.entity.Approval;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.preferences.enums.ApprovalTypeEnum;
import com.wiiy.core.service.export.ApprovalService;
import com.wiiy.core.service.export.DataDictInitService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Pager;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class InvestmentAction extends JqGridBaseAction<Investment>{
	
	private InvestmentService investmentService;
	private InvestmentInvestorService investmentInvestorService;
	private InvestmentDirectorService investmentDirectorService;
	private InvestmentVentureTypeService investmentVentureTypeService;
	private InvestmentProcessService investmentProcessService;
	private InvestmentProcessAttService investmentProcessAttService;

	private Result result;
	private Investment investment;
	private InvestmentDirector investmentDirector;
	private List<InvestmentInvestor> investmentInvestorList;
	private List<InvestmentDirector> invesmentDirectorList;
	private InvestmentRegInfo investmentRegInfo;
	private List<InvestmentProcessAtt> attList;
	private List<DataDict> incubationRouteList;
	private List<DataDict> customerQualificationList;

	private Long id;
	private static Long yetId;
	private Long prevId;
	
	private String ids;
	private Pager pager;
	
	private String name;
	
	private String fileName;
	private InputStream inputStream;
	
	private String type="ALL";
	private List<String> imgSrc;
	private String sourceType;
	
	private boolean connect = true;
	
	/**
	 * 导入企业
	 * @return
	 */
	public String addCustomer(){
		incubationRouteList = new ArrayList<DataDict>();
		customerQualificationList = new ArrayList<DataDict>();
		DataDictInitService dataDictInitService = BusinessActivator.getDataDictInitService();
		if(dataDictInitService!=null){
			incubationRouteList = dataDictInitService.getDataDictByParentId("business.0025");
			customerQualificationList = dataDictInitService.getDataDictByParentId("business.0027");
		}
		result = investmentService.getBeanById(id);
		return "addCustomer";
	}
	
	/**
	 * 业务转交 (原招商人员ID: prevId 转交人员ID:id TYPE:项目、线索 
	 * @return
	 */
	public String assigneTo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		prevId = Long.valueOf(request.getParameter("prevId"));
		result = investmentService.assigneTo(prevId,id,type);
		return JSON;
	} 
	
	public String initZSXM(){
		//result = investmentService.getListByFilter(new Filter(Investment.class).ne("investmentStatus", InvestmentStatusEnum.PARK).orderBy("createTime",Filter.DESC).maxResults(6));
		result = investmentService.getListByLimitNum(6);
		
		return JSON;
	}
	
	public String businessmanSuggestion(){
		result = investmentService.getBeanById(id);
		return "businessmanSuggestion";
	}
	public String updateBusinessmanSuggestion(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setBusinessmanSuggestion(investment.getBusinessmanSuggestion());
		result = investmentService.update(dbBean);
		result.setMsg("招商人员意见保存成功");
		return JSON;
	}
	
	public String submitSettled(){
		result = investmentService.submitSettled(id);
		return JSON;
	}
	
	public String print() {
		fileName = StringUtil.URLEncoderToUTF8("申请单")+".doc";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			investmentService.print(id, out);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "print";
	}
	
	public String select(){
		return SELECT;
	}
	
	public String select2(){
		return SELECT+"2";
	}
	
	public String selectList(){
		return refresh(new Filter(Investment.class));
	}
	
	public String selectList2(){
		return refresh(new Filter(Investment.class));
	}
	
	public String departmentApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"department");
		return JSON;
	}
	
	public String managerApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"manager");
		return JSON;
	}
	
	public String chiefApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"chief");
		return JSON;
	}
	
	public String officeApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"office");
		return JSON;
	}
	
	public String departmentApprovalUpdate(){
		Investment dbBean = investmentService.getBeanById(investment.getId()).getValue();
		Approval approval = dbBean.getDepartmentApproval();
		if(investmentService.updateApproval(approval).isSuccess()){
			result = Result.success(R.Investment.APPROVAL_SUCCESS);
		} else {
			result = Result.failure(R.Investment.APPROVAL_FAILURE);
		}
		return JSON;
	}
	
	public String chiefApprovalUpdate(){
		Investment dbBean = investmentService.getBeanById(investment.getId()).getValue();
		Approval approval = dbBean.getChiefApproval();
		if(investmentService.updateApproval(approval).isSuccess()){
			result = Result.success(R.Investment.APPROVAL_SUCCESS);
		} else {
			result = Result.failure(R.Investment.APPROVAL_FAILURE);
		}
		return JSON;
	}
	
	public String officeApprovalUpdate(){
		Investment dbBean = investmentService.getBeanById(investment.getId()).getValue();
		Approval approval = dbBean.getOfficeApproval();
		if(investmentService.updateApproval(approval).isSuccess()){
			result = Result.success(R.Investment.APPROVAL_SUCCESS);
		} else {
			result = Result.failure(R.Investment.APPROVAL_FAILURE);
		}
		return JSON;
	}
	
	public String approvalView(){
		result = investmentService.getBeanById(id);
			imgSrc = new ArrayList<String>();
			attList = investmentProcessAttService.getListByFilter(new Filter(InvestmentProcessAtt.class).eq("investmentId", id)).getValue();
			Collections.sort(attList, new Comparator<InvestmentProcessAtt>() {
				@Override
				public int compare(InvestmentProcessAtt o1, InvestmentProcessAtt o2) {
					if(o1.getCreateTime()==null)return -1;
					if(o2.getCreateTime()==null)return 1;
					if(o1.getCreateTime().getTime()==o2.getCreateTime().getTime())return 0;
					return o1.getCreateTime().getTime()>o2.getCreateTime().getTime() ? 1 : -1;
				}
			});
			for(InvestmentProcessAtt att:attList){
				imgSrc.add(att.getNewName());
			}
			if(imgSrc.isEmpty()){
				imgSrc=null;
			}
		return "approvalView";
	}
	
	public String wakeUp(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.NEW);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String sleep() {
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.SLEEP);
		result = investmentService.update(dbBean);
		return JSON;
	}

	public String agree() {
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.AGREE);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String disagree() {
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.DISAGREE);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String approval(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.APPROVAL);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String clear(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(null);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String high(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setPriority(PriorityEnum.HIGH);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String middle(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setPriority(PriorityEnum.MIDDLE);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String low(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setPriority(PriorityEnum.LOW);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String generateCode(){
		result = investmentService.generateCode();
		return JSON;
	}
	
	public String saveOrUpdate(){
		String[] enterpriseTypeIds = ServletActionContext.getRequest().getParameterValues("enterpriseTypeIds");
		/*if(investmentDirector==null) investmentDirector = new InvestmentDirector();*/
		if(investmentRegInfo==null) investmentRegInfo = new InvestmentRegInfo();
		investmentDirector.setInvestment(investment);
		investment.setPriority(PriorityEnum.LOW);
		investment.setInvestmentStatus(InvestmentStatusEnum.NEW);
		if(("").equals(investment.getCurrencyId()))investment.setCurrencyId(null);
		/*if(investmentDirector.getDegreeId().equals(""))investmentDirector.setDegreeId(null);*/
		if(("").equals(investmentRegInfo.getCurrencyTypeId()))investmentRegInfo.setCurrencyTypeId(null);
		if(("").equals(investmentRegInfo.getDocumentTypeId()))investmentRegInfo.setDocumentTypeId(null);
		if(("").equals(investmentRegInfo.getRegTypeId()))investmentRegInfo.setRegTypeId(null);
		if(("").equals(investment.getIncubateConfigId())) investment.setIncubateConfigId(null);
		if(("").equals(investment.getTechnicId())) investment.setTechnicId(null);
		result = investmentService.saveOrUpdate(investment,investmentRegInfo,enterpriseTypeIds);
		return JSON;
	}
	
	public String save(){
		result = investmentService.save(investment);
		return JSON;
	}
	
	public String viewFloatbox(){
		return view();
	}
	
	public String view(){
		yetId=id;
		List<InvestmentVentureType> typeList = investmentVentureTypeService.getListByFilter(new Filter(InvestmentVentureType.class).eq("investmentId", id)).getValue();
		StringBuilder sb = new StringBuilder();
		if(typeList.size()>0 && typeList!=null){
			for(InvestmentVentureType it : typeList){
				sb.append(it.getVentureType().getDataValue()).append(",");  
			}
			if(sb.toString().endsWith(","))sb.deleteCharAt(sb.length()-1);
		}
		ServletActionContext.getRequest().setAttribute("typeNames", sb.toString());
		result = investmentService.getBeanById(id);
		Investment it = (Investment) result.getValue();
		if(it.getTechnicId()!=null && it.getTechnicId().length()>0){
			ServletActionContext.getRequest().setAttribute("technic",it.getTechnic().getDataValue());
		}
		if(it.getIncubateConfigId()!=null && it.getIncubateConfigId().length()>0){
			ServletActionContext.getRequest().setAttribute("incubate",it.getIncubateConfig().getDataValue());
		}
		investmentInvestorList = investmentInvestorService.getListByFilter(new Filter(InvestmentInvestor.class).eq("investmentId", id)).getValue();
		invesmentDirectorList = investmentDirectorService.getListByFilter(new Filter(InvestmentDirector.class).eq("investmentId", id)).getValue();
		if(BusinessActivator.getHttpSessionService().isInResourceMap("business_project_allViewBasic")){
			return VIEW;
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_businessPlan_allView")){
			return "businessPlan";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_record_allList")){
			return "investmentArchiveAtt";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_track_allList")){
			return "investmentLog";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_agreement_allList")){
			return "investmentContractAtt";
		}else{
			return "null";
		}
	}
	
	public String edit(){
		List<InvestmentVentureType> typeList = investmentVentureTypeService.getListByFilter(new Filter(InvestmentVentureType.class).eq("investmentId", id)).getValue();
		StringBuilder sb = new StringBuilder();
		if(typeList.size()>0 && typeList!=null){
			for(InvestmentVentureType it : typeList){
				sb.append(it.getVentureTypeId()).append(",");  
			}
			if(sb.toString().endsWith(","))sb.deleteCharAt(sb.length()-1);
		}
		ServletActionContext.getRequest().setAttribute("typeIds", sb.toString());
		result = investmentService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		String[] enterpriseTypeIds = ServletActionContext.getRequest().getParameterValues("enterpriseTypeIds");
		if(("").equals(investment.getIncubateConfigId())) investment.setIncubateConfigId(null);
		if(("").equals(investment.getTechnicId())) investment.setTechnicId(null);
		result = investmentService.update(investment,investmentRegInfo,enterpriseTypeIds);
		return JSON;
	}
	
	public String delete(){		
		if(id!=null&&!id.equals(yetId)){
			result = investmentService.deleteById(id);
			prevId = yetId;
			id=null;
		}else if(id!=null&&id.equals(yetId)){
			result = investmentService.deleteById(id);
			prevId=id=yetId=null;
		}else if(ids!=null){
			result = investmentService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String myList(){
		
		List<Approval> approvals =BusinessActivator.getService(ApprovalService.class).getListByFilter(new Filter(Approval.class).eq("userId",BusinessActivator.getSessionUser().getId()).eq("type",ApprovalTypeEnum.INVESTMENT)).getValue();
		if(approvals != null && approvals.size()>0){
			List<Long> groupIds = new ArrayList<Long>();
			for(Approval approval:approvals){
				groupIds.add(approval.getGroupId());				
			}
			Filter filter = createFilter();
			filter.or(Filter.Eq("creatorId", BusinessActivator.getSessionUser().getId()),Filter.In("id", groupIds.toArray()));			
			setPager(filter);
			result = investmentService.getListByFilter(filter);
		}else{
			Filter filter = createFilter();
			if(sourceType!=null && !"".equals(sourceType)){
				if("importMe".equals(sourceType)){
					filter.eq("importId", BusinessActivator.getSessionUser().getId());
				}
				if("followMe".equals(sourceType)){
					filter.eq("hostId", BusinessActivator.getSessionUser().getId());
				}
			}else{
				filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
			}
			result = investmentService.getListByFilter(filter);
		}
		return "myList";
	}
	
	public String list(){
		Filter filter = createFilter();
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "list";
	}
	
	public String myList2(){
		Filter filter = createFilter();
		filter.eq("investmentStatus", InvestmentStatusEnum.SLEEP);
		filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "myList2";
	}
	
	public String list2(){
		Filter filter = createFilter();
		filter.eq("investmentStatus", InvestmentStatusEnum.SLEEP);
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "list2";
	}
	
	public String myList3(){
		List<Approval> approvals =BusinessActivator.getService(ApprovalService.class).getListByFilter(new Filter(Approval.class).eq("userId",BusinessActivator.getSessionUser().getId()).eq("type",ApprovalTypeEnum.INVESTMENT)).getValue();
		if(approvals != null){
			List<Long> groupIds = new ArrayList<Long>();
			for(Approval approval:approvals){
				groupIds.add(approval.getGroupId());				
			}
			Filter filter = createFilter();
			filter.eq("investmentStatus", InvestmentStatusEnum.APPROVAL);
			filter.or(Filter.Eq("creatorId", BusinessActivator.getSessionUser().getId()),Filter.In("id", groupIds.toArray()));			
			setPager(filter);
			result = investmentService.getListByFilter(filter);
		}else{
		Filter filter = createFilter();
		filter.eq("investmentStatus", InvestmentStatusEnum.APPROVAL);
		filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		}
		return "myList3";
	}
	
	public String list3(){
		Filter filter = createFilter();
		filter.eq("investmentStatus", InvestmentStatusEnum.APPROVAL);
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "list3";
	}

	public String myList4(){
		Filter filter = createFilter();
		filter.eq("investmentStatus", InvestmentStatusEnum.DISAGREE);
		filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "myList4";
	}
	
	public String list4(){
		Filter filter = createFilter();
		filter.eq("investmentStatus", InvestmentStatusEnum.DISAGREE);
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "list4";
	}

	public String myList5(){
		Filter filter = createFilter();
		filter.orderBy("investmentStatus", Filter.DESC);
		filter.or(Filter.Eq("investmentStatus", InvestmentStatusEnum.AGREE),Filter.Eq("investmentStatus", InvestmentStatusEnum.PARK));
		filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "myList5";
	}
	
	public String list5(){
		Filter filter = createFilter();
		filter.orderBy("investmentStatus", Filter.DESC);
		filter.or(Filter.Eq("investmentStatus", InvestmentStatusEnum.AGREE),Filter.Eq("investmentStatus", InvestmentStatusEnum.PARK));
		setPager(filter);
		result = investmentService.getListByFilter(filter);
		return "list5";
	}
	
	private void setPager(Filter filter){
		if(page!=0){
			pager = new Pager(page,15);
		} else {
			pager = new Pager(1,15);
		}
		filter.pager(pager);
	}
	
	private Filter createFilter(){
		Filter filter = new Filter(Investment.class);
		if(name!=null && name.length()>0){
			name = StringUtil.ISOToUTF8(name);
			filter.like("name", name);
		}
		return filter.orderBy("modifyTime", Filter.DESC);
	}
	
	@Override
	protected List<Investment> getListByFilter(Filter fitler) {
		return investmentService.getListByFilter(fitler).getValue();
	}
	
	
	
	
	
	public String myBusinessmanSuggestion(){
		result = investmentService.getBeanById(id);
		return "mybusinessmanSuggestion";
	}
	public String myUpdateBusinessmanSuggestion(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setBusinessmanSuggestion(investment.getBusinessmanSuggestion());
		result = investmentService.update(dbBean);
		result.setMsg("招商人员意见保存成功");
		return JSON;
	}
	
	public String mySubmitSettled(){
		result = investmentService.submitSettled(id);
		return JSON;
	}
	
	public String myPrint() {
		fileName = StringUtil.URLEncoderToUTF8("申请单")+".doc";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			investmentService.print(id, out);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "myprint";
	}
	
	public String mySelect(){
		return "my"+SELECT;
	}
	
	public String mySelectList(){
		return refresh(new Filter(Investment.class).include("id").include("name"));
	}
	
	public String myDepartmentApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"department");
		return JSON;
	}
	
	public String myManagerApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"manager");
		return JSON;
	}
	
	public String myChiefApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"chief");
		return JSON;
	}
	
	public String myOfficeApproval(){
		Long userId = Long.parseLong(ServletActionContext.getRequest().getParameter("userId"));
		result = investmentService.approval(id,userId,"office");
		return JSON;
	}
	
	public String myDepartmentApprovalUpdate(){
		Investment dbBean = investmentService.getBeanById(investment.getId()).getValue();
		Approval approval = dbBean.getDepartmentApproval();
		if(investmentService.updateApproval(approval).isSuccess()){
			result = Result.success(R.Investment.APPROVAL_SUCCESS);
		} else {
			result = Result.failure(R.Investment.APPROVAL_FAILURE);
		}
		return JSON;
	}
	
	public String myChiefApprovalUpdate(){
		Investment dbBean = investmentService.getBeanById(investment.getId()).getValue();
		Approval approval = dbBean.getChiefApproval();
		if(investmentService.updateApproval(approval).isSuccess()){
			result = Result.success(R.Investment.APPROVAL_SUCCESS);
		} else {
			result = Result.failure(R.Investment.APPROVAL_FAILURE);
		}
		return JSON;
	}
	
	public String myOfficeApprovalUpdate(){
		Investment dbBean = investmentService.getBeanById(investment.getId()).getValue();
		Approval approval = dbBean.getOfficeApproval();
		if(investmentService.updateApproval(approval).isSuccess()){
			result = Result.success(R.Investment.APPROVAL_SUCCESS);
		} else {
			result = Result.failure(R.Investment.APPROVAL_FAILURE);
		}
		return JSON;
	}
	
	public String myApprovalView(){
		result = investmentService.getBeanById(id);
			imgSrc = new ArrayList<String>();
			attList = investmentProcessAttService.getListByFilter(new Filter(InvestmentProcessAtt.class).eq("investmentId", id)).getValue();
			Collections.sort(attList, new Comparator<InvestmentProcessAtt>() {
				@Override
				public int compare(InvestmentProcessAtt o1, InvestmentProcessAtt o2) {
					if(o1.getCreateTime()==null)return -1;
					if(o2.getCreateTime()==null)return 1;
					if(o1.getCreateTime().getTime()==o2.getCreateTime().getTime())return 0;
					return o1.getCreateTime().getTime()>o2.getCreateTime().getTime() ? 1 : -1;
				}
			});
			for(InvestmentProcessAtt att:attList){
				imgSrc.add(att.getNewName());
			}
			if(imgSrc.isEmpty()){
				imgSrc=null;
			}
		return "myapprovalView";
	}
	
	public String myWakeUp(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.NEW);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String mySleep() {
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.SLEEP);
		result = investmentService.update(dbBean);
		return JSON;
	}

	public String myAgree() {
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.AGREE);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String myDisagree() {
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.DISAGREE);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String myApproval(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.APPROVAL);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String myNewStatus(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.NEW);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String newStatus(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(InvestmentStatusEnum.NEW);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String myClear(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setInvestmentStatus(null);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String myHigh(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setPriority(PriorityEnum.HIGH);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String myMiddle(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setPriority(PriorityEnum.MIDDLE);
		result = investmentService.update(dbBean);
		return JSON;
	}
	public String myLow(){
		Investment dbBean = investmentService.getBeanById(id).getValue();
		dbBean.setPriority(PriorityEnum.LOW);
		result = investmentService.update(dbBean);
		return JSON;
	}
	
	public String myGenerateCode(){
		result = investmentService.generateCode();
		return JSON;
	}
	
	public String mySaveOrUpdate(){
		String[] enterpriseTypeIds = ServletActionContext.getRequest().getParameterValues("enterpriseTypeIds");
		/*if(investmentDirector==null) investmentDirector = new InvestmentDirector();*/
		if(investmentRegInfo==null) investmentRegInfo = new InvestmentRegInfo();
		investmentDirector.setInvestment(investment);
		investment.setPriority(PriorityEnum.LOW);
		investment.setInvestmentStatus(InvestmentStatusEnum.NEW);
		if(("").equals(investment.getCurrencyId()))investment.setCurrencyId(null);
		/*if(investmentDirector.getDegreeId().equals(""))investmentDirector.setDegreeId(null);*/
		if(("").equals(investmentRegInfo.getCurrencyTypeId()))investmentRegInfo.setCurrencyTypeId(null);
		if(("").equals(investmentRegInfo.getDocumentTypeId()))investmentRegInfo.setDocumentTypeId(null);
		if(("").equals(investmentRegInfo.getRegTypeId()))investmentRegInfo.setRegTypeId(null);
		if(("").equals(investment.getIncubateConfigId())) investment.setIncubateConfigId(null);
		if(("").equals(investment.getTechnicId())) investment.setTechnicId(null);
		result = investmentService.saveOrUpdate(investment,investmentRegInfo,enterpriseTypeIds);
		return JSON;
	}
	
	public String mySave(){
		result = investmentService.save(investment);
		return JSON;
	}
	
	public String myViewFloatbox(){
		return myView();
	}
	
	public String myView(){
		yetId=id;
		List<InvestmentVentureType> typeList = investmentVentureTypeService.getListByFilter(new Filter(InvestmentVentureType.class).eq("investmentId", id)).getValue();
		StringBuilder sb = new StringBuilder();
		if(typeList.size()>0 && typeList!=null){
			for(InvestmentVentureType it : typeList){
				sb.append(it.getVentureType().getDataValue()).append(",");  
			}
			if(sb.toString().endsWith(","))sb.deleteCharAt(sb.length()-1);
		}
		ServletActionContext.getRequest().setAttribute("typeNames", sb.toString());
		result = investmentService.getBeanById(id);
		Investment it = (Investment) result.getValue();
		if(it!=null && it.getTechnicId()!=null && it.getTechnicId().length()>0){
			ServletActionContext.getRequest().setAttribute("technic",it.getTechnic().getDataValue());
		}
		if(it!=null && it.getIncubateConfigId()!=null && it.getIncubateConfigId().length()>0){
			ServletActionContext.getRequest().setAttribute("incubate",it.getIncubateConfig().getDataValue());
		}
		investmentInvestorList = investmentInvestorService.getListByFilter(new Filter(InvestmentInvestor.class).eq("investmentId", id)).getValue();
		invesmentDirectorList = investmentDirectorService.getListByFilter(new Filter(InvestmentDirector.class).eq("investmentId", id)).getValue();
		
		if(BusinessActivator.getHttpSessionService().isInResourceMap("business_project_myViewBasic")){
			return "my"+VIEW;
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_businessPlan_myView.")){
			return "myBusinessPlan";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_record_myList.")){
			return "myInvestmentArchiveAtt";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_track_myList.")){
			return "myInvestmentLog";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_agreement_myList")){
			return "myInvestmentContractAtt";
		}else{
			return "null";
		}
	}
	
	public String myEdit(){
		List<InvestmentVentureType> typeList = investmentVentureTypeService.getListByFilter(new Filter(InvestmentVentureType.class).eq("investmentId", id)).getValue();
		StringBuilder sb = new StringBuilder();
		if(typeList.size()>0 && typeList!=null){
			for(InvestmentVentureType it : typeList){
				sb.append(it.getVentureTypeId()).append(",");  
			}
			if(sb.toString().endsWith(","))sb.deleteCharAt(sb.length()-1);
		}
		ServletActionContext.getRequest().setAttribute("typeIds", sb.toString());
		result = investmentService.getBeanById(id);
		return "my"+EDIT;
	}
	
	public String myUpdate(){
		String[] enterpriseTypeIds = ServletActionContext.getRequest().getParameterValues("enterpriseTypeIds");
		if(("").equals(investment.getIncubateConfigId())) investment.setIncubateConfigId(null);
		if(("").equals(investment.getTechnicId())) investment.setTechnicId(null);
		result = investmentService.update(investment,investmentRegInfo,enterpriseTypeIds);
		return JSON;
	}
	
	public String myDelete(){		
		if(id!=null&&!id.equals(yetId)){
			result = investmentService.deleteById(id);
			prevId = yetId;
			id=null;
		}else if(id!=null&&id.equals(yetId)){
			result = investmentService.deleteById(id);
			prevId=id=yetId=null;
		}else if(ids!=null){
			result = investmentService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String myListByType(){
		if(type!=null && "ALL".equals(type)){
			List<Approval> approvals =BusinessActivator.getService(ApprovalService.class).getListByFilter(new Filter(Approval.class).eq("userId",BusinessActivator.getSessionUser().getId()).eq("type",ApprovalTypeEnum.INVESTMENT)).getValue();
			if(approvals != null && approvals.size()>0){
				List<Long> groupIds = new ArrayList<Long>();
				for(Approval approval:approvals){
					groupIds.add(approval.getGroupId());				
				}
				Filter filter = createFilter();
				filter.or(Filter.Eq("creatorId", BusinessActivator.getSessionUser().getId()),Filter.In("id", groupIds.toArray()));			
				setPager(filter);
				result = investmentService.getListByFilter(filter);
			}else{
			Filter filter = createFilter();
			filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
			setPager(filter);
			result = investmentService.getListByFilter(filter);
			}
			return "myList";
		}else if(type!=null && "NEW".equals(type)){
			Filter filter = new Filter(Investment.class);
			filter.eq("creatorId", BusinessActivator.getSessionUser().getId()).eq("investmentStatus",InvestmentStatusEnum.NEW);
			setPager(filter);
			result = investmentService.getListByFilter(filter);
			return "myList";
		}else if(type!=null && "SLEEP".equals(type)){
			Filter filter = new Filter(Investment.class);
			filter.eq("creatorId", BusinessActivator.getSessionUser().getId()).eq("investmentStatus",InvestmentStatusEnum.SLEEP);
			setPager(filter);
			result = investmentService.getListByFilter(filter);
			return "myList";
		}else if(type!=null && "APPROVAL".equals(type)){
			List<Approval> approvals =BusinessActivator.getService(ApprovalService.class).getListByFilter(new Filter(Approval.class).eq("userId",BusinessActivator.getSessionUser().getId()).eq("type",ApprovalTypeEnum.INVESTMENT)).getValue();
			if(approvals != null && approvals.size()>0){
				List<Long> groupIds = new ArrayList<Long>();
				for(Approval approval:approvals){
					groupIds.add(approval.getGroupId());				
				}
				Filter filter = createFilter();
				filter.eq("investmentStatus", InvestmentStatusEnum.APPROVAL);
				filter.or(Filter.Eq("creatorId", BusinessActivator.getSessionUser().getId()),Filter.In("id", groupIds.toArray()));			
				setPager(filter);
				result = investmentService.getListByFilter(filter);
			}else{
			Filter filter = createFilter();
			filter.eq("investmentStatus", InvestmentStatusEnum.APPROVAL);
			filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
			setPager(filter);
			result = investmentService.getListByFilter(filter);
			}
			return "myList";
		}else if(type!=null && "DISAGREE".equals(type)){
			Filter filter = new Filter(Investment.class);
			filter.eq("creatorId", BusinessActivator.getSessionUser().getId()).eq("investmentStatus",InvestmentStatusEnum.DISAGREE);
			setPager(filter);
			result = investmentService.getListByFilter(filter);
			return "myList";
		}else if(type!=null && "PARK".equals(type)){
			Filter filter = new Filter(Investment.class);
			filter.eq("creatorId", BusinessActivator.getSessionUser().getId()).or(Filter.Eq("investmentStatus", InvestmentStatusEnum.AGREE), Filter.Eq("investmentStatus", InvestmentStatusEnum.PARK));
			filter.orderBy("modifyTime", Filter.DESC);
			setPager(filter);
			result = investmentService.getListByFilter(filter);
			return "myList";
		}else{
			return "myList";
		}
	}
	
	public String listByType(){
		Filter filter = createFilter();
		setPager(filter);
		if(type!=null && "ALL".equals(type)){
			result = investmentService.getListByFilter(filter);
			return "list";
		}else if(type!=null && "NEW".equals(type)){
			result = investmentService.getListByFilter(filter.eq("investmentStatus",InvestmentStatusEnum.NEW));
			return "list";
		}else if(type!=null && "SLEEP".equals(type)){
			result = investmentService.getListByFilter(filter.eq("investmentStatus",InvestmentStatusEnum.SLEEP));
			return "list";
		}else if(type!=null && "APPROVAL".equals(type)){
			result = investmentService.getListByFilter(filter.eq("investmentStatus",InvestmentStatusEnum.APPROVAL));
			return "list";
		}else if(type!=null && "DISAGREE".equals(type)){
			result = investmentService.getListByFilter(filter.eq("investmentStatus",InvestmentStatusEnum.DISAGREE));
			return "list";
		}else if(type!=null && "PARK".equals(type)){
			result = investmentService.getListByFilter(filter.or(Filter.Eq("investmentStatus", InvestmentStatusEnum.AGREE), Filter.Eq("investmentStatus", InvestmentStatusEnum.PARK)));
			return "list";
		}else{
			return "list";
		}
	}
	
	
	
	public Investment getInvestment() {
		return investment;
	}

	public void setInvestment(Investment investment) {
		this.investment = investment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}
	
	public Result getResult() {
		return result;
	}

	public Pager getPager() {
		return pager;
	}
	public InvestmentDirector getInvestmentDirector() {
		return investmentDirector;
	}

	public void setInvestmentDirector(InvestmentDirector investmentDirector) {
		this.investmentDirector = investmentDirector;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<InvestmentInvestor> getInvestmentInvestorList() {
		return investmentInvestorList;
	}
	public void setInvestmentInvestorService(
			InvestmentInvestorService investmentInvestorService) {
		this.investmentInvestorService = investmentInvestorService;
	}

	public InvestmentRegInfo getInvestmentRegInfo() {
		return investmentRegInfo;
	}

	public void setInvestmentRegInfo(InvestmentRegInfo investmentRegInfo) {
		this.investmentRegInfo = investmentRegInfo;
	}

	public String getFileName() {
		return fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInvestmentVentureTypeService(
			InvestmentVentureTypeService investmentVentureTypeService) {
		this.investmentVentureTypeService = investmentVentureTypeService;
	}
	public static Long getYetId() {
		return yetId;
	}
	public static void setYetId(Long yetId) {
		InvestmentAction.yetId = yetId;
	}
	public Long getPrevId() {
		return prevId;
	}
	public void setPrevId(Long prevId) {
		this.prevId = prevId;
	}
	public void setInvestmentDirectorService(
			InvestmentDirectorService investmentDirectorService) {
		this.investmentDirectorService = investmentDirectorService;
	}
	public List<InvestmentDirector> getInvesmentDirectorList() {
		return invesmentDirectorList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setInvestmentProcessService(
			InvestmentProcessService investmentProcessService) {
		this.investmentProcessService = investmentProcessService;
	}

	public void setInvestmentProcessAttService(
			InvestmentProcessAttService investmentProcessAttService) {
		this.investmentProcessAttService = investmentProcessAttService;
	}

	public List<String> getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(List<String> imgSrc) {
		this.imgSrc = imgSrc;
	}

	public List<InvestmentProcessAtt> getAttList() {
		return attList;
	}

	public void setAttList(List<InvestmentProcessAtt> attList) {
		this.attList = attList;
	}

	public boolean isConnect() {
		return connect;
	}

	public void setConnect(boolean connect) {
		this.connect = connect;
	}
	public List<DataDict> getIncubationRouteList() {
		return incubationRouteList;
	}

	public void setIncubationRouteList(List<DataDict> incubationRouteList) {
		this.incubationRouteList = incubationRouteList;
	}

	public List<DataDict> getCustomerQualificationList() {
		return customerQualificationList;
	}

	public void setCustomerQualificationList(
			List<DataDict> customerQualificationList) {
		this.customerQualificationList = customerQualificationList;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
}

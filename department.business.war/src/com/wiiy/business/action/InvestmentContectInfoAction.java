package com.wiiy.business.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dto.ClueDto;
import com.wiiy.business.entity.InvestmentBook;
import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.business.entity.InvestmentRepeatedViste;
import com.wiiy.business.service.InvestmentContectInfoService;
import com.wiiy.business.service.InvestmentRepeatedVisteService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;


import com.wiiy.business.preferences.enums.ContectInfoEnum;
import com.wiiy.business.preferences.enums.LevelEnum;

/**
 * @author my
 */
public class InvestmentContectInfoAction extends JqGridBaseAction<InvestmentContectInfo>{
	
	private InvestmentContectInfoService investmentContectInfoService;
	private InvestmentRepeatedVisteService investmentRepeatedVisteService;
	private Result result;
	private InvestmentContectInfo investmentContectInfo;
	private Long id;
	private String ids;
	
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	private LevelEnum enumLevel;

	private boolean who;
	
	private String commentType;
	private Integer day;




	private String data7;
	private String data15;
	private String data30;
	
	private BooleanEnum associate;
	
//	public String contects(){
//		result = Result.value(investmentContectInfoService.contects());
//		return JSON;
//	}
	




	//所有未联系线索
	public String countNotContectAll(){
		User user = BusinessActivator.getSessionUser();
		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();
		
		
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "le");
		filter.ne("level", LevelEnum.D);
		data7 = investmentContectInfoService.getRowCount(filter).toString();
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -15);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "le");
		filter.ne("level", LevelEnum.D);
		data15 = investmentContectInfoService.getRowCount(filter).toString();
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -30);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "le");
		filter.ne("level", LevelEnum.D);
		data30 = investmentContectInfoService.getRowCount(filter).toString();
		
		
		System.out.println(calendar.getTime());
		filter.match("receiverId", user.getId(), "eq");
		
		return JSON;
		
	}
	
	//我的未联系线索
	public String countNotContectMy(){
		User user = BusinessActivator.getSessionUser();
		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();
		
		
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "le");
		filter.match("receiverId", user.getId(), "eq");
		filter.ne("level", LevelEnum.D);
		data7 = investmentContectInfoService.getRowCount(filter).toString();
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -15);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "le");
		filter.match("receiverId", user.getId(), "eq");
		filter.ne("level", LevelEnum.D);
		data15 = investmentContectInfoService.getRowCount(filter).toString();
		
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -30);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "le");
		filter.match("receiverId", user.getId(), "eq");
		filter.ne("level", LevelEnum.D);
		data30 = investmentContectInfoService.getRowCount(filter).toString();
		
		return JSON;
		
	}
	//所有需联系线索
	public String countNeedContectAll(){
		
		User user = BusinessActivator.getSessionUser();
		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, 7);
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnVisit", calendar.getTime(), "le");
		filter.ne("level", LevelEnum.D);
		data7 = investmentContectInfoService.getRowCount(filter).toString();
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 15);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnVisit", calendar.getTime(), "le");
		filter.ne("level", LevelEnum.D);
		data15 = investmentContectInfoService.getRowCount(filter).toString();
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 30);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnVisit", calendar.getTime(), "le");
		filter.ne("level", LevelEnum.D);
		data30 = investmentContectInfoService.getRowCount(filter).toString();

		
		return JSON;

	}
	
	//我的需联系线索
	public String countNeedContectMy(){
		
		User user = BusinessActivator.getSessionUser();
		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, 7);
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnVisit", calendar.getTime(), "le");
		filter.match("receiverId", user.getId(), "eq");
		filter.ne("level", LevelEnum.D);
		data7 = investmentContectInfoService.getRowCount(filter).toString();
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 15);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnVisit", calendar.getTime(), "le");
		filter.match("receiverId", user.getId(), "eq");
		filter.ne("level", LevelEnum.D);
		data15 = investmentContectInfoService.getRowCount(filter).toString();
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 30);
		filter.clear();
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnVisit", calendar.getTime(), "le");
		filter.match("receiverId", user.getId(), "eq");
		filter.ne("level", LevelEnum.D);
		data30 = investmentContectInfoService.getRowCount(filter).toString();
		
		
		return JSON;

	}
	
	//所有联系过
	public String countHadContectAll(){

		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "ge");
		filter.ne("level", LevelEnum.D);
		data7 = investmentContectInfoService.getRowCount(filter).toString();
		
		return JSON;
		
	}
	
	//我的联系过
	public String countHadContectMy(){
		User user = BusinessActivator.getSessionUser();
		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, -7);
		filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
		filter.match("returnTime", calendar.getTime(), "ge");
		filter.match("receiverId", user.getId(), "eq");
		filter.ne("level", LevelEnum.D);
		data7 = investmentContectInfoService.getRowCount(filter).toString();
		
		return JSON;
		
	}

	
	public String contectInfos() {
		result = Result.value(investmentContectInfoService.contectInfos(who));
		return JSON;
	}
	
	
	
	public String singleSelect(){
		return "singleSelect";
	}
	
	public String changeState(){
		result = investmentContectInfoService.changeState(id);
		return JSON;
	}
	
	public String opportunityStatistic(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String time = null;
		String year = sdf.format(date);
		for (int i = 1; i > -4; i--) {
			if(years==null){
				years = new ArrayList<Integer>();
			}
			years.add(Integer.parseInt(year)+i);
		}
		List<ClueDto> dtos = new ArrayList<ClueDto>();
		Filter filter = new Filter(InvestmentContectInfo.class);
		boolean flag = false;
		if(yearNo!=null&&yearNo.length()>0){
			time = yearNo;
			if(monthNo!=null&&monthNo.length()>0){
				time = time+"-"+monthNo;
				flag = true;
			}else{
				monthNo = "0";
			}
		}
		if(time!=null){
			Date startTime = null;
			Date endTime = null;
			Date d = null;
			try {
				int calendarIndex = Calendar.YEAR;
				if(flag){
					d = new SimpleDateFormat("yyyy-MM").parse(time);
					calendarIndex = Calendar.MONTH;
				}else{
					d = new SimpleDateFormat("yyyy").parse(time);
				}
				startTime = CalendarUtil.getEarliest(d, calendarIndex);
				endTime = CalendarUtil.getLatest(d, calendarIndex);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			filter.between("createTime", startTime, endTime);
		}
		List<InvestmentContectInfo> list = investmentContectInfoService.getListByFilter(filter).getValue();
		Map<Long,ClueDto> timeMap = investmentRepeatedVisteService.getTimes();
		for (InvestmentContectInfo contectInfo : list) {
			ClueDto dto = new ClueDto();
			dto.setName(contectInfo.getName());
			dto.setDate(contectInfo.getCreateTime());
			dto.setDemand(contectInfo.getDemand());
			if(contectInfo.getLevel()!=null){
				dto.setLevel(contectInfo.getLevel().getTitle());
			}
			dto.setReceiver(contectInfo.getReceiver().getRealName());
			if(contectInfo.getEntityStatus()!=null){
				dto.setStatus(contectInfo.getEntityStatus().getTitle());
			}
			dto.setPhone(contectInfo.getPhone());
			dto.setProduct(contectInfo.getProduct());
			if(timeMap.get(contectInfo.getId())!=null){
				dto.setLastDate(timeMap.get(contectInfo.getId()).getLastDate());
				dto.setTimes(timeMap.get(contectInfo.getId()).getTimes());
				dto.setMemo(timeMap.get(contectInfo.getId()).getMemo());
			}
			dtos.add(dto);
		}
		result = Result.value(dtos);
		return "opportunityStatistic";
	}
	
	public String save(){
		if(investmentContectInfo.getLevel() != null && //
				"".equals(investmentContectInfo.getLevel())){
			investmentContectInfo.setLevel(null);
		}
		if(investmentContectInfo.getTechnicId() != null && //
				"".equals(investmentContectInfo.getTechnicId())){
			investmentContectInfo.setTechnic(null);
			investmentContectInfo.setTechnicId(null);
		}
		if(investmentContectInfo.getReceiverId() != null && //
				"".equals(investmentContectInfo.getReceiverId())){
			investmentContectInfo.setReceiverId(null);
			investmentContectInfo.setReceiver(null);
		}
		result = investmentContectInfoService.save(investmentContectInfo);
		return JSON;
	}
	
	public String view(){
		result = investmentContectInfoService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = investmentContectInfoService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		InvestmentContectInfo dbBean = investmentContectInfoService.getBeanById(investmentContectInfo.getId()).getValue();
		BeanUtil.copyProperties(investmentContectInfo, dbBean);
		if(investmentContectInfo.getLevel() != null && //
				"".equals(investmentContectInfo.getLevel())){
			dbBean.setLevel(null);
		}
		if(investmentContectInfo.getTechnicId() != null && //
				"".equals(investmentContectInfo.getTechnicId())){
			dbBean.setTechnic(null);
			dbBean.setTechnicId(null);
		}
		if(investmentContectInfo.getReceiverId() != null && //
				"".equals(investmentContectInfo.getReceiverId())){
			dbBean.setReceiverId(null);
			dbBean.setReceiver(null);
		}
		if((investmentContectInfo.getReturnVisit() != null && //
				"".equals(investmentContectInfo.getReturnVisit())) //
				|| investmentContectInfo.getReturnVisit() == null){
			dbBean.setReturnVisit(null);
		}
		result = investmentContectInfoService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = investmentContectInfoService.deleteById(id);
		} else if(ids!=null){
			result = investmentContectInfoService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){

		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();
		
		//是否关联项目
		if(associate!=null){
			filter.eq("associate", associate);
		}
		
		//线索等级
		if( null != enumLevel ){
			filter.eq("level", enumLevel);
		}
		if( null != commentType  && !"".equals(commentType) ){
			filter.ne("level", LevelEnum.D);
			//所有未联系			
			if( commentType.equals("NOT")){
				
				calendar.add(Calendar.DAY_OF_YEAR, -1*day);
				filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
				filter.match("returnTime", calendar.getTime(), "le");					
			}
			//所有需联系线索
			if( commentType.equals("NEED")){
				calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, day);
				filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
				filter.match("returnVisit", calendar.getTime(), "le");
				
			}
			//所有已联系线索
			if( commentType.equals("HAD")){
				calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, -1*day);
				filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
				filter.match("returnTime", calendar.getTime(), "ge");
			}			
		}
		filter.createAlias("receiver", "receiver");
		String value=null;
		if(filters!=null && filters.length()>0){
			value = serachByLikeFromFilters("infoAll",filters);		
		}
		if(value!=null){
			filter.or(Filter.Like("name", value),Filter.Like("phone", value),Filter.Like("receiver.realName", value));
		}
		filter.orderBy("contectInfoStatus", Filter.DESC).orderBy("createTime", Filter.DESC);
		return refresh(filter);
		
	}
	
	
	public String listMy(){
		User user = BusinessActivator.getSessionUser();
		Filter filter = new Filter(InvestmentContectInfo.class);
		Calendar calendar = Calendar.getInstance();
		//线索等级
		if( null != enumLevel ){
			filter.eq("level", enumLevel);
		}
		
		if( null != commentType && !"".equals(commentType) ){
			filter.ne("level", LevelEnum.D);
			//未联系			
			if( commentType.equals("NOT")){
				
				calendar.add(Calendar.DAY_OF_YEAR, -1*day);
				filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
				filter.match("returnTime", calendar.getTime(), "le");					
			}
			//需联系线索
			if( commentType.equals("NEED")){
				calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, day);
				filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
				filter.match("returnVisit", calendar.getTime(), "le");
				
			}
			//已联系线索
			if( commentType.equals("HAD")){
				calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, -1*day);
				filter.match("contectInfoStatus", ContectInfoEnum.NORMAL, "eq");
				filter.match("returnTime", calendar.getTime(), "ge");
			}
		}


		filter.match("receiverId", user.getId(), "eq");
		filter.orderBy("contectInfoStatus", Filter.DESC).orderBy("createTime", Filter.DESC);
		return refresh(filter);
		
	}
	
	
	
	@Override
	protected List<InvestmentContectInfo> getListByFilter(Filter fitler) {
		return investmentContectInfoService.getListByFilter(fitler).getValue();
	}
	
	
	public InvestmentContectInfo getInvestmentContectInfo() {
		return investmentContectInfo;
	}

	public void setInvestmentContectInfo(InvestmentContectInfo investmentContectInfo) {
		this.investmentContectInfo = investmentContectInfo;
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

	public void setInvestmentContectInfoService(InvestmentContectInfoService investmentContectInfoService) {
		this.investmentContectInfoService = investmentContectInfoService;
	}
	
	public Result getResult() {
		return result;
	}


	public List<Integer> getYears() {
		return years;
	}


	public void setYears(List<Integer> years) {
		this.years = years;
	}


	public void setInvestmentRepeatedVisteService(
			InvestmentRepeatedVisteService investmentRepeatedVisteService) {
		this.investmentRepeatedVisteService = investmentRepeatedVisteService;
	}


	public String getYearNo() {
		return yearNo;
	}


	public void setYearNo(String yearNo) {
		this.yearNo = yearNo;
	}


	public String getMonthNo() {
		return monthNo;
	}


	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}







	public boolean isWho() {
		return who;
	}

	public void setWho(boolean who) {
		this.who = who;
	}


	public String getData7() {
		return data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public String getData15() {
		return data15;
	}

	public void setData15(String data15) {
		this.data15 = data15;
	}

	public String getData30() {
		return data30;
	}

	public void setData30(String data30) {
		this.data30 = data30;
	}
	
	public LevelEnum getEnumLevel() {
		return enumLevel;
	}

	public void setEnumLevel(LevelEnum enumLevel) {
		this.enumLevel = enumLevel;
	}
	
	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public BooleanEnum getAssociate() {
		return associate;
	}

	public void setAssociate(BooleanEnum associate) {
		this.associate = associate;
	}
	
}

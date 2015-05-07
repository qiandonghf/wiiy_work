package com.wiiy.estate.action;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.BillingMethodEnum;
import com.wiiy.common.preferences.enums.EstateFeeEnum;
import com.wiiy.common.preferences.enums.PriceUnitEnum;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.estate.dto.BillCostDto;
import com.wiiy.estate.entity.EstateBillPlanRent;
import com.wiiy.estate.entity.EstateSubjectRent;
import com.wiiy.estate.preferences.enums.RentBillPlanEnum;
import com.wiiy.estate.service.BillPlanRentService;
import com.wiiy.estate.service.SubjectRentService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class BillPlanRentAction extends JqGridBaseAction<EstateBillPlanRent>{
	
	private BillPlanRentService billPlanRentService;
	private SubjectRentService subjectRentService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateBillPlanRent billPlanRent;
	private Long id;
	private String ids;
	private List<EstateSubjectRent> subjectRentList;
	
	
	private EstateFeeEnum feeType;
	private RentBillPlanEnum rentBillPlan;
	private BillingMethodEnum billingMethod;
	private Date startDate;
	private Date endDate;
	private String memo;
	private Double roomArea;
	private Double rentPrice;
	private PriceUnitEnum rentPriceUnit;
	private Double managePrice;
	private PriceUnitEnum managePriceUnit;
	
	private String year;
	private String status;
	private String statusType;
	private String roomIds;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	/**
	 * 出账列表
	 * @return
	 */
	public String listByBillCostDetail() {
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
		List<BillCostDto> dtoList = new ArrayList<BillCostDto>();
		Filter filter =new Filter(EstateBillPlanRent.class);
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
				//filter.createAlias("signDate", "regTime");
				filter.between("signDate", startTime, endTime);
		}
		List<EstateBillPlanRent> billList=billPlanRentService.getListByFilter(filter).getValue();
		if(billList!=null){
			for (EstateBillPlanRent list : billList) {
				BillCostDto dto = new BillCostDto();
				dto.setStartDate(list.getStartDate());
				dto.setEndDate(list.getEndDate());
				dto.setCustomerName(list.getContract().getCustomerName());
				dto.setTotalAmount(list.getContract().getTotalAmount());
				dto.setRoomName(list.getRoomName());
				dtoList.add(dto);
			}
			result = Result.value(dtoList);
		}
		
		return "billCostDetail";
	}
	public String checkoutListBillPlanRent() {
		/*HttpServletRequest request = ServletActionContext.getRequest();
		String rentDay = EstateActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		Date date = CalendarUtil.getLatest(
				CalendarUtil.add(Calendar.DAY_OF_MONTH,
						Integer.parseInt(rentDay)).getTime(),
				Calendar.DAY_OF_MONTH);
		request.setAttribute("date", DateUtil.format(date));*/
		return "checkoutListBillPlanRent";
	}
	/**
	 * 结算提醒数量(物业)
	 * @return
	 */
	public String initRemindCount(){
		result = billPlanRentService.getRowCountByFeeType();
		return JSON;
	}
	
	/**
	 * 批量出账
	 * @return
	 */
	public String batchCheckout(){
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean autoRemind = request.getParameter("autoRemind") != null;
		String types = request.getParameter("types");
		result = billPlanRentService.batchCheckout(ids,types.split(","),autoRemind);
		return JSON;
	}
	
	public String submitBillPlan(){
		EstateSubjectRent subjectRent = subjectRentService.getBeanById(id).getValue();
		String[] startDates = ServletActionContext.getRequest().getParameterValues("startDates");
		String[] endDates = ServletActionContext.getRequest().getParameterValues("endDates");
		String[] planPayDates = ServletActionContext.getRequest().getParameterValues("planPayDates");
		String[] planFees = ServletActionContext.getRequest().getParameterValues("planFees");
		String[] realFees = ServletActionContext.getRequest().getParameterValues("realFees");
		String[] prices = ServletActionContext.getRequest().getParameterValues("prices");
		String[] amounts = ServletActionContext.getRequest().getParameterValues("amounts");
		List<EstateBillPlanRent> billPlanRentList = new ArrayList<EstateBillPlanRent>();
		for (int i = 0; i < planFees.length; i++) {
			EstateBillPlanRent billPlanRent = new EstateBillPlanRent();
			billPlanRent.setStartDate(DateUtil.parse(startDates[i]));
			billPlanRent.setEndDate(DateUtil.parse(endDates[i]));
			billPlanRent.setPlanPayDate(DateUtil.parse(planPayDates[i]));
			billPlanRent.setPlanFee(Double.valueOf(planFees[i]));
			
			billPlanRent.setPrice(prices[i]);
			billPlanRent.setAmount(Double.valueOf(amounts[i]));
			
			if(realFees[i]!=null && realFees[i].length()>0){
				billPlanRent.setRealFee(Double.valueOf(realFees[i]));
			}
			billPlanRent.setStatus(BillPlanStatusEnum.UNCHECK);
			billPlanRent.setFeeType(feeType);
			billPlanRent.setContractId(subjectRent.getContractId());
			billPlanRent.setMemo(memo);
			billPlanRent.setRoomId(subjectRent.getRoomId());
			billPlanRent.setRoomName(subjectRent.getRoomName());
			billPlanRent.setSubjectId(id);
			if(billPlanRent.getAutoCheck()==null) billPlanRent.setAutoCheck(BooleanEnum.NO);
			billPlanRentList.add(billPlanRent);
		}
		billPlanRentService.save(billPlanRentList);
		result = Result.success("保存成功");
		return JSON;
	}
	
	public String autoGenerate(){
		/*if(feeType.equals(EstateFeeEnum.BUSINESS_ZJ)){
			result = billPlanRentService.autoGenerate(feeType,rentBillPlan,billingMethod,startDate,endDate,roomArea,rentPrice,rentPriceUnit);
		} else {
			result = billPlanRentService.autoGenerate(feeType,rentBillPlan,billingMethod,startDate,endDate,roomArea,managePrice,managePriceUnit);
		}*/
		return "autoGenerate";
	}
	
	public String checkinById(){
		result = billPlanRentService.checkoutById(id, BillPlanStatusEnum.INCHECKED);
		return JSON;
	}
	public String checkoutById(){
		result = billPlanRentService.checkoutById(id, BillPlanStatusEnum.OUTCHECKED);
		return JSON;
	}
	
	/**
	 * 添加物业资金计划
	 * @return
	 */
	public String addEstate(){
		return "addEstate";
	}
	
	public String add(){
		return "add";
	}
	
	public String save(){
		try{
			if(billPlanRent.getRoomId()!=null){
				List<EstateSubjectRent> subjectRentList = subjectRentService.getListByFilter(new Filter(EstateSubjectRent.class).eq("contractId", billPlanRent.getContractId())).getValue();
				for (EstateSubjectRent subjectRent : subjectRentList) {
					if(Long.valueOf(billPlanRent.getRoomId())==Long.valueOf(subjectRent.getRoomId())){
						billPlanRent.setSubjectId(subjectRent.getId());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(billPlanRent.getStatus()==null){
			billPlanRent.setStatus(BillPlanStatusEnum.UNCHECK);
		}
		if(billPlanRent.getAutoCheck()==null) billPlanRent.setAutoCheck(BooleanEnum.NO);
		result = billPlanRentService.save(billPlanRent);
		return JSON;
	}
	
	public String view(){
		result = billPlanRentService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		EstateBillPlanRent billPlanRent = billPlanRentService.getBeanById(id).getValue();
		if(billPlanRent.getRoomId()!=null){
			List<EstateSubjectRent> subjectRentList = subjectRentService.getListByFilter(new Filter(EstateSubjectRent.class).eq("contractId", billPlanRent.getContractId())).getValue();
			for (EstateSubjectRent subjectRent : subjectRentList) {
				if(Long.valueOf(billPlanRent.getRoomId())==Long.valueOf(subjectRent.getRoomId())){
					billPlanRent.setSubjectId(subjectRent.getId());
				}
			}
		}
		result = Result.value(billPlanRent);
		return EDIT;
	}
	
	public String update(){
		EstateBillPlanRent dbBean = billPlanRentService.getBeanById(billPlanRent.getId()).getValue();
		BeanUtil.copyProperties(billPlanRent, dbBean);
		result = billPlanRentService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = billPlanRentService.deleteById(id);
		} else if(ids!=null){
			result = billPlanRentService.deleteByIds(ids);
		}
		return JSON;
	}
	public String listRendPredict(){
		Filter filter = new Filter(EstateBillPlanRent.class).ne("status", BillPlanStatusEnum.CHARGEOFF);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(year+"-01-01 00:00:00");
			Date latestDate = CalendarUtil.getLatest(date, Calendar.YEAR);
			filter.ge("planPayDate", date).le("planPayDate", latestDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if("YS".equals(status)){
			filter.ne("status", BillPlanStatusEnum.OUTCHECKED);
		}else if("WS".equals(status)){
			filter.eq("status", BillPlanStatusEnum.UNCHECK);
		}else if("SS".equals(status)){
			filter.ne("status", BillPlanStatusEnum.UNCHECK);
		}
		return refresh(filter);
	}
	
	public String list(){
		Filter filter = new Filter(EstateBillPlanRent.class);
		if(feeType!=null){
			filter.eq("feeType", feeType);
		}
		if(statusType !=null){
			filter.eq("status",BillPlanStatusEnum.valueOf(statusType.toUpperCase()) );
		}
		filter.createAlias("contract", "contract");
		refresh(filter);
		return JSON;
	}
	
	@Override
	protected List<EstateBillPlanRent> getListByFilter(Filter fitler) {
		return billPlanRentService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateBillPlanRent getBillPlanRent() {
		return billPlanRent;
	}

	public void setBillPlanRent(EstateBillPlanRent billPlanRent) {
		this.billPlanRent = billPlanRent;
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

	public void setBillPlanRentService(BillPlanRentService billPlanRentService) {
		this.billPlanRentService = billPlanRentService;
	}
	
	public Result getResult() {
		return result;
	}

	public void setSubjectRentService(SubjectRentService subjectRentService) {
		this.subjectRentService = subjectRentService;
	}
	
	public List<EstateSubjectRent> getSubjectRentList() {
		return subjectRentList;
	}

	public void setFeeType(EstateFeeEnum feeType) {
		this.feeType = feeType;
	}

	public void setRentBillPlan(RentBillPlanEnum rentBillPlan) {
		this.rentBillPlan = rentBillPlan;
	}

	public void setBillingMethod(BillingMethodEnum billingMethod) {
		this.billingMethod = billingMethod;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setRoomArea(Double roomArea) {
		this.roomArea = roomArea;
	}

	public void setRentPrice(Double rentPrice) {
		this.rentPrice = rentPrice;
	}

	public void setRentPriceUnit(PriceUnitEnum rentPriceUnit) {
		this.rentPriceUnit = rentPriceUnit;
	}

	public void setManagePrice(Double managePrice) {
		this.managePrice = managePrice;
	}

	public void setManagePriceUnit(PriceUnitEnum managePriceUnit) {
		this.managePriceUnit = managePriceUnit;
	}

	public EstateFeeEnum getFeeType() {
		return feeType;
	}

	public String getMemo() {
		return memo;
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRoomIds() {
		return roomIds;
	}

	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
}

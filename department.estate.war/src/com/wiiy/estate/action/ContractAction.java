package com.wiiy.estate.action;

import java.io.ByteArrayInputStream;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.wiiy.business.service.CustomerService;
import com.wiiy.common.preferences.enums.BillPlanStatusEnum;
import com.wiiy.common.preferences.enums.BillStatusEnum;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.commons.util.POIUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.core.entity.DataDict;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dto.EstateContractDto;
import com.wiiy.estate.dto.RentPredictionDto;
import com.wiiy.estate.entity.EstateBillPlanRent;
import com.wiiy.estate.entity.EstateContract;
import com.wiiy.estate.entity.EstateContractTemplate;
import com.wiiy.estate.entity.EstateSubjectRent;
import com.wiiy.estate.preferences.enums.ContractItemEnum;
import com.wiiy.estate.preferences.enums.ContractRentStatusEnum;
import com.wiiy.estate.preferences.enums.ContractStatusEnum;
import com.wiiy.estate.service.BillPlanRentService;
import com.wiiy.estate.service.ContractService;
import com.wiiy.estate.service.ContractTemplateService;
import com.wiiy.estate.service.SubjectRentService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.CommonBill;
import com.wiiy.park.service.BillService;

/**
 * @author my
 */
public class ContractAction extends JqGridBaseAction<EstateContract>{
	private BillService billService;
	private BillPlanRentService billPlanRentService;
	private SubjectRentService subjectRentService;
	private CustomerService customerService;
	private ContractService contractService;
	private ContractTemplateService contractTemplateService;
	
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateContract contract;
	private List<EstateContract> contractList;
	private List<EstateContractTemplate> contractTemplateList;
	private List<EstateSubjectRent> subjectRentList;
	private Long id;
	private Long subjectRentId;
	private Long templateId;
	private String ids;
	private String fileName;
	private Date executeTime;
	private String memo;
	private String roomNames;
	private InputStream inputStream;
	private String columns;
	private boolean checkoutnow;
	private String contractIds;
	private String contractModel;
	
	private int countDueRemind;
	private List<String> years;
	
	private String groupIds;
	private String roomIds;
	private String startDate;
	private String endDate;
	private String saveFlag;

	private String type;
	private List<Integer> iyears;
	private String yearNo;
	private String monthNo;
	
	/**
	 * save物业合同
	 * @return
	 */
	public String listByContractDetail () {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String time = null;
		String year = sdf.format(date);
		for (int i = 1; i > -4; i--) {
			if(years==null){
				iyears = new ArrayList<Integer>();
			}
			iyears.add(Integer.parseInt(year)+i);
		}
		List<EstateContractDto> dtoList = new ArrayList<EstateContractDto>();
		Filter filter =new Filter(EstateContract.class);
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
		List<EstateContract> contractList=contractService.getListByFilter(filter).getValue();
		if(contractList!=null){
			for (EstateContract list : contractList) {
				EstateContractDto dto = new EstateContractDto();
				dto.setCode(list.getCode());
				dto.setName(list.getName());
				dto.setCustomerName(list.getCustomerName());
				dto.setManager(list.getManager().getRealName());
				dto.setSignDate(list.getSignDate());
				dto.setStartDate(list.getStartDate());
				dto.setEndDate(list.getEndDate());
				dtoList.add(dto);
			}
			result = Result.value(dtoList);
		}
			
		return "contractDetail";
	}
	
	
	public String listByDataDict(){
		List<DataDict> list = EstateActivator.getDataDictInitService().getDataDictByParentId("business.0036");
		String str[] = new String[list.size()];
		int i=0;
		for (DataDict dataDict : list) {
			str[i]=dataDict.getDataValue();
			i++;
		}
		result = Result.value(str);
		return JSON;
	}
	public String saveEstateContract(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Map<String,String[]> roomRent = new HashMap<String, String[]>();
		Map<String,String[]> billPlanRent = new HashMap<String, String[]>();
		Map<String,String[]> deposit = new HashMap<String, String[]>();
		
		roomRent = formRoomRent(roomRent,request);
		billPlanRent = fromBillPlanRent(billPlanRent,request);
		deposit = fromDeposit(deposit,request);
		
		result = contractService.saveEstateContract(contract,roomRent,billPlanRent,deposit);
		return JSON;
	}
	
	/**
	 * 物业资金计划
	 * @return
	 */
	public String addBillRent(){
		return "addBillRent";
	}
	
	/**
	 * 2.0界面 物业合同
	 * @return
	 */
	public String estateDesktop(){
		
		return "estateDesktop";
	}
	
	/**
	 * 物业合同列表
	 * @return
	 */
	public String estateContractList(){
		Filter filter = new Filter(EstateContract.class);
		filter.eq("item", ContractItemEnum.WYHT);
		if("execute".equals(type)){
			filter.eq("state", ContractStatusEnum.NEW);
		}
		String[] includes = {"id","code","name","item","customerName","totalAmount","state","endDate","startDate"};
		filter.include(includes);
		return refresh(filter);
	}
	
	/**
	 * 到期合同数量
	 * @return
	 */
	public String dueCount(){
		Calendar remindTime = Calendar.getInstance();
		remindTime.setTime(new Date());
		Filter filter = new Filter(EstateContract.class).//
				eq("state", ContractStatusEnum.EXECUTE).le("endDate", remindTime.getTime());
		filter.ne("item", ContractItemEnum.WYHT);
		String[] includes = {"id","code","name","item","customerName","totalAmount",//
				"state","endDate","startDate"};
		filter.include(includes);
		contractList = contractService.getListByFilter(filter).getValue();
		int count = 0;
		if (contractList != null && contractList.size() > 0) {
			count = contractList.size();
		}
		result = Result.value(count);
		return JSON;
	}
	/**
	 * 退租合同数量
	 * @return
	 */
	public String rentoffCount(){
		Calendar date = Calendar.getInstance();
		date.setTime(new Date());
		if(EstateActivator.getAppConfig().getConfig("rentoffContract")!=null && EstateActivator.getAppConfig().getConfig("rentoffContract").getParameter("time")!=null){
			Integer day = Integer.valueOf(EstateActivator.getAppConfig().getConfig("rentoffContract").getParameter("time"));
			date.set(Calendar.DATE, date.get(Calendar.DATE) + day);
		}
		Date time = CalendarUtil.getLatest(date.getTime(),Calendar.DAY_OF_YEAR);
		Filter filter = new Filter(EstateContract.class).//
				eq("rentState", ContractRentStatusEnum.RENTOFF).le("endDate", time).le("endDate", CalendarUtil.getLatest(new Date(),Calendar.DAY_OF_YEAR));
		String[] includes = {"id","code","name","item","customerName","totalAmount",//
				"state","endDate","startDate"};
		filter.ne("item", ContractItemEnum.WYHT);
		filter.include(includes);
		contractList = contractService.getListByFilter(filter).getValue();
		int count = 0;
		if (contractList != null && contractList.size() > 0) {
			count = contractList.size();
		}
		result = Result.value(count);
		return JSON;
	}
	/**
	 * 续租合同数量
	 * @return
	 */
	public String reletCount(){
		Calendar date = Calendar.getInstance();
		date.setTime(new Date());
		if(EstateActivator.getAppConfig().getConfig("rentoffContract")!=null && EstateActivator.getAppConfig().getConfig("rentoffContract").getParameter("time")!=null){
			Integer day = Integer.valueOf(EstateActivator.getAppConfig().getConfig("rentoffContract").getParameter("time"));
			date.set(Calendar.DATE, date.get(Calendar.DATE) + day);
		}
		Date time = CalendarUtil.getLatest(date.getTime(),Calendar.DAY_OF_YEAR);
		Filter filter = new Filter(EstateContract.class).//
				eq("rentState", ContractRentStatusEnum.RELET).ge("startDate", time).le("startDate", CalendarUtil.getLatest(new Date(),Calendar.DAY_OF_YEAR));
		String[] includes = {"id","code","name","item","customerName","totalAmount",//
				"state","endDate","startDate"};
		filter.ne("item", ContractItemEnum.WYHT);
		filter.include(includes);
		contractList = contractService.getListByFilter(filter).getValue();
		int count = 0;
		if (contractList != null && contractList.size() > 0) {
			count = contractList.size();
		}
		result = Result.value(count);
		return JSON;
	}
	
	public String subjectRentEdit() {
		result = subjectRentService.getBeanById(id);
		return "subjectRentEdit";
	}
	
	public String subjectRentView() {
		result = subjectRentService.getBeanById(id);
		return "subjectRentView";
	}
	
	private Map<String,Map<String,RentPredictionDto>> reformMapByYear(String year,Map<String,Map<String,RentPredictionDto>> map,EstateBillPlanRent billPlanRent,String key){
		if(map.get(key)!=null){
			if(map.get(key).get(year)!=null){
				map.get(key).get(year).setFee(map.get(key).get(year).getFee()+billPlanRent.getRealFee());
			}else{
				RentPredictionDto dto = new RentPredictionDto();
				dto.setFee(billPlanRent.getRealFee());
				dto.setYear(year);
				dto.setStatus(billPlanRent.getStatus());
				map.get(key).put(year, dto);
			}
		}else{
			Map<String,RentPredictionDto> dtoMap = new HashMap<String, RentPredictionDto>();
			map.put(key, dtoMap);
		}
		return map;
	}
	
	//资金计划---房租 收费测算
	public String rentPrediction(){
		List<EstateBillPlanRent> list = billPlanRentService.getListHql("select new EstateBillPlanRent(b.realFee,b.planPayDate,b.status) from EstateBillPlanRent b where b.status != 'CHARGEOFF'").getValue();
		String now = DateUtil.format(new Date(), "yyyy");
		String prevYear = DateUtil.format(CalendarUtil.add(new Date(), Calendar.YEAR, -1).getTime(), "yyyy");
		String nextYear = DateUtil.format(CalendarUtil.add(new Date(), Calendar.YEAR, 1).getTime(), "yyyy");
		years = new ArrayList<String>();
		years.add(prevYear);
		years.add(now);
		years.add(nextYear);
		Map<String,Map<String,RentPredictionDto>> map = new HashMap<String,Map<String,RentPredictionDto>>();
		for (EstateBillPlanRent billPlanRent : list) {
			if(BillPlanStatusEnum.OUTCHECKED!=billPlanRent.getStatus()){
				String year = DateUtil.format(billPlanRent.getPlanPayDate(), "yyyy");
				for (String y : years) {
					if(y.equals(year)){
						map = reformMapByYear(year,map,billPlanRent,"应收");
					}
				}
			}
			if(BillPlanStatusEnum.UNCHECK == billPlanRent.getStatus()){
				String year = DateUtil.format(billPlanRent.getPlanPayDate(), "yyyy");
				for (String y : years) {
					if(y.equals(year)){
						map = reformMapByYear(year,map,billPlanRent,"未收");
					}
				}
			}
			if(BillPlanStatusEnum.UNCHECK !=  billPlanRent.getStatus()){
				if(BillPlanStatusEnum.OUTCHECKED==billPlanRent.getStatus()){
					billPlanRent.setRealFee(-billPlanRent.getRealFee());
				}
				String year = DateUtil.format(billPlanRent.getPlanPayDate(), "yyyy");
				for (String y : years) {
					if(y.equals(year)){
						map = reformMapByYear(year,map,billPlanRent,"实收");
					}
				}
			}
		}
		result = Result.value(map);
		return "rentPrediction";
	}
	
	
	public String contractListByEndDate(){
		Filter filter = new Filter(EstateContract.class).eq("state", ContractStatusEnum.EXECUTE).ne("item", ContractItemEnum.WYHT).lt("endDate", new Date());
		return refresh(filter);
	}
	
	public String estateDueContract(){
		Filter filter = new Filter(EstateContract.class).eq("state", ContractStatusEnum.EXECUTE).eq("item", ContractItemEnum.WYHT).lt("endDate", new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -30);
		filter.gt("endDate", calendar.getTime());
		result = contractService.getRowCount(filter);
		return JSON;
	}
	
	/**
	 * 物业到期合同
	 * @return
	 */
	public String eContractListByEndDate(){
		Filter filter = new Filter(EstateContract.class).eq("item", ContractItemEnum.WYHT).lt("endDate", new Date());
		filter.Or(Filter.Eq("state", ContractStatusEnum.EXECUTE), Filter.Eq("state", ContractStatusEnum.NEW));
		Calendar calendar = Calendar.getInstance();
		//表示截止现在为止已经过去的30天
		calendar.add(Calendar.DAY_OF_YEAR, -30);
		filter.gt("endDate", calendar.getTime());
		return refresh(filter);
	}
	
	public String contractListByParkStatus(){
		Filter filter = new Filter(EstateContract.class).eq("rentState", ContractRentStatusEnum.RENTOFF);
		return refresh(filter);
	}
	public String listRelet(){
		Filter filter = new Filter(EstateContract.class).eq("rentState", ContractRentStatusEnum.RELET);
		return refresh(filter);
	}
	
	public String contractWarnList(){
		Calendar remindTime = Calendar.getInstance();
		remindTime.setTime(new Date());
		remindTime.set(Calendar.DATE, remindTime.get(Calendar.DATE) + 30);
		Filter filter = new Filter(EstateContract.class).eq("state", ContractStatusEnum.EXECUTE).and(Filter.Le("endDate", remindTime.getTime()),Filter.Gt("endDate", new Date()));
		String[] includes = {"id","code","name","item","customerName","totalAmount","state","endDate","startDate"};
		filter.include(includes);
		return refresh(filter);
	}
	
	/**
	 * 预警数量
	 * @return
	 */
	public String warnCount() {
		Calendar remindTime = Calendar.getInstance();
		remindTime.setTime(new Date());
		remindTime.set(Calendar.DATE, remindTime.get(Calendar.DATE) + 30);
		Filter filter = new Filter(EstateContract.class).//
				eq("state", ContractStatusEnum.EXECUTE).and(//
						Filter.Le("endDate", remindTime.getTime()),Filter.Gt("endDate", new Date()));
		String[] includes = {"id","code","name","item","customerName","totalAmount",//
				"state","endDate","startDate"};
		filter.include(includes);
		contractList = contractService.getListByFilter(filter).getValue();
		int count = 0;
		if (contractList != null && contractList.size() > 0) {
			count = contractList.size();
		}
		result = Result.value(count);
		return JSON;
	}
	
	/*public String workBenchContractEdit(){
		EstateContract contract = contractService.getBeanById(id).getValue();
		result = Result.value(contract);
		switch(contract.getItem()){
			case DSZLHT:
				return "workBenchContractEdit1";
			case CFZLHT:
				return "workBenchContractEdit2";
			case FHXY:
				return "workBenchContractEdit3";
		}
		return EDIT;
	}*/
	
	public String saveNetContract1(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] netIds = request.getParameterValues("netId");
		String[] ips = request.getParameterValues("ip");
		String[] ports = request.getParameterValues("port");
		String[] ipPubs = request.getParameterValues("ipPub");
		String[] netStartDates = request.getParameterValues("netStartDate");
		String[] netEndDates = request.getParameterValues("netEndDate");
		String[] playPayDates = request.getParameterValues("playPayDate");
		String[] planStartDates = request.getParameterValues("planStartDate");
		String[] planEndDates = request.getParameterValues("planEndDate");
		String[] prices = request.getParameterValues("price");
		String[] moneys = request.getParameterValues("money");
		String[] feeTypes = request.getParameterValues("feeType");
		String autocheck = request.getParameter("autocheck");
		result = contractService.saveNetContract1(contract,netIds,prices,ips,ports,ipPubs,netStartDates,netEndDates,feeTypes,moneys,playPayDates,planStartDates,planEndDates,autocheck);
		return JSON;
	}
	
	private Map<String,String[]> formRoomRent(Map<String,String[]> roomRent, HttpServletRequest request){
		String[] roomIds = request.getParameterValues("roomId");
		String[] roomNames = request.getParameterValues("roomName");
		String[] roomAreas = request.getParameterValues("roomArea");
		String[] startDate = request.getParameterValues("startDate");
		String[] endDate = request.getParameterValues("endDate");
		String[] rentPrices = request.getParameterValues("rentPrice");
		String[] rentPriceUnit = request.getParameterValues("rentPriceUnit");
		/*String[] managePrice = request.getParameterValues("managePrice");
		String[] managePriceUnit = request.getParameterValues("managePriceUnit");*/
		String[] rebate = request.getParameterValues("rebate");
		String[] rebateRuleId = request.getParameterValues("rebateRuleId");
		String[] memo = request.getParameterValues("memo");
		
		roomRent.put("roomIds", roomIds);
		roomRent.put("roomNames", roomNames);
		roomRent.put("roomAreas", roomAreas);
		roomRent.put("startDate", startDate);
		roomRent.put("endDate", endDate);
		roomRent.put("rentPrices", rentPrices);
		roomRent.put("rentPriceUnit", rentPriceUnit);
		/*roomRent.put("managePrice", managePrice);
		roomRent.put("managePriceUnit", managePriceUnit);*/
		roomRent.put("rebate", rebate);
		roomRent.put("rebateRuleId", rebateRuleId);
		roomRent.put("memo", memo);
		return roomRent;
	}
	
	private Map<String, String[]> fromBillPlanRent(
			Map<String, String[]> billPlanRent, HttpServletRequest request) {
		String[] planRoomNames = request.getParameterValues("planRoomName");
		String[] planRoomIds = request.getParameterValues("planRoomId");
		String[] feeTypes = request.getParameterValues("feeType");
		String[] planFees = request.getParameterValues("planFee");
		String[] realFees = request.getParameterValues("realFee");
		String[] planStartDates = request.getParameterValues("planStartDate");
		String[] planEndDates = request.getParameterValues("planEndDate");
		String[] planPayDates = request.getParameterValues("planPayDate");
		String[] planStatus = request.getParameterValues("planStatus");
		String[] planMemos = request.getParameterValues("planMemo");
		
		billPlanRent.put("planRoomNames", planRoomNames);
		billPlanRent.put("planRoomIds", planRoomIds);
		billPlanRent.put("feeTypes", feeTypes);
		billPlanRent.put("planFees", planFees);
		billPlanRent.put("realFees", realFees);
		billPlanRent.put("planStartDates", planStartDates);
		billPlanRent.put("planEndDates", planEndDates);
		billPlanRent.put("planPayDates", planPayDates);
		billPlanRent.put("planStatus", planStatus);
		billPlanRent.put("planMemos", planMemos);
		return billPlanRent;
	}
	
	private Map<String, String[]> fromDeposit(
			Map<String, String[]> deposit, HttpServletRequest request) {
		String[] depositTypes = request.getParameterValues("depositType");
		String[] depositAmounts = request.getParameterValues("depositAmount");
		String[] depositMemos = request.getParameterValues("depositMemo");
		
		deposit.put("depositTypes", depositTypes);
		deposit.put("depositAmounts", depositAmounts);
		deposit.put("depositMemos", depositMemos);
		return deposit;
	}
	
	
	public String saveRentContract1(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Map<String,String[]> roomRent = new HashMap<String, String[]>();
		Map<String,String[]> billPlanRent = new HashMap<String, String[]>();
		Map<String,String[]> deposit = new HashMap<String, String[]>();
		
		roomRent = formRoomRent(roomRent,request);
		billPlanRent = fromBillPlanRent(billPlanRent,request);
		deposit = fromDeposit(deposit,request);
		
		result = contractService.saveRentContract1(contract,roomRent,billPlanRent,deposit);
		return JSON;
	}
	
	public String checkBillPlan(){
		Filter rentFilter = new Filter(EstateBillPlanRent.class).eq("contractId", id);
		rentFilter.eq("status", BillPlanStatusEnum.UNCHECK);
		Integer count2 = billPlanRentService.getRowCount(rentFilter);
		if(count2>0){
			result = Result.failure("您还有未出账的资金计划");
		} else {
			result = Result.success("资金计划已全部出账");
		}
		return JSON;
	}
	public String checkDeposit(){
		/*Filter filter = new Filter(Deposit.class).eq("contractId", id);
		filter.eq("status", BillPlanStatusEnum.UNCHECK);
		Integer count = depositService.getRowCount(filter);
		if(count>0){
			result = Result.failure("您还有未结算的押金");
		} else {
			result = Result.success("押金已全部结算");
		}*/
		return JSON;
	}
	public String checkBill(){
		EstateContract contract = contractService.getBeanById(id).getValue();
		Filter filter = new Filter(CommonBill.class).eq("contractNo", contract.getCode());
		filter.eq("status", BillStatusEnum.UNPAID);
		Integer count = billService.getRowCount(filter);
		if(count>0){
			result = Result.failure("您还有未结算的账单");
		} else {
			result = Result.success("账单已全部结算");
		}
		return JSON;
	}
	
	public String dueRemind(){
		Filter filter = new Filter(EstateContract.class);
		filter.createAlias("customer", "customer");
		String[] names = {"id","customer.shortName","customer.name","state","item","endDate"};
		filter.include(names);
		filter.eq("state", ContractStatusEnum.EXECUTE);
		filter.or(Filter.Le("endDate", new Date()),Filter.And(Filter.Ge("endDate", new Date()), Filter.Le("endDate", CalendarUtil.add(Calendar.DAY_OF_MONTH, 30).getTime())));
		filter.orderBy("endDate", Filter.ASC);
		contractList = contractService.getListByFilter(filter).getValue();
		/*contractList = contractService.getListByHql("select new Contract(c.id,c.customer,c.customer.shortName,c.customer.name,c.item,c.endDate) from Contract c where c.state = '"+ContractStatusEnum.EXECUTE+"' and (c.endDate <= '"+DateUtil.format(new Date())+"' or (c.endDate >= '"+DateUtil.format(new Date())+"' and c.endDate <= '"+DateUtil.format(CalendarUtil.add(Calendar.DAY_OF_MONTH, 30).getTime())+"')) order by c.endDate asc").getValue();*/
		return JSON;
	}
	public String countDueRemind(){
		//int days = Integer.parseInt(EstateActivator.getAppConfig().getConfig("contractDueRemindDays").getParameter("day"));
		Filter filter = new Filter(EstateContract.class);
		filter.eq("state", ContractStatusEnum.EXECUTE).or(Filter.Le("endDate",new Date()), Filter.And(Filter.Le("endDate", CalendarUtil.add(Calendar.DAY_OF_MONTH, 30).getTime()),Filter.Ge("endDate", new Date()))) ;
		//filter.maxResults(6);
		countDueRemind=billService.getRowCount(filter);
		return JSON;
	}

	public String customerContractList(){
		if(customerService.getSessionUserCustomer().getValue()!=null){
			result = contractService.getListByFilter(new Filter(EstateContract.class).eq("customerId",customerService.getSessionUserCustomer().getValue().getId()));
		}
		return "customerContractList";
	}
	
	public String export(){
		Filter filter = new Filter(EstateContract.class);
		page=0;//不要分页
		String name = "合同";
		if("END".equalsIgnoreCase(fileName)){
			name += "到期";
			filter.eq("state", ContractStatusEnum.EXECUTE).lt("endDate", new Date());
		}
		if("THROWALEASE".equalsIgnoreCase(fileName)){
			name += "退租";
			filter.sqlRestriction("{alias}.customer_id in (select id from business_business_customer where park_status = 'THROWALEASE') ");
		}
		if("WARN".equalsIgnoreCase(fileName)){
			name += "预警";
			Calendar remindTime = Calendar.getInstance();
			remindTime.setTime(new Date());
			remindTime.set(Calendar.DATE, remindTime.get(Calendar.DATE) + 30);
			filter.eq("state", ContractStatusEnum.EXECUTE).and(Filter.Le("endDate", remindTime.getTime()),Filter.Gt("endDate", new Date()));
		}
		name += "列表";
		refresh(filter);
		fileName = StringUtil.URLEncoderToUTF8(name)+".xls";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		POIUtil.export(name, generateExportColumns(columns), root, out);
		inputStream = new ByteArrayInputStream(out.toByteArray());
		return "export";
	}
	
	private LinkedHashMap<String, String> generateExportColumns(String columns2) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		columns = columns.replace("{", "");
		columns = columns.replace("}", "");
		String[] properties = columns.split(",");
		for (String string : properties) {
			String[] ss = string.split(":");
			String field = ss[0].replace("\"", "");
			String description = ss[1].replace("\"", "");
			map.put(field, description);
		}
		return map;
	}

	public String listByCustomerId(){
		return "listByCustomerId";
	}
	
	public String closeContract(){
		result = contractService.closeContract(id);
		return JSON;
	}
	
	/**
	 * 执行合同
	 * @return
	 */
	public String executeContract(){
		result = contractService.executeContract(id);
		return JSON;
	}
	/**
	 * 待执行合同数量
	 * @return
	 */
	public String executeContractCounts(){
		Filter filter = new Filter(EstateContract.class);
		filter.eq("state", ContractStatusEnum.NEW);
		result = contractService.getRowCount(filter);
		return JSON;
	}
	
	public String approval(){
		result = contractService.approval(id,Long.parseLong(ids));
		return JSON;
	}
	
	public String print(){
		EstateContract contract = contractService.getBeanById(id).getValue();
		result = contractService.print(id);
		return "print_"+contract.getItem().name().toLowerCase();
	}
	
	public String loadTemplate(){
		EstateContract contract = contractService.getBeanById(id).getValue();
		List<EstateContractTemplate> list = contractTemplateService.getListByFilter(new Filter(EstateContractTemplate.class).eq("type", contract.getItem()).eq("chief", BooleanEnum.YES)).getValue();
		if(list!=null && list.size()>0){
			for (EstateContractTemplate contractTemplate : list) {
				templateId = contractTemplate.getId();
			}
			result = Result.success();
		}else{
			result = Result.failure("系统未配置合同模板，请联系管理员！");
		}
		return JSON;
	}
	public String addBillRentAuto(){
		return "addBillRentAuto";
	}
	public String printDoc(){
		fileName = StringUtil.URLEncoderToUTF8("合同")+".doc";
		try {
			String path = contractTemplateService.getBeanById(templateId).getValue().getNewName();
			File template = EstateActivator.getResourcesService().getFileByPath(path);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			contractService.print(id, template, out);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "print";
	}
	
	public String submitApproval(){
		contractService.submitApproval(id);
		return JSON;
	}
	
	
	public String relet(){
		EstateContract contract = contractService.getBeanById(id).getValue();
		contract.setSignDate(new Date());
		contract.setStartDate(new Date());
		contract.setCode(contractService.generateCode(contractModel).getValue());
		contract.setEndDate(CalendarUtil.add(Calendar.YEAR, 1).getTime());
		//SubjectRentAction.setSessionSubjectRentList(subjectRentService.getListByFilter(new Filter(EstateSubjectRent.class).eq("contractId", id)).getValue());
		result = Result.value(contract);
		return "relet";
	}
	
	public String subtract(){
		result = contractService.getBeanById(id);
		subjectRentList = subjectRentService.getListByFilter(new Filter(EstateSubjectRent.class).eq("contractId", id)).getValue();
		return "subtract";
	}
	public String surrender(){
		result = contractService.getBeanById(id);
		subjectRentList = subjectRentService.getListByFilter(new Filter(EstateSubjectRent.class).eq("contractId", id)).getValue();
		return "surrender";
	}
	
	public String submitRelet(){
		result = contractService.submitRelet(contract);
		contract = null;
		return JSON;
	}
	public String submitSubtract(){
		Double money = Double.valueOf(ServletActionContext.getRequest().getParameter("money"));
		result = contractService.submitSubtract(id, subjectRentId, executeTime, money, checkoutnow, memo);
		return JSON;
	}
	public String submitSurrender(){
		String[] moneys = ServletActionContext.getRequest().getParameterValues("moneys");
		List<Double> moneyList = new ArrayList<Double>();
		if(moneys==null){
			result=Result.failure("没有租赁标的，该合同不需要退租");
			return JSON;
		}
		for (int i = 0; i < moneys.length; i++) {
			if(moneys[i].trim().length()>0){
				moneyList.add(Double.valueOf(moneys[i].trim()));
			} else {
				moneyList.add(0d);
			}
		}
		result = contractService.submitSurrender(id, executeTime, moneyList, checkoutnow, memo);
		return JSON;
	}
	
	public String generateCode(){
		try{
		result = contractService.generateCode(contractModel);
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSON;
	}
	
	public String saveOrUpdate(){
		if(contract.getId()==null||contract.getId()<=0)
			return save();
		else return update();
	}
	
	public String save(){
		contract.setState(ContractStatusEnum.NEW);
		result = contractService.save(contract);
		return JSON;
	}
	
	/*public String simpleView(){
		EstateContract contract = contractService.getBeanById(id).getValue();
		contractTemplateList = contractTemplateService.getListByFilter(new Filter(EstateContractTemplate.class).eq("type", contract.getType())).getValue();
		subjectRentList = subjectRentService.getListByFilter(new Filter(EstateSubjectRent.class).eq("contractId", id)).getValue();
		result = Result.value(contract);
		return VIEW;
	}*/
	public String view(){
		String type = ServletActionContext.getRequest().getParameter("type");
		EstateContract contract = contractService.getBeanById(id).getValue();
		contractTemplateList = contractTemplateService.getListByFilter(new Filter(EstateContractTemplate.class).eq("type", contract.getItem())).getValue();
		subjectRentList = subjectRentService.getListByFilter(new Filter(EstateSubjectRent.class).eq("contractId", id)).getValue();
		result = Result.value(contract);
		switch(contract.getItem()){
		case WYHT:
			return "estateView";
		}
		return VIEW;
	}
	
	public String edit(){
		EstateContract contract = contractService.getBeanById(id).getValue();
		result = Result.value(contract);
		if(contract.getItem()!=null){
			switch(contract.getItem()){
				case WYHT:
					return "estateEdit";
			}
		}
		return "editrent";
	}
	
	public String update(){
		EstateContract dbBean = contractService.getBeanById(contract.getId()).getValue();
		BeanUtil.copyProperties(contract, dbBean);
		result = contractService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractService.deleteById(id);
		} else if(ids!=null){
			result = contractService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(EstateContract.class);
		if("execute".equals(type)){
			filter.eq("state", ContractStatusEnum.NEW);
		}
		filter.ne("item", ContractItemEnum.WYHT);//排除物业管理
		String[] includes = {"id","code","name","item","customerName","totalAmount","state","endDate","startDate"};
		filter.include(includes);
		return refresh(filter);
	}
	
	public String listOnDesktop(){
		Filter filter = new Filter(EstateContract.class);
		if(contractIds!=null && !contractIds.equals("null")){
			//工作台中点击具体某栋楼地区即将到期和已过期合同
			String[] idArray = contractIds.split(",");
			Long[] idArray2 = new Long[idArray.length];
			for (int i=0;i<idArray.length;i++) {
				idArray2[i] =Long.parseLong(idArray[i]);
			}
			filter.in("id", idArray2);
		}		
		return refresh(filter);
	}
	
	public String loadContractByCustomerId(){
		result = contractService.getListByFilter(new Filter(EstateContract.class).eq("customerId", id).include("contractNo").include(new String[]{"name","id"}));
		return JSON;
	}
	
	@Override
	protected List<EstateContract> getListByFilter(Filter fitler) {
		return contractService.getListByFilter(fitler).getValue();
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public EstateContract getContract() {
		return contract;
	}

	public void setContract(EstateContract contract) {
		this.contract = contract;
	}

	public List<EstateContract> getContractList() {
		return contractList;
	}

	public void setContractList(List<EstateContract> contractList) {
		this.contractList = contractList;
	}

	public List<EstateContractTemplate> getEstateContractTemplateList() {
		return contractTemplateList;
	}

	public void setEstateContractTemplateList(List<EstateContractTemplate> contractTemplateList) {
		this.contractTemplateList = contractTemplateList;
	}

	public List<EstateSubjectRent> getSubjectRentList() {
		return subjectRentList;
	}

	public void setSubjectRentList(List<EstateSubjectRent> subjectRentList) {
		this.subjectRentList = subjectRentList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSubjectRentId() {
		return subjectRentId;
	}

	public void setSubjectRentId(Long subjectRentId) {
		this.subjectRentId = subjectRentId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRoomNames() {
		return roomNames;
	}

	public void setRoomNames(String roomNames) {
		this.roomNames = roomNames;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getContractIds() {
		return contractIds;
	}

	public void setContractIds(String contractIds) {
		this.contractIds = contractIds;
	}

	public String getContractModel() {
		return contractModel;
	}

	public void setContractModel(String contractModel) {
		this.contractModel = contractModel;
	}

	public int getCountDueRemind() {
		return countDueRemind;
	}

	public void setCountDueRemind(int countDueRemind) {
		this.countDueRemind = countDueRemind;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getRoomIds() {
		return roomIds;
	}

	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public void setBillPlanRentService(BillPlanRentService billPlanRentService) {
		this.billPlanRentService = billPlanRentService;
	}

	public void setSubjectRentService(SubjectRentService subjectRentService) {
		this.subjectRentService = subjectRentService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public void setEstateContractTemplateService(
			ContractTemplateService contractTemplateService) {
		this.contractTemplateService = contractTemplateService;
	}

	public void setCheckoutnow(boolean checkoutnow) {
		this.checkoutnow = checkoutnow;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setContractTemplateService(
			ContractTemplateService contractTemplateService) {
		this.contractTemplateService = contractTemplateService;
	}

	public List<Integer> getIyears() {
		return iyears;
	}

	public void setIyears(List<Integer> iyears) {
		this.iyears = iyears;
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
}

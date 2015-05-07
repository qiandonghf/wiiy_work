package com.wiiy.park.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.wiiy.common.activator.ProjectActivator;
import com.wiiy.common.dto.BillInformDto;
import com.wiiy.common.dto.StatisticGroupDto;
import com.wiiy.common.preferences.R.Customer;
import com.wiiy.common.preferences.enums.BillInOutEnum;
import com.wiiy.common.preferences.enums.BillStatusEnum;
import com.wiiy.common.preferences.enums.BusinessFeeEnum;
import com.wiiy.common.preferences.enums.FacilityTypeEnum;
import com.wiiy.common.preferences.enums.InvoiceTypeEnum;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.UserTypeEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.commons.util.POIUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.BillType;
import com.wiiy.park.entity.Building;
import com.wiiy.park.entity.CommonBill;
import com.wiiy.park.service.BillService;
import com.wiiy.park.service.BillTypeService;
import com.wiiy.park.service.BuildingService;
import com.wiiy.park.service.StatisticService;

/**
 * @author my
 */
public class BillAction extends JqGridBaseAction<CommonBill> {

	private BillService billService;
	private BillTypeService billTypeService;
	private StatisticService statisticService;
	/*private CustomerService customerService;*/
	private BuildingService buildingService;
	private List<BillType> billTypeList;
	@SuppressWarnings("rawtypes")
	private Result result;
	private CommonBill bill;
	private Long id;
	private String ids;
	private Long roomId;
	private Date startTime;
	private Date endTime;
	private String fileName;
	private InputStream inputStream;
	private String time;
	private String type;
	private List<CommonBill> list = new ArrayList<CommonBill>() ;
	private String billIds;//工作台页面传过来的参数
	private String askedFromDesktop;//请求来源判断
	private String STARTTIME;
	private String ENDTIME;
	private String status;
	
	private int totalCount;
	private int settleAccounts;
	
	private int customerToPayCount;
	
	private BusinessFeeEnum feeType;
	
	//工作台出账
	private String rentDay;
	private String depositDay;
	private String facilityDay;
	private int deposit;
	private int facilitySum;
	private Map<String, Integer> facilityMap;
	private Map<String,Integer> feeTypeMap;
	
	private Integer noBillings;//待开票数量
	private Integer checks;//待结算数量
	private Integer remindBills;//待催缴数量
	
	private String settlementType;//结算类型
	
	public List<CommonBill> getList() {
		return list;
	}

	public void setList(List<CommonBill> list) {
		this.list = list;
	}

	private Long customerId;
	private String columns;
	
	public String pay() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String payTime = request.getParameter("payTime");
		result = billService.pay(ids, DateUtil.parse(payTime));
		return JSON;
	}
	
	
	public String desktop(){
		
		return "desktop";
	}
	
	/**
	 * 账单催缴
	 * @return
	 */
	public String remindById(){
		if(id!=null){
			result = billService.remindById(id);
		}else if(ids!=null){
			result = billService.remindByIds(ids);
		}
		return JSON;
	}
	
	
	public String invoiceById(){
		//待调整
		if(id!=null){
			result = billService.getInvoiceDto(id);
		}else if(ids!=null){
			result = billService.getInvoiceDto(ids);
		}
		return "invoiceById";
	}
	
	/**待开票(N) 待结算(N) 待催缴(N)*/
	public String invoiceCounts(){
		noBillings = billService.getRowCount(new Filter(CommonBill.class).eq("inOut", BillInOutEnum.IN).ne("invoiceType", InvoiceTypeEnum.HASBILLING).eq("invoice", BooleanEnum.YES).eq("status", BillStatusEnum.UNPAID));
		
		checks = billService.getRowCount(new Filter(CommonBill.class).eq("inOut", BillInOutEnum.IN).eq("status", BillStatusEnum.UNPAID));
		
		remindBills = billService.getRowCount(new Filter(CommonBill.class).eq("status", BillStatusEnum.UNPAID).eq("invoiceType", InvoiceTypeEnum.HASBILLING));
		
		return JSON;
	}
	
	/**
	 * 开票
	 * @return
	 */
	public String invoice(){
		if(ids!=null){
			result = billService.invoice(bill,ids);
		}else{
			CommonBill dbBean = billService.getBeanById(bill.getId()).getValue();
			BeanUtil.copyProperties(bill, dbBean);
			result = billService.invoice(dbBean);
		}
		return JSON;
	}
	
	public String export(){
		Filter filter = new Filter(CommonBill.class);
		filter.eq("status",BillStatusEnum.UNPAID);
		page=0;//不要分页
		refresh(filter);
		List<Object[]> dataList = new ArrayList<Object[]>();
		for(int i=0;i<root.size();i++){
			Object[] obj = new Object[12];
			obj[0] = ((CommonBill)root.get(i)).getCustomerName();
			obj[1] = ((CommonBill)root.get(i)).getNumber();
			obj[2] = ((CommonBill)root.get(i)).getBillType().getTypeName();
			if(((CommonBill)root.get(i)).getInOut()!=null){
				obj[3] = ((CommonBill)root.get(i)).getInOut().getTitle();
			}else{
				obj[3] = "";
			}
			obj[4] = ((CommonBill)root.get(i)).getFactPayment();
			obj[5] = ((CommonBill)root.get(i)).getFeeStartTime();
			obj[6] = ((CommonBill)root.get(i)).getFeeEndTime();
			obj[7] = ((CommonBill)root.get(i)).getCheckoutTime();
			obj[8] = ((CommonBill)root.get(i)).getPayTime();
			obj[9] = ((CommonBill)root.get(i)).getStatus().getTitle();
			if(((CommonBill) root.get(i)).getRoom()!=null){
				String address = "";
				Building building=new Building();
				building = buildingService.getBeanById(((CommonBill) root.get(i)).getRoom().getBuildingId()).getValue();
				address += building.getPark().getName();
				address += building.getName();
				address += ((CommonBill) root.get(i)).getRoom().getName();
				obj[10] = address;
				obj[11] = ((CommonBill)root.get(i)).getRoom().getRealArea();
			}else{
				obj[10] = "";
				obj[11] = "";
			}
			dataList.add(obj);
		}
		fileName = StringUtil.URLEncoderToUTF8("费用结算列表")+".xls";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		POIUtil.export("费用结算列表", generateColumns(columns), dataList, out);
		inputStream = new ByteArrayInputStream(out.toByteArray());
		return "export";
	}
	
	@SuppressWarnings("unused")
	private LinkedHashMap<String, String> generateExportColumns(String columns){
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
	private String[] generateColumns(String columns){
		String[] column_s = new String[12];
		columns = columns.replace("{", "");
		columns = columns.replace("}", "");
		String[] properties = columns.split(",");
		for (int i=0;i<properties.length;i++) {
			String string = properties[i];
			String[] ss = string.split(":");
			String description = ss[1].replace("\"", "");
			column_s[i]=description;
		}
		return column_s;
	}
	
	
	public String listByCustomerId() {
		return "listByCustomerId";
	}
	
	//工作台账单属性
	private int billCountIn;//多少条账单
	private double receiveCount;//应收总计
	//private List<BillOnDesktopInDto> inDtoList;
	private int billCountOut;//多少条账单
	private double payCount;//应付总计
	//private List<BillOnDesktopOutDto> outDtoList;
	public int getBillCountIn() {
		return billCountIn;
	}

	public void setBillCountIn(int billCountIn) {
		this.billCountIn = billCountIn;
	}

	public double getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(double receiveCount) {
		this.receiveCount = receiveCount;
	}

	/*public List<BillOnDesktopInDto> getInDtoList() {
		return inDtoList;
	}

	public void setInDtoList(List<BillOnDesktopInDto> inDtoList) {
		this.inDtoList = inDtoList;
	}*/

	public int getBillCountOut() {
		return billCountOut;
	}

	public void setBillCountOut(int billCountOut) {
		this.billCountOut = billCountOut;
	}

	public double getPayCount() {
		return payCount;
	}

	public void setPayCount(double payCount) {
		this.payCount = payCount;
	}

	/*public List<BillOnDesktopOutDto> getOutDtoList() {
		return outDtoList;
	}

	public void setOutDtoList(List<BillOnDesktopOutDto> outDtoList) {
		this.outDtoList = outDtoList;
	}
	//工作台账单
	public String listBillOnDesktop(){
		List<Bill> billsIn = billService.getListByHql("select new Bill(b.id,b.billTypeId,b.customerId,b.factPayment,b.createTime) Bill Bill b where b.inOut = '"+BillInOutEnum.IN+"' and b.status ='"+BillStatusEnum.UNPAID+"'").getValue();
		inDtoList = new ArrayList<BillOnDesktopInDto>();
		List<BusinessCustomer> customerList = customerService.getListByHql("select new BusinessCustomer(c.id,c.name) from BusinessCustomer c where c.id in (select b.customerId from Bill b where b.status ='"+BillStatusEnum.UNPAID+"')").getValue();
		Map<Long,String> customerMap = new HashMap<Long, String>();
		if(customerList!=null && customerList.size()>0){
			for (BusinessCustomer customer : customerList) {
				customerMap.put(customer.getId(), customer.getName());
			}
		}
		
		List<BillType> billTypeList = billTypeService.getListByHql("select new BillType(bt.id,bt.typeName) from BillType bt where bt.id in (select b.billTypeId from Bill b where b.status ='"+BillStatusEnum.UNPAID+"')").getValue();
		Map<Long,String> billTypeMap = new HashMap<Long, String>();
		if(billTypeList!=null && billTypeList.size()>0){
			for (BillType billType : billTypeList) {
				billTypeMap.put(billType.getId(), billType.getTypeName());
			}
		}
		billCountIn = 0;
		receiveCount = 0d;
		if(billsIn.size()>0){
			for (Bill bill : billsIn) {
				BillOnDesktopInDto inDto = new BillOnDesktopInDto();
				inDto.setBillId(bill.getId());
				inDto.setBillType(billTypeMap.get(bill.getBillTypeId()));
				inDto.setCreateTime(bill.getCreateTime());
				inDto.setCustomerName(customerMap.get(bill.getCustomer().getCustomerId()));
				if(bill.getFactPayment()!=null){
					inDto.setMoneyPerBill(bill.getFactPayment());
				}else{
					inDto.setMoneyPerBill(0D);
				}
				receiveCount+=inDto.getMoneyPerBill();
				inDtoList.add(inDto);
			}
			billCountIn = inDtoList.size();
		}
		receiveCount = receiveCount/10000;
		
		List<Bill> billsOut = billService.getListByHql("select new Bill(b.id,b.billTypeId,b.customerId,b.factPayment,b.createTime) from Bill b where inOut = '"+BillInOutEnum.OUT+"' and status ='"+BillStatusEnum.UNPAID+"'").getValue();
		outDtoList = new ArrayList<BillOnDesktopOutDto>();
		billCountOut = 0;
		payCount = 0d;
		if(billsOut.size()>0){
			for (Bill bill : billsOut) {
				BillOnDesktopOutDto outDto = new BillOnDesktopOutDto();
				//String billType = billTypeService.getBeanById(bill.getBillTypeId()).getValue().getTypeName();
				//String customerName = customerService.getBeanByHql("select new Customer(c.name) from Customer c where c.id = "+bill.getCustomer().getCustomerId()).getValue().getName();
				outDto.setBillId(bill.getId());
				outDto.setBillType(billTypeMap.get(bill.getBillTypeId()));
				outDto.setCreateTime(bill.getCreateTime());
				outDto.setCustomerName(customerMap.get(bill.getCustomer().getCustomerId()));
				if(bill.getFactPayment()!=null){
					outDto.setMoneyPerBill(bill.getFactPayment());
				}else{
					outDto.setMoneyPerBill(0D);
				}
				payCount+=outDto.getMoneyPerBill();
				outDtoList.add(outDto);
			}
			billCountOut = outDtoList.size();
		}
		payCount = payCount/10000;
		return JSON;
	}*/
	public String settleAccounts(){
		int billsIn = billService.getRowCount(new Filter(CommonBill.class).eq("inOut", BillInOutEnum.IN).eq("status", BillStatusEnum.UNPAID));
		int billsOut = billService.getRowCount(new Filter(CommonBill.class).eq("inOut", BillInOutEnum.OUT).eq("status", BillStatusEnum.UNPAID));
		settleAccounts=billsOut+billsIn;
		return JSON;
	}
	// 导出日结算报表
	public String exportBillByDay() {
		Filter filter = new Filter(CommonBill.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
		if (!STARTTIME.isEmpty() && ENDTIME.isEmpty()) {
			filter.gt("planPayTime", sdf.parse(STARTTIME));
		} else if (STARTTIME.isEmpty() && !ENDTIME.isEmpty()) {
			filter.le("planPayTime", sdf.parse(ENDTIME));
		} else if (!STARTTIME.isEmpty() && !ENDTIME.isEmpty()) {
			filter.between("planPayTime", sdf.parse(STARTTIME), sdf.parse(ENDTIME));
		}else{
			filter.between("planPayTime",sdf.parse(DateUtil.format(new Date(),"yyyy-MM-dd")) ,sdf.parse(DateUtil.format(CalendarUtil.add(new Date(), Calendar.DAY_OF_YEAR, 1).getTime(),"yyyy-MM-dd")) );
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		page = 0;
		List<CommonBill> billList = billService.getListByFilter(filter).getValue();
		List<Object[]> taskList = new ArrayList<Object[]>();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (billList != null && billList.size() > 0) {
			String[] columns = { "序号", "企业名称", "费用类型", "结算金额", "收支", "出账日期",
					"结算日期", "状态" };
			fileName = StringUtil.URLEncoderToUTF8("日结算报表") + ".xls";
			int j = 1;
			for (CommonBill b : billList) {
				List<String> datas = new ArrayList<String>();
				datas.add(j + "");
				j++;
				datas.add(b.getCustomerName());
				datas.add(b.getBillType().getTypeName());
				datas.add(b.getFactPayment() + "");
				datas.add(b.getInOut().getTitle());
				datas.add(DateUtil.format(b.getCheckoutTime(), "yyyy-MM-dd"));
				datas.add(DateUtil.format(b.getPlanPayTime(), "yyyy-MM-dd"));
				datas.add(b.getStatus().getTitle());
				taskList.add(datas.toArray());
			}
			POIUtil.export("日结算报表", columns, taskList, out);
		}
		fileName = StringUtil.URLEncoderToUTF8("日结算报表") + ".xls";
		inputStream = new ByteArrayInputStream(out.toByteArray());
		return "export";
	}

	// 导出月结算报表
	public String exportBillByMonth() {
		time = time + "-10";
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");
		try {
			startTime = fmt.parse(time);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>) statisticService
				.billCostByTime(startTime, "month").getValue();
		List<Object[]> dataList = statisticService.exportBillByTime(startTime,
				"month");
		fileName = StringUtil.URLEncoderToUTF8("月结算报表") + ".xls";
		List<String> colNameList = getColNameList(topGroupList, "总计");
		String[] names = getColumns(dataList, topGroupList, "时间", "收", "付",
				"总计");
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			POIUtil.export("月结算报表", names, dataList, out, colNameList);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
		}
		return "export";
	}

	// 导出年结算报表
	@SuppressWarnings("unchecked")
	public String exportBillByYear() {
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>) statisticService
				.billCostByTime(null, "year").getValue();
		List<Object[]> dataList = statisticService.exportBillByTime(startTime,
				"year");
		fileName = StringUtil.URLEncoderToUTF8("年结算报表") + ".xls";
		List<String> colNameList = getColNameList(topGroupList, "总计");
		String[] names = getColumns(dataList, topGroupList, "时间", "收", "付",
				"总计");
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			POIUtil.export("年结算报表", names, dataList, out, colNameList);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
		}
		return "export";
	}

	// 导出分户明细表
	@SuppressWarnings("unchecked")
	public String exportBillByCustomer() {
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>) statisticService
				.billCostByCustomer(customerId, startTime, endTime).getValue();
		List<Object[]> dataList = statisticService.exportBillByCustomer(
				customerId, startTime, endTime);
		fileName = StringUtil.URLEncoderToUTF8("分户明细表") + ".xls";
		List<String> colNameList = getColNameList(topGroupList, "总计");
		String[] names = getColumns(dataList, topGroupList, "企业名称", "收", "付",
				"总计");
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			POIUtil.export("分户明细表", names, dataList, out, colNameList);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
		}
		return "export";
	}

	// 导出物业应收实收结算报表
	@SuppressWarnings("unchecked")
	public String exportBillByProperty() {
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>) statisticService
				.billCostByProperty(startTime, endTime).getValue();
		List<Object[]> dataList = statisticService.exportBillByProperty(
				startTime, endTime);
		fileName = StringUtil.URLEncoderToUTF8("物业应收实收统计表") + ".xls";
		List<String> colNameList = getColNameList(topGroupList, null);
		String[] names = getColumns(dataList, topGroupList, "年月", "应收", "实收",
				null);
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			POIUtil.export("物业应收实收统计表", names, dataList, out, colNameList);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
		}
		return "export";
	}
	// 导出物业应收未收结算报表
	@SuppressWarnings("unchecked")
	public String exportBillWzubByProperty() {
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>) statisticService
				.billWzubCostByProperty(startTime, endTime).getValue();
		List<Object[]> dataList = statisticService.exportBillWzubByProperty(
				startTime, endTime);
		fileName = StringUtil.URLEncoderToUTF8("物业应收未收统计表") + ".xls";
		List<String> colNameList = getColNameList(topGroupList, null);
		String[] names = getColumns(dataList, topGroupList, "年月", "应收", "未收",
				null);
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			POIUtil.export("物业应收未收统计表", names, dataList, out, colNameList);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
		}
		return "export";
	}

	private String[] getColumns(List<Object[]> dataList,
			List<StatisticGroupDto> topGroupList, String type, String col1,
			String col2, String total) {
		List<String> colNames = new ArrayList<String>();
		List<String> colNames2 = new ArrayList<String>();
		List<String> colNames3 = new ArrayList<String>();
		if (topGroupList != null && topGroupList.size() > 0) {
			for (StatisticGroupDto statisticGroupDto : topGroupList) {
				for (StatisticGroupDto childDto : statisticGroupDto.getGroups()) {
					if (!colNames.contains(col1 + childDto.getName())) {
						colNames.add(col1 + childDto.getName());
						colNames3.add(col1);
					}
					if (!colNames.contains(col2 + childDto.getName())) {
						colNames.add(col2 + childDto.getName());
						colNames3.add(col2);
					}
					if (!colNames2.contains(childDto.getName())) {
						colNames2.add(childDto.getName());
					}
				}
			}
		}
		Integer length = 0;
		for (Object[] objects : dataList) {
			length = objects.length;
		}
		String[] names = new String[length];
		int k = 0;
		names[k++] = "序号";
		names[k++] = type;
		for (String col : colNames3) {
			names[k++] = col;
		}
		if (total != null) {
			names[k++] = col1;
			names[k++] = col2;
		}
		return names;
	}

	private List<String> getColNameList(List<StatisticGroupDto> topGroupList,
			String total) {
		List<String> colNameList = new ArrayList<String>();
		if (topGroupList != null && topGroupList.size() > 0) {
			for (StatisticGroupDto statisticGroupDto : topGroupList) {
				for (StatisticGroupDto childDto : statisticGroupDto.getGroups()) {
					if (!colNameList.contains(childDto.getName())) {
						colNameList.add(childDto.getName());
					}
				}
			}
		}
		if (total != null) {
			colNameList.add(total);
		}
		return colNameList;
	}

	public String billCostByDay() {
		Filter filter = new Filter(CommonBill.class);
		filter.eq("status", BillStatusEnum.FULLPAID);
		if (startTime != null && endTime == null) {
			filter.gt("payTime", startTime);
		} else if (startTime == null && endTime != null) {
			filter.le("payTime", endTime);
		} else if (startTime != null && endTime != null) {
			filter.between("payTime", startTime, endTime);
		} else if (startTime == null && endTime == null) {
			Date tDay = CalendarUtil.add(new Date(), Calendar.DAY_OF_YEAR, 1)
					.getTime();
			String tString = DateUtil.format(tDay, "yyyy-MM-dd");
			Date t = DateUtil.parse(tString, "yyyy-MM-dd");
			String tString2 = DateUtil.format(new Date(), "yyyy-MM-dd");
			Date t2 = DateUtil.parse(tString2, "yyyy-MM-dd");
			filter.between("payTime", t2, t);
		}
		return refresh(filter);
	}

	@SuppressWarnings("rawtypes")
	public String checkoutCount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rentDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		request.setAttribute("rentDay", rentDay);
		String depositDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("deposit");
		request.setAttribute("depositDay", depositDay);
		String facilityDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("facility");
		request.setAttribute("facilityDay", facilityDay);

		String sql1 = "SELECT b.fee_type,count(*) FROM business_business_bill_plan_rent b "
				+ "WHERE b.`status` = 'UNCHECK' "
				+ "AND b.plan_pay_date < '"
				+ DateUtil.format(CalendarUtil.getLatest(
						CalendarUtil.add(Calendar.DAY_OF_MONTH,
								Integer.parseInt(rentDay)).getTime(),
						Calendar.DAY_OF_MONTH), DateUtil.FULL_PATTERN) + "' "
				+ "GROUP BY b.fee_type";
		List list1 = billService.getObjectListBySql(sql1);
		for (Object object : list1) {
			Object[] os = (Object[]) object;
			String feeType = (String) os[0];
			int count = BigInteger.class.cast(os[1]).intValue();
			request.setAttribute(feeType, count);
		}
		String sql2 = "SELECT f.type,count(b.id) "
				+ "FROM business_bill_plan_facility b "
				+ "LEFT JOIN business_facility f on b.facility_id = f.id "
				+ "WHERE b.`status` = 'UNCHECK' "
				+ "AND b.plan_pay_date < '"
				+ DateUtil.format(CalendarUtil.getLatest(
						CalendarUtil.add(Calendar.DAY_OF_MONTH,
								Integer.parseInt(rentDay)).getTime(),
						Calendar.DAY_OF_MONTH), DateUtil.FULL_PATTERN) + "' "
				+ "GROUP BY f.type";
		List list2 = billService.getObjectListBySql(sql2);
		int facilitySum = 0;
		Map<String, Integer> facilityMap = new LinkedHashMap<String, Integer>();
		for (Object object : list2) {
			Object[] os = (Object[]) object;
			String feeType = (String) os[0];
			int count = BigInteger.class.cast(os[1]).intValue();
			facilitySum += count;
			facilityMap
					.put(FacilityTypeEnum.valueOf(feeType).getTitle(), count);
		}
		request.setAttribute("facilityMap", facilityMap);
		request.setAttribute("facilitySum", facilitySum);
		return "checkoutCount";
	}

	@SuppressWarnings("rawtypes")
	public String inList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int status = billService.getRowCount(new Filter(CommonBill.class).eq(
				"status", BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.IN));
		request.setAttribute("count", status);
		Result sum = billService.getProjectionResult(new Filter(CommonBill.class)
				.eq("status", BillStatusEnum.UNPAID)
				.eq("inOut", BillInOutEnum.IN).sum("factPayment"));
		request.setAttribute("sum", sum.getValue());
		Filter filter = new Filter(CommonBill.class);
		filter.eq("inOut", BillInOutEnum.IN);
		filter.eq("status", BillStatusEnum.UNPAID);
		filter.maxResults(6);
		result = billService.getListByFilter(filter);
		return "inList";
	}

	@SuppressWarnings("rawtypes")
	public String outList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int status = billService
				.getRowCount(new Filter(CommonBill.class).eq("status",
						BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.OUT));
		request.setAttribute("count", status);
		Result sum = billService.getProjectionResult(new Filter(CommonBill.class)
				.eq("status", BillStatusEnum.UNPAID)
				.eq("inOut", BillInOutEnum.OUT).sum("factPayment"));
		request.setAttribute("sum", sum.getValue());
		Filter filter = new Filter(CommonBill.class);
		filter.eq("inOut", BillInOutEnum.OUT);
		filter.eq("status", BillStatusEnum.UNPAID);
		filter.maxResults(6);
		result = billService.getListByFilter(filter);
		return "outList";
	}

	public String printCustomerBill() {
		return "printCustomerBill";
	}

	public String customerBillList() {
		//待调整
		/*Customer customer = customerService.getSessionUserCustomer().getValue();
		if(null!=customer){
			customerToPayCount = billService.getRowCount(new Filter(CommonBill.class).eq("status", BillStatusEnum.UNPAID).eq("customerId",customer.getId()));
		}else{
			customerToPayCount = 0;
		}*/
		return "customerBillList";
	}

	public String measure() {
		result = billService.measureStatistics();
		return "measure";
	}

	public String arrearAlert() {
		result = billService.arrearAlertStatistics();
		return "arrearAlert";
	}

	public String inInform() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String urged = request.getParameter("urged");
		String email = request.getParameter("email");
		String sms = request.getParameter("sms");
		result = billService.inInform(urged, email, sms);
		return JSON;
	}

	public String outInform() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String email = request.getParameter("email");
		String sms = request.getParameter("sms");
		result = billService.outInform(email, sms);
		return JSON;
	}

	public String inInformList() {
		Filter filter = new Filter(CommonBill.class).eq("status",
				BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.IN);
		String urged = ServletActionContext.getRequest().getParameter("urged");
		if (urged != null && urged.equals("NO")) {
			ServletActionContext.getRequest().setAttribute("urged", urged);
			filter.eq("urged", BooleanEnum.NO);
		}
		List<CommonBill> billList = billService.getListByFilter(filter).getValue();
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		int urgedTotal = 0;
		/*for (CommonBill bill : billList) {
			if (bill.getUrged() != null
					&& bill.getUrged().equals(BooleanEnum.YES))
				urgedTotal++;
			if (!dtoMap.containsKey(bill.getCustomer().getCustomerId())) {
				dtoMap.put(bill.getCustomer().getCustomerId(),
						new BillInformDto(bill.getCustomer().getCustomerId(), bill
								.getCustomer().getName()));
			}
			dtoMap.get(bill.getCustomer().getCustomerId()).add(bill);
		}*/
		for (CommonBill bill : billList) {
			if(bill.getUrged()!=null && bill.getUrged().equals(BooleanEnum.YES)) urgedTotal++;
			if(!dtoMap.containsKey(bill.getCustomerId())) {
				dtoMap.put(bill.getCustomerId(),new BillInformDto(bill.getCustomerId(),bill.getCustomerName()));
			}
			dtoMap.get(bill.getCustomerId()).add(bill);
		}
		ServletActionContext.getRequest()
				.setAttribute("urgedTotal", urgedTotal);
		ServletActionContext.getRequest().setAttribute("customerTotal",
				dtoMap.size());
		ServletActionContext.getRequest().setAttribute("billTotal",
				billList.size());
		result = Result.value(dtoMap.values());
		return "inInformList";
	}
	/**
	 * 根据企业编号获取企业缴费信息
	 * @return
	 */
	public String inInformListById() {
		Long customerId = null ;
		String[] idLIist = null ;
		Filter filter = null ;
		List<CommonBill> billList = null ;
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		if (ids.length()>1) {
			idLIist = ids.split(",");
			for (int i = 0; i < idLIist.length; i++) {
				 customerId =Long.parseLong(idLIist[i]);
				 filter = new Filter(CommonBill.class).eq("status",
						BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.IN).eq("id", customerId);
				String urged = ServletActionContext.getRequest().getParameter("urged");
				if (urged != null && urged.equals("NO")) {
					ServletActionContext.getRequest().setAttribute("urged", urged);
					filter.eq("urged", BooleanEnum.NO);
				}
				 billList = billService.getListByFilter(filter).getValue();
					int urgedTotal = 0;
					for (CommonBill bill : billList) {
						if(bill.getUrged()!=null && bill.getUrged().equals(BooleanEnum.YES)) urgedTotal++;
						if(!dtoMap.containsKey(bill.getCustomerId())) {
							dtoMap.put(bill.getCustomerId(),new BillInformDto(bill.getCustomerId(),bill.getCustomerName()));
						}
						dtoMap.get(bill.getCustomerId()).add(bill);
					}
					/*for (CommonBill bill : billList) {
						if (bill.getUrged() != null
								&& bill.getUrged().equals(BooleanEnum.YES))
							urgedTotal++;
						if (!dtoMap.containsKey(bill.getCustomer().getCustomerId())) {
							dtoMap.put(bill.getCustomer().getCustomerId(),
									new BillInformDto(bill.getCustomer().getCustomerId(), bill
											.getCustomer().getName()));
						}
						dtoMap.get(bill.getCustomer().getCustomerId()).add(bill);
					}*/
					ServletActionContext.getRequest()
							.setAttribute("urgedTotal", urgedTotal);
					ServletActionContext.getRequest().setAttribute("customerTotal",
							dtoMap.size());
					ServletActionContext.getRequest().setAttribute("billTotal",
							billList.size());
					result = Result.value(dtoMap.values());
			}
		}else {
			 customerId =Long.parseLong(ids);
			 filter = new Filter(CommonBill.class).eq("status",
			 BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.IN).eq("id", customerId);
			 String urged = ServletActionContext.getRequest().getParameter("urged");
			 if (urged != null && urged.equals("NO")) {
					ServletActionContext.getRequest().setAttribute("urged", urged);
					filter.eq("urged", BooleanEnum.NO);
			 }
			 billList = billService.getListByFilter(filter).getValue();
			 
				int urgedTotal = 0;
				for (CommonBill bill : billList) {
					if(bill.getUrged()!=null && bill.getUrged().equals(BooleanEnum.YES)) urgedTotal++;
					if(!dtoMap.containsKey(bill.getCustomerId())) {
						dtoMap.put(bill.getCustomerId(),new BillInformDto(bill.getCustomerId(),bill.getCustomerName()));
					}
					dtoMap.get(bill.getCustomerId()).add(bill);
				}
				
				/*for (CommonBill bill : billList) {
					if (bill.getUrged() != null
							&& bill.getUrged().equals(BooleanEnum.YES))
						urgedTotal++;
					if (!dtoMap.containsKey(bill.getCustomer().getCustomerId())) {
						dtoMap.put(bill.getCustomer().getCustomerId(),
								new BillInformDto(bill.getCustomer().getCustomerId(), bill
										.getCustomer().getName()));
					}
					dtoMap.get(bill.getCustomer().getCustomerId()).add(bill);
				}*/
				ServletActionContext.getRequest()
						.setAttribute("urgedTotal", urgedTotal);
				ServletActionContext.getRequest().setAttribute("customerTotal",
						dtoMap.size());
				ServletActionContext.getRequest().setAttribute("billTotal",
						billList.size());
				result = Result.value(dtoMap.values());
		}
		return "inInformList";
	}
	/**
	 * 打印缴费通知单
	 * @return
	 */
	public String printPayMentIn() {
		Filter filter = new Filter(CommonBill.class);
		List<CommonBill> billList = new ArrayList<CommonBill>();
		if(null!=ids&&0!=ids.length()){
			billList = billService.getListByFilter(filter.in("id",
					(Object[]) StringUtil.splitToLongArray(ids))).getValue();
		}else{
			billList = billService.getListByFilter(filter.eq("status",BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.IN)).getValue();
		}
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		
		for (CommonBill bill : billList) {
			if (!dtoMap.containsKey(bill.getCustomerId())) {
				dtoMap.put(bill.getCustomerId(),new BillInformDto(bill.getCustomerId(), bill.getCustomerName()));
			}
			dtoMap.get(bill.getCustomerId()).add(bill);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String payTime = request.getParameter("payTime");
		request.setAttribute("payTime", payTime);
		result = Result.value(dtoMap.values());
		return "printPayMentIn";
	}
	public String printPayMentOut() {
		Filter filter = new Filter(CommonBill.class);
		List<CommonBill> billList = new ArrayList<CommonBill>();
		if(null!=ids&&0!=ids.length()){
			billList = billService.getListByFilter(filter.in("id",(Object[]) StringUtil.splitToLongArray(ids))).getValue();
		}else{
			billList = billService.getListByFilter(filter.eq("status",BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.OUT)).getValue();
		}
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		for (CommonBill bill : billList) {
			if (!dtoMap.containsKey(bill.getCustomerId())) {
				dtoMap.put(bill.getCustomerId(),new BillInformDto(bill.getCustomerId(), bill.getCustomerName()));
			}
			dtoMap.get(bill.getCustomerId()).add(bill);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String payTime = request.getParameter("payTime");
		request.setAttribute("payTime", payTime);
		result = Result.value(dtoMap.values());
		return "printPayMentOut";
	}
	/**
	 * 根据企业编号获取企业缴费信息
	 * @return
	 */
	
	
	public String outInformListById() {
		Long customerId = null ;
		String[] idLIist = null ;
		Filter filter = null ;
		List<CommonBill> billList = null ;
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		if (ids.length()>1) {
			idLIist = ids.split(",");
			for (int i = 0; i < idLIist.length; i++) {
				 customerId =Long.parseLong(idLIist[i]);
				 filter = new Filter(CommonBill.class).eq("status",
						BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.OUT).eq("id", customerId);
				String urged = ServletActionContext.getRequest().getParameter("urged");
				if (urged != null && urged.equals("NO")) {
					ServletActionContext.getRequest().setAttribute("urged", urged);
					filter.eq("urged", BooleanEnum.NO);
				}
				 billList = billService.getListByFilter(filter).getValue();
					int urgedTotal = 0;
					for (CommonBill bill : billList) {
						if (bill.getUrged() != null
								&& bill.getUrged().equals(BooleanEnum.YES))
							urgedTotal++;
						if (!dtoMap.containsKey(bill.getCustomerId())) {
							dtoMap.put(bill.getCustomerId(),
									new BillInformDto(bill.getCustomerId(), bill
											.getCustomerName()));
						}
						dtoMap.get(bill.getCustomerId()).add(bill);
					}
					ServletActionContext.getRequest()
							.setAttribute("urgedTotal", urgedTotal);
					ServletActionContext.getRequest().setAttribute("customerTotal",
							dtoMap.size());
					ServletActionContext.getRequest().setAttribute("billTotal",
							billList.size());
					result = Result.value(dtoMap.values());
			}
		}else {
			 customerId =Long.parseLong(ids);
			 filter = new Filter(CommonBill.class).eq("status",
			 BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.OUT).eq("id", customerId);
			 String urged = ServletActionContext.getRequest().getParameter("urged");
			 if (urged != null && urged.equals("NO")) {
					ServletActionContext.getRequest().setAttribute("urged", urged);
					filter.eq("urged", BooleanEnum.NO);
			 }
			 billList = billService.getListByFilter(filter).getValue();
			 
				int urgedTotal = 0;
				for (CommonBill bill : billList) {
					if (bill.getUrged() != null
							&& bill.getUrged().equals(BooleanEnum.YES))
						urgedTotal++;
					if (!dtoMap.containsKey(bill.getCustomerId())) {
						dtoMap.put(bill.getCustomerId(),
								new BillInformDto(bill.getCustomerId(), bill
										.getCustomerName()));
					}
					dtoMap.get(bill.getCustomerId()).add(bill);
				}
				ServletActionContext.getRequest()
						.setAttribute("urgedTotal", urgedTotal);
				ServletActionContext.getRequest().setAttribute("customerTotal",
						dtoMap.size());
				ServletActionContext.getRequest().setAttribute("billTotal",
						billList.size());
				result = Result.value(dtoMap.values());
		}
		return "inInformList";
	}
	/**
	 * 根据企业的编号获取该企业的所有账单
	 * @return
	 */
    public String allBillBycustomer(){
    	Filter filter = null ;
		List<CommonBill> billList = null ;
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		 filter = new Filter(CommonBill.class).eq("customerId", id);
		 String urged = ServletActionContext.getRequest().getParameter("urged");
		 if (urged != null && urged.equals("NO")) {
				ServletActionContext.getRequest().setAttribute("urged", urged);
				filter.eq("urged", BooleanEnum.NO);
		 }
		 billList = billService.getListByFilter(filter).getValue();
		 
			int urgedTotal = 0;
			for (CommonBill bill : billList) {
				if (bill.getUrged() != null
						&& bill.getUrged().equals(BooleanEnum.YES))
					urgedTotal++;
				if (!dtoMap.containsKey(bill.getCustomerId())) {
					dtoMap.put(bill.getCustomerId(),
							new BillInformDto(bill.getCustomerId(), bill
									.getCustomerName()));
				}
				dtoMap.get(bill.getCustomerId()).add(bill);
			}
			ServletActionContext.getRequest()
					.setAttribute("urgedTotal", urgedTotal);
			ServletActionContext.getRequest().setAttribute("customerTotal",
					dtoMap.size());
			ServletActionContext.getRequest().setAttribute("billTotal",
					billList.size());
			result = Result.value(dtoMap.values());
    	return "inInformList" ;
    }
	public String outInformList() {
		List<CommonBill> billList = billService.getListByFilter(
				new Filter(CommonBill.class).eq("status", BillStatusEnum.UNPAID).eq(
						"inOut", BillInOutEnum.OUT)).getValue();
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		for (CommonBill bill : billList) {
			if (!dtoMap.containsKey(bill.getCustomerId())) {
				dtoMap.put(bill.getCustomerId(),
						new BillInformDto(bill.getCustomerId(), bill
								.getCustomerName()));
			}
			dtoMap.get(bill.getCustomerId()).add(bill);
		}
		ServletActionContext.getRequest().setAttribute("customerTotal",
				dtoMap.size());
		ServletActionContext.getRequest().setAttribute("billTotal",
				billList.size());
		result = Result.value(dtoMap.values());
		return "outInformList";
	}

	public String back() {
		result = billService.back(id);
		return JSON;
	}

	public String listByTypeId() {
		Filter filter = new Filter(CommonBill.class);
		filter.eq("billTypeId", id).createAlias("billType", "billType");
		//收款
    	filter.eq("inOut", BillInOutEnum.IN);
		return refresh(filter);
	}

	public String list() {
		Filter filter = new Filter(BillType.class);
		billTypeList = billTypeService.getListByFilter(filter).getValue();
		if("NOBILLING".equals(settlementType)){
			return LIST;
		}else if("BILLING".equals(settlementType)){
			return "BILLING";
		}else if("REMINDBILL".equals(settlementType)){
			return "REMINDBILL";
		}
		return LIST;
	}
	
	public String listOnDeskTop() {
		billTypeList = billTypeService.getListByFilter(
				new Filter(BillType.class)).getValue();
		return "listOnDeskTop";
	}
	
	public String checkoutListBillPlanRent() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rentDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		Date date = CalendarUtil.getLatest(
				CalendarUtil.add(Calendar.DAY_OF_MONTH,
						Integer.parseInt(rentDay)).getTime(),
				Calendar.DAY_OF_MONTH);
		request.setAttribute("date", DateUtil.format(date));
		return "checkoutListBillPlanRent";
	}

	public String checkoutListDeposit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rentDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		Date date = CalendarUtil.getLatest(
				CalendarUtil.add(Calendar.DAY_OF_MONTH,
						Integer.parseInt(rentDay)).getTime(),
				Calendar.DAY_OF_MONTH);
		request.setAttribute("date", DateUtil.format(date));
		return "checkoutListDeposit";
	}

	public String checkoutListBillPlanFacility() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rentDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		Date date = CalendarUtil.getLatest(
				CalendarUtil.add(Calendar.DAY_OF_MONTH,
						Integer.parseInt(rentDay)).getTime(),
				Calendar.DAY_OF_MONTH);
		request.setAttribute("date", DateUtil.format(date));
		return "checkoutListBillPlanFacility";
	}

	public String chargeoff() {
		result = billService.chargeoff(id);
		return JSON;
	}

	public String apart() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String amount = request.getParameter("amount");
		result = billService.apart(id, Double.parseDouble(amount));
		return JSON;
	}

	public String confirm() {
		Filter filter = new Filter(CommonBill.class);
		List<CommonBill> billList = billService.getListByFilter(filter.in("id",
				(Object[]) StringUtil.splitToLongArray(ids))).getValue();
		Map<Long, BillInformDto> dtoMap = new HashMap<Long, BillInformDto>();
		for (CommonBill bill : billList) {
			if (!dtoMap.containsKey(bill.getCustomerId())) {
				dtoMap.put(bill.getCustomerId(),new BillInformDto(bill.getCustomerId(), bill.getCustomerName()));
			}
			dtoMap.get(bill.getCustomerId()).add(bill);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String payTime = request.getParameter("payTime");
		request.setAttribute("payTime", payTime);
		result = Result.value(dtoMap.values());
		return "confirm";
	}

	public String billIn() {
		if(customerId!=null && !customerId.equals("")){
			bill = billService.getBeanByFilter(new Filter(CommonBill.class).eq("customerId", customerId)).getValue();
		}
		return "in";
	}

	public String out() {
		if(customerId!=null && !customerId.equals("")){
			bill = billService.getBeanByFilter(new Filter(CommonBill.class).eq("customerId", customerId)).getValue();
		}
		return "out";
	}
	
	/**
	 * 根据公司编号查询该公司的所有支出账单
	 * @return
	 */
    public String outBill(){
    	Filter filter = new Filter(CommonBill.class);
    	filter.eq("status",BillStatusEnum.UNPAID).eq("inOut", BillInOutEnum.OUT).eq("customerId", customerId);
    	return refresh(filter);
    }
    /**
	 * 根据公司编号查询该公司的所有收入账单
	 * @return
	 */
    public String inBill(){
    	Filter filter = new Filter(CommonBill.class);
    	filter.eq("status",BillStatusEnum.UNPAID).eq("customerId", customerId);
    	//收款
    	filter.eq("inOut", BillInOutEnum.IN);
    	return refresh(filter);
    }
	@SuppressWarnings("rawtypes")
	public String checkout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rentDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		request.setAttribute("rentDay", rentDay);
		String depositDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("deposit");
		request.setAttribute("depositDay", depositDay);
		String facilityDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("facility");
		request.setAttribute("facilityDay", facilityDay);

		String sql1 = "SELECT b.fee_type,count(*) FROM business_business_bill_plan_rent b "
				+ "WHERE b.`status` = 'UNCHECK' "
				+ "AND b.plan_pay_date < '"
				+ DateUtil.format(CalendarUtil.getLatest(
						CalendarUtil.add(Calendar.DAY_OF_MONTH,
								Integer.parseInt(rentDay)).getTime(),
						Calendar.DAY_OF_MONTH), DateUtil.FULL_PATTERN) + "' "
				+ "GROUP BY b.fee_type";
		List list1 = billService.getObjectListBySql(sql1);
		for (Object object : list1) {
			Object[] os = (Object[]) object;
			String feeType = (String) os[0];
			int count = BigInteger.class.cast(os[1]).intValue();
			request.setAttribute(feeType, count);
		}
		/*String sql2 = "SELECT f.type,count(b.id) "
				+ "FROM business_bill_plan_facility b "
				+ "LEFT JOIN business_facility f on b.facility_id = f.id "
				+ "WHERE b.`status` = 'UNCHECK' "
				+ "AND b.plan_pay_date < '"
				+ DateUtil.format(CalendarUtil.getLatest(
						CalendarUtil.add(Calendar.DAY_OF_MONTH,
								Integer.parseInt(rentDay)).getTime(),
						Calendar.DAY_OF_MONTH), DateUtil.FULL_PATTERN) + "' "
				+ "GROUP BY f.type";
		List list2 = billService.getObjectListBySql(sql2);*/
		int facilitySum = 0;
		Map<String, Integer> facilityMap = new LinkedHashMap<String, Integer>();
		/*for (Object object : list2) {
			Object[] os = (Object[]) object;
			String feeType = (String) os[0];
			int count = BigInteger.class.cast(os[1]).intValue();
			facilitySum += count;
			facilityMap
					.put(FacilityTypeEnum.valueOf(feeType).getTitle(), count);
		}*/
		request.setAttribute("facilityMap", facilityMap);
		request.setAttribute("facilitySum", facilitySum);
		return "checkout";
	}
	
	@SuppressWarnings("rawtypes")
	public String countCheckout(){
		totalCount=0;
		HttpServletRequest request = ServletActionContext.getRequest();
		String rentDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		request.setAttribute("rentDay", rentDay);
		String depositDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("deposit");
		request.setAttribute("depositDay", depositDay);
		String facilityDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("facility");
		request.setAttribute("facilityDay", facilityDay);
		
		String sql1 = "SELECT b.fee_type,count(*) FROM business_business_bill_plan_rent b "
				+ "WHERE b.`status` = 'UNCHECK' "
				+ "AND b.plan_pay_date < '"
				+ DateUtil.format(CalendarUtil.getLatest(
						CalendarUtil.add(Calendar.DAY_OF_MONTH,
								Integer.parseInt(rentDay)).getTime(),
								Calendar.DAY_OF_MONTH), DateUtil.FULL_PATTERN) + "' "
								+ "GROUP BY b.fee_type";
		List list1 = billService.getObjectListBySql(sql1);
		for (Object object : list1) {
			Object[] os = (Object[]) object;
			int count = BigInteger.class.cast(os[1]).intValue();
			totalCount+=count;
		}
		return JSON;
	}
	
	@SuppressWarnings("rawtypes")
	public String checkout2() {
		rentDay = ProjectActivator.getAppConfig()
				.getConfig("billCheckoutForecastDay").getParameter("rent");
		
		String sql1 = "SELECT b.fee_type,count(*) FROM business_business_bill_plan_rent b "
				+ "WHERE b.`status` = 'UNCHECK' "
				+ "AND b.plan_pay_date < '"
				+ DateUtil.format(CalendarUtil.getLatest(
						CalendarUtil.add(Calendar.DAY_OF_MONTH,
								Integer.parseInt(rentDay)).getTime(),
								Calendar.DAY_OF_MONTH), DateUtil.FULL_PATTERN) + "' "
								+ "GROUP BY b.fee_type";
		List list1 = billService.getObjectListBySql(sql1);
		feeTypeMap = new HashMap<String, Integer>();
		for (Object object : list1) {
			Object[] os = (Object[]) object;
			String feeType = (String) os[0];
			int count = BigInteger.class.cast(os[1]).intValue();
			feeTypeMap.put(feeType, count);
		}
		return JSON;
	}

	public String save() {
		bill.setNumber(billService.generateNumber());
		result = billService.save(bill);
		return JSON;
	}

	public String view() {
		result = billService.getBeanById(id);
		return VIEW;
	}

	public String edit() {
		result = billService.getBeanById(id);
		return EDIT;
	}

	public String update() {
		CommonBill dbBean = billService.getBeanById(bill.getId()).getValue();
		BeanUtil.copyProperties(bill, dbBean);
		result = billService.update(dbBean);
		return JSON;
	}

	public String delete() {
		if (id != null) {
			result = billService.deleteById(id);
		} else if (ids != null) {
			result = billService.deleteByIds(ids);
		}
		return JSON;
	}

	public String loadBill() {
		Filter filter = new Filter(CommonBill.class)
				.orderBy("status", Filter.DESC).orderBy("checkoutTime",Filter.DESC);
		if(status!=null && status.equals("UNPAID")){
			filter.eq("status", BillStatusEnum.UNPAID);
		}
		
		//收款
    	filter.eq("inOut", BillInOutEnum.IN);
		
		//企业账户登录 调整
		/*if(checkIfIsCustomer()){
			Customer customer = customerService.getBeanByFilter(new Filter(Customer.class).eq("userId", ProjectActivator.getSessionUser().getId())).getValue();
			filter.eq("customerId", customer.getId());
		}*/
		
		if (type!=null || filters != null) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray rules = jsonObject.getJSONArray("rules");
			if(rules!=null){
				for (Object obj : rules) {
					JSONObject rule = (JSONObject) obj;
					String field = rule.getString("field");
					if (field.indexOf(".") > 0) {
						String alias = field.substring(0, field.indexOf("."));
						filter.createAlias(alias, alias);
					}
				}
			}
		}
		if(billIds!=null && billIds.length()>0){
			//工作台中点击具体某栋楼入驻企业欠费账单
			String[] idArray = billIds.split(",");
			Long[] idArray2 = new Long[idArray.length];
			for (int i=0;i<idArray.length;i++) {
				idArray2[i] =Long.parseLong(idArray[i]);
			}
			filter.in("id", idArray2);
		}
		if(id!=null){
			filter.eq("customerId", id);
		}
		if("NOBILLING".equals(settlementType)){
			filter.ne("invoiceType", InvoiceTypeEnum.HASBILLING).eq("invoice", BooleanEnum.YES).eq("status", BillStatusEnum.UNPAID);
		}else if("BILLING".equals(settlementType)){
			filter.eq("status", BillStatusEnum.UNPAID);
		}else if("REMINDBILL".equals(settlementType)){
			filter.eq("status", BillStatusEnum.UNPAID).eq("invoiceType", InvoiceTypeEnum.HASBILLING);
		}
		return refresh(filter);
	}
	public boolean checkIfIsCustomer() {
		User user = ProjectActivator.getSessionUser();
		if(user.getUserType()==UserTypeEnum.Customer){
			return true;
		}else{
			return false;
		}
		
	}

	public String loadBillForDesktop() {
		Filter filter = new Filter(CommonBill.class).orderBy("customer", Filter.ASC)
				.orderBy("status", Filter.DESC);
		if (type!=null || filters != null) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray rules = jsonObject.getJSONArray("rules");
			for (Object obj : rules) {
				JSONObject rule = (JSONObject) obj;
				String field = rule.getString("field");
				if (field.indexOf(".") > 0) {
					String alias = field.substring(0, field.indexOf("."));
					filter.createAlias(alias, alias);
				}
			}
		}
		if(billIds!=null && billIds.length()>0){
			//工作台中点击具体某栋楼入驻企业欠费账单
			String[] idArray = billIds.split(",");
			Long[] idArray2 = new Long[idArray.length];
			for (int i=0;i<idArray.length;i++) {
				idArray2[i] =Long.parseLong(idArray[i]);
			}
			filter.in("id", idArray2);
		}
		if(id!=null){
			filter.eq("id", id);
		}
		return refresh(filter);
	}

	@Override
	protected List<CommonBill> getListByFilter(Filter fitler) {
		return billService.getListByFilter(fitler).getValue();
	}

	public CommonBill getBill() {
		return bill;
	}

	public void setBill(CommonBill bill) {
		this.bill = bill;
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

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public Result getResult() {
		return result;
	}

	public List<BillType> getBillTypeList() {
		return billTypeList;
	}

	public void setBillTypeList(List<BillType> billTypeList) {
		this.billTypeList = billTypeList;
	}

	public void setBillTypeService(BillTypeService billTypeService) {
		this.billTypeService = billTypeService;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFileName() {
		return fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBillIds() {
		return billIds;
	}

	public void setBillIds(String billIds) {
		this.billIds = billIds;
	}

	/*public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}*/

	public void setBuildingService(BuildingService buildingService) {
		this.buildingService = buildingService;
	}

	public String getAskedFromDesktop() {
		return askedFromDesktop;
	}

	public void setAskedFromDesktop(String askedFromDesktop) {
		this.askedFromDesktop = askedFromDesktop;
	}
	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSettleAccounts() {
		return settleAccounts;
	}

	public void setSettleAccounts(int settleAccounts) {
		this.settleAccounts = settleAccounts;
	}

	public String getSTARTTIME() {
		return STARTTIME;
	}

	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}

	public String getENDTIME() {
		return ENDTIME;
	}

	public void setENDTIME(String eNDTIME) {
		ENDTIME = eNDTIME;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCustomerToPayCount() {
		return customerToPayCount;
	}

	public void setCustomerToPayCount(int customerToPayCount) {
		this.customerToPayCount = customerToPayCount;
	}
	public String getRentDay() {
		return rentDay;
	}

	public void setRentDay(String rentDay) {
		this.rentDay = rentDay;
	}

	public String getDepositDay() {
		return depositDay;
	}

	public void setDepositDay(String depositDay) {
		this.depositDay = depositDay;
	}

	public String getFacilityDay() {
		return facilityDay;
	}

	public void setFacilityDay(String facilityDay) {
		this.facilityDay = facilityDay;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public int getFacilitySum() {
		return facilitySum;
	}

	public void setFacilitySum(int facilitySum) {
		this.facilitySum = facilitySum;
	}

	public Map<String, Integer> getFacilityMap() {
		return facilityMap;
	}

	public void setFacilityMap(Map<String, Integer> facilityMap) {
		this.facilityMap = facilityMap;
	}

	public Map<String, Integer> getFeeTypeMap() {
		return feeTypeMap;
	}

	public void setFeeTypeMap(Map<String, Integer> feeTypeMap) {
		this.feeTypeMap = feeTypeMap;
	}

	public Integer getNoBillings() {
		return noBillings;
	}

	public void setNoBillings(Integer noBillings) {
		this.noBillings = noBillings;
	}

	public Integer getChecks() {
		return checks;
	}

	public void setChecks(Integer checks) {
		this.checks = checks;
	}

	public BusinessFeeEnum getFeeType() {
		return feeType;
	}

	public void setFeeType(BusinessFeeEnum feeType) {
		this.feeType = feeType;
	}

	public Integer getRemindBills() {
		return remindBills;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public void setRemindBills(Integer remindBills) {
		this.remindBills = remindBills;
	}

}

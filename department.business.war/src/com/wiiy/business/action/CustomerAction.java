package com.wiiy.business.action;

import java.io.ByteArrayInputStream;




import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dto.ArchivesDto;
import com.wiiy.business.dto.CategoryDto;
import com.wiiy.business.dto.CustomerTypeDto;
import com.wiiy.business.dto.IncubatedsDto;
import com.wiiy.business.dto.TreeDto;
import com.wiiy.business.entity.BusinessContract;
import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.entity.CustomerCategory;
import com.wiiy.business.entity.CustomerInfo;
import com.wiiy.business.entity.CustomerLabel;
import com.wiiy.business.entity.CustomerLabelRef;
import com.wiiy.business.entity.CustomerQualification;
import com.wiiy.business.entity.CustomerVentureType;
import com.wiiy.business.entity.IncubationInfo;
import com.wiiy.business.entity.IncubationRoute;
import com.wiiy.business.preferences.enums.CustomerTypeEnum;
import com.wiiy.business.preferences.enums.OwnerEnum;
import com.wiiy.business.preferences.enums.ParkStatusEnum;
import com.wiiy.business.service.ContractService;
import com.wiiy.business.service.CustomerCategoryService;
import com.wiiy.business.service.CustomerInfoService;
import com.wiiy.business.service.CustomerLabelService;
import com.wiiy.business.service.CustomerQualificationService;
import com.wiiy.business.service.CustomerService;
import com.wiiy.business.service.CustomerVentureTypeService;
import com.wiiy.business.service.IncubationInfoService;
import com.wiiy.business.service.IncubationRouteService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.commons.util.POIUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.core.service.export.DataDictInitService;
import com.wiiy.core.service.export.OsgiUserService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class CustomerAction extends JqGridBaseAction<BusinessCustomer>{
	
	private CustomerService customerService;
	private ContractService contractService;
	private CustomerCategoryService customerCategoryService;
	private CustomerLabelService customerLabelService;
	private IncubationRouteService incubationRouteService;
	private CustomerQualificationService customerQualificationService;
	private CustomerVentureTypeService customerVentureTypeService;
	private Result result;
	private List<CustomerCategory> customerCategoryList;
	private List<CustomerLabel> customerLabelList;
	private List<CustomerLabel> myLabelList;
	private List<BusinessCustomer> customerList;
	private List<DataDict> incubationRouteList;
	private List<DataDict> customerQualificationList;
	private List<IncubationRoute> incubationRoutes;
	private List<CustomerQualification> customerQualifications;
	private BusinessCustomer customer;
	private CustomerInfo customerInfo;
	private IncubationInfo incubationInfo;
	private List<IncubationInfo> incubationInfoList;
	private CustomerInfoService customerInfoService;
	private IncubationInfoService incubationInfoService;
	private Long id;
	private Long labelId;
	private String routeId;
	private String ids;
	
	private String excelName;
	private InputStream inputStream;
	private String columns;
	
	private String username;
	private String password;
	
	private String status;
	private String statusType;
	
	private String customerIds;
	
	private boolean desktop;
	
	private String form;
	
	private String incubated;
	private CustomerTypeEnum type;
	private String improveArchives;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	private String customerSource;

	public String listByCustomerCategory(){
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
		List<CategoryDto> dtoList = new ArrayList<CategoryDto>();
		Filter filter =new Filter(BusinessCustomer.class);
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
				filter.createAlias("customerInfo.regTime", "regTime");
				filter.between("regTime", startTime, endTime);
		}
		List<BusinessCustomer> customerList=customerService.getListByFilter(filter).getValue();
		if(customerList!=null){
			for (BusinessCustomer list : customerList) {
				CategoryDto dto = new CategoryDto();
				dto.setName(list.getName());
				dto.setType(list.getType());
				dto.setShortName(list.getShortName());
				dto.setNumber(list.getCode());
				dto.setStatus(list.getParkStatus());
				dto.setReceiver(list.getImportName());
				dto.setCreateTime(list.getCreateTime());
				dto.setProductCategory(list.getTechnic());
				dto.setSource(list.getSource());
				dto.setIncubate(list.getIncubated());
				dtoList.add(dto);
			}
			result = Result.value(dtoList);
		}
		return "customerCategoryForm";
	}
	
	public String newCustomerCount(){
		Filter filter = new Filter(BusinessContract.class);
		filter.include("id").include("customerId");
		List<BusinessContract> contractList = contractService.getListByFilter(filter).getValue();
		Long[] ids = null;
		if(contractList!=null && contractList.size()>0){
			ids = new Long[contractList.size()];
			int i = 0;
			for (BusinessContract businessContract : contractList) {
				ids[i++] = businessContract.getCustomerId();
			}
		}
		filter = new Filter(BusinessCustomer.class);
		filter.in("id", ids);
		result = customerService.getRowCount(filter);
		return JSON;
	}
	
	/**
	 * 物业 新招商客户
	 * @return
	 */
	public String newCustomerlist(){
		Filter filter = new Filter(BusinessContract.class);
		filter.include("id").include("customerId");
		List<BusinessContract> contractList = contractService.getListByFilter(filter).getValue();
		Long[] ids = null;
		if(contractList!=null && contractList.size()>0){
			ids = new Long[contractList.size()];
			int i = 0;
			for (BusinessContract businessContract : contractList) {
				ids[i++] = businessContract.getCustomerId();
			}
		}
		filter = new Filter(BusinessCustomer.class);
		filter.in("id", ids);
		filter.createAlias("customerInfo", "customerInfo");
		filter.createAlias("incubationInfo", "incubationInfo");
		if(improveArchives!=null&&!("").equals(improveArchives)){
			filter.eq("improveArchive", BooleanEnum.valueOf(improveArchives));
			return refresh(filter);
		}
		if(statusType!=null&&!("").equals(statusType)){
			List<DataDict> list = BusinessActivator.getDataDictInitService().getDataDictByParentId("business.0025");
			for (DataDict dataDict : list) {
				if(dataDict.getId().equals(statusType)){
					String sql="SELECT id FROM business_business_customer WHERE id in(SELECT customer_id FROM business_incubation_route WHERE id in(SELECT status_id FROM business_incubation_info WHERE status_name='"+dataDict.getDataValue()+"'))";
					List<Object> customerList=customerService.getListBySql(sql);
					Long[] idNum=new Long[customerList.size()];
					int i=0;
					for(Object li : customerList){
						idNum[i]=Long.parseLong(li.toString());
						i++;
					}
					filter.in("id", idNum);
					break;
				}
			}
		 return	refresh(filter);
		}
		if(!("").equals(incubated)&&incubated!=null){
			filter.eq("incubated",BooleanEnum.valueOf(incubated));
		 return	refresh(filter);
		}
		if (type != null) {
				filter.eq("type", type);
		}
		if(filters == null){
				if(labelId!=null && labelId!=-1){
					filter.createAlias("labelRefs", "labelRef");
					filter.eq("labelRef.labelId", labelId);
				}
				if(routeId!=null){
					filter.createAlias("incubationInfo.status", "incubationInfoStatus");
					filter.eq("incubationInfoStatus.routeId", routeId);
				}
			}else{
				if(filters!=null && filters.indexOf("incubationInfoStatusRoute.")>-1){
					filter.createAlias("incubationInfo.status", "incubationInfoStatus");
					filter.createAlias("incubationInfoStatus.route", "incubationInfoStatusRoute");
				}
				if(labelId!=null){
					filter.createAlias("labelRefs", "labelRef");
					filter.eq("labelRef.labelId", labelId);
				}
				if(routeId!=null){
					filter.createAlias("incubationInfo.status", "incubationInfoStatus");
					filter.eq("incubationInfoStatus.routeId", routeId);
				}
			}
			return refresh(filter);
	}
	
	
	public String incubateds(){
		//List<DataDict> list = BusinessActivator.getDataDictInitService().getDataDictByParentId("business.0025");
		String sql="SELECT status_id,status_name,count(*) FROM business_incubation_info WHERE status_name='在孵' OR status_name='毕业' GROUP BY id";
		List<Object> incubationInfoList=incubationInfoService.getListBySql(sql);
		List<IncubatedsDto> dtoList = new ArrayList<IncubatedsDto>();
		IncubatedsDto dto = new IncubatedsDto();
		int i=0;
		int j=0;
		for(Object li : incubationInfoList){
				Object[] obj=(Object[])li;
				if(obj[1].toString().equals("在孵")){
					i++;
				}
				if(obj[1].toString().equals("毕业")){
					j++;
				}
		}
		dto.setIncubateNum(i);
		dto.setGraduateNum(j);
		dtoList.add(dto);
		result=Result.value(dtoList);
		return JSON;
	}
	
	public String amounts(){
		List<CustomerTypeDto> dtoList=new ArrayList<CustomerTypeDto>();
		CustomerTypeEnum[] enums = CustomerTypeEnum.values();
		Filter filter = new Filter(BusinessCustomer.class);
		filter.include("type").include("id");
		List<BusinessCustomer> list = customerService.getListByFilter(filter).getValue();
		int i=0;
		int j=0;
		for(CustomerTypeEnum customerEnum : enums){
				CustomerTypeDto dto = new CustomerTypeDto();
				if(list.size()>0){
					for(BusinessCustomer li : list){
						if(li.getType().equals(customerEnum)){
							i++;
						}
					}
					dto.setValue(customerEnum.getTitle());
					dto.setName(customerEnum.name());
					dto.setAmount(i);
					dtoList.add(dto);
					i=0;
				}else{
					dto.setValue(customerEnum.getTitle());
					dto.setName(customerEnum.name());
					dtoList.add(dto);
				}
		}
		result=Result.value(dtoList);
		return JSON;
	}
	
//	public String booleanEnums(){
//		List<CustomerTypeDto> dtoList=new ArrayList<CustomerTypeDto>();
//		Filter filter = new Filter(BusinessCustomer.class);
//		filter.include("incubated");
//		List<BusinessCustomer> list = customerService.getListByFilter(filter).getValue();
//		CustomerTypeDto dto = new CustomerTypeDto();
//		int j=0;
//		for(BusinessCustomer li : list){
//			if(li.getIncubated().equals(BooleanEnum.YES)){
//				j++;
//			}
//		}
//		dto.setAmount(j);
//		dto.setValue("YES");
//		dtoList.add(dto);
//		result=Result.value(dtoList);
//		return JSON;
//	}
	
	public String workBenchCustomerEdit(){
		List<CustomerVentureType> typeList = customerVentureTypeService.getListByFilter(new Filter(CustomerVentureType.class).eq("customerId", id)).getValue();
		StringBuilder sb = new StringBuilder();
		if(typeList!=null && typeList.size()>0){
			for(CustomerVentureType it : typeList){
				sb.append(it.getVentureTypeId()).append(",");  
			}
			if(sb.toString().endsWith(","))sb.deleteCharAt(sb.length()-1);
		}
		ServletActionContext.getRequest().setAttribute("typeIds", sb.toString());
		incubationRoutes = incubationRouteService.getListByFilter(new Filter(IncubationRoute.class).eq("customerId", id)).getValue();
		customerQualifications = customerQualificationService.getListByFilter(new Filter(CustomerQualification.class).eq("customerId", id)).getValue();
		customer = customerService.getBeanByFilter(new Filter(BusinessCustomer.class).eq("id", id).fetchMode("labelRefs", Filter.JOIN)).getValue();
		result = Result.value(customer);
		return "workBenchCustomerEdit";
	}
	
	public String workBenchCustomerList(){
		result = customerService.getListByHql("select new Customer(c.id,c.name,c.parkStatus) from BusinessCustomer c where c.parkStatus != '"+ParkStatusEnum.APPLY+"' and c.time > '"+DateUtil.format(CalendarUtil.add(Calendar.DAY_OF_MONTH, -30).getTime())+"'");
		return JSON;
	}
	
	public String initRZKH(){
		result = customerService.getListByLimitNum(6);
		return JSON;
	}
	
	public String add(){
		incubationRouteList = new ArrayList<DataDict>();
		customerQualificationList = new ArrayList<DataDict>();
		DataDictInitService dataDictInitService = BusinessActivator.getDataDictInitService();
		if(dataDictInitService!=null){
			incubationRouteList = dataDictInitService.getDataDictByParentId("business.0025");
			customerQualificationList = dataDictInitService.getDataDictByParentId("business.0027");
		}
		return "add";
	}
	
	/*public String parkStatusGraph(){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("width", request.getParameter("width"));
		result = statisticService.customerParkStatus();
		return "parkStatusGraph";
	}*/
	
	public String importCard(){
		result = customerService.importCard(ids);
		return JSON;
	}
	
	public String configAccount(){
		return "configAccount";
	}
	
	public String saveAccount(){
		result = customerService.saveAccount(id,username,password);
		return JSON;
	}
	
	public String updatePassword(){
		OsgiUserService userService = BusinessActivator.getService(OsgiUserService.class);
		result = Result.value(userService.getById(id));
		return "updatePassword";
	}
	
	public String updateAccountPassword(){
		result = customerService.updateAccountPassword(id,password);
		return JSON;
	}
	
	public String loadCategory(){
		Filter filter = new Filter(CustomerCategory.class);
		filter.or(Filter.Eq("ownerId", BusinessActivator.getSessionUser().getId()),Filter.Eq("ownerEnum", OwnerEnum.PUBLIC));
		//customerCategoryList = customerCategoryService.getListByFilter(filter).getValue();
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		TreeDto allCustomerDto = new TreeDto();
		allCustomerDto.setId(-1l);
		allCustomerDto.setText("所有企业");
		dtoList.add(allCustomerDto);
		
		/*TreeDto incubatorRoteDto = new TreeDto();
		incubatorRoteDto.setId(-2l);
		incubatorRoteDto.setText("孵化状态分组");
		incubatorRoteDto.setState(TreeDto.CLOSED);
		dtoList.add(incubatorRoteDto);
		
		for (CustomerCategory category : customerCategoryList) {
			TreeDto dto = new TreeDto();
			dto.setId(category.getId());
			dto.setText(category.getName()+"<input type='hidden' value='"+category.getId()+"'>");
			dto.setState(TreeDto.CLOSED);
			dtoList.add(dto);
		}*/
		TreeDto myLabelDto = new TreeDto();
		myLabelDto.setId(0l);
		myLabelDto.setText("我的分组");
		myLabelDto.setState(TreeDto.CLOSED);
		dtoList.add(myLabelDto);
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String loadIncubatorRote(){
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		List<DataDict> list = BusinessActivator.getDataDictInitService().getDataDictByParentId("business.0025");
		for (DataDict dataDict : list) {
			TreeDto dto = new TreeDto();
			dto.setId(-2L);
			dto.setText(dataDict.getDataValue()+"<input type='hidden' value='"+dataDict.getId()+"'>");
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String loadLabelByCategoryId(){
		Filter filter = new Filter(CustomerLabel.class);
		System.out.println(id);
		if(id!=0){
			filter.eq("categoryId", id);
		} else {
			filter.eq("ownerEnum",OwnerEnum.PRIVATE).eq("ownerId", BusinessActivator.getSessionUser().getId());
		}
		customerLabelList = customerLabelService.getListByFilter(filter).getValue();
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		for (CustomerLabel category : customerLabelList) {
			TreeDto dto = new TreeDto();
			dto.setId(category.getId());
			dto.setText(category.getName()+"<input type='hidden' value='"+category.getId()+"'>");
			dto.setState(TreeDto.OPEN);
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String loadMyLabel(){
		Filter filter = new Filter(CustomerLabel.class);
		filter.eq("ownerEnum",OwnerEnum.PRIVATE).eq("ownerId", BusinessActivator.getSessionUser().getId());
		customerLabelList = customerLabelService.getListByFilter(filter).getValue();
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		for (CustomerLabel category : customerLabelList) {
			TreeDto dto = new TreeDto();
			dto.setId(category.getId());
			dto.setText(category.getName());
			dto.setState(TreeDto.OPEN);
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String export(){
		Filter filter = new Filter(BusinessCustomer.class);
		filter.createAlias("customerInfo", "customerInfo");
		filter.createAlias("incubationInfo", "incubationInfo");
		if(labelId!=null){
			filter.createAlias("labelRefs", "labelRef");
			filter.eq("labelRef.labelId", labelId);
		}
		page=0;//不要分页
		refresh(filter);
		excelName = StringUtil.URLEncoderToUTF8("企业列表")+".xls";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		POIUtil.export("企业列表", generateExportColumns(columns), root, out);
		inputStream = new ByteArrayInputStream(out.toByteArray());
		return "export";
	}
	
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
	
	public String multiSelect(){
		select();
		customerList = customerService.getListByFilter(new Filter(BusinessCustomer.class).include("id").include("name")).getValue();
		return "multiSelect";
	}
	
	public String select(){
		Filter filter = new Filter(CustomerCategory.class);
		filter.or(Filter.Eq("ownerId", BusinessActivator.getSessionUser().getId()),Filter.Eq("ownerEnum", OwnerEnum.PUBLIC));
		customerCategoryList = customerCategoryService.getListByFilter(filter).getValue();
		customerLabelList = customerLabelService.getList().getValue();
		myLabelList = customerLabelService.getListByFilter(new Filter(CustomerLabel.class).eq("ownerEnum",OwnerEnum.PRIVATE).eq("ownerId", BusinessActivator.getSessionUser().getId())).getValue();
		return SELECT;
	}
	
	public String generateCode(){
		result = customerService.generateCode();
		return JSON;
	}
	
	public String save(){
		List<BusinessCustomer> list = customerService.getListByFilter(new Filter(BusinessCustomer.class).eq("name", customer.getName())).getValue();
		if(list!=null && list.size()>0){
			result = Result.failure("企业名称不能重复");
			return JSON;
		}
		if(customerInfo==null)customerInfo = new CustomerInfo();
		else{
			if(("").equals(customerInfo.getRegTypeId()))customerInfo.setRegTypeId(null);
			if(("").equals(customerInfo.getDocumentTypeId()))customerInfo.setDocumentTypeId(null);
			if(("").equals(customerInfo.getCurrencyTypeId()))customerInfo.setCurrencyTypeId(null);
		}
		if(incubationInfo==null)incubationInfo = new IncubationInfo();
		String[] enterpriseTypeIds = ServletActionContext.getRequest().getParameterValues("enterpriseTypeId");
		String[] incubationRouteIds = ServletActionContext.getRequest().getParameterValues("incubationRouteId");
		String[] incubationRouteTimes = ServletActionContext.getRequest().getParameterValues("incubationRouteTime");
		String[] customerQualificationIds = ServletActionContext.getRequest().getParameterValues("customerQualificationId");
		String[] custimerQualificationTimes = ServletActionContext.getRequest().getParameterValues("custimerQualificationTime");
		result = customerService.save(customer,customerInfo,incubationInfo,ids,incubationRouteIds,incubationRouteTimes,customerQualificationIds,custimerQualificationTimes,enterpriseTypeIds);
		return JSON;
	}
	
	public String simpleView(){
		customer = customerService.getBeanById(id).getValue();
		result = Result.value(customer);
		loadCustoemrOtherInfo();
		return "simpleView";
	}
	
	private void loadCustoemrOtherInfo(){
		OsgiUserService userService = BusinessActivator.getService(OsgiUserService.class);
		if(customer.getUserId()!=null){
			User user = userService.getById(customer.getUserId());
			ServletActionContext.getRequest().setAttribute("user", user);
		}
	}
	
	public String view(){
		if(id==null){                                    //id为空说明是企业帐号查看。
			customer = customerService.getSessionUserCustomer().getValue();
			if(customer==null){
				return VIEW;
			}else{
				if(customer.getLabelRefs()!=null){
					Iterator<CustomerLabelRef> it = customer.getLabelRefs().iterator();
					while (it.hasNext()) {
						CustomerLabelRef ref = it.next();
						CustomerLabel label = ref.getCustomerLabel();
						if(label.getOwnerEnum().equals(OwnerEnum.PRIVATE) && label.getOwnerId().longValue() != BusinessActivator.getSessionUser().getId().longValue()){
							it.remove();
						}
					}
				}
				incubationRoutes = incubationRouteService.getListByFilter(new Filter(IncubationRoute.class).eq("customerId", customer.getId())).getValue();
				customerQualifications = customerQualificationService.getListByFilter(new Filter(CustomerQualification.class).eq("customerId", customer.getId())).getValue();
				result = Result.value(customer);
				loadCustoemrOtherInfo();
				if(BusinessActivator.getHttpSessionService().isInResourceMap("ps_customerMessage_basicView")){
					return "customerView";
				}else if(BusinessActivator.getHttpSessionService().isInResourceMap("ps_linkman")){
					return "customerLinkman";
				}else if(BusinessActivator.getHttpSessionService().isInResourceMap("ps_contectInfo")){
					return "customerContectInfo";
				}else if(BusinessActivator.getHttpSessionService().isInResourceMap("ps_customer_investmentView")){
					return "customerInvestmentView";
				}else if(BusinessActivator.getHttpSessionService().isInResourceMap("ps_staffer")){
					return "customerStaffer";
				}else if(BusinessActivator.getHttpSessionService().isInResourceMap("ps_knowledge")){
					return "customerKnowledge";
				}else if(BusinessActivator.getHttpSessionService().isInResourceMap("ps_dataFill")){
					return "customerDataFill";
				}else {
					return "error";
				}
			}
		} else {
			customer = customerService.getBeanByFilter(new Filter(BusinessCustomer.class).eq("id", id).fetchMode("labelRefs", Filter.JOIN)).getValue();
		}
		if(customer.getLabelRefs()!=null){
			Iterator<CustomerLabelRef> it = customer.getLabelRefs().iterator();
			while (it.hasNext()) {
				CustomerLabelRef ref = it.next();
				CustomerLabel label = ref.getCustomerLabel();
				if(label.getOwnerEnum().equals(OwnerEnum.PRIVATE) && label.getOwnerId().longValue() != BusinessActivator.getSessionUser().getId().longValue()){
					it.remove();
				}
			}
		}
		incubationRoutes = incubationRouteService.getListByFilter(new Filter(IncubationRoute.class).eq("customerId", customer.getId())).getValue();
		customerQualifications = customerQualificationService.getListByFilter(new Filter(CustomerQualification.class).eq("customerId", customer.getId())).getValue();
		/*IncubationRoute incubationRoute = incubationRouteService.getBeanById(customer.getIncubationInfo().getStatusId()).getValue();
		status = incubationRoute.getRoute().getDataValue();*/
		result = Result.value(customer);
		loadCustoemrOtherInfo();
		if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerMessage_basicView")){
			return VIEW;
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_linkman")){
			return "linkman";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contectInfo")){
			return "contectInfo";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customer_investmentView")){
			return "investmentView";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_staffer")){
			return "staffer";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_knowledge")){
			return "knowledge";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractMessage")){
			return "contractMessage";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_bill_listByCustomer")){
			return "billMessage";
		}else if(BusinessActivator.getHttpSessionService().isInResourceMap("business_dataFill")){
			return "dataFill";
		}else {
			return "error";
		}
	}
	
	public String edit(){
		List<CustomerVentureType> typeList = customerVentureTypeService.getListByFilter(new Filter(CustomerVentureType.class).eq("customerId", id)).getValue();
		StringBuilder sb = new StringBuilder();
		if(typeList!=null && typeList.size()>0){
			for(CustomerVentureType it : typeList){
				sb.append(it.getVentureTypeId()).append(",");  
			}
			if(sb.toString().endsWith(","))sb.deleteCharAt(sb.length()-1);
		}
		ServletActionContext.getRequest().setAttribute("typeIds", sb.toString());
		incubationRoutes = incubationRouteService.getListByFilter(new Filter(IncubationRoute.class).eq("customerId", id)).getValue();
		customerQualifications = customerQualificationService.getListByFilter(new Filter(CustomerQualification.class).eq("customerId", id)).getValue();
		customer = customerService.getBeanByFilter(new Filter(BusinessCustomer.class).eq("id", id).fetchMode("labelRefs", Filter.JOIN)).getValue();
		result = Result.value(customer);
		return EDIT;
	}
	
	public String update(){
		List<BusinessCustomer> list = customerService.getListByFilter(new Filter(BusinessCustomer.class).ne("id", customer.getId()).eq("name", customer.getName())).getValue();
		if(list!=null && list.size()>0){
			result = Result.failure("企业名称不能重复");
			return JSON;
		}
		String[] enterpriseTypeIds = ServletActionContext.getRequest().getParameterValues("enterpriseTypeId");
		String[] incubationRouteIds = ServletActionContext.getRequest().getParameterValues("incubationRouteId");
		String[] incubationRouteTimes = ServletActionContext.getRequest().getParameterValues("incubationRouteTime");
		String[] customerQualificationIds = ServletActionContext.getRequest().getParameterValues("customerQualificationId");
		String[] custimerQualificationTimes = ServletActionContext.getRequest().getParameterValues("custimerQualificationTime");
		result = customerService.update(customer,customerInfo,incubationInfo,ids,incubationRouteIds,incubationRouteTimes,customerQualificationIds,custimerQualificationTimes,enterpriseTypeIds);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerService.deleteById(id);
		} else if(ids!=null){
			result = customerService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String loadCustomer(){
		Filter filter = new Filter(BusinessCustomer.class);
		filter.include("id").include("name");
		if(labelId!=null && labelId!=0){
			filter.createAlias("labelRefs", "labelRef");
			filter.eq("labelRef.labelId", labelId);
		}
		customerList = customerService.getListByFilter(filter).getValue();
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		for (BusinessCustomer customer : customerList) {
			TreeDto dto = new TreeDto();
			dto.setId(customer.getId());
			dto.setText(customer.getName()+"<input type='hidden' value='"+customer.getId()+"'>");
			dto.setState(TreeDto.OPEN);
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String simpleList(){
		Filter filter = new Filter(BusinessCustomer.class);
		filter.include("id").include("name");
		if(labelId!=null){
			filter.createAlias("labelRefs", "labelRef");
			filter.eq("labelRef.labelId", labelId);
		}
		return refresh(filter);
	}
	public String improveArchives(){
		String sql="SELECT count(*) FROM business_business_customer where improve_archive='NO'";
		List<Object> list=customerService.getListBySql(sql);
		int num=Integer.parseInt(list.get(0).toString());
		result=Result.value(num);
		return JSON;
	}
	public String listByArchives(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		Format format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times=format1.format(calendar.getTime());
		String sql="SELECT id,short_name FROM business_business_customer WHERE modify_time>'"+times+"' and improve_archive='NO'";
		List<Object> objList=customerService.getListBySql(sql);
		List<ArchivesDto> dtoList = new ArrayList<ArchivesDto>();
			for (Object li : objList) {
				ArchivesDto dto = new ArchivesDto();
				Object[] obj=(Object[])li;
				dto.setCustomerId(Long.parseLong(obj[0].toString()));
				dto.setName(obj[1].toString());
				dtoList.add(dto);
			}
		result=Result.value(dtoList);
		return JSON;
	}
	//incubationInfoStatus.route.id
	public String listByEmployee(){
		Filter filter = new Filter (BusinessCustomer.class);
		if(customerSource=="improtme"){
			filter.eq("importName", "importName");
		}
		if(customerSource=="followme"){
			filter.eq("importName", "hostName");
		}
		return refresh(filter);
	}
	public String list(){
		String value=null;
		Filter filter = new Filter(BusinessCustomer.class);
		filter.createAlias("customerInfo", "customerInfo");
		filter.createAlias("incubationInfo", "incubationInfo");
		if(improveArchives!=null&&!("").equals(improveArchives)){
			filter.eq("improveArchive", BooleanEnum.valueOf(improveArchives));
			return refresh(filter);
		}
		if("importMe".equals(customerSource)){
			if(BusinessActivator.getSessionUser().getId()!=null){
				filter.eq("importId",BusinessActivator.getSessionUser().getId());
			}
			if(filters!=null && filters.length()>0){
				value = serachByLikeFromFilters("infoAll",filters);		
			}
			if(value!=null){
				filter.or(Filter.Like("code", value),Filter.Like("name", value),Filter.Like("importName", value),Filter.Like("hostName", value));
			}
			return refresh(filter);
		}
		if("followMe".equals(customerSource)){
			if(BusinessActivator.getSessionUser().getId()!=null){
				filter.eq("hostId",BusinessActivator.getSessionUser().getId());
			}
			if(filters!=null && filters.length()>0){
				value = serachByLikeFromFilters("infoAll",filters);		
			}
			if(value!=null){
				filter.or(Filter.Like("code", value),Filter.Like("name", value),Filter.Like("importName", value),Filter.Like("hostName", value));
			}
			return refresh(filter);
		}
		if(statusType!=null&&!("").equals(statusType)){
			List<DataDict> list = BusinessActivator.getDataDictInitService().getDataDictByParentId("business.0025");
			for (DataDict dataDict : list) {
				if(dataDict.getId().equals(statusType)){
					String sql="SELECT id FROM business_business_customer WHERE id in(SELECT customer_id FROM business_incubation_route WHERE id in(SELECT status_id FROM business_incubation_info WHERE status_name='"+dataDict.getDataValue()+"'))";
					List<Object> customerList=customerService.getListBySql(sql);
					Long[] idNum=new Long[customerList.size()];
					int i=0;
					for(Object li : customerList){
						idNum[i]=Long.parseLong(li.toString());
						i++;
					}
					filter.in("id", idNum);
					break;
				}
			}
			
		 return	refresh(filter);
		}
		if(!("").equals(incubated)&&incubated!=null){
			filter.eq("incubated",BooleanEnum.valueOf(incubated));
		 return	refresh(filter);
		}
		if (type != null) {
				filter.eq("type", type);
		}
		if(filters == null){
				if(labelId!=null && labelId!=-1){
					filter.createAlias("labelRefs", "labelRef");
					filter.eq("labelRef.labelId", labelId);
				}
				if(routeId!=null){
					filter.createAlias("incubationInfo.status", "incubationInfoStatus");
					filter.eq("incubationInfoStatus.routeId", routeId);
				}
			}else{
				if(filters!=null && filters.indexOf("incubationInfoStatusRoute.")>-1){
					filter.createAlias("incubationInfo.status", "incubationInfoStatus");
					filter.createAlias("incubationInfoStatus.route", "incubationInfoStatusRoute");
				}
				if(labelId!=null){
					filter.createAlias("labelRefs", "labelRef");
					filter.eq("labelRef.labelId", labelId);
				}
				if(routeId!=null){
					filter.createAlias("incubationInfo.status", "incubationInfoStatus");
					filter.eq("incubationInfoStatus.routeId", routeId);
				}
				if(filters!=null && filters.length()>0){
					value = serachByLikeFromFilters("infoAll",filters);		
				}
				if(value!=null){
					filter.or(Filter.Like("code", value),Filter.Like("name", value),Filter.Like("importName", value),Filter.Like("hostName", value));
				}
			}
			//createCustomerAccount();
			return refresh(filter);
	}
	
	public String listOnDesktop(){
		Filter filter = new Filter(BusinessCustomer.class);
		filter.createAlias("customerInfo", "customerInfo");
		filter.createAlias("incubationInfo", "incubationInfo");
		if(filters == null){
			if(labelId!=null && labelId!=-1){
				filter.createAlias("labelRefs", "labelRef");
				filter.eq("labelRef.labelId", labelId);
			}
		}else{
			if(filters!=null && filters.indexOf("incubationInfoStatusRoute.")>-1){
				filter.createAlias("incubationInfo.status", "incubationInfoStatus");
				filter.createAlias("incubationInfoStatus.route", "incubationInfoStatusRoute");
			}
			if(labelId!=null){
				filter.createAlias("labelRefs", "labelRef");
				filter.eq("labelRef.labelId", labelId);
			}
		}
		
		if(customerIds!=null && !customerIds.equals("null")){
			//工作台中点击具体某栋楼入驻企业欠费账单
			String[] idArray = customerIds.split(",");
			Long[] idArray2 = new Long[idArray.length];
			for (int i=0;i<idArray.length;i++) {
				idArray2[i] =Long.parseLong(idArray[i]);
			}
			filter.in("id", idArray2);
		}
		return refresh(filter);
	}
	public void createCustomerAccount(){//给迁移到数据库中的企业创建账号 专用方法
		customerService.createCustomerAccount();
	}
	
	@Override
	protected List<BusinessCustomer> getListByFilter(Filter fitler) {
		return customerService.getListByFilter(fitler).getValue();
	}	
	
	public BusinessCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(BusinessCustomer customer) {
		this.customer = customer;
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

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public void setCustomerCategoryService(
			CustomerCategoryService customerCategoryService) {
		this.customerCategoryService = customerCategoryService;
	}

	public List<CustomerCategory> getCustomerCategoryList() {
		return customerCategoryList;
	}

	public Result getResult() {
		return result;
	}
	
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public IncubationInfo getIncubationInfo() {
		return incubationInfo;
	}

	public void setIncubationInfo(IncubationInfo incubationInfo) {
		this.incubationInfo = incubationInfo;
	}

	public List<CustomerLabel> getCustomerLabelList() {
		return customerLabelList;
	}
	
	public Long getLabelId() {
		return labelId;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getExcelName() {
		return excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public List<BusinessCustomer> getCustomerList() {
		return customerList;
	}

	public void setCustomerLabelService(CustomerLabelService customerLabelService) {
		this.customerLabelService = customerLabelService;
	}

	public List<CustomerLabel> getMyLabelList() {
		return myLabelList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIncubationRouteService(
			IncubationRouteService incubationRouteService) {
		this.incubationRouteService = incubationRouteService;
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
	public List<IncubationRoute> getIncubationRoutes() {
		return incubationRoutes;
	}

	public void setIncubationRoutes(List<IncubationRoute> incubationRoutes) {
		this.incubationRoutes = incubationRoutes;
	}

	public List<CustomerQualification> getCustomerQualifications() {
		return customerQualifications;
	}

	public void setCustomerQualifications(
			List<CustomerQualification> customerQualifications) {
		this.customerQualifications = customerQualifications;
	}
	public void setCustomerQualificationService(
			CustomerQualificationService customerQualificationService) {
		this.customerQualificationService = customerQualificationService;
	}
	public String getStatus() {
		return status;
	}
	public void setCustomerVentureTypeService(
			CustomerVentureTypeService customerVentureTypeService) {
		this.customerVentureTypeService = customerVentureTypeService;
	}

	public String getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}

	public boolean isDesktop() {
		return desktop;
	}

	public void setDesktop(boolean desktop) {
		this.desktop = desktop;
	}
	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public List<IncubationInfo> getIncubationInfoList() {
		return incubationInfoList;
	}

	public void setIncubationInfoList(List<IncubationInfo> incubationInfoList) {
		this.incubationInfoList = incubationInfoList;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setIncubationInfoService(IncubationInfoService incubationInfoService) {
		this.incubationInfoService = incubationInfoService;
	}

	public String getIncubated() {
		return incubated;
	}

	public void setIncubated(String incubated) {
		this.incubated = incubated;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	public CustomerTypeEnum getType() {
		return type;
	}

	public void setType(CustomerTypeEnum type) {
		this.type = type;
	}

	public String getImproveArchives() {
		return improveArchives;
	}

	public void setImproveArchives(String improveArchives) {
		this.improveArchives = improveArchives;
	}
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
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

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
}

package com.wiiy.business.action;

import java.io.BufferedReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dto.TypeDto;
import com.wiiy.business.entity.CustomerService;
import com.wiiy.business.entity.CustomerServiceTrack;
import com.wiiy.business.preferences.enums.CustomerServiceResultEnum;
import com.wiiy.business.preferences.enums.CustomerServiceStatusEnum;
import com.wiiy.business.preferences.enums.PriorityEnum;
import com.wiiy.business.service.CustomerServiceService;
import com.wiiy.business.service.CustomerServiceTrackService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Pager;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class CustomerServiceAction extends JqGridBaseAction<CustomerService>{
	
	private CustomerServiceService customerServiceService;
	private CustomerServiceTrackService customerServiceTrackService;
	private Result result;
	private CustomerService customerService;
	private Long id;
	private String ids;
	private Long userId;
	private PriorityEnum priority;
	private Long serviceId;
	private Pager pager;
	private String name;
	private String typeId;
	private String fileName;
	private InputStream inputStream;
	
	private int yetCustomerServiceCount;
	
	
	public String initCustomerView(){
		yetCustomerServiceCount = customerServiceService.countYetCustomer();
		return JSON;
	}
	public String select(){
			customerService = customerServiceService.getBeanByFilter(new Filter(CustomerService.class)).getValue();
		return "customerSelect";
	}
	public String initCustomerService(){
		Filter filter = new Filter(CustomerService.class);
		filter.createAlias("customer","customer");
		filter.createAlias("type","type");
		String[] names = {"id","status","type.moduleName","customer.name","result","createTime"};
		filter.include(names);
		result = customerServiceService.getListByFilter(filter);
		return JSON;
	}
	
	public String solved(){
		CustomerService dbBean = customerServiceService.getBeanById(id).getValue();
		dbBean.setResult(CustomerServiceResultEnum.SOLVED);
		result = customerServiceService.update(dbBean);
		return JSON;
	}
	
	public String partSolved(){
		CustomerService dbBean = customerServiceService.getBeanById(id).getValue();
		dbBean.setResult(CustomerServiceResultEnum.PartSOLVED);
		result = customerServiceService.update(dbBean);
		return JSON;
	}
	
	public String unsolved(){
		CustomerService dbBean = customerServiceService.getBeanById(id).getValue();
		dbBean.setResult(CustomerServiceResultEnum.UNSOLVE);
		result = customerServiceService.update(dbBean);
		return JSON;
	}
	
	public String accept(){
		CustomerService dbBean = customerServiceService.getBeanById(id).getValue();
		dbBean.setStatus(CustomerServiceStatusEnum.ACCEPT);
		result = customerServiceService.update(dbBean);
		return JSON;
	}
	
	public String serviceClosed(){
		CustomerService dbBean = customerServiceService.getBeanById(id).getValue();
		dbBean.setStatus(CustomerServiceStatusEnum.CLOSE);
		dbBean.setEndDate(new Date());
		result = customerServiceService.update(dbBean);
		return JSON;
	}
	
	public String suspend(){
		CustomerService dbBean = customerServiceService.getBeanById(id).getValue();
		dbBean.setStatus(CustomerServiceStatusEnum.PENDING);
		result = customerServiceService.update(dbBean);
		return JSON;
	}
	
	public String send(){
		CustomerService customerService = customerServiceService.getBeanById(id).getValue();
		SysEmailSenderPubService sysEmailSenderPubService = BusinessActivator.getService(SysEmailSenderPubService.class);
		if(sysEmailSenderPubService!=null){
			User user = BusinessActivator.getUserById(userId);
			String receiverEmail = user.getEmail();
			String subject = "客服联系单";
			String content = "";
			try {
				URL url = BusinessActivator.getURL("web/msgRemindModule/msgRemindModule.html");
				InputStreamReader Inputreader = new InputStreamReader(url.openStream(),"utf-8");
				BufferedReader br = new BufferedReader(Inputreader);
				String temp=br.readLine();
				StringBuffer data=new StringBuffer();
				while( temp!=null){
					data.append(temp+"\n");
					temp=br.readLine(); 
				}
				content = data.toString();
				content = content.replace("${subject}", customerService.getCustomerName());
				content = content.replace("${msgType}", "客户联系单");
				content = content.replace("${url}",ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/"+"department.business/customerService!view.action?id="+id);
				content = content.replace("${receiver}", user.getRealName());
				content = content.replace("${sender}", BusinessActivator.getSessionUser().getRealName());
				content = content.replace("${customerName}",user.getRealName());
				content = content.replace("${content}","有一份联系单需要您去处理!");
				content = content.replace("${msgLink}",BusinessActivator.getRemindEmailService().getRemindEmailLink());
				sysEmailSenderPubService.send(receiverEmail,content,subject);
				SMSPubService smsPubService = BusinessActivator.getService(SMSPubService.class);
				if(smsPubService!=null && smsActive()){
					String receiverMobile = user.getMobile();
					String receiverName = user.getRealName();
					String content2 = BusinessActivator.getAppConfig().getConfig("customerServiceRemind").getParameter("smsModule");
					content2 = content2.replace("${customerServiceName}",customerService.getCustomerName());
					smsPubService.send(receiverMobile, content2, receiverName);
				}
				result = Result.success("转交成功");
				customerService.setUser(user);
				customerService.setUserId(userId);
				customerServiceService.update(customerService);
				setTrack(BusinessActivator.getSessionUser().getRealName()+"将任务发送给"+user.getRealName());
			} catch (Exception e) {
				e.printStackTrace();
				result = Result.failure("转交失败");
			}
		}
		return JSON;
	}
	
	/**
	 * 保存轨迹信息
	 * @param content
	 */
	private void setTrack(String content) {
		CustomerServiceTrack customerServiceTrack = new CustomerServiceTrack();
		customerServiceTrack.setUser(BusinessActivator.getSessionUser());
		customerServiceTrack.setUserId(BusinessActivator.getSessionUser().getId());
		customerServiceTrack.setService(customerService);
		customerServiceTrack.setServiceId(customerService.getId());
		customerServiceTrack.setHandleTime(new Date());
		customerServiceTrack.setContent(content);
		customerServiceTrackService.save(customerServiceTrack);
	}
	
	private boolean smsActive(){
		String msgSet =  BusinessActivator.getAppConfig().getConfig("customerServiceRemind").getParameter("sms");
		if(msgSet.equals("active")){
			return true;
		}else{
			return false;
		}
	}
	
	public String print() {
		fileName = StringUtil.URLEncoderToUTF8("客服联系单")+".doc";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			customerServiceService.print(id, out);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "print";
	}
	
	public String chagePriority() {
		customerService = customerServiceService.getBeanById(id).getValue();
		customerService.setPriority(priority);
		customerServiceService.update(customerService);
		return JSON;
	}
	
	public String save(){
		customerService.setStatus(CustomerServiceStatusEnum.ACCEPT);
		customerService.setResult(CustomerServiceResultEnum.UNSOLVE);
		customerService.setPriority(PriorityEnum.LOW);
		result = customerServiceService.save(customerService);
		return JSON;
	}
	
	public String view(){
		result = customerServiceService.getBeanById(id);
		return VIEW;
	}
	
	public String view2(){
		result = customerServiceService.getBeanById(id);
		return "view2";
	}
	
	public String edit(){
		result = customerServiceService.getBeanById(id);
		return EDIT;
	}
	public String editDesktop(){
		result = customerServiceService.getBeanById(id);
		return "editDesktop";
	}
	
	public String update(){
		CustomerService dbBean = customerServiceService.getBeanById(customerService.getId()).getValue();
		id = customerService.getId();
		BeanUtil.copyProperties(customerService, dbBean);
		result = customerServiceService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerServiceService.deleteById(id);
		} else if(ids!=null){
			result = customerServiceService.deleteByIds(ids);
		}
		return JSON;
	}
//	public String search(){
//		Filter filter = new Filter(CustomerService.class);
//		String value=null;
//		if(filters!=null && filters.length()>0){
//			value = serachByLikeFromFilters(filters);		
//			
//		}
//		if(value!=null){
//			filter.like("customerService.serviceName", value);
//		}
//	generateSearchCriterion();
//	}
	
	public String list(){
		Filter filter = new Filter(CustomerService.class);
		userId = BusinessActivator.getSessionUser().getId();
		if(name!=null && name.length()>0){
			name = StringUtil.ISOToUTF8(name);
			filter.like("customerName", name);
		}
		if(page!=0){
			pager = new Pager(page,15);
		} else {
			pager = new Pager(1,15);
		}
		filter.pager(pager);
		filter.eq("userId", userId);
		List<CustomerService> list = customerServiceService.
				getListByFilter(filter).getValue();
		result = Result.value(list);
		return LIST;
	}
	//轨迹跳转后的数据需要过滤为当前联系单的所有轨迹
	public String list2(){
		Filter filter = new Filter(CustomerService.class);
		filter.eq("id", id);
		List<CustomerService> list = customerServiceService.getListByFilter(filter).getValue();
		result = Result.value(list);
		return LIST;
	}
	public String add(){
		return "add";
	}
	public String amounts(){
		List<DataDict> list = BusinessActivator.getDataDictInitService().getDataDictByParentId("business.0024");
		String sql="SELECT type_id,count(*) FROM business_customer_service GROUP BY type_id";
		//当前表里存在的type数量小于等于type总数 已查出来 包含了几种类型 和 每种类型的id与数量
		List<Object> customerList=customerServiceService.getListBySql(sql);
		List<TypeDto> dtoList = new ArrayList<TypeDto>();
			for (DataDict dataDict : list) {//当数据字典里的所有(9条)数据
				boolean flag=true;
				TypeDto dto = new TypeDto();
				if(customerList.size()!=0){//服务联系单不为空时
					for(Object li : customerList){
						Object[] obj=(Object[])li;
						if(obj[0].equals(dataDict.getId())){//匹配到数据后就不用继续循环
							dto.setChildId(dataDict.getId());
							dto.setAmount(Integer.parseInt(obj[1].toString()));
							dto.setTypeName(dataDict.getDataValue());
							dtoList.add(dto);
							flag=false;
							break;
						}
					}
					if(flag){//如果联系单只的6种 数据字典有8种 一定有两次找不到
						dto.setChildId(dataDict.getId());
						dto.setTypeName(dataDict.getDataValue());
						dtoList.add(dto);
					}
				}else{//如果联系单为空，页面上一样要显示每种类型 只是数值及括号不显示
					dto.setChildId(dataDict.getId());
					dto.setTypeName(dataDict.getDataValue());
					dtoList.add(dto);
				}
		}
		result=Result.value(dtoList);
		return JSON;
		
	}

	public String listAll(){
		Filter filter = new Filter(CustomerService.class);
		String value=null;
		if(filters!=null && filters.length()>0){
			value = serachByLikeFromFilters("serviceName",filters);		
		}
		if(value!=null){
			filter.like("serviceName", value);
		}
		return refresh(filter);
	}
	public String listType(){
		//点击页面传回来的typeId 或唯一 标识点击的事件
		//过滤所有联系单 条件为typeId typeId
		//refresh(filter)
		Filter filter = new Filter(CustomerService.class);
		filter.eq("typeId", typeId);
		List<CustomerService> list = customerServiceService.getListByFilter(filter).getValue();
		result = Result.value(list);
		return LIST;
	}
	
//	public String generateCode() {
//		result = customerServiceService.generateCode();
//		return JSON;
//	}
	
	@Override
	protected List<CustomerService> getListByFilter(Filter fitler) {
		return customerServiceService.getListByFilter(fitler).getValue();
	}
	
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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

	public void setCustomerServiceService(CustomerServiceService customerServiceService) {
		this.customerServiceService = customerServiceService;
	}
	
	public Result getResult() {
		return result;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Pager getPager() {
		return pager;
	}
	public String getFileName() {
		return fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setCustomerServiceTrackService(
			CustomerServiceTrackService customerServiceTrackService) {
		this.customerServiceTrackService = customerServiceTrackService;
	}


	public int getYetCustomerServiceCount() {
		return yetCustomerServiceCount;
	}


	public void setYetCustomerServiceCount(int yetCustomerServiceCount) {
		this.yetCustomerServiceCount = yetCustomerServiceCount;
	}

	public PriorityEnum getPriority() {
		return priority;
	}

	public void setPriority(PriorityEnum priority) {
		this.priority = priority;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
}

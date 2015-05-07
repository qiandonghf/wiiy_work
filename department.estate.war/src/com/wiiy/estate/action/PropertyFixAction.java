package com.wiiy.estate.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.User;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dto.PropertyDto;
import com.wiiy.estate.entity.PropertyFix;
import com.wiiy.estate.preferences.enums.PropertyFixStatusEnum;
import com.wiiy.estate.service.PropertyFixService;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

public class PropertyFixAction extends JqGridBaseAction<PropertyFix>{
	private PropertyFixService propertyFixService;
	private PropertyFix propertyFix;
	private List<PropertyFix> propertyFixList;

	private int countPropertyRepairs;
	private String status;
	private Long id;
	private String ids;
	private Result result;
	private String roomIds;
	private String oddNum;
	private Long customerId;
	
	
	public String amountsgq(){
		List<PropertyDto> dtoList=new ArrayList<PropertyDto>();
		PropertyFixStatusEnum[] enums = PropertyFixStatusEnum.values();
		Filter filter = new Filter(PropertyFix.class);
		filter.eq("status", PropertyFixStatusEnum.HANGUP);
		List<PropertyFix> list = propertyFixService.getListByFilter(filter).getValue();
		if(list.size()!=0){
			PropertyDto dto = new PropertyDto();
			for (PropertyFix li : list) {
				dto.setId(li.getId());
				dto.setTypeName(li.getStatus().toString());
				dtoList.add(dto);
			}
		}
		result=Result.value(dtoList);
		return JSON;
	}
	public String amounts(){
		List<PropertyDto> dtoList=new ArrayList<PropertyDto>();
		PropertyFixStatusEnum[] enums = PropertyFixStatusEnum.values();
		Filter filter = new Filter(PropertyFix.class);
		filter.or(Filter.Eq("status", PropertyFixStatusEnum.UNSTART),Filter.Eq("status", PropertyFixStatusEnum.HANGIN));
		List<PropertyFix> list = propertyFixService.getListByFilter(filter).getValue();
		if(list.size()!=0){
			PropertyDto dto = new PropertyDto();
			for (PropertyFix li : list) {
					dto.setId(li.getId());
					dto.setTypeName(li.getStatus().toString());
					dtoList.add(dto);
			}
		}
		result=Result.value(dtoList);
		return JSON;
	}
	
	public String workBenchPropertyFixList(){
		Filter filter = new Filter(PropertyFix.class);
		String[] names = {"id","status","reportReason","reporter","reportTime"};
		filter.include(names);
		filter.orderBy("reportTime", Filter.DESC).maxResults(6);
		propertyFixList = propertyFixService.getListByFilter(filter).getValue();
		return JSON;
	}
	
	public String countRepairs(){
		countPropertyRepairs = propertyFixService.getListByFilter(new Filter(PropertyFix.class).eq("status", PropertyFixStatusEnum.UNSTART)).getValue().size();
		return JSON;
	}
	
	public 	String add(){
		oddNum = propertyFixService.getOrderNum();
		return "add";
	}
	
	public String listAll(){
		
		return "listAll";
	}
	
	public String list(){
			Filter filter = new Filter(PropertyFix.class);
			return refresh(filter);
	}
	public String listByType(){
			Filter filter = new Filter(PropertyFix.class);
			if(status!=null&&!("").equals(status)){
				if("HANGUP".equals(status)){
					filter.eq("status",PropertyFixStatusEnum.valueOf(status));
				}else{
					filter.or(Filter.Eq("status", PropertyFixStatusEnum.UNSTART),Filter.Eq("status", PropertyFixStatusEnum.HANGIN));
				}
			}
			return refresh(filter);
	}
	
	public String save(){
		propertyFix.setFinished(BooleanEnum.NO);
		propertyFix.setStatus(PropertyFixStatusEnum.UNSTART);
		if(("").equals(propertyFix.getTypeId())){
			propertyFix.setTypeId(null);
		}
		if(("").equals(propertyFix.getMethodId())){
			propertyFix.setMethodId(null);
		}
		result = propertyFixService.save(propertyFix);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = propertyFixService.deleteById(id);
		}else if(ids!=null){
			result = propertyFixService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String edit(){
		result = propertyFixService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		if(propertyFix.getFinishTime()!=null){
			propertyFix.setFinished(BooleanEnum.YES);
		}else{
			propertyFix.setFinished(BooleanEnum.NO);
		}
		if(("").equals(propertyFix.getTypeId())){
			propertyFix.setTypeId(null);
		}
		if(("").equals(propertyFix.getMethodId())){
			propertyFix.setMethodId(null);
		}
		result = propertyFixService.update(propertyFix);
		return JSON;
	}
	
	public String view(){
		result = propertyFixService.getBeanById(id);
		return VIEW;
	}
	
	public String handle(){
		result = propertyFixService.getBeanById(id);
		return "handle";
	}
	public String handleDesktop(){
		result = propertyFixService.getBeanById(id);
		return "handleDesktop";
	}
	
	public String handleOrder(){
		result = propertyFixService.update(propertyFix);
		return JSON;
	}
	
	public String serviceList(){
		return "serviceList";
	}
	
	//递交
	public String hangIn(){
		propertyFix = propertyFixService.getBeanById(id).getValue();
		propertyFix.setStatus(PropertyFixStatusEnum.HANGIN);
		result = propertyFixService.update(propertyFix);
		if(result.isSuccess()){
			SysEmailSenderPubService sysEmailSenderPubService = EstateActivator.getService(SysEmailSenderPubService.class);
			Long userId = Long.valueOf(EstateActivator.getAppConfig().getConfig("propertyContect").getParameter("name"));
			User user = EstateActivator.getUserById(userId);
			String receiverName = user.getRealName();
			if(sysEmailSenderPubService!=null && emailActive()){
				String content = parseHTML("web/msgRemindModule/msgRemindModule.html").toString();
				String subject = "物业保修单递交提醒";
				content = content.replace("${subject}", propertyFix.getReporter());
				content = content.replace("${msgType}", "报修提醒");
				content = content.replace("${url}", basePath()+"parkmanager.pb/propertyFix!view.action?id="+propertyFix.getId());
				content = content.replace("${receiver}",receiverName);
				content = content.replace("${customerName}",receiverName);
				content = content.replace("${sender}", EstateActivator.getSessionUser().getRealName());
				content = content.replace("${content}", "报修地点："+propertyFix.getReportAddr()+"&lt;br&gt;"+propertyFix.getReportReason());
				content = content.replace("${msgLink}",EstateActivator.getRemindEmailService().getRemindEmailLink());
				sendMail(receiverName,user.getEmail(),subject,content,sysEmailSenderPubService);
			}
			SMSPubService smsPubService = EstateActivator.getService(SMSPubService.class);
			if(smsPubService!=null && smsActive()){
				String content = EstateActivator.getAppConfig().getConfig("propertyRemind").getParameter("smsModule");
				String receiverMobile = user.getMobile();
				sendSms(receiverMobile, content, receiverName, smsPubService);
			}
		}
		return JSON;
	}
	
	private void sendSms(String receiverMobile, String content,
			String receiverName,SMSPubService smsPubService) {
		smsPubService.send(receiverMobile, content, receiverName);
	}

	public void sendMail(String receiverName,String receiverEmail,String subject,String content,SysEmailSenderPubService sysEmailSenderPubService){
		if(receiverEmail == null){
			receiverEmail = "";
		}
		sysEmailSenderPubService.send(receiverEmail,content,subject);
	}
	
	private boolean emailActive(){
		String msgSet =  EstateActivator.getAppConfig().getConfig("propertyRemind").getParameter("email");
		if(msgSet.equals("active")){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean smsActive(){
		String msgSet =  EstateActivator.getAppConfig().getConfig("propertyRemind").getParameter("sms");
		if(msgSet.equals("active")){
			return true;
		}else{
			return false;
		}
	}
	
	private StringBuffer parseHTML(String str){
		URL url = EstateActivator.getURL(str);
		InputStreamReader Inputreader;
		StringBuffer data = new StringBuffer();
		try {
			Inputreader = new InputStreamReader(url.openStream(),"utf-8");
			BufferedReader br = new BufferedReader(Inputreader);
			String temp=br.readLine();
			while( temp!=null){
				data.append(temp+"\n");
				temp=br.readLine(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private String basePath(){
		return ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/";
	}

	
	@Override
	protected List<PropertyFix> getListByFilter(Filter fitler) {
		return propertyFixService.getListByFilter(fitler).getValue();
	}
	public PropertyFix getPropertyFix() {
		return propertyFix;
	}
	public void setPropertyFix(PropertyFix propertyFix) {
		this.propertyFix = propertyFix;
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
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public void setPropertyFixService(PropertyFixService propertyFixService) {
		this.propertyFixService = propertyFixService;
	}

	public String getOddNum() {
		return oddNum;
	}

	public void setOddNum(String oddNum) {
		this.oddNum = oddNum;
	}

	public List<PropertyFix> getPropertyFixList() {
		return propertyFixList;
	}

	public void setPropertyFixList(List<PropertyFix> propertyFixList) {
		this.propertyFixList = propertyFixList;
	}

	public int getCountPropertyRepairs() {
		return countPropertyRepairs;
	}

	public void setCountPropertyRepairs(int countPropertyRepairs) {
		this.countPropertyRepairs = countPropertyRepairs;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}

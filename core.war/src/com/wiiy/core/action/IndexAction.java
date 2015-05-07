package com.wiiy.core.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.mapping.Array;

import com.wiiy.commons.action.BaseAction;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.core.activator.CoreActivator;
import com.wiiy.core.dto.CoreWorkbenchTipDto;
import com.wiiy.core.dto.Menu;
import com.wiiy.core.dto.Module;
import com.wiiy.core.dto.Operation;
import com.wiiy.core.dto.ResourceDto;
import com.wiiy.core.entity.DesktopItem;
import com.wiiy.core.entity.Role;
import com.wiiy.core.entity.RoleAuthorityRef;
import com.wiiy.core.entity.User;
import com.wiiy.core.entity.UserEmailParam;
import com.wiiy.core.entity.UserRoleRef;
import com.wiiy.core.extensions.CoreWorkbenchTipExtensions;
import com.wiiy.core.extensions.ResourceExtensions;
import com.wiiy.core.mail.MailServer;
import com.wiiy.core.service.RoleDesktopService;
import com.wiiy.core.service.RoleService;
import com.wiiy.core.service.UserService;
import com.wiiy.core.service.export.HttpSessionService;
import com.wiiy.core.service.export.ModuleService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.core.dto.DayDto;
//import com.wiiy.synthesis.entity.Schedule;

public class IndexAction extends BaseAction {
	private String contactWay;//企业服务平台左侧菜单显示的联系方式
	private List<CoreWorkbenchTipDto> coreWorkbenchTipDtoList;
	private CoreWorkbenchTipExtensions coreWorkbenchTipExtensions;
	private List<ResourceDto> resourceList = new ArrayList<ResourceDto>();
	
	private Map<String,ResourceDto> resourceMap = new HashMap<String, ResourceDto>();
	private Date scheduledDay = new Date();
	private String monthAction = "";//"pre next"
	private Result result;

	public static Set<String> uris;

	public List<ResourceDto> getResourceList() {
		return resourceList;
	}


	public void setResourceList(List<ResourceDto> resourceList) {
		this.resourceList = resourceList;
	}

	private List<DesktopItem> desktopItemList;
	private RoleDesktopService roleDesktopService;
	private String mottoStr;//工作台显示格言警句
	
	
	private String email;
	private String url;
	private String name;
	private String emailInfo;//email相关信息
	
	//当前登录时间
    private String dataStr ;
    private String type;

	public String getDataStr() {
		return dataStr;
	}
	
	public void setCoreWorkbenchTipExtensions(
			CoreWorkbenchTipExtensions coreWorkbenchTipExtensions) {
		this.coreWorkbenchTipExtensions = coreWorkbenchTipExtensions;
	}

	public List<CoreWorkbenchTipDto> getCoreWorkbenchTipDtoList() {
		return coreWorkbenchTipDtoList;
	}
	private void initDesktop(){
		String mottoAppconfig = CoreActivator.getAppConfig().getConfig("motto").getParameter("allMotto");
		Random random = new Random();
		if(!("").equals(mottoAppconfig) && mottoAppconfig!=null){
			String[] strArray = mottoAppconfig.split("~");
			int i = random.nextInt(strArray.length);
			mottoStr = ","+strArray[i];
		}else{
			mottoStr = ",为了美好的明天，奋斗吧！";
		}
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int hours  = date.getHours();
		if(hours<12)dataStr="上午好";
		else dataStr="下午好";
		coreWorkbenchTipDtoList = coreWorkbenchTipExtensions.getTips();
		desktopItemList = roleDesktopService.getUserDesktopItem(CoreActivator.getSessionUser().getId());
		UserEmailParam	param = CoreActivator.getSessionUser().getUserEmailParam();
		if(param == null){
			email = "您还未配置邮箱";
			url = "core/web/account/core_user_edit.jsp";
			name="账户设置";
		}else{
			Result<Integer[]> result = 
					MailServer.mailNums(param.getPop3(), param.getEmail(), 
							param.getPasswd(), "imap", "INBOX");
			Integer[] nums = result.getValue();
			if (nums !=null && nums.length == 3) {
				emailInfo = "收件箱总共:"+nums[0]+"条,其中未读:"+nums[1]+"条,新邮件:"+nums[2]+"条";
				email = nums[1]+"";
			}else {
				emailInfo = "";
				email = param.getEmail();
			}
			url = "parkmanager.oa/mail!list.action";
			name="收件箱";
		}
		
		resourceList = ResourceExtensions.resourceList;
		Set<UserRoleRef> roleRefs;
		try {
			roleRefs = userService.getUserRoleRefs(user.getId());
			accessibleModuleMenuIds = calculateMoudleMenuIds(resourceList, roleRefs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String desktop(){
		this.user = (User) ServletActionContext.getRequest().getSession().getAttribute("login_user");
		if(user==null){
			autoLogin();
		}
		if (user == null) return LOGIN;
		initDesktop();
		return "desktop";
	}
	
	public String desktopGL(){
		this.user = (User) ServletActionContext.getRequest().getSession().getAttribute("login_user");
		if(user==null){
			autoLogin();
		}
		if (user == null) return LOGIN;
		initDesktop();
		return "desktopGL";
	}
	
	public String desktopGK(){
		this.user = (User) ServletActionContext.getRequest().getSession().getAttribute("login_user");
		if(user==null){
			autoLogin();
		}
		if (user == null) return LOGIN;
		initDesktop();
		return "desktopGK";
	}
	
	private static final Log logger = LogFactory.getLog(IndexAction.class);
	
	private ModuleService moduleService;
	
	private RoleService roleService;
	
	private UserService userService;

	private Map<String, String> accessibleModuleMenuIds;
	
	private HttpSessionService httpSessionService;
	
	private User user;
	
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Map<String, String> getAccessibleModuleMenuIds() {
		return accessibleModuleMenuIds;
	}
	
	public void setHttpSessionService(HttpSessionService httpSessionService) {
		this.httpSessionService = httpSessionService;
	}
	
	private void autoLogin(){
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if(cookies!=null)
		for (Cookie cookie : cookies) {
			if(cookie.getName().equalsIgnoreCase("autoLogin")){
				String userinfo = cookie.getValue();
				if(userinfo!=null){
					String[] array = userinfo.split(",");
					String username = array[0];
					String password = array[1];
					User u = new User();
					u.setUsername(username);
					u.setPassword(password);
					Result<User> result = userService.login(u);
					user = result.getValue();
					if(result.isSuccess()){
						CoreActivator.getHttpSessionService().setSessionUser((User)result.getValue());
						ServletActionContext.getRequest().getSession().setAttribute("login_user", user);
						UserEmailParam emailParam=user.getUserEmailParam();
						System.out.println("LoginAction.login() emailParam="+emailParam);
						if(emailParam!=null){
							System.out.println("LoginAction.login():emailParam.getEmail()="+emailParam.getEmail());
						}
						CoreActivator.getOperationLogService().logLogin("登录系统");
					}
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @return 日历初始化
	 */
	public String schedule_list(){
		if(monthAction.equals("prev")){
			Calendar c = CalendarUtil.getCalendarInstance(scheduledDay);
			c.add(Calendar.MONTH, -1);
			scheduledDay = c.getTime();
		}else if(monthAction.equals("next")){
			Calendar c = CalendarUtil.getCalendarInstance(scheduledDay);
			c.add(Calendar.MONTH, 1);
			scheduledDay = c.getTime();
		}
		Date[][] days = CalendarUtil.generateCalendar(scheduledDay, Calendar.MONDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate;
		Date endDate;
		try {
			String startTime = sdf.format(CalendarUtil.getEarliest(days[0][0],Calendar.DAY_OF_MONTH));
			String endTime = sdf.format(CalendarUtil.getLatest(days[days.length-1][days[days.length-1].length-1],Calendar.DAY_OF_MONTH));
			startDate = sdf.parse(startTime);
			endDate = sdf.parse(endTime);
			//schedules = scheduleService.getListByFilter(new Filter(Schedule.class).eq("ownerId", SynthesisActivator.getSessionUser().getId()).between("startTime", startDate, endDate).orderBy("startTime",Filter.ASC)).getValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/*Map<Long,List<Schedule>> scheduleMap = new HashMap<Long,List<Schedule>>();
		if(schedules!=null&&schedules.size()>0){
			for (Schedule schedule : schedules) {
				Long key = CalendarUtil.getEarliest(schedule.getStartTime(),Calendar.DAY_OF_MONTH).getTime();
				List<Schedule> list = scheduleMap.get(key);
				if(list==null){
					list = new ArrayList<Schedule>();
					scheduleMap.put(key, list);
				}
				list.add(schedule);
			}
		}*/
		List<DayDto> dayDtoList = new ArrayList<DayDto>();
		for (int i = 0; i < days.length; i++) {
			boolean weekFlag = false;
			Long startTime = CalendarUtil.getEarliest(days[i][0],Calendar.DAY_OF_MONTH).getTime();
			Long endTime = CalendarUtil.getLatest(days[i][days[i].length-1],Calendar.DAY_OF_MONTH).getTime();
			weekFlag = (startTime<scheduledDay.getTime()|startTime == scheduledDay.getTime()) && (endTime>scheduledDay.getTime()|endTime==scheduledDay.getTime());
			for (int j = 0; j < days.length+1; j++) {
				DayDto dto = new DayDto();
				Date date = days[i][j];
				Long key = CalendarUtil.getEarliest(date,Calendar.DAY_OF_MONTH).getTime();
				dto.setDate(date);
				dto.setDay(DateUtil.format(date,"d"));
				dto.setCheckedDay(DateUtil.format(date, "yyyy-MM-dd").equals(DateUtil.format(scheduledDay, "yyyy-MM-dd")));
				dto.setThisMonth(DateUtil.format(date, "MM").equals(DateUtil.format(scheduledDay, "MM")));
				dto.setNowDate(key==CalendarUtil.getEarliest(new Date(),Calendar.DAY_OF_MONTH).getTime());
				//List<Schedule> list = scheduleMap.get(key);
				dto.setThisWeek(weekFlag);
				//dto.setSchedules(list);
				dayDtoList.add(dto);
			}
		}
		result = Result.value(dayDtoList);
		return LIST;
	}
	
	
	
	public String execute(){
		this.user = (User) ServletActionContext.getRequest().getSession().getAttribute("login_user");
		if(user==null){
			autoLogin();
		}
		if (user == null) return LOGIN;
		
		resourceList = ResourceExtensions.resourceList;
		
		String contactWayConfig = CoreActivator.getAppConfig().getConfig("contactWay").getParameter("allTelephoneNumber");
		contactWay = contactWayConfig.replaceAll("br", "<br/>");
		
		//moduleList = moduleService.listModules();
		
		Set<UserRoleRef> roleRefs;
		try {
			roleRefs = userService.getUserRoleRefs(user.getId());
			accessibleModuleMenuIds = calculateMoudleMenuIds(resourceList, roleRefs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		schedule_list();
		return INDEX;
	}

	private Map<String, String> calculateMoudleMenuIds(List<ResourceDto> resourceList, Set<UserRoleRef> roleRefs) {
		
		Map<String, String> result = new HashMap<String, String>();

		putVisibleByGranted(result, roleRefs);
		
		appendVisibleByGrantedChildren(result, resourceList);
		
		return result;
	}

	private void putVisibleByGranted(Map<String, String> result, Set<UserRoleRef> roleRefs) {
		uris = new HashSet<String>();
		Map<String,ResourceDto> resourceMap = ResourceExtensions.resourceMap;
		Map<String,ResourceDto> accessibleResourceMap = new HashMap<String, ResourceDto>();
		for (UserRoleRef userRoleRef : roleRefs) {
			if(userRoleRef!=null){
				Role role = userRoleRef.getRole();
				Set<RoleAuthorityRef> roleAuthorityRefs = role.getAuthorityRefs();
				for (RoleAuthorityRef roleAuthorityRef : roleAuthorityRefs) {
					if (roleAuthorityRef == null) continue;
					String key = buildKey(roleAuthorityRef.getModuleId(), roleAuthorityRef.getAuthorityId());
					result.put(key, key);
					ResourceDto dto = resourceMap.get(String.valueOf(roleAuthorityRef.getAuthorityId()));
					if(dto!=null){
						String uri = dto.getUris();
						if(uri!=null&&!("").endsWith(uri)){
							uris.add(uri);
						}
						accessibleResourceMap.put(dto.getIdSpace(), dto);
						loadMap(accessibleResourceMap,dto,resourceMap);
					}
				}
			}
		}
		CoreActivator.getHttpSessionService().SetUris(uris);
		CoreActivator.getHttpSessionService().setResourceMap(accessibleResourceMap);
	}
	
	private Map<String,ResourceDto> loadMap(Map<String,ResourceDto> accessibleResourceMap,ResourceDto dto,Map<String,ResourceDto> resourceMap){
		if(dto.getParentId()!=null&&dto.getParentId().length()>0){
			if(resourceMap.get(dto.getParentId())!=null){
				String uri = resourceMap.get(dto.getParentId()).getUris();
				if(uri!=null && uri.trim().length()>0){
					uris.add(uri);
				}
			}
			ResourceDto parentDto = accessibleResourceMap.get(dto.getParentId());
			if(parentDto!=null){
				String type = parentDto.getType();
				if(("menu").equalsIgnoreCase(type) || ("module").equalsIgnoreCase(type)){
					accessibleResourceMap.put(dto.getParentId(), parentDto);
					loadMap(accessibleResourceMap,parentDto,resourceMap);
				}
			}
		}
		return accessibleResourceMap;
	}

	private String buildKey(String moduleId, String authorityId) {
		return authorityId;
	}

	private void appendVisibleByGrantedChildren(Map<String, String> result, List<ResourceDto> resourceList) {
		
		//System.out.println("=================菜单开始=================");
		for (ResourceDto res : resourceList) {
			checkResource(result, res);
			//printResource(res);
		}
		//System.out.println("=================菜单结束=================");
	}

	private void printResource(ResourceDto resDto){
		System.err.println("\t"+resDto.getName());
		List<ResourceDto> reses = resDto.getChildren();
		int i = 0;
		int j = 0;
		if(reses!=null && reses.size()>0){
			for (ResourceDto res : reses) {
				checkChildren(res,i++,j++);
			}
		}
	}
	
	private void checkChildren(ResourceDto resDto,int i,int j){
		if(i<j){
			String str = "\t\t";
			for(int k=0;k<j-i;k++){
				str += "\t";
			}
			System.err.println(str+resDto.getName());
		}else{
			System.err.println("\t\t"+resDto.getName());
		}
		List<ResourceDto> reses = resDto.getChildren();
		if(reses!=null && reses.size()>0){
			int j2 = j+1;
			for (ResourceDto res : reses) {
				checkChildren(res,i,j2);
			}
		}
	}
	
	private void checkResource(Map<String, String> result, ResourceDto resDto) {
		
		boolean moduleVisibleByChildren = false;

		List<ResourceDto> reses = resDto.getChildren();
		if(reses!=null && reses.size()>0){
			for (ResourceDto res : reses) {
				boolean menuVisible = checkMenu(result, res);
				
				moduleVisibleByChildren = moduleVisibleByChildren || menuVisible; 
			}
		}
		boolean moduleGranted = result.containsKey(resDto.getIdSpace());
		
		if (!moduleGranted && moduleVisibleByChildren) {
			result.put(resDto.getIdSpace(), resDto.getIdSpace());
		}
	}

	private boolean checkMenu(Map<String, String> result, ResourceDto res) {
		
		boolean menuVisibleByChildren = false;
		
		boolean menuGranted = result.containsKey(res.getIdSpace());

		List<ResourceDto> subMenus = res.getChildren();

		for (ResourceDto subMenu : subMenus) {
			
			boolean subMenuVisible = checkMenu(result, subMenu);
			
			menuVisibleByChildren = menuVisibleByChildren || subMenuVisible; 
		}
		
		if (!menuVisibleByChildren) {
			menuVisibleByChildren = getVisibleByOperation(result, res);
		}
		
		if (!menuGranted && menuVisibleByChildren) {
			result.put(res.getIdSpace(), res.getIdSpace());
		}

		return menuGranted || menuVisibleByChildren;
	}


	private boolean getVisibleByOperation(Map<String, String> result, ResourceDto menu) {
		List<ResourceDto> operationList = menu.getChildren();
		for (ResourceDto operation : operationList) {
			boolean operationGranted = result.containsKey(operation.getIdSpace());
			if (operationGranted) {
				return true;
			}
		}
		return false;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContactWay() {
		return contactWay;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public void setRoleDesktopService(RoleDesktopService roleDesktopService) {
		this.roleDesktopService = roleDesktopService;
	}

	public List<DesktopItem> getDesktopItemList() {
		return desktopItemList;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMottoStr() {
		return mottoStr;
	}


	public void setMottoStr(String mottoStr) {
		this.mottoStr = mottoStr;
	}


	public String getEmailInfo() {
		return emailInfo;
	}


	public void setEmailInfo(String emailInfo) {
		this.emailInfo = emailInfo;
	}


	public Date getScheduledDay() {
		return scheduledDay;
	}


	public void setScheduledDay(Date scheduledDay) {
		this.scheduledDay = scheduledDay;
	}


	public String getMonthAction() {
		return monthAction;
	}


	public void setMonthAction(String monthAction) {
		this.monthAction = monthAction;
	}


	public Result getResult() {
		return result;
	}


	public void setResult(Result result) {
		this.result = result;
	}
	
}

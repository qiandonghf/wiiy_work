package com.wiiy.estate.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndImageImpl;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.DeviceMaintenance;
import com.wiiy.estate.entity.DeviceManagement;
import com.wiiy.estate.entity.DevicePatrol;
import com.wiiy.estate.entity.DeviceWorkOrder;
import com.wiiy.estate.entity.DeviceYearly;
import com.wiiy.estate.preferences.enums.PatrolIntervalEnum;
import com.wiiy.estate.service.DeviceManagementService;
import com.wiiy.estate.service.DevicePatrolService;
import com.wiiy.estate.service.DeviceYearlyService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author zhf
 */
public class DeviceManagementAction extends JqGridBaseAction<DeviceManagement>{
	
	private DeviceManagementService deviceManagementService;
	private Result<DeviceManagement> result;
	private Result results;
	private DeviceManagement deviceManagement;//设备管理
	private DeviceMaintenance deviceMaintenance;//设备维护
	private DevicePatrol devicePatrol;//设备巡检
	private DevicePatrolService devicePatrolService; //设备巡检service
	private List<DevicePatrol> devicePatrolList;//设备巡检List
	private DeviceYearly deviceYearly;//设备年检
	private DeviceYearlyService deviceYearlyService;
	private List<DeviceYearly> deviceYearlyList;
	private DeviceWorkOrder deviceWorkOrder;//维修派工单
	private Long id;
	private String ids;
	private String names;
	private Integer totalCount = 0;
	private List<DeviceManagement> deviceList;
	private List<String> checkType;
	private Boolean report = false;
	
	public String send(){
		if(id!=null){
			if(names.equals("patrols")){
				Filter filter = new Filter(DevicePatrol.class);
				filter.eq("id", id);
				devicePatrol = devicePatrolService.getBeanByFilter(filter).getValue();
				Filter filter1=new Filter(DeviceManagement.class);
				filter1.eq("id", devicePatrol.getDeviceId());
				deviceManagement=deviceManagementService.getBeanByFilter(filter1).getValue();
				if(devicePatrol.getOperationDate()!=null){
					deviceManagement.setPatrolTime(devicePatrol.getOperationDate());
				}
				result = deviceManagementService.saveOrUpdate(deviceManagement);
			}
			if(names.equals("years")){
				Filter filter = new Filter(DeviceYearly.class);
				filter.eq("id", id);
				deviceYearly = deviceYearlyService.getBeanByFilter(filter).getValue();
				Filter filter1=new Filter(DeviceManagement.class);
				filter1.eq("id", deviceYearly.getDeviceId());
				deviceManagement=deviceManagementService.getBeanByFilter(filter1).getValue();
				if(deviceYearly.getOperationDate()!=null){
					deviceManagement.setYearlyTime(deviceYearly.getOperationDate());
				}
				result = deviceManagementService.saveOrUpdate(deviceManagement);
			}
		}
		return JSON;
	}
	
	public String loadDeviceCount() {
		listDesktop();
		totalCount = deviceList.size();
		return JSON;
	}
	
	public String amounts(){
		listDesktop();
		totalCount = deviceList.size();
		if(totalCount!=0){
			results=Result.value(totalCount);
		}
	/*	String sql="SELECT COUNT(id) FROM estate_device_management m WHERE m.yearly_time is not null or m.patrol_time is not null";
		List<Object> list=deviceManagementService.getListBySql(sql);
		if(list.size()>0){
			results=Result.value(Integer.parseInt(list.get(0).toString()));
		}*/
		return JSON;
	}
	public String listDesktop(){
		deviceList = new ArrayList<DeviceManagement>();
		checkType = new ArrayList<String>();
		Filter filter = new Filter(DeviceManagement.class);
		List<DeviceManagement> devices = getListByFilter(filter);
		
		for (DeviceManagement device : devices) {
			if (device.getPatrolInterval() == null &&
					device.getPatrolTime() == null && 
					device.getYearlyTime() == null&&
					device.getLastPatrolTime() == null&&
					device.getLastYearlyTime() == null) {
				continue;
			}
			
			/**
			 * 巡检
			 */
			patrol(device);
			
			/**
			 * 年检 按照设置的年检日期进行检查
			 */
			yearly(device);
			
		}
		root = deviceList;
		if (root != null && root.size() > 0) {
			for (int i = 0; i < root.size(); i++) {
				deviceManagement = root.get(i);
				String type = null;
				if (deviceManagement.getPatrolIntervalType() != null) {
					type = deviceManagement.getPatrolInterval()+" "+
							deviceManagement.getPatrolIntervalType().getTitle();
				}
				deviceManagement.setPatrolInterval(type);
			}
		}
		return JSON;
	}
	
	//年检判断
	private void yearly(DeviceManagement device) {
		int day = 30;
		Date nDate = new Date();
		//年检日期
		Date yDate = device.getYearlyTime();
		//如果上次年检不存在，年检日期存在
		if (yDate != null) {
			// 当前时间的处理
			Date curDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//当年年份
			String nowTime = sdf.format(curDate);
			String nowDate = nowTime.substring(0, 4);
			// 年检的年份
			String yearTime = sdf.format(yDate);
			String yearDate = yearTime.substring(0, 4);
			// 当前年份与年检年份的差值
			Integer dDate = Integer.parseInt(nowDate)
					- Integer.parseInt(yearDate);
            
			/*
			 * 判断如果差值在一年或者一年之内，表示第一次年检，不需要查询最近一次年检时间
			 */
			if (dDate <= 1) {
				addReportYearly(yDate, nDate, day, device, dDate);
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(yDate);
				calendar.add(Calendar.YEAR, dDate);

				// 计算最近一次需要年检的年份
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				long mostYearDate = calendar.getTimeInMillis();
				String mostDate = sdf1.format(mostYearDate);
				// 计算年份倒数第二次需要年检的年份，最近一次减去1
				String year = mostDate.substring(0, 4);
				String monthDate = mostDate.substring(4, 10);
				Integer secYear = Integer.parseInt(year) - 1;
				String privYearTime = secYear + monthDate;

				// 调用查询上一次年检日期到目前为止是否已经年检过
				boolean flag = deviceYearlyService.checkYear(privYearTime,
						nowTime);
				// 说明已经年检过了
				if (flag == true) {
					return;
				}
				if (yDate.getTime() <= calendar.getTimeInMillis()) {
					addReportYearly(yDate, nDate, day, device, dDate);
				}
			}
		}
	}
	
	//巡检判断
	private void patrol(DeviceManagement device) {
		//设置提醒时间为3天，减去了本身那一天，所以为两天
		int day = 2;
		Date nDate = new Date();
		//巡检日期
		Date patrolDate = device.getPatrolTime();
		//上次巡检日期
		Date lastPatrolDate = device.getLastPatrolTime();
		
		//巡检日期和巡检间隔都为空
		if(device.getPatrolInterval() == null &&  patrolDate == null) return;
		if(device.getPatrolInterval() == null ||  patrolDate == null) return;
		
		//device.getPatrolInterval()巡检间隔
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(patrolDate);
		
		//日期处理问题
		dealDate(calendar, device.getPatrolInterval(), //
				device.getPatrolIntervalType());
		
		//获取当前时间,以及当前时间年月日
		String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(nDate);
		//判断是不是第一次巡检
		Calendar firPotrol = Calendar.getInstance();
	
		firPotrol.setTime(patrolDate);
		if (PatrolIntervalEnum.Week == device.getPatrolIntervalType()) {
			firPotrol.add(Calendar.WEEK_OF_YEAR,
					Integer.parseInt(device.getPatrolInterval()));
		} else if (PatrolIntervalEnum.Month == device.getPatrolIntervalType()) {
			firPotrol.add(Calendar.MONTH,
					Integer.parseInt(device.getPatrolInterval()));
		}
		long firPotrolTime = firPotrol.getTimeInMillis();
		
		//用于判断
		Calendar firPotrol1 = Calendar.getInstance();
		firPotrol1.setTime(patrolDate);
		if (PatrolIntervalEnum.Month == device.getPatrolIntervalType()) {
			firPotrol1.add(Calendar.MONTH,
					Integer.parseInt(device.getPatrolInterval()) * 2);
		} else if (PatrolIntervalEnum.Week == device.getPatrolIntervalType()) {
			firPotrol1.add(Calendar.WEEK_OF_YEAR,
					Integer.parseInt(device.getPatrolInterval()) * 2);
		}
		firPotrol1.add(Calendar.MONTH, Integer.parseInt(device.getPatrolInterval())*2);
		long firPotrolTime1 = firPotrol1.getTimeInMillis();
		
		//获取上次时间
		String previous = null;
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(patrolDate);
		dealDate(c1, device.getPatrolInterval(), device.getPatrolIntervalType());
		long calendar1 = 0;
		Calendar count = Calendar.getInstance();
		//表示第一次循环
		if(calendar.getTimeInMillis() != 0 && !"".trim().equals(calendar.getTimeInMillis())){
			//表示第二次循环
			c1 = dealDate(c1, device.getPatrolInterval(), device.getPatrolIntervalType());
			
			int i = 0;
			//计算循环次数
			int j = 0;
			Calendar c2 = Calendar.getInstance();
            c2.setTime(patrolDate);			
			for(i=0;i<=10000;i++){
				c2 = dealDate(c2, device.getPatrolInterval(), device.getPatrolIntervalType());
				j++;
				String t = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTimeInMillis());
				int t1 = Integer.parseInt(t.substring(0,4)+t.substring(5,7)+t.substring(8,10));
				
				String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(nDate.getTime());
				int nowDate1 = Integer.parseInt(nowDate.substring(0,4)+nowDate.substring(5,7)+nowDate.substring(8,10));
				if(t1 >= nowDate1){
					break;
				}
			}
			System.out.println("j="+j);
			
			count.setTime(patrolDate);
			for(int k=0;k<j-1;k++){
				dealDate(count, device.getPatrolInterval(), device.getPatrolIntervalType());
			}
			if(c1.getTimeInMillis() > calendar.getTimeInMillis()){
				calendar1 = count.getTimeInMillis();
				previous = new SimpleDateFormat("yyyy-MM-dd").format(calendar1);
			}else{
				previous = new SimpleDateFormat("yyyy-MM-dd").format(patrolDate);
			}			
		}
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar1));
		
		//获取上次计划巡检时间
//		String previous = (String) ((firPotrolTime <= (nDate.getTime()+(24*60*60*1000*(day+1))) && 
//				(firPotrolTime1 >= (nDate.getTime()+(24*60*60*1000*(day+1))))) ? 
//						new SimpleDateFormat("yyyy-MM-dd").format(patrolDate) : 
//							new SimpleDateFormat("yyyy-MM-dd").format(calendar1));
		//判断是否已经巡检 2015-01-08
		boolean flag = devicePatrolService.checkPatrol(previous, nowTime);
		if(flag == true){
			return;
		}
		calendar.add(Calendar.DAY_OF_YEAR, 0-day);
		if(lastPatrolDate != null){
			if (lastPatrolDate.getTime() < calendar.getTimeInMillis()) {
				device.setReportPatrol(patrolDate);
				addReport(patrolDate, nDate, day, device,"巡检");
			}
		}else{
			if(calendar.getTimeInMillis() <= new Date().getTime()){
				device.setReportPatrol(patrolDate);
				addReport(count.getTime(), nDate, day, device, "巡检");
			}
		}
	}
	
	/**
	 * 处理日期(日期计算)
	 * @param c
	 * @param interval
	 * @param type
	 */
	private Calendar dealDate(Calendar c,//
			String interval,PatrolIntervalEnum type){
		if(PatrolIntervalEnum.Month == type){
			int m = Integer.parseInt(interval);
			c.add(Calendar.MONTH, m);
		}else if(PatrolIntervalEnum.Week == type){
			int m = Integer.parseInt(interval);
			c.add(Calendar.WEEK_OF_YEAR, m);
		}
		return c;
	}
	
	//添加检查提示
	private void addReport(Date fDate,Date nDate,int day,DeviceManagement device,String name){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fDate);
		//0-day表示时间已经超过了，同时减去当天
		calendar.add(Calendar.DAY_OF_YEAR, 0-day);
		/**
		 * 如果当前日期已经超过了提醒检查的日期
		 */
		
		if (nDate.getTime() >= calendar.getTimeInMillis()) {
			if("年检".equals(name)){
				device.setReportYearly(fDate);
			}
			if(device.getReportType() == null){
				device.setReportType(name);
			}else {
				if ("年检".equals(name)) {
					device.setReportType(device.getReportType()+"/"+name);
					if (report) {
						return;
					}
				}
			}
			deviceList.add(device);
			checkType.add(name);
		}
	}
	

	//添加年检检查提示
	private void addReportYearly(Date fDate,Date nDate,int day,DeviceManagement device,Integer dDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fDate);
		calendar.add(Calendar.YEAR,dDate);
		//其中+1相当于减去当天
		calendar.add(Calendar.DAY_OF_YEAR, 0-day+1);

		/**
		 * 如果当前日期已经超过了提醒检查的日期
		 */
		if (nDate.getTime() >= calendar.getTimeInMillis()) {
			device.setReportYearly(fDate);
			if(device.getReportType() == null){
				device.setReportType("年检");
			}else {
				device.setReportType(device.getReportType()+"/年检");
				if (report) {
					return;
				}
			}
			deviceList.add(device);
			checkType.add("年检");
		}
	}
	
	//获取间隔天数
//	private int getDays(PatrolIntervalEnum intervalEnum,Integer days){
//		if (intervalEnum == PatrolIntervalEnum.Day) {
//			return (int)Math.ceil(days/2.0);
//		}else if (intervalEnum == PatrolIntervalEnum.Month) {
//			return 15;
//		}else if (intervalEnum == PatrolIntervalEnum.Year) {
//			return 15;
//		}
//		return 0;
//	}
	
	
	public String saveOrUpdate(){
		result = deviceManagementService.saveOrUpdate(deviceManagement);
		return JSON;
	}
	
	public String view(){
		result = deviceManagementService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = deviceManagementService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		DeviceManagement dbBean = deviceManagementService.getBeanById(deviceManagement.getId()).getValue();
		if (deviceManagement.getIsImported() == null) {
			dbBean.setIsImported(null);
		}
		if (deviceManagement.getIsCNC() == null) {
			dbBean.setIsCNC(null);
		}
		if (deviceManagement.getStatus() == null) {
			dbBean.setStatus(null);
		}
		if (deviceManagement.getPatrolIntervalType() == null) {
			dbBean.setPatrolIntervalType(null);
		}
		BeanUtil.copyProperties(deviceManagement, dbBean);
		result = deviceManagementService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = deviceManagementService.deleteById(id);
		} else if(ids!=null){
			result = deviceManagementService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String listAll(){
		return LIST;
	}
	public String list(){
		refresh(new Filter(DeviceManagement.class));
		if (root != null && root.size() > 0) {
			for (int i = 0; i < root.size(); i++) {
				deviceManagement = root.get(i);
				String type = null;
				if (deviceManagement.getPatrolIntervalType() != null) {
					type = deviceManagement.getPatrolInterval()+" "+
							deviceManagement.getPatrolIntervalType().getTitle();
				}
				deviceManagement.setPatrolInterval(type);
			}
		}
		return JSON;
	}
	
	@Override
	protected List<DeviceManagement> getListByFilter(Filter fitler) {
		return deviceManagementService.getListByFilter(fitler).getValue();
	}
	
	
	public DeviceManagement getDeviceManagement() {
		return deviceManagement;
	}

	public void setDeviceManagement(DeviceManagement deviceManagement) {
		this.deviceManagement = deviceManagement;
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

	public void setDeviceManagementService(DeviceManagementService deviceManagementService) {
		this.deviceManagementService = deviceManagementService;
	}
	public Result<DeviceManagement> getResult() {
		return result;
	}

	public DeviceMaintenance getDeviceMaintenance() {
		return deviceMaintenance;
	}

	public void setDeviceMaintenance(DeviceMaintenance deviceMaintenance) {
		this.deviceMaintenance = deviceMaintenance;
	}

	public DevicePatrol getDevicePatrol() {
		return devicePatrol;
	}

	public void setDevicePatrol(DevicePatrol devicePatrol) {
		this.devicePatrol = devicePatrol;
	}

	public DeviceYearly getDeviceYearly() {
		return deviceYearly;
	}

	public void setDeviceYearly(DeviceYearly deviceYearly) {
		this.deviceYearly = deviceYearly;
	}

	public DeviceWorkOrder getDeviceWorkOrder() {
		return deviceWorkOrder;
	}

	public void setDeviceWorkOrder(DeviceWorkOrder deviceWorkOrder) {
		this.deviceWorkOrder = deviceWorkOrder;
	}

	public Integer getTotalCount() {
		return totalCount;
	}


	public List<DeviceManagement> getDeviceList() {
		return deviceList;
	}


	public List<String> getCheckType() {
		return checkType;
	}


	public void setReport(Boolean report) {
		this.report = report;
	}

	public Result getResults() {
		return results;
	}

	public void setResults(Result results) {
		this.results = results;
	}

	public List<DevicePatrol> getDevicePatrolList() {
		return devicePatrolList;
	}

	public void setDevicePatrolList(List<DevicePatrol> devicePatrolList) {
		this.devicePatrolList = devicePatrolList;
	}

	public List<DeviceYearly> getDeviceYearlyList() {
		return deviceYearlyList;
	}

	public void setDeviceYearlyList(List<DeviceYearly> deviceYearlyList) {
		this.deviceYearlyList = deviceYearlyList;
	}

	public void setDevicePatrolService(DevicePatrolService devicePatrolService) {
		this.devicePatrolService = devicePatrolService;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public void setDeviceYearlyService(DeviceYearlyService deviceYearlyService) {
		this.deviceYearlyService = deviceYearlyService;
	}

}

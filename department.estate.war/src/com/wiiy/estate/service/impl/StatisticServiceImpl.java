package com.wiiy.estate.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wiiy.business.dao.CustomerDao;
import com.wiiy.common.preferences.enums.BillInOutEnum;
import com.wiiy.common.preferences.enums.BillStatusEnum;
import com.wiiy.common.preferences.enums.RoomStatusEnum;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.core.entity.DataDict;
import com.wiiy.estate.dto.BillReportDto;
import com.wiiy.estate.dto.StatisticDto;
import com.wiiy.estate.dto.StatisticGroupDto;
import com.wiiy.estate.preferences.R.Bill;
import com.wiiy.estate.preferences.R.BillType;
import com.wiiy.estate.service.StatisticService;
import com.wiiy.hibernate.ModulePrefixNamingStrategy;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Building;
import com.wiiy.park.entity.Room;

public class StatisticServiceImpl implements StatisticService {
	
	private CustomerDao businessCustomerDao;

	
	public void setBusinessCustomerDao(CustomerDao businessCustomerDao) {
		this.businessCustomerDao = businessCustomerDao;
	}

	@Override
	public Result<?> buildingGraph() {
		//SELECT b.id,b.`name`,d.data_value,SUM(r.building_area) FROM pb_room r LEFT JOIN pb_building b ON r.building_id = b.id LEFT JOIN core_data_dict d ON r.kind_id = d.id GROUP BY b.id,b.`name`,d.id,d.data_value;
		String sql = "SELECT b.id,b.`name`,d.data_value,SUM(r.building_area) FROM "+ModulePrefixNamingStrategy.classToTableName(Room.class)+" r " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Building.class)+" b ON r.building_id = b.id " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(DataDict.class)+" d ON r.kind_id = d.id " +
				"GROUP BY b.id,b.`name`,d.id,d.data_value";
		List<Object> list = businessCustomerDao.getObjectListBySql(sql);
		Map<Long,StatisticGroupDto> groups = new HashMap<Long,StatisticGroupDto>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			Long bId = BigInteger.class.cast(objects[0]).longValue();
			if(!groups.containsKey(bId)){
				StatisticGroupDto group = new StatisticGroupDto();
				group.setName(String.valueOf(objects[1]));
				groups.put(bId, group);
			}
			String status = String.valueOf(objects[2]);
			Double value = Double.parseDouble(String.valueOf(objects[3]));
			StatisticDto dto = new StatisticDto();
			dto.setName(status);
			dto.setdValue(value);
			groups.get(bId).getList().add(dto);
		}
		List<StatisticGroupDto> groupList = new ArrayList<StatisticGroupDto>(groups.values());
		return Result.value(groupList);
	}

	@Override
	public Result<?> buildingRentGraph() {
		//SELECT b.id,b.`name`,r.`status`,SUM(r.building_area) FROM pb_room r LEFT JOIN pb_building b ON r.building_id = b.id WHERE r.kind_id = 'pb.000701' GROUP BY b.id,b.`name`,r.`status`;
		String sql = "SELECT b.id,b.`name`,r.`status`,SUM(r.building_area) FROM "+ModulePrefixNamingStrategy.classToTableName(Room.class)+" r " +
		"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Building.class)+" b ON r.building_id = b.id " +
		"GROUP BY b.id,b.`name`,r.`status`";
		List<Object> list = businessCustomerDao.getObjectListBySql(sql);
		Map<Long,StatisticGroupDto> groups = new HashMap<Long,StatisticGroupDto>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			Long bId = BigInteger.class.cast(objects[0]).longValue();
			if(!groups.containsKey(bId)){
				StatisticGroupDto group = new StatisticGroupDto();
				group.setName(String.valueOf(objects[1]));
				groups.put(bId, group);
			}
			String status = String.valueOf(objects[2]);
			Double value = Double.parseDouble(String.valueOf(objects[3]));
			StatisticDto dto = new StatisticDto();
			dto.setName(RoomStatusEnum.valueOf(status).getTitle());
			dto.setdValue(value);
			groups.get(bId).getList().add(dto);
		}
		List<StatisticGroupDto> groupList = new ArrayList<StatisticGroupDto>(groups.values());
		return Result.value(groupList);
	}

	@Override
	public Result<?> billCost(Date startTime, Date endTime , Date time , String type) {
		//SELECT t.type_name,b.in_out,SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id WHERE b.planPayTime < NOW() GROUP BY t.type_name,b.in_out;
		String sql = "";
		if(startTime!=null && endTime!=null){
			sql = "SELECT t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.status ='"+BillStatusEnum.FULLPAID+"'AND b.payTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY t.type_name,b.in_out";
				//" t ON b.bill_type_id = t.id WHERE b.planPayTime < NOW() GROUP BY t.id,t.type_name,b.in_out";
		}else if(startTime!=null && endTime==null){
			sql = "SELECT t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.payTime > '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY t.type_name,b.in_out";
		}else if(startTime==null && endTime!=null){
			sql = "SELECT t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.payTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY t.type_name,b.in_out";
		}else if(startTime==null && endTime==null){
			sql = "SELECT t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.payTime BETWEEN '"+DateUtil.format(new Date(),"yyyy-MM-dd")+"' AND '"+DateUtil.format(CalendarUtil.add(new Date(), Calendar.DAY_OF_YEAR, 1).getTime(),"yyyy-MM-dd")+"' GROUP BY t.id,t.type_name,b.in_out";
		}
		List<Object> list = businessCustomerDao.getObjectListBySql(sql);
		Map<Long,StatisticGroupDto> groups = new HashMap<Long,StatisticGroupDto>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			Long bId = BigInteger.class.cast(objects[0]).longValue();
			if(!groups.containsKey(bId)){
				StatisticGroupDto group = new StatisticGroupDto();
				group.setName(String.valueOf(objects[1]));
				groups.put(bId, group);
			}
			
			String inOut = String.valueOf(objects[2]);
			Double value = Double.parseDouble(String.valueOf(objects[3]));
			StatisticDto dto = new StatisticDto();
			dto.setName(BillInOutEnum.valueOf(inOut).getTitle());
			dto.setdValue(value);
			groups.get(bId).getList().add(dto);
		}
		List<StatisticGroupDto> groupList = new ArrayList<StatisticGroupDto>(groups.values());
		return Result.value(groupList);
	}

	@Override
	public Result<?> billCostByMonth(Date startTime, Date endTime,String type) {
		//SELECT t.id,b.planPayTime, t.type_name,b.in_out,SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id WHERE DATE_FORMAT(b.planPayTime,'%Y')=DATE_FORMAT(NOW(),'%Y') GROUP BY DATE_FORMAT(b.planPayTime,'%Y%m'),t.id,t.type_name,b.in_out;
		String sql = "SELECT b.payTime ,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
					" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
					" t ON b.bill_type_id = t.id WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND DATE_FORMAT(b.payTime,'%Y%m') between DATE_FORMAT('"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"','%Y%m') AND DATE_FORMAT('"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"','%Y%m') GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.id,t.type_name,b.in_out";
		
		String sql2 = "SELECT b.payTime ,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND DATE_FORMAT(b.payTime,'%Y%m') between DATE_FORMAT('"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"','%Y%m') AND DATE_FORMAT('"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"','%Y%m') GROUP BY DATE_FORMAT(b.payTime,'%Y'),t.id,t.type_name,b.in_out";
		
		List<Object> list = new ArrayList<Object>();
		List<StatisticGroupDto> topGroupList = new ArrayList<StatisticGroupDto>();
		if(type.equals("month")){
			LinkedHashMap<String,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<String,Map<Long,StatisticGroupDto>>();
			list = businessCustomerDao.getObjectListBySql(sql);
			for (Object object : list) {
				Object[] objects = (Object[])object;
				Date bTime = DateUtil.parse(String.valueOf(objects[0]));
				String month = DateUtil.format(bTime, "yyyy-MM");
				String tName = String.valueOf(objects[2]);
				if(!groupMaps.containsKey(month)){
					Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
					groupMaps.put(month, map);
				}
				Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(month);
				
				Long tId = BigInteger.class.cast(objects[1]).longValue();
				if(!subGroupMap.containsKey(tId)){
					StatisticGroupDto group = new StatisticGroupDto();
					group.setName(tName);
					group.setLid(tId);
					subGroupMap.put(tId, group);
				}
				StatisticGroupDto group = subGroupMap.get(tId);
				
				StatisticDto dto = new StatisticDto();
				dto.setName(String.valueOf(objects[3]));
				if(objects[4]!=null){
					dto.setdValue(Double.parseDouble(String.valueOf(objects[4])));
				}
				group.getList().add(dto);
				group.setAmount(group.getAmount()+1);
			}
			for (Entry<String, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
				StatisticGroupDto topGroup = new StatisticGroupDto();
				topGroup.setName(entry.getKey());
				topGroup.getGroups().addAll(entry.getValue().values());
				topGroupList.add(topGroup);
			}
		}else if(type.equals("year")){
			LinkedHashMap<Integer,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<Integer,Map<Long,StatisticGroupDto>>();
			list = businessCustomerDao.getObjectListBySql(sql2);
			for (Object object : list) {
				Object[] objects = (Object[])object;
				Date bTime = DateUtil.parse(String.valueOf(objects[0]));
				Integer year = Integer.parseInt(DateUtil.format(bTime, "yyyy"));
				String tName = String.valueOf(objects[2]);
				if(!groupMaps.containsKey(year)){
					Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
					groupMaps.put(year, map);
				}
				Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(year);
				
				Long tId = BigInteger.class.cast(objects[1]).longValue();
				if(!subGroupMap.containsKey(tId)){
					StatisticGroupDto group = new StatisticGroupDto();
					group.setName(tName);
					group.setLid(tId);
					subGroupMap.put(tId, group);
				}
				StatisticGroupDto group = subGroupMap.get(tId);
				
				StatisticDto dto = new StatisticDto();
				dto.setName(String.valueOf(objects[3]));
				if(objects[4]!=null){
				dto.setdValue(Double.parseDouble(String.valueOf(objects[4])));
				}
				group.getList().add(dto);
				group.setAmount(group.getAmount()+1);
			}
			for (Entry<Integer, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
				StatisticGroupDto topGroup = new StatisticGroupDto();
				topGroup.setAmount(entry.getKey());
				topGroup.getGroups().addAll(entry.getValue().values());
				topGroupList.add(topGroup);
			}
		}
		return Result.value(topGroupList);
	}
	@Override
	public Result<?> billCostByTime(Date time, String type) {
		//SELECT t.id,b.planPayTime, t.type_name,b.in_out,SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id WHERE DATE_FORMAT(b.planPayTime,'%Y')=DATE_FORMAT(NOW(),'%Y') GROUP BY DATE_FORMAT(b.planPayTime,'%Y%m'),t.id,t.type_name,b.in_out;
		String sql = "";
		if(time==null){
			sql = "SELECT b.payTime ,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND DATE_FORMAT(b.payTime,'%Y')=DATE_FORMAT(NOW(),'%Y') GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.id,t.type_name,b.in_out";
				//" t ON b.bill_type_id = t.id WHERE DATE_FORMAT(b.planPayTime,'%Y')=DATE_FORMAT(NOW(),'%Y') GROUP BY DATE_FORMAT(b.planPayTime,'%Y%m'),t.id,t.type_name,b.in_out";
		}else{
			sql = "SELECT b.payTime ,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND DATE_FORMAT(b.payTime,'%Y')=DATE_FORMAT('"+DateUtil.format(time,"yyyy-MM-dd HH:mm:ss")+"','%Y') GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.id,t.type_name,b.in_out";
		}
		
		//SELECT b.planPayTime,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id GROUP BY DATE_FORMAT(b.planPayTime,'%Y'),t.id,t.type_name,b.in_out;
		String sql2 = "SELECT b.payTime ,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.status = '"+BillStatusEnum.FULLPAID+"' GROUP BY DATE_FORMAT(b.payTime,'%Y'),t.id,t.type_name,b.in_out";
		
		List<Object> list = new ArrayList<Object>();
		Map<Integer,StatisticGroupDto> groups = new HashMap<Integer,StatisticGroupDto>();
		List<StatisticGroupDto> topGroupList = new ArrayList<StatisticGroupDto>();
		if(type.equals("month")){
			LinkedHashMap<String,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<String,Map<Long,StatisticGroupDto>>();
			list = businessCustomerDao.getObjectListBySql(sql);
			for (Object object : list) {
				Object[] objects = (Object[])object;
				Date bTime = DateUtil.parse(String.valueOf(objects[0]));
				String month = DateUtil.format(bTime, "yyyy-MM");
				String tName = String.valueOf(objects[2]);
				if(!groupMaps.containsKey(month)){
					Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
					groupMaps.put(month, map);
				}
				Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(month);
				
				Long tId = BigInteger.class.cast(objects[1]).longValue();
				if(!subGroupMap.containsKey(tId)){
					StatisticGroupDto group = new StatisticGroupDto();
					group.setName(tName);
					group.setLid(tId);
					subGroupMap.put(tId, group);
				}
				StatisticGroupDto group = subGroupMap.get(tId);
				
				StatisticDto dto = new StatisticDto();
				dto.setName(String.valueOf(objects[3]));
				if(objects[4]!=null){
				dto.setdValue(Double.parseDouble(String.valueOf(objects[4])));
				}
				group.getList().add(dto);
				group.setAmount(group.getAmount()+1);
			}
			for (Entry<String, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
				StatisticGroupDto topGroup = new StatisticGroupDto();
				topGroup.setName(entry.getKey());
				topGroup.getGroups().addAll(entry.getValue().values());
				topGroupList.add(topGroup);
			}
		}else if(type.equals("year")){
			LinkedHashMap<Integer,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<Integer,Map<Long,StatisticGroupDto>>();
			list = businessCustomerDao.getObjectListBySql(sql2);
			for (Object object : list) {
				Object[] objects = (Object[])object;
				Date bTime = DateUtil.parse(String.valueOf(objects[0]));
				Integer year = Integer.parseInt(DateUtil.format(bTime, "yyyy"));
				String tName = String.valueOf(objects[2]);
				if(!groupMaps.containsKey(year)){
					Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
					groupMaps.put(year, map);
				}
				Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(year);
				
				Long tId = BigInteger.class.cast(objects[1]).longValue();
				if(!subGroupMap.containsKey(tId)){
					StatisticGroupDto group = new StatisticGroupDto();
					group.setName(tName);
					group.setLid(tId);
					subGroupMap.put(tId, group);
				}
				StatisticGroupDto group = subGroupMap.get(tId);
				
				StatisticDto dto = new StatisticDto();
				dto.setName(String.valueOf(objects[3]));
				if(objects[4]!=null){
				dto.setdValue(Double.parseDouble(String.valueOf(objects[4])));
				}
				group.getList().add(dto);
				group.setAmount(group.getAmount()+1);
			}
			for (Entry<Integer, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
				StatisticGroupDto topGroup = new StatisticGroupDto();
				topGroup.setAmount(entry.getKey());
				topGroup.getGroups().addAll(entry.getValue().values());
				topGroupList.add(topGroup);
			}
		}
		return Result.value(topGroupList);
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public Result<?> billCostByCustomer(Long customerId, Date startTime, Date endTime) {
		//SELECT c.`name`, t.id , t.type_name, b.in_out,SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id  LEFT JOIN crm_customer c on b.customer_id=c.id GROUP BY  c.`name`, t.id, t.type_name, b.in_out;
		String sql = "";
		if(customerId==null && startTime==null && endTime==null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id where b.status = '"+BillStatusEnum.FULLPAID+
				"' GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		}else if(customerId!=null && startTime==null && endTime==null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id " +
					"WHERE c.id='"+customerId+"'AND b.status = '"+BillStatusEnum.FULLPAID+"' GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		}else if(customerId!=null && startTime!=null && endTime==null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id " +
				"WHERE c.id='"+customerId+"' AND b.status = '"+BillStatusEnum.FULLPAID+"' AND b.payTime > '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"'  GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		}else if(customerId!=null && startTime==null && endTime!=null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id " +
					"WHERE c.id='"+customerId+"' AND b.status = '"+BillStatusEnum.FULLPAID+"' AND b.payTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"'  GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		}else if(customerId==null && startTime!=null && endTime!=null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id " +
					"WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.payTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"'  GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		}else if(customerId!=null && startTime!=null && endTime!=null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
					"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id " +
					"WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.payTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' AND c.id='"+customerId+"' GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		}else if(customerId==null && startTime!=null && endTime==null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id " +
				"WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.payTime > '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		
		}else if(customerId==null && startTime==null && endTime!=null){
			sql = "SELECT c.`name`, t.id, t.type_name, b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+" b " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+" t ON b.bill_type_id=t.id " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c ON b.customer_id=c.id " +
				"WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.payTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY c.`name`, t.id, t.type_name, b.in_out";
		}
		LinkedHashMap<String,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<String,Map<Long,StatisticGroupDto>>();
		List<Object> list = businessCustomerDao.getObjectListBySql(sql);
		for (Object object : list) {
			Object[] objects = (Object[])object;
			String cName = String.valueOf(objects[0]);
			if(!groupMaps.containsKey(cName)){
				Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
				groupMaps.put(cName, map);
			}
			Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(cName);
			
			Long tId = BigInteger.class.cast(objects[1]).longValue();
			if(!subGroupMap.containsKey(tId)){
				StatisticGroupDto group = new StatisticGroupDto();
				group.setName(String.valueOf(objects[2]));
				group.setLid(tId);
				subGroupMap.put(tId, group);
			}
			StatisticGroupDto group = subGroupMap.get(tId);
			
			StatisticDto dto = new StatisticDto();
			dto.setName(String.valueOf(objects[3]));
			if(objects[4]!=null){
			dto.setdValue(Double.parseDouble(String.valueOf(objects[4])));
			}
			group.getList().add(dto);
			group.setAmount(group.getAmount()+1);
		}
		List<StatisticGroupDto> topGroupList = new ArrayList<StatisticGroupDto>();
		for (Entry<String, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
			StatisticGroupDto topGroup = new StatisticGroupDto();
			topGroup.setName(entry.getKey());
			topGroup.getGroups().addAll(entry.getValue().values());
			topGroupList.add(topGroup);
		}
		return Result.value(topGroupList);
	}
*/
	//物业应收未收统计
	@Override
	public Result<?> billWzubCostByProperty(Date startTime,Date endTime) {
		//SELECT b.planPayTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id WHERE b.in_out='IN' GROUP BY DATE_FORMAT(b.planPayTime,'%Y%m'),t.type_name;
		//应收
		String sql = "SELECT b.checkoutTime,t.id,t.type_name,SUM(b.totalAmount) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status != '"+BillStatusEnum.CHARGEOFF+"' AND b.in_out='IN' AND ";
		//实收（不累计）
		String sql2 = "SELECT b.payTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.in_out='IN' AND ";
		if(startTime==null && endTime==null){
			//默认显示当前月
			Date preDate = CalendarUtil.getEarliest(new Date(),Calendar.MONTH);
			Date nextDate = CalendarUtil.getLatest(new Date(),Calendar.MONTH);
			sql2 += " b.payTime BETWEEN '"+DateUtil.format(preDate,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(nextDate,"yyyy-MM-dd")+"' AND b.checkoutTime BETWEEN '"+DateUtil.format(preDate,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(nextDate,"yyyy-MM-dd")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
			sql += "b.checkoutTime BETWEEN '"+DateUtil.format(preDate,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(nextDate,"yyyy-MM-dd")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
		}else if(startTime!=null && endTime==null){
			sql2 += "b.payTime < '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND b.checkoutTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
			sql += "b.checkoutTime > '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
		}else if(startTime==null && endTime!=null){
			sql2 += "b.payTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' AND b.checkoutTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
			sql += "b.checkoutTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
		}else if(startTime!=null && endTime!=null){
			sql2 += "b.payTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' AND b.checkoutTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
			sql += "b.checkoutTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
		}
		List<Object> list = new ArrayList<Object>();
		List<Object> list2 = new ArrayList<Object>();
		list = businessCustomerDao.getObjectListBySql(sql);
		list2 = businessCustomerDao.getObjectListBySql(sql2);
		
		Map<String,Map<String,Double>> factPayMap = new HashMap<String,Map<String,Double>>();//实收
		for (Object object : list2) {
			Object[] objects = (Object[])object;
			Date bTime = DateUtil.parse(String.valueOf(objects[0]));
			String time = DateUtil.format(bTime, "yyyy-MM");
			String tName = String.valueOf(objects[2]);
			if(!factPayMap.containsKey(time)){
				Map<String,Double> map = new HashMap<String, Double>();
				map.put(tName, Double.parseDouble(String.valueOf(objects[4])));
				factPayMap.put(time, map);
			}else{
				factPayMap.get(time).put(tName, Double.parseDouble(String.valueOf(objects[4])));
			}
		}
		
		List<StatisticGroupDto> topGroupList = new ArrayList<StatisticGroupDto>();		
		LinkedHashMap<String,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<String,Map<Long,StatisticGroupDto>>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			Date bTime = DateUtil.parse(String.valueOf(objects[0]));
			String time = DateUtil.format(bTime, "yyyy-MM");
			String tName = String.valueOf(objects[2]);
			if(!groupMaps.containsKey(time)){
				Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
				groupMaps.put(time, map);
			}
			Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(time);
			
			Long tId = BigInteger.class.cast(objects[1]).longValue();
			if(!subGroupMap.containsKey(tId)){
				StatisticGroupDto group = new StatisticGroupDto();
				group.setName(tName);
				group.setLid(tId);
				subGroupMap.put(tId, group);
			}
			StatisticGroupDto group = subGroupMap.get(tId);
			
			StatisticDto dto = new StatisticDto();
			dto.setName(tName);
			dto.setdValue(Double.parseDouble(String.valueOf(objects[3])));
			if(factPayMap.get(time)!=null && factPayMap.get(time).get(tName)!=null){
				BigDecimal bd1 = new BigDecimal(dto.getdValue()); 
		        BigDecimal bd2 = new BigDecimal(factPayMap.get(time).get(tName));
		        dto.setdValue2(bd1.subtract(bd2).doubleValue());
			}else{
				dto.setdValue2(dto.getdValue());
			}
			/*if(objects[4]!=null){
				
				BigDecimal bd1 = new BigDecimal(dto.getdValue()); 
		        BigDecimal bd2 = new BigDecimal(Double.parseDouble(String.valueOf(objects[4])));
		        dto.setdValue2(bd1.subtract(bd2).doubleValue());
			}*/
			group.getList().add(dto);
			group.setAmount(group.getAmount()+1);
		}
		for (Entry<String, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
			StatisticGroupDto topGroup = new StatisticGroupDto();
			topGroup.setName(entry.getKey());
			topGroup.getGroups().addAll(entry.getValue().values());
			topGroupList.add(topGroup);
		}
		return Result.value(topGroupList);
	}
	//物业应收实收统计
	@Override
	public Result<?> billCostByProperty(Date startTime,Date endTime) {
		//SELECT b.planPayTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id WHERE b.in_out='IN' GROUP BY DATE_FORMAT(b.planPayTime,'%Y%m'),t.type_name;
		//实收
		String sql = "SELECT b.payTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status != '"+BillStatusEnum.CHARGEOFF+"' AND b.in_out='IN' AND ";
		//应收
		String sql2 = "SELECT b.checkoutTime,t.id,t.type_name,SUM(b.totalAmount) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status != '"+BillStatusEnum.CHARGEOFF+"' AND b.in_out='IN' AND ";
		if(startTime==null && endTime==null){
			//默认显示三个月的时间(前一月 当前月  后一月)
			Date preDate = CalendarUtil.add(new Date(), Calendar.MONTH, -1).getTime();
			Date nextDate = CalendarUtil.add(new Date(), Calendar.MONTH, 1).getTime();
			sql2 += "b.checkoutTime BETWEEN '"+DateUtil.format(preDate,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(nextDate,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
			sql += "b.payTime BETWEEN '"+DateUtil.format(preDate,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(nextDate,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}else if(startTime!=null && endTime==null){
			sql2 += "b.checkoutTime > '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
			sql += "b.payTime > '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}else if(startTime==null && endTime!=null){
			sql2 += "b.checkoutTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
			sql += "b.payTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}else if(startTime!=null && endTime!=null){
			sql2 += "b.checkoutTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.checkoutTime,'%Y%m'),t.type_name";
			sql += "b.payTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}
		List<Object> list = new ArrayList<Object>();
		list = businessCustomerDao.getObjectListBySql(sql);
		List<Object> list2 = new ArrayList<Object>();
		list2 = businessCustomerDao.getObjectListBySql(sql2);
		
		
		Map<String,Map<String,Double>> factPayMap = new HashMap<String,Map<String,Double>>();//实收
		for (Object object : list) {
			Object[] objects = (Object[])object;
			Date bTime = DateUtil.parse(String.valueOf(objects[0]));
			String time = DateUtil.format(bTime, "yyyy-MM");
			String tName = String.valueOf(objects[2]);
			if(!factPayMap.containsKey(time)){
				Map<String,Double> map = new HashMap<String, Double>();
				map.put(tName, Double.parseDouble(String.valueOf(objects[4])));
				factPayMap.put(time, map);
			}else{
				factPayMap.get(time).put(tName, Double.parseDouble(String.valueOf(objects[4])));
			}
		}
		List<StatisticGroupDto> topGroupList = new ArrayList<StatisticGroupDto>();		
		LinkedHashMap<String,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<String,Map<Long,StatisticGroupDto>>();
		for (Object object : list2) {
			Object[] objects = (Object[])object;
			Date bTime = DateUtil.parse(String.valueOf(objects[0]));
			String time = DateUtil.format(bTime, "yyyy-MM");
			String tName = String.valueOf(objects[2]);//类型名称
			if(!groupMaps.containsKey(time)){
				Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
				groupMaps.put(time, map);
			}
			Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(time);
			
			Long tId = BigInteger.class.cast(objects[1]).longValue();//类型id
			if(!subGroupMap.containsKey(tId)){
				StatisticGroupDto group = new StatisticGroupDto();
				group.setName(tName);
				group.setLid(tId);
				subGroupMap.put(tId, group);
			}
			StatisticGroupDto group = subGroupMap.get(tId);
			
			StatisticDto dto = new StatisticDto();
			dto.setName(tName);
			
			dto.setdValue(Double.parseDouble(String.valueOf(objects[3])));
			
			if(factPayMap.get(time)!=null && factPayMap.get(time).get(tName)!=null){
				dto.setdValue2(factPayMap.get(time).get(tName));
			}else{
				dto.setdValue2(0D);
			}
			group.getList().add(dto);
			group.setAmount(group.getAmount()+1);
		}
		for (Entry<String, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
			StatisticGroupDto topGroup = new StatisticGroupDto();
			topGroup.setName(entry.getKey());
			topGroup.getGroups().addAll(entry.getValue().values());
			topGroupList.add(topGroup);
		}
		return Result.value(topGroupList);
	}

	/*@Override
	public Result<?> billCostByProperty(Date startTime,Date endTime) {
		//SELECT b.planPayTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id WHERE b.in_out='IN' GROUP BY DATE_FORMAT(b.planPayTime,'%Y%m'),t.type_name;
		String sql = "";
		if(startTime==null && endTime==null){
			//默认显示三个月的时间(前一月 当前月  后一月)
			Date preDate = CalendarUtil.add(new Date(), Calendar.MONTH, -1).getTime();
			Date nextDate = CalendarUtil.add(new Date(), Calendar.MONTH, 1).getTime();
			sql = "SELECT b.payTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.in_out='IN' AND b.payTime BETWEEN '"+DateUtil.format(preDate,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(nextDate,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}else if(startTime!=null && endTime==null){
			sql = "SELECT b.payTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.in_out='IN' AND b.payTime > '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}else if(startTime==null && endTime!=null){
			sql = "SELECT b.payTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.in_out='IN' AND b.payTime < '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}else if(startTime!=null && endTime!=null){
			sql = "SELECT b.payTime,t.id,t.type_name,SUM(b.totalAmount),SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id  WHERE b.status = '"+BillStatusEnum.FULLPAID+"' AND b.in_out='IN' AND b.payTime BETWEEN '"+DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss")+"' AND '"+DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss")+"' GROUP BY DATE_FORMAT(b.payTime,'%Y%m'),t.type_name";
		}
		List<Object> list = new ArrayList<Object>();
		list = businessCustomerDao.getObjectListBySql(sql);
		List<StatisticGroupDto> topGroupList = new ArrayList<StatisticGroupDto>();		
		LinkedHashMap<String,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<String,Map<Long,StatisticGroupDto>>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			Date bTime = DateUtil.parse(String.valueOf(objects[0]));
			String time = DateUtil.format(bTime, "yyyy-MM");
			String tName = String.valueOf(objects[2]);
			if(!groupMaps.containsKey(time)){
				Map<Long,StatisticGroupDto> map = new HashMap<Long,StatisticGroupDto>();
				groupMaps.put(time, map);
			}
			Map<Long,StatisticGroupDto> subGroupMap = groupMaps.get(time);
			
			Long tId = BigInteger.class.cast(objects[1]).longValue();
			if(!subGroupMap.containsKey(tId)){
				StatisticGroupDto group = new StatisticGroupDto();
				group.setName(tName);
				group.setLid(tId);
				subGroupMap.put(tId, group);
			}
			StatisticGroupDto group = subGroupMap.get(tId);
			
			StatisticDto dto = new StatisticDto();
			dto.setName(tName);
			dto.setdValue(Double.parseDouble(String.valueOf(objects[3])));
			if(objects[4]!=null){
			dto.setdValue2(Double.parseDouble(String.valueOf(objects[4])));
			}
			group.getList().add(dto);
			group.setAmount(group.getAmount()+1);
		}
		for (Entry<String, Map<Long, StatisticGroupDto>> entry : groupMaps.entrySet()) {
			StatisticGroupDto topGroup = new StatisticGroupDto();
			topGroup.setName(entry.getKey());
			topGroup.getGroups().addAll(entry.getValue().values());
			topGroupList.add(topGroup);
		}
		return Result.value(topGroupList);
	}*/
	
	@Override
	public Result<?> billCostYearCount() {
		//SELECT b.planPayTime,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM crm_bill b LEFT JOIN crm_bill_type t ON b.bill_type_id = t.id GROUP BY DATE_FORMAT(b.planPayTime,'%Y'),t.id,t.type_name,b.in_out;
		String sql = "SELECT b.planPayTime ,t.id,t.type_name,b.in_out,SUM(b.factPayment) FROM "+ModulePrefixNamingStrategy.classToTableName(Bill.class)+
				" b LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(BillType.class)+
				" t ON b.bill_type_id = t.id WHERE b.status = '"+BillStatusEnum.FULLPAID+"' GROUP BY t.id,t.type_name,b.in_out";
		List<Object> list = new ArrayList<Object>();
		list = businessCustomerDao.getObjectListBySql(sql);
		List<StatisticDto> topList = new ArrayList<StatisticDto>();		
		LinkedHashMap<String,Map<Long,StatisticGroupDto>> groupMaps = new LinkedHashMap<String,Map<Long,StatisticGroupDto>>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			String tName = String.valueOf(objects[2]);
			String inOut = String.valueOf(objects[3]);
			Double dValue = Double.parseDouble(String.valueOf(objects[4]));
			StatisticDto dto = new StatisticDto();
			dto.setName(tName);
			dto.setMemo(inOut);
			dto.setdValue(dValue);
			topList.add(dto);
		}
		return Result.value(topList);
	}

	
	static boolean isFirstHalfYear(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return calendar.get(Calendar.MONTH)<6;
	}

	@Override
	public List<Object[]> exportBillByTime(Date startTime,String type) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>)billCostByTime(startTime, type).getValue();
		List<BillReportDto> dtoList = new ArrayList<BillReportDto>();
		List<String> colNames = new ArrayList<String>();
		if(topGroupList!=null && topGroupList.size()>0){
			for(StatisticGroupDto statisticGroupDto : topGroupList){
				BillReportDto brdto = new BillReportDto();
				String monthName = "";
				if(type.equals("month")){
					monthName = statisticGroupDto.getName();
				}else if(type.equals("year")){
					monthName = statisticGroupDto.getAmount()+"";
				}
				List<StatisticDto> inOutList = new ArrayList<StatisticDto>();
				Double totalIn = 0D;
				Double totalOut = 0D;
				for(StatisticGroupDto childDto : statisticGroupDto.getGroups()){
					if(!colNames.contains(childDto.getName()+"收入")){
						colNames.add(childDto.getName()+"收入");
					}
					if(!colNames.contains(childDto.getName()+"支出")){
						colNames.add(childDto.getName()+"支出");
					}
					if(childDto.getList()!=null && childDto.getList().size()>0){
						String inValue = "0.0";
						String outValue = "0.0";
						for(StatisticDto dto : childDto.getList()){
							StatisticDto statisticDto = new StatisticDto();
							if(dto.getName().equals("IN")){
								inValue = dto.getdValue()+"";
								statisticDto.setName(childDto.getName()+"收入");
								statisticDto.setdValue(dto.getdValue());
							}else if(dto.getName().equals("OUT")){
								outValue = dto.getdValue()+"";
								statisticDto.setName(childDto.getName()+"支出");
								statisticDto.setdValue(dto.getdValue());
							}
							inOutList.add(statisticDto);
						}
						if(!inValue.equals("0.0")){
							totalIn += Double.parseDouble(inValue);
						}
						if(!outValue.equals("0.0")){
							totalOut += Double.parseDouble(outValue);
						}
					}
				}
				brdto.setTime(monthName);
				brdto.setInOutList(inOutList);
				brdto.setTotalIn(totalIn);
				brdto.setTotalOut(totalOut);
				dtoList.add(brdto);
			}
		}
		int j =1;
		for (BillReportDto dto : dtoList) {
			List<String> datas = new ArrayList<String>();
			datas.add(j+"");
			j++;
			datas.add(dto.getTime());
			loop:for(String name : colNames){
				int i=0;
				for(StatisticDto dtoN : dto.getInOutList()){
					if(dtoN.getName().equals(name)){
						datas.add(dtoN.getdValue()+"");
						i = 1;
						continue loop;
					}
				}
				if(i==0){
					datas.add("0");
				}
			}
			datas.add(dto.getTotalIn()+"");
			datas.add(dto.getTotalOut()+"");
			dataList.add(datas.toArray());
		}
		List<String> countDatas = new ArrayList<String>();
		countDatas.add(j+"");
		countDatas.add("合计");
		for(String name : colNames){
			Double count = 0D;
			for (BillReportDto dto : dtoList) {
				for(StatisticDto dtoN : dto.getInOutList()){
					if(dtoN.getName().equals(name)){
						count += dtoN.getdValue();
					}
				}
			}
			countDatas.add(count+"");
		}
		Double ti = 0D;
		Double to = 0D;
		for(BillReportDto dto : dtoList){
			ti += dto.getTotalIn();
			to += dto.getTotalOut();
		}
		countDatas.add(ti+"");
		countDatas.add(to+"");
		dataList.add(countDatas.toArray());
		return dataList;
	}

	@Override
	public List<Object[]> exportBillByCustomer(Long customerId, Date startTime,Date endTime) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>)billCostByCustomer(customerId,startTime,endTime).getValue();
		List<BillReportDto> dtoList = new ArrayList<BillReportDto>();
		List<String> colNames = new ArrayList<String>();
		if(topGroupList!=null && topGroupList.size()>0){
			for(StatisticGroupDto statisticGroupDto : topGroupList){
				BillReportDto brdto = new BillReportDto();
				String monthName = statisticGroupDto.getName();
				List<StatisticDto> inOutList = new ArrayList<StatisticDto>();
				Double totalIn = 0D;
				Double totalOut = 0D;
				for(StatisticGroupDto childDto : statisticGroupDto.getGroups()){
					if(!colNames.contains(childDto.getName()+"收入")){
						colNames.add(childDto.getName()+"收入");
					}
					if(!colNames.contains(childDto.getName()+"支出")){
						colNames.add(childDto.getName()+"支出");
					}
					if(childDto.getList()!=null && childDto.getList().size()>0){
						String inValue = "0.0";
						String outValue = "0.0";
						for(StatisticDto dto : childDto.getList()){
							StatisticDto statisticDto = new StatisticDto();
							if(dto.getName().equals("IN")){
								inValue = dto.getdValue()+"";
								statisticDto.setName(childDto.getName()+"收入");
								statisticDto.setdValue(dto.getdValue());
							}else if(dto.getName().equals("OUT")){
								outValue = dto.getdValue()+"";
								statisticDto.setName(childDto.getName()+"支出");
								statisticDto.setdValue(dto.getdValue());
							}
							inOutList.add(statisticDto);
						}
						if(!inValue.equals("0.0")){
							totalIn += Double.parseDouble(inValue);
						}
						if(!outValue.equals("0.0")){
							totalOut += Double.parseDouble(outValue);
						}
					}
				}
				brdto.setTime(monthName);
				brdto.setInOutList(inOutList);
				brdto.setTotalIn(totalIn);
				brdto.setTotalOut(totalOut);
				dtoList.add(brdto);
			}
		}
		int j =1;
		for (BillReportDto dto : dtoList) {
			List<String> datas = new ArrayList<String>();
			datas.add(j+"");
			j++;
			datas.add(dto.getTime());
			loop:for(String name : colNames){
				int i=0;
				for(StatisticDto dtoN : dto.getInOutList()){
					if(dtoN.getName().equals(name)){
						datas.add(dtoN.getdValue()+"");
						i = 1;
						continue loop;
					}
				}
				if(i==0){
					datas.add("0");
				}
			}
			datas.add(dto.getTotalIn()+"");
			datas.add(dto.getTotalOut()+"");
			dataList.add(datas.toArray());
		}
		return dataList;
	}

	@Override
	public List<Object[]> exportBillByProperty(Date startTime, Date endTime) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>)billCostByProperty(startTime,endTime).getValue();
		List<BillReportDto> dtoList = new ArrayList<BillReportDto>();
		List<String> colNames = new ArrayList<String>();
		if(topGroupList!=null && topGroupList.size()>0){
			for(StatisticGroupDto statisticGroupDto : topGroupList){
				BillReportDto brdto = new BillReportDto();
				String monthName = statisticGroupDto.getName();
				List<StatisticDto> inOutList = new ArrayList<StatisticDto>();
				for(StatisticGroupDto childDto : statisticGroupDto.getGroups()){
					if(!colNames.contains("应收"+childDto.getName())){
						colNames.add("应收"+childDto.getName());
					}
					if(!colNames.contains("实收"+childDto.getName())){
						colNames.add("实收"+childDto.getName());
					}
					if(childDto.getList()!=null && childDto.getList().size()>0){
						String inValue = "0.0";
						String outValue = "0.0";
						for(StatisticDto dto : childDto.getList()){
							StatisticDto statisticDto = new StatisticDto();
							statisticDto.setName("应收"+childDto.getName());
							if(dto.getdValue()!=null){
								inValue = dto.getdValue()+"";
							}
							statisticDto.setdValue(dto.getdValue());
							
							StatisticDto statisticDto2 = new StatisticDto();
							statisticDto2.setName("实收"+childDto.getName());
							if(dto.getdValue2()!=null){
								outValue = dto.getdValue2()+"";
							}
							statisticDto2.setdValue(dto.getdValue2());
							
							inOutList.add(statisticDto);
							inOutList.add(statisticDto2);
						}
						
					}
				}
				brdto.setTime(monthName);
				brdto.setInOutList(inOutList);
				dtoList.add(brdto);
			}
		}
		int j =1;
		for (BillReportDto dto : dtoList) {
			List<String> datas = new ArrayList<String>();
			datas.add(j+"");
			j++;
			datas.add(dto.getTime());
			loop:for(String name : colNames){
				int i=0;
				for(StatisticDto dtoN : dto.getInOutList()){
					if(dtoN.getName().equals(name)){
						datas.add(dtoN.getdValue()+"");
						i = 1;
						continue loop;
					}
				}
				if(i==0){
					datas.add("0");
				}
			}
			dataList.add(datas.toArray());
		}
		return dataList;
	}
	
	@Override
	public List<Object[]> exportBillWzubByProperty(Date startTime, Date endTime) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<StatisticGroupDto> topGroupList = (List<StatisticGroupDto>)billWzubCostByProperty(startTime,endTime).getValue();
		List<BillReportDto> dtoList = new ArrayList<BillReportDto>();
		List<String> colNames = new ArrayList<String>();
		if(topGroupList!=null && topGroupList.size()>0){
			for(StatisticGroupDto statisticGroupDto : topGroupList){
				BillReportDto brdto = new BillReportDto();
				String monthName = statisticGroupDto.getName();
				List<StatisticDto> inOutList = new ArrayList<StatisticDto>();
				for(StatisticGroupDto childDto : statisticGroupDto.getGroups()){
					if(!colNames.contains("应收"+childDto.getName())){
						colNames.add("应收"+childDto.getName());
					}
					if(!colNames.contains("未收"+childDto.getName())){
						colNames.add("未收"+childDto.getName());
					}
					if(childDto.getList()!=null && childDto.getList().size()>0){
						String inValue = "0.0";
						String outValue = "0.0";
						for(StatisticDto dto : childDto.getList()){
							StatisticDto statisticDto = new StatisticDto();
							statisticDto.setName("应收"+childDto.getName());
							if(dto.getdValue()!=null){
								inValue = dto.getdValue()+"";
							}
							statisticDto.setdValue(dto.getdValue());
							
							StatisticDto statisticDto2 = new StatisticDto();
							statisticDto2.setName("未收"+childDto.getName());
							if(dto.getdValue2()!=null){
								outValue = dto.getdValue2()+"";
							}
							statisticDto2.setdValue(dto.getdValue2());
							
							inOutList.add(statisticDto);
							inOutList.add(statisticDto2);
						}
						
					}
				}
				brdto.setTime(monthName);
				brdto.setInOutList(inOutList);
				dtoList.add(brdto);
			}
		}
		int j =1;
		for (BillReportDto dto : dtoList) {
			List<String> datas = new ArrayList<String>();
			datas.add(j+"");
			j++;
			datas.add(dto.getTime());
			loop:for(String name : colNames){
				int i=0;
				for(StatisticDto dtoN : dto.getInOutList()){
					if(dtoN.getName().equals(name)){
						datas.add(dtoN.getdValue()+"");
						i = 1;
						continue loop;
					}
				}
				if(i==0){
					datas.add("0");
				}
			}
			dataList.add(datas.toArray());
		}
		return dataList;
	}

	@Override
	public Result<?> billCostByCustomer(Long customerId, Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}
}

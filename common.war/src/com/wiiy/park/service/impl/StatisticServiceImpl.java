package com.wiiy.park.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wiiy.common.dto.StatisticDto;
import com.wiiy.common.dto.StatisticGroupDto;
import com.wiiy.common.preferences.R.Customer;
import com.wiiy.common.preferences.enums.RoomStatusEnum;
import com.wiiy.core.entity.DataDict;
import com.wiiy.hibernate.ModulePrefixNamingStrategy;
import com.wiiy.hibernate.Pager;
import com.wiiy.hibernate.Result;
import com.wiiy.park.dao.ParkDao;
import com.wiiy.park.entity.Building;
import com.wiiy.park.entity.Room;
import com.wiiy.park.service.StatisticService;

public class StatisticServiceImpl implements StatisticService {
	
	/*private parkDao parkDao;

	
	public void setparkDao(parkDao parkDao) {
		this.parkDao = parkDao;
	}
*/
	private ParkDao parkDao;
	
	public void setParkDao(ParkDao parkDao) {
		this.parkDao = parkDao;
	}
	
	@Override
	public Result<?> customerCategory() {
		return null;
	}


	@Override
	public Result<?> customerTechnic() {
		//SELECT d.data_value,COUNT(c.id) FROM core_data_dict d LEFT JOIN crm_customer c on d.id = c.technic_id WHERE d.parent_id = 'crm.0002' GROUP BY d.data_value;
		List<Object> list = parkDao.getObjectListBySql("SELECT d.data_value,COUNT(c.id) FROM "+ModulePrefixNamingStrategy.classToTableName(DataDict.class)+" d LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Customer.class)+" c on d.id = c.technic_id WHERE d.parent_id = 'crm.0002' GROUP BY d.data_value");
		List<StatisticDto> dtoList = new ArrayList<StatisticDto>();
		for (Object object : list) {
			Object[] objects = (Object[])object;
			String name = String.valueOf(objects[0]);
			Integer amount = BigInteger.class.cast(objects[1]).intValue();
			StatisticDto dto = new StatisticDto();
			dto.setName(name);
			dto.setAmount(amount);
			dtoList.add(dto);
		}
		return Result.value(dtoList);
	}

	@Override
	public Result<?> buildingGraph() {
		//SELECT b.id,b.`name`,d.data_value,SUM(r.building_area) FROM pb_room r LEFT JOIN pb_building b ON r.building_id = b.id LEFT JOIN core_data_dict d ON r.kind_id = d.id GROUP BY b.id,b.`name`,d.id,d.data_value;
		String sql = "SELECT b.id,b.`name`,d.data_value,SUM(r.building_area) FROM "+ModulePrefixNamingStrategy.classToTableName(Room.class)+" r " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(Building.class)+" b ON r.building_id = b.id " +
				"LEFT JOIN "+ModulePrefixNamingStrategy.classToTableName(DataDict.class)+" d ON r.kind_id = d.id " +
				"GROUP BY b.id,b.`name`,d.id,d.data_value";
		List<Object> list = parkDao.getObjectListBySql(sql);
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
		List<Object> list = parkDao.getObjectListBySql(sql);
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

	
	static boolean isFirstHalfYear(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return calendar.get(Calendar.MONTH)<6;
	}

	@Override
	public Result<?> customerParkStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> customerIncubationStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> customerLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> productTechnic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> registeredCapital() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> customerStaffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> propertyRight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> billCost(Date startTime, Date endTime, Date time,
			String timeType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> billCostByTime(Date time, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> billCostYearCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> billCostByCustomer(Long customerId, Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> billCostByProperty(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> customerContract(Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> exportBillByTime(Date startTime, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> exportBillByCustomer(Long customerId, Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> exportBillByProperty(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> billWzubCostByProperty(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> exportBillWzubByProperty(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> billCostByMonth(Date startTime, Date endTime, String string) {
		// TODO Auto-generated method stub
		return null;
	}
}

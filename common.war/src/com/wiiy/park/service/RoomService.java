package com.wiiy.park.service;

import java.util.List;

import com.wiiy.common.dto.CustomerDto;
import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Room;
import com.wiiy.park.entity.RoomAtt;

/**
 * @author my
 */
public interface RoomService extends IService<Room> {
	boolean checkNameUnique(Long buildingId,String name);
	
	boolean checkNameUnique(Long buildingId,String name,Long excludeId);
	
	String[] minus(String[] arr1, String[] arr2);
	
	int isImg(String name);
	
	Result<List<Room>> getListByHql(String hql);
	
	Result<Room> save(Room t,List<RoomAtt> atts);
	Result update(Room t,List<RoomAtt> atts);

	List<Object> getListBySql(String sql);

	Result<List<CustomerDto>> getCustomerList(Long id);

	Result getRowCount(Filter filter);
	Result<Room> mergeRoom(Room room1, Room room2,String newName);

	Result<Room> roomBroken(Room room, String string, String string2);
}

package com.wiiy.park.service.impl;


import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.common.activator.ProjectActivator;
import com.wiiy.common.dto.CustomerDto;
import com.wiiy.common.preferences.R;
import com.wiiy.common.preferences.enums.BillStatusEnum;
import com.wiiy.common.preferences.enums.ParkStatusEnum;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.dao.RoomDao;
import com.wiiy.park.entity.CommonBill;
import com.wiiy.park.entity.Room;
import com.wiiy.park.entity.RoomAtt;
import com.wiiy.park.entity.RoomChangeLog;
import com.wiiy.park.entity.RoomFee;
import com.wiiy.park.entity.RoomMemo;
import com.wiiy.park.entity.RoomMonitor;
import com.wiiy.park.service.RoomService;

/**
 * @author my
 */
public class RoomServiceImpl implements RoomService{
	
	private RoomDao roomDao;
	
	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	@Override
	public Result<Room> save(Room t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			roomDao.save(t);
			return Result.success(R.Room.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Room.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Room> delete(Room t) {
		try {
			roomDao.delete(t);
			return Result.success(R.Room.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Room> deleteById(Serializable id) {
		try {
			roomDao.deleteById(id);
			return Result.success(R.Room.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Room> deleteByIds(String ids) {
		try {
			roomDao.deleteByIds(ids);
			return Result.success(R.Room.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Room> update(Room t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			roomDao.update(t);
			return Result.success(R.Room.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Room.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Room> getBeanById(Serializable id) {
		try {
			return Result.value(roomDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Room> getBeanByFilter(Filter filter) {
		try {
			return Result.value(roomDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Room>> getList() {
		try {
			return Result.value(roomDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Room>> getListByFilter(Filter filter) {
		try {
			return Result.value(roomDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Room.LOAD_FAILURE);
		}
	}

	@Override
	public boolean checkNameUnique(Long buildingId, String name) {
		return roomDao.getRowCount(new Filter(Room.class).eq("buildingId",
				buildingId).eq("name", name)) == 0;
	}

	@Override
	public boolean checkNameUnique(Long buildingId, String name, Long excludeId) {
		return roomDao.getRowCount(new Filter(Room.class)
				.eq("buildingId", buildingId).ne("id", excludeId)
				.eq("name", name)) == 0;
	}

	public int isImg(String name) {
		String imgeArray[] = { "bmp", "dib", "gif", "jfif", "jpe", "jpeg",
				"jpg", "png", "tif", "tiff", "ico" };
		String suffixName = name.substring(name.lastIndexOf("."));
		String endStr = suffixName.substring(1, suffixName.length())
				.toLowerCase();
		int flag = 0;
		if (suffixName != null) {
			for (int i = 0; i < imgeArray.length; i++) {
				if (imgeArray[i].equals(endStr)) {
					flag = 1;
					break;
				}
			}
		}
		return flag;
	}

	public String[] minus(String[] arr1, String[] arr2) {
		LinkedList<String> list = new LinkedList<String>();
		LinkedList<String> history = new LinkedList<String>();
		String[] longerArr = arr1;
		String[] shorterArr = arr2;
		// 找出较长的数组来减较短的数组
		if (arr1.length > arr2.length) {
			longerArr = arr2;
			shorterArr = arr1;
		}
		for (String str : longerArr) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : shorterArr) {
			if (list.contains(str)) {
				history.add(str);
				list.remove(str);
			} else {
				if (!history.contains(str)) {
					list.add(str);
				}
			}
		}

		String[] result = {};
		return list.toArray(result);
	}

	@Override
	public Result<List<Room>> getListByHql(String hql) {
		try {
			return Result.value(roomDao.getListByHql(hql));
		} catch (Exception e) {
			return Result.failure(R.Room.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Room> save(Room t, List<RoomAtt> atts) {
		Session session = null;
		Transaction tr = null;
		try {
			session = roomDao.openSession();
			tr = session.beginTransaction();
			setModifyAndCreate(t);
			session.save(t);
			for (RoomAtt roomAtt : atts) {
				roomAtt.setRoomId(t.getId());
				setModifyAndCreate(roomAtt);
				session.save(roomAtt);
			}
			tr.commit();
			return Result.success(R.Room.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Room.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Room.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	private void setModifyAndCreate(BaseEntity t){
		t.setCreateTime(new Date());
		t.setCreator(ProjectActivator.getSessionUser().getRealName());
		t.setCreatorId(ProjectActivator.getSessionUser().getId());
		setModify(t);
		t.setEntityStatus(EntityStatus.NORMAL);
	}
	
	private void setModify(BaseEntity t){
		t.setModifyTime(t.getCreateTime());
		t.setModifier(t.getCreator());
		t.setModifierId(t.getCreatorId());
	}

	@Override
	public Result update(Room t, List<RoomAtt> atts) {
		Session session = null;
		Transaction tr = null;
		try {
			session = roomDao.openSession();
			tr = session.beginTransaction();
			setModify(t);
			session.update(t);
			String sql = "DELETE FROM park_room_att where room_id="+t.getId();
			session.createSQLQuery(sql).executeUpdate();
			for (RoomAtt roomAtt : atts) {
				roomAtt.setRoomId(t.getId());
				setModifyAndCreate(roomAtt);
				session.save(roomAtt);
			}
			tr.commit();
			return Result.success(R.Room.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Room.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Room.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	@Override
	public List<Object> getListBySql(String sql) {
		return roomDao.getObjectListBySql(sql);
	}

	@Override
	public Result<List<CustomerDto>> getCustomerList(Long id) {
		List<CustomerDto> list = new ArrayList<CustomerDto>();
		/*select cust.id,cust.name,cust.`code`,cust.park_status,cust.incubated,cont.id,r.id from business_business_customer cust 
		LEFT JOIN business_business_contract cont 
		on cust.id = cont.customer_id 
		LEFT JOIN project_subject_rent sr 
		on sr.contract_id = cont.id 
		LEFT JOIN project_room r 
		on sr.room_id = r.id 
		where cont.state = 'EXECUTE' and r.id=4*/
		String sql = "select cust.id id,cust.name,cust.code,cust.park_status,cust.incubated,cont.id cId,r.id rId from business_business_customer cust ";
		sql += "LEFT JOIN business_business_contract cont ";
		sql += "on cust.id = cont.customer_id ";
		sql += "LEFT JOIN business_business_subject_rent sr ";
		sql += "on sr.contract_id = cont.id ";
		sql += "LEFT JOIN park_room r ";
		sql += "on sr.room_id = r.id ";
		sql += "where cont.state = 'EXECUTE' and r.id="+id;
		
		Session session = null;
		try {
			session = roomDao.openSession();
			List<Object> objects = session.createSQLQuery(sql).list();
			for (Object object : objects) {
				Object[] obj = (Object[])object;
				CustomerDto dto = new CustomerDto();
				if(obj[0]!=null){
					dto.setId(Long.valueOf(obj[0].toString()));
				}
				if(obj[1]!=null){
					dto.setName(obj[1].toString());
				}
				if(obj[2]!=null){
					dto.setCode(obj[2].toString());
				}
				if(obj[3]!=null){
					dto.setParkStatus(ParkStatusEnum.valueOf((obj[3].toString())));
				}
				if(obj[4]!=null){
					dto.setIncubated(BooleanEnum.valueOf((obj[4].toString())));
				}
				list.add(dto);
			}
			return Result.value(list);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public Result getRowCount(Filter filter) {
		try {
			return Result.value(roomDao.getRowCount(filter));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Result<Room> mergeRoom(Room room1, Room room2, String newName) {
		Session session = null;
		Transaction tr = null;
		try {
		/*	String name1 = room1.getName();
			String name2 = room2.getName();
			session = roomDao.openSession();
			tr = session.beginTransaction();
			List<SubjectRent> subjectRents = session.createQuery(
					"from SubjectRent where roomId = " + room1.getId()).list();
			List<SubjectRent> subjectRents2 = session.createQuery(
					"from SubjectRent where roomId = " + room2.getId()).list();
			if (subjectRents != null && subjectRents.size() > 0) {
				for (SubjectRent subjectRent : subjectRents) {
					if (subjectRent.getEndDate().getTime() < (new Date())
							.getTime()) {
					}else{
						return Result.failure("租赁合同中有房间【"+room1.getName()+"】相关联数据 请先处理");
					}
				}
			}
			
			if (subjectRents2 != null && subjectRents2.size() > 0) {
				for (SubjectRent subjectRent : subjectRents2) {
					if (subjectRent.getEndDate().getTime() < (new Date())
							.getTime()) {
						subjectRent.setRoomId(room1.getId());
						session.update(subjectRent);
					}else{
						return Result.failure("租赁合同中有房间【"+room2.getName()+"】相关联数据 请先处理");
					}
				}
			}
			List<BillPlanRent> billPlanRents  = session.createQuery(
					"from Bill where roomId = " + room1.getId()).list();
			if(billPlanRents!=null && billPlanRents.size()>0){
				for (BillPlanRent billPlanRent : billPlanRents) {
					if(billPlanRent.getStatus().equals(BillPlanStatusEnum.UNCHECK)){
						return Result.failure("房间【"+room1.getName()+"】中还有资金计划未出帐");
					}
				}
			}
			List<BillPlanRent> billPlanRents2  = session.createQuery(
					"from Bill where roomId = " + room2.getId()).list();
			if(billPlanRents2!=null && billPlanRents2.size()>0){
				for (BillPlanRent billPlanRent : billPlanRents2) {
					if(billPlanRent.getStatus().equals(BillPlanStatusEnum.UNCHECK)){
						return Result.failure("房间【"+room2.getName()+"】中还有资金计划未出帐");
					}else{
						billPlanRent.setRoomId(room1.getId());
					}
				}
			}
			List<CommonBill> bills = session.createQuery(
					"from Bill where roomId = " + room1.getId()).list();
			List<CommonBill> bills2 = session.createQuery(
					"from Bill where roomId = " + room2.getId()).list();
			if(bills!=null && bills.size()>0){
				for (CommonBill bill : bills) {
					if(bill.getStatus().equals(BillStatusEnum.UNPAID)){
						return Result.failure("房间【"+room1.getName()+"】中还有未结算账单 请先处理");
					}
				}
			}
			if(bills2!=null && bills2.size()>0){
				for (CommonBill bill : bills2) {
					if(bill.getStatus().equals(BillStatusEnum.UNPAID)){
						return Result.failure("房间【"+room2.getName()+"】中还有未结算账单 请先处理");
					}else{
						bill.setRoomId(room1.getId());
					}
				}
			}
			room1.setName(newName);
			if (room1.getBuildingArea() == null) {
				room1.setBuildingArea(BigDecimal.valueOf(0));
			}
			if (room2.getBuildingArea() == null) {
				room2.setBuildingArea(BigDecimal.valueOf(0));
			}
			if (room1.getRealArea() == null) {
				room1.setRealArea(BigDecimal.valueOf(0));
			}
			if (room2.getRealArea() == null) {
				room2.setRealArea(BigDecimal.valueOf(0));
			}
			room1.setBuildingArea(room1.getBuildingArea().add(
					room2.getBuildingArea()));
			room1.setRealArea(room1.getRealArea().add(room2.getRealArea()));
			List<RoomAtt> roomAtts = session.createQuery(
					"from RoomAtt where roomId = " + room2.getId()).list();
			List<RoomMemo> roomMemos = session.createQuery(
					"from RoomMemo where roomId = " + room2.getId()).list();
			List<RoomChangeLog> roomChangeLogs = session.createQuery(
					"from RoomChangeLog where roomId = " + room2.getId())
					.list();
			List<RoomFee> roomFees2 = session.createQuery(
					"from RoomFee where roomId = " + room2.getId()).list();
			List<RoomFee> roomFees = session.createQuery(
					"from RoomFee where roomId = " + room1.getId()).list();
			List<RoomMonitor> roomMonitors = session.createQuery(
					"from RoomMonitor where roomId = " + room2.getId()).list();
			if (roomAtts != null && roomAtts.size() > 0) {
				for (RoomAtt roomAtt : roomAtts) {
					int flag = isImg(roomAtt.getName());
					if (flag == 1) {
						room1.setPhotos(roomAtt.getNewName() + ","
								+ room1.getPhotos());
					}
					roomAtt.setRoomId(room1.getId());
					session.update(roomAtt);
				}
			}
			if (roomMonitors != null && roomMonitors.size() > 0) {
				for (RoomMonitor roomMonitor : roomMonitors) {
					roomMonitor.setRoomId(room1.getId());
					session.update(roomMonitor);
				}
			}
			if (roomMemos != null && roomMemos.size() > 0) {
				for (RoomMemo roomMemo : roomMemos) {
					roomMemo.setRoomId(room1.getId());
					session.update(roomMemo);
				}
			}
			if (roomChangeLogs != null && roomChangeLogs.size() > 0) {
				for (RoomChangeLog roomChangeLog : roomChangeLogs) {
					roomChangeLog.setRoomId(room1.getId());
					session.update(roomChangeLog);
				}
			}
			Map<RentBillPlanFeeEnum, RoomFee> map = new HashMap<RentBillPlanFeeEnum, RoomFee>();
			if (roomFees2 != null && roomFees2.size() > 0) {
				for (RoomFee roomFee : roomFees2) {
					roomFee.setRoomId(room1.getId());
					session.update(roomFee);
					map.put(roomFee.getFeeType(), roomFee);
				}
			}
			for (RoomFee roomFee : roomFees) {
				if (map.get(roomFee.getFeeType()) != null) {
					RoomFee fee = map.get(roomFee.getFeeType());
					fee.setAmount(fee.getAmount() + roomFee.getAmount());
					session.update(fee);
					session.createQuery(
							"delete RoomFee where id = " + roomFee.getId())
							.executeUpdate();
				}
			}
			RoomChangeLog roomChangeLog = new RoomChangeLog();
			roomChangeLog.setContent(name1 + "与" + name2 + "合并为" + newName);
			roomChangeLog.setRoomId(room1.getId());
			roomChangeLog.setCreateTime(new Date());
			roomChangeLog
					.setCreator(ProjectActivator.getSessionUser().getRealName());
			roomChangeLog.setCreatorId(ProjectActivator.getSessionUser().getId());
			roomChangeLog.setModifyTime(room1.getCreateTime());
			roomChangeLog.setModifier(room1.getCreator());
			roomChangeLog.setModifierId(room1.getCreatorId());
			roomChangeLog.setEntityStatus(EntityStatus.NORMAL);
			roomChangeLog.setTypeId("pb.000801");
			session.save(roomChangeLog);
			session.update(room1);
			session.createQuery("delete Room where id = " + room2.getId())
					.executeUpdate();
			tr.commit();
			ProjectActivator.getOperationLogService().logLogout(
					name1 + "与" + name2 + "合并房间为【" + newName + "】");*/
			return Result.success("房间合并成功", room1);
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("房间合并失败");
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Room> roomBroken(Room room, String name1, String name2) {
		Session session = null;
		Transaction tr = null;
		try {
			session = roomDao.openSession();
			tr = session.beginTransaction();
			String name = room.getName();
			Room room2 = new Room();
			BeanUtil.copyProperties(room, room2);
			room2.setName(name2);
			room2.setCustomerName(null);
			room2.setCustomerId(null);
			room2.setEndDate(null);
			room2.setBuildingArea(BigDecimal.ZERO);
			room2.setRealArea(BigDecimal.ZERO);
			room2.setPhotos("");
			session.save(room2);
			room.setName(name1);
			session.update(room);
			RoomChangeLog roomChangeLog = new RoomChangeLog();
			roomChangeLog.setContent(room.getName() + "拆分为" + name1 + "与"
					+ name2);
			roomChangeLog.setRoomId(room.getId());
			roomChangeLog.setCreateTime(new Date());
			roomChangeLog
					.setCreator(ProjectActivator.getSessionUser().getRealName());
			roomChangeLog.setCreatorId(ProjectActivator.getSessionUser().getId());
			roomChangeLog.setModifyTime(room.getCreateTime());
			roomChangeLog.setModifier(room.getCreator());
			roomChangeLog.setModifierId(room.getCreatorId());
			roomChangeLog.setEntityStatus(EntityStatus.NORMAL);
			roomChangeLog.setTypeId("pb.000802");
			session.save(roomChangeLog);
			ProjectActivator.getOperationLogService().logLogout(
					"拆分房间【" + name + "】为" + room.getName() + "和"
							+ room2.getName());
			tr.commit();
			return Result.success("房间拆分成功");
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("房间拆分失败");
		} finally {
			session.close();
		}
	}

}

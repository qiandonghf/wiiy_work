package com.wiiy.estate.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.common.preferences.enums.MeterTypeEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.SelfMeterLabelDao;
import com.wiiy.estate.entity.Meter;
import com.wiiy.estate.entity.SelfLabelRecord;
import com.wiiy.estate.entity.SelfMeterLabel;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.SelfMeterLabelService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.ModulePrefixNamingStrategy;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.entity.Building;

public class SelfMeterLabelServiceImpl implements SelfMeterLabelService{
	private SelfMeterLabelDao selfMeterLabelDao;
	public void setSelfMeterLabelDao(SelfMeterLabelDao selfMeterLabelDao) {
		this.selfMeterLabelDao = selfMeterLabelDao;
	}

	@Override
	public Result<SelfMeterLabel> save(SelfMeterLabel t) {
		Session session = null;
		Transaction tr = null;
		try{
			session = selfMeterLabelDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			
			if(t.getBuildingIds()!=null){
				String ids = t.getBuildingIds();
				if(ids.endsWith(",")){
					ids = ids.substring(0,ids.length()-1);
				}
				
				String sql = "";
				sql = "SELECT m.id, m.pre_reading, m.reading_date,m.room_ids,m.orderNo,m.type,m.meter_factor,m.building_id,m.park_id FROM "+ModulePrefixNamingStrategy.classToTableName(Meter.class)+" m " +
						"WHERE m.building_id in ("+ids+") AND m.kind='SELF' AND m.type='"+t.getType()+"' " +
						"GROUP BY m.id, m.pre_reading, m.reading_date,m.room_ids,m.orderNo,m.type,m.meter_factor,m.building_id,m.park_id";
				List<Object> list = new ArrayList<Object>();
				list = selfMeterLabelDao.getObjectListBySql(sql);
				for (Object object : list) {
					Object[] objects = (Object[])object;
					Long meterId =  Long.parseLong(String.valueOf(objects[0]));
					Double preReading = null;
					if(String.valueOf(objects[1])!="null"){
						 preReading = Double.parseDouble(String.valueOf(objects[1]));
					}
					Date readingDate = DateUtil.parse(String.valueOf(objects[2]));
					String roomIds = String.valueOf(objects[3]);
					String orderNo = String.valueOf(objects[4]);
					MeterTypeEnum type = MeterTypeEnum.valueOf(String.valueOf(objects[5]));
					Integer meterFactor = Integer.parseInt(String.valueOf(objects[6]));
					SelfLabelRecord mlr = new SelfLabelRecord();
					mlr.setLabelId(t.getId());
					mlr.setMeterId(meterId);
					mlr.setPreReading(preReading);
					mlr.setPreDate(readingDate);
					mlr.setMeterOrderNo(orderNo);
					mlr.setMeterType(type);
					mlr.setMeterFactor(meterFactor);
					mlr.setCurDate(new Date());
					
					Meter meter = (Meter) session.get(Meter.class, meterId);
					meter.setReadingDate(new Date());
					session.merge(meter);
					
					String bId = String.valueOf(objects[7]);
					if(bId!="null"){
						Long buildingId = Long.parseLong(String.valueOf(objects[7]));
						if(buildingId!=null){
							Building building = (Building)session.load(Building.class, buildingId);
							mlr.setBuildingId(buildingId);
							mlr.setBuildingName(building.getPark().getName()+"-"+building.getName());
						}
					}
					mlr.setCreateTime(new Date());
					mlr.setCreator(EstateActivator.getSessionUser().getRealName());
					mlr.setCreatorId(EstateActivator.getSessionUser().getId());
					mlr.setModifyTime(t.getCreateTime());
					mlr.setModifier(t.getCreator());
					mlr.setModifierId(t.getCreatorId());
					mlr.setEntityStatus(EntityStatus.NORMAL);
					session.save(mlr);
				}
			}
			tr.commit();
			return Result.success(R.SelfMeterLabel.SAVE_SUCCESS, t);
		}catch (Exception e) {
			tr.rollback();
			return Result.failure(R.SelfMeterLabel.SAVE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result<SelfMeterLabel> update(SelfMeterLabel t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			selfMeterLabelDao.update(t);
			return Result.success(R.SelfMeterLabel.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SelfMeterLabel.class)));
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.UPDATE_FAILURE);
		}
	}
	
	@Override
	public Result<SelfMeterLabel> delete(SelfMeterLabel t) {
		try {
			selfMeterLabelDao.delete(t);
			return Result.success(R.SelfMeterLabel.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SelfMeterLabel> deleteById(Serializable id) {
		try {
			selfMeterLabelDao.deleteById(id);
			return Result.success(R.SelfMeterLabel.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SelfMeterLabel> deleteByIds(String ids) {
		try {
			selfMeterLabelDao.deleteByIds(ids);
			return Result.success(R.SelfMeterLabel.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SelfMeterLabel> getBeanByFilter(Filter filter) {
		try {
			return Result.value(selfMeterLabelDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SelfMeterLabel> getBeanById(Serializable id) {
		try {
			return Result.value(selfMeterLabelDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SelfMeterLabel>> getList() {
		try {
			return Result.value(selfMeterLabelDao.getList());
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SelfMeterLabel>> getListByFilter(Filter filter) {
		try {
			return Result.value(selfMeterLabelDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.SelfMeterLabel.LOAD_FAILURE);
		}
	}

	@Override
	public void updateRecord(Long id, String oldIds, String newIds) {
		Session session = null;
		Transaction tr = null;
		try{
			session = selfMeterLabelDao.openSession();
			tr = session.beginTransaction();
			List<String> oldList = Arrays.asList(oldIds.split(","));
			List<String> newList = Arrays.asList(newIds.split(","));
			String deleteIds = "";
			for(String oldId : oldList){
				if(!newList.contains(oldId)){
					deleteIds += oldId+",";
				}
			}
			String addIds = "";
			for(String newId : newList){
				if(!oldList.contains(newId)){
					addIds += newId+",";
				}
			}
			String sql = "";
			if(deleteIds!=""){
				if(deleteIds.endsWith(",")){
					deleteIds = deleteIds.substring(0,deleteIds.length()-1);
				}
				
				sql = "DELETE FROM "+ModulePrefixNamingStrategy.classToTableName(SelfLabelRecord.class)+
				" WHERE building_id in ("+deleteIds+")";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			if(addIds!=""){
				if(addIds.endsWith(",")){
					addIds = addIds.substring(0,addIds.length()-1);
				}
				sql = "SELECT m.id, m.pre_reading, m.reading_date,m.room_ids,m.orderNo,m.type,m.meter_factor,m.building_id,m.park_id FROM "+ModulePrefixNamingStrategy.classToTableName(Meter.class)+" m " +
						"WHERE m.building_id in ("+addIds+") AND m.kind='SELF' " +
						"GROUP BY m.id, m.pre_reading, m.reading_date,m.room_ids,m.orderNo,m.type,m.meter_factor,m.building_id,m.park_id";
				List<Object> list = new ArrayList<Object>();
				list = selfMeterLabelDao.getObjectListBySql(sql);
				for (Object object : list) {
					Object[] objects = (Object[])object;
					Long meterId =  Long.parseLong(String.valueOf(objects[0]));
					Double preReading = null;
					if(String.valueOf(objects[1])!="null"){
						 preReading = Double.parseDouble(String.valueOf(objects[1]));
					}
					Date readingDate = DateUtil.parse(String.valueOf(objects[2]));
					String roomIds = String.valueOf(objects[3]);
					String orderNo = String.valueOf(objects[4]);
					MeterTypeEnum type = MeterTypeEnum.valueOf(String.valueOf(objects[5]));
					Integer meterFactor = Integer.parseInt(String.valueOf(objects[6]));
					SelfLabelRecord mlr = new SelfLabelRecord();
					mlr.setLabelId(id);
					mlr.setMeterId(meterId);
					mlr.setPreReading(preReading);
					mlr.setPreDate(readingDate);
					mlr.setMeterOrderNo(orderNo);
					mlr.setMeterType(type);
					mlr.setMeterFactor(meterFactor);
					mlr.setCurDate(new Date());
					
					Meter meter = (Meter) session.get(Meter.class, meterId);
					meter.setReadingDate(new Date());
					session.merge(meter);
					
					String bId = String.valueOf(objects[7]);
					if(bId!="null"){
						Long buildingId = Long.parseLong(String.valueOf(objects[7]));
						if(buildingId!=null){
							Building building = (Building)session.load(Building.class, buildingId);
							mlr.setBuildingId(buildingId);
							mlr.setBuildingName(building.getPark().getName()+"-"+building.getName());
						}
					}
					mlr.setCreateTime(new Date());
					mlr.setCreator(EstateActivator.getSessionUser().getRealName());
					mlr.setCreatorId(EstateActivator.getSessionUser().getId());
					mlr.setModifyTime(mlr.getCreateTime());
					mlr.setModifier(mlr.getCreator());
					mlr.setModifierId(mlr.getCreatorId());
					mlr.setEntityStatus(EntityStatus.NORMAL);
					session.save(mlr);
				}
			}
			tr.commit();
		}catch (Exception e) {
			tr.rollback();
		}finally{
			session.close();
		}
	}


}

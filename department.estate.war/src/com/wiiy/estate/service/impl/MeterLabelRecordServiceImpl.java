package com.wiiy.estate.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.entity.BusinessCustomer;
import com.wiiy.business.service.CustomerService;
import com.wiiy.common.preferences.enums.BillInOutEnum;
import com.wiiy.common.preferences.enums.BillStatusEnum;
import com.wiiy.common.preferences.enums.EstateFeeEnum;
import com.wiiy.common.preferences.enums.MeterEleFeeEnum;
import com.wiiy.common.preferences.enums.MeterTypeEnum;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.MeterLabelRecordDao;
import com.wiiy.estate.dto.FeeStatementsDto;
import com.wiiy.estate.entity.EstateContract;
import com.wiiy.estate.entity.EstateSubjectRent;
import com.wiiy.estate.entity.Meter;
import com.wiiy.estate.entity.MeterLabel;
import com.wiiy.estate.entity.MeterLabelRecord;
import com.wiiy.estate.entity.MeterLabelReport;
import com.wiiy.estate.entity.RoomMeterFee;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.preferences.enums.MeterRecordStatusEnum;
import com.wiiy.estate.service.MeterLabelRecordService;
import com.wiiy.estate.service.MeterLabelService;
import com.wiiy.estate.service.MeterService;
import com.wiiy.estate.service.RoomMeterFeeService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.ModulePrefixNamingStrategy;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.entity.BillType;
import com.wiiy.park.entity.Building;
import com.wiiy.park.entity.CommonBill;
import com.wiiy.park.entity.Park;
import com.wiiy.park.entity.Room;
import com.wiiy.park.service.BillService;
import com.wiiy.park.service.BillTypeService;
import com.wiiy.park.service.ParkService;

public class MeterLabelRecordServiceImpl implements MeterLabelRecordService{
	private MeterLabelRecordDao meterLabelRecordDao;
	private MeterLabelService meterLabelService;
	private MeterService meterService;
	private ParkService parkService;
	private BillService billService;
	private BillTypeService billTypeService;
	private CustomerService customerService;
	private RoomMeterFeeService roomMeterFeeService;
	public void setRoomMeterFeeService(RoomMeterFeeService roomMeterFeeService) {
		this.roomMeterFeeService = roomMeterFeeService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setBillTypeService(BillTypeService billTypeService) {
		this.billTypeService = billTypeService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public void setParkService(ParkService parkService) {
		this.parkService = parkService;
	}

	public void setMeterService(MeterService meterService) {
		this.meterService = meterService;
	}

	public void setMeterLabelService(MeterLabelService meterLabelService) {
		this.meterLabelService = meterLabelService;
	}

	public void setMeterLabelRecordDao(MeterLabelRecordDao meterLabelRecordDao) {
		this.meterLabelRecordDao = meterLabelRecordDao;
	}

	@Override
	public Result<MeterLabelRecord> save(MeterLabelRecord t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			meterLabelRecordDao.save(t);
			return Result.success(R.MeterLabelRecord.SAVE_SUCCESS, t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),MeterLabelRecord.class)));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.SAVE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelRecord> update(MeterLabelRecord t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			meterLabelRecordDao.update(t);
			return Result.success(R.MeterLabelRecord.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),MeterLabelRecord.class)));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.UPDATE_FAILURE);
		}
	}
	
	@Override
	public Result<MeterLabelRecord> delete(MeterLabelRecord t) {
		try {
			meterLabelRecordDao.delete(t);
			return Result.success(R.MeterLabelRecord.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.DELETE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelRecord> deleteById(Serializable id) {
		try {
			meterLabelRecordDao.deleteById(id);
			return Result.success(R.MeterLabelRecord.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.DELETE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelRecord> deleteByIds(String ids) {
		try {
			meterLabelRecordDao.deleteByIds(ids);
			System.out.println(ids);
			return Result.success(R.MeterLabelRecord.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.DELETE_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelRecord> getBeanByFilter(Filter filter) {
		try {
			return Result.value(meterLabelRecordDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.LOAD_FAILURE);
		}
	}

	@Override
	public Result<MeterLabelRecord> getBeanById(Serializable id) {
		try {
			return Result.value(meterLabelRecordDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<MeterLabelRecord>> getList() {
		try {
			return Result.value(meterLabelRecordDao.getList());
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<MeterLabelRecord>> getListByFilter(Filter filter) {
		try {
			return Result.value(meterLabelRecordDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.MeterLabelRecord.LOAD_FAILURE);
		}
	}

	@Override
	public Result addMeter(Long labelId, List<String> existList, List<String> newList) {
		Session session = null;
		Transaction tr = null;
		try{
			session = meterLabelRecordDao.openSession();
			tr = session.beginTransaction();
			String sql = "";
			String addIds = "";
			String delIds = "";
			for (String id : newList) {
				if(!existList.contains(id)){
					addIds += id + ",";
				}
			}
			if(addIds.endsWith(",")){
				addIds = addIds.substring(0,addIds.length()-1);
			}
			
			for(String oldId : existList){
				if(!newList.contains(oldId)){
					delIds += oldId+",";
				}
			}
			if(delIds.endsWith(",")){
				delIds = delIds.substring(0,delIds.length()-1);
			}
			
			if(addIds.length()>0){
			sql = "SELECT m.id, m.pre_reading, m.reading_date,m.room_ids,m.orderNo,m.type,m.meter_factor,m.building_id,m.park_id  FROM "+ModulePrefixNamingStrategy.classToTableName(Meter.class)+" m " +
					"WHERE m.id in ("+addIds+")" +
					"GROUP BY m.id, m.pre_reading, m.reading_date,m.room_ids,m.orderNo,m.type,m.meter_factor,m.building_id,m.park_id ";
			List<Object> list = new ArrayList<Object>();
			list = meterLabelRecordDao.getObjectListBySql(sql);
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
				
				MeterLabelRecord mlr = new MeterLabelRecord();
				mlr.setLabelId(labelId);
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
				
				if(!roomIds.equals(",") ){
					Room room = (Room) session.load(Room.class,Long.parseLong(roomIds.split(",")[1]));
					mlr.setCustomerName(room.getCustomerName());
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
			if(delIds.length()>0){
				sql = "DELETE FROM "+ModulePrefixNamingStrategy.classToTableName(MeterLabelRecord.class)+
						" WHERE meter_id in ("+delIds+")";
						session.createSQLQuery(sql).executeUpdate();
			}
			tr.commit();
			return Result.success("操作成功");
		}catch (Exception e) {
			tr.rollback();
			return Result.failure("操作失败");
		}finally{
			session.close();
		}
	}
	
	/**
	 * 生成费用报表 MeterLabelReport
	 * 创建多条记录 MeterLabelReport
	 * 以企业为维度查询对应企业下所有水电表
		创建水电报表记录（通过房间连接企业跟水电表）
		企业 水电表 读数 
		
		查询水电表，获取房间ids
		
		查询对应房间获取企业name 跟水电表对应 多对多 一家企业对应多个水电表，一个水电表对应多个企业
		
		根据水电表查询抄表信息 
		
		插入数据

	 * 
	 */
	@Override
	public Result<List<MeterLabelReport>> generateReport(Long labelId,boolean isSetFee){
		List<MeterLabelReport> list = new ArrayList<MeterLabelReport>();
		Session session = null;
		Transaction tr = null;
		try {
			session = meterLabelRecordDao.openSession();
			tr = session.beginTransaction();
			
			//重新生成
			List<MeterLabelReport> labelReports = session.createQuery("from MeterLabelReport where labelId ="+labelId).list();
			for (MeterLabelReport meterLabelReport : labelReports) {
				session.delete(meterLabelReport);
			}
			
			List<MeterLabelRecord> mrList = meterLabelRecordDao.getListByFilter(new Filter(MeterLabelRecord.class).eq("labelId", labelId));
			 
			Map<Long,MeterLabelRecord> mrMap = new HashMap<Long, MeterLabelRecord>();
			
			
			//获取水电表
			if(mrList!=null && mrList.size()>0){
				for (MeterLabelRecord meterLabelRecord : mrList) {
					mrMap.put(meterLabelRecord.getMeterId(), meterLabelRecord);
				}
			}
			
			List l=session.createSQLQuery("select meter_id,room_id,ratio from estate_room_meter_fee where meter_id " 
						+"in(select meter_id from estate_meter_label_record where label_id ="+labelId+")").list();
			//可以去到meterid与roomid的映射关系 1对多的
			//获取房间ids
			Map<Long,Set<Long>> meterRoomMap = new HashMap<Long, Set<Long>>();
			if(l!=null){
				for(int i=0;i<l.size();i++){
					Object[] o=(Object[])l.get(i);
					Long meter_id=((BigInteger)o[0]).longValue();
					Long room_id=((BigInteger)o[1]).longValue();
					//Long ratio=((BigInteger)o[2]).longValue();
					Set<Long> rooms=meterRoomMap.get(meter_id);
					if(rooms==null){
						rooms=new HashSet<Long>();
						rooms.add(room_id);
						//rooms.add(ratio);
						meterRoomMap.put(meter_id, rooms);
					}
					else{
						rooms.add(room_id);
					}
				}
			}
			
			List l1=session.createSQLQuery("select distinct room_id,customer_id,customer_name from estate_estate_subject_rent  t1"
						+" left outer join estate_estate_contract  t2"
						+" on t1.contract_id=t2.id"
						+" where room_id in(select distinct room_id from estate_room_meter_fee where meter_id"
						+" in(select meter_id from estate_meter_label_record where label_id ="+labelId+"))").list();
			//可以去到room_id与客户的映射关系 1对多的
			
			//查询room
			Map<Long,Set<BusinessCustomer>> roomCustomerMap = new HashMap<Long, Set<BusinessCustomer>>();
			if(l1!=null){
				for(int i=0;i<l1.size();i++){
					Object[] o=(Object[])l1.get(i);
					Long room_id=((BigInteger)o[0]).longValue();
					Long customer_id=((BigInteger)o[1]).longValue();
					String customer_name=(String)o[2];
					Set<BusinessCustomer> customerSet=roomCustomerMap.get(room_id);
					if(customerSet==null){
						customerSet=new HashSet<BusinessCustomer>();
						roomCustomerMap.put(room_id, customerSet);
					}
					BusinessCustomer customer = new BusinessCustomer();
					customer.setId(customer_id);
					customer.setName(customer_name);
					customerSet.add(customer);
				}
			}

			MeterLabel ml = meterLabelService.getBeanById(labelId).getValue();
			ml.setCheckOut(MeterRecordStatusEnum.GENERATED);
			session.update(ml);
			//生成报表记录
			if(meterRoomMap.size()>0){
				for (Long meterId : meterRoomMap.keySet()) {
					Set<Long> rooms = meterRoomMap.get(meterId);
					for (Long roomId : rooms) {
						if(roomCustomerMap.size() >0){
							Set<BusinessCustomer> cSet = roomCustomerMap.get(roomId);
							for (BusinessCustomer c : cSet) {
								MeterLabelReport report = new MeterLabelReport();
								MeterLabelRecord record = mrMap.get(meterId);
								if(record!=null){
									report.setLabelRecordId(record.getId());
								}
								report.setCheckOut(MeterRecordStatusEnum.GENERATED);
								report.setCustomerName(c.getName());
								report.setMeterId(meterId);
								report.setLabelId(labelId);
								report.setStartTime(ml.getStartTime());
								report.setEndTime(ml.getEndTime());
								report.setType(ml.getType());
								report.setCheckOut(ml.getCheckOut());
								session.save(report);
							}
						}
					}
				}
			}
			tr.commit();
			return Result.success("操作成功！",list);
		}catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("操作失败,请将信息输入完整后再试",list);
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}
	
	/**
	 * old
	 */
	@Override
	public Result<FeeStatementsDto> generateReport2(Long labelId,boolean isSetFee) {
		FeeStatementsDto fsDto = new FeeStatementsDto();
		Session session = null;
		Transaction tr = null;
		try {
			session = meterLabelRecordDao.openSession();
			tr = session.beginTransaction();
			List<Meter> meterList = new ArrayList<Meter>();
			if(isSetFee&&labelId!=null){
				MeterLabel ml = meterLabelService.getBeanById(labelId).getValue();
				if(labelId!=null){
					List<MeterLabelRecord> mrList = meterLabelRecordDao.getListByFilter(new Filter(MeterLabelRecord.class).eq("labelId", labelId));
					if(mrList!=null&&mrList.size()>0){
						for(MeterLabelRecord mr:mrList){
							Meter m = meterService.getBeanByFilter(new Filter(Meter.class).eq("orderNo", mr.getMeterOrderNo())).getValue();
							Park p = parkService.getBeanById(m.getParkId()).getValue();
							RoomMeterFee rmf = roomMeterFeeService.getBeanByFilter(new Filter(RoomMeterFee.class).eq("meterId", m.getId()).eq("priceType", "CUSTOMIZE")).getValue();
							Double elePrice=0.0;
							Double watPrice=0.0;
							if(rmf!=null){
								if(rmf.getType()==MeterTypeEnum.WATER){
									watPrice=rmf.getPrice();
									elePrice=p.getElectricity();
								}else{
									watPrice = p.getWater();
									elePrice=rmf.getPrice();
								}
							}else{
								elePrice = p.getElectricity();//电价
								watPrice = p.getWater();//水价
							}
							if(null== elePrice || 0 == elePrice || null == watPrice || 0== watPrice){
								return Result.failure("请设置水电费单价后重试。");
							}
							if(MeterTypeEnum.ELECTRICITY.equals(mr.getMeterType())&&isSetFee){
								mr.setPrice(elePrice);
								mr.setCheckOut(MeterRecordStatusEnum.GENERATED);
								mr.setBill(elePrice*mr.getTotalAmount());
								double totalBill=mr.getTotalAmount()*elePrice;
								if(m.getCapacity()!=null&&m.getCapacityBill()!=null){
									totalBill+=(m.getCapacity())+(m.getCapacityBill());
								}
								//double totalBill = (meterLableRecord.getTotalAmount())*elePrice+((meter.getCapacity())+(meter.getCapacityBill()));
								m.setReadingDate(mr.getCurDate());
								meterService.update(m);
								mr.setTotalBill(totalBill);
								meterLabelRecordDao.update(mr);
							}else if(MeterTypeEnum.WATER.equals(mr.getMeterType())&&isSetFee){
								mr.setPrice(watPrice);
								mr.setCheckOut(MeterRecordStatusEnum.GENERATED);
								double totalBill = (mr.getTotalAmount())*watPrice;
								mr.setTotalBill(totalBill);
								m.setReadingDate(mr.getCurDate());
								meterService.update(m);
								meterLabelRecordDao.update(mr);
							}
							meterList.add(m);
						}
						fsDto.setLabelRecords(mrList);
						fsDto.setMeters(meterList);
						fsDto.setStartTime(ml.getStartTime());
						fsDto.setEndTime(ml.getEndTime());
						fsDto.setLabelId(ml.getId());
						fsDto.setLabelType(ml.getType().toString());
						fsDto.setCheckOut(ml.getCheckOut().toString());
						tr.commit();
					}
				}
				ml.setCheckOut(MeterRecordStatusEnum.GENERATED);
				meterLabelService.update(ml);
				return Result.success("操作成功！",fsDto);
			}else{
				List<MeterLabelRecord> mrList = meterLabelRecordDao.getListByFilter(new Filter(MeterLabelRecord.class).eq("labelId", labelId));
				MeterLabel ml = meterLabelService.getBeanById(labelId).getValue();
				for(MeterLabelRecord mr : mrList){
					Meter m = meterService.getBeanByFilter(new Filter(Meter.class).eq("orderNo", mr.getMeterOrderNo())).getValue();
					meterList.add(m);
				}
				fsDto.setLabelRecords(mrList);
				fsDto.setMeters(meterList);
				fsDto.setStartTime(ml.getStartTime());
				fsDto.setEndTime(ml.getEndTime());
				fsDto.setLabelId(ml.getId());
				fsDto.setLabelType(ml.getType().toString());
				fsDto.setCheckOut(ml.getCheckOut().toString());
				return Result.success("成功",fsDto);
			}
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("操作失败,请将信息输入完整后再试",fsDto);
		}finally{
			session.close();
		}
	}
	
	@Override
	public Result waterEleFee(long labelId,String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			String idsStr="";
			session = meterLabelRecordDao.openSession();
			tr = session.beginTransaction();
			//Filter filter = new Filter(MeterLabelRecord.class).eq("labelId", labelId);
			MeterLabel meterLabel = meterLabelService.getBeanById(labelId).getValue();
			meterLabel.setCheckOut(MeterRecordStatusEnum.CKECKOUT);
			List<MeterLabelRecord> meterLabelRecordList=new ArrayList<MeterLabelRecord>();
			if(ids.length()>0){
				idsStr = ids.substring(0, ids.length()-1);
				meterLabelRecordList = session.createQuery("from MeterLabelRecord where id not in ("+idsStr+") and labelId="+labelId).list();
			}else{
				meterLabelRecordList = session.createQuery("from MeterLabelRecord where labelId="+labelId).list();
			}
			String number = billService.generateNumber(session);
			int num = Integer.valueOf(number.substring(2, number.length()-1));
			BillType topType = billTypeService.getBillType("物业").getValue();
			Map<EstateFeeEnum,Long> typeMap = new HashMap<EstateFeeEnum,Long>();
			for (EstateFeeEnum meterEleFeeEnum : EstateFeeEnum.values()) {
				BillType billType = new BillType();
				billType.setTypeName(meterEleFeeEnum.getTitle());
				billType.setParentId(topType.getId());
				billTypeService.checkBillType(billType);
				typeMap.put(meterEleFeeEnum, billType.getId());
			}
			for(MeterLabelRecord meterLableRecord : meterLabelRecordList){
				meterLableRecord.setCheckOut(MeterRecordStatusEnum.CKECKOUT);
				session.update(meterLableRecord);
				BusinessCustomer c = customerService.getBeanByFilter(new Filter(BusinessCustomer.class).eq("name", meterLableRecord.getCustomerName())).getValue();
				Meter meter = meterService.getBeanByFilter(new Filter(Meter.class).eq("orderNo", meterLableRecord.getMeterOrderNo())).getValue();
				RoomMeterFee rmf = roomMeterFeeService.getBeanByFilter(new Filter(RoomMeterFee.class).eq("meterId", meter.getId())).getValue();
				CommonBill bill = new CommonBill();
				StringBuilder sb = new StringBuilder(); 
				String billNumber = sb.append("账单").append(String.valueOf(num)).append("号").toString();
				bill.setNumber(billNumber);
				bill.setBillSplit(null);
				if(MeterTypeEnum.WATER.equals(meterLableRecord.getMeterType())){
					bill.setBillTypeId(typeMap.get(MeterEleFeeEnum.WATER));
				}else{
					bill.setBillTypeId(typeMap.get(MeterEleFeeEnum.ELECTRICITY));
				}
				bill.setCustomerId(c.getId());
				bill.setRoomId(rmf.getRoomId());
				bill.setBillPlanId(meterLableRecord.getId());
				bill.setBillPlanTableName(ModulePrefixNamingStrategy.classToTableName(MeterLabelRecord.class));
				bill.setPrice(meterLableRecord.getPrice().toString());
				bill.setAmount(meterLableRecord.getTotalAmount());
				bill.setContractNo(null);
				bill.setInvoice(BooleanEnum.NO);
				bill.setUrged(BooleanEnum.NO);
				bill.setPayType(null);
				bill.setTotalAmount(meterLableRecord.getTotalBill());
				bill.setFactPayment(meterLableRecord.getTotalBill());
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				int day =Integer.valueOf(EstateActivator.getAppConfig().getConfig("contractDueRemindDays").getParameter("day"));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_YEAR, day);
				
				bill.setPlanPayTime(calendar.getTime());
				bill.setPayTime(null);
				bill.setCheckoutTime(new Date());
				bill.setPenalty(0d);
				bill.setStatus(BillStatusEnum.UNPAID);
				bill.setInOut(BillInOutEnum.IN);
				bill.setDiscountRate(meterLableRecord.getTotalBill());
				bill.setFeeStartTime(meterLabel.getStartTime());
				bill.setFeeEndTime(meterLabel.getEndTime());
				bill.setMemo(null);
				num++;
				setcreatemodify(bill);
				session.save(bill);
			}
			
			meterLabelService.update(meterLabel);
			tr.commit();
			return Result.success("出账成功");
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("出账失败");
		}finally{
			session.close();
		}
	}

	public void setcreatemodify(BaseEntity t){
		t.setCreateTime(new Date());
		t.setCreator(EstateActivator.getSessionUser().getRealName());
		t.setCreatorId(EstateActivator.getSessionUser().getId());
		t.setModifyTime(t.getCreateTime());
		t.setModifier(t.getCreator());
		t.setModifierId(t.getCreatorId());
		t.setEntityStatus(EntityStatus.NORMAL);
	}

	@Override
	public Result printFee(long labelId) {
		FeeStatementsDto fsDto = new FeeStatementsDto();
		Session session = null;
		Transaction tr = null;
		try {
			session = meterLabelRecordDao.openSession();
			tr=session.beginTransaction();
			Filter filter = new Filter(MeterLabelRecord.class).eq("labelId", labelId);
			MeterLabel meterLabel = meterLabelService.getBeanById(labelId).getValue();
			List<MeterLabelRecord> meterLabelRecordList = meterLabelRecordDao.getListByFilter(filter);
			List<Meter> meters = new ArrayList<Meter>();
			for(MeterLabelRecord meterLableRecord : meterLabelRecordList){
				Meter meter = meterService.getBeanByFilter(new Filter(Meter.class).eq("orderNo", meterLableRecord.getMeterOrderNo())).getValue();
				meters.add(meter);
			}
			fsDto.setLabelRecords(meterLabelRecordList);
			fsDto.setMeters(meters);
			fsDto.setStartTime(meterLabel.getStartTime());
			fsDto.setEndTime(meterLabel.getEndTime());
			fsDto.setLabelId(meterLabel.getId());
			fsDto.setLabelType(meterLabel.getType().toString());
			tr.commit();
			return Result.value(fsDto);
		} catch (Exception e) {
			tr.rollback();
			return Result.failure("操作失败");
		}finally{
			session.close();
		}
	}

	/**
	 * 
	 */
	@Override
	public Result labelReport(Long labelId, boolean b) {
		Session session = null;
		try{
			session = meterLabelRecordDao.openSession();
			List<MeterLabelReport> list = session.createQuery("from MeterLabelReport where labelId = "+labelId).list();
			return Result.value(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 按照物业合同生成水电表费报表(non-Javadoc)
	 */
	/*
	public Result<List<MeterLabelReport>> generateReport3(Long labelId,boolean isSetFee) {
		Session session = null;
		Transaction ts = null;
		List<MeterLabelRecord> lableRecord = new ArrayList<MeterLabelRecord>();
		try {
			session = meterLabelRecordDao.openSession();
			ts = session.beginTransaction();
			
			//查询水电表id
			String hql = "from MeterLabelRecord where labelId="+labelId;
			lableRecord = session.createQuery(hql).list();
			Map<Long,MeterLabelRecord> mrMap = new HashMap<Long, MeterLabelRecord>();
			
			//获取水电表
			List<RoomMeterFee> metersFee = null;
			if(lableRecord!=null && lableRecord.size()>0){
				//水电表id
				String meterIds = "";
				for (MeterLabelRecord Record : lableRecord) {
					meterIds += Record.getMeterId()+",";
					mrMap.put(key, value)
				}
				meterIds = meterIds.substring(0,meterIds.length()-1);
				//根据水电表id获取水电表费用
				metersFee = (List<RoomMeterFee>) session.createQuery("from RoomMeterFee where id in ("+meterIds+")").list();
			}
			
			//根据roomId查询出合同
			List<EstateSubjectRent> estateRent = new ArrayList<EstateSubjectRent>();
			if(metersFee!=null && metersFee.size()>0){
				String roomId = "";
				for(RoomMeterFee meterFee : metersFee){
					roomId += meterFee.getRoomId()+",";
				}
				String hql1 = "from EstateSubjectRent where roomId in ("+roomId+")";
				estateRent = session.createQuery(hql1).list();
			}
			
			//根据合同id查询客户id
			List<EstateContract> estateContracts = new ArrayList<EstateContract>();
			if(estateRent!=null && estateRent.size()>0){
				String contractId = "";
				for(EstateSubjectRent subjectRent : estateRent){
					contractId += subjectRent.getContractId()+",";
				}
				estateContracts = session.createQuery("from EstateContract where id in ("+contractId+")").list();
			}
			
			//根据客户id查询客户
			List<Room> roomCustomer = new ArrayList<Room>();
			if(estateContracts!=null && estateContracts.size()>0){
				String customerId = "";
				for(EstateContract contract : estateContracts){
					customerId += contract+",";
				}
				roomCustomer = session.createQuery("from Room where customerId in ("+customerId+")").list();
			}
			
			//分户表抄表
			MeterLabel ml = meterLabelService.getBeanById(labelId).getValue();
			ml.setCheckOut(MeterRecordStatusEnum.GENERATED);
			session.update(ml);
			
			//生成报表记录
			if(roomCustomer!=null && roomCustomer.size()>0){
				String customerName = "";
				for(Room customer : roomCustomer){
					customerName += customer+",";
				}
				for (String key : roomCustomer) {
					List<Long> meterIds = customerMap.get(key);
					for (Long meterId : meterIds) {
						MeterLabelReport meterLabelReport = new MeterLabelReport();
						
						MeterLabelRecord record = mrMap.get(meterId);
						if(record!=null){
							meterLabelReport.setLabelRecordId(record.getId());
						}
						meterLabelReport.setCheckOut(MeterRecordStatusEnum.GENERATED);
						meterLabelReport.setCustomerName(key);
						meterLabelReport.setMeterId(meterId);
						meterLabelReport.setLabelId(labelId);
						meterLabelReport.setStartTime(ml.getStartTime());
						meterLabelReport.setEndTime(ml.getEndTime());
						meterLabelReport.setType(ml.getType());
						meterLabelReport.setCheckOut(ml.getCheckOut());
						session.save(meterLabelReport);
						list.add(meterLabelReport);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}*/
}

package com.wiiy.sale.entity;

import java.io.Serializable;

import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.User;
import com.wiiy.sale.preferences.enums.RentEnum;
import com.wiiy.common.preferences.enums.SubscribeEnum;

/**
 * <br/>class-description 预订
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Residential extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 销售顾问
	 */
	@FieldDescription("销售顾问")
	private User user;
	/**
	 * 客户名称
	 */
	@FieldDescription("客户名称")
	private String name;
	/**
	 * 线索外键
	 */
	@FieldDescription("线索外键")
	private Long contectInfoId;
	/**
	 * 线索名称
	 */
	@FieldDescription("线索名称")
	private String contectInfoName;
	/**
	 * 业务编号
	 */
	@FieldDescription("业务编号")
	private String saleCode;
	/**
	 * 预订编号
	 */
	@FieldDescription("预订编号")
	private String bookCode;
	/**
	 * 房间外键
	 */
	@FieldDescription("房间外键")
	private Long roomId;
	/**
	 * 房间编号
	 */
	@FieldDescription("房间编号")
	private String roomCode;
	/**
	 * 房屋名
	 */
	@FieldDescription("房屋名")
	private String roomName;
	/**
	 * 销售单价
	 */
	@FieldDescription("销售单价")
	private Double saleUnit;
	/**
	 * 销售面积
	 */
	@FieldDescription("销售面积")
	private Double saleArea;
	/**
	 * 销售价格
	 */
	@FieldDescription("销售价格")
	private Double salePrice;
	/**
	 * 预订金额
	 */
	@FieldDescription("预订金额")
	private Double reservationAmount;
	/**
	 * 身份证
	 */
	@FieldDescription("身份证")
	private String idCard;
	/**
	 * 移动电话
	 */
	@FieldDescription("移动电话")
	private String mobile;
	/**
	 * 工作电话
	 */
	@FieldDescription("工作电话")
	private String phone;
	/**
	 * 通讯地址
	 */
	@FieldDescription("通讯地址")
	private String adress;
	/**
	 * 邮政编号
	 */
	@FieldDescription("邮政编号")
	private String zipCode;
	/**
	 * 预订日期
	 */
	@FieldDescription("预订日期")
	private Date registratTime;
	/**
	 * 终止日期
	 */
	@FieldDescription("终止日期")
	private Date endTime;
	/**
	 * 到期是否可销售
	 */
	@FieldDescription("到期是否可销售")
	private BooleanEnum whetherSales;
	/**
	 * 是否同步到房间
	 */
	@FieldDescription("是否同步到房间")
	private BooleanEnum whetherRoom;
	/**
	 * 预订原因
	 */
	@FieldDescription("预订原因")
	private String reason;
	/**
	 * 销售顾问外键
	 */
	@FieldDescription("销售顾问外键")
	private Long userId;
	/**
	 * 状态
	 */
	@FieldDescription("状态")
	private RentEnum rentStatus;
	/**
	 * 预订类型
	 */
	@FieldDescription("预订类型")
	private SubscribeEnum subscribeType;
	/**
	 * 备注
	 */
	@FieldDescription("备注")
	private String memo;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 销售顾问
	 */
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	/**
	 * 客户名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 线索外键
	 */
	public Long getContectInfoId(){
		return contectInfoId;
	}
	public void setContectInfoId(Long contectInfoId){
		this.contectInfoId = contectInfoId;
	}
	/**
	 * 线索名称
	 */
	public String getContectInfoName(){
		return contectInfoName;
	}
	public void setContectInfoName(String contectInfoName){
		this.contectInfoName = contectInfoName;
	}
	/**
	 * 业务编号
	 */
	public String getSaleCode(){
		return saleCode;
	}
	public void setSaleCode(String saleCode){
		this.saleCode = saleCode;
	}
	/**
	 * 预订编号
	 */
	public String getBookCode(){
		return bookCode;
	}
	public void setBookCode(String bookCode){
		this.bookCode = bookCode;
	}
	/**
	 * 房间外键
	 */
	public Long getRoomId(){
		return roomId;
	}
	public void setRoomId(Long roomId){
		this.roomId = roomId;
	}
	/**
	 * 房间编号
	 */
	public String getRoomCode(){
		return roomCode;
	}
	public void setRoomCode(String roomCode){
		this.roomCode = roomCode;
	}
	/**
	 * 房屋名
	 */
	public String getRoomName(){
		return roomName;
	}
	public void setRoomName(String roomName){
		this.roomName = roomName;
	}
	/**
	 * 销售单价
	 */
	public Double getSaleUnit(){
		return saleUnit;
	}
	public void setSaleUnit(Double saleUnit){
		this.saleUnit = saleUnit;
	}
	/**
	 * 销售面积
	 */
	public Double getSaleArea(){
		return saleArea;
	}
	public void setSaleArea(Double saleArea){
		this.saleArea = saleArea;
	}
	/**
	 * 销售价格
	 */
	public Double getSalePrice(){
		return salePrice;
	}
	public void setSalePrice(Double salePrice){
		this.salePrice = salePrice;
	}
	/**
	 * 预订金额
	 */
	public Double getReservationAmount(){
		return reservationAmount;
	}
	public void setReservationAmount(Double reservationAmount){
		this.reservationAmount = reservationAmount;
	}
	/**
	 * 身份证
	 */
	public String getIdCard(){
		return idCard;
	}
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
	/**
	 * 移动电话
	 */
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	/**
	 * 工作电话
	 */
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 通讯地址
	 */
	public String getAdress(){
		return adress;
	}
	public void setAdress(String adress){
		this.adress = adress;
	}
	/**
	 * 邮政编号
	 */
	public String getZipCode(){
		return zipCode;
	}
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}
	/**
	 * 预订日期
	 */
	public Date getRegistratTime(){
		return registratTime;
	}
	public void setRegistratTime(Date registratTime){
		this.registratTime = registratTime;
	}
	/**
	 * 终止日期
	 */
	public Date getEndTime(){
		return endTime;
	}
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 到期是否可销售
	 */
	public BooleanEnum getWhetherSales(){
		return whetherSales;
	}
	public void setWhetherSales(BooleanEnum whetherSales){
		this.whetherSales = whetherSales;
	}
	/**
	 * 是否同步到房间
	 */
	public BooleanEnum getWhetherRoom(){
		return whetherRoom;
	}
	public void setWhetherRoom(BooleanEnum whetherRoom){
		this.whetherRoom = whetherRoom;
	}
	/**
	 * 预订原因
	 */
	public String getReason(){
		return reason;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	/**
	 * 销售顾问外键
	 */
	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
	}
	/**
	 * 状态
	 */
	public RentEnum getRentStatus(){
		return rentStatus;
	}
	public void setRentStatus(RentEnum rentStatus){
		this.rentStatus = rentStatus;
	}
	/**
	 * 预订类型
	 */
	public SubscribeEnum getSubscribeType(){
		return subscribeType;
	}
	public void setSubscribeType(SubscribeEnum subscribeType){
		this.subscribeType = subscribeType;
	}
	/**
	 * 备注
	 */
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
	}
}
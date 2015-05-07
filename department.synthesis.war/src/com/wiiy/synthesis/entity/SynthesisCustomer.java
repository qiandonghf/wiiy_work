package com.wiiy.synthesis.entity;

import java.io.Serializable;
import java.util.Date;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.common.preferences.enums.CustomerSupplierTypeEnum;
import com.wiiy.common.preferences.enums.CustomerTypeEnum;
import com.wiiy.synthesis.entity.SynthesisCustomerInfo;

/**
 * <br/>class-description 客商
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class SynthesisCustomer extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 账号实体
	 */
	@FieldDescription("账号实体")
	private User user;
	/**
	 * 跟踪人实体
	 */
	@FieldDescription("跟踪人实体")
	private User host;
	/**
	 * 引进人实体
	 */
	@FieldDescription("引进人实体")
	private User referee;
	/**
	 * 技术领域实体
	 */
	@FieldDescription("技术领域实体")
	private DataDict technic;
	/**
	 * 企业来源实体
	 */
	@FieldDescription("企业来源实体")
	private DataDict source;
	/**
	 * 创业者归属实体
	 */
	@FieldDescription("创业者归属实体")
	private DataDict enterpriseType;
	/**
	 * 客商详细信息
	 */
	@FieldDescription("客商详细信息")
	private SynthesisCustomerInfo customerInfo;
	/**
	 * 名称
	 */
	@FieldDescription("名称")
	private String name;
	/**
	 * 编码
	 */
	@FieldDescription("编码")
	private String code;
	/**
	 * 入园时间
	 */
	@FieldDescription("入园时间")
	private Date parkTime;
	/**
	 * 简称
	 */
	@FieldDescription("简称")
	private String shortName;
	/**
	 * 账号外键ID
	 */
	@FieldDescription("账号外键ID")
	private Long userId;
	/**
	 * 跟踪人外键ID
	 */
	@FieldDescription("跟踪人外键ID")
	private Long hostId;
	/**
	 * 引进人外键ID
	 */
	@FieldDescription("引进人外键ID")
	private Long refereeId;
	/**
	 * 状态变更时间
	 */
	@FieldDescription("状态变更时间")
	private Date time;
	/**
	 * 技术领域外键
	 */
	@FieldDescription("技术领域外键")
	private String technicId;
	/**
	 * 企业来源外键
	 */
	@FieldDescription("企业来源外键")
	private String sourceId;
	/**
	 * 创业者归属外键
	 */
	@FieldDescription("创业者归属外键")
	private String enterpriseTypeId;
	/**
	 * 是否孵化企业
	 */
	@FieldDescription("是否孵化企业")
	private BooleanEnum incubated;
	/**
	 * 类型
	 */
	@FieldDescription("类型")
	private CustomerTypeEnum type;
	/**
	 * 客商类型
	 */
	@FieldDescription("客商类型")
	private CustomerSupplierTypeEnum customerType;
	/**
	 * 扩展1
	 */
	@FieldDescription("扩展1")
	private String ext1;
	/**
	 * 扩展2
	 */
	@FieldDescription("扩展2")
	private String ext2;
	/**
	 * 扩展3
	 */
	@FieldDescription("扩展3")
	private String ext3;
	/**
	 * 扩展4
	 */
	@FieldDescription("扩展4")
	private String ext4;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 账号实体
	 */
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	/**
	 * 跟踪人实体
	 */
	public User getHost(){
		return host;
	}
	public void setHost(User host){
		this.host = host;
	}
	/**
	 * 引进人实体
	 */
	public User getReferee(){
		return referee;
	}
	public void setReferee(User referee){
		this.referee = referee;
	}
	/**
	 * 技术领域实体
	 */
	public DataDict getTechnic(){
		return technic;
	}
	public void setTechnic(DataDict technic){
		this.technic = technic;
	}
	/**
	 * 企业来源实体
	 */
	public DataDict getSource(){
		return source;
	}
	public void setSource(DataDict source){
		this.source = source;
	}
	/**
	 * 创业者归属实体
	 */
	public DataDict getEnterpriseType(){
		return enterpriseType;
	}
	public void setEnterpriseType(DataDict enterpriseType){
		this.enterpriseType = enterpriseType;
	}
	/**
	 * 客商详细信息
	 */
	public SynthesisCustomerInfo getCustomerInfo(){
		return customerInfo;
	}
	public void setCustomerInfo(SynthesisCustomerInfo customerInfo){
		this.customerInfo = customerInfo;
	}
	/**
	 * 名称
	 */
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 编码
	 */
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * 入园时间
	 */
	public Date getParkTime(){
		return parkTime;
	}
	public void setParkTime(Date parkTime){
		this.parkTime = parkTime;
	}
	/**
	 * 简称
	 */
	public String getShortName(){
		return shortName;
	}
	public void setShortName(String shortName){
		this.shortName = shortName;
	}
	/**
	 * 账号外键ID
	 */
	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
	}
	/**
	 * 跟踪人外键ID
	 */
	public Long getHostId(){
		return hostId;
	}
	public void setHostId(Long hostId){
		this.hostId = hostId;
	}
	/**
	 * 引进人外键ID
	 */
	public Long getRefereeId(){
		return refereeId;
	}
	public void setRefereeId(Long refereeId){
		this.refereeId = refereeId;
	}
	/**
	 * 状态变更时间
	 */
	public Date getTime(){
		return time;
	}
	public void setTime(Date time){
		this.time = time;
	}
	/**
	 * 技术领域外键
	 */
	public String getTechnicId(){
		return technicId;
	}
	public void setTechnicId(String technicId){
		this.technicId = technicId;
	}
	/**
	 * 企业来源外键
	 */
	public String getSourceId(){
		return sourceId;
	}
	public void setSourceId(String sourceId){
		this.sourceId = sourceId;
	}
	/**
	 * 创业者归属外键
	 */
	public String getEnterpriseTypeId(){
		return enterpriseTypeId;
	}
	public void setEnterpriseTypeId(String enterpriseTypeId){
		this.enterpriseTypeId = enterpriseTypeId;
	}
	/**
	 * 是否孵化企业
	 */
	public BooleanEnum getIncubated(){
		return incubated;
	}
	public void setIncubated(BooleanEnum incubated){
		this.incubated = incubated;
	}
	/**
	 * 类型
	 */
	public CustomerTypeEnum getType(){
		return type;
	}
	public void setType(CustomerTypeEnum type){
		this.type = type;
	}
	/**
	 * 客商类型
	 */
	public CustomerSupplierTypeEnum getCustomerType(){
		return customerType;
	}
	public void setCustomerType(CustomerSupplierTypeEnum customerType){
		this.customerType = customerType;
	}
	/**
	 * 扩展1
	 */
	public String getExt1(){
		return ext1;
	}
	public void setExt1(String ext1){
		this.ext1 = ext1;
	}
	/**
	 * 扩展2
	 */
	public String getExt2(){
		return ext2;
	}
	public void setExt2(String ext2){
		this.ext2 = ext2;
	}
	/**
	 * 扩展3
	 */
	public String getExt3(){
		return ext3;
	}
	public void setExt3(String ext3){
		this.ext3 = ext3;
	}
	/**
	 * 扩展4
	 */
	public String getExt4(){
		return ext4;
	}
	public void setExt4(String ext4){
		this.ext4 = ext4;
	}
}
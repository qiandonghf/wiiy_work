package com.wiiy.pf.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.wiiy.commons.annotation.FieldDescription;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;

/**
 * <br/>class-description 虚拟入驻审批单
 * <br/>extends com.wiiy.commons.entity.BaseEntity
 */
public class Fictitious extends BaseEntity implements Serializable {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 公司名称
	 */
	@FieldDescription("公司名称")
	private String investmentName;
	/**
	 * 公司ID
	 */
	@FieldDescription("公司ID")
	private Long investmentId;
	/**
	 * 企业ID
	 */
	@FieldDescription("企业ID")
	private Long customerId;
	/**
	 * 合同ID
	 */
	@FieldDescription("合同ID")
	private Long contractId;
	/**
	 * 实际经营地址
	 */
	@FieldDescription("实际经营地址")
	private String managerAddress;
	/**
	 * 法人代表
	 */
	@FieldDescription("法人代表")
	private String legalPerson;
	/**
	 * 注册资本
	 */
	@FieldDescription("注册资本")
	private Double regCapital;
	/**
	 * 联系人
	 */
	@FieldDescription("联系人")
	private String contect;
	/**
	 * 办公电话
	 */
	@FieldDescription("办公电话")
	private String phone;
	/**
	 * 手机
	 */
	@FieldDescription("手机")
	private String mobile;
	/**
	 * 传真
	 */
	@FieldDescription("传真")
	private String fax;
	/**
	 * 内容
	 */
	@FieldDescription("内容")
	private String content;
	/**
	 * 企业经营范围及规模、优势、前景等情况介绍
	 */
	@FieldDescription("企业经营范围及规模、优势、前景等情况介绍")
	private String introduce;
	/**
	 * 本公司引荐人或推荐单位意见
	 */
	@FieldDescription("本公司引荐人或推荐单位意见")
	private String suggestion;
	/**
	 * 流程实例ID
	 */
	@FieldDescription("流程实例ID")
	private String processInstanceId;
	/**
	 * 发起人ID
	 */
	@FieldDescription("发起人ID")
	private String startUserId;
	/**
	 * 发起人姓名
	 */
	@FieldDescription("发起人姓名")
	private String startUserName;
	/**
	 * 申请时间
	 */
	@FieldDescription("申请时间")
	private Date applyTime;
	/**
	 * 业务员意见
	 */
	@FieldDescription("业务员意见")
	private String ywyOpinion;
	/**
	 * 是否在园内
	 */
	@FieldDescription("是否在园内")
	private BooleanEnum inpark;
	/**
	 * 租赁房间ids
	 */
	@FieldDescription("租赁房间ids")
	private String subjectRentIds;
	/**
	 * 资金计划ids
	 */
	@FieldDescription("资金计划ids")
	private String billPlanRentIds;
	/**
	 * 押金ids
	 */
	@FieldDescription("押金ids")
	private String depositIds;
	
	 //-- 临时属性 --//

    // 流程任务
    private Task task;

    private Map<String, Object> variables;

    // 运行中的流程实例
    private ProcessInstance processInstance;

    // 历史的流程实例
    private HistoricProcessInstance historicProcessInstance;

    // 流程定义
    private ProcessDefinition processDefinition;
    
    //流程定义与实体关联
    private ProcessType processType;
	//附件
    private List<ContactBaseAtt> contactAtts;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 公司名称
	 */
	public String getInvestmentName(){
		return investmentName;
	}
	public void setInvestmentName(String investmentName){
		this.investmentName = investmentName;
	}
	/**
	 * 公司ID
	 */
	public Long getInvestmentId(){
		return investmentId;
	}
	public void setInvestmentId(Long investmentId){
		this.investmentId = investmentId;
	}
	/**
	 * 实际经营地址
	 */
	public String getManagerAddress(){
		return managerAddress;
	}
	public void setManagerAddress(String managerAddress){
		this.managerAddress = managerAddress;
	}
	/**
	 * 法人代表
	 */
	public String getLegalPerson(){
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson){
		this.legalPerson = legalPerson;
	}
	/**
	 * 注册资本
	 */
	public Double getRegCapital(){
		return regCapital;
	}
	public void setRegCapital(Double regCapital){
		this.regCapital = regCapital;
	}
	/**
	 * 联系人
	 */
	public String getContect(){
		return contect;
	}
	public void setContect(String contect){
		this.contect = contect;
	}
	/**
	 * 办公电话
	 */
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 手机
	 */
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	/**
	 * 传真
	 */
	public String getFax(){
		return fax;
	}
	public void setFax(String fax){
		this.fax = fax;
	}
	/**
	 * 企业经营范围及规模、优势、前景等情况介绍
	 */
	public String getIntroduce(){
		return introduce;
	}
	public void setIntroduce(String introduce){
		this.introduce = introduce;
	}
	/**
	 * 本公司引荐人或推荐单位意见
	 */
	public String getSuggestion(){
		return suggestion;
	}
	public void setSuggestion(String suggestion){
		this.suggestion = suggestion;
	}
	/**
	 * 流程实例ID
	 */
	public String getProcessInstanceId(){
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId){
		this.processInstanceId = processInstanceId;
	}
	/**
	 * 发起人ID
	 */
	public String getStartUserId(){
		return startUserId;
	}
	public void setStartUserId(String startUserId){
		this.startUserId = startUserId;
	}
	/**
	 * 发起人姓名
	 */
	public String getStartUserName(){
		return startUserName;
	}
	public void setStartUserName(String startUserName){
		this.startUserName = startUserName;
	}
	/**
	 * 申请时间
	 */
	public Date getApplyTime(){
		return applyTime;
	}
	public void setApplyTime(Date applyTime){
		this.applyTime = applyTime;
	}
	/**
	 * 业务员意见
	 */
	public String getYwyOpinion(){
		return ywyOpinion;
	}
	public void setYwyOpinion(String ywyOpinion){
		this.ywyOpinion = ywyOpinion;
	}
	/**
	 * 是否在园内
	 */
	public BooleanEnum getInpark() {
		return inpark;
	}
	public void setInpark(BooleanEnum inpark) {
		this.inpark = inpark;
	}
	@Transient
	public List<ContactBaseAtt> getContactAtts() {
		return contactAtts;
	}
	
	public void setContactAtts(List<ContactBaseAtt> contactAtts) {
		this.contactAtts = contactAtts;
	}
	@Transient
    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Transient
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    @Transient
    public HistoricProcessInstance getHistoricProcessInstance() {
        return historicProcessInstance;
    }

    public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
        this.historicProcessInstance = historicProcessInstance;
    }

    @Transient
    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }
    
    @Transient
	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public String getSubjectRentIds() {
		return subjectRentIds;
	}
	public void setSubjectRentIds(String subjectRentIds) {
		this.subjectRentIds = subjectRentIds;
	}
	public String getBillPlanRentIds() {
		return billPlanRentIds;
	}
	public void setBillPlanRentIds(String billPlanRentIds) {
		this.billPlanRentIds = billPlanRentIds;
	}
	public String getDepositIds() {
		return depositIds;
	}
	public void setDepositIds(String depositIds) {
		this.depositIds = depositIds;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
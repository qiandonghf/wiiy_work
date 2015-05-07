package com.wiiy.common.dto;

import java.util.Date;
import java.util.List;


public class BillInvoiceDto {
	private Long id;
	private String customerName;//企业名称
	private String taxNumberG;//税务登记证
	private String content;//开票内容
	private Double money;//金额
	private Double penalty;//违约金
	private Date payTime;//实际支付日期
	private Date checkoutTime;//出账日期
	private List<BillTypeInvoiceDto> billTypeDto;//费用类型 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTaxNumberG() {
		return taxNumberG;
	}
	public void setTaxNumberG(String taxNumberG) {
		this.taxNumberG = taxNumberG;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public List<BillTypeInvoiceDto> getBillTypeDto() {
		return billTypeDto;
	}
	public void setBillTypeDto(List<BillTypeInvoiceDto> billTypeDto) {
		this.billTypeDto = billTypeDto;
	}
	public Double getPenalty() {
		return penalty;
	}
	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getCheckoutTime() {
		return checkoutTime;
	}
	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
}

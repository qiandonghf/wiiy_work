package com.wiiy.engineering.system;

import java.util.ArrayList;
import java.util.List;

import com.wiiy.core.entity.DataDict;

public class DataDictInit {
	private List<DataDict> list=new ArrayList<DataDict>();
	public boolean init(){
		boolean r=true;
		/*this.newData("customer.0001", null, "客商管理", "TECHNIC_TYPE", "技术领域", 1, 1,1);
		this.newData("customer.000101", "customer.0001", "客商管理", "TECHNIC_TYPE", "电子信息", 1, 1,1);
		this.newData("customer.000102", "customer.0001", "客商管理", "TECHNIC_TYPE", "先进制造", 1, 1,2);
		this.newData("customer.000103", "customer.0001", "客商管理", "TECHNIC_TYPE", "航空航天", 1, 1,3);
		this.newData("customer.000104", "customer.0001", "客商管理", "TECHNIC_TYPE", "现代交通", 1, 1,4);
		this.newData("customer.000105", "customer.0001", "客商管理", "TECHNIC_TYPE", "生物医药与医疗器械", 1, 1,5);
		this.newData("customer.000106", "customer.0001", "客商管理", "TECHNIC_TYPE", "新材料", 1, 1,6);
		this.newData("customer.000107", "customer.0001", "客商管理", "TECHNIC_TYPE", "新能源与节能", 1, 1,7);
		this.newData("customer.000108", "customer.0001", "客商管理", "TECHNIC_TYPE", "环境保护", 1, 1,8);
		this.newData("customer.000109", "customer.0001", "客商管理", "TECHNIC_TYPE", "地球空间与海洋", 1, 1,9);
		this.newData("customer.000110", "customer.0001", "客商管理", "TECHNIC_TYPE", "核应用技术", 1, 1,10);
		this.newData("customer.000111", "customer.0001", "客商管理", "TECHNIC_TYPE", "现代农业", 1, 1,11);
		this.newData("customer.000199", "customer.0001", "客商管理", "TECHNIC_TYPE", "其他", 1, 1,99);
		
		this.newData("customer.0002", null, "客商管理", "CUSTOMER_SOURCE", "客户来源", 1, 1,2);
		this.newData("customer.000201", "customer.0002", "客商管理", "CUSTOMER_SOURCE", "电话", 1, 1,1);
		this.newData("customer.000202", "customer.0002", "客商管理", "CUSTOMER_SOURCE", "网络", 1, 1,2);
		this.newData("customer.000203", "customer.0002", "客商管理", "CUSTOMER_SOURCE", "客户或朋友介绍", 1, 1,3);
		this.newData("customer.000299", "customer.0002", "客商管理", "CUSTOMER_SOURCE", "其他", 1, 1,99);
		
		this.newData("customer.0003", null, "客户关系管理", "CONTECTTYPE", "交往类型", 1, 1,3);
		this.newData("customer.000301", "customer.0003", "客户关系管理", "VISIT", "拜访", 1, 1,1);
		this.newData("customer.000302", "customer.0003", "客户关系管理", "RECOMMEND", "引荐", 1, 1,2);
		this.newData("customer.000303", "customer.0003", "客户关系管理", "METTING", "会晤", 1, 1,3);
		
		this.newData("customer.0004", null, "客户关系管理", "REGISTER_TYPE", "注册类型", 1, 1,4);
		this.newData("customer.000401", "customer.0004", "客户关系管理", "REGISTER_TYPE", "内资-国有企业", 1, 1,1);
		this.newData("customer.000402", "customer.0004", "客户关系管理", "REGISTER_TYPE", "内资-集体企业", 1, 1,2);
		this.newData("customer.000403", "customer.0004", "客户关系管理", "REGISTER_TYPE", "内资-股份合作企业", 1, 1,3);
		this.newData("customer.000404", "customer.0004", "客户关系管理", "REGISTER_TYPE", "内资-私营企业", 1, 1,4);
		this.newData("customer.000405", "customer.0004", "客户关系管理", "REGISTER_TYPE", "内资-股份有限公司", 1, 1,5);
		this.newData("customer.000406", "customer.0004", "客户关系管理", "REGISTER_TYPE", "内资-有限责任公司", 1, 1,6);
		this.newData("customer.000407", "customer.0004", "客户关系管理", "REGISTER_TYPE", "内资-联营企业", 1, 1,7);
		this.newData("customer.000408", "customer.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-合资经营企业", 1, 1,8);
		this.newData("customer.000409", "customer.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-合作经营企业", 1, 1,9);
		this.newData("customer.000410", "customer.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-独资经营企业", 1, 1,10);
		this.newData("customer.000411", "customer.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-股份有限公司", 1, 1,11);
		this.newData("customer.000412", "customer.0004", "客户关系管理", "REGISTER_TYPE", "外资-中外合资", 1, 1,12);
		this.newData("customer.000413", "customer.0004", "客户关系管理", "REGISTER_TYPE", "外资-中外合作", 1, 1,13);
		this.newData("customer.000414", "customer.0004", "客户关系管理", "REGISTER_TYPE", "外资-外商独资", 1, 1,14);
		this.newData("customer.000415", "customer.0004", "客户关系管理", "REGISTER_TYPE", "外资-股份有限公司", 1, 1,15);
		
		this.newData("customer.0005", null, "客户关系管理", "DOCUMENT_TYPE", "证件类型", 1, 1,5);
		this.newData("customer.000501", "customer.0005", "客户关系管理", "DOCUMENT_TYPE", "身份证", 1, 1,1);
		this.newData("customer.000502", "customer.0005", "客户关系管理", "DOCUMENT_TYPE", "护照", 1, 1,2);*/
		
		return r;
	}
	/**
	 * 
	 * @param id
	 * @param parentId
	 * @param moduleName
	 * @param dataName
	 * @param value
	 * @param fixed 1 系统 0用户
	 * @param type 0单一值 1列表值
	 */
	private void newData(String id,String parentId,String moduleName,String dataName,String value,Integer fixed,Integer type,Integer order){
		DataDict dataDict=new DataDict();
		dataDict.setId(id);
		dataDict.setModuleName(moduleName);
		dataDict.setDataName(dataName);
		dataDict.setDataValue(value);
		dataDict.setFixed(fixed);
		dataDict.setParentId(parentId);
		dataDict.setType(type);
		dataDict.setDisplayOrder(order);
		list.add(dataDict);
	}
	public List<DataDict> getList() {
		init();
		return list;
	}
	
}

package com.wiiy.sale.system;

import java.util.ArrayList;
import java.util.List;

import com.wiiy.core.entity.DataDict;

public class DataDictInit {
	private List<DataDict> list=new ArrayList<DataDict>();
	public boolean init(){
		boolean r=true;
/*	    this.newData("sale.0001", null, "销售部客户", "EDUCATION", "学历", 1, 1,1);
		this.newData("sale.000101", "sale.0001", "销售部客户", "EDUCATION", "高中", 1, 1,1);
		this.newData("sale.000102", "sale.0001", "销售部客户", "EDUCATION", "专科", 1, 1,2);
		this.newData("sale.000103", "sale.0001", "销售部客户", "EDUCATION", "本科 ", 1, 1,3);
		this.newData("sale.000104", "sale.0001", "销售部客户", "EDUCATION", "研究生", 1, 1,4);
		this.newData("sale.000105", "sale.0001", "销售部客户", "EDUCATION", "博士", 1, 1,5);
		this.newData("sale.000106", "sale.0001", "销售部客户", "EDUCATION", "硕士", 1, 1,6);
		
		this.newData("sale.0002", null, "销售部客户", "PROFESSION", "职业", 1, 1,2);
		this.newData("sale.000201", "sale.0002", "销售部客户", "PROFESSION", "制造", 1, 1,1);
		this.newData("sale.000202", "sale.0002", "销售部客户", "PROFESSION", "个体工商户", 1, 1,2);
		this.newData("sale.000203", "sale.0002", "销售部客户", "PROFESSION", "商业服务行业", 1, 1,3);
		this.newData("sale.000204", "sale.0002", "销售部客户", "PROFESSION", "公务员", 1, 1,4);
		this.newData("sale.000205", "sale.0002", "销售部客户", "PROFESSION", "房地产", 1, 1,5);
		this.newData("sale.000206", "sale.0002", "销售部客户", "PROFESSION", "军人", 1, 1,6);
		this.newData("sale.000207", "sale.0002", "销售部客户", "PROFESSION", "金融证券", 1, 1,7);
		this.newData("sale.000208", "sale.0002", "销售部客户", "PROFESSION", "高科技", 1, 1,8);
		this.newData("sale.000209", "sale.0002", "销售部客户", "PROFESSION", "其它", 1, 1,9);
		
		this.newData("sale.0003", null, "销售部客户", "FAMILY_INCOME", "家庭年收入", 1, 1,3);
		this.newData("sale.000301", "sale.0003", "销售部客户", "FAMILY_INCOME", "4万以下", 1, 1,1);
		this.newData("sale.000302", "sale.0003", "销售部客户", "FAMILY_INCOME", "4-6万", 1, 1,2);
		this.newData("sale.000303", "sale.0003", "销售部客户", "FAMILY_INCOME", "6-8万", 1, 1,3);
		this.newData("sale.000304", "sale.0003", "销售部客户", "FAMILY_INCOME", "8-10万", 1, 1,4);
		this.newData("sale.000305", "sale.0003", "销售部客户", "FAMILY_INCOME", "10-15万", 1, 1,5);
		this.newData("sale.000306", "sale.0003", "销售部客户", "FAMILY_INCOME", "15-20万", 1, 1,6);
		this.newData("sale.000307", "sale.0003", "销售部客户", "FAMILY_INCOME", "20万以上", 1, 1,7);
		
		this.newData("sale.0004", null, "销售部客户", "CLIENT_AREA", "客户区域", 1, 1,4);
		this.newData("sale.000401", "sale.0004", "销售部客户", "CLIENT_AREA", "地段", 1, 1,1);
		this.newData("sale.000402", "sale.0004", "销售部客户", "CLIENT_AREA", "户型", 1, 1,2);
		this.newData("sale.000403", "sale.0004", "销售部客户", "CLIENT_AREA", "价格", 1, 1,3);
		this.newData("sale.000404", "sale.0004", "销售部客户", "CLIENT_AREA", "环境", 1, 1,4);
		
		this.newData("sale.0005", null, "销售部客户", "SOURCE", "讯息来源", 1, 1,5);
		this.newData("sale.000501", "sale.0005", "销售部客户", "SOURCE", "网络", 1, 1,1);
		this.newData("sale.000502", "sale.0005", "销售部客户", "SOURCE", "电视", 1, 1,2); 
		this.newData("sale.000503", "sale.0005", "销售部客户", "SOURCE", "报纸", 1, 1,3); 
		
		this.newData("sale.0006", null, "销售部客户", "HONGXING", "户型需求", 1, 1,6);
		this.newData("sale.000601", "sale.0006", "销售部客户", "HONGXING", "不确定", 1, 1,1);
		this.newData("sale.000602", "sale.0006", "销售部客户", "HONGXING", "很好", 1, 1,2);
		this.newData("sale.000603", "sale.0006", "销售部客户", "HONGXING", "可以", 1, 1,3);
		this.newData("sale.000604", "sale.0006", "销售部客户", "HONGXING", "一般", 1, 1,4);
		this.newData("sale.000605", "sale.0006", "销售部客户", "HONGXING", "随便看看", 1, 1,5);
		this.newData("sale.000606", "sale.0006", "销售部客户", "HONGXING", "无购买意向", 1, 1,6);
		
		this.newData("sale.0007", null, "销售部客户", "RESISTANCE", "抗性分析", 1, 1,7);
		this.newData("sale.000701", "sale.0007", "销售部客户", "RESISTANCE", "价格偏高", 1, 1,1);
		this.newData("sale.000702", "sale.0007", "销售部客户", "RESISTANCE", "户型不合适", 1, 1,2); 
		this.newData("sale.000703", "sale.0007", "销售部客户", "RESISTANCE", "立面园林", 1, 1,3);
		this.newData("sale.000704", "sale.0007", "销售部客户", "RESISTANCE", "竞争影响", 1, 1,4);
		this.newData("sale.000705", "sale.0007", "销售部客户", "RESISTANCE", "犹豫观望", 1, 1,5); 
		this.newData("sale.000706", "sale.0007", "销售部客户", "RESISTANCE", "其他原因", 1, 1,6); 
		
		this.newData("sale.0008", null, "销售部客户", "MOTIVATION", "购房动机", 1, 1,8);
		this.newData("sale.000801", "sale.0008", "销售部客户", "MOTIVATION", "不确定", 1, 1,1);
		this.newData("sale.000802", "sale.0008", "销售部客户", "MOTIVATION", "首次购房", 1, 1,2);
		this.newData("sale.000803", "sale.0008", "销售部客户", "MOTIVATION", "改善住房条件", 1, 1,3);
		this.newData("sale.000804", "sale.0008", "销售部客户", "MOTIVATION", "工作需要", 1, 1,4);
		this.newData("sale.000805", "sale.0008", "销售部客户", "MOTIVATION", "投资", 1, 1,5); 
		this.newData("sale.000806", "sale.0008", "销售部客户", "MOTIVATION", "其他原因", 1, 1,6); 
*/		
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

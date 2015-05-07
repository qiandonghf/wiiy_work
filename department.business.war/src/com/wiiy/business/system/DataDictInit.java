package com.wiiy.business.system;

import java.util.ArrayList;
import java.util.List;

import com.wiiy.core.entity.DataDict;

public class DataDictInit {
	private List<DataDict> list=new ArrayList<DataDict>();
	public boolean init(){
		
		
		
		return true;
		/* delete by Jonathan 20150303
		boolean r=true;
		this.newData("business.0002", null, "招商管理", "TECHNIC_TYPE", "技术领域", 1, 1,1);
		this.newData("business.000201", "business.0002", "招商管理", "TECHNIC_TYPE", "电子信息", 1, 1,1);
		this.newData("business.000202", "business.0002", "招商管理", "TECHNIC_TYPE", "先进制造", 1, 1,2);
		this.newData("business.000203", "business.0002", "招商管理", "TECHNIC_TYPE", "航空航天", 1, 1,3);
		this.newData("business.000204", "business.0002", "招商管理", "TECHNIC_TYPE", "现代交通", 1, 1,4);
		this.newData("business.000205", "business.0002", "招商管理", "TECHNIC_TYPE", "生物医药与医疗器械", 1, 1,5);
		this.newData("business.000206", "business.0002", "招商管理", "TECHNIC_TYPE", "新材料", 1, 1,6);
		this.newData("business.000207", "business.0002", "招商管理", "TECHNIC_TYPE", "新能源与节能", 1, 1,7);
		this.newData("business.000208", "business.0002", "招商管理", "TECHNIC_TYPE", "环境保护", 1, 1,8);
		this.newData("business.000209", "business.0002", "招商管理", "TECHNIC_TYPE", "地球空间与海洋", 1, 1,9);
		this.newData("business.000210", "business.0002", "招商管理", "TECHNIC_TYPE", "核应用技术", 1, 1,10);
		this.newData("business.000211", "business.0002", "招商管理", "TECHNIC_TYPE", "现代农业", 1, 1,11);
		this.newData("business.000212", "business.0002", "招商管理", "TECHNIC_TYPE", "其他", 1, 1,12);

		this.newData("business.0003", null, "客户关系管理", "CUSTOMER_SOURCE", "客户来源", 1, 1,3);
		this.newData("business.000301", "business.0003", "客户关系管理", "CUSTOMER_SOURCE", "电话", 1, 1,1);
		this.newData("business.000302", "business.0003", "客户关系管理", "CUSTOMER_SOURCE", "网络", 1, 1,2);
		this.newData("business.000303", "business.0003", "客户关系管理", "CUSTOMER_SOURCE", "客户或朋友介绍", 1, 1,3);
		this.newData("business.000399", "business.0003", "客户关系管理", "CUSTOMER_SOURCE", "其他", 1, 1,99);
		
		this.newData("business.0004", null, "客户关系管理", "REGISTER_TYPE", "注册类型", 1, 1,4);
		this.newData("business.000401", "business.0004", "客户关系管理", "REGISTER_TYPE", "内资-国有企业", 1, 1,1);
		this.newData("business.000402", "business.0004", "客户关系管理", "REGISTER_TYPE", "内资-集体企业", 1, 1,2);
		this.newData("business.000403", "business.0004", "客户关系管理", "REGISTER_TYPE", "内资-股份合作企业", 1, 1,3);
		this.newData("business.000404", "business.0004", "客户关系管理", "REGISTER_TYPE", "内资-私营企业", 1, 1,4);
		this.newData("business.000405", "business.0004", "客户关系管理", "REGISTER_TYPE", "内资-股份有限公司", 1, 1,5);
		this.newData("business.000406", "business.0004", "客户关系管理", "REGISTER_TYPE", "内资-有限责任公司", 1, 1,6);
		this.newData("business.000407", "business.0004", "客户关系管理", "REGISTER_TYPE", "内资-联营企业", 1, 1,7);
		this.newData("business.000408", "business.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-合资经营企业", 1, 1,8);
		this.newData("business.000409", "business.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-合作经营企业", 1, 1,9);
		this.newData("business.000410", "business.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-独资经营企业", 1, 1,10);
		this.newData("business.000411", "business.0004", "客户关系管理", "REGISTER_TYPE", "港澳台-股份有限公司", 1, 1,11);
		this.newData("business.000412", "business.0004", "客户关系管理", "REGISTER_TYPE", "外资-中外合资", 1, 1,12);
		this.newData("business.000413", "business.0004", "客户关系管理", "REGISTER_TYPE", "外资-中外合作", 1, 1,13);
		this.newData("business.000414", "business.0004", "客户关系管理", "REGISTER_TYPE", "外资-外商独资", 1, 1,14);
		this.newData("business.000415", "business.0004", "客户关系管理", "REGISTER_TYPE", "外资-股份有限公司", 1, 1,15);
		
		this.newData("business.0005", null, "客户关系管理", "CURRENCY_TYPE", "币种", 1, 1,5);
		this.newData("business.000501", "business.0005", "客户关系管理", "CURRENCY_TYPE", "人民币", 1, 1,1);
		this.newData("business.000502", "business.0005", "客户关系管理", "CURRENCY_TYPE", "港元", 1, 1,2);
		this.newData("business.000503", "business.0005", "客户关系管理", "CURRENCY_TYPE", "台币", 1, 1,3);
		this.newData("business.000504", "business.0005", "客户关系管理", "CURRENCY_TYPE", "美元", 1, 1,4);
		this.newData("business.000505", "business.0005", "客户关系管理", "CURRENCY_TYPE", "欧元", 1, 1,5);
		
		this.newData("business.0006", null, "客户关系管理", "DOCUMENT_TYPE", "证件类型", 1, 1,6);
		this.newData("business.000601", "business.0006", "客户关系管理", "DOCUMENT_TYPE", "身份证", 1, 1,1);
		this.newData("business.000602", "business.0006", "客户关系管理", "DOCUMENT_TYPE", "护照", 1, 1,2);

		this.newData("business.0007", null, "客户关系管理", "APPLY_TYPE", "申报类型", 1, 1,7);
		this.newData("business.000701", "business.0007", "客户关系管理", "APPLY_TYPE", "国家-自然科学基金", 1, 1,1);
		this.newData("business.000702", "business.0007", "客户关系管理", "APPLY_TYPE", "国家-863计划", 1, 1,2);
		this.newData("business.000703", "business.0007", "客户关系管理", "APPLY_TYPE", "国家-973计划", 1, 1,3);
		this.newData("business.000704", "business.0007", "客户关系管理", "APPLY_TYPE", "国家-科技新中小企业创新基金", 1, 1,4);
		this.newData("business.000705", "business.0007", "客户关系管理", "APPLY_TYPE", "国家-火炬计划", 1, 1,5);
		this.newData("business.000706", "business.0007", "客户关系管理", "APPLY_TYPE", "省市-省市级创新基金", 1, 1,6);
		this.newData("business.000707", "business.0007", "客户关系管理", "APPLY_TYPE", "省市-市级创新专项资金", 1, 1,7);
		
		this.newData("business.0008", null, "客户关系管理", "APPLY_STATUS", "申报状态", 1, 1,8);
		this.newData("business.000801", "business.0008", "客户关系管理", "APPLY_STATUS", "申请", 1, 1,1);
		this.newData("business.000802", "business.0008", "客户关系管理", "APPLY_STATUS", "成功", 1, 1,2);
		this.newData("business.000803", "business.0008", "客户关系管理", "APPLY_STATUS", "失败", 1, 1,3);
		
		this.newData("business.0009", null, "客户关系管理", "COPYRIGHT_TYPE", "著作权类型", 1, 1,9);
		this.newData("business.000901", "business.0009", "客户关系管理", "COPYRIGHT_TYPE", "软件著作权", 1, 1,1);
		
		this.newData("business.0010", null, "客户关系管理", "PATENT_TYPE", "专利类型", 1, 1,10);
		this.newData("business.001001", "business.0010", "客户关系管理", "PATENT_TYPE", "发明专利", 1, 1,1);
		this.newData("business.001002", "business.0010", "客户关系管理", "PATENT_TYPE", "实用新型", 1, 1,2);
		this.newData("business.001003", "business.0010", "客户关系管理", "PATENT_TYPE", "外观设计", 1, 1,3);
		
		this.newData("business.0011", null, "客户关系管理", "PATENT_STATUS", "专利状态", 1, 1,11);
		this.newData("business.001101", "business.0011", "客户关系管理", "PATENT_STATUS", "申请", 1, 1,1);
		this.newData("business.001102", "business.0011", "客户关系管理", "PATENT_STATUS", "授权", 1, 1,2);
		
		this.newData("business.0012", null, "客户关系管理", "PATENT_SOURCE", "专利来源", 1, 1,12);
		this.newData("business.001201", "business.0012", "客户关系管理", "PATENT_SOURCE", "境内专利", 1, 1,1);
		this.newData("business.001202", "business.0012", "客户关系管理", "PATENT_SOURCE", "境外专利", 1, 1,2);
		this.newData("business.001203", "business.0012", "客户关系管理", "PATENT_SOURCE", "境内专利转让", 1, 1,3);
		this.newData("business.001204", "business.0012", "客户关系管理", "PATENT_SOURCE", "境外专利转让", 1, 1,4);
		
		this.newData("business.0013", null, "客户关系管理", "PRODUCT_STAGE", "产品阶段", 1, 1,13);
		this.newData("business.001301", "business.0013", "客户关系管理", "PRODUCT_STAGE", "研发", 1, 1,1);
		this.newData("business.001302", "business.0013", "客户关系管理", "PRODUCT_STAGE", "中试", 1, 1,2);
		this.newData("business.001303", "business.0013", "客户关系管理", "PRODUCT_STAGE", "推广", 1, 1,3);
		this.newData("business.001304", "business.0013", "客户关系管理", "PRODUCT_STAGE", "发布", 1, 1,4);
		
		this.newData("business.0014", null, "客户关系管理", "POST", "职位", 1, 1,14);
		this.newData("business.001401", "business.0014", "客户关系管理", "POST", "CEO", 1, 1,1);
		this.newData("business.001402", "business.0014", "客户关系管理", "POST", "COO", 1, 1,1);
		this.newData("business.001403", "business.0014", "客户关系管理", "POST", "CFO", 1, 1,1);
		this.newData("business.001404", "business.0014", "客户关系管理", "POST", "CTO", 1, 1,1);
		this.newData("business.0015", null, "客户关系管理", "DEGREE", "学位", 1, 1,15);
		this.newData("business.001501", "business.0015", "客户关系管理", "DEGREE", "高中", 1, 1,1);
		this.newData("business.001502", "business.0015", "客户关系管理", "DEGREE", "大专", 1, 1,2);
		this.newData("business.001503", "business.0015", "客户关系管理", "DEGREE", "学士", 1, 1,3);
		this.newData("business.001504", "business.0015", "客户关系管理", "DEGREE", "硕士", 1, 1,4);
		this.newData("business.001505", "business.0015", "客户关系管理", "DEGREE", "博士", 1, 1,5);
		this.newData("business.001506", "business.0015", "客户关系管理", "DEGREE", "博士后", 1, 1,6);
		this.newData("business.001507", "business.0015", "客户关系管理", "DEGREE", "研究生", 1, 1,6);
		
		this.newData("business.0016", null, "客户关系管理", "CONTECTTYPE", "交往类型", 1, 1,16);
		this.newData("business.001601", "business.0016", "客户关系管理", "VISIT", "拜访", 1, 1,1);
		this.newData("business.001602", "business.0016", "客户关系管理", "RECOMMEND", "引荐", 1, 1,2);
		this.newData("business.001603", "business.0016", "客户关系管理", "METTING", "会晤", 1, 1,3);
		
		
		this.newData("business.0017", null, "客户关系管理", "CERTIFICATION_TYPE", "认证类型", 1, 1,17);
		this.newData("business.001701", "business.0017", "客户关系管理", "SOFTWERE", "双软认证", 1, 1,1);
		
		this.newData("business.0018", null, "客户关系管理", "CAPITAL_FORM", "出资方式", 1, 1,18);
		this.newData("business.001801", "business.0018", "客户关系管理", "MONEY", "货币", 1, 1,1);
		this.newData("business.001802", "business.0018", "客户关系管理", "TECHNOLOGY", "技术", 1, 1,2);
		this.newData("business.001803", "business.0018", "客户关系管理", "EQUIPMENT", "设备", 1, 1,3);
		
		//this.newData("business.0019", null, "客户关系管理", "CONTRACT_TYPE", "合同类型", 1, 1,19);
		//this.newData("business.001901", "business.0019", "客户关系管理", "RENT CONTRACT", "租赁合同", 1, 1,1);
		this.newData("business.0020", null, "客户关系管理", "RENT_REBATE_RULE", "租赁优惠规则", 1, 1,20);
		this.newData("business.002001", "business.0020", "客户关系管理", "CollegeStudents", "大学生创业", 1, 1,1);
		//this.newData("business.0021", null, "客户关系管理", "资金计划自动出账提前时间（天）", "7", 1, 1,20);
		
		this.newData("business.0022", null, "客户关系管理", "POLICY_TYPE", "招商政策类型", 1, 1,21);
		this.newData("business.002201", "business.0022", "客户关系管理", "STUDENT", "留学生", 1, 1,1);
		this.newData("business.002202", "business.0022", "客户关系管理", "STUDENT", "海归", 1, 1,1);
		
		this.newData("business.0023", null, "客户关系管理", "Net_TYPE", "宽带类型", 1, 1,21);
		this.newData("business.002301", "business.0023", "客户关系管理", "10Mbps", "10Mbps", 1, 1,1);
		this.newData("business.002302", "business.0023", "客户关系管理", "5Mbps", "5Mbps", 1, 1,1);
		
		this.newData("business.0024", null, "客服联系单服务类型", "Service_TYPE", "服务类型", 1, 1,21);
//		this.newData("business.002401", "business.0024", "客服联系单服务类型", "项目申报", "项目申报", 1, 1,1);
//		this.newData("business.002402", "business.0024", "客服联系单服务类型", "项目融资", "项目融资", 1, 1,1);
//		this.newData("business.002403", "business.0024", "客服联系单服务类型", "培训", "培训", 1, 1,1);
//		this.newData("business.002404", "business.0024", "客服联系单服务类型", "财务", "财务", 1, 1,1);
		this.newData("business.002401", "business.0024", "客服联系单服务类型", "贷款服务", "贷款服务", 1, 1,1);
		this.newData("business.002402", "business.0024", "客服联系单服务类型", "会计服务", "会计服务", 1, 1,1);
		this.newData("business.002403", "business.0024", "客服联系单服务类型", "后勤服务", "后勤服务", 1, 1,1);
		this.newData("business.002404", "business.0024", "客服联系单服务类型", "投资服务", "投资服务", 1, 1,1);
		this.newData("business.002405", "business.0024", "客服联系单服务类型", "法律服务", "法律服务", 1, 1,1);
		this.newData("business.002406", "business.0024", "客服联系单服务类型", "税务服务", "税务服务", 1, 1,1);
		this.newData("business.002407", "business.0024", "客服联系单服务类型", "人力资源", "人力资源", 1, 1,1);
		this.newData("business.002408", "business.0024", "客服联系单服务类型", "项目申报", "项目申报", 1, 1,1);
		this.newData("business.002409", "business.0024", "客服联系单服务类型", "营销服务", "营销服务", 1, 1,1);
		
		
		
		this.newData("business.0025", null, "孵化过程", "INCUBATE_ROUTE", "孵化过程", 1, 1,25);
		this.newData("business.002501", "business.0025", "孵化过程", "在孵", "在孵", 1, 1,1);
		this.newData("business.002502", "business.0025", "孵化过程", "毕业", "毕业", 1, 1,1);
		this.newData("business.002503", "business.0025", "孵化过程", "肄业", "肄业", 1, 1,1);
		this.newData("business.002504", "business.0025", "孵化过程", "消亡", "消亡", 1, 1,1);
		this.newData("business.002505", "business.0025", "孵化过程", "其他", "其他", 1, 1,1);
		
		this.newData("business.0026", null, "入驻场所", "INCUBATE_SETTING", "入驻场所", 1, 1,26);
		
		this.newData("business.0027", null, "企业资质", "CUSTOMER_QUALIFICATION", "企业资质", 1, 1,27);
		this.newData("business.002701", "business.0027", "企业资质", "经认定的软件企业", "经认定的软件企业", 1, 1,1);
		this.newData("business.002702", "business.0027", "企业资质", "园区高新技术企业", "园区高新技术企业", 1, 1,1);
		this.newData("business.002703", "business.0027", "企业资质", "市级高新技术企业", "市级高新技术企业", 1, 1,1);
		this.newData("business.002704", "business.0027", "企业资质", "国家级高新技术企业", "国家级高新技术企业", 1, 1,1);
		this.newData("business.002705", "business.0027", "企业资质", "已参加新企业见面会企业", "已参加新企业见面会企业", 1, 1,1);
		this.newData("business.002706", "business.0027", "企业资质", "大学生企业", "大学生企业", 1, 1,1);
		this.newData("business.002707", "business.0027", "企业资质", "服务外包", "服务外包", 1, 1,1);
		this.newData("business.002708", "business.0027", "企业资质", "明星企业", "明星企业", 1, 1,1);
		this.newData("business.002709", "business.0027", "企业资质", "其他", "其他", 1, 1,1);
		
		this.newData("business.0028", null, "企业纳税地", "CUSTOMER_TAXADRESS", "企业纳税地", 1, 1,28);
		this.newData("business.002801", "business.0028", "企业纳税地", "高新区", "高新区", 1, 1,1);
		this.newData("business.002802", "business.0028", "企业纳税地", "江东区", "江东区", 1, 1,1);
		this.newData("business.002803", "business.0028", "企业纳税地", "江北区", "江北区", 1, 1,1);
		this.newData("business.002804", "business.0028", "企业纳税地", "海曙区", "海曙区", 1, 1,1);
		this.newData("business.002805", "business.0028", "企业纳税地", "镇海区", "镇海区", 1, 1,1);
		this.newData("business.002806", "business.0028", "企业纳税地", "北仑区", "北仑区", 1, 1,1);
		this.newData("business.002807", "business.0028", "企业纳税地", "鄞州区", "鄞州区", 1, 1,1);
		
		this.newData("business.0029", null, "政治面貌", "Political", "政治面貌", 1, 1,5);
		this.newData("business.002901", "business.0029", "政治面貌", "PartyMember", "党员", 1, 1,1);
		this.newData("business.002902", "business.0029", "政治面貌", "LeagueMember", "团员", 1, 1,2);
		this.newData("business.002903", "business.0029", "政治面貌", "Masses", "群众", 1, 1,3);
		
		this.newData("business.0030", null, "企业类型", "EnterpriseType", "企业类型", 1, 1,5);
		this.newData("business.003001", "business.0030", "企业类型", "DXS", "大学生", 1, 1,1);
		this.newData("business.003002", "business.0030", "企业类型", "LXS", "留学生", 1, 1,2);
		this.newData("business.003003", "business.0030", "企业类型", "YB", "一般", 1, 1,2);
		this.newData("business.003004", "business.0030", "企业类型", "WZ", "外资", 1, 1,3);
		this.newData("business.003005", "business.0030", "企业类型", "ZD", "浙大企业", 1, 1,3);
		this.newData("business.003006", "business.0030", "企业类型", "JD", "街道企业", 1, 1,3);
		
		this.newData("business.0031", null, "合同甲方类型", "ContractParty", "合同甲方", 1, 1,31);
		this.newData("business.003101", "business.0031", "合同甲方", "ZJWLCY", "浙江华业", 1, 1,1);
		
		this.newData("business.0032", null, "招商管理", "ROOM_TYPE", "房间用途", 1, 1,32);
		this.newData("business.003201", "business.0032", "招商管理", "ROOM_TYPE", "办公", 1, 1,1);
		this.newData("business.003202", "business.0032", "招商管理", "ROOM_TYPE", "厂房", 1, 1,2);
		this.newData("business.003203", "business.0032", "招商管理", "ROOM_TYPE", "宿舍", 1, 1,3);
		this.newData("business.003299", "business.0032", "招商管理", "ROOM_TYPE", "其他", 1, 1,99);
		
		this.newData("business.0033", null, "招商管理", "RENT_REBATE_RULE", "租赁优惠规则", 1, 1,33);
		this.newData("business.003301", "business.0033", "招商管理", "CollegeStudents", "大学生创业", 1, 1,1);
	
		this.newData("business.0034", null, "城市", "CityId", "城市", 1, 1,34);
		this.newData("business.003401", "business.0034", "城市", "CITY_TYPE", "杭州", 1, 1,1);
		this.newData("business.003402", "business.0034", "城市", "CITY_TYPE", "宁波", 1, 1,1);
		this.newData("business.003403", "business.0034", "城市", "CITY_TYPE", "温州", 1, 1,1);
		this.newData("business.003404", "business.0034", "城市", "CITY_TYPE", "绍兴", 1, 1,1);
		this.newData("business.003405", "business.0034", "城市", "CITY_TYPE", "湖州", 1, 1,1);
		this.newData("business.003406", "business.0034", "城市", "CITY_TYPE", "嘉兴", 1, 1,1);
		this.newData("business.003407", "business.0034", "城市", "CITY_TYPE", "金华", 1, 1,1);
		this.newData("business.003408", "business.0034", "城市", "CITY_TYPE", "衢州", 1, 1,1);
		this.newData("business.003409", "business.0034", "城市", "CITY_TYPE", "台州", 1, 1,1);
		this.newData("business.003410", "business.0034", "城市", "CITY_TYPE", "丽水", 1, 1,1);
		this.newData("business.003411", "business.0034", "城市", "CITY_TYPE", "舟山", 1, 1,1);
		
		this.newData("business.0035", null, "所属行业", "IndustryId", "所属行业", 1, 1,34);
		this.newData("business.003501", "business.0035", "所属行业", "DZXX", "电子信息", 1, 1,1);
		this.newData("business.003502", "business.0035", "所属行业", "XJZZ", "先进制造", 1, 1,2);
		this.newData("business.003503", "business.0035", "所属行业", "HKHT", "航空航天", 1, 1,3);
		this.newData("business.003504", "business.0035", "所属行业", "XDJT", "现代交通", 1, 1,3);
		this.newData("business.003505", "business.0035", "所属行业", "SWYY", "生物医药与医疗器械", 1, 1,3);
		this.newData("business.003506", "business.0035", "所属行业", "XCL", "新材料", 1, 1,3);
		this.newData("business.003507", "business.0035", "所属行业", "XLY", "新能源与节能", 1, 1,3);
		this.newData("business.003508", "business.0035", "所属行业", "HJBH", "环境保护", 1, 1,3);
		this.newData("business.003509", "business.0035", "所属行业", "DQKJ", "地球空间与海洋", 1, 1,3);
		this.newData("business.003510", "business.0035", "所属行业", "HYY", "核应用技术", 1, 1,3);
		this.newData("business.003511", "business.0035", "所属行业", "XDNY", "现代农业", 1, 1,3);
		this.newData("business.003512", "business.0035", "所属行业", "QT", "其它", 1, 1,3);
		
		
		this.newData("business.0036", null, "客户关系管理", "CustomerScanType", "客户信息扫描件类型", 1, 1,36);
		this.newData("business.003601", "business.0036", "客户关系管理", "YYZZ", "营业执照", 1, 1,1);
		this.newData("business.003602", "business.0036", "客户关系管理", "SWDJZ", "税务登记证", 1, 1,2);
		this.newData("business.003603", "business.0036", "客户关系管理", "ZZJGDMZ", "组织机构代码证", 1, 1,3);
		this.newData("business.003604", "business.0036", "客户关系管理", "KHXKZ", "客户许可证", 1, 1,4);
		this.newData("business.003605", "business.0036", "客户关系管理", "FRSFZ", "法人身份证", 1, 1,5);
		return r;
		*/
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

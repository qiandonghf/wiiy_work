package com.wiiy.common.system;

import java.util.ArrayList;
import java.util.List;

import com.wiiy.core.entity.DataDict;

public class DataDictInit {
	private List<DataDict> list=new ArrayList<DataDict>();
	public boolean init(){
		return true;
		
		/* delete by Jonathan 20150303
		boolean r=true;
		
		this.newData("project.0001", null, "项目", "CURRENCY_TYPE", "货币种类", 1, 1,1);
		this.newData("project.000101", "project.0001", "项目", "CURRENCY_TYPE", "人民币", 1, 1,1);
		this.newData("project.000102", "project.0001", "项目", "CURRENCY_TYPE", "港元", 1, 1,2);
		this.newData("project.000103", "project.0001", "项目", "CURRENCY_TYPE", "台币", 1, 1,3);
		this.newData("project.000104", "project.0001", "项目", "CURRENCY_TYPE", "美元", 1, 1,4);
		this.newData("project.000105", "project.0001", "项目", "CURRENCY_TYPE", "欧元", 1, 1,5);
		this.newData("contract.0002", null, "合同", "CATEGORY", "合同类别", 1, 1, 2);
		this.newData("contract.000201", "contract.0002", "合同", "CATEGORY", "商务合同", 1, 1, 1);
		this.newData("contract.000202", "contract.0002", "合同", "CATEGORY", "租赁合同", 1, 1, 2);
		this.newData("contract.000203", "contract.0002", "合同", "CATEGORY", "聘用合同", 1, 1, 3);
		this.newData("contract.000204", "contract.0002", "合同", "CATEGORY", "采购合同", 1, 1, 4);
		this.newData("contract.000205", "contract.0002", "合同", "CATEGORY", "销售合同", 1, 1, 5);
		
		this.newData("customer.0001", null, "客商管理", "TECHNIC_TYPE", "技术领域", 1, 1,1);
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
		
		this.newData("customer.0003", null, "客商管理", "CONTECTTYPE", "交往类型", 1, 1,3);
		this.newData("customer.000301", "customer.0003", "客商管理", "VISIT", "拜访", 1, 1,1);
		this.newData("customer.000302", "customer.0003", "客商管理", "RECOMMEND", "引荐", 1, 1,2);
		this.newData("customer.000303", "customer.0003", "客商管理", "METTING", "会晤", 1, 1,3);
		
		this.newData("customer.0004", null, "客商管理", "REGISTER_TYPE", "注册类型", 1, 1,4);
		this.newData("customer.000401", "customer.0004", "客商管理", "REGISTER_TYPE", "内资-国有企业", 1, 1,1);
		this.newData("customer.000402", "customer.0004", "客商管理", "REGISTER_TYPE", "内资-集体企业", 1, 1,2);
		this.newData("customer.000403", "customer.0004", "客商管理", "REGISTER_TYPE", "内资-股份合作企业", 1, 1,3);
		this.newData("customer.000404", "customer.0004", "客商管理", "REGISTER_TYPE", "内资-私营企业", 1, 1,4);
		this.newData("customer.000405", "customer.0004", "客商管理", "REGISTER_TYPE", "内资-股份有限公司", 1, 1,5);
		this.newData("customer.000406", "customer.0004", "客商管理", "REGISTER_TYPE", "内资-有限责任公司", 1, 1,6);
		this.newData("customer.000407", "customer.0004", "客商管理", "REGISTER_TYPE", "内资-联营企业", 1, 1,7);
		this.newData("customer.000408", "customer.0004", "客商管理", "REGISTER_TYPE", "港澳台-合资经营企业", 1, 1,8);
		this.newData("customer.000409", "customer.0004", "客商管理", "REGISTER_TYPE", "港澳台-合作经营企业", 1, 1,9);
		this.newData("customer.000410", "customer.0004", "客商管理", "REGISTER_TYPE", "港澳台-独资经营企业", 1, 1,10);
		this.newData("customer.000411", "customer.0004", "客商管理", "REGISTER_TYPE", "港澳台-股份有限公司", 1, 1,11);
		this.newData("customer.000412", "customer.0004", "客商管理", "REGISTER_TYPE", "外资-中外合资", 1, 1,12);
		this.newData("customer.000413", "customer.0004", "客商管理", "REGISTER_TYPE", "外资-中外合作", 1, 1,13);
		this.newData("customer.000414", "customer.0004", "客商管理", "REGISTER_TYPE", "外资-外商独资", 1, 1,14);
		this.newData("customer.000415", "customer.0004", "客商管理", "REGISTER_TYPE", "外资-股份有限公司", 1, 1,15);
		
		this.newData("customer.0005", null, "客商管理", "DOCUMENT_TYPE", "证件类型", 1, 1,5);
		this.newData("customer.000501", "customer.0005", "客商管理", "DOCUMENT_TYPE", "身份证", 1, 1,1);
		this.newData("customer.000502", "customer.0005", "客商管理", "DOCUMENT_TYPE", "护照", 1, 1,2);
		
		this.newData("room.0001", null, "房源管理", "ROOM_DIRECTION", "房间朝向", 1, 1,6);
		this.newData("room.000101", "room.0001", "房源管理", "DIRECTION1", "南", 1, 1,1);
		this.newData("room.000102", "room.0001", "房源管理", "DIRECTION2", "北", 1, 1,2);
		this.newData("room.000103", "room.0001", "房源管理", "DIRECTION3", "南北", 1, 1,3);
		
		this.newData("room.0002", null, "房源管理", "ROOM_TYPE", "户型编号", 1, 1,7);
		this.newData("room.000201", "room.0002", "房源管理", "TYPE1", "A户型", 1, 1,1);
		this.newData("room.000202", "room.0002", "房源管理", "TYPE2", "B户型", 1, 1,2);
		this.newData("room.000203", "room.0002", "房源管理", "TYPE3", "C户型", 1, 1,3);
		
		this.newData("room.0003", null, "房源管理", "ROOM_TYPE", "房屋户型", 1, 1,8);
		this.newData("room.000301", "room.0003", "房源管理", "TYPE1", "三室两厅", 1, 1,1);
		this.newData("room.000302", "room.0003", "房源管理", "TYPE2", "两室两厅", 1, 1,2);
		this.newData("room.000303", "room.0003", "房源管理", "TYPE3", "两室一厅", 1, 1,3);
		
		this.newData("estate.0010", null, "物业管理", "FIXREPORT_TYPE", "报修类型", 1, 1,10);
		this.newData("estate.001001", "estate.0010", "物业管理", "TYPE1", "空调报修", 1, 1,1);
		this.newData("estate.001002", "estate.0010", "物业管理", "TYPE2", "网络报修", 1, 1,2);
		this.newData("estate.001003", "estate.0010", "物业管理", "TYPE3", "墙体报修", 1, 1,3);
		this.newData("estate.0011", null, "物业管理", "FIXREPORT_METHOD", "报修方式", 1, 1,11);
		this.newData("estate.001101", "estate.0011", "物业管理", "NETWORK", "网络报修", 1, 1,1);
		this.newData("estate.001102", "estate.0011", "物业管理", "PHONE", "电话报修", 1, 1,2);
		this.newData("estate.001103", "estate.0011", "物业管理", "SCENCE", "现场报修", 1, 1,3);
		
		this.newData("pb.0001", null, "园区管理", "BUILDING_TYPE", "楼宇类型", 1, 1,1);
		this.newData("pb.000101", "pb.0001", "园区管理", "BUILDING_TYPE", "办公楼", 1, 1,1);
		this.newData("pb.000102", "pb.0001", "园区管理", "BUILDING_TYPE", "厂房", 1, 1,2);
		this.newData("pb.000103", "pb.0001", "园区管理", "BUILDING_TYPE", "宿舍公寓", 1, 1,3);
		this.newData("pb.000104", "pb.0001", "园区管理", "BUILDING_TYPE", "混合型", 1, 1,4);
		this.newData("pb.000105", "pb.0001", "园区管理", "BUILDING_TYPE", "其他", 1,1, 5);
		
		this.newData("pb.0002", null, "园区管理", "BUILDING_KIND", "楼宇性质", 1, 1,2);
		this.newData("pb.000201", "pb.0002", "园区管理", "BUILDING_KIND", "自营楼宇", 1, 1,1);
		this.newData("pb.000202", "pb.0002", "园区管理", "BUILDING_KIND", "托管楼宇", 1, 1,2);
		this.newData("pb.000203", "pb.0002", "园区管理", "BUILDING_KIND", "其他", 1, 1,3);
		
		this.newData("pb.0003", null, "园区管理", "INVEST_DIRECTION", "招商方向", 1, 1,3);
		this.newData("pb.000301", "pb.0003", "园区管理", "INVEST_DIRECTION", "旅游产业", 1, 1,1);
		this.newData("pb.000302", "pb.0003", "园区管理", "INVEST_DIRECTION", "文化创意产业", 1, 1,2);
		this.newData("pb.000303", "pb.0003", "园区管理", "INVEST_DIRECTION", "金融服务业", 1, 1,3);
		this.newData("pb.000304", "pb.0003", "园区管理", "INVEST_DIRECTION", "商贸物流业", 1, 1,4);
		this.newData("pb.000305", "pb.0003", "园区管理", "INVEST_DIRECTION", "信息服务与软件业", 1, 1,5);
		this.newData("pb.000306", "pb.0003", "园区管理", "INVEST_DIRECTION", "中介服务业", 1, 1,6);
		this.newData("pb.000307", "pb.0003", "园区管理", "INVEST_DIRECTION", "房地产业", 1, 1,7);
		this.newData("pb.000308", "pb.0003", "园区管理", "INVEST_DIRECTION", "社区服务业", 1, 1,8);
		this.newData("pb.000309", "pb.0003", "园区管理", "INVEST_DIRECTION", "其他", 1, 1,9);
		
		this.newData("pb.0004", null, "园区管理", "AIRCON_SITUATION", "空调设施", 1, 1,4);
		this.newData("pb.000401", "pb.0004", "园区管理", "AIRCON_SITUATION", "独立空调", 1, 1,1);
		this.newData("pb.000402", "pb.0004", "园区管理", "AIRCON_SITUATION", "中央空调", 1, 1,2);
		this.newData("pb.000403", "pb.0004", "园区管理", "AIRCON_SITUATION", "自配空调", 1, 1,3);

		this.newData("pb.0005", null, "园区管理", "DECORATION_SITUATION", "装修情况", 1, 1,5);
		this.newData("pb.000501", "pb.0005", "园区管理", "DECORATION_SITUATION", "未装", 1, 1,1);
		this.newData("pb.000502", "pb.0005", "园区管理", "DECORATION_SITUATION", "简装", 1, 1,2);
		this.newData("pb.000503", "pb.0005", "园区管理", "DECORATION_SITUATION", "精装", 1, 1,3);

		
		this.newData("pb.0006", null, "园区管理", "ROOM_TYPE", "房间用途", 1, 1,6);
		this.newData("pb.000601", "pb.0006", "园区管理", "ROOM_TYPE", "办公", 1, 1,1);
		this.newData("pb.000602", "pb.0006", "园区管理", "ROOM_TYPE", "厂房", 1, 1,2);
		this.newData("pb.000603", "pb.0006", "园区管理", "ROOM_TYPE", "宿舍", 1, 1,3);
		this.newData("pb.000699", "pb.0006", "园区管理", "ROOM_TYPE", "其他", 1, 1,99);
		
		this.newData("pb.0007", null, "园区管理", "ROOM_KIND", "房间性质", 1, 1,7);
		this.newData("pb.000701", "pb.0007", "园区管理", "ROOM_KIND", "出租", 1, 1,1);
		this.newData("pb.000702", "pb.0007", "园区管理", "ROOM_KIND", "出售", 1, 1,2);
		this.newData("pb.000703", "pb.0007", "园区管理", "ROOM_KIND", "自用", 1, 1,3);
		this.newData("pb.000704", "pb.0007", "园区管理", "ROOM_KIND", "共用", 1, 1,4);
		this.newData("pb.000799", "pb.0007", "园区管理", "ROOM_KIND", "其他", 1, 1,99);
		
		this.newData("pb.0008", null, "园区管理", "CHANGE_TYPE", "变更类型", 1, 1,8);
		this.newData("pb.000801", "pb.0008", "园区管理", "CHANGE_TYPE", "房屋合并", 1, 1,1);
		this.newData("pb.000802", "pb.0008", "园区管理", "CHANGE_TYPE", "房屋拆分", 1, 1,2);
		this.newData("pb.000803", "pb.0008", "园区管理", "CHANGE_TYPE", "水电气表变更", 1, 1,3);
		this.newData("pb.000899", "pb.0008", "园区管理", "CHANGE_TYPE", "其他", 1, 1,99);
		
		this.newData("pb.0009", null, "园区管理", "FACILITY_TYPE", "设施类型", 1, 1,9);
		this.newData("pb.000901", "pb.0009", "园区管理", "FACILITY_TYPE", "网络", 1, 1,1);
		this.newData("pb.000902", "pb.0009", "园区管理", "FACILITY_TYPE", "电梯", 1, 1,2);
		this.newData("pb.000903", "pb.0009", "园区管理", "FACILITY_TYPE", "车位", 1, 1,3);
		this.newData("pb.000904", "pb.0009", "园区管理", "FACILITY_TYPE", "广告位", 1, 1,4);
		this.newData("pb.000905", "pb.0009", "园区管理", "FACILITY_TYPE", "会议室", 1, 1,5);
		
		
		this.newData("pb.0010", null, "园区管理", "FIXREPORT_TYPE", "报修类型", 1, 1,10);
		this.newData("pb.001001", "pb.0010", "园区管理", "TYPE1", "空调报修", 1, 1,1);
		this.newData("pb.001002", "pb.0010", "园区管理", "TYPE2", "网络报修", 1, 1,2);
		this.newData("pb.001003", "pb.0010", "园区管理", "TYPE3", "墙体报修", 1, 1,3);
		this.newData("pb.0011", null, "园区管理", "FIXREPORT_METHOD", "报修方式", 1, 1,11);
		this.newData("pb.001101", "pb.0011", "园区管理", "NETWORK", "网络报修", 1, 1,1);
		this.newData("pb.001102", "pb.0011", "园区管理", "PHONE", "电话报修", 1, 1,2);
		this.newData("pb.001103", "pb.0011", "园区管理", "SCENCE", "现场报修", 1, 1,3);
		
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

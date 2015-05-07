package com.wiiy.pf.system;

import java.util.ArrayList;
import java.util.List;

import com.wiiy.core.entity.DataDict;

public class DataDictInit {
	private List<DataDict> list=new ArrayList<DataDict>();
	public boolean init(){
		boolean r=true;
/*		
		this.newData("pf.0001", null, "流程管理系统", "WorkflowType", "应用类别", 1, 1,3);
		this.newData("pf.000101", "pf.0001", "流程管理系统", "Executive", "行政类", 1, 1,1);
		this.newData("pf.000102", "pf.0001", "流程管理系统", "Executive", "财务类", 1, 1,2);
		this.newData("pf.000103", "pf.0001", "流程管理系统", "Executive", "园区管理类", 1, 1,3);*/
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
	
	public List<DataDict> getListByDataName(String dataName) {
		init();
		List<DataDict> dataDicts = new ArrayList<DataDict>();
		String pid = null;
		for (DataDict dd : list) {
			if (dd.getDataName().equals(dataName) && dd.getParentId() == null) {
				pid = dd.getId();
				continue;
			}
			if (pid != null && dd.getParentId().endsWith(pid)) {
				dataDicts.add(dd);
			}
		}
		return dataDicts;
	}
	
}

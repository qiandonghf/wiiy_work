package com.wiiy.estate.action;

import java.io.ByteArrayInputStream;



import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.jstl.core.Config;

import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.POIUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyCategory;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyPurchaseConfig;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.estate.entity.SupplyStockIn;
import com.wiiy.estate.service.SupplyPurchaseConfigService;
import com.wiiy.estate.service.SupplyPurchaseFormService;
import com.wiiy.estate.service.SupplyPurchaseService;
import com.wiiy.estate.service.SupplyService;
import com.wiiy.estate.service.SupplyStockInService;

public class SupplyStockInAction extends JqGridBaseAction<SupplyStockIn>{

	private SupplyStockInService supplyStockInService;
	private SupplyService supplyService;
	private SupplyStockIn supplyStockIn;
	private Supply supply;
	private Result result;
	private Long id;
	private String ids;
	private String excelName;
	private InputStream inputStream;
	private String columns;
	private List<SupplyPurchaseForm> supplyPurchaseForms;
	private List<SupplyPurchaseForm> supplyPurchaseLabel;
	private SupplyPurchaseService supplyPurchaseService;
	private SupplyPurchaseFormService supplyPurchaseFormService;
	private SupplyPurchaseConfigService supplyPurchaseConfigService;
	private List<SupplyPurchaseConfig> configList;
	private List<SupplyPurchase> supplyPurchaseList;
	private List<Supply> supplyList;
	
	public String add(){
		return "add";
	}
	
	public String select(){
		Filter filter = new Filter(SupplyPurchaseForm.class);
		supplyPurchaseForms = supplyPurchaseFormService.getListByFilter(filter).getValue();
		supplyPurchaseLabel = supplyPurchaseFormService.getListByFilter(new Filter(SupplyCategory.class).notNull("parentId")).getValue();
		result = supplyPurchaseFormService.getList();
		return SELECT;
	}
	public String export(){
		Filter filter = new Filter(SupplyStockIn.class);
		filter.createAlias("supply", "supply");
		page=0;//不要分页
		refresh(filter);
		excelName = StringUtil.URLEncoderToUTF8("办公用品入库")+".xls";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		POIUtil.export("办公用品入库列表", generateExportColumns(columns), root, out);
		inputStream = new ByteArrayInputStream(out.toByteArray());
		return "export";
	}

	private LinkedHashMap<String, String> generateExportColumns(String columns){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		columns = columns.replace("{", "");
		columns = columns.replace("}", "");
		String[] properties = columns.split(",");
		for (String string : properties) {
			String[] ss = string.split(":");
			String field = ss[0].replace("\"", "");
			String description = ss[1].replace("\"", "");
			map.put(field, description);
		}
		return map;
	}
	public String save(){
		/*Filter filter = new Filter(SupplyPurchaseConfig.class);
		supply=supplyService.getBeanById(supplyStockIn.getSupplyId()).getValue();
		configList=supplyPurchaseConfigService.getListByFilter(filter.eq("supplyPurchaseFormId", supplyStockIn.getSupplyPurchaseFormId())).getValue();
		supplyPurchaseList=supplyPurchaseService.getList().getValue();
		Long[] ids =new Long[configList.size()];
		Long[] idss=new Long[configList.size()];
		int i=-1;
		for (SupplyPurchaseConfig config : configList) {
			i++;
			ids[i]=config.getSupplyPurchase().getSupplyId();
			idss[i]=config.getSupplyPurchaseId();
		}
		Filter filter1=new Filter(Supply.class);
		Filter filter2=new Filter(SupplyPurchase.class);
		List<Supply> list =supplyService.getListByFilter(filter1.in("id", ids)).getValue();
		List<SupplyPurchase> purchaseList=supplyPurchaseService.getListByFilter(filter2.in("id", idss)).getValue();
		result = supplyStockInService.save(supplyStockIn,list,purchaseList);
		supply=null;
		configList=null;
		supplyPurchaseList=null;*/
		String[] supplyIds = ServletActionContext.getRequest().getParameterValues("supplyIdList");
		String[] amounts = ServletActionContext.getRequest().getParameterValues("amountList");
		String[] prices = ServletActionContext.getRequest().getParameterValues("priceList");
		String[] usages = ServletActionContext.getRequest().getParameterValues("usageList");
		Long[] ids =new Long[supplyIds.length];
		for (int i = 0; i < supplyIds.length; i++) {
			ids[i]=Long.parseLong(supplyIds[i]);
		}
		Filter filter = new Filter (Supply.class);
		supplyList=supplyService.getListByFilter(filter.in("id", ids)).getValue();
		result = supplyStockInService.save(supplyList,supplyIds,amounts,prices,usages);
		
		return JSON;
	}
	public String delete(){
		if(id!=null){
			supplyStockIn=supplyStockInService.getBeanById(id).getValue();
			Filter filter1 = new Filter(SupplyPurchaseConfig.class);
			if(supplyStockIn.getSupplyId()!=null){
				supply=supplyService.getBeanById(supplyStockIn.getSupplyId()).getValue();
			}
			configList=supplyPurchaseConfigService.getListByFilter(filter1.eq("supplyPurchaseFormId", supplyStockIn.getSupplyPurchaseFormId())).getValue();
			supplyPurchaseList=supplyPurchaseService.getList().getValue();
			Long[] idss=new Long[configList.size()];
			int i=-1;
			for (SupplyPurchaseConfig config : configList) {
				i++;
				idss[i]=config.getSupplyPurchaseId();
			}
			Filter filter3=new Filter(SupplyPurchase.class);
			List<SupplyPurchase> purchaseList=supplyPurchaseService.getListByFilter(filter3.in("id", idss)).getValue();
			List<Supply> supplyList =supplyService.getList().getValue();
			
			result = supplyStockInService.deletes(supplyStockIn,supply,purchaseList,supplyList);
			configList=null;
			supplyStockIn=null;
			supply=null;
			supplyPurchaseList=null;
		}else if(ids!=null){
			result = supplyStockInService.deleteByIds(ids);
		}
		return JSON;
	}



	public String view(){
		supplyStockIn=supplyStockInService.getBeanById(id).getValue();
		if(supplyStockIn.getSupplyId()!=null && supplyStockIn.getSupplyPurchaseFormId()==null){
			result = supplyStockInService.getBeanById(id);
			return VIEW;
		}
		if(supplyStockIn.getSupplyPurchaseFormId()!=null){
			Filter filter = new Filter(SupplyPurchaseConfig.class);
			configList=supplyPurchaseConfigService.getListByFilter(filter.eq("supplyPurchaseFormId",supplyStockIn.getSupplyPurchaseFormId())).getValue();
			result = supplyStockInService.getBeanById(id);//3
			return "viewAll";
		}
		return null;
	}
	public String edit(){
		supplyStockIn=supplyStockInService.getBeanById(id).getValue();
		//if(supplyStockIn.getSupplyId()!=null && supplyStockIn.getSupplyPurchaseFormId()==null){
			result = supplyStockInService.getBeanById(id);
			return EDIT;
			//	return EDIT;
	//	}
		/*if(supplyStockIn.getSupplyPurchaseFormId()!=null){
			//Filter filter = new Filter(SupplyPurchaseConfig.class);
			//configList=supplyPurchaseConfigService.getListByFilter(filter.eq("supplyPurchaseFormId",supplyStockIn.getSupplyPurchaseFormId())).getValue();
			//result = supplyPurchaseFormService.getBeanById(id);//3
			result = supplyStockInService.getBeanById(id);
			return "editAll";
		}*/
	}
	public String update(){
		//获取原来表单的值
		//获取页面新的表单值
	SupplyStockIn dbean = supplyStockInService.getBeanById(supplyStockIn.getId()).getValue();
	BeanUtil.copyProperties(supplyStockIn, dbean);
	result = supplyStockInService.update(dbean);
		return JSON;
	}
	
	public String list(){
		result = supplyStockInService.getList();
		return LIST;
	}

	public String listAll(){
		Filter filter = new Filter(SupplyStockIn.class);
		return refresh(filter);
	}
	
	@Override
	protected List<SupplyStockIn> getListByFilter(Filter fitler) {
		return supplyStockInService.getListByFilter(fitler).getValue();
	}
	
	public void setSupplyStockInService(SupplyStockInService supplyStockInService) {
		this.supplyStockInService = supplyStockInService;
	}


	public SupplyStockIn getSupplyStockIn() {
		return supplyStockIn;
	}

	public void setSupplyStockIn(SupplyStockIn supplyStockIn) {
		this.supplyStockIn = supplyStockIn;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	public Supply getSupply() {
		return supply;
	}

	public void setSupply(Supply supply) {
		this.supply = supply;
	}

	public void setSupplyService(SupplyService supplyService) {
		this.supplyService = supplyService;
	}
	
	public String getExcelName() {
		return excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
	public List<SupplyPurchaseForm> getSupplyPurchaseForms() {
		return supplyPurchaseForms;
	}
	public void setSupplyPurchaseForms(List<SupplyPurchaseForm> supplyPurchaseForms) {
		this.supplyPurchaseForms = supplyPurchaseForms;
	}
	public List<SupplyPurchaseForm> getSupplyPurchaseLabel() {
		return supplyPurchaseLabel;
	}
	public void setSupplyPurchaseLabel(List<SupplyPurchaseForm> supplyPurchaseLabel) {
		this.supplyPurchaseLabel = supplyPurchaseLabel;
	}
	public void setSupplyPurchaseService(SupplyPurchaseService supplyPurchaseService) {
		this.supplyPurchaseService = supplyPurchaseService;
	}
	public void setSupplyPurchaseFormService(
			SupplyPurchaseFormService supplyPurchaseFormService) {
		this.supplyPurchaseFormService = supplyPurchaseFormService;
	}
	public void setSupplyPurchaseConfigService(
			SupplyPurchaseConfigService supplyPurchaseConfigService) {
		this.supplyPurchaseConfigService = supplyPurchaseConfigService;
	}
	public List<SupplyPurchaseConfig> getConfigList() {
		return configList;
	}
	public void setConfigList(List<SupplyPurchaseConfig> configList) {
		this.configList = configList;
	}
	public List<SupplyPurchase> getSupplyPurchaseList() {
		return supplyPurchaseList;
	}
	public void setSupplyPurchaseList(List<SupplyPurchase> supplyPurchaseList) {
		this.supplyPurchaseList = supplyPurchaseList;
	}
	public List<Supply> getSupplyList() {
		return supplyList;
	}
	public void setSupplyList(List<Supply> supplyList) {
		this.supplyList = supplyList;
	}


}

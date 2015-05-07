package com.wiiy.estate.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.POIUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyPurchaseConfig;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.estate.service.SupplyPurchaseConfigService;
import com.wiiy.estate.service.SupplyPurchaseFormService;
import com.wiiy.estate.service.SupplyPurchaseService;

/**
 * @author my
 */
public class SupplyPurchaseFormAction extends
		JqGridBaseAction<SupplyPurchaseForm> {

	private SupplyPurchaseFormService supplyPurchaseFormService;
	private Result result;
	private SupplyPurchaseForm supplyPurchaseForm;
	private Long id;
	private String ids;
	private SupplyPurchaseConfigService supplyPurchaseConfigService;
	private SupplyPurchaseConfig supplyPurchaseConfig;
	private SupplyPurchase supplyPurchase;
	private SupplyPurchaseService supplyPurchaseService;
	public List<SupplyPurchaseConfig> configList;
	public List<SupplyPurchase> supplyList;
	// 用于数据导出
	private String excelName;
	private InputStream inputStream;

	private String columns;

	/*
	 * 商品申购中的导出
	 */
	public String export() {
		Filter filter = new Filter(SupplyPurchaseConfig.class);
		filter.createAlias("supplyPurchaseForm", "supplyPurchaseForm");
		filter.createAlias("supplyPurchase", "supplyPurchase");
		filter.createAlias("supplyPurchase.supply", "supplyPurchase.supply");
		filter.createAlias("supplyPurchaseForm.requisitioner", "supplyPurchaseForm.requisitioner");
		configList = supplyPurchaseConfigService.getListByFilter(filter).getValue();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("supplyPurchase.supply.id", "商品ID");
		map.put("supplyPurchase.supply.name", "商品名称");
		map.put("supplyPurchase.supply.unit", "商品单位");
		map.put("supplyPurchase.price", "单价");
		map.put("supplyPurchase.amount", "申购数量");
		map.put("supplyPurchaseForm.requisitioner.realName", "申购人");
		map.put("supplyPurchaseForm.applyTime", "申购日期");
		map.put("supplyPurchase.usages", "产品用途");
		
		excelName = StringUtil.URLEncoderToUTF8("商品申购") + ".xls";
		POIUtil.export("商品申购列表", map, configList, out);
		inputStream = new ByteArrayInputStream(out.toByteArray());
		return "export";
	}

	private LinkedHashMap<String, String> generateExportColumns(String columns) {
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

	public String save() {
		result = supplyPurchaseFormService.save(supplyPurchaseForm);
		return JSON;
	}

	public String generateCode() {
		result = supplyPurchaseFormService.generateCode();
		return JSON;
	}

	// view 语句修改
	public String view() {
		Filter filter = new Filter(SupplyPurchaseConfig.class);
		// filter.include("supplyPurchaseFormId");
		filter.eq("supplyPurchaseFormId", id);
		configList = supplyPurchaseConfigService.getListByFilter(filter)
				.getValue();
		result = supplyPurchaseFormService.getBeanById(id);// 3
		return VIEW;
	}

	// 语句修改
	public String edit() {
		// Filter filter = new Filter(SupplyPurchaseConfig.class);
		// configList=supplyPurchaseConfigService.getListByFilter(filter.eq("supplyPurchaseFormId",id)).getValue();
		Filter filter = new Filter(SupplyPurchaseConfig.class);
		// filter.include("supplyPurchaseFormId");
		filter.eq("supplyPurchaseFormId", id);
		configList = supplyPurchaseConfigService.getListByFilter(filter)
				.getValue();
		result = supplyPurchaseFormService.getBeanById(id);
		return EDIT;
	}

	public String update() {
		SupplyPurchaseForm dbBean = supplyPurchaseFormService.getBeanById(
				supplyPurchaseForm.getId()).getValue();
		BeanUtil.copyProperties(supplyPurchaseForm, dbBean);
		result = supplyPurchaseFormService.update(dbBean);
		return JSON;
	}

	public String delete() {
		if (id != null) {
			result = supplyPurchaseFormService.deleteById(id);
		} else if (ids != null) {
			result = supplyPurchaseFormService.deleteByIds(ids);
		}
		return JSON;
	}

	public String list() {
		return refresh(new Filter(SupplyPurchaseForm.class).orderBy(
				"createTime", Filter.DESC));
	}

	@Override
	protected List<SupplyPurchaseForm> getListByFilter(Filter fitler) {
		return supplyPurchaseFormService.getListByFilter(fitler).getValue();
	}

	public SupplyPurchaseForm getSupplyPurchaseForm() {
		return supplyPurchaseForm;
	}

	public void setSupplyPurchaseForm(SupplyPurchaseForm supplyPurchaseForm) {
		this.supplyPurchaseForm = supplyPurchaseForm;
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

	public void setSupplyPurchaseFormService(
			SupplyPurchaseFormService supplyPurchaseFormService) {
		this.supplyPurchaseFormService = supplyPurchaseFormService;
	}

	public Result getResult() {
		return result;
	}

	public SupplyPurchaseConfig getSupplyPurchaseConfig() {
		return supplyPurchaseConfig;
	}

	public void setSupplyPurchaseConfig(
			SupplyPurchaseConfig supplyPurchaseConfig) {
		this.supplyPurchaseConfig = supplyPurchaseConfig;
	}

	public SupplyPurchase getSupplyPurchase() {
		return supplyPurchase;
	}

	public void setSupplyPurchase(SupplyPurchase supplyPurchase) {
		this.supplyPurchase = supplyPurchase;
	}

	public void setSupplyPurchaseConfigService(
			SupplyPurchaseConfigService supplyPurchaseConfigService) {
		this.supplyPurchaseConfigService = supplyPurchaseConfigService;
	}

	public void setSupplyPurchaseService(
			SupplyPurchaseService supplyPurchaseService) {
		this.supplyPurchaseService = supplyPurchaseService;
	}

	public List<SupplyPurchaseConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<SupplyPurchaseConfig> configList) {
		this.configList = configList;
	}

	public List<SupplyPurchase> getSupplyList() {
		return supplyList;
	}

	public void setSupplyList(List<SupplyPurchase> supplyList) {
		this.supplyList = supplyList;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
	
	public String getExcelName() {
		return excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getColumns() {
		return columns;
	}

}

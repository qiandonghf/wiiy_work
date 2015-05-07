package com.wiiy.estate.action;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.POIUtil;
import com.wiiy.commons.util.StringUtil;

import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.estate.dto.SupplyStockInDto;
import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyApply;
import com.wiiy.estate.entity.SupplyCategory;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyStockIn;
import com.wiiy.estate.service.SupplyApplyService;
import com.wiiy.estate.service.SupplyCategoryService;
import com.wiiy.estate.service.SupplyPurchaseService;
import com.wiiy.estate.service.SupplyService;
import com.wiiy.estate.service.SupplyStockInService;


public class SupplyAction extends JqGridBaseAction<Supply>{
	
	private SupplyService supplyService;
	private SupplyStockInService supplyStockInService;
	private SupplyPurchaseService supplyPurchaseService;
	private SupplyApplyService supplyApplyService;
	private SupplyCategoryService supplyCategoryService;
	private Supply supply;
	private SupplyCategory supplyCategory;
	private List<SupplyCategory> supplyCategorys;
	private List<SupplyCategory> supplyCategoryLabel;
	private Result result;
	private Long id;
	private String ids;
	private List<SupplyStockIn> supplyStockIns;
	private List<SupplyPurchase> supplyPurchases;
	private List<SupplyApply> supplyApplies;
	private List<SupplyStockInDto> dtoList;
	private String isStockIn;
	
	private String excelName;
	private InputStream inputStream;
	private String columns;
	public String stockInRemind(){
		String sql="SELECT count(id) FROM estate_supply WHERE alarm='YES' AND stock < alarm_stock";
		List<Object> list=supplyService.getListBySql(sql);
		int num=Integer.parseInt(list.get(0).toString());
		result=Result.value(num);
		return JSON;
	}
	public String check(){
		//数据过滤传回JSP页面
		return "check";
	}
	
	public String inList(){
		dtoList=new ArrayList<SupplyStockInDto>();
		Filter filter1 = new Filter(SupplyPurchase.class);
		filter1.eq("supplyId",id);
		supplyPurchases=supplyPurchaseService.getListByFilter(filter1).getValue();
		Filter filter=new Filter(SupplyStockIn.class);
		filter.eq("supplyId", id);
		supplyStockIns=supplyStockInService.getListByFilter(filter).getValue();
		if(supplyPurchases.size()>0){
			for (SupplyPurchase list1 : supplyPurchases) {
				SupplyStockInDto dto=new SupplyStockInDto();
				dto.setAmount(list1.getAmount());
				dto.setInTime(list1.getApplyTime());//取stockin里的进货时间
				dto.setBuyMan(list1.getPurchaser());//取stockin里的购买人
				dtoList.add(dto);
			}
		}
		if(supplyStockIns.size()>0){
			for (SupplyStockIn list2 : supplyStockIns) {
				SupplyStockInDto dto=new SupplyStockInDto();
				dto.setAmount(list2.getAmount());
				dto.setInTime(list2.getInTime());
				dto.setBuyMan(list2.getPurchaser());
				dtoList.add(dto);
			}
		}
		result=Result.value(dtoList);
		return JSON;
	//	return refresh(filter);
	
	}
	
	public String outList(){
		Filter filter =new Filter(SupplyApply.class);
		filter.eq("supplyId",id);
		return refresh(filter);
	}
	
	public String export(){
		Filter filter = new Filter(Supply.class);
		filter.createAlias("category", "category");
		page=0;//不要分页
		refresh(filter);
		excelName = StringUtil.URLEncoderToUTF8("办公用品管理")+".xls";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		POIUtil.export("办公用品列表", generateExportColumns(columns), root, out);
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
	

	public String loadSupplyByCategoryId(){
		Filter filter = new Filter(Supply.class).eq("categoryId", id);
		return refresh(filter);
	}
	
	public String add(){
		supplyCategorys = supplyCategoryService.getListByFilter(new Filter(SupplyCategory.class).notNull("parentId")).getValue();
		return "add";
	}
	
	public String save(){
		if(supply.getAlarm()!=null && supply.getAlarm().equals(BooleanEnum.NO)){
			supply.setAlarmStock(0.0);
		}
		if(supply.getStock()==null){
			supply.setStock(0.0);
		}
		result = supplyService.save(supply);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = supplyService.deleteById(id);
		}else if(ids!=null){
			result = supplyService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String edit(){
		supplyCategorys = supplyCategoryService.getListByFilter(new Filter(SupplyCategory.class).notNull("parentId")).getValue();
		result = supplyService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		if(supply.getAlarm()!=null && supply.getAlarm().equals(BooleanEnum.NO)){
			supply.setAlarmStock(0.0);
		}
		if(supply.getStock()==null){
			supply.setStock(0.0);
		}
		Supply dbBean = supplyService.getBeanById(supply.getId()).getValue();
		BeanUtil.copyProperties(supply, dbBean);
		result = supplyService.update(dbBean);
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(SupplyCategory.class).isNull("parentId");
		supplyCategorys = supplyCategoryService.getListByFilter(filter).getValue();
		supplyCategoryLabel = supplyCategoryService.getListByFilter(new Filter(SupplyCategory.class).notNull("parentId")).getValue();
		result = supplyCategoryService.getList();
		return LIST;
	}
	
	public String listAll(){
		Filter filter = new Filter(Supply.class);
		if(isStockIn!=null&&!"".equals(isStockIn)){
			String sql="SELECT id FROM estate_supply WHERE alarm='YES' AND stock < alarm_stock";
			List<Object> list=supplyService.getListBySql(sql);
			Long[] ids=new Long[list.size()];
			int i=0;
			for (Object li : list) {
				ids[i]=Long.parseLong(li.toString());
				i++;
			}
			filter.in("id", ids);
				
			return refresh(filter);
		}
		return refresh(filter);
	}
	
	public String view(){
		result = supplyService.getBeanById(id);
		return VIEW;
	}
	
	public String select(){
		Filter filter = new Filter(SupplyCategory.class).isNull("parentId");
		supplyCategorys = supplyCategoryService.getListByFilter(filter).getValue();
		supplyCategoryLabel = supplyCategoryService.getListByFilter(new Filter(SupplyCategory.class).notNull("parentId")).getValue();
		result = supplyCategoryService.getList();
		return SELECT;
	}
	
	public String loadSupply(){
		Filter filter = new Filter(Supply.class);
		filter.eq("id", id);
		return refresh(filter);
	}

	@Override
	protected List<Supply> getListByFilter(Filter fitler) {
		return supplyService.getListByFilter(fitler).getValue();
	}

	
	public Supply getSupply() {
		return supply;
	}

	public void setSupply(Supply supply) {
		this.supply = supply;
	}

	public Result getResult() {
		return result;
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
	public SupplyService getSupplyService() {
		return supplyService;
	}

	public void setSupplyService(SupplyService supplyService) {
		this.supplyService = supplyService;
	}


	public SupplyCategoryService getSupplyCategoryService() {
		return supplyCategoryService;
	}


	public void setSupplyCategoryService(SupplyCategoryService supplyCategoryService) {
		this.supplyCategoryService = supplyCategoryService;
	}
	
	public List<SupplyCategory> getSupplyCategorys() {
		return supplyCategorys;
	}


	public void setSupplyCategorys(List<SupplyCategory> supplyCategorys) {
		this.supplyCategorys = supplyCategorys;
	}


	public void setResult(Result result) {
		this.result = result;
	}

	public SupplyCategory getSupplyCategory() {
		return supplyCategory;
	}

	public void setSupplyCategory(SupplyCategory supplyCategory) {
		this.supplyCategory = supplyCategory;
	}

	public List<SupplyCategory> getSupplyCategoryLabel() {
		return supplyCategoryLabel;
	}

	public void setSupplyCategoryLabel(List<SupplyCategory> supplyCategoryLabel) {
		this.supplyCategoryLabel = supplyCategoryLabel;
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

	public List<SupplyStockIn> getSupplyStockIns() {
		return supplyStockIns;
	}

	public void setSupplyStockIns(List<SupplyStockIn> supplyStockIns) {
		this.supplyStockIns = supplyStockIns;
	}

	public List<SupplyApply> getSupplyApplies() {
		return supplyApplies;
	}

	public void setSupplyApplies(List<SupplyApply> supplyApplies) {
		this.supplyApplies = supplyApplies;
	}

	public void setSupplyStockInService(SupplyStockInService supplyStockInService) {
		this.supplyStockInService = supplyStockInService;
	}

	public void setSupplyApplyService(SupplyApplyService supplyApplyService) {
		this.supplyApplyService = supplyApplyService;
	}

	public List<SupplyPurchase> getSupplyPurchases() {
		return supplyPurchases;
	}

	public void setSupplyPurchases(List<SupplyPurchase> supplyPurchases) {
		this.supplyPurchases = supplyPurchases;
	}

	public void setSupplyPurchaseService(SupplyPurchaseService supplyPurchaseService) {
		this.supplyPurchaseService = supplyPurchaseService;
	}

	public List<SupplyStockInDto> getDtoList() {
		return dtoList;
	}

	public void setDtoList(List<SupplyStockInDto> dtoList) {
		this.dtoList = dtoList;
	}
	public String getIsStockIn() {
		return isStockIn;
	}
	public void setIsStockIn(String isStockIn) {
		this.isStockIn = isStockIn;
	}


}

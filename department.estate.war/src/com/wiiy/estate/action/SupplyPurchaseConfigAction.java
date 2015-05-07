package com.wiiy.estate.action;

import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;




import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;



import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.entity.TreeEntity;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.TreeUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dto.SupplyDto;
import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyCategory;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyPurchaseConfig;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.estate.service.SupplyCategoryService;
import com.wiiy.estate.service.SupplyPurchaseConfigService;
import com.wiiy.estate.service.SupplyPurchaseFormService;
import com.wiiy.estate.service.SupplyPurchaseService;

/**
 * @author my
 */
public class SupplyPurchaseConfigAction extends JqGridBaseAction<SupplyPurchaseConfig>{
	
	private SupplyPurchaseConfigService supplyPurchaseConfigService;
	private Result result;
	private SupplyPurchaseConfig supplyPurchaseConfig;
	private SupplyPurchaseForm supplyPurchaseForm;
	private SupplyPurchaseFormService supplyPurchaseFormService;
	private SupplyPurchase supplyPurchase;
	private SupplyPurchaseService supplyPurchaseService;
	private String configCheck;
	private Long id;
	private String ids;
	private User user;
	private String supplyList;
	private List<SupplyCategory> supplyCategorys;
	private SupplyCategoryService supplyCategoryService;
	private List<SupplyCategory> supplyCategoryLabel;
	private List<SupplyPurchaseConfig> configList;
	public String searchSupply(){
		String sql="SELECT supply_id,price,amount,usages FROM estate_supply_purchase WHERE id IN(SELECT supplyPurchase_id FROM estate_supply_purchase_config WHERE supplyPurchaseForm_id = "+id+")";
		List<Object> list=supplyPurchaseConfigService.getListBySql(sql);
		
		List<SupplyDto> dtoList = new ArrayList<SupplyDto>();
		for(Object li : list){//每个商品信息
			Object[] obj=(Object[])li;
			SupplyDto dto = new SupplyDto();
			
			String sql2= "SELECT id,name,unit FROM estate_supply WHERE id ="+Long.parseLong(obj[0].toString())+"";
			List<Object> list2=supplyPurchaseConfigService.getListBySql(sql2);
			for(Object li2 :list2){//当前商品
				Object[] obj2 =(Object[])li2;
				if(Long.parseLong(obj[0].toString())==Long.parseLong(obj2[0].toString())){
					dto.setId(Long.parseLong(obj2[0].toString()));
					dto.setSupply(String.valueOf(obj2[1].toString()));
					dto.setUnit(String.valueOf(obj2[2].toString()));
					break;
				}
 			}
			dto.setPrice(Double.parseDouble(obj[1].toString()));
			dto.setAmount(Double.parseDouble(obj[2].toString()));
			dto.setUsage(String.valueOf(obj[3].toString()));
			dtoList.add(dto);
		}
		result=Result.value(dtoList);
		return JSON;
	}
	
	public SupplyCategoryService getSupplyCategoryService(){
		return supplyCategoryService;
	}

	public String add(){
		Filter filter = new Filter(SupplyCategory.class).isNull("parentId");
		supplyCategorys = supplyCategoryService.getListByFilter(filter).getValue();
		supplyCategoryLabel = supplyCategoryService.getListByFilter( //
				new Filter(SupplyCategory.class).notNull("parentId")).getValue();
		result = supplyCategoryService.getList();
		return "add";
	}

	public String save(){
		if(supplyPurchaseConfig==null)supplyPurchaseConfig = new SupplyPurchaseConfig();
		if(supplyPurchaseForm==null)supplyPurchaseForm = new SupplyPurchaseForm();
		if(supplyPurchase==null)supplyPurchase = new SupplyPurchase();
		else{
			if(("").equals(supplyPurchase.getSupplyId()))supplyPurchase.setSupplyId(null);
			if(("").equals(supplyPurchaseForm.getRequisitionerId()))supplyPurchaseForm.setRequisitionerId(null);
		}
		String[] supplyIds = ServletActionContext.getRequest().getParameterValues("supplyIdList");
		String[] amounts = ServletActionContext.getRequest().getParameterValues("amountList");
		String[] prices = ServletActionContext.getRequest().getParameterValues("priceList");
		String[] usages = ServletActionContext.getRequest().getParameterValues("usageList");
		result = supplyPurchaseConfigService.save(supplyPurchaseForm,supplyPurchase,supplyPurchaseConfig,supplyIds,amounts,prices,usages);
		return JSON;
	}
	
	public String view(){
	//	result = supplyPurchaseConfigService.getBeanById(id);
		result=supplyPurchaseService.getList();
		return VIEW;
	}
	
	public SupplyPurchaseForm getSupplyPurchaseForm() {
		return supplyPurchaseForm;
	}
	public SupplyPurchase getSupplyPurchase() {
		return supplyPurchase;
	}
	public String edit(){
		result=supplyPurchaseService.getBeanById(supplyPurchaseConfig.getId());
//		result =supplyPurchaseConfigService.getBeanById(id);
		return EDIT;
	}
	private List<SupplyPurchase> backListFromJSON(Object json) {
		List<SupplyPurchase> supplyLists = new ArrayList<SupplyPurchase>();
		if(json != null){
			JSONArray jsonArray = JSONArray.fromObject(json);
			if (jsonArray.size() > 0) {
				for (int i = 0; i <jsonArray.size(); i++) {
					JSONObject o = jsonArray.getJSONObject(i);
					String supplyIds = o.getString("supplyIds");
					String pricelist = o.getString("price");
					String amountlist = o.getString("amount");
					String usageslist = o.getString("usages");
					try {
						supplyIds = URLDecoder.decode(supplyIds, "utf-8");
						pricelist = URLDecoder.decode(pricelist, "utf-8");
						amountlist = URLDecoder.decode(amountlist, "utf-8");
						usageslist = URLDecoder.decode(usageslist, "utf-8");
						//oldName = URLDecoder.decode(oldName, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					SupplyPurchase a = new SupplyPurchase();
					a.setId(Long.valueOf(supplyIds));
					a.setPrice(Double.valueOf(pricelist));
					a.setAmount(Double.valueOf(amountlist));
					a.setUsages(usageslist);
					supplyLists.add(a);
				}
			}
		}
		return supplyLists;
	}
	public String update(){
		List<SupplyPurchase> attList=backListFromJSON(supplyList);//当前页面传回来的2数据
		configList=supplyPurchaseConfigService.getListByFilter(new Filter(SupplyPurchaseConfig.class).eq("supplyPurchaseFormId",supplyPurchaseForm.getId())).getValue();
		result = supplyPurchaseConfigService.update(configList,attList,supplyPurchaseForm);
		configList=null;
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = supplyPurchaseConfigService.deleteById(id);
		} else if(ids!=null){
			result = supplyPurchaseConfigService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		fetchDepth = 2;
		Filter filter=new Filter(SupplyPurchaseForm.class);
//		filter.createAlias("supplyPurchase","supplyPurchase");
//		filter.createAlias("supplyPurchase.supply","supplyPurchase.supply");
//		filter.createAlias("supplyPurchaseForm","supplyPurchaseForm");
//		filter.createAlias("supplyPurchaseForm.requisitioner","supplyPurchaseForm.requisitioner");
			return refresh(filter);
	}
	public String getConfigCheck() {
		return configCheck;
	}

	public void setConfigCheck(String configCheck) {
		this.configCheck = configCheck;
	}

	@Override
	protected List<SupplyPurchaseConfig> getListByFilter(Filter fitler) {
		return supplyPurchaseConfigService.getListByFilter(fitler).getValue();
	}
	
	
	public SupplyPurchaseConfig getSupplyPurchaseConfig() {
		return supplyPurchaseConfig;
	}
	
	public void setSupplyPurchaseForm(SupplyPurchaseForm supplyPurchaseForm) {
		this.supplyPurchaseForm = supplyPurchaseForm;
	}
	public void setSupplyPurchase(SupplyPurchase supplyPurchase) {
		this.supplyPurchase = supplyPurchase;
	}

	public void setSupplyPurchaseConfig(SupplyPurchaseConfig supplyPurchaseConfig) {
		this.supplyPurchaseConfig = supplyPurchaseConfig;
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

	public void setSupplyPurchaseConfigService(SupplyPurchaseConfigService supplyPurchaseConfigService) {
		this.supplyPurchaseConfigService = supplyPurchaseConfigService;
	}
	
	public Result getResult() {
		return result;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public SupplyPurchaseService getSupplyPurchaseService() {
		return supplyPurchaseService;
	}
	public void setSupplyPurchaseService(SupplyPurchaseService supplyPurchaseService) {
		this.supplyPurchaseService = supplyPurchaseService;
	}
	public SupplyPurchaseFormService getSupplyPurchaseFormService() {
		return supplyPurchaseFormService;
	}
	public void setSupplyPurchaseFormService(
			SupplyPurchaseFormService supplyPurchaseFormService) {
		this.supplyPurchaseFormService = supplyPurchaseFormService;
	}

	public List<SupplyCategory> getSupplyCategorys() {
		return supplyCategorys;
	}

	public void setSupplyCategorys(List<SupplyCategory> supplyCategorys) {
		this.supplyCategorys = supplyCategorys;
	}

	public void setSupplyCategoryService(SupplyCategoryService supplyCategoryService) {
		this.supplyCategoryService = supplyCategoryService;
	}

	public List<SupplyCategory> getSupplyCategoryLabel() {
		return supplyCategoryLabel;
	}

	public void setSupplyCategoryLabel(List<SupplyCategory> supplyCategoryLabel) {
		this.supplyCategoryLabel = supplyCategoryLabel;
	}

	public List<SupplyPurchaseConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<SupplyPurchaseConfig> configList) {
		this.configList = configList;
	}

	public String getSupplyList() {
		return supplyList;
	}

	public void setSupplyList(String supplyList) {
		this.supplyList = supplyList;
	}
	
}

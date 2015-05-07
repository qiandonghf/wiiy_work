package com.wiiy.synthesis.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.Seal;
import com.wiiy.synthesis.service.SealService;

/**
 * @author my
 */
public class SealAction extends JqGridBaseAction<Seal>{
	
	private SealService sealService;
	private Result result;
	private Seal seal;
	private Long id;
	private String ids;
	
	private int selType=0;//0单选 1多选
	
	private List<Seal> selectedSeals;

	public String select(){
		selType=1;
		if (ids != null && ids.trim().length() > 0) {
				Set<Long> idSet = splitIds(ids);
				Filter filter = new Filter(Seal.class);
				filter.in("id", idSet.toArray(new Long[idSet.size()]));
				selectedSeals = sealService.getListByFilter(filter).getValue();
		}
		return "select";
	}
	private Set<Long> splitIds(String ids2) {
    	
    	if (ids2 == null) return new HashSet<Long>();
    	
		Set<Long> ret = new HashSet<Long>();
		
		String[] idArray = ids2.split("\\,");
		
		for (String id : idArray) {
			try {
				ret.add(Long.valueOf(id.trim()));
			} catch (NumberFormatException e) {
			}
		}
		return ret;
	}

	public String loadSealsByOrgId(){
		Filter filter = new Filter(Seal.class);
		filter.eq("orgId", id);
		return refresh(filter);
	}
	
	public String save(){
		if(seal.getSealTypeId()!=null && ("").equals(seal.getSealTypeId())){
			seal.setSealTypeId(null);
		}
		result = sealService.save(seal);
		return JSON;
	}
	
	public String view(){
		result = sealService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = sealService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Seal dbBean = sealService.getBeanById(seal.getId()).getValue();
		BeanUtil.copyProperties(seal, dbBean);
		result = sealService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = sealService.deleteById(id);
		} else if(ids!=null){
			result = sealService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(Seal.class));
	}
	
	@Override
	protected List<Seal> getListByFilter(Filter fitler) {
		return sealService.getListByFilter(fitler).getValue();
	}
	
	
	public Seal getSeal() {
		return seal;
	}

	public void setSeal(Seal seal) {
		this.seal = seal;
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

	public void setSealService(SealService sealService) {
		this.sealService = sealService;
	}
	
	public Result getResult() {
		return result;
	}

	public int getSelType() {
		return selType;
	}
	public void setSelType(int selType) {
		this.selType = selType;
	}
	public List<Seal> getSelectedSeals() {
		return selectedSeals;
	}
	public void setSelectedSeals(List<Seal> selectedSeals) {
		this.selectedSeals = selectedSeals;
	}
}

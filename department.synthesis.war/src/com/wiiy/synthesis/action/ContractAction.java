package com.wiiy.synthesis.action;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisContract;
import com.wiiy.synthesis.service.SynthesisContractService;

/**
 * @author my
 */
@SuppressWarnings("rawtypes")
public class ContractAction extends JqGridBaseAction<SynthesisContract>{
	
	private SynthesisContractService contractService;
	private Result result;
	private SynthesisContract contract;
	private Long id;
	private String ids;
	private String times;
	
	public String amounts(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);
		Format format1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tt = format1.format(cal.getTime());
		String sql = "SELECT count(id) FROM synthesis_synthesis_contract WHERE sign_date >'"+tt+"'";
		List<Object> list = contractService.getListBySql(sql);
		result=Result.value(Integer.parseInt(list.get(0).toString()));
		return JSON;
	}
	
	public String generateCode() {
		result = contractService.generateCode();
		return JSON;
	}
	
	public String save(){
		if ((contract.getCategoryId() != null && //
				"".equals(contract.getCategoryId()) || //
				contract.getCategoryId() == null)) {
			contract.setCategoryId(null);
			contract.setCategory(null);
		}
		if (contract.getContractForm() != null && //
				"".equals(contract.getContractForm())) {
			contract.setContractForm(null);
		}
		if (contract.getContractStatus() != null && //
				"".equals(contract.getContractStatus())) {
			contract.setContractStatus(null);
		}
		if ((contract.getCurrencyTypeId() != null &&//
				"".equals(contract.getCurrencyTypeId()))||//
				contract.getCurrencyTypeId() == null) {
			contract.setCurrencyTypeId(null);
			contract.setCurrencyType(null);
		}
		if (contract.getPayment() != null && 
				"".equals(contract.getPayment())) {
			contract.setPayment(null);
		}
		if (contract.getSettlement() != null&& //
				"".equals(contract.getSettlement())) {
			contract.setSettlement(null);
		}
		if (contract.getAudit() == null) {
			contract.setAudit(BooleanEnum.NO);
		}
		if (contract.getFinished() == null) {
			contract.setFinished(BooleanEnum.NO);
		}
		if (contract.getPublished() == null) {
			contract.setPublished(BooleanEnum.NO);
		}
		result = contractService.save(contract);
		return JSON;
	}
	
	public String view(){
		result = contractService.getBeanById(id);
		return VIEW;
	}
	
	public String viewById(){
		return "viewById";
	}
	
	public String edit(){
		result = contractService.getBeanById(id);
		return EDIT;
	}
	public String editById(){
		return "editById";
	}
	
	public String update(){
		SynthesisContract dbBean = contractService.getBeanById(contract.getId()).getValue();
		BeanUtil.copyProperties(contract, dbBean);
		if ((contract.getCategoryId() != null && //
				"".equals(contract.getCategoryId()) || //
				contract.getCategoryId() == null)) {
			dbBean.setCategoryId(null);
			dbBean.setCategory(null);
		}
		if (contract.getContractForm() != null && //
				"".equals(contract.getContractForm())) {
			dbBean.setContractForm(null);
		}
		if (contract.getContractStatus() != null && //
				"".equals(contract.getContractStatus())) {
			dbBean.setContractStatus(null);
		}
		if ((contract.getCurrencyTypeId() != null &&//
				"".equals(contract.getCurrencyTypeId()))||//
				contract.getCurrencyTypeId() == null) {
			dbBean.setCurrencyTypeId(null);
			dbBean.setCurrencyType(null);
		}
		if (contract.getPayment() != null && 
				"".equals(contract.getPayment())) {
			dbBean.setPayment(null);
		}
		if (contract.getSettlement() != null&& //
				"".equals(contract.getSettlement())) {
			dbBean.setSettlement(null);
		}
		if (contract.getAudit() == null) {
			dbBean.setAudit(BooleanEnum.NO);
		}
		if (contract.getFinished() == null) {
			dbBean.setFinished(BooleanEnum.NO);
		}
		if (contract.getPublished() == null) {
			dbBean.setPublished(BooleanEnum.NO);
		}
		result = contractService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contractService.deleteById(id);
		} else if(ids!=null){
			result = contractService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(SynthesisContract.class);
		if(times!=null&&!("").equals(times)){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.MONTH, -2);
			Format format1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String tt = format1.format(cal.getTime());
			String sql = "SELECT id FROM synthesis_synthesis_contract WHERE sign_date >'"+tt+"'";
			List<Object> list = contractService.getListBySql(sql);
			Long[] idss=new Long[list.size()];
			int i=0;
			for (Object li : list) {
				idss[i]=Long.parseLong(li.toString());
				i++;
			}
			filter.in("id", idss);
		}
		if (id != null) {
			filter.eq("projectId", id);
		}
		return refresh(filter);	
	}
	
	@Override
	protected List<SynthesisContract> getListByFilter(Filter fitler) {
		return contractService.getListByFilter(fitler).getValue();
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

	public Result getResult() {
		return result;
	}

	public void setContractService(SynthesisContractService contractService) {
		this.contractService = contractService;
	}

	public SynthesisContract getContract() {
		return contract;
	}

	public void setContract(SynthesisContract contract) {
		this.contract = contract;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
	
}

package com.wiiy.synthesis.action;

import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.entity.Archives;
import com.wiiy.synthesis.entity.LaborContract;
import com.wiiy.synthesis.entity.LaborContractAtt;
import com.wiiy.synthesis.service.LaborContractAttService;
import com.wiiy.synthesis.service.LaborContractService;

/**
 * @author my
 */
public class LaborContractAction extends JqGridBaseAction<LaborContract>{
	
	private LaborContractService laborContractService;
	private LaborContractAttService laborContractAttService;
	private Result result;
	private LaborContract laborContract;
	private LaborContractAtt laborContractAtt;
	private Long id;
	private String ids;
	private String filePath;
	private String attNames;
	private String attSizes;
	private String attPaths;
	private User user;
	private List<LaborContract> laborContractList;
	private List<LaborContractAtt> laborContractAttList;
	

	public void setLaborContractAttList(List<LaborContractAtt> laborContractAttList) {
		this.laborContractAttList = laborContractAttList;
	}
	public List<LaborContractAtt> getLaborContractAttList() {
		return laborContractAttList;
	}
	public User getUser() {
		return user;
	}
	public String add(){
		return "add";
	}
	public String save(){
		List<LaborContractAtt> attList = backListFromJSON(filePath);
		result = laborContractService.save(laborContract,attList);
		return JSON;
	}
	/**
	 * 解析从js中发回的JSON格式的数据
	 * @param json
	 * @return
	 */
	private List<LaborContractAtt> backListFromJSON(Object json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<LaborContractAtt> att = new ArrayList<LaborContractAtt>();
		if (jsonArray.size() > 0) {
			for (int i = 0; i <jsonArray.size(); i++) {
				JSONObject o = jsonArray.getJSONObject(i);
				String oldName = o.getString("fileName");
				try {
					oldName = URLDecoder.decode(oldName, "utf-8");
					oldName = URLDecoder.decode(oldName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				LaborContractAtt a = new LaborContractAtt();
				a.setName(oldName);
				a.setNewName(o.getString("filePath"));
				att.add(a);
			}
		}
		return att;
	}
	public String list(){	
		Filter filter=new Filter(LaborContract.class);
		filter.eq("userId", id);
		return refresh(filter); 
	}
	
	public String view(){
		result = laborContractService.getBeanById(id);
		return VIEW;
	}
	public String edit(){
		result = laborContractService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		LaborContract dbBean = laborContractService.getBeanById(laborContract.getId()).getValue();
		BeanUtil.copyProperties(laborContract, dbBean);
		result = laborContractService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = laborContractService.deleteById(id);
		} else if(ids!=null){
			result = laborContractService.deleteByIds(ids);
		}
		return JSON;
	}
	@Override
	protected List<LaborContract> getListByFilter(Filter fitler) {
		return laborContractService.getListByFilter(fitler).getValue();
	}
	
	
	public LaborContract getLaborContract() {
		return laborContract;
	}

	public void setLaborContract(LaborContract laborContract) {
		this.laborContract = laborContract;
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

	public void setLaborContractService(LaborContractService laborContractService) {
		this.laborContractService = laborContractService;
	}
	
	public Result getResult() {
		return result;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public LaborContractAttService getLaborContractAttService() {
		return laborContractAttService;
	}
	public LaborContractAtt getLaborContractAtt() {
		return laborContractAtt;
	}
	public void setLaborContractAtt(LaborContractAtt laborContractAtt) {
		this.laborContractAtt = laborContractAtt;
	}
	public List<LaborContract> getLaborContractList() {
		return laborContractList;
	}
	public void setLaborContractList(List<LaborContract> laborContractList) {
		this.laborContractList = laborContractList;
	}
}

package com.wiiy.synthesis.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.LaborContractAtt;
import com.wiiy.synthesis.entity.RosterAtt;
import com.wiiy.synthesis.service.RosterAttService;

/**
 * @author my
 */
public class RosterAttAction extends JqGridBaseAction<RosterAtt>{
	
	private RosterAttService rosterAttService;
	private Result result;
	private RosterAtt rosterAtt;
	private Long id;
	private String ids;
	private String filePath;
	public String add(){
		return "add";
	}
public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String save(){
		result = rosterAttService.save(rosterAtt);
		return JSON;
	}
	public String view(){
		result = rosterAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = rosterAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		RosterAtt dbBean = rosterAttService.getBeanById(rosterAtt.getId()).getValue();
		BeanUtil.copyProperties(rosterAtt, dbBean);
		result = rosterAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = rosterAttService.deleteById(id);
		} else if(ids!=null){
			result = rosterAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(RosterAtt.class);
		filter.eq("userId", id);
		return refresh(filter);
	}
	
	@Override
	protected List<RosterAtt> getListByFilter(Filter fitler) {
		return rosterAttService.getListByFilter(fitler).getValue();
	}
	
	
	public RosterAtt getRosterAtt() {
		return rosterAtt;
	}

	public void setRosterAtt(RosterAtt rosterAtt) {
		this.rosterAtt = rosterAtt;
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

	public void setRosterAttService(RosterAttService rosterAttService) {
		this.rosterAttService = rosterAttService;
	}
	
	public Result getResult() {
		return result;
	}
	
}

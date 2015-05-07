package com.wiiy.synthesis.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.Appraisal;
import com.wiiy.synthesis.entity.AppraisalAtt;
import com.wiiy.synthesis.preferences.enums.AppraisalTypeEnums;
import com.wiiy.synthesis.service.AppraisalAttService;
import com.wiiy.synthesis.service.AppraisalService;

/**
 * @author my
 */
public class AppraisalAction extends JqGridBaseAction<Appraisal>{
	
	private AppraisalService appraisalService;
	private AppraisalAttService appraisalAttService;
	private Result result;
	private Appraisal appraisal;
	private Long id;
	private String ids;
	private List<AppraisalAtt> appraisalAtts;
	
	private String type;
	private String filePath;
	
	
	/**
	 * 解析从js中发回的JSON格式的数据
	 * @param json
	 * @return
	 */
	private List<AppraisalAtt> backListFromJSON(Object json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<AppraisalAtt> att = new ArrayList<AppraisalAtt>();
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
				AppraisalAtt a = new AppraisalAtt();
				a.setName(oldName);
				a.setNewName(o.getString("filePath"));
				att.add(a);
			}
		}
		return att;
	}
	
	public String save(){
		List<AppraisalAtt> list = backListFromJSON(filePath);
		result = appraisalService.save(appraisal,list);
		return JSON;
	}
	
	public String view(){
		appraisal = appraisalService.getBeanById(id).getValue();
		Filter filter = new Filter(AppraisalAtt.class);
		filter.eq("appraisalId", id);
		appraisalAtts = appraisalAttService.getListByFilter(filter).getValue();
		result = Result.value(appraisal);
		return VIEW;
	}
	
	public String edit(){
		appraisal = appraisalService.getBeanById(id).getValue();
		Filter filter = new Filter(AppraisalAtt.class);
		filter.eq("appraisalId", id);
		appraisalAtts = appraisalAttService.getListByFilter(filter).getValue();
		result = Result.value(appraisal);
		return EDIT;
	}
	
	public String update(){
		Appraisal dbBean = appraisalService.getBeanById(appraisal.getId()).getValue();
		BeanUtil.copyProperties(appraisal, dbBean);
		List<AppraisalAtt> list = backListFromJSON(filePath);
		result = appraisalService.update(dbBean,list);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = appraisalService.deleteById(id);
		} else if(ids!=null){
			result = appraisalService.deleteByIds(ids);
		}
		return JSON;
	}
	public String amounts(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.YEAR, 0);
		Format format1=new SimpleDateFormat("yyyy");
		String tt=format1.format(cal.getTime());
		String sql="SELECT COUNT(id) FROM synthesis_appraisal WHERE YEAR(time)='"+tt+"' AND appraisal_type='"+AppraisalTypeEnums.ANNUAL+"'";
		List<Object> list=appraisalService.getListBySql(sql);
		result=Result.value(Integer.parseInt(list.get(0).toString()));
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Appraisal.class);
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.YEAR, 0);
		Format format1=new SimpleDateFormat("yyyy");
		String tt=format1.format(cal.getTime());
		String sql="SELECT id FROM synthesis_appraisal WHERE YEAR(time)='"+tt+"' AND appraisal_type='"+AppraisalTypeEnums.ANNUAL+"'";
		List<Object> list=appraisalService.getListBySql(sql);
		Long[] idss=new Long[list.size()];
		int i=0;
		for (Object li : list) {
			idss[i]=Long.parseLong(li.toString());
			i++;
		}
		filter.in("id", idss);
		return refresh(filter);
	}
	
	@Override
	protected List<Appraisal> getListByFilter(Filter fitler) {
		return appraisalService.getListByFilter(fitler).getValue();
	}
	
	
	public Appraisal getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(Appraisal appraisal) {
		this.appraisal = appraisal;
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

	public void setAppraisalService(AppraisalService appraisalService) {
		this.appraisalService = appraisalService;
	}
	
	public Result getResult() {
		return result;
	}

	public AppraisalAttService getAppraisalAttService() {
		return appraisalAttService;
	}

	public void setAppraisalAttService(AppraisalAttService appraisalAttService) {
		this.appraisalAttService = appraisalAttService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AppraisalService getAppraisalService() {
		return appraisalService;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<AppraisalAtt> getAppraisalAtts() {
		return appraisalAtts;
	}
	
}

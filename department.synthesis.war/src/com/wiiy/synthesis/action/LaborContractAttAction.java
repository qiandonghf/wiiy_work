package com.wiiy.synthesis.action;

import java.io.File;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.LaborContractAtt;
import com.wiiy.synthesis.service.LaborContractAttService;

/**
 * @author my
 */
public class LaborContractAttAction extends JqGridBaseAction<LaborContractAtt>{
	
	private LaborContractAttService laborContractAttService;
	private Result result;
	private LaborContractAtt laborContractAtt;
	private Long id;
	private String ids;
	private String filePath;
	private String rootPath;
	
	public LaborContractAttAction() {
		rootPath = System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"upload/";
	}
	public String save(){
		result = laborContractAttService.save(laborContractAtt);
		return JSON;
	}
	
	public String view(){
		result = laborContractAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = laborContractAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		LaborContractAtt dbBean = laborContractAttService.getBeanById(laborContractAtt.getId()).getValue();
		BeanUtil.copyProperties(laborContractAtt, dbBean);
		result = laborContractAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = laborContractAttService.deleteById(id);
		} else if(ids!=null){
			result = laborContractAttService.deleteByIds(ids);
		}
		return JSON;
	}
	public String deleteByFilePath(){
		if(filePath!=null){
			File file = new File(rootPath + filePath);
			if (file.exists()) {
				file.delete();
			}
			result = Result.success("文件删除成功");
		}
		return JSON;
	}
	public String list(){
		return refresh(new Filter(LaborContractAtt.class));
	}
	
	@Override
	protected List<LaborContractAtt> getListByFilter(Filter fitler) {
		return laborContractAttService.getListByFilter(fitler).getValue();
	}
	
	
	public LaborContractAtt getLaborContractAtt() {
		return laborContractAtt;
	}

	public void setLaborContractAtt(LaborContractAtt laborContractAtt) {
		this.laborContractAtt = laborContractAtt;
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

	public void setLaborContractAttService(LaborContractAttService laborContractAttService) {
		this.laborContractAttService = laborContractAttService;
	}
	
	public Result getResult() {
		return result;
	}
	
}

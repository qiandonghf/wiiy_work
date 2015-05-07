package com.wiiy.synthesis.action;

import java.io.File;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.AppraisalAtt;
import com.wiiy.synthesis.service.AppraisalAttService;

/**
 * @author my
 */
public class AppraisalAttAction extends JqGridBaseAction<AppraisalAtt>{
	
	private AppraisalAttService appraisalAttService;
	private Result result;
	private AppraisalAtt appraisalAtt;
	private Long id;
	private String ids;
	private String filePath;
	private String rootPath;
	public AppraisalAttAction() {
		rootPath = System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"upload/";
	}
	
	/**
	 * 根据文件路径删除上传时的文件
	 * @return
	 */
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
	
	
	public String save(){
		result = appraisalAttService.save(appraisalAtt);
		return JSON;
	}
	
	public String view(){
		result = appraisalAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = appraisalAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		AppraisalAtt dbBean = appraisalAttService.getBeanById(appraisalAtt.getId()).getValue();
		BeanUtil.copyProperties(appraisalAtt, dbBean);
		result = appraisalAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = appraisalAttService.deleteById(id);
		} else if(ids!=null){
			result = appraisalAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(AppraisalAtt.class));
	}
	
	@Override
	protected List<AppraisalAtt> getListByFilter(Filter fitler) {
		return appraisalAttService.getListByFilter(fitler).getValue();
	}
	
	
	public AppraisalAtt getAppraisalAtt() {
		return appraisalAtt;
	}

	public void setAppraisalAtt(AppraisalAtt appraisalAtt) {
		this.appraisalAtt = appraisalAtt;
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

	public void setAppraisalAttService(AppraisalAttService appraisalAttService) {
		this.appraisalAttService = appraisalAttService;
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
	
}

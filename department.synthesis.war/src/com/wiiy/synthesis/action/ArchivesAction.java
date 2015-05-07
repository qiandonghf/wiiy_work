package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.entity.Archives;
import com.wiiy.synthesis.service.ArchivesService;

/**
 * @author my
 */
public class ArchivesAction extends JqGridBaseAction<Archives>{
	
	private ArchivesService archivesService;
	private Result result;
	private Archives archives;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = archivesService.save(archives);
		return JSON;
	}
	
	public String view(){
		result = archivesService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = archivesService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Archives dbBean = archivesService.getBeanById(archives.getId()).getValue();
		BeanUtil.copyProperties(archives, dbBean);
		result = archivesService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = archivesService.deleteById(id);
		} else if(ids!=null){
			result = archivesService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(Archives.class));
	}
	
	@Override
	protected List<Archives> getListByFilter(Filter fitler) {
		return archivesService.getListByFilter(fitler).getValue();
	}
	
	
	public Archives getArchives() {
		return archives;
	}

	public void setArchives(Archives archives) {
		this.archives = archives;
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

	public void setArchivesService(ArchivesService archivesService) {
		this.archivesService = archivesService;
	}
	
	public Result getResult() {
		return result;
	}
	
}

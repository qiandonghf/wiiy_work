package com.wiiy.pf.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.service.ContactBaseAttService;

/**
 * @author my
 */
public class ContactBaseAttAction extends JqGridBaseAction<ContactBaseAtt>{
	
	private ContactBaseAttService contactBaseAttService;
	private Result result;
	private ContactBaseAtt contactBaseAtt;
	private Long id;
	private String ids;
	private String imgPath;
	
	public String attImage(){
		return "attImage";
	}
	
	public String save(){
		result = contactBaseAttService.save(contactBaseAtt);
		return JSON;
	}
	
	public String view(){
		result = contactBaseAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contactBaseAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		ContactBaseAtt dbBean = contactBaseAttService.getBeanById(contactBaseAtt.getId()).getValue();
		BeanUtil.copyProperties(contactBaseAtt, dbBean);
		result = contactBaseAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contactBaseAttService.deleteById(id);
		} else if(ids!=null){
			result = contactBaseAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(ContactBaseAtt.class));
	}
	
	@Override
	protected List<ContactBaseAtt> getListByFilter(Filter fitler) {
		return contactBaseAttService.getListByFilter(fitler).getValue();
	}
	
	
	public ContactBaseAtt getContactBaseAtt() {
		return contactBaseAtt;
	}

	public void setContactBaseAtt(ContactBaseAtt contactBaseAtt) {
		this.contactBaseAtt = contactBaseAtt;
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

	public void setContactBaseAttService(ContactBaseAttService contactBaseAttService) {
		this.contactBaseAttService = contactBaseAttService;
	}
	
	public Result getResult() {
		return result;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}

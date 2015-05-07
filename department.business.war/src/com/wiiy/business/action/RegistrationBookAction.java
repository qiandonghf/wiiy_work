package com.wiiy.business.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.business.entity.InvestmentBook;
import com.wiiy.business.service.RegistrationBookService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class RegistrationBookAction extends JqGridBaseAction<InvestmentBook>{
	
	private RegistrationBookService registrationBookService;
	private Result result;
	private InvestmentBook registrationBook;
	private Long id;
	private String ids;
	
	private String type;
	
	//预订提醒数量 7天内
	public String registCount(){
		Filter filter = new Filter(InvestmentBook.class);
		Calendar calendar = Calendar.getInstance();
		Date time = CalendarUtil.getEarliest(calendar.getTime(), Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		filter.match("endTime", time, "ge");
		filter.match("endTime", calendar.getTime(), "le");
		result = registrationBookService.getRowCount(filter);
		return JSON;
	}
	
	public String save(){
		result = registrationBookService.save(registrationBook);
		return JSON;
	}
	
	public String view(){
		result = registrationBookService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = registrationBookService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		InvestmentBook dbBean = registrationBookService.getBeanById(registrationBook.getId()).getValue();
		BeanUtil.copyProperties(registrationBook, dbBean);
		result = registrationBookService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = registrationBookService.deleteById(id);
		} else if(ids!=null){
			result = registrationBookService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(InvestmentBook.class);
		filter.createAlias("user", "user");
		if (id != null) {
			filter.eq("roomId", id);
		}
		//预订到期提醒 7天内
		if("remind".equals(type)){
			Calendar calendar = Calendar.getInstance();
			Date time = CalendarUtil.getEarliest(calendar.getTime(), Calendar.DAY_OF_MONTH);
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			filter.match("endTime", time, "ge");
			filter.match("endTime", calendar.getTime(), "le");
		}
		return refresh(filter);
	}
	
	public String changeState() {
		result = registrationBookService.changeState(id);
		return JSON;
	}
	
	@Override
	protected List<InvestmentBook> getListByFilter(Filter fitler) {
		return registrationBookService.getListByFilter(fitler).getValue();
	}
	
	
	public InvestmentBook getRegistrationBook() {
		return registrationBook;
	}

	public void setRegistrationBook(InvestmentBook registrationBook) {
		this.registrationBook = registrationBook;
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

	public void setRegistrationBookService(RegistrationBookService registrationBookService) {
		this.registrationBookService = registrationBookService;
	}
	
	public Result getResult() {
		return result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

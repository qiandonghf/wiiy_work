package com.wiiy.park.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.park.entity.RoomAtt;
import com.wiiy.park.service.RoomAttService;

/**
 * @author my
 */
public class RoomAttAction extends JqGridBaseAction<RoomAtt>{
	
	private RoomAttService roomAttService;
	private Result result;
	private RoomAtt roomAtt;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = roomAttService.save(roomAtt);
		return JSON;
	}
	
	public String view(){
		result = roomAttService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = roomAttService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		RoomAtt dbBean = roomAttService.getBeanById(roomAtt.getId()).getValue();
		BeanUtil.copyProperties(roomAtt, dbBean);
		result = roomAttService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = roomAttService.deleteById(id);
		} else if(ids!=null){
			result = roomAttService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(RoomAtt.class));
	}
	
	@Override
	protected List<RoomAtt> getListByFilter(Filter fitler) {
		return roomAttService.getListByFilter(fitler).getValue();
	}
	
	
	public RoomAtt getRoomAtt() {
		return roomAtt;
	}

	public void setRoomAtt(RoomAtt roomAtt) {
		this.roomAtt = roomAtt;
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

	public void setRoomAttService(RoomAttService roomAttService) {
		this.roomAttService = roomAttService;
	}
	
	public Result getResult() {
		return result;
	}
	
}

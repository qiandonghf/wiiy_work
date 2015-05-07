package com.wiiy.pf.dto;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

public class UserTaskDto implements Comparable<UserTaskDto>{

	private List<User> candidateUserList=new ArrayList<User>();
	
	
	private List<Group> candidateGroupList=new ArrayList<Group>();
	
	private User assignee;
	private User dealUser;
	
	private boolean display;
	private int order;
	
	private List<HistoricVariableDto> historicVariableDtos;
	
	public List<User> getCandidateUserList() {
		return candidateUserList;
	}
	public void addCandidateUserList(User user) {
		this.candidateUserList.add(user);
	}
	public List<Group> getCandidateGroupList() {
		return candidateGroupList;
	}
	public void addCandidateGroupList(Group group) {
		this.candidateGroupList.add(group);
	}
	public User getAssignee() {
		return assignee;
	}
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public int compareTo(UserTaskDto arg0) {
		if(arg0==null) return 1;
		if(this.order==arg0.getOrder())return 0;
		else if(this.order>arg0.getOrder()) return 1;
		else return -1;
	}
	public List<HistoricVariableDto> getHistoricVariableDtos() {
		return historicVariableDtos;
	}
	public void setHistoricVariableDtos(
			List<HistoricVariableDto> historicVariableDtos) {
		this.historicVariableDtos = historicVariableDtos;
	}
	public User getDealUser() {
		return dealUser;
	}
	public void setDealUser(User dealUser) {
		this.dealUser = dealUser;
	}
	
	
}

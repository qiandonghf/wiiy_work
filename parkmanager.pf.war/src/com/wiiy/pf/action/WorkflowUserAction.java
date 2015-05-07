package com.wiiy.pf.action;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.hibernate.Filter;

public class WorkflowUserAction extends JqGridBaseAction<User> {

	private IdentityService identityService;

	private String id;
	
	
	private UserEntity user;
	private String firstName;
	
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	public String list(){
		return "list";
	}
	public String add(){
		return "add";
	}
	
	
	public String load() {
		
		System.out.println("-------------------------------------WorkflowUserAction.load():firstName="+firstName);
		
		if(firstName!=null)firstName=firstName.trim();
		else firstName="";
		
		
		
		records= (int)identityService.createUserQuery().count();
		if(rows==0)rows=10;
		if (records > 0) {
			if (records % rows == 0) {
				total = records % rows;
			} else {
				total = records / rows + 1;
			}
		}
		if(page<=0)page=1;
		
		UserQuery userQuery=identityService.createUserQuery();
		
		
		if(firstName.length()>0){
			userQuery=userQuery.userFirstNameLike(firstName);
		}
		List<User> users=userQuery.listPage((page - 1)*rows, rows);
		root=new ArrayList<User>();
		for(User u:users){
			UserEntity ue=new UserEntity();
			ue.setId(u.getId());
			ue.setFirstName(u.getFirstName());
			ue.setEmail(u.getEmail());
			
			root.add(ue);
		}
		
		
		return JSON;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	@Override
	protected List<User> getListByFilter(Filter fitler) {
		// TODO Auto-generated method stub
		return null;
	}



	public UserEntity getUser() {
		return user;
	}



	public void setUser(UserEntity user) {
		this.user = user;
	}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


}

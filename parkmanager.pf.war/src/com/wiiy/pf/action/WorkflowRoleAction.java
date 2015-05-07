package com.wiiy.pf.action;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.dto.GroupDto;

public class WorkflowRoleAction extends JqGridBaseAction<GroupDto> {

	private IdentityService identityService;

	private String id;
	private String ids;
	private Result<GroupDto> result;
	private List<GroupDto> groupList;
	
	private List<UserEntity> selectedUsers=new ArrayList<UserEntity>();
	
	private GroupEntity role;
	public Result<GroupDto> getResult() {
		return result;
	}



	public void setResult(Result<GroupDto> result) {
		this.result = result;
	}
	public GroupEntity getRole() {
		return role;
	}
	public void setRole(GroupEntity role) {
		this.role = role;
	}
	public List<GroupDto> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<GroupDto> groupList) {
		this.groupList = groupList;
	}
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	public String list(){
		return "list";
	}
	public String add(){
		return "add";
	}
	
	public String multiSelect(){
		List<User> s_users=identityService.createUserQuery().memberOfGroup(id).list();
		for(User u:s_users){
			UserEntity ue=new UserEntity();
			ue.setId(u.getId());
			ue.setFirstName(u.getFirstName());
			selectedUsers.add(ue);
		}
		return "multiSelect";	
	}
	public String multiSelectSave(){
		try {
			System.out.println("WorkflowRoleAction.multiSelectSave():id="+id+",ids="+ids);
			if(id!=null&&id.trim().length()>0){
				List<User> oldUsers=identityService.createUserQuery().memberOfGroup(id).list();
				for(User u:oldUsers){
					identityService.deleteMembership(u.getId(),id);
				}
				if(ids!=null&&ids.trim().length()>0){
					ids=ids.trim();
					String[] newIds=ids.split(",");
					for(String s:newIds){
						identityService.createMembership(s, id);
					}	
				}
			}
			result= Result.success("保存成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result= Result.failure("保存失败！");
		}
		return JSON;	
	}
	
	
	public String save(){
		try {
			logger.info("创建流程角色:"+role.getId()+","+role.getName());
			//role.setRevision(1);
			role.setType("assignment");
			identityService.saveGroup(role);
			result=Result.success("角色创建成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=Result.failure("角色创建失败！");
		}
		return JSON;
	}
	public String update(){
		try {
			logger.info("更新流程角色:"+role.getId()+","+role.getName());
			Group g= identityService.createGroupQuery().groupId(role.getId()).singleResult();
			role.setType(g.getType());
			identityService.saveGroup(role);
			result=Result.success("角色更新成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=Result.failure("角色更新失败！");
		}
		return JSON;
	}
	
	public String edit(){
		try {
			role=(GroupEntity) identityService.createGroupQuery().groupId(id).singleResult();
			result=Result.success("加载成功!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=Result.success("加载失败!");
		}
		return "edit";
	}
	public String delete(){
		try {
			identityService.deleteGroup(id);
			result=Result.success("角色删除成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=Result.failure("角色删除失败！");
		}
		return JSON;
	}
	public String load() {
		records= (int)identityService.createGroupQuery().count();
		if(rows==0)rows=10;
		if (records > 0) {
			if (records % rows == 0) {
				total = records % rows;
			} else {
				total = records / rows + 1;
			}
		}
		if(page<=0)page=1;
		List<Group> groupList=identityService.createGroupQuery().listPage((page - 1)*rows, rows);
		
		root=new ArrayList<GroupDto>();
		for(Group g:groupList){
			GroupDto groupDto=new GroupDto();
			groupDto.setId(g.getId());
			groupDto.setName(g.getName());
			groupDto.setType(g.getType());
			List<User> users=identityService.createUserQuery().memberOfGroup(g.getId()).list();
			
			String userNames="";
			for(User u:users){
				userNames+=u.getFirstName()+";";
			}
			groupDto.setMemberNames(userNames);
			root.add(groupDto);
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
	protected List<GroupDto> getListByFilter(Filter fitler) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<UserEntity> getSelectedUsers() {
		return selectedUsers;
	}



	public void setIds(String ids) {
		this.ids = ids;
	}



}

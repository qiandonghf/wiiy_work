package com.wiiy.core.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.wiiy.commons.activator.AbstractActivator.CachedLog;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.GenderEnum;
import com.wiiy.commons.preferences.enums.UserTypeEnum;
import com.wiiy.core.activator.CoreActivator;
import com.wiiy.core.dto.ResourceDto;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.Org;
import com.wiiy.core.entity.Role;
import com.wiiy.core.entity.RoleAuthorityRef;
import com.wiiy.core.entity.User;
import com.wiiy.core.entity.UserRoleRef;
import com.wiiy.core.extensions.ResourceExtensions;

public class DataDictInit {
//	protected CachedLog logger = CachedLog.getLog(getClass());
	private static final Log logger = LogFactory.getLog(CoreActivator.class);
	private List<DataDict> list=new ArrayList<DataDict>();
	public boolean init(){
		initAdmin();
		return true;
	}
	
	/*
	 * 初始化admin帐号 超级管理员角色 admin权限
	 * 初始化顶级组织名称
	 * 
	 */
	public void initAdmin(){
		SessionFactory sessionFactory=CoreActivator.getService(SessionFactory.class);
		Session session=sessionFactory.openSession();
		
		Transaction tr=session.beginTransaction();
		try {
			String companyName=CoreActivator.getAppConfig().getConfig("companyName").getParameter("name");
			if( null == companyName || 0 >= companyName.length()){
				companyName = "www.parkmanager.cn";
			}
			// Top org ID static define 1L
			Org org = (Org)session.get(Org.class, (Long)1L);
			if( null == org ){
				logger.info("=============== Init Top Org["+companyName+"] ===============");
				org=new Org();
				org.setId(1L);
				org.setName(companyName);
				org.setMemo("");
				org.setOrderCode(1);
				org.setLevelCode(0);
				org.setPathCode("1");
				org.setChildrenCount(0);
				org.setParent(null);
				session.save(org);
			}
			
			// Customer org ID static define 2L
			Org org2 = (Org)session.get(Org.class, (Long)2L);
			if( null == org2 ){
				logger.info("=============== Init Customer Org[园区企业] ===============");
				org2=new Org();
				org2.setId(2L);
				org2.setName("园区企业");
				org2.setMemo("");
				org2.setOrderCode(2);
				org2.setLevelCode(1);
				org2.setPathCode("1");
				org2.setChildrenCount(0);
				org2.setParent(null);
				session.save(org2);
			}
			
			// Admin ID static define 1L
			User user = (User)session.get(User.class, (Long)1L);
			if( null == user ){
				logger.info("=============== Init User Admin[admin] ===============");
				user=new User();
				user.setAdmin(BooleanEnum.YES);
				user.setBirthday(new Date());
				user.setEmail("services@sanlue.com.cn");
				user.setGender(GenderEnum.Male);
				user.setId(1L);
				user.setIp(null);
				user.setLastIp(null);
				user.setLastLoginTime(new Date());
				user.setMobile("");
				user.setMsn("");
				user.setOrg(org);
				user.setUsername("admin");
				user.setPassword("e10adc3949ba59abbe56e057f20f883e");
				user.setRealName("超级管理员");
				user.setUserType(UserTypeEnum.Center);
				session.save(user);
				
				// Admin role ID static define 1L				
				Role role = (Role)session.get(Role.class, (Long)1L);
				if( null == role ){
					logger.info("=============== Init Admin Role ===============");
					role=new Role();
					role.setMemo("超级管理员");
					role.setName("admin");
					role.setId(1l);
					session.save(role);

					logger.info("=============== Init Admin Authority ===============");
					ResourceDto dto = ResourceExtensions.resourceMap.get("core");
					RoleAuthorityRef ref=new RoleAuthorityRef();
					ref.setId(1L);
					ref.setModuleId(0+"");
					ref.setAuthorityId(dto.getIdSpace());
					ref.setAuthorityType(dto.getType());
					ref.setRole(role);
					session.save(ref);
					List<ResourceDto> children = dto.getChildren();
					loadChildren(children,session,role);

					logger.info("=============== Init Admin business Authority ===============");
					ResourceDto dto1 = ResourceExtensions.resourceMap.get("business_functions");
					RoleAuthorityRef ref1 =new RoleAuthorityRef();
					ref1.setModuleId(0+"");
					ref1.setAuthorityId(dto1.getIdSpace());
					ref1.setAuthorityType(dto1.getType());
					ref1.setRole(role);
					session.save(ref1);

					logger.info("=============== Init Admin User&Role Relationship ===============");
					UserRoleRef userRoleRef=new UserRoleRef();
					userRoleRef.setId(1L);
					userRoleRef.setRole(role);
					userRoleRef.setUser(user);
					session.save(userRoleRef);
				}
			}
			Role role=(Role)session.get(Role.class, (Long)2L);
			if( null == role ){
				logger.info("=============== Init Customer Role ===============");
				role=new Role();
				role.setMemo("企业帐号");
				role.setName("customer");
				role.setId(2L);
				session.save(role);
				
				ResourceDto dto = ResourceExtensions.resourceMap.get("ps");
				if( null != dto){
					logger.info("=============== Init Customer Authority ===============");
					RoleAuthorityRef ref=new RoleAuthorityRef();
					ref.setModuleId(1+"");
					ref.setAuthorityId(dto.getIdSpace());
					ref.setAuthorityType(dto.getType());
					ref.setRole(role);
					session.save(ref);
					List<ResourceDto> children = dto.getChildren();
					loadChildren(children,session,role);					
				}
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}finally{
			if(session!=null)session.close();
		}
	}
	private void loadChildren(List<ResourceDto> children,Session session,Role role) {
		if(children!=null){
			for (ResourceDto resourceDto : children) {
				RoleAuthorityRef ref=new RoleAuthorityRef();
				ref.setModuleId(0+"");
				ref.setAuthorityId(resourceDto.getIdSpace());
				ref.setAuthorityType(resourceDto.getType());
				ref.setRole(role);
				session.save(ref);
				List<ResourceDto> subChildren = resourceDto.getChildren();
				loadChildren(subChildren,session,role);
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @param parentId
	 * @param moduleName
	 * @param dataName
	 * @param value
	 * @param fixed 1 系统 0用户
	 * @param type 0单一值 1列表值
	 */
	private void newData(String id,String parentId,String moduleName,String dataName,String value,Integer fixed,Integer type,Integer order){
		DataDict dataDict=new DataDict();
		dataDict.setId(id);
		dataDict.setModuleName(moduleName);
		dataDict.setDataName(dataName);
		dataDict.setDataValue(value);
		dataDict.setFixed(fixed);
		dataDict.setParentId(parentId);
		dataDict.setType(type);
		dataDict.setDisplayOrder(order);
		list.add(dataDict);
	}
	public List<DataDict> getList() {
		init();
		return list;
	}
	
}

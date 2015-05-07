package com.wiiy.activiti.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.wiiy.activiti.Activator;
import com.wiiy.commons.activator.AbstractActivator.CachedLog;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.core.external.service.UserPubService;

public class DataDictInit {
	protected CachedLog logger = CachedLog.getLog(getClass());
	private List<DataDict> list=new ArrayList<DataDict>();
	private Timer timer;
	public boolean init(){
		boolean r=true;
		SessionFactory sessionFactory=Activator.getService(SessionFactory.class);
		Session session=sessionFactory.openSession();
		
		Transaction tr=session.beginTransaction();
		try {
			UserPubService userPubService=Activator.getService(UserPubService.class);
			//IdentityService identityService=Activator.getService(IdentityService.class);
			
			logger.info("###########################################################################------++++++++++初始化流程引擎用户表++++++++--------userPubService="+userPubService);
			
			List<User> centerUsers=userPubService.getCenterUserList2();
			
			for(User u:centerUsers){
				List l=session.createSQLQuery("select * from act_id_user where ID_='"+u.getUsername()+"'").list();
				if(l==null||l.size()==0){
					session.createSQLQuery("insert into act_id_user(ID_,REV_,FIRST_,LAST_,EMAIL_,PWD_,PICTURE_ID_) values('"+u.getUsername()+"','1','"+u.getRealName()+"','','"+u.getEmail()+"','"+u.getPassword()+"','')").executeUpdate();
					logger.info("创建Activiti用户："+u.getUsername()+","+u.getRealName());
				}
				logger.info("Activiti用户已存在："+u.getUsername());
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}finally{
			if(session!=null)session.close();
		}
		
		
		return r;
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
		try{
			new Timer().schedule(new InitUsers(),1000 * 60);// 延迟启动
		}catch(Exception e){
			logger.info("###########################################################################------++++++++++初始化流程引擎用户表++++++++--------userPubService失败");
			System.err.println("初始化流程引擎用户表失败");
			e.printStackTrace();
		}
		return list;
	}
	class InitUsers extends TimerTask{
		@Override
		public void run() {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						init();
						System.out.println("--------------------");
						System.out.println("初始化流程引擎用户表启动成功");
						System.out.println("--------------------");
					} catch (Throwable e) {
						System.err.println("--------------------");
						System.out.println("初始化流程引擎用户表启动失败，稍后再次启动");
						System.err.println("--------------------");
					}
					timer.cancel();
				}
			}, 1000, 1000*60);
		}
	}
}

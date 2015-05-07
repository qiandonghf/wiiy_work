package me.kafeitu.demo.activiti.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.wiiy.activiti.entity.Leave;
import com.wiiy.hibernate.BaseDao;

/**
 * Activiti相关DAO操作
 * @author: aswan
 */
@Component
public class ActivitiDao extends BaseDao<Leave>{




    /**
     * 流程完成后清理detail表中的表单类型数据
     * @param processInstanceId
     * @return
     */
    public int deleteFormPropertyByProcessInstanceId(String processInstanceId) {
    	
    	Session s=this.openSession();
    	int i=s.createSQLQuery("delete from act_hi_detail where proc_inst_id_ ="+processInstanceId+" and type_ = 'FormProperty' ").executeUpdate();
    	
    	if(s!=null&&s.isOpen()){
    		s.close();
    	}
    	//this.executeSQLUpdate("delete from act_hi_detail where proc_inst_id_ ="+processInstanceId+" and type_ = 'FormProperty' ");
        //int i = entityManager.createNativeQuery("delete from act_hi_detail where proc_inst_id_ = ? and type_ = 'FormProperty' ")
         //       .setParameter(1, processInstanceId).executeUpdate();
        return i;
    }


}

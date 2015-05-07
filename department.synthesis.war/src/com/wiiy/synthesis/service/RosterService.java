package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.Archives;

/**
 * @author my
 */
public interface RosterService extends IService<Archives> {

	Result update(User user,Archives archives);
	Result save(Archives archives);
//	String getCustomerLabelIdsByCustomerId(Long id);
//
//	Result<String> generateCode();
//	
//	Result saveAccount(Long id, String username, String password);
//	
//	Result updateAccountPassword(Long id, String password);
//	
//	Result<User> getSessionUserCustomer();
//	
//	Result<?> importCard(String ids);
//	
//	List getObjectListBySql(String sql);
//	
//	void createCustomerAccount();
//	Result getListByLimitNum(int i);
//	
//	Result<List<User>> getListByHql(String string);
//	
//	Result<User> getBeanByHql(String string);
}

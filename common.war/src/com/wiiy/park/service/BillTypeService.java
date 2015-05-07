package com.wiiy.park.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.BillType;

/**
 * @author my
 */
public interface BillTypeService extends IService<BillType> {
	
	/**
	 * 加载账单类型 如果不存在此name的类型则创建类型并返回
	 * @param typeNames
	 * @return
	 */
	Result<BillType> getBillType(String typeName);
	
	/**
	 * 验证账单类型 如果不存在此类型则创建类型并返回
	 * @param typeNames
	 * @return
	 */
	Result<BillType> checkBillType(BillType billType);

	Result<List<BillType>> getListByHql(String string);
	
}

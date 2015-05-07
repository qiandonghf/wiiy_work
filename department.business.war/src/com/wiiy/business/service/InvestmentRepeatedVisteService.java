package com.wiiy.business.service;

import java.util.Map;

import com.wiiy.business.dto.ClueDto;
import com.wiiy.business.entity.InvestmentRepeatedViste;
import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface InvestmentRepeatedVisteService extends IService<InvestmentRepeatedViste> {

	Map<Long,ClueDto> getTimes();

	Result<InvestmentRepeatedViste> save(InvestmentRepeatedViste t);
}

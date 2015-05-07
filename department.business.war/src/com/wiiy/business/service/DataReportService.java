package com.wiiy.business.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.business.dto.AnalyseDto;
import com.wiiy.business.dto.DataReportPropertyDto;
import com.wiiy.business.dto.DataStatisticDto;
import com.wiiy.business.dto.ParkLogDto;
import com.wiiy.business.entity.DataReport;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface DataReportService extends IService<DataReport> {
	
	/**
	 * 新建报表
	 * @param dataReportDef
	 * @param customerIds 报送企业id
	 * @return
	 */
	Result save(DataReport dataReport, String customerIds);

	/**
	 * 更新报表
	 * @param dataReportDef
	 * @param customerIds 报送企业id
	 * @return
	 */
	Result update(DataReport dbBean, String customerIds);

	/**
	 * 发布报表
	 * @param id
	 * @param ids 报送企业id
	 */
	Result publish(Long id, String customerIds);

	Result close(Long id);

	Result open(Long id);

	
	Result<List<DataReportPropertyDto>> count(Long id);

	Result<DataReport> addCustomer(Long id, String ids);
	
	List<DataStatisticDto> getCountDto(AnalyseDto dto);

	List<DataStatisticDto> getCountDto2(AnalyseDto dto, Long id);
	List<ParkLogDto> getParkLog();
	Result getParkLogPage(int page) ;

	List<Object> getListBySql(String sql);
}

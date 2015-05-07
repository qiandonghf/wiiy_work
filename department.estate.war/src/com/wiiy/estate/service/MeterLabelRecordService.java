package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.dto.FeeStatementsDto;
import com.wiiy.estate.entity.MeterLabelRecord;
import com.wiiy.estate.entity.MeterLabelReport;
import com.wiiy.hibernate.Result;

public interface MeterLabelRecordService extends IService<MeterLabelRecord>{

	Result addMeter(Long labelId, List<String> existList, List<String> newList);

	Result<List<MeterLabelReport>> generateReport(Long labelId,boolean isSetFee);

	Result waterEleFee(long labelId,String ids); 
	Result printFee(long labelId);

	Result<FeeStatementsDto> generateReport2(Long laelId, boolean isSetFee);

	Result labelReport(Long labelId, boolean b);
}

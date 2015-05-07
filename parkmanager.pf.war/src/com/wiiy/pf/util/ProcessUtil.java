package com.wiiy.pf.util;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import com.wiiy.pf.dto.ProcessDto;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.SuspensionStateEnum;

public final class ProcessUtil {
	private ProcessUtil() {
	}
	
	/**
	 * 将取出的流程和部署信息写入到processDto中
	 * @param definition
	 * @param deployment
	 * @return
	 */
	public static ProcessDto createDto(
			ProcessDefinition definition,
			Deployment deployment) {
		ProcessDto dto = new ProcessDto();
		dto.setId(definition.getId());
		dto.setDeploymentId(definition.getDeploymentId());
		dto.setName(definition.getName());
		dto.setKey(definition.getKey());
		dto.setVersion(definition.getVersion());
		dto.setResourceName(definition.getResourceName());
		dto.setDiagramResourceName(definition.getDiagramResourceName());
		dto.setDeploymentTime(deployment.getDeploymentTime());
		if (definition.isSuspended()) {
			dto.setState(SuspensionStateEnum.PENDING);
		} else {
			dto.setState(SuspensionStateEnum.ACTIVE);
		}
		return dto;
	}
	
	/**
	 * 将取出的流程和部署信息写入到processDto中
	 * @param definition
	 * @param deployment
	 * @return
	 */
	public static ProcessDto createDto(
			ProcessDefinition definition,
			Deployment deployment,
			ProcessType processType) {
		ProcessDto dto = new ProcessDto();
		dto.setId(definition.getId());
		dto.setDeploymentId(definition.getDeploymentId());
		dto.setName(definition.getName());
		dto.setKey(definition.getKey());
		dto.setVersion(definition.getVersion());
		dto.setResourceName(definition.getResourceName());
		dto.setDiagramResourceName(definition.getDiagramResourceName());
		dto.setDeploymentTime(deployment.getDeploymentTime());
		dto.setProcessType(processType);
		if (definition.isSuspended()) {
			dto.setState(SuspensionStateEnum.PENDING);
		} else {
			dto.setState(SuspensionStateEnum.ACTIVE);
		}
		return dto;
	}
	
	
}

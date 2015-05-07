package com.wiiy.pf.dto;

import org.activiti.engine.identity.Group;

public class GroupDto implements Group {
	private String id;
	private String type;
	private String name;
	private String memberNames;
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public void setType(String type) {
		this.type=type;
	}

	public String getMemberNames() {
		return memberNames;
	}

	public void setMemberNames(String memberNames) {
		this.memberNames = memberNames;
	}

}

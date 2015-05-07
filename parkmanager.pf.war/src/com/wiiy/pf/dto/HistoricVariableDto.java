package com.wiiy.pf.dto;


public class HistoricVariableDto implements Comparable<HistoricVariableDto>{
	private String variableTypeName;
	private String variableName;
	private String value;
	private String time;
	public String getVariableTypeName() {
		return variableTypeName;
	}
	public void setVariableTypeName(String variableTypeName) {
		this.variableTypeName = variableTypeName;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTime() {
		
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public int compareTo(HistoricVariableDto o) {
		if(variableTypeName!=null&&variableTypeName.equalsIgnoreCase("boolean")) return -100;
		if(variableTypeName!=null&&!variableTypeName.equalsIgnoreCase("boolean")) return 100;
		return 0;
	}
	
}

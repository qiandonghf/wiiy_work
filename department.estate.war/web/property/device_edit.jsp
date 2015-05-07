<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>

<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(function(){
		initTip();
		initForm();
		initMaintenanceList();
		initPatrolList();
		initYearlyList();
		initDeviceWorkOrderList();
		//$("#patrolIntervalType").children().eq(0).text("请选择");
	});
	
	/*
		让日期选择框向上居中
	*/
	function getCalendarScrollTop(){
		return $("#scrollDiv").scrollTop();
	}
	
	function initForm(){
		$("#form1").validate({
			rules: {
				"deviceManagement.name":"required"
			},
			messages: {
				"deviceManagement.name":"请输入设备名称"
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
				if(check() == true){
					$('#form1').ajaxSubmit({ 
				        dataType: 'json',		        
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadList();parent.fb.end();", 2000);
				        	}
				        } 
				    });
				}
			}
		});
	}
	
	function initMaintenanceList(){
		var width = $(".textname").width();
		var maintenanceId = ${result.value.id};
		$("#maintenanceList").jqGrid({
	    	url:'<%=basePath%>deviceMaintenance!list.action?id='+maintenanceId,
			colModel: [
				{label:'档案编号', name:'number', align:'center'}, 
				{label:'上次保养时间', name:'lastTime', align:"center",hidden:true,formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
				{label:'计划保养时间', name:'planTime', align:"center",hidden:true,formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
				{label:'检查项目', name:'plan', align:"center"},
			    {label:'维护工时', name:'manHour', align:"center"},
			    {label:'维护方式', name:'maintenanceMode', align:"center"},
			    {label:'维护日期', name:'maintenanceTime', align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
			    {label:'维护人', name:'maintenanceMan', align:"center"},
			    {label:'维护效果', name:'maintenanceEffect', align:"center",hidden:true},
			    {label:'维护过程', name:'process', align:"center",hidden:true},
			    {label:'维护内容', name:'content', align:"center",hidden:true},
			    {label:'备注', name:'memo', align:"center",hidden:true},
			    {label:'管理', name:'manager', align:"center", resizable:false,sortable:false}
			],
			height: 100,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_view")){ %>
					content += "<img src=\"core/common/images/viewbtn.gif\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('设备维护','deviceMaintenance','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_edit")){ %>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('设备维护','deviceMaintenance','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_delete")){ %>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('maintenanceList','deviceMaintenance','"+id+"');\"  /> ";
					<%}%>
					//content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" style=\"margin-top:4px;\" height=\"14\" title=\"查看\" onclick=\"viewById('设备维护','deviceMaintenance','"+id+"','200');\"  /> "; 
					//content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('设备维护','deviceMaintenance','"+id+"');\"  /> "; 
					//content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('maintenanceList','deviceMaintenance','"+id+"');\"  /> ";
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		});
	}
	
	function initPatrolList(){
		var width = $(".textname").width();
		var maintenanceId = ${result.value.id};
		$("#patrolList").jqGrid({
	    	url:'<%=basePath%>devicePatrol!list.action?id='+maintenanceId,
			colModel: [
				{label:'操作人员', name:'operator', align:"center"}, 
				{label:'巡检日期', name:'operationDate', align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
				{label:'巡检时间', name:'operationTime', align:"center"},
				{label:'检查人', name:'inspector', align:"center"},
			    {label:'检查结果', name:'memo', align:"center"},
			    {label:'创建人', name:'creator', align:"center"},
			    {label:'管理', name:'manager', align:"center", resizable:false,sortable:false}
			],
			height: 100,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
<%-- 					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_devicePatrol_syn")){ %> --%>
// 					content += "<img src=\"core/common/images/send.png\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"同步\" onclick=\"sendById('"+id+"','patrols');\"  /> "; 
<%-- 					<%}%> --%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceYearly_view")){ %>
					content += "<img src=\"core/common/images/viewbtn.gif\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('设备巡检','devicePatrol','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceYearly_edit")){ %>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('设备巡检','devicePatrol','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceYearly_delete")){ %>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('patrolList','devicePatrol','"+id+"');\"  /> ";
					<%}%>
					//content += "<img src=\"core/common/images/send.png\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"同步\" onclick=\"sendById('"+id+"','patrols');\"  /> "; 
					//content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" style=\"margin-top:4px;\" height=\"14\" title=\"查看\" onclick=\"viewById('设备巡检','devicePatrol','"+id+"');\"  /> "; 
					//content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('设备巡检','devicePatrol','"+id+"');\"  /> "; 
					//content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('patrolList','devicePatrol','"+id+"');\"  /> ";
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		});
	}
	
	function initYearlyList(){
		var width = $(".textname").width();
		var maintenanceId = ${result.value.id};
		$("#yearlyList").jqGrid({
	    	url:'<%=basePath%>deviceYearly!list.action?id='+maintenanceId,
			colModel: [
				{label:'年检日期', name:'operationDate', align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
				{label:'年检单位', name:'unitName', align:"center"},
				{label:'操作人员', name:'operator', align:"center"},
			    {label:'年检结果', name:'yearlyResults', align:"center"},
			    {label:'备注', name:'memo', align:"center"},
			    {label:'管理', name:'manager', align:"center", resizable:false,sortable:false}
			],
			height: 100,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
<%-- 					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceYearly_syn")){ %> --%>
// 					content += "<img src=\"core/common/images/send.png\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"同步\" onclick=\"sendById('"+id+"','years');\"  /> "; 
<%-- 					<%}%> --%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_devicePatrol_syn")){ %>
					content += "<img src=\"core/common/images/viewbtn.gif\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('设备年检','deviceYearly','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_devicePatrol_syn")){ %>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('设备年检','deviceYearly','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_devicePatrol_syn")){ %>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('yearlyList','deviceYearly','"+id+"');\"  /> ";
					<%}%>
					//content += "<img src=\"core/common/images/send.png\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"同步\" onclick=\"sendById('"+id+"','years');\"  /> "; 
					//content += "<img src=\"core/common/images/viewbtn.gif\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('设备年检','deviceYearly','"+id+"');\"  /> "; 
					//content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('设备年检','deviceYearly','"+id+"');\"  /> "; 
					//content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('yearlyList','deviceYearly','"+id+"');\"  /> ";
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		});
	}
	
	function initDeviceWorkOrderList(){
		var width = $(".textname").width();
		var maintenanceId = ${result.value.id};
		$("#deviceWorkOrderList").jqGrid({
	    	url:'<%=basePath%>deviceWorkOrder!list.action?id='+maintenanceId,
			colModel: [
				{label:'维修部位', name:'repairParts', align:"center"},
				{label:'开始日期', name:'startDate', align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
				{label:'结束日期', name:'endDate', align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
			    {label:'维修人员', name:'repairStaff', align:"center"},
			    {label:'维修负责人', name:'head', align:"center"},
			    {label:'制单人', name:'tabulator', align:"center"},
			    {label:'维修内容', name:'repairContent', align:"center"},
			    {label:'更换部件', name:'repairComponents', align:"center",hidden:true},
			    {label:'备注', name:'memo', align:"center",hidden:true},
			    {label:'管理', name:'manager', align:"center", resizable:false,sortable:false}
			],
			height: 100,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceWorkOrder_view")){ %>
					content += "<img src=\"core/common/images/viewbtn.gif\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('维修派工单','deviceWorkOrder','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceWorkOrder_edit")){ %>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('维修派工单','deviceWorkOrder','"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceWorkOrder_delete")){ %>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('deviceWorkOrderList','deviceWorkOrder','"+id+"');\"  /> ";
					<%}%>
					//content += "<img src=\"core/common/images/viewbtn.gif\" style=\"margin-top:4px;\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('维修派工单','deviceWorkOrder','"+id+"');\"  /> "; 
					//content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('维修派工单','deviceWorkOrder','"+id+"');\"  /> "; 
					//content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('deviceWorkOrderList','deviceWorkOrder','"+id+"');\"  /> ";
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		});
	}
	
	function sendById(id,name){
		$.post('<%=basePath%>deviceManagement!send.action?id='+id+'&names='+name,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		var idNum=data.deviceManagement.id;
        		<%-- window.location.href="<%=basePath %>deviceManagement!edit.action?id="+idNum; --%>
        		location.reload();
        		
        	}
		});
	}
	function check(){
		var type = $("#patrolIntervalType").val();
		var interval = $("#patrolInterval").val();
		if(type != ''){
			if(interval == ''){
				$("#patrolInterval").focus();
				showTip("请输入巡检间隔",2000);
				return false;
			}
		}else{
			if(interval != ''){
				$("#patrolIntervalType").focus();
				showTip("请选择巡检间隔类型",2000);
				return false;
			}
		}
		return true;
	}
	
	//查看页面大小IE 需调整
	function viewById(fbName,actionName,id){
		switch(fbName){
			case '设备维护': fbStart(fbName,'<%=basePath %>'+actionName+'!view.action?id='+id,600,327);
				break;
			case '设备巡检': fbStart(fbName,'<%=basePath %>'+actionName+'!view.action?id='+id,600,233);
				break;
			case '设备年检': fbStart(fbName,'<%=basePath %>'+actionName+'!view.action?id='+id,620,247);
				break;
			case '维修派工单': fbStart(fbName,'<%=basePath %>'+actionName+'!view.action?id='+id,600,311);
				break;
		}
	}
	function editById(fbName,actionName,id){
		switch(fbName){
			case '设备维护': fbStart(fbName,'<%=basePath %>'+actionName+'!edit.action?id='+id,600,327);
				break;
			case '设备巡检': fbStart(fbName,'<%=basePath %>'+actionName+'!edit.action?id='+id,600,233);
				break;
			case '设备年检': fbStart(fbName,'<%=basePath %>'+actionName+'!edit.action?id='+id,620,247);
				break;
			case '维修派工单': fbStart(fbName,'<%=basePath %>'+actionName+'!edit.action?id='+id,600,267);
				break;
		}
		setTimeout("getOpener().reloadList();", 2000);
	}
	
	<%-- function editById(fbName,actionName,id){
		fbStart(fbName,'<%=basePath %>'+actionName+'!edit.action?id='+id,620,247);
		setTimeout("getOpener().reloadList();", 2000);
	} --%>
	
	//cssId:html标签中的id
	function deleteById(cssId,actionName,id){
		if(confirm("确定要删吗")){
			$.post('<%=basePath%>'+actionName+'!delete.action?id='+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadInitList(cssId,actionName);
	        	}
			});
		}
	}
	
	//id:html标签的id
	function deleteByIds(actionName,id){
		var ids = $("#"+id).jqGrid("getGridParam","selarrrow");
		if(ids!='' && confirm("确定要删吗")){
			$.post('<%=basePath%>'+actionName+'!delete.action?ids='+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadInitList(id,actionName);
	        	}
			});
		}else if(ids =='')
			showTip("请先选择要删除的项",2000);
	}
	//cssId:html标签中的id
	function reloadInitList(cssId,actionName){
		var id = $("#deviceId").val();
		$("#"+cssId).setGridParam({url:'<%=basePath%>'+actionName+'!list.action?id='+id}).trigger('reloadGrid');
	}
</script>
</head>

<body>
<form action="<%=basePath %>deviceManagement!update.action" method="post" name="form1" id="form1">
	<input id="deviceId" name="deviceManagement.id" value="${result.value.id }" type="hidden"/>
	<div id="scrollDiv" style=" overflow:hidden; position:relative;">			
	<div class="basediv">
		<!-- <div class="titlebg">设备基本信息</div> -->
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>设备名称：</td>
					<td width="22%" class="layerright"><input id="name" name="deviceManagement.name"  value="${result.value.name}" type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>设备编号：</td>
					<td width="22%" class="layerright"><input id="code" name="deviceManagement.number" value="${result.value.number}" type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>设备规格：</td>
					<td width="22%" class="layerright"><input id="code" name="deviceManagement.specifications" value="${result.value.specifications}" type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred"></span>设备型号：</td>
					<td class="layerright"><input id="name" name="deviceManagement.type" type="text" value="${result.value.type}" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>制造厂商：</td>
					<td class="layerright"><input id="code" name="deviceManagement.manufacturer" type="text" value="${result.value.manufacturer}" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>出厂编号：</td>
					<td class="layerright"><input id="code" name="deviceManagement.serialNumber" type="text" value="${result.value.serialNumber}" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred"></span>使用单位：</td>
					<td class="layerright"><input id="name" name="deviceManagement.department" value="${result.value.department}" type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>部门编号：</td>
					<td class="layerright"><input id="code" name="deviceManagement.departmentNumber" value="${result.value.departmentNumber}" type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>使用地点：</td>
					<td class="layerright"><input id="code" name="deviceManagement.location" value="${result.value.location}" type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred"></span>制造日期：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="manufacturingDate" name="deviceManagement.manufacturingDate" value='<fmt:formatDate value="${result.value.manufacturingDate}" pattern="yyyy-MM-dd"/>' type="text" readonly="readonly" class="inputauto" onclick="showCalendar('manufacturingDate');"/></td>
								<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="showCalendar('manufacturingDate');"/></td>
							</tr>
						</table>
					</td>
					<td class="layertdleft100"><span class="psred"></span>使用日期：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="usedDate" name="deviceManagement.usedDate" value='<fmt:formatDate value="${result.value.usedDate}" pattern="yyyy-MM-dd"/>' type="text" readonly="readonly" class="inputauto" onclick="showCalendar('usedDate');"/></td>
								<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="showCalendar('usedDate');"/></td>
							</tr>
						</table>
					</td>
					<td class="layertdleft100"><span class="psred"></span>设备类别：</td>
					<td class="layerright"><input id="code" name="deviceManagement.category" value="${result.value.category}" type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred"></span>设备状态：</td>
					<td class="layerright">
						<enum:select id="name" name="deviceManagement.status" checked="result.value.status" type="com.wiiy.estate.preferences.enums.DeviceStatusEnum"/>
					</td>
					<td class="layertdleft100"><span class="psred"></span>设备级别：</td>
					<td class="layerright"><input id="code" name="deviceManagement.level" value="${result.value.level}" type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>复杂系数：</td>
					<td class="layerright"><input id="code" name="deviceManagement.degree" value='<fmt:formatNumber value="${result.value.degree}" pattern="#0.0"/>' type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred"></span>巡检间隔：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td ><input id="patrolInterval" name="deviceManagement.patrolInterval" value="${result.value.patrolInterval}" type="text" class="inputauto" /></td>
								<td width="" style="position: relative;left:5px;height:22px;border-color: #333;">
									<enum:select id="patrolIntervalType" name="deviceManagement.patrolIntervalType" checked="result.value.patrolIntervalType"  type="com.wiiy.estate.preferences.enums.PatrolIntervalEnum"/>
								</td>
							</tr>
						</table>
					</td>
					<td class="layertdleft100"><span class="psred"></span>巡检日期：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="patrolTime" name="deviceManagement.patrolTime" value='<fmt:formatDate value="${result.value.patrolTime}" pattern="yyyy-MM-dd"/>' type="text" readonly="readonly" class="inputauto" onclick="showCalendar('patrolTime');"/></td>
								<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="showCalendar('patrolTime');"/></td>
							</tr>
						</table>
					</td>
					<%-- <td class="layertdleft100"><span class="psred"></span>年检间隔：</td>
					<td class="layerright"><input id="name" name="deviceManagement.yearlyInterval" value="${result.value.yearlyInterval}" type="text" class="inputauto" /></td> --%>
					<td class="layertdleft100"><span class="psred"></span>年检日期：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="yearlyTime" name="deviceManagement.yearlyTime" value='<fmt:formatDate value="${result.value.yearlyTime}" pattern="yyyy-MM-dd"/>' type="text" readonly="readonly" class="inputauto" onclick="showCalendar('yearlyTime');"/></td>
								<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="showCalendar('yearlyTime');"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred"></span>设备原值：</td>
					<td class="layerright"><input id="name" name="deviceManagement.cost" value='<fmt:formatNumber value="${result.value.cost}" pattern="#0.00"/>' type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>折旧年限：</td>
					<td class="layerright"><input id="code" name="deviceManagement.depreciationYear" value="${result.value.depreciationYear}" type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>外形尺寸：</td>
					<td class="layerright"><input id="code" name="deviceManagement.size" type="text" value="${result.value.size}" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred"></span>设备重量：</td>
					<td class="layerright"><input id="name" name="deviceManagement.weight" value="${result.value.weight}" type="text" class="inputauto" /></td>
					<td class="layertdleft100"><span class="psred"></span>数控设备：</td>
					<td class="layerright">
						<enum:select id="code" name="deviceManagement.isCNC" checked="result.value.isCNC" type="com.wiiy.commons.preferences.enums.BooleanEnum"/>
					</td>
					<td class="layertdleft100"><span class="psred"></span>进口设备：</td>
					<td class="layerright">
						<enum:select id="code" name="deviceManagement.isImported" checked="result.value.isImported" type="com.wiiy.commons.preferences.enums.BooleanEnum"/>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">备注：</td>
					<td colspan="5" class="layerright">
						<textarea name="deviceManagement.memo" class="inputauto" style="height:40px;resize: none;">${result.value.memo}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div class="hackbox"></div>
	</div>
	<div style="height:190px;overflow:hidden;">
		<div class="apptab" id="tableid">
			<ul>
				<li class="apptabliover" onclick="tabSwitch('apptabli','apptabliover','tabswitch',0)">设备维护</li>
				<li class="apptabli tabheader" onclick="tabSwitch('apptabli','apptabliover','tabswitch',1)">设备巡检</li>
				<li class="apptabli tabheader" onclick="tabSwitch('apptabli','apptabliover','tabswitch',2)">设备年检</li>
				<li class="apptabli tabheader" onclick="tabSwitch('apptabli','apptabliover','tabswitch',3)">维修派工单</li>
			</ul>
		</div>
		<div style="margin-top:0px;">
			<!-- 设备维护 -->
			<div class="basediv tabswitch textname" style="margin-top:0px;">
				<div class="emailtop">
					<div id="" class="leftemail">
						<ul>
						<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_new")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="fbStart('新增设备维护',
									'<%=basePath %>web/property/deviceMaintenance_add.jsp?deviceId=${result.value.id }',600,327);">
								<span><img src="core/common/images/emailadd.gif" /></span>添加
							</li>
						<%}%>
						<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_delete")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="deleteByIds('deviceMaintenance','maintenanceList')">
								<span><img src="core/common/images/emaildel.png" /></span>删除
							</li>
						<%}%>
						</ul>
					</div>
				</div>
				<table id="maintenanceList"><tr><td></td></tr></table>
			</div>
			
			<!-- 设备巡检 -->
			<div class="basediv tabswitch textname" style="margin-top:0px;display:none;" >
				<div class="emailtop">
					<div id="" class="leftemail">
						<ul>
						<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_devicePatrol_add")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="fbStart('新增设备巡检',
									'<%=basePath %>web/property/devicePatrol_add.jsp?deviceId=${result.value.id }',600,233);">
								<span><img src="core/common/images/emailadd.gif" /></span>添加
							</li>
							<%}%>
							<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_devicePatrol_delete")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="deleteByIds('devicePatrol','patrolList')">
								<span><img src="core/common/images/emaildel.png" /></span>删除
							</li>
							<%}%>
						</ul>
					</div>
				</div>
				<table id="patrolList"><tr><td></td></tr></table>
			</div>
			
			<!-- 设备年检 -->
			<div class="basediv tabswitch textname" style="margin-top:0px;display:none;" >
				<div class="emailtop">
					<div id="" class="leftemail">
						<ul>
						<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceYearly_add")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="fbStart('新增设备年检',
									'<%=basePath %>web/property/deviceYearly_add.jsp?deviceId=${result.value.id }',620,247);">
								<span><img src="core/common/images/emailadd.gif" /></span>添加
							</li>
							<%}%>
							<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceYearly_delete")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="deleteByIds('deviceYearly','yearlyList')">
								<span><img src="core/common/images/emaildel.png" /></span>删除
							</li>
							<%}%>
						</ul>
					</div>
				</div>
				<table id="yearlyList"><tr><td></td></tr></table>
			</div>
			
			<!-- 维修派工单 -->
			<div class="basediv tabswitch textname" style="margin-top:0px;display:none;" >
				<div class="emailtop">
					<div id="" class="leftemail">
						<ul>
						<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceWorkOrder_add")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="fbStart('新增维修派工单',
									'<%=basePath %>web/property/deviceWorkOrder_add.jsp?deviceId=${result.value.id }',600,267);">
								<span><img src="core/common/images/emailadd.gif" /></span>添加
							</li>
							<%}%>
						<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_add_deviceWorkOrder_delete")){ %>
							<li onmouseover="this.className='libg'" 
								onmouseout="this.className=''" 
								onclick="deleteByIds('deviceWorkOrder','deviceWorkOrderList')">
								<span><img src="core/common/images/emaildel.png" /></span>删除
							</li>
							<%}%>
						</ul>
					</div>
				</div>
				<table id="deviceWorkOrderList"><tr><td></td></tr></table>
			</div>
			
		</div>
	</div>
	<div class="buttondiv" style="margin-top:2px;height:auto;">
		<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end()"/></label>
	</div>
</div>
</form>
</body>
</html>
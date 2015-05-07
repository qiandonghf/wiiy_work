<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		initList();
	});
	
	function initList(){
		var height = getTabContentHeight()-79;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'${contextLocation}workflowRole!load.action',
			colModel: [
				{label:'id', name:'id',align:"center",hidden:true,width:200}, 
				{label:'名称', width:200,name:'name', align:"center"},
				{label:'人员', width:width-600,name:'memberNames', align:"center"},
				{label:'类型',width:230,name:'type',align:"center"}, 
				{label:'管理',width:80, name:'manager', align:"center", sortable:false, resizable:false}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			multiselect: true,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					//var stateTxt = $(this).getCell(id,'state.title');
					var content = "";
					<% boolean add = PfActivator.getHttpSessionService().isInResourceMap("workflow_role_add");
					boolean edit = PfActivator.getHttpSessionService().isInResourceMap("workflow_role_edit");
					boolean delete = PfActivator.getHttpSessionService().isInResourceMap("workflow_role_delete");
					boolean setting = PfActivator.getHttpSessionService().isInResourceMap("workflow_role_setting");
					if(edit){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%} %> 
					
					<%if(delete){%>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
					<%} %>
					<%if(setting){%>
					content += "<img src=\"core/common/images/adminbtn.png\" width=\"15\" height=\"13\" title=\"指定人员\" onclick=\"settingById('"+id+"');\"  /> ";
					<%} %>
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		}).navGrid('#pager',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager',{
		    caption : "列选择",
		    title : "选择要显示的列",
		    onClickButton : function(){
		        $(this).jqGrid('columnChooser');
		    }
		});
	}	
	function viewById(id){
		fbStart('查看','${contextLocation}workflowRole!view.action?id='+id,670,410);
	}	
	
	function editById(id){
		fbStart('编辑','${contextLocation}workflowRole!edit.action?id='+id,400,124);
	}
	function settingById(id){
		fbStart('指定人员','${contextLocation}workflowRole!multiSelect.action?id='+id,486,395);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function refreshTree(){
		$("#list").trigger("reloadGrid");
	}
	
	function setSelectedUsers(groupId,users){
		//alert(111);
		var ids = "";
		for(var i = 0; i < users.length; i++){
			ids += users[i].id+",";
		}
		ids = deleteLastCharWhenMatching(ids,",");
		$.post("${contextLocation}workflowRole!multiSelectSave.action?ids="+ids+"&id="+groupId,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		reloadList();
        	}
		});
	}
	
	function deleteById(id){
		if(confirm("确定删除此角色")){
			$.post("${contextLocation}workflowRole!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}
	}
	
</script>
</head>
<body>
<!--emailtop-->
<div class="emailtop">
	<div class="leftemail">
		<ul>
			<%
			if (add) {
			%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建角色','<%=basePath %>workflowRole!add.action',400,124);"><span><img src="core/common/images/emailadd.gif"/></span>新建</li>
			<%} %>
			<%
			if (delete) {
			%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
			<%} %>
		</ul>
	</div>
</div>
<!--//emailtop-->
<!--container-->
<div class="msglist" id="msglist" style="padding-bottom:0px;">
	<div id="container">
		<table id="list" class="jqGridList"><tr><td/></tr></table>
		<div id="pager"></div>
	</div>
</div>
<!--//container-->
</body>
</html>

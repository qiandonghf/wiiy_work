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
<base href="<%=BaseAction.rootLocation%>/"/>
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
	    	url:'<%=basePath%>process!list.action',
			colModel: [
				{label:'id', name:'id',align:"center",hidden:true,width:200}, 
				{label:'deploymentId', name:'deploymentId',hidden:true,align:"center",width:200}, 
				{label:'名称', width:160,name:'name', align:"center"},
				{label:'KEY',width:80,name:'key',align:"center"}, 
				{label:'版本号',width:80,name:'version',align:"center"},
				{label:'XML',width:160,name:'resourceName',align:"center",formatter: function (value, grid, rec, state) 
					{ return "<a title='点击查看XML' style='text-decoration:none;color:blue;' href='<%=basePath%>process!resourceRead.action?id="+rec.id+"&resourceType=xml' target='_blank'>"+value+"</a>" }},
				{label:'图片',width:160,name:'diagramResourceName',align:"center",formatter: function (value, grid, rec, state) 
					{ return "<a title='点击查看图片' style='text-decoration:none;color:blue;' href='<%=basePath%>process!resourceRead.action?id="+rec.id+"&resourceType=image' target='_blank'>"+value+"</a>" }},
				{label:'部署时间',width:80,name:'deploymentTime',align:"center",sortable:false, resizable:false,formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'状态',width:120,name:'state.title',align:"center",sortable:false, resizable:false}, 
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
					var stateTxt = $(this).getCell(id,'state.title');
					var content = "";
					<%boolean add = PfActivator.getHttpSessionService().isInResourceMap("workflow_process_add");
					boolean edit = PfActivator.getHttpSessionService().isInResourceMap("workflow_process_edit");
					boolean delete = PfActivator.getHttpSessionService().isInResourceMap("workflow_process_delete");
					boolean state = PfActivator.getHttpSessionService().isInResourceMap("workflow_process_changeState");%>
					//content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%-- <%if(edit){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%} %> --%>
					<%if(state){%>
						if(stateTxt == '激活')
							content += "<img src=\"core/common/images/back.png\" width=\"14\" height=\"14\" title=\"挂起流程\" onclick=\"changeState('"+id+"');\"  /> ";
						else
							content += "<img src=\"core/common/images/uploadicon.png\" width=\"14\" height=\"14\" title=\"激活流程\" onclick=\"changeState('"+id+"');\"  /> ";
					<%} %>
					<%if(delete){%>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
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
		fbStart('查看','<%=basePath%>param!view.action?id='+id,670,410);
	}	
	
	function editById(id){
		fbStart('编辑','<%=basePath%>param!edit.action?id='+id,670,508);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function refreshTree(){
		$("#list").trigger("reloadGrid");
	}
	
	function changeState(id){
		$.post("<%=basePath%>process!changeState.action?id="+id,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		reloadList();
        	}
		});
	}
	
	function deleteById(id){
		if(confirm("确定删除此流程")){
			$.post("<%=basePath%>process!processDelete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}
	}
	
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids==""){
			showTip('请先选择一个流程',2000);	
		}else if(confirm("确定要删除这些流程")){
			$.post("<%=basePath%>process!processDelete.action?ids="+ids,function(data){
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
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建流程','<%=basePath %>web/process/process_add.jsp',670,109);"><span><img src="core/common/images/emailadd.gif"/></span>新建</li>
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

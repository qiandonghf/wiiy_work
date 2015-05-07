<%@page import="com.wiiy.common.activator.ProjectActivator"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<link href="core/common/style/base.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/smartMenu.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css"  href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css"  href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"  href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initList("list");
	});
	function initList(list){
		<%
			Map<String, ResourceDto> resourceMap = ProjectActivator.getHttpSessionService().getResourceMap();
			boolean view = ProjectActivator.getHttpSessionService().isInResourceMap("park_business_reserve_history_view");
		%>
		$("#"+list).jqGrid({
			url:'<%=BaseAction.rootLocation%>/department.business/registrationBook!list.action?id=${param.id}',
	    	colModel: [
				{label:'房间名称',width:30,name:'roomName',align:"center"}, 
   				{label:'客户名称',width:50,name:'name',align:"center", sortable:false}, 
   				{label:'招商顾问',width:50,name:'user.realName',align:"center", sortable:false}, 
   			    {label:'工作电话', name:'phone', align:"center",width:50},
   				{label:'移动电话', name:'mobile', align:"center",width:50,hidden:true}, 
   				{label:'预定日期',width:30,name:'registratTime',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
   				{label:'终止日期',width:30,name:'endTime',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
			    {label:'预定原因', name:'reson', align:"center", hidden:true},
			    {label:'单价', name:'saleUnit', align:"center", hidden:true},
			    {label:'面积', name:'saleArea', align:"center", hidden:true},
			    {label:'总价', name:'salePrice', align:"center", hidden:true},
			    {label:'是否同步到房间', name:'whetherRoom', align:"center", hidden:true},
			    {label:'状态', width:'30' ,name:'entityStatus.title',index:'entityStatus', align:"center",formatter:checkStatus}, 
			    {label:'出租/出售', width:'30' ,name:'rentStatus.title',index:'rentStatus', align:"center"},
			    {label:'管理',width:50, name:'manager', align:"center", sortable:false, resizable:false}
   			],
			height: 166,
			width: 782,
			rowNum: 0,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(view){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%} %>
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		});
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function viewById(id){
		fbStart('查看','<%=BaseAction.rootLocation%>/department.business/registrationBook!view.action?id='+id,620,414);
	}
	function checkStatus(cellvalue,options,rowObject){
		if(cellvalue =='禁用'){
			return "关闭";
		}
		return cellvalue;
	}
	function changeState(id){
		$.post("<%=BaseAction.rootLocation%>/department.business/registrationBook!changeState.action?id="+id,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		reloadList();
        	}
		});
	}
</script>
</head>

<body style="background:#fff;">
	<div class="pm_msglist">
		<table id="list" class="jqGridList"><tr><td/></tr></table>
	</div>
</body>
</html>

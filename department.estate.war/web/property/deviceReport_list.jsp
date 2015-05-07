<%-- <%@page import="com.wiiy.crm.preferences.enums.ContractItemEnum"%> --%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page import="com.wiiy.estate.activator.EstateActivator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String contractIds = request.getParameter("contractIds");
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

<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
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
		var height = getTabContentHeight()-107;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>deviceManagement!listDesktop.action?report=true',
			colModel: [
				{label:'ID', name:'id', align:"center",width:60,hidden:true}, 
				{label:'设备名称', name:'name', align:"center",width:120}, 
				{label:'巡检间隔', name:'patrolInterval',align:"center",width:130}, 
				{label:'巡检日期', name:'reportPatrol', align:"center",formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}}, 
				{label:'年检日期', name:'reportYearly', align:"center",formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}}, 
				{label:'提醒类型', name:'reportType', align:"center"}, 
			    {label:'管理', name:'operation', align:"center", resizable:false,sortable:false,width:78}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_device_deviceManage_edit")){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> "; 
					<%}%>
					$(this).jqGrid('setRowData',id,{operation:content});
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
	
	function editById(id){
		fbStart('编辑设备管理','<%=basePath %>deviceManagement!edit.action?id='+id,770,498);
	}

	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
	}

</script>

</head>
<body>
<form action="<%=basePath%>contract!export.action?fileName=END" id="exportForm" method="post">
	<input type="hidden" id="columns" name="columns"/>
	<input type="hidden" id="filters" name="filters"/>
</form>
<div class="emailtop" style="position:relative;z-index:99999;">
	<div class="leftemail">
		<ul>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="reloadList()"><span><img src="core/common/images/refresh3.png"/></span>刷新</li>
		</ul>
	</div>
</div>
<div  id="container">
	<div class="msglist" style="display:block;" id="textname" name="textname">
		<table id="list" class="jqGridList"><tr><td></td></tr></table>
		<div id="pager"></div>
	</div>
</div>
</body>
</html>
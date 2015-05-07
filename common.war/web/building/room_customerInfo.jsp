<%@page import="com.wiiy.common.activator.ProjectActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
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
		$(document).ready(function() {
			initList();
		});
		
		function initList(){
			$("#list").jqGrid({
				jsonReader: {root:"result.value",repeatitems: false},
		    	url:'<%=basePath %>room!customerList.action?id=${param.id}',
				colModel: [
					{label:'企业名称', name:'name', align:"center",sortable:false}, 
					{label:'企业编号', name:'code', align:"center",sortable:false}, 
				    {label:'入驻状态', name:'parkStatus.title', align:"center",sortable:false},
				    {label:'孵化企业', name:'incubated.title', align:"center",sortable:false},
				    {label:'管理', name:'manager', align:"center",width:80,sortable:false, resizable:false}
				],
				height: 166,
				width: 770,
				shrinkToFit: false,
				gridComplete: function(){
					var ids = $(this).jqGrid('getDataIDs');
					for(var i = 0 ; i < ids.length; i++){
						var id = ids[i];
						var content = "";
						var row = $(this).jqGrid('getRowData',id);
						var name = row.name;
						content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+name+"','"+id+"');\"  /> "; 
						$(this).jqGrid('setRowData',id,{manager:content});
					}
				}
			});
		}
		
		function viewById(name,id){
			fbStart(name,'<%=BaseAction.rootLocation %>/department.business/customer!view.action?id='+id,870,460);
		}
	</script>
</head>

<body style="background:#fff;">
	<div class="pm_msglist">
		<table id="list" class="jqGridList"><tr><td/></tr></table>
	</div>
</body>
</html>

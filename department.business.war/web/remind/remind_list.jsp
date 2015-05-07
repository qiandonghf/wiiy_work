<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%
	String path = request.getContextPath();
	String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
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
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		//$("#resizable").resizable();
		$("#resizable").css("height",getTabContentHeight()-28);
		$("#msglist").css("height",getTabContentHeight()-28);
		$("#treeviewdiv").css("height",getTabContentHeight()-82);
		initList();
	});
	
	function initList(){
		var height = getTabContentHeight()-52;
		var width = $(this).width();
		$("#list").jqGrid({
	    	url:'<%=basePath%>billPlanRent!remindList.action',
	    	colModel: [
				{label:'编号',width:100,name:'code',align:"center"}, 
				{label:'计划金额',name:'planFee',align:"center",formatter:'number',formatoptions:{decimalSeparator:".",thousandsSeparator: "",defaultValue: '0.00'}}, 
				{label:'已审核',width:60,name:'audit.title',align:"center"}, 
				//{label:'状态',width:80,name:'status.title',align:"center"}, 
				{label:'审批状态',width:80,name:'approvalStatus.title',align:"center"}, 
				//{label:'计费开始日期',hidden:true,name:'startDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				//{label:'计费结束日期',hidden:true,name:'endDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'计划付费日期',name:'planPayDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},  
			    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:100}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
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
		fbStart('查看结算计划','<%=basePath%>billPlanRent!view.action?id='+id,650,265);
	}
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
</script>

</head>
<body>
<div class="msglist" id="msglist">
	<div id="container">
		<table id="list" class="jqGridList"><tr><td/></tr></table>
		<div id="pager"></div>
	</div>
</div>
<!--//container-->
</body>
</html>
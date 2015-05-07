<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	
	<script type="text/javascript">
		$(function(){
			initTip();
			initList();
		});
 		
		function initList(){
			var height = $(this).height()-60;
 			$("#list").jqGrid({
 		    	url:'<%=BaseAction.rootLocation%>/parkmanager.pf/bill!list.action?department=SYNTHESIS&contractId=${param.id }',
 				colModel: [
 					{label:'计划编号',width:80,name:'code',align:"center"}, 
 					{label:'金额',width:100,name:'settlementFee',align:"center",formatter:'number',formatoptions:{decimalSeparator:".",thousandsSeparator: "",defaultValue: '0.00'}}, 
 					{label:'结算日期',width:80,name:'settlementDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
 					{label:'收付方向',width:60,name:'payment.title',align:"center"}, 
 					{label:'结算方式',width:60,name:'settlement.title',align:"center"}, 
 					{label:'结算性质',width:60,name:'settlementType.title',align:"center"}, 
 					{label:'已付款',width:40,name:'paid.title',align:"center"}, 
 				    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:60}
 				],
 				height: height,
 				width: 630,
 				shrinkToFit: false,
 				gridComplete: function(){
 					var ids = $(this).jqGrid('getDataIDs');
 					for(var i = 0 ; i < ids.length; i++){
 						var id = ids[i];
 						var content = "";
 						<%boolean add = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_rent_add");
 						boolean edit = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_rent_edit");
 						boolean view = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_rent_view");
 						boolean del = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_rent_delete");%>
 						<%if(view){%>
 						content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"billView('"+id+"');\"  /> ";
 						<%}%>
 						<%-- <%if(edit){%>
 						content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"billEdit('"+id+"');\"  /> ";
 						<%}%>
 						<%if(del){%>
 						content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"billDelete('"+id+"');\"  /> ";
 						<%}%> --%>
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
 		function billView(id){
 			fbStart('查看结算流程','<%=BaseAction.rootLocation %>/parkmanager.pf/bill!viewById.action?id='+id,650,236);
 		}
 		<%-- function billEdit(id){
 			fbStart('编辑结算流程','<%=basePath%>billRent!edit.action?id='+id,650,257);
 		}
 		function billDelete(id){
 			if(confirm("您确定要删除")){
 				$.post("<%=basePath%>billRent!delete.action?id="+id,function(data){
 					showTip(data.result.msg,1000);
 					if(data.result.success){
 		        		reloadList();
 		        	}
 				});
 			}
 		}
 		function billDeleteSelected(){
 			var ids = $("#contractBill").jqGrid("getGridParam","selarrrow");
 			if(ids==""){
 				showTip('请先选择结算流程',2000);	
 			}else if(confirm("确定要删除这些结算流程")){
 				$.post("<%=basePath%>billRent!delete.action?ids="+ids,function(data){
 					showTip(data.result.msg,2000);
 		        	if(data.result.success){
 		        		reloadList();
 		        	}
 				});
 			}
 		} --%>
 		function reloadList(){
 			$("#list").trigger("reloadGrid");
 		}
 	
	</script>
</head>
<body>
<div class="basediv" style="margin-top:0px;">
	  <!-- 合同文件 -->
	  <div class="divlays" style="margin:0px;padding:0px;">
	  	<div class="basediv" style="margin: 0px;border:0px;">
			<!--container-->
			<div class="msglist" id="msglist" style="border:0px;padding-bottom:0px;">
				<div id="container">
					<table id="list" class="jqGridList"><tr><td/></tr></table>
					<div id="pager"></div>
				</div>
			</div>
			<!--//container-->
		</div>
	  </div>
</div>
</body>
</html>

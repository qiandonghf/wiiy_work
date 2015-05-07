<%@page import="com.wiiy.engineering.activator.EngineeringActivator"%>
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
			var height = $(this).height()-86;
 			$("#list").jqGrid({
 		    	url:'<%=basePath%>billPlanRent!list.action?id=${param.id }',
 				colModel: [
 					{label:'合同名称',name:'contract.name',align:"center",hidden:true}, 
 					{label:'编号',width:100,name:'code',align:"center"}, 
 					{label:'计划金额',name:'planFee',align:"center",formatter:'number',formatoptions:{decimalSeparator:".",thousandsSeparator: "",defaultValue: '0.00'}}, 
 					{label:'已审核',width:60,name:'audit.title',align:"center"}, 
 					{label:'状态',width:80,name:'status.title',align:"center"}, 
 					{label:'审批状态',width:80,name:'approvalStatus.title',align:"center"}, 
 					{label:'计费开始日期',hidden:true,name:'startDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
 					{label:'计费结束日期',hidden:true,name:'endDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
 					{label:'计划付费日期',hidden:true,name:'planPayDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},  
 				    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:100}
 				],
 				height: height,
 				width: 630,
 				shrinkToFit: false,
 				gridComplete: function(){
 					var ids = $(this).jqGrid('getDataIDs');
 					for(var i = 0 ; i < ids.length; i++){
 						var id = ids[i];
 						var content = "";
 						var launch = $(this).getCell(id,'status.title');
 						var audit = $(this).getCell(id,'audit.title');
 						var approval = $(this).getCell(id,'approvalStatus.title');
 						<%boolean add = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_add");
 						boolean edit = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_edit");
 						boolean view = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_view");
 						boolean del = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_delete");
 						boolean launch = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_launch");%>
 						if(launch == '未出账' && approval == '未发起'){
 							if(audit == '是'){
		 						<%if(launch){%>
		 						content += "<img src=\"core/common/images/uploadicon.png\" width=\"14\" height=\"14\" title=\"发起结算流程\" onclick=\"launchById('"+id+"');\"  /> ";
		 						<%}%>
 							}
	 						<%if(view){%>
	 						content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
	 						<%}%>
	 						<%if(edit){%>
	 						content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
	 						<%}%>
	 						<%if(del){%>
	 						content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
	 						<%}%>
 						}else{
	 						<%if(view){%>
	 						content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
	 						<%}%>
 						}
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
 		function addById(id){
 			fbStart('新建结算计划','<%=basePath%>web/contract/billPlanRent_add.jsp?id='+id,650,240);
 		}
 		function editById(id){
 			fbStart('编辑结算计划','<%=basePath%>billPlanRent!edit.action?id='+id,650,240);
 		}
 		function launchById(id){
 			var iWidth=1024; //弹出窗口的宽度;
 			var iHeight=500; //弹出窗口的高度;
 			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
 			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
 			var openUrl = "";
 			openUrl = '<%=BaseAction.rootLocation%>/parkmanager.pf/bill!addByPlanId.action?contractId=${result.value.id }&department=ENGINEERING&id='+id;//弹出窗口的url
 			window.open(openUrl,'发起结算流程','top='+iTop+',left='+iLeft+',height='+iHeight+',width='+iWidth+',toolbar=no,menubar=no,scrollbars=yes,resizable=no, location=no,status=no');
 		}
 		function deleteById(id){
 			if(confirm("您确定要删除")){
 				$.post("<%=basePath%>billPlanRent!delete.action?id="+id,function(data){
 					showTip(data.result.msg,1000);
 					if(data.result.success){
 						reloadList();
 		        	}
 				});
 			}
 		}
 		function deleteSelected(){
 			var ids = $("#list").jqGrid("getGridParam","selarrrow");
 			if(ids==""){
 				showTip('请先选择结算计划',2000);	
 			}else if(confirm("确定要删除这些结算计划")){
 				$.post("<%=basePath%>billPlanRent!delete.action?ids="+ids,function(data){
 					showTip(data.result.msg,2000);
 					if(data.result.success){
 						reloadList();
 		        	}
 				});
 			}
 		}
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
			<div class="emailtop">
				<div class="leftemail">
					<ul>
						<%if(add){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="addById(${param.id})"><span><img src="core/common/images/emailadd.gif" /></span>新建</li>
						<%}%>
						<%-- <%if(del){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fileDeleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
						<%} %> --%>
					</ul>
				</div>
			</div>
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

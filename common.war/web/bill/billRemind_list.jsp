<%@page import="com.wiiy.common.activator.ProjectActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css" />
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
<script>
	$(document).ready(function() {
		initTip();
		$("#resizable").css("height",getTabContentHeight());
		$("#msglist").css("height",getTabContentHeight());
		initList();
	});
	function initList(){
		var height = getTabContentHeight()-79;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>billRemind!list.action?feeType=${param.feeType}&department=${param.department}',
			colModel: [
				{label:'企业名称', name:'customerName',sortable:false, align:"center"}, 
				{label:'流水号', name:'number',sortable:false, align:"center"}, 
			    {label:'费用类型', name:'billType.typeName',index:'billType',sortable:false, align:"center"}, 
				{label:'收支', name:'inOut.title',index:'inOut',sortable:false, align:"center",formatter:outChange}, 
			    {label:'结算金额', name:'factPayment',sortable:false, align:"center"},
			    {label:'计费开始时间',width:80, name:'feeStartTime',sortable:false, align:"center", formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'计费结束时间',width:80, name:'feeEndTime',sortable:false, align:"center", formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'出账日期', name:'checkoutTime', align:"center",sortable:false, formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'催缴状态', name:'remindStatus.title',index:'remindStatus', align:"center"},
			    {label:'企业编号', name:'customerId',sortable:false, align:"center",hidden :true},
			    {label:'管理', name:'manager',sortable:false, align:"center", resizable:false},
			],
			height: height,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var customerId = $(this).getCell(id,"customerId");
					var customerName =  $(this).getCell(id,"customerName");
					var content = "";
					
					<%if(ProjectActivator.getHttpSessionService().isInResourceMap("estate_settle_remind_reminder_view")){%>
						content += "<a href=\"javascript:void(0)\" onclick=\"viewById('"+id+"');\">查看</a> ";
					<%}%>
					<%if(ProjectActivator.getHttpSessionService().isInResourceMap("estate_settle_remind_reminder_add")){%>
						content += "<a href=\"javascript:void(0)\" onclick=\"remindById('"+id+"');\">催缴</a> ";
					<%}%>
					<%if(ProjectActivator.getHttpSessionService().isInResourceMap("estate_settle_remind_reminder_delete")){%>
						content += "<a href=\"javascript:void(0)\" onclick=\"deleteById('"+id+"');\">删除</a> ";
					<%}%>
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
	
	function remindById(id){
		if(confirm("确定设置已催缴")){
			$.post("<%=basePath%>billRemind!remind.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	
	function outChange(cellvalue, options, rowObject ){
		if(cellvalue=='支出'){
			cellvalue = "<span style='color:red'>支出</span>";
		}
		return cellvalue;
	}
	
	function viewById(id){
		fbStart($("#list").getRowData(id)["customerName"],'<%=basePath %>billRemind!view.action?id='+id,550,360);
	}
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function deleteById(id){
		if(confirm("确定要删除吗")){
			$.post("<%=basePath%>billRemind!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids!='' && confirm("确定要删吗")){
			$.post("<%=basePath%>billRemind!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
	}
	function setSelectedCustomer(customer){
		$("#customerId").val(customer.id);
		$("#customerName").val(customer.name);
	}
	
	function exportDate(){
		var columns = "{";
		$.each($("#list").getGridParam("colModel"),function(){
			if(this.label && this.name!="manager" && !this.hidden){
				columns += "\"" + this.name + "\"" + ":" + "\"" + this.label + "\",";
			}
		});
		columns += "\"" + "roomAddress" + "\"" + ":" + "\"" + "房间地址" + "\",";
		columns += "\"" + "roomArea" + "\"" + ":" + "\"" + "房间面积" + "\",";
		columns = deleteLastCharWhenMatching(columns,",");
		columns += "}";
		$("#columns").val(columns);
		$("#exportForm").submit();
	}
	
	//$("#list").jqGrid("getGridParam","selarrrow");

</script>
</head>

<body>
<form action="<%=basePath%>billRemind!export.action" id="exportForm" method="post">
	<input type="hidden" id="columns" name="columns"/>
	<input type="hidden" id="filters" name="filters"/>
</form>
	<div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" valign="top">
					<div class="searchdivkf">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" height="25">
							<tr>
								<td width="65">企业名称：</td>
								<td width="200">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<search:choose dataType="long" field="customerId" op="eq">
												<input type="hidden" id="customerId" class="data"/>
												</search:choose>
												<search:choose dataType="string" field="customerName" op="cn">
												<input onkeyup="$('#customerId').val('')" id="customerName" name="endTime" type="text" class="data inputauto"/>
												</search:choose>
											</td>
											<td width="20"><img style="cursor:pointer;" src="core/common/images/outdiv.gif" width="20" height="22"  onclick="fbStart('选择企业','<%=basePath %>customer!select.action',520,400);"/></td>
										</tr>
									</table>
								</td>
								<td><label><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map"></label></td>
							</tr>
						</table>
					</div>
					
					<div style="overflow: auto;">
						<table id="list" class="jqGridList"><tr><td/></tr></table>
						<div id="pager"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<map name="Map" id="Map">
		<area shape="rect" coords="1,2,50,21" href="javascript:void(0)" onclick="doSearch()"/>
		<area shape="rect" coords="54,0,73,19" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/billRemind/billRemind_search.jsp',550,410);" />
	</map>
</body>
</html>

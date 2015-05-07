<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
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
<link rel="stylesheet" type="text/css" href="core/common/style/smartMenu.css"/>
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
<style>a{TEXT-DECORATION:none}</style>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		//$("#resizable").resizable();
		$("#resizable").css("height",getTabContentHeight()-28);
		$("#msglist").css("height",getTabContentHeight()-28);
		$("#treeviewdiv").css("height",getTabContentHeight()-82);
		//initTree();
		initList();
	});
	
	function initList(){
		var height = getTabContentHeight()-75;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>contract!list.action',
			colModel: [
				{label:'项目',width:120,name:'projectName',align:"center"}, 
				{label:'合同编号',width:100,name:'contract.code',align:"center"}, 
				{label:'合同名称',width:120,name:'contract.name',align:"center"}, 
				{label:'供应商名称',width:100,name:'supplierName',align:"center"}, 
				{label:'客户名称',width:100,name:'customerName',align:"center"}, 
				{label:'开始日期',width:100,name:'contract.startDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'结束日期',width:100,name:'contract.endDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'签订日期',width:100,name:'contract.signDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
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
					var content = "";
					<%Map<String,ResourceDto> resourceMap = SynthesisActivator.getHttpSessionService().getResourceMap();
					boolean view = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_contract_view");%>
					<%if(view){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
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

<%-- 	function viewById(id){
		fbStart($($("#list").getRowData(id).name).text(),'<%=basePath %>contract!view.action?id='+id,870,453);
	} --%>
	function viewById(id){
		fbStart('查看','<%=basePath%>contract!view.action?id='+id,650,407);
	}	
	function reloadList(){
   		$("#list").trigger("reloadGrid");
	}
	function doSearch(){
		var filters = getSearchFilters();
		$("#filters").val(filters);
		search(filters);
	}
	function search(filters){
		$("#filters").val(filters);
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
	}
</script>

</head>
<body>
<form action="<%=basePath%>contract!export.action" id="exportForm" method="post">
	<input type="hidden" id="columns" name="columns"/>
	<input type="hidden" id="filters" name="filters"/>
	<input type="hidden" id="labelId" name="labelId"/>
</form>
<div id="container">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" valign="top">
				<div class="pm_msglist" id="msglist">
					<div class="titlebg">合同信息</div>
					<div class="searchdivkf">
						<form id="form1" name="form1" method="post" action="">
						<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="65">合同名称： </td>
								<td width="105">
									<table width="100%" border="0" cellspacing="2" cellpadding="0">
										<tr>
											<td width="180">
												<search:input id="name" dataType="string" field="contract.code"  op="cn"/>
											</td>
						  				</tr>
									</table>
								</td>
								<td width="70" align="right">合同类别：</td>
								<td width="105">
									<search:choose id="name" dataType="string" field="contract.categoryId" op="eq" >
									<dd:select styleClass="data" name="contract.categoryId" key="contract.0002" />
									</search:choose>
								</td> 
								<td width="70" align="right">合同状态：</td>
								<td width="105">
									<search:choose id="name" dataType="com.wiiy.common.preferences.enums.ContractStatusEnum" field="contract.contractStatus" op="eq" >
									<enum:select styleClass="data" name="contract.contractStatus" checked="sessionScope.Park_type" type="com.wiiy.common.preferences.enums.ContractStatusEnum"/>
									</search:choose>
								</td>
								<td width="75" align="center"><label><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map"/></label></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
						</form>
					</div>
					<div style="overflow: auto;">
						<table id="list" class="jqGridList"><tr><td/></tr></table>
						<div id="pager"></div>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>
<map name="Map" id="Map">
	<area shape="rect" coords="2,1,51,20" href="javascript:void(0)" onclick="doSearch()"/>
	<area shape="rect" coords="55,1,74,20" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/contract/contract_search.jsp',550,160);" />
</map>
</body>
</html>
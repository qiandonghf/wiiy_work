<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page import="com.wiiy.engineering.activator.EngineeringActivator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<c:set value="${param.type }" var="paramType"></c:set>
<%
	String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String name = "客户";
if(pageContext.getAttribute("paramType") != null){
	name = "供应商";
}
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
		initList();
	});
	
	function customerName(cellvalue, options, rowObject){
		var id = rowObject["id"];
		var data = "";
 		data = "<a href=\"javascript:void(0)\" onclick=\"showView('"+cellvalue+"','"+id+"');\">"+cellvalue+"</a>";
		return data;
	}
	
	function showView(name,id){
		fbStart(name,'<%=basePath%>customer!view.action?id='+id,870,450);
	}
	
	function initList(){
		var height = getTabContentHeight()-135;
		var width = $(this).width();
		var type = "${param.type}".toUpperCase();
		if(type =='') type="CUSTOMER";
		$("#list").jqGrid({
	    	url:'<%=basePath%>customer!list.action?customerType='+type,
			colModel: [
				{label:'<%=name%>名称', name:'name', align:"center",width:220,formatter:customerName}, 
				{label:'简称', name:'shortName', align:"center"}, 
				{label:'编号', name:'code', align:"center"}, 
			    {label:'类别', name:'type.title', align:"center"}, 
			    {label:'管理', name:'manager', align:"center",width:80,sortable:false, resizable:false}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%-- var row = $(this).jqGrid('getRowData',id);
					if(row.userId==''){
						<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("pb_configAccount")){%>
						content += "<img src=\"core/common/images/users.png\" width=\"14\" height=\"14\" title=\"设置账号\" onclick=\"createUser('"+id+"');\"  /> ";
						<%}%>
					}else{
						<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("pb_updatePassword")){%>
						content += "<img src=\"core/common/images/users.png\" width=\"14\" height=\"14\" title=\"修改密码\" onclick=\"updateAccountPassword('"+row.userId+"');\"  /> ";
						<%}%>
					} --%>
					<%boolean add = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_supplier_add") || 
							EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_customer_add");
						boolean edit = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_supplier_edit") || 
							EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_customer_edit");
						boolean view = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_supplier_view") || 
							EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_customer_view");
						boolean delete = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_supplier_delete") || 
							EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_customer_delete");%>
					<%if(view){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> "; 
					<%}%>
					<%if(edit){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> "; 
					<%}%>
					<%if(delete){%>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
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
	function createUser(id){
		fbStart('设置账号','<%=basePath %>customer!configAccount.action?id='+id,300,95);
	}
	function updateAccountPassword(userId){
		fbStart('修改密码','<%=basePath %>customer!updatePassword.action?id='+userId,300,95);
	}
	function viewById(id){
		fbStart($($("#list").getRowData(id).name).text(),'<%=basePath %>customer!view.action?id='+id,700,393);
	}
	function viewInvestmentById(investmentById){
		fbStart('项目信息','<%=basePath %>investment!view.action?id='+investmentById,700,364);
	}
	function add(){
		var type = 'customer';
		if('${param.type}' == 'supplier') type = "supplier";
		fbStart('新建<%=name%>','<%=basePath %>web/customer/customer_add.jsp?type='+type,700,422);
	}
	function editById(id){
		fbStart('编辑<%=name%>','<%=basePath %>customer!edit.action?id='+id,700,450);
	}
	function reloadList(){
   		$("#list").trigger("reloadGrid");
	}
	function deleteById(id){
		if(confirm("确定要删吗")){
			$.post("<%=basePath%>customer!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
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
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		alert(ids);
		if(ids!='' && confirm("确定要删吗")){
			$.post("<%=basePath%>customer!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function exportDate(){
		var columns = "{";
		$.each($("#list").getGridParam("colModel"),function(){
			if(this.label && this.name!="manager" && !this.hidden){
				columns += "\"" + this.name + "\"" + ":" + "\"" + this.label + "\",";
			}
		});
		columns = deleteLastCharWhenMatching(columns,",");
		columns += "}";
		$("#columns").val(columns);
		showTip("正在导出，请耐心等候...",5000);
		$("#exportForm").submit();
	}
	
	function toImportCard() {
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(confirm("确定要导入名片夹（客户-企业档案）吗")){
			showTip("正在导入名片，请稍候...",100000);
			$.post('<%=basePath %>customer!importCard.action?ids='+ids,
				function(data){
					showTip(data.result.msg,2000);
				}
			);
		 }
	 }
</script>

</head>
<body>
<form action="<%=basePath%>customer!export.action" id="exportForm" method="post">
	<input type="hidden" id="columns" name="columns"/>
	<input type="hidden" id="filters" name="filters"/>
	<input type="hidden" id="labelId" name="labelId"/>
</form>
<div class="emailtop">
	<div class="leftemail">
		<ul>
		<%if(add){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="add();"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
		<%} %>
		<%if(delete){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
		<%} %>
		<%-- <%if(SynthesisActivator.getHttpSessionService().isInResourceMap("pb_customer_export")){ %>	
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="exportDate()"><span><img src="core/common/images/database_(start)_16x16.gif" width="16" height="16" /></span>导出</li>
		<%} %>
		<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("pb_customer_importCard")){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="toImportCard()"><span><img src="core/common/images/card.png"/></span>导入名片</li>
		<%} %> --%>
		</ul>
	</div>
</div>
<div id="container">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" valign="top">
				<div class="pm_msglist" id="msglist">
					<div class="titlebg"><%=name%>列表</div>
					<div class="searchdivkf">
						<form id="form1" name="form1" method="post" action="">
						<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="65"><%=name%>名称： </td>
								<td width="105">
									<table width="100%" border="0" cellspacing="2" cellpadding="0">
										<tr>
											<td width="180">
												<search:input dataType="string" field="customer.name" op="cn" inputClass="inputauto"/>
											</td>
						  				</tr>
									</table>
								</td>
								<td width="70" align="right">类别：</td>
								<td width="105">
									<search:choose id="customerType" dataType="com.wiiy.common.preferences.enums.CustomerTypeEnum" field="customer.type" op="eq" >
										<enum:select styleClass="data" name="customerType" checked="sessionScope.Park_type" type="com.wiiy.common.preferences.enums.CustomerTypeEnum"/>
									</search:choose>
								</td>
								<%-- <td width="70" align="right">孵化状态：</td>
								<td width="105">
									<search:choose id="routeId"  dataType="string" field="incubationInfoStatusRoute.id" op="eq" >
										<dd:select key="crm.0025" styleClass="data"/>
									</search:choose>
								</td> --%>
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
	<area shape="rect" coords="55,1,74,20" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/client/customer_search.jsp',550,400);" />
</map>
</body>
</html>
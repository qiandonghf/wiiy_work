<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
		var height = getTabContentHeight()-105;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>contractTemplate!list.action',
			colModel: [
				{label:'合同模板', name:'chief.title', align:"center",width:50,formatter:isChief}, 
				{label:'名称', name:'name', align:"center",width:300}, 
				{label:'路径', name:'newName', align:"center",hidden:true}, 
				{label:'类型', name:'type.title', align:"center",width:100}, 
			    {label:'最后修改时间', name:'modifyTime', align:"center",width:140, formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'管理', name:'manager', align:"center", width:70 ,resizable:false,sortable:false}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractTemplate_chief")){%>
					content += "<img src=\"core/common/images/set_sys_icon2.gif\" width=\"14\" height=\"14\" title=\"设置主要打印模板\" onclick=\"chief('"+id+"');\"  /> "; 
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractTemplate_download")){%>
					content += "<img src=\"core/common/images/down.png\" width=\"14\" height=\"14\" title=\"下载\" onclick=\"downloadById('"+id+"');\"  /> "; 
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractTemplate_edit")){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"重命名\" onclick=\"editById('"+id+"');\"  /> "; 
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractTemplate_delete")){%>
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
	

	function isChief(cellvalue, options, rowObject){
		if(cellvalue == "是"){
			return "<img src=\"core/common/images/rightgreen.png\" width=\"7\" height=\"7\"/> ";
		}else{
			//return "<img src=\"core/common/images/rightgray.png\" width=\"7\" height=\"7\"/> ";
			return "&nbsp;";
		}
		
	}
	
	
	function chief(id){
		if(confirm("确定要设置成主要模板吗")){
			$.post("<%=basePath%>contractTemplate!chief.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	
	function downloadById(id){
		var path = $("#list").getCell(id,4);
		var name = $("#list").getCell(id,3);
		alert(path+","+name);
		location.href="<%=BaseAction.rootLocation %>/core/resources/"+path+"?name="+name;
	}
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function editById(id){
		fbStart('重命名','<%=basePath %>contractTemplate!edit.action?id='+id,400,76);
	}
	function deleteById(id){
		if(confirm("确定要删吗")){
			$.post("<%=basePath%>contractTemplate!delete.action?id="+id,function(data){
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
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids!='' && confirm("确定要删吗")){
			$.post("<%=basePath%>contect!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
</script>

</head>
<body>
<div class="emailtop">
	<div class="leftemail">
		<ul>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractTemplate_add")){%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建合同模板','<%=basePath %>web/contract/contractTemplate_add.jsp',450,136);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
		<%} %>
		</ul>
	</div>
</div>
<div class="searchdiv">
	<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="70">名称： </td>
			<td width="150">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="150"><search:input dataType="string" field="name" op="cn" inputClass="inputauto"/></td>
					</tr>
				</table>
			</td>
			<td width="45" align="right">类型：</td>
			<td width="90"><search:choose dataType="com.wiiy.business.preferences.enums.ContractItemEnum" field="type" op="eq"><enum:select styleClass="data" name="type" type="com.wiiy.business.preferences.enums.ContractItemEnum"/></search:choose></td>
			<td width="75" align="center"><label><img src="core/common/images/search.gif" width="75" height="22" border="0" onclick="doSearch()" /></label></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
<div>
		<table id="list" class="jqGridList"><tr><td/></tr></table>
		<div id="pager"></div>
</div>
<map name="Map" id="Map">
	<area shape="rect" coords="2,0,52,20" href="javascript:void(0)" onclick="doSearch()"/>
	<area shape="rect" coords="55,0,74,20" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/client/contect_search.jsp',550,400);"/>
</map>
</body>
</html>
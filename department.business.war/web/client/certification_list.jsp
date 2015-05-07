<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
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
$(document).ready(function(){
	initTip();
	initList("list");
});
function initList(list){
	var height = getTabContentHeight()-105;
	var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
	$("#"+list).jqGrid({
    	url:'<%=basePath%>certification!list.action',
		colModel: [
			{label:'企业名称', name:'customer.name', align:"center"}, 
			{label:'认证名称', name:'name',align:"center"}, 
			{label:'认证类型', name:'type.dataValue',align:"center"}, 
			{label:'认证编号', name:'serialNo', align:"center"}, 
			{label:'认证日期',width:100, name:'certTime',formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}, align:"center"}, 
			{label:'认证机构', name:'agency', align:"center"}, 
			{label:'是否发布',width:60, name:'pub.title', align:"center", hidden:true},
		    {label:'管理', name:'manager',width:70, align:"center", sortable:false, resizable:false}
		    
		],
		height: height,
		width: width,
		shrinkToFit: false,
		gridComplete: function(){
			var ids = $(this).jqGrid('getDataIDs');
			for(var i = 0 ; i < ids.length; i++){
				var id = ids[i];
				var content = "";
				<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_certification_view")){%>
				content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
				<%}%>
				<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_certification_edit")){%>
				content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
				<%}%>
				<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_certification_delete")){%>
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

function reloadList(){
	$("#list").trigger("reloadGrid");
}
function viewById(id){
	fbStart('查看认证','<%=basePath%>certification!view.action?id='+id,500,236);
}
function editById(id){
	fbStart('编辑认证','<%=basePath%>certification!edit.action?id='+id,500,317);
}
function deleteById(id){
	if(confirm("您确定要删除")){
		$.post("<%=basePath%>certification!delete.action?id="+id,function(data){
			showTip(data.result.msg,1000);
			$("#list").trigger("reloadGrid");
		});
	}
}
function deleteByIds(){
	var selectedRowIds =   
	$("#list").jqGrid("getGridParam","selarrrow"); 
	if(confirm("您确定要删除")){
		$.post("<%=basePath%>certification!delete.action?ids="+selectedRowIds,function(data){
			showTip(data.result.msg,1000);
			$("#list").trigger("reloadGrid");
		});
	}
}

function doSearch(){
	search(getSearchFilters());
}
function search(filters){
	$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
}
</script>
</head>

<body >
<div class="emailtop">
	<div class="leftemail">
		<ul>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_certification_add")){%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建认证','<%=basePath %>web/client/certification_add.jsp',500,317);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_certification_delete")){%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteByIds()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
		<%} %>
		</ul>
	</div>
</div>
<div class="searchdivkf">
  <form id="form2" name="form2" method="post" action="">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="70">企业名称： </td>
        <td width="105"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
            	 <search:input  dataType="string" field="customer.name" op="cn" inputClass="inputauto" />	
			</td>
          </tr>
        </table>       
         <label></label></td>
        <td width="70" align="right">认证名称：</td>
        <td width="105"><search:input dataType="string" field="name" op="cn" inputClass="inputauto"/></td>
        <td width="70" align="right">认证类型：</td>
        <td width="105">
        	<search:choose dataType="string" field="typeId" op="eq">
      			<dd:select styleClass="data" key="business.0017"/>
      		</search:choose>
     	</td>
        <td width="75" align="center"><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map" /></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </form>
</div>
<map name="Map" id="Map">
  <area shape="rect" coords="2,1,51,21" href="javascript:void(0)" onclick="doSearch()"/>
  <area shape="rect" coords="56,1,76,21" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/client/certification_search.jsp',550,225);"/>
</map>
<div class="msglist" id="msglist">
  <table id="list" class="jqGridList"><tr><td/></tr></table>
  <div id="pager"></div>
</div>
</body>
</html>

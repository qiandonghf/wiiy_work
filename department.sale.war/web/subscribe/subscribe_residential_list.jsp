<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
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
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		initList();
	});
	function initList(){
		<%
		Map<String, ResourceDto> resourceMap = SaleActivator.getHttpSessionService().getResourceMap();
		boolean add = SaleActivator.getHttpSessionService().isInResourceMap("registration_add");
		boolean edit = SaleActivator.getHttpSessionService().isInResourceMap("registration_edit");
		boolean del = SaleActivator.getHttpSessionService().isInResourceMap("registration_delete");
		boolean view = SaleActivator.getHttpSessionService().isInResourceMap("registration_view");
		boolean status = SaleActivator.getHttpSessionService().isInResourceMap("registration_changeState");
		%>
		<%if(add || del){ %>
			var height = getTabContentHeight()-105;
		<%}else{%>
			var height = getTabContentHeight() - 79;
		<%}%>
		var width = $(this).width()-2;
		$("#list").jqGrid({
	    	url:'<%=basePath%>residential!list.action?type=${param.type}',
	    	colModel: [
   				{label:'房间名称',width:30,name:'roomName',align:"center"}, 
   				{label:'客户名称',width:40,name:'name',align:"center", sortable:false}, 
   				{label:'销售顾问',width:40,name:'user.realName',align:"center", sortable:false}, 
   			    {label:'工作电话', name:'phone', align:"center",width:40},
   				{label:'移动电话', name:'mobile', align:"center",width:50,hidden:true}, 
   				{label:'预定日期',width:30,name:'registratTime',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
   				{label:'终止日期',width:30,name:'endTime',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
			    {label:'预定类型', name:'subscribeType.title',width:40, align:"center"},
			    {label:'预定原因', name:'reson', align:"center", hidden:true},
			    {label:'单价', name:'saleUnit', align:"center", hidden:true},
			    {label:'面积', name:'saleArea', align:"center", hidden:true},
			    {label:'总价', name:'salePrice', align:"center", hidden:true},
			    {label:'是否同步到房间', name:'whetherRoom', align:"center", hidden:true},
			    {label:'状态', width:'30' ,name:'entityStatus.title',index:'entityStatus', align:"center",formatter:checkStatus}, 
			    {label:'出租/出售', width:'30' ,name:'rentStatus.title',index:'rentStatus', align:"center"},
			    {label:'管理',width:60, name:'manager', align:"center", sortable:false, resizable:false}
   			],
			height: height,
			width: width,
			multiselect: true,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					var stateTxt = $(this).getCell(id,'entityStatus.title');
					<%if(status){%>
					if(stateTxt == '正常')
						content += "<img src=\"core/common/images/back.png\" width=\"14\" height=\"14\" title=\"关闭\" onclick=\"changeState('"+id+"');\"  /> ";
					else
						content += "<img src=\"core/common/images/uploadicon.png\" width=\"14\" height=\"14\" title=\"正常\" onclick=\"changeState('"+id+"');\"  /> ";
				    <%} %>
					<%if(view){%>
						content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%} %>
					<%if(edit){%>
						content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%} %>
					<%if(del){%>
						content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
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
	function viewById(id){
		fbStart('查看','<%=basePath%>residential!view.action?id='+id,620,412);
	}	
	function editById(id){
		fbStart('编辑','<%=basePath%>residential!edit.action?id='+id,620,332);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function changeState(id){
		$.post("<%=basePath%>residential!changeState.action?id="+id,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		reloadList();
        	}
		});
	}
	
	function checkStatus(cellvalue,options,rowObject){
		if(cellvalue =='禁用'){
			return "关闭";
		}
		return cellvalue;
	}
	
	function deleteById(id){
		if(confirm("确定删除")){
			$.post("<%=basePath%>residential!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}
	}
	
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids==""){
			showTip('请先选择',2000);	
		}else if(confirm("确定要删除")){
			$.post("<%=basePath%>residential!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}
	}
	
	function refreshTree(){
		$("#list").trigger("reloadGrid");
	}
	
	function doSearch(){
		var filters = getSearchFilters();
		$("#filters").val(filters);
		search(filters);
		/* search(getSearchFilters()); */
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
		/* $("#startTime1").val("");
		$("#startTime2").val(""); */
		/* $("#customer").val(""); */
	}
	function addSubscribeTab(title,icon,url){
		parent.addTab(title,icon,url);
	}
	 
</script>
</head>
<body>
<!--emailtop-->
<%if(add || del){ %>
<div class="emailtop">
	<div class="leftemail">
		<ul>
			<%if(del){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
			<%} %>
		</ul>
	</div>
</div>
<%} %>
<div class="searchdivkf">
  <form id="form2" name="form2" method="post" action="">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="70">线索： </td>
        <td width="105"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <search:input  dataType="string" field="contectInfo.name" op="cn" inputClass="inputauto" />
            </td>
          </tr>
       	 </table>       
        </td>
         <td width="65" align="right">预订日期：</td>
         <td width="230">
	        <table width="100%" border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="registratTime" op="ge">
	            		<input id="startTime1" class="data inputauto" onclick="return showCalendar('startTime1')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime1');" />
	            </td>
	            <td>--</td>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="registratTime" op="le">
	            		<input id="startTime2" class="data inputauto" onclick="return showCalendar('startTime2')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime2');" />
	            </td>
	          </tr>
	        </table>
	    </td>
        <td width="75" align="center"><label><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map" /></label></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
</form>
</div>
<div id="container">
	<div class="msglist" id="msglist">
	  <table id="list" class="jqGridList"><tr><td/></tr></table>
	  <div id="pager"></div>
	</div>
</div>
<map name="Map" id="Map">
<area shape="rect" coords="3,2,53,22"  href="javascript:void(0)" onclick="doSearch();" />
<area shape="rect" coords="56,1,73,20" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/registration/registration_search.jsp',450,280);" />
</map></body>
</html>


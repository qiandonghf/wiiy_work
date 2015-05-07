<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/smartMenu.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
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
		jqGridResizeRegister("list");
	});
	
	function addPropertyTab(title,icon,url){
		parent.addTab(title,icon,url);
	}
	function initList(){
		var height = getTabContentHeight()-105;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>complaint!listByType.action?status=${param.status}',
			colModel: [
					{label:'状态', width:'100' ,name:'status.title', align:"center",hidden:true}, 
					{label:'投诉对象' ,name:'complaintObject', align:"center"}, 
					{label:'投诉时间',width:'80' , name:'complaintTime', align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
					{label:'投诉人',name:'name', align:"center"}, 
					{label:'处理状态',name:'status.title', align:"center"}, 
					{label:'投诉内容',name:'content', align:"center",hidden:true}, 
					{label:'联系电话',name:'tel', align:"center",hidden:true}, 
					{label:'受理人', name:'accepter', align:"center",hidden:true}, 
					{label:'处理结果', name:'result', align:"center",hidden:true}, 
				    {label:'管理', width:'100' ,name:'manager', align:"center",sortable:false, resizable:false}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_complaint_hagin_view")){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_complaint_hagin_edit")){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_complaint_hagin_handle")){%>
					content += "<img src=\"core/common/images/cl.png\" width=\"14\" height=\"14\" title=\"处理\" onclick=\"handle('"+id+"');\"  /> "; 
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_complaint_hagin_delete")){%>
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
	
	function viewById(id){
		fbStart('查看投诉单','<%=basePath %>complaint!view.action?id='+id,600,191);
	}
	
	function editById(id){
		fbStart('编辑投诉单','<%=basePath %>complaint!edit.action?id='+id,600,222);
	}
	
	function handIn(id){
		if(confirm("是否现在递交处理？")){
			$.post("<%=basePath%>complaint!hangIn.action?id="+id,function(data){
	        	if(data.result.success){
					showTip("递交成功",2000);
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	
	function handle(id){
		fbStart('处理投诉单','<%=basePath %>complaint!handle.action?id='+id,596,357);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function deleteById(id){
		if(confirm("确定要删吗")){
			$.post("<%=basePath%>complaint!delete.action?id="+id,function(data){
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
			$.post("<%=basePath%>complaint!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	
	function doSearch(){
		var filters = getSearchFilters();
		search(filters);
	}
	
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
		$("#startTime1").val("");
		$("#startTime2").val("");
	}
	
</script>

</head>

<body>
<!--emailtop-->
<div class="emailtop">
			<div class="leftemail">
				<ul>
				<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_complaint_add")){%>
					<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建投诉单','<%=basePath %>web/property/complaint_add.jsp?form=index',600,222);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
				<%} %>
				<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_complaint_hagin_delete")){%>
					<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected();"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
				<%} %>
				</ul>
			</div>
		</div>
<!--//emailtop-->
<!--search-->
<div class="searchdiv">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="65">投诉日期： </td>
       <td width="230">
	        <table width="100%" border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="complaintTime" op="ge">
	            		<input id="startTime1" class="data inputauto" onclick="return showCalendar('startTime1')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime1');" />
	            </td>
	            <td>--</td>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="complaintTime" op="le">
	            		<input id="startTime2" class="data inputauto" onclick="return showCalendar('startTime2')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime2');" />
	            </td>
	          </tr>
	        </table>
	    </td>
        <td width="85" align="center"><label>
          <input name="Submit" type="submit" class="searchbtn" value="" onclick="doSearch();" />
        </label></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
</div>
<!--//search-->
<!--container-->
<div class="msglist" id="msglist">
	<div id="container">
		<table id="list" class="jqGridList"><tr><td/></tr></table>
		<div id="pager"></div>
	</div>
</div>
<!--//container-->
</body>
</html>

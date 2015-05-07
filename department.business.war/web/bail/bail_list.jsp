<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
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
		var type = "${param.type}";
		var url = "<%=basePath%>bail!list.action";
		if(type != 'null'){
			url = "?type="+type;
		}
		var height = getTabContentHeight()-105;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#"+list).jqGrid({
	    	url:url,
			colModel: [
				{label:'ID', name:'id', align:"center",hidden:true}, 
				{label:'单位名称', name:'name', align:"center",width:180}, 
				{label:'面积', name:'area', width:100, align:"center"},
				{label:'退租时间', name:'time',width:140,formatter:'date',formatoptions:{srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, width:120, align:"center"}, 
				{label:'退租原因', name:'memo', width:100, align:"center",hidden:true},
				{label:'违约金', name:'money', width:100, align:"center"},
				{label:'是否同步到房间', name:'enterStatus', align:"center", hidden:true},
				{label:'退租/续租', name:'rentState.title',width:'100' ,index:'rentState', align:"center"},
				/* {label:'状态',name:'rentState.title', width:30, align:"center",formatter:checkStatus},  */
			    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:100}
			],
			shrinkToFit: false,
			height: height,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_bail_view")){ %>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_bail_edit")){ %>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_bail_delete")){ %>
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
		fbStart('查看','<%=basePath%>bail!view.action?id='+id,500,300);
	}
	function editById(id){
		fbStart('编辑','<%=basePath%>bail!edit.action?id='+id,500,320);
	}
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>bail!delete.action?id="+id,function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function deleteByIds(){
		var selectedRowIds =   
		$("#list").jqGrid("getGridParam","selarrrow"); 
		if(selectedRowIds==""){
			showTip('请先选择',2000);
		}else if(confirm("您确定要删除")){
			$.post("<%=basePath%>bail!delete.action?ids="+selectedRowIds,function(data){
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
		/* $("#startTime1").val("");
		$("#startTime2").val(""); */
	}
</script>

</head>

<body>

<div class="emailtop">
	<div class="leftemail">
		<ul>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_bail_add")){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''"onclick="fbStart('新建','<%=basePath %>web/bail/bail_add.jsp',500,320);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_bail_delete")){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteByIds()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
		<%} %>
		</ul>
	</div>
	<!--//leftemail-->
</div>
<div class="searchdivkf">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
     <tr>
        <td width="70">单位名称： </td>
        <td width="130"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<td>
            	 <search:input  dataType="string" field="bail.name" op="cn" inputClass="inputauto" />
          	</td>
          </tr>
        </table>       
         <label></label></td>
		<td width="50" align="right">日期：</td>
         <td width="230">
	        <table width="100%" border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="time" op="ge">
	            		<input id="time1" class="data inputauto" onclick="return showCalendar('time1')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('time1');" />
	            </td>
	            <td>--</td>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="time" op="le">
	            		<input id="time2" class="data inputauto" onclick="return showCalendar('time2')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('time2');" />
	            </td>
	          </tr>
	        </table>
	    </td>
	    <td width="70" align="right">租赁状态：</td>
		<td width="105">
			<search:choose id="level"  dataType="com.wiiy.business.preferences.enums.ContractRentStatusEnum" field="level" op="eq" >
				<enum:select styleClass="data" type="com.wiiy.business.preferences.enums.ContractRentStatusEnum" defaultValue="${param.type }"/>
			</search:choose>
		</td>
        <td width="75" align="center"><label><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map" /></label></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
</div>
<div id="container">
	<div class="msglist" id="msglist">
	  <table id="list" class="jqGridList"><tr><td/></tr></table>
	  <div id="pager"></div>
	</div>
</div>
<map name="Map" id="Map">
<area shape="rect" coords="3,2,53,22"  href="javascript:void(0)" onclick="doSearch();" />
<area shape="rect" coords="56,1,73,20" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>/web/bail/bail_search.jsp',450,185);" />
</map></body>
</html>
			    
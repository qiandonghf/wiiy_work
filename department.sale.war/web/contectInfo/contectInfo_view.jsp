<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		initRepeatedVisteList();
	});
	
	function reloadList(){
		$("#repeatedVisteList").trigger("reloadGrid");
	}
	function initRepeatedVisteList(){
		var width = $("#textname").width();
		$("#repeatedVisteList").jqGrid({
	    	url:'<%=basePath%>repeatedViste!list.action',
			colModel: [
				{label:'回访人员', name:'receiver.realName', align:"center"}, 
				{label:'回访时间', name:'startTime',width:140,formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}, width:120, align:"center"},
				{label:'管理', name:'manager', align:"center", sortable:false, resizable:false}       
			],
			height: 110,
			forceFit: true,
			width: width,
			rowNum: 0,
			postData:{filters:generateSearchFilters("contectInfoId","eq",'${result.value.id}',"long")},
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById("+id+");\"  /> ";
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById("+id+");\"  /> ";
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById("+id+");\"  /> ";
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		});
	}
	
	function contect(cellvalue,options,rowObject){
	   	if(cellvalue.length > 15){
			cellvalue = cellvalue.substring(0,14)+"......";
	   	}
	   	return cellvalue;
	}
	
	function viewById(id){
		fbStart('查看回访信息',"<%=basePath%>repeatedViste!view.action?id="+id,480,160);
	}
	function editById(id){
		fbStart('编辑回访信息',"<%=basePath%>repeatedViste!edit.action?id="+id,480,200);
	}
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>repeatedViste!delete.action?id="+id,function(data){
				showTip(data.result.msg,1000);
				$("#repeatedVisteList").trigger("reloadGrid");
			});
		}
	}
</script>
<style>
	table{
		table-layout:fixed;
	}
</style>
</head>
<body>
<div class="divlays" style="margin:0px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td valign="top">
			<div class="basediv" style="margin:0px;">
				<div class="titlebg">来电信息</div>
				<div class="divlays" style="margin:0px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td class="layertdleft100">客户名称：</td>
				       	<td class="layerright">${result.value.customer.name }</td>
				        <td class="layertdleft100">接待人员：</td>
						  <td class="layerright">
						     ${result.value.receiver.realName }
						  </td>
				      </tr>
				      <tr>
				        <td class="layertdleft100">交往类型：</td>
				        <td class="layerright">${result.value.type.dataValue }</td>
				      	<td class="layertdleft100">状态：</td>
						  	<td class="layerright">
						  		${result.value.infoStatus.title}
					      	</td>
				      </tr>
				      <tr>
				        <td class="layertdleft100">交往日期：</td>
				        <td class="layerright"><fmt:formatDate value="${result.value.time }" pattern="yyyy-MM-dd HH:mm"/></td>
				      	<td class="layertdleft100">最后联系时间: </td>
						  <td class="layerright">
							   <fmt:formatDate value="${result.value.returnTime }" pattern="yyyy-MM-dd"/>
						  </td>
				      </tr>
				      <tr>
				        <td class="layertdleft100">接待情况：</td>
				        <td class="layerright"><div style="height: 50px;overflow-x: hidden;overflow-y: auto;word-break:break-all; word-wrap:break-word;">${result.value.content }</div></td>
				      </tr>
				      <tr>
				        <td class="layertdleft100">备注：</td>
				        <td class="layerright"><div style="height:50px;overflow-x: hidden;overflow-y: auto;word-break:break-all; word-wrap:break-word;">${result.value.memo }</div></td>
				      </tr>
				      <tr>
				      	<td class="layertdleft100">回访提醒：</td>
				      	<td class="layerright">
				      		<fmt:formatDate value="${result.value.returnVisit }" pattern="yyyy-MM-dd"/>
				      	</td>
				      </tr>
				    </table>
				    </div>
					<div class="hackbox"></div>
				</div>
			<div class="apptab" id="tableid">
				<ul>
				<% int flag=-1;
				if(SaleActivator.getHttpSessionService().isInResourceMap("sale_repeatedViste_view")){flag++;%>
				 	<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">回访信息</li>
				<%} %>
				</ul>
			</div>
			<% int flag2=-1; 
			   if(SaleActivator.getHttpSessionService().isInResourceMap("sale_repeatedViste_view")){flag2++; %>
				<div class="basediv tabswitch" style="margin:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
					<div class="emailtop">
						<div class="leftemail">
							<ul>
								<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建回访信息','<%=basePath %>web/contectInfo/repeatedViste_add.jsp?infoId=${result.value.id }',480,200);"><span><img src="core/common/images/emailadd.gif" /></span>新建</li>
							</ul>
						</div>
					</div>
					<table id="repeatedVisteList" width="100%"><tr><td/></tr></table>
				</div>
			<%} %>
	       <div class="hackbox"></div>
	 	</td>
	  </tr>
    </table>
   </div>
<div style="height: 5px;"></div>
</body>
</html>

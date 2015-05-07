<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript">
 		var clicked = null;
		$(function(){
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='plan.audit']");
			var finished = $("input[name='plan.finished']");
			changeState(audit);
			changeState(finished);
		}
		
		function changeState(obj){
			var p = $(obj).parent();
			var c = $(p).find("span");
			var txt = $(c).text();
			if($(obj).val() == 'YES'){
				$(obj).attr("checked",true);
				txt = txt.replace("未","已");
				$(c).text(txt);
			}
		}
	</script>
	<style>
		.mainClass{
			table-layout:fixed;
		}
	</style>
</head>
<body>
<form action="<%=basePath %>plan!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="plan.projectId" value="${result.value.project.id }"/>
<input type="hidden" name="plan.id" value="${result.value.id }"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table class="mainClass" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="layertdleft100">编号：</td>
			<td class="layerright">${result.value.code }</td>
       		<td class="layertdleft100">日期：</td>
     		<td class="layerright"><fmt:formatDate value="${result.value.time }" pattern="yyyy-MM-dd" /></td>
		</tr>
       	<tr>
    		<td class="layertdleft100">计划完成进度：</td>
			<td class="layerright">${result.value.schedule }</td>
			<td class="layertdleft100">项目进度总数：</td>
			<td class="layerright">${result.value.project.schedules }</td>
       	</tr>
       	<tr>
    		<td class="layertdleft100">经手人：</td>
			<td class="layerright">${result.value.handling.realName }</td>
	      	<td class="layerright" colspan="2">
				<label><input name="plan.audit" disabled="disabled" type="checkbox" onclick="changeState(this);" value="${result.value.audit }" /><span>未审核</span></label>
				<label><input name="plan.finished" disabled="disabled" type="checkbox" onclick="changeState(this);" value="${result.value.finished }" /><span>未完成</span></label>
		   		<!-- <label><input name="project.published" type="checkbox" onclick="changeState(this);" value="NO" />公开标志</label> -->
	      	</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">备注信息：</td>
			<td class="layerright" colspan="3">
	    		<textarea class="inputauto" readonly="readonly" style="color:#666;resize:none;height:80px;border:0px;padding-left:0px;">${result.value.memo }</textarea>
			</td>
       	</tr>
  </table>
  </div>
<div class="hackbox"></div>
</div>
<div class="buttondiv" style="height:5px;">
</div>
</form>
</body>
</html>

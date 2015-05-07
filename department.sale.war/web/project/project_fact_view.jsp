<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript">
 		var clicked = null;
		$(function(){
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='fact.audit']");
			var finished = $("input[name='fact.finished']");
			changeState(audit);
			changeState(finished);
		}
		
		function changeState(obj){
			if($(obj).attr("checked")){
				$(obj).val("YES");
			}else{
				$(obj).val("NO");
			}
			var p = $(obj).parent();
			var c = $(p).find("span");
			var txt = $(c).text();
			if($(obj).val() == 'YES'){
				$(obj).attr("checked",true);
				txt = txt.replace("未","已");
				$(c).text(txt);
			}else{
				$(obj).removeAttr("checked");
				txt = txt.replace("已","未");
				$(c).text(txt);
			}
			if($(obj).attr("name") == 'project.finished'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未完成");
				else $(c).text("已完成");
			}else if($(obj).attr("name") == 'project.audit'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未审核");
				else $(c).text("已审核");
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
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table class="mainClass" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="layertdleft100">进度计划：</td>
			<td class="layerright">${result.value.plan.code }</td>
       		<td class="layertdleft100">计划完成日期：</td>
     		<td class="layerright"><fmt:formatDate value="${result.value.plan.time }" pattern="yyyy-MM-dd" /></td>
		</tr>
       	<tr>
    		<td class="layertdleft100">计划完成进度：</td>
			<td class="layerright">${result.value.plan.schedule }</td>
			<td class="layertdleft100">项目进度总数：</td>
			<td class="layerright">${result.value.project.schedules }</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">实际完成日期：</td>
     		<td class="layerright"><fmt:formatDate value="${result.value.time }" pattern="yyyy-MM-dd" /></td>
		    <td class="layertdleft100">实际完成进度：</td>
			<td class="layerright">${result.value.schedule }</td>
       	</tr>
       	<tr>
    		<td class="layertdleft100">经手人：</td>
			<td class="layerright">${result.value.handling.realName }</td>
	      	<td class="layerright" colspan="2">
				<label><input name="fact.audit" disabled="disabled" <c:if test="${result.value.audit eq 'YES'}">checked="checked"</c:if> type="checkbox" onclick="changeState(this);" value="NO" /><span>未审核</span></label>
				<label><input name="fact.finished" disabled="disabled" <c:if test="${result.value.finished eq 'YES'}">checked="checked"</c:if> type="checkbox" onclick="changeState(this);" value="NO" /><span>未完成</span></label>
		   		<!-- <label><input name="project.published" type="checkbox" onclick="changeState(this);" value="NO" />公开标志</label> -->
	      	</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">备注信息：</td>
			<td class="layerright" colspan="3">
	    		<textarea name="fact.memo" readonly="readonly" class="inputauto" style="resize:none;height:80px;border:0px;color:#666;padding-left:0px;">${result.value.memo }</textarea>
			</td>
       	</tr>
  </table>
  </div>
<div class="hackbox"></div>
</div>
<div class="buttondiv" style="height:5px;">
</div>
</body>
</html>

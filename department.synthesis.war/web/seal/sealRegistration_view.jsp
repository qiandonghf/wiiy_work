<%@page import="com.wiiy.synthesis.preferences.enums.SealApplyEnum"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>

	<script type="text/javascript">
		$(function(){
			if($("#attNames").val()!=""){
				var attNames = {};
				var attSizes = {};
				var attPaths = {};
				attNames = $("#attNames").val().split(",");
				attSizes = $("#attSizes").val().split(",");
				attPaths = $("#attPaths").val().split(",");
				$.each(attNames,function(i){
					$("#attList").append($("<li></li>").append("<label></label>").append("<a href='/core/resources/"+attPaths[i]+"?name="+attNames[i]+"'>"+attNames[i]+"</a>"));
				});
			}
		});
		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		} 
 		
		
		

	</script>
</head>
<body>
<input name="sealRegistration.id" type="hidden" value="${result.value.id }"/>
<input id="attNames" type="hidden" value="${attNames }"/>
<input id="attSizes" type="hidden" value="${attSizes }"/>
<input id="attPaths" type="hidden" value="${attPaths }"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	<tr>
		    <td class="layertdleft100">使用人：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="20%">${result.value.user.realName }</td>
					</tr>
				</table>
			</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">印章：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>${result.value.sealNames }</td>
					</tr>
				</table>
			</td>
        </tr>
        <tr>
			<td class="layertdleft100">使用日期：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100"><fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.time}"/></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
   		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		    <tr>
		    <td class="layertdleft100">附件：</td>
		    <td class="layerright">
		      <table border="0" cellspacing="0" cellpadding="0" style="white-space:nowrap;">
		      	<tr><td style="padding-left:5px;width:300px;">
		      		<div id="attList" style="overflow-x:hidden;overflow-y: auto; ">
			      	</div>
			      	</td>
			      </tr>
		      </table>
		     </td>
		    </tr>
   		</table>
   		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr>
		      <td class="layertdleft100">备注：</td>
		      <td class="layerright">
		        <div style="height: 75px;overflow-x: hidden;overflow-y: auto">${result.value.memo}</div>
		      </td>
	      	</tr>
      	</table>
  	</table>
  </div>
<div class="hackbox"></div>
</div>
</body>
</html>

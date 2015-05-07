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
		
		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		} 
		function download(path,name){
			location="/core/resources/"+path+"?name="+name;
		}
	</script>
</head>
<body>
<input type="hidden" name="filePath"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	<tr>
		    <td class="layertdleft100">姓名：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="20%">${result.value.name }</td>
					</tr>
				</table>
			</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">岗位名称：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>${result.value.position }</td>
					</tr>
				</table>
			</td>
        </tr>
        <tr>
			<td class="layertdleft100">合同类型：</td>
			<td class="layerright">${result.value.contractCharacter.title }</td>
		</tr>
        <tr>
			<td class="layertdleft100">签订日期：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100"><fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.signingDate}"/></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
        <tr>
			<td class="layertdleft100">开始日期：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100"><fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.startTime}"/></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
        <tr>
			<td class="layertdleft100">结束日期：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100"><fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.endTime}"/></td>
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
		      		<div id="attList" style="overflow-x:hidden;overflow-y: auto;">
		      		<c:forEach  items="${result.value.atts }" var="att" >
<%-- 		      			<a href="<%=uploadPath %>core/resource/${att.newName }">${att.name }</a>
 --%>		      			<a href="javascript:;" onclick="download('${att.newName}','${att.name }')">${att.name }</a> 
		      		</c:forEach>
			      	</div>
			      	</td>
			      </tr>
		      </table>
		     </td>
		    </tr>
   		</table>
  	</table>
  </div>
</div>
</body>
</html>
 
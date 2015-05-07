 <%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript"></script>
</head>

<body>
<%-- <input type="hidden" id="id" name="bail.id" value="${result.value.id }"/>
<input type="hidden" id="businessContractId" value="${param.id}" name="bail.businessContractId"/> --%>
<div class="basediv">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="layertdleft100">合同：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="350"><input id="businessContract" name="bail.businessContract" type="text" class="inputauto" readonly="readonly" /><input id=businessContractId name="bail.businessContractId" type="hidden"/></td>
							<td><img src="core/common/images/outdiv.gif" style="position:relative;left:-4px;" width="20" height="22" onclick="fbStart('房间选择','<%=BaseAction.rootLocation%>/common/room!select.action?roomIds='+$('#roomIds').val(),520,400);" style=" cursor:pointer;"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">单位名称：</td>
				<td class="layerright">
					${result.value.name }
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">违约金额：</td>
				<td class="layerright">
					<fmt:formatNumber value="${result.value.money}" pattern="#0.00"/> 
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">面积：</td>
				<td class="layerright">
					<fmt:formatNumber value="${result.value.area}" pattern="#0.00"/> 
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">退租时间：</td>
				<td class="layerright">
					<fmt:formatDate value="${result.value.time }" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">是否入住：</td>
				<td class="layerright">
					${result.value.enterStatus.title }
				</td>
			</tr> 
			<tr>
				<td class="layertdleft100">租赁状态：</td>
				<td class="layerright">
					${result.value.rentState.title }
				</td>
			</tr> 
			<tr>
			 <td class="layertdleft100">原因：</td>
                <td  class="layerright" colspan="3" style="padding-top:1px;">
            	<div style="height:80px;margin-top:2px;" class="textareaauto">${result.value.memo}</div></td>
			</tr>
		</table>
	</div>
	<div class="hackbox"></div>
</div>
</body>
</html>
 
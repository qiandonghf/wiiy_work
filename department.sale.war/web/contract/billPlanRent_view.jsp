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
<script type="text/javascript">
$(function(){
	initChange();
});

function initChange(){
	var audit = $("input[name='billPlanRent.audit']");
	var p = $(audit).parent();
	var c = $(p).find("span");
	if($(audit).val() == 'NO'){
		$(c).text("未审核");
		$(audit).attr("checked",false);
	}
	else{
		$(c).text("已审核");
		$(audit).attr("checked","checked");
	} 
}
</script>
</head>

<body>
<div class="basediv">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="layertdleft100">编号：</td>
				<td class="layerright">${result.value.code }</td>
			</tr>
			<tr>
				<td class="layertdleft100">计划金额：</td>
				<td class="layerright">
					<fmt:formatNumber value="${result.value.planFee }" pattern="0.00" />&nbsp; 元 本期设置优惠之前的金额
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">实际金额：</td>
				<td class="layerright">
					<fmt:formatNumber value="${result.value.realFee }" pattern="0.00" />&nbsp; 元 本期实际需要支付的金额
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">计费日期：</td>
				<td class="layerright">
					<fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.startDate}"/>&nbsp;-&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.endDate}"/>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">计划付费日期：</td>
				<td class="layerright"><fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.planPayDate}"/>&nbsp;&nbsp;<label><input name="billPlanRent.audit" disabled="disabled" type="checkbox" <c:if test="${result.value.audit eq 'YES' }">checked="checked"</c:if> onclick="changeState(this);" /><span>未审核</span></label></td>
			</tr>
			<tr>
				<td class="layertdleft100">状态：</td>
				<td class="layerright">${result.value.status.title }</td>
			</tr>
			<tr>
				<td class="layertdleft100">评审状态：</td>
				<td class="layerright">${result.value.approvalStatus.title }</td>
			</tr>
			<tr>
				<td class="layertdleft100">备注：</td>
				<td class="layerright"><textarea name="billPlanRent.memo" class="inputauto" readonly="readonly" style="border:0px;color:#666;padding-left:0px;resize:none;height:50px;">${result.value.memo }</textarea></td>
			</tr>
		</table>
	</div>
	<div class="hackbox"></div>
</div>
</body>
</html>

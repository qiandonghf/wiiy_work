<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript">
	</script>
</head>

<body>
	<div class="basediv">
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100">合同名称：</td>
					<td class="layerright">${result.value.contract.name}</td>
				</tr>
				<tr>
					<td class="layertdleft100">企业名称：</td>
					<td class="layerright">${result.value.customer.name}</td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>保证金类型：</td>
					<td class="layerright">${result.value.type.title}</td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>金额：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100"><fmt:formatNumber value="${result.value.amount}" pattern="#0.00"/></td>
								<td>&nbsp; 元 </td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">备注：</td>
					<td class="layerright"><textarea class="inputauto" readonly="readonly" style="color:#666;height:100px;resize:none;padding-left:0px;border:0px;">${result.value.memo}</textarea></td>
				</tr>
			</table>
		</div>
		<div class="hackbox"></div>
	</div>
	<div style="height: 5px;"></div>
</body>
</html>

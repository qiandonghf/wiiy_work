<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	
	<script type="text/javascript">
		$(function(){
			initTip();
		});
		function iframeSwitch(url,li){
			$(li).removeClass().addClass("apptabliover");
			$(li).siblings().removeClass().addClass("apptabli");
			$("#iframe").attr("src",url);
		}	
		function reloadList(){
			frames[0].reloadList();
		}
	</script>
</head>
<body>
<div class="basediv">
	<div class="divlays" style="margin:0px;padding-bottom:0px;background-color: #eeeeee;">
		<div class="apptab" id="tableid">
			<ul>
				<li class="apptabliover" onclick="iframeSwitch('<%=basePath %>contract!view.action?id=${id}',this)">基本信息</li>
				<li class="apptabli" onclick="iframeSwitch('<%=basePath %>web/contract/contractAtt.jsp?id=${id }',this)">合同文件</li>
				<li class="apptabli" onclick="iframeSwitch('<%=basePath %>web/contract/billPlanRent.jsp?id=${id }',this)">结算计划</li>
				<li class="apptabli" onclick="iframeSwitch('<%=basePath %>web/contract/billRent.jsp?id=${id }',this)">结算流程</a></li>
			</ul>
		</div>
	</div>
	<iframe id="iframe" scrolling="no" frameborder="0" src="<%=basePath %>contract!view.action?id=${id}" name="sale" width="100%" height="403"></iframe>
</div>
</body>
</html>

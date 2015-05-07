<%@page import="com.wiiy.pf.dto.ContactBaseDto"%>
<%@page import="com.wiiy.pf.entity.Leave"%>
<%@page import="com.wiiy.core.entity.DataDict"%>
<%@page import="com.wiiy.pf.dto.ProcessDto"%>
<%@page import="com.wiiy.pf.entity.ProcessType"%>
<%@ page import="com.wiiy.pf.preferences.enums.LeaveTypeEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
<link href="/activiti/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
 	var currentTaskId = "";
 	var right = true;
	$(document).ready(function() {
		initTip();
		$("#msglist").css("height",$(this).height()-10);
		openRight();
	});
	
	function openRight(){
		var pid = "${param.pid}";
		var id = "${param.id}";
		var src = "<%=basePath%>contact!myView.action?plan=1&pid="+pid+"&id="+id;
		$("#msglist").attr("src",src);
	}
	
	
</script>
</head>
<body style="background-color: #eeeeee;">
<!--container-->
<div id="container">
	<div class="" style="padding:4px;">
		<iframe src="" frameborder="0" style="border: 1px solid #c3c3c3;height:100%;" id="msglist" width="100%" height="100%" name="app"></iframe>
	</div>
</div>
<!--//container-->
</body>
</html>

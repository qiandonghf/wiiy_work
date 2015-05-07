<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="stylesheet" type="text/css" href="/activiti/css/style.css" />
<link rel="stylesheet" href="/activiti/css/blueprint/screen.css" type="text/css" media="screen, projection" />
<link rel="stylesheet" href="/activiti/css/blueprint/print.css" type="text/css" media="print" /> 
<link href="/activiti/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script src="/activiti/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
<script src="/activiti/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
<script type="text/javascript" src="parkmanager.pf/js/module/activiti/workflow.js"></script>
<script type="text/javascript">
	var id = "${param.id}";
	var definitionId = "${param.definitionId}";
	$(document).ready(function() {
		initTip();
		showTip("正在加载图片,请稍后……",999999);
		var options = {'pid':id,'pdid':definitionId};
		graphTrace(options);
	});
</script>
<style>
	#workflowTraceDialog{
		display:none;
	}
</style>
</head>
<body style="height:450px;">
<div id="handleTemplate" class="template"></div>
<script type="text/javascript">
	function initHeight(id){
		obj = $("#"+id);
		$(obj).css("position","relative");
		var htmlH = $(this).height();
		var objH = (htmlH - $(obj).height()) /2;
		if(objH > 0){
			$(obj).css("top",Math.round(objH));
		}
		$(obj).css("left",'10px');
		$(obj).css("display",'block');
	}
	function loop(){
		htmlValue = $("#workflowTraceDialog").height();
		if(htmlValue == null){
			setTimeout("loop()",500);
		}else{
			showTip("图片加载成功",2000);
			setTimeout("initHeight('workflowTraceDialog')",800);
		}
	}
	loop();
</script>
</body>
</html>
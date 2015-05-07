<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = BaseAction.rootLocation + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>园区动力-物业管理</title>
<link href="core/common/style/index.css" rel="stylesheet"
	type="text/css" />
<link href="core/web/newdesktop/style/newindex.css" rel="stylesheet"
	type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet"
	type="text/css" />
<link href="core/common/style/tab.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet"
	type="text/css" />

<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/menu.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tab.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>



<script type="text/javascript">
	$(function(){
		initProperty();
		initComplaint();
	});

	function addPropertyTab(title,icon,url){
		parent.addTab(title,icon,url);
	}
	/*处理中的报修*/
	function initProperty(){
		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.estate/propertyFix!amounts.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i=0;i<data.result.value.length;i++){
				html = "<a href=\"javascript:;\" onclick=\"parent.addTab('所有报修','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.estate\/web\/property\/property_repair_listByType.jsp?status='+'"+nums[i].typeName+"');\" class=\" huise\">处理中的报修(<font class=\"huise\" style=\"color: red;line-height:25px;\">"+nums.length+"</font>)</a>"
			   }
			   if(data.result.value.length==0){
				html = "<a href=\"javascript:;\" onclick=\"parent.addTab('所有报修','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.estate\/web\/property\/property_repair_listByType.jsp?status='+'');\" class=\" huise\">处理中的报修</a>"
			   }
			   $("#propertyType").append(html);
		   }
		});
	}
	function initComplaint(){
		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.estate/complaint!amounts.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i=0;i<data.result.value.length;i++){
				html = "<a href=\"javascript:;\" onclick=\"parent.addTab('所有投诉','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.estate\/web\/property\/complaint_listByType.jsp?status='+'"+nums[i].typeName+"');\" class=\" huise\">处理中的投诉(<font class=\"huise\" style=\"color: red;line-height:25px;\">"+nums.length+"</font>)</a>"
			   }
			   if(data.result.value.length==0){
				html = "<a href=\"javascript:;\" onclick=\"parent.addTab('所有投诉','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.estate\/web\/property\/complaint_listByType.jsp?status='+'');\" class=\" huise\">处理中的投诉</a>"
			   }
			   $("#complaintType").append(html);
		   }
		});
	}
</script>
</head>
<body>
<ul>
	<li class="main2">
		<p class="text">
			<strong>
				<a href="javascript:;" class="blue" style="text-decoration:none">合同模板</a>
			</strong>
			<span>
				<a href="javascript:;" class="underline huise">物业合同</a>
			</span>
		</p>
	</li>
	<li>
		<p class="text">
			<strong>
				<a href="javascript:;" class="blue" style="text-decoration:none">合同登记</a></strong> 
				<span>
					<a href="javascript:;" class="underline huise">+物业合同</a> 
					<a href="javascript:;" class="huise">新招商客户 (<font>N</font>)</a>
				</span>
		</p>
		<p class="text">
			<strong><a href="javascript:;" class="blue" style="text-decoration:none">提醒</a></strong> <span><a
					href="javascript:;" class="underline  huise">合同到期(<font>N</font>)
				</a> （默认为30天） <a href="javascript:;" class="huise underline ">到期房源(<font>N</font>)
				</a></span>
		</p>
	</li>
</ul>
</body>
</html>

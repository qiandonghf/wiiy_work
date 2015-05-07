<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.ps.extensions.EnterpriseServiceExtensions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="parkmanager.ps/web/style/user.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript">
	function openService(plugin,icon,url){
		if (parent.$('#tt').tabs('exists',plugin)){
			parent.$('#tt').tabs('select', plugin);
		} else {
			parent.$('#tt').tabs('add',{
				title:plugin,
				iconCls:'icon-reload',
				content: '<iframe src="'+url+'" frameborder="0" height="'+(parent.document.documentElement.clientHeight-147)+'" width="100%"></iframe>',
				closable:true
			});
		}
		var dotIndex = icon.lastIndexOf(".");
		icon = icon.substring(0,dotIndex)+"16"+icon.substring(dotIndex);
		addTabIcon(plugin,icon);
	}
	function addTabIcon(title,icon){
		var span = parent.$('#tt').find("span:contains('"+title+"')");
		span.next().css("background","url('"+icon+"') no-repeat");
	}
	$(function(){
		$(".dataUrl").each(function(){
			var _this = this;
			$.post($(this).val(),function(data){
				if(data!='' && data.result!='' && data.result.success!='' && data.result.success){
					$(_this).prev().html(data.result.value);
				}
			});
		});
	});
</script>
</head>
<body>
<div class="service">
	<c:forEach items="${serviceDtoList}" var="service">
		<div class="servicetips" <c:if test="${service.runAs eq 'tab'}">onclick="openService('${service.name}','${service.icon}','<%=BaseAction.rootLocation%>/${service.url}')"</c:if><c:if test="${service.runAs eq 'floatbox'}">onclick="fbStart('加载中...','<%=BaseAction.rootLocation%>/${service.url}',${service.floatboxWidth},${service.floatboxHeight})"</c:if>>
			<img src="${service.icon}" width="50" height="50" />
			<ul>
				<li class="title">${service.name}<span style="margin-left: 5px;color: red;"></span><input type="hidden" class="dataUrl" value="<%=BaseAction.rootLocation%>/${service.dataUrl}"/></li>
				<li>${service.description}</li>
			</ul>
		</div>
	</c:forEach>
</div>
</body>
</html>

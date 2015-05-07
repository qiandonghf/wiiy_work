<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.core.entity.User"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.cms.activator.CmsActivator"%>
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
<script type="text/javascript">
	function openWeb(url){
		if(""!=trim(url)){
			window.open(url);
		}else{
			alert("未配置网站域名");
		}
	}
</script>
</head>
<body>
<div class="mainleft">
<div class="mainnewslist">
	<h3 class="title bg0801">网站管理</h3>
	<p class="toptext">
		这里是网站管理的一段文字介绍，文字介绍稍后提</p>
	<ul>
		<li class="main2">
			<p class="text">
				<strong><a href="javascript:void(0);" class="blue">网站管理</a></strong> 
				<span class="w300th">
					<a href="javascript:void(0);" onclick="fbStart('新建网站','<%=BaseAction.rootLocation %>/parkmanager.cms/param!add.action?form=index',670,573);" class="huise">+网站</a>
					<span style="clear:both;"></span>
					<span id="paramList">
					    <c:forEach items="${result.value }" var="paramList">
					    	<a href="javascript:void(0);" onclick="openWeb('${paramList.domainName}')" class="huise">${paramList.name }</a>
					    </c:forEach>
					</span>
				</span>
			</p>
			<p class="text">
				<strong><a href="javascript:void(0);" class="blue">栏目管理</a></strong> <span>
					<a href="javascript:void(0);" onclick="parent.addTab('栏目管理','/parkmanager.cms/web/images/sys_04_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/articleType!webList.action');" class="underline huise">查看栏目</a>
				</span>
			</p>
			<p class="text">
				<strong><a href="javascript:void(0);" class="blue">文章管理</a></strong> 
				<span class="w300th">
					<a href="javascript:void(0);" onclick="parent.addTab('单页文章','/parkmanager.cms/web/images/sys_07_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/articleType!listSingle.action');" class="huise">单页文章</a>
					<span style="clear:both;"></span>
						<a href="javascript:void(0);" onclick="parent.addTab('列表文章','/parkmanager.cms/web/images/sys_07_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/articleType!listArticle.action');" class="huise">列表文章</a>
						<a href="javascript:void(0);" onclick="fbStart('新建文章','<%=BaseAction.rootLocation %>/parkmanager.cms/web/content/content_addByNormal.jsp?form=index',800,555);" class="huise">+文章</a>
					</span>
				</p>
				<p class="text">
					<strong><a href="javascript:void(0);" class="blue">专题管理</a></strong> 
					<span class="w300th">
					<a href="javascript:void(0);" onclick="fbStart('新建文章','<%=BaseAction.rootLocation %>/parkmanager.cms/web/content/content_addByTopic.jsp?form=index',600,233);" class="huise">+专题</a>
					</span>
				</p>
				<p class="text">
					<strong><a href="javascript:void(0);" class="blue">评论管理</a></strong> 
					<span class="w300th">
						<a href="javascript:void(0);" onclick="parent.addTab('所有评论','/parkmanager.cms/web/images/sys_05_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/conventional/feedback_main.jsp');" class="huise">所有评论</a>
					</span>
				</p>
				<p class="text">
					<strong><a href="javascript:void(0);" class="blue">网站资源</a></strong>
					<span class="w300th">
						<a href="javascript:void(0);" onclick="parent.addTab('所有资源','/parkmanager.cms/web/images/ico_wzzy_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/param!webListResource.action');" class="huise">所有资源</a>
						<a href="javascript:void(0);" onclick="fbStart('新建资源','<%=BaseAction.rootLocation %>/parkmanager.cms/resource!add.action?id=1&form=index',600,233);" class="huise">+资源</a>
					</span>
				</p>
				<p class="text">
					<strong><a href="javascript:void(0);" class="blue">浮窗管理</a></strong>
					<span class="w300th">
						<a href="javascript:void(0);" onclick="parent.addTab('所有浮窗','/parkmanager.cms/web/images/sys_01_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/param!webListFloatingWindow.action');" class="huise">所有浮窗</a>
						<a href="javascript:void(0);" onclick="fbStart('新建浮窗','<%=BaseAction.rootLocation %>/parkmanager.cms/floatingWindow!add.action?id=1&form=index',600,233);" class="huise">+浮窗</a>
					</span>
				</p>
				<p class="text">
					<strong><a href="javascript:void(0);" class="blue">回收站</a></strong>
					<a href="javascript:void(0);" onclick="parent.addTab('回收站','/parkmanager.cms/web/images/sys_06_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/article!webList.action');" class="huise">回收站</a>
				</p>
				<p class="text">
					<strong><a href="javascript:void(0);" class="blue">其他配置</a></strong>
					<span class="w300th">
						<a href="javascript:void(0);" onclick="parent.addTab('意见反馈','/parkmanager.cms/web/images/ico_yjfk_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/system/advicement.jsp');" class="huise">意见反馈</a>
						<a href="javascript:void(0);" onclick="parent.addTab('邮件订阅','/parkmanager.cms/web/images/sys_01_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/system/mail.jsp');" class="huise">邮件订阅</a>
					</span>
				</p>
			</li>
		</ul>
	</div>
</div>
</body>
</html>

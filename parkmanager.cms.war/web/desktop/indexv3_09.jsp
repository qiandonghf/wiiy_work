<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = BaseAction.rootLocation + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>园区动力-内容管理</title>
<link href="core/common/style/index.css" rel="stylesheet" type="text/css" />
<link href="core/web/newdesktop/style/newindex.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/tab.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/menu.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tab.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript">
	$(function(){
		initTip();
	});
	
	function doBackUp(){
		$.post("<%=basePath %>rss!doBackUp.action",function(data){
			showTip(data.result.msg,2000);
		});
	}
</script>
</head>
<body>
	<div id="contant2" style="overflow: auto;">
		<div id="news_contenter" class="main"
			style="padding-left: 30px; padding-top: 10px;">
			<div class="toptitle">
				<p>
					<strong>内容管理</strong><span class="subtitle">ParkCMS</span><br />
					<span class="toptitle_bg">这里是内容管理的一段文字介绍，文字介绍稍后提供</span>
				</p>
			</div>
			<div class="maincontent">
				<!----左侧内容开始------->
				<s:action name="param!desktop" namespace="/"  executeResult="true"></s:action>
				<!----左侧内容结束------->
				<!----右侧内容开始------->
				<div class="mainright">
					<div class="mainnewslist">
						<h3 class="title bg0803">文档管理</h3>
						<p class="toptext">
							这里是文档管理的一段文字介绍，文字介绍稍后提</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue">文档管理</a></strong> 
									<span class="w300th">
										<a href="javascript:void(0);" onclick="fbStart('新建文档','<%=BaseAction.rootLocation %>/parkmanager.cms/web/document/personaldocuments_add.jsp?form=index',500,220);" class="huise">+文档</a>
										<span style="clear:both;"></span>
										<a href="javascript:void(0);" onclick="parent.addTab('我的文档','/parkmanager.cms/web/images/oaico/doc_01_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/document/personaldocuments_list.jsp');" class="huise">我的文档</a>
										<a href="javascript:void(0);" onclick="parent.addTab('共享文档','/parkmanager.cms/web/images/oaico/doc_02_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/document/share_document_list.jsp');" class="huise">共享文档</a>
										<a href="javascript:void(0);" onclick="parent.addTab('企业文档','/parkmanager.cms/web/images/oaico/doc_03_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/document/public_document_list.jsp');" class="huise">企业文档</a>
									</span>
								</p>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue">文档检索</a></strong> <span><a
										href="javascript:void(0);" class="huise"></a></span>
								</p>
							</li>
						</ul>
					</div>
					<div class="mainnewslist">
						<h3 class="title bg0804">抓取管理</h3>
						<p class="toptext">
							这里是抓取管理的一段文字介绍，文字介绍稍后提</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue">抓取配置</a></strong> 
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('所有站点','/parkmanager.cms/web/images/oaico/ad_08_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/news/webInfo_list.jsp');" class="huise">所有站点</a>
										<a href="javascript:void(0);" class="huise">+站点</a>
										<a href="javascript:void(0);" onclick="fbStart('新建栏目','<%=BaseAction.rootLocation %>/parkmanager.cms/web/news/channel_add.jsp?form=index',400,75);" class="huise">+栏目</a>
									</span>
								</p>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue">文章管理</a></strong> 
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('所有抓取','/parkmanager.cms/web/images/oaico/ad_08_min.png','<%=BaseAction.rootLocation %>/parkmanager.cms/web/news/webContent_list.jsp');" class="huise">所有抓取</a>
										<a href="javascript:void(0);" onclick="doBackUp();" class="huise">立即抓取</a>
									</span>
								</p>
							</li>
						</ul>
					</div>
				</div>
				<!----右侧内容结束------->
			</div>
		</div>
	</div>
</body>
</html>

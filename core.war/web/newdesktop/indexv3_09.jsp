<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@page import="com.wiiy.core.activator.CoreActivator"%>
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
		initParamList();//网站
		initTip();
	});
	
	function initParamList(){
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/parkmanager.cms/param!list.action",
		   success	: function(data){
			   if(data.root!=null){
					genParamList(data.root);
			   }
		   }
		});
	}
	
	function genParamList(paramlist){
		var html = "";
		if(paramlist!=null && paramlist.length>0){
			 for(var i = 0; i< paramlist.length; i++){
				 
				html += "<a href=\"javascript:;\" onclick=\"fbStart('查看网站','<%=BaseAction.rootLocation %>\/parkmanager.cms\/param!view.action?id=";
				html += paramlist[i].id;
				html += "',670,450)\";";			
				html += "class=\"huise\">";
				html += paramlist[0].name;
				html += "</a>";
			}	 
		}
		$("#ParamList").html(html);
	}

	function doBackUp(){
		$.post("<%=BaseAction.rootLocation%>/parkmanager.cms/rss!doBackUp.action",function(data){
			alert(data.result.msg);
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
					<span class="toptitle_bg">园区内容为园区提供了一套完整的园区外网网站和内网网站解决方案。以“创业者”与“投资者”角度引领园区网站和园区内容建设，从视觉、感性及文化上打造园区品牌与园区形象。</span>
				</p>
			</div>
			<div class="maincontent">
				<!----左侧内容开始------->
					<div class="mainleft">
						<div class="mainnewslist">
							<h3 class="title bg0801">网站管理</h3>
							<p class="toptext">
								园区的外网是园区的门户网站，是园区对外宣传的主要窗口。网站管理实现外网基本元素的全方位管理。</p>
							<ul>
								<li class="main2">
									<p class="text">
										<strong><a href="javascript:void(0);" class="blue">网站管理</a></strong> 
										<span class="w300th">
											<a href="javascript:void(0);" onclick="parent.addTab('所有网站','/parkmanager.cms/web/images/ico_wzsz_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/web/system/param.jsp');" class="huise">所有网站</a>
											<a href="javascript:void(0);" onclick="fbStart('新建网站','<%=BaseAction.rootLocation %>/parkmanager.cms/param!add.action?form=index',670,573);" class="huise">+网站</a>
											<span style="clear:both;"></span>
											<span id="ParamList">
											</span>
										</span>
									</p>
									<p class="text">
										<strong><a href="javascript:void(0);" class="blue">栏目管理</a></strong> 
										<span  class="w300th">
											<a href="javascript:void(0);" onclick="parent.addTab('查看栏目','/parkmanager.cms/web/images/sys_04_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/articleType!webList.action');" class="huise">栏目设置</a>
										</span>
									</p>
									<p class="text">
										<strong><a href="javascript:void(0);" class="blue">文章管理</a></strong> 
										<span class="w300th">
											<a href="javascript:void(0);" onclick="parent.addTab('单页文章','/parkmanager.cms/web/images/sys_07_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/articleType!listSingle.action');" class="huise">单页文章</a>
											<span style="clear:both;"></span>
											<a href="javascript:void(0);" onclick="parent.addTab('列表文章','/parkmanager.cms/web/images/sys_07_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/articleType!listArticle.action');" class="huise">列表文章</a>
											<a href="javascript:void(0);" onclick="fbStart('新建文章','<%=BaseAction.rootLocation %>/parkmanager.cms/web/content/content_addByNormal.jsp',800,555);" class="huise">+文章</a>
										</span>
										</p>
										<p class="text">
											<strong><a href="javascript:void(0);" class="blue">专题管理</a></strong> 
											<span class="w300th">
											<a href="javascript:void(0);" onclick="parent.addTab('所有专题','/parkmanager.cms/web/images/sys_08_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/articleType!listTopic.action');" class="huise">所有专题</a>
											<a href="javascript:void(0);" onclick="fbStart('新建专题','<%=BaseAction.rootLocation %>/parkmanager.cms/web/content/content_addByTopic.jsp',600,233);" class="huise">+专题</a>
											</span>
										</p>
										<p class="text">
											<strong><a href="javascript:void(0);" class="blue">评论管理</a></strong> 
											<span class="w300th">
												<a href="javascript:void(0);" onclick="parent.addTab('所有评论','/parkmanager.cms/web/images/sys_05_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/web/conventional/feedback_main.jsp');" class="huise">评论管理</a>
											</span>
										</p>
										<p class="text">
											<strong><a href="javascript:void(0);" class="blue">网站资源</a></strong>
											<span class="w300th">
												<a href="javascript:void(0);" onclick="parent.addTab('所有资源','/parkmanager.cms/web/images/ico_wzzy_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/param!webListResource.action');" class="huise">所有资源</a>
												<a href="javascript:void(0);" onclick="fbStart('添加资源','<%=BaseAction.rootLocation %>/parkmanager.cms/resource!add.action?id=1',650,258);" class="huise">+资源</a>
											</span>
										</p>
										<p class="text">
											<strong><a href="javascript:void(0);" class="blue">浮窗管理</a></strong>
											<span class="w300th">
												<a href="javascript:void(0);" onclick="parent.addTab('所有浮窗','/parkmanager.cms/web/images/sys_01_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/param!webListFloatingWindow.action');" class="huise">浮窗管理</a>
												<a href="javascript:void(0);" onclick="fbStart('添加浮窗','<%=BaseAction.rootLocation %>/parkmanager.cms/floatingWindow!add.action?id=1',650,258);" class="huise">+浮窗</a>
											</span>
										</p>
										<p class="text">
											<strong><a href="javascript:void(0);" class="blue">回收站</a></strong>
											<span class="w300th">
												<a href="javascript:void(0);" onclick="parent.addTab('回收站','/parkmanager.cms/web/images/sys_06_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/article!webList.action');" class="huise">回收站</a>
											</span>
										</p>
										<p class="text">
											<strong><a href="javascript:void(0);" class="blue">其他配置</a></strong>
											<span class="w300th">
												<a href="javascript:void(0);" onclick="parent.addTab('意见反馈','/parkmanager.cms/web/images/ico_yjfk_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/web/system/advicement.jsp');" class="huise">意见反馈</a>
												<a href="javascript:void(0);" onclick="parent.addTab('邮件订阅','/parkmanager.cms/web/images/sys_01_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/web/system/mail.jsp');" class="huise">邮件订阅</a>
											</span>
										</p>
									</li>
								</ul>
							</div>
						</div>
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
										<a href="javascript:void(0);" onclick="fbStart('添加文档','<%=BaseAction.rootLocation %>/parkmanager.cms/web/document/personaldocuments_add.jsp',500,220);" class="huise">+文档</a>
										<span style="clear:both;"></span>
										<a href="javascript:void(0);" onclick="parent.addTab('我的文档','/parkmanager.cms/web/images/ico_yjfk_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/web/document/personaldocuments_list.jsp');" class="huise">我的文档</a>
										<a href="javascript:void(0);" onclick="parent.addTab('共享文档','/parkmanager.cms/web/images/oaico/doc_02_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/web/document/share_document_list.jsp');" class="huise">共享文档</a>
										<a href="javascript:void(0);" onclick="parent.addTab('企业文档','/parkmanager.cms/web/images/oaico/doc_03_min.png','<%=BaseAction.rootLocation%>/parkmanager.cms/web/document/public_document_list.jsp');" class="huise">企业文档</a>
									</span>
								</p>
								<p class="text">
									
									<script type="text/javascript">
									function info(){
										alert("此版本不支持全文检索，如需查询文章，则选择文章列表中的查询功能");
									}
									</script>
									<strong><a href="javascript:void(0);" class="blue">文档检索</a></strong> 
									<span class="w300th">
										<a href="javascript:info();" class="huise">检索</a>
									</span>
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
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue">公告管理</a></strong> 
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('所有公告','/core/common/images/info_02_min.png','<%=BaseAction.rootLocation %>/department.synthesis/web/information/notice_list.jsp');" class="huise">所有公告</a>
										<a href="javascript:void(0);" onclick="fbStart('新建公告信息','<%=BaseAction.rootLocation %>/department.synthesis/web/information/notice_add.jsp',600,585);" class="huise">+公告</a>
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

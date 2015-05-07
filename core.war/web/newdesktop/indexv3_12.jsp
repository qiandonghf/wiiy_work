<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@page import="com.wiiy.core.activator.CoreActivator"%>
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
<title>系统设置</title>
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
	$(document).ready(function() {
		initTip();
	});

	function refreshDataTables(){
		parent.addTab('用户管理','/core/web/images/m_user_min.png','<%=BaseAction.rootLocation%>/core/user.action');
	}
	
	function reloadList(){
		parent.addTab('系统用户','/core/web/images/m_role_min.png','<%=BaseAction.rootLocation %>/core/web/base/corporation/corporation_list.jsp');
	}
</script>

</head>
<body>


	<div id="contant2" style="overflow: auto;">

		<div id="news_contenter" class="main"
			style="padding-left: 30px; padding-top: 10px;">
			<div class="toptitle">
				<p>
					<strong>系统设置</strong><span class="subtitle">SystemConfig</span><br />
					<span class="toptitle_bg">园区经理基础组件。</span>
				</p>
			</div>
			<div class="maincontent">
				<!----左侧内容开始------->
				<div class="mainleft">
					<div class="mainnewslist">
						<h3 class="title bg1001">基础管理</h3>
						<p class="toptext">科技园机构部门设置、管理人员与企业用户设置、权限配置、系统用户设置。</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="#" class="blue">机构设置</a></strong> 
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('机构设置','/core/web/images/m_setSystem_min.png','<%=BaseAction.rootLocation %>/core/org.action');" class="huise">机构管理</a>
									</span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">用户管理</a></strong>
									<span class="w300th">
									<a href="javascript:void(0);" onclick="parent.addTab('用户管理','/core/web/images/m_user_min.png','<%=BaseAction.rootLocation %>/core/user.action');" class="huise">用户管理</a>
										<a href="javascript:void(0);"  onclick="fbStart('新建用户','<%=BaseAction.rootLocation %>/core/user!create.action',590,539);" class="huise">+用户</a>

									</span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">权限管理</a></strong>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('角色管理','/core/web/images/m_role_min.png','<%=BaseAction.rootLocation %>/core/role.action');" class="huise">角色管理</a>
									</span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">系统用户</a></strong>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('系统用户','/core/web/images/m_role_min.png','<%=BaseAction.rootLocation %>/core/web/base/corporation/corporation_list.jsp');" class="huise">系统用户</a>
										<a href="javascript:void(0);"  onclick="fbStart('新建系统用户','<%=BaseAction.rootLocation %>/core/web/base/corporation/corporation_add.jsp',650,308);" class="huise">+系统用户</a>
									</span>
								</p>
							</li>
						</ul>
					</div>
					
					<div class="mainnewslist">
						<h3 class="title bg1002">流程管理</h3>
						<p class="toptext">园区经理采用最先进的工作流引擎activiti，包括了：流程定义，流程发布、流程角色管理、流程监控等重要功能</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="javascript:;" class="blue">流程管理 </a></strong>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('流程定义','/core/web/images/m_app_min.png','<%=BaseAction.rootLocation %>/parkmanager.pf/web/process/process.jsp');" class="huise">流程定义</a>
									</span>
								</p>
								<p class="text">
									<strong><a href="javascript:;" class="blue">流程角色</a></strong>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('角色管理','/core/web/images/m_app_min.png','<%=BaseAction.rootLocation %>/parkmanager.pf/workflowRole!list.action');" class="huise">角色管理</a>
									</span>
								</p>
							</li>
						</ul>
					</div>
					<div class="mainnewslist">
						<h3 class="title bg1002">应用管理</h3>
						<p class="toptext">园区经理采用了OSGI框架，可实时各应用组件的运行情况</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="javascript:;" class="blue">应用管理</a></strong>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('应用管理','/core/web/images/m_app_min.png','<%=BaseAction.rootLocation %>/core/appConsole.action');" class="huise">应用管理</a>
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
						<h3 class="title bg1003">系统管理</h3>
						<p class="toptext">系统运行基础功能管理。</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="#" class="blue">登录统计</a></strong> 
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('登录统计','/core/web/images/m_coding_min.png','<%=BaseAction.rootLocation %>/core/statistical!loginStatistical.action');" class="huise">登录统计</a>
										<a href="javascript:void(0);" onclick="parent.addTab('操作日志','/core/web/images/m_count_min.png','<%=BaseAction.rootLocation %>/core/statistical!opStatistical.action');" class="huise">操作日志</a>
									</span>

								</p>
								<p class="text">
									<strong><a href="#" class="blue">备份管理</a></strong>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('登录统计','/core/web/images/m_backup_min.png','<%=BaseAction.rootLocation %>/core/db!list.action');" class="huise">备份管理</a>
										<a href="javascript:void(0);" onclick="parent.addTab('登录统计','/core/common/images/backup.png','<%=BaseAction.rootLocation %>/core/db!doBackup.action');" class="huise">立即备份</a>
										<a href="javascript:void(0);" onclick="parent.addTab('数据清理','/core/common/images/emaildel.png','<%=BaseAction.rootLocation %>/core/db!delete.action');" class="huise">数据清理</a><span class="spanhuise">（默认清理超过30天备份）</span> 
									</span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">短信统计</a></strong>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('所有短信','/core/common/images/backup.png','<%=BaseAction.rootLocation %>/core/statistical!smsStatistical.action');" class="huise">所有短信</a>
									</span>
								</p>
								
							</li>
						</ul>
					</div>
					<div class="mainnewslist">
						<h3 class="title bg1004">数据自检</h3>
						<p class="toptext">园区经理提供系统数据与业务数据两类自检功能，保障系统的可靠性与可用性。</p>
						<ul>
							<li class="main2">
								<p class="text">
								<strong><a href="#" class="blue">系统数据自检</a></strong>
								<span class="w300th">
										<a href="javascript:void(0);" class="huise">sys20141201.html</a> 
										<a href="javascript:void(0);" class="huise" style="color: red">sys20141201.html</a> 
										<a href="javascript:void(0);" class="huise" style="color: red">sys20141201.html</a> 
								</span>
								</p>
								<p class="text">
								<strong><a href="#" class="blue">业务数据自检</a></strong>
								<span class="w300th">
										<a href="javascript:void(0);" class="huise">biz20141201.html</a> 
										<a href="javascript:void(0);" class="huise">biz20141201.html</a> 
										<a href="javascript:void(0);" class="huise">biz20141201.html</a> 
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

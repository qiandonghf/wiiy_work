<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.Format"%>
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
<title>园区动力-销售管理</title>
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
			initAppraisalType();
			initContractType();
		});
		function addRosterTab(title,icon,url){
			parent.addTab(title,icon,url);
		}
		/*合同*/
		function initContractType(){
			html="";
			$.ajax({
				type	:"POST",
				url		:"<%=BaseAction.rootLocation %>/department.synthesis/contract!amounts.action",
				success	:function(data){
					if(data.result.value!=0){
						var n=data.result.value;
						html+="("+n+")"
						$("#contractType").append(html);
					}else{
						$("#contractType").append(html);
					}
				}
			});
		}
		/*考核*/
		function initAppraisalType(){
			html="";
			$.ajax({
				type	:"POST",
				url		:"<%=BaseAction.rootLocation %>/department.synthesis/appraisal!amounts.action",
				success	:function(data){
					if(data.result.value!=0){
						var n=data.result.value;
						html+="("+n+")"
						$("#appraisalType").append(html);
					}else{
						$("#appraisalType").append(html);
					}
				}
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
					<strong>办公管理</strong><span class="subtitle">ParkCustomer</span><br />
					<span class="toptitle_bg">面向综合办公部，使得园区办公信息运转更为紧凑有效，园区办公流程运转更为合理规范。通过实现园区办公自动化，优化园区现有的管理组织结构，调整管理体制，在提高效率的基础上，增加园区协同办公能力，强化决策的一致性，从而实现园区提高决策效能的目的。</span>
				</p>
			</div>
			<div class="maincontent">
				<div class="mainleft">
					<div class="mainnewslist">
						<h3 class="title bg0701">日常工作</h3>
						<p class="toptext">
							合同备案</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="#" class="blue">合同备案</a></strong> <span><a
										href="#" class="underline huise">合同类型1</a> <a href="#"
										class="underline huise">合同类型2</a> <a href="#"
										class="underline huise">合同类型3</a> <a href="#"
										class="underline huise">全部合同类型</a> <a href="#"
										class="underline huise">全部合同类型</a> <a href="#"
										class="underline huise">全部合同类型</a> <a href="#"
										class="underline huise">全部合同类型</a></span>
								</p>
							</li>
						</ul>
					</div>
					<div class="mainnewslist">
						<h3 class="title bg0702">人事管理</h3>
						<p class="toptext">
							员工花名册、员工考核、劳动合同</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">员工花名册</a></strong> <span >
										<a href="javascript:void(0);"
										onclick="parent.addTab('花名册','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.synthesis/web/roster/roster.jsp');"
										class="huise">所有花名册</a> 
										<a
										href="javascript:void(0);"
										onclick="fbStart('新建花名册','<%=BaseAction.rootLocation%>/core/user!create.action?form=index',588,542);"
										class="huise">+花名册</a>
									</span>
								</p>
								<p class="text">
									<strong><a href="javascript:void(0);" style="text-decoration: none" class="blue">员工考核</a></strong> 
									<span class="w300th">
										<a href="javascript:;" onclick="parent.addTab('所有考核','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.synthesis/web/appraisal/appraisal_list.jsp');" class="huise">
										<%
												Calendar cal=Calendar.getInstance();
												cal.add(Calendar.YEAR, 0);
												Format format1=new SimpleDateFormat("yyyy");
												String yearly=format1.format(cal.getTime());
										%><%=yearly %>年度考核<font id="appraisalType" style="color:blue;line-height:25px;"></font></a>
									</span>
								</p>
								<p class="text">
									<strong><a href="javascript:void(0);" style="text-decoration: none" class="blue">劳动合同</a></strong>
									<span class="w300th">
									<a href="javascript:;" onclick="parent.addTab('劳动合同','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.synthesis/web/contract/contract_list.jsp');" class="huise">所有合同</a>
									<a href="javascript:;" onclick="parent.addTab('最近合同','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.synthesis/web/contract/contract_list.jsp?time=time');" class="huise">最近合同<font id="contractType" style="color:blue;line-height:25px;"></font></a>
									</span>
								</p>
							</li>
						</ul>
					</div>
					<div class="mainnewslist">
						<h3 class="title bg0703">行政管理</h3>
						<p class="toptext">
							办公用品管理、会议室</p>
						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="#" class="blue">办公用品管理</a></strong> <span><a
										href="#" class="huise on">+施工合同</a> <a href="#"
										class="huise on">+材料合同</a></span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">会议室</a>(<font>N</font>)</strong>
									<span><a href="#" class="huise on">预定</a></span>
								</p>
							</li>
						</ul>
					</div>

				</div>
				<div class="mainright">
					<div class="mainnewslist">
						<h3 class="title bg0704">信息材料</h3>
						<p class="toptext">
							公告、集团文件、规章制度、培训材料</p>

						<ul>
							<li class="main2">
								<p class="text">
									<strong><a href="#" class="blue">公告</a></strong> <span><a
										href="#" class="underline huise"></a> </span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">集团文件</a></strong> <span><a
										href="#" class="underline huise"></a> </span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">规章制度</a></strong> <span><a
										href="#" class="underline huise"></a> </span>
								</p>
								<p class="text">
									<strong><a href="#" class="blue">培训材料</a></strong> <span><a
										href="#" class="underline huise"></a> </span>
								</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

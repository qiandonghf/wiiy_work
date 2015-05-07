<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.synthesis.entity.Task"%>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.synthesis.entity.Task"%>
<%@page import="org.apache.taglibs.standard.tag.common.core.ForEachSupport"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
long userId = SynthesisActivator.getSessionUser(request).getId().longValue();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
<link href="core/common/style/base.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="core/common/style/smartMenu.css" rel="stylesheet" type="text/css" />
<link href="department.synthesis/web/style/task.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/righth.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript">
		 
	$(document).ready(function() {
		var h=window.document.documentElement.clientHeight;
		$(".taskcontent").css({height:h});
		$(".tasksidebar").css({height:h});
		//$(".contentDiv").width(window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft));
		$(".sendWorklist li").each(function(i){
			$(".sendWorklist li .arrow").eq(i).click(function(){
				if($(".sendchild").eq(i).css("display")=="block")
				{$(".sendchild").eq(i).css({display:"none"});}
				else{
				$(".sendWorklist li .arrow img").attr("src","department.synthesis/web/images/taskup.gif");
				$(".sendWorklist li .arrow img").eq(i).attr("src","department.synthesis/web/images/taskdown.gif");
				$(".sendchild").css({display:"none"});
				$(".sendchild").eq(i).css({display:"block"});
				}
			});
		});
		var sendH=h-$(".todowork").height()-90;
		$(".sendWorklist").css({height:sendH});
		loadPage('<%=basePath %>workReport!list.action');
		
		$("#tasktab").height(h);
		$(".tasktab li").each(function(k){
			$(".tasktab li").eq(k).click(function(){
				$(".tasktab li").removeClass("livisited").addClass("lilink");
				$(".tasktab li").eq(k).removeClass("lilink").addClass("livisited");
			});
		});
	    });
	
	function setSelectedUsers(users){
		window.frames[0].setSelectedUsers(users);
	}
	function setSelectedUser(user){
		window.frames[0].setSelectedUser(user);
	}
	function setSelectedDeparts(departs){
		window.frames[0].setSelectedDeparts(departs);
	}
	function setSelectedProject(project){
		window.frames[0].setSelectedProject(project);
	}
  	function setDepartReload(){
  		window.frames[0].setDepartReload();
  	}
  	function setProjectReload(){
  		window.frames[0].setProjectReload();
  	}
  	function setReload(){
  		window.frames[0].setReload();
  	}
  	function loadPage(url){
		//$("#workreportContainer").attr("style", "display:block");
		$("#workreportContainer").html('<iframe src="'+url+'" frameborder="0" height="'+(parent.parent.document.documentElement.clientHeight-120)+'" width="100%"></iframe>');
	}
  	
  	
  	<%
	Map<String, ResourceDto> resourceMap = SynthesisActivator.getHttpSessionService().getResourceMap();
	%>
  </script>
</head>
<body class="taskbg">
<div class="contentDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" class="taskcontent" >
    	<div id="workreportContainer"></div>
	</td>
    <td width="32" valign="top">
		<div class="tasktab" id="tasktab">
		<ul>
			
			<a href="javascript:loadPage('<%=basePath %>workReport!list.action')" style="text-decoration: none"><li class="livisited" >我<br/>的<br/>汇<br/>报<br/></li></a>
			
			<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_workReport_personal")){ %>
			<a href="javascript:loadPage('<%=basePath %>workReport!workCountList.action')" style="text-decoration: none"><li class="lilink">个<br/>人<br/>汇<br/>总<br/></li></a>
			<%} %>
			<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_workReport_department")){ %>
			<a href="javascript:loadPage('<%=basePath %>workReport!depWeekCountList.action')"  style="text-decoration: none"><li class="lilink">部<br/>门<br/>汇<br/>总<br/></li></a>
			<%} %>
			<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_workReport_employee")){ %>
			<a href="javascript:loadPage('<%=basePath %>workReport!employeeCountList.action')"  style="text-decoration: none"><li class="lilink">下<br/>属<br/>汇<br/>报<br/></li></a>
			<%} %>
		</ul>
		</div>
	</td>
  </tr>
</table>
</div>

</body>


</html>

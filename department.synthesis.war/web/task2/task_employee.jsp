<%@page import="com.wiiy.synthesis.service.TaskService"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.synthesis.entity.Task"%>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.synthesis.dto.TaskDto"%>
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
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="core/common/style/smartMenu.css" rel="stylesheet" type="text/css" />
<link href="department.synthesis/web/style/task.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/righth.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/FusionChartsFree/JSClass/FusionCharts.js"></script>
  <script type="text/javascript">
  $(document).ready(function() {
	initTip();
	var h=window.document.documentElement.clientHeight;
	$(".taskcontent").css({height:h});
	$(".tasksidebar").css({height:h});
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
	$(".tasklist").height()+88;
    });
  
  function slide(id,img){
	  	if($("#"+id).is(':hidden')){
			$(".slide").slideUp();
			$(".slideImg").attr("src","core/common/images/synthesis_arrow.png");
			$("#"+id).slideDown();
			$("#"+img).attr("src","core/common/images/synthesis_arrowbtn.png");
		}else{
			$("#"+id).slideUp();
			$("#"+img).attr("src","core/common/images/synthesis_arrow.png");
		}
	}	
  
  function deleteTaskProjectById(id){
		if(confirm("确定要删吗")){
			$.post("<%=basePath%>taskProject!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
					location.reload();	        		
	        	}
			});
		}
	}
  
  function setSelectedProject(project){
	  window.frames[0].setSelectedProject(project); 
  }
  
  function setSelectedDeparts(departs){
	  window.frames[0].setSelectedDeparts(departs); 
  }
  
  function setProjectReload(){
		location.reload();	
  }
  function setReload(){
		location.reload();
	}
  </script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="300" valign="top">
		<div style="OVERFLOW-Y: auto; OVERFLOW-X: auto; WIDTH: 100%; HEIGHT: 100%">
		<div class="tasksidebar">
			<!--tasktable-->
			<div class="" style="margin:10px; border-left:1px solid #ddd;">
				<table width="280" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tasktableth">员工</td>
					<td width="50" class="tasktableth">待办</td>
					<td width="50" class="tasktableth">完成</td>
					<td width="50" class="tasktableth">中止</td>
				</tr>
				<c:forEach items="${employeeWorkDtoList}" var="employeeDto">
				<tr>
					<td class="tasktabletd">
					<a href="<%=basePath%>task!viewEmployee.action?id=${employeeDto.id}" target="employeeWork"  style="text-decoration:none;color:#666;">${employeeDto.name}</a>
					</td>
					<td class="tasktabletd">${employeeDto.unfinish}</td>
					<td class="tasktabletd">${employeeDto.finish}</td>
					<td class="tasktabletdred">${employeeDto.stop}</td>
				</tr>
				</c:forEach>
			</table>
			</div>
			</div>
			<!--//tasktable-->
		</div></td>
		<td valign="top">
		
		<div class="tasklist">
			 <iframe scrolling="auto" src="<%=basePath %>task!viewEmployee.action" id="employeeWork" name="employeeWork" frameborder="0" width="100%" height="465px;"></iframe>
		</div></td>
	</tr>
</table>	
</body>


</html>

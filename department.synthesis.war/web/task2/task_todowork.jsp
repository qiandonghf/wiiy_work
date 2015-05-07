<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.synthesis.entity.Task"%>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.synthesis.entity.TaskLog"%>
<%@page import="com.wiiy.synthesis.dto.TaskDto"%>
<%@page import="org.apache.taglibs.standard.tag.common.core.ForEachSupport"%>
<%@page import="com.wiiy.synthesis.preferences.enums.SubTaskStatusEnum"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript">

	var createMenu = [[{
		text: "编辑",
		classname: "smarty_menu_ico0",
		func: function() {
			fbStart('编辑工作','<%=basePath%>task!edit.action?id='+$(this).find(".id").val(),650,500);
		}
	},{
		text: "查看",
		classname: "smarty_menu_view",
		func: function() {
			fbStart('查看工作','<%=basePath%>task!view.action?id='+$(this).find(".id").val(),650,400);
		}
	}],[{
		text: "删除",
		classname: "smarty_menu_ico2",
		func: function() {
			if(confirm("您确认要删除吗?")){
				$.post("<%=basePath%>task!delete.action?id="+$(this).find(".id").val(),function(data){
					if(data.result.success){
						location.reload();
					} else {
						showTip(data.result.msg,2000);
						setTimeout("location.reload();",4000);
					}
				});
			}
		}
	}],[{
		text: "派送",
		classname: "smarty_menu_appoint",
		func: function() {
			$("#taskId").val($(this).find(".id").val());
			$("#selectUser").val("send");
			var ids = "";
			var departIds = $(this).find(".receiverId");
			if(departIds.size()>0){
				ids = ",";
				for(var i = 0; i < departIds.size(); i++){
					ids += departIds[i].value+",";
				}
			}
			fbStart('工作派送','<%=BaseAction.rootLocation%>/core/user!select2.action?ids='+ids+'&id='+1,520,400);
		}
	},{
		text: "移交",
		classname: "smarty_menu_transfer",
		func: function() {
			$("#taskId").val($(this).find(".id").val());
			$("#selectUser").val("send");
			var ids = "";
			var departIds = $(this).find(".receiverId");
			if(departIds.size()>0){
				ids = ",";
				for(var i = 0; i < departIds.size(); i++){
					ids += departIds[i].value+",";
				}
			}
			fbStart('工作移交','<%=BaseAction.rootLocation%>/core/user!select.action?ids='+ids+'&id='+1,520,400);
		}
	}],[{
		text: "优先级",
		classname: "smarty_yu_ico0",
		data: [[{
			text: "高",
			classname: "smarty_yu_ico0",
			func: function() {
	            $.post("<%=basePath%>task!high.action?id="+$(this).find(".id").val(),function(){
					location.reload();
				});
			}
		},{
			text: "中",
			classname: "smarty_pu_ico0",
			func: function() {
				$.post("<%=basePath%>task!middle.action?id="+$(this).find(".id").val(),function(){
					location.reload();
				});
			}
		},{
			text: "低",
			classname: "smarty_di_ico0",
			func: function() {
				$.post("<%=basePath%>task!low.action?id="+$(this).find(".id").val(),function(){
					location.reload();
				});
			}
		}]]
	},{
		text: "设置进度",
		classname: "smarty_bai0_ico0",
		data: [[{
			text: "0%",
			classname: "smarty_bai0_ico0",
			func: function() {
				$.post("<%=basePath%>task!progress.action?id="+$(this).find(".id").val()+"&progress=0",function(){
					location.reload();
				});
			}
		}, {
			text: "25%",
			classname: "smarty_bai25_ico0",
			func: function() {
				$.post("<%=basePath%>task!progress.action?id="+$(this).find(".id").val()+"&progress=25",function(){
					location.reload();
				});
			}
		}, {
			text: "50%",
			classname: "smarty_bai50_ico0",
			func: function() {
				$.post("<%=basePath%>task!progress.action?id="+$(this).find(".id").val()+"&progress=50",function(){
					location.reload();
				});
			}
		},{
			text: "75%",
			classname: "smarty_bai75_ico0",
			func: function() {
				$.post("<%=basePath%>task!progress.action?id="+$(this).find(".id").val()+"&progress=75",function(){
					location.reload();
				});
			}
		},{
			text: "100%",
			classname: "smarty_bai100_ico0",
			func: function() {
				$.post("<%=basePath%>task!progress.action?id="+$(this).find(".id").val()+"&progress=100",function(){
					location.reload();
				});
			}
		}]]
	}],[{
		text: "中止工作",
		classname: "smarty_menu_stop",
		func: function() {
		 	if(confirm("您确认中止吗?")){
				$.post("<%=basePath%>task!aborted.action?id="+$(this).find(".id").val(),function(){
					location.reload();
				});
			}
		}
	},{
		text: "工作延期",
		classname: "smarty_menu_time",
		func: function() {
			fbStart('工作延期','<%=basePath%>web/task/task_edit_endTime.jsp?id='+$(this).find(".id").val(),420,178);
		}
	},{
		text: "设置完成",
		classname: "smarty_menu_finish",
		func: function() {
			if(confirm("您确认要设置完成吗?")){
				 $.post("<%=basePath%>task!finished.action?id="+$(this).find(".id").val(),function(){
					location.reload();
				});
		 	}
		 }
	}],[{
		text: "设置部门",
		classname: "smarty_menu_org",
		func: function() {
			$("#taskId").val($(this).find(".id").val());
			var ids = "";
			var departIds = $(this).find(".departId");
			if(departIds.size()>0){
				ids = ",";
				for(var i = 0; i < departIds.size(); i++){
					ids += departIds[i].value+",";
				}
			}
			fbStart('设置部门','<%=basePath%>taskDepart!select.action?ids='+ids,420,240);
		}
	},{
		text: "设置项目",
		classname: "smarty_menu_porject",
		func: function() {
			$("#taskId").val($(this).find(".id").val());
			fbStart('设置项目','<%=basePath%>taskProject!select.action?id='+$(this).find(".projectId").val(),420,240);
		}
	}]];
	
	 $(document).ready(function() {
		initTip();
		$(".liTask").smartMenu(createMenu,{name:'createMenu'});
		var h=window.parent.parent.document.documentElement.clientHeight;
		var h2 = h-50;
		$(".taskcontet").css({height:h2});
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
		var sendH=h-$("#todoWorkIframe").height()-200;
		$(".sendWorklist").css({height:sendH});
		var sendIframe = sendH+170;
		$("#sendWorIframe").css({height:sendIframe});
	});
	
	function setSelectedDeparts(departs){
		var ids = "";
		for(var i = 0; i < departs.length; i++){
			ids += departs[i].id+",";
		}
		ids = deleteLastCharWhenMatching(ids,",");
		$.post("<%=basePath%>task!setDepart.action?id="+$("#taskId").val()+"&ids="+ids,function(data){
			if(data.result.success){
				showTip("部门设置成功",2000);
				setTimeout("location.reload();",2000);
			}
		});
	}
	
	function setSelectedUsers(users){
	var ids = "";
	for(var i = 0; i < users.length; i++){
		ids += users[i].id+",";
	}
	ids = deleteLastCharWhenMatching(ids,",");
	$.post("<%=basePath%>task!taskSend.action?id="+$("#taskId").val()+"&ids="+ids,function(data){
		if(data.result.success){
			showTip("工作派送成功",2000);
			setTimeout("location.reload();window.parent.frames[1].location.reload();",2000);
		}
	});
	}
	
	function setSelectedUser(user){
		var ids = user.id;
		$.post("<%=basePath%>task!taskMove.action?id="+$("#taskId").val()+"&ids="+ids,function(data){
			if(data.result.success){
				showTip("工作移交成功",2000);
				setTimeout("location.reload();",2000);
			}
		});
		}
	
	function setSelectedProject(project){
	$.post("<%=basePath%>task!setProject.action?id="+$("#taskId").val()+"&projectId="+project.id,function(data){
		if(data.result.success){
			showTip("项目设置成功",2000);
			setTimeout("location.reload();",2000);
		}
	});
	}
	
	function singed(id){
	$.post("<%=basePath%>task!signed.action?id="+id,function(data){
		if(data.result.success){
			showTip("任务签收成功",2000);
			setTimeout("location.reload();",2000);
		}
	});
	}
	function setReload(){
  		location.reload();
  	}
  
	function viewTask(id){
		fbStart('查看工作','<%=basePath%>task!view.action?id='+id,650,400);
	}
	function jumpPage(page){
		var url = "<%=basePath%>task!mySendTaskList.action";
		url += "?page="+page;
		url = encodeURI(url);
		location.href=url;
	}
  </script>
</head>
<body>
<div class="taskcontet">
<input type="hidden" id="taskId"/>
	<ul>
		<c:forEach items="${taskWorkDtoList}" var="dto">
		<li class="liTask" onmouseover="$(this).addClass('trover')" onmouseout="$(this).removeClass('trover')" onclick="viewTask(${dto.task.id});">
		<input type="hidden" class="id" value="${dto.task.id}"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="15" align="center">
								<c:if test="${dto.task.priority eq 'HIGH'}">
									<img src="core/common/images/gthred.png" width="4" height="10" />
								</c:if>
								<c:if test="${dto.task.priority eq 'MIDDLE'}">
									<img src="core/common/images/gthyellow.png" width="4" height="10" />
								</c:if>
								<c:if test="${dto.task.priority eq 'LOW'}">
									<img src="core/common/images/gthblue.png" width="4" height="10" />
								</c:if>
							</td>
							<td>
								<c:if test="${fn:length(dto.task.atts)>0}"> 
									<td width="15" align="center"><img src="core/common/images/uploadfj.gif" width="7" height="12" /></td>
								</c:if>
								<c:if test="${fn:length(dto.task.atts)<=0}"> 
									<td width="15" align="center"></td>
								</c:if>
							</td>
							<td>${dto.task.title}
								<c:if test="${dto.name ne dto.task.creator}">
								<c:if test="${dto.signedDate!=null}">
									<span class="taskgray">
										<fmt:formatDate value="${dto.signedDate}" pattern="M"/>月<fmt:formatDate value="${dto.signedDate}" pattern="dd"/>日签收
									</span>
								</c:if>
								</c:if>
							</td>
							<td width="80"class="taskfnormal">
								<c:if test="${dto.day==1}">
									<td width="80" class="taskfnormal">明天到期</td>
								</c:if>
								<c:if test="${dto.day>1}">
									<td width="80" class="taskfnormal">${dto.day}天后到期</td>
								</c:if>
								<c:if test="${dto.day==0}">
									<td width="80" class="taskfnormal">今天到期</td>
								</c:if>
								<c:if test="${dto.day<0}">
									<td width="80" class="taskfred">已逾期${-dto.day}天</td>
								</c:if>
							</td>
							
						</tr>
					</table></td>
				</tr>
				<tr>
					<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="15" align="center">&nbsp;</td>
							<td width="15" align="center">&nbsp;</td>
							<td width="120" class="taskgray">
								${dto.taskDepart}
								<input type="hidden" class="departId" value="${dto.depIds}"/>
								<input type="hidden" class="projectId" value="${dto.task.projectId}"/>
							</td>
							<td class="taskgraypr">开始时间：<fmt:formatDate value="${dto.task.startTime}" pattern="M"/>月<fmt:formatDate value="${dto.task.startTime}" pattern="dd"/>日  完成情况：<img src="core/common/images/bai${dto.task.progress}_min.png" /></td>
						</tr>
					</table></td>
				</tr>
			</table>
		</li>
		</c:forEach>
	</ul>
</div>
</body>


</html>

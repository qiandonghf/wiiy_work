<%@page import="com.wiiy.pf.dto.ContactBaseDto"%>
<%@page import="com.wiiy.pf.entity.Leave"%>
<%@page import="com.wiiy.core.entity.DataDict"%>
<%@page import="com.wiiy.pf.dto.ProcessDto"%>
<%@page import="com.wiiy.pf.entity.ProcessType"%>
<%@ page import="com.wiiy.pf.preferences.enums.LeaveTypeEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/web/newdesktop/style/task.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
<link href="/activiti/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<!-- <script type="text/javascript" src="parkmanager.pf/js/module/activiti/leave-todo.js"></script>
<script src="/activiti/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<script src="/activiti/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
<script src="/activiti/js/common/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script> -->
<script type="text/javascript">
 	var currentTaskId = "";
 	var right = true;
	$(document).ready(function() {
		initTip();
		$("#contactdiv").css("height",$(this).height()-81);
		$("#msglist").css("height",$(this).height());
		$("#fee_lefts").css("width","510px");
		var topTr = $(".autoWidth");
		var btmTr = $(".autoWidth2");
		if($(btmTr).children().eq(4).width() <134){
			$(topTr).children().eq(5).removeAttr("width");
			$(topTr).children().eq(4).css("width",$(btmTr).children().eq(4).width());
		} 
		
		$(".handle").click(handle);
		$(".page").css({ "width": "280px", "margin-left": "135px" });
		
	});
	
	function highlight(el){
		$(el).addClass("highlight");
		$(el).siblings().removeClass("highlight").css("background","#fff");
	}
	function keepHighlight(el){
		if($(el).hasClass("highlight")){
			$(el).css("background","#f4f4f4");
		}
	}
	
	function companylabel(){
		var size = $(".shourul").size();
		if(size <= 0)
			return;
		if($("#company_label").is(":hidden") == true){
			$("#company_label").show();
		}else{
			$("#company_label").hide();
		}
	}
	
	function handle() {
		right = false;
		// 记录ID 
		var id = $(this).parents('tr').attr('id');
		// 当前点击的任务ID
		taskId = $(this).parents('tr').attr('tid');
		var pid = $(this).parents('tr').attr('pid');
		var key = $(this).parents('tr').attr('tpid');
		var formName= $(this).parents('tr').attr('formname');
		formName = formName.toLowerCase();
		var openUrl = '<%=basePath %>'+formName+'!handle.action?id='+id+'&taskId='+taskId+'&pid='+pid+'&processDefinitionKey='+key;//弹出窗口的url
		<%-- var openUrl = '<%=basePath %>contact!handle.action?type=leave&id='+id+'&taskId='+taskId;//弹出窗口的url --%>
		var iWidth=1024; //弹出窗口的宽度;
		var iHeight=500; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open(openUrl,'办理','top='+iTop+',left='+iLeft+',height='+iHeight+',width='+iWidth+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
	}
	
	function openRight(pid,id,obj){
		var key = $(obj).attr("tpid");
		if(right){
			var currentTime = new Date().getTime();
			var src = "<%=basePath%>contact!myView.action?processDefinitionKey="+key+"&pid="+pid+"&id="+id+"&currentTime="+currentTime;
			//$("#msglist").attr("src",src);
			fbStart('查看',src,700,500);
		}else
			right = true;
	}
	
	function claim(taskId,id){
		right = false;
		$.ajax({
			type: "POST",
			 url: "<%=basePath%>contact!claim.action",
			data: "taskId="+taskId+"&id="+id,
		 success: function(data){
			right = false;
			showTip(data.result.msg,2000);
			if(data.result.success){
				setTimeout("window.location.reload();", 500);
			}
		 }
		});
		right = false;
	}
	
	function closeNew(){
		$("#company_label").hide();
	}
	
	function addProcess1(conName,name,id){
		name = name.toLowerCase();
		var iWidth=1024; //弹出窗口的宽度;
		var iHeight=500; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		var openUrl = "";
		openUrl = "<%=BaseAction.rootLocation %>/parkmanager.pf/"+name+"!add.action?taskId="+id;//弹出窗口的url
		window.open(openUrl,'新建'+conName,'top='+iTop+',left='+iLeft+',height='+iHeight+',width='+iWidth+',toolbar=no,menubar=no,scrollbars=yes,resizable=no, location=no,status=no');
	}
	
	function deleteTask(pid,id,actionName){
		right = false;
		if(confirm("您确定要删除")){
			actionName = actionName.toLowerCase();
			$.post("<%=basePath%>"+actionName+"!delete.action?pid="+pid+"&&id="+id,function(data){
				showTip(data.result.msg);
				if(data.result.success){
					setTimeout("location.reload()",2000);
				}
			});
		}
	}
	
	function submitTask(id,actionName){
		right = false;
		actionName = actionName.toLowerCase();
		fbStart('新建企业档案',"<%=basePath%>"+actionName+"!customerAdd.action?id="+id,700,519);
	}
	
	function openContract(customerId){
		fbStart('办公楼租赁合同','<%=basePath %>web/contract/contract_add_customer_rent_3.jsp?item=BGLZL',750,483);
	}
	
</script>
<style type="text/css">
	.highlight {
		background: #f4f4f4;
	}
	.newProcessForm{width:650px;}
	.newProcessForm ul{margin-bottom:5px;width:120px;float:left;padding-left:5px;}
	.newProcessForm ul h1,.newProcessForm ul li a{display:inline-block;width:120px; height:23px; line-height:23px;float:left;}
	.newProcessForm ul h1{clear:both;border-bottom:1px solid #c9c9c9;width:110px; height:30px; line-height:30px;}
	.newProcessForm ul li{border-left:none!important; border-right:none!important; height:auto!important; clear:both;}
	.newProcessForm ul li a{cursor:pointer;padding-left:8px;display:inline-block;background:url(../core/common/images/yd.gif) no-repeat left;}
	.newProcessForm span{ top:0!important;}
	.newProcessForm ul.shourul{ width:120px;float:left; }
	.newProcessForm ul.shourul h1,.newProcessForm ul.shourul li a{display:inline-block;width:100px; height:23px; line-height:23px;float:left;}
	.newProcessForm ul.shourul h1{clear:both;border-bottom:1px solid #c9c9c9;width:70px; height:30px; line-height:30px;float:left;}
	.company_label ul li{padding-left:0px;}
</style>
</head>
<body>
<div class="emailtop" style="positon:relative;"> 
  <!--leftemail-->
  <div class="leftemail" id="" >
    <ul>
      <li
          class="libg"
          onclick="location.href='/parkmanager.pf/contact!launchList.action'" class="">
          <span><img src="parkmanager.pf/images/process_02_min.png"/></span>我发起的流程
          <em class="em"></em> </li>
      <li onmouseover="this.className='libg'" 
          onmouseout="this.className=''" 
          onclick="location.href='/parkmanager.pf/contact!taskList1.action'" class="">
          <span><img src="parkmanager.pf/images/process_03_min.png"/></span>待处理的流程
          <em class="em"></em> </li>
      <li onmouseover="this.className='libg'" 
          onmouseout="this.className=''" 
          onclick="location.href='/parkmanager.pf/contact!hiTaskList.action'" class="">
          <span><img src="parkmanager.pf/images/process_04_min.png"/></span>已处理的流程
          <em class="em"></em> </li>
    </ul>
  </div>
  <!--//leftemail--> 
</div>
<!--container-->
<div id="container">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td width="" valign="top" id="fee_lefts">
				<div class="write_list" style="border-right: 1px solid #ddd; border-bottom: none; width: 510px;padding-top:24px;position:relative;" id="resizable">
					<!--merter_fee-->
					<table style="position:absolute;top:0px;" width="510" border="0" cellspacing="0" cellpadding="0">
						<tr class="autoWidth">
							<td class="tdleftc"  width="30"><img src="/parkmanager.pf/images/redxqz.gif" width="9" height="10" /></td>
							<td class="tdleftc" width="120">流程名称</td>
							<td class="tdleftc" width="80">申请人</td>
							<td class="tdleftc"  width="90">创建日期</td>
							<td class="tdleftc">当前节点</td>
							<td class="tdleftc" width="80">操作</td>
						</tr>
					</table>
					<div id="contactdiv" style="height:200px;overflow-x: hidden; overflow-y: auto;position:relative;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="lsittable">
							<c:forEach items="${result.value }" var="dto">
								<tr class="autoWidth2" onmouseover="this.style.background='#f4f4f4'" 
										onmouseout="this.style.background='#fff';keepHighlight(this)" 
										style="cursor: pointer;" id="${dto.hiProcessInstance.businessKey }" tid="${dto.task.id }" pid='${dto.hiProcessInstance.id}' formname="${dto.processType.formName }"
										tpid="${dto.processType.processDefinition.id }" 
										onclick="openRight('${dto.hiProcessInstance.id }',${dto.hiProcessInstance.businessKey },this);highlight(this);">
								  <td class="centertd "  width="30"><c:if test="${dto.hiProcessInstance.endTime!=null }" var="isEnd"><img src="/parkmanager.pf/images/grayxqz.gif" width="9" height="10" /></c:if><c:if test="${!isEnd}"><img src="/parkmanager.pf/images/redxqz.gif" width="9" height="10" /></c:if></td>
								  <td class="centertd"  width="120">${dto.processType.formName.title }V${dto.processType.processDefinition.version }</td>
								  <td class="centertd"  width="80">${dto.user.realName }</td>
								  <td class="centertd"  width="90"><fmt:formatDate pattern="yyyy/M/d" value="${dto.hiProcessInstance.startTime }" type="date"/></td>
								  <td class="centertd"><c:if test="${dto.hiProcessInstance.endTime!=null }" var="isEnd">已结束</c:if><c:if test="${!isEnd}">${dto.task.name }</c:if></td>
								  <td class="centertd" width="80">
								  	<c:choose>
								  		<c:when test="${dto.task.taskDefinitionKey eq 'modifyApply' || dto.task.taskDefinitionKey eq 'reportBack'}">
											<a class="handle" style="text-decoration:none;" tkey='${dto.task.taskDefinitionKey }' tname='${dto.task.name }' href="javascript:;">办理</a>
								  		</c:when>
								  		<c:otherwise>
								  			<a style="text-decoration:none;" href="javascript:void(0);" onclick="deleteTask('${dto.hiProcessInstance.id }','${dto.hiProcessInstance.businessKey }','${dto.processType.formName}');">删除</a>
								  			<c:if test="${dto.hiProcessInstance.endTime!=null && dto.processType.formName == 'FICTITIOUS'  }">
								  				<a style="text-decoration:none;" href="javascript:void(0);" onclick="submitTask('${dto.hiProcessInstance.businessKey }','${dto.processType.formName}');">提交</a>
								  			</c:if>
								  		</c:otherwise>
								  	</c:choose>
								  </td>
								</tr>
							</c:forEach>
						</table>
						<div style="position:fixed;bottom:0px;width:510px;border-right: 1px solid #ddd;background:#f2f2f2;">
							<!--分页开始-->
							<%@include file="../pager.jsp"%>
							<!--分页结束-->
						</div>
					</div>
				</div>
			</td>
			<td valign="top">
				<!--table切换开始--> <!--//table切换开始-->
				<div id="navlist" style="border-color: #fff;overflow: auto;">
					
					
					<div class="tasklist rightv2t">
				       
				      <c:forEach items="${processTypeList }" var="type">
					  <div class="taskcontet liuchengtext">
					  <h3 class="v2titlebg02"> ${type.typeName } </h3>
					  	<ul>
					  		<li class="ctext">
					  		<c:forEach items="${type.processTypes}" var="processType">
					  			<a class="huise underline" href="javascript:addProcess1('${processType.formName.title }','${processType.formName }','${processType.processDefId }');" >
					    		${processType.formName.title }V${processType.processVesion}</a>&nbsp;
					  		</c:forEach>
					  		</li>
					  	</ul>
					  </div>
					  </c:forEach>
				      
				    </div>
				</div>
			</td>
		</tr>
	</table>
</div>

</body>
</html>

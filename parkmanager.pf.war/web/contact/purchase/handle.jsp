<%@page import="com.wiiy.core.entity.User"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ page import="com.wiiy.pf.preferences.enums.LeaveTypeEnum"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<%
	User user = PfActivator.getSessionUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
	if(user == null){
%>
	<title>即将关闭</title>
<%}else{%>
	<title>办理</title>
<%}%>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/tree/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/tree/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="core/common/tree/demo/demo.css"/>
<link href="parkmanager.pf/style/base.css" rel="stylesheet" type="text/css" />
<link href="parkmanager.pf/style/content.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/tree/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="core/common/tree/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>

<script type="text/javascript">
var variables = {'keys':"",'values':"",'types':""};
var canCalendar = false;
$(document).ready(function(){
	initTip();
	initForm();
	//alert($("body").height());
	<%
	if(user == null){
	%>
		$("body").html("<h1 style=\'margin-top:200px;text-align:center;font-size:14px;\'>未登录或会话过期，本窗口将在<span style=\'color:red;margin:0px 5px;\'>5</span>秒后关闭</h1>");
		setTimeout("window.close()",5000);
	<%}%>
	initContent();
	initUploadify();
	if("${result.value.task.taskDefinitionKey}" == 'modifyApply'){
		radioChange();
	}
	
	initDefinitions();
});
function initDefinitions(){
	var id= "${result.value.hiProcessInstance.processDefinitionId}";
	var pid="${result.value.hiProcessInstance.id}";
	$.ajax({
		 "url" : "<%=basePath%>contact!findDefinitions.action?id="+id+"&pid="+pid,
		  type:"POST",
		  success: function(data){
			  
			  if(data.result.success){
				  var definition = data.result.value;
				  var infos = data.userTaskInfos;
				  var htmlTxt = "";
				  for(var i= 0;i<definition.length;i++){
					  var info = "";
					  var id = "";
					  if(infos.length > i){
						  var taskInfo = infos[i];
						  
						  if(taskInfo.display){
						  	if(taskInfo!=null){
						 	 info = "默认：";
							  
								  if(taskInfo.assignee!=null){//流程中已经指派负责人
									  //info += "负责人:[";
									  info += "[";
									  info += taskInfo.assignee.firstName;
									  if(taskInfo.assignee.id==null)info +="(待创建)";
									  info += "] ";
									  
								  }
								  if(taskInfo.candidateUserList!=null&&taskInfo.candidateUserList.length>0){//流程中已经指派负责人
									  var arrays=taskInfo.candidateUserList;
									  //info += "候选审批人:";
									  for(var j=0;j<arrays.length;j++){
										  info += "[";
										  info +=arrays[j].firstName;
										  if(arrays[j].id==null)info +="(待创建)";
										  info += "] ";
									  } 
								  }
								  if(taskInfo.candidateGroupList!=null&&taskInfo.candidateGroupList.length>0){//流程中已经指派负责人
									  var arrays=taskInfo.candidateGroupList;
									 // info += "候选审批角色:";
									  for(var j=0;j<arrays.length;j++){
										  info += "[";
										  info +=arrays[j].name;
										  if(arrays[j].id==null)info +="(待创建)";
										  info += "] ";
									  } 
								  }
								  
							  }
							  if(taskInfo.historicVariableDtos!=null&&taskInfo.historicVariableDtos.length>0){
								  info += "<br>";
								  info +=taskInfo.dealUser.firstName+":";
								  var displayTime=true;
								  for(var j=0;j<taskInfo.historicVariableDtos.length;j++){
									  if(taskInfo.historicVariableDtos[j].variableTypeName=='boolean'){
										  if(taskInfo.historicVariableDtos[j].value=='true'){
											  info += "<font color='green'>同意申请("+taskInfo.historicVariableDtos[j].time+")</font>";
											  displayTime=false;
										  }
										  else if(taskInfo.historicVariableDtos[j].value=='false'){
											  info += "<font color='red'>拒绝申请("+taskInfo.historicVariableDtos[j].time+")</font>";
											  displayTime=false;
										  }
									  }
									  else{
										  info += taskInfo.historicVariableDtos[j].value;
										  if(displayTime)
											  info+="("+taskInfo.historicVariableDtos[j].time+")";
									  }
									  info += "<br>";
								  }
								  
							  }
						 
						  
							  htmlTxt += "<tr><td class=\'layertdleft100\'>"+definition[i].nameExpression.expressionText+"：</td>";
							  htmlTxt += "<td colspan=\'3\' class=\'layerright\' style=\'padding-bottom:2px;padding-top:2px;padding-right:5px;\'>";
							  if(id < 0){
								  htmlTxt += "<div  style=\'cursor:pointer;line-height:22px;min-height:40px;resize: none;color:#666;\'>"+info+"</div>";
							  }else{
								  htmlTxt += "<div  style=\'cursor:pointer;background-color:#FFFFCB;line-height:22px;min-height:40px;resize: none;color:#666;\'>"+info+"</div>";
							  }
							  htmlTxt += "</td></tr>";
						  }
					  }
				  }
				  $("#insertTxt").html($("#insertTxt").html()+htmlTxt); 
			  }
		  }
	});
}
function initForm(){
	$("#form1").validate({
		rules: {
			"purchaseRequisition.purchaseDepartment":"required",
			"purchaseRequisition.requisitioner":"required"
		},
		messages: {
			"purchaseRequisition.purchaseDepartment":"请填写申购部门",
			"purchaseRequisition.requisitioner":"请填写申购人"
		},
		errorPlacement: function(error, element){
			showTip(error.html());
		},
		submitHandler: function(form){
			if(getVariables()){
				var sendEmail = false;
				if($("#checkbox").attr("checked")=='checked'){
					sendEmail = true;
				}
				$("#filePath").val(getAttsList());
				$(form).ajaxSubmit({
			        dataType: 'json',
			        data: {
			        	'keys' : variables.keys,
			           	'values': variables.values,
			            'types': variables.types,
			            'sendEmail':sendEmail
		            },
			        success: function(data){
		        		showTip(data.result.msg,2000);
			        	if(data.result.success){
			        		setTimeout("closeWeb();", 2000);
			        	}
			        } 
			    });
			}
		}
	});
}

function selectCalendar(cid){
	if(canCalendar)
		showCalendar(cid);
}

function radioChange(){
	changeState(true);
	$("input[name='agreement']").change(function(){
		var value = ($("input[name='agreement']:checked").val());
		if(value == 'false')
			value= false;
		else
			value =true;
		changeState(value);
	});
}

function changeState(state){
	if(state){
		$(".readon").removeAttr("readonly");
	}else{
		$(".readon").attr("readonly","readonly");
	}
}

/**
 * B:boolean
 * S:String
 */
function getVariables(){
	var deal = "${result.value.task.taskDefinitionKey}";
	var agree =  $("input[name='agreement']:checked").val();
	var leaderBackReason = $("#reason").val();
	switch(deal){
	case "usertask1":
		if(leaderBackReason == ''){
			alert("请输入部门负责人意见");
			$("#reason").focus();
			return false;
		}
		variables.keys= 'departLeaderPass,leaderBackReason';
		variables.values= true;
		variables.types= 'B,S';
		if(agree == 'false'){
			variables.values= false;
		}
		variables.values += ","+leaderBackReason;
		break;
	case "usertask2":
		if(leaderBackReason == ''){
			alert("请输入公司负责人意见");
			$("#reason").focus();
			return false;
		}
		variables.keys= 'chargePersonPass,leaderBackReason';
		variables.values= true;
		variables.types= 'B,S';
		if(agree == 'false'){
			variables.values= false;
		}
		variables.values += ","+leaderBackReason;
		break;
	case "usertask3":
		if(leaderBackReason == ''){
			alert("请输入仓库确认意见");
			$("#reason").focus();
			return false;
		}
		variables.keys= 'entrepotPass,leaderBackReason';
		variables.values= true;
		variables.types= 'B,S';
		if(agree == 'false'){
			variables.values= false;
		}
		variables.values += ","+leaderBackReason;
		break;
	case "usertask4":
		if(leaderBackReason == ''){
			alert("请输入采购经办人意见");
			$("#reason").focus();
			return false;
		}
		variables.keys= 'agentPass,leaderBackReason';
		variables.values= true;
		variables.types= 'B,S';
		if(!agree){
			variables.values= false;
		}
		variables.values += ","+leaderBackReason;
		break;
	case "modifyApply":
		variables.keys = 'reApply';
		variables.values = agree;
		variables.types = 'B';
		break;
	}
	return true;
}

function initUploadify(){
	<% String rootPath = BaseAction.rootLocation+"/";%>
	$("#fileUpload").uploadify( {
		'auto'				: true, //是否自动开始 默认true
		'multi'				: false, //是否支持多文件上传 默认true
		'formData'			: {'module':'pf','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
		'uploader'			: "<%=rootPath %>core/upload.action",//文件服务器路径
		'swf'				: uploadify.swf,//上传组件swf
		'width'				: 60,//按钮图片的长度
		'height'			: uploadify.height,//按钮图片的高度
		'buttonText'		: '<span style="top:5px;"></span>添加附件 ', //按钮上的文字
		'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
		'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
		'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
		'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
		'fileTypeDesc'		: "*.*",//选择文件对话框文件类型描述信息
		'fileTypeExts'		: "*.*",//可上传上的文件类型
		'onUploadSuccess'	: uploadSuccess
	});
}
function uploadSuccess(file, data, response){
	var file = $.parseJSON(data);
	addText(file);
}

function addText(file){
	var path = file.path;
	var newName = file.name;
	var oldName = file.originalName;
	var size = file.size;
	var htmlText = $("#attrs").html();
	htmlText += "<div class=\'downlistprocess\'>";
	htmlText +="<img src='core/common/images/downloadico.png' />";
	htmlText +="<input type='hidden' value=\'"+path+"\'/>";
	htmlText +="<input type='hidden' value=\'"+oldName+"\'/>";
	htmlText +="<input type='hidden' value=\'"+size+"\'/>";
	htmlText +="<ul>";
	htmlText +="<li>"+oldName;
	htmlText +="</li><li>";
	htmlText +='<a href="javascript:void(0)" onclick="">下载</a>';
	//htmlText +='<a href="javascript:void(0)" onclick="">打开</a>';
	htmlText +="<a href=\'javascript:void(0)\' onclick=\'deleteAttr(this)\'>删除</a>";
	htmlText +="</li>";
	htmlText +="</ul>";
	htmlText +="</div>";
	$("#attrs").html(htmlText);
}

function getAttsList(){
	var topicPaths = '[';
	var obj = $("#attrs").children();
	$(obj).each(function (i){
		var child = $(this).children();
		var oldName = $(child).eq(2).val();
		var size = $(child).eq(3).val();
		alert(size);
		oldName = encodeURIComponent(oldName);
		oldName = encodeURIComponent(oldName);
		var str = "{\"filePath\" : \""+$(child).eq(1).val()+"\",";
		str += "\"type\" : \"attachment\",";
		str += "\"size\" : \""+size+"\",";
		str += "\"fileName\" : \""+oldName+"\"}";
		topicPaths += str+",";
	})
	if(topicPaths.lastIndexOf(",") == topicPaths.length-1)
		topicPaths = topicPaths.substr(0,topicPaths.length-1);
	return topicPaths+"]";
}

function initContent(){
	var size = ($(".content").size());
	<%int i=0;%>
	for(var i=0;i<size;i++){
		var obj = $(".content").eq(i);
		var child = $(obj).children().eq(0);
		var uid = $(child).attr("userid");
		if(uid > 0)
			$(obj).find("textarea").css("background-color","#FFFFCB");
	}
}

function closeWeb(){
	window.opener.location.href=window.opener.location.href;window.close();
}

function deleteAttr(obj,id){
	var name =  $(obj).parent().parent().children().eq(0).text();
	obj = $(obj).parent().parent().parent();
	var pid="${result.value.hiProcessInstance.id}";
	if(confirm("您确定要删除 \""+name+"\"")){
		$.post("<%=basePath %>contactBaseAtt!delete.action?id="+id,function(data){
			showTip(data.result.msg,2000);
			$(obj).remove();
		});
	}
}
function downLoadFile(path,name){
	location.href="<%=BaseAction.rootLocation %>/core/resources/"+path+"?name="+name;
}
</script>
<style>
.uploadify-button {
		background: #fff /*url("../core/common/images/emailadd.gif")*/;
		background-position: left center;
		background-repeat: no-repeat;
		border: 1px solid #f0f0f0;;
		color: #333;
		font: 12px Arial, Helvetica, sans-serif;
		/*padding-left:10px;*/
		position: relative;
		text-align: center;
		top: 3px;
		width: 100%;
	}
</style>
</head>

<body>
<form action="<%=basePath %>${actionName }!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="taskId" value="${result.value.task.id }"/>
<input type="hidden" name="pid" value="${result.value.processInstance.processInstanceId}"/>
<input type="hidden" id="filePath" name="filePath" value=""/>
<!--basediv-->
<div id="container" style="height:500px;overflow: scroll;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0"  style="border:1px solid #ececec;">
    <tr>
      <td valign="top">      
         <!--divlays-->
         <div class="basediv" >
         	<s:set var="actionName">${actionName}!view</s:set>
			<s:action name="%{#actionName}" namespace="/" executeResult="true"></s:action>
          </div> 
      </td>
      <td width="335" valign="top">
      <table width="98%" cellspacing="0"cellpadding="0">
          <tr>
            <td>
                <!--basediv-->
                <div class="basediv" > 
                  <!--divlays-->
                  <div class="divlays reminds"> 
                    <!--titlebg-->
                    <div class="righttitle">
                      <ul>
                        <li class="fjTitle"><strong>附件</strong></li>
                        <li class="addfj"><input id="fileUpload" name="uploadify" type="file"/></li>
                      </ul>
                    </div>
                    <div id="attrs">
                    </div>
                  </div>
					<div class="hackbox"></div>
                    <!--//titlebg--> 
                  <div class="textremind">
                  <ul>
	                  <li><strong>短信</strong> </li>
	                  <li class="remind"> <textarea id="content" name="content" cols="45" rows="3" style="resize:none;"></textarea></li>
	                  <li class="remind2">
	                      <input type="checkbox" id="checkbox" />
	                      	发送短信提醒一下环节人员
	                  </li>
                  </ul>
				</div>
				</div>
              </td>
          </tr>
          <tr>
          	<td>
           		<c:choose>
           		<c:when test="${result.value.task.taskDefinitionKey eq 'modifyApply'}">
           		<div class="basediv" style="width: 330px;height: 45px;border: 1px solid #cbcbcb;margin-top: 5px;"> 
					<!--divlays-->
					<div class="divlays"> 
					<!--titlebg-->
					<div style="margin:12px 5px 0px 10px;">
		             	<label>
		             		<input type="radio" name="agreement" value="true" checked/><span style="margin-left:2px;">调整申请</span>
		             	</label>
		             	<label>
		             		<input type="radio" name="agreement" value="false" /><span style="margin-left:2px;">取消申请</span>
		             	</label>
             		</div>
          			</div>
              	<div class="hackbox"></div>
                  <!--//basediv-->
				</div>
           		</c:when>
           		<c:when test="${result.value.task.taskDefinitionKey eq 'usertask1' ||result.value.task.taskDefinitionKey eq 'usertask2' || result.value.task.taskDefinitionKey eq 'usertask3'|| result.value.task.taskDefinitionKey eq 'usertask4'}">
           		<div class="basediv reminds3"> 
					<!--divlays-->
					<div class="divlays"> 
					<!--titlebg-->
					<div style="margin:12px 5px 0px 10px;">
           			<label>
             		<input type="radio" name="agreement" value="true" checked/><span style="margin-left:2px;">同意</span>
	             	</label>
	             	<label>
             		<input type="radio" name="agreement" value="false" /><span style="margin-left:2px;">不同意</span>
	             	</label>
	             	</div>
          			</div>
          		<div class="textremind">
						<ul>
						<li><strong>${result.value.task.name }</strong> </li>
						<li class="remind"> <textarea id="reason" cols="45" rows="3" style="resize:none;"></textarea></li>                  
						</ul>
					</div>
              	<div class="hackbox"></div>
                  <!--//basediv-->
				</div>
           		</c:when>
           		</c:choose>
               <div class="buttondiv2" style="margin-top:10px;">
                 <input  type="image" src="parkmanager.pf/images/bt_bllc.gif"  name="zConserve" id="zConserve" value="" />
               </div>
          	</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</form>
</body>
</html>

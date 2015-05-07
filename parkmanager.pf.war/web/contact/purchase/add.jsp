<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.core.entity.User"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<title>新建内部联系单</title>
<%}%>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="parkmanager.pf/style/base.css" rel="stylesheet" type="text/css" />
<link href="parkmanager.pf/style/content.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />

<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>

<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>

<script type="text/javascript">
var clicked = null;
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
	initDefinitions();
	initUploadify();
});

function initDefinitions(){
	var id= $("#taskId").val();
	$.ajax({
		 "url" : "<%=basePath%>contact!findDefinitions.action?id="+id,
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
									  info += "负责人:[";
									  info += taskInfo.assignee.firstName;
									  if(taskInfo.assignee.id==null)info +="(待创建)";
									  info += "] ";
									  
								  }
								  if(taskInfo.candidateUserList!=null&&taskInfo.candidateUserList.length>0){//流程中已经指派负责人
									  var arrays=taskInfo.candidateUserList;
									  info += "候选审批人:";
									  for(var j=0;j<arrays.length;j++){
										  info += "[";
										  info +=arrays[j].firstName;
										  if(arrays[j].id==null)info +="(待创建)";
										  info += "] ";
									  } 
								  }
								  if(taskInfo.candidateGroupList!=null&&taskInfo.candidateGroupList.length>0){//流程中已经指派负责人
									  var arrays=taskInfo.candidateGroupList;
									  info += "候选审批角色:";
									  for(var j=0;j<arrays.length;j++){
										  info += "[";
										  info +=arrays[j].name;
										  if(arrays[j].id==null)info +="(待创建)";
										  info += "] ";
									  } 
								  }
								  
							  }
							  htmlTxt += "<tr><td class=\'layertdleft100\'>"+definition[i].nameExpression.expressionText+"：</td>";
							  htmlTxt += "<td colspan=\'3\' class=\'layerright\' style=\'padding-bottom:2px;padding-top:2px;padding-right:5px;\'>";
							  if(id < 0){
								  htmlTxt += "<textarea readonly=\'readonly\' class=\'inputauto\'  style=\'cursor:pointer;height:40px;resize: none;color:#666;\'>"+info+"</textarea>";
							  }else{
								  htmlTxt += "<textarea readonly=\'readonly\' class=\'inputauto\'  style=\'cursor:pointer;background-color:#FFFFCB;height:40px;resize: none;color:#666;\'>"+info+"</textarea>";
							  }
							  htmlTxt += "</td></tr>";
						  }
					  }
				  }
				  $("#insertTxt").html($("#insertTxt").html()+htmlTxt); 
				  $("select").each(function(){
					$(this).children().eq(1).selected();
				  });
			  }
		  }
	});
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
	//htmlText +='<a href="javascript:void(0)" onclick="">下载</a>';
	//htmlText +='<a href="javascript:void(0)" onclick="rename(this)">重命名</a>';
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

function deleteAttr(obj) {
	var name =  $(obj).parent().parent().children().eq(0).text();
	var file = $(obj).parent().parent().parent().children().eq(1).val();
	if(confirm("您确定要删除 \""+name+"\"")){
		$.ajax({
			type:"POST",
			 url: "<%=basePath %>contact!deleteByFilePath.action",
			data:"filePath="+file,
			success: function(msg){
			     showTip(msg.result.msg,2000);
			     if(msg.result.success){
			    	 $(obj).parent().parent().parent().remove();
			     }
			}
		});
	}
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
			$("#filePath").val(getAttsList());
			$(form).ajaxSubmit({
		        dataType: 'json',		        
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("closeWeb();", 2000);
		        	}
		        } 
		    });
		}
	});
}


function closeWeb(){
	window.opener.location.href=window.opener.location.href;window.close();
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
<form action="<%=basePath %>purchase!save.action" method="post" name="form1" id="form1">
<input type="hidden" id="filePath" name="filePath" value=""/>
<input type="hidden" id="taskId" value="${taskId }"/>
<!--basediv-->
<div id="container" style="height:500px;overflow: scroll;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #ececec;">
    <tr>
      <td valign="top">      
         <!--divlays-->
         <div class="basediv" style="min-width:600px;">
			<div class="divlays" style="margin:0px;">
              <table id="insertTxt" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="4">
						<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>实际结算</strong></div>
					</td>  
				</tr>
				<tr>
					<td class="layertdleft100">申请人：</td>
					<td width="29%" class="layerright"><input id="name" disabled="disabled" value="<%=(user == null ? "" :user.getRealName()) %>" type="text" class="inputauto" /></td>
					<td width="40%" class="layertdleft100">填写日期：</td>
					<td width="29%" class="layerright">
						<input name="applyTime" disabled="disabled" class="inputauto" value='默认为当前日期'/>
					</td>
				</tr>
				<tr>
				    <td class="layertdleft100"><span class="psred">*</span>申购部门：</td>
					<td class="layerright">
						<input id="purchaseDepartment" name="purchaseRequisition.purchaseDepartment" type="text"  class="inputauto"/>
					</td>
					<td class="layertdleft100"><span class="psred">*</span>申购人：</td>
					<td class="layerright">
					<input id="requisitioner" name="purchaseRequisition.requisitioner" type="text"  class="inputauto"/>
					</td>
		       	</tr>
		       	<tr>
		       	    <td class="layertdleft100">采购情况：</td>
			    	<td class="layerright" colspan="3" style="padding-bottom:3px;">
			    		<textarea name="purchaseRequisition.opinion" class="inputauto" style="resize:none;height:40px;"></textarea>
			    	</td>
		       	</tr>
		       	<tr>
					<td colspan="4">
						<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>审批信息</strong></div>
					</td>
				</tr>
			</table>
          	</div>
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
					<div class="hackbox"></div>
                  </div>
                  <!--//divlays--> 
                    <!--//titlebg--> 
                  <div class="textremind">
                  <ul>
	                  <li><strong>短信</strong> </li>
	                  <li class="remind"> <textarea name="remind" id="remind" cols="45" rows="3" style="resize:none;"></textarea></li>
	                  <li class="remind2">
	                      <input type="checkbox" name="checkbox" id="checkbox" />
	                      	发送短信提醒一下环节人员
	                  </li>
                  </ul>
                  </div>
                  
					<div class="hackbox"></div>
				<!--//basediv-->
				</div>
				<div class="buttondiv2" style="margin-top:10px;">
				  <input  type="image" src="parkmanager.pf/images/bt_fqlc.gif"  name="zConserve" id="zConserve" value="" />
				</div>
              </td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>
</form>
</body>
</html>

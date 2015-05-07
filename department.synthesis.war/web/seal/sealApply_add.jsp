<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.synthesis.preferences.enums.SealApplyEnum"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>

	<script type="text/javascript">
		$(function(){
			initTip();
			initForm();
			initUploadify("file");
		});
		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		} 
 		
		function initForm(){
			$("#form1").validate({
				rules: {
					"sealRegistration.userId":"required",
					"sealRegistration.sealNames":"required"
				},
				messages: {
					"sealRegistration.userId":"请选择使用人",
					"sealRegistration.sealNames":"请选择印章"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					var att  = "";
					var path = "";
					var name = "";
					var size = "";
					$(".attPath").each(function(){
						path = $(this).attr("value");
						name = $(this).prev(".attName").attr("value");
						size = $(this).prev().prev(".size").attr("value");
						att += path+","+size+","+name+";";
					});
					$(form).ajaxSubmit({
				        dataType: 'json',
				        data: {attAddress:att},
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadList();parent.fb.end()", 2000);
				        	}
				        } 
				    });
				}
			});
		}
		
		function initUploadify(id){
			$("#"+id).uploadify( {
				'auto'				: true, //是否自动开始 默认true
				'multi'				: false, //是否支持多文件上传 默认true
				'formData'			: {'module':'oa','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
				'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
				'swf'				: uploadify.swf,//上传组件swf
				'width'				: uploadify.width,//按钮图片的长度
				'height'			: uploadify.height,//按钮图片的高度
				'buttonText'		: uploadify.buttonText, //按钮上的文字
				'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
				'fileSizeLimit'		: 102400,//控制上传文件的大小，单位byte
				'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
				'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
				'fileTypeDesc'		: "Word文件",//选择文件对话框文件类型描述信息
				'fileTypeExts'		: "*.doc",//可上传上的文件类型
				'onUploadSuccess'	: onUploadSuccess//上传成功回调函数 有三个参数 file(可取文件名  注：上传前的文件名不是服务器端重写的文件名) data(服务器端写到response里面的信息) response(true,false)
			});
		}
		
		function onUploadSuccess(file, data, response){
			$("#newName").val($.parseJSON(data).path);
			$("#attList").append($("<li></li>").append("<label></label>").append($.parseJSON(data).originalName).append("<input type='hidden' value='"+$.parseJSON(data).size+"' class='size' /><input type='hidden' value='"+$.parseJSON(data).originalName+"' class='attName' /><input type='hidden' value='"+$.parseJSON(data).path+"' class='attPath' />").append($("<img />",{src:'core/common/images/locking.jpg',click:function(){
				$.post("core/resources/"+$.parseJSON(data).path+"-d");
				$(this).parent().remove();
			}})));
		}
		
		function deleteAtt(obj){
			var path = $(obj).prev().val();
			$.post("core/resources/"+path+"-d");
			$(obj).parent().remove();
		}
		
		
		function setSelectedUser(user){
			$("#userId").val(user.id);
			$("#userName").val(user.realName);
		}
		function selectUser(){
			fbStart('选择用户','<%=BaseAction.rootLocation%>/core/user!selectSelf.action',520,400);
		}
		
		function selectSeals(){
			fbStart('选择印章','<%=basePath%>seal!select.action',520,400);
		}
		
		function setSelectedSeals(seals){
			var ids = "";
			var names = "";
			for(var i = 0; i < seals.length; i++){
				ids += ","+seals[i].id;
				names += seals[i].name+",";
			}
			ids = deleteLastCharWhenMatching(ids,",");
			names = deleteLastCharWhenMatching(names,",");
			$("#ids").val(ids);
			$("#sealName").val(names);
		}

	</script>
</head>
<body>
<form action="<%=basePath %>sealRegistration!save.action" method="post" name="form1" id="form1">
<input type="hidden" value="SAPPLAY" name="sealRegistration.status"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	<tr>
		    <td class="layertdleft100">使用人：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="20%"><%=SynthesisActivator.getSessionUser(request).getRealName() %><input id="userId" name="sealRegistration.userId" value="<%=SynthesisActivator.getSessionUser(request).getId() %>" type="hidden" /></td>
					</tr>
				</table>
			</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100"><span class="psred">*</span>印章：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><input type="hidden" id="ids" name="sealRegistration.sealIds"/><input id="sealName" name="sealRegistration.sealNames" readonly="readonly" type="text" class="inputauto" onclick="selectSeals()" /></td>
						<td width="25"><img style="position: relative;left:-1px;" onclick="selectSeals()" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
					</tr>
				</table>
			</td>
        </tr>
        <tr>
			<td class="layertdleft100">使用日期：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100"><input readonly="readonly" id="time" name="sealRegistration.time" value='<fmt:formatDate pattern="yyyy-MM-dd" value="<%=new Date() %>"/>' type="text" class="inputauto" onclick="return showCalendar('time')"/></td>
						<td width="20"><img src="core/common/images/timeico.gif" style="position: relative;left:-1px;" width="20" height="22" onclick="return showCalendar('time')"/></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
	        <td class="layertdleft100">添加附件：</td>
	       	<td class="layerright" height="40">
	       		<input id="size" name="sealRegistrationAtt.size" type="hidden" />
	       		<input id="name" name="sealRegistrationAtt.name" type="hidden" />
	       		<input id="newName" name="sealRegistrationAtt.newName" type="hidden"/>
				<table><tr>
					<td><input id="file" type="file" /></td>
				</tr></table>
			</td>
   		</tr>
   		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		    <tr>
		    <td class="layertdleft100">附件：</td>
		    <td class="layerright">
		      <table border="0" cellspacing="0" cellpadding="0" style="white-space:nowrap;">
		      	<tr><td style="padding-left:5px;width:300px;">
		      		<div id="attList" style="height: 40px;overflow-x:hidden;overflow-y: auto; ">
			      	</div>
			      	</td>
			      </tr>
		      </table>
		     </td>
		    </tr>
   		</table>
   		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr>
		      <td class="layertdleft100">备注：</td>
		      <td  class="layerright"><textarea name="sealRegistration.memo" rows="3" class="textareaauto"></textarea></td>
		    </tr>
	    </table>
  	</table>
  </div>
<div class="hackbox"></div>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
 </div>
</form>
</body>
</html>

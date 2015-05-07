<%@page import="com.wiiy.synthesis.preferences.enums.SealApplyEnum"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.wiiy.core.entity.User"%>

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
        			"laborContract.name" : "required",
        			"laborContract.position" : "required",
        			"laborContract.startTime" : "required",
        			"laborContract.endTime" : "required",
        			"laborContract.signingDate" : "required"
    			},
    			messages: {
    				"laborContract.name":"请输入姓名",
    				"laborContract.position":"请输入岗位名称",
    				"laborContract.startTime" : "请输入开始时间",
    	        	"laborContract.endTime" : "请输入结束时间",
    	        	"laborContract.signingDate" : "请填写签订日期"
    			}, 
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
 					$("input[name='filePath']").val(getAttsList());
						$(form).ajaxSubmit({
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadList();fb.end()", 2000);
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
				'formData'			: {'module':'syn','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
				'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
				'swf'				: uploadify.swf,//上传组件swf
				'width'				: uploadify.width,//按钮图片的长度
				'height'			: uploadify.height,//按钮图片的高度
				'buttonText'		: uploadify.buttonText, //按钮上的文字
				'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
				'fileSizeLimit'		: 102400,//控制上传文件的大小，单位byte
				'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
				'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
				'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
				'fileTypeExts'		: "*.*;*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
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
		function getAttsList(){
			var topicPaths = '[';
			var obj = $("#attList").children();
			$(obj).each(function (i){
				var child = $(this).children();
				var oldName = $(child).eq(2).val();
				oldName = encodeURIComponent(oldName);
				oldName = encodeURIComponent(oldName);
				var str = "{\"filePath\" : \""+$(child).eq(3).val()+"\",";
				str += "\"fileName\" : \""+oldName+"\"}";
				topicPaths += str+",";
			})
			if(topicPaths.lastIndexOf(",") == topicPaths.length-1)
				topicPaths = topicPaths.substr(0,topicPaths.length-1);
			return topicPaths+"]";
		}
	</script>
</head>
<body>
<form action="<%=basePath %>laborContract!save.action" method="post" name="form1" id="form1">
<input type="hidden" name="filePath"/>
<input type="hidden" name="laborContract.userId" value="${param.id }"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
   	    	<td class="layertdleft100"><span class="psred">*</span>姓名：</td>
   			<td class="layerright"><input name="laborContract.name" type="text" class="inputauto" /></td>
      	</tr>
       	<tr>
       	    <td class="layertdleft100"><span class="psred">*</span>岗位名称：</td>
       		<td class="layerright"><input name="laborContract.position" type="text" class="inputauto" /></td>
      	</tr>
      	<tr>
      	<td class="layertdleft100">合同类型：</td>
            <td>&nbsp;
                 <enum:radio name="laborContract.contractCharacter" type="com.wiiy.synthesis.preferences.enums.LaborContractTypeEnum" checked="PROBATION" />
            </td>
      	</tr>
		<tr>
			<td class="layertdleft100" ><span class="psred">*</span>开始时间：</td>
     		<td class="layerright" >
	     		<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="startTime" name="laborContract.startTime" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('startTime')"/>
			          </td>
			          <td width="20" align="center">
			          	<img style="position:relative; left:-1px;" onclick="showCalendar('startTime')" src="core/common/images/timeico.gif" width="20" height="22" />
			          </td>
			        </tr>
			    </table>
		    </td>
       	</tr>
       	<tr>
       	<td class="layertdleft100" ><span class="psred">*</span>结束时间：</td>
   		<td class="layerright" >
    		<table width="143" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td>
	          	<input id="endTime" name="laborContract.endTime" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('endTime')"/>
	          </td>
	          <td width="20" align="center">
	          	<img style="position:relative; left:-1px;" onclick="showCalendar('endTime')" src="core/common/images/timeico.gif" width="20" height="22" />
	          </td>
	        </tr>
	    	</table>
		</td>
       	</tr>
       <tr>
       		<td class="layertdleft100" ><span class="psred">*</span>签订日期：</td>
     		<td class="layerright" >
	     		<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="signingDate" name="laborContract.signingDate" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('signingDate')"/>
			          </td>
			          <td width="20" align="center">
			          	<img style="position:relative; left:-1px;" onclick="showCalendar('signingDate')" src="core/common/images/timeico.gif" width="20" height="22" />
			          </td>
			        </tr>
			    </table>
		    </td>
       	</tr>
		<tr>
	        <td class="layertdleft100">添加附件：</td>
	       	<td class="layerright" height="40">
				<table><tr>
					<td><input id="file" name="uploadify" type="file" /></td>
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
  	</table>
  </div>
<div class="hackbox"></div>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="fb.end();"/></label>
 </div>
</form>
</body>
</html>

<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
	
	<script type="text/javascript">
 		var clicked = null;
 		var renameObj = null;
		$(function(){
			initTip();
			initForm();
			initDate();
			initUploadify();
		});
		
		function initDate(){
			<%Calendar c = Calendar.getInstance();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = s.format(c.getTime());	
			c.add(Calendar.YEAR, 1);
			c.add(Calendar.DATE, -1);
			String endTime = s.format(c.getTime());
			%>
			var startDate = '<%=startTime%>';
			var endDate = '<%=endTime%>';
			$("#time").val(startDate);
			
		}
		
		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		}
		function initUploadify(){
			$("#fileUpload").uploadify( {
				'auto'				: true, //是否自动开始 默认true
				'multi'				: false, //是否支持多文件上传 默认true
				'formData'			: {'module':'crm','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
				'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
				'swf'				: uploadify.swf,//上传组件swf
				'width'				: uploadify.width,//按钮图片的长度
				'height'			: uploadify.height,//按钮图片的高度
				'buttonText'		: "上传", //按钮上的文字
				'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
				'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
				'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
				'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
				'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
				'fileTypeExts'		: "*.*;*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
				'onUploadSuccess'	: function(file, data, response) {//上传成功回调函数 有三个参数 file(可取文件名  注：上传前的文件名不是服务器端重写的文件名) data(服务器端写到response里面的信息) response(true,false)
					var data = $.parseJSON(data);
					var path = data.path;
					var newName = data.name;
					var oldName = data.originalName;
					var htmlText = $("#topicList").html();
					htmlText += "<div class='downlist'>";
					htmlText +="<img src='core/common/images/downloadico.png' />";
					htmlText +="<input type='hidden' value=\'"+path+"\'/>";
					htmlText +="<ul>";
					htmlText +="<li><input readonly title=\'"+oldName+"\' value=\'"+oldName+"\' />";
					htmlText +="</li><li>";
					htmlText +="<a href='javascript:void(0)' onclick='downLoad(this)'>下载</a>";
					htmlText +="<a href='javascript:void(0)' onclick='rename(this)'>重命名</a>";
					htmlText +="<a href='javascript:void(0)' onclick='deleteAttr(this)'>删除</a>";
					htmlText +="</li>";
					htmlText +="</ul>";
					htmlText +="</div>";
					$("#topicList").html(htmlText);
				},
			});
		}
		//删除上传的文件
		function deleteAttr(obj) {
			var obj = $(obj).parent().parent().parent();
			var path = $(obj).children().eq(1).val();
			$.ajax({
				type:"POST",
				 url: "<%=basePath%>appraisalAtt!deleteByFilePath.action",
				data:"filePath="+path,
				success: function(msg){
				     showTip(msg.result.msg,2000);
				     if(msg.result.success){
				    	 $(obj).remove();
				     }
				}
			});
		}
		//重命名
		function rename(obj){
			var parent = $(obj).parent().parent();
			renameObj = $(parent).children().eq(0).children().eq(0);
			var name = $(renameObj).val();
			name = encodeURIComponent(name);
			name = encodeURIComponent(name);
			fbStart('重命名','<%=basePath %>web/appraisal/appraisal_att_rename.jsp?name='+name,400,75);
		}
		
		function getTopic(){
			var topicPaths = '[';
			var obj = $("#topicList").children();
			$(obj).each(function (i){
				var child = $(this).children();
				var oldName = $(child).eq(2).children().eq(0).children().val();
				oldName = encodeURIComponent(oldName);
				oldName = encodeURIComponent(oldName);
				var str = "{\"filePath\" : \""+$(child).eq(1).val()+"\",";
				str += "\"fileName\" : \""+oldName+"\"}";
				topicPaths += str+",";
			})
			if(topicPaths.lastIndexOf(",") == topicPaths.length-1)
				topicPaths = topicPaths.substr(0,topicPaths.length-1);
			return topicPaths+"]";
		}
		
		//下载
		function downLoad(obj){
			var parent = $(obj).parent().parent();
			var path = $(parent).parent().children().eq(1).val();
			var name = $(parent).children().eq(0).children().eq(0).val();
			location.href="<%=BaseAction.rootLocation %>/core/resources/"+path+"?name="+name;
		}
	 
		function initForm(){
			$("#form1").validate({
				rules: {
					"appraisal.name":"required",
					"appraisal.appraisalType":"required",
					"appraisal.time":"required"
				},
				messages: {
					"appraisal.name":"请填写用户",
					"appraisal.appraisalType":"请选择考核类型",
					"appraisal.time":"请选择考核日期"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					$("#filePath").val(getTopic());
					$('#form1').ajaxSubmit({ 
				        dataType: 'json',	
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadList();parent.fb.end();", 2000);
				        	}
				        } 
				    });
				}
			});
		}
		
		function setAttName(attName){
			showTip("修改名称成功",2000);
			var oldName = $(renameObj).val();
			var pos = oldName.lastIndexOf(".");
			var suffix = oldName.substr(pos);
			$(renameObj).val(attName.name+suffix);
		}
		
	</script>
	<style>
		.mainClass{
			table-layout:fixed;
		}
		.attrdown{
			margin-top:5px;
			padding-left:6px;
			border:0px solid #e4e4e4;
			height:60px;
			overflow-y:auto;
	 		overflow-x: none;
		}
		.downlist,.downlist2{
			width:210px;
			padding:4px 10px;
		}
		
		.downlist img,.downlist2 img{
			float:left; 
			padding-right:5px; 
			position:relative; 
			top:2px;
		}
		.downlist a:link{
			color: #1f699d;
			padding-right: 10px;
		}
		.downlist2 a:link{
			color: #1f699d;
			padding-right: 10px;
		}
		
		.downlist input,.downlist2 input{
			border:0px;
		}
		.height60{
			height:60px;
		}
	</style>
</head>
<body>
<form action="<%=basePath %>appraisal!save.action" method="post" name="form1" id="form1">
<input id="filePath" name="filePath" type="hidden"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table class="mainClass" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
       		<td class="layertdleft100"><span class="psred">*</span>名称：</td>
     		<td class="layerright">
     			<input type="text" name="appraisal.name"  class="inputauto" value="" />
		    </td>
		</tr>
		<tr>
       		<td class="layertdleft100"><span class="psred">*</span>考核类型：</td>
     		<td class="layerright">
	     		<table border="0"  width="100%"  cellspacing="0" cellpadding="0">
			        <tr>
					   <enum:select name="appraisal.appraisalType" type="com.wiiy.synthesis.preferences.enums.AppraisalTypeEnums" />
			        </tr>
			    </table>
		    </td>
		</tr>
		<tr>
       		<td class="layertdleft100"><span class="psred">*</span>考核日期：</td>
     		<td class="layerright" colspan="2">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="time" type="text" name="appraisal.time" readonly="readonly" class="inputauto" onclick="showCalendar('time');"/></td>
			          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('time');" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			        </tr>
			    </table>
		    </td>
		</tr>
		<tr>
			<td class="layertdleft100">考核信息：</td>
			<td class="layerright" colspan="3" >
			   	<div id="topic">
					<input id="fileUpload" name="uploadify" type="file"/>
					<div class="attrdown">
						<div id="topicList"  style="display:block;">
						</div>
					</div>
				</div>
			 </td>
		</tr>		
       	<tr>
       		<td class="layertdleft100">备注信息：</td>
			<td class="layerright" colspan="3">
	    		<textarea name="appraisal.memo" class="inputauto" style="resize:none;height:80px;"></textarea>
			</td>
       	</tr>
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

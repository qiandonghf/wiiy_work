<%@page import="com.wiiy.pf.preferences.enums.ProcessTypeEnum,com.wiiy.core.taglib.DataDictSelectTaglib"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = BaseAction.rootLocation
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base
	href="<%=BaseAction.rootLocation%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet"
	type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet"
	type="text/css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet"
	type="text/css" />

<link rel="stylesheet" type="text/css"
	href="core/common/style/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css"
	href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>

<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="core/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	var oldAtt = null;
	$(document).ready(function() {
		initTip();
		initForm();
		initUploadify("fileUpload");
		//alert($("body").height());
	});

	function initForm() {
		$("#form1").validate({
			rules: {
				"processType.type":"required",
				"processType.formName":"required"
			},
			messages: {
				"processType.type":"请选择流程类别",
				"processType.formName":"请选择流程对应的表单"
			},
			errorPlacement : function(error, element) {
				showTip(error.html());
			},
			submitHandler : function(form) {
				var isTrue = $("#filePath").val() == '';
				$("#typeName").val($("select[name='processType.type'] option:selected").text());
				if(!isTrue){
					$(form).ajaxSubmit({
						dataType : 'json',
						success : function(data) {
							showTip(data.result.msg, 2000);
							if (data.result.success) {
								setTimeout("getOpener().refreshTree();parent.fb.end();", 2000);
							}
						}
					});
				}else
					showTip("请先上传资源文件!",2000);
			}
		});
	}

	function initUploadify(id){
	var root = '<%=BaseAction.rootLocation%>/';
		$("#" + id).uploadify({
			'auto'				: true, //是否自动开始 默认true
			'multi'				: false, //是否支持多文件上传 默认true
			'formData'			: {'module' : 'pf','directory' : uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
			'uploader'			: root + "core/upload.action",
			'swf'				: uploadify.swf,//上传组件swf
			'width'				: "80",//按钮图片的长度
			'height'			: uploadify.height,//按钮图片的高度
			'buttonText'		: "上传文件",
			'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
			'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
			'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
			'fileTypeDesc' 		: "*.zip;*.bar;*.bpmn;*.xml;",//选择文件对话框文件类型描述信息
			'fileTypeExts'		: "*.zip;*.bar;*.bpmn;*.xml;",//可上传上的文件类型
			'onUploadSuccess'	: onUploadSuccess
		//上传成功回调函数 有三个参数 file(可取文件名  注：上传前的文件名不是服务器端重写的文件名) data(服务器端写到response里面的信息) response(true,false)
		});
	}
	function onUploadSuccess(file, data, response) {
		showTip("文件读取成功!",2000);
		var data = $.parseJSON(data);
		var path = data.path;
		var newName = data.name;
		var oldName = data.originalName;
		if(oldAtt == null){
			oldAtt = path;
			setText(path,oldName,newName);
		}else
			deleteAtt(oldAtt,path,oldName,newName);
	}
	
	function setText(path,oldName,newName){
		$("#filePath").val(path);
		var htmlText = '';
		htmlText += '<div class="downlist">';
		htmlText +='<img src="core/common/images/downloadico.png" />';
		htmlText +='<input type="hidden" name="newName" value="'+newName+'"></input>';
		htmlText +='<ul>';
		htmlText +='<li><input readonly name="oldName" value="'+oldName+'" onblur="loseFocus(this)"/>';
		htmlText +='</li><li>';
		//htmlText +='<a href="javascript:void(0)" onclick="">下载</a>';
		//htmlText +='<a href="javascript:void(0)" onclick="rename(this)">重命名</a>';
		htmlText +='<a href="javascript:void(0)" onclick="deleteAttr(this)">删除</a>';
		htmlText +='</li>';
		htmlText +='</ul>';
		htmlText +='</div>';
		$("#topicList").html(htmlText);
	}
	
	function deleteAtt(old,path,oldName,newName){
		$.ajax({
			type:"POST",
			 url: "<%=basePath %>process!deleteByFilePath.action",
			data:"filePath="+old,
			success: function(msg){
			     if(msg.result.success){
			    	 $("#topicList").children().eq(0).remove();
			    	 setText(path,oldName,newName);
			    	 showTip("文件读取成功!",2000);
			     }
			}
		});
	}
	
	function deleteAttr(obj) {
		var obj = $(obj).parent().parent().parent();
		var path = $(obj).children().eq(1).val();
		$.ajax({
			type:"POST",
			 url: "<%=basePath %>process!deleteByFilePath.action",
			data:"filePath="+path,
			success: function(msg){
			     showTip(msg.result.msg,2000);
			     if(msg.result.success){
			    	 $(obj).remove();
			     }
			}
		});
	}
	
	function closeFrame(){
		var isTrue = $("#filePath").val() == '';
		if(!isTrue){
			$.ajax({
				type:"POST",
				 url: "<%=basePath %>process!deleteByFilePath.action",
				data:"filePath="+$("#filePath").val(),
				success: function(msg){
				     showTip(msg.result.msg,2000);
				     if(msg.result.success){
				    	 parent.fb.end();
				     }
				}
			});
		}
		parent.fb.end();
	}
</script>
<style>
	.layerright {
		word-break: break-all;
		width: 32%;
	}

	.layertdleft100 {
		white-space: nowrap;
		width: 18%;
	}
	.downlist{
		margin:4px 0px;
	}
	
	.downlist img{
		float:left; 
		padding-right:5px; 
		position:relative; 
		top:2px;
	}
	.downlist a:link{
		color: #1f699d;
		padding-right: 10px;
	}
	
	.downlist input{
		border:0px;
	}
	
	#hide{
		float:left;
		width:auto;
		display:none;
	}
</style>
</head>

<body style="background: #fff;">
	<form id="form1" name="form1" method="post" 
		action="<%=basePath%>process!deploy.action">
		<input name="filePath" type="hidden" id="filePath"/>
		<input id="typeName" name="processType.typeName" type="hidden" />
		<div class="basediv">
			<!-- <div class="titlebg">上传部署文件</div> -->
			<div class="divlays" style="margin: 0px;">
				<table width="100%" border="0" align="right" cellpadding="0"
					cellspacing="0">
					<tr>
						<td class="layertdleft100">应用类别:</td>
						<td style="padding-left:4px;">
							<dd:select name="processType.type" key="pf.0001"/>
						</td>
						<td class="layertdleft100">选择表单:</td>
						<td style="padding-left:4px;">
							<enum:select name="processType.formName" type="com.wiiy.pf.preferences.enums.ProcessTypeEnum"/>
						</td>
					</tr>
					<tr>
						<td class="layertdleft100">上传部署文件:</td>
						<td valign="top" style="padding:6px 4px;width:240px;">
							<input type="file" name="upload" id="fileUpload" />
						</td>
						<td id="topicList" colspan="2"></td>
					</tr>
				</table>
			</div>
			<div class="hackbox"></div>
		</div>
		<div class="buttondiv" style="margin-bottom: 2px;">
			<label>
				<input name="Submit" type="submit" class="savebtn" value="" /></label> <label>
				<input name="Submit2" type="button" class="cancelbtn" value="" onclick="closeFrame();" /></label>
		</div>
	</form>
</body>
</html>

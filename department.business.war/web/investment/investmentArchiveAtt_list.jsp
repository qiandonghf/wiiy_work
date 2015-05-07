<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
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
		
		<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
		<link rel="stylesheet" type="text/css" href="department.business/web/style/merchants.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
		
		<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.js"></script>
		<script type="text/javascript" src="core/common/js/tools.js"></script>
		<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
		<style>
			.uploadify-button {
				background: #fff /*url("../core/common/images/emailadd.gif")*/;
				background-position: left center;
				background-repeat: no-repeat;
				border: 1px solid #fff;;
				color: #1F699D;
				font: 12px Arial, Helvetica, sans-serif;
				/*padding-left:10px;*/
				position: relative;
				text-align: center;
				top: 1px;
				width: 100%;
			}
			.emailtop .leftemail ul li span {
				display: inline;
				float: left;
				height: 20px;
				line-height: 20px;
				padding-right: 3px;
				position: relative;
				top:-1px;
			}
			.uploadify:hover .uploadify-button {
				background-color: #fff;
				color: #1F699D;
				background-position: left center;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$('#investRight_height').css('height',getTabContentHeight()-8);
				$('#investRight_height2').css('height',getTabContentHeight()-12);
				$('#investRight_height3').css('height',getTabContentHeight()-46);
				initTip();
				initUploadify();
				checkAttrHidden();
			});
			function checkAttrHidden(){
				$(".agreementTR").each(function(){
					var length = $(this).find(".downlistleft").size();
					if(length==0) $(this).hide();
				});
			}
			function initUploadify(){
				$("#file1").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess1
				});
				$("#file2").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess2
				});
				$("#file3").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess3
				});
				$("#file4").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess4
				});
				$("#file5").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess5
				});
				$("#file6").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess6
				});
				$("#file7").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess7
				});
				$("#file8").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess8
				});
				$("#file9").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${investment.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
					'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
					'swf'				: uploadify.swf,//上传组件swf
					'width'				: 65,//按钮图片的长度
					'height'			: uploadify.height,//按钮图片的高度
					'buttonText'		: "上传", //按钮上的文字
					'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
					'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
					'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
					'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
					'fileTypeDesc'		: "支持格式:jpg/gif/jpeg/png/bmp/tif/txt/doc/zip/rar/pdf/docx",//选择文件对话框文件类型描述信息
					'fileTypeExts'		: "*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.tif;*.txt;*.doc;*.zip;*.rar;*.pdf;*.docx",//可上传上的文件类型
					'onUploadSuccess'	: uploadSuccess9
				});
			}
			function uploadSuccess1(file, data, response){
				uploadSuccess(file, data, response, "YYZZ");
			}
			function uploadSuccess2(file, data, response){
				uploadSuccess(file, data, response, "ZZJG");
			}
			function uploadSuccess3(file, data, response){
				uploadSuccess(file, data, response, "GSDJ");
			}
			function uploadSuccess4(file, data, response){
				uploadSuccess(file, data, response, "GSZC");
			}
			function uploadSuccess5(file, data, response){
				uploadSuccess(file, data, response, "FRGD");
			}
			function uploadSuccess6(file, data, response){
				uploadSuccess(file, data, response, "YZBG");
			}
			function uploadSuccess7(file, data, response){
				uploadSuccess(file, data, response, "FRDB");
			}
			function uploadSuccess8(file, data, response){
				uploadSuccess(file, data, response, "OTHER");
			}
			function uploadSuccess9(file, data, response){
				uploadSuccess(file, data, response, "MCHZ");
			}
			function uploadSuccess(file, data, response, type){
				alert(file+"              "+data+"              "+response+"              "+type);
				var file = $.parseJSON(data);
				var postData = {"investmentArchiveAtt.attType":type,"investmentArchiveAtt.investmentId":"${id}","investmentArchiveAtt.name":file.originalName,"investmentArchiveAtt.newName":file.path,"investmentArchiveAtt.size":file.size};
				$.post("<%=basePath%>investmentArchiveAtt!save.action",postData,function(callbackData){
					showTip(callbackData.result.msg);
					setTimeout("location.reload();",2000);
				});
			}
			function deleteFile(id){
				if(confirm("确定删除？")){
				$.post("<%=basePath %>investmentArchiveAtt!delete.action?id="+id,function(data){
					showTip(data.result.msg,2000);
					setTimeout("location.reload();",1000);
				});
				}
			}
			function downLoadFile(path,name){
				location.href="<%=BaseAction.rootLocation %>/core/resources/"+path+"?name="+name;
			}
		</script>
	</head>

	<body>
	<div class="basediv" id="investRight_height">
	<div class="divlays" id="investRight_height2" style="margin:0px;">
	 	<jsp:include page="common.jsp">
			<jsp:param value="2" name="index"/>
			<jsp:param value="${id}" name="investmentId"/>
			<jsp:param value="${investment.investmentStatus}" name="investmentStatus"/>
		</jsp:include> 
		<div id="investRight_height3" class="pm_msglist" style="overflow-y:auto; overflow-x:hidden;">
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>营业执照副本复印件</em><span><input id="file1" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 10px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
								<div style="display:block;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'YYZZ'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>组织机构代码证副本复印件</em><span><input id="file2" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'ZZJG'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>国税登记证、地税登记证副本复印件</em><span><input id="file3" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'GSDJ'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>公司章程复印件（股东已盖章或签字）</em><span><input id="file4" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'GSZC'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>法人股东的营业执照副本复印件、自然人股东身份证明复印件</em><span><input id="file5" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'FRGD'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>依法设立的验资机构出具的验资报告复印件</em><span><input id="file6" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'YZBG'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>法定代表人任职文件及身份证明复印件</em><span><input id="file7" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'FRDB'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>企业名称核准通知书</em><span><input id="file9" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'MCHZ'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10"></td>
					</tr>
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>其它</em><span><input id="file8" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.attType eq 'OTHER'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a><a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>investmentArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a></li>
										</ul>
									</div>
									</c:if>
									</c:forEach>
									<div class="hackbox"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
			</div>
		</div>
	</body>
</html>

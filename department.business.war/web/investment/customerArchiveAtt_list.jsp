<%@page import="com.wiiy.business.entity.CustomerArchiveAtt"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="java.text.Format"%>
<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
boolean service = false;
if(request.getAttribute("service")!=null)service=(Boolean)request.getAttribute("service");
Calendar cal = Calendar.getInstance();
cal.add(Calendar.MINUTE, -5);
Long tt = cal.getTime().getTime();
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
			<%if(!BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_upload")){ %>
			.uploadClass{
				display:none;
			}
			<%}%>
			<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_view")&&!BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
			.viewClass{
				display:none;
			}
			<%}%>
		</style>
		<script type="text/javascript">
			$(function(){
				$('#investRight_height3').css('height',getTabContentHeight()-46);
				initTip();
				initUploadify();
				checkAttrHidden();
				getDate();
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
				$("#file10").uploadify({
					'auto'				: true, //是否自动开始 默认true
					'multi'				: false, //是否支持多文件上传 默认true
					'formData'			: {'module':'business/${customer.code}','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
					'onUploadSuccess'	: uploadSuccess10
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
				uploadSuccess(file, data, response, "KHXKZ");
			}
			function uploadSuccess5(file, data, response){
				uploadSuccess(file, data, response, "GSZC");
			}
			function uploadSuccess6(file, data, response){
				uploadSuccess(file, data, response, "FRGD");
			}
			function uploadSuccess7(file, data, response){
				uploadSuccess(file, data, response, "YZBG");
			}
			function uploadSuccess8(file, data, response){
				uploadSuccess(file, data, response, "FRSFZ");
			}
			function uploadSuccess9(file, data, response){
				uploadSuccess(file, data, response, "FRDB");
			}
			function uploadSuccess10(file, data, response){
				uploadSuccess(file, data, response, "OTHER");
			}
			function uploadSuccess(file, data, response, type){
				var file = $.parseJSON(data);
				var postData = {"customerArchiveAtt.attType":type,"customerArchiveAtt.customerId":"${id}","customerArchiveAtt.name":file.originalName,"customerArchiveAtt.newName":file.path,"customerArchiveAtt.size":file.size};
				$.post("<%=basePath%>customerArchiveAtt!save.action",postData,function(callbackData){
					showTip(callbackData.result.msg);
					setTimeout("location.reload();",2000);
				});
			}
			function deleteFile(id){
				if(confirm("确定删除？")){
				$.post("<%=basePath %>customerArchiveAtt!delete.action?id="+id,function(data){
					showTip(data.result.msg,2000);
					setTimeout("location.reload();",1000);
				});
				}
			}
			function downLoadFile(path,name){
				location.href="<%=BaseAction.rootLocation %>/core/resources/"+path+"?name="+name;
			}
			function getDate(){
				<%Calendar c = Calendar.getInstance();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				c.add(Calendar.MINUTE, -5);
				String showTime = s.format(c.getTime());
				%>
				return "<%=showTime%>";
			}
		</script>
	</head>

	<body>
	<div class="basediv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td width="140" valign="top">
	<%if(service){ %>
		<jsp:include page="../customer_view_common2.jsp">
			<jsp:param value="10" name="index" />
			<jsp:param value="<%=request.getParameter("id")%>" name="customerId" />
		</jsp:include>
	<%}else{%>
		<jsp:include page="../customer_view_common.jsp">
			<jsp:param value="10" name="index" />
			<jsp:param value="<%=request.getParameter("id")%>" name="customerId" />
		</jsp:include>
	<%} %>
	</td>
	<td valign="top" style="background:#eaeced ">
						<div class="pm_view_right" style="width:700px;height: 432px;">
							<div class="basediv" style="margin: 0px;">
								<div class="titlebg">企业档案</div>
									<div class="divlays" style="margin:0px;">
	 	

	 <div id="investRight_height3" class="pm_msglist" style="overflow-y:auto; overflow-x:hidden;">
			 <div style=" margin:5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="merchantsTtitle">
								<ul>
									<li><em>营业执照</em><span class="uploadClass"><input id="file1" type="file"/></span></li>
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
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="yyzz"></c:set>
									<c:if test="${att.attType eq 'YYZZ'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
												<%
												Long yyzz=((Date)pageContext.getAttribute("yyzz")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<yyzz){ 
												%>	
												<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
												<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
												<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
												<%} %>
												<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>组织机构代码证</em><span class="uploadClass"><input id="file2" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="zzjg"></c:set>
									<c:if test="${att.attType eq 'ZZJG'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long zzjg=((Date)pageContext.getAttribute("zzjg")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<zzjg){ 
												%>	
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>税务登记证</em><span class="uploadClass"><input id="file3" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="gsdj"></c:set>
									<c:if test="${att.attType eq 'GSDJ'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long gsdj=((Date)pageContext.getAttribute("gsdj")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<gsdj){ 
												%>	
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>开户许可证</em><span class="uploadClass"><input id="file4" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="khxkz"></c:set>
									<c:if test="${att.attType eq 'KHXKZ'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long khxkz=((Date)pageContext.getAttribute("khxkz")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<khxkz){ 
												%>	
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>公司章程复印件</em><span class="uploadClass"><input id="file5" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="gszc"></c:set>
									<c:if test="${att.attType eq 'GSZC'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul>
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long gszc=((Date)pageContext.getAttribute("gszc")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<gszc){ 
												%>	
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>法人股东的营业执照</em><span class="uploadClass"><input id="file6" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="frgd"></c:set>
									<c:if test="${att.attType eq 'FRGD'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li  class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long frgd=((Date)pageContext.getAttribute("frgd")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<frgd){ 
												%>
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>验资报告</em><span class="uploadClass"><input id="file7" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="yzbg"></c:set>
									<c:if test="${att.attType eq 'YZBG'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long yzbg=((Date)pageContext.getAttribute("yzbg")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<yzbg){ 
												%>
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>法人身份证</em><span class="uploadClass"><input id="file8" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="frsfz"></c:set>
									<c:if test="${att.attType eq 'FRSFZ'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long frsfz=((Date)pageContext.getAttribute("frsfz")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<frsfz){ 
												%>
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>法定代表人任职文件</em><span class="uploadClass"><input id="file9" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != null }">
									<c:set value="${att.createTime }" var="frdb"></c:set>
									<c:if test="${att.attType eq 'FRDB'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long frdb=((Date)pageContext.getAttribute("frdb")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<frdb){ 
												%>
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
									<li><em>其它</em><span class="uploadClass"><input id="file10" type="file"/></span></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="agreementTR">
						<td >
							<div class="divlays" style="padding:2px 5px 0px 5px;">
								<div class="emaildown" style="margin-top:5px;border-bottom:1px solid #e4e4e4;border-left:1px solid #e4e4e4;border-right:1px solid #e4e4e4;">
									<c:forEach items="${result.value}" var="att">
									<c:if test="${att.createTime != '' }">
									<c:set value="${att.createTime }" var="other"></c:set>
									<c:if test="${att.attType eq 'OTHER'}">
									<div class="downlistleft">
										<img src="core/common/images/word.png" />
										<ul >
											<li><em class="lititle" title="${att.name}"><c:if test="${fn:length(att.name)>12}">${fn:substring(att.name,0,12)}...</c:if><c:if test="${fn:length(att.name)<=12}">${att.name }</c:if></em><!--<span>(<format:fileSize size="att.size"/>)</span>--></li>
											<li class="viewClass">
											<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt_edit")){ %>
											<%Long other=((Date)pageContext.getAttribute("other")).getTime();
												if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)||tt<other){%>
											<a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.name}');">下载</a>
											<a href="javascript:void(0)" onclick="fbSearch('重命名','<%=basePath %>customerArchiveAtt!edit.action?id=${att.id}',350,75);">重命名</a>
											<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<%} %>
											<%} %>
											</li>
										</ul>
									</div>
									</c:if>
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
				<div id="pager"></div>
				<div class="hackbox"></div>
			</div>
		</div>
	</td>
			</tr>
			</table>
		</div>
	</body>
</html>

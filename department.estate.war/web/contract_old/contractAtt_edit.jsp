<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>

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
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		initTip();
	});
	function checkForm(){
		if(notNull("name","名称") && notNull("type","文件类别")){
			$("#form1").ajaxSubmit({
		        dataType: 'json',		        
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout(function(){
		        			if("${param.id}" != '') getOpener().reloadEachList("contractFile");
		        			else getOpener().reloadList();
		        			parent.fb.end();
		        		},2000);
		        	}
		        } 
		    });
		}
	}
</script>
</head>

<body>
	<form action="<%=basePath %>contractAtt!update.action" method="post" name="form1" id="form1">
		<input type="hidden" value="${result.value.id}" name="contractAtt.id"/>
		<div class="basediv">
			<div class="divlays" style="margin:0px;">
   				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="layertdleft100"><span class="psred">*</span>名称：</td>
						<td class="layerright"><input id="name" name="contractAtt.name" type="text" class="inputauto" value="<format:fileName value="result.value.name"/>"/></td>
					</tr>
					<tr>
						<td class="layertdleft100"><span class="psred">*</span>文件类别：</td>
						<td class="layerright">
       						<enum:select id="type" name="contractAtt.type" checked="result.value.type" type="com.wiiy.common.preferences.enums.ContractAttTypeEnum" />
						</td>
					</tr>
				</table>
			</div>
			<div class="hackbox"></div>
		</div>
		<div class="buttondiv">
			<label><input name="Submit" type="button" class="savebtn" value="" onclick="checkForm()"/></label>
			<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
		</div>
	</form>
</body>
</html>

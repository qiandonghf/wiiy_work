<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
	});
	function checkForm(){
		if(notNull("name","分类名称")){
			$('#form1').ajaxSubmit({ 
		        dataType: 'json',
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("getOpener().refreshGroup();parent.fb.end();", 2000);
		        	}
		        } 
		    });
		}
	}
</script>
</head>
<body>
<form action="<%=basePath %>customerCategory!update.action" method="post" name="form1" id="form1">
<div class="basediv">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="layertdleft100">分类名称：</td>
				<td class="layerright">
					<input id="name" name="customerCategory.name" type="text" value="${result.value.name}" class="inputauto" />
					<input id="id" name="customerCategory.id" type="hidden" value="${result.value.id}" class="inputauto" />
				</td>
			</tr>
			<% if(BusinessActivator.getSessionUser(request).getAdmin().equals(BooleanEnum.YES)){ %>
			<tr>
				<td class="layertdleft100">拥有类型：</td>
				<td class="layerright"><enum:radio name="customerCategory.ownerEnum" checked="result.value.ownerEnum" styleClass="ownerEnum" type="com.wiiy.business.preferences.enums.OwnerEnum"/></td>
			</tr>
			<% } %>
		</table>
	</div>
	<div class="hackbox"></div>
</div>
<div class="buttondiv">
	<label><input name="Submit" type="button" class="savebtn" value="" onclick="checkForm()"/></label>
	<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end()"/></label>
</div>
</form>
</body>
</html>

<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=BaseAction.rootLocation %>/" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		
		<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
		<script type="text/javascript" src="core/common/js/jquery.js"></script>
		<script type="text/javascript" src="core/common/js/tools.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
		<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
		<script type="text/javascript">
			$(function(){
				initTip();
				ininName();
			});
			
			function ininName(){
				var name = "${param.name}";
				//字符解码
				name = decodeURIComponent(name);
				name = decodeURIComponent(name);
				var pos = name.lastIndexOf(".");
				if(pos != -1){
					name = name.substr(0,pos);
				}
				$("#name").val(name);
			}
			
			function checkForm(){
				if(notNull("name","名称")){
					var result = "${result}";
					if(result == ''){
						setTimeout(function(){
							var name = $("#name").val();
							var attName = {'name' : name};
							getOpener().setAttName(attName);
							parent.fb.end();
						},2000);
					}else{
						$("#form1").ajaxSubmit({
					        dataType: 'json',		        
					        success: function(data){
				        		showTip(data.result.msg,2000);
					        	if(data.result.success){
					        		setTimeout("getOpener().location.reload();parent.fb.end();",2000);
					        	}
					        } 
					    });
					}
				}
			}
		</script>

	</head>

	<body>
		<form action="<%=basePath %>appraisalAtt!update.action" method="post" name="form1" id="form1">
			<input type="hidden" value="${result.value.id}" name="appraisalAtt.id"/>
			<div class="basediv">
				<div class="divlays" style="margin:0px;">
    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="layertdleft100">名称：</td>
							<td class="layerright"><input id="name" name="appraisalAtt.name" type="text" class="inputauto" /></td>
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


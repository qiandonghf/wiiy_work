<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
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
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
		$(function(){
			initTip();
			initForm();
			});
		function initForm(){
			$("#form1").validate({
				rules: {
					"taskLog.executeTime":"required",
					"taskLog.executeUserName":"required",
					"taskLog.memo":"required"
				},
				messages: {
					"taskLog.executeTime":"请选择时间",
					"taskLog.executeUserName":"请输入执行人",
					"taskLog.memo":"请输入说明"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					$(form).ajaxSubmit({
				        dataType: 'json',		        
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadTaskLogList();parent.fb.end();", 2000);
				        	}
				        } 
				    });
				}
			});
		}
	</script>
</head>

<body>
<form action="<%=basePath %>taskLog!update.action" method="post" name="form1" id="form1">
	<input type="hidden" value="${result.value.id}" name="taskLog.id"/>
	<div class="basediv">
		<div class="divlays">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100">时间：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td >
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="150"><input value="<fmt:formatDate value="${result.value.executeTime}" pattern="yyyy-MM-dd"/>" id="time" name="taskLog.executeTime" readonly="readonly" type="text" class="inputauto" onclick="return showCalendar('time')"/></td>
											<td width="20" align="center"><img src="core/common/images/timeico.gif" style="position: relative; left:-1px;" width="20" height="22" onclick="return showCalendar('time')"/></td>
											<td>&nbsp; </td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">执行人：</td>
					<td ><input name="taskLog.executeUserName" type="text" class="input170" value="${result.value.executeUserName}"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">说明：</td>
					<td class="layerright" style="padding-bottom:2px;"><textarea name="taskLog.memo" style="height:100px;"class="textareaauto">${result.value.memo}</textarea></td>
				</tr>
			</table>
			<div class="hackbox"></div>
		</div>
	</div>
	<div class="buttondiv">
		<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
	</div>
</form>
</body>
</html>

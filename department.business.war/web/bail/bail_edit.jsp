 <%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
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
					"bail.name":"required",
					"bail.money":{
						"required":true,
						"positivenumber":true
					},
					"bail.time":"required",
				},
				messages: {
					"bail.name":"请输入单位名称",
					"bail.money":{
						"required":"请输入违约金额"
					},
					"bail.time":"请输入退租时间",
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
				        		setTimeout("getOpener().reloadList();parent.fb.end();", 2000);
				        	}
				        } 
				    });
				}
			});
		}
	</script>
</head>

<body>
<form action="<%=basePath %>bail!update.action" method="post" name="form1" id="form1">
<input type="hidden" id="id" name="bail.id" value="${result.value.id }"/>
<input type="hidden" id="businessContractId" value="${param.id}" name="bail.businessContractId"/>
<div class="basediv">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="layertdleft100">合同：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="350"><input id="businessContract" name="bail.businessContract" type="text" class="inputauto" readonly="readonly" /><input id=businessContractId name="bail.businessContractId" type="hidden"/></td>
							<td><img src="core/common/images/outdiv.gif" style="position:relative;left:-4px;" width="20" height="22" onclick="fbStart('房间选择','<%=BaseAction.rootLocation%>/common/room!select.action?roomIds='+$('#roomIds').val(),520,400);" style=" cursor:pointer;"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>单位名称：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input name="bail.name" type="text" value="${result.value.name }"  class="inputauto" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>违约金额：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input name="bail.money" type="text" class="inputauto"  value="<fmt:formatNumber value="${result.value.money}" pattern="#0.00"/>" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
							<td>&nbsp; </td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">面积：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input name="bail.area" type="text" class="inputauto"  value="<fmt:formatNumber value="${result.value.area}" pattern="#0.00"/>"  onkeyup="value=value.replace(/[^\d\.]/g,'')" /></td>
							<td>&nbsp;&nbsp;㎡ </td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>退租时间：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input readonly="readonly" id="time" name="bail.time" value="<fmt:formatDate value="${result.value.time }" pattern="yyyy-MM-dd" />" type="text" class="inputauto" onclick="return showCalendar('time')"/></td>
							<td width="20"><img src="core/common/images/timeico.gif" style="position: relative;left:-1px;" width="20" height="22" onclick="return showCalendar('time')"/></td>
							<td align="center">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">是否入住：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="80"><enum:radio checked="result.value.enterStatus" name="bail.enterStatus" type="com.wiiy.commons.preferences.enums.BooleanEnum"/></td>
							<td>&nbsp; </td>
						</tr>
					</table>
				</td>
			</tr> 
			<tr>
				<td class="layertdleft100">租赁状态：</td>
				<td class="layerright">
					<enum:select checked="result.value.rentState" type="com.wiiy.business.preferences.enums.ContractRentStatusEnum" name="bail.rentState"/>
				</td>
			</tr> 
			<tr>
				<td class="layertdleft100">原因：</td>
				<td class="layerright"><textarea name="bail.memo" class="inputauto" style="height:70px;resize:none;">${result.value.memo}</textarea></td>
			</tr>
		</table>
	</div>
	<div class="hackbox"></div>
</div>
<div class="buttondiv">
	<label><input name="Submit" type="submit" class="savebtn" value="" /></label>
	<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
</div>
</form>
</body>
</html>
 
<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

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
				"customerName":"required",
				"contect.name":{
					required: true
				},
				"contect.email":"email"
			},
			messages: {
				"customerName":"请选择企业",
				"contect.name":{
					required: "请输入姓名"
				}
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
			        		setTimeout("parent.refresh();parent.fb.end();", 2000);
			        	}
			        } 
			    });
			}
		});
	}
	function setSelectedCustomer(customer){
		$("#customerId").val(customer.id);
		$("#customerName").val(customer.name);
	}
</script>
</head>

<body>
<form action="<%=basePath %>contect!save.action" method="post" name="form1" id="form1">
	<div class="basediv">
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>企业：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="403"><input id="customerId" name="contect.customerId" type="hidden" /><input id="customerName" name="customerName" class="inputauto" readonly="readonly" onclick="fbStart('选择企业','<%=basePath %>customer!select.action',520,400);"/></td>
								<td><img src="core/common/images/outdiv.gif" style="position:relative;left:-5px;"width="20" height="22" onclick="fbStart('选择企业','<%=basePath %>customer!select.action',520,400);"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>姓名：</td>
					<td class="layerright"><input id="name" name="contect.name" type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100">性别：</td>
					<td class="layerright"><enum:radio name="contect.gender" type="com.wiiy.commons.preferences.enums.GenderEnum"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">手机：</td>
					<td class="layerright"><input name="contect.mobile" type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100">是否主要联系人：</td>
					<td class="layerright"><enum:radio name="contect.main" type="com.wiiy.commons.preferences.enums.BooleanEnum"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">职位：</td>
					<td class="layerright"><input id="position" name="contect.position" type="text" class="inputauto" /></td>
				</tr>
			</table>
		</div>
		<div class="hackbox"></div>
	</div>
	<div class="apptab" id="tableid">
		<ul>
		<%int flag=-1;
		if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contectWay_add")){
			flag++;%>
		
			<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">联系方式</li>
			<%} %>
			<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_extendMessage_add")){flag++; %>
			<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">扩展信息</li>
			<%} %>
			<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contectOther_add")){flag++; %>
			<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">其它信息</li>
			<%} %>
		</ul>
	</div>
	<%int flag2=-1;
	if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contectWay_add")){
		flag2++;%>	
		<div class="basediv tabswitch" style="margin-top:0px;<%if(flag2!=0) {%>display:none;<%} %>" id="textname">
			<div class="divlays" style="margin:0px;">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="layertdleft100">Email：</td>
						<td class="layerright"><input name="contect.email" type="text" class="inputauto"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">电话：</td>
						<td class="layerright"><input name="contect.phone" type="text" class="inputauto"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">MSN：</td>
						<td class="layerright"><input name="contect.msn" type="text" class="inputauto"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">QQ：</td>
						<td class="layerright"><input name="contect.qq" type="text" class="inputauto"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">传真：</td>
						<td class="layerright"><input name="contect.fax" type="text" class="inputauto"/></td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_extendMessage_add")){
			flag2++;%>
		<div class="basediv tabswitch" style="margin-top:0px; <%if(flag2!=0) {%>display:none;<%} %>" id="textname">
			<div class="divlays" style="margin:0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="layertdleft100">邮编：</td>
						<td class="layerright"><input name="contect.zipcode" type="text" class="inputauto"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">家庭地址：</td>
						<td class="layerright"><input name="contect.homeAddr" type="text" class="inputauto"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">家庭电话：</td>
						<td class="layerright"><input name="contect.homePhone" type="text" class="inputauto"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">生日：</td>
						<td class="layerright">
							<table border="0" cellspacing="0" cellpadding="10">
								<tr>
									<td width="200"><input id="birthDay" name="contect.birthDay" type="text" class="inputauto" readonly="readonly" onclick="showCalendar('birthDay');"/></td>
									<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="showCalendar('birthDay');"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="layertdleft100">爱好：</td>
						<td class="layerright"><input name="contect.favorite" type="text" class="inputauto"/></td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contectOther_add")){
			flag2++;%>
		<div class="basediv tabswitch" style="margin-top:0px;<%if(flag2!=0) {%>display:none;<%} %>" id="textname">
			<div class="divlays" style="margin:0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="layertdleft100">备注：</td>
						<td class="layerright"><textarea name="contect.memo"  class="textareaauto"  style="height:128px;"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
		<div class="buttondiv">
			<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
			<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end()"/></label>
		</div>
</form>
</body>
</html>
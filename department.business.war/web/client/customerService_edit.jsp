<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
				"customerService.customerId":"required",
				"customerService.typeId":"required",
				"customerService.serviceName":"required",
				"customerService.contect":"required",
				"customerService.startDate":"required",
				"customerService.endDate":"required",
				"customerService.phone":"required",
				"customerService.description":"required"
			},
			messages: {
				"customerService.customerId":"请选择企业",
				"customerService.typeId":"请选择服务类型",
				"customerService.serviceName":"请填写联系单名称",
				"customerService.contect":"请填写联系人",
				"customerService.startDate":"请选择受理开始日期",
				"customerService.endDate":"请选择受理开始日期",
				"customerService.phone":"请填写联系电话",
				"customerService.description":"请填写情况说明"
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
				if($("#endDate").val()!=null && $("#startDate").val()>$("#endDate").val()){
					showTip("受理开始日期不能大于受理结束日期",2000);
					return;
				}
				$(form).ajaxSubmit({
			        dataType: 'json',		        
			        success: function(data){
		        		showTip(data.result.msg,2000);
			        	if(data.result.success){
			        		setTimeout(function(){
			        			getOpener().location.href = '<%=basePath%>customerService!list.action?id='+data.id;
			        			parent.fb.end();
			        		},2000);
			        	}
			        } 
			    });
			}
		});
	}
	
	function setSelectedCustomer(customer){
		$("#customerId").val(customer.id);
		$("#customerName").val(customer.name);
		loadContectsByCustomerId(customer.id);
	}
	
	function setSelectedOrg(selectedOrg) {
    	$("#user_org_id").val(selectedOrg.id);
    	$("#user_org_name").val(selectedOrg.name);
    	loadUsersByOrgId(selectedOrg.id);
    }
</script>
</head>

<body>
<form action="<%=basePath %>customerService!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="customerService.id" value="${result.value.id }"/>
	<div class="basediv">
		<div class="titlebg">基本信息</div>
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>企业名称：</td>
					<td class="layerright" style="width:250px;">
								${result.value.customerName }
					</td>
					<td class="layertdleft100"><span class="psred">*</span>联系单名称：</td>
			        <td class="layerright">
			           ${result.value.serviceName}
			        </td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>联系人：</td>
			        <td class="layerright">
			           <input name="customerService.contect" type="text" class="inputauto" style="width:98%;" value="${result.value.contect}"/>
			        </td>
					<td class="layertdleft100"><span class="psred">*</span>受理开始日期：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
					          <td><input name="customerService.startDate" value="<fmt:formatDate value="${result.value.startDate}" pattern="yyyy-MM-dd"/>" readonly="readonly" type="text" class="inputauto" id="startDate" onclick="return showCalendar('startDate');" /></td>
					          <td width="20" align="center"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startDate');" /></td>
					        </tr>
						</table>
					</td>
				 </tr>
				 <tr>
				 	<td class="layertdleft100"><span class="psred">*</span>联系电话：</td>
				 	<td class="layerright"><input name="customerService.phone" value="${result.value.phone }" type="text" class="inputauto" style="width:98%;" /></td>
				 	<td class="layertdleft100"><span class="psred">*</span>受理结束日期：</td>
				 	<td class="layerright">
				 		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				 			<tr>
					          <td><input name="customerService.endDate" value="<fmt:formatDate value="${result.value.endDate}" pattern="yyyy-MM-dd"/>" readonly="readonly" type="text" class="inputauto" id="endDate" onclick="return showCalendar('endDate');" /></td>
					          <td width="20" align="center"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('endDate');" /></td>
					        </tr>
			 			</table>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td class="layertdleft100"><span class="psred">*</span>受理人：</td>
				 	<td class="layerright">
						${result.value.user.realName}
			        </td>
			 		<td class="layertdleft100"><span class="psred">*</span>状态：</td>
			 		<td width="30%" class="layerright">
			 			<enum:select type="com.wiiy.business.preferences.enums.CustomerServiceStatusEnum" name="customerService.status" checked="result.value.status"/>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">服务结果：</td>
			 		<td class="layerright">
			 			<enum:select type="com.wiiy.business.preferences.enums.CustomerServiceResultEnum" name="customerService.result" checked="result.value.result"/>
				 	</td>
				 	<td class="layertdleft100"><span class="psred">*</span>服务类型：</td>
					<td class="layerright">
						<dd:select key="business.0024" name="customerService.typeId" checked="result.value.typeId"/>
            		</td>
				</tr>
			 	<tr>
			 		<td class="layertdleft100"><span class="psred">*</span>情况说明：</td>
				 	<td colspan="3" class="layerright" style="padding-bottom:2px;"><textarea name="customerService.description" class="inputauto"  style="height:50px;resize:none;">${result.value.description }</textarea></td>
			 	</tr>
			 	<tr>
			 		<td class="layertdleft100">企业意见及建议：</td>
				 	<td colspan="3" class="layerright"><textarea name="customerService.suggest" class="inputauto"  style="height:50px;resize:none;">${result.value.suggest }</textarea></td>
			 	</tr>
		 	</table>
	 	</div>
	 	<div class="hackbox"></div>
	</div>
	<div class="buttondiv">
		<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end()"/></label>
	</div>
</form>
</body>
</html>
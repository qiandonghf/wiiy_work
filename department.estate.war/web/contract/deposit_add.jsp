<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript">
		$(function(){
			initTip();
			initForm();
			});
		function initForm(){
			$("#form1").validate({
				rules: {
					"deposit.type":"required",
					"deposit.amount":{
						"required":true,
						"positivenumber":true
					}
				},
				messages: {
					"deposit.type":"请选择费用类型",
					"deposit.amount":{
						"required":"请输入金额"
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
				        		setTimeout("getOpener().reloadDepositList();parent.fb.end();", 2000);
				        	}
				        } 
				    });
				}
			});
		}
	</script>
</head>

<body>
<form action="<%=basePath %>deposit!save.action" method="post" name="form1" id="form1">
	<input type="hidden" value="${result.value.id}" name="deposit.contractId"/>
	<input type="hidden" value="${result.value.customerId}" name="deposit.customerId"/>
	<div class="basediv">
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100">合同名称：</td>
					<td class="layerright">${result.value.name}</td>
				</tr>
				<tr>
					<td class="layertdleft100">企业名称：</td>
					<td class="layerright">${result.value.customer.name}</td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>保证金类型：</td>
					<td class="layerright"><enum:select id="type" type="com.wiiy.business.preferences.enums.DepositTypeEnum" name="deposit.type"/></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>金额：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100"><input name="deposit.amount" type="text" class="inputauto" /></td>
								<td>&nbsp; 元 </td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">备注：</td>
					<td class="layerright"><textarea name="deposit.memo" style="height:100px;resize:none;" class="inputauto"></textarea></td>
				</tr>
			</table>
		</div>
		<div class="hackbox"></div>
	</div>
	<div class="buttondiv">
		<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
	</div>
</form>
</body>
</html>

<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
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
				"bill.invoiceCode":"required"
			},
			messages: {
				"bill.invoiceCode":"请填写开票号"
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
	function getCalendarScrollTop(){
		return $("#scrollDiv").scrollTop();
	}
</script>
</head>

<body>
<form action="<%=basePath %>bill!invoice.action" method="post" name="form1" id="form1">
<input type="hidden" name="bill.id" value="${result.value.id }"/>
	<div class="basediv">
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100">企业名称：</td>
					<td class="layerright" colspan="3">${result.value.customerName}</td>
				</tr>
				<tr>
					<td class="layertdleft100">税务登记证：</td>
					<td class="layerright">${result.value.taxNumberG}</td>
					<td class="layertdleft100">滞纳金：</td>
					<td class="layerright"><span class="redweight">￥<fmt:formatNumber value="${result.value.penalty}" pattern="#0.00"/></span></td>
				</tr>
				<tr>
					<td class="layertdleft100">出帐日期：</td>
					<td class="layerright"><fmt:formatDate value="${result.value.checkoutTime}" pattern="yyyy-MM-dd"/></td>
					<td class="layertdleft100">实际支付时间：</td>
					<td class="layerright"><fmt:formatDate value="${result.value.payTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
			</table>
		</div>
		<div style="margin:0 4px; border:1px solid #ccc;">
		    <table border="1" bordercolor="#ccc" cellpadding="0" cellspacing="0" width="100%" style="border-collapse:collapse">
		    	<tr>
		        	<th width="100">费用类型</th>
		            <th width="150">计费时间</th>
		            <th width="90">出帐日期</th>
		            <th width="100">金额</th>
		        </tr>
		        <tr>
		           <td align="center">${result.value.typeName}</td>
		           <td align="center"><fmt:formatDate value="${result.value.feeStartTime}" pattern="yyyy-MM-dd"/> – <fmt:formatDate value="${result.value.feeEndTime}" pattern="yyyy-MM-dd"/></td>
		           <td align="center"><fmt:formatDate value="${result.value.checkoutTime}" pattern="yyyy-MM-dd"/></td>
		           <td align="right"><fmt:formatNumber value="${result.value.money}" pattern="#0.00"/></td>
		        </tr>
		        <tr>
		        	<td colspan="3" align="right">合计：</td>
		           <td align="right"><fmt:formatNumber value="${result.value.money}" pattern="#0.00"/></td>
		        </tr>
		    </table>
		    </div>
		<div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>开票号：</td>
					<td class="layerright" colspan="3"><input type="text" style="width: 99%;" class="inputauto" name="bill.invoiceCode"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">票据号：</td>
					<td class="layerright">
						<enum:radio name="bill.invoice" type="com.wiiy.commons.preferences.enums.BooleanEnum" checked="YES"/>
					</td>
					<td class="layertdleft100">开票日期：</td>
					<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="133"><input id="invoiceTime" readonly="readonly" value="<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd"/>" name="bill.invoiceTime" type="text" class="data inputauto" onclick="showCalendar('invoiceTime');"/></td>
							<td width="20" align="center"><img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer;" onclick="showCalendar('invoiceTime');"></td>
						</tr>
					</table>
				</td>
				</tr>
			</table>
		</div>		
		<div class="hackbox"></div>
	</div>
	<div class="buttondiv">
		<label><input name="Submit" type="submit" class="rightbtn" value=""/></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
	</div>
</form>
</body>
</html>
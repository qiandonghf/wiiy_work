<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>

	<script type="text/javascript">
	 	var selecteds = "";
	 	var clicked = null;
		$(function(){
			initTip();
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='billRent.paid']");
			var p = $(audit).parent();
			var c = $(p).find("span");
			if($(audit).val() == 'NO'){
				$(c).text("未付款");
				$(audit).attr("checked",false);
			}
			else{
				$(c).text("已付款");
				$(audit).attr("checked","checked");
			} 
		}
		
		function changeState(obj){
			if($(obj).attr("checked"))
				$(obj).val("YES");
			else
				$(obj).val("NO");
			var p = $(obj).parent();
			var c = $(p).find("span");
			if($(obj).val() == 'NO')$(c).text("未审核");
			else $(c).text("已审核");
		}
	</script>
</head>
<body>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	<tr>
		    <td class="layertdleft100">结算计划：</td>
			<td class="layerright">${result.value.code }</td>
       		<td class="layertdleft100" >预计结算：</td>
       		<td class="layerright" >${billRentDto.planFee }</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100" >预计结算日期：</td>
	      	<td class="layerright" ><fmt:formatDate value="${billRentDto.planPayDate }" pattern="yyyy-MM-dd"/></td>
		    <td class="layertdleft100">合同名称：</td>
			<td class="layerright" >${billRentDto.contractName}</td>
		</tr>
       	<tr>
   			<td class="layertdleft100">合同金额：</td>
			<td class="layerright" ><fmt:formatNumber value="${billRentDto.contractAmount }" pattern="0.00" /></td>
       		<td class="layertdleft100" >结算日期：</td>
	      	<td class="layerright" ><fmt:formatDate value="${result.value.settlementDate }" pattern="yyyy-MM-dd" /></td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">金额：</td>
       		<td class="layerright" ><fmt:formatNumber value="${result.value.settlementFee }" pattern="0.00"/></td>
     		<td class="layertdleft100">结算性质：</td>
       		<td class="layerright">${result.value.settlementType.title }</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">收付方向：</td>
       		<td class="layerright">${result.value.payment.title }</td>
		    <td class="layerright" colspan="2">
				<label><input disabled="disabled" name="billRent.paid" type="checkbox" value="${result.value.paid }" /><span>未付款</span></label>
	      	</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">结算方式：</td>
       		<td class="layerright">${result.value.settlement.title }</td>
       	</tr>
       	<tr>
       	    <td class="layertdleft100">备注：</td>
	    	<td class="layerright" colspan="3" style="padding-bottom:3px;">
	    		<textarea name="billRent.memo" class="inputauto" readonly="readonly" style="resize:none;height:40px;color:#666;padding-left:0px;border:0px;">${result.value.memo }</textarea>
	    	</td>
       	</tr>

  	</table>
  </div>
<div class="hackbox"></div>
</div>
<div class="" style="height:5px;">
</div>
</body>
</html>

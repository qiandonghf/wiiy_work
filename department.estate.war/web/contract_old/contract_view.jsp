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
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript">
		$(function(){
			initChange();
		});
		function initChange(){
			var audit = $("input[name='contract.audit']");
			var finished = $("input[name='contract.finished']");
			changeState(audit);
			changeState(finished);
		}
		function changeState(obj){
			if($(obj).attr("checked"))
				$(obj).val("YES");
			else
				$(obj).val("NO");
			if($(obj).attr("name") == 'contract.finished'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未完成");
				else $(c).text("已完成");
			}else if($(obj).attr("name") == 'contract.audit'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未审核");
				else $(c).text("已审核");
			}
		}
	</script>
	<style>
		.mainClass{
			table-layout:fixed;
		}
	</style>
</head>
<body>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table class="mainClass" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
       		<td class="layertdleft100">合同名称：</td>
		    <td class="layerright">${result.value.name }</td>
       		<td class="layertdleft100">合同编号：</td>
			<td class="layerright">${result.value.code }</td>
       	</tr>
       	<tr>
       	    <td class="layertdleft100">供应商：</td>
			<td class="layerright">${result.value.supplier.name }</td>
		    <td class="layertdleft100" >客户：</td>
			<td class="layerright">${result.value.customer.name }</td>
		</tr>
		<tr>
		    <td class="layertdleft100" >所属项目：</td>
			<td class="layerright">${result.value.project.name }</td>
			<td class="layertdleft100">主要联系人：</td>
			<td class="layerright">${result.value.contact.realName }</td>
       	</tr>
       	<tr>
			<td class="layertdleft100" >责任人：</td>
			<td class="layerright">${result.value.duty.realName }</td>
		   	<td class="layertdleft100" >经手人：</td>
			<td class="layerright">${result.value.handling.realName }</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">合同类别：</td>
			<td class="layerright">${result.value.category.dataValue }</td>
			<td class="layertdleft100">合同形式：</td>
		    <td class="layerright">${result.value.contractForm.title }</td>
       	</tr>
       	<tr>
		    <td class="layertdleft100">合同状态：</td>
			<td class="layerright">${result.value.contractStatus.title }</td>
		    <td class="layertdleft100">货币种类：</td>
       		<td class="layerright">${result.value.currencyType.dataValue }</td>
       	</tr>
       	<tr>
		    <td class="layertdleft100">收付方式：</td>
       		<td class="layerright">${result.value.payment.title }</td>
       		<td class="layertdleft100">结算方式：</td>
       		<td class="layerright">${result.value.settlement.title }</td>	
    	</tr>
    	<tr>
      		<td class="layertdleft100" >合同生效时间：</td>
     		<td class="layerright"><fmt:formatDate value="${result.value.startDate}" pattern="yyyy-MM-dd"/></td>
		    <td class="layertdleft100" >合同结束时间：</td>
     		<td class="layerright"><fmt:formatDate value="${result.value.endDate}" pattern="yyyy-MM-dd"/></td>
      	</tr>
    	<tr>
			<td class="layertdleft100" >合同签订日期：</td>
	      	<td class="layerright"><fmt:formatDate value="${result.value.signDate}" pattern="yyyy-MM-dd"/></td>
		    <td class="layertdleft100" >登记日期：</td>
	      	<td class="layerright"><fmt:formatDate value="${result.value.receiveDate}" pattern="yyyy-MM-dd"/></td>
       	</tr>
       	<tr>
    	   	<td class="layertdleft100">总金额：</td>
	      	<td class="layerright"><fmt:formatNumber value="${result.value.contractAmount}" pattern="0.00" /></td>
    	    <td class="layertdleft100">预计花费：</td>
	      	<td class="layerright"><fmt:formatNumber value="${result.value.predictCost}" pattern="0.00" /></td>
    	</tr>
    	<tr>
    	    <td class="layertdleft100">实际花费：</td>
    		<td class="layerright"><fmt:formatNumber value="${result.value.actualCost}" pattern="0.00" /></td>
    		<td class="layerright" colspan="2">
				<label><input name="contract.audit" disabled="disabled" <c:if test="${result.value.audit eq 'YES'}">checked="checked"</c:if> type="checkbox" value="NO" /><span>未审核</span></label>
				<label><input name="contract.finished" disabled="disabled" <c:if test="${result.value.finished eq 'YES'}">checked="checked"</c:if>type="checkbox" onclick="changeState(this);" value="NO" /><span>完成标志</span></label>
		   		<label><input name="contract.published" disabled="disabled" <c:if test="${result.value.published eq 'YES'}">checked="checked"</c:if>type="checkbox" onclick="changeState(this);" value="NO" />公开标志</label>
	      	</td>
    	</tr>
       	<tr>
	    	<td class="layertdleft100">合同简介：</td>
	    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
	    		<textarea name="contract.introduction" readonly="readonly" class="inputauto" style="padding-left:0px;border:0px;color:#666;resize:none;height:40px;">${result.value.introduction }</textarea>
	    	</td>
	    </tr>
       	<tr>
       		<td class="layertdleft100">备注信息：</td>
			<td class="layerright"  colspan="3">
	    		<textarea name="contract.memo" readonly="readonly" class="inputauto" style="padding-left:0px;border:0px;color:#666;resize:none;height:40px;">${result.value.memo }</textarea>
			</td>
       	</tr>
  	</table>
  </div>
<div class="hackbox"></div>
</div>
</body>
</html>

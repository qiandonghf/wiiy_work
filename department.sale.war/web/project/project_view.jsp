<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	var selected = "";
	$(function(){
		initChange();
	});
	
	function initChange(){
		var audit = $("#audit");
		var finished = $("#finished");
		var published = $("#published");
		changeState(audit);
		changeState(finished);
		if($(published).val() == 'YES'){
			$(published).attr("checked",true);
		}
	}
	
	function changeState(obj){
		if($(obj).val() == 'YES'){
			var p = $(obj).parent();
			var c = $(p).find("span");
			$(c).text("已完成");
			$(obj).attr("checked",true);
		}
	}
</script>
</head>
<body>
<div class="basediv">
	<c:set value="${result.value }" var="project"></c:set>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="140" valign="top">
				<jsp:include page="../project_view_common.jsp">
					<jsp:param value="0" name="index"/>
					<jsp:param value="${result.value.id}" name="projectId"/>
				</jsp:include>
			</td>
			<td valign="top">
				<div class="pm_view_right" style="width:590px;height:auto;">
					<div class="basediv" style="margin:0px;">
						<div class="titlebg">基本信息</div>
						<div class="divlays" style="margin:0px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
					       		<td class="layertdleft100">项目名称：</td>
								<td class="layerright" colspan="3">${project.name }</td>
					       	</tr>
					       	<tr>
					       		<td class="layertdleft100">甲方：</td>
								<td class="layerright" width="33%">${project.supplierName }</td>
					       		<td class="layertdleft100">乙方：</td>
								<td class="layerright" width="33%">${project.customerName }</td>
					       	</tr>
					       	<tr>
					       		<td class="layertdleft100">项目编号：</td>
								<td class="layerright">${project.code }</td>
					       		<td class="layertdleft100">项目简称：</td>
							    <td class="layerright">${project.abbreviation }</td>
					       	</tr>
					       	<tr>
					       		<td class="layertdleft100">项目状态：</td>
								<td class="layerright" >${project.status.title }</td>
							    <td class="layertdleft100">重要程度：</td>
								<td class="layerright">${project.priority.title }</td>
					    	</tr>
					    	<tr>
					    		<td class="layertdleft100">主要联系人：</td>
								<td class="layerright">${project.manager.realName }</td>
							    <td class="layertdleft100">经手人：</td>
								<td class="layerright" >${project.handling.realName }</td>
					    	</tr>
					       	<tr>
					       	 	<td class="layertdleft100">货币种类：</td>
					       		<td class="layerright" >${project.currencyType.dataValue }</td>
					       		<td class="layertdleft100">金额：</td>
						      	<td class="layerright"><fmt:formatNumber value="${project.amount}" pattern="0.00" /></td>
					       	</tr>
					       	<tr>
					    		<td class="layertdleft100">预计花费：</td>
						      	<td class="layerright"><fmt:formatNumber value="${project.expectedCost}" pattern="0.00" /></td>
					    		<td class="layertdleft100">实际花费：</td>
						      	<td class="layerright"><fmt:formatNumber value="${project.actualCost}" pattern="0.00" /></td>
					    	</tr>
					    	<tr>
					    		<td class="layertdleft100">收付方式：</td>
					       		<td class="layerright">${project.payment.title }</td>
					       		<td class="layertdleft100">结算方式：</td>
					       		<td class="layerright">${project.settlement.title }</td>
					    	</tr>
					    	<tr>
					      		<td class="layertdleft100">项目开始日期：</td>
					     		<td class="layerright"><fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd"/></td>
							    <td class="layertdleft100">项目结束日期：</td>
					     		<td class="layerright"><fmt:formatDate value="${project.endDate}" pattern="yyyy-MM-dd"/></td>
					      	</tr>
					    	<tr>
					    		<td class="layertdleft100">项目签订日期：</td>
						      	<td class="layerright"><fmt:formatDate value="${project.signDate}" pattern="yyyy-MM-dd"/></td>
						      	<td class="layerright" colspan="2">
									<label><input id="audit" value="${project.audit}" disabled="disabled" type="checkbox" /><span>未审核</span></label>
									<label><input id="finished" value="${project.finished}" disabled="disabled" type="checkbox"  /><span>未完成</span></label>
							   		<label><input id="published" value="${project.published}" disabled="disabled" type="checkbox" />公开标志</label>
						      	</td>
					       	</tr>
					       	<tr>
						    	<td class="layertdleft100">项目简介：</td>
						    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
						    		<textarea readonly="readonly" class="inputauto" style="padding-left:0px;border:0px;color:#666;resize:none;height:40px;">${project.introduction }</textarea>
						    	</td>
						    </tr>
					       	<tr>
					       		<td class="layertdleft100">备注信息：</td>
								<td class="layerright"  colspan="3">
						    		<textarea readonly="readonly" class="inputauto" style="padding-left:0px;border:0px;color:#666;resize:none;height:40px;">${project.memo }</textarea>
								</td>
					       	</tr>
						</table>
						</div>
						<div class="hackbox"></div>
					</div>
					<div class="hackbox"></div>
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>


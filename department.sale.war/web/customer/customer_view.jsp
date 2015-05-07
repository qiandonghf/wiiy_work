<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
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
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
</head>

<body>
	<div id="scrollDiv" style=" position:relative;">			
	<div class="basediv">
		<div class="titlebg">客户信息</div>
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100">姓名：</td>
					<td width="35%" class="layerright">${result.value.name }</td>
					<td class="layertdleft100">性别：</td>
					<td class="layerright">${result.value.gender.title }</td>
				</tr>
				<tr>
					<td class="layertdleft100">客户类型：</td>
					<td class="layerright">${result.value.customerType.title}</td>
					<td class="layertdleft100">客户等级：</td>
					<td class="layerright">${result.value.level.title}</td>
				</tr>
				<tr>
					<td class="layertdleft100">年龄：</td>
					<td class="layerright">${result.value.age }</td>
					<td class="layertdleft100">证件号：</td>
					<td class="layerright">${result.value.idNO }</td>
				</tr>
				<tr>
					<td class="layertdleft100">手机：</td>
					<td class="layerright">${result.value.mobile }</td>
					<td class="layertdleft100">固话：</td>
					<td class="layerright">${result.value.phone }</td>
				</tr>
				<tr>
					<td class="layertdleft100">email：</td>
					<td class="layerright">${result.value.email }</td>
					<td class="layertdleft100">婚否：</td>
					<td class="layerright">${result.value.marriage}</td>
				</tr>
				<tr>
					<td class="layertdleft100">学历：</td>
					<td class="layerright">${result.value.education.dataValue } </td>
					<td class="layertdleft100">职业：</td>
					<td class="layerright">${result.value.profession.dataValue }</td>
				</tr>
				<tr>
					<td class="layertdleft100">家庭年收入：</td>
					<td class="layerright">${result.value.familyIncome.dataValue}</td>
					<td class="layertdleft100">客户区域：</td>
					<td class="layerright">${result.value.clientArea.dataValue}</td>
				</tr>
				<tr>
					<td class="layertdleft100">讯息来源：</td>
					<td class="layerright">${result.value.source.dataValue}</td>
					<td class="layertdleft100">户型需求：</td>
					<td class="layerright">${result.value.huxing.dataValue}</td>
				</tr>
				<tr>
				  <td class="layertdleft100">可接受价格区间：</td>
				  <td class="layerright">${result.value.acceptPrice }</td>
				  <td class="layertdleft100">家庭住址</td>
			      <td  class="layerright" ><div style="height: 50px; overflow-x: hidden;overflow-y: auto;word-break:break-all; word-wrap:break-word;">${result.value.address }</div></td>
		     	</tr>
		     	<tr>
					<td class="layertdleft100">抗性分析：</td>
					<td class="layerright">${result.value.resistance.dataValue}</td>
					<td class="layertdleft100">置业顾问：</td>
					<td class="layerright">
						${result.value.user.realName }
					</td>
				</tr>
		     	<tr>
					<td class="layertdleft100">地段：</td>
					<td class="layerright">${result.value.section.title}</td>
					<td class="layertdleft100">户型：</td>
					<td class="layerright">${result.value.houseType.title}</td>
				</tr>
		     	<tr>
					<td class="layertdleft100">价格：</td>
					<td class="layerright">${result.value.cost.title}</td>
					<td class="layertdleft100">环境：</td>
					<td class="layerright">${result.value.environment.title}</td>
				</tr>
		     	<tr>
					<td class="layertdleft100">需求级别：</td>
					<td class="layerright">${result.value.demandLevel.title}</td>
					<td class="layertdleft100">购房动机：</td>
					<td class="layerright">${result.value.motivation.dataValue }</td>
				</tr>
				
				<tr>
					<td class="layertdleft100">企业标签：</td>
					<td colspan="3" class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="customerdiv">
								<div style=" width:auto; height:50px; overflow-x:hidden; overflow-y:scroll">
									<ul id="labelUl">
									<c:forEach items="${result.value.labelRefs}" var="labelRef">
										<li onmouseout="$(this).find('span').hide()" onmouseover="$(this).find('span').show()">${labelRef.customerLabel.name}<input type="hidden" name="ids" class="customerLabelId" value="${labelRef.customerLabel.id}"/><span style="display: none;" onclick="$(this).parent().remove()"></span></li>
									</c:forEach>
									</ul>
								</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="hackbox"></div>
	</div>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link href="core/common/style/calendar.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/js/calendar.js"></script>
<script type="text/javascript" src="core/common/js/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/js/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
</head>

<body>
<div class="basediv">
	<div class="titlebg" >商品申购列表</div>
	<div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
 		<tr>
	    	 <td class="layertdleft100" style="width:130px;">申请表名称：</td>
	     	 <td style="width:200px;">${result.value.name}</td>
	    	 <td class="layertdleft100" style="width:130px;">申请人：</td>
	     	 <td style="width:200px;">${result.value.requisitioner.realName}</td>
	     	 <td class="layertdleft100" style="width:130px;">申请时间：</td>
	     	 <td style="width:200px;"><fmt:formatDate value="${result.value.applyTime}" pattern="yyyy-MM-dd"/></td>
    	</tr>
    	</table>	
	</div>

	<div class="titlebg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
			<tr>
				<td style="width:50px;text-align:center;">序号</td>		
				<td style="width:100px;text-align:center;">名称</td>		
				<td style="width:80px;text-align:center;">单价金额(￥)</td>		
				<td style="width:80px;text-align:center;">数量</td>		
				<td style="width:80px;text-align:center;">单位</td>		
				<td style="width:120px;text-align:center;">物品用途</td>
				<td style="width:20px;text-align:center;" align="center"></td>		
			</tr>
		</table>
	</div>
	<div  style="width:100%;height:133px; overflow:auto; background-color: #f4f4f4;">
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
 		<c:forEach items="${configList }" var="supplys" varStatus="i">
			<tr>
				<td style="width:50px;text-align:center;">${i.count }</td>		
				<td style="width:100px;text-align:center;">${supplys.supplyPurchase.supply.name}</td>		
				<td style="width:80px;text-align:center;">${supplys.supplyPurchase.price}</td>		
				<td style="width:80px;text-align:center;">${supplys.supplyPurchase.amount}</td>		
				<td style="width:80px;text-align:center;">${supplys.supplyPurchase.supply.unit}</td>		
				<td style="width:120px;text-align:center;">${supplys.supplyPurchase.usages}</td>
			</tr>
		</c:forEach>
  	</table>
	</div>
</div>
</body>
</html>

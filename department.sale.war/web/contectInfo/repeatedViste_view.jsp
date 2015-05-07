<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %> 
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
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/calendar/calendar.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>

</head>

<body>
<div class="basediv">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
	  	  <td class="layertdleft100">回访人员：</td>
      	  <td class="layerright"> 
      	  	   ${result.value.receiver.realName }
      	  </td>
      </tr>
      <tr>
      		<td class="layertdleft100">回访时间：</td>
     		<td class="layerright">
	     		<fmt:formatDate value="${result.value.startTime }" pattern="yyyy-MM-dd"/>
		    </td>
      	</tr>
      	<tr>
	        <td class="layertdleft100">回访情况：</td>
	        <td class="layerright">
		       <div style="height:60px;overflow-x: hidden;overflow-y: auto;word-break:break-all; word-wrap:break-word;">
		        	${result.value.memo }
		       </div>
		    </td>
      	</tr>
      	<tr>
	      	<td class="layertdleft100">下次回访提醒：</td>
	      	<td class="layerright">
	      		<fmt:formatDate value="${result.value.remindTime }" pattern="yyyy-MM-dd"/>
	      	</td>
      </tr>
    </table>
	<div class="hackbox"></div>
</div>
</body>
</html>

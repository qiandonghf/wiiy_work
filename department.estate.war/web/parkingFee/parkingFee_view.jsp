<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
	      	<td class="layertdleft100">车牌号:</td>
	        <td class="layerright">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	       <tr>
	          <td>
	          	${result.value.licenseNo }
	          </td>
	       </tr>
	       </table>
	       </td>
       </tr>
	</table>
 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
	      	<td class="layertdleft100">车位:</td>
	        <td class="layerright">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	       <tr>
	          <td>
	          	${result.value.parkingManage }
	          </td>
	       </tr>
	       </table>
	       </td>
       </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td class="layertdleft100">单位/个人：</td>
	      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="193">
	         	 ${result.value.name }
	          </td>
 	        </tr>
	      </table>
	      </td>
	    </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td class="layertdleft100">收费人：</td>
	     	 <td class="layerright">
	      	   ${result.value.toller }
	        </td>  
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100">金额：</td>
      <td class="layerright"><fmt:formatNumber value="${result.value.money}" pattern="#0.00"/></td>
    </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100">开始时间： </td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
         	 <td width="120"><fmt:formatDate value="${result.value.startDate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
          </tr>
        </table></td>
        </tr>
        </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td class="layertdleft100">结束时间：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<td width="150"><fmt:formatDate value="${result.value.endDate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
          </tr>
        </table></td>
    </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100">备注：</td>
      <td  class="layerright">
      	<div style="height: 100px;overflow-x: hidden;overflow-y: auto">${result.value.memo}</div> 
      </td>
    </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
</body>
</html>

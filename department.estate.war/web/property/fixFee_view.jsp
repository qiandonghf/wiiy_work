<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.estate.preferences.enums.PropertyFixStatusEnum"%>
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
<script type="text/javascript">
	 
	 
</script>
<style>
	#mainTable{
		table-layout:fixed;
	}
</style>
</head>

<body>
<div class="basediv">
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>维修人员：</td>
      <td><span class="layerright">
        ${result.value.maintainer}
      </span></td>
      <td class="layertdleft100"><span class="psred">*</span>维修人员所属单位：</td>
      <td><span class="layerright">
        ${result.value.maintainerOrg}
      </span></td>
    </tr>
    <tr>
    <td class="layertdleft100">人工费用：</td>
      <td class="layerright">
      	<fmt:formatNumber value="${result.value.laborCosts}" pattern="#0.00"/></td>
      <td class="layertdleft100">材料费用：</td>
      <td class="layerright">
      	<fmt:formatNumber value="${result.value.materialCosts}" pattern="#0.00"/></td>
    </tr>
    
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>维修结果：</td>
      <td colspan="5" class="layerright" style="padding-bottom:2px;">
      	<div style="height:50px;"  class="textareaauto">${result.value.result}</div>
      </td>
    </tr>
    <tr>
      <td class="layertdleft100">整改意见：</td>
      <td colspan="5" class="layerright" style="padding-bottom:2px;"> 
        <div style="height:40px;" class="textareaauto" >${result.value.rectification}</div>
      </td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td colspan="5" class="layerright" style="padding-bottom:2px;"> 
        <div style="height:40px;" class="textareaauto" >${result.value.memo}</div>
       </td>
    </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
</body>
</html>

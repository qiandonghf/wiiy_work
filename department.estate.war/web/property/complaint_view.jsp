<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
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
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/calendar.css" rel="stylesheet" type="text/css" />

</head>

<body>
<form action="" method="post" enctype="multipart/form-data" name="form1" id="form1">
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<div class="titlebg">投诉信息</div>
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100">投诉人：</td>
      <td class="layerright" width="180">${result.value.name }</td>
      <td class="layertdleft100">联系电话：</td>
      <td class="layerright">${result.value.tel }</td>
    </tr>
    <tr>
      <td class="layertdleft100">投诉对象：</td>
      <td class="layerright">${result.value.complaintObject }</td>
      <td class="layertdleft100">投诉日期：</td>
      <td class="layerright"><fmt:formatDate value="${result.value.complaintTime }" pattern="yyyy-MM-dd" /></td>
    </tr>
    <tr>
      <td class="layertdleft100">企业名称：</td>
      <td class="layerright">${result.value.customerName }</td>
      <td class="layertdleft100">重要程度：</td>
      <td class="layerright">${result.value.importance.title }</td>
    </tr>
    <tr>
      <td class="layertdleft100">投诉内容：</td>
      <td colspan="5" class="layerright">
	      <label>
	        	<textarea name="complaint.content" readonly="readonly" 
	        		style="height:60px;resize:none;color:#666;border:0px;padding-left:0px;" 
	        		class="inputauto">${result.value.content }</textarea>
	      </label>
      </td>
    </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<div style="height: 5px;">
  </div>
</form>
</body>
</html>

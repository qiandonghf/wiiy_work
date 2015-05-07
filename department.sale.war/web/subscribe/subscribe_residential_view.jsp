<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ page import="com.wiiy.commons.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>普通文章</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/layertable.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
</head>

<body>
<div class="basediv">
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
		<td class="layertdleft100">客户名称：</td>
		<td class="layerright">
				${result.value.name }
		</td>
    	<td class="layertdleft100">线索：</td>
		<td class="layerright">
			${result.value.contectInfoName }
		</td>
    </tr>
    <tr>
    	<td class="layertdleft100">销售顾问：</td>
	       <td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							${result.value.user.realName }
						</td>
					</tr>
				</table>
		   </td>
		   <td class="layertdleft100">房间名称：</td>
	   <td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							${result.value.roomName }
						</td>
					</tr>
				</table>
		   </td>
    </tr>
    <tr>
       <td class="layertdleft100">房间编号：</td>
       <td class="layerright">
       	 ${result.value.roomCode }
       </td>
	   <td class="layertdleft100">面积：</td>
       <td class="layerright">
         ${result.value.saleArea }
	   </td>
    </tr>
    <tr>
       <td class="layertdleft100">单价：</td>
       <td class="layerright">
         ${result.value.saleUnit }
       </td>
	    <td class="layertdleft100">总价：</td>
       <td class="layerright">
         ${result.value.salePrice}
       </td>
    </tr>
    <tr>
	   <td class="layertdleft100">移动电话：</td>
	   <td class="layerright">
	     ${result.value.mobile }
	   </td>
	   <td class="layertdleft100">工作电话：</td>
	   <td class="layerright">
	     ${result.value.phone }
	   </td>
	</tr>
	<tr>
	  <td class="layertdleft100">出租/出售：</td>
      <td class="layerright">
      	${result.value.rentStatus.title }
      </td>
      <td class="layertdleft100">是否同步到房间：</td>
      <td class="layerright">
      	${result.value.whetherRoom.title }
      </td>
	</tr>					
    <tr>
      <td class="layertdleft100">预购日期： </td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><fmt:formatDate value="${result.value.registratTime }" pattern="yyyy-MM-dd" /></td>
          </tr>
        </table></td>
      <td class="layertdleft100">终止日期：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><fmt:formatDate value="${result.value.endTime }" pattern="yyyy-MM-dd" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
       <td class="layertdleft100">预订原因：</td>
       <td class="layerright" colspan="3" style="padding-top:1px;">
     		<div style="height:80px;margin-top:2px;" class="textareaauto">${result.value.reason}</div>
       </td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td  class="layerright" colspan="3" style="padding-top:1px;">
      	<div style="height:80px;margin-top:2px;" class="textareaauto">${result.value.memo}</div></td>
    </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<!--basediv-->
<!--//basediv-->
</body>
</html>

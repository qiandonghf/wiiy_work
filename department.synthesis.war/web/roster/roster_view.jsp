<%--@elvariable id="enums" type="java.util.Map<String, Map<String, String>>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction"%>
<%@ page import="com.wiiy.commons.preferences.enums.EntityStatus"%>
<%@ page import="com.wiiy.core.entity.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/calendar/calendar.css" rel="stylesheet" type="text/css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
</head>
<body>
<div>
	<!--titlebg-->
	<div class="layertitle">基本信息</div>
	<!--//titlebg-->
	<!--divlay-->
	<div class="divlays">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="50%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="layertdleft">用户名：</td>
              <td>${user.username}</td>
            </tr>
            <tr>
              <td class="layertdleft">真实姓名：</td>
              <td>${user.realName }</td>
            </tr>
            <tr>
              <td class="layertdleft">管理员：</td>
              <td>${user.admin.title }</td>
            </tr>
            <tr>
              <td class="layertdleft">出生年月：</td>
              <td>${user.birthday}</td>
            </tr>
            <tr>
              <td class="layertdleft">移动电话：</td>
              <td>${user.mobile}</td>
            </tr>
          </table>
          </td>
          <td valign="top">
          <table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
		<tr>
          <td valign="top">
          <table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
			<tr>
              <td width="96">
              <c:if test="${user.imagery != null}">
              <img id="user_imagery_img" src="${user.imagery}" width="107" height="110" class="touxian" />
              </c:if>
              <c:if test="${user.imagery == null}">
              <img id="user_imagery_img" src="core/common/images/topxiao.gif" width="107" height="110" class="touxian" />
              </c:if>
              </td>
            </tr>
          </table>
          </td>
            </tr>
          </table>
          </td>
        </tr>
      </table>
	  
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="50%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="layertdleft">固定电话：</td>
              <td>${user.telephone}</td>
            </tr>
            <tr>
              <td class="layertdleft">Email：</td>
              <td>${user.email}</td>
            </tr>
            <tr>
              <td class="layertdleft">MSN：</td>
              <td>${user.msn}</td>
            </tr>

          </table></td>
          <td valign="top"><table width="99%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td class="layertdleft">性别：</td>
              <td><label>${user.gender.title }</label></td>
            </tr>
            <tr>
              <td class="layertdleft">所属部门：</td>
              <td>${user.org.name}</td>
            <tr>
              <td class="layertdleft">状态：</td>
              <td style="padding-left:6px;"><label>${user.entityStatus.title }</label></td>
            </tr>
          </table></td>
        </tr>
      </table>
	</div>
</div>
</body>
</html>

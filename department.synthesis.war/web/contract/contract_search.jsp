<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
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
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
var userType = 0;
	$(function(){
		initTip();
	});
	function setSelectedUser(user){
		$("#contactId").val(user.id);
		$("#contact").val(user.realName);
	}
	function search(){
		parent.fb.end();
		parent.search(getSearchFilters());
	}
	function selectUser(obj){
		userType=obj;
		fbStart('选择用户','/core/user!selectSelf.action',520,400);
	}
</script>
</head>

<body style=" background:#fff">
	<div class="basediv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100">合同名称：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="10">
							<tr>
								<td width="310"><search:input dataType="string" field="contract.name" op="cn" inputClass="inputauto"/></td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">合同编号：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="10">
							<tr>
								<td width="310"><search:input dataType="string" field="contract.code" op="eq" inputClass="inputauto"/></td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">联系人：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="288"><search:choose dataType="long" field="contarct.contactName" op="in"><input id="contactId" type="hidden" class="data inputauto"/></search:choose><input id="contact" readonly="readonly" type="text" class="inputauto" onclick="selectUser()" /></td>
								<td><img style="cursor:pointer;" onclick="selectUser()" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</div>
<div class="buttondiv">
	<input name="Submit" type="button" class="search_cx" value="" onclick="search()"/>
	<input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/>
</div>
</body>
</html>
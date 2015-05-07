<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="parkmanager.association/web/style/assciation.css" />
	
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>

	<script type="text/javascript">
		$(function(){
			initTip();
			initForm();
		});
		function initForm(){
			$("#form1").validate({
				rules: {
					"seal.name":"required"
				},
				messages: {
					"seal.name":"请填写印章名称"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					$(form).ajaxSubmit({
				        dataType: 'json',
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadList();parent.fb.end()", 2000);
				        	}
				        } 
				    });
				}
			});
		}
		function setSelectedOrg(selectedOrg){
			$("#orgId").val(selectedOrg.id);
			$("#orgName").val(selectedOrg.name);
		}
	</script>
</head>
<body>
<form action="<%=basePath %>seal!update.action" method="post" name="form1" id="form1">
<input type="hidden" value="${result.value.id }" name="seal.id"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	<tr>
		    <td class="layertdleft100"><span class="psred">*</span>名称：</td>
			<td class="layerright">
				<input id="name" name="seal.name" type="text" value="${result.value.name }" class="inputauto"  style="width: 70%" />
			</td>
       	</tr>
       	<tr>
		    <td class="layertdleft100">印章类别：</td>
			<td class="layerright">
				<dd:select key="syn.0001" name="seal.sealType" checked="result.value.sealType.dataValue" />
			</td>
       	</tr>
       	<tr>
            <td class="layertdleft">所属部门：</td>
            <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
					<td width="50%"><input id="orgId" name="seal.orgId" value="${result.value.orgId }" type="hidden" /><input id="orgName" name="orgName" value="${result.value.org.name }" class="inputauto" readonly="readonly" onclick="fbStart('选择部门','<%=BaseAction.rootLocation %>/core/org!select.action',520,400);"/></td>
					<td><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="fbStart('选择部门','<%=BaseAction.rootLocation %>/core/org!select.action',520,400);"/></td>
				</tr>
              </table></td>
        </tr>
  	</table>
  </div>
<div class="hackbox"></div>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
 </div>
</form>
</body>
</html>

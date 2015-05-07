<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.commons.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
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
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initForm();
	});

	function initForm(){
		$("#form1").validate({
			rules:{
				"parkingManage.parkingId":"required"
			},
			messages: {
				"parkingManage.parkingId":"请输入车位号"
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
					$('#form1').ajaxSubmit({ 
				        dataType: 'json',		        
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadGarage();fb.end();", 2000);
				        	}
				        } 
				    });
			}
		});
	}
	 
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=basePath%>parkingManage!update.action">
<input type="hidden" name="parkingManage.id" value="${result.value.id}" />
<input type="hidden" name="parkingManage.garageId" value="${result.value.garageId}" />
<div class="basediv">
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>车位号：</td>
      <td><input id="parkingId" name="parkingManage.parkingId" value="${result.value.parkingId}" type="text" class="input170" /></td>
    </tr>
    <tr>
      <td class="layertdleft100">状态：</td>
      <td class="layerright"><label><enum:select id="statusType" type="com.wiiy.estate.preferences.enums.ParkingManageEnum" checked="result.value.status" name="parkingManage.status" /></label></td>
    </tr>
    <tr id="carLicenseId" style="display:none;">
      <td class="layertdleft100"><span class="psred">*</span>车牌号：</td>
      <td><input id="carLicenseName" name="parkingManage.carLicense" value="${result.value.carLicense }" type="text" class="input170" /></td>
    </tr>
     <%--  <tr id="startTimeIds" style="display:none;">
        <td class="layertdleft100"><span class="psred">*</span>开始时间：</td>
	    <td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	         	<td width="120">
	         		<input name="startTime" readonly="readonly" type="text" class="inputauto" id="startTime" onclick="return showCalendar('startTime');" />
	         		<input id="startTimeId" name="parkingManage.startTime" type="hidden" value="test"/>
	         	</td>
	         	<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime');" /></td>
	         	<td>
					<select id="startHour">
					<c:forEach begin="0" end="23" var="s">
						<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
					</c:forEach>
					</select>&nbsp;时
					<select id="startMinute">
						<c:forEach begin="0" end="59" var="s" step="1">
							<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
						</c:forEach>
					</select>&nbsp;分
				</td>
	         	<td></td>	
	        </tr>
	    </table></td>
      </tr> --%>
    <tr>
      <td class="layertdleft100">描述：</td>
      <td class="layerright"><label>
        <textarea id="descriptionId" name="parkingManage.description" style="height:140px" value="${result.value.description }" class="textareaauto"></textarea>
      </label></td>
    </tr>
  </table>
</div>

    
	<div class="hackbox"></div>
</div>
<div class="buttondiv">
  <label><input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>
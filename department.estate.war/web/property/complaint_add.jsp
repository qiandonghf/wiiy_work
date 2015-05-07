<%@page import="com.wiiy.commons.util.DateUtil"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initForm();
	});
	
	function getCalendarScrollTop(){
		return $("#scrollDiv").scrollTop();
	} 
	
	function initForm(){
		$("#form1").validate({
			rules:{
				"complaint.name":"required",
				"complaint.complaintObject":"required",
				"complaint.content":"required"
			},
			messages: {
				"complaint.name":"请填写投诉人",
				"complaint.complaintObject":"请填写投诉对象",
				"complaint.content":"请填写投诉内容"
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
			        		var type = $("#type").val();
			        		if(type == 'index'){
			        			var title = "所有投诉";
			        			var icon = "/department.synthesis/web/images/icon/sealdj_min.png";
			        			var url = "<%=BaseAction.rootLocation%>/department.estate/complaint!listAll.action";
			        			setTimeout("getOpener().addPropertyTab('"+title+"','"+icon+"','"+url+"');parent.fb.end();",2000);
			        		}else{	
			        			setTimeout("getOpener().reloadList();parent.fb.end();", 2000);
			        		}
			        	}
			        } 
			    });
			}
		});
	}
	
	 function setSelectedCustomer(customer){
		$("#customerName").val(customer.name);
		$("#customerId").val(customer.id);
	}
	 
</script>
</head>

<body>
<form action="<%=basePath %>complaint!save.action" method="post" name="form1" id="form1">
<input type="hidden" value="${param.form }" id="type"/>
<!--basediv-->
<div class="basediv">
	<div class="titlebg">投诉信息</div>
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>投诉人：</td>
      <td class="layerright"><input name="complaint.name" type="text" class="inputauto" /></td>
      <td class="layertdleft100">联系电话：</td>
      <td class="layerright"><input name="complaint.tel" type="text" class="inputauto" /></td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>投诉对象：</td>
      <td class="layerright"><input name="complaint.complaintObject" type="text" class="inputauto" /></td>
      <td class="layertdleft100">投诉日期：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="100">
          	<input readonly="readonly" id="complaintTime" name="complaint.complaintTime" type="text" class="inputauto" value="<%=DateUtil.format(new Date())%>" onclick="return showCalendar('complaintTime', 'y-mm-dd');"/></td>
          <td>
          	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('complaintTime', 'y-mm-dd');"/></td>
        </tr>
       </table>
      </td>
    </tr>
    <tr>
      <td class="layertdleft100">企业名称：</td>
      <td class="layerright"><input name="complaint.customerName" type="text" class="inputauto" /></td>
      <td class="layertdleft100">重要程度：</td>
      <td class="layerright">
      	<enum:select name="complaint.importance" type="com.wiiy.common.preferences.enums.ProjectPriorityEnum" />
      </td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>投诉内容：</td>
      <td colspan="3" class="layerright"><label>
        <textarea name="complaint.content" style="height:60px;resize:none;" class="inputauto"></textarea>
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

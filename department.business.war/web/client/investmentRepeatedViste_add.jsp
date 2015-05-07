<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %> 
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript">
	var remindTime = "${result.value.remindTime}";
	$(document).ready(function() {
		remindTime = trim("${result.value.remindTime}");
		remindTime = remindTime=="" ? getDate() : remindTime;
		initTip();
		initForm();
		initDate();
		$("#timeClick").change(function(){
			if($(this).attr("checked")){
				$(".visittime").show();
				$("input[name='investmentRepeatedViste.remindTime']").val(remindTime);
			}else{
				$("input[name='investmentRepeatedViste.remindTime']").val("");
				$(".visittime").hide();
			}
		});
		if("${result.value.remindTime}" == ''){
			$("#timeClick").attr("checked","checked");
		}
	});

	function getDate(){
		<%Calendar c = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DATE, 3);
		String remindTime = s.format(c.getTime());
		%>
		return "<%=remindTime%>";
	}
	
	function initForm(){
		$("#form1").validate({
			rules: {
				/* "investmentRepeatedViste.name":"required",
				"investmentRepeatedViste.demand":"required",
				 "investmentRepeatedViste.startTime":"required" */
			},
			messages: {
				/* "investmentRepeatedViste.name":"请输入姓名",
				"investmentRepeatedViste.demand":"请输入需求", 
				"investmentRepeatedViste.startTime":"请选择有效开始时间"*/
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
				if($("#endTime").val()<$("#startTime").val()){
					showTip("有效日期开始时间不能小于有效日期结束时间",3000); 
					return;
				}
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
	
	function setSelectedUser(user){
		$("#receiverId").val(user.id);
		$("#receiver").val(user.realName);
	}
	
	function initDate(){
		<% c = Calendar.getInstance();
		 s = new SimpleDateFormat("yyyy-MM-dd");
		 String startTime = s.format(c.getTime());
		 c.add(Calendar.DATE, 3);
		 remindTime = s.format(c.getTime());
		%>
		var startTime = '<%=startTime%>';
		var remindTime = '<%=remindTime%>';
		$("#startTime").val(startTime);
		$("#remindTime").val(remindTime);
	}
	
</script>
</head>

<body>
<form action="<%=basePath %>investmentRepeatedViste!save.action" method="post" name="form1" id="form1">
<input type="hidden" name="investmentRepeatedViste.investmentContectInfoId" value="${param.infoId }"/>
<div class="basediv">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <!-- <tr>
	      <td class="layertdleft100"><span class="psred">*</span>姓名：</td>
	      <td class="layerright" colspan="3"> 
	          <input id="name" name="investmentRepeatedViste.name" type="text" class="inputauto"  /> 
	      </td>
      </tr> -->
      <tr>
		  <!-- <td class="layertdleft100">联系电话：</td>
		  <td class="layerright"><input name="investmentRepeatedViste.phone" type="text" class="inputauto" /></td> -->
	  	  <td class="layertdleft100">回访人员：</td>
      	  <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td>
	          	<input id="receiverId" name="investmentRepeatedViste.receiverId" value="<%=BusinessActivator.getSessionUser(request).getId() %>" readonly="readonly" type="hidden"/>
	          	<input id="receiver" value="<%=BusinessActivator.getSessionUser(request).getRealName() %>" readonly="readonly" type="text" class="inputauto" onclick="fbStart('选择接待人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	          <td width="20" align="center">
	          	<img src="core/common/images/outdiv.gif" width="20" height="22" style="position: relative;left:-4px;" onclick="fbStart('选择接待人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	        </tr>
      	</table></td>
      </tr>
      <tr>
      		<td class="layertdleft100">回访时间：</td>
     		<td class="layerright">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="startTime" name="investmentRepeatedViste.startTime" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('startTime');"/></td>
			          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('startTime');" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			        </tr>
			    </table>
		    </td>
	   <!-- <td class="layertdleft100"><span class="psred">*</span>回访结束时间：</td>
     		<td class="layerright">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="endTime" name="investmentRepeatedViste.endTime" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('endTime')"/></td>
			          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('endTime')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			        </tr>
			    </table>
		    </td>
      	</tr>
      	<tr>
		    <td class="layertdleft100">产业：</td>
		    <td class="layerright" colspan="3"><input name="investmentRepeatedViste.product" type="text" class="inputauto" /></td>
	  	</tr> 
        <tr>
	        <td class="layertdleft100"><span class="psred">*</span>需求：</td>
	        <td class="layerright" colspan="3" style="padding-bottom:2px;"><textarea id="demand" name="investmentRepeatedViste.demand" style="height:50px;" class="textareaauto"></textarea></td> -->
      	</tr> 
      	<tr>
	        <td class="layertdleft100">回访情况：</td>
	        <td class="layerright" colspan="3" style="padding-bottom:2px;"><textarea name="investmentRepeatedViste.memo" style="height:65px;" class="textareaauto"></textarea></td>
      	</tr>
      	<tr>
      	<td class="layertdleft100">下次回访提醒：</td>
      	<td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      		<tr>
      		  <td width="5%"><input type="checkbox" id="timeClick"/></td>
	          <td width="10" class="visittime">
	          	 <input name="investmentRepeatedViste.remindTime" readonly="readonly" type="text" class="inputauto" id="remindTime" onclick="return showCalendar('remindTime');" />
	          </td>
	          <td width="20" class="visittime" ><img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer;position:relative;left:-1px;" onclick="return showCalendar('remindTime');" /></td>
			</tr>
      	</table>
      	</td>
      </tr>
    </table>
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<div class="buttondiv">
  <label><input name="Submit" type="submit" class="savebtn" value=""/></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ page import="com.wiiy.commons.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
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

<script type="text/javascript" >
	$(document).ready(function() {
		initTip();
		initForm();
		initDate();
	});
	
	function initDate(){
		<%Calendar c = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = s.format(c.getTime());	
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		String endTime = s.format(c.getTime());
		%>
		var registratTime = '<%=startTime%>';
		var endTime = '<%=endTime%>';
		$("#registratTime").val(registratTime);
		$("#endTime").val(endTime);
		
	}
	
	function initForm(){
		$("#form1").validate({
			rules: {
				"userName":"required",
			    "registrationBook.registratTime":"required",
			    "registrationBook.endTime":"required",
			    "registrationBook.reason":"required"
			},
			messages: {
				"userName":"请选择招商顾问",
				"registrationBook.registratTime":"请选择预购日期",
				"registrationBook.endTime":"请选择终止日期",
				"registrationBook.reason":"请填写预订原因"
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			 
			submitHandler: function(form){
				if(notNull("registratTime","预购日期") &&　notNull("endTime","终止日期")){
					if($("#registratTime").val()>$("#endTime").val){
						showTip("终止日期不能小于预购日期",5000);
						return;
					}
				}
					$(form).ajaxSubmit({
				        dataType: 'json',		        
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		var type = $("#type").val();
				        		if(type == 'index'){
				        			var title = "预订登记";
				        			var icon = "/department.synthesis/web/images/icon/sealdj_min.png";
				        			var url = "<%=BaseAction.rootLocation%>/department.business/web/registration/registration_list.jsp";
				        			setTimeout("getOpener().addCustomTab('"+title+"','"+icon+"','"+url+"');parent.fb.end();",2000);
				        		}else{	
				        			setTimeout("getOpener().reloadList();parent.fb.end();", 2000);
				        		}
				        	}
				        } 
				    });
				}
			});
		}
	
	function selectRoom(){
		fbStart('选择房间','<%=BaseAction.rootLocation %>/common/room!select.action',520,400);
	}
	
	function setSelectedRoom(room){
		$("#roomId").val(room.id);
		$("#roomName").val(room.name);
		$("input[name='registrationBook.saleUnit']").val(room.priceRent);
		$("input[name='registrationBook.salePrice']").val(room.totalRent);
		$("input[name='registrationBook.saleArea']").val(room.buildingArea);
	}
	
	function selectInvestment(){
		fbStart('选择项目','<%=basePath %>investment!select.action',520,400);
	}
	
	function selectContectInfo(){
		fbStart('选择线索','<%=basePath %>investmentContectInfo!singleSelect.action',520,400);
	}
	
	function setSelectedCotectInfo(contectInfo){
		$("#contectInfoId").val(contectInfo.id);
		$("#contectInfoName").val(contectInfo.name);
	}
	
	function setSelectedInvestment(investment){
		$("#investmentId").val(investment.id);
		$("#investmentName").val(investment.name);
	}
	function selectUser(user){
		fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!selectSelf.action',520,400);
	}
	
	function setSelectedUser(user){
		$("#userId").val(user.id);
		$("#userName").val(user.realName);
	}
	 
	 
</script>
</head>

<body>
<form action="<%=basePath %>registrationBook!save.action" method="post" name="form1" id="form1">
<!--basediv-->
<input type="hidden" value="${param.form }" id="type"/>
<div class="basediv">
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
		<td class="layertdleft100">客户名称：</td>
		<td class="layerright" colspan="3">
			<input id="name" name="registrationBook.name" class="inputauto" />
		</td>
    </tr>
    <tr>
    	<td class="layertdleft100">线索：</td>
		<td class="layerright">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input id="contectInfoName" name="registrationBook.contectInfoName" class="inputauto" readonly="readonly" type="text" onclick="selectContectInfo();" />
						<input id="contectInfoId" name="registrationBook.contectInfoId" type="hidden" class="inputauto" />
					</td>
					<td width="25" align="center">
						<img style="position: relative;left:-1px;" onclick="selectContectInfo();" src="core/common/images/outdiv.gif" width="20" height="22" />
					</td>
				</tr>
			</table>
		</td>
		<td class="layertdleft100">项目：</td>
		<td class="layerright">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input id="investmentId" name="registrationBook.investmentId" type="hidden" class="inputauto" />
						<input id="investmentName" name="registrationBook.investmentName" class="inputauto" readonly="readonly" type="text" onclick="selectInvestment();" />
					</td>
					<td width="25" align="center">
						<img style="position: relative;left:-1px;" onclick="selectInvestment();" src="core/common/images/outdiv.gif" width="20" height="22" />
					</td>
				</tr>
			</table>
		</td>
    </tr>
    <tr>
    	<td class="layertdleft100"><span class="psred">*</span>招商顾问：</td>
       	<td class="layerright">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input id="userId" name="registrationBook.userId" type="hidden" class="inputauto" />
						<input id="userName" name="userName" readonly="readonly" type="text" class="inputauto" onclick="selectUser();" />
					</td>
					<td width="25" align="center">
						<img style="position: relative;left:-1px;" onclick="selectUser();" src="core/common/images/outdiv.gif" width="20" height="22" />
					</td>
				</tr>
			</table>
	   </td>
	   <td class="layertdleft100">房间名称：</td>
	   <td class="layerright">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input id="roomId" name="registrationBook.roomId" type="hidden" class="inputauto" />
						<input id="roomName" name="registrationBook.roomName" readonly="readonly" type="text" class="inputauto" onclick="selectRoom();" />
					</td>
					<td width="25" align="center"><img style="position: relative;left:-1px;" onclick="selectRoom();" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
				</tr>
			</table>
	   </td>
    </tr>
    <tr>
       <td class="layertdleft100">房间编号：</td>
       <td class="layerright">
       	 <input name="registrationBook.roomCode" type="text" class="inputauto" />
       </td>
	   <td class="layertdleft100">面积：</td>
       <td class="layerright">
         <input name="registrationBook.saleArea" type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
	   </td>
    </tr>
    <tr>
       <td class="layertdleft100">单价：</td>
       <td class="layerright">
         <input name="registrationBook.saleUnit" type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
       </td>
	   <td class="layertdleft100">总价：</td>
       <td class="layerright">
         <input name="registrationBook.salePrice" type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
       </td>
    </tr>
    <tr>
	   <td class="layertdleft100">移动电话：</td>
	   <td colspan="5"  class="layerright">
	     <input name="registrationBook.mobile" type="text" class="inputauto"/>
	   </td>
	  <!--  <td class="layertdleft100">工作电话：</td>
	   <td class="layerright">
	     <input name="registrationBook.phone" type="text" class="inputauto"/>
	   </td> -->
	</tr>
	<tr>
	  <td class="layertdleft100">出租/出售：</td>
      <td class="layerright">
      	  <enum:select name="registrationBook.rentStatus" type="com.wiiy.business.preferences.enums.RentEnum" /> 
      </td>
	  <td class="layertdleft100">是否同步到房间：</td>
      <td class="layerright">
      	  <enum:radio name="registrationBook.whetherRoom" type="com.wiiy.commons.preferences.enums.BooleanEnum" defaultValue="NO" /> 
      </td>
	</tr>	
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>预订日期： </td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="150"><input id="registratTime" name="registrationBook.registratTime" readonly="readonly" type="text" class="inputauto" onclick="return showCalendar('registratTime', 'y-mm-dd');"/></td>
            <td><img src="core/common/images/timeico.gif" style="relative; left:-1px;" width="20" height="22" onclick="return showCalendar('registratTime', 'y-mm-dd');"/></td>
          </tr>
        </table>
      </td>
      <td class="layertdleft100"><span class="psred">*</span>终止日期：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="150"><input id="endTime" name="registrationBook.endTime" type="text" readonly="readonly"  class="inputauto" onclick="return showCalendar('endTime', 'y-mm-dd');"/></td>
            <td><img src="core/common/images/timeico.gif" style="relative; left:-1px;" width="20" height="22" onclick="return showCalendar('endTime', 'y-mm-dd');"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
       <td class="layertdleft100"><span class="psred">*</span>预订原因：</td>
       <td class="layerright" colspan="3" style="padding-top:1px;">
     		<textarea name="registrationBook.reason" style="height:40px;margin-top:2px;" class="textareaauto"></textarea>
       </td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td  class="layerright" colspan="3" style="padding-top:1px;">
      		<textarea name="registrationBook.memo"  style="height:40px;margin-top:2px;" class="textareaauto"></textarea></td>
    </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<!--basediv-->
<!--//basediv-->
<div class="buttondiv">
  <label><input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

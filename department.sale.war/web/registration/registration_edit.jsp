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

<script type="text/javascript" >
	$(document).ready(function() {
		initTip();
		initForm();
	});
	
	function checkForm(){
		if($("#reservationAmount").val()!="") {
			if(checkDouble("reservationAmount","预订金额")==false){
				return;
			}
		}
	}
	function initForm(){
		$("#form1").validate({
			rules: {
				"customerName":"required",  
				"userName":"required",
				"registrationBook.bookCode":"required",
				"registrationBook.reservationAmount":"number",
			    "registrationBook.registratTime":"required",
			    "registrationBook.endTime":"required",
			    "registrationBook.reason":"required"
			},
			messages: {
				"customerName":"请选择客户名称",  
				"userName":"请选择置业顾问",
				"registrationBook.bookCode":"请填写预订编号",
				"registrationBook.reservationAmount":"请填写预订金额",
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
				        		setTimeout("getOpener().reloadList();parent.fb.end();", 2000);
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
	}
	
	function selectCustomer(customer){
		fbStart('选择客户','<%=basePath %>web/registration/sale_selector.jsp',520,400);
	}
	
	function setSelectedCustomer(customer){
		$("#customerId").val(customer.id);
		$("#customerName").val(customer.name);
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
<form action="<%=basePath %>registrationBook!update.action" method="post" name="form1" id="form1">
<input type="hidden" id="id" name="registrationBook.id" value="${result.value.id }"/>
<!--basediv-->
<div class="basediv">
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
		<td class="layertdleft100"><span class="psred">*</span>客户名称：</td>
		<td class="layerright">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input id="customerId" name="registrationBook.customerId" value="${result.value.customerId }" type="hidden" class="inputauto" />
						<input id="customerName" name="customerName" value="${result.value.customer.name }" readonly="readonly" type="text" class="inputauto" onclick="selectCustomer();" />
					</td>
					<td width="25"><img style="position: relative;left:-1px;" onclick="selectCustomer();" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
				</tr>
			</table>
		</td>
		<td class="layertdleft100"><span class="psred">*</span>置业顾问：</td>
       <td class="layerright">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input id="userId" name="registrationBook.userId" value="${result.value.userId }" type="hidden" class="inputauto" />
						<input id="userName" name="userName" value="${result.value.user.realName }" readonly="readonly" type="text" class="inputauto" onclick="selectUser();" />
					</td>
					<td width="25"><img style="position: relative;left:-1px;" onclick="selectUser();" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
				</tr>
			</table>
	   </td>
    </tr>
    <tr>
       <td class="layertdleft100">身份证号：</td>
       <td class="layerright"><input name="registrationBook.idCard" value="${result.value.idCard }" type="text" class="inputauto" /></td>
       <td class="layertdleft100">到期是否可销售：</td>
       <td class="layerright">
       		<enum:radio  name="registrationBook.whetherSales" checked="result.value.whetherSales" type="com.wiiy.commons.preferences.enums.BooleanEnum" /> 
       </td>
    </tr>
    <tr>
       <td class="layertdleft100">业务编号：</td>
       <td class="layerright"><input name="registrationBook.businessCode" value="${result.value.businessCode }" type="text" class="inputauto" /></td>
	   <td class="layertdleft100"><span class="psred">*</span>预订编号：</td>
       <td class="layerright">
         <input name="registrationBook.bookCode" value="${result.value.bookCode }" type="text" class="inputauto" />
	   </td>
    </tr>
    <tr>
       <td class="layertdleft100">房间编号：</td>
       <td class="layerright">
       	 <input name="registrationBook.roomCode" value="${result.value.roomCode }" type="text" class="inputauto" />
       </td>
	   <td class="layertdleft100">房间名称：</td>
	    <td class="layerright">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input id="roomId" name="registrationBook.roomId" value="${result.value.roomId }" type="hidden" class="inputauto" />
						<input id="roomName" name="roomName" value="${result.value.roomName }"  readonly="readonly" type="text" class="inputauto" onclick="selectRoom();" />
					</td>
					<td width="25"><img style="position: relative;left:-1px;" onclick="selectRoom();" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
				</tr>
			</table>
	   </td>
    </tr>
    <tr>
       <td class="layertdleft100">单价：</td>
       <td class="layerright">
         <input name="registrationBook.saleUnit" value="<fmt:formatNumber value="${result.value.saleUnit}" pattern="#0.00"/>" type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
       </td>
	   <td class="layertdleft100">面积：</td>
       <td class="layerright">
         <input name="registrationBook.saleArea" value="<fmt:formatNumber value="${result.value.saleArea}" pattern="#0.00"/>" type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
	   </td>
    </tr>
    <tr>
       <td class="layertdleft100">总价：</td>
       <td class="layerright">
         <input name="registrationBook.salePrice" value="<fmt:formatNumber value="${result.value.salePrice}" pattern="#0.00"/>" type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
       </td>
	   <td class="layertdleft100"><span class="psred">*</span>预订金额：</td>
       <td class="layerright">
         <input name="registrationBook.reservationAmount" value="<fmt:formatNumber value="${result.value.reservationAmount}" pattern="#0.00"/>"  onkeyup="value=value.replace(/[^\d\.]/g,'')" type="text" class="inputauto" />
	   </td>
    </tr>
    <tr>
	   <td class="layertdleft100">移动电话：</td>
	   <td class="layerright">
	     <input name="registrationBook.mobile" value="${result.value.mobile }" type="text" class="inputauto"/>
	   </td>
	   <td class="layertdleft100">工作电话：</td>
	   <td class="layerright">
	     <input name="registrationBook.phone" value="${result.value.phone }" type="text" class="inputauto"/>
	   </td>
	</tr>
    <tr>
       <td class="layertdleft100">邮政编号：</td>
	   <td class="layerright">
	     <input name="registrationBook.zipCode" value="${result.value.zipCode }" type="text" class="inputauto"/>
	   </td>
	   <td class="layertdleft100">通讯地址：</td>
	   <td class="layerright">
	     <input name="registrationBook.adress" value="${result.value.adress }" type="text" class="inputauto"/>
	   </td>
	 </tr>
	<tr>
	  <td class="layertdleft100">出租/出售：</td>
      <td class="layerright">
      	  <enum:select checked="result.value.rentStatus" name="registrationBook.rentStatus" type="com.wiiy.sale.preferences.enums.RentEnum" /> 
      </td>
      <td class="layertdleft100">是否同步到房间：</td>
      <td class="layerright">
      	  <enum:radio checked="result.value.whetherRoom" name="registrationBook.whetherRoom" type="com.wiiy.commons.preferences.enums.BooleanEnum" /> 
      </td>
	</tr>					
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>预购日期： </td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="150"><input id="registratTime" name="registrationBook.registratTime" type="text" class="inputauto" value="<fmt:formatDate value="${result.value.registratTime }" pattern="yyyy-MM-dd" />" onclick="return showCalendar('registratTime');"/></td>
            <td><img src="core/common/images/timeico.gif" style="relative; left:-1px;" width="20" height="22" onclick="return showCalendar('registratTime', 'y-mm-dd');"/></td>
          </tr>
        </table></td>
      <td class="layertdleft100"><span class="psred">*</span>终止日期：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="150"><input id="endTime" name="registrationBook.endTime" type="text" class="inputauto" value="<fmt:formatDate value="${result.value.endTime }" pattern="yyyy-MM-dd" />"onclick="return showCalendar('endTime');"/></td>
            <td><img src="core/common/images/timeico.gif" style="relative; left:-1px;" width="20" height="22" onclick="return showCalendar('endTime', 'y-mm-dd');"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
       <td class="layertdleft100"><span class="psred">*</span>预订原因：</td>
       <td class="layerright" colspan="3" style="padding-top:1px;">
     		<textarea name="registrationBook.reason" style="height:40px;margin-top:2px;" class="textareaauto">${result.value.reason}</textarea>
       </td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td  class="layerright" colspan="3" style="padding-top:1px;">
      	<textarea name="registrationBook.memo" style="height:40px;margin-top:2px;" class="textareaauto">${result.value.memo}</textarea></td>
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

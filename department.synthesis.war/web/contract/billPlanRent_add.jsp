<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.common.preferences.enums.ApprovalStatusEnum"%>
<%@page import="com.wiiy.common.preferences.enums.BillPlanStatusEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
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
		$(function(){
			initTip();
			initForm();
		});
		function initForm(){
			$("#form1").validate({
				rules: {
					"billPlanRent.code":"required",
					"billPlanRent.planFee":{
						"required":true,
						"positivenumber":true
					},
					"billPlanRent.realFee":{
						"required":true,
						"positivenumber":true
					},
					"billPlanRent.startDate":"required",
					"billPlanRent.endDate":"required",
					"billPlanRent.planPayDate":"required"
				},
				messages: {
					"billPlanRent.code":"请输入或自动生成编号",
					"billPlanRent.planFee":{
						"required":"请输入计划金额"
					},
					"billPlanRent.realFee":{
						"required":"请输入实际金额"
					},
					"billPlanRent.startDate":"请输入计费开始日期",
					"billPlanRent.endDate":"请输入计费结束日期",
					"billPlanRent.planPayDate":"请输入计划付费日期"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					if($("#endDate").val()<$("#startDate").val()){
						showTip("计费开始日期不能小于计费结束日期",3000);
						return;
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
		function setSelectedRoom(room){
			if(room["kind.dataValue"]!='出租' || room["status.title"]!='空闲'){
				if(!confirm("该房间状态不是空闲 或 性质不是出租 是否继续")){
					return false;
				}
			}
			$("#roomId").val(room.id);
			$("#roomName").val(room["building.park.name"]+room["building.name"]+" "+room.name);
		}
		
		function generateCode(){
			$.post("<%=basePath%>billPlanRent!generateCode.action",function(data){
				if(data.result.success){
					$("#code").val(data.result.value);
				}
			});
		}
		
		function changeState(obj){
			if($(obj).val() == 'NO'){
				$(obj).val("YES");
			}else{
				$(obj).val("NO");
			}
			var p = $(obj).parent();
			var c = $(p).find("span");
			if($(obj).val() == 'NO')$(c).text("未审核");
			else $(c).text("已审核");
		}
	</script>
</head>

<body>
<form action="<%=basePath %>billPlanRent!save.action" method="post" name="form1" id="form1">
<input type="hidden" id="contractId" value="${param.id}" name="billPlanRent.contractId"/>
<input type="hidden" value="<%=BillPlanStatusEnum.UNCHECK %>" name="billPlanRent.status"/>
<input type="hidden" value="<%=ApprovalStatusEnum.NOAPPLICATION %>" name="billPlanRent.approvalStatus"/>
<div class="basediv">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>编号：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input id="code" name="billPlanRent.code" type="text" class="inputauto" /></td>
							<td><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>计划金额：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input name="billPlanRent.planFee" type="text" class="inputauto" /></td>
							<td>&nbsp; 元 本期设置优惠之前的金额 </td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>实际金额：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input name="billPlanRent.realFee" type="text" class="inputauto" /></td>
							<td>&nbsp; 元 本期实际需要支付的金额 </td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>计费日期：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input readonly="readonly" id="startDate" name="billPlanRent.startDate" type="text" class="inputauto" onclick="return showCalendar('startDate')"/></td>
							<td width="20"><img src="core/common/images/timeico.gif" style="position: relative;left:-1px;" width="20" height="22" onclick="return showCalendar('startDate')"/></td>
							<td width="18">&nbsp;-&nbsp;</td>
							<td width="100" align="center"><input readonly="readonly" id="endDate" name="billPlanRent.endDate" type="text" class="inputauto" onclick="return showCalendar('endDate')"/></td>
							<td width="20"><img style="position: relative;left:-1px;" src="core/common/images/timeico.gif" width="20" height="22" onclick="return showCalendar('endDate')"/></td>
							<td align="center">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>计划付费日期：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input readonly="readonly" id="planPayDate" name="billPlanRent.planPayDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>' type="text" class="inputauto" onclick="return showCalendar('planPayDate')"/></td>
							<td width="20"><img src="core/common/images/timeico.gif" style="position: relative;left:-1px;" width="20" height="22" onclick="return showCalendar('planPayDate')"/></td>
							<td>&nbsp;</td>
							<td class="layerright">
								<label><input name="billPlanRent.audit" type="checkbox" onclick="changeState(this);" value="<%=BooleanEnum.NO %>" /><span>未审核</span></label>
							</td>
						</tr>
					</table>
				</td>
				
			</tr>
			<tr>
				<td class="layertdleft100">备注：</td>
				<td class="layerright"><textarea name="billPlanRent.memo" class="inputauto" style="resize:none;height:50px;"></textarea></td>
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

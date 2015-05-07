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
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='billPlanRent.audit']");
			var p = $(audit).parent();
			var c = $(p).find("span");
			if($(audit).val() == 'NO'){
				$(c).text("未审核");
				$(audit).attr("checked",false);
			}
			else{
				$(c).text("已审核");
				$(audit).attr("checked","checked");
			} 
		}
		
		function changeState(obj){
			if($(obj).attr("checked"))
				$(obj).val("YES");
			else
				$(obj).val("NO");
			var p = $(obj).parent();
			var c = $(p).find("span");
			if($(obj).val() == 'NO')$(c).text("未审核");
			else $(c).text("已审核");
		}
		
		function initForm(){
			$("#form1").validate({
				rules: {
					"billPlanRent.feeType":"required",
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
					"billPlanRent.feeType":"请选择费用类型",
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
		
		function generateCode(){
			$.post("<%=basePath%>billPlanRent!generateCode.action",function(data){
				if(data.result.success){
					$("#code").val(data.result.value);
				}
			});
		}
	</script>
</head>

<body>
<form action="<%=basePath %>billPlanRent!update.action" method="post" name="form1" id="form1">
<input type="hidden" value="${result.value.id}" name="billPlanRent.id"/>
<input type="hidden" value="${result.value.contractId}" name="billPlanRent.contractId"/>
<input type="hidden" value="${result.value.status }" name="billPlanRent.status"/>
<div class="basediv">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>编号：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input id="code" name="billPlanRent.code" value="${result.value.code }" type="text" class="inputauto" /></td>
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
							<td width="100"><input name="billPlanRent.planFee" value='<fmt:formatNumber value="${result.value.planFee }" pattern="0.00" />' type="text" class="inputauto" /></td>
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
							<td width="100"><input name="billPlanRent.realFee" value='<fmt:formatNumber value="${result.value.realFee }" pattern="0.00" />' type="text" class="inputauto" /></td>
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
							<td width="100"><input readonly="readonly" id="startDate" name="billPlanRent.startDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.startDate}"/>'  type="text" class="inputauto" onclick="return showCalendar('startDate')"/></td>
							<td width="20"><img src="core/common/images/timeico.gif" style="position: relative;left:-1px;" width="20" height="22" onclick="return showCalendar('startDate')"/></td>
							<td width="18">&nbsp;-&nbsp;</td>
							<td width="100" align="center"><input readonly="readonly" id="endDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.endDate}"/>' name="billPlanRent.endDate" type="text" class="inputauto" onclick="return showCalendar('endDate')"/></td>
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
							<td width="100"><input readonly="readonly" id="planPayDate" name="billPlanRent.planPayDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.startDate}"/>' type="text" class="inputauto" onclick="return showCalendar('planPayDate')"/></td>
							<td width="20"><img src="core/common/images/timeico.gif" style="position: relative;left:-1px;" width="20" height="22" onclick="return showCalendar('planPayDate')"/></td>
							<td>&nbsp;</td>
							<td class="layerright">
								<label><input name="billPlanRent.audit" type="checkbox" <c:if test="${result.value.audit eq 'YES' }">checked="checked"</c:if> onclick="changeState(this);" /><span>未审核</span></label>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="layertdleft100">备注：</td>
				<td class="layerright"><textarea name="billPlanRent.memo" class="inputauto" style="resize:none;height:50px;">${result.value.memo }</textarea></td>
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

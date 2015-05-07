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
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>

	<script type="text/javascript">
	 	var selecteds = "";
	 	var clicked = null;
		$(function(){
			initTip();
			initForm();
			initDate();
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='billRent.audit']");
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
		function initDate(){
			<%Calendar c = Calendar.getInstance();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = s.format(c.getTime());	
			c.add(Calendar.YEAR, 1);
			c.add(Calendar.DATE, -1);
			String endTime = s.format(c.getTime());
			%>
			var startTime = '<%=startTime%>';
			var endTime = '<%=endTime%>';
			$("#startTime").val(startTime);
			$("#nextTime").val(endTime);
			$("#endTime").val(endTime);
			$("#receiveDate").val(startTime);
			
		}
		function selectUser(id){
			var title = '选择';
			clicked = id;
			switch(id){
			case 'handling':
				title += "经手人";
				break;
			case 'supplier':
				title += "供应商";
				break;
			case 'customer':
				title += "客户";
				break;
			case 'contactName':
				title += "主要联系人";
				break;
			case 'dutyMember':
				title += "责任人";
				break;
			}
			fbStart(title,'<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
		} 
		function setSelectedUser(contect){
			$("#"+clicked).val(contect.name);
			$("#"+clicked+"Id").val(contect.id);
		}
		
	    function checkDouble(el){
	    	$(el).val($(el).val().replace(/[^\d\.]/g,''));
	    } 
		function initForm(){
			$("#form1").validate({
				rules: {
					"billRentName":"required",
					"billRent.settlementDate":"required",
					"billRent.payment":"required"
				},
				messages: {
					"billRentName":"请选择结算安排",
					"billRent.settlementDate":"请选择结算日期",
					"billRent.payment":"请选择收付方向"
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
				        		setTimeout("getOpener().reloadEachList('contractBill');parent.fb.end()", 2000);
				        	}
				        } 
				    });
				}
			});
		}
 		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		} 
 		
 		function selectPlan(){
 			var id = "${param.id}";
 			var rentId = $("#billRentId").val();
 			var rentName = $("#billRentName").val();
 			if(rentId != '' && rentName != ''){
	 			fbStart('选择结算安排','<%=basePath %>web/contract/billPlanRent_selector.jsp?id='+rentId+'&name='+rentName+'&contractId='+id,550,358);
 			}else{
	 			fbStart('选择结算安排','<%=basePath %>web/contract/billPlanRent_selector.jsp?contractId='+id,550,358);
 			}
 		}
 		
 		function setSelectedParam(selectedParam){
 			if(selectedParam && selectedParam.id){
	 			$("#billRentId").val(selectedParam.id);
	 			$("#billRentName").val(selectedParam.name);
	 			$.post("<%=basePath%>billPlanRent!findPlanById.action?id="+selectedParam.id,function(data){
					if(data.result.success){
						//planFee planPayDate
						var planPayDate = data.result.value.planPayDate;
						var planFee = data.result.value.planFee+"";
						var fee = planFee.split('.');
						if(fee.length == 1){
							planFee += ".00";
						}
						planPayDate = planPayDate.substr(0,planPayDate.indexOf("T"));
						$("#planPayDate").val(planPayDate);
						$("#planFee").val(planFee);
					}
				});
 			}
 			
 		}
	</script>
</head>
<body>
<form action="<%=basePath %>billRent!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="billRent.id" value="${result.value.id }"/>
<input type="hidden" name="billRent.contractId" value="${result.value.contractId }"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	<tr>
		    <td class="layertdleft100"><span class="psred">*</span>结算安排：</td>
			<td class="layerright">
	          	<input id="billRentId" name="billRent.billPlanRentId" type="hidden"/>
	          	<input id="billRentName" name="billRentName" value="${result.value.billPlanRent.code }" class="inputauto" readonly="readonly"/></td>
			</td>
       		<td class="layertdleft100" >预计结算：</td>
       		<td class="layerright" >
				<input id="planFee" readonly="readonly" 
				value='<fmt:formatNumber value="${result.value.billPlanRent.planFee }" pattern="0.00" />' type="text" class="inputauto" />
		    </td>
       	</tr>
       	<tr>
       		<td class="layertdleft100" >预计结算日期：</td>
	      	<td class="layerright" >
				<input id="planPayDate" readonly="readonly"
				value='<fmt:formatDate value="${result.value.billPlanRent.planPayDate }" pattern="yyyy-MM-dd"/>' type="text" class="inputauto" />
	      	</td>
		    <td class="layertdleft100">合同名称：</td>
			<td class="layerright" >
				<input value="${result.value.contract.name }" class="inputauto" readonly="readonly"/>
		   	</td>
		</tr>
       	<tr>
   			<td class="layertdleft100">合同金额：</td>
			<td class="layerright" >
				<input readonly="readonly" value='<fmt:formatNumber value="${result.value.contract.contractAmount }" pattern="0.00" />' class="inputauto" />
		   	</td>
   			<td class="layertdleft100">已结算总和：</td>
			<td class="layerright" >
				<input class="inputauto" 
				value='<fmt:formatNumber value="${result.value.contract.ext1 }" pattern="0.00" />' readonly="readonly" />
		   	</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100" ><span class="psred">*</span>结算日期：</td>
	      	<td class="layerright" >
	      		<table width="143" border="0" cellspacing="0" cellpadding="0">
		        	<tbody>
			          <tr>
			            <td>
			            	<input id="settlementDate" name="billRent.settlementDate"
			            	value='<fmt:formatDate value="${result.value.settlementDate }" pattern="yyyy-MM-dd" />' readonly="readonly" type="text" class="inputauto" onclick="showCalendar('settlementDate')"/>
			            </td>
	          			<td width="20" align="center">
	          				<img style="position:relative; left:-1px;" onclick="showCalendar('settlementDate')" src="core/common/images/timeico.gif" width="20" height="22" />
	          			</td>
			          </tr>
		        	</tbody>
	      		</table>
	      	</td>
       		<td class="layertdleft100"><span class="psred">*</span>金额：</td>
       		<td class="layerright" >
				<input name="billRent.settlementFee"
				type="text" class="inputauto" value='<fmt:formatNumber value="${result.value.settlementFee }" pattern="0.00"/>' />
		    </td>
       	</tr>
       	<tr>
     		<td class="layertdleft100">结算性质：</td>
       		<td class="layerright">
       			<enum:select type="com.wiiy.common.preferences.enums.SettlementTypeEnum" checked="result.value.settlementType" name="billRent.settlementType" />
       		</td>
       		<td class="layertdleft100"><span class="psred">*</span>收付方向：</td>
       		<td class="layerright">
       			<enum:select type="com.wiiy.common.preferences.enums.ContractPaymentEnum" checked="result.value.payment" name="billRent.payment" />
       		</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">结算方式：</td>
       		<td class="layerright">
       			<enum:select type="com.wiiy.common.preferences.enums.ContractSettlementEnum" checked="result.value.settlement" name="billRent.settlement" />
       		</td>
		    <td class="layerright" colspan="2">
				<label><input name="billRent.audit" type="checkbox" onclick="changeState(this);" value="${result.value.audit }" /><span>未审核</span></label>
	      	</td>
       	</tr>
       	<tr>
       	    <td class="layertdleft100">备注：</td>
	    	<td class="layerright" colspan="3" style="padding-bottom:3px;">
	    		<textarea name="billRent.memo" class="inputauto" style="resize:none;height:40px;">${result.value.memo }</textarea>
	    	</td>
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

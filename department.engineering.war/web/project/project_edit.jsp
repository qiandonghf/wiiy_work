<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript">
 		var clicked = null;
		$(function(){
			initTip();
			initForm();
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='engineering.audit']");
			var finished = $("input[name='engineering.finished']");
			var published = $("input[name='engineering.published']");
			if($(audit).val() == 'YES'){
				$(audit).attr("checked",true);
			}
			if($(finished).val() == 'YES'){
				$(finished).attr("checked",true);
			}
			if($(published).val() == 'YES'){
				$(published).attr("checked",true);
			}
			changeState(audit);
			changeState(finished);
		}
		
		function changeState(obj){
			if($(obj).attr("checked")){
				$(obj).val("YES");
			}else{
				$(obj).val("NO");
			}
			if($(obj).attr("checked")){
				$(obj).val("YES");
			}else{
				$(obj).val("NO");
			}
			var p = $(obj).parent();
			var c = $(p).find("span");
			var txt = $(c).text();
			if($(obj).val() == 'YES'){
				$(obj).attr("checked",true);
				txt = txt.replace("未","已");
				$(c).text(txt);
			}else{
				$(obj).removeAttr("checked");
				txt = txt.replace("已","未");
				$(c).text(txt);
			}
		}
		
		function selectUser(id){
			var title = '选择';
			clicked = id;
			switch(id){
			case 'handling':
				title += "经手人";
				break;
			case 'manager':
				title += "负责人";
				break;
			}
			fbStart(title,'<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
		} 
		function setSelectedUser(contect){
			$("#"+clicked).val(contect.name);
			$("#"+clicked+"Id").val(contect.id);
		}
		
		function selectCustomer(id){
			var title = '选择';
			clicked = id;
			switch(id){
			case 'supplier':
				title += "甲方";
				break;
			case 'customer':
				title += "乙方";
				break;
			}
			fbStart('选择企业','<%=basePath %>web/customer_selector.jsp',520,400);
		}
		function setSelectedCustomer(customer){
			$("#"+clicked).val(customer.name);
			$("#"+clicked+"Id").val(customer.id);
		} 
		function checkDouble(el){
	    	$(el).val($(el).val().replace(/[^\d\.]/g,''));
	    	if($(el).val() == "."){
	    		$(el).val("0.");
	    	}
	    	var pos1 = ($(el).val()+"").indexOf(".");
	    	var pos2 = ($(el).val()+"").lastIndexOf(".");
	    	if(pos1 != -1 && pos2 != pos1){
	    		lastVal = ($(el).val()+"").substr(pos1+1);
	    		var pos3 = pos1+lastVal.indexOf(".");
	    		var val = ($(el).val()+"").substr(0,pos3+1);
	    		$(el).val(val);
	    		showTip("不允许有多个小数点",2000);
	    	}
	    }
		function initForm(){
			$("#form1").validate({
				rules: {
					"engineering.name":"required",
					"engineering.code":"required",
					"engineering.startDate":"required",
					"engineering.endDate":"required",
					"engineering.signDate":"required"
				},
				messages: {
					"engineering.name":"请输入项目名称",
					"engineering.code":"请输入项目编号",
					"engineering.startDate":"请选择有效日期开始时间",
					"engineering.endDate":"请选择有效日期结束时间",
					"engineering.signDate":"请选择签订日期"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					if($("#endDate").val()<$("#startDate").val()){
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
		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		}
		
		function generateCode(){
			showTip("正在生成编号,请稍后……",60000);
			$.post("<%=basePath%>project!generateCode.action",function(data){
				if(data.result.success){
					showTip("生成编号成功",2000);
					$("#code").val(data.result.value);
				}
			});
		}
		

	</script>
	<style>
	</style>
</head>
<body>
<form action="<%=basePath %>project!update.action" method="post" name="form1" id="form1">
<input name="engineering.id" value="${result.value.id }" type="hidden" />
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
       		<td class="layertdleft100"><span class="psred">*</span>项目名称：</td>
			<td class="layerright"><input name="engineering.name" type="text" class="inputauto" value="${result.value.name }" /></td>
       		<td class="layertdleft100"><span class="psred">*</span>编号：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100"><input id="code" name="engineering.code" value="${result.value.code }" type="text" class="inputauto" /></td>
						<td><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
					</tr>
				</table>
			</td>
       	</tr>
       	<tr>
			<td class="layertdleft100">项目简称：</td>
		    <td class="layerright"><input name="engineering.abbreviation" type="text" class="inputauto" value="${result.value.abbreviation}" /></td>
       		<td class="layertdleft100"><span class="psred">*</span>项目进度数：</td>
		    <td class="layerright"><input name="engineering.schedules" onkeyup="checkDouble(this);" value="${result.value.schedules}" class="inputauto"/>
		    </td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">甲方：</td>
			<td class="layerright">
				<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="supplierId" name="engineering.supplierId" value="${result.value.supplierId }" type="hidden"/>
			          	<input id="supplier" name="engineering.supplierName" class="inputauto" value="${result.value.supplierName }" onclick="selectCustomer('supplier')" readonly="readonly"/></td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('supplier')" style="cursor:pointer"/></td>
			        </tr>
		   		</table>
		   	</td>
       		<td class="layertdleft100">乙方：</td>
			<td class="layerright">
				<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
				          <input id="customerId" name="engineering.customerId" value="${result.value.customerId }" type="hidden"/>
				          <input id="customer" name="engineering.customerName" class="inputauto" value="${result.value.customerName }" onclick="selectCustomer('customer')" readonly="readonly"/>
				      </td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('customer')" style="cursor:pointer"/></td>
			        </tr>
			    </table>
			</td>			
       	</tr>
       	<tr>
       		<td class="layertdleft100">项目状态：</td>
			<td class="layerright" >
				<enum:select name="engineering.status" checked="result.value.status" type="com.wiiy.common.preferences.enums.ProjectStatusEnum" />
		    </td>
		    <td class="layertdleft100">重要程度：</td>
			<td class="layerright">
				<enum:select name="engineering.priority" checked="result.value.priority" type="com.wiiy.common.preferences.enums.ProjectPriorityEnum" />
		    </td>
    	</tr>
    	<tr>
    		<td class="layertdleft100">主要联系人：</td>
			<td class="layerright">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="managerId" name="engineering.managerId" value="${result.value.manager.id }" type="hidden" />
			          	<input id="manager" name="managerName" value="${result.value.manager.realName }" class="inputauto" onclick="selectUser('manager')" readonly="readonly" />
			          </td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('manager')" style="cursor:pointer"/></td>
			        </tr>
			    </table>
		    </td>
		    <td class="layertdleft100">经手人：</td>
			<td class="layerright" >
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="handlingId" name="engineering.handlingId" value="${result.value.handling.id }" type="hidden" />
			          	<input id="handling" class="inputauto" value="${result.value.handling.realName }" onclick="selectUser('handling')" readonly="readonly" />
			          </td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('handling')" style="cursor:pointer"/></td>
			        </tr>
			    </table>
			</td>
    	</tr>
       	<tr>
       	 	<td class="layertdleft100">货币种类：</td>
       		<td class="layerright" >
	       		<dd:select name="engineering.currencyTypeId" checked="result.value.currencyTypeId" key="project.0001"/>
       		</td>
       		<td class="layertdleft100">金额：</td>
	      	<td class="layerright"><input name="engineering.amount" value='<fmt:formatNumber value="${result.value.amount }" pattern="0.00" />' type="text" value="0.00" class="inputauto"  /></td>
       	</tr>
       	<tr>
    		<td class="layertdleft100">预计花费：</td>
	      	<td class="layerright"><input name="engineering.expectedCost" value='<fmt:formatNumber value="${result.value.expectedCost }" pattern="0.00" />' type="text" value="0.00" class="inputauto"/></td>
    		<td class="layertdleft100">实际花费：</td>
    		<td class="layerright" ><input name="engineering.actualCost" value='<fmt:formatNumber value="${result.value.actualCost }" pattern="0.00" />' type="text" value="0.00" class="inputauto"/></td>
    	</tr>
    	<tr>
    		<td class="layertdleft100">收付方式：</td>
       		<td class="layerright">
				<enum:select name="engineering.payment" checked="result.value.payment" type="com.wiiy.common.preferences.enums.PaymentTypeEnum"/>
       		</td>
       		<td class="layertdleft100">结算方式：</td>
       		<td class="layerright" >
				<enum:select name="engineering.settlement" checked="result.value.settlement" type="com.wiiy.common.preferences.enums.SettlementTypeEnum" />
       		</td>
    	</tr>
    	<tr>
      		<td class="layertdleft100"><span class="psred">*</span>项目开始日期：</td>
     		<td class="layerright">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="startDate" name="engineering.startDate" 
			          value='<fmt:formatDate value="${result.value.startDate }" pattern="yyyy-MM-dd" />' type="text" readonly="readonly" class="inputauto" onclick="showCalendar('startDate');"/></td>
			          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('startDate');" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			        </tr>
			    </table>
		    </td>
		    <td class="layertdleft100"><span class="psred">*</span>项目结束日期：</td>
     		<td class="layerright">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="endDate" name="engineering.endDate"
			          value='<fmt:formatDate value="${result.value.endDate }" pattern="yyyy-MM-dd" />' type="text" readonly="readonly" class="inputauto" onclick="showCalendar('endDate')"/></td>
			          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('endDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			        </tr>
			    </table>
		    </td>
      	</tr>
    	<tr>
    		<td class="layertdleft100"><span class="psred">*</span>项目签订日期：</td>
	      	<td class="layerright">
		      	<table border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><input id="signDate" name="engineering.signDate" readonly="readonly"
		            value='<fmt:formatDate value="${result.value.signDate }" pattern="yyyy-MM-dd" />' type="text" class="inputauto" onclick="showCalendar('signDate')"/></td>
          			<td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('signDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
		          </tr>
		      	</table>
	      	</td>
	      	<td class="layerright" colspan="2">
				<label><input name="engineering.audit" type="checkbox" onclick="changeState(this);" value="${result.value.audit }" /><span>未审核</span></label>
				<label><input name="engineering.finished" type="checkbox" onclick="changeState(this);" value="${result.value.finished }" /><span>未完成</span></label>
		   		<label><input name="engineering.published" type="checkbox" onclick="changeState(this);" value="${result.value.published }" />公开标志</label>
	      	</td>
       	</tr>
       	<tr>
	    	<td class="layertdleft100">项目简介：</td>
	    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
	    		<textarea name="engineering.introduction" class="inputauto" style="resize:none;height:40px;">${result.value.introduction }</textarea>
	    	</td>
	    </tr>
       	<tr>
       		<td class="layertdleft100">备注信息：</td>
			<td class="layerright"  colspan="3">
	    		<textarea name="engineering.memo" class="inputauto" style="resize:none;height:40px;">${result.value.memo }</textarea>
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

<%@page import="com.wiiy.common.preferences.enums.DepartmentEnum"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
			initDate();
			$("select").each(function(){
				$(this).children().eq(1).selected();
			});
		});
		
		function changeState(obj){
			if($(obj).val() == 'NO'){
				$(obj).val("YES");
			}else{
				$(obj).val("NO");
			}
			if($(obj).attr("name") == 'sale.finished'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未完成");
				else $(c).text("已完成");
			}else if($(obj).attr("name") == 'sale.audit'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未审核");
				else $(c).text("已审核");
			}
		}
		
		function initDate(){
			<%Calendar c = Calendar.getInstance();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = s.format(c.getTime());	
			c.add(Calendar.YEAR, 1);
			c.add(Calendar.DATE, -1);
			String endTime = s.format(c.getTime());
			%>
			var startDate = '<%=startTime%>';
			var endDate = '<%=endTime%>';
			$("#startDate").val(startDate);
			$("#signDate").val(startDate);
			$("#endDate").val(endDate);
			
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
		
		function checkDouble(el){
	    	$(el).val($(el).val().replace(/[^\d\.]/g,''));
	    	var pos1 = ($(el).val()+"").indexOf(".");
	    	var pos2 = ($(el).val()+"").lastIndexOf(".");
	    	if(pos1 != pos2){
	    		var val = ($(el).val()+"").substr(0,$(el).val().length-1);
	    		showTip("不允许有多个小数点",2000);
	    		$(el).val(val);
	    	}
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
			fbStart('选择客商','<%=basePath %>web/customer_selector.jsp',520,400);
		}
		function setSelectedCustomer(customer){
			$("#"+clicked).val(customer.name);
			$("#"+clicked+"Id").val(customer.id);
		} 
	    
		function initForm(){
			$("#form1").validate({
				rules: {
					"sale.name":"required",
					"sale.code":"required",
					"sale.schedules":"required",
					"sale.startDate":"required",
					"sale.endDate":"required",
					"sale.signDate":"required"
				},
				messages: {
					"sale.name":"请输入项目名称",
					"sale.code":"请输入项目编号",
					"sale.schedules":"请输入项目进度数",
					"sale.startDate":"请选择有效日期开始时间",
					"sale.endDate":"请选择有效日期结束时间",
					"sale.signDate":"请选择签订日期"
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
<form action="<%=basePath %>project!save.action" method="post" name="form1" id="form1">
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
       		<td class="layertdleft100"><span class="psred">*</span>项目名称：</td>
			<td class="layerright"><input name="sale.name" type="text" class="inputauto" value="" /></td>
       		<td class="layertdleft100"><span class="psred">*</span>编号：</td>
			<td class="layerright">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100"><input id="code" name="sale.code" type="text" class="inputauto" /></td>
						<td><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
					</tr>
				</table>
			</td>
       	</tr>
       	<tr>
			<td class="layertdleft100">项目简称：</td>
		    <td class="layerright"><input name="sale.abbreviation" type="text" class="inputauto" value="" /></td>
			<td class="layertdleft100"><span class="psred">*</span>项目进度数：</td>
		    <td class="layerright"><input name="sale.schedules" onkeyup="checkDouble(this);" class="inputauto"/></td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">甲方：</td>
			<td class="layerright">
				<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="supplierId" name="sale.supplierId" type="hidden"/>
			          	<input id="supplier" name="sale.supplierName" class="inputauto" onclick="selectCustomer('supplier')" readonly="readonly"/></td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('supplier')" style="cursor:pointer"/></td>
			        </tr>
		   		</table>
		   	</td>
       		<td class="layertdleft100">乙方：</td>
			<td class="layerright">
				<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
				          <input id="customerId" name="sale.customerId" type="hidden"/>
				          <input id="customer" name="sale.customerName" class="inputauto" onclick="selectCustomer('customer')" readonly="readonly"/>
				      </td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('customer')" style="cursor:pointer"/></td>
			        </tr>
			    </table>
			</td>			
       	</tr>
       	<tr>
       		<td class="layertdleft100">项目状态：</td>
			<td class="layerright" >
				<enum:select name="sale.status" type="com.wiiy.common.preferences.enums.ProjectStatusEnum" />
		    </td>
		    <td class="layertdleft100">重要程度：</td>
			<td class="layerright">
				<enum:select name="sale.priority" type="com.wiiy.common.preferences.enums.ProjectPriorityEnum" />
		    </td>
    	</tr>
    	<tr>
    		<td class="layertdleft100">主要联系人：</td>
			<td class="layerright">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="managerId" name="sale.managerId" type="hidden" />
			          	<input id="manager" name="managerName" class="inputauto" onclick="selectUser('manager')" readonly="readonly" />
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
			          	<input id="handlingId" name="sale.handlingId" type="hidden" />
			          	<input id="handling" class="inputauto" onclick="selectUser('handling')" readonly="readonly" />
			          </td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('handling')" style="cursor:pointer"/></td>
			        </tr>
			    </table>
			</td>
    	</tr>
       	<tr>
       	 	<td class="layertdleft100">货币种类：</td>
       		<td class="layerright" >
	       		<dd:select name="sale.currencyTypeId" key="project.0001"/>
       		</td>
       		<td class="layertdleft100">金额：</td>
	      	<td class="layerright"><input name="sale.amount" type="text" value="0.00" class="inputauto"  /></td>
       	</tr>
       	<tr>
    		<td class="layertdleft100">预计花费：</td>
	      	<td class="layerright"><input name="sale.expectedCost" type="text" value="0.00" class="inputauto"/></td>
    		<td class="layertdleft100">实际花费：</td>
    		<td class="layerright" ><input name="sale.actualCost" type="text" value="0.00" class="inputauto"/></td>
    	</tr>
    	<tr>
    		<td class="layertdleft100">收付方式：</td>
       		<td class="layerright">
				<enum:select name="sale.payment" type="com.wiiy.common.preferences.enums.PaymentTypeEnum"/>
       		</td>
       		<td class="layertdleft100">结算方式：</td>
       		<td class="layerright" >
				<enum:select name="sale.settlement" type="com.wiiy.common.preferences.enums.SettlementTypeEnum" />
       		</td>
    	</tr>
    	<tr>
      		<td class="layertdleft100"><span class="psred">*</span>项目开始日期：</td>
     		<td class="layerright">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="startDate" name="sale.startDate" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('startDate');"/></td>
			          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('startDate');" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			        </tr>
			    </table>
		    </td>
		    <td class="layertdleft100"><span class="psred">*</span>项目结束日期：</td>
     		<td class="layerright">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="endDate" name="sale.endDate" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('endDate')"/></td>
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
		            <td><input id="signDate" name="sale.signDate" readonly="readonly" type="text" class="inputauto" onclick="showCalendar('signDate')"/></td>
          			<td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('signDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
		          </tr>
		      	</table>
	      	</td>
	      	<td class="layerright" colspan="2">
				<label><input name="sale.audit" type="checkbox" onclick="changeState(this);" value="NO" /><span>未审核</span></label>
				<label><input name="sale.finished" type="checkbox" onclick="changeState(this);" value="NO" /><span>未完成</span></label>
		   		<label><input name="sale.published" type="checkbox" onclick="changeState(this);" value="NO" />公开标志</label>
	      	</td>
       	</tr>
       	<tr>
	    	<td class="layertdleft100">项目简介：</td>
	    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
	    		<textarea name="sale.introduction" class="inputauto" style="resize:none;height:40px;"></textarea>
	    	</td>
	    </tr>
       	<tr>
       		<td class="layertdleft100">备注信息：</td>
			<td class="layerright"  colspan="3">
	    		<textarea name="sale.memo" class="inputauto" style="resize:none;height:40px;"></textarea>
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

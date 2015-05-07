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
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
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
			initDoubleFormat();
		});
		
		function initChange(){
			var audit = $("input[name='contract.audit']");
			var finished = $("input[name='contract.finished']");
			var published = $("input[name='contract.published']");
			changeState(audit);
			changeState(finished);
			changeState(published);
		}
		function changeState(obj){
			if($(obj).attr("checked"))
				$(obj).val("YES");
			else
				$(obj).val("NO");
			if($(obj).attr("name") == 'contract.finished'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未完成");
				else $(c).text("已完成");
			}else if($(obj).attr("name") == 'contract.audit'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未审核");
				else $(c).text("已审核");
			}
		}
		function initDate(){
			var start = $("#startDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var sign = $("#signDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var end = $("#endDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var receive = $("#receiveDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var startDate;
			var endDate;
			if(start || end || sign){
				<%Calendar c = Calendar.getInstance();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				String startTime = s.format(c.getTime());	
				c.add(Calendar.YEAR, 1);
				c.add(Calendar.DATE, -1);
				String endTime = s.format(c.getTime());%>
				startTime = '<%=startTime%>';
				endTime = '<%=endTime%>';
			}
			if(start) $("#startDate").val(startDate);
			if(sign) $("#signDate").val(startDate);
			if(end) $("#endDate").val(endDate);
			if(receive) $("#receiveDate").val(startDate);
		}
		
		function selectUser(id){
			var title = '选择';
			clicked = id;
			switch(id){
			case 'handling':
				title += "经手人";
				break;
			case 'contact':
				title += "主要联系人";
				break;
			case 'duty':
				title += "责任人";
				break;
			}
			fbStart(title,'<%=BaseAction.rootLocation%>/core/user!select.action',520,400);
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
			fbStart('选择客商','<%=basePath%>web/customer_selector.jsp',520,400);
		}
		function setSelectedCustomer(customer){
			$("#"+clicked).val(customer.name);
			$("#"+clicked+"Id").val(customer.id);
		} 
		
	    function checkDouble(el){
	    	$(el).val($(el).val().replace(/[^\d\.]/g,''));
	    } 
		function initForm(){
			$("#form1").validate({
				rules: {
					"contract.name":"required",
					"contract.code":"required",
					//"contract.supplierName":"required",
					//"contract.customerName":"required",
					//"contract.contactName":"required",
					"contract.startTime":"required",
					"contract.endTime":"required",
					"contract.signDate":"required"
				},
				messages: {
					"contract.name":"请输合同名称",
					"contract.code":"请输入合同编号",
					//"contract.supplierName":"请选择供应商",
					//"contract.customerName":"请选择客户",
					//"contract.contactName":"请选主要联系人",
					"contract.startTime":"请选择有效日期开始时间",
					"contract.endTime":"请选择有效日期结束时间",
					"contract.signDate":"请选择签订日期"
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
				        		setTimeout("getOpener().reloadList();", 2000);
				        	}
				        } 
				    });
				}
			});
		}
 		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		} 
 		
 		function reloadEachList(id){
 			$("#"+id).trigger("reloadGrid");
 		}
 		function generateCode(){
			showTip("正在生成编号,请稍后……",60000);
			$.post("<%=basePath%>contract!generateCode.action",function(data){
				if(data.result.success){
					showTip("生成编号成功",2000);
					$("#code").val(data.result.value);
				}
			});
		}
 		
 		function selectProject(id){
			clicked = id;
			var name = $("#"+clicked).val();
			name = encodeURIComponent(name);
			name = encodeURIComponent(name);
			var projectId = $("#"+clicked+"Id").val();
			fbStart("选择项目",'<%=basePath %>web/project_selector.jsp?name='+name+"&id="+projectId,520,361);
		} 
		
		function setSelectedProject(project){
			$("#"+clicked).val(project.name);
			$("#"+clicked+"Id").val(project.id); 
		}
	</script>
</head>
<body>
<form action="<%=basePath %>contract!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="contract.id" value="${result.value.id }"/>
<div class="basediv" style="margin-top:0px;">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
	       		<td class="layertdleft100"><span class="psred">*</span>合同名称：</td>
			    <td class="layerright"><input name="contract.name" type="text" class="inputauto" value="${result.value.name }" /></td>
	       		<td class="layertdleft100"><span class="psred">*</span>合同编号：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input id="code" name="contract.code" type="text" class="inputauto" value="${result.value.code }" /></td>
							<td><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
						</tr>
					</table>
				</td>
	       	</tr>
	       	<tr>
	       	    <td class="layertdleft100">甲方：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="supplierId" value="${result.value.supplierId }" name="contract.supplierId" type="hidden"/>
				          	<input id="supplier" name="contract.supplierName" value="${result.value.supplierName }" class="inputauto" onclick="selectCustomer('supplier')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('supplier')" style="cursor:pointer"/></td>
				        </tr>
			   		</table>
			   	</td>
			    <td class="layertdleft100">乙方：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
					          <input id="customerId" name="contract.customerId" value="${result.value.customerId }" type="hidden"/>
					          <input id="customer" name="contract.customerName" value="${result.value.customerName }" class="inputauto" onclick="selectCustomer('customer')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('customer')" style="cursor:pointer"/></td>
				        </tr>
				    </table>
			    </td>
			</tr>
			<tr>
			    <td class="layertdleft100">所属项目：</td>
				<td class="layerright">
					<c:if test="${not empty result.value.project.id }">
						<input id="projectId" name="contract.projectId" value="${result.value.project.id }" type="hidden"/>
					    <input class="inputauto" name="projectName" value="${result.value.project.name }" readonly="readonly"/>
					</c:if>
					<c:if test="${empty result.value.project.id }">
						<table width="143" border="0" cellspacing="0" cellpadding="0">
					        <tr>
					          <td>
						          <input id="projectId" name="contract.projectId" type="hidden"/>
						          <input id="project" name="projectName" class="inputauto" onclick="selectProject('project')" readonly="readonly"/></td>
					          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectProject('project')" style="cursor:pointer"/></td>
					        </tr>
					    </table>
					</c:if>
			    </td>
				<td class="layertdleft100">主要联系人：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="contactId" name="contract.contactId" value="${result.value.contact.id }" type="hidden"/>
				         	<input id="contact" value="${result.value.contact.realName }" class="inputauto" onclick="selectUser('contact')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('contact')" style="cursor:pointer"/></td>
				        </tr>
		    		</table>
		    	</td>
	       	</tr>
	       	<tr>
				<td class="layertdleft100">责任人：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="dutyId" value="${result.value.duty.id }" name="contract.dutyId" type="hidden"/>
				          	<input id="duty" value="${result.value.duty.realName }" class="inputauto" onclick="selectUser('duty')" readonly="readonly"/></td>
				          <td width="21" align="center">
				          <img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('duty')" style="cursor:pointer"/></td>
				        </tr>
				    </table>
				</td>
			   	<td class="layertdleft100">经手人：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="handlingId" name="contract.handlingId" value="${result.value.handling.id }" type="hidden"/>
				          	<input id="handling" value="${result.value.handling.realName }" class="inputauto" onclick="selectUser('handling')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('handling')" style="cursor:pointer"/></td>
				        </tr>
			    	</table>
			    </td>
	       	</tr>
	       	<tr>
	       		<td class="layertdleft100">合同类别：</td>
				<td class="layerright">
					<dd:select name="contract.categoryId" checked="result.value.categoryId" key="contract.0002" />
			    </td>
				<td class="layertdleft100">合同形式：</td>
			    <td class="layerright">
					<enum:select name="contract.contractForm" checked="result.value.contractForm" type="com.wiiy.common.preferences.enums.ContractFormEnum" />
				</td>
	       	</tr>
	       	<tr>
			    <td class="layertdleft100">合同状态：</td>
				<td class="layerright">
				<enum:select name="contract.contractStatus" checked="result.value.contractStatus" type="com.wiiy.common.preferences.enums.ContractStatusEnum" />
			    </td>
			    <td class="layertdleft100">货币种类：</td>
	       		<td class="layerright">
					<dd:select name="contract.currencyTypeId" checked="result.value.currencyTypeId" key="project.0001" />
	       		</td>
	       	</tr>
	       	<tr>
			    <td class="layertdleft100">收付方式：</td>
	       		<td class="layerright">
	       			<enum:select name="contract.payment" checked="result.value.payment" type="com.wiiy.common.preferences.enums.PaymentTypeEnum"/>
	       		</td>
	       		<td class="layertdleft100">结算方式：</td>
	       		<td class="layerright">
	       			<enum:select name="contract.settlement" checked="result.value.settlement" type="com.wiiy.common.preferences.enums.SettlementTypeEnum"/>
	       		</td>	
	    	</tr>
	    	<tr>
	      		<td class="layertdleft100"><span class="psred">*</span>合同生效时间：</td>
	     		<td class="layerright">
		     		<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="startDate" name="contract.startDate" 
				          	value='<fmt:formatDate value="${result.value.startDate}" pattern="yyyy-MM-dd"/>' type="text" readonly="readonly" 
				          	class="inputauto" onclick="showCalendar('startDate')"/>
				          </td>
				          <td width="20" align="center">
				          	<img style="position:relative; left:-1px;" onclick="showCalendar('startDate')" src="core/common/images/timeico.gif" width="20" height="22" />
				          </td>
				        </tr>
				    </table>
			    </td>
			    <td class="layertdleft100"><span class="psred">*</span>合同结束时间：</td>
	     		<td class="layerright">
	     			<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="endDate" name="contract.endDate" type="text" readonly="readonly" 
				          	value='<fmt:formatDate value="${result.value.endDate}" pattern="yyyy-MM-dd"/>'
				          	class="inputauto" onclick="showCalendar('endDate')"/>
				          </td>
				          <td width="20" align="center">
				          	<img style="position:relative; left:-1px;" onclick="showCalendar('endDate')" src="core/common/images/timeico.gif" width="20" height="22" />
				          </td>
				        </tr>
			   		 </table>
			    </td>
	      	</tr>
	    	<tr>
				<td class="layertdleft100"><span class="psred">*</span>合同签订日期：</td>
		      	<td class="layerright"><table width="143" border="0" cellspacing="0" cellpadding="0">
		        	<tbody>
			          <tr>
			            <td>
			            	<input id="signDate" name="contract.signDate" readonly="readonly" type="text" 
				          	value='<fmt:formatDate value="${result.value.signDate}" pattern="yyyy-MM-dd"/>'
			            	class="inputauto" onclick="showCalendar('signDate')"/>
			            </td>
	          			<td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('signDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			          </tr>
		        	</tbody>
		     	</table></td>
			    <td class="layertdleft100"><span class="psred">*</span>登记日期：</td>
		      	<td class="layerright">
		      		<table width="143" border="0" cellspacing="0" cellpadding="0">
			        	<tbody>
				          <tr>
				            <td>
				            	<input id="receiveDate" name="contract.receiveDate" readonly="readonly" type="text" 
				          		value='<fmt:formatDate value="${result.value.receiveDate}" pattern="yyyy-MM-dd"/>'
				            	class="inputauto" onclick="showCalendar('receiveDate')"/>
				            </td>
		          			<td width="20" align="center">
		          				<img style="position:relative; left:-1px;" onclick="showCalendar('receiveDate')" src="core/common/images/timeico.gif" width="20" height="22" />
		          			</td>
				          </tr>
			        	</tbody>
		      		</table>
		      	</td>
	       	</tr>
	       	<tr>
	    	   	<td class="layertdleft100">总金额：</td>
		      	<td class="layerright">
		      		<input name="contract.contractAmount" 
		      		value='<fmt:formatNumber value="${result.value.contractAmount}" pattern="0.00" />'
		      		type="text" class="inputauto doubleformat"/>
		      	</td>
	    	    <td class="layertdleft100">预计花费：</td>
		      	<td class="layerright">
		      		<input name="contract.predictCost" 
		      		value='<fmt:formatNumber value="${result.value.predictCost}" pattern="0.00" />'
		      		type="text" class="inputauto doubleformat"/>
		      	</td>
	    	</tr>
	    	<tr>
	    	    <td class="layertdleft100">实际花费：</td>
	    		<td class="layerright">
	    			<input name="contract.actualCost" 
		      		value='<fmt:formatNumber value="${result.value.actualCost}" pattern="0.00" />'
	    			type="text" class="inputauto doubleformat"/>
	    		</td>
	    		<td class="layerright" colspan="2">
					<label><input name="contract.audit" <c:if test="${result.value.audit eq 'YES'}">checked="checked"</c:if> type="checkbox" onclick="changeState(this);" value="NO" /><span>未审核</span></label>
					<label><input name="contract.finished" <c:if test="${result.value.finished eq 'YES'}">checked="checked"</c:if>type="checkbox" onclick="changeState(this);" value="NO" /><span>完成标志</span></label>
			   		<label><input name="contract.published" <c:if test="${result.value.published eq 'YES'}">checked="checked"</c:if>type="checkbox" onclick="changeState(this);" value="NO" />公开标志</label>
		      	</td>
	    	</tr>
	       	<tr>
	       	    <td class="layertdleft100">合同简介：</td>
		    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
		    		<textarea name="contract.introduction" class="inputauto" style="resize:none;height:40px;">${result.value.introduction }</textarea>
		    	</td>
	       	</tr>
	       	<tr>
	       	    <td class="layertdleft100">备注信息：</td>
		    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
		    		<textarea name="contract.memo" class="inputauto" style="resize:none;height:40px;">${result.value.memo }</textarea>
		    	</td>
	       	</tr>
	  	</table>
	  </div>
	  
	  <!-- 结算流程 -->
	  <div class="divlays tabswitch" style="margin:0px;display:none;padding:0px;">
	  	<div class="basediv" style="margin: 0px;border-left:0px;border-right:0px;border-bottom:0px;">
			<%-- <div class="emailtop">
				<div class="leftemail">
					<ul>
						<%if(add){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="billAdd(${result.value.id})">
							<span><img src="core/common/images/emailadd.gif" /></span>新建
						</li>
						<%}%>
						<%if(del){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="billDeleteSelected()">
							<span><img src="core/common/images/emaildel.png"/></span>删除
						</li>
						<%} %>
					</ul>
				</div>
			</div> --%>
			<div></div>
			<table id="contractBill" width="100%">
				<tr>
					<td />
				</tr>
			</table>
		</div>
	  </div>
</div>
</form>
</body>
</html>

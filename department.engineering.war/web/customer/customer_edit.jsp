<%@page import="com.wiiy.engineering.activator.EngineeringActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		initTip();
		initForm();
		initNums();
	});
	
	function getCalendarScrollTop(){
		return $("#scrollDiv").scrollTop();
	}
	function initNums(){
		var info = $("input[name='customerInfo.userInfo']").val();
		var total = info.split(",");
		for(var i = 0;i<total.length;i++){
			$(".getNum").eq(i).val(total[i]);
		}
		$("#totalNums").val(getTotalNums);
	}
	
	function getNums(){
		var sum = "";
		$(".getNum").each(function(){
			sum += $(this).val()+",";
		});
		sum = sum.substr(0,sum.length-1);
		return sum;
	}
	
	function getTotalNums(){
		var sum = 0;
		$(".getNum").each(function(){
			sum += Number($(this).val());
		});
		return sum;
	}
	
	function onNumKeyUp(){
		$("#totalNums").val(getTotalNums);
		$("input[name='customerInfo.userInfo']").val(getNums());
	}
	
	function generateCode(){
		showTip("正在生成编号,请稍后……",9999999);
		$.post("<%=basePath%>customer!generateCode.action",function(data){
			if(data.result.success){
				showTip("生成编号成功",2000);
				$("#code").val(data.result.value);
			}
		});
	}
	function initForm(){
		$("#form1").validate({
			rules: {
				"customer.code":"required",
				"customer.name":"required",
				"customer.shortName":"required",
				"importName":"required",
				"customer.sourceId":"required",
				"customer.technicId":"required"
			},
			messages: {
				"customer.code":"请输入企业编号",
				"customer.name": "请输入企业名称",
				"customer.shortName":"请输入简称",
				"importName":"请选择引进人员",
				"customer.technicId":"请选择技术领域",
				"customer.sourceId":"请选择企业来源"
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
			        		setTimeout("getOpener().reloadList();parent.fb.end();", 2000);
			        	}
			        } 
			    });
			}
		});
	}
	function setSelectedUser(user){
		$("#importId").val(user.id);
		$("#importName").val(user.realName);
	}
	function selectUser(){
		fbStart('选择用户','<%=BaseAction.rootLocation%>/core/user!selectSelf.action',520,400);
	}
	
</script>
<style>
	.getNum{
		text-align:center;
	}
</style>
</head>

<body>
<form action="<%=basePath%>customer!update.action" method="post" name="form1" id="form1">
	<input name="customer.id" type="hidden" value="${result.value.id }"/>
	<input name="customerInfo.id" type="hidden" value="${result.value.customerInfo.id }"/>
	<input id="customerType" name="customer.customerType" type="hidden" value="${result.value.customerType }"/>
	<c:set value="${result.value}" var="customer"></c:set>
	<c:set value="${result.value.customerInfo}" var="customerInfo"></c:set>
	<div id="scrollDiv" style=" position:relative;">			
	<div class="basediv selectedDiv">
		<div class="titlebg">基本信息</div>
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>名称：</td>
					<td class="layerright" colspan="3"><input id="name" value="${customer.name }" name="customer.name" type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>编号：</td>
					<td width="35%" class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="code" name="customer.code" value="${customer.code }" type="text" class="inputauto" /></td>
								<td width="80" align="center"><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
							</tr>
						</table>
					</td>
					<td class="layertdleft100"><span class="psred">*</span>简称：</td>
					<td width="35%" class="layerright"><input id="shortName" value="${customer.shortName }" name="customer.shortName" type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>来源：</td>
					<td width="35%" class="layerright"><dd:select id="sourceId" checked="customer.sourceId" name="customer.sourceId" key="customer.0002"/></td>
					<td class="layertdleft100"><span class="psred">*</span>所属行业：</td>
					<td width="35%" class="layerright"><dd:select id="technicId" checked="customer.technicId" name="customer.technicId" key="customer.0001"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">类别：</td>
					<td width="35%" class="layerright"><enum:select id="type" name="customer.type" checked="customer.type" type="com.wiiy.common.preferences.enums.CustomerTypeEnum"/></td>
					<td class="layertdleft100"><span class="psred">*</span>引进人：</td>
					<td width="35%" class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="importId" name="customer.refereeId" type="hidden" class="inputauto" value="${customer.referee.id }" /><input id="importName" value="${customer.referee.realName }" name="importName" readonly="readonly" type="text" class="inputauto" onclick="selectUser(2)" /></td>
								<td width="25"><img style="position: relative;left:-1px;" onclick="selectUser()" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="hackbox"></div>
	</div>
	<div style="height:233px;">
		<div class="apptab" id="tableid">
			<ul>
				<%
					int flag=-1;
						boolean company = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_supplier_add_company") ||
								EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_customer_add_company");
						boolean register = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_supplier_add_register") ||
								EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_customer_add_register");
						boolean ohter = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_supplier_add_other") ||
								EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_customer_add_other");
						if(register){flag++;
				%>
				<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">注册信息</li>
				<%} %>
				<%if(company){flag++; %>
				<li class="apptabli<%if(flag==0){ %>over<%} %>"  onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">公司信息</li>
				<%} %>
				<%if(ohter){ flag++;%>
				<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">其它</li>
				<%} %>
			</ul>
		</div>
		<%int flag2=-1;if(register){flag2++; %>
		<div class="basediv tabswitch" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
			<div class="divlays" style="margin:0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="layertdleft100">注册时间：</td>
						<td width="35%" class="layerright">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><input id="regTime" name="customerInfo.regTime"
									value='<fmt:formatDate value="${customerInfo.regTime }" pattern="yyyy-MM-dd" />' type="text" readonly="readonly" class="inputauto" onclick="showCalendar('regTime');"/></td>
									<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="showCalendar('regTime');"/></td>
								</tr>
							</table>
						</td>
						<td class="layertdleft100">注册类型：</td>
						<td width="35%" class="layerright"><dd:select checked="customerInfo.regTypeId" name="customerInfo.regTypeId" key="customer.0004"/></td>
					</tr>
					<tr>
						<td class="layertdleft100">注册资本：</td>
						<td class="layerright">
							<input name="customerInfo.regCapital"
							value='<fmt:formatNumber value="${customerInfo.regCapital }" pattern="0.00"/>' type="text" value="0.00" class="inputauto" style="width:90px;"/> 万元 <dd:select checked="customerInfo.currencyTypeId" name="customerInfo.currencyTypeId" key="project.0001"/>
						</td>
						<td class="layertdleft100">工商注册号：</td>
						<td class="layerright"><input name="customerInfo.businessNumber" value="${customerInfo.businessNumber}" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td class="layertdleft100">组织机构代码：</td>
						<td class="layerright"><input name="customerInfo.organizationNumber" value="${customerInfo.organizationNumber }" type="text" class="inputauto" /></td>
						<td class="layertdleft100">法定代表人：</td>
						<td class="layerright"><input name="customerInfo.legalPerson" value="${customerInfo.legalPerson }" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td class="layertdleft100">国税登记号：</td>
						<td width="35%" class="layerright"><input id="taxNumberG" value="${customerInfo.taxNumberG }" name="customerInfo.taxNumberG" type="text" class="inputauto" /></td>
						<td class="layertdleft100">地税登记号：</td>
						<td width="35%" class="layerright"><input id="taxNumberD" value="${customerInfo.taxNumberD }" name="customerInfo.taxNumberD" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td class="layertdleft100">证件类型：</td>
						<td class="layerright"><dd:select checked="customerInfo.documentTypeId" name="customerInfo.documentTypeId" key="customer.0005"/></td>
						<td class="layertdleft100">E-mail：</td>
						<td class="layerright"><input name="customerInfo.regMail" value="${customerInfo.regMail }" type="text" class="inputauto" /></td>						
					</tr>
					<tr>
						<td class="layertdleft100">证件号：</td>
						<td class="layerright"><input name="customerInfo.documentNumber" value="${customerInfo.documentNumber }" type="text" class="inputauto" /></td>
						<td class="layertdleft100">移动电话：</td>
						<td class="layerright"><input name="customerInfo.cellphone" value="${customerInfo.cellphone }" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td class="layertdleft100">注册地址：</td>
						<td class="layerright"><input name="customerInfo.regAddress" value="${customerInfo.regAddress }" type="text" class="inputauto" /></td>
						<td class="layertdleft100">营业截至日期：</td>
						<td class="layerright">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><input id="businessExpireDate" readonly="readonly"
									value='<fmt:formatDate value="${customerInfo.businessExpireDate }" pattern="yyyy-MM-dd" />' name="customerInfo.businessExpireDate" type="text" class="inputauto" onclick="showCalendar('businessExpireDate');"/></td>
									<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="showCalendar('businessExpireDate');"/></td>
								</tr>
		        			</table>
		        		</td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
		<%if(company){ flag2++;%>
		<div class="basediv tabswitch" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
			<div class="divlays" style="margin:0px;">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="15%" class="layertdleft100">联系地址：</td>
						<td class="layerright"><input name="customerInfo.address" value="${customerInfo.address }" type="text" class="inputauto"/></td>
						<td rowspan="6" style="vertical-align:top; padding-left:2px;">
				        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td class="layertdleft100" style="text-align:left; padding-left:10px;">企业总人数：</td>
				                <td class="layerright" colspan="3"><label><input id="totalNums" class="inputauto" readonly="readonly"/><input value="${customerInfo.userInfo }" name="customerInfo.userInfo" type="hidden" /></label></td>
				              </tr>
				              <tr>
				                <td class="layertdleft100" style="text-indent:10px;"><span style="float:left;">其中</span>博士后：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				                <td class="layertdleft100">博士：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				                
				              </tr>
				              <tr>
				                <td class="layertdleft100">硕士：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				                <td class="layertdleft100">本科：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				              </tr>
				              <tr>
				                <td class="layertdleft100">高级职称：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				                <td class="layertdleft100">中级职称：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();" /></label></td>
				              </tr>
				              <tr>
				                <td class="layertdleft100">初级职称：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				                <td class="layertdleft100">其他：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				              </tr>
				              <tr>
				                <td class="layertdleft100">留学生：</td>
				                <td class="layerright"><label><input type="text" class="inputauto getNum" onkeyup="value=value.replace(/[^\d]/g,'');onNumKeyUp();"/></label></td>
				              </tr>
				            </table>
				        </td>
        			</tr>
      				<tr>
						<td width="15%" class="layertdleft100">办公电话：</td>
						<td class="layerright"><input name="customerInfo.officePhone" value="${customerInfo.officePhone }" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td width="15%" class="layertdleft100">传真：</td>
						<td class="layerright"><input name="customerInfo.fax" value="${customerInfo.fax }" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td width="15%" class="layertdleft100">邮编：</td>
						<td class="layerright"><input name="customerInfo.zipCode" value="${customerInfo.zipCode }" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td class="layertdleft100">网址：</td>
						<td width="35%" class="layerright"><input name="customerInfo.webSite" value="${customerInfo.webSite }" type="text" class="inputauto" /></td>
					</tr>
					<tr>
						<td width="15%" class="layertdleft100">E-mail：</td>
						<td class="layerright"><input name="customerInfo.email" value="${customerInfo.email }" type="text" class="inputauto" /></td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
		<%if(ohter){ flag2++;%>
		<div class="basediv tabswitch" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>">
			<div class="divlays" style="margin:0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="layertdleft100">公司描述：</td>
						<td colspan="3" class="layerright" style="padding-bottom:2px;"><textarea name="customerInfo.description"  class="inputauto" style="height:70px;resize:none;">${customerInfo.description }</textarea></td>
					</tr>
					<tr>
						<td class="layertdleft100">备注：</td>
						<td colspan="3" class="layerright"><textarea name="customerInfo.memo"  class="inputauto"  style="height:120px;resize:none;">${customerInfo.memo }</textarea></td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
	</div>
	<div class="buttondiv">
		<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end()"/></label>
	</div>
	</div>
</form>
</body>
</html>
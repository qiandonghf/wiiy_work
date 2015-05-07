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
		initNums();
	});
	
	function initNums(){
		var info = "${result.value.customerInfo.userInfo}";
		var total = info.split(",");
		for(var i = 0;i<total.length;i++){
			$(".getNum").eq(i).text(total[i]);
		}
		$("#totalNums").text(getTotalNums);
	}
	
	function getTotalNums(){
		var sum = 0;
		$(".getNum").each(function(){
			sum += Number($(this).text());
		});
		return sum;
	}
	
	/* function getNums(){
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
	} */
	
</script>
<style>
	.getNum{
		text-align:center;
	}
</style>
</head>

<body>
	<c:set value="${result.value}" var="customer"></c:set>
	<c:set value="${result.value.customerInfo}" var="customerInfo"></c:set>
	<div id="scrollDiv" style=" position:relative;">			
	<div class="basediv selectedDiv">
		<div class="titlebg">基本信息</div>
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100">名称：</td>
					<td class="layerright" colspan="3">${customer.name }</td>
				</tr>
				<tr>
					<td class="layertdleft100">编号：</td>
					<td width="35%" class="layerright">${customer.code }</td>
					<td class="layertdleft100">简称：</td>
					<td width="35%" class="layerright">${customer.shortName }</td>
				</tr>
				<tr>
					<td class="layertdleft100">来源：</td>
					<td width="35%" class="layerright">${customer.source.dataValue }</td>
					<td class="layertdleft100">所属行业：</td>
					<td width="35%" class="layerright">${customer.technic.dataValue }</td>
				</tr>
				<tr>
					<td class="layertdleft100">类别：</td>
					<td width="35%" class="layerright">${customer.type.title }</td>
					<td class="layertdleft100">引进人：</td>
					<td width="35%" class="layerright">${customer.referee.realName }</td>
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
						<td width="35%" class="layerright"><fmt:formatDate value="${customerInfo.regTime }" pattern="yyyy-MM-dd" /></td>
						<td class="layertdleft100">注册类型：</td>
						<td width="35%" class="layerright">${customerInfo.regType.dataValue }</td>
					</tr>
					<tr>
						<td class="layertdleft100">注册资本：</td>
						<td class="layerright"><fmt:formatNumber value="${customerInfo.regCapital }" pattern="0.00"/></td>
						<td class="layertdleft100">工商注册号：</td>
						<td class="layerright">${customerInfo.businessNumber}</td>
					</tr>
					<tr>
						<td class="layertdleft100">组织机构代码：</td>
						<td class="layerright">${customerInfo.organizationNumber }</td>
						<td class="layertdleft100">法定代表人：</td>
						<td class="layerright">${customerInfo.legalPerson }</td>
					</tr>
					<tr>
						<td class="layertdleft100">国税登记号：</td>
						<td width="35%" class="layerright">${customerInfo.taxNumberG }</td>
						<td class="layertdleft100">地税登记号：</td>
						<td width="35%" class="layerright">${customerInfo.taxNumberD }</td>
					</tr>
					<tr>
						<td class="layertdleft100">证件类型：</td>
						<td class="layerright">${customerInfo.documentType.dataValue }</td>
						<td class="layertdleft100">E-mail：</td>
						<td class="layerright">${customerInfo.regMail }</td>						
					</tr>
					<tr>
						<td class="layertdleft100">证件号：</td>
						<td class="layerright">${customerInfo.documentNumber }</td>
						<td class="layertdleft100">移动电话：</td>
						<td class="layerright">${customerInfo.cellphone }</td>
					</tr>
					<tr>
						<td class="layertdleft100">注册地址：</td>
						<td class="layerright">${customerInfo.regAddress }</td>
						<td class="layertdleft100">营业截至日期：</td>
						<td class="layerright"><fmt:formatDate value="${customerInfo.businessExpireDate }" pattern="yyyy-MM-dd" /></td>
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
						<td class="layerright">${customerInfo.address }</td>
						<td rowspan="6" style="vertical-align:top; padding-left:2px;">
				        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td class="layertdleft100" style="text-align:left; padding-left:10px;">企业总人数：</td>
				                <td class="layerright" colspan="3"><span id="totalNums"></span></td>
				              </tr>
				              <tr> 
				                <td class="layertdleft100" style="text-indent:10px;"><span style="float:left;">其中</span>博士后：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				                <td class="layertdleft100">博士：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				                
				              </tr>
				              <tr>
				                <td class="layertdleft100">硕士：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				                <td class="layertdleft100">本科：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				              </tr>
				              <tr>
				                <td class="layertdleft100">高级职称：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				                <td class="layertdleft100">中级职称：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				              </tr>
				              <tr>
				                <td class="layertdleft100">初级职称：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				                <td class="layertdleft100">其他：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				              </tr>
				              <tr>
				                <td class="layertdleft100">留学生：</td>
				                <td class="layerright"><span class="getNum"></span></td>
				              </tr>
				            </table>
				        </td>
        			</tr>
      				<tr>
						<td width="15%" class="layertdleft100">办公电话：</td>
						<td class="layerright">${customerInfo.officePhone }</td>
					</tr>
					<tr>
						<td width="15%" class="layertdleft100">传真：</td>
						<td class="layerright">${customerInfo.fax }</td>
					</tr>
					<tr>
						<td width="15%" class="layertdleft100">邮编：</td>
						<td class="layerright">${customerInfo.zipCode }</td>
					</tr>
					<tr>
						<td class="layertdleft100">网址：</td>
						<td width="35%" class="layerright">${customerInfo.webSite }</td>
					</tr>
					<tr>
						<td width="15%" class="layertdleft100">E-mail：</td>
						<td class="layerright">${customerInfo.email }</td>
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
						<td colspan="3" class="layerright" style="padding-bottom:2px;">
							<textarea name="customerInfo.description"  class="inputauto" readonly="readonly" style="height:70px;resize:none;border:0px;padding-left:0px;color:#666;">${customerInfo.description }</textarea>
						</td>
					</tr>
					<tr>
						<td class="layertdleft100">备注：</td>
						<td colspan="3" class="layerright">
							<textarea name="customerInfo.memo"  class="inputauto" readonly="readonly" style="height:120px;resize:none;border:0px;padding-left:0px;color:#666;">${customerInfo.memo }</textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
	</div>
	<div class="buttondiv" style="height:5px;">
	</div>
	</div>
</body>
</html>
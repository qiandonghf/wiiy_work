<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
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
	});
	
	function getCalendarScrollTop(){
		return $("#scrollDiv").scrollTop();
	}
	function loadCustomerLabel(){
		$.post("<%=basePath%>customerLabel!loadSaleCustomerLabel.action",function(data){
			$("#customerLabelList").children("h1").remove();
			$("#customerLabelList").children("ul").remove();
			data.result.value[data.result.value.length] = {customerCategory:{name:"我的分组"},customerLabelList:data.customerLabelList};
			for(var j = 0; j < data.result.value.length; j++){
				var dto = data.result.value[j];
				var li = $("<li></li>");
				if(dto.customerLabelList.length==0)continue;
				for(var i = 0; i < dto.customerLabelList.length; i++){
					var label = dto.customerLabelList[i];
					li.append($("<a></a>",{href:"javascript:void(0)",click:function(){
						$("#customerLabelList").hide();
						var newLabel = $(this).find("input").val();
						var exist = false;
						$.each($(".customerLabelId"),function(){
							if($(this).val()==newLabel) exist = true;
						});
						if(exist)return;
						$("#labelUl").append(
							$("<li></li>",{
								mouseover:function(){$(this).find('span').show()},
								mouseout:function(){$(this).find('span').hide()}
							}).append($(this).text()).append($("<input type=\"hidden\" name=\"ids\" class=\"customerLabelId\" value=\""+$(this).find("input").val()+"\"/>")
							).append($("<span></span>",{click:function(){$(this).parent().remove();}}).hide())
						);
					}}).append(label.name).append($("<input type=\"hidden\" value=\""+label.id+"\"/>")));
				}
				$("#customerLabelList").append($("<h1></h1>").append(dto.customerCategory.name)).append($("<ul></ul>").append(li)).show();
			}
			if(data.result.value.length==0){
				$("#customerLabelList").append($("<h1></h1>").append("暂无标签")).show();
			}
		});
	}
	function initForm(){
		$("#form1").validate({
			rules: {
				"customer.name":"required"
			},
			messages: {
				"customer.name": "请输入客户名称"
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
			        		setTimeout("getOpener().reloadList('customer');parent.fb.end();", 2000);
			        	}
			        } 
			    });
			}
		});
	}
	function setSelectedUser(user){
		$("#userId").val(user.id);
		$("#userName").val(user.realName);
	}
	function selectUser(obj){
		userType=obj;
		fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!selectSelf.action',520,400);
	}
</script>
</head>

<body>
<form action="<%=basePath %>customer!save.action" method="post" name="form1" id="form1">
	<input id="ids" name="ids" type="hidden"/>
	<div id="scrollDiv" style=" position:relative;">			
	<div class="basediv">
		<div class="titlebg">客户信息</div>
		<div class="divlays" style="margin:0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="layertdleft100"><span class="psred">*</span>姓名：</td>
					<td width="35%" class="layerright"><input id="name" name="customer.name" type="text" class="inputauto" /></td>
					<td class="layertdleft100">性别：</td>
					<td class="layerright"><enum:radio name="customer.gender" checked="Male" type="com.wiiy.commons.preferences.enums.GenderEnum"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">客户类型：</td>
					<td class="layerright"><enum:select name="customer.customerType" type="com.wiiy.sale.preferences.enums.CustomerTypeEnum"/></td>
					<td class="layertdleft100">客户等级：</td>
					<td class="layerright"><enum:select name="customer.level" type="com.wiiy.sale.preferences.enums.CustomerLevelEnum"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">年龄：</td>
					<td class="layerright"><input id="age" name="customer.age" type="text" class="inputauto"/></td>
					<td class="layertdleft100">证件号：</td>
					<td class="layerright"><input id="idNO" name="customer.idNO" type="text" class="inputauto"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">手机：</td>
					<td class="layerright"><input id="mobile" name="customer.mobile" type="text" class="inputauto"/></td>
					<td class="layertdleft100">固话：</td>
					<td class="layerright"><input id="phone" name="customer.phone" type="text" class="inputauto"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">email：</td>
					<td class="layerright"><input id="email" name="customer.email" type="text" class="inputauto"/></td>
					<td class="layertdleft100">婚否：</td>
					<td class="layerright"><enum:radio name="customer.marriage" checked="YES" type="com.wiiy.commons.preferences.enums.BooleanEnum"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">学历：</td>
					<td class="layerright"><dd:select name="customer.educationId" key="sale.0001"/></td>
					<td class="layertdleft100">职业：</td>
					<td class="layerright"><dd:select id="professionId" name="customer.professionId" key="sale.0002"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">家庭年收入：</td>
					<td class="layerright"><dd:select name="customer.familyIncomeId" key="sale.0003"/></td>
					<td class="layertdleft100">客户区域：</td>
					<td class="layerright"><dd:select name="customer.clientAreaId" key="sale.0004"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">讯息来源：</td>
					<td class="layerright"><dd:select name="customer.sourceId" key="sale.0005"/></td>
					<td class="layertdleft100">户型需求：</td>
					<td class="layerright"><dd:select name="customer.huxingId" key="sale.0006"/></td>
				</tr>
				<tr>
				  <td class="layertdleft100">可接受价格区间：</td>
				  <td class="layerright"><input id="acceptPrice" name="customer.acceptPrice" type="text" class="inputauto"/></td>
				  <td class="layertdleft100">家庭住址：</td>
			      <td  class="layerright" colspan="3"><textarea name="customer.address" rows="3" class="textareaauto"></textarea></td>
		     	</tr>
		     	<tr>
					<td class="layertdleft100">抗性分析：</td>
					<td class="layerright"><dd:select name="customer.resistanceId" key="sale.0007"/></td>
					<td class="layertdleft100"><span class="psred">*</span>置业顾问：</td>
					<td class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="userId" name="customer.userId" type="hidden" class="inputauto" /><input id="userName" readonly="readonly" type="text" class="inputauto" onclick="selectUser(1)" /></td>
								<td width="25"><img style="position: relative;left:-1px;" onclick="selectUser(1)" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
							</tr>
						</table>
					</td>
				</tr>
		     	<tr>
					<td class="layertdleft100">地段：</td>
					<td class="layerright"><enum:select name="customer.section" type="com.wiiy.sale.preferences.enums.SatisfactionEnum"/></td>
					<td class="layertdleft100">户型：</td>
					<td class="layerright"><enum:select name="customer.houseType" type="com.wiiy.sale.preferences.enums.SatisfactionEnum"/></td>
				</tr>
		     	<tr>
					<td class="layertdleft100">价格：</td>
					<td class="layerright"><enum:select name="customer.cost" type="com.wiiy.sale.preferences.enums.SatisfactionEnum"/></td>
					<td class="layertdleft100">环境：</td>
					<td class="layerright"><enum:select name="customer.environment" type="com.wiiy.sale.preferences.enums.SatisfactionEnum"/></td>
				</tr>
		     	<tr>
					<td class="layertdleft100">需求级别：</td>
					<td class="layerright"><enum:select name="customer.demandLevel" type="com.wiiy.sale.preferences.enums.DemandEnum"/></td>
					<td class="layertdleft100">购房动机：</td>
					<td class="layerright"><dd:select name="customer.motivationId" key="sale.0008"/></td>
				</tr>
				
				<tr>
					<td class="layertdleft100">企业标签：</td>
					<td colspan="3" class="layerright">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="28">
									<img src="core/common/images/outdiv.gif" width="20" height="22" style="cursor:pointer;" onclick="loadCustomerLabel();" />
									<div class="company_label" style="display:none;overflow-x: hidden;overflow-y:scroll;height:200px;" id="customerLabelList">
										<div class="titlebg"><span style="float:right"><img src="core/common/images/companyclose.gif" onclick="$(this).parent().parent().parent().hide()" style="cursor:pointer;" /></span>企业标签</div>
									</div>
								</td>
								<td class="customerdiv">
									<div style=" width:auto; height:50px; overflow-x:hidden; overflow-y:scroll">
									<ul id="labelUl"></ul>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="hackbox"></div>
	</div>
	<div class="buttondiv">
		<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end()"/></label>
	</div>
	</div>
</form>
</body>
</html>
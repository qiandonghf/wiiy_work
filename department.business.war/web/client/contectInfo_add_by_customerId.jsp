<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/calendar/calendar.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		loadContectsByCustomerId('${result.value.id}');
	});

	function checkForm(){
		 if(notNull("contectId","联系人")
				&&checkSelect("type","交往类型")
				&&notNull("startTime","交往日期")
				&&notNull("content","内容")
		 	){
			 	$("#contectInfoStartTime").val($("#startTime").val()+" "+$("#startHour").val()+":"+$("#startMinute").val()+":00");
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
	}
	function setSelectedCustomer(customer){
		$("#customerId").val(customer.id);
		$("#customerName").val(customer.name);
		loadContectsByCustomerId(customer.id);
	}
	function loadContectsByCustomerId(id){
		$.post("<%=basePath%>contect!loadContectsByCustomerId.action?id="+id,function(data){
			if(data.result.success){
				var list = data.result.value;
				var contectId = $("#contectId");
				contectId.empty();
				contectId.append($("<option></option>",{value:""}).append("---请选择---"));
				for(var i = 0; i < list.length; i++){
					var contect = list[i];
					contectId.append($("<option></option>",{value:contect.id}).append(contect.name));
				}
			} else {
				showTip(data.result.msg,2000);
			}
		});
	}
</script>
</head>

<body>
<form action="<%=basePath %>contectInfo!save.action" method="post" name="form1" id="form1">
<div class="basediv">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
	      <td class="layertdleft100">企业名称：</td>
	      <td class="layerright">${result.value.name}<input id="customerId" name="contectInfo.customerId" type="hidden" value="${result.value.id}"/></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>联系人：</td>
        <td class="layerright"><label>
          <select id="contectId" name="contectInfo.contectId"><option value="">---请选择---</option></select>
        </label></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>交往类型：</td>
        <td class="layerright">
      	<dd:select id="type" name="contectInfo.typeId" key="business.0016"/>
    	</td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>交往日期：</td>
	    <td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	         	<td width="120">
	         		<input name="startTime" readonly="readonly" type="text" class="inputauto" id="startTime" onclick="return showCalendar('startTime');" />
	         		<input id="contectInfoStartTime" name="contectInfo.startTime" type="hidden" value="test"/>
	         	</td>
	         	<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime');" /></td>
	         	<td>
					<select id="startHour">
					<c:forEach begin="0" end="23" var="s">
						<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
					</c:forEach>
					</select>&nbsp;时
					<select id="startMinute">
						<c:forEach begin="0" end="59" var="s" step="5">
							<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
						</c:forEach>
					</select>&nbsp;分
				</td>
	         	<td></td>	
	        </tr>
	    </table></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>内容：</td>
        <td class="layerright"><textarea id="content" name="contectInfo.content" style="height:115px;" class="textareaauto"></textarea></td>
      </tr>
      <tr>
        <td class="layertdleft100">备注：</td>
        <td class="layerright" style="margin-top:4px;"><textarea name="contectInfo.memo" style="height:30px;" class="textareaauto"></textarea></td>
      </tr>
    </table>
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<div class="buttondiv">
  <label><input name="Submit" type="button" class="savebtn" value="" onclick="checkForm()"/></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

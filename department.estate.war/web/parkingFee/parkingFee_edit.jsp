<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.estate.entity.ParkingFee"%>
<%@page import="com.wiiy.hibernate.Result"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initForm();
	});
	
	function initForm(){
		$("#form1").validate({
			rules:{
				"parkingFee.licenseNo":"required",
				"parkingFee.money":"number"
			},
			messages: {
				"parkingFee.licenseNo":"请输入车牌号",
				"parkingFee.money":"请正确填写金额"
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
				if(notNull("startDate","开始时间")&&notNull("endDate","结束时间")){
					$("#startDateId").val($("#startDate").val()+" "+$("#startHour").val()+":"+$("#startMinute").val()+":00");
					$("#endDateId").val($("#endDate").val()+" "+$("#endHour").val()+":"+$("#endMinute").val()+":00");
					if($("#startDate").val()>$("#endDate").val()){
						showTip("停放开始时间不能大于停放结束时间",2000);
						return;
					}
				$('#form1').ajaxSubmit({ 
			        dataType: 'json',		        
			        success: function(data){
		        		showTip(data.result.msg,2000);
			        	if(data.result.success){
			        		setTimeout("parent.fb.end();getOpener().reloadList();", 2000);
			        	}
			        } 
			    });
				}
			}
		});
	}
	/*车牌*/
	function setSelectedParking(parking){
		$("#licenseNoId").val(parking.id);
		$("#licenseNo").val(parking.licenseTag);
		$("#name").val(parking.name);
	}
	/*车位*/ 
	function setSelectedPark(park){
		$("#parkingManage").val(park.parkingId);
		$("#parkingManageId").val(park.id);
	}
</script>
</head>
<body>
<form id="form1" name="form1" method="post" action="<%=basePath%>parkingFee!update.action">
<input type="hidden" value="${param.id }" name="parkingFee.id" />
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    	<td class="layertdleft100"><span class="psred">*</span>车牌号：</td>
		<td class="layerright">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td width="370">
						<input id="licenseNoId" value="" name="parkingFee.licenseNoId" type="hidden" value="${result.value.licenseNoId}"/>
						<input id="licenseNo" name="parkingFee.licenseNo"  value="${result.value.licenseNo }" type="text" class="inputauto" />
						</td>
					<td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="fbStart('选择车辆','<%=basePath %>vehicleManagement!select.action',520,400);" style="cursor:pointer"/></td>
				</tr>
			</table>
		</td>
       </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    	<td class="layertdleft100">车位：</td>
		<td class="layerright">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td width="370">
						<input id="parkingManageId" value="" name="parkingFee.parkingManageId" type="hidden" value="${result.value.parkingManageId}"/>
						<input id="parkingManage" name="parkingFee.parkingManage" value="${result.value.parkingManage }" type="text" class="inputauto" />
					</td>
					<td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="fbStart('选择车位','<%=basePath %>parkingManage!select.action',520,400);" style="cursor:pointer"/></td>
				</tr>
			</table>
		</td>
       </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td class="layertdleft100">单位/个人：</td>
	      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="193">
	          	<input id="name" name="parkingFee.name"  value="${result.value.name }" type="text" class="inputauto" /></td>
	        </tr>
	      </table>
	      </td>
	    </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td class="layertdleft100">收费人：</td>
	     	 <td class="layerright">
	      	   <input type="text" name="parkingFee.toller" value="${result.value.toller }" class="inputauto"/> 
	        </td>  
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>金额：</td>
      <td class="layerright"><input name="parkingFee.money"  value="<fmt:formatNumber value="${result.value.money}" pattern="#0.00"/>" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"  type="text" class="inputauto" /></td>
    </tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr >
        <td class="layertdleft100"><span class="psred">*</span>开始时间：</td>
        <td colspan="3" class="layerright">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          	<td>
		          		<input name="startDate" readonly="readonly" type="text" class="inputauto" id="startDate"  value="<fmt:formatDate value="${result.value.startDate }" pattern="yyyy-MM-dd"/>" onclick="return showCalendar('startDate');" />
		          		<input id="startDateId" name="parkingFee.startDate" type="hidden" value="test"/>
		          	</td>
		          	<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startDate');" /></td>
		        	<td style="padding-left: 4px;">
		        		<%
		        			Result<ParkingFee> result = (Result<ParkingFee>)request.getAttribute("result"); 
        					if(result.getValue().getStartDate().toString()!=""){
        						Calendar startDate = Calendar.getInstance();
        						startDate.setTime(result.getValue().getStartDate());
        						int hour = startDate.get(Calendar.HOUR_OF_DAY);
        						int minute = startDate.get(Calendar.MINUTE);
		        		%>
						<select id="startHour">
							<c:forEach begin="0" end="23" var="s">
								<option value="<c:if test="${s<10}">0</c:if>${s}" <%if(Integer.parseInt(pageContext.getAttribute("s").toString())==hour) out.println("selected=\"selected\"");%>>${s}</option>
							</c:forEach>
						</select>&nbsp;时
						<select id="startMinute">
							<c:forEach begin="0" end="59" var="s" step="1">
								<option value="<c:if test="${s<10}">0</c:if>${s}" <%if(Integer.parseInt(pageContext.getAttribute("s").toString())==minute) out.println("selected=\"selected\"");%>>${s}</option>
							</c:forEach>
						</select>&nbsp;分
						<%} %><%else {%>
							<select id="startHour">
							<c:forEach begin="0" end="23" var="s">
								<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
							</c:forEach>
							</select>&nbsp;时
							<select id="startMinute">
								<c:forEach begin="0" end="59" var="s" step="1">
									<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
								</c:forEach>
							</select>&nbsp;分
						<%} %>
					</td>
		        </tr>
     		 </table>
      	</td>
      </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>结束时间：</td>
        <td colspan="3" class="layerright">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          	<td>
		          		<input name="endDate" readonly="readonly" type="text" class="inputauto" id="endDate"  value="<fmt:formatDate value="${result.value.endDate }" pattern="yyyy-MM-dd"/>" onclick="return showCalendar('endDate');" />
		          		<input id="endDateId" name="parkingFee.endDate" type="hidden" value="test"/>
		          	</td>
		          	<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('endDate');" /></td>
		        	<td style="padding-left: 4px;">
		        		<%
		        		Result<ParkingFee> result1 = (Result<ParkingFee>)request.getAttribute("result"); 
        					if(result1.getValue().getEndDate().toString()!=""){
        						Calendar endDate = Calendar.getInstance();
        						endDate.setTime(result1.getValue().getEndDate());
        						int hour = endDate.get(Calendar.HOUR_OF_DAY);
        						int minute = endDate.get(Calendar.MINUTE);
		        		%>
						<select id="endHour">
							<c:forEach begin="0" end="23" var="s">
								<option value="<c:if test="${s<10}">0</c:if>${s}" <%if(Integer.parseInt(pageContext.getAttribute("s").toString())==hour) out.println("selected=\"selected\"");%>>${s}</option>
							</c:forEach>
						</select>&nbsp;时
						<select id="endMinute">
							<c:forEach begin="0" end="59" var="s" step="1">
								<option value="<c:if test="${s<10}">0</c:if>${s}" <%if(Integer.parseInt(pageContext.getAttribute("s").toString())==minute) out.println("selected=\"selected\"");%>>${s}</option>
							</c:forEach>
						</select>&nbsp;分
						<%} %><%else {%>
							<select id="endHour">
							<c:forEach begin="0" end="23" var="s">
								<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
							</c:forEach>
							</select>&nbsp;时
							<select id="endMinute">
								<c:forEach begin="0" end="59" var="s" step="1">
									<option value="<c:if test="${s<10}">0</c:if>${s}">${s}</option>
								</c:forEach>
							</select>&nbsp;分
						<%} %>
					</td>
		        </tr>
     		 </table>
      	</td>
      </tr>
      </table>
    <table>
    <tr>
		<td class="layertdleft100"><span class="psred">*</span>关闭提醒：</td>
		<td class="layerright" id="isRemind"><enum:radio  name="parkingFee.isRemind"  type="com.wiiy.commons.preferences.enums.BooleanEnum" checked="result.value.isRemind"/></td>
	</tr>
    </table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100">备注：</td>
      <td  class="layerright">
      	<textarea name="parkingFee.memo"  rows="5" class="textareaauto">${result.value.memo }</textarea>
      </td>
    </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<!--basediv-->
<!--//basediv-->
<div class="buttondiv">
  <label><input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

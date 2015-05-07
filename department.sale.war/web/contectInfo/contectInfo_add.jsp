<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wiiy.sale.preferences.enums.InfoEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %> 
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
	var retutnVisit = "${result.value.returnVisit}"; 
	$(document).ready(function() {
		returnVisit = trim("${result.value.returnVisit}");
		returnVisit = returnVisit=="" ? getDate() : returnVisit;
		initTip();
		initDate();
		$("#timeClick").change(function(){
			if($(this).attr("checked")){
				$(".visittime").show();
				$("input[name='contectInfo.returnVisit']").val(returnVisit);
			}else{
				$("input[name='contectInfo.returnVisit']").val("");
				$(".visittime").hide();
			}
		});
		if("${result.value.returnVisit}" == ''){
			$("#timeClick").attr("checked","checked");
		}
	});

	function getDate(){
		<%Calendar c = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DATE, 3);
		String returnVisit = s.format(c.getTime());
		%>
		return "<%=returnVisit%>";
	}
	
	function checkForm(){
		 if(notNull("customerName","客户名称")
				&&checkSelect("type","交往类型")
				&&notNull("time","交往日期")
				&&notNull("content","内容")
		 	){
			 	$("#contectInfoTime").val($("#time").val()+" "+$("#startHour").val()+":"+$("#startMinute").val()+":00");
				$('#form1').ajaxSubmit({ 
		        dataType: 'json',
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("getOpener().reloadList('contectInfo');parent.fb.end();", 2000);
		        	}
		        } 
		    });
		} 
	}
	function setSelectedCustomer(customer){
		$("#customerId").val(customer.id);
		$("#customerName").val(customer.name);
		/* loadContectsByCustomerId(customer.id); */
	}
	
	function setSelectedUser(user){
		$("#receiverId").val(user.id);
		$("#receiver").val(user.realName);
	}
	
	function initDate(){
		<% c = Calendar.getInstance();
	    s = new SimpleDateFormat("yyyy-MM-dd");
		String time = s.format(c.getTime());
		String startHour = c.getTime().getHours()+"";
		String startMinute = c.getTime().getMinutes()+"";
		System.out.println(startHour+":"+startMinute);
		String returnTime = s.format(c.getTime());
		c.add(Calendar.DATE, 3);
		returnVisit = s.format(c.getTime());
		%>
		var startDate = '<%=time%>';
		var returnDate = '<%=returnTime%>';
		var returnVisit = '<%=returnVisit%>';
		$("#time").val(startDate);
		$("#startHour").val("<%=startHour%>");
		$("#startMinute").val("<%=startMinute%>");
		$("#returnTime").val(returnDate);
		$("#returnVisit").val(returnVisit);
	}
	
	<%-- function loadContectsByCustomerId(id){
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
	} --%>
</script>
</head>

<body>
<form action="<%=basePath %>contectInfo!save.action" method="post" name="form1" id="form1">
<input name="contectInfo.infoStatus" type="hidden" value="<%=InfoEnum.NORMAL%>"/>
<div class="basediv">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
	      <td class="layertdleft100"><span class="psred">*</span>客户名称：</td>
	      <td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="370"><input id="customerId" name="contectInfo.customerId" type="hidden" />
	            <input id="customerName" name="customerName" readonly="readonly" class="inputauto" onclick="fbStart('选择客户','<%=basePath %>web/customer_selector.jsp',520,400);"/></td>
	            <td><img src="core/common/images/outdiv.gif" style="position:relative;left:-1px;" width="20" height="22" onclick="fbStart('选择客户','<%=basePath %>web/customer_selector.jsp',520,400);"/></td>
	          </tr>
	      </table></td>
      </tr>
      <tr>
      <td class="layertdleft100">接待人员：</td>
      	   <td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td>
	          	<input id="receiverId" name="contectInfo.receiverId"  readonly="readonly" type="hidden"/>
	          	<input id="receiver" readonly="readonly" type="text" class="inputauto" onclick="fbStart('选择接待人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	          <td width="20" align="center">
	          	<img src="core/common/images/outdiv.gif" width="20" height="22" style="position: relative;left:-4px;" onclick="fbStart('选择接待人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	        </tr>
      	</table></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>交往类型：</td>
        <td class="layerright">
      	<dd:select id="type" name="contectInfo.typeId" key="crm.0016"/>
    	</td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>交往日期：</td>
	    <td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          	<td width="120">
	          		<input name="time" readonly="readonly" type="text" class="inputauto" id="time" onclick="return showCalendar('time');" />
	          		<input id="contectInfoTime" name="contectInfo.time" type="hidden" value="test"/>
	          	</td>
	         	<td width="20" ><img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer;position:relative;left:-1px;" onclick="return showCalendar('time');" /></td>
	         	<td style="padding-left: 4px;">
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
      	<td class="layertdleft100">最后联系时间:</td>
	  	<td class="layerright">
	  		<table border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td><input id="returnTime" name="contectInfo.returnTime" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('returnTime');"/></td>
		          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('returnTime');" src="core/common/images/timeico.gif" width="20" height="22" /></td>
		        </tr>
			</table>
	    </td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>接待情况：</td>
        <td class="layerright" style="padding-bottom:2px;"><textarea id="content" name="contectInfo.content" style="height:50px;" class="textareaauto"></textarea></td>
      </tr>
      <tr>
        <td class="layertdleft100">备注：</td>
        <td class="layerright" style="padding-bottom:2px;"><textarea name="contectInfo.memo" style="height:50px;" class="textareaauto"></textarea></td>
      </tr>
       <tr>
      	<td class="layertdleft100">回访提醒：</td>
      	<td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      		<tr>
      		<td width="5%"><input type="checkbox" id="timeClick"/></td>
	        <td width="25%" class="visittime" > 
	        	<input name="contectInfo.returnVisit" readonly="readonly"  type="text" class="inputauto" id="returnVisit" value="<fmt:formatDate value="${result.value.returnVisit }" pattern="yyyy-MM-dd"/>" onclick="return showCalendar('returnVisit');" />
	        </td>
	        <td width="20" class="visittime" ><img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer;position:relative;left:-1px;" onclick="return showCalendar('returnVisit');" /></td>
			</tr>
      	</table>
      	</td>
      </tr>
    </table>
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<div class="buttondiv">
  <label><input name="Submit" type="button" class="savebtn" value="" onclick="checkForm();"/></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.wiiy.hibernate.Result"%>
<%@page import="com.wiiy.business.entity.InvestmentContectInfo"%> 
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
	var returnVisit = "${result.value.returnVisit}";
	$(document).ready(function() {
		returnVisit = trim("${result.value.returnVisit}");
		returnVisit = returnVisit=="" ? getDate() : returnVisit;
		initTip();
		$("#timeClick").change(function(){
			if($(this).attr("checked")){
				$(".visittime").show();
				$("input[name='investmentContectInfo.returnVisit']").val(getDate());
			}else{
				$("input[name='investmentContectInfo.returnVisit']").val("");
				$(".visittime").hide();
			}
		});
		if("${result.value.returnVisit}" == ''){
			$(".visittime").hide();
		}else{
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
		 if(notNull("name","姓名")
				&&notNull("startTime","日期")
				&&notNull("demand","需求")
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
	
	function setSelectedUser(user){
		$("#receiverId").val(user.id);
		$("#receiver").val(user.realName);
	}
	 
</script>
</head>

<body>
<form action="<%=basePath %>investmentContectInfo!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="investmentContectInfo.id" value="${result.value.id}" />
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
	      <td class="layertdleft100"><span class="psred">*</span>姓名：</td>
	      <td class="layerright" colspan="3" > 
	          <input id="name" name="investmentContectInfo.name" value="${result.value.name }" type="text" class="inputauto"  /> 
	      </td>
	  </tr>
	  <tr>
		  <td class="layertdleft100">联系电话：</td>
		  <td class="layerright" width="30%"><input name="investmentContectInfo.phone" value="${result.value.phone }" type="text" class="inputauto" /></td>
	  	  <td class="layertdleft100">线索等级：</td>
		  <td class="layerright" width="30%"><enum:select name="investmentContectInfo.level" checked="result.value.level" type="com.wiiy.business.preferences.enums.LevelEnum"/></td>
	  </tr>
	  <tr>
	     <td class="layertdleft100">接待人员：</td>
      	   <td class="layerright" width="30%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td>
	          	<input id="receiverId" name="investmentContectInfo.receiverId" readonly="readonly" type="hidden"/>
	          	<input id="receiver" value="${result.value.receiver.realName }" readonly="readonly" type="text" class="inputauto" onclick="fbStart('选择接待人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	          <td width="20" align="center">
	          	<img src="core/common/images/outdiv.gif" width="20" height="22" style="position: relative;left:-4px;" onclick="fbStart('选择接待人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	        </tr>
      	</table></td>
      			<td class="layertdleft100">线索类型：</td>
		  <td class="layerright" width="30%">

			<enum:select  name="investmentContectInfo.contectType" checked="result.value.contectType" type="com.wiiy.business.preferences.enums.ContectTypeEnum" />
		 </td>
      </tr>
      <tr>
      	<td class="layertdleft100">产业类别：</td>
		<td class="layerright"><dd:select id="technicId" name="investmentContectInfo.technicId" checked="result.value.technicId" key="business.0002"/></td>
	  	<td class="layertdleft100">最后联系时间: </td>
	  	<td class="layerright">
	  		<table border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td width="100"><input id="returnTime" name="investmentContectInfo.returnTime" type="text" readonly="readonly" value="<fmt:formatDate value="${result.value.returnTime }" pattern="yyyy-MM-dd"/>" class="inputauto" /></td>
		          <!-- <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('returnTime');" src="core/common/images/timeico.gif" width="20" height="22" /></td> -->
		        </tr>
			</table>
	    </td>	    
	  </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>来电时间：</td>
        <td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          	<td width="25%">
          		<input style="width: 100%"name="startTime" readonly="readonly" type="text" class="input100" id="startTime"  value="<fmt:formatDate value="${result.value.startTime }" pattern="yyyy-MM-dd"/>" onclick="return showCalendar('startTime');" />
          		<input id="contectInfoStartTime" type="hidden" value="test"/>
          	</td>
          	<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime');" /></td>
        	<td style="padding-left: 4px;">
        		<%
        			Result<InvestmentContectInfo> result = (Result<InvestmentContectInfo>)request.getAttribute("result"); 
        						Calendar startTime = Calendar.getInstance();
        						startTime.setTime(result.getValue().getStartTime());
        						int hour = startTime.get(Calendar.HOUR_OF_DAY);
        						int minute = startTime.get(Calendar.MINUTE);
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
			</td>
        </tr>
      </table></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="psred">*</span>需求：</td>
        <td class="layerright"  style="padding-bottom:2px;"><input id="demand" name="investmentContectInfo.demand" type="text" value="${result.value.demand }" class="inputauto"/></td>
        <td class="layertdleft100">备注：</td>
        <td class="layerright" style="padding-bottom:2px;"><input name="investmentContectInfo.memo" type="text" value="${result.value.memo }"  class="inputauto"/></td>
      </tr>
      <tr>
       	<td class="layertdleft100">状态：</td>
		  <td class="layerright" width="30%">
	      	  <enum:radio checked="result.value.contectInfoStatus" name="investmentContectInfo.contectInfoStatus" type="com.wiiy.business.preferences.enums.ContectInfoEnum" /> 
	      </td>

        
      </tr>

      <tr>
      	<td class="layertdleft100">回访提醒：<input type="checkbox" id="timeClick"/></td>
      	<td colspan="3" class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      		<tr>
	          <td width="25%" class="visittime">
	          	<input name="investmentContectInfo.returnVisit" readonly="readonly"  type="text" class="inputauto" id="returnVisit" value="<fmt:formatDate value="${result.value.returnVisit }" pattern="yyyy-MM-dd"/>" onclick="return showCalendar('returnVisit');" />
	          </td>
	         <td width="20" class="visittime"><img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer;position:relative;left:-1px;" onclick="return showCalendar('returnVisit');" /></td>
			</tr>
      	</table>
      	</td>
      </tr>
    </table>
    </div>
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

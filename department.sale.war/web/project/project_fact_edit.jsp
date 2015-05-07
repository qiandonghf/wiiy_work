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
			initDate();
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='fact.audit']");
			var finished = $("input[name='fact.finished']");
			changeState(audit);
			changeState(finished);
		}
		
		function changeState(obj){
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
			if($(obj).attr("name") == 'project.finished'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未完成");
				else $(c).text("已完成");
			}else if($(obj).attr("name") == 'project.audit'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未审核");
				else $(c).text("已审核");
			}
		}
		
		function initDate(){
			if($("#time").val() == ''){
				<%Calendar c = Calendar.getInstance();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				String startTime = s.format(c.getTime());	
				c.add(Calendar.YEAR, 1);
				c.add(Calendar.DATE, -1);
				String endTime = s.format(c.getTime());
				%>
				var startDate = '<%=startTime%>';
				var endDate = '<%=endTime%>';
				$("#time").val(startDate);
			}
			
		}
		function selectUser(id){
			clicked = id;
			fbStart("选择经手人",'<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
		} 
		function setSelectedUser(contect){
			$("#"+clicked).val(contect.name);
			$("#"+clicked+"Id").val(contect.id);
		}
		function selectPlan(id){
			clicked = id;
			fbStart("选择计划进度",'<%=basePath %>web/project/plan_selector.jsp',520,400);
		} 
		function setSelectedParam(plan){
			$("#"+clicked).val(plan.name);
			$("#"+clicked+"Id").val(plan.id);
			$.post("<%=basePath%>plan!findPlanById.action?id="+plan.id,function(data){
				if(data.result.success){
					var planTime = data.result.value.time;
					planTime = planTime.substr(0,planTime.indexOf("T"));
					$("#planTime").val(planTime);
					$("#schedule").val(data.result.value.schedule);
				}
			});
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
	    	var jh = Number($(el).val());
	    	var total = Number($("#schedules").val()); 
	    	if(jh > total){
	    		showTip("实际进度数大于项目总进度数",2000);
	    	}
	    }
	    
		function initForm(){
			$("#form1").validate({
				rules: {
					"fact.schedule":"required"
				},
				messages: {
					"fact.schedule":"请输入项目实际完成进度"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
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
		
	</script>
	<style>
		.mainClass{
			table-layout:fixed;
		}
	</style>
</head>
<body>
<form action="<%=basePath %>fact!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="fact.id" value="${result.value.id }"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
	<table class="mainClass" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="layertdleft100">进度计划：</td>
			<td class="layerright">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="planId" name="fact.planId" value="${result.value.plan.id }" type="hidden" />
			          	<input id="plan" class="inputauto" value="${result.value.plan.code }" onclick="selectPlan('plan');" readonly="readonly" />
			          </td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectPlan('plan');" style="cursor:pointer"/></td>
			        </tr>
			    </table>
			</td>
       		<td class="layertdleft100">计划完成日期：</td>
     		<td class="layerright">
	     		<input id="planTime" value='<fmt:formatDate value="${result.value.plan.time }" pattern="yyyy-MM-dd" />' readonly="readonly" class="inputauto"/>
		    </td>
		</tr>
       	<tr>
    		<td class="layertdleft100">计划完成进度：</td>
			<td class="layerright">
				<input id="schedule" value="${result.value.plan.schedule }" class="inputauto" readonly="readonly" />
			</td>
			<td class="layertdleft100">项目进度总数：</td>
			<td class="layerright">
				<input id="schedules" value="${result.value.project.schedules }" class="inputauto" value="${result.value }" readonly="readonly" />
			</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100"><span class="psred">*</span>实际完成日期：</td>
     		<td class="layerright">
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td><input id="time" name="fact.time" readonly="readonly" class="inputauto" onclick="showCalendar('time');"/></td>
			          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('time');" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			        </tr>
			    </table>
		    </td>
		    <td class="layertdleft100"><span class="psred">*</span>实际完成进度：</td>
			<td class="layerright">
				<input name="fact.schedule" value="${result.value.schedule }" onkeyup="checkDouble(this);" class="inputauto"  />
			</td>
       	</tr>
       	<tr>
    		<td class="layertdleft100">经手人：</td>
			<td class="layerright">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="handlingId" value="${result.value.handling.id }" name="fact.handlingId" type="hidden" />
			          	<input id="handling" value="${result.value.handling.realName }" class="inputauto" onclick="selectUser('handling')" readonly="readonly" />
			          </td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('handling')" style="cursor:pointer"/></td>
			        </tr>
			    </table>
			</td>
	      	<td class="layerright" colspan="2">
				<label><input name="fact.audit" <c:if test="${result.value.audit eq 'YES'}">checked="checked"</c:if> type="checkbox" onclick="changeState(this);" value="NO" /><span>未审核</span></label>
				<label><input name="fact.finished" <c:if test="${result.value.finished eq 'YES'}">checked="checked"</c:if> type="checkbox" onclick="changeState(this);" value="NO" /><span>未完成</span></label>
		   		<!-- <label><input name="project.published" type="checkbox" onclick="changeState(this);" value="NO" />公开标志</label> -->
	      	</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100">备注信息：</td>
			<td class="layerright" colspan="3">
	    		<textarea name="fact.memo" class="inputauto" style="resize:none;height:80px;">${result.value.memo }</textarea>
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

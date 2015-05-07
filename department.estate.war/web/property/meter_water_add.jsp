<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/calendar.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	initTip();
	initForm();
});

function initForm(){
	$("#form1").validate({
		rules:{
			"meter.name":"required",
			"meter.meterFactor":"required",
			"meter.orderNo":"required",
			"meter.preReading":{
					"required":true,
					"number":true
			},
			"meter.kind":"required",
			"meter.status":"required",
			"meter.parkId":"required",
			"meter.readingDate":"required"
		},
		messages: {
			"meter.name":"请填写水表名称",
			"meter.meterFactor":"请填写水表倍数",
			"meter.orderNo":"请填写水表编号",
			"meter.preReading":{
				"required":"请正确填写上期读数"
			},
			"meter.kind":"请选择水表性质",
			"meter.status":"请选择水表状态",
			"meter.parkId":"请选择园区",
			"meter.readingDate":"请填写最后抄表时间"
		},
		errorPlacement: function(error, element){
			showTip(error.html());
		},
		submitHandler: function(form){
			if($("#parentNo").val()==$("#orderNo").val()){
				showTip("不能选择自己为母表",2000);
				return;
			}
			$('#form1').ajaxSubmit({ 
		        dataType: 'json',		        
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("parent.fb.end();getOpener().window.frames[0].reloadList();", 2000);
		        	}
		        } 
		    });
		}
	});
}

function selectBuilding(){
	var id = $("#park").val();
	if(id!=null){
		$.post("<%=BaseAction.rootLocation %>/common/room!loadByParkId.action?id="+id, function(data) {
			if (data.result.success) {
				var list = data.result.value;
				var contectId = $("#building");
				contectId.empty();
				contectId.append($("<option></option>", {value : ""}).append("----请选择楼宇----"));
				for ( var i = 0; i < list.length; i++) {
					var contect = list[i];
					contectId.append($("<option></option>", {value : contect.id}).append(contect.name));
				}
			} else {
				showTip(data.result.msg,2000);
			}
		});
	}
}

function setSelectedMeter(meter){
	$("#parentNo").val(meter.orderNo);
	$("#parentId").val(meter.id);
}


function setSelectedMeters(meters){
	var ids = "";
	for(var i = 0; i < meters.length; i++){
		ids += meters[i].orderNo+",";
	}
	ids = deleteLastCharWhenMatching(ids,",");
	$("#parentNo").val(ids);
}

</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=basePath%>meter!save.action">
<input type="hidden" name="meter.type" value="WATER"/>
<input type="hidden" name="meter.parentId" id="parentId"/>
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>水表名称：</td>
      <td><input name="meter.name" type="text" class="input170" /></td>
      <td class="layertdleft100"><span class="redweight">*</span>水表倍数：</td>
      <td><input name="meter.meterFactor" type="text" class="input170" /></td>
      </tr>
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>水表编号：</td>
      <td><input id="orderNo" name="meter.orderNo" type="text" class="input170" /></td>
      <td class="layertdleft100">母表编号：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
           <%--  <td><input id="parentNo" name="meter.parentNo" type="text" class="inputauto" onclick="fbStart('选择母表编号','<%=basePath%>meter!select.action',520,420);"/></td> --%>
            <td><input readonly="readonly" id="parentNo" name="meter.parentNo" type="text" class="inputauto" onclick="fbStart('选择母表编号','<%=basePath%>web/property/meter_select2(1).jsp',520,420);"/></td> 
            <td width="25" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="fbStart('选择母表编号','<%=basePath%>web/property/meter_select2(1).jsp,520,420);" /></td>
          </tr>
        </tbody>
      </table></td>
    </tr>
    <tr>
      <td class="layertdleft100">水表序列号：</td>
      <td><input name="meter.sequenceNo" type="text" class="input170" /></td>
      <td class="layertdleft100"><span class="redweight">*</span>最后抄表日期：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><input readonly="readonly" id="readingDate" name="meter.readingDate" type="text" class="inputauto" onclick="return showCalendar('readingDate', 'y-mm-dd');"/></td>
          <td width="20"><img style="position:relative; left:-1px;" src="core/common/images/timeico.gif" width="20" height="22" onclick="return showCalendar('readingDate', 'y-mm-dd');"/></td>
        </tr>
      </table></td>
      </tr>
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>上期读数：</td>
      <td><input name="meter.preReading" type="text" class="input170" /></td>
      <td class="layertdleft100"><span class="redweight">*</span>水表性质：</td>
      <td class="layerright">
      	 <enum:select type="com.wiiy.estate.preferences.enums.MeterKindEnum" name="meter.kind" />
      </td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>是否出表：</td>
      <td class="layerright">
      	<enum:radio name="meter.checkOut" type="com.wiiy.commons.preferences.enums.BooleanEnum" />
	  </td>
      <td class="layertdleft100"><span class="redweight">*</span>水表状态：</td>
      <td class="layerright">
     	 <enum:select type="com.wiiy.estate.preferences.enums.MeterStatusEnum" name="meter.status" />
      </td>
    </tr>
    <tr>
    	<td class="layertdleft100"><span class="redweight">*</span>所属楼宇：</td>
        <td colspan="3" class=""><span class="layerright">
     	 	 <select id="park" onchange="selectBuilding();" name="meter.parkId">
				<option value="">----请选择园区----</option>
				<c:forEach items="${result.value}" var="park">
					<option value="${park.id }">${park.name }</option>
				</c:forEach>
			</select>
      		<select id="building" class="data" name="meter.buildingId">
				<option value="">----请选择楼宇----</option>
			</select> 
      </span></td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td colspan="3" class="layerright"><textarea name="meter.memo" style="height:60px;resize:none;" class="textareaauto"></textarea></td>
      </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<!--basediv-->
<div class="buttondiv">
   <label><input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>

</form>
</body>
</html>

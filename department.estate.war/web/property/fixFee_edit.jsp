<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.estate.preferences.enums.PropertyFixStatusEnum"%>
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
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initForm();
	});
	
	
	function initForm(){
		$("#form1").validate({
			rules:{
				"fixFee.maintainer":"required",
				"fixFee.maintainerOrg":"required",
				"fixFee.result":"required",
				"fixFee.laborCosts":"number",
				"fixFee.materialCosts":"number"
			},
			messages: {
				"fixFee.maintainer":"请填写维修人员",
				"fixFee.maintainerOrg":"请填写维修人员所属单位",
				"fixFee.result":"请填写维修结果",
				"fixFee.laborCosts":"请正确填写人工费用",
				"fixFee.materialCosts":"请正确填写材料费用"
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
			        		setTimeout("parent.fb.end();getOpener().reloadList();", 2000);
			        	}
			        } 
			    });
			}
		});
	}
	 
</script>
<style>
	#mainTable{
		table-layout:fixed;
	}
</style>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=basePath%>fixFee!update.action">
<input id="id" name="fixFee.id" type="hidden" value="${result.value.id}"/>
<div class="basediv">
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>维修人员：</td>
      <td><span class="layerright">
        <input name="fixFee.maintainer" type="text" class="input100" value="${result.value.maintainer}"/>
      </span></td>
      <td class="layertdleft100"><span class="psred">*</span>维修人员所属单位：</td>
      <td><span class="layerright">
        <input name="fixFee.maintainerOrg" type="text" class="input200" value="${result.value.maintainerOrg}"/>
      </span></td>
    </tr>
    <tr>
    <td class="layertdleft100">人工费用：</td>
      <td class="layerright">
        <input name="fixFee.laborCosts" type="text" class="input100" value="<fmt:formatNumber value="${result.value.laborCosts}" pattern="#0.00"/>"/>     </td>
      <td class="layertdleft100">材料费用：</td>
      <td class="layerright">
      	<input name="fixFee.materialCosts" type="text" class="input100" value="<fmt:formatNumber value="${result.value.materialCosts}" pattern="#0.00"/>"/></td>
    </tr>
    
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>维修结果：</td>
      <td colspan="5" class="layerright" style="padding-bottom:2px;"><textarea style="height:50px;" name="fixFee.result"  class="textareaauto">${result.value.result}</textarea></td>
    </tr>
    <tr>
      <td class="layertdleft100">整改意见：</td>
      <td colspan="5" class="layerright" style="padding-bottom:2px;"><label>
        <textarea name="fixFee.rectification" style="height:40px;" class="textareaauto">${result.value.rectification}</textarea>
      </label></td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td colspan="5" class="layerright" style="padding-bottom:2px;"><label>
        <textarea name="fixFee.memo" style="height:40px;" class="textareaauto">${result.value.memo}</textarea>
      </label></td>
    </tr>
  </table>
</div>
<!--//divlay-->
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<div class="buttondiv">
  <label><input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

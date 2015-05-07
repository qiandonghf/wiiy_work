<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/calendar/calendar.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		loadProductsByCustomerId(${result.value.id});
	});
	function loadProductsByCustomerId(id){
		$.post("<%=basePath%>product!loadProductsByCustomerId.action?id="+id,function(data){
			if(data.result.success){
				var list = data.result.value;
				var productId = $("#productId");
				productId.empty();
				productId.append($("<option></option>",{value:""}).append("---请选择---"));
				for(var i = 0; i < list.length; i++){
					var product = list[i];
					productId.append($("<option></option>",{value:product.id}).append(product.name));
				}
			} else {
				showTip(data.result.msg,2000);
			}
		});
	}
	function checkForm(){
		if($("#amount").val()!="") {
			if(checkDouble("amount","申报金额")==false){
				return;
			}
		}
		if (notNull("name","申报项目名称") && checkInput("applyYear","申报年度") 
			&& checkInput("applyTypeId","申报类型") && checkInput("applyState","申报状态")
			&& checkInput("productId","产品")&&notNull("amount","申请金额")) {
			 $('#form1').ajaxSubmit({ 
			     	dataType: 'json',
			     	success: function(data){
		        	showTip(data.result.msg,2000);
			        if(data.result.success){
			        	setTimeout("getOpener().reloadList('projectApply');parent.fb.end();", 2000);
			        }
			     } 
			  });
		}
	}
	function checkInput(id,name) {
		if ($("#"+id).val()=="") {
			showTip("请选择"+name);
			return false;
		}else{
			return true;
		}
	}
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=basePath%>projectApply!save.action">
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>企业名称：</td>
      <td class="layerright">${result.value.name}<input id="customerId" name="projectApply.customerId" type="hidden" value="${result.value.id}"/></td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>申报项目名称：</td>
      <td class="layerright">
      <input id="name" name="projectApply.name" type="text" class="inputauto" /></td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>产品：</td>
      <td class="layerright">
      <select id="productId" name="projectApply.productId"><option value="">---请选择---</option></select>
   </td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>申报年度：</td>
      <td class="layerright"><select name="projectApply.applyYear" id="applyYear">
        <option value="">----请选择----</option>
        <option value="2011">2011</option>
        <option value="2012">2012</option>
        <option value="2013">2013</option>
        <option value="2014">2014</option>
        <option value="2015">2015</option>
        <option value="2016">2016</option>
        <option value="2017">2017</option>
        <option value="2018">2018</option>
        <option value="2019">2019</option>
        <option value="2020">2020</option>
            </select></td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>申报类型：</td>
      <td class="layerright">
		<dd:select key="business.0007" name="projectApply.applyTypeId" id="applyTypeId"/>
	</td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>申报状态：</td>
      <td class="layerright">
      <enum:select type="com.wiiy.business.preferences.enums.ProjectApplyStatusEnum" name="projectApply.applyState" id="applyState"/>
	</td>
    </tr>
    
    <tr>
      <td class="layertdleft100">是否验收：</td>
      <td class="layerright">
      <enum:radio name="projectApply.checked" type="com.wiiy.commons.preferences.enums.BooleanEnum" defaultValue="NO"/>
		</td>
    </tr>
      <tr>
      <td class="layertdleft100">发布到网站：</td>
      <td class="layerright">
	      <enum:radio name="projectApply.pub" type="com.wiiy.commons.preferences.enums.BooleanEnum" defaultValue="NO"/>
      </td>
    </tr>
    <tr>
      <td class="layertdleft100">验收时间：</td>
      <td class="layerright"><table width="205" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="180"><input name="projectApply.checkTime" type="text" class="inputauto" id="starttime" readonly="readonly" onclick="return showCalendar('starttime');"/></td>
          <td width="20"><img src="core/common/images/timeico.gif" width="20" style="position: relative;left:-1px;" height="22" onclick="return showCalendar('starttime');"/></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>申请金额：</td>
      <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="10">
        <tr>
          <td width="180"><input name="projectApply.amount" type="text" class="inputauto" id="amount" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
          <td>&nbsp;万元</td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td class="layerright"><label>
        <textarea name="projectApply.memo" style="height:115px;" class="textareaauto"></textarea>
      </label></td>
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
  <label><input name="Submit" type="button" class="savebtn" onclick="checkForm()" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

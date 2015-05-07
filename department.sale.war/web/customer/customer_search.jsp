<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
	$(function(){
		initTip();
	});
	
	function search(){
		parent.fb.end();
		parent.search(getSearchFilters());
	}
</script>
</head>

<body style=" background:#fff">
<div class="basediv">
  <div class="divlays" style="margin:0px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
     
      <tr>
        <td class="layertdleft100">客户名称：</td>
        <td class="layerright">
	        <table border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="310">
	           	 	<search:input  dataType="string" field="name" op="cn" inputClass="inputauto" />
	            </td>
	          </tr>
	        </table>
	     </td>
      </tr>
      <tr>
		<td class="layertdleft100">客户类型：</td>
		<td class="layerright">
		   <search:choose dataType="com.wiiy.sale.preferences.enums.CustomerTypeEnum" field="customerType" op="eq">
		   	  <enum:select styleClass="data"  type="com.wiiy.sale.preferences.enums.CustomerTypeEnum"/> 
	       </search:choose>
	   	</td>
	  </tr>
	  <tr>
		<td class="layertdleft100">客户等级：</td>
		<td class="layerright">
		  <search:choose dataType="com.wiiy.sale.preferences.enums.CustomerLevelEnum" field="level" op="eq">
		    <enum:select styleClass="data" type="com.wiiy.sale.preferences.enums.CustomerLevelEnum"/>
		  </search:choose>
		 </td>
	  </tr>
      <tr>
        <td class="layertdleft100">置业顾问：</td>
        <td class="layerright">
	        <table width="100%" border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td width="310">
	           	 	<search:input dataType="string" field="user.realName" op="cn" inputClass="inputauto"/>
	            </td>
	            <td>&nbsp;</td>
	          </tr>
	        </table>
	        <label></label>
	    </td>
      </tr>
      <tr>
        <td class="layertdleft100">证件号：</td>
        <td class="layerright">
	        <table width="100%" border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td width="310">
	           	 	<search:input dataType="string" field="idNO" op="cn" inputClass="inputauto"/>
	            </td>
	      	 	<td>&nbsp;</td>
	          </tr>
	        </table>
	        <label></label>
	    </td>
      </tr>
      <tr>
        <td class="layertdleft100">固话：</td>
        <td class="layerright">
	        <table width="100%" border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td width="310">
	           	 	<search:input dataType="string" field="phone" op="cn" inputClass="inputauto"/>
	            </td>
	            <td>&nbsp;</td>
	          </tr>
	        </table>
	        <label></label>
	    </td>
      </tr>
      <tr>
		<td class="layertdleft100">地段：</td>
		<td class="layerright">
		  <search:choose dataType="com.wiiy.sale.preferences.enums.SatisfactionEnum" field="section" op="eq">
		    <enum:select styleClass="data" type="com.wiiy.sale.preferences.enums.SatisfactionEnum"/>
		  </search:choose> 
		</td>
	  </tr>
	  <tr>
		<td class="layertdleft100">户型：</td>
		<td class="layerright">
		  <search:choose dataType="com.wiiy.sale.preferences.enums.SatisfactionEnum" field="houseType" op="eq">
		    <enum:select styleClass="data" type="com.wiiy.sale.preferences.enums.SatisfactionEnum"/>
		  </search:choose> 
		</td>
	  </tr>
    </table>
  </div>
  <!--//divlays-->
  <div class="hackbox"></div>
</div>
<div class="buttondiv">
  <label><input name="Submit" type="button" class="search_cx" value="" onclick="search()"/>
  </label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</body>
</html>

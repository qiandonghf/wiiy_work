<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css" />
<style type="text/css">
	.combo{
		position: relative;
	}
</style>

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
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
		initParent();
	});
	function initParent(){
		$('#parentId').combotree({
		    url: '<%=basePath%>dataProperty!loadParentProperty.action'
		}); 
	}
	function initForm(){
		$("#form1").validate({
			rules: {
				//"dataProperty.dataType":"required",
				"dataProperty.name":"required"
			},
			messages: {
				//"dataProperty.dataType":"请选择数据类型",
				"dataProperty.name":"请填写名称"
				
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
			        		setTimeout(function(){
			        			getOpener().reloadListNode(data.dataProperty.parentId);
			        			parent.fb.end();
			        		}, 2000);
			        	}
			        } 
			    });
			}
		});
	}
</script>
</head>

<body>
<form action="<%=basePath %>dataProperty!save.action" method="post" name="form1" id="form1">
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
       <td class="layertdleft100">上级数据：</td>
       <td class="layerright">
       	<div>
       	 <input id="parentId" name="dataProperty.parentId" style="width:180px;" value="----请选择----"/>
       </div>
      </td>
      <td class="layertdleft100">数据类型：</td>
      <td class="layerright">
      	<enum:select id="dataType" name="dataProperty.dataType" type="com.wiiy.business.preferences.enums.DataTypeEnum" />
     </td>
    </tr>
    <tr>
    	<td class="layertdleft100"><span class="psred">*</span>名称：</td>
    	<td class="layerright">
    		<input id="name" name="dataProperty.name" type="text" class="inputauto" />
    	</td>
    	<td class="layertdleft100">单位：</td>
    	<td class="layerright">
    		<input id="unit" name="dataProperty.unit" type="text" class="inputauto" />
    	</td>
    </tr>
    <tr>
    	<td class="layertdleft100">数据类型扩展：</td>
    	<td colspan="3" class="layerright"><textarea id="summery" name="dataProperty.dataTypeExt"  class="textareaauto" style="height:40px;"></textarea></td>
    </tr>
    <tr>
      <td class="layertdleft100">填报说明：</td>
      <td colspan="3" class="layerright"><textarea id="summery" name="dataProperty.note"  class="textareaauto" style="height:70px;"></textarea></td>
    </tr>
  </table>
</div>
	<div class="hackbox"></div>
</div>
<div class="buttondiv">
  <label><input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

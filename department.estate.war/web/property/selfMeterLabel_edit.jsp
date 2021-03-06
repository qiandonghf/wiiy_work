<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.wiiy.commons.preferences.enums.TitleEnum"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@page import="com.wiiy.commons.util.DateUtil"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>无标题文档</title>
<link href="core/common/style/base.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery.treeview.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="department.estate/web/style/cord_icon.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/righth.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/tree/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/layertable.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	 initTip();
	 initTree();
	 initForm();
});


function initTree(){
	$.ajax({
		  "url" : "<%=basePath%>park!listBuilding.action",
		  type:"POST",
		  success: function(data){
			$("#tt").tree({
				animate: true,
				checkbox:true,
				lines:true,
				"data" : data.parkList
			});
			var roots = $('#tt').tree("getRoots");
			var myIds = $("#ids").val();
			var ids = myIds.split(",");
			for(var i=0;i<roots.length;i++){
				var root = roots[i];
				var children = $(this).tree("getChildren",root.target);
				for(var j=0;j<children.length;j++){
					var child = children[j];
					if(child!=""){
						var cId = child.id;
						for(var k=0;k<ids.length;k++){
							var selectId = ids[k];
							if(cId == selectId){
								$('#tt').tree("check",child.target);
							} 
						} 
					}
				}
				
				
				//$("#tt").tree("check",bId.target);
			}
		  }
		});
}


function initForm(){
	$("#form1").validate({
		rules:{
			"selfMeterLabel.name":"required",
			"selfMeterLabel.type":"required",
			"selfMeterLabel.date":"required"
		},
		messages: {
			"selfMeterLabel.name":"请填写报表名",
			"selfMeterLabel.type":"请选择报表类型",
			"selfMeterLabel.date":"请选择制表时间"
		},
		errorPlacement: function(error, element){
			showTip(error.html());
		},
		submitHandler: function(form){
			var nodes = $('#tt').tree("getChecked");
			var buildingIds = "";
			for(var i=0;i<nodes.length;i++){
				var node = nodes[i];
				var children = $(this).tree("getChildren",node.target);
				if(children==""){
					var id = node.id;
					buildingIds += id+",";
				}
			}
			if(buildingIds==""){
				showTip("请选择楼宇",2000);
				return;
			}
			$("#buildingIds").val(buildingIds);
			$('#form1').ajaxSubmit({ 
		        dataType: 'json',		        
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("parent.fb.end();getOpener().location.reload();", 2000);
		        	}
		        } 
		    });
		}
	});
}

	  
</script>
</head>
<body>
<form action="<%=basePath%>selfMeterLabel!update.action" method="post"  name="form1" id="form1">
<input type="hidden" id="ids" value="${result.value.buildingIds}"/>
<input type="hidden" name="selfMeterLabel.id" value="${result.value.id}"/>

<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>报表名：</td>
      <td class=""  colspan="3">
      		<input name="selfMeterLabel.name" type="text" class="input170" style="width:94%;" value="${result.value.name}"/>
      </td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>报表类型：</td>
      <td class="layerright">	
      	 <enum:select type="com.wiiy.common.preferences.enums.MeterTypeEnum" name="selfMeterLabel.type" checked="result.value.type"/>
      </td>
    </tr>
    <tr>
       <td class="layertdleft100"><span class="redweight">*</span>制表日期：</td>
       <td class="layerright">
        	<table width="35%" border="0" cellspacing="0" cellpadding="0">
	           <tr>
	              <td><input readonly="readonly" id="date" name="selfMeterLabel.date" type="text" class="inputauto"  value="<fmt:formatDate value="${result.value.date}" pattern="yyyy-MM-dd"/>"  onclick="return showCalendar('date');"/></td>
	              <td width="20"><img style="position:relative; left:-1px;" src="core/common/images/timeico.gif" width="20" height="22" onclick="return showCalendar('date');"/></td>
	          </tr>
           </table>
       </td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>抄表人:</td>
      <td class="layerright"  colspan="3">
      	   ${result.value.reader}
      	   <input type="hidden" name="selfMeterLabel.reader" value="${result.value.reader}"/>
      </td>
    </tr>
    <tr>
      <td class="layertdleft100"><span class="redweight">*</span>楼宇选择:</td>
      <td class="layerright" colspan="3">
        <div class="treeviewdiv" style="overflow-Y:auto; width:98%; padding:0; height:70px; border:1px solid #ddd; margin-bottom:10px;" id="treeviewdiv">
				<input type="hidden" id="buildingIds" name="selfMeterLabel.buildingIds"/>
				<ul id="tt">
				</ul>
          </div>
        </td>
    </tr>
    <tr>
      <td class="layertdleft100">备注：</td>
      <td class="layerright" colspan="3"><textarea name="selfMeterLabel.memo" rows="3" class="textareaauto">${result.value.memo}</textarea></td>
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
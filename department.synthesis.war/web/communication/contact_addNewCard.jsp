<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.TitleEnum"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
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
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/calendar.css"/>
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />

<script type="text/javascript" src="core/common/js/calendar.js"></script>
<script type="text/javascript" src="core/common/js/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/js/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/layertable.js"></script>
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
			"card.name":"required",
			"card.mobile":"required"
		},
		messages: {
			"card.name":"请输入名字",
			"card.mobile":"请输入手机号码"
		},
		errorPlacement: function(error, element){
			showTip(error.html());
		},
		submitHandler: function(form){
			if($("#labelUl").text()==''){
				showTip("请选择名片夹",2000);
				return false;
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
	});
}
function loadMyLabel(){
	$.post("<%=basePath%>card!loadMyLabel.action",function(data){
		$("#customerLabelList").children("h1").remove();
		$("#customerLabelList").children("ul").remove();
		data.result.value[data.result.value.length] = {cardCategory:{name:"我的名片夹"},cardLabelList:data.myCardCategory};
		for(var j = 0; j < data.result.value.length; j++){
			var dto = data.result.value[j];
			var li = $("<li></li>");
			if(dto.cardLabelList==null)continue;
			for(var i = 0; i < dto.cardLabelList.length; i++){
				var label = dto.cardLabelList[i];
				li.append($("<a></a>",{href:"javascript:void(0)",click:function(){
					$("#customerLabelList").hide();
					var newLabel = $(this).find("input").val();
					var exist = false;
					$.each($(".customerLabelId"),function(){
						if($(this).val()==newLabel) exist = true;
					});
					if(exist)return;
					$("#labelUl").append(
						$("<li></li>",{
							mouseover:function(){$(this).find('span').show()},
							mouseout:function(){$(this).find('span').hide()}
						}).append($(this).text()).append($("<input type=\"hidden\" name=\"ids\" class=\"customerLabelId\" value=\""+$(this).find("input").val()+"\"/>")
						).append($("<span></span>",{click:function(){$(this).parent().remove();}}).hide())
					);
				}}).append(label.name).append($("<input type=\"hidden\" value=\""+label.id+"\"/>")));
			}
			$("#customerLabelList").append($("<h1></h1>").append(dto.cardCategory.name)).append($("<ul></ul>").append(li)).show();
		}
		if(data.result.value.length==0){
			$("#customerLabelList").append($("<h1></h1>").append("暂无标签")).show();
		}
	});
}

</script>
</head>

<body>
<form action="<%=basePath%>card!save.action" method="post"  name="form1" id="form1">
<!--basediv-->
<div class="basediv">
<div class="divlays">
	<input id="ownerId" name="card.ownerId" value="<%=SynthesisActivator.getSessionUser(request).getId()%>" type="hidden"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
		<td class="layertdleft100"><span class="psred">*</span>姓名：</td>
		<td><input name="card.name" type="text" class="input170" /></td>
		<td class="layertdleft100">称谓：</td>
	      <td class="layerright"><label>
	     	 <enum:radio name="card.appellation" type="com.wiiy.commons.preferences.enums.TitleEnum" styleClass="incubated"/>
	      </label></td>
    </tr>
    <tr>
		<td class="layertdleft100"><span class="psred">*</span>手机号码：</td>
		<td><input id="mobile" name="card.mobile" type="text" class="input170" /></td>
      	<td class="layertdleft100">Email：</td>
		<td><input name="card.email" type="text" class="input170" /></td>
		<!-- <td class="layertdleft100">联系电话：</td>
		<td><label><input id="homeTel" name="card.homeTel" type="text" class="input170" /></label></td> -->
    </tr>
    <tr>
		<td class="layertdleft100">QQ：</td>
		<td><input name="card.qq" type="text" class="input170" /></td>
	</tr>
    <tr>
      <td class="layertdleft100"><span class="psred">*</span>名片夹：</td>
	  <td colspan="3" class="layerright" id="myCards">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<td width="28">
				<img src="core/common/images/outdiv.gif" width="20" height="22" style="cursor:pointer;" onclick="loadMyLabel();" />
				<div class="company_label" style="display:none;overflow-x:hidden; hidden;overflow-y:auto;position:absolute; left:85px; top:97px;height:150px;" id="customerLabelList">
					<div class="titlebg"><span style="float:right"><img src="core/common/images/companyclose.gif" onclick="$(this).parent().parent().parent().hide()" style="cursor:pointer;" /></span>名片夹</div>
					<h1>专业分类</h1>
						<ul>
							<li><a href="javascript:void(0)">水处理</a><a href="javascript:void(0)">空气处理</a><a href="javascript:void(0)">其它环保</a><a href="javascript:void(0)">电子信息</a></li>
						</ul>
						<h1>专业分类</h1>
						<ul>
							<li><a href="javascript:void(0)">水处理</a><a href="javascript:void(0)">空气处理</a><a href="javascript:void(0)">其它环保</a><a href="javascript:void(0)">电子信息</a></li>
						</ul>
				</div>
			</td>
			<td class="customerdiv">
				<div style=" width:auto; height:50px; overflow-x:hidden; overflow-y:scroll">
				<ul id="labelUl"></ul>
				</div>
			</td>
		</tr>
	</table>
</td>
    </tr>
    
  </table>
  <div class="hackbox"></div>
	</div>
</div>
<!--//basediv-->
<div style="height:166px;">
<!--table切换开始-->
	<div class="apptab" id="tableid">
		<ul>
			<li class="apptabliover" onclick="tabSwitch('apptabli','apptabliover','tabswitch',0)">公司</li>
			<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',1)">备注</li>
		</ul>
	</div>
<!--//table切换开始-->
			<div class="basediv tabswitch" style="margin-top: 0px; "
				name="textname" id="textname">
				<!--divlays-->
				<div class="divlays" style="margin: 0px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="layertdleft100">单位名称：</td>
							<td><label> <input name="card.company" type="text" class="input170" />
							</label></td>
							<td class="layertdleft100">职务：</td>
							<td ><label> <input
									name="card.position" type="text" class="input170" />
							</label></td>
						</tr>
						<tr>
							<td class="layertdleft100">单位电话：</td>
							<td><input name="card.homeTel" type="text"
								class="input170" /></td>
							<td class="layertdleft100">单位传真：</td>
							<td><input name="card.fax" type="text"
								class="input170" /></td>
						</tr>
						<tr>
							<td class="layertdleft100">单位网址：</td>
							<td colspan="3" class="layerright"><input name="card.homepage" type="text"
								class="inputauto" /></td>
						</tr>
						<tr>
							<td class="layertdleft100">单位地址：</td>
							<td colspan="3" class="layerright"><input name="card.companyAddr" type="text"
								class="inputauto" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="basediv tabswitch" style="margin-top: 0px; display: none;"
				name="textname" id="textname">
				<!--divlays-->
				<div class="divlays" style="margin: 0px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="layertdleft100">备注：</td>
							<td class="layerright"><label> <textarea
										name="card.memo" rows="8" class="textareaauto"></textarea>
							</label></td>
						</tr>
					</table>
				</div>
			</div>
			<!--//basediv-->
		</div>
		<div class="buttondiv">
			<label>
			<input name="Submit" type="submit" class="savebtn"  value=""  /></label> 
			<label>
			<input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
		</div>
	</form>
</body>
</html>

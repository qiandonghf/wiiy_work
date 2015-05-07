<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.business.activator.BusinessActivator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String url = BaseAction.rootLocation+"/";
Long userId = BusinessActivator.getSessionUser().getId();
pageContext.setAttribute("userId", userId);
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
<script type="text/javascript" src="core/common/js/layertable.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript">
	$(function(){
		initTip();
	});

	function save(type){
		if('opinion1'== type){
			var opinion = $("#opinion1Txt").html();
			if(opinion == ""){
				alert("请填写意见");
			}else{
				var id=$("#id").val();
				var url="<%=basePath%>rentOutContact!approval.action?approvalType=opinion1&id="+id;
				$.post(url,{opinion:opinion},function(data){
					if(data.result.success){
						showTip(data.result.msg,2000);
						setTimeout(function(){
							location.reload();
						},2000);
					}
					
					
				});
			}
		}else if('opinion2' == type){
			var opinion = $("#opinion2Txt").html();
			if(opinion == ""){
				alert("请填写意见");
			}else{
				var id=$("#id").val();
				var url="<%=basePath%>rentOutContact!approval.action?approvalType=opinion2&id="+id;
				$.post(url,{opinion:opinion},function(data){
					if(data.result.success){
						showTip(data.result.msg,2000);
						setTimeout(function(){
							location.reload();
						},2000);
					}
					
				});
			}
		}else if('opinion3' == type){
			var opinion = $("#opinion3Txt").html();
			if(opinion == ""){
				alert("请填写意见");
			}else{
				var id=$("#id").val();
				var url="<%=basePath%>rentOutContact!approval.action?approvalType=opinion3&id="+id;
				$.post(url,{opinion:opinion},function(data){
					if(data.result.success){
						showTip(data.result.msg,2000);
						setTimeout(function(){
							location.reload();
						},2000);
					}
					
				});
			}
		}else if('opinion4' == type){
			var opinion = $("#opinion4Txt").html();
			if(opinion == ""){
				alert("请填写意见");
			}else{
				var id=$("#id").val();
				var url="<%=basePath%>rentOutContact!approval.action?approvalType=opinion4&id="+id;
				$.post(url,{opinion:opinion},function(data){
					if(data.result.success){
						showTip(data.result.msg,2000);
						setTimeout(function(){
							location.reload();
						},2000);
					}
					
				});
			}
		}else if('opinion5' == type){
			var opinion = $("#opinion5Txt").html();
			if(opinion == ""){
				alert("请填写意见");
			}else{
				var id=$("#id").val();
				var url="<%=basePath%>rentOutContact!approval.action?approvalType=opinion5&id="+id;
				$.post(url,{opinion:opinion},function(data){
					if(data.result.success){
						showTip(data.result.msg,2000);
						setTimeout(function(){
							location.reload();
						},2000);
					}
					
				});
			}
		}else if('opinion6' == type){
			var opinion = $("#opinion6Txt").html();
			if(opinion == ""){
				alert("请填写意见");
			}else{
				var id=$("#id").val();
				var url="<%=basePath%>rentOutContact!approval.action?approvalType=opinion6&id="+id;
				$.post(url,{opinion:opinion},function(data){
					if(data.result.success){
						showTip(data.result.msg,2000);
						setTimeout(function(){
							location.reload();
						},2000);
					}
					
				});
			}
		}
	}
	var approvalType;
	function send(type){
		approvalType = type;
		fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!selectSelf.action',520,400);
	}
	function setSelectedUser(user){
		var id = $("#id").val();
		var url = "<%=basePath%>rentOutContact!send.action?id="+id+"&approvalType="+approvalType+"&receiveId="+user.id;
		$.post(url,function(data){
			var msg = data.result.msg;
			if(data.result.success){
				showTip(msg,2000);
				setTimeout(function(){
					location.reload();
				},2000);
			}
		});
	}
</script>
 
 
<style> 
html { overflow:auto;}
h1 { font:bold 16px/32px ''; height:32px; text-align:center;}
</style>
 
</head>
<body>
 
<form action="" method="post" enctype="multipart/form-data" name="form1" id="form1" >
<!--basediv-->
<div class="basediv" style="overflow-x:hidden; overflow-y:scroll; height:400px;margin-bottom:8px;">
 
<h1>企业客户入驻宁波市科技创业中心申请表</h1>
 
<div class="overflowAuto" id="overflowAuto">
                  
<style type="text/css"> 
<!--
table.tsy1{margin:0 10px; font-weight:bold; }
table.tsy3{margin:0 10px 0 10px; border:1px solid #000000; border-top:none; border-left:none; }
table.tsy3 td{border:1px solid #000; border-right:none; border-bottom:none;  padding:8px 10px; }
table.tsy4{margin:0 10px 0 10px; border:1px solid #000000; border-top:none; border-left:none; }
table.tsy4 td{border:1px solid #000; border-right:none; border-bottom:none;  padding:8px 10px; }
 
.tsy-h80{height:80px; vertical-align:top; }
-->
</style>
<table width="96%" border="0" cellspacing="0" cellpadding="0"  class="tsy1">
      <tr>
        <td width="80">编号：</td>
        <td >&nbsp;</td>
        <td width="80">填表日期：</td>
        <td >&nbsp;&nbsp;&nbsp;&nbsp;年   &nbsp;&nbsp;&nbsp;&nbsp;月  &nbsp;&nbsp;&nbsp;&nbsp; 日</td>
        </tr>
</table>
<input type="hidden" id="id" value="${result.value.id }"/>
<table width="96%" border="0" cellspacing="0" cellpadding="0" class="tsy4">
      <tr>
        <td colspan="2" width="20%">租赁客户名称</td>
        <td colspan="7">${result.value.customer }</td>
        </tr>
      <tr>
        <td colspan="2">原租赁房屋 房号/面积</td>
        <td colspan="7">${result.value.room.building.name }${result.value.room.name} ${result.value.room.buildingArea }平方米</td>
      </tr>
      <tr>
        <td colspan="2">申请退房主要原因</td>
        <td colspan="7">${result.value.reason }</td>
      </tr>
    </table>
   <div class="" style="padding:5px 0 0;">
<div class="titlebg">反馈信息：</div>
                     	
                     	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:2px 0;">
      <tr>
        <td class="layertdleft100"><span class="tsy-h80">管委会创业服务部初审意见：</span></td>
        <td class="layerright" style="padding-top:4px;" colspan="3"><textarea name="textarea"  class="textareaauto" readonly="readonly" id="opinion1Txt" style="height:50px;">${result.value.opinion1 }</textarea></td>
        <td width="6%"><input type="hidden" id="departmentId" value="${result.value.opinion1Id }" /><label id="departmentLab">
        </label></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="tsy-h80">管委（公司）领导批复意见：</span></td>
        <td class="layerright" style="padding-top:4px;" colspan="3"><textarea name="textarea" id="opinion2Txt" readonly="readonly" class="textareaauto"  style="height:50px;">${result.value.opinion2 }</textarea></td>
        <td width="6%"><input type="hidden" id="headId" value="${result.value.opinion2Id }" /><label id="headLab">
        </label></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="tsy-h80">管委会企业发展部注册变更审核意见：</span></td>
        <td class="layerright" style="padding-top:4px;" colspan="3"><textarea name="textarea" id="opinion3Txt" readonly="readonly" class="textareaauto"  style="height:50px;">${result.value.opinion3 }</textarea></td>
        <td width="6%"><input type="hidden" id="headId" value="${result.value.opinion3Id }" /><label id="headLab">
        </label></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="tsy-h80">管委会财务部审核意见：</span></td>
        <td class="layerright" style="padding-top:4px;" colspan="3"><textarea name="textarea" id="opinion4Txt" readonly="readonly" class="textareaauto"  style="height:50px;">${result.value.opinion4 }</textarea></td>
        <td width="6%"><input type="hidden" id="headId" value="${result.value.opinion4Id }" /><label id="headLab">
        </label></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="tsy-h80">南都物业服务中心验房审核意见：</span></td>
        <td class="layerright" style="padding-top:4px;" colspan="3"><textarea name="textarea" id="opinion5Txt" readonly="readonly" class="textareaauto"  style="height:50px;">${result.value.opinion5 }</textarea></td>
        <td width="6%"><input type="hidden" id="headId" value="${result.value.opinion5Id }" /><label id="headLab">
        </label></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="tsy-h80">南都物业服务中心（财务）审核意见：</span></td>
        <td class="layerright" style="padding-top:4px;" colspan="3"><textarea name="textarea" id="opinion6Txt" readonly="readonly" class="textareaauto"  style="height:50px;">${result.value.opinion6 }</textarea></td>
        <td width="6%"><input type="hidden" id="headId" value="${result.value.opinion6Id }" /><label id="headLab">
        </label></td>
      </tr>
      <tr>
        <td class="layertdleft100"><span class="tsy-h80">备注：</span></td>
        <td class="layerright" style="padding-top:4px;" colspan="3"> <textarea name="textfield4"  class="textareaauto" readonly="readonly"  style="height:50px;">${result.value.description}</textarea></td>
        <td width="6%"><label></label></td>
      </tr>
    </table>
                          
                     </div>
    
<!--[if lte ie 8]> </div><![endif]-->
  </div>
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<!--<div class="buttondiv">
  <label><input name="Submit" type="button" class="savebtn" value=" " /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value=" " onclick="parent.fb.end();"/></label>
  </div>-->
</form>
 
 
 
</body>
</html>
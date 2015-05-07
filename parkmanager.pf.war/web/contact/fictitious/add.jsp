<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.core.entity.User"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ page import="com.wiiy.pf.preferences.enums.LeaveTypeEnum"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<%
	User user = PfActivator.getSessionUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
	if(user == null){
%>
	<title>即将关闭</title>
<%}else{%>
	<title>新建入驻审批表</title>
<%}%>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="parkmanager.pf/style/base.css" rel="stylesheet" type="text/css" />
<link href="parkmanager.pf/style/content.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />

<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>

<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	initTip();
	initForm();
	//alert($("body").height());
	<%
	if(user == null){
	%>
		$("body").html("<h1 style=\'margin-top:200px;text-align:center;font-size:14px;\'>未登录或会话过期，本窗口将在<span style=\'color:red;margin:0px 5px;\'>5</span>秒后关闭</h1>");
		setTimeout("window.close()",5000);
	<%}%>
	initDefinitions();
	initUploadify();
});

function initDefinitions(){
	var id= $("#taskId").val();
	$.ajax({
		 "url" : "<%=basePath%>contact!findDefinitions.action?id="+id,
		  type:"POST",
		  success: function(data){
			  
			  if(data.result.success){
				  var definition = data.result.value;
				  var infos = data.userTaskInfos;
				  var htmlTxt = "";
				  for(var i= 0;i<definition.length;i++){
					  var info = "";
					  var id = "";
					  if(infos.length > i){
						  var taskInfo = infos[i];
						  if(taskInfo.display){
						  	if(taskInfo!=null){
						 	 info = "默认：";
							  
								  if(taskInfo.assignee!=null){//流程中已经指派负责人
									  info += "负责人:[";
									  info += taskInfo.assignee.firstName;
									  if(taskInfo.assignee.id==null)info +="(待创建)";
									  info += "] ";
									  
								  }
								  if(taskInfo.candidateUserList!=null&&taskInfo.candidateUserList.length>0){//流程中已经指派负责人
									  var arrays=taskInfo.candidateUserList;
									  info += "候选审批人:";
									  for(var j=0;j<arrays.length;j++){
										  info += "[";
										  info +=arrays[j].firstName;
										  if(arrays[j].id==null)info +="(待创建)";
										  info += "] ";
									  } 
								  }
								  if(taskInfo.candidateGroupList!=null&&taskInfo.candidateGroupList.length>0){//流程中已经指派负责人
									  var arrays=taskInfo.candidateGroupList;
									  info += "候选审批角色:";
									  for(var j=0;j<arrays.length;j++){
										  info += "[";
										  info +=arrays[j].name;
										  if(arrays[j].id==null)info +="(待创建)";
										  info += "] ";
									  } 
								  }
								  
							  }
							  
						 
						  
							  htmlTxt += "<tr><td class=\'layertdleft100\'>"+definition[i].nameExpression.expressionText+"：</td>";
							  htmlTxt += "<td colspan=\'3\' class=\'layerright\' style=\'padding-bottom:2px;padding-top:2px;padding-right:5px;\'>";
							  if(id < 0){
								  htmlTxt += "<textarea readonly=\'readonly\' class=\'inputauto\'  style=\'cursor:pointer;height:40px;resize: none;color:#666;\'>"+info+"</textarea>";
							  }else{
								  htmlTxt += "<textarea readonly=\'readonly\' class=\'inputauto\'  style=\'cursor:pointer;background-color:#FFFFCB;height:40px;resize: none;color:#666;\'>"+info+"</textarea>";
							  }
							  htmlTxt += "</td></tr>";
						  }
					  }
				  }
				  $("#insertTxt").html($("#insertTxt").html()+htmlTxt); 
			  }
		  }
	});
}

function initUploadify(){
	<% String rootPath = BaseAction.rootLocation+"/";%>
	$("#fileUpload").uploadify( {
		'auto'				: true, //是否自动开始 默认true
		'multi'				: false, //是否支持多文件上传 默认true
		'formData'			: {'module':'pf','directory':uploadify.directory.attachments},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
		'uploader'			: "<%=rootPath %>core/upload.action",//文件服务器路径
		'swf'				: uploadify.swf,//上传组件swf
		'width'				: 60,//按钮图片的长度
		'height'			: uploadify.height,//按钮图片的高度
		'buttonText'		: '<span style="top:5px;"></span>添加附件 ', //按钮上的文字
		'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
		'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
		'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
		'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
		'fileTypeDesc'		: "*.*",//选择文件对话框文件类型描述信息
		'fileTypeExts'		: "*.*",//可上传上的文件类型
		'onUploadSuccess'	: uploadSuccess
	});
}
function uploadSuccess(file, data, response){
	var file = $.parseJSON(data);
	addText(file);
}

function addText(file){
	var path = file.path;
	var newName = file.name;
	var oldName = file.originalName;
	var size = file.size;
	var htmlText = $("#attrs").html();
	htmlText += "<div class=\'downlistprocess\'>";
	htmlText +="<img src='core/common/images/downloadico.png' />";
	htmlText +="<input type='hidden' value=\'"+path+"\'/>";
	htmlText +="<input type='hidden' value=\'"+oldName+"\'/>";
	htmlText +="<input type='hidden' value=\'"+size+"\'/>";
	htmlText +="<ul>";
	htmlText +="<li>"+oldName;
	htmlText +="</li><li>";
	//htmlText +='<a href="javascript:void(0)" onclick="">下载</a>';
	//htmlText +='<a href="javascript:void(0)" onclick="rename(this)">重命名</a>';
	htmlText +="<a href=\'javascript:void(0)\' onclick=\'deleteAttr(this)\'>删除</a>";
	htmlText +="</li>";
	htmlText +="</ul>";
	htmlText +="</div>";
	$("#attrs").html(htmlText);
}

function getAttsList(){
	var topicPaths = '[';
	var obj = $("#attrs").children();
	$(obj).each(function (i){
		var child = $(this).children();
		var oldName = $(child).eq(2).val();
		var size = $(child).eq(3).val();
		oldName = encodeURIComponent(oldName);
		oldName = encodeURIComponent(oldName);
		var str = "{\"filePath\" : \""+$(child).eq(1).val()+"\",";
		str += "\"type\" : \"attachment\",";
		str += "\"size\" : \""+size+"\",";
		str += "\"fileName\" : \""+oldName+"\"}";
		topicPaths += str+",";
	})
	if(topicPaths.lastIndexOf(",") == topicPaths.length-1)
		topicPaths = topicPaths.substr(0,topicPaths.length-1);
	return topicPaths+"]";
}

function deleteAttr(obj) {
	var name =  $(obj).parent().parent().children().eq(0).text();
	var file = $(obj).parent().parent().parent().children().eq(1).val();
	if(confirm("您确定要删除 \""+name+"\"")){
		$.ajax({
			type:"POST",
			 url: "<%=basePath %>contact!deleteByFilePath.action",
			data:"filePath="+file,
			success: function(msg){
			     showTip(msg.result.msg,2000);
			     if(msg.result.success){
			    	 $(obj).parent().parent().parent().remove();
			     }
			}
		});
	}
}

function initForm(){
	$("#form1").validate({
		rules: {
			"fictitious.investmentName":"required"
		},
		messages: {
			"fictitious.investmentName":"请选择公司"
		},
		errorPlacement: function(error, element){
			showTip(error.html());
		},
		submitHandler: function(form){
			$("#filePath").val(getAttsList());
			$(form).ajaxSubmit({
		        dataType: 'json',		        
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("closeWeb();", 2000);
		        	}
		        } 
		    });
		}
	});
}


function closeWeb(){
	window.opener.location.href=window.opener.location.href;window.close();
}

function setSelectedInvestment(rowData,legalPerson){
	$("#investmentName").val(rowData.name);
	$("#investmentId").val(rowData.id);
	$("#regCapital").val(rowData.regCapital);
	$("#introduce").val(rowData.businessScope);
	$("#managerAddress").val(rowData.address);
	$("#legalPerson").val(legalPerson);
}

function appendBillPlanRent(obj){
	var tr = $("<tr></tr>");
	$("#noPlan").remove();
	var roomName = $("<input />",{name:"planRoomName",type:"hidden",value:obj.get("roomName")}).addClass("planRoomName");
	var roomId = $("<input />",{name:"planRoomId",type:"hidden",value:obj.get("roomId")}).addClass("planRoomId");
	var feeType = $("<input />",{name:"feeType",type:"hidden",value:obj.get("feeType")}).addClass("feeType");
	var planFee = $("<input />",{name:"planFee",type:"hidden",value:obj.get("planFee")}).addClass("planFee");
	var realFee = $("<input />",{name:"realFee",type:"hidden",value:obj.get("realFee")}).addClass("realFee");
	var startDate = $("<input />",{name:"planStartDate",type:"hidden",value:obj.get("startDate")}).addClass("planStartDate");
	var endDate = $("<input />",{name:"planEndDate",type:"hidden",value:obj.get("endDate")}).addClass("planEndDate");
	var planPayDate = $("<input />",{name:"planPayDate",type:"hidden",value:obj.get("planPayDate")}).addClass("planPayDate");
	var status = $("<input />",{name:"status",type:"hidden",value:obj.get("status")}).addClass("planStatus");
	var memo = $("<input />",{name:"planMemo",type:"hidden",value:obj.get("memo")}).addClass("planMemo");

	var typeName = "";
	if(obj.get("feeType") == "RENT"){
		typeName = "租金";
	}else if(obj.get("feeType") == "MANAGE"){
		typeName = "物管费";
	}else if(obj.get("feeType") == "ENERGY"){
		typeName = "能源损耗费";
	}
	var feeType2 = $("<td></td>").addClass("lefttd").append(typeName);
	var planFee2 = $("<td></td>").addClass("centertd").append(obj.get("planFee"));
	var planPayDate2 = $("<td></td>").addClass("centertd").append(obj.get("planPayDate"));
	var date = $("<td></td>").addClass("centertd").append(obj.get("startDate")).append("至"+obj.get("endDate"));
	
	var a = $("<a></a>",{href:"javascript:void(0)",click:function(){deletePlan($(this).parent().parent());}}).append($("<img src='core/common/images/del.gif' title='删除'/>"));
	var op = $("<td></td>").addClass("centertd").append(a);
	
	tr.append(feeType2).append(planFee2).append(planPayDate2).append(date).append(op);
	tr.append(roomName).append(roomId).append(feeType).append(planFee).append(realFee).append(startDate).append(endDate).append(planPayDate).append(status).append(memo);
	$("#planListTBody").append(tr);
}

function appendDeposit(obj){
	var tr = $("<tr></tr>");
	$("#noDeposit").remove();
	var depositMemo = obj.get("memo");
	var type = $("<input />",{name:"depositType",type:"hidden",value:obj.get("type")}).addClass("depositType");
	var amount = $("<input />",{name:"depositAmount",type:"hidden",value:obj.get("amount")}).addClass("depositAmount");
	var memo = $("<input />",{name:"depositMemo",type:"hidden",value:depositMemo}).addClass("depositMemo");
	var typeName = "";
	if("WATERELECTRICITY" == obj.get("type")){
		typeName = "水电费押金";
	}else if("ROOM" == obj.get("type")){
		typeName = "房租押金";
	}
	var type2 = $("<td></td>").addClass("lefttd").append(typeName);
	var amount2 = $("<td></td>").addClass("centertd").append(obj.get("amount"));
	if(obj.get("memo")!=null && obj.get("memo").length>10){
		depositMemo = obj.get("memo").substring(0,10);
	}
	var memo2 = $("<td></td>").addClass("centertd").append(depositMemo);
	var a = $("<a></a>",{href:"javascript:void(0)",click:function(){deletePlan($(this).parent().parent());}}).append($("<img src='core/common/images/del.gif' title='删除'/>"));
	var op = $("<td></td>").addClass("centertd").append(a);
	
	tr.append(type2).append(amount2).append(memo2).append(op);
	tr.append(type).append(amount).append(memo);
	$("#depositListTBody").append(tr);
}

function appendRoom(obj){
	var tr = $("<tr></tr>");
	if(checkRoomExist(obj.get("roomId"))){
		alert("房间已在租凭列表中！");
		return false;
	}
	$("#nonData").remove();
	var rentPrice = $("<input />",{name:"rentPrice",type:"hidden",value:obj.get("rentPrice")}).addClass("rentPrice");
	var rentPriceUnit = $("<input />",{name:"rentPriceUnit",type:"hidden",value:obj.get("rentPriceUnit")}).addClass("rentPriceUnit");
	var rebate = $("<input />",{name:"rebate",type:"hidden",value:obj.get("rebate")}).addClass("rebate");
	var rebateRuleId = $("<input />",{name:"rebateRuleId",type:"hidden",value:obj.get("rebateRuleId")}).addClass("rebateRuleId");
	var memo = $("<input />",{name:"memo",type:"hidden",value:obj.get("memo")}).addClass("memo");
	
	var id = $("<input />",{name:"roomId",type:"hidden",value:obj.get("roomId")}).addClass("roomId");
	var name = $("<input />",{name:"roomName",type:"hidden",value:obj.get("roomName")}).addClass("name");
	var roomName = $("<td></td>").addClass("lefttd").append(obj.get("roomName")).append(id).append(name);
	
	var area = $("<input />",{name:"roomArea",type:"hidden",value:obj.get("roomArea")}).addClass("roomArea");
	var roomArea = $("<td></td>").addClass("centertd").append(obj.get("roomArea")).append("平方米").append(area);
	
	var startDate = $("<input />",{name:"startDate",type:"hidden",value:obj.get("startDate")}).addClass("startDate");
	var endDate = $("<input />",{name:"endDate",type:"hidden",value:obj.get("endDate")}).addClass("endDate");
	var date = $("<td></td>").addClass("centertd").append(obj.get("startDate")).append("至"+obj.get("endDate")).append(startDate).append(endDate);

	var a = $("<a></a>",{href:"javascript:void(0)",click:function(){deleteRoom($(this).parent().parent());}}).append($("<img src='core/common/images/del.gif' title='删除'/>"));
	var op = $("<td></td>").addClass("centertd").append(a);

	tr.append(rentPrice).append(rentPriceUnit).append(rebate).append(rebateRuleId).append(memo).append(roomName).append(roomArea).append(date).append(op);
	$("#roomListTBody").append(tr);
	var roomIds = new Array();
	$(".roomId").each(function (){
		roomIds.push($(this).val());
	});
	$("#roomIds").val(roomIds);
}

function checkRoomExist(id){
	var exist = false;
	$(".roomId").each(function(){
		if($(this).val()==id) exist = true;
	});
	return exist;
}

function deleteRoom(tr){
	tr.remove();
}
function deletePlan(tr){
	tr.remove();
}
function deleteDeposit(tr){
	tr.remove();
}

function addBillRent(){
	var roomIds = "";
	$(".roomId").each(function(){
		roomIds += $(this).attr("value")+",";
	})
	var rentPrices = ""; 
	$(".rentPrice").each(function(){
		rentPrices += $(this).attr("value")+",";
	})
	var rentPriceUnits = "";
	$(".rentPriceUnit").each(function(){
		rentPriceUnits += $(this).attr("value")+",";
	})
	var areas = $("#areas").val();
	<%-- fbStart('添加资金计划','<%=BaseAction.rootLocation %>/parkmanager.pb/contract!addBillRent.action?roomIds = '+roomIds+'&startDate='+$('#startDate').val()+'&rentPrices='+rentPrices+'&rentPriceUnits='+rentPriceUnits+'&areas='+areas,500,286); --%>
	fbStart('添加资金计划','<%=BaseAction.rootLocation %>/parkmanager.pb/web/contract/billPlanRent_addByRoomName.jsp?flag=1',500,300);
}

</script>
<style>
.uploadify-button {
		background: #fff /*url("../core/common/images/emailadd.gif")*/;
		background-position: left center;
		background-repeat: no-repeat;
		border: 1px solid #f0f0f0;;
		color: #333;
		font: 12px Arial, Helvetica, sans-serif;
		/*padding-left:10px;*/
		position: relative;
		text-align: center;
		top: 3px;
		width: 100%;
	}
</style>
</head>

<body>
<form action="<%=basePath %>fictitious!save.action" method="post" name="form1" id="form1">
<input type="hidden" id="filePath" name="filePath" value=""/>
<input type="hidden" id="taskId" value="${taskId }"/>
<!--basediv-->
<div id="container" style="height:500px;overflow: scroll;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0"  style="border:1px solid #ececec;">
    <tr>
      <td valign="top">      
         <!--divlays-->
         <div class="basediv" style="min-width:600px;">
			<div class="divlays" style="margin:0px;">
              <table id="insertTxt" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="4">
						<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong><c:if test="${!inpark }">审批表（虚拟）<input type="hidden" name="fictitious.inpark" value="<%=BooleanEnum.NO%>"/></c:if><c:if test="${inpark }">审批表(入驻)<input type="hidden" name="fictitious.inpark" value="<%=BooleanEnum.YES%>"/></c:if></strong></div>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">申请人：</td>
					<td width="29%" class="layerright"><input id="name" disabled value="<%=(user == null ? "" :user.getRealName()) %>" type="text" class="inputauto" /></td>
					<td width="40%" class="layertdleft100">填写日期：</td>
					<td width="29%" class="layerright">
						<input name="applyTime" disabled class="inputauto" value='默认为当前日期'/>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">公司名称：</td>
					<td class="layerright" style="padding:0px 3px;">
						<input id="investmentName" name="fictitious.investmentName"  type="text" class="inputauto" />
						<input type="hidden" name="fictitious.investmentId" id="investmentId"/>
					</td>
					<td><img src="core/common/images/outdiv.gif" style="position:relative;left:-4px;" width="20" height="22" onclick="fbStart('选择项目','<%=BaseAction.rootLocation %>/parkmanager.pb/investment!select.action?type=1',520,400);"/></td>
				</tr>
				<tr>
					<td class="layertdleft100">法人代表：</td>
					<td class="layerright" style="padding:0px 3px;">
						<input id="legalPerson" name="fictitious.legalPerson"  type="text" class="inputauto" />
					</td>
					<td class="layertdleft100">注册资本：</td>
					<td class="layerright" style="padding:0px 3px;">
						<input id="regCapital" name="fictitious.regCapital"  type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'');" />
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">联系人：</td>
					<td class="layerright" style="padding:0px 3px;">
						<input id="contect" name="fictitious.contect"  type="text" class="inputauto" />
					</td>
					<td class="layertdleft100">办公电话：</td>
					<td class="layerright" style="padding:0px 3px;">
						<input id="phone" name="fictitious.phone"  type="text" class="inputauto" />
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">手机：</td>
					<td class="layerright" style="padding:0px 3px;">
						<input id="mobile" name="fictitious.mobile"  type="text" class="inputauto" />
					</td>
					<td class="layertdleft100">传真：</td>
					<td class="layerright" style="padding:0px 3px;">
						<input id="fax" name="fictitious.fax"  type="text" class="inputauto" />
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">实际经营地址：</td>
					<td colspan="3" class="layerright" style="padding:0px 3px;">
						<input id="managerAddress" name="fictitious.managerAddress"  type="text" class="inputauto" /></td>
				</tr>
				<tr>
					<td class="layertdleft100">企业经营范围及规模、优势、前景等情况介绍：</td>
					<td colspan="3" class="layerright" style="padding:0px 3px;">
						<textarea id="introduce" name="fictitious.introduce" class='inputauto' style='height:40px;resize: none;color:#666;'></textarea>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">本公司引荐人或推荐单位意见：</td>
					<td colspan="3" class="layerright" style="padding:0px 3px;">
						<textarea id="suggestion" name="fictitious.suggestion" class='inputauto' style='height:40px;resize: none;color:#666;'></textarea>
					</td>
				</tr>
				<%-- <tr>
					<td colspan="4">
					<div class="emailtop">
						<div class="leftemail">
					    	<strong style="line-height:27px; color:#000; padding-left:5px;">租赁房间</strong>
					    </div>
						<div class="rightemail" style="width:auto; padding-right:10px;">
							<ul>
								<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('添加租赁房间','<%=BaseAction.rootLocation %>/parkmanager.pb/web/contract/subjectRent_add.jsp?saveFlag=1',500,284);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>添加租赁房间</li>
							</ul>
						</div>
					</div>
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nowrap">
							<tbody id="roomListTBody">
								<tr>
						            <td class="tdcenterL" onmousemove="this.className='tdcenterLover'" onmouseout="this.className='tdcenterL'" style="border-left:0;">房间名称</td>
						            <td width="185" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">租用面积 </td>
						            <td width="200" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">租用日期 </td>
						            <td width="90" class="tdrightc" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">操作</td>
					          	</tr>
					          	<tr id="nonData">
					          		<td colspan="4" align="center">无数据</td>
					          	</tr>
				          	</tbody>
				         </table>
				    </div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="emailtop">
						<div class="leftemail">
					    	<strong style="line-height:27px; color:#000; padding-left:5px;">资金计划</strong>
					    </div>
						<div class="rightemail" style="width:auto; padding-right:10px;">
							<ul>
								<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="addBillRent();" class=""><span><img src="core/common/images/emailadd.gif" width="15" height="13"/></span>添加资金计划</li>
							</ul>
						</div>
					</div>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="nowrap">
						<tbody id="planListTBody">
							<tr>
								<td class="tdcenterL" onmousemove="this.className='tdcenterLover'" onmouseout="this.className='tdcenterL'" style="border-left:0;">费用类型</td>
								<td width="145" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计划付费金额</td>
								<td width="120" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计划付费日期</td>
								<td width="160" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计费日期</td>
								<td width="50" class="tdrightc" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">操作</td>
					        </tr>
				          	<tr id="noPlan">
				          		<td colspan="5" align="center">无数据</td>
				          	</tr>
				        </tbody>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="emailtop">
						<div class="leftemail">
					    	<strong style="line-height:27px; color:#000; padding-left:5px;">押金信息</strong>
					    </div>
						<div class="rightemail" style="width:auto; padding-right:10px;">
							<ul>
								<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建押金','<%=BaseAction.rootLocation %>/parkmanager.pb/web/contract/contract_deposit_add.jsp?flag=1',500,175);" class=""><span><img src="core/common/images/emailadd.gif" width="15" height="13"/></span>添加押金</li>
							</ul>
						</div>
					</div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tbody id="depositListTBody">
							<tr>
								<td class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'" style="border-left:0;">押金类型</td>
								<td width="100" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">实际金额</td>
								<td width="100" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">备注</td>
								<td width="100" class="tdrightc" onmousemove="this.className='tdrightcover'" onmouseout="this.className='tdrightc'">操作</td>
							</tr>
							<tr id="noDeposit" onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'" style="background-color: rgb(255, 255, 255); background-position: initial initial; background-repeat: initial initial;">
					            <td colspan="4" align="center">无数据</td>
							</tr>
				    	</tbody>
				    </table>
				</div>
					</td>
				</tr> --%>
				<tr>
					<td colspan="4">
						<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>审批信息</strong></div>
					</td>
				</tr>
			</table>
          	</div>
          </div> 
      </td>
      <td width="335" valign="top">
      <table width="98%" cellspacing="0"cellpadding="0">
          <tr>
            <td>
                <!--basediv-->
                <div class="basediv" > 
                  <!--divlays-->
                  <div class="divlays reminds"> 
                    <!--titlebg-->
                    <div class="righttitle">
                      <ul>
                        <li class="fjTitle"><strong>附件</strong></li>
                        <li class="addfj"><input id="fileUpload" name="uploadify" type="file"/></li>
                      </ul>
                    </div>
                    <div id="attrs">
                    </div>
					<div class="hackbox"></div>
                  </div>
                  <!--//divlays--> 
                    <!--//titlebg--> 
                  <div class="textremind">
                  <ul>
	                  <li><strong>短信</strong> </li>
	                  <li class="remind"> <textarea name="remind" id="remind" cols="45" rows="3" style="resize:none;"></textarea></li>
	                  <li class="remind2">
	                      <input type="checkbox" name="checkbox" id="checkbox" />
	                      	发送短信提醒一下环节人员
	                  </li>
                  </ul>
                  </div>
                  
					<div class="hackbox"></div>
				<!--//basediv-->
				</div>
				<div class="buttondiv2" style="margin-top:10px;">
				  <input  type="image" src="parkmanager.pf/images/bt_fqlc.gif"  name="zConserve" id="zConserve" value="" />
				</div>
              </td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>
</form>
</body>
</html>

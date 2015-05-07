<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.core.preferences.enums.ContactTypeEnum"%>
<%@page import="com.wiiy.pf.entity.ContactBaseLog"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String url = BaseAction.rootLocation+"/";
String uploadPath = BaseAction.rootLocation+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7/>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/base.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/activiti/css/blueprint/print.css" type="text/css" media="print" /> 
<link href="/activiti/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/activiti/css/style.css" />

<script src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/layertable.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script src="/activiti/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
<script src="/activiti/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
<script type="text/javascript" src="parkmanager.pf/js/module/activiti/workflow.js"></script>
<script type="text/javascript"> 
	$(document).ready(function(){
		initTip();
		initUploadify();
		var options = {'pid':"${result.value.hiProcessInstance.id}",'pdid':"${result.value.hiProcessInstance.processDefinitionId}"};
		getNode(options); 
		if("${param.plan}" == 1){
			$("#view").css({ "height": $(window.parent).height()-177+"px", "width": $(this).width()-10+"px" });
			$("#workflowTraceDialog").css({ "height": $(window.parent).height()-182+"px", "width": $(this).width()-10+"px" });
		}else{
			$("#view").css({ "height": $(window.parent).height()-182+"px", "width": $(this).width()-10+"px" });
			$("#workflowTraceDialog").css({ "height": $(window.parent).height()-182+"px", "width": $(this).width()-10+"px" });
		}
		$("#scanView").css({ "height": $(window.parent).height()-182+"px", "width": $(this).width()-10+"px" });
		if("${param.plan}" == 1){
			$("#hq").css({ "height": $("#view").height()-1+"px", "width": $(this).width()-14+"px"});
			
			
		}else{
			$("#hq").css({ "height": $("#view").height()-1+"px", "width": $(this).width()-10+"px"});
		}
		/* $("#view").css({ "height": $(this).height()-59+"px", "width": 540+"px" });
		$("#scanView").css({ "height": $(this).height()-69+"px", "width": 540+"px" });
		$("#hq").css({ "height": $("#view").height()-1+"px", "width": 540+"px" }); */
		/* $(".hq td").css("padding","0px 5px");
		$(".hq .noPadding").css("padding","0px");
		$(".tb2").css({"padding-top":"2px","padding-bottom":"2px"}); */
		initDefinitions();
		var tabId = $("#tabId").val();
		if(tabId!=null && tabId!=''){
			tabSwitch('apptabli','apptabliover','tabswitch',tabId);
		}
	});
	
	function initUploadify(){
		$("#file").uploadify( {
			'auto'				: true, //是否自动开始 默认true
			'multi'				: false, //是否支持多文件上传 默认true
			'formData'			: {'module':'pf','directory':uploadify.directory.images},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
			'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
			'swf'				: uploadify.swf,//上传组件swf
			'width'				: 105,//按钮图片的长度
			'height'			: uploadify.height,//按钮图片的高度
			'buttonText'		: '<span style="top:5px;"><img src="core/common/images/print_btn.gif" /></span>文件上传 ', //按钮上的文字
			'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
			'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
			'removeTimeout'		: uploadify.removeTimeout, //上传完成移除出队列 默认true
			'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
			'fileTypeDesc'		: '*.*',//选择文件对话框文件类型描述信息
			'fileTypeExts'		: '*.*',//可上传上的文件类型
			'onUploadSuccess'	: uploadSuccess
		});
	}
	function uploadSuccess(file, data, response){
		var file = $.parseJSON(data);
		var postData = {"contactBaseAtt.contactId":"${id}","contactBaseAtt.oldName":file.originalName,"contactBaseAtt.newName":file.path,"contactBaseAtt.path":file.path,"contactBaseAtt.size":file.size,"contactBaseAtt.type":"${type}","contactBaseAtt.processInstanceId":"${pid}"};
		var pid="${result.value.hiProcessInstance.id}";
		$.post("<%=basePath%>contactBaseAtt!save.action",postData,function(callbackData){
			showTip(callbackData.result.msg);
			setTimeout(function(){
				location.href="<%=basePath%>contact!myView.action?type="+$("#type").val()+"&id="+$("#id").val()+"&tabId=2&pid="+pid;
			},2000);
		});
	}

	function viewDetails(type,id){
	}
	function deleteFile(id){
		var pid="${result.value.hiProcessInstance.id}";
		if(confirm("确定删除？")){
			$.post("<%=basePath %>contactBaseAtt!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
				setTimeout(function(){
					location.href="<%=basePath%>contact!myView.action?type="+$("#type").val()+"&id="+$("#id").val()+"&tabId=2&pid="+pid;
				},2000);
			});
		}
	}
	function downLoadFile(path,name){
		location.href="<%=BaseAction.rootLocation %>/core/resources/"+path+"?name="+name;
	}
	function openAtt(path){
		var src = "<%=basePath%>contactBaseAtt!attImage.action?imgPath="+path;
		fbStart("扫描件",src,800,638);
	}
	
	function initDefinitions(){
		var id= "${result.value.hiProcessInstance.processDefinitionId}";
		var pid="${result.value.hiProcessInstance.id}";
		$.ajax({
			 "url" : "<%=basePath%>contact!findDefinitions.action?id="+id+"&pid="+pid,
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
										  //info += "负责人:[";
										  info += "[";
										  info += taskInfo.assignee.firstName;
										  if(taskInfo.assignee.id==null)info +="(待创建)";
										  info += "] ";
										  
									  }
									  if(taskInfo.candidateUserList!=null&&taskInfo.candidateUserList.length>0){//流程中已经指派负责人
										  var arrays=taskInfo.candidateUserList;
										  //info += "候选审批人:";
										  for(var j=0;j<arrays.length;j++){
											  info += "[";
											  info +=arrays[j].firstName;
											  if(arrays[j].id==null)info +="(待创建)";
											  info += "] ";
										  } 
									  }
									  if(taskInfo.candidateGroupList!=null&&taskInfo.candidateGroupList.length>0){//流程中已经指派负责人
										  var arrays=taskInfo.candidateGroupList;
										 // info += "候选审批角色:";
										  for(var j=0;j<arrays.length;j++){
											  info += "[";
											  info +=arrays[j].name;
											  if(arrays[j].id==null)info +="(待创建)";
											  info += "] ";
										  } 
									  }
									  
								  }
								  if(taskInfo.historicVariableDtos!=null&&taskInfo.historicVariableDtos.length>0){
									  info += "<br>";
									  info +=taskInfo.dealUser.firstName+":";
									  var displayTime=true;
									  for(var j=0;j<taskInfo.historicVariableDtos.length;j++){
										  if(taskInfo.historicVariableDtos[j].variableTypeName=='boolean'){
											  if(taskInfo.historicVariableDtos[j].value=='true'){
												  info += "<font color='green'>同意申请("+taskInfo.historicVariableDtos[j].time+")</font>";
												  displayTime=false;
											  }
											  else if(taskInfo.historicVariableDtos[j].value=='false'){
												  info += "<font color='red'>拒绝申请("+taskInfo.historicVariableDtos[j].time+")</font>";
												  displayTime=false;
											  }
										  }
										  else{
											  info += taskInfo.historicVariableDtos[j].value;
											  if(displayTime)
												  info+="("+taskInfo.historicVariableDtos[j].time+")";
										  }
										  info += "<br>";
									  }
									  
								  }
							 
							  
								  htmlTxt += "<tr><td class=\'layertdleft100\'>"+definition[i].nameExpression.expressionText+"：</td>";
								  htmlTxt += "<td colspan=\'3\' class=\'layerright\' style=\'padding-bottom:2px;padding-top:2px;padding-right:5px;\'>";
								  if(id < 0){
									  htmlTxt += "<div  style=\'cursor:pointer;line-height:22px;min-height:40px;resize: none;color:#666;\'>"+info+"</div>";
								  }else{
									  htmlTxt += "<div  style=\'cursor:pointer;background-color:#FFFFCB;line-height:22px;min-height:40px;resize: none;color:#666;\'>"+info+"</div>";
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
	
	function initHeight(){
		htmlValue = $("#workflowTraceDialog").height();
		if(htmlValue == null){
			showTip("正在加载图片,请稍后……",999999);
			$("#workflowTraceDialog").css("display",'none');
			loop();
		}else{
			dealHeight('workflowTraceDialog');
		}
	}
	function loop(){
		htmlValue = $("#workflowTraceDialog").height();
		if(htmlValue == null){
			setTimeout("loop()",500);
		}else{
			showTip("图片加载成功",2000);
			setTimeout("dealHeight('workflowTraceDialog')",800);
		}
	}
	
	function dealHeight(id){
		obj = $("#"+id);
		$(obj).css("position","relative");
		var htmlH = $("#view").height();
		var objH = (htmlH - $(obj).height()) /2;
		//if(objH > 0){
			$(obj).css("top",'20px');
		//}
		$(obj).css("left",'10px');
		$(obj).css("display",'block');
	}
</script>
<style>
h1 { font:bold 16px/32px ''; height:32px; text-align:center;}
ul.listStyle1 li{ margin:5px; background:#f8f8f8; padding:5px 10px;}
.uploadify-button {
		background: #f0f0f0 /*url("../core/common/images/emailadd.gif")*/;
		background-position: left center;
		background-repeat: no-repeat;
		border: 1px solid #f0f0f0;;
		color: #333;
		font: 12px Arial, Helvetica, sans-serif;
		/*padding-left:10px;*/
		position: relative;
		text-align: center;
		top: 0px;
		width: 100%;
	}
.emailtop .leftemail ul li span {
		display: inline;
		float: left;
		height: 26px;
		line-height: 26px;
		padding-right: 3px;
		position: relative;
		top: -1px;
	}
	
	.uploadify:hover .uploadify-button {
		background-color: #e4f3ff;
		color: #333;
		background-position: left center;
	}	
	
	.titlebg2{color:#FFF;height:27px; background:#67839b;border-bottom:1px solid #cbcbcb;line-height:27px;font-weight:bold;font-family:Georgia, "Times New Roman", Times, serif;}
	.emaildown2{padding-top:5px; padding-left:5px; padding-bottom:10px;margin-top:10px; text-align:left;}
	.emaildown2 h1{ font-size:14px; height:25px; line-height:25px;}
	.emaildown2 .downlistprocess2{ padding:15px 5px 5px 5px;background:#f7f7f7; border:1px solid #e4e4e4;margin-bottom:5px;}
	.emaildown2 .downlistprocess2 img{ float:left; padding-right:5px; top:2px;}
	.emaildown2 .downlistprocess2 ul li{ height:16px;}
	.emaildown2 .downlistprocess2 ul li span{ color:#999;}
	.emaildown2 .downlistprocess2 ul li a:link,.emaildown .downlistprocess2 ul li a:visited,.emaildown .downlistprocess2 ul li a:hover{ color:#1f699d; padding-right:10px; text-decoration:underline;}
	

</style>

</head>
<body>
	<input type="hidden" id="type" value="${type }"/>
	<input type="hidden" id="id" value="${id }"/>
	<input type="hidden" id="tabId" value="${tabId }"/>
	<input type="hidden" id="pid" value="${pid }"/>
		<div class="apptab" id="apptab">
			<ul>
				<li class="apptabliover" onclick="tabSwitch('apptabli','apptabliover','tabswitch',0)">会签单</li>
				<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',1);initHeight();">流程图</li>
				<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',2)">附件</li>
			</ul>
		</div>
			
		<!--会签单-->	
		<!--baseDiv-->
		<div class="basediv tabswitch" style="margin-right:6px;margin-left: 2px;" id="textname">
			<div class="appname" id="hq" style="position: relative;overflow-y: scroll;" >
				<s:set var="view">${actionName}!view</s:set>
				<s:action name="%{#view}" namespace="/" executeResult="true"></s:action>
			</div>
        </div>
		<!--//baseDiv-->
		
		<!-- baseDiv 流程图-->
		<div class="basediv tabswitch" style="margin: 0px;display:none;padding:0px 2px;" id="textname">
			<div class="appname" id="view"  style="overflow: auto;">
				<div id="workflowTraceDialog" ></div>
			</div>
		</div>
		<!--//baseDiv-->
			
		<!-- baseDiv -->
		<div class="basediv tabswitch" style="margin: 0px;display:none;padding:0px 2px;" id="textname">
			<div class="appname" id="scanView" style="overflow-x:hidden;overflow-y:scorll;min-height: 200px;">
		 		<s:set var="attList">${actionName}!attList</s:set>
				<s:action name="%{#attList}" namespace="/" executeResult="true"></s:action>
			 	<%-- <div class="emailtop">
	                <div class="leftemail">
	                    <ul>
	                       <li onmouseover="this.className='libg'" onmouseout="this.className=''" ><input type="file" id="file"/></li>
	                    </ul>
	                </div>
	                </div>
				<div class="divlays" id="upFile" style="padding: 2px 5px 10px 5px;<c:if test="${fn:length(contactAttList) eq 0 }">display:none</c:if>"  >
					<div class="emaildown"
						style="margin-top: 5px; border-bottom: 1px solid #e4e4e4; border-left: 1px solid #e4e4e4; border-right: 1px solid #e4e4e4;">
						<!--外框-->
						<div style="display: block;">
						<c:forEach items="${contactAttList }" var="att">
							<div class="downlistleft">
								<img src="core/common/images/print.png"/>
									<ul>
										<li>${att.oldName }<span>(<format:fileSize size="${att.size}"/>)</span></li>
										<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.oldName}');">下载</a><a
											href="javascript:void(0)" onclick="openAtt('${att.newName}')">打开</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<a href="javascript:void(0)"></a></li>
									</ul>
							</div>
							</c:forEach>
							<div class="hackbox"></div>
						</div>
						<!--外框-->
						<div class="hackbox"></div>
					</div>
				</div> --%>
		</div>
	</div>
	<!--//baseDiv-->
</body>
</html>

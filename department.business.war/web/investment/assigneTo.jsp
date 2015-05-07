<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=BaseAction.rootLocation %>/" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		
		<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
		<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
		<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
		 
		<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.js"></script>
		<script type="text/javascript" src="core/common/js/tools.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.form.js"></script>

		<script type="text/javascript">
			$(function(){
				initTip();
			});
			function checkForm(){
				if(notNull("prevName","原招商人员")&&notNull("name","转交人员")){
					if(!$("#contectInfo").attr("checked") && !$("#investment").attr("checked")){
						showTip("请选择转交内容");
						return;
					}
					var contectInfo = false;
					var investment = false;
					var type = "";
					if($("#contectInfo").attr("checked")){
						type = "contectInfo";
						contectInfo = true;
					}
					if($("#investment").attr("checked")){
						type = "investment";
						investment = true;
					}
					if(investment && contectInfo){
						type = "all";	
					}
					var id = $("#assigneId").val();
					$("#form1").ajaxSubmit({
				        dataType: 'json',
				        data:{id:id,type:type},
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("fb.end();",2000);
				        	}
				        } 
				    });
				}
			}
			function setSelectedUser(user){
				if(userType==1){
				$("#prevId").val(user.id);
				$("#prevName").val(user.realName);
				}else if(userType==2){
					$("#assigneId").val(user.id);
					$("#name").val(user.realName);
				}
			}
			function selectUser(obj){
				userType=obj;
				fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!selectSelf.action',520,400);
			}
		</script>

	</head>

	<body>
		<form action="<%=basePath %>investment!assigneTo.action" method="post" name="form1" id="form1">
			<div class="basediv">
				<div class="divlays" style="margin:0px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="layertdleft150"><span class="psred">*</span>原招商人员：</td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="35"><input id="prevId" name="prevId" type="hidden" class="inputauto" value="<%=BusinessActivator.getSessionUser(request).getId() %>" /><input id="prevName" value="<%=BusinessActivator.getSessionUser(request).getRealName() %>" readonly="readonly" type="text" class="inputauto" onclick="selectUser(1)" /></td>
										<td width="25"><img style="position: relative;left:-1px;" onclick="selectUser(1)" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="layertdleft150"><span class="psred">*</span>转交人员：</td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="35"><input id="assigneId" name="assigneId" type="hidden" class="inputauto" /><input id="name" readonly="readonly" type="text" class="inputauto" onclick="selectUser(2)" /></td>
										<td width="25"><img style="position: relative;left:-1px;" onclick="selectUser(2)" src="core/common/images/outdiv.gif" width="20" height="22" /></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							 <td class="layertdleft150">转交内容：</td>
							<td class="layerright">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
						      		<tr>
						      		<td><input type="checkbox" id="contectInfo" style="vertical-align:middle;"/>&nbsp;线索</td>
						      		<td><input type="checkbox" id="investment"  style="vertical-align:middle;"/>&nbsp;项目</td>
									</tr>
						      	</table>
					      	</td> 
						</tr>
					</table>
				</div>
				<div class="hackbox"></div>
			</div>
			<div class="buttondiv">
				<label><input name="Submit" type="button" class="savebtn" value="" onclick="checkForm()"/></label>
				<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
			</div>
		</form>
	</body>
</html>


<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
 
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript"> 
	$(document).ready(function(){
		initTip();
		initForm();
	});
	function initForm(){
		$("#form1").validate({
			rules:{
				"orgId":"required",
				"username":"required",
				"password":"required"
			},
			messages: {
				"orgId":"请选择机构",
				"username":"请输入用户名",
				"password":"请输入密码"
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
	function setSelectedOrg(selectedOrg) {
		$("#user_org_id").val(selectedOrg.id);
		$("#user_org_name").val(selectedOrg.name);
	}
</script>
</head>
 
<body>
<form action="<%=basePath %>agency!saveAccount.action" method="post" name="form1" id="form1">
<input type="hidden" value="${id}" name="id"/>
<div class="basediv">
	<div class="divlays" style="margin:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
              <td class="layertdleft"><span class="psred">*</span>所属机构：</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                  <input id="user_org_id" name="orgId" type="hidden" />
                  <input id="user_org_name" name="user_org_name" type="text" class="input170" readonly="readonly"/>
                  </td>
                  <td align="right"><a href="javascript:void(0);" onclick="fbStart('选择机构','<%=BaseAction.rootLocation %>/core/org!select.action',520,460);"><img src="core/common/images/outdiv.gif" width="20" height="22" /></a></td>
                </tr>
              </table></td>
            </tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>用户名：</td>
				<td class="layerright">
					<input id="userName" name="username" type="text" class="inputauto" />
				</td>
			</tr>
			<tr>
				<td class="layertdleft100"><span class="psred">*</span>密码：</td>
				<td class="layerright">
					<input id="passWord" name="password" type="password" class="inputauto" />
				</td>
			</tr>
		</table>
	</div>
	<div class="hackbox"></div>
</div>
<div class="buttondiv">
	<label><input name="Submit" type="submit" class="savebtn" value=""/></label>
	<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end()"/></label>
</div>
</form>
</body>
</html>
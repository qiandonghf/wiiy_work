<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>


    <script type="text/javascript">
        $(document).ready(function() {
    		initTip();
    		initForm();
        });
        function initForm() {
    		$("#roleForm").validate({
    			rules: {
    				"role.name":"required"
    			},
    			messages: {
    				"role.name":"请输入角色名称"
    			},
    			errorPlacement: function(error, element){
    				showTip(error.html());
    			},
    			submitHandler: function(form){
    				$('#roleForm').ajaxSubmit({ 
    			        dataType: 'json',		        
                    	success:function(root, statusText) {
    	                    if (root.result.success) {
    	                        getOpener().refreshDataTables();
    	                        fb.end();
    	                        //parent.$.showMessage("保存成功！");
    	                    } else {
    	                        showTip(root.result.msg, 2000);
    	                    }
                    	}
    			    });
    			}
    		});
        }
    </script>
</head>

<body>
<form id="roleForm" name="roleForm" method="post" action="${contextLocation}role!update.action">
<input type="hidden" name="role.id" value="${role.id}" />
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="layertdleft"><span class="psred">*</span>角色ID：</td>
      <td><label>
        <input name="role.id" type="text" class="input200" value="${role.id}" disabled="disabled"/>
      </label></td>
    </tr>
   <%-- <tr>
      <td class="layertdleft">状态：</td>
      <td>
		<enum:radio name="role.entityStatus" type="com.wiiy.commons.preferences.enums.EntityStatus" checked="role.entityStatus" />
	  </td>
    </tr> --%>
    <tr> 
      <td class="layertdleft">角色名称：</td>
      <td style="padding-left: 5px;padding-top: 5px;padding-bottom: 1px;"><textarea name="role.name"  style=" height:50px;" class="textareaauto">${role.name}</textarea></td>
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
  <label>
  <input name="Submit" type="submit" class="savebtn" value="" />
  </label>
  <input name="Submit2" type="button" class="cancelbtn" value="" onclick="fb.end();"/>
</div>
</form>
</body>
</html>

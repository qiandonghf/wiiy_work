<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	
	<script type="text/javascript">
		$(function(){
			initTip();
		});
		function iframeSwitch(url,li,index){
			if(index == 1){
				$(".buttondiv").show();
				$("#iframe").height(408);
			}else{
				$(".buttondiv").hide();
				$("#iframe").height(438);
			}
			$(li).removeClass().addClass("apptabliover");
			$(li).siblings().removeClass().addClass("apptabli");
			$("#iframe").attr("src",url);
		}	
		
		function saveForm(){
			var form = $(window.frames[0].document).find("#form1");
			if($(form).find("input[name='contract.id']").val() == null){
				showTip("页面正在加载中,请稍后……",2000);return;
			}
			var name = $(form).find("input[name='contract.name']").val();
			name = trim(name);
			var code = $(form).find("input[name='contract.code']").val();
			code = trim(code);
			if(name == ''){
				showTip("请输合同名称",2000); 
				$(form).find("input[name='contract.name']").focus();
				return;
			}
			if(code == ''){
				showTip("请输入合同编号",2000); 
				$(form).find("input[name='contract.code']").focus();
				return;
			}
			var startTime = $(form).find("input[name='contract.startTime']").val();
			var signDate = $(form).find("input[name='contract.signDate']").val();
			if(signDate < startTime){
				showTip("有效日期开始时间不能小于有效日期结束时间",2000); 
				return;
			}
			$(form).ajaxSubmit({
		        dataType: 'json',
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("getOpener().reloadList();", 2000);
		        	}
		        } 
		    });
		}
		function setSelectedUser(contect){
			frames[0].setSelectedUser(contect);

		}
		function setSelectedCustomer(customer){
			frames[0].setSelectedCustomer(customer);
		} 
		
		function setSelectedProject(project){
			frames[0].setSelectedProject(project);
		}
		
		function reloadList(){
			frames[0].reloadList();
		}
	</script>
</head>
<body>
<div class="basediv">
	<div class="divlays" style="margin:0px;padding-bottom:0px;background-color: #eeeeee;">
		<div class="apptab" id="tableid">
			<ul>
				<li class="apptabliover" onclick="iframeSwitch('<%=basePath %>contract!edit.action?id=${id}',this,1)">基本信息</li>
				<li class="apptabli" onclick="iframeSwitch('<%=basePath %>web/contract/contractAtt.jsp?id=${id }',this,2)">合同文件</li>
				<li class="apptabli" onclick="iframeSwitch('<%=basePath %>web/contract/billPlanRent.jsp?id=${id }',this,3)">结算计划</li>
				<li class="apptabli" onclick="iframeSwitch('<%=basePath %>web/contract/billRent.jsp?id=${id }',this,4)">结算流程</a></li>
			</ul>
		</div>
	</div>
	<iframe id="iframe" scrolling="no" frameborder="0" src="<%=basePath %>contract!edit.action?id=${id}" name="sale" width="100%" height="408"></iframe>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="button" class="savebtn" onclick="saveForm();"/></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
 </div>
</body>
</html>

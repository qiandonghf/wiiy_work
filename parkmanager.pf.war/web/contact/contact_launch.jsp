<%@page import="com.wiiy.pf.entity.ProcessType"%>
<%@page import="com.wiiy.core.entity.DataDict"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@page import="com.wiiy.pf.dto.ProcessDto"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		initClick();
	});
	
	function initClick(){
		$("#container").find(".menu-hit").click(function(node){
			if($(this).hasClass("tree-collapsed")){
				$(this).removeClass("tree-collapsed");
				$(this).addClass("tree-expanded");
				$(this.parentNode).siblings().eq(0).show();
			}else{
				$(this).removeClass("tree-expanded");
				$(this).addClass("tree-collapsed");
				$(this.parentNode).siblings().eq(0).hide();
			}
		});
	}
	
	function addProcess(conName,name){
		name = name.toLowerCase();
		fbStart('新建'+conName,'<%=basePath %>web/contact/contact_'+name+'_add.jsp',500,189);
	}
</script>
</head>
<body>
<!--//emailtop-->
<!--container-->
<div class="msglist" id="msglist" style="padding-bottom:0px;">
	<div id="container">
		<table	width="100%">
			<%
			List<DataDict> flowTypes = 
				(List<DataDict>)request.getAttribute("flowTypes"); 
			for (DataDict dataDict : flowTypes) {
			%>
			<tr>
				<td>
					<div class="layertdleftauto" style="text-align:left;padding-left:10px;">
						<span class="menu-hit tree-expanded"></span>
						<span class="tree-title" style=""><%=dataDict.getDataValue() %></span>
					</div>
					<div style="margin-bottom:5px;">
						<% 
						List<ProcessDto> dtoList = 
							(List<ProcessDto>)request.getAttribute("dtoList"); 
						for (ProcessDto dto : dtoList) {
							ProcessType type = dto.getProcessType();
							if(!(type.getType().equals(dataDict.getId())))
								continue; 
						%>
						<div style="margin:10px 15px;width:200px;float:left;">
							<ul>
								<li onmouseover="this.className='libg'" 
									onmouseout="this.className=''" 
									style="cursor:pointer;"
									onclick="addProcess('<%=type.getFormName().getTitle()+"v"+dto.getVersion()%>','<%=type.getFormName()%>');">
									<span><%=type.getFormName().getTitle() %>&nbsp;<i>V&nbsp;<%=dto.getVersion() %></i></span>
									<input type="hidden" value='<%=type.getType()+","+dataDict.getId()%>'/>
								</li>
							</ul>
						</div>
						<%}%>
					</div>
				</td>
			</tr>
			<%}%>
		</table>
	</div>
</div>
<!--//container-->
</body>
</html>

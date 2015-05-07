<%@page import="com.wiiy.business.entity.Patent"%>
<%@page import="com.wiiy.business.entity.Brand"%>
<%@page import="com.wiiy.business.entity.Staffer"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.business.entity.IncubationInfo"%>
<%@page import="com.wiiy.business.entity.BusinessCustomerInfo"%>
<%@page import="com.wiiy.business.entity.BusinessCustomer"%>
<%@page import="com.wiiy.commons.util.DateUtil"%>
<%@page import="com.wiiy.business.dto.CustomerSearchResultDto"%>
<%@page import="com.wiiy.hibernate.Result"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
List<String> propertyList = (List<String>)session.getAttribute("propertys");
boolean patent_name = propertyList.contains("patent.name");
boolean patent_type = propertyList.contains("patent.type");
boolean patent_applyTime = propertyList.contains("patent.applyTime");
boolean patent_buyTime = propertyList.contains("patent.buyTime");
boolean patent_summery = propertyList.contains("patent.summery");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/base.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript">
	$(function(){
		$("#overflowAuto").height(getTabContentHeight()-60);
	});
	function jumpPage(page){
		var url = "<%=basePath%>search.action";
		url += "?page="+page;
		url += "&tab=${tab}";
		location.href=url;
	}
</script>
</head>
<body>
	<div class="pm_msglist" id="msglist">
		<div class="emailtop">
			<div class="leftemail">
				<ul>
					<li onmouseover="this.className='libg'" onmouseout="this.className=''" class="" onclick="location.href='<%=basePath%>search!export.action'"><span><img src="core/common/images/xls_min.png"></span>导出</li>
					<li onmouseover="this.className='libg'" onmouseout="this.className=''" class="" onclick="parent.location.href='<%=basePath%>search!before.action'"><span><img src="core/common/images/back.png"></span>返回</li>
				</ul>
			</div>
		</div>
		<div class="overflowAuto" id="overflowAuto">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_sy1" style="white-space:nowrap; table-layout:auto;">
			<colgroup>
				<col>
				<col>
			</colgroup>
			<thead>
				<tr class="table_titlebgcolor" style="font-weight:bold;">
					<%
						out.println("<th scope='col'>企业编号</th>");
						out.println("<th scope='col'>企业名称</th>");
						
						if(patent_name) out.println("<th scope='col'>专利名称</th>");
						if(patent_type) out.println("<th scope='col'>专利类别</th>");
						if(patent_applyTime) out.println("<th scope='col'>申请日期</th>");
						if(patent_buyTime) out.println("<th scope='col'>授权公告日</th>");
						if(patent_summery) out.println("<th scope='col'>授权情况</th>");
						
					%>
				</tr>
			</thead>
			<tbody>
				<%
				Result<List<Patent>> result = (Result<List<Patent>>)request.getAttribute("result");
				List<Patent> list = result.getValue();
				if(list!=null)
				for(Patent patent : list){
					
					out.println("<tr>");
					
					out.println("<td>"+patent.getCustomer().getCode()+"</td>");
					out.println("<td>"+patent.getCustomer().getName()+"</td>");
					
					if(patent_name){if(patent!=null && patent.getName()!=null) out.println("<td>"+patent.getName()+"</td>");
					else out.println("<td>&nbsp;</td>");}
					if(patent_type){if(patent!=null && patent.getType()!=null) out.println("<td>"+patent.getType().getDataValue()+"</td>");
					else out.println("<td>&nbsp;</td>");}
					if(patent_applyTime){if(patent!=null && patent.getApplyTime()!=null) out.println("<td>"+DateUtil.format(patent.getApplyTime())+"</td>");
					else out.println("<td>&nbsp;</td>");}
					if(patent_buyTime){if(patent!=null && patent.getBuyTime()!=null) out.println("<td>"+DateUtil.format(patent.getBuyTime())+"</td>");
					else out.println("<td>&nbsp;</td>");}
					if(patent_summery){if(patent!=null && patent.getSummery()!=null) out.println("<td>"+patent.getSummery()+"</td>");
					else out.println("<td>&nbsp;</td>");}
					
					out.println("</tr>");
				}
				%>
			</tbody>    
		</table>
	</div>
	<jsp:include page="../pager.jsp" />
</div>
</body>
</html>

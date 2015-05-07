<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>融资项目信息表2</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="department.business/web/style/print.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript">
	 function selectedYear(){
		var yearNo = $("#yearNo").val();
		var monthNo = $("#monthNo").val();
		location.href="<%=basePath%>product!listByProjectApply.action?yearNo="+yearNo+"&monthNo="+monthNo;
	}
	function selectedMonth(){
		var yearNo = $("#yearNo").val();
		var monthNo = $("#monthNo").val();
		if(yearNo==""){
			alert("请先选择年份");
			return;
		}
		location.href="<%=basePath%>product!listByProjectApply.action?yearNo="+yearNo+"&monthNo="+monthNo;
	}
	function exportData(){
		var yearNo = $("#yearNo").val();
		var monthNo = $("#monthNo").val();
		if(yearNo==""){
			yearNo = null;
		}
		if(monthNo==""){
			monthNo = null;
		}
		$("#exportDataFrame").attr("src", "<%=basePath%>product!export.action?yearNo="+yearNo+"&monthNo="+monthNo);
	}
</script>
<style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:12px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
</head>

<body style="background:#fff;">
<iframe id="exportDataFrame" src="about:blank" style="display:none" width="0px" height="0px"></iframe>
<div id="overflowAuto">
	<div style="width:820px; padding:10px 0 5px; margin:0 auto;">
	<h2 style="text-align:center; font-size:18px; font-family:verdana,arial,sans-serif; ">融资项目信息表</h2>
	 <div style="overflow:hidden; zoom:1; padding:10px 0; font-family:verdana,arial,sans-serif; font-size: 14px">
		&nbsp;请选择年月
		<label>
        <select id="yearNo" name="yearNo" onchange="selectedYear();">
         	<option value="">----请选择----</option>
          	<c:forEach items="${years}" var="year">
     			<option value="${year}" <c:if test="${year eq yearNo}">selected="selected"</c:if>>${year}</option>
			</c:forEach>
        </select>
                      年
        </label>
    	<label>
        <select id="monthNo" name="monthNo" onchange="selectedMonth();">
         	<option value="">----请选择----</option>
          	<c:forEach begin="1" end="12" var="month">
     			<option value="${month}" <c:if test="${month eq monthNo}">selected="selected"</c:if>>${month}</option>
			</c:forEach>
        </select>
       	月
        </label>
       </div>
       </div>
<div class="basediv" style="width:99%">
    <!--divlay-->
<div class="divlays" style="margin:0px; padding:0px;  ">
  <table border="0" cellpadding="0" cellspacing="0" class="gridtable" align="center">
        <tr>
          <th width="30">序号</th>
          <th >企业</th>
          <th >阶段</th>
          <th >技术领域</th>
          <th >项目名称</th>
          <th >详细信息</th>
          <th >是否发布到网站</th>
        </tr>
        <tr></tr>
        <c:forEach items="${result.value}" var="dto" varStatus="status">
        	<tr>
	          	<td  >&nbsp;${status.index+1}</td>
	        	<td  >&nbsp;${dto.customer}</td>
	        	<td  >&nbsp;${dto.stage.dataValue}</td>
	        	<td  >&nbsp;${dto.technic.dataValue}</td>
	        	<td  >&nbsp;${dto.name}</td>
	        	<td  >&nbsp;${dto.introduction}</td>
	        	<td  >&nbsp;${dto.pub.title}</td> 
	        </tr>
        </c:forEach>
  </table>
</div>
</div>
</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.commons.action.BaseAction"%>
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
<title>知识产权表</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="department.business/web/style/print.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript">

	function selectedYear(){
		var yearNo = $("#yearNo").val();
		var monthNo = $("#monthNo").val();
		var n=${param.flag};
		switch(n){
		case 0:location.href="<%=basePath%>patent!listByKnowledge.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=0";
		break;
		case 1:location.href="<%=basePath%>copyright!listByKnowledge.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=1";
		break;
		case 2:location.href="<%=basePath%>certification!listBycertification.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=2";
		break;
		case 3:location.href="<%=basePath%>brand!listByBrand.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=3";
		break;
		}
	}
	function selectedMonth(){
		var yearNo = $("#yearNo").val();
		var monthNo = $("#monthNo").val();
		if(yearNo==""){
			alert("请先选择年份");
			return;
		}
		var n=${param.flag};
		alert("Year:"+n);
		switch(n){
		case 0:location.href="<%=basePath%>patent!listByKnowledge.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=0";
				break;
		case 1:location.href="<%=basePath%>copyright!listByKnowledge.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=1";
				break;
		case 2:location.href="<%=basePath%>certification!listBycertification.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=2";
				break;
		case 3:location.href="<%=basePath%>brand!listByBrand.action?yearNo="+yearNo+"&monthNo="+monthNo+"&flag=3";
				break;		
		}
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
		$("#exportDataFrame").attr("src", "<%=basePath%>projectApply!export.action?yearNo="+yearNo+"&monthNo="+monthNo);
	}
	function switchtab(n){
		switch(n){
		case 0 :
				location.href="<%=basePath%>patent!listByKnowledge.action?flag=0";
				break;
		case 1 :
				location.href="<%=basePath%>copyright!listByKnowledge.action?flag=1";
				break;
		case 2 :
				location.href="<%=basePath%>certification!listBycertification.action?flag=2";
				break;
		case 3 :
				location.href="<%=basePath%>brand!listByBrand.action?flag=3";
				break;
		}
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
	<h2 style="text-align:center; font-size:18px; font-family:verdana,arial,sans-serif; ">知识产权表</h2>
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
				<!--table切换开始-->
				<div class="apptab" id="tableid">
					<ul>
					<!-- tabSwitch('apptabli','apptabliover','tabswitch',3); -->
						<%-- class='apptabli<c:if test="${flag eq 1 }" >over</c:if>' --%>
						<li class='apptabli<c:if test="${flag eq 0 }" >over</c:if>' onclick="switchtab(0);">专利</li>
						<li class='apptabli<c:if test="${flag eq 1 }" >over</c:if>' onclick="switchtab(1);">著作权</li>
						<li class='apptabli<c:if test="${flag eq 2 }" >over</c:if>' onclick="switchtab(2);">认证</li>
						<li class='apptabli<c:if test="${flag eq 3 }" >over</c:if>' onclick="switchtab(3);">商标</li>
					</ul>
				</div>
			<!--//table切换开始-->
			<!--专利-->
			 <c:if test="${flag eq 0}">
			<div class="basediv tabswitch" style="margin:0px;" id="textname">
				<table id="patentId" border="0" cellpadding="0" cellspacing="0" class="gridtable" align="center">
			        <tr>
			          <th width="30">序号</th>
			          <th >企业</th>
			          <th >专利名称</th>
			          <th >专利号</th>
			          <th >专利类型</th>
			          <th >授权购买日期</th>
			          <th >专利状态</th>
			       	  <th >专利申请人</th>
			          <th >专利申请日期</th>
			          <th >专利来源</th>
			          <th >专利失效日期</th>
			          <th >是否发布到网站</th>
			        </tr>
			        <tr></tr>
			         <c:forEach items="${result.value}" var="dto" varStatus="status">
			        	<tr>
				          	<td  >&nbsp;${status.index+1}</td>
				        	<td  >&nbsp;${dto.customer}</td>
				        	<td  >&nbsp;${dto.name}</td>
				        	<td  >&nbsp;${dto.serialNo}</td>
				         	<td  >&nbsp;${dto.type.dataValue}</td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.buyTime}" pattern="yyyy-MM-dd"/></td>
				        	<td  >&nbsp;${dto.state.dataValue}</td>
				        	<td  >&nbsp;${dto.appler}</td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.applyTime}" pattern="yyyy-MM-dd" /></td>
				        	<td  >&nbsp;${dto.source.dataValue}</td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.expireTime}" pattern="yyyy-MM-dd" /></td>
				        	<td  >&nbsp;${dto.pub.title}</td>
				        </tr>
			        </c:forEach>
			  </table>
			</div>	
					</c:if>
			<!--著作权-->
			<c:if test="${flag eq 1}">
			<div class="basediv tabswitch" style="margin:0px;" id="textname">
				<table id="copyrightId" border="0" cellpadding="0" cellspacing="0" class="gridtable" align="center">
			        <tr>
			          <th width="30">序号</th>
			          <th >企业</th>
			          <th >著作权名称</th>
			          <th >著作权号</th>
			          <th >申请日期</th>
			          <th >著作申请人</th>
			          <th >著作权类型</th>
			          <th >著作权生效日期</th>
			          <th >著作权失效日期</th>
			          <th >是否发布到网站</th>
			          <th >摘要</th>
			        </tr>
			        <tr></tr>
			         <c:forEach items="${result.value}" var="dto" varStatus="status">
			        	<tr>
				          	<td  >&nbsp;${status.index+1}</td>
				        	<td  >&nbsp;${dto.customer}</td>
				        	<td  >&nbsp;${dto.name}</td>
				        	<td  >&nbsp;${dto.serialNo}</td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.applyTime}" pattern="yyyy-MM-dd"/></td>
				        	<td  >&nbsp;${dto.appler}</td>
				        	<td  >&nbsp;${dto.type.dataValue}</td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.effectivetime}" pattern="yyyy-MM-dd" /></td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.expireTime}" pattern="yyyy-MM-dd" /></td>
				        	<td  >&nbsp;${dto.pub.title}</td> 
				        	<td  >&nbsp;${dto.summery}</td> 
				        </tr>
			        </c:forEach>
			  </table>
			</div>
					</c:if>			
			<!--认证-->
			<c:if test="${flag eq 2 }">
			<div class="basediv tabswitch" style="margin:0px;" id="textname">
					<table border="0" cellpadding="0" cellspacing="0" class="gridtable" align="left">
			        <tr>
			          <th width="30">序号</th>
			          <th >企业</th>
			          <th >认证编号</th>
			          <th >认证类型</th>
			          <th >是否发布</th>
			          <th >认证日期</th>
			          <th >认证名称</th>
			          <th >认证机构</th>
			          <th >认证摘要</th>
			        </tr>
			        <tr></tr>
			         <c:forEach items="${result.value}" var="dto" varStatus="status">
			        	<tr>
			        		<td  >&nbsp;${status.index+1}</td>
				        	<td  >&nbsp;${dto.customer}</td>
				        	<td  >&nbsp;${dto.serialNo}</td>
				        	<td  >&nbsp;${dto.type.dataValue}</td>
				        	<td  >&nbsp;${dto.pub.title}</td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.certTime}" pattern="yyyy-MM-dd"/></td>
				        	<td  >&nbsp;${dto.name}</td>
				        	<td  >&nbsp;${dto.agency}</td>
				        	<td  >&nbsp;${dto.summery}</td>
				        </tr>
			        </c:forEach> 
			  </table>
			</div>		
			</c:if>
			<!--商标-->
			<c:if test="${flag eq 3 }">
			<div class="basediv tabswitch" style="margin:0px; " id="textname">
					<table border="0" cellpadding="0" cellspacing="0" class="gridtable" align="left">
			        <tr>
			          <th width="30">序号</th>
			          <th >企业</th>
			          <th >商标名称</th>
			          <th >商标编号</th>
			          <th >注册人</th>
			          <th >注册地址</th>
			          <th >注册有效日期</th>
			          <th >注册有效结束日期</th>
			          <th >授权日期</th>
			          <th >详细说明</th>
			        </tr>
			        <tr></tr>
			        <c:forEach items="${result.value}" var="dto" varStatus="status">
			        	<tr>
				          	<td  >&nbsp;${status.index+1}</td>
				        	<td  >&nbsp;${dto.customer}</td>
				        	<td  >&nbsp;${dto.name}</td>
				        	<td  >&nbsp;${dto.brandNo}</td>
				        	<td  >&nbsp;${dto.register}</td>
				        	<td  >&nbsp;${dto.registerAddress}</td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.startDate}" pattern="yyyy-MM-dd"/></td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.endDate}" pattern="yyyy-MM-dd" /></td>
				        	<td  >&nbsp;<fmt:formatDate value="${dto.grantDate}" pattern="yyyy-MM-dd" /></td>
				        	<td  >&nbsp;${dto.memo}</td>
				        </tr>
			        </c:forEach> 
			  </table>
			</div>		
			</c:if>
		</div>
</div>
</div>
</body>
</html>

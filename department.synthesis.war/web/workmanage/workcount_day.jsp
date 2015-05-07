<%@page import="org.apache.taglibs.standard.tag.common.core.ForEachSupport"%>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.synthesis.entity.WorkReport"%>
<%@ page language="java" import="java.util.*,java.text.*,com.wiiy.commons.action.BaseAction"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ page import="com.wiiy.synthesis.preferences.enums.*"%>
<%@ page import="com.wiiy.synthesis.dto.*"%>
<%@ page import="com.wiiy.commons.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link href="core/common/style/base.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/righth.js"></script>
<script type="text/javascript" src="core/common/js/layertable.js"></script>
<link href="core/common/style/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<link href="core/common/style/smartMenu.css" rel="stylesheet" type="text/css" />
<script src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#container").css("height",getTabContentHeight());
});

function vopen(url,name,w,h) {
	window.open(url,name,'height='+h+',width='+w+',top=100,left=150,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
}
function exportDate(){
	 fbStart('导出','<%=basePath %>workReport!selectPersonReport.action',380,240);
}
</script>

</head>

<body>
<!--container-->
<div id="container" style="overflow:auto;">
<div class="emailtop">
	<div class="leftemail">
		<ul>
			<li style="background-color:#D2E6FF" onmouseover="this.className='libg'" onmouseout="this.className=''"  onclick="location.href='<%=basePath %>workDayReport!dayCountList.action'"><span><img src="core/common/images/daylycount.gif" /></span>日报</li>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''"  onclick="location.href='<%=basePath %>workReport!workCountList.action'"><span><img src="core/common/images/weeklycount.png" /></span>周报</li>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" 	onclick="location.href='<%=basePath %>workReport!workMonthCountList.action'"><span><img src="core/common/images/monthcount.png" /></span>月报</li>
			<!-- <li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="exportDate()"><span><img src="core/common/images/database_(start)_16x16.gif" width="16" height="16" /></span>导出</li> -->
		</ul>
	</div>
</div>
  <table border="0" width="665" cellspacing="0" cellpadding="0" class="oacount">
   <tr>
    	<td colspan="13" align="center" class="workcountth"><em style="position:relative; top:3px; padding-right:5px;">
    	<img src="core/common/images/dataviewleft.png" width="15" height="16" onclick="location.href='<%=basePath %>workDayReport!dayCountList.action?yearStr=<fmt:formatDate value="${prevYear}" pattern="yyyy-MM-dd HH:mm:ss"/>'"/></em>
    	${year}年<em style="position:relative; top:3px; padding-left:5px;">
    	<img src="core/common/images/dataviewright.png" onclick="location.href='<%=basePath %>workDayReport!dayCountList.action?yearStr=<fmt:formatDate value="${nextYear}" pattern="yyyy-MM-dd HH:mm:ss"/>'"/></em>&nbsp;&nbsp;&nbsp;<em style="position:relative; top:3px; padding-right:5px;">
    	<img src="core/common/images/dataviewleft.png" width="15" height="16" onclick="location.href='<%=basePath %>workDayReport!dayCountList.action?monthStr=<fmt:formatDate value="${prevMonth}" pattern="yyyy-MM-dd HH:mm:ss"/>'"/></em>
    	${mon}月<em style="position:relative; top:3px; padding-left:5px;">
    	<img src="core/common/images/dataviewright.png" onclick="location.href='<%=basePath %>workDayReport!dayCountList.action?monthStr=<fmt:formatDate value="${nextMonth}" pattern="yyyy-MM-dd HH:mm:ss"/>'"/></em></td>
    	</tr>
    <tr>
      <td width="65" class="workcounttd">&nbsp;</td>
      <td class=""><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<c:forEach items="${monthDtoList}" var="monthDto">
				  <c:forEach items="${monthDto.dayDtoList}" var="dayDto">
		      			<td class="workcountth">${dayDto.daySign}号</td>
		      	  </c:forEach>
	  		</c:forEach>
          </tr>
      </table></td>
    </tr>
    
    
     <c:forEach items="${workCountDtoList}" var="workCountDto">
    <tr>
      <td class="workcounttd"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="64">${workCountDto.username}</td>
        </tr>
      </table></td>
      <c:forEach items="${workCountDto.monthDtoList}" var="monthDto">
      <td class=""><table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
          <tr>
         	 <c:forEach items="${monthDto.dayDtoList}" var="dayDto">
         	 <fmt:formatDate value="${dayDto.date}" pattern="yyyy-MM-dd" var="d2"/>
         	 	<c:if test="${dayDto.daySign==2}">
         	 		<td class="workcounttd"><img src="core/common/images/rightgreen.png" onclick="javascript:vopen('<%=basePath%>workDayReport!viewByDay.action?dateStr=${d2}&id=${workCountDto.id}','weeks',800,300);" width="7" height="7" /></td>
         	 	</c:if>
         	 	<c:if test="${dayDto.daySign==1}">
         	 		<td class="workcounttd"><span onclick="javascript:vopen('<%=basePath%>workDayReport!viewByDay.action?dateStr=${d2}&id=${workCountDto.id}','weeks',800,300);">${dayDto.lateSignInNum}+</span></td>
         	 	</c:if>
         	 	<c:if test="${dayDto.daySign==0}">
         	 		<td class="workcounttd">&nbsp;</td>
         	 	</c:if>
         	 	<c:if test="${dayDto.daySign==null}">
         	 		<td class="workcounttd">&nbsp;</td>
         	 	</c:if>
            </c:forEach>
          </tr>
      </table></td>
      </c:forEach>
    </tr>
   </c:forEach>
  </table>
</div>
 <!--//container-->
</body>

</html>

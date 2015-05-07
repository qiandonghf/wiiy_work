<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String path = request.getContextPath();
	String basePath = BaseAction.rootLocation + path + "/";
%>

<script type="text/javascript">
//<![CDATA[ 
$(function () {
  	//水平方向节点,每个节点表示签订了一个合同
    $('#container').highcharts(options);
});
//]]>  
</script>
<div id="container"></div>

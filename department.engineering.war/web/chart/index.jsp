<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = BaseAction.rootLocation + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>园区动力-工程管理</title>
<link rel="stylesheet" type="text/css" href="department.engineering/web/style/chart.css"/>
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="department.engineering/web/js/highstock.js"></script>
<script type="text/javascript" src="department.engineering/web/js/exporting.js"></script>
<script type="text/javascript" src="department.engineering/web/js/chart.js"></script>
<script type="text/javascript">

/*项目列表*/
function loadProjectList(){
	$.ajax({
	   type		: "POST",
	   url		: "<%=BaseAction.rootLocation%>/department.engineering/project!loadProjectData.action",
	   success	: function(data){
		   dealProjects(data.root);
	   }
	});
}

/*合同列表*/
function loadContractList(id){
	$.ajax({
	   type		: "POST",
	   url		: "<%=BaseAction.rootLocation%>/department.engineering/contract!loadContractData.action?id="+id,
	   success	: function(data){
		   dealContracts(data.result.value);
	   }
	});
}

/*项目计划列表*/
function loadPlanList(id){
	$.ajax({
	   type		: "POST",
	   url		: "<%=BaseAction.rootLocation%>/department.engineering/plan!findPlansByProjectId.action?id="+id,
	   success	: function(data){
		   showPlans(data.result.value);
	   }
	});
}

/*项目实际列表*/
function loadFactList(id){
	$.ajax({
	   type		: "POST",
	   url		: "<%=BaseAction.rootLocation%>/department.engineering/fact!findFactsByProjectId.action?id="+id,
	   success	: function(data){
		   showFacts(data.result.value);
	   }
	});
}

/*计划付费列表*/
function loadBillPlanList(id){
	$.ajax({
	   type		: "POST",
	   url		: "<%=BaseAction.rootLocation%>/department.engineering/billPlanRent!findPlanByContractId.action?id="+id,
	   success	: function(data){
		   showBillPlan(data.result.value,id);
	   }
	});
}
/*实际付费列表*/
function loadBillList(id){
	$.ajax({
	   type		: "POST",
	   url		: "<%=BaseAction.rootLocation%>/parkmanager.pf/bill!findBillsByContractId.action?department=ENGINEERING&contractId="+id,
	   success	: function(data){
		   showBill(data.result.value,id);
	   }
	});
}

function viewById(id){
	fbStart('查看','<%=BaseAction.rootLocation%>/department.engineering/contract!viewById.action?id='+id,650,451);
}	

</script>
</head>
<body>
	<div class="easyui-panel" style="width:100%;padding:10px 0px;">
	    <div class="easyui-layout">
	        <div class="src-left" style="width:120px;min-width:120px;">
	        	<div class="select">
	        	</div>
	        	<div class="contract">
	        		<span class="span">勾选合同</span>
	        		<div class="contracts"></div>
	        	</div>
	        </div>
	        
	        <div class="src-center"style=""></div>
	    </div>
	</div>
</body>

</html>

<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.util.DateUtil"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@page import="com.wiiy.core.activator.CoreActivator"%>
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
<title>园区动力-物业管理</title>
<link href="core/common/style/index.css" rel="stylesheet"
	type="text/css" />
<link href="core/web/newdesktop/style/newindex.css" rel="stylesheet"
	type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet"
	type="text/css" />
<link href="core/common/style/tab.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet"
	type="text/css" />

<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/menu.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tab.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>

<script type="text/javascript">
	$(function(){
		initProperty();
		initPropertygq();//挂起
		initComplaint();
		initParkingFee();
		initDeviceYearly();
		initCount("dueContract","/department.business/contract!estateDueContract.action");//到期合同数量
		initCount("dueRoom","/common/room!dueRoom.action");//到期房源数量
		initCount("newCustomerCount","/department.business/customer!newCustomerCount.action");//招商客户数量
		initRemindCount();
		initBillRemindCount();//结算提醒数量(remindCount)
		initBillReportList();//账单报表
		initMeterLabelList();//水电抄表
		initStockIn();
	});
	
	function initMeterLabelList(){
		$("#meterLabelList").empty();
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/department.estate/meterLabel!initList.action",
			   success	: function(data){
				   if(data.result!=null){
					   var span = "";
					   var meterLabel = data.result.value;
					   if(meterLabel!=null && meterLabel.length>0){
						   for(var i=0;i<meterLabel.length;i++){
							   var a = "<a href=\"javascript:void(0);\" class=\"huise\" onclick=\"openMeterLabel('"+meterLabel[i].id+"')\">"+meterLabel[i].name+"</a>";
							   span += a;
						   }
					   }
					   $("#meterLabelList").append(span);
				   }
			   }
			});
	}
	function initBillReportList(){
		$("#billReportList").empty();
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/department.estate/meterLabel!initList.action",
			   success	: function(data){
				   if(data.result!=null){
					   var span = "";
					   var meterLabel = data.result.value;
					   if(meterLabel!=null && meterLabel.length>0){
						   for(var i=0;i<meterLabel.length;i++){
							  if(meterLabel[i].checkOut.title == '已出账'){
								  var a = "<a href=\"javascript:void(0);\" class=\"huise\" style=\"color:red;\" onclick=\"openMeterLabel('"+meterLabel[i].id+"')\">"+meterLabel[i].name+"</a>";
							  }else{
								  var a = "<a href=\"javascript:void(0);\" class=\"huise\" onclick=\"openMeterLabel('"+meterLabel[i].id+"')\">"+meterLabel[i].name+"</a>";
							  }
							   span += a;
						   }
					   }
					   $("#billReportList").append(span);
				   }
			   }
			});
	}
	
	function openMeterLabel(id){
		parent.addTab('水电表抄表','/department.estate/web/images/icon/property_12_min.png','<%=BaseAction.rootLocation%>/department.estate/meterLabel!list.action?id='+id);
	}

	function initRemindCount(){
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/department.estate/billPlanRent!initRemindCount.action",
			   success	: function(data){
				   if(data.result!=null){
					   var map = data.result.value;
					   var totalCount = 0;
					   for(var key in map){
						   $("#"+key+"2").html(map[key]);
						   totalCount += map[key];
					   }
					   $("#remindCount").html(totalCount);
				   }
			   }
			});
	}
	
	function initBillRemindCount(){
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/common/billRemind!initRemindCount.action?department=ESTATE",
			   success	: function(data){
				   if(data.result!=null){
					   var map = data.result.value;
					   var totalCount = 0;
					   for(var key in map){
						   $("#"+key).html(map[key]);
						   totalCount += map[key];
					   }
					   $("#billRemindCounts").html(totalCount);
				   }
			   }
			});
	}
	
	function initCount(type,url){
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>"+url,
			   success	: function(data){
				   if(data.result!=null){
					   $("#"+type).html(data.result.value);
				   }
			   }
			});
		
	}
	
	function addPropertyTab(title,icon,url){
		parent.addTab(title,icon,url);
	}
	/*待处理的报修*/
	function initProperty(){
		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.estate/propertyFix!amounts.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i=0;i<data.result.value.length;i++){
				html = "<a href=\"javascript:void(0);\" onclick=\"parent.addTab('待处理','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation%>\/department.estate\/web\/property\/property_repair_listByType.jsp?status='+'"+nums[i].typeName+"');\" class=\" huise\">待处理(<font class=\"huise\" style=\"color: red;line-height:25px;\">"+nums.length+"</font>)</a>"
			   }
			   if(data.result.value.length==0){
				html = "<a href=\"javascript:void(0);\" onclick=\"parent.addTab('待处理','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation%>\/department.estate\/web\/property\/property_repair_listByType.jsp?status='+'');\" class=\" huise\">待处理(<font style=\"color:blue;line-height:25px;\">0</font>)</a>"
			   }
			   $("#propertyType").append(html);
		   }
		});
	}
	/*挂起的报修*/
	function initPropertygq(){
		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.estate/propertyFix!amountsgq.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i=0;i<data.result.value.length;i++){
				html = "<a href=\"javascript:void(0);\" onclick=\"parent.addTab('挂起的报修','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation%>\/department.estate\/web\/property\/property_repair_listByType.jsp?status='+'"+nums[i].typeName+"');\" class=\" huise\">挂起(<font class=\"huise\" style=\"color: red;line-height:25px;\">"+nums.length+"</font>)</a>"
			   }
			   if(data.result.value.length==0){
				html = "<a href=\"javascript:void(0);\" onclick=\"parent.addTab('挂起的报修','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation%>\/department.estate\/web\/property\/property_repair_listByType.jsp?status='+'');\" class=\" huise\">挂起(<font style=\"color:blue;line-height:25px;\">0</font>)</a>"
			   }
			   $("#propertygq").append(html);
		   }
		});
	}
	/*待处理的投诉*/
	function initComplaint(){
		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.estate/complaint!amounts.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i=0;i<data.result.value.length;i++){
				html = "<a href=\"javascript:void(0);\" onclick=\"parent.addTab('待处理','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation%>\/department.estate\/web\/property\/complaint_listByType.jsp?status='+'"+nums[i].typeName+"');\" class=\" huise\">待处理(<font class=\"huise\" style=\"color: red;line-height:25px;\">"+nums.length+"</font>)</a>"
			   }
			   if(data.result.value.length==0){
				html = "<a href=\"javascript:void(0);\" onclick=\"parent.addTab('待处理','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation%>\/department.estate\/web\/property\/complaint_listByType.jsp?status='+'');\" class=\" huise\">待处理(<font style=\"color:blue;line-height:25px;\">0</font>)</a>"
			   }
			   $("#complaintType").append(html);
		   }
		});
	}
	//id="parkingFeeId"
	/*缴费提醒*/
	function initParkingFee(){
		var html="缴费提醒";
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/department.estate/parkingFee!listAll.action",
			   success	: function(data){
				if(data.result.value!=0){
					var n=data.result.value;
					html+="<font style=\"color:red;line-height:25px;\">("+n+")</font>"
					$("#parkingFeeId").append(html);
				}else{
					html+="<font style=\"color:red;line-height:25px;\">(0)</font>"
					$("#parkingFeeId").append(html);
				}				  
			   }
			});
	}
	/*设备提醒*/
	function initDeviceYearly(){
		var html="巡检/年检提醒";
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/department.estate/deviceManagement!amounts.action?report=true",
			   success	: function(data){
				if(data.results!=null){
					if(data.results.value!=0){
						var n=data.results.value;
						html+="<font style=\"color:red;line-height:25px;\">("+n+")</font>"
						$("#deviceYearlyId").append(html);
					}else{
						html+="<font style=\"color:red;line-height:25px;\">(0)</font>"
						$("#deviceYearlyId").append(html);
					}	
				}
				else{
					html+="<font style=\"color:red;line-height:25px;\">(0)</font>"
					$("#deviceYearlyId").append(html);
				}
			   }
			});
	}
	function downLoad(path,name){
		location="/core/resources/"+path+"?name="+name;
	}
	
	function reloadList(type){
		if('WYHT'==type){
			parent.addTab('物业合同','/department.business/web/images/icon/contract_03_min.png','<%=BaseAction.rootLocation%>/department.estate/web/contract/contract_list.jsp');
		}
		if('meter'==type){
			parent.addTab('水电表抄表','/department.estate/web/images/icon/property_12_min.png','<%=BaseAction.rootLocation%>/department.estate/meterLabel!list.action');
			
		}
	}
	function estateCostDetail(){
		var url = '<%=BaseAction.rootLocation%>/department.estate/billPlanRent!listByBillCostDetail.action';
		window.open(url,'物业收费明细表','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	function estateContract(){
		var url = '<%=BaseAction.rootLocation%>/department.estate/contract!listByContractDetail.action';
		window.open(url,'物业合同明细','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	function initStockIn(){
		var html="";
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/department.estate/supply!stockInRemind.action",
			   success	: function(data){
				   if(data.result.value!=0){
						var n=data.result.value;
						 html += "<a href=\"javascript:;\" onclick=\"parent.addTab('库存警示','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.estate\/supply!list.action?stockIn=stockIn');\" class=\" huise\">库存警示(<font style=\"color:blue;line-height:25px;\">"+n+"</font>)</a>"
						 $("#stockInRemind").append(html);
					}else{
						 html += "<a href=\"javascript:;\" onclick=\"parent.addTab('库存警示','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.estate\/supply!list.action?stockIn=stockIn');\" class=\" huise\">库存警示(<font style=\"color:blue;line-height:25px;\">0</font>)</a>"
						 $("#stockInRemind").append(html);
					}
			   }
			});
	}
</script>
</head>
<body>


	<div id="contant2" style="overflow: auto;">

		<div id="news_contenter" class="main"
			style="padding-left: 30px; padding-top: 10px;">
			<div class="toptitle">
				<p>
					<strong>物业管理</strong><span class="subtitle">ParkBuilding</span><br />
					<span class="toptitle_bg">面向物业部门,实现对园区内建筑物、设施、设备、场所等进行管理。包括楼宇管理、物业管理、水电管理、公共设施管理等。</span>
				</p>
			</div>
			<div class="maincontent">
				<div class="mainleft">
				<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0501">物业合同</h3>
						<p class="toptext">物业合同登记与合同提醒</p>
						<div id="estateContract"></div>
						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_complate")){ %>
								<p class="text">
									<strong> <a href="javascript:void(0);" class="blue"
										style="text-decoration: none">合同模板</a>
									</strong> 
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_complate_list")){ %>
									<span class="w300th"> <a href="javascript:void(0);"
										onclick="downLoad('attachments/estate/201504281002001.doc','物业合同');"
										class="huise">物业大厦合同</a>
									<%} %>
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_complate_list")){ %>
									 <a href="javascript:void(0);"
										onclick="downLoad('attachments/estate/201504281003002.doc','物业合同');"
										class="huise">物业厂房合同</a>
									</span>
									<%} %>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_registration")){ %>
								<p class="text">
									<strong> 
									<a href="javascript:void(0);" class="blue"
										style="text-decoration: none">合同登记</a></strong> 
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_registration_list")){ %>
										<span class="w300th">
										<a href="javascript:void(0);"
										onclick="parent.addTab('物业合同','/department.business/web/images/icon/contract_03_min.png','<%=BaseAction.rootLocation%>/department.estate/web/contract/contract_list.jsp');"
										class="huise">所有合同</a> 
										
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_registration_add")){ %>
										<a href="javascript:void(0);"
										onclick="fbStart('新建合同','<%=BaseAction.rootLocation%>/department.estate/web/contract/contract_add.jsp?type=WYHT',750,493);"
										class="huise">+物业合同</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_registration_new_customer")){ %>
										<a href="javascript:void(0);" title="根据招商合同中查询出来的所有客户数"
										onclick="parent.addTab('客户档案','/department.business/web/images/icon/client_00_min.png','<%=BaseAction.rootLocation%>/department.estate/web/customer/customer_list.jsp');"
										class="huise">招商客户 (<font style="line-height:25px;color: blue" id="newCustomerCount" 
											>0</font>)
										</a>
										<%} %>
									</span>
									<%} %>	
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_remind")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">合同提醒</a></strong> 
										<span class="w300th">
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_remind")){ %>
										<a href="javascript:void(0);" title="物业部所有还没有出账的计划资金总数" onclick="parent.addTab('结算提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/department.estate/billPlanRent!checkoutListBillPlanRent.action');" class="huise">结算提醒(<font
											id="remindCount" style="line-height:25px;color: blue">0</font>)
										</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_remind_estateCost")){ %>
										<a href="javascript:void(0);" title="计划资金中所有的物业费" onclick="parent.addTab('结算提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/department.estate/billPlanRent!checkoutListBillPlanRent.action?feeType=ESTATE_WY');" class="huise">物业费(<font
											id="ESTATE_WY2" style="line-height:25px;color: blue">0</font>)
										</a> <span style="clear: both;"></span> <span style="clear: both;"></span>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_remind_listByEndDate")){ %>
										<a href="javascript:;"
										onclick="parent.addTab('到期合同','/department.business/web/images/icon/contract_07_min.png','<%=BaseAction.rootLocation%>/department.estate/web/contract/contract_listByEndDate.jsp');"
										class="huise">合同到期(<font id="dueContract" style="line-height:25px;color: blue">0</font>)
										</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_remind_roomExpires")){ %>
										<span class="spanhuise">（默认为30天）</span> <a href="javascript:;" title="到期时间已经超过当前日期的房间数量"
										onclick="parent.addTab('到期单元','/common/web/images/icon/house_04_min.png','<%=BaseAction.rootLocation%>/common/web/building/building_roomExpires_list.jsp?type=expire&department=BUSINESS');"
										class="huise">到期房源(<font id="dueRoom" style="line-height:25px;color: blue">0</font>)
										</a>
										<%} %>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_count")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">合同统计</a></strong> <span class="w300th">
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_count_contract")){ %>
										<a href="javascript:void(0);" onclick="estateContract()" class="huise">物业合同明细</a>
									<%} %>
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_contract_count_costDetail")){ %>
										<a href="javascript:void(0);" onclick="estateCostDetail()" class="huise">物业收费明细表</a>
									<%} %>
									</span>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0502">基础物业</h3>
						<p class="toptext">物业报修、客户投诉、车辆管理、库存管理、设备管理</p>
						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_repair")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">物业报修</a>
									</strong> 
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_repair_listAll")){ %>
									<span class="w300th">
										<a href="javascript:void(0);"
										onclick="parent.addTab('所有报修','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/property_repair_list.jsp');"
										class="huise">所有报修</a> 
										<span id="propertyType" title="所有保修中处于挂起状态的数量"></span>
										<span id="propertygq"></span>
										 <a
										href="javascript:void(0);"
										onclick="fbStart('新建报修','<%=BaseAction.rootLocation%>/department.estate/web/property/property_repair_add.jsp?form=index',800,197);"
										class="huise">+报修</a>
									</span>
									<%} %>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_complaint")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">客户投诉</a></strong> 
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_complaint_list")){ %>
										<span class="w300th">
										<a href="javascript:void(0);"
										onclick="parent.addTab('所有投诉','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/complaint_list.jsp');"
										class="huise">所有投诉</a> 
										<span id="complaintType" title="所有投诉中未处理和待进一步跟进的数量"></span> <a
										href="javascript:void(0);"
										onclick="fbStart('新建投诉','<%=BaseAction.rootLocation%>/department.estate/web/property/complaint_add.jsp?form=index',600,222);"
										class="huise">+投诉</a>
										</span>
										<%} %>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_car")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">车辆管理</a></strong> 
										<span class="w300th">
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_car_manage")){ %>
										<a href="javascript:void(0);"
										onclick="parent.addTab('车辆管理','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/vehicle_list.jsp');"
										class="huise">车辆管理</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_car_fixmanage")){ %>
										<a href="javascript:void(0);"
										onclick="parent.addTab('车位管理','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/grage_list.jsp');"
										class="huise">车位管理</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_car_registration")){ %>
										<a href="javascript:void(0);"
										onclick="parent.addTab('缴费登记','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/parkingFee/parkingFee_list.jsp');"
										class="huise">缴费登记</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_car_remind")){ %>
										<a id="parkingFeeId" title="车库缴费登记即将到期的数量，提前一周提醒"
										href="javascript:void(0);"
										onclick="parent.addTab('缴费提醒','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/parkingFee/parkingFee_list.jsp?currentDate=<%=(new java.util.Date()).toLocaleString()%>'+'&form=index');"
										class="huise"></a>
										<%} %>
										</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_stockin")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">库存管理</a></strong> 
										<span  class="w300th">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_stockin_manage")){ %>
										<a href="javascript:void(0);"
										onclick="parent.addTab('所有商品','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/supply!list.action');"
										class="huise">商品管理</a>
										<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_stockin_subscribe")){ %>
										<a href="javascript:void(0);"
										onclick="parent.addTab('所有商品','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/chiefadmin/Office_subscribe.jsp');"
										class="huise">商品申购</a>
										<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_stockin_all")){ %>
										<a href="javascript:void(0);"
										onclick="parent.addTab('所有入库','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/chiefadmin/Office_supplies_storage_list.jsp');"
										class="huise">所有入库</a> 
										<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_stockin_add")){ %>
										<a href="javascript:void(0);"
										onclick="fbStart('新建入库','<%=BaseAction.rootLocation%>/department.estate/web/chiefadmin/Office_supplies_storage_add.jsp?form=index',780,349);"
										class="huise">+入库</a> 
										<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_stockin_outAll")){ %>
										<a href="javascript:void(0);"
										onclick="parent.addTab('所有出库','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/chiefadmin/Office_supplies_sq.jsp');"
										class="huise">所有出库</a> 
										<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_stockin_out")){ %>
										<a href="javascript:void(0);"
										onclick="fbStart('新建出库','<%=BaseAction.rootLocation%>/department.estate/web/chiefadmin/Office_supplies_sq_add.jsp?form=index',500,320);"
										class="huise">+出库</a>
										<%} %>
									<span style="clear: both;"></span> <span style="clear: both;"></span>
									<span id="stockInRemind" title="当实际库量存小于库存警示量时的商品">
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_device")){ %>
								<p class="text">
									<strong> <a href="javascript:void(0);" class="blue"
										style="text-decoration: none">设备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></strong>
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_device_deviceManage")){ %>
									<span class="w300th"> 
										<a href="javascript:void(0);"
										onclick="parent.addTab('所有设备','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/device_list.jsp');"
										class="huise">设备管理</a> 
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_device_add")){ %>
										<a href="javascript:void(0);"
										onclick="fbStart('新建设备','<%=BaseAction.rootLocation%>/department.estate/web/property/device_add.jsp?form=index',770,498);"
										class="huise">+设备</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_base_device_deviceReport")){ %>
										<a id="deviceYearlyId" title="设备巡检，年检提醒；分别是根据到期巡检，年检日期提前3天和30天进行提醒"
										href="javascript:void(0);" 
										onclick="parent.addTab('巡检/年检提醒','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/deviceReport_list.jsp');"
										class="huise"></a>
										<%} %>
									</span>
									<%} %>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
				</div>
				<div class="mainright">
				<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_hydropower")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0503">水电</h3>
						<p class="toptext">根据园区大楼进行水电表配置、费用设置、抄表定义、生成费用报表</p>
						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_hydropower_waterMeterSet")){ %>
								<p class="text">
									<strong> <a href="javascript:void(0);" class="blue"
										style="text-decoration: none">水电表</a>
									</strong> <span> <a href="javascript:void(0);" onclick="parent.addTab('水电表设置','/department.estate/web/images/icon/property_15_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/meter_list.jsp');" class="underline huise">+水电表</a> 
										<!-- <a href="javascript:void(0);" class="underline huise on">车未定义</a> -->
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_hydropower_costSet")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue" style="text-decoration: none">费用设置</a></strong>
									<span>
										<a href="javascript:void(0);" onclick="parent.addTab('费用定义','/department.estate/web/images/icon/property_16_min.png','<%=BaseAction.rootLocation%>/department.estate/web/property/config_list.jsp');" class="underline huise">费用定义</a>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_hydropower_meterLabel")){ %>
								<p class="text">
									<strong>
										<a href="javascript:void(0);" class="blue" style="text-decoration: none">水电抄表</a>
									</strong> 
									<span class="w300th"> 
										<a href="javascript:void(0);" onclick="fbStart('新建分户表抄表','<%=BaseAction.rootLocation%>/department.estate/web/property/meterLabel_add.jsp',400,360);" class="huise on">+水电抄表</a>
									</span> 
									<span id="meterLabelList" class="w300th">
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_hydropower_billReport")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">账单报表</a></strong> 
									<span id="billReportList" class="w300th"></span>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_settle")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0504">结算</h3>
						<p class="toptext">各类物业费用的自定义出账、催缴提醒及结算清单查看</p>
						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_settle_remind")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue" style="text-decoration: none">结算提醒</a></strong> 
									<span> 
										<a href="javascript:void(0);" onclick="parent.addTab('催缴提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/common/web/bill/billRemind_list.jsp?department=ESTATE');" class="underline huise on">催缴提醒(<font id="billRemindCounts" style="line-height:25px;color: red">0</font>)</a>
									</span>
									<span class="w300th">
										<a href="javascript:void(0);" onclick="parent.addTab('催缴提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/common/web/bill/billRemind_list.jsp?feeType=ESTATE_WY');" class="huise">物业费(<font style="line-height:25px;color: blue" id="ESTATE_WY">0</font>)</a> 
										<a href="javascript:void(0);" onclick="parent.addTab('催缴提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/common/web/bill/billRemind_list.jsp?feeType=ESTATE_GGNH');" class="huise on">公共能耗费(<font style="line-height:25px;color: blue" id="ESTATE_GGNH">0</font>)</a>
										<a href="javascript:void(0);" onclick="parent.addTab('催缴提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/common/web/bill/billRemind_list.jsp?feeType=ESTATE_LJQY');" class="huise">垃圾消运费(<font style="line-height:25px;color: blue" id="ESTATE_LJQY">0</font>)</a>
										<a href="javascript:void(0);" onclick="parent.addTab('催缴提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/common/web/bill/billRemind_list.jsp?feeType=ESTATE_SF');" class="huise on">水费(<font style="line-height:25px;color: blue" id="ESTATE_SF">0</font>)</a>
										<a href="javascript:void(0);" onclick="parent.addTab('催缴提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/common/web/bill/billRemind_list.jsp?feeType=ESTATE_DF');" class="huise on">电费(<font style="line-height:25px;color: blue" id="ESTATE_DF">0</font>)</a>
										<a href="javascript:void(0);" onclick="parent.addTab('催缴提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation %>/common/web/bill/billRemind_list.jsp?feeType=ESTATE_YJF');" class="huise">预缴费(<font style="line-height:25px;color: blue" id="ESTATE_YJF">0</font>)</a></span>
									</p>
									<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_settle_chargeOff")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue" style="text-decoration: none">自定义出账</a></strong> 
									<span>
									<a href="javascript:void(0);" onclick="parent.addTab('结算提醒','/department.business/web/images/icon/account_01_min.png','<%=BaseAction.rootLocation%>/department.estate/billPlanRent!checkoutListBillPlanRent.action');" class="underline huise">物业费<font style="line-height:25px;color: red">(0)</font></a>
									<a href="javascript:void(0);" class="underline huise on">公共能耗费</a>
									<a href="javascript:void(0);" class="underline huise">垃圾消运费</a>
									<a href="javascript:void(0);" class="underline huise on">水电费</a>
									<a href="javascript:void(0);" class="underline huise">预缴费</a></span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("estate_settle_bill")){ %>
								<p class="text">
									<strong><a href="javascript:void(0);" class="blue"
										style="text-decoration: none">结算清单</a></strong> <span> 
										<a href="javascript:void(0);" class="underline huise">查看结算单</a>
									</span>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

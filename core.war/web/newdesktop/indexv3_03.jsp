<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@page import="com.wiiy.core.activator.CoreActivator"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum"%>
<%
	String path = request.getContextPath();
	String basePath = BaseAction.rootLocation + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>园区动力-客户管理</title>
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
		//initTypeEnum();
		initCount();
		initOrg();
		initInOut();
		initReportType();
		initCustomerEnum();
		initIncubate();
		improveArchiveType();
		initCustomerArchives();
	});
	
	/*入驻迁出*/
	function initInOut(){
		$("#IN").empty();
	  	$("#OUT").empty();
	  	$("#RELET").empty();
		$.ajax({
			   type		: "POST",
			   url		: "<%=BaseAction.rootLocation%>/department.business/contract!inOut.action",
			   success	: function(data){
				   if(data.result!=null){
					   var dtoList = data.result.value;
					   if(dtoList!=null && dtoList.length>0){
						   	var customerIn = "";
						   	var customerOut = "";
						   	var relet = "";
						   for(var i = 0; i< dtoList.length; i++){
							   	if(dtoList[i].type == 'IN'){
								   	customerIn += "<a href=\"javascript:;\" onclick=\"customerView('"+dtoList[i].attr+"',"+dtoList[i].customerId+")\" class=\"huise\">"+dtoList[i].attr+"</a>";
							  	}else if(dtoList[i].type == 'OUT'){
							  		customerOut += "<a href=\"javascript:;\" onclick=\"customerView('"+dtoList[i].attr+"',"+dtoList[i].customerId+")\" class=\"huise\">"+dtoList[i].attr+"</a>";
							  	}else if(dtoList[i].type == 'RELET'){
							  		relet += "<a href=\"javascript:;\" onclick=\"customerView('"+dtoList[i].attr+"',"+dtoList[i].customerId+")\" class=\"huise\">"+dtoList[i].attr+"</a>";
							  	}
						   }
						  	$("#IN").append(customerIn);
						  	$("#OUT").append(customerOut);
						  	$("#RELET").append(relet);
					   }
				   }
			   }
			});
	}
	
	function customerView(name,id){
		var url = "<%=BaseAction.rootLocation%>/department.business/customer!view.action?id="+id;
		fbStart(name,url,870,460);
	}
	
	function initTypeEnum(){
		$("#typeEnum").children().eq(0).remove();
	  	$("#typeEnum").hide();
		var children = $("#typeEnum").children();
		var html = '<a href=\"javascript:;\" onclick=\"addTabList(this,\'client\',\'customer\',\'customer\')\" class=\"huise\">所有客户</a>';
		$(children).each(function(i){
			var txt = $(this).text();
			var type = $(this).val();
			html+= '<a href=\"javascript:;\" onclick=\"addTabList(this,\'client\',\'customer\',\''+type+'\')\" class=\"huise\">'+txt+'</a>';
		});
		if(html != ''){
 			$("#typeList").html(html+$("#typeList").html());
 		} 
	}
	
	function addNewTab(obj,file,type,content,width,height,actionName){
		if(!actionName) actionName = 'department.business';
		var title = "新建"+$(obj).text().substr(1);
		var url = "<%=BaseAction.rootLocation%>/"+actionName+"/web/"+file+"/"+type+"_add.jsp";
		if(type == 'contract')
			url = "<%=BaseAction.rootLocation%>/"+actionName+"/web/"+file+"/"+type+"_add_by_id.jsp";
		if(content){
			url += "?type="+content;
		}
		fbStart(title,url,width,height);
	}
	function addCustomServiceTab(title,icon,url){
		parent.addTab(title,icon,url);
	}
	
	function addTabList(obj,file,type,content,msg,actionName){
		if(!actionName) actionName = 'department.business';
		var title = "";
		if(obj)
		 	title = $(obj).text();
		if(msg)
		    title = msg;
		var icon = 'core/common/images/console.png';
		var url = "<%=BaseAction.rootLocation%>/department.business/web/"+file+"/"+type+"_list.jsp";
		if(content){
			url += "?type="+content;
		}
		parent.addTab(title,icon,url);
	}
	
	function view(obj,type,id){
		var title = $(obj).text();
		var width = 760;
		var height = 419;
		fbStart(title,'<%=BaseAction.rootLocation%>/department.business/'+type+'!view.action?id='+id,width,height);
	}
	
	/*项目列表*/
	function loadProjectList(create){
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.business/project!loadProjectData.action",
		   success	: function(data){
			   dealProjects(data.root,create);
		   }
		});
	}
	
	/*处理返回的项目信息*/
	function dealProjects(projects,create){
		var html = "";
		if(create){
			html = $("#projectList").html();
		}
		for(var i = 0; i< projects.length; i++){
			html += "<p class=\"text\">";
			html += "<strong>";
			html += "<a href=\"javascript:;\" onclick=\"view(this,'project',"+projects[i].id+")\" class=\"blue\">"+projects[i].name+"</a>"
			html += "</strong>";
			html += "<span><a href=\"javascript:;\" class=\" huise\">整体进度</a></span>";
			html += "</p>";
		}
		$("#projectList").html(html);
	}
	
	/*重新加载*/
	function reloadList(type){
		switch(type){
		case 'contectInfo':
			addTabList(null,type,type,'客户跟进');
			break;
		case 'contect':
			addTabList(null,type,null,'联系人');
			break;		
		case 'customer':
			addTabList(null,type,null,'客户');
			break;			
		case 'ZLHT':	
		 	addTabList(null,'contract',type,'租赁合同');
			break;
		case 'XSHT':
		 	addTabList(null,'contract',type,'销售合同');
			break;
		}
	}
	/*服务类型*/
	function initCount(){	
		var html = "";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.business/customerService!amounts.action",
		   success	: function(data){
 			   var nums = data.result.value.slice(",");
			   for(var i = 0;i < data.result.value.length;i++){
				  if(nums[i].amount!=0&&nums[i]!=''){
						html += "<a href=\"javascript:;\" onclick=\"parent.addTab('"+nums[i].typeName+"','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/customerService!listType.action?typeId='+'"+nums[i].childId+"');\" class=\" huise\">"+nums[i].typeName+"(<font style=\"color:blue;line-height:25px;\">"+nums[i].amount+"</font>)</a>"
				  }else{
					html += "<a href=\"javascript:;\" onclick=\"parent.addTab('"+nums[i].typeName+"','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/customerService!listType.action?typeId='+'"+nums[i].childId+"');\" class=\" huise\">"+nums[i].typeName+"(<font style=\"color:blue;line-height:25px;\">0</font>)</a>" 
				  }
				  if((i+1)%3==0){
					  html +="<span style=\"clear:both;\"></span>"
				  }
			   }
			   $("#serviceType").html(html);
 		   }
		});
	}
	/*服务机构*/
	function initOrg(){
		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.business/agency!amounts.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i = 0;i < data.result.value.length;i++){
				   if(nums[i].amount!=0&&nums[i]!=''){
					   html += "<a href=\"javascript:;\" onclick=\"parent.addTab('"+nums[i].value+"','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/web\/cooperate\/cooperate_list.jsp?agencyType='+'"+nums[i].name+"');\" class=\" huise\">"+nums[i].value+"(<font style=\"color:blue;line-height:25px;\">"+nums[i].amount+"</font>)</a>"
				   }else{
					   html += "<a href=\"javascript:;\" onclick=\"parent.addTab('"+nums[i].value+"','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/web\/cooperate\/cooperate_list.jsp?agencyType='+'"+nums[i].name+"');\" class=\" huise\">"+nums[i].value+"(<font style=\"color:blue;line-height:25px;\">0</font>)</a>"
				   }
			   }
			   $("#orgType").append(html);
		   }
		});
	}
	
	/*孵化类型*/
	function initIncubate(){
		var html="孵化";
		var html2="毕业 ";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.business/customer!incubateds.action",
		   success	: function(data){
			if(data.result!=null){
				var nums=data.result.value;
				var incubateNum=nums[0].incubateNum;
				var graduateNum=nums[0].graduateNum;
				if(incubateNum!=0&&incubateNum!=''){
					html+="<font style=\"color:blue;line-height:25px;\">("+incubateNum+")</font>"
					$("#incubatedType").append(html);
				}else{
					html+="<font style=\"color:blue;line-height:25px;\">(0)</font>"
					$("#incubatedType").append(html);
				}
				if(graduateNum!=0&&graduateNum!=''){
					html2+="<font style=\"color:blue;line-height:25px;\">("+graduateNum+")</font>"
					$("#graduateType").append(html2);
				}else{
					html2+="<font style=\"color:blue;line-height:25px;\">(0)</font>"
					$("#graduateType").append(html2);
				}
			}else{
				html+="<font style=\"color:blue;line-height:25px;\">(0)</font>"
					$("#incubatedType").append(html);
				html2+="<font style=\"color:blue;line-height:25px;\">(0)</font>"
					$("#graduateType").append(html2);
			}
		   }
		});
	}
	/*客户类型*/
	function initCustomerEnum(){
		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.business/customer!amounts.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i = 0;i < data.result.value.length;i++){
				   if(nums[i].amount!=0&&nums[i]!=''){
					   html += "<a href=\"javascript:;\" onclick=\"parent.addTab('所有客户','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/web\/client\/customer_list.jsp?type='+'"+nums[i].name+"');\" class=\" huise\">"+nums[i].value+"(<font style=\"color:blue;line-height:25px;\">"+nums[i].amount+"</font>)</a>"
				   }else{
					   html += "<a href=\"javascript:;\" onclick=\"parent.addTab('所有客户','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/web\/client\/customer_list.jsp?type='+'"+nums[i].name+"');\" class=\" huise\">"+nums[i].value+"(<font style=\"color:blue;line-height:25px;\">0</font>)</a>"
				   }
			   }
			   $("#customerType").append(html);
		   }
		});
	}
	/*完善档案*/
	function improveArchiveType(){
			var html="完善档案";
			$.ajax({
				   type		: "POST",
				   url		: "<%=BaseAction.rootLocation%>/department.business/customer!improveArchives.action",
				   success	: function(data){
					if(data.result.value!=0){
						var n=data.result.value;
						html+="<font style=\"color:blue;line-height:25px;\">("+n+")</font>"
						$("#improveArchiveId").append(html);
					}else{
						html+="<font style=\"color:blue;line-height:25px;\">("+0+")</font>"
						$("#improveArchiveId").append(html);
					}				  
				   }
				});
	}
	/*数据填报*/
 	function initReportType(){
 		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.business/dataReport!amounts.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i = 0;i < data.result.value.length;i++){
				  if(nums[i].amount!=0&&nums[i]!=''){
						html += "<a href=\"javascript:;\" onclick=\"parent.addTab('"+nums[i].groupName+"','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/dataReport!listGroup.action?groupId='+'"+nums[i].groupId+"');\" class=\" huise\">"+nums[i].groupName+"(<font style=\"color:blue;line-height:25px;\">"+nums[i].amount+"</font>)</a>"
				  }else{
					html += "<a href=\"javascript:;\" onclick=\"parent.addTab('"+nums[i].groupName+"','\/department.business\/web\/images\/icon\/projectadmin_02_min.png','<%=BaseAction.rootLocation %>\/department.business\/dataReport!listGroup.action?groupId='+'"+nums[i].groupId+"');\" class=\" huise\">"+nums[i].groupName+"(<font style=\"color:blue;line-height:25px;\">0</font>)</a>" 
				  }
				  if((i+1)%3==0){
					  html +="<span style=\"clear:both;\"></span>"
				  }
			   }
			   $("#dataReportType").append(html);
		   }
		});
	}
	/* 档案调整*/
 	function initCustomerArchives(){
 		var html="";
		$.ajax({
		   type		: "POST",
		   url		: "<%=BaseAction.rootLocation%>/department.business/customer!listByArchives.action",
		   success	: function(data){
			   var nums = data.result.value.slice(",");
			   for(var i = 0;i < data.result.value.length;i++){
				   html +="<a href=\"javascript:;\" onclick=\"fbStart('新建客户','<%=BaseAction.rootLocation %>\/department.business\/customer!edit.action?id='+'"+nums[i].customerId+"'+'&form=index',700,550);\" class=\"huise\">"+nums[i].name+"</a>"
			   }
			   $("#customerArchivesId").append(html);
		   }
		});
	}
 	/*常用表单*/
	function customerCategory(){
		var url = '<%=BaseAction.rootLocation%>/department.business/customer!listByCustomerCategory.action';
		window.open(url,'客户分类明细','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	function customerContact(){
		var url = '<%=BaseAction.rootLocation%>/department.business/contect!listByCustomerContact.action';
		window.open(url,'客户联系方式表','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	function customerTalents(){
		var url = '<%=BaseAction.rootLocation%>/department.business/staffer!listByCustomerTalents.action';
		window.open(url,'高层次人才表','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	function customerIncubate(){
		var url = '<%=BaseAction.rootLocation%>/department.business/incubationInfo!listByCustomerIncubate.action';
		window.open(url,'孵化企业表','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	function customerProjectInfos(){
		var url = '<%=BaseAction.rootLocation%>/department.business/product!listByProductInfoApply.action';
		window.open(url,'融资项目信息表','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	function customerProperty(){
		var url = '<%=BaseAction.rootLocation%>/department.business/patent!listByKnowledge.action?flag=0';
		window.open(url,'知识产权表','height=500,width=820,toolbar=no,menubar=no,scrollbars=yes,resizable=yes, location=no,status=no');
	}
	
</script>
</head>
<body>


	<div id="contant2" style="overflow: auto;">

		<div id="news_contenter" class="main"
			style="padding-left: 30px; padding-top: 10px;">
			<div class="toptitle">
				<p>
					<strong>客户管理</strong><span class="subtitle">ParkCustomer</span><br />
					<span class="toptitle_bg">面向园区综合服务部、企业服务部，包括了客户信息管理和客户服务管理。客户信息管理记录了园内企业客户和商业客户的详细信息，如客户基本信息、项目与产品信息、经营情况、主要人员、知识产权、项目申报等，多维度科技检索功能和图型统计结果能够让园区更加清晰了解园区客户分布与深入实施客户关怀。客户服务管理实现在园客户问题统一处理，将问题归类，以客户联系单的方式在处理人员中传递，记录相应服务轨迹，保证每一个问题能够落实到人，及时跟进并予以解决。</span>
				</p>
			</div>
			<div class="maincontent">
				<div class="mainleft">
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0301">客户信息</h3>
						<p class="toptext">客户档案、客户跟进、客户关怀、常用表单、客户统计</p>
						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives")){ %>
								<p class="text">
<!-- 									<strong><a href="javascript:;" onclick="addTabList(this,'client','customer')" class="blue">客户档案</a></strong> -->
									<strong><a href="javascript:;"  class="blue" style="text-decoration:none">客户档案</a></strong>
									<span id="typeList" class="w300th"> 
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_all")){ %>
										<a href="javascript:;"
										onclick="parent.addTab('所有客户','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/web/client/customer_list.jsp?type=');"
										class="huise">所有客户</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_newCustomer")){ %>
										<a href="javascript:;" onclick="fbStart('新建客户','<%=BaseAction.rootLocation %>/department.business/customer!add.action?form=index',700,519);" class="huise">+客户</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_incubatedAll")){ %>
										<span id="customerType" class="w300th"></span> 
										<span style="clear:both;"></span>
										<a href="javascript:;" onclick="parent.addTab('孵化企业','/department.business/web/images/icon/client_01_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/customer_list.jsp?incubated=YES')" class="huise" style="text-decoration:none">孵化企业</a>
										<a id="incubatedType" href="javascript:;" onclick="parent.addTab('企业信息','/department.business/web/images/icon/client_01_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/customer_list.jsp?status=business.002501')" class="huise"></a> 
										<a id="graduateType" href="javascript:;" onclick="parent.addTab('企业信息','/department.business/web/images/icon/client_01_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/customer_list.jsp?status=business.002502')" class="huise"></a> 
										<a href="javascript:;" onclick="parent.addTab('企业信息','/department.business/web/images/icon/client_01_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/customer_list.jsp?status=business.002503')" class="huise">肄业</a> 
										<span style="clear:both;"></span>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_contact")){ %>
										<a href="javascript:;" onclick="addTabList(this,'client','contect');" class="huise">联系人</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_employee")){ %>
										<a href="javascript:;" onclick="addTabList(this,'client','staffer');" class="huise">企业人员</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_projectInfo")){ %>
										<span style="clear:both;"></span>
										<a href="javascript:;" onclick="" class="huise" style="text-decoration:none">项目信息:</a>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("business_productManage")){ %>
										<a href="javascript:;" onclick="parent.addTab('项目与产品管理','/department.business/web/images/icon/client_12_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/product_list.jsp')" class="huise">项目与产品管理</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("business_projectApply")){ %>
										<a href="javascript:;" onclick="parent.addTab('项目申报情况','/department.business/web/images/icon/client_13_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/projectApply_list.jsp')" class="huise">项目申报情况</a>
										<%} %>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_property")){ %>
										<span style="clear:both;"></span>
										<a href="javascript:;" onclick="" class="huise" style="text-decoration:none">知识产权 :</a>
										<a href="javascript:;" onclick="parent.addTab('专利','/department.business/web/images/icon/client_32_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/patent_list.jsp')" class="huise">专利</a>
										<a href="javascript:;" onclick="parent.addTab('著作权','/department.business/web/images/icon/client_33_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/copyright_list.jsp')" class="huise">著作权</a>
										<a href="javascript:;" onclick="parent.addTab('版权','/department.business/web/images/icon/client_34_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/certification_list.jsp')" class="huise">版权</a>
										<a href="javascript:;" onclick="parent.addTab('商标','/department.business/web/images/icon/client_35_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/brand_list.jsp')" class="huise">商标</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage")){ %>
										<span style="clear:both;"></span>
 										<a href="javascript:;" onclick="parent.addTab('所有项目库','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/web/project/project_message.jsp');" class="huise">OnePage</a>
										<%} %>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_follow")){ %>
								<p class="text">
<!-- 									<strong><a href="javascript:;" onclick="addTabList(this,'client','contectInfo')" class="blue" style="text-decoration:none">客户跟进</a></strong> -->
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">客户跟进</a></strong>
									<span class="w300th"> 
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_follow_contactInfo")){ %>
										<a href="javascript:;" onclick="addNewTab(this,'client','contectInfo','contectInfo',500,320);" class="huise">交往信息</a> 
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_follow_improveArchives")){ %>
										<a id="improveArchiveId" href="javascript:;" onclick="parent.addTab('所有企业','/department.business/web/images/icon/client_01_min.png','<%=BaseAction.rootLocation %>/department.business/web/client/customer_list.jsp?improveArchive=NO')" class="huise"></a>
								<%} %>	
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_careFor")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">客户关怀</a></strong>
									 <span class="w300th">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_careFor_birthday")){ %>
										<a href="javascript:;" class="huise">生日关怀</a>
										<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_careFor_festival")){ %>
								 <a
										href="javascript:;" class="huise">节日关怀</a>
										<%} %>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">常用表单</a></strong> 
									<span class="w300th">
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form_detail")){ %>
										<a href="javascript:;" class="huise" onclick="customerCategory()">客户分类明细表</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form_contact")){ %>
										<a href="javascript:;" class="huise" onclick="customerContact()">客户联系方式表</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form_talents")){ %>
										<a href="javascript:;" class="huise" onclick="customerTalents()">高层次人才表</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form_incubateCustomer")){ %>
										<a href="javascript:;" class=" huise " onclick="customerIncubate()">孵化企业表</a> 
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form_productInfo")){ %>
										<a href="javascript:;" class="huise" onclick="customerProjectInfos()">融资项目信息表</a>
										<%} %>
										<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form_property")){ %>
										<a href="javascript:;" class="huise" onclick="customerProperty()">知识产权表</a>
										<%} %>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_form")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">客户统计</a></strong> 
									<span class="w300th">
									<a href="javascript:;" class="huise" >客户统计</a> 
									</span>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_dataReport")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0302">数据填报</h3>
						<p class="toptext">数据项定义、数据模板定义、创建数据报送报表、数据分析</p>
						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_dataReport_item")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">数据项&nbsp;&nbsp;&nbsp;</a></strong>
									<span class="w300th"> 
									 <a href="javascript:;" onclick="parent.addTab('数据项','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/web/client/dataProperty_list.jsp');" class="huise">定义数据项</a>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_dataReport_template")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">数据模版</a></strong> 
									<span class="w300th">
										<a href="javascript:;" onclick="parent.addTab('所有模版','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/dataTemplate!list.action');" class="huise">所有模版</a>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_dataReport_reprot")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">数据报表</a></strong>
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_dataReport_reprot_all")){ %>
									<span id="test1" class="w300th"> 
										<a href="javascript:;"
											onclick="parent.addTab('所有报表','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/web/client/dataReport_list.jsp');"
											class="huise">所有报表</a> 
										<a href="javascript:;" onclick="fbStart('新建报表','<%=BaseAction.rootLocation%>/department.business/dataReport!add.action?form=index',500,208);" class="huise">+报表</a> 
										<span style="clear:both;"></span>
										<span id="dataReportType" class="w300th"></span>
									</span>
									<%} %>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_dataReport_analyse")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">数据分析</a></strong> 
									<span class="w300th"> 
										<a href="javascript:;"
											onclick="parent.addTab('数据分析','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/analyse!list.action');"
											class="huise">数据分析</a> 
										</span>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_remindAll")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0303">综合提醒</h3>
						<p class="toptext">合同到期提醒、孵化到期提醒、产权到期提醒</p>
						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_remindAll_contract")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">合同到期</a></strong>
									<span class="w300th"></span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_remindAll_incubate")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">孵化到期</a></strong>
									<span class="w300th">
										<a href="javascript:;" class="huise"></a>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_remindAll_property")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">知识产权有效期</a></strong>
									<span class="w300th">
										 <a href="javascript:;" class="huise"></a>
									</span>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
				</div>
				<div class="mainright">
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_parkLog")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0304">园区日志</h3>
						<p class="toptext">最近入驻日志、最近迁出日志、最近续约日志、最近档案调整日志</p>

						<ul>
							<li class="main2">
							<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_parkLog_IN")){ %>
								<p class="text">
									<strong>
										<a href="javascript:;" class="blue" style="text-decoration: none;">入驻迁出</a>
									</strong>
									<span class="w300th">
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_parkLog_IN_recently")){ %>
										<a href="javascript:;" class="huise" style="text-decoration: none;">最近入驻：</a>
										<span style="clear:both;"></span>
										<span id="IN"><a href="javascript:;" class="huise">企业名称1</a>
									 	<a href="javascript:;" class="huise on">企业名称2</a>
										<a href="javascript:;" class="huise">企业名称3</a> 
										<a href="javascript:;" class="huise on">企业名称4</a> </span>
										<span style="clear:both;"></span>
									<%} %>	
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_parkLog_IN_emigration")){ %>
										<a href="javascript:;" class="huise" style="text-decoration: none;">最近迁出：</a>
										<span style="clear:both;"></span>
										<span id="OUT"><a href="javascript:;" class="huise">企业名称1</a>
									 	<a href="javascript:;" class="huise on">企业名称2</a>
										<a href="javascript:;" class="huise">企业名称3</a> 
										<a href="javascript:;" class="huise on">企业名称4</a></span>
										<span style="clear:both;"></span> 
										<%} %>
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_parkLog_IN_renewal")){ %>
										<a href="javascript:;" class="huise" style="text-decoration: none;">最近续约：</a>
										<span style="clear:both;"></span>
										<span id="RELET"><a href="javascript:;" class="huise">企业名称1</a>
									 	<a href="javascript:;" class="huise on">企业名称2</a>
										<a href="javascript:;" class="huise">企业名称3</a> 
										<a href="javascript:;" class="huise on">企业名称4</a> </span>
									<%} %>	
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_parkLog_archivesAdjust")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">档案调整</a></strong> 
									<span id="customerArchivesId" class="w300th">
									</span>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0305">客户服务</h3>
						<p class="toptext">创建服务联系单、跟踪服务联系单、服务联系单种类定义、服务机构定义</p>

						<ul>
							<li class="main2">
							<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service_contactList")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none" >服务联系单</a></strong>
									<span> 
									<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service_contactList_all")){ %>
									<a href="javascript:;" onclick="parent.addTab('所有联系单','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/customerService!list.action');"
										class="huise">所有联系单</a>
									<a href="javascript:;"
										onclick="fbStart('新建联系单','<%=BaseAction.rootLocation%>/department.business/web/client/customerService_add.jsp?form=index',700,238);"
										class="huise">+联系单</a>
									<%} %>										
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service_type")){ %>
								<p class="text">
									<strong> <a href="javascript:;" class="blue" style="text-decoration:none">服务类型</a>
									</strong> <span id="serviceType" class="w300th"></span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service_locus")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">服务轨迹</a></strong> <span>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service_locus_all")){ %>
										<a href="javascript:;"
										onclick="parent.addTab('所有轨迹','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/customerServiceTrack!listAll.action');"class="huise">所有轨迹</a> 
										<a href="javascript:;"
										onclick="fbStart('新建轨迹','<%=BaseAction.rootLocation%>/department.business/web/client/customerServiceTrack_add.jsp?form=index',450,185);"
										class="huise">+轨迹</a>
									</span>
								<%} %>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service_org")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">服务机构</a></strong>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_service_org_all")){ %>
									<span class="w300th">
								 	<a href="javascript:;"
										onclick="parent.addTab('所有机构','/department.business/web/images/icon/projectadmin_02_min.png','<%=BaseAction.rootLocation%>/department.business/web/cooperate/cooperate_list.jsp');"
										class="huise">所有机构</a> 
									<a href="javascript:;"
										onclick="fbStart('新建机构','<%=BaseAction.rootLocation%>/department.business/web/cooperate/cooperate_add.jsp?form=index',600,342);"
										class="huise">+机构</a> <span style="clear: both;"></span>
									<span style="clear:both;"></span>
									<span id="orgType" class="w300th"></span>
									</span>
			               		<%} %>
								</p>
								<%} %>
							</li>
						</ul>
					</div>
					<%} %>
					<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_assist")){ %>
					<div class="mainnewslist">
						<h3 class="title bg0306">辅助</h3>
						<p class="toptext">客户材料、业务转交</p>

						<ul>
							<li class="main2">
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_assist_salePlacard")){ %>
								<p class="text">
									<strong class="fourtext num"><a href="javascript:;" style="text-decoration:none"
										class="blue">销售公告</a>(<font>N</font>)</strong>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_assist_att")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">附件材料</a></strong> 
									<span class="w300th">
										<a href="javascript:;" class="huise"></a>
									</span>
								</p>
								<%} %>
								<%if(CoreActivator.getHttpSessionService().isInResourceMap("customer_assist_deliver")){ %>
								<p class="text">
									<strong><a href="javascript:;" class="blue" style="text-decoration:none">业务转交</a></strong> 
									<span class="w300th">
										<a href="javascript:;" class="huise"></a>
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

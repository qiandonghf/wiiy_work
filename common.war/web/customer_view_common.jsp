<%@page import="com.wiiy.common.activator.ProjectActivator"%>
<%@page import="com.wiiy.common.activator.ProjectActivator"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<div class="pm_view_left">
	<ul>
		<%-- <% 
			Integer index = Integer.parseInt(request.getParameter("index"));
		%>
		<li <%if(index==0){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/customer!view.action?id=<%=request.getParameter("customerId") %>">基本信息</a></li>
		<li <%if(index==1){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/web/client/contect_list_byCustomerId.jsp?id=<%=request.getParameter("customerId") %>">联系人</a></li>
		<li <%if(index==2){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/web/client/contectInfo_list_by_customerId.jsp?id=<%=request.getParameter("customerId") %>">交往信息</a></li>
		<li <%if(index==3){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/web/client/project_view.jsp?id=<%=request.getParameter("customerId") %>">项目信息</a></li>
		<li <%if(index==4){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/web/client/staffer_view_by_customerId.jsp?id=<%=request.getParameter("customerId") %>">主要人员</a></li>
		<li <%if(index==5){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/web/client/knowledge_view.jsp?id=<%=request.getParameter("customerId") %>">知识产权</a></li>
		<%if(ProjectActivator.getSessionUser(request).getUserType().equals(UserTypeEnum.Center)){ %>
		<li <%if(index==6){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/contract!listByCustomerId.action?id=<%=request.getParameter("customerId") %>">合同信息</a></li>
		<li <%if(index==7){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/bill!listByCustomerId.action?id=<%=request.getParameter("customerId") %>">账单信息</a></li>
		<%} %>
		<li <%if(index==8){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>department.business/analyse!customerView.action?id=<%=request.getParameter("customerId") %>">数据填报</a></li>
		 --%>
		
		<% 
			Integer index = Integer.parseInt(request.getParameter("index"));
			boolean desktop = Boolean.parseBoolean(request.getParameter("desktop"));
		%>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_customerMessage_basicView")){ %>
		<li <%if(index==0){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/customer!view.action?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">基本信息</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_linkman")){ %>
		<li <%if(index==1){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/web/client/contect_list_byCustomerId.jsp?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">联系人</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_contectInfo")){ %>
		<li <%if(index==2){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/web/client/contectInfo_list_by_customerId.jsp?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">交往信息</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_customer_investmentView")){ %>
		<li <%if(index==3){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/web/client/project_view.jsp?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">项目信息</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_staffer")){ %>
		<li <%if(index==4){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/web/client/staffer_view_by_customerId.jsp?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">主要人员</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_knowledge")){ %>
		<li <%if(index==5){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/web/client/knowledge_view.jsp?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">知识产权</a></li>
		<%} %>
		<%if(ProjectActivator.getSessionUser(request).getUserType().equals(UserTypeEnum.Center)){ %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_contractMessage")){ %>
		<li <%if(index==6){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/contract!listByCustomerId.action?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">合同信息</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_bill_listByCustomer") && desktop == false){ %>
		<li <%if(index==7){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/common/bill!listByCustomerId.action?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">账单信息</a></li>
		<%} %>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_dataFill") && desktop == false){ %>
		<li <%if(index==8){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/analyse!customerView.action?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">数据填报</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_customerModifyLog") && desktop == false){ %>
		<li <%if(index==9){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/customerModifyLog!loadModifyByCustomerId.action?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">变更信息</a></li>
		<%} %>
		<%if(ProjectActivator.getHttpSessionService().isInResourceMap("business_customerArchiveAtt") && desktop == false){ %>
		<li <%if(index==10){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.business/web/images/tipsico.png" /></span><a href="<%=BaseAction.rootLocation %>/department.business/customerArchiveAtt!list.action?id=<%=request.getParameter("customerId") %>&desktop=<%=desktop%>">企业档案</a></li>
		<%} %>
	</ul>
</div>
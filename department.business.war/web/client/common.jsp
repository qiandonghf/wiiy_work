<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
int index = Integer.parseInt(request.getParameter("index"));
String id = request.getParameter("customerServiceId");
//String status = request.getParameter("investmentStatus");
%>
	<div class="apptab" id="tableid">
		<ul>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_service_contactList_all_info_customerService_list")||
				BusinessActivator.getHttpSessionService().isInResourceMap("business_customerService_allView")){ %>
			<li class="apptabli<%if(index==0){%>over<%} %>"><a href="<%=basePath %>customerService!view.action?id=<%=id %>">企业服务联系单</a></li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_service_contactList_all_info_customerService_locus")||
				BusinessActivator.getHttpSessionService().isInResourceMap("business_customerService_allView")){ %>
			<li class="apptabli<%if(index==1){%>over<%} %>"><a href="<%=basePath %>web/client/customerServiceTrack_view.jsp?id=<%=id %>">服务轨迹</a></li>
		<%} %>
		</ul>
	</div>
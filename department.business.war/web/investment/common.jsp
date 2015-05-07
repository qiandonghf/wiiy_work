<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
int index = Integer.parseInt(request.getParameter("index"));
String id = request.getParameter("investmentId");
String status = request.getParameter("investmentStatus");
%>
	<div class="apptab" id="tableid">
		<ul>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_project_allViewBasic")){ %>
			<li class="apptabli<%if(index==0){%>over<%} %>"><a href="<%=basePath %>investment!view.action?id=<%=id %>">申请信息</a></li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_businessPlan_allView")){ %>
			<li class="apptabli<%if(index==1){%>over<%} %>" ><a href="<%=basePath %>businessPlan!view.action?id=<%=id %>">创业计划书</a></li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_record_allList")){ %>
			<li class="apptabli<%if(index==2){%>over<%} %>" ><a href="<%=basePath %>investmentArchiveAtt!list.action?id=<%=id %>">备案材料</a></li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_track_allList")){ %>
			<li class="apptabli<%if(index==3){%>over<%} %>" ><a href="<%=basePath %>web/investment/investmentLog_list.jsp?id=<%=id %>&investmentStatus=<%=status%>">招商轨迹</a></li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_investmentConfig_all")){ %>
			<li class="apptabli<%if(index==4){%>over<%} %>" ><a href="<%=basePath %>investmentConfig!myList.action?id=<%=id %>">招商线索</a></li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_agreement_allList")){ %>
			<li class="apptabli<%if(index==5){%>over<%} %>" ><a href="<%=basePath %>investmentContractAtt!list.action?id=<%=id %>">协议与合同</a></li>
		<%} %>
		</ul>
	</div>
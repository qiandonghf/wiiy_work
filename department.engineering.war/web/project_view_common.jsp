<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@page import="com.wiiy.engineering.activator.EngineeringActivator"%>
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
		<li <%if(index==0){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>customer!view.action?id=<%=request.getParameter("customerId") %>">基本信息</a></li>
		<li <%if(index==1){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/client/contect_list_byCustomerId.jsp?id=<%=request.getParameter("customerId") %>">联系人</a></li>
		<li <%if(index==2){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/client/contectInfo_list_by_customerId.jsp?id=<%=request.getParameter("customerId") %>">交往信息</a></li>
		<li <%if(index==3){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/client/project_view.jsp?id=<%=request.getParameter("customerId") %>">项目信息</a></li>
		<li <%if(index==4){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/client/staffer_view_by_customerId.jsp?id=<%=request.getParameter("customerId") %>">主要人员</a></li>
		<li <%if(index==5){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/client/knowledge_view.jsp?id=<%=request.getParameter("customerId") %>">知识产权</a></li>
		<%if(SynthesisActivator.getSessionUser(request).getUserType().equals(UserTypeEnum.Center)){ %>
		<li <%if(index==6){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>contract!listByCustomerId.action?id=<%=request.getParameter("customerId") %>">合同信息</a></li>
		<li <%if(index==7){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>bill!listByCustomerId.action?id=<%=request.getParameter("customerId") %>">账单信息</a></li>
		<%} %>
		<li <%if(index==8){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>analyse!customerView.action?id=<%=request.getParameter("customerId") %>">数据填报</a></li>
		 --%>
		
		<%
					Integer index = Integer.parseInt(request.getParameter("index"));
				%>
		<%
			if(EngineeringActivator.getHttpSessionService().isInResourceMap("department_engineering_project_view")){
		%>
		<li <%if(index==0){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%}%>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>project!view.action?id=<%=request.getParameter("projectId")%>">基本信息</a></li>
		<%
			}
		%>
		<%
			if(EngineeringActivator.getHttpSessionService().isInResourceMap("department_engineering_project_contract")){
		%>
		<li <%if(index==1){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%}%>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/project/project_contract_list.jsp?id=<%=request.getParameter("projectId")%>">合同信息</a></li>
		<%
			}
		%>
		<%
			if(EngineeringActivator.getHttpSessionService().isInResourceMap("department_engineering_project_plan")){
		%>
		<li <%if(index==2){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%}%>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/project/project_plan_list.jsp?id=<%=request.getParameter("projectId")%>">计划进度</a></li>
		<%
			}
		%>
		<%
			if(EngineeringActivator.getHttpSessionService().isInResourceMap("department_engineering_project_fact")){
		%>
		<li <%if(index==3){%>class="overbg" style="border-top:none;"<%} else {%>class="pm_color"<%} %>><span><img src="department.engineering/web/images/tipsico.png" /></span><a href="<%=basePath%>web/project/project_fact_list.jsp?id=<%=request.getParameter("projectId") %>">实际进度</a></li>
		<%} %>
	</ul>
</div>
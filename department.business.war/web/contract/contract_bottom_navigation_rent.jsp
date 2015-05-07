<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<div class="apptab" id="tableid">
	<ul>
	<%int flag=-1;
	if(BusinessActivator.getHttpSessionService().isInResourceMap("business_subjectRent_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">租赁房间</li>
	<%} %>
	<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_billPlanRent_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">资金计划</li>
	<%} %>
<%-- 	<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_deposit_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">押金</li>
	<%} %> --%>
	<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractAtt_list")){flag++; %>	
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同附件</li>
	<%} %>
	<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractMemo_list")){flag++; %>	
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同备忘</li>
	<%} %>
	<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_approval_list")){flag++; %>	
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同审批</li>
	<%} %>
	<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_contractModifyLog_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同变更</li>
	<%} %>
	<%if(BusinessActivator.getHttpSessionService().isInResourceMap("business_favorable")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">优惠信息</li>
	<%} %>
	</ul>
</div>
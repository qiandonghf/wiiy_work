<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<div class="apptab" id="tableid">
	<ul>
	<%int flag=-1;
	if(EstateActivator.getHttpSessionService().isInResourceMap("estate_subjectRent_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">租赁房间</li>
	<%} %>
	<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_billPlanRent_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">资金计划</li>
	<%} %>
	<%-- <%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_deposit_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">押金</li>
	<%} %> --%>
	<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_contractAtt_list")){flag++; %>	
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同附件</li>
	<%} %>
	<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_contractMemo_list")){flag++; %>	
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同备忘</li>
	<%} %>
	<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_approval_list")){flag++; %>	
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同审批</li>
	<%} %>
	<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_contractModifyLog_list")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">合同变更</li>
	<%} %>
	<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_favorable")){flag++; %>
		<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">优惠信息</li>
	<%} %>
	</ul>
</div>
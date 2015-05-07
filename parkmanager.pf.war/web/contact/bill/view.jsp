<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
				<input name="bill.id" id="billId" value="${result.value.id }" type="hidden"/>
				<div class="divlays" style="margin:0px;padding:1px;background-color:#fff;"  align="left" >
					<table id="insertTxt" width="671" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="4">
								<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>结算流程</strong></div>
							</td>
						</tr>
						<tr>
							<td class="layertdleft100">申请人：</td>
							<td width="29%" class="layerright"><input readonly="readonly" value="${result.value.user.realName }" class="inputauto"/> </td>
							<td width="40%" class="layertdleft100">填写日期：</td>
							<td width="29%" class="layerright">
								<input readonly="readonly" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${result.value.applyTime }'/>" class="inputauto"/>
							</td>
						</tr>
						<tr>
						    <td class="layertdleft100">结算计划：</td>
							<td class="layerright">
								<input class="inputauto" value="${billRentDto.code }" readonly="readonly"/>
							</td>
				       		<td class="layertdleft100" >预计结算：</td>
				       		<td class="layerright" >
								<input value="${billRentDto.planFee }" readonly="readonly" type="text" class="inputauto" />
						    </td>
				       	</tr>
				       	<tr>
				       		<td class="layertdleft100" >预计结算日期：</td>
					      	<td class="layerright" >
								<input value="<fmt:formatDate pattern='yyyy-MM-dd' value='${billRentDto.planPayDate }'/>" readonly="readonly" type="text" class="inputauto" />
					      	</td>
						    <td class="layertdleft100">合同名称：</td>
							<td class="layerright" >
								<input id="contractId" value="${billRentDto.contractId }" type="hidden"/>
								<input id="contractName" value="${billRentDto.contractName }" readonly="readonly" class="inputauto"/>
						   	</td>
						</tr>
				       	<tr>
				   			<td class="layertdleft100">合同金额：</td>
							<td class="layerright" >
								<input id="contractAmount" value="${billRentDto.contractAmount }" readonly="readonly" class="inputauto" />
						   	</td>
						   	<td class="layertdleft100">已结算金额：</td>
							<td class="layerright" >
								<input id="contractAmount" value='<fmt:formatNumber value="${billRentDto.completedFee }" pattern="0.00"/>' readonly="readonly" class="inputauto" />
						   	</td>
						</tr>
						<tr>
							<td class="layertdleft100">流程：</td>
							<td class="layerright" >
								<input value="${billRentDto.taskName }" readonly="readonly" class="inputauto"/>
						   	</td>
						   	<td class="layertdleft100" >结算日期：</td>
					      	<td class="layerright" >
								<input readonly="readonly"  value="<fmt:formatDate pattern='yyyy-MM-dd' value='${result.value.settlementDate }'/>" readonly="readonly" type="text" class="inputauto" />
					      	</td>
				       	</tr>
				       	<tr>
				       		<td class="layertdleft100">金额：</td>
				       		<td class="layerright" >
								<input readonly="readonly" type="text" class="inputauto" value="${result.value.settlementDate }" />
						    </td>
						    <td class="layertdleft100">结算方式：</td>
				       		<td class="layerright">
								<input readonly="readonly" type="text" class="inputauto" value="${result.value.settlement.title }" />
				       		</td>
				       	</tr>
				       	<tr>
				     		<td class="layertdleft100">结算性质：</td>
				       		<td class="layerright">
								<input readonly="readonly" type="text" class="inputauto" value="${result.value.settlementType.title }" />
				       		</td>
				       		<td class="layertdleft100">收付方向：</td>
				       		<td class="layerright">
								<input readonly="readonly" type="text" class="inputauto" value="${result.value.payment.title }" />
				       		</td>
				       	</tr>
				       	<tr>
				       	    <td class="layertdleft100">备注：</td>
					    	<td class="layerright" colspan="3" style="padding-bottom:3px;">
					    		<textarea readonly="readonly" class="inputauto" style="resize:none;height:40px;">${result.value.memo }</textarea>
					    	</td>
				       	</tr>
						<tr>
							<td colspan="4">
								<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>审批信息</strong></div>
							</td>
						</tr>
					</table>
		       </div>

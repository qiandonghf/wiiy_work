<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
				<input name="purchaseRequisition.id" id="purchaseRequisitionId" value="${result.value.id }" type="hidden"/>
				<div class="divlays" style="margin:0px;padding:1px;" align="left">
					<table id="insertTxt" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="4">
								<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>实际结算</strong></div>
							</td>
						</tr>
						<tr>
							<td class="layertdleft100">申请人：</td>
							<td width="29%" class="layerright"><input id="name" value="${result.value.creator }" disabled="disabled" type="text" class="inputauto" readonly /></td>
							<td width="40%" class="layertdleft100">填写日期：</td>
							<td width="29%" class="layerright">
								<input name="applyTime" disabled="disabled" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${result.value.applyTime }' type='both' />"  class="inputauto" readonly/>
							</td>
						</tr>
						<tr>
					    <td class="layertdleft100"><span class="psred">*</span>申购部门：</td>
						<td class="layerright">
							<input id="purchaseDepartment" readonly name="purchaseRequisition.purchaseDepartment" value="${result.value.purchaseDepartment}" type="text"  class="inputauto"/>
						</td>
						<td class="layertdleft100"><span class="psred">*</span>申购人：</td>
						<td class="layerright">
						<input id="requisitioner" name="purchaseRequisition.requisitioner" value="${result.value.requisitioner}" readonly type="text"  class="inputauto"/>
						</td>
			       	</tr>
			       	<tr>
			       	    <td class="layertdleft100">采购情况：</td>
				    	<td class="layerright" colspan="3" style="padding-bottom:3px;">
				    		<textarea name="purchaseRequisition.opinion" class="inputauto" readonly style="resize:none;height:40px;">${result.value.opinion}</textarea>
				    	</td>
			       	</tr>
				       	<tr>
							<td colspan="4">
								<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>审批信息</strong></div>
							</td>
						</tr>
					</table>
		       </div>

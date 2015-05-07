<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
				<input name="contactForm.id" id="contactFormId" value="${result.value.id }" type="hidden"/>
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
						    <td class="layertdleft100"><span class="psred">*</span>发出部门：</td>
							<td class="layerright">
								<input id="department" readonly name="contactForm.department" type="text" value="${result.value.department }" class="inputauto"/>
							</td>
				       		<td class="layertdleft100" ><span class="psred">*</span>发出时间：</td>
					      	<td class="layerright" >
					      		<table width="143" border="0" cellspacing="0" cellpadding="0">
						        	<tbody>
							          <tr>
							            <td>
							            	<input id="sendTime" name="contactForm.sendTime" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${result.value.sendTime }" type="both"/>" readonly type="text" class="inputauto" onclick="showCalendar('sendTime')"/>
							            </td>
					          			<td width="20" align="center">
					          				<img style="position:relative; left:-1px;" onclick="showCalendar('sendTime')" src="core/common/images/timeico.gif" width="20" height="22" />
					          			</td>
							          </tr>
						        	</tbody>
					      		</table>
					      	</td>
				       	</tr>
				       	<tr>
				       	    <td class="layertdleft100">工作事宜：</td>
					    	<td class="layerright" colspan="3" style="padding-bottom:3px;">
					    		<textarea name="contactForm.affairs" readonly class="inputauto" style="resize:none;height:40px;">${result.value.affairs }</textarea>
					    	</td>
				       	</tr>
				       	<tr>
							<td colspan="4">
								<div class="titlebg2" style="text-align:center;margin:1px 1px 0px 1px;"><strong>审批信息</strong></div>
							</td>
						</tr>
					</table>
		       </div>

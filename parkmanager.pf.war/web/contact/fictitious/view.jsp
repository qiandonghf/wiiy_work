<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
				<input name="fictitious.id" id="fictitiousId" value="${result.value.id }" type="hidden"/>
				<div class="divlays" style="margin:0px;padding:1px;" align="left">
					<table width="100%" id="insertTxt" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:5px;border: solid 1px #666666">
						<tr>
							<td colspan="4" class="noPadding">
								<div class="titlebg2" style="text-align:center;"><strong><c:if test="${result.value.inpark ne 'YES' }">审批表（虚拟）</c:if><c:if test="${result.value.inpark eq 'YES' }">审批表(入驻)</c:if></strong></div>
							</td>
						</tr>
						<tr> 
							<td class="layertdleft100">申请人：</td>
							<td width="29%" class="layerright"><input id="name" readonly value="${result.value.startUserName }" type="text" class="inputauto" /></td>
							<td width="40%" class="layertdleft100">填写日期：</td>
							<td width="29%" class="layerright">
								<input readonly value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${result.value.applyTime }' type='both' />" class="inputauto"/>
							</td>
						</tr>
						<tr>
					<td class="layertdleft100">公司名称：</td>
					<td class="layerright">
						<input id="investmentName" name="fictitious.investmentName" readonly value="${result.value.investmentName }"  type="text" class="inputauto" />
					</td>
					<td class="layerright">
						<a href="javascript:void(0)" style="text-decoration: none;" onclick="fbStart('项目信息','<%=BaseAction.rootLocation %>/parkmanager.pb/investment!view.action?id='+${result.value.investmentId },700,364);">查看项目</a>
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">法人代表：</td>
					<td class="layerright">
						<input id="legalPerson" name="fictitious.legalPerson" readonly value="${result.value.legalPerson }" type="text" class="inputauto readon" />
					</td>
					<td class="layertdleft100">注册资本：</td>
					<td class="layerright">
						<input id="regCapital" name="fictitious.regCapital" readonly value="<fmt:formatNumber pattern="#.##" value="${result.value.regCapital }"/>" type="text" class="inputauto readon" onkeyup="value=value.replace(/[^\d\.]/g,'');" />
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">联系人：</td>
					<td class="layerright">
						<input id="contect" name="fictitious.contect" readonly value="${result.value.contect }" type="text" class="inputauto readon" />
					</td>
					<td class="layertdleft100">办公电话：</td>
					<td class="layerright">
						<input id="phone" name="fictitious.phone" readonly value="${result.value.phone }" type="text" class="inputauto readon" />
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">手机：</td>
					<td class="layerright">
						<input id="mobile" name="fictitious.mobile" readonly value="${result.value.mobile }" type="text" class="inputauto readon" />
					</td>
					<td class="layertdleft100">传真：</td>
					<td class="layerright">
						<input id="fax" name="fictitious.fax" readonly value="${result.value.fax }" type="text" class="inputauto readon" />
					</td>
				</tr>
				<tr>
					<td class="layertdleft100">实际经营地址：</td>
					<td colspan="3" class="layerright">
						<input id="managerAddress" name="fictitious.managerAddress" readonly value="${result.value.managerAddress }" type="text" class="inputauto readon" /></td>
				</tr>
				<tr>
					<td class="layertdleft100">企业经营范围及规模、优势、前景等情况介绍：</td>
					<td colspan="3" class="layerright">
						<textarea id="introduce" name="fictitious.introduce" class='inputauto readon' readonly style='height:40px;resize: none;color:#666;'>${result.value.introduce }</textarea>
					</td>
				</tr>
					</table>
		       </div>
			<%-- <div class="divlays" style="padding:2px 5px 0px 0px;">
				<div class="emaildown2">
					<c:forEach items="${contactAttList }" var="att">
						<div class="downlistprocess2"> <img src="core/common/images/downloadico.png">
							<ul>
								<li>${att.oldName }<span>(<format:fileSize size="${att.size}"/>)</span></li>
								<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.oldName}');">下载</a>
									<a href="javascript:void(0)" onclick="openAtt('${att.newName}')">打开</a>
									<a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
								</li>
								<!-- <li>合同会签审核单.rar<span>(20KB)</span></li>
								<li><a href="javascript:void(0)">下载</a><a href="javascript:void(0)">打开</a><a href="javascript:void(0)">删除</a></li> -->
							</ul>
						</div>
					</c:forEach>
				</div>
			</div>
 --%>
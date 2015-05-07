<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<div class="divlays" style="margin:0px;padding:1px;padding-top: 10px;padding-bottom: 10px;" align="center">
					<table width="97%" id="insertTxt" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:5px;border: solid 1px #666666">
						<tr>
							<td colspan="4" class="noPadding">
								<div class="titlebg2" style="text-align:center;"><strong>请假申请单</strong></div>
							</td>
						</tr>
						<tr>
							<td class="layertdleft100">申请人：</td>
							<td width="29%" class="layerright"><input id="name" readonly value="${result.value.creator }" type="text" class="inputauto" /></td>
							<td width="40%" class="layertdleft100">填写日期：</td>
							<td width="29%" class="layerright">
								<input readonly value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${result.value.applyTime }' type='both' />" class="inputauto"/>
							</td>
						</tr>
						<tr>
							<td class="layertdleft100">请假起始时间：</td>
							<td class="layerright">
								<input readonly value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${result.value.startTime }' type='both' />" class="inputauto"/>
							</td>
							<td class="layertdleft100">请假类型：</td>
							<td class="layerright">${result.value.leaveType.title }</td>
						</tr>
						<tr>
							<td class="layertdleft100">请假结束时间：</td>
							<td class="layerright"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${result.value.endTime }" type="both"/></td>
							<td class="layertdleft100">共计：</td>
							<td class="layerright"> 天</td>
						</tr>
						<tr>
							<td class="layertdleft100">请假原因：</td>
							<td colspan="3" class="layerright tb2" style="margin-bottom:2px;">
								<div class="inputauto" style="height:40px;resize: none;overflow:hidden;overflow-y:scroll;padding-right:1px;">
									${result.value.reason }
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" class="noPadding">
								<div class="titlebg2" style="text-align:center;"><strong>审批信息</strong></div>
							</td>
						</tr>
						
					</table>
		       </div>
			<div class="divlays" style="padding:2px 5px 0px 0px;">
				<div class="emaildown2">			
					<div class="downlistprocess2"> <img src="core/common/images/downloadico.png">
						<ul>
							<li>合同会签审核单.rar<span>(20KB)</span></li>
							<li><a href="javascript:void(0)">下载</a><a href="javascript:void(0)">打开</a><a href="javascript:void(0)">删除</a></li>
						</ul>
					</div>
					<div class="downlistprocess2"> <img src="core/common/images/downloadico.png">
						<ul>
							<li>合同会签审核单.rar<span>(20KB)</span></li>
							<li><a href="javascript:void(0)">下载</a><a href="javascript:void(0)">打开</a><a href="javascript:void(0)">删除</a></li>
						</ul>
					</div>
				</div>
			</div>
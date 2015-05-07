<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="format" uri="http://www.wiiy.com/taglib/format" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
				<div class="emailtop">
	                <div class="leftemail">
	                    <ul>
	                       <li onmouseover="this.className='libg'" onmouseout="this.className=''" ><input type="file" id="file"/></li>
	                    </ul>
	                </div>
	            </div>
				<div class="divlays" id="upFile" style="padding: 2px 5px 10px 5px;<c:if test="${fn:length(contactAttList) eq 0 }">display:none</c:if>"  >
					<div class="emaildown" style="margin-top: 5px; border-bottom: 1px solid #e4e4e4; border-left: 1px solid #e4e4e4; border-right: 1px solid #e4e4e4;">
						<!--外框-->
						<div style="display: block;">
						<c:forEach items="${contactAttList }" var="att">
							<div class="downlistleft">
								<img src="core/common/images/print.png"/>
									<ul>
										<li>${att.oldName }<span>(<format:fileSize size="${att.size}"/>)</span></li>
										<li><a href="javascript:void(0)" onclick="downLoadFile('${att.newName}','${att.oldName}');">下载</a><a
											href="javascript:void(0)" onclick="openAtt('${att.newName}')">打开</a><a href="javascript:void(0)" onclick="deleteFile('${att.id}')">删除</a>
											<a href="javascript:void(0)"></a></li>
									</ul>
							</div>
							</c:forEach>
							<div class="hackbox"></div>
						</div>
						<!--外框-->
						<div class="hackbox"></div>
					</div>
				</div>

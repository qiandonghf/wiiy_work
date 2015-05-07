<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum"%>
<%@taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=BaseAction.rootLocation %>/"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
		
		<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
		<link rel="stylesheet" type="text/css" href="department.business/web/style/merchants.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
		<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
		
		<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.js"></script>
		<script type="text/javascript" src="core/common/js/tools.js"></script>
		<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
		<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript">
			$(function(){
				initTip();
				$('#investRight_height').css('height',getTabContentHeight());
				$('#investRight_height2').css('height',getTabContentHeight());
				$('#investRight_height3').css('height',getTabContentHeight()-33);
				initList();
			});
			
			function initList(){
				$("#list").jqGrid({
			    	url:'<%=basePath%>customerServiceTrack!view.action?id=${param.id}',
					colModel: [
						{label:'处理人', width:70, name:'user.realName', align:"center"}, 
						{label:'服务时间',width:130, name:'handleTime',formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}, align:"center"},
					    {label:'内容',width:130, name:'content', align:"center"}, 
					    {label:'管理',width:70,name:'manager', align:"center", sortable:false, resizable:false}
					],
					height: getTabContentHeight()-100,
					width: 450,
					shrinkToFit: false,
					multiselect: false,
					postData:{filters:generateSearchFilters("serviceId","eq","${result.value.id}","long")},
					gridComplete: function(){
						var ids = $(this).jqGrid('getDataIDs');
						for(var i = 0 ; i < ids.length; i++){
							var id = ids[i];
							var content = "";
							<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_service_contactList_all_info_customerService_locus_del")){ %>
							content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
							<%}%>
							$(this).jqGrid('setRowData',id,{manager:content});
						}
					}
				});
			}
			function deleteById(id){
				if(confirm("确定删除")){
					$.post("<%=basePath%>customerServiceTrack!delete.action?id="+id,function(data){
						showTip(data.result.msg,2000);
			        	if(data.result.success){
			        		reloadList();
			        	}
					});
				}
			}
			function formContent(cellvalue,options,rowObject){
				var span = "<span title='"+cellvalue+"'>";
				if(cellvalue.length>15){
					cellvalue = cellvalue.substring(0,15)+"....";
				}
				span += cellvalue;
				return span;
			}
			
			function reloadList(){
				$("#list").trigger("reloadGrid");
			}
		</script>
		<style>
			.del_icon{
				overflow:hidden;
				background: url(core/common/images/layerdiv.png) -2px -53px;
				background-repeat: no-repeat;
				width: 15px;
				display: inline-block;
				float: left;
			}
			.apptable .tdleft{
				text-align:right;
			}
			.apptable .layerright{
				border-bottom:#cbcbcb 1px solid;
			}
			.apptable .layerright input{
				color:#666;
			}
		</style>
	</head>

	<body style="padding-bottom: 2px;background-color:#fff;">
		<div class="basediv" id="investRight_height" style="border:0px;margin:0px;">
			<div class="divlays" id="investRight_height2" style="margin:0px;padding:0px;padding-right:5px;">
				<jsp:include page="common.jsp">
					<jsp:param value="1" name="index"/>
					<jsp:param value="${param.id}" name="customerServiceId"/>
				</jsp:include>
				<div class="pm_msglist" id="investRight_height3" style="overflow-y:auto;overflow-x:hidden;">
					<div>
						<div class="emailtop" style="border: 1px solid #D9D9D9; background: #f6f6f6;">
							<!--leftemail-->
							<div class="leftemail">
								<ul>
								<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_service_contactList_all_info_customerService_locus_add")){ %>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('添加服务轨迹','<%=basePath %>web/client/customerServiceTrack_add2.jsp?serviceId=${param.id }',450,170);" class="" style="border-color: #f6f6f6;"><span><img src="core/common/images/emailadd.gif"/></span>添加</li>
								<%} %>
								</ul>
							</div>
							<!--//leftemail-->
						</div>
						<div>
							<table id="list" class="jqGridList"><tr><td/></tr></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>

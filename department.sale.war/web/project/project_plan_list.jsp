<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
 <%
 	String path = request.getContextPath();
 String basePath = BaseAction.rootLocation+path+"/";
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />


<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initList("list");
		jqGridResizeRegister("list");
	});
	function initList(list){
		$("#"+list).jqGrid({
	    	url:'<%=basePath%>plan!list.action?id=${param.id}',
			colModel: [
				{label:'编号',width:100,name:'code',align:"center"}, 
				{label:'日期',width:100,name:'time',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'计划完成进度',width:120,name:'schedule',align:"center"}, 
				{label:'经手人',width:100,name:'handling.realName',align:"center"}, 
				{label:'已审核',width:100,name:'audit.title',align:"center",hidden:true}, 
				{label:'已完成',width:100,name:'finished.title',align:"center",hidden:true}, 
				{label:'是否公开',width:100,name:'published.title',align:"center",hidden:true}, 
			    {label:'管理',width:80, name:'manager', align:"center", sortable:false, resizable:false}
			],
			height: 290,
			width: 588,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%boolean add = SaleActivator.getHttpSessionService().isInResourceMap("department_sale_project_fact_add");
					boolean edit = SaleActivator.getHttpSessionService().isInResourceMap("department_sale_project_plan_edit");
					boolean view = SaleActivator.getHttpSessionService().isInResourceMap("department_sale_project_plan_view");
					boolean del = SaleActivator.getHttpSessionService().isInResourceMap("department_sale_project_plan_delete");%>
					<%if(view){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%}%>
					<%if(edit){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%}%>
					<%if(del){%>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
					<%}%>
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			}
		}).navGrid('#pager',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager',{
		    caption : "列选择",
		    title : "选择要显示的列",
		    onClickButton : function(){
		        $(this).jqGrid('columnChooser');
		    }
		});
	}	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function viewById(id){
		fbStart('查看计划进度','<%=basePath%>plan!view.action?id='+id,650,183);
	}
	function addById(id){
		fbStart('新建计划进度','<%=basePath %>plan!add.action?id='+id,650,214);
	}
	function editById(id){
		fbStart('编辑计划进度','<%=basePath%>plan!edit.action?id='+id,650,214);
	}
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>plan!delete.action?id="+id,function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids==""){
			showTip('请先选择进度',2000);	
		}else if(confirm("确定要删除这些进度")){
			$.post("<%=basePath%>plan!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}
	}
</script>

</head>

<body>
		<div class="basediv">
			<!--divlay-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="140" valign="top"><jsp:include page="../project_view_common.jsp">
							<jsp:param value="2" name="index" />
							<jsp:param value="${param.id }" name="projectId" />
						</jsp:include></td>
					<td valign="top" style="background: #eaeced;">
						<div class="pm_view_right" style="height:auto;width:auto;padding-right:0px;">
							<div class="basediv" style="margin: 0px;">
								<div class="titlebg">项目计划进度</div>
								<div class="emailtop">
									<div class="leftemail">
										<ul>
											<%if(add){ %>
											<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="addById(${param.id});"><span><img src="core/common/images/emailadd.gif" /></span>新建</li>
											<%}%>
											<%if(del){ %>
											<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
											<%} %>
										</ul>
									</div>
								</div>
								<table id="list" width="100%">
									<tr>
										<td />
									</tr>
								</table>
								<div id="pager"></div>
								<div class="hackbox"></div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>

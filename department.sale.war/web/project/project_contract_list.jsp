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
	    	url:'<%=basePath%>contract!list.action?id=${param.id}',
			colModel: [
				{label:'项目',width:120,name:'project.name',align:"center"}, 
				{label:'合同编号',width:100,name:'code',align:"center"}, 
				{label:'合同名称',width:120,name:'name',align:"center"}, 
				{label:'甲方',width:100,name:'supplier.name',align:"center"}, 
				{label:'乙方',width:100,name:'customer.name',align:"center"}, 
				{label:'开始日期',width:100,name:'startDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'结束日期',width:100,name:'endDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'签订日期',width:100,name:'signDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
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
					<%boolean add = SaleActivator.getHttpSessionService().isInResourceMap("sale_contract_add");
					boolean edit = SaleActivator.getHttpSessionService().isInResourceMap("sale_contract_edit");
					boolean view = SaleActivator.getHttpSessionService().isInResourceMap("sale_contract_view");
					boolean del = SaleActivator.getHttpSessionService().isInResourceMap("sale_contract_delete");%>
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
		fbStart('查看合同信息','<%=basePath%>contract!viewById.action?id='+id,650,451);
	}	
	
	function editById(id){
		fbStart('编辑合同信息','<%=basePath%>contract!editById.action?id='+id,650,485);
	}

	function add(){
		//fbStart('新建','<%=basePath %>web/contract/contract_add_by_id.jsp',650,485);
		fbStart('新建合同信息','<%=basePath %>project!contractAdd.action?id=${param.id}',650,485)
	}
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>contract!delete.action?id="+id,function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids==""){
			showTip('请先选择合同',2000);	
		}else if(confirm("确定要删除这些合同")){
			$.post("<%=basePath%>contract!delete.action?ids="+ids,function(data){
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
							<jsp:param value="1" name="index" />
							<jsp:param value="<%=request.getParameter("id")%>" name="projectId" />
						</jsp:include></td>
					<td valign="top" style="background: #eaeced;">
						<div class="pm_view_right" style="height:auto;width:auto;padding-right:0px;">
							<div class="basediv" style="margin: 0px;">
								<div class="titlebg">合同信息</div>
								<div class="emailtop">
									<div class="leftemail">
										<ul>
											<%if(add){ %>
											<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="add();"><span><img src="core/common/images/emailadd.gif" /></span>新建</li>
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

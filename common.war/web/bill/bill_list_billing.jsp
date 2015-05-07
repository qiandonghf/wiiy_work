<%@page import="com.wiiy.common.activator.ProjectActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
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
<link rel="stylesheet" type="text/css" href="core/common/style/base.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script>
	$(document).ready(function() {
		initTip();
		//$("#resizable").resizable();
		$("#resizable").css("height",getTabContentHeight());
		$("#msglist").css("height",getTabContentHeight());
		$("#treeviewdiv").css("height",getTabContentHeight()-30);
		$("#browser").tree({
			animate: true,
			lines: true,
			onClick: function(node){
				if($(this).tree("isLeaf",node.target)){
					$("#list").setGridParam({url:'<%=basePath%>bill!listByTypeId.action?id='+$(node.target).find("input").val()}).trigger('reloadGrid');
				}
			}
		});
		initList();
	});
	function initList(){
		var height = getTabContentHeight()-103;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft)-185;
		$("#list").jqGrid({
	    	url:'<%=basePath%>bill!loadBill.action?settlementType=${settlementType}',
			colModel: [
				{label:'企业名称', name:'customerName',sortable:false, align:"center"}, 
				{label:'流水号', name:'number',sortable:false, align:"center"}, 
			    {label:'费用类型', name:'billType.typeName',sortable:false, align:"center"}, 
				{label:'收支', name:'inOut.title',sortable:false, align:"center",formatter:outChange,hidden :true}, 
			    {label:'结算金额', name:'factPayment',sortable:false, align:"center"},
			    {label:'计费开始时间',width:100, name:'feeStartTime',sortable:false,hidden :true, align:"center", formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'计费结束时间',width:110, name:'feeEndTime',sortable:false, align:"center", formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'出账日期',width:100, name:'checkoutTime', align:"center",sortable:false, formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'结算日期', name:'payTime', align:"center",sortable:false,hidden :true, formatter:'date',formatoptions:{srcformat: 'Y-m-d', newformat: 'Y-m-d'}},
			    {label:'状态',width:80, name:'status.title',sortable:false, align:"center"},
			    {label:'是否开票',width:80, name:'invoice.title',sortable:false, align:"center"},
			    {label:'企业编号', name:'customerId',sortable:false, align:"center",hidden :true},
			    {label:'管理',width:180, name:'manager',sortable:false, align:"center", resizable:false},
			],
			height: height,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var customerId = $(this).getCell(id,"customerId");
					var customerName =  $(this).getCell(id,"customerName");
					var inOut = $(this).getCell(id,'inOut.title');
					var stauts = $(this).getCell(id,'status.title');
					var content = "";
					content += "<a href=\"javascript:void(0)\" onclick=\"viewById('"+id+"');\">查看</a> ";
					if(stauts=='未结算'){
						if(inOut=='收入'){
							content += "<a href=\"javascript:void(0)\" onclick=\"settlement('"+inOut+"','"+customerId+"','"+customerName+"');\">结算</a> ";  
						} else {
							content += "<a href=\"javascript:void(0)\" onclick=\"settlement('"+inOut+"','"+customerId+"','"+customerName+"');\">结算</a> ";
						}
					}
					/* content += "<a href=\"javascript:void(0)\" onclick=\"backById('"+id+"');\">退回</a> "; */
					content += "<a href=\"javascript:void(0)\" onclick=\"chargeoffById('"+id+"');\">核销</a> ";
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
	//开票
	function invoiceById(id){
		fbStart('开票','<%=basePath %>bill!invoiceById.action?id='+id,500,250);
	}
	//退回
	function backById(id){
		if(confirm("确定要退回吗")){
			$.post("<%=basePath%>bill!back.action?id="+id,function(data){
				showTip(data.result.msg,2000);
				if(data.result.success){
					$("#list").trigger("reloadGrid");
				}
			});
		}
	}
	
	function outChange(cellvalue, options, rowObject ){
		return cellvalue;
	}
	
	function settlement(inout,customerId ,customerName){
		if(inout=='支出'){
			fbStart('退款单','<%=basePath%>web/bill/bill_out.jsp?id='+customerId+'&customerName='+customerName,'700','490');
		}else {
			fbStart('收款单','<%=basePath%>web/bill/bill_in.jsp?id='+customerId+'&customerName='+customerName,'700','490')
		}
	}
	
	function viewById(id){
		fbStart($("#list").getRowData(id)["customerName"],'<%=basePath %>bill!view.action?id='+id,550,360);
	}
	function editById(id){
		fbStart('编辑账单','<%=basePath %>bill!edit.action?id='+id,500,370);
	}
	function chargeoffById(id){
		if(confirm("确定要核销吗")){
			$.post("<%=basePath%>bill!chargeoff.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function deleteById(id){
		if(confirm("确定要删除吗")){
			$.post("<%=basePath%>bill!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids!='' && confirm("确定要删吗")){
			$.post("<%=basePath%>bill!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
	}
	function setSelectedCustomer(customer){
		$("#customerId").val(customer.id);
		$("#customerName").val(customer.name);
	}
	
	function exportDate(){
		var columns = "{";
		$.each($("#list").getGridParam("colModel"),function(){
			if(this.label && this.name!="manager" && !this.hidden){
				columns += "\"" + this.name + "\"" + ":" + "\"" + this.label + "\",";
			}
		});
		columns += "\"" + "roomAddress" + "\"" + ":" + "\"" + "房间地址" + "\",";
		columns += "\"" + "roomArea" + "\"" + ":" + "\"" + "房间面积" + "\",";
		columns = deleteLastCharWhenMatching(columns,",");
		columns += "}";
		$("#columns").val(columns);
		$("#exportForm").submit();
	}
	
	//催缴
	function remindBills(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids==''){
			showTip('请选择账单',2000);
		}
		if(ids!=''){
			if(confirm("是否催缴")){
				$.post("<%=basePath%>bill!remindById.action?ids="+ids,function(data){
					showTip(data.result.msg,2000);
					if(data.result.success){
						$("#list").trigger("reloadGrid");
					}
				});
			}
		}
	}
</script>
</head>

<body>
<form action="<%=basePath%>bill!export.action" id="exportForm" method="post">
	<input type="hidden" id="columns" name="columns"/>
	<input type="hidden" id="filters" name="filters"/>
</form>
	<div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
	        	<td width="182" valign="top">
					<div class="agency" id="resizable">
						<div class="titlebg">费用类型</div>
						<div class="treeviewdiv" style="overflow-Y:auto;" id="treeviewdiv">
						 	<ul id="browser" class="filetree">
								<c:forEach items="${billTypeList}" var="type">
								<c:if test="${type.parentId eq null}">
								<li state="closed" id="typeLi${type.id}">
									<span class="folder">${type.typeName}<input type="hidden" value="${type.id}" /><input type="hidden" value="${type.typeName}" /></span>
									<ul id="typeUl${type.id}">
										<c:forEach items="${billTypeList}" var="subType">
											<c:if test="${subType.parentId eq type.id}">
												<li class="public">${subType.typeName}<input type="hidden" value="${subType.id}" /></li>
											</c:if>
										</c:forEach>
									</ul>
								</li>
								</c:if>
								</c:forEach>
						 	</ul>
			          	</div>
					</div>		
				</td>
				<td width="100%" valign="top">
					<div class="emailtop">
						<div class="leftemail">
							<ul>
								<%if(ProjectActivator.getHttpSessionService().isInResourceMap("pb_bill_in")){ %>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('收款单','<%=basePath%>bill!billIn.action',700,485);"><span><img src="core/common/images/bill_01.gif"/></span>收款</li>
								<%} %>
								<%-- <%if(ProjectActivator.getHttpSessionService().isInResourceMap("pb_bill_add")){ %>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('添加账单','<%=basePath%>web/bill/bill_add.jsp',500,400);"><span><img src="core/common/images/bill_05.gif"/></span>补收</li>
								<%} %> --%>
								<%if(ProjectActivator.getHttpSessionService().isInResourceMap("pb_bill_add")){ %>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="remindBills();"><span><img src="core/common/images/bill_05.gif"/></span>催缴</li>
								<%} %>
							</ul>
						</div>
					</div>
					<div class="searchdivkf">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" height="25">
							<tr>
								<td width="65">企业名称：</td>
								<td width="200">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<search:choose dataType="long" field="customerId" op="eq">
												<input type="hidden" id="customerId" class="data"/>
												</search:choose>
												<search:choose dataType="string" field="customerName" op="cn">
												<input onkeyup="$('#customerId').val('')" id="customerName" name="endTime" type="text" class="data inputauto"/>
												</search:choose>
											</td>
											<td width="20"><img style="cursor:pointer;" src="core/common/images/outdiv.gif" width="20" height="22"  onclick="fbStart('选择企业','<%=basePath %>customer!select.action',520,400);"/></td>
										</tr>
									</table>
								</td>
								<td><label><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map"></label></td>
							</tr>
						</table>
					</div>
					
					<div style="overflow: auto;">
						<table id="list" class="jqGridList"><tr><td/></tr></table>
						<div id="pager"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<map name="Map" id="Map">
		<area shape="rect" coords="1,2,50,21" href="javascript:void(0)" onclick="doSearch()"/>
		<area shape="rect" coords="54,0,73,19" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/bill/bill_search.jsp',550,410);" />
	</map>
</body>
</html>

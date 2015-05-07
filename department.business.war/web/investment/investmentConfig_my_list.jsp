<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
 <%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/merchants.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
		<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
		<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
		
		<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.js"></script>
		<script type="text/javascript" src="core/common/js/tools.js"></script>
		
		<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
		<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
		<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#investRight_height').css('height',getTabContentHeight()-8);
		$('#investRight_height2').css('height',getTabContentHeight()-12);
		$('#investRight_height3').css('height',getTabContentHeight()-46);
		initTip();
		initList("list");
		jqGridResizeRegister("list");
	});
	function initList(list){
		$("#"+list).jqGrid({
	    	url:'<%=basePath%>investmentConfig!list.action?id=${param.id}',
			colModel: [
				{label:'名称', name:'name', align:"center",width:180}, 
				{label:'联系电话', name:'phone', align:"center",width:100}, 
				{label:'日期', name:'startTime',width:140,formatter:'date',formatoptions:{srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, width:120, align:"center"}, 
				{label:'接待人员', name:'receiver.realName',width:100, align:"center"}, 
				{label:'产业', name:'pruduct', width:100, align:"center",hidden:true},
				{label:'需求', name:'demand', width:100, align:"center",hidden:true},
				{label:'线索等级',index:'level', name:'level.title', width:100, align:"center"},
				{label:'回访提醒', name:'returnVisit', width:140,formatter:'date',formatoptions:{srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, width:120, align:"center",hidden:true},
				{label:'需求', name:'demand', width:100, align:"center",hidden:true},
				{label:'状态', width:'100' ,name:'entityStatus.title', align:"center",formatter:checkStatus}, 
			    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:70}
			    
			],
			shrinkToFit: false,
			height: getTabContentHeight()-124,
			width: document.documentElement.clientWidth-8,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					var stateTxt = $(this).getCell(id,'entityStatus.title');
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_view")){ %>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_delete")){ %>
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
	
	function checkStatus(cellvalue,options,rowObject){
		if(cellvalue =='禁用'){
			return "关闭";
		}
		return cellvalue;
	}
	
	function changeState(id){
		$.post("<%=basePath%>investmentContectInfo!changeState.action?id="+id,function(data){
			showTip(data.result.msg,2000);
			if(data.result.success){
				$("list").trigger("reloadGrid");
			}
		});
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function viewById(id){
		fbStart('查看来电','<%=basePath%>investmentContectInfo!view.action?id='+id,600,500);
	}
	function editById(id){
		fbStart('编辑来电','<%=basePath%>investmentContectInfo!edit.action?id='+id,500,343);
	}
	function deleteById(id){
		if(confirm("您确定要删除关联")){
			$.post("<%=basePath%>investmentConfig!delete.action?id="+id+"&investmentId=${param.id}",function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function deleteByIds(){
		var selectedRowIds =   
		$("#list").jqGrid("getGridParam","selarrrow");
		if(confirm("您确定要删除关联")){
			$.post("<%=basePath%>investmentConfig!delete.action?ids="+selectedRowIds+"&investmentId=${param.id}",function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
		/* $("#startTime1").val("");
		$("#startTime2").val(""); */
	}
</script>

</head>

<body>
<div class="basediv" id="investRight_height">
	<div class="divlays" style="margin:0px;" id="investRight_height2">
		<jsp:include page="common_my.jsp">
			<jsp:param value="4" name="index"/>
			<jsp:param value="${id}" name="investmentId"/>
		</jsp:include>
		<div class="pm_msglist" id="investRight_height3" style="overflow-y:auto;overflow-x:hidden;">
			<div class="emailtop">
				<div class="leftemail">
					<ul>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_add")){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''"onclick="fbStart('选择线索','<%=basePath %>investmentConfig!select.action?id=${id }',500,400);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>关联</li>
					<%} %>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_delete")){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteByIds()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
					<%} %>
					</ul>
				</div>
			</div>
			<table id="list">
			<tr><td></td></tr></table>
			<div id="pager"></div>
		</div>
	</div>
</div>
</body>
</html>

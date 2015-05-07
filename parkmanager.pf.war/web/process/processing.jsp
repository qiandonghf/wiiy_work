<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		initList();
	});
	
	function showImage(id,definitionId){
		fbStart('查看当前节点','<%=basePath%>web/process/process_showNode.jsp?id='+id+'&definitionId='+definitionId,830,450);
	}
	
	function initList(){
		var height = getTabContentHeight()-53;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>process!running.action',
			colModel: [
				{label:'id', name:'id',align:"center",hidden:true,width:50}, 
				{label:'执行Id', name:'executeId',align:"center",width:80}, 
				{label:'实例Id', width:80,name:'instanceId', align:"center"},
				{label:'流程Id',width:200,name:'definitionId',align:"center"}, 
				{label:'当前节点',width:200,name:'currentNodeName',align:"center",formatter: function (value, grid, rec, state) 
					{ return "<a title='点击查看流程图' style='text-decoration:none;color:blue;' href='javascript:showImage(\""+rec.id+"\",\""+rec.definitionId+"\");'>"+value+"</a>" }},
				{label:'是否结束',width:80,name:'isEnd.title',align:"center",sortable:false, resizable:false}, 
				{label:'状态',width:120,name:'state.title',align:"center",sortable:false, resizable:false}, 
				{label:'管理',width:80, name:'manager', align:"center", sortable:false, resizable:false}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			multiselect: true,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var stateTxt = $(this).getCell(id,'state.title');
					var content = "";
					<%boolean add = PfActivator.getHttpSessionService().isInResourceMap("workflow_process_add");
					boolean delete = PfActivator.getHttpSessionService().isInResourceMap("workflow_process_delete");
					boolean state = PfActivator.getHttpSessionService().isInResourceMap("workflow_process_changeState");%>
					//content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%-- <%if(edit){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%} %> --%>
					<%if(state){%>
						if(stateTxt == '激活')
							content += "<img src=\"core/common/images/back.png\" width=\"14\" height=\"14\" title=\"挂起流程\" onclick=\"changeState('"+id+"');\"  /> ";
						else
							content += "<img src=\"core/common/images/uploadicon.png\" width=\"14\" height=\"14\" title=\"激活流程\" onclick=\"changeState('"+id+"');\"  /> ";
					<%} %>
					<%-- <%if(delete){%>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
					<%} %> --%>
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
	function viewById(id){
		fbStart('查看','<%=basePath%>param!view.action?id='+id,670,410);
	}	
	
	function editById(id){
		fbStart('编辑','<%=basePath%>param!edit.action?id='+id,670,508);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function refreshTree(){
		$("#list").trigger("reloadGrid");
	}
	
	function changeState(id){
		$.post("<%=basePath%>process!changeState.action?id="+id,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		reloadList();
        	}
		});
	}
	
	function deleteById(id){
		if(confirm("确定删除此流程")){
			$.post("<%=basePath%>process!processDelete.action.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}
	}
	
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids==""){
			showTip('请先选择一个流程',2000);	
		}else if(confirm("确定要删除这些流程")){
			$.post("<%=basePath%>param!delete.action?ids="+ids,function(data){
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
<%-- <!--emailtop-->
<div class="emailtop">
	<div class="leftemail">
		<ul>
			<%
			if (add) {
			%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建流程','<%=basePath %>web/system/param_add.jsp',670,508);"><span><img src="core/common/images/emailadd.gif"/></span>新建</li>
			<%} %>
			<%
			if (delete) {
			%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
			<%} %>
		</ul>
	</div>
</div>
<!--//emailtop--> --%>
<!--container-->
<div class="msglist" id="msglist" style="padding-bottom:0px;">
	<div id="container">
		<table id="list" class="jqGridList"><tr><td/></tr></table>
		<div id="pager"></div>
	</div>
</div>
<!--//container-->
</body>
</html>

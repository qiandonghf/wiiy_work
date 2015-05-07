<%@page import="com.wiiy.sale.activator.SaleActivator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	
	<script type="text/javascript">
		$(function(){
			initTip();
			initFileList();
		});
 		
 		function initFileList(){
 			var height = $(this).height()-86;
 			$("#list").jqGrid({
 		    	url:'<%=basePath%>contractAtt!list.action?id=${param.id }',
 				colModel: [
					{label:'ID', name:'id', align:"center",hidden:true}, 
 					{label:'合同名称',name:'contract.name',align:"center",hidden:true}, 
 					{label:'文件类别',width:80,name:'type.title',align:"center"}, 
 					{label:'名称',width:80,name:'name',align:"center"}, 
 					{label:'路径', name:'newName', align:"center",hidden:true}, 
 					{label:'备注',name:'memo',align:"center"}, 
 				    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:60}
 				],
 				height: height,
 				width: 630,
 				shrinkToFit: false,
 				gridComplete: function(){
 					var ids = $(this).jqGrid('getDataIDs');
 					for(var i = 0 ; i < ids.length; i++){
 						var id = ids[i];
 						var content = "";
 						<%boolean attAdd = SaleActivator.getHttpSessionService().isInResourceMap("sale_file_add");
 						boolean attEdit = SaleActivator.getHttpSessionService().isInResourceMap("sale_file_edit");
 						boolean attView = SaleActivator.getHttpSessionService().isInResourceMap("sale_file_view");
 						boolean attDel = SaleActivator.getHttpSessionService().isInResourceMap("sale_file_delete");%>
 						content += "<img src=\"core/common/images/down.png\" width=\"14\" height=\"14\" title=\"下载\" onclick=\"downloadContractAttById('"+id+"');\"  /> "; 
 						<%if(attEdit){%>
 						content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"fileEdit('"+id+"');\"  /> ";
 						<%}%>
 						<%if(attDel){%>
 						content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"fileDelete('"+id+"');\"  /> ";
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
 		
 		function downloadContractAttById(id){
 			var path = $("#list").getCell(id,6);
 			var name = $("#list").getCell(id,5);
 			location.href="<%=BaseAction.rootLocation%>/core/resources/"+path+"?name="+name;
 		}
 		function fileAdd(id){
 			fbStart('新建合同文件','<%=basePath%>web/contract/contractAtt_add.jsp?id='+id,650,245);
 		}
 		function fileEdit(id){
 			fbStart('编辑合同文件','<%=basePath%>contractAtt!edit.action?id='+id,650,100);
 		}
 		function fileDelete(id){
 			if(confirm("您确定要删除")){
 				$.post("<%=basePath%>contractAtt!delete.action?id="+id,function(data){
 					showTip(data.result.msg,1000);
 					if(data.result.success){
 		        		reloadList();
 		        	}
 				});
 			}
 		}
 		function fileDeleteSelected(){
 			var ids = $("#list").jqGrid("getGridParam","selarrrow");
 			if(ids==""){
 				showTip('请先选择合同文件',2000);	
 			}else if(confirm("确定要删除这些合同文件")){
 				$.post("<%=basePath%>contractAtt!delete.action?ids="+ids,function(data){
 					showTip(data.result.msg,2000);
 		        	if(data.result.success){
 		        		reloadList();
 		        	}
 				});
 			}
 		}
 		function reloadList(){
 			$("#list").trigger("reloadGrid");
 		}
 	
	</script>
</head>
<body>
<div class="basediv" style="margin-top:0px;">
	  <!-- 合同文件 -->
	  <div class="divlays" style="margin:0px;padding:0px;">
	  	<div class="basediv" style="margin: 0px;border:0px;">
			<div class="emailtop">
				<div class="leftemail">
					<ul>
						<%if(attAdd){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fileAdd(${param.id})"><span><img src="core/common/images/emailadd.gif" /></span>新建</li>
						<%}%>
						<%if(attDel){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fileDeleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
						<%} %>
					</ul>
				</div>
			</div>
			<!--container-->
			<div class="msglist" id="msglist" style="border:0px;padding-bottom:0px;">
				<div id="container">
					<table id="list" class="jqGridList"><tr><td/></tr></table>
					<div id="pager"></div>
				</div>
			</div>
			<!--//container-->
		</div>
	  </div>
</div>
</body>
</html>

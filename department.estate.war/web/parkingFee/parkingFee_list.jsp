<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@page import="com.wiiy.commons.util.DateUtil"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/><title>无标题文档</title>
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
	
	function initList(){
		var height = getTabContentHeight()-105;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>parkingFee!list.action?times=${param.currentDate}',
			colModel: [
				{label:'车牌号', name:'licenseNo',width:60,align:"center"}, 
				{label:'车位', name:'parkingManage',width:60,align:"center"}, 
				{label:'单位/个人',width:80,name:'name',align:"center"}, 
				{label:'收费人',width:60,name:'toller',align:"center"}, 
				{label:'金额', width:60,name:'money', align:"center",hidden:false},
				{label:'开始时间',width:80, name:'startDate',align:"center",formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}}, 
				{label:'结束时间',width:80, name:'endDate',align:"center",formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}}, 
				{label:'备注', width:80,name:'memo', align:"center",hidden:true},
			    {label:'管理',width:80, name:'manager', align:"center", sortable:false, resizable:false}
			],
			height: height,
			autowidth:true,
			shrinkToFit: true,
			multiselect: true,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_car_registration_view")){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_car_registration_edit")){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%}%>
					<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_car_registration_delete")){%>
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
	
	 
	
	
	function viewById(id){
		fbStart('查看','<%=basePath%>parkingFee!view.action?id='+id,430,348);
	}	
	
	function editById(id){
		fbStart('编辑','<%=basePath%>parkingFee!edit.action?id='+id,430,348);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>parkingFee!delete.action?id="+id,function(data){
				if(data.result!=null){
					showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		$("#list").trigger("reloadGrid");
		        	}
				}else{
					showTip("没有权限");
				}
			});
		}
	}
	
	function deleteSelected(){
		if(confirm("确定要删吗")){
			var ids = $("#list").jqGrid("getGridParam","selarrrow");
			$.post("<%=basePath%>parkingFee!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	
	function add(){
		fbStart('新建','<%=basePath %>web/parkingFee/parkingFee_add.jsp?form=${param.form}',450,348);
	}
	
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
	}
	function addParkingFeeTab(title,icon,url){
		parent.addTab(title,icon,url);
	}

</script>
</head>
<body>
<input type="hidden" id="applyId"/>
<div class="emailtop">
	<div class="leftemail">
		<ul>
		<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_car_registration_add")){%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="add();"><span><img src="core/common/images/emailadd.gif"/></span>新建</li>
		<%}%>
		<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_base_car_registration_delete")){%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
		<%}%>
		</ul>
	</div>
</div>
<div class="searchdivkf">
  <form id="form2" name="form2" method="post" action="">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="70">车牌号码： </td>
        <td width="105"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
            	 <search:input  dataType="string" field="infoAll" op="cn" inputClass="inputauto" />
            </td>
          </tr>
        </table>       
         <label></label></td>
        <td width="80" align="center"><input name="Submit" type="button" class="search_cx" value="" onclick="doSearch()" /></td>
		<td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </form>
</div>
<!--//emailtop-->
<!--container-->
<div id="container">
	<table id="list" class="jqGridList"><tr><td/></tr></table>
	<div id="pager"></div>
</div>
<!--//container-->
</body>
</html>

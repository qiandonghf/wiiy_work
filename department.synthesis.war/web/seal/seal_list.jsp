<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
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
	
	function initList(){
		<%
		Map<String, ResourceDto> resourceMap = SynthesisActivator.getHttpSessionService().getResourceMap();
		boolean add = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_sealManage_add");
		boolean edit = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_sealManage_edit");
		boolean del = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_sealManage_delete");
		boolean view = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_sealManage_view");
		%>
		<%if(add || del){ %>
			var height = getTabContentHeight()-105;
		<%}else{%>
			var height = getTabContentHeight()-80;
		<%}%>
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>seal!list.action',
			colModel: [
				{label:'名称', name:'name',align:"center", sortable:false}, 
				{label:'部门',width:80,name:'sealNames',align:"center", sortable:false}, 
				{label:'类型',width:80,name:'sealType.dataValue',align:"center", sortable:false}, 
			    {label:'管理',width:80, name:'manager', align:"center", sortable:false, resizable:false}
			],
			height: height,
			width: width,
			shrinkToFit: true,
			multiselect: true, 
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(view){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%} %>
					<%if(edit){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%} %>
					<%if(del){%>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
					<%} %>
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
		fbStart('查看','<%=basePath%>seal!view.action?id='+id,420,102);
	}	
	
	function editById(id){
		fbStart('编辑','<%=basePath%>seal!edit.action?id='+id,420,135);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function deleteById(id){
		if(confirm("确定删除印章")){
			$.post("<%=basePath%>seal!delete.action?id="+id,function(data){
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
			showTip('请先选择印章',2000);	
		}else if(confirm("确定要删除这些印章")){
			$.post("<%=basePath%>seal!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
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
</script>
</head>
<body>
<!--emailtop-->
<%if(add || del){ %>
<div class="emailtop">
	<div class="leftemail">
		<ul>
			<%if(add){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建','<%=basePath %>web/seal/seal_add.jsp',420,135);"><span><img src="core/common/images/emailadd.gif"/></span>添加</li>
			<%} %>
			<%if(del){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
			<%} %>
		</ul>
	</div>
</div>
<%} %>
<div class="searchdivkf">
  <form id="form2" name="form2" method="post" action="">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="70">印章名称： </td>
        <td width="105"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
            	 <search:input  dataType="string" field="name" op="cn" inputClass="inputauto" />
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

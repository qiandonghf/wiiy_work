<%@page import="com.wiiy.synthesis.preferences.enums.SealApplyEnum"%>
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
		String type = (String)request.getAttribute("type");
		String addstr = "synthesis_";
		String editstr = "synthesis_";
		String delstr = "synthesis_";
		String viewstr = "synthesis_";
		if("Agree".equals(type)){
			addstr = addstr+"sealRegistration";
			editstr = editstr+"sealRegistration";
			delstr = delstr+"sealRegistration";
			viewstr = viewstr+"sealRegistration";
		}else if("Apply".equals(type)){
			addstr = addstr+"sealApply";
			editstr = editstr+"sealApply";
			delstr = delstr+"sealApply";
			viewstr = viewstr+"sealApply";
		}else{
			addstr = addstr+"all";
			editstr = editstr+"all";
			delstr = delstr+"all";
			viewstr = viewstr+"all";
		}
		Map<String, ResourceDto> resourceMap = SynthesisActivator.getHttpSessionService().getResourceMap();
		boolean add = SynthesisActivator.getHttpSessionService().isInResourceMap(addstr+"_add");
		boolean edit = SynthesisActivator.getHttpSessionService().isInResourceMap(editstr+"_edit");
		boolean del = SynthesisActivator.getHttpSessionService().isInResourceMap(delstr+"_delete");
		boolean view = SynthesisActivator.getHttpSessionService().isInResourceMap(viewstr+"_view");
		//审批
		boolean agree = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_sealRegistration_approve");
		%>
		<%if(add || del){ %>
			var height = getTabContentHeight()-105;
		<%}else{%>
			var height = getTabContentHeight() - 79;
		<%}%>
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>sealRegistration!list.action?type=${type}',
	    	colModel: [
   				{label:'使用人',width:100, name:'user.realName',align:"center", sortable:false}, 
   				{label:'印章',name:'sealNames',align:"center", sortable:false}, 
   				{label:'状态',name:'status.title',align:"center", sortable:false}, 
   				{label:'使用日期',width:100,name:'time',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
   				{label:'申请日期',width:100,name:'createTime',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
   			    {label:'管理',width:80, name:'manager', align:"center", sortable:false, resizable:false}
   			],
			height: height,
			width: width,
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
					if($(this).getCell(id,"status.title")=='<%=SealApplyEnum.SAPPLAY.getTitle()%>'){
						<%if("Agree".equals(type)&&agree){%>
						content += "<img src=\"core/common/images/profile.gif\" width=\"14\" height=\"14\" title=\"同意申请\" onclick=\"approve('"+id+"');\"  /> ";
						<%} %>
					}
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
		fbStart('查看','<%=basePath%>sealRegistration!view.action?id='+id,505,205);
	}	
	
	function approve(id){
		if(confirm("同意申请")){
			$.post("<%=basePath%>sealRegistration!agree.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}else{
			if(confirm("是否删除记录")){
				$.post("<%=basePath%>sealRegistration!delete.action?id="+id,function(data){
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
	}
	
	function editById(id){
		fbStart('编辑','<%=basePath%>sealRegistration!edit.action?id='+id+'&type=<%=type%>',505,260);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function deleteById(id){
		if(confirm("确定删除")){
			$.post("<%=basePath%>sealRegistration!delete.action?id="+id,function(data){
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
			showTip('请先选择',2000);	
		}else if(confirm("确定要删除")){
			$.post("<%=basePath%>sealRegistration!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		reloadList();
	        	}
			});
		}
	}
	
	function add(){
		<%if("Agree".equals(type)){%>
			fbStart('新建','<%=basePath %>web/seal/sealRegistration_add.jsp',505,260);
		<%}else{%>
			fbStart('新建','<%=basePath %>web/seal/sealApply_add.jsp',505,260);
		<%}%>
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
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="add()"><span><img src="core/common/images/emailadd.gif"/></span>添加</li>
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
            	 <search:input  dataType="string" field="seal.name" op="cn" inputClass="inputauto" />
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
<div  id="container">
	<table id="list" class="jqGridList"><tr><td/></tr></table>
	<div id="pager"></div>
</div>
<!--//container-->
</body>
</html>

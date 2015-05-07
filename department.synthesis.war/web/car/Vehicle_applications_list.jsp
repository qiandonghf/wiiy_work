<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="com.wiiy.synthesis.preferences.enums.CarApplyStatusEnum"%>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
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
		<%
		String type = (String)request.getAttribute("type");
		String addstr = "synthesis_";
		String editstr = "synthesis_";
		String delstr = "synthesis_";
		String viewstr = "synthesis_";
		if("registration".equals(type)){
			addstr = addstr+"carRegistration";
			editstr = editstr+"carRegistration";
			delstr = delstr+"carRegistration";
			viewstr = viewstr+"carRegistration";
		}else if("apply".equals(type)){
			addstr = addstr+"carApply";
			editstr = editstr+"carApply";
			delstr = delstr+"carApply";
			viewstr = viewstr+"carApply";
		}else{
			addstr = addstr+"allcar";
			editstr = editstr+"allcar";
			delstr = delstr+"allcar";
			viewstr = viewstr+"allcar";
		}
		Map<String, ResourceDto> resourceMap = SynthesisActivator.getHttpSessionService().getResourceMap();
		boolean add = SynthesisActivator.getHttpSessionService().isInResourceMap(addstr+"_add");
		boolean edit = SynthesisActivator.getHttpSessionService().isInResourceMap(editstr+"_edit");
		boolean del = SynthesisActivator.getHttpSessionService().isInResourceMap(delstr+"_delete");
		boolean view = SynthesisActivator.getHttpSessionService().isInResourceMap(viewstr+"_view");
		boolean agree = SynthesisActivator.getHttpSessionService().isInResourceMap("synthesis_carRegistration_approve");
		%>
		<%if(add || del){ %>
			var height = getTabContentHeight()-105;
		<%}else{%>
			var height = getTabContentHeight()-80;
		<%}%>
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
	    	url:'<%=basePath%>carApply!list.action?type=${type}',
			colModel: [
				{label:'车牌号', name:'licenseNo',align:"center"}, 
				{label:'申请人',width:80,name:'creator',align:"center"}, 
				{label:'用车人',width:80,name:'usePersons',align:"center"}, 
				{label:'开始时间',width:90, name:'startDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'结束时间',width:90, name:'endDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
				{label:'审批状态', width:80,name:'status.title',align:"center"}, 
				{label:'原因', width:160,name:'reason', align:"center"},
				{label:'申请时间', width:160,name:'applyDate', align:"center",hidden:true,formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
				{label:'里程', width:160,name:'distance', align:"center",hidden:true},
				{label:'油耗', width:160,name:'oil', align:"center",hidden:true},
				{label:'备注', width:160,name:'memo', align:"center",hidden:true},
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
					if($(this).getCell(id,'status.title')=='<%=CarApplyStatusEnum.PENDING.getTitle()%>'){
						<%if("registration".equals(type) && agree){%>
							content += "<img src=\"core/common/images/profile.gif\" width=\"14\" height=\"14\" title=\"同意\" onclick=\"approve('"+id+"');\"  /> ";
						<%} %>
					}
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
	
<%-- 	function auidtById(id){
		if(confirm("是否提交申请?")){
			$("#applyId").val(id);
			fbStart('选择审批人','<%=BaseAction.rootLocation%>/core/user!select.action',520,400);
		}
	} --%>
	
	function approve(id){
		if(confirm("同意申请")){
			$.post("<%=basePath%>carApply!approveCarApply.action?id="+id+"&applyCheck="+1,function(data){
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}else{
			if(confirm("是否删除记录")){
				$.post("<%=basePath%>carApply!delete.action?id="+id,function(data){
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
	
	
	function viewById(id){
		fbStart('车辆查看','<%=basePath%>carApply!view.action?id='+id,550,305);
	}	
	
	function editById(id){
		fbStart('车辆编辑','<%=basePath%>carApply!edit.action?id='+id,550,305);
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>carApply!delete.action?id="+id,function(data){
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
			$.post("<%=basePath%>carApply!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	
	function setSelectedUser(user){
		var id = user.id;
		var name = user.realName;
		$.post("<%=basePath%>carApply!approve.action?approverId="+id+"&approverl="+name+"&id="+$("#applyId").val(),function(data){
			if(data.result.success){
				showTip("送审成功",2000);
				$("#list").trigger("reloadGrid");
			}
		});
	}
	function add(){
		<%if("registration".equals(type)){%>
			fbStart('车辆登记','<%=basePath %>web/car/Vehicle_registration_add.jsp',550,305);
		<%}else{%>
			fbStart('新建车辆申请单','<%=basePath %>web/car/Vehicle_applications_add.jsp',550,305);
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
<input type="hidden" id="applyId"/>
<%if(add || del){ %>
<div class="emailtop">
	<div class="leftemail">
		<ul>
		    <%if(add){%>
				<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="add();"><span><img src="core/common/images/emailadd.gif"/></span>新建车辆申请单</li>
		    <%} %>
		    <%if(del){%>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
		    <%}%>
		</ul>
	</div>
</div>
<%} %>
<div class="searchdivkf">
  <form id="form2" name="form2" method="post" action="">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="70">车牌号码： </td>
        <td width="105"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
            	 <search:input  dataType="string" field="licenseNo" op="cn" inputClass="inputauto" />
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

<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/smartMenu.css"/>
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
var imageMenuData = [
	             	    [{
	             	        text: "新建分组",
	             			classname: "smarty_menu_ico0",
	             	        func: function(e) {
                         	fbStart('新建分组信息','<%=BaseAction.rootLocation%>/core/org!createChild.action?srcOrg.id='+$(this.parentNode).attr("node-id"),450,210);
	             	        }
	             	    }, 
/* 	             	    {
	             	        text: "插入子机构",
	             			classname: "smarty_menu_ico1",
	             	        func: function(e) {
                             fbStart('编辑机构信息','core/org!edit.action?srcOrg.id='+$(this.parentNode).attr("node-id"),450,200);
	             	        }
	             	    },
*/	             	    {
	             	        text: "编辑分组",
	             			classname: "smarty_menu_ico1",
	             	        func: function(e) {
                             fbStart('编辑分组信息','<%=BaseAction.rootLocation%>/core/org!edit.action?srcOrg.id='+$(this.parentNode).attr("node-id"),450,200);
	             	        }
	             	    },
          	    	{
	             	        text: "删除分组",
	             			classname: "smarty_menu_ico2",
	             	        func: function(e) {
	             	        	
                         	if ($("#tt").tree("getChildren", this.parentNode).length>0) {
                         		alert("请先删除子分组");
                         		return;
                         	}
                             if (confirm("您确定要删除")) {
                                 $.ajax({
                                     async : false,
                                     url:"<%=BaseAction.rootLocation%>/core/org!delete.action",
                                     type:"post",
                                     data:{"_method":"delete", "srcOrg.id" : $(this.parentNode).attr("node-id")},
                                     error:function (XMLHttpRequest, textStatus, errorThrown) {
                                         alert(errorThrown);
                                     },
                                     success:function(r) {
                                    	 if (r.success) {
                                          	$("#tt").tree("remove", $("#tt").tree("find", r.value.id).target);
                                    	 } else {
                                    		 showTip(r.msg + ", 该组织已被用户引用。", 2000);
                                    	 }
                                     }
                                 });
                             }
	             	        }
	             	    }]
	             	];

// var _parent_node_removed_below;
$(document).ready(function() {
    //$("#resizable").resizable();
	$("#resizable").css("height",getTabContentHeight()-10);
	$("#orgTreeContainer").css("height",$("#resizable").height()-40);
	initTip();
	initOrgTree();
	initList();
});
function initOrgTree() {
		$.ajax({
		  "url" : "<%=BaseAction.rootLocation%>/core/org!treeOrgs.action",
		  type:"POST",
		  success: function(data){
			$("#tt").tree({
				"data" : data,
				onClick : function(node) {
					//$("#org_data").val(node.id);
					loadUserById(node.id);
					//$("#list").jqGrid('setGridParam',{page:1,postData:{filters:getSearchFilters()}}).trigger('reloadGrid');
				},
			});
			$("#tt .tree-title").smartMenu(imageMenuData);
		  }
		});
}
function loadUserById(id){
	$("#list").setGridParam({url:'<%=BaseAction.rootLocation%>/core/user!loadUserByOrgId.action?id='+id}).trigger('reloadGrid');
<%-- 	$("#list").setGridParam({url:'<%=basePath%>personnel!loadUserByUserId.action?id='+id}).trigger('reloadGrid');
 --%>}
function editById(id){
	fbStart('编辑用户','<%=basePath%>roster!edit.action?id='+id,588,506);
}
function deleteById(id){
	if(confirm("确定要删吗")){
		$.post("<%=BaseAction.rootLocation%>/core/user!delete.action?id="+id,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		$("#list").trigger("reloadGrid");
        	}
		});
	}
}
function viewById(id){
	fbStart('查看','<%=basePath%>roster!view.action?id='+id,650,297);
}	
function refreshDataTables() {
	  $("#list").trigger("reloadGrid");
	 }

var addSubOrg = function (parentId, newNode) {
	var node = $('#tt').tree('find', parentId);
	$('#tt').tree('append', {parent:node.target, data:[{"id" : newNode.id, "text" : newNode.name}]});
	$($('#tt').tree('find', newNode.id).target).find(".tree-title").smartMenu(imageMenuData);
};
var updateOrg = function (newNode) {
	var node = $('#tt').tree('find', newNode.id);
	$('#tt').tree('update', {target:node.target, id:newNode.id,text:newNode.name});
    //$("#orgTreeContainer").jstree("create", selectedOrgNode, "last", newNode, false, true);
};
function initList(){
	var height = window.parent.document.documentElement.clientHeight-252;
	$("#list").jqGrid({
    	url:'<%=BaseAction.rootLocation%>/core/user!list.action',
		datatype: 'json',
		prmNames: {search: "search"},
		jsonReader: {root:"root",repeatitems: false},
		colModel: [
		    {label:'真实姓名', name:'realName', index:'realName',width:110, align:"center"}, 
		    {label:'移动电话', name:'mobile', index:'mobile', width:90, align:"center"},
		    {label:'所属部门', name:'orgName', index:'org', width:90, align:"center"},
		    {label:'Email', name:'email', index:'email', width:100, align:"center"},
		    {label:'职务', name:'orgName', index:'org',width:110, align:"center"}, 
		    {label:'管理', name:'manager', width:90, align:"center", title:false, sortable:false, resizable:false} 
	],
		shrinkToFit: false,
		height: height,
		sortname:'createTime',
		sortorder:'asc',
		rowNum: 20,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
		rowList: [10,20,30],//用来调整表格显示的记录数
		autowidth: true,//宽度自动
		multiselect: true,//是否可以多选
		viewrecords: true,//是否显示总记录数
		rownumbers: true,//显示行顺序号，从1开始递增。此列名为'rn'
		loadui:'disable',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
		pager: '#pager',//指定分页栏对象
		gridComplete: function(){
			var ids = $(this).jqGrid('getDataIDs');
			for(var i = 0 ; i < ids.length; i++){
				var id = ids[i];
				var content = "";
				content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" alt=\"编辑\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> "; 
				content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" alt=\"删除\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
				content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" alt=\"查看\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
				$(this).jqGrid('setRowData',id,{manager:content});
			}
		},
		gridview: true
	})
	.navGrid('#pager',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager',{
	    caption : "列选择",
	    title : "选择要显示的列",
	    onClickButton : function(){
	        $(this).jqGrid('columnChooser');
	    }
	});
}
function expandAllNode() {
	/* var selectedNode = $("#tt").tree("getSelected");
	if (selectedNode == null) {
		selectedNode = $("#tt").tree("getRoot");
	}		
	$("#tt").tree("expandAll", selectedNode.target); */
	$("#tt").tree("expandAll");
}
function collapseAllNode() {
	/* var selectedNode = $("#tt").tree("getSelected");
	if (selectedNode == null) {
		selectedNode = $("#tt").tree("getRoot");
	}		
	$("#tt").tree("collapseAll", selectedNode.target); */
	$("#tt").tree("collapseAll");
}
/*  function doSearch(){
	search(getSearchFilters());
}  */
function doSearch(){
	var filters = getSearchFilters();
	$("#filters").val(filters);
	search(filters);
}
function search(filters){
	$("#filters").val(filters);
	$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
}
/*  function search(filters){
	$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
}  */
function deleteSelected(){
	if(confirm("确定要删吗")){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		$.post("<%=BaseAction.rootLocation%>/core/user!delete.action?ids="+ids,function(data){
			showTip(data.result.msg,2000);
        	if(data.result.success){
        		$("#list").trigger("reloadGrid");
        	}
		});
	}
}
</script>
<style type="text/css">
	.refresh{
		overflow:hidden;
	}
	.refresh a{
		display:inline-block;
	}
	.refresh span{
		display:block;
		float:left;
	}
	.re1{
		margin-top:4px;
	}
</style>
</head>

<body>
<!-- <div class="msgpage">您的删除成功</div>
 -->
<!--container-->
<div id="container">
<!-- <input id="org_field" class="field" type="hidden" value="org.id"/>
<input id="org_op" class="op" type="hidden" value="eq"/>
<input id="org_dataType" class="dataType" type="hidden" value="long"/>
<input id="org_data" type="hidden" class="data" value="-1"/> -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="182" valign="top">
                <div class="agency" id="resizable">
                    <!--titlebg-->
                    <div class="titlebg">分组设置</div>
                    <!--//titlebg-->
                    <!--agencybtn-->
                    <div class="agencybtn">
                        <ul>
                            <li><a href="javascript:expandAllNode()"><span class="ico2"></span>展开</a></li>
                            <li><a href="javascript:collapseAllNode()"><span class="ico3"></span>收起</a></li>
                        </ul>
                    </div>
                    <!--//agencybtn-->
                    <!--agencylist-->
                    <div>
                    <form name="menuForm">
                        <input type="hidden" name="id" value=""/>
                        <div id="orgTreeContainer" style="width: 182;overflow-x: auto">
                            <ul id="tt"></ul>
                        </div>
                    </form>
                    </div>
                    <!--//agencylist-->
                </div>
            </td>
            <td width="100%" valign="top">
                <!--msglist-->
                <div class="msglist" id="msglist">
                    <!--titlebg-->
                    <div class="titlebg">分组信息</div>
                    <div class="emailtop">
						<!--leftemail-->
						<div class="emailtop">
							<div class="leftemail">
								<ul>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建','<%=BaseAction.rootLocation%>/core/user!create.action',588,542);"><span><img src="core/common/images/emailadd.gif"/></span>添加</li>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
								</ul>
							</div>
						</div>
					</div>
                    <div class="searchdivkf">
					    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
					     <tr>
					        <td width="45" align="right">模糊查询：</td>
					        <td width="100">
					        	<search:input dataType="string" field="infoAll" op="nc" inputClass="inputauto"/>
					     	</td>
					     	<td width="10">&nbsp;</td>
					        <td width="75" align="center"><input type="button" name="Submit" value="" class="searchbtn" onclick="doSearch()"/></td>
					        <td>&nbsp;</td>
					        <td>&nbsp;</td>
					      </tr>
					    </table>
					</div>
                    <!--//titlebg-->
						<table id="list" class="jqGridList"><tr><td/></tr></table>
						<div id="pager"></div>
                    </div>
                <!--//msglist-->        
                </td>
        </tr>
    </table>
</div>
<!--//container-->
</body>
</html>

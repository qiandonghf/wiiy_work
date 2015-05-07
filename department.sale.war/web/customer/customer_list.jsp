<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@ page import="com.wiiy.sale.activator.SaleActivator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>

<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String customerIds = request.getParameter("customerIds");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
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
<style>a{TEXT-DECORATION:none}</style>
<script type="text/javascript">
	<%if(SaleActivator.getSessionUser(request).getAdmin().equals(BooleanEnum.YES)){%>
	var customterMenu=[[
	<%boolean flag=false;
	if(SaleActivator.getHttpSessionService().isInResourceMap("sale_customerCategory_rename")){
		flag=true;%>
	
 	{
		text:"重命名",
		classname:"smarty_menu_ico1",
		func:function(){
			fbStart('重命名','<%=basePath%>customerCategory!edit.action?id='+$(this).find('input').val(),300,80);
		}
	}
	<%}%>
	<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_customerCategory_delete")){
		if(flag){
			%>,<%}%>
 	{
		text:"删除",
		classname:"smarty_menu_ico2",
		func:function(){
			if(confirm("您确定要删除")){
				$.post("<%=basePath%>customerCategory!delete.action?id="+$(this).find('input').val(),function(data){
					showTip(data.result.msg,1000);
					setTimeout("location.reload()", 1000);
				});
			}
		}
	}
 	<%}%>
 	],[
 	   <%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_tag_add")){%>
 	   {
		text:"添加标签",
		classname:"smarty_menu_ico0",
		func:function(){
			fbStart('添加标签','<%=basePath%>customerLabel!addByCategoryId.action?id='+$(this).find('input').val(),300,80);
		}
	}
 	   <%}%>]];
	<%}%>
	var customterLabel=[[
	<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_customerLabel_edit")){%>
 	{
		text:"重命名",
		classname:"smarty_menu_ico1",
		func:function(){
			fbStart('重命名','<%=basePath%>customerLabel!edit.action?id='+$(this).find('input').val(),300,80);
		}
	}
 	<%}%>
 	],[
	<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_customerLabel_delete")){%>
	 	   {
			text:"删除",
			classname:"smarty_menu_ico2",
			func:function(){
				if(confirm("您确定要删除")){
					$.post("<%=basePath%>customerLabel!delete.action?id="+$(this).find('input').val(),function(data){
						showTip(data.result.msg,1000);
						setTimeout("location.reload()", 1000);
					});
				}
			}
		}
	 <%}%>	   
 	   ]];
	<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_tag_add")){%>
	var MyCustomterMenu=[[{
		text:"添加标签",
		classname:"smarty_menu_ico0",
		func:function(){
			
			fbStart('添加标签','<%=basePath%>customerLabel!addByCategoryId.action?id='+$(this).find('input').val(),300,80);
			
		}
	}]];
	<%}%>
	$(document).ready(function() {
		initTip();
		//$("#resizable").resizable();
		$("#resizable").css("height",getTabContentHeight()-28);
		$("#msglist").css("height",getTabContentHeight()-28);
		$("#treeviewdiv").css("height",getTabContentHeight()-82);
		initTree();
		initList();
	});
	function loadCustomerLabelByCategoryId(categoryId){
		if($("#customerCategoryLi"+categoryId).hasClass("expandable")){
			$.post("<%=basePath%>customerLabel!loadCustomerLabelByCategoryId.action?id="+categoryId,function(data){
				if(data.result.success){
					$("#customerLabelUl"+categoryId).empty();
					for(var i = 0; i < data.result.value.length; i++){
						var customerLabel = data.result.value[i];
						var span = $("<span class=\"file\"></span>").append(customerLabel.name).append($("<input />",{type:"hidden",value:customerLabel.id}));
						span.smartMenu(customterLabel,{name:'customterLabel'});
						span.bind("click",function(){
							loadCustomerByLabelId($(this).find('input').val());
						});
						$("#customerLabelUl"+categoryId).append($("<li></li>").append($("<a href=\"javascript:void(0)\"></a>").append(span)));
					}
				}
			});
		}
	}
	function loadMyLabel(categoryId){
		if($("#customerCategoryLi"+categoryId).hasClass("expandable")){
			$.post("<%=basePath%>customerLabel!loadMyLabel.action",function(data){
				if(data.result.success){
					$("#customerLabelUl"+categoryId).empty();
					for(var i = 0; i < data.result.value.length; i++){
						var customerLabel = data.result.value[i];
						var span = $("<span class=\"file\"></span>").append(customerLabel.name).append($("<input />",{type:"hidden",value:customerLabel.id}));
						span.smartMenu(customterLabel,{name:'customterLabel'});
						span.bind("click",function(){
							loadCustomerByLabelId($(this).find('input').val());
						});
						$("#customerLabelUl"+categoryId).append($("<li></li>").append($("<a href=\"javascript:void(0)\"></a>").append(span)));
					}
				}
			});
		}
	}
	function loadCustomerByLabelId(labelId){
		$("#labelId").val(labelId);
		$("#list").setGridParam({url:'<%=basePath%>customer!list.action?labelId='+labelId}).trigger('reloadGrid');
	}
	function loadAllCustomer(){
		$("#list").setGridParam({url:'<%=basePath%>customer!list.action'}).trigger('reloadGrid');
	}
	
	function customerName(cellvalue, options, rowObject){
		var id = rowObject["id"];
		var data = "";
 		data = "<a href=\"javascript:void(0)\" onclick=\"showView('"+cellvalue+"','"+id+"');\">"+cellvalue+"</a>";
		return data;
	}
	
	function showView(name,id){
		fbStart(name,'<%=basePath %>customer!view.action?id='+id,870,450);
	}
	
	function initList(){
		var type = "${param.type}";
		var own = "${param.own}";
		var url = "<%=basePath%>customer!list.action";
		if(type && type != 'null'){
			url += "?type="+type;
		}
		if(own && own != 'null'){
			if(url.indexOf("?") != -1){
				url +="&";
			}else{
				url +="?";
			}
			url += "own=my";
		} 
		var height = getTabContentHeight()-135;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft)-186;
		$("#list").jqGrid({
	    	url:url,
			colModel: [
				{label:'姓名', name:'name', align:"center",width:100}, 
				{label:'性别', name:'gender.title', align:"center",width:100}, 
				{label:'年龄', name:'age', align:"center",width:60}, 
			    {label:'客户类型', name:'customerType.title', align:"center"},
			    {label:'置业顾问', name:'user.realName', align:"center"},
				{label:'证件号', name:'idNO', align:"center", hidden:true},
				{label:'EMAIL', name:'email', align:"center", hidden:true},
			    {label:'电话', name:'phone', align:"center", hidden:true},
			    {label:'手机号码', name:'mobile', align:"center", hidden:true},
			    {label:'婚否', name:'marriage.title', align:"center",width:60,hidden:true}, 
			    {label:'家庭住址', name:'address', align:"center", hidden:true},
			    {label:'地段', name:'section.title', align:"center",width:60},
			    {label:'户型', name:'houseType.title', align:"center",width:60},
			    {label:'价格', name:'cost.title', align:"center", hidden:true},
			    {label:'环境', name:'environment.title', align:"center", hidden:true},
			    {label:'需求级别', name:'demandLevel.title', align:"center", hidden:true},
			    {label:'客户等级', name:'level.title', align:"center",width:60},
			    {label:'学历', name:'education.dataValue', align:"center", hidden:true},
			    {label:'职业', name:'profession.dataValue', align:"center", hidden:true},
			    {label:'家庭年收入', name:'familyIncome.dataValue', align:"center", hidden:true},
			    {label:'可接受价格区间', name:'acceptPrice', align:"center", hidden:true},
			    {label:'客户区域', name:'clientArea.dataValue', align:"center", hidden:true},
			    {label:'讯息来源', name:'source.dataValue', align:"center", hidden:true},
			    {label:'抗性分析', name:'resistance.dataValue', align:"center", hidden:true},
			    {label:'购房动机', name:'motivation.dataValue', align:"center", hidden:true},
			    {label:'户型需求', name:'huxing.dataValue', align:"center", hidden:true},
			    {label:'管理', name:'manager', align:"center",width:80,sortable:false, resizable:false}
			],
			height: height,
			width: width,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_view")){%>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> "; 
					<%}%>
					<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_edit")){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> "; 
					<%}%>
					<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_delete")){%>
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
		fbStart('查看','<%=basePath %>customer!view.action?id='+id,700,490);
	}
	function editById(id){
		fbStart('编辑','<%=basePath %>customer!edit.action?id='+id,700,520);
	}
	function reloadList(){
   		$("#list").trigger("reloadGrid");
	}
	function deleteById(id){
		if(confirm("确定要删吗")){
			$.post("<%=basePath%>customer!delete.action?id="+id,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function doSearch(){
		var filters = getSearchFilters();
		$("#filters").val(filters);
		search(filters);
	}
	function search(filters){
		$("#filters").val(filters);
		$("#list").jqGrid('setGridParam',{url:"<%=basePath%>customer!list.action",page:1,postData:{filters:filters}}).trigger('reloadGrid');
	}
	function deleteSelected(){
		var ids = $("#list").jqGrid("getGridParam","selarrrow");
		if(ids!='' && confirm("确定要删吗")){
			$.post("<%=basePath%>customer!delete.action?ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		$("#list").trigger("reloadGrid");
	        	}
			});
		}
	}
	function initTree(){
		$("#browser").tree({
			animate: true,
			url:"<%=basePath%>customer!loadCategory.action",
			onClick: function(node){
				if($(this).tree("isLeaf",node.target)){
					if($(node.target).find("input").val()==null){
						loadAllCustomer();
					}
					loadCustomerByLabelId($(node.target).find("input").val());
				}
			},
			onBeforeExpand: function(node){
				if(node.id>-1){
					$("#browser").tree("options").url="<%=basePath%>customer!loadLabelByCategoryId.action";
				} else {
					$("#browser").tree("options").url="<%=basePath%>customer!loadCategory.action";
				}
			},
			onLoadSuccess: function(node, data){
				var nodes = $(this).tree("getRoots");
				for( var i = 0; i < nodes.length; i++){
					var node = nodes[i];
					if(!$(this).tree("isLeaf",node.target)){
						if(node.id==0){
							$(node.target).find(".tree-title").smartMenu(MyCustomterMenu,{name:'myCustomterMenu'});
						<%if(SaleActivator.getSessionUser(request).getAdmin().equals(BooleanEnum.YES)){%>
						} else {
							$(node.target).find(".tree-title").smartMenu(customterMenu,{name:'customterMenu'});
						<%}%>
						}
						var children = $(this).tree("getChildren",node.target);
						for( var j = 0; j < children.length; j++){
							var child = children[j];
							$(child.target).find(".tree-title").smartMenu(customterLabel,{name:'customterLabel'});
						}
					}
				}
			}
		});
	}
	
	function refreshGroup(){
		initTree();
	}
	 
</script>

</head>
<body>
<form action="<%=basePath%>customer!export.action" id="exportForm" method="post">
	<input type="hidden" id="columns" name="columns"/>
	<input type="hidden" id="filters" name="filters"/>
	<input type="hidden" id="labelId" name="labelId"/>
</form>
<div class="emailtop">
	<div class="leftemail">
		<ul>
		<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_add")){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建客户','<%=basePath %>customer!add.action',700,520);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
		<%} %>
		<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_delete")){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
		<%} %>
		</ul>
	</div>
</div>
<div id="container">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="182" valign="top">
				<div class="agency" id="resizable" >
					<div class="titlebg">客户分组</div>
					<%if(SaleActivator.getSessionUser(request).getAdmin().equals(BooleanEnum.YES)){ %>
					<div class="agencybtn">
						<ul>
						<%if(SaleActivator.getHttpSessionService().isInResourceMap("sale_enterprise_classify")){ %>
							<li><a href="javascript:void(0)" onclick="fbStart('客户分类','<%=basePath %>web/customer/group_add.jsp',300,80);"><span class="icoadd"></span>客户分类</a></li>
						<%} %>
						</ul>
					</div>
					<%} %>
					<div class="treeviewdiv" style="overflow-Y:auto;" id="treeviewdiv">
						<ul id="browser" class="filetree">
						</ul>
					 </div>
				</div>
			</td>
			<td width="100%" valign="top">
				<div class="pm_msglist" id="msglist">
					<div class="titlebg">客户列表</div>
					<div class="searchdivkf">
						<form id="form1" name="form1" method="post" action="">
						<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="65">客户名称： </td>
								<td width="105">
									<table width="100%" border="0" cellspacing="2" cellpadding="0">
										<tr>
											<td width="180">
												<search:input id="name" dataType="string" inputClass="inputauto" field="name" op="cn"/>
											</td>
						  				</tr>
									</table>
								</td>
								<td width="70" align="right">客户类型：</td>
								<td width="105">
									<search:choose id="customerType"  dataType="com.wiiy.sale.preferences.enums.CustomerTypeEnum" field="customerType" op="eq" >
										<enum:select styleClass="data" type="com.wiiy.sale.preferences.enums.CustomerTypeEnum" />
									</search:choose>
								</td>
								<td width="70" align="right">客户等级：</td>
								<td width="105">
									<search:choose id="level"  dataType="com.wiiy.sale.preferences.enums.CustomerLevelEnum" field="level" op="eq" >
										<enum:select styleClass="data" type="com.wiiy.sale.preferences.enums.CustomerLevelEnum" defaultValue="${param.type }"/>
									</search:choose>
								</td>
								<td width="75" align="center"><label><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map"/></label></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
						</form>
					</div>
					<div style="overflow: auto;">
						<table id="list" class="jqGridList"><tr><td/></tr></table>
						<div id="pager"></div>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>
<map name="Map" id="Map">
	<area shape="rect" coords="2,1,51,20" href="javascript:void(0)" onclick="doSearch()"/>
	<area shape="rect" coords="55,1,74,20" href="javascript:void(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/customer/customer_search.jsp',480,260);" />
</map>
</body>
</html>
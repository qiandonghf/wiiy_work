<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
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
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#browser").tree({
			animate: true,
			lines: true,
			onClick: function(node){
				if($(this).tree("isLeaf",node.target)){
					var garageId = $(node.target).find("input").val();
					if(garageId==null){
						$("#list").setGridParam({url:'<%=basePath%>parkingManage!listAll.action',postData:{filters:''}}).trigger('reloadGrid'); 
					}else{
						$("#list").setGridParam({url:'<%=basePath%>parkingManage!listByGarage.action?id='+garageId,postData:{filters:''}}).trigger('reloadGrid');
					}
				}
			}
		});
		initList();
		jqGridResizeRegister("list");
	});
	
	
	
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").setGridParam({postData:{filters:filters}}).trigger('reloadGrid');
	}
	function initList(){
		$("#list").jqGrid({
			url:'<%=basePath%>parkingManage!listAll.action',
			datatype: 'json',
			mtype: 'POST',
			prmNames: {search: "search"},
			jsonReader: {root:"root",repeatitems: false},
			colModel: [
				{label:'&nbsp;', sortable: false, width:20, name:'MY_ID', index:'MY_ID', align:"center"},
				{label:'ID', name:'id', index:'id', align:"center",hidden:true},
				{label:'所属车库', name:'garage.title', align:"center"},
				{label:'车库ID', name:'garageId', align:"center",hidden:true},
				{label:'所属车位', name:'parkingId', align:"center"},
				{label:'车位状态', name:'status.title', align:"center",hidden:true},
				{label:'描述', name:'description', hidden:true, align:"center",hidden:true}
			],
			height: 235,
			rowNum: 10,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
			autowidth: true,//宽度自动
			multiselect: false,//是否可以多选
			viewrecords: true,//是否显示总记录数
			recordtext: "共 {2} 条",
			rownumbers: true,//显示行顺序号，从1开始递增。此列名为'rn'
			loadui: 'block',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
			pager: '#pager',//指定分页栏对象
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
                    var radioId = "<input id='"+id+"' type='radio' name='myname' value='" + id + "'  />";
                    
					$(this).jqGrid('setRowData',id,{MY_ID:radioId});
				}
			},
			onSelectRow: function(id) {
                $($(this)).find("input[value="+id+"]").prop('checked',true);
            },
			gridview: true
		}).navGrid('#pager',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager',{
		    caption : "列选择",
		    title : "选择要显示的列",
		    onClickButton : function(){
		        $(this).jqGrid('columnChooser');
		    }
		});
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	
	function submitSelectedPark() {
		try{
			if(typeof(getOpener().eval("setSelectedPark")) == "function"){
				var ids = $("#list").jqGrid("getDataIDs");
				$(ids).each(function (index, id) {
					if ($("#list").find('#'+id+' input').prop('checked')) {
						var rowData = $("#list").jqGrid("getRowData", id);
						getOpener().setSelectedPark(rowData);
						return;
					}
				});
			} 
		} catch(e){}
		fb.end();
	}
</script>
</head>

<body>
	<div class="basediv">
		<table width="100%" border="0" cellspacing="0" cellpadding="10">
			<tr>
				<td valign="top">
					<div class="userleftcontact" id="resizable">
						<div class="titlebg">园区分类</div>
						<div class="treeviewdiv" style="overflow-Y:auto;height: 187px;" id="treeviewdiv">
							<ul id="browser" class="filetree">
								<li>
									<span class="file" onclick="loadAllCustomer()">所有车位</span>
								</li>
								 <c:forEach items="${parkList}" var="park">
								<li state="closed" id="parkLi${park.id}">
								<span class="folder public" >${park.name}<input type="hidden" value="${park.id}" /></span>
									<ul id="garageUl${park.id}">
										<c:forEach items="${garageList}" var="garage">
											<c:if test="${garage.parkId eq park.id}">
												<li class="public">${garage.title}<input type="hidden" value="${garage.id}" /></li>
											</c:if>
										</c:forEach>
									</ul>
								</li>
							</c:forEach> 
							</ul>
						</div>
					</div>
				</td>
				<td valign="top">
					<div class="userrightcontact" >
						<div class="titlebg">车位列表</div>
						<div class="searchdiv">
							<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="185"><search:input id="name" dataType="string" field="name" op="cn" inputClass="inputauto"/></td>
									<td><input name="Submit3" type="submit" class="search_cx" value="" onclick="doSearch()"/></td>
								</tr>
							</table>
						</div>
						<table id="list" class="jqGridList"><tr><td/></tr></table>
						<div id="pager"></div>
					</div>
				</td>
			</tr>
		</table>
		<div class="hackbox"></div>
	</div>
	<div class="buttondiv">
		<input name="Submit" type="button" class="savebtn" value="" onclick="submitSelectedPark()"/>
		<input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/>
	</div>
</body>
</html>

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
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery.treeview.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>

<script type="text/javascript">
  var selectedMeter={id:"${param.id}", name:"${param.name}",orderNo:"${param.orderNo}"};
  $(document).ready(function() {
	  initTip();
	  refreshTree();
	  initList();
	  initEleList();
	  //initGasList();
	  showSelectedMeter();
  });

  function showSelectedMeter() {
	  $("#selectedMeterDiv").text(selectedMeter.orderNo);
  }

  function refreshTree() {
		$.ajax({
		  "url" :	"<%=BaseAction.rootLocation %>/common/park!listBuilding.action",
		  type	:	"POST",
		  success: function(data){
			$("#tt").tree({
				animate: true,
				"data" : data.parkList,
				onDblClick : function(node) {
					$("#list").jqGrid('setGridParam',{page:1,postData:{filters:"{\"rules\":[{\"field\":\"buildingId\",\"op\":\"eq\",\"data\":\""+node.id+"\",\"dataType\":\"long\"}]}"}}).trigger('reloadGrid');
					$("#eleList").jqGrid('setGridParam',{page:1,postData:{filters:"{\"rules\":[{\"field\":\"buildingId\",\"op\":\"eq\",\"data\":\""+node.id+"\",\"dataType\":\"long\"}]}"}}).trigger('reloadGrid');
					$("#gasList").jqGrid('setGridParam',{page:1,postData:{filters:"{\"rules\":[{\"field\":\"buildingId\",\"op\":\"eq\",\"data\":\""+node.id+"\",\"dataType\":\"long\"}]}"}}).trigger('reloadGrid');
				}
			});
		  }
		});
  }
  
  function initList(){
		$("#list").jqGrid({
	    	url:"<%=basePath%>meter!waterList.action",
			datatype: 'json',
			prmNames: {search: "search"},
			jsonReader: {root:"root",repeatitems: false},
			colModel: [
				{label:'&nbsp;', sortable: false, width:20, name:'MY_ID', index:'MY_ID', align:"center"}, 
				{label:'表名称', width:50, name:'name', index:'name', align:"center"}, 
			    {label:'表编号', width:50, name:'orderNo', index:'orderNo', align:"center"}, 
			    {label:'楼宇', name:'building', index:'building', align:"center",hidden:true}
			    ],
			height: 227,
			rowNum: 10,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
			rowList: [],//用来调整表格显示的记录数
			recordtext: "共 {2} 条",	// 共字前是全角空格
			autowidth: true,//宽度自动
			multiselect: false,//是否可以多选
			multiboxonly: false,
			viewrecords: true,//是否显示总记录数
			loadui: 'block',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
			pager: '#page',//指定分页栏对象
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
                    var radioId = "<input id='"+id+"' type='radio' name='myOrderNo' value='" + id + "'/>";
					$(this).jqGrid('setRowData',id,{MY_ID:radioId});
				}
			},
			onSelectRow: function(id) {
                $($(this)).find("input[value="+id+"]").prop('checked',true);
            },
			loadComplete: function() {
				if (selectedMeter.id != "") {
					$("#list").find('#'+selectedMeter.id+' input').prop('checked',true); 
				}
				$("#list").find("input").click(function (e){
					setTimeout(checkSelectedMeter, 100);
				});
				$("#list").find("tr").click(function (e){
					setTimeout(checkSelectedMeter, 100);
				});
			},
			gridview: true
		});
	}
  
  
	function checkSelectedMeter() {
		var ids = $("#list").jqGrid("getDataIDs");
		$(ids).each(function (index, id) {
			if ($("#list").find('#'+id+' input').prop('checked')) {
				var rowData = $("#list").jqGrid("getRowData", id);
				selectedMeter = {id : id, name : rowData.name, orderNo : rowData.orderNo};
			}
		});
		showSelectedMeter();
	}  
	
	function initEleList(){
		$("#eleList").jqGrid({
	    	url:"<%=basePath%>meter!eleList.action",
			datatype: 'json',
			prmNames: {search: "search"},
			jsonReader: {root:"root",repeatitems: false},
			colModel: [
				{label:'&nbsp;', sortable: false, width:20, name:'MY_ID', index:'MY_ID', align:"center"}, 
				{label:'表名称', width:50, name:'name', index:'name', align:"center"}, 
			    {label:'表编号', width:50, name:'orderNo', index:'orderNo', align:"center"}, 
			    {label:'楼宇', name:'building', index:'building', align:"center",hidden:true}
			    ],
			height: 227,
			width:342,
			rowNum: 10,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
			rowList: [],//用来调整表格显示的记录数
			recordtext: "共 {2} 条",	// 共字前是全角空格
			multiselect: false,//是否可以多选
			multiboxonly: false,
			viewrecords: true,//是否显示总记录数
			loadui: 'block',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
			pager: '#elePage',//指定分页栏对象
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
                    var radioId = "<input id='"+id+"' type='radio' name='myOrderNo' value='" + id + "'/>";
					$(this).jqGrid('setRowData',id,{MY_ID:radioId});
				}
			},
			onSelectRow: function(id) {
                $($(this)).find("input[value="+id+"]").prop('checked',true);
            },
			loadComplete: function() {
				if (selectedMeter.id != "") {
					$("#eleList").find('#'+selectedMeter.id+' input').prop('checked',true); 
				}
				
				$("#eleList").find("input").click(function (e){
					setTimeout(checkSelectedMeter2, 100);
				});

				$("#eleList").find("tr").click(function (e){
					setTimeout(checkSelectedMeter2, 100);
				});
			},
			gridview: true
		});
	}
  
  
	function checkSelectedMeter2() {
		var ids = $("#eleList").jqGrid("getDataIDs");
		$(ids).each(function (index, id) {
			if ($("#eleList").find('#'+id+' input').prop('checked')) {
				var rowData = $("#eleList").jqGrid("getRowData", id);
				selectedMeter = {id : id, name : rowData.name, orderNo : rowData.orderNo};
			}
		});
		showSelectedMeter();
	}  
	
	function initGasList(){
		$("#gasList").jqGrid({
	    	url:"<%=basePath%>meter!gasList.action",
			datatype: 'json',
			prmNames: {search: "search"},
			jsonReader: {root:"root",repeatitems: false},
			colModel: [
				{label:'&nbsp;', sortable: false, width:20, name:'MY_ID', index:'MY_ID', align:"center"}, 
				{label:'表名称', width:50, name:'name', index:'name', align:"center"}, 
			    {label:'表编号', width:50, name:'orderNo', index:'orderNo', align:"center"}, 
			    {label:'楼宇', name:'building', index:'building', align:"center",hidden:true}
			    ],
			height: 227,
			width:342,
			rowNum: 10,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
			rowList: [],//用来调整表格显示的记录数
			recordtext: "共 {2} 条",	// 共字前是全角空格
			multiselect: false,//是否可以多选
			multiboxonly: false,
			viewrecords: true,//是否显示总记录数
			loadui: 'block',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
			pager: '#gasPage',//指定分页栏对象
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
                    var radioId = "<input id='"+id+"' type='radio' name='myOrderNo' value='" + id + "'/>";
					$(this).jqGrid('setRowData',id,{MY_ID:radioId});
				}
			},
			onSelectRow: function(id) {
                $($(this)).find("input[value="+id+"]").prop('checked',true);
            },
			loadComplete: function() {
				if (selectedMeter.id != "") {
					$("#gasList").find('#'+selectedMeter.id+' input').prop('checked',true); 
				}
				
				$("#gasList").find("input").click(function (e){
					setTimeout(checkSelectedMeter3, 100);
				});

				$("#gasList").find("tr").click(function (e){
					setTimeout(checkSelectedMeter3, 100);
				});
			},
			gridview: true
		});
	}
  
  
	function checkSelectedMeter3() {
		var ids = $("#gasList").jqGrid("getDataIDs");
		$(ids).each(function (index, id) {
			if ($("#gasList").find('#'+id+' input').prop('checked')) {
				var rowData = $("#gasList").jqGrid("getRowData", id);
				selectedMeter = {id : id, name : rowData.name, orderNo : rowData.orderNo};
			}
		});
		showSelectedMeter();
	}  
	
	
	function searchByWater() {
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:getSearchFilters()}}).trigger('reloadGrid');
	}
	
	function submitSelectedMeter() {
		if (selectedMeter.id == "") {
			showTip("未选择水电表", 1000);
			return;
		}
		getOpener().setSelectedMeter(selectedMeter);
		fb.end();
	}
</script>
</head>

<body>
<div class="basediv">
<table width="100%" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td valign="top"><!--userleft-->
<div class="userleftcontact">
<div class="titlebg">分类</div>
<!--treeviewdiv-->
	<div style="overflow:auto; width:130px; height:315px;">
		<ul id="tt">
		</ul>
	</div>
<!--//treeviewdiv-->
</div>
<!--//userleft-->
<!--userright--><!--userright--></td>
    <td valign="top">
	<div class="userrightcontact" style="height:312px;">
      <div class="apptab" id="tableid">
			<ul>
				<li class="apptabliover" onclick="tabSwitch('apptabli','apptabliover','tabswitch',0)">水表</li>
				<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',1)">电表</li>
			<!-- 	<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',2)">气表</li> -->
			</ul>
	</div>
   		
   	<div class="pm_msglist tabswitch" id="textname">
      <div class="userrightdivC" style="height:252px;">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="100%" valign="top">
			<!--msglist-->
			<div class="msglistall">
			</div>
			<table id="list" class="jqGridList"><tr><td/></tr></table>
			<div id="page"></div>
	        </td>
	      </tr>
	  </table>
      </div>
      </div>
      
     <div class="pm_msglist tabswitch" style="display: none;">
      <div class="userrightdivC" style="height:252px;">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="100%" valign="top">
			<!--msglist-->
			<div class="msglistall">
			</div>
			<table id="eleList" class="jqGridList"><tr><td/></tr></table>
			<div id="elePage"></div>
	        </td>
	      </tr>
	  </table>
      </div>
      </div>
      
      
      <div class="pm_msglist tabswitch" style="display: none;">
      <div class="userrightdivC" style="height:252px;">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="100%" valign="top">
			<!--msglist-->
			<div class="msglistall">
			</div>
			<table id="gasList" class="jqGridList"><tr><td/></tr></table>
			<div id="gasPage"></div>
	        </td>
	      </tr>
	  </table>
      </div>
      </div>
    </div>
    
    <div id="selectedMeterDiv" style=" background:#ffffe1; position:relative; left:3px; height:20px; line-height:20px; overflow-x:hidden; overflow-y:auto; border:1px solid #ccc;width:343px; margin:1px 6px 5px; padding:2px;">
	  	
    </div>
    </td>
  </tr>
</table>

<div class="hackbox"></div>
</div>
	<div class="buttondiv">
		<a href="javascript:void(0)" title="" class="btn_bg" onclick="submitSelectedMeter()"><span><img src="core/common/images/accept.png"/>确认</span></a>
		<a href="javascript:void(0)" title="" class="btn_bg" onclick="fb.end();"><span><img src="core/common/images/close.png"/>取消</span></a>
	</div>
</body>
</html>

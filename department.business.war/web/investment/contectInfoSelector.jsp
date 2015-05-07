<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction"%>
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
	var selectedContectInfos = new Array();
	<c:forEach var="selectedContectInfo" items="${selectedContectInfos}">
	selectedContectInfos[selectedContectInfos.length]={id:"${selectedContectInfo.id}", name:"${selectedContectInfo.name}"};
	</c:forEach>
	$(document).ready(function() {
		  initList();
		  initTip();
		  showSelectedContectInfo();
	});
	function initList(){
		$("#list").jqGrid({
	    	url:'<%=basePath%>investmentConfig!contectInfoList.action',
	    	datatype: 'json',
	    	mtype: 'POST',
			prmNames: {search: "search"},
			jsonReader: {root:"root",repeatitems: false},
			colModel: [
				{label:'序列', width:'30', name:'id', index:'id', align:"center"},
				{label:'姓名',width:'100' , name:'name', align:"center"}, 
				{label:'联系电话', width:'90' ,name:'phone', align:"center"}
			],
			height: 205,
			autowidth: true,//宽度自动
			multiselect: true,//是否可以多选
			viewrecords: false,//是否显示总记录数
			recordtext: "共 {2} 条",
			rownumbers: true,//显示行顺序号，从1开始递增。此列名为'rn'
			loadui: 'block',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
			pager: '#pager',//指定分页栏对象
			gridview: true,
			loadComplete: function() {
				$(selectedContectInfos).each(function (index, selectedContectInfo) {
					$("#list").find('#'+selectedContectInfo.id+' input[type=checkbox]').prop('checked',true); 
				});
				$("#list").find("input").click(function (e){
					setTimeout(checkSelectedContectInfos, 100);
				});
				$("#jqgh_list_cb").click(function (e) {
					setTimeout(checkSelectedContectInfos, 100);
				});
				$("#list").find("tr").click(function (e){
					setTimeout(checkSelectedContectInfos, 100);
				}).navGrid('#pager',{add: false, edit: false, del: false, search: false});
			},
		});
	}
	
	function showSelectedContectInfo() {
		  var selectedContectInfoText = "";
		  $(selectedContectInfos).each(function (index, selectedContectInfo){
			  selectedContectInfoText += selectedContectInfo.name + ",";
		  });
		  selectedContectInfoText=deleteLastCharWhenMatching(selectedContectInfoText,",");
		  $("#selectedContectInfoDiv").text(selectedContectInfoText);
	}
	function addSelectedContectInfo(user) {
		  var added = false;
		  $(selectedContectInfos).each(function (index, selectedContectInfo){
			  if (selectedContectInfo.id == user.id) {
				  added = true;
			  }
		  });
		  if (added == false) {
			  selectedContectInfos[selectedContectInfos.length] = user;
		  }
		  showSelectedContectInfo();
	  }
	  function removeSelectedContectInfo(user) {
		  var removeIndex = -1;
		  $(selectedContectInfos).each(function (index, selectedContectInfo){
			  if (selectedContectInfo.id == user.id) {
				  removeIndex = index;
			  }
		  });
		  
		  if (removeIndex != -1) {
			  selectedContectInfos.splice(removeIndex,1);
		  }
		  showSelectedContectInfo();
	  }
	  function checkSelectedContectInfos() {
			var ids = $("#list").jqGrid("getDataIDs");
			$(ids).each(function (index, id) {
				var rowData = $("#list").jqGrid("getRowData", id);
				if ($("#list").find('#'+id+' input[type=checkbox]').prop('checked')) {
					addSelectedContectInfo({"id":id, "name":rowData.name,"email":rowData.email});
				} else {
					removeSelectedContectInfo({"id":id, "name":rowData.name,"email":rowData.email});
				}
			});

		}  
	 	function doSearch(){
			search(getSearchFilters());
		}
		function search(filters){
			$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
		}
		
		function submitSelectedContact() {
			var ids = "";
			for(var i = 0; i < selectedContectInfos.length; i++){
				if(selectedContectInfos[i].id!=null && trim(selectedContectInfos[i].id) != ''){
					ids += selectedContectInfos[i].id+",";
				}
			}
			if(ids.length>0){
				ids = ids.substring(0,ids.length-1);
			}
			var id = $("#investmentId").val();
			$.post("<%=basePath%>investmentConfig!save.action?id="+id+"&ids="+ids,function(data){
				showTip(data.result.msg,2000);
	        	if(data.result.success){
	        		setTimeout(function(){getOpener().frames[0].location.reload();fb.end()},2000);
	        	}
			});
		}
</script>
</head>

<body>
	<input type="hidden" value="${id }" id="investmentId"/>
	<div class="basediv">
		<table width="100%" border="0" cellspacing="0" cellpadding="10">
		  <tr>
			<td valign="top">
				<div >
			      <div class="titlebg">线索列表</div>
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
			    <div id="selectedContectInfoDiv" style=" background:#ffffe1; position:relative; height:20px; line-height:20px; overflow-x:hidden; overflow-y:auto; border:1px solid #ccc;margin:1px 6px 5px; padding:2px;">
				  	
			    </div>
		    </td>
		  </tr>
		</table>
	</div>
	<div class="buttondiv">
		<a href="javascript:void(0)" title="" class="btn_bg" onclick="submitSelectedContact()"><span><img src="core/common/images/accept.png"/>确认</span></a>
		<a href="javascript:void(0)" title="" class="btn_bg" onclick="parent.fb.end();"><span><img src="core/common/images/close.png"/>取消</span></a>
	</div>
</body>
</html>

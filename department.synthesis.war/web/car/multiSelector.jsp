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
<base href="<%=BaseAction.rootLocation%>"/>
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
	var objType = "${objType}";
	var selectedCars = new Array();
	<c:forEach var="selectedCar" items="${selectedCars}">
	selectedCars[selectedCars.length]={id:"${selectedCar.id}", licenseNo:"${selectedCar.licenseNo}"};
	</c:forEach>
  $(document).ready(function() {
	  initList();
	  showSelectedCar();
  });

  function showSelectedCar() {
	  var selectedCarText = "";
	  $(selectedCars).each(function (index, selectedCar){
		  selectedCarText += selectedCar.licenseNo + ",";
	  });
	  selectedCarText=deleteLastCharWhenMatching(selectedCarText,",");
	  $("#selectedCarDiv").text(selectedCarText);
  }
  function addSelectedCar(user) {
	  var added = false;
	  $(selectedCars).each(function (index, selectedCar){
		  if (selectedCar.id == user.id) {
			  added = true;
		  }
	  });
	  if (added == false) {
		  selectedCars[selectedCars.length] = user;
	  }
	  showSelectedCar();
  }
  function removeSelectedCar(user) {
	  var removeIndex = -1;
	  $(selectedCars).each(function (index, selectedCar){
		  if (selectedCar.id == user.id) {
			  removeIndex = index;
		  }
	  });
	  
	  if (removeIndex != -1) {
		  selectedCars.splice(removeIndex,1);
	  }
	  showSelectedCar();
  }
  function initList(){
		$("#list").jqGrid({
	    	url:'<%=basePath %>car!list.action',
			datatype: 'json',
			prmNames: {search: "search"},
			jsonReader: {root:"root",repeatitems: false},
			colModel: [
				{label:'序列', width:30, name:'id', index:'id', align:"center"}, 
			    {label:'车牌', width:100, name:'licenseNo', index:'licenseNo', align:"center"}, 
			    {label:'厂家型号', name:'factoryModel', index:'factoryModel', align:"center"},
			    ],
			height: 203,
			rowNum: 10,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
			//rowList: [10],//用来调整表格显示的记录数
			autowidth: true,//宽度自动
			multiselect: true,//是否可以多选
			multiboxonly: false,
			recordtext: "共 {2} 条",	// 共字前是全角空格
			viewrecords: true,//是否显示总记录数
			rownumbers: false,//显示行顺序号，从1开始递增。此列名为'rn'
			loadui: 'block',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
			pager: '#pager',//指定分页栏对象
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			},
			loadComplete: function() {
				$(selectedCars).each(function (index, selectedCar) {
					$("#list").find('#'+selectedCar.id+' input[type=checkbox]').prop('checked',true); 
				});
				$("#list").find("input").click(function (e){
					setTimeout(checkSelectedCars, 100);
				});
				$("#jqgh_list_cb").click(function (e) {
					setTimeout(checkSelectedCars, 100);
				});
				$("#list").find("tr").click(function (e){
					setTimeout(checkSelectedCars, 100);
				});
			},
			gridview: true
		});
	}
	function checkSelectedCars() {
		var ids = $("#list").jqGrid("getDataIDs");
		$(ids).each(function (index, id) {
			var rowData = $("#list").jqGrid("getRowData", id);
			if ($("#list").find('#'+id+' input[type=checkbox]').prop('checked')) {
				addSelectedCar({"id":id, "licenseNo":rowData.licenseNo});
			} else {
				removeSelectedCar({"id":id, "licenseNo":rowData.licenseNo});
			}
		});

	}  
	function searchByCarname() {
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:getSearchFilters()}}).trigger('reloadGrid');
	}
	
	function submitSelectedCar() {
		if($("#type").val()==1){
			parent.setSelectedCars(selectedCars);
		}else{
			if(objType > 0){
				getOpener().setSelectedCars(selectedCars,objType);
			}else{
				getOpener().setSelectedCars(selectedCars);
			}
		}
		fb.end();
	}
</script>
</head>

<body>
<input type="hidden" id="type" value="${type }"/>
<div class="basediv">
<table width="100%" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td valign="top">
	<div style="height:312px;">
      <div class="titlebg">车辆列表</div>
      <!--searchdiv-->
      <div class="searchdiv">
        <form id="form2" name="form2" method="post" action="">
          <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="185"><label>
                <search:input id="licenseNo" dataType="string" field="licenseNo" op="cn" inputClass="inputauto"/>
              </label></td>
              <td><label> &nbsp;
                    <input name="Submit3" type="button" class="search_cx" value="" onclick="searchByCarname()"/>
              </label></td>
            </tr>
          </table>
        </form>
      </div>
      <!--//searchdiv-->
      <!--userrightdiv-->
      <div class="userrightdivC" style="height:230px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" valign="top">
		<!--msglist-->
		<div class="msglistall">
		</div>
		<table id="list" class="jqGridList"><tr><td/></tr></table>
		<div id="pager"></div>
        </td>
      </tr>
  </table>
      </div>
      <!--userrightdiv-->
    </div>
    <div id="selectedCarDiv" style=" background:#ffffe1; position:relative; left:3px; height:20px; line-height:20px; overflow-x:hidden; overflow-y:auto; border:1px solid #ccc;width:343px; margin:1px 6px 5px; padding:2px;">
	  	
    </div>
    </td>
  </tr>
</table>

<div class="hackbox"></div>
</div>
	<div class="buttondiv">
		<a href="javascript:void(0)" title="" class="btn_bg" onclick="submitSelectedCar()"><span><img src="core/common/images/accept.png">确认</span></a>
		<a href="javascript:void(0)" title="" class="btn_bg" onclick="parent.fb.end();"><span><img src="core/common/images/close.png">取消</span></a>
	</div>
</body>
</html>

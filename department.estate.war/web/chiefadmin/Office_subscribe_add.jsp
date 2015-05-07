<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.wiiy.core.entity.User"%>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@page import="com.wiiy.estate.entity.SupplyPurchaseForm"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#browser").tree({
			animate: true,
			lines: true,
			onClick: function(node){
				if($(this).tree("isLeaf",node.target)){
					var categoryId = $(node.target).find("input").val();
					if(categoryId==null){
						$("#list").setGridParam({url:'<%=basePath%>supply!listAll.action',postData:{filters:''}}).trigger('reloadGrid'); 
					}else{
						$("#list").setGridParam({url:'<%=basePath%>supply!loadSupplyByCategoryId.action?id='+categoryId,postData:{filters:''}}).trigger('reloadGrid');
					}
				}
			}
		});
		$(".emailtop").css("padding-bottom","0px");
   		$(".emailtop").css("border-top","#c9c9c9 1px solid");
		initList();
		initForm();
		initTip();
		//initList2();
		generateCode();
		jqGridResizeRegister("list");
	});
	function loadAllSupply(){
		$("#list").setGridParam({url:'<%=basePath%>supply!listAll.action',postData:{filters:''}}).trigger('reloadGrid'); 
	}
	function initForm(){
		$("#form1").validate({
			rules:{
				"supplyPurchaseForm.name":"required",
				"requisitioner":"required",
				"supplyPurchaseForm.applyTime":"required"
			},
			messages: {
				"supplyPurchaseForm.name":"请填写申请表名称",
				"requisitioner":"请选择申请人",
				"supplyPurchaseForm.applyTime":"请选择申请时间"
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
				if(!submits()) return;
				$(form).ajaxSubmit({
			        dataType: 'json',
			        success: function(data){
		        		showTip(data.result.msg,2000);
			        	if(data.result.success){
			        		setTimeout("getOpener().reloadList();parent.fb.end()", 2000);
			        	}
			        } 
			    });
			
			}
		});
	}
	
	function checkSub(){
		var prices = $("#tabList").find("input[name='priceList']");
		var amounts = $("#tabList").find("input[name='amountList']");
		//var usages = $("#tabList").find("input[name='usageList']");
		var name = null;
		var num = 0;
		for(var i = 0; i< prices.length;i++){
			var p = trim($(prices).eq(i).val());
			num = i;
			if(p == ''){
				name = "正确的单价";
				showTips(prices,num,name);
				return false;
			}
			p = trim($(amounts).eq(i).val());
			if(p == ''){
				name = "采购的数量";
				showTips(amounts,num,name);
				return false;
			}
		/* 	p = trim($(usages).eq(i).val());
			if(p == ''){
				name = "物品的用途";
				showTips(usages,num,name);
				return false;
			} */
		}
		return true;
	}
	
	function showTips(obj,num,name){
		showTip("请输入"+name,2000);
		$(obj).eq(num).focus();
	}
	
	function submits(){
		if($("#tabList").children().size() <= 1){
			showTip("未选择商品",2000);
			return false;
		}
		return checkSub();
	}
	
		
	
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").setGridParam({postData:{filters:filters}}).trigger('reloadGrid');
	}
	function initList(){
		var height=$(".userrightcontact").height()-136;
		var width = $(this).width()-181;
		$("#list").jqGrid({
			url:'<%=basePath%>supply!listAll.action',
			datatype: 'json',
			mtype: 'POST',
			prmNames: {search: "search"},
			jsonReader: {root:"root",repeatitems: false},
			colModel: [
						{label:'ID', name:'id', index:'id', align:"center",hidden:true},
						{label:'所属分类', name:'categoryId', index:'categoryId', align:"center",hidden:true},
						{label:'商品名称', name:'name',width:152, index:'name', align:"center"},
						{label:'单价金额', name:'price',width:122, align:"center"},
						{label:'计量单位', name:'unit',width:122, align:"center"},
						{label:'库存', name:'stock',width:123, align:"center"},
						{label:'规格', name:'spec', hidden:true, align:"center"},
			],
			height: height,
			width:width,
			rowNum: 4,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
    		rowList: [10,20,30],//用来调整表格显示的记录数
    		multiselect: true,//是否可以多选
    		viewrecords: true,//是否显示总记录数
    		rownumbers: true,//显示行顺序号，从1开始递增。此列名为'rn'
		}).navGrid('#pager',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager',{
		    caption : "列选择",
		    title : "选择要显示的列",
		    onClickButton : function(){
		        $(this).jqGrid('columnChooser');
		    }
		});
	}
	function appendToHtml(id){
		var idNums = 1;
		if($("#tabList").children().size() > 1){
			idNums=$("#tabList").children().size();
			
		}
		var rowData = $("#list").jqGrid("getRowData", id);
		var Id=rowData.id;
		var names=rowData.name;
		var units=rowData.unit;
		var amounts=rowData.stock;
		var prices=rowData.price;
		var specs=rowData.spec;
		var html = "<tr>"
		html += '<td><input type=\"hidden\" name=\"supplyIdList\" value=\"'+Id+'\" /></td>'
		html += '<td>'+idNums+'</td>'
		html += '<td>'+names+'</td>'
		html += '<td><input onkeyup=\"if(isNaN(value))execCommand(\'undo\')\" onafterpaste=\"if(isNaN(value))execCommand(\'undo\')\" style=\"width:75px;\" class=\"inputauto\" name=\"priceList\" type=\"text\" value=\"'+prices+'\" /></td>'
		html += '<td><input onkeyup=\"if(isNaN(value))execCommand(\'undo\')\" onafterpaste=\"if(isNaN(value))execCommand(\'undo\')\" style=\"width:75px;\" class=\"inputauto\" name=\"amountList\" type=\"text\" value=\"\" /></td>';
		html += '<td>'+units+'</td>';
		html += '<td><input style=\"width:115px;\" class=\"inputauto\" name=\"usageList\" type=\"text\" value=\"\"/></td>';
		html += '<td align=\"center\"><img width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById(this);\" src=\"core/common/images/del.gif\" /></td>'	
		html += "</tr>"
		$("#tabList").html($("#tabList").html()+html);
	}
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	 function addList(obj){
		 var ids = $("#list").jqGrid("getDataIDs");
		 $(ids).each(function (index, id) {
			 if ($("#list").find('#'+id+' input').prop('checked')) {
				 var rowData = $("#list").jqGrid("getRowData", id);
				 var newId=rowData.id;
				 if($("#tabList").children().size() > 1){
					 var idss = $("#tabList").find("input[name='supplyIdList']");
					 var canAppend = true;
					  $(idss).each(function () {
						  var ids_id = $(this).val();
						  if(newId == ids_id){
							  canAppend = false;
						  }
						}); 
					 if(canAppend) appendToHtml(newId);
				 }else{
					 appendToHtml(newId);
				 }
			 }
		});
	}
	function setSelectedUser(user){
		$("#requisitionerId").val(user.id);
		$("#requisitioner").val(user.realName);
	}
	function deleteById(obj){
		$(obj).parent().parent().parent().remove();
		var count = $("#tabList").children().size() -1;
		if(count > 0){
			var children = $("#tabList").children();
			for(var i = 0;i<count;i++){
				$(children).eq(i+1).children().eq(0).children().eq(1).html(i+1);
			}
		}
	}
	function generateCode(){
		$.post("<%=basePath%>supplyPurchaseForm!generateCode.action",function(data){
			if(data.result.success){
				$("#code").val(data.result.value);
			}
		});
	}
</script>

</head>

<body>
<form id="form1" name="form1" method="post" action="<%=basePath%>supplyPurchaseConfig!save.action">
<!-- <input type="hidden" id="categoryId" name="categoryId"/> -->
<div class="basediv">
		<table width="100%" border="0" cellspacing="4" cellpadding="4">
			<tr>
				<td valign="top">
					<div class="userleftcontact" style="width:147px;height: 245px;" id="resizable">
						<div class="titlebg">办公用品分类</div>
						<div class="treeviewdiv" style="overflow-Y:auto;height: 217px;" id="treeviewdiv">
							<ul id="browser" class="filetree">
								<li>
									<span class="file" onclick="loadAllSupply()">所有办公用品</span>
								</li>
								<c:forEach items="${supplyCategorys}" var="supplyCategory">
								<li state="closed" id="supplyCategoryLi${supplyCategory.id}">
								<span class="folder public" >${supplyCategory.name}<input type="hidden" value="${supplyCategory.id}" /></span>
									<ul id="supplyLabelUl${supplyCategory.id}">
										<c:forEach items="${supplyCategoryLabel}" var="supplyCategoryLabel">
											<c:if test="${supplyCategoryLabel.parentId eq supplyCategory.id}">
												<li class="public">${supplyCategoryLabel.name}<input type="hidden" value="${supplyCategoryLabel.id}" /></li>
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
					<div class="userrightcontact" style="width:599px;height: 245px;">
						<div class="titlebg">办公用品列表</div>
						<div class="emailtop">
							<div class="leftemail">
								<ul>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="addList(this)">
									<span><img src="core/common/images/action_add.png" />增加</span></li>
								</ul>
							</div>
						</div>
						<div class="searchdiv" >
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
</div>
 <div class="basediv">
	<div class="titlebg" >商品申购列表</div>
	<div id="purchaseForm"><table  width="100%" border="0" cellpadding="0" cellspacing="0" >
 		
 		<tr>
 		   	<td class="layertdleft100"><span class="psred">*</span>申请表名称：</td>
			<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td ><input style="width:85px" id="code" name="supplyPurchaseForm.name" type="text" class="inputauto" /></td>
							<td><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
						</tr>
					</table>
			</td>
 		   <td class="layertdleft100"><span class="psred">*</span>申购人：</td>
      	   <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="100%">
	          	<input id="requisitionerId" name="supplyPurchaseForm.requisitionerId" readonly="readonly" type="hidden"/>
	          	<input id="requisitioner" name="requisitioner" readonly="readonly" type="text" class="inputauto" onclick="fbStart('选择申请人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	          <td width="20" align="center">
	          	<img src="core/common/images/outdiv.gif" width="20" height="22" style="position: relative;left:-4px;" onclick="fbStart('选择申请人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	        </tr>
      </table></td>
      <td class="layertdleft100" ><span class="psred">*</span>申购日期：</td>
      <td class="layerright" >
	     		<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td width="100%">
			          	<input id="applyTime" name="supplyPurchaseForm.applyTime" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('applyTime')"/>
			          </td>
			          <td width="20" align="center">
			          	<img style="position:relative; left:-1px;" onclick="showCalendar('applyTime')" src="core/common/images/timeico.gif" width="20" height="22" />
			          </td>
			        </tr>
			    </table>
		    </td>
 		</tr>
		</table>
	</div>
	<div id="list2" style="width:100%;height: 139px; overflow:auto; background-color: #f4f4f4;" >
		<table id="tabList" width="100%" border="0" cellpadding="0" cellspacing="0" >
			<tr class="titlebg">
			    <td style="width:20px;"></td> 
				<td style="width:50px;">序号</td>		
				<td style="width:100px;">名称</td>		
				<td style="width:80px;">单价金额(￥)</td>		
				<td style="width:80px;">数量</td>		
				<td style="width:80px;">单位</td>		
				<td style="width:120px;">物品用途</td>
				<td style="width:20px;" align="center"></td>		
			</tr>
		</table>
</div>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="fb.end();"/></label>
 </div>
</form>
</body>
</html>

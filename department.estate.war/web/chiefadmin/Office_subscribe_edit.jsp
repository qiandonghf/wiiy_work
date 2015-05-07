<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.wiiy.core.entity.User"%>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@page import="com.wiiy.estate.entity.SupplyPurchaseForm"%>
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
		initTip();
		initList();
		initForm();
	//	jqGridResizeRegister("list");
	});
	function initForm(){
		$("#form1").validate({
			rules:{
				"supplyPurchaseForm.name":"required",
				"applyTime":"required",
				"requisitioner":"required"
			},
			messages: {
				"supplyPurchaseForm.name":"请填写申请表名",
				"applyTime":"请选择申请时间",
				"requisitioner":"请选择申请人"
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
				$("#supplyList").val(getList());
				$('#form1').ajaxSubmit({ 
			        dataType: 'json',		        
			        success: function(data){
		        		showTip(data.result.msg,2000);
			        	if(data.result.success){
			        		setTimeout("parent.fb.end();getOpener().reloadList();", 2000);
			        	}
			        } 
			    });
			}
		});
	}	
	
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").setGridParam({postData:{filters:filters}}).trigger('reloadGrid');
	}
	function initList(){
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
			shrinkToFit: false,
			height: 75,
			width:594,
			multiselect: true,
/* 			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					content += "<img src=\"core/common/images/emailadd.gif\" width=\"14\" height=\"14\" alt=\"增加\" title=\"增加\" onclick=\"initAppend();\"  /> ";
					$(this).jqGrid('setRowData',id,{manager:content});
				}
			} */
		}).navGrid('#pager',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager',{
		    caption : "列选择",
		    title : "选择要显示的列",
		    onClickButton : function(){
		        $(this).jqGrid('columnChooser');
		    }
		});
	}
	
	function removeTable(){
		var t=document.getElementById("list2");
		t.firstChild.removeNode(true);
	}
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function setSelectedUser(user){
		$("#requisitionerId").val(user.id);
		$("#requisitioner").val(user.realName);
	}
	function generateCode(){
		$.post("<%=basePath%>supplyPurchaseForm!generateCode.action",function(data){
			if(data.result.success){
				$("#code").val(data.result.value);
			}
		});
	}
	function getList(){
		var topicPaths = '[';
		var obj = $("#tabList").children().children();
		$(obj).each(function (i){
			var newSupplyIds=$(obj).eq(i).find("input[name='supplyIdList']").val()
			var newPrice=$(obj).eq(i).find("input[name='priceList']").val()
			var newAmount=$(obj).eq(i).find("input[name='amountList']").val()
			var newUsages=$(obj).eq(i).find("input[name='usageList']").val()
			var str = "{\"supplyIds\" : \""+newSupplyIds+"\",";
			str += "\"price\" : \""+newPrice+"\",";
			str += "\"amount\" : \""+newAmount+"\",";
			str += "\"usages\" : \""+newUsages+"\"}";
			topicPaths += str+",";
		})
		 if(topicPaths.lastIndexOf(",") == topicPaths.length-1)
			topicPaths = topicPaths.substr(0,topicPaths.length-1);
		return topicPaths+"]";
	}
	
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=basePath%>supplyPurchaseConfig!update.action">
<input type="hidden" name="supplyPurchaseForm.id" value="${result.value.id }"/>
<input type="hidden" name="supplyPurchaseForm.requisitioner.id" value="${result.value.requisitioner.id }"/>
<input type="hidden" id="supplyList" name="supplyList" value=""/>

<div class="basediv">
	<div class="titlebg" >商品申购列表</div>
	 <div id="purchaseForm"><table  width="100%" border="0" cellpadding="0" cellspacing="0" >
 		<tr>
				<td class="layertdleft100"><span class="psred">*</span>申请表名称：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input id="code" name="supplyPurchaseForm.name" type="text" class="inputauto" value="${result.value.name }" /></td>
							<td><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
						</tr>
					</table>
				</td>
 		   <td class="layertdleft100"><span class="psred">*</span>申购人：</td>
      	   <td class="layerright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="100%">
	          	<input id="requisitionerId" name="supplyPurchaseForm.requisitionerId" readonly="readonly" value="${result.value.requisitionerId }" type="hidden"/>
	          	<input id="requisitioner" name="requisitioner" value="${result.value.requisitioner.realName }" readonly="readonly" type="text" class="inputauto" onclick="fbStart('选择申请人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	          <td width="20" align="center">
	          	<img src="core/common/images/outdiv.gif" width="20" height="22" style="position: relative;left:-4px;" onclick="fbStart('选择申请人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);"/></td>
	        </tr>
      </table></td> 
      <td class="layertdleft100" ><span class="psred">*</span>申购日期：</td>
	      	<td class="layerright" >
	      		<table width="143" border="0" cellspacing="0" cellpadding="0">
		        	<tbody>
			          <tr>
			            <td>
			            	<input id="applyTime" name="supplyPurchaseForm.applyTime"
			            	value='<fmt:formatDate value="${result.value.applyTime }" pattern="yyyy-MM-dd" />' readonly="readonly" type="text" class="inputauto" onclick="showCalendar('applyTime')"/>
			            </td>
	          			<td width="20" align="center">
	          				<img style="position:relative; left:-1px;" onclick="showCalendar('applyTime')" src="core/common/images/timeico.gif" width="20" height="22" />
	          			</td>
			          </tr>
		        	</tbody>
	      		</table>
	      	</td>
 		</tr>
		</table>
	</div>
	<div  class="titlebg"><table width="96%" border="0" cellpadding="0" cellspacing="0" >
			<tr>
				<td width="10%" style="text-align:center;">序号</td>		
				<td width="20%" style="text-align:center;">名称</td>		
				<td width="15%" style="text-align:center;">单价金额(￥)</td>		
				<td width="15%" style="text-align:center;">数量</td>		
				<td width="15%" style="text-align:center;">单位</td>		
				<td width="25%" style="text-align:center;">物品用途</td>
			</tr>
		</table>
	</div>
	<div style="height: 139px;width:100%; overflow:auto; background-color: #f4f4f4;">	
		<table id="tabList" border="0" cellpadding="0" cellspacing="0" >
		<c:forEach items="${configList}" var="supplys" varStatus="i">
	 	<tr>
			<td><input type="hidden" value="${supplys.supplyPurchaseId }" name="supplyIdList" /></td>	
			<td style="text-align:center;" width="10%">${i.count }</td>	
			<td style="width:20%;text-align:center;">${supplys.supplyPurchase.supply.name}</td>		
			<td width="15%" style="text-align:center;"><input style="width:83%;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" name="priceList" type="text" class="inputauto" value="${supplys.supplyPurchase.price }"/></td>
			<td width="15%" style="text-align:center;"><input style="width:83%;" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" name="amountList" type="text" class="inputauto" value="${supplys.supplyPurchase.amount}"/></td>		
			<td style="text-align:center;" width="15%">${supplys.supplyPurchase.supply.unit}</td>		
			<td width="25%" style="text-align:center;"><input style="width:95%;" style="width:93%;" name="usageList" type="text" class="inputauto" value="${supplys.supplyPurchase.usages}"/></td>
		</tr>
		</c:forEach>
		</table>
	</div>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit"  class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="fb.end();"/></label>
 </div>
</form>
</body>
</html>

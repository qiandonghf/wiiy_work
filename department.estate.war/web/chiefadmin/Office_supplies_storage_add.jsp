<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
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
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript">
	var mode = "SUPPLYS";
	$(document).ready(function() {
		initTip();
		checkMode();
		$("#stockInModeId").change(function (){
			checkMode();
		});
		initForm();
	});
	function checkMode(){
		$("input[name='supplyStockIn.mode']").each(function (){
			if($(this).attr("checked")){
				if($(this).val()=='SUPPLYS'){
					mode = "SUPPLYS";
					$("#supplyIds").show();
					$("#formsIds").val(""); 
					$("#supplyPurchaseFormId").val("");
					$("#formsIds").hide(); 
				}
				if($(this).val()=='FORMS'){
					mode = "FORMS";
					$("#formsIds").show();
					$("#supplyIds").val(""); 
					$("#supplyId").val("");
					$("#supplyIds").hide(); 
				}
			}
			
		});
	}
	function notNulls(id,msg){
		if(getOb(id)!=null && getOb(id).value!=''){
			return true;
		} else {
			showTip("请输入"+msg);
			focus(id);
			return false;
		}
	}
	function initForm(){
		$("#form1").validate({
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
				showTip("入库保存成功",2000);
			}
		});
	}
	function submits(){
		if($("#tabList").children().size() <= 1){
			showTip("未选择商品",2000);
			return false;
		}
		return checkSub();
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
			var a = trim($(amounts).eq(i).val());
			if(a == ''){
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
	function checkAmount(obj){
		//先把非数字的都替换掉，除了数字和.   
	    obj.value = obj.value.replace(/[^\d.]/g,"");   
	    //必须保证第一个为数字而不是.   
	    obj.value = obj.value.replace(/^\./g,"");   
	    //保证只有出现一个.而没有多个.   
	    obj.value = obj.value.replace(/\.{2,}/g,".");   
	    //保证.只出现一次，而不能出现两次以上   
	    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
	}
	function setSelectedSupply(supply){
		$("#supplyId").val(supply.id);
		$("#supplyName").val(supply.name);
		appendToHtml(supply);
	}
	
	function setSelectedForm(supplyForm){
		$("#supplyPurchaseFormId").val(supplyForm.id);
		$("#supplyPurchaseFormName").val(supplyForm.name);
		appendToHtml(supplyForm,"supplyForms");
	}
	
	function appendToHtml(obj,type){
		var idNums = 1;
		var id=obj.id;
		var names=obj.name;
		var units=obj.unit;
		var amounts=obj.stock;
		var prices=obj.price;
		var usages=obj.usage;
		if(type=="supplyForms"){
			$.ajax({
				   type		: "POST",
				   url		: "<%=BaseAction.rootLocation%>/department.estate/supplyPurchaseConfig!searchSupply.action?id="+id,
				   success	: function(data){
					   if(data.result!=null){
					   		var num=data.result.value;
						 for(var i=0;i<num.length;i++){
							id=num[i].id;
							names=num[i].supply;
							units=num[i].unit;
							amounts=num[i].amount;
							prices=num[i].price;
							usages=num[i].usage;
							if($("#tabList").children().size() > 1){
								idNums=$("#tabList").children().size();
							}
							
							var html = "<tr>"
							html += '<td><input type=\"hidden\" name=\"supplyIdList\" value=\"'+id+'\" /></td>'
							html += '<td>'+idNums+'</td>'
							html += '<td>'+names+'</td>'
							html += '<td><input onkeyup=\"if(isNaN(value))execCommand(\'undo\')\" onafterpaste=\"if(isNaN(value))execCommand(\'undo\')\" style=\"width:75px;\" class=\"inputauto\" name=\"priceList\" type=\"text\" value=\"'+prices+'\" /></td>'
							html += '<td><input onkeyup=\"if(isNaN(value))execCommand(\'undo\')\" onafterpaste=\"if(isNaN(value))execCommand(\'undo\')\" style=\"width:75px;\" class=\"inputauto\" name=\"amountList\" type=\"text\" value=\"'+amounts+'\" /></td>';
							html += '<td>'+units+'</td>';
							html += '<td><input style=\"width:115px;\" class=\"inputauto\" name=\"usageList\" type=\"text\" value=\"'+usages+'\"/></td>';
							html += '<td align=\"center\"><img width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById(this);\" src=\"core/common/images/del.gif\" /></td>'	
							html += "</tr>"
							$("#tabList").html($("#tabList").html()+html);
						} 
					   }
				   }
				});
		}else{
			var html = "<tr>"
				html += '<td><input type=\"hidden\" name=\"supplyIdList\" value=\"'+id+'\" /></td>'
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
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=basePath %>supplyStockIn!save.action">
<!--basediv-->
<div class="basediv">
	<!--titlebg-->
	<!--//titlebg-->
    <!--divlay-->
<div class="divlays" style="margin:0px;">
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  	<tr>
		<td class="layertdleft100"><span class="psred">*</span>入库方式</td>
		<td class="layerright"  id="stockInModeId"><enum:radio name="supplyStockIn.mode"  type="com.wiiy.estate.preferences.enums.StockInModeEnum" checked="SUPPLYS" /></td>
	</tr>
    <tr id="formsIds"  >
      <td class="layertdleft100"><span class="psred">*</span>申购详单：</td>
      <td class="layerright">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td >
	          	  <input id="supplyPurchaseFormId" name="supplyStockIn.supplyPurchaseFormId" type="hidden"/>
	          	  <input id="supplyPurchaseFormName" readonly="readonly" class="inputauto"  onclick="fbStart('选择申请详单','<%=basePath %>supplyStockIn!select.action',520,308);"/>
	          </td>
	          <td width="20" align="center">
	          	<img width="20" height="22"  style="position: relative;left:-3px;" onclick="fbStart('选择申请详单','<%=basePath %>supplyStockIn!select.action',520,308);" src="core/common/images/outdiv.gif"/>
	          </td>
	       </tr>
	      </table>
      </td>
    </tr>
    <tr id="supplyIds">
      <td class="layertdleft100"><span class="psred">*</span>商品名称：</td>
      <td class="layerright">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td>
	          	  <input id="supplyId" name="supplyStockIn.supplyId" type="hidden"/>
	          	  <input id="supplyName" readonly="readonly" class="inputauto"  onclick="fbStart('选择商品','<%=basePath %>supply!select.action',721,400);"/>
	          </td>
	          <td width="20" align="center">
	          	<img width="20" height="22"  style="position: relative;left:-3px;" onclick="fbStart('选择商品','<%=basePath %>supply!select.action',721,400);" src="core/common/images/outdiv.gif"/>
	          </td>
	       </tr>
	      </table>
      </td>
    </tr>
    </table>
</div>
<div id="list2" style="width:100%;height: 239px; overflow:auto; background-color: #f4f4f4;" >
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

<!--//divlay-->
</div>
<!--//basediv-->
<!--basediv-->
<!--//basediv-->
<div class="buttondiv">
  <label>  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
</div>
</form>
</body>
</html>

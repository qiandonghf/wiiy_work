<%@page import="com.wiiy.estate.activator.EstateActivator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wiiy.common.preferences.enums.BusinessFeeEnum"%>
<%@ page import="com.wiiy.common.preferences.enums.ContractTypeEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String fees = "";
for(BusinessFeeEnum fee : BusinessFeeEnum.values()){
	fees += fee.name() + ":" + fee.getTitle() + ",";
}
if(fees.endsWith(",")) fees = fees.substring(0, fees.length()-1);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	<script type="text/javascript">
		$(function(){
			initTip();
			initForm();
			initDate();
			generateCode();
			initRoom();
		});
		function initDate(){
			<%Calendar c = Calendar.getInstance();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = s.format(c.getTime());	
			c.add(Calendar.YEAR, 1);
			c.add(Calendar.DATE, -1);
			String endTime = s.format(c.getTime());
			%>
			var startDate = '<%=startTime%>';
			var endDate = '<%=endTime%>';
			$("#startDate").val(startDate);
			$("#signDate").val(startDate);
			$("#endDate").val(endDate);
			$("#receiveDate").val(startDate);
			
		}
		
		function selectUser(){
			fbStart('选择负责人','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
		}
		function setSelectedUser(contect){
			$("#managerName").val(contect.name);
			$("#managerId").val(contect.id);
		}
		function selectCustomer(){
			fbStart('选择企业','<%=BaseAction.rootLocation %>/department.business/customer!select.action',520,400);
		}
		function setSelectedCustomer(customer){
			$("#customerId").val(customer.id);
			$("#customerName").val(customer.name);
		}
		function checkRoomExist(id){
			var exist = false;
			$(".roomId").each(function(){
				if($(this).val()==id) exist = true;
			});
			return exist;
		}
		
		function appendBillPlanRent(obj){
			var tr = $("<tr></tr>");
			$("#noPlan").remove();
			var roomName = $("<input />",{name:"planRoomName",type:"hidden",value:obj.get("roomName")}).addClass("planRoomName");
			var roomId = $("<input />",{name:"planRoomId",type:"hidden",value:obj.get("roomId")}).addClass("planRoomId");
			var feeType = $("<input />",{name:"feeType",type:"hidden",value:obj.get("feeType")}).addClass("feeType");
			var planFee = $("<input />",{name:"planFee",type:"hidden",value:obj.get("planFee")}).addClass("planFee");
			var realFee = $("<input />",{name:"realFee",type:"hidden",value:obj.get("realFee")}).addClass("realFee");
			var startDate = $("<input />",{name:"planStartDate",type:"hidden",value:obj.get("startDate")}).addClass("planStartDate");
			var endDate = $("<input />",{name:"planEndDate",type:"hidden",value:obj.get("endDate")}).addClass("planEndDate");
			var planPayDate = $("<input />",{name:"planPayDate",type:"hidden",value:obj.get("planPayDate")}).addClass("planPayDate");
			var status = $("<input />",{name:"status",type:"hidden",value:obj.get("status")}).addClass("planStatus");
			var memo = $("<input />",{name:"planMemo",type:"hidden",value:obj.get("memo")}).addClass("planMemo");

			
			/* ESTATE_WY("物业费"), 
			ESTATE_GGNH("公共能耗费"),
			ESTATE_LJQY("垃圾消运费"),
			ESTATE_SF("水费"),
			ESTATE_DF("电费"),
			ESTATE_CWF("物业车位费"),
			ESTATE_YJF("物业预缴费"); */
			var typeName = "";
			if(obj.get("feeType") == "ESTATE_WY"){
				typeName = "物业费";
			}else if(obj.get("feeType") == "ESTATE_GGNH"){
				typeName = "公共能耗费";
			}else if(obj.get("feeType") == "ESTATE_LJQY"){
				typeName = "垃圾消运费";
			}else if(obj.get("feeType") == "ESTATE_SF"){
				typeName = "水费";
			}else if(obj.get("feeType") == "ESTATE_DF"){
				typeName = "电费";
			}else if(obj.get("feeType") == "ESTATE_CWF"){
				typeName = "物业车位费";
			}else if(obj.get("feeType") == "ESTATE_YJF"){
				typeName = "物业预缴费";
			}
			var feeType2 = $("<td></td>").addClass("lefttd").append(typeName);
			var planFee2 = $("<td></td>").addClass("centertd").append(obj.get("planFee"));
			var planPayDate2 = $("<td></td>").addClass("centertd").append(obj.get("planPayDate"));
			var date = $("<td></td>").addClass("centertd").append(obj.get("startDate")).append("至"+obj.get("endDate"));
			
			var a = $("<a></a>",{href:"javascript:void(0)",click:function(){deletePlan($(this).parent().parent());}}).append($("<img src='core/common/images/del.gif' title='删除'/>"));
			var op = $("<td></td>").addClass("centertd").append(a);
			
			tr.append(feeType2).append(planFee2).append(planPayDate2).append(date).append(op);
			tr.append(roomName).append(roomId).append(feeType).append(planFee).append(realFee).append(startDate).append(endDate).append(planPayDate).append(status).append(memo);
			$("#planListTBody").append(tr);
		}
		
		function appendRoom(obj){
			var tr = $("<tr></tr>");
			if(checkRoomExist(obj.get("roomId"))){
				alert("房间已在租凭列表中！");
				return false;
			}
			$("#nonData").remove();
			var rentPrice = $("<input />",{name:"rentPrice",type:"hidden",value:obj.get("rentPrice")}).addClass("rentPrice").addClass("rent"+obj.get("roomId"));
			var rentPriceUnit = $("<input />",{name:"rentPriceUnit",type:"hidden",value:obj.get("rentPriceUnit")}).addClass("rentPriceUnit");
			var rebate = $("<input />",{name:"rebate",type:"hidden",value:obj.get("rebate")}).addClass("rebate");
			var rebateRuleId = $("<input />",{name:"rebateRuleId",type:"hidden",value:obj.get("rebateRuleId")}).addClass("rebateRuleId");
			var memo = $("<input />",{name:"memo",type:"hidden",value:obj.get("memo")}).addClass("memo");
			
			var id = $("<input />",{name:"roomId",type:"hidden",value:obj.get("roomId")}).addClass("roomId");
			var name = $("<input />",{name:"roomName",type:"hidden",value:obj.get("roomName")}).addClass("name").addClass("name"+obj.get("roomId"));
			var roomName = $("<td></td>").addClass("lefttd").append(obj.get("roomName")).append(id).append(name);
			
			var area = $("<input />",{name:"roomArea",type:"hidden",value:obj.get("roomArea")}).addClass("roomArea").addClass("area"+obj.get("roomId"));
			var roomArea = $("<td></td>").addClass("centertd").append(obj.get("roomArea")).append("平方米").append(area);
			var startDate = $("<input />",{name:"startDate",type:"hidden",value:obj.get("startDate")}).addClass("startDate").addClass("start"+obj.get("roomId"));
			var endDate = $("<input />",{name:"endDate",type:"hidden",value:obj.get("endDate")}).addClass("endDate").addClass("end"+obj.get("roomId"));
			var date = $("<td></td>").addClass("centertd").append(obj.get("startDate")).append("至"+obj.get("endDate")).append(startDate).append(endDate);
		
			var a = $("<a></a>",{href:"javascript:void(0)",click:function(){deleteRoom($(this).parent().parent());}}).append($("<img src='core/common/images/del.gif' title='删除'/>"));
			var op = $("<td></td>").addClass("centertd").append(a);

			tr.append(rentPrice).append(rentPriceUnit).append(rebate).append(rebateRuleId).append(memo).append(roomName).append(roomArea).append(date).append(op);
			$("#roomListTBody").append(tr);
			var roomIds = new Array();
			$(".roomId").each(function (){
				roomIds.push($(this).val());
			});
			$("#roomIds").val(roomIds);
		}
		
	    function checkDouble(el){
	    	$(el).val($(el).val().replace(/[^\d\.]/g,''));
	    }
	    function getCalendarInputTd(id,name,value){
	    	return $("<td></td>",{width:80}).append($("<input />",{id:id,name:name,readonly:"readonly",value:value}).addClass("inputauto").addClass(name).click(function(){showCalendar(id);}));
	    }
	    function getCalendarIconTd(id){
	    	return $("<td></td>",{width:20,align:"center"}).append($("<img />",{src:"core/common/images/timeico.gif"}).css({"position":"relative","left":-3}).click(function(){showCalendar(id);}));
	    }
		function deleteRoom(tr){
			tr.remove();
			calculateTotal("roomArea","totalArea");
		}
		function deletePlan(tr){
			tr.remove();
			calculateTotal("money","totalMoney");
		}
		function fireChange(el,className){
			$("."+className).each(function(){
				if(!$(this).val() || $(this).val()==""){
					$(this).val($(el).val());
				}
			});
		}
		function calculateTotal(className,total){
			var sum = 0;
			$("."+className).each(function(){
				if($(this).val()){
					sum += parseFloat($(this).val());
				}
			});
			$("#"+total).text(sum);
		}
		function initForm(){
			$("#form1").validate({
				rules: {
					"contract.contractPartyId":"required",
					"contract.customerName":"required",
					"contract.code":"required",
					"contract.roomTypeId":"required",
					"managerName":"required",
					"contract.signDate":"required",
					"contract.startDate":"required",
					"contract.endDate":"required"
				},
				messages: {
					"contract.contractPartyId":"请选择甲方",
					"contract.customerName":"请选择已方",
					"contract.code":"请输入合同编号",
					"contract.roomTypeId":"请选择房间类型",
					"managerName":"请选择负责人",
					"contract.signDate":"请选择签订日期",
					"contract.startDate":"请选择有效日期开始时间",
					"contract.endDate":"请选择有效日期结束时间"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					if($("#endDate").val()<$("#startDate").val()){
						showTip("有效日期开始时间不能小于有效日期结束时间",3000); 
						return;
					}
					$(form).ajaxSubmit({
				        dataType: 'json',
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadList('WYHT');parent.fb.end()", 2000);
				        	}
				        } 
				    });
				}
			});
		}
		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		}
		
		function generateCode(){
			var name = $("#contractModel :selected").val();
			$.post("<%=basePath%>contract!generateCode.action?contractModel="+name,function(data){
				if(data.result.success){
					$("#code").val(data.result.value);
				}
			});
		}
		
		function addBillRent(){
			var roomIds =$("#roomIds").val();
			fbStart('添加资金计划','<%=BaseAction.rootLocation %>/department.estate/contract!addBillRent.action?roomIds = '+roomIds+'&startDate='+$('#startDate').val()+'&endDate='+$('#endDate').val(),500,268);
		}
		
		function mySelected(cal, date) {
			  cal.sel.value = date; // just update the date in the input field.
			  if (cal.dateClicked && (cal.sel.id == "sel1" || cal.sel.id == "sel3"))
			    // if we add this call we close the calendar on single-click.
			    // just to exemplify both cases, we are using this only for the 1st
			    // and the 3rd field, while 2nd and 4th will still require double-click.
			    cal.callCloseHandler();
			  if(cal.sel.id=="startDate"){
				  $("#receiveDate").val(date);
				  var ss = date.split("-");
				  var startDate = new Date(ss[0],ss[1],ss[2]);
				  startDate.setFullYear(startDate.getFullYear()+1);
				  startDate.setMonth(startDate.getMonth()-1);
				  startDate.setDate(startDate.getDate()-1);
				  startDate = startDate.format("yyyy-MM-dd");
				  $("#endDate").val(startDate);
			  }
			}
		function myShowCalendar(id){
			var el = document.getElementById(id);
			  if(el==null) el = id;
			    // first-time call, create the calendar.
			    var cal = new Calendar(false, null, mySelected, closeHandler);
			    // uncomment the following line to hide the week numbers
			    // cal.weekNumbers = false;
			    calendar = cal;                  // remember it in the global var
			    cal.setRange(1900, 2200);        // min/max year allowed.//更改起始年月
			    cal.create();
			 
			  //calendar.setDateFormat(format);    // set the specified date format
			  calendar.parseDate(el.value);      // try to parse the text in field
			  calendar.sel = el;                 // inform it what input field we use
			  // the reference element that we pass to showAtElement is the button that
			  // triggers the calendar.  In this example we align the calendar bottom-right
			  // to the button.
			  calendar.showAtElement(el, "B");        // show the calendar
			  return false;
		}
		
		function selectContractPartyA(){
			fbStart('选择合同甲方','<%=BaseAction.rootLocation %>/core/corporation!select.action',520,400);
		}
		
		function setSelectCorporation(data){
			$("#contractPartyA").val(data.name);	
		}
		function initRoom(){
			html="";
			$.ajax({
				   type		: "POST",
				   url		: "<%=BaseAction.rootLocation%>/department.estate/contract!listByDataDict.action",
				   success	: function(data){
					   if(data.result.value!=null){
					   var str=data.result.value;
					 	  for(var i = 0;i<str.length;i++){
							html +="<option>"+str[i]+"</option>";	   
					  	  }
					 	 $("#propertyId").append(html);	  
					   }
				   }
				});
		}
		function addByRating(){
			var html="<tr >";
			var y=$("#checkYear").val();
		    var	r=$("#rateId").val();
		    if(y==''||r=='') return;
		    if($("#addRating").children().size()>1){
					html +="<td align=\"center\"  height=\"25px\"><input class=\"yearId\" style=\"text-align:center;border:0;\" id=\"addYear\" value=\""+y+"\" type=\"text\" readonly=\"readonly\" \/></td><td align=\"center\"  height=\"25px\" ><input class=\"rateId\" style=\"text-align:center;border:0;line-height:20px;\" id=\"addRate\"  value=\""+r+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
				    html += '<td align=\"center\"><img width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteRate(this);\" src=\"core/common/images/del.gif\" /></td>'	
				    html +="</tr>"
				    $("#noData").remove();
					var ids=$("#addRating").find("input[id='addYear']");
					var flag=false;
					var i=0;
					$(ids).each(function (){
						if($(this).val()==y){
							$(this).parent().parent().remove();
						}
					});
					var s = $("#addRating").find("input[id='addYear']");
					$(s).each(function (){
						if(y<$(this).val()){
							flag=true;
							return;
						}
						i++;
					});
					if(flag){
						$("#addRating").children().eq(i).after(html);
						flag=false;
					}else $("#addRating").append(html);
		    }
			
		}
		function deleteRate(obj){
			$(obj).parent().parent().remove();
			if($("#addRating").children().size()==1){
				$("#addRating").append("<tr id=\"noData\"><td colspan=\"4\" align=\"center\" height=\"25px\" style=\"color: #aaaaaa\">无数据</td></tr>");
			}
		}
		function rentAuto(){
			if($("#roomIds").val()!=''){
			fbStart('自动生成资金计划','<%=basePath %>contract!addBillRentAuto.action?settleType='+$('#payId').val(),700,368);
			}else{
				showTip("没有相关房间信息",2000);
			}
		}
		 function rateValue(){
			 var arr2 = new Array;
				var i=0;
				$(".rateId").each(function (){
					arr2[i]=$(this).val();
					i++;
				});
				return arr2;
		}
		function yearValue(){
			var arr1 = new Array;
			var i=0;
			$(".yearId").each(function (){
				arr1[i]=$(this).val();
				i++;
			});
			return arr1;
		}
		function roomValue(){
			var ids=$("#roomIds").val();
			var arr=ids.split(",");
			return arr;
		}
 		function roomName(i){
 		 	var roomName = $(".name"+i).eq(0).val();
			return roomName;
		} 
 		function startValue(i){
 			var startDate = $(".start"+i).eq(0).val();
			return startDate;
		}
		function endValue(i){
			 var endDate = $(".end"+i).eq(0).val();
			 return endDate;
		} 
		function areaValue(i){
		  var roomArea=$(".area"+i).eq(0).val();
			return roomArea;
		}
		function rentValue(i){
			var rentPrice=$(".rent"+i).eq(0).val();
			return rentPrice;
		}
		function Count(areaAmount,payAmount,startYear,endYear){
			 if($("#areaAmountshow").text()!=null&& $("#areaAmountshow").text()!=''){
				var areas =parseInt($("#areaAmountshow").text())+parseInt(areaAmount);
				$("#areaAmountshow").text(areas);
			}else{
				$("#areaAmountshow").text(areaAmount)
			} 
			var yearSta=startYear.substr(0,4);//租赁开始的年
			var nowDate=new Date();
			var yearNow=nowDate.getFullYear();//当前的年
			var yearEnd=endYear.substr(0,4);//结束的年
			//开始年=当前年
			if(parseInt(yearSta)==parseInt(yearNow)){
				if(parseInt(yearNow)==parseInt(yearEnd)){
					var diff=DateDiff(startYear,endYear);
					if($("#payAmountshow").text()!=null&& $("#payAmountshow").text()!=''){
						var payAmounts=parseInt($("#payAmountshow").text())+diff*payAmount*parseInt(areaAmount);
						$("#payAmountshow").text(payAmounts.toFixed(2))
					}else{
						var payAmountss=diff*payAmount*parseInt(areaAmount);
						$("#payAmountshow").text(payAmountss.toFixed(2))
					}
				}else if(parseInt(yearNow)<parseInt(yearEnd)){
					var d=yearNow+'-'+'12'+'-'+'31';
					var diff=DateDiff(startYear,d);
					if($("#payAmountshow").text()!=null&& $("#payAmountshow").text()!=''){
						var payAmounts=parseInt($("#payAmountshow").text())+diff*payAmount*parseInt(areaAmount);
						$("#payAmountshow").text(payAmounts.toFixed(2))
					}else{
						var payAmountss=diff*payAmount*parseInt(areaAmount);
						$("#payAmountshow").text(payAmountss.toFixed(2))
					}
				}
			}
			//开始年<当前年
			else if(parseInt(yearSta)<parseInt(yearNow)){
				if(parseInt(yearNow)==parseInt(yearEnd)){
					var d=yearNow+'-'+'1'+'-'+'1';
					var diff=DateDiff(d,endYear);
					if($("#payAmountshow").text()!=null&& $("#payAmountshow").text()!=''){
						var payAmounts=parseInt($("#payAmountshow").text())+diff*payAmount*parseInt(areaAmount);
						$("#payAmountshow").text(payAmounts.toFixed(2))
					}else{
						var payAmountss=diff*payAmount*parseInt(areaAmount);
						$("#payAmountshow").text(payAmountss.toFixed(2))
					}
				}else if(parseInt(yearNow)<parseInt(yearEnd)){
					var d;
					if(parseInt(yearNow)/400==0 || parseInt(yearNow)/4==0 && parseInt(yearNow)/100!=0){
						 d=366;
						 if($("#payAmountshow").text()!=null&& $("#payAmountshow").text()!=''){
								var payAmounts=parseInt($("#payAmountshow").text())+d*payAmount*parseInt(areaAmount);
								$("#payAmountshow").text(payAmounts.toFixed(2))
							}else{
								var payAmountss=d*payAmount*parseInt(areaAmount);
								$("#payAmountshow").text(payAmountss.toFixed(2))
							}
					}else{
						d=365
						if($("#payAmountshow").text()!=null&& $("#payAmountshow").text()!=''){
							var payAmounts=parseInt($("#payAmountshow").text())+d*payAmount*parseInt(areaAmount);
							$("#payAmountshow").text(payAmounts.toFixed(2))
						}else{
							var payAmountss=d*payAmount*parseInt(areaAmount);
							$("#payAmountshow").text(payAmountss.toFixed(2))
						}
					}
				}
			}
		}
		   function  DateDiff(sDate1,sDate2){    
		       var aDate, oDate1, oDate2, iDays  
		       aDate  =  sDate1.split("-")  
		       oDate1  =  new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])   
		       aDate  =  sDate2.split("-")  
		       oDate2  =  new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])  
		       iDays  =  parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24)   
		       return  iDays  
		   }    
		   
	</script>
</head>
<body>
<input type="hidden" id="roomIds" name="roomIds"/>
<input type="hidden" id="roomId"/>
<input type="hidden" id="roomArea"/>
<input type="hidden" id="roomName"/>
<input type="hidden" id="roomNames"/>
<form action="<%=BaseAction.rootLocation %>/department.estate/contract!saveEstateContract.action" method="post" name="form1" id="form1">
<input type="hidden" name="contract.item" value="${param.type}"/>
<div class="basediv" style="margin-top:0px; height:450px; overflow:hidden; margin-top:3px;">
<div id="scrollDiv" style=" overflow-x:hidden;  height:450px; overflow-y:scroll; position:relative;">
<div class="basediv">
<div class="titlebg" style="background:#f0f0f0; border-bottom-color:#D9D9D9;">甲方乙方</div>
<div class="divlays" style="margin:0px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <td class="layertdleft100"><span class="psred">*</span>甲方(出租方)：</td>
      <td class="layerright"><table width="50%" border="0" cellspacing="0" cellpadding="0">
        <tbody><tr>
          <td><input id="contractPartyA" name="contract.contractPartyA" class="inputauto" onclick="selectContractPartyA()" readonly="readonly"/></td>
          <td width="21" align="center" style="padding-right: 5px;"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectContractPartyA()" style="cursor:pointer"/></td>
          </tr>
      </tbody></table></td>
      <%-- <td class="layerright" >
	      <strong><dd:select name="contract.contractPartyId" key="business.0031" checked="business.003101"/></strong>
	  </td> --%>
    </tr>
    <tr>
     
      <td class="layertdleft100"><span class="psred">*</span>乙方(承租方)：</td>
      <td class="layerright"><table width="50%" border="0" cellspacing="0" cellpadding="0">
        <tbody><tr>
          <td><input id="customerId" name="contract.customerId" type="hidden" ><input id="customerName" name="contract.customerName" class="inputauto" onclick="selectCustomer()" readonly="readonly"></td>
          <td width="21" align="center" style="padding-right: 5px;"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer()" style="cursor:pointer"></td>
          </tr>
      </tbody></table></td>
    </tr>
    
  </table>
  </div>
	<div class="hackbox"></div>
</div>
<div class="basediv">
<div class="titlebg" style="background:#f0f0f0; border-bottom-color:#D9D9D9;">基本信息</div>
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<!-- <td class="layertdleft100"><span class="psred">*</span>合同编号：</td>
      		<td class="layerright" width="33%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tbody>
		            <tr>
		            	<td width="265"><input id="code" name="contract.code" type="text" style="width: 118px;"class="inputauto"/></td>
		            	<td>
			            	<select id="contractModel" style="height: 21px">
				            	<option>S</option>
				            	<option>C</option>
				            	<option>P</option>
			            	</select>
		            	</td>
		                <td><img src="core/common/images/auto.gif" width="75" height="22" onclick="generateCode()" style="margin-left:-4px;"/></td>
		            </tr>
	            </tbody>
       		</table></td> -->
       		<td class="layertdleft100"><span class="psred">*</span>合同编号：</td>
      		<td class="layerright" width="33%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tbody>
	            <tr>
					<td width="165" style="padding-right: 5px"><input onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" id="code" name="contract.code" type="text" style="width: 165px;" class="inputauto"/></td>
		            <td><img src="core/common/images/auto.gif" width="75" height="22" onclick="generateCode()" style="margin-left:-4px;"/></td>
		        </tr>
	            </tbody>
       		</table></td>
      		<td class="layertdleft100">本物业名称：</td>
      		<td class="layerright" width="25%"><input name="contract.propertyName" type="text" class="inputauto" style="width:99%;" value="华业集团" /></td>
    	</tr>
    	<tr>
	      	<td class="layertdleft100">所租房屋产权证号：</td>
	      	<td class="layerright"><select id="propertyId" name="contract.propertyNo" style="height: 22px;width:99%"></select>
	      	</td>
	      	<td class="layertdleft100">座落位置：</td>
	      	<td class="layerright" width="33%"><input name="contract.address" type="text" class="inputauto" style="width:99%;" value="杭州市滨江区" /></td>
      	</tr>
      	<tr>
	      	<td class="layertdleft100"><span class="psred">*</span>房屋类型：</td>
	      	<td class="layerright" >
	      		<dd:select name="contract.roomTypeId" key="business.0032"/>
	      	</td>
	      	<td class="layertdleft100"><span class="psred">*</span>合同期限：</td>
	      	<td class="layerright"><table border="0" cellspacing="0" cellpadding="10">
	        	<tbody>
	          	<tr>
		            <td width="80"><input id="startDate" name="contract.startDate" type="text" readonly="readonly" class="inputauto"  onclick="myShowCalendar('startDate');" /></td>
		            <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="myShowCalendar('startDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
		            <td>&nbsp;至&nbsp;</td>
		            <td width="80"><input id="endDate" name="contract.endDate" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('endDate');" /></td>
		            <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('endDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
	          	</tr>
	        	</tbody>
      		</table></td>
      	</tr>
      	<tr>
      		<!-- <td class="layertdleft100">物管理单价：</td>
      		<td class="layerright" ><input name="contract.propertyUnit" type="text" class="inputauto" style="width:55%;" /> 元/月/平米</td>
      		 -->
      		 
      	</tr>
      	<tr>
       		<!-- <td class="layertdleft100">总建筑面积</td>
      		<td class="layerright" ><input name="contract.overallFloorage" type="text" class="inputauto" style="width:55%;" /> 平方米</td>
      		 --><td class="layertdleft100"><span class="psred">*</span>签订日期：</td>
     		<td class="layerright"><table width="143" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td><input id="signDate" name="contract.signDate" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('signDate')"/></td>
		          <td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('signDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
		        </tr>
		    </table></td>
      <!-- 	</tr>
      	<tr> -->
	     	<!-- <td class="layertdleft100">合同总金额</td>
	      	<td class="layerright" ><input name="contract.totalAmount" type="text" class="inputauto" style="width:83%;" /> 元</td>
	      	 --><td class="layertdleft100">房屋交付时间：</td>
	      	<td class="layerright"><table width="143" border="0" cellspacing="0" cellpadding="0">
	        	<tbody>
		          <tr>
		            <td><input id="receiveDate" name="contract.receiveDate" readonly="readonly" type="text" class="inputauto" onclick="showCalendar('receiveDate')"/></td>
          			<td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('receiveDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
		          </tr>
	        	</tbody>
	      </table></td>
       	</tr>
       		<tr>
       		<td class="layertdleft100"><span class="psred">*</span>负责人：</td>
     		<td class="layerright"><table width="143" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td><input id="managerId" name="contract.managerId" type="hidden"><input id="managerName" name="managerName" class="inputauto" onclick="selectUser()" readonly="readonly"></td>
		          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser()" style="cursor:pointer"/></td>
		        </tr>
		    </table></td>
	     	<td class="layertdleft100">首期金额：</td>
      		<td class="layerright" >
      			<input type="text" value="" class="inputauto"/>
      		</td>
       	</tr>
       	<tr>
       	<td class="layertdleft100">结算方式：</td>
      		<td class="layerright" >
      		<enum:select id="payId" type="com.wiiy.business.preferences.enums.SettleEnum" name="contract.payType" defaultValue="THREEMONTH"/>
      		</td>
       	</tr>
  </table>
  </div>
<div class="hackbox"></div>
</div>
<div class="basediv" style="margin-top:0px; display:block" id="textname" name="textname">
	<div class="emailtop">
		<div class="leftemail">
	    	<strong style="line-height:27px; color:#000; padding-left:5px;">租金递增方式</strong>
	    </div>
		<div class="leftemail">
	    	<span style="line-height:27px;  color:#000; padding-left:260px;">第</span>
	    	<select id="checkYear" style="width:40px; height:18px;">
			<c:forEach var="a" begin="1" end="10" step="1">
	    		<option>${a }</option>
			</c:forEach>
	    	</select>年&nbsp;&nbsp;增加
	    	<input id="rateId" value="2" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" style="line-height:10px;width:20px; color:#000; padding-left:15px;" type="text" />%
	    </div>
	    <div class="rightemail" style="width:auto; padding-right:10px;">
			<ul>
				<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="addByRating()" class=""><span><img src="core/common/images/action_add.png" /></span>生成增长计划</li>
			</ul>
		</div>
	</div>
	<div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nowrap">
			<tbody id="addRating">
				 <tr>
		            <td width="300" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">递增年份&nbsp;(年)</td>
		            <td width="300" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">增长率&nbsp;(%)</td>
	          		 <td width="92" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">操作</td>	
	          	</tr>
	          	<tr id="noData">
	          		<td colspan="4" align="center" height="25px" style="color: #aaaaaa">无数据</td>
	          	</tr>
          	</tbody>
         </table>
    </div>
</div>    





<div class="basediv" style="margin-top:0px; display:block" id="textname" name="textname">
	<div class="emailtop">
		<div class="leftemail">
	    	<strong style="line-height:27px; color:#000; padding-left:5px;">租赁房间</strong>
	    </div>
		<div class="leftemail">
	    	<span style="line-height:27px; color:#000; padding-left:160px;">租赁面积(平方米)：</span>
	    	<span id="areaAmountshow" ></span>
	    </div>
		<div class="leftemail">
	    	<span style="line-height:27px; color:#000; padding-left:25px;">当年租金(元)：</span>
	    	<span id="payAmountshow"></span>
	    </div>
	    <div class="rightemail" style="width:auto; padding-right:10px;">
			<ul>
				<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('添加租赁房间','<%=basePath %>web/contract/subjectRent_add.jsp?saveFlag=0&contractId='+$('#contractId').val()+'&startDate='+$('#startDate').val()+'&endDate='+$('#endDate').val(),500,284);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>添加租赁房间</li>
			</ul>
		</div>
	</div>
	<div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nowrap">
			<tbody id="roomListTBody">
				<tr>
		            <td class="tdcenterL" onmousemove="this.className='tdcenterLover'" onmouseout="this.className='tdcenterL'" style="border-left:0;">房间名称</td>
		            <td width="185" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">租用面积 </td>
		            <td width="200" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">租用日期 </td>
		            <td width="90" class="tdrightc" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">操作</td>
	          	</tr>
	          	<tr id="nonData">
	          		<td colspan="4" align="center" height="25px" style="color: #aaaaaa">无数据</td>
	          	</tr>
          	</tbody>
         </table>
    </div>
</div>    

<div class="basediv" style="margin-top:0px; display:block" id="textname" name="textname">
	<div class="emailtop">
		<div class="leftemail">
	    	<strong style="line-height:27px; color:#000; padding-left:5px;">资金计划</strong>
	    </div>
		
		<div class="rightemail" style="width:auto; padding-right:10px;">
			<ul>
				<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="rentAuto();" class=""><span><img src="core/common/images/oa018.png" width="15" height="13"></span>自动生成资金计划</li>
			<%if(EstateActivator.getHttpSessionService().isInResourceMap("estate_billPlanRent_list")){ %>
				<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="addBillRent();" class=""><span><img src="core/common/images/emailadd.gif" width="15" height="13"></span>添加资金计划</li>
			<%} %>
			</ul>
		</div>
	</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="nowrap">
		<tbody id="planListTBody">
			<tr>
				<td class="tdcenterL" onmousemove="this.className='tdcenterLover'" onmouseout="this.className='tdcenterL'" style="border-left:0;">费用类型</td>
				<td width="145" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计划付费金额</td>
				<td width="120" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计划付费日期</td>
				<td width="160" class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计费日期</td>
				<td width="50" class="tdrightc" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">操作</td>
	        </tr>
          	<tr   id="noPlan">
          		<td colspan="5" align="center" height="25px" style="color: #aaaaaa">无数据</td>
          	</tr>
        </tbody>
	</table>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

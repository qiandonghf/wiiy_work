<%@page import="com.wiiy.engineering.activator.EngineeringActivator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
	<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
	
	<script type="text/javascript">
	 	var selecteds = "";
	 	var clicked = null;
		$(function(){
			initTip();
			initForm();
			initDate();
			initChange();
		});
		
		function initChange(){
			var audit = $("input[name='contract.audit']");
			var finished = $("input[name='contract.finished']");
			var published = $("input[name='contract.published']");
			changeState(audit);
			changeState(finished);
			changeState(published);
		}
		function changeState(obj){
			if($(obj).attr("checked"))
				$(obj).val("YES");
			else
				$(obj).val("NO");
			if($(obj).attr("name") == 'contract.finished'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未完成");
				else $(c).text("已完成");
			}else if($(obj).attr("name") == 'contract.audit'){
				var p = $(obj).parent();
				var c = $(p).find("span");
				if($(obj).val() == 'NO')$(c).text("未审核");
				else $(c).text("已审核");
			}
		}
		function initDate(){
			var start = $("#startDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var sign = $("#signDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var end = $("#endDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var receive = $("#receiveDate").val().replace(/(^\s*)|(\s*$)/g, "") == '';
			var startDate;
			var endDate;
			if(start || end || sign){
				<%Calendar c = Calendar.getInstance();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				String startTime = s.format(c.getTime());	
				c.add(Calendar.YEAR, 1);
				c.add(Calendar.DATE, -1);
				String endTime = s.format(c.getTime());%>
				startTime = '<%=startTime%>';
				endTime = '<%=endTime%>';
			}
			if(start) $("#startDate").val(startDate);
			if(sign) $("#signDate").val(startDate);
			if(end) $("#endDate").val(endDate);
			if(receive) $("#receiveDate").val(startDate);
		}
		
		function selectUser(id){
			var title = '选择';
			clicked = id;
			switch(id){
			case 'handling':
				title += "经手人";
				break;
			case 'contact':
				title += "主要联系人";
				break;
			case 'duty':
				title += "责任人";
				break;
			}
			fbStart(title,'<%=BaseAction.rootLocation%>/core/user!select.action',520,400);
		} 
		
		function setSelectedUser(contect){
			$("#"+clicked).val(contect.name);
			$("#"+clicked+"Id").val(contect.id);
		}
		
		function selectCustomer(id){
			var title = '选择';
			clicked = id;
			switch(id){
			case 'supplier':
				title += "甲方";
				break;
			case 'customer':
				title += "乙方";
				break;
			}
			fbStart('选择客商','<%=basePath%>web/customer_selector.jsp',520,400);
		}
		function setSelectedCustomer(customer){
			$("#"+clicked).val(customer.name);
			$("#"+clicked+"Id").val(customer.id);
		} 
		
	    function checkDouble(el){
	    	$(el).val($(el).val().replace(/[^\d\.]/g,''));
	    } 
		function initForm(){
			$("#form1").validate({
				rules: {
					"contract.name":"required",
					"contract.code":"required",
					//"contract.supplierName":"required",
					//"contract.customerName":"required",
					//"contract.contactName":"required",
					"contract.startTime":"required",
					"contract.endTime":"required",
					"contract.signDate":"required"
				},
				messages: {
					"contract.name":"请输合同名称",
					"contract.code":"请输入合同编号",
					//"contract.supplierName":"请选择供应商",
					//"contract.customerName":"请选择客户",
					//"contract.contactName":"请选主要联系人",
					"contract.startTime":"请选择有效日期开始时间",
					"contract.endTime":"请选择有效日期结束时间",
					"contract.signDate":"请选择签订日期"
				},
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					if($("#endTime").val()<$("#startTime").val()){
						showTip("有效日期开始时间不能小于有效日期结束时间",3000); 
						return;
					}
					$(form).ajaxSubmit({
				        dataType: 'json',
				        success: function(data){
			        		showTip(data.result.msg,2000);
				        	if(data.result.success){
				        		setTimeout("getOpener().reloadList();", 2000);
				        	}
				        } 
				    });
				}
			});
		}
 		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		} 
 		
 		function initList(list){
 			$("#"+list).jqGrid({
 		    	url:'<%=basePath%>billPlanRent!list.action?id=${result.value.id }',
 				colModel: [
 					{label:'合同名称',name:'contract.name',align:"center",hidden:true}, 
 					{label:'编号',width:100,name:'code',align:"center"}, 
 					{label:'计划金额',name:'planFee',align:"center",formatter:'number',formatoptions:{decimalSeparator:".",thousandsSeparator: "",defaultValue: '0.00'}}, 
 					//{label:'实际金额',width:100,name:'realFee',align:"center"}, 
 					{label:'已审核',width:60,name:'audit.title',align:"center"}, 
 					{label:'状态',width:80,name:'status.title',align:"center"}, 
 					{label:'审批状态',width:80,name:'approvalStatus.title',align:"center"}, 
 					{label:'计费开始日期',hidden:true,name:'startDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
 					{label:'计费结束日期',hidden:true,name:'endDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
 					{label:'计划付费日期',hidden:true,name:'planPayDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},  
 				    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:100}
 				],
 				height: 350,
 				width: 639,
 				shrinkToFit: false,
 				gridComplete: function(){
 					var ids = $(this).jqGrid('getDataIDs');
 					for(var i = 0 ; i < ids.length; i++){
 						var id = ids[i];
 						var content = "";
 						var launch = $(this).getCell(id,'status.title');
 						var audit = $(this).getCell(id,'audit.title');
 						var approval = $(this).getCell(id,'approvalStatus.title');
 						<%boolean add = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_add");
 						boolean edit = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_edit");
 						boolean view = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_view");
 						boolean del = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_delete");
 						boolean launch = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_launch");%>
 						if(launch == '未出账' && approval == '未发起'){
 							if(audit == '是'){
		 						<%if(launch){%>
		 						content += "<img src=\"core/common/images/uploadicon.png\" width=\"14\" height=\"14\" title=\"发起结算流程\" onclick=\"launchById('"+id+"');\"  /> ";
		 						<%}%>
 							}
	 						<%if(edit){%>
	 						content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
	 						<%}%>
	 						<%if(del){%>
	 						content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
	 						<%}%>
 						}
 						<%if(view){%>
 						content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
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
 		function reloadList(id){
 			$("#contractRent").trigger("reloadGrid");
 		}
 		function viewById(id){
 			fbStart('查看结算安排','<%=basePath%>billPlanRent!view.action?id='+id,650,265);
 		}
 		function addById(id){
 			fbStart('新建结算安排','<%=basePath%>web/contract/billPlanRent_add.jsp?id='+id,650,240);
 		}
 		function editById(id){
 			fbStart('编辑结算安排','<%=basePath%>billPlanRent!edit.action?id='+id,650,240);
 		}
 		function launchById(id){
 			var iWidth=1024; //弹出窗口的宽度;
 			var iHeight=500; //弹出窗口的高度;
 			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
 			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
 			var openUrl = "";
 			openUrl = '<%=BaseAction.rootLocation%>/parkmanager.pf/bill!addByPlanId.action?contractId=${result.value.id }&department=ENGINEERING&id='+id;//弹出窗口的url
 			window.open(openUrl,'发起结算流程','top='+iTop+',left='+iLeft+',height='+iHeight+',width='+iWidth+',toolbar=no,menubar=no,scrollbars=yes,resizable=no, location=no,status=no');
 		}
 		function deleteById(id){
 			if(confirm("您确定要删除")){
 				$.post("<%=basePath%>billPlanRent!delete.action?id="+id,function(data){
 					showTip(data.result.msg,1000);
 					if(data.result.success){
 		        		reloadEachList("contractRent");
 		        	}
 				});
 			}
 		}
 		function deleteSelected(){
 			var ids = $("#contractRent").jqGrid("getGridParam","selarrrow");
 			if(ids==""){
 				showTip('请先选择结算安排',2000);	
 			}else if(confirm("确定要删除这些结算安排")){
 				$.post("<%=basePath%>billPlanRent!delete.action?ids="+ids,function(data){
 					showTip(data.result.msg,2000);
 					if(data.result.success){
 		        		reloadEachList("contractRent");
 		        	}
 				});
 			}
 		}
 		function chageTab(b){
 			if(b) $(".buttondiv").show();
 			else $(".buttondiv").hide();
 		}
 		
 		function initFileList(list){
 			$("#"+list).jqGrid({
 		    	url:'<%=basePath%>contractAtt!list.action?id=${result.value.id }',
 				colModel: [
					{label:'ID', name:'id', align:"center",hidden:true}, 
 					{label:'合同名称',name:'contract.name',align:"center",hidden:true}, 
 					{label:'文件类别',width:80,name:'type.title',align:"center"}, 
 					{label:'名称',width:80,name:'name',align:"center"}, 
 					{label:'路径', name:'newName', align:"center",hidden:true}, 
 					{label:'备注',name:'memo',align:"center"}, 
 				    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:60}
 				],
 				height: 350,
 				width: 639,
 				shrinkToFit: false,
 				gridComplete: function(){
 					var ids = $(this).jqGrid('getDataIDs');
 					for(var i = 0 ; i < ids.length; i++){
 						var id = ids[i];
 						var content = "";
 						<%boolean attAdd = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_file_add");
 						boolean attEdit = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_file_edit");
 						boolean attView = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_file_view");
 						boolean attDel = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_file_delete");%>
 						content += "<img src=\"core/common/images/down.png\" width=\"14\" height=\"14\" title=\"下载\" onclick=\"downloadContractAttById('"+id+"');\"  /> "; 
 						<%if(attEdit){%>
 						content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"fileEdit('"+id+"');\"  /> ";
 						<%}%>
 						<%if(attDel){%>
 						content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"fileDelete('"+id+"');\"  /> ";
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
 		
 		function downloadContractAttById(id){
 			var path = $("#contractFile").getCell(id,6);
 			var name = $("#contractFile").getCell(id,5);
 			location.href="<%=BaseAction.rootLocation%>/core/resources/"+path+"?name="+name;
 		}
 		function fileAdd(id){
 			fbStart('新建合同文件','<%=basePath%>web/contract/contractAtt_add.jsp?id='+id,650,245);
 		}
 		function fileEdit(id){
 			fbStart('编辑合同文件','<%=basePath%>contractAtt!edit.action?id='+id,650,100);
 		}
 		function fileDelete(id){
 			if(confirm("您确定要删除")){
 				$.post("<%=basePath%>contractAtt!delete.action?id="+id,function(data){
 					showTip(data.result.msg,1000);
 					if(data.result.success){
 		        		reloadEachList("contractFile");
 		        	}
 				});
 			}
 		}
 		function fileDeleteSelected(){
 			var ids = $("#contractFile").jqGrid("getGridParam","selarrrow");
 			if(ids==""){
 				showTip('请先选择合同文件',2000);	
 			}else if(confirm("确定要删除这些合同文件")){
 				$.post("<%=basePath%>contractAtt!delete.action?ids="+ids,function(data){
 					showTip(data.result.msg,2000);
 		        	if(data.result.success){
 		        		reloadEachList("contractFile");
 		        	}
 				});
 			}
 		}
 		
 		function initBillList(list){
 			$("#"+list).jqGrid({
 		    	url:'<%=BaseAction.rootLocation%>/parkmanager.pf/bill!list.action?department=ENGINEERING&contractId=${result.value.id }',
 				colModel: [
 					{label:'计划编号',width:80,name:'code',align:"center"}, 
 					{label:'金额',width:100,name:'settlementFee',align:"center",formatter:'number',formatoptions:{decimalSeparator:".",thousandsSeparator: "",defaultValue: '0.00'}}, 
 					{label:'结算日期',width:80,name:'settlementDate',align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}}, 
 					{label:'收付方向',width:60,name:'payment.title',align:"center"}, 
 					{label:'结算方式',width:60,name:'settlement.title',align:"center"}, 
 					{label:'结算性质',width:60,name:'settlementType.title',align:"center"}, 
 					{label:'已付款',width:40,name:'paid.title',align:"center"}, 
 				    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:60}
 				],
 				height: 372,
 				width: 639,
 				shrinkToFit: false,
 				gridComplete: function(){
 					var ids = $(this).jqGrid('getDataIDs');
 					for(var i = 0 ; i < ids.length; i++){
 						var id = ids[i];
 						var content = "";
 						<%add = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_add");
 						edit = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_edit");
 						view = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_view");
 						del = EngineeringActivator.getHttpSessionService().isInResourceMap("engineering_rent_delete");%>
 						<%if(view){%>
 						content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"billView('"+id+"');\"  /> ";
 						<%}%>
 						<%-- <%if(edit){%>
 						content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"billEdit('"+id+"');\"  /> ";
 						<%}%>
 						<%if(del){%>
 						content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"billDelete('"+id+"');\"  /> ";
 						<%}%> --%>
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
 		function billView(id){
 			fbStart('查看实际结算','<%=BaseAction.rootLocation %>/parkmanager.pf/bill!viewById.action?id='+id,650,236);
 		}
 		function billEdit(id){
 			fbStart('编辑实际结算','<%=basePath%>billRent!edit.action?id='+id,650,257);
 		}
 		function billDelete(id){
 			if(confirm("您确定要删除")){
 				$.post("<%=basePath%>billRent!delete.action?id="+id,function(data){
 					showTip(data.result.msg,1000);
 					if(data.result.success){
 						reloadEachList("contractBill");
 		        	}
 				});
 			}
 		}
 		function billDeleteSelected(){
 			var ids = $("#contractBill").jqGrid("getGridParam","selarrrow");
 			if(ids==""){
 				showTip('请先选择实际结算',2000);	
 			}else if(confirm("确定要删除这些实际结算")){
 				$.post("<%=basePath%>billRent!delete.action?ids="+ids,function(data){
 					showTip(data.result.msg,2000);
 		        	if(data.result.success){
 		        		reloadEachList("contractBill");
 		        	}
 				});
 			}
 		}
 		
 		function chageTab(b,index){
 			if(b) $(".buttondiv").show();
 			else $(".buttondiv").hide();
 			if(index){
 				$(".tabswitch").each(function(i){
 					if(i != 0){
						$(this).children().eq(0).children().eq(1).remove();
						$(this).children().eq(0).children().eq(2).remove();
 					}
				});
 				switch(index){
 				case 1:
	 				var html = $(".tabswitch").eq(index).children().eq(0).html();
					$(".tabswitch").eq(index).children().eq(0).html(html+"<table id=\"contractFile\" width=\"100%\"><tr><td /></tr></table><div id=\"pager\"></div>");
					initFileList("contractFile");
 	 				jqGridResizeRegister("contractFile");
 					break;
 				case 2:
	 				var html = $(".tabswitch").eq(index).children().eq(0).html();
					$(".tabswitch").eq(index).children().eq(0).html(html+"<table id=\"contractRent\" width=\"100%\"><tr><td /></tr></table><div id=\"pager\"></div>");
 					initList("contractRent");
 	 				jqGridResizeRegister("contractRent");
 					break;
 				case 3:
	 				var html = $(".tabswitch").eq(index).children().eq(0).html();
					$(".tabswitch").eq(index).children().eq(0).html(html+"<table id=\"contractBill\" width=\"100%\"><tr><td /></tr></table><div id=\"pager\"></div>");
					initBillList("contractBill");
 	 				jqGridResizeRegister("contractBill");
 					break;
 				}
 			} 
 		}
 		
 		function reloadEachList(id){
 			$("#"+id).trigger("reloadGrid");
 		}
 		
 		function generateCode(){
			showTip("正在生成编号,请稍后……",60000);
			$.post("<%=basePath%>contract!generateCode.action",function(data){
				if(data.result.success){
					showTip("生成编号成功",2000);
					$("#code").val(data.result.value);
				}
			});
		}
	</script>
</head>
<body>
<form action="<%=basePath %>contract!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="contract.id" value="${result.value.id }"/>
<div class="basediv">
	<div class="divlays" style="margin:0px;padding-bottom:0px;background-color: #eeeeee;">
		<div class="apptab" id="tableid">
			<ul>
				<li class="apptabliover" onclick="tabSwitch('apptabli','apptabliover','tabswitch',0);chageTab(true);">基本信息</li>
				<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',1);chageTab(false,1);">合同文件</li>
				<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',2);chageTab(false,2);">结算安排</li>
				<li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',3);chageTab(false,3);">实际结算</li>
			</ul>
		</div>
	</div>
	<div class="divlays tabswitch" style="margin:0px;border-top:1px solid #d9d9d9;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
	       		<td class="layertdleft100"><span class="psred">*</span>合同名称：</td>
			    <td class="layerright"><input name="contract.name" type="text" class="inputauto" value="${result.value.name }" /></td>
	       		<td class="layertdleft100"><span class="psred">*</span>合同编号：</td>
				<td class="layerright">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100"><input id="code" name="contract.code" type="text" class="inputauto" value="${result.value.code }" /></td>
							<td><img src="core/common/images/auto.gif" width="75" height="22" style="cursor:pointer;" onclick="generateCode()"/></td>
						</tr>
					</table>
				</td>
	       	</tr>
	       	<tr>
	       	    <td class="layertdleft100">供应商：</td>
				<td class="layerright" >
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="supplierId" value="${result.value.supplier.id }" name="contract.supplierId" type="hidden"/>
				          	<input id="supplier" name="supplierName" value="${result.value.supplier.name }" class="inputauto" onclick="selectCustomer('supplier')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('supplier')" style="cursor:pointer"/></td>
				        </tr>
			   		</table>
			   	</td>
			    <td class="layertdleft100" >客户：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
					          <input id="customerId" name="contract.customerId" value="${result.value.customer.id }" type="hidden"/>
					          <input id="customer" name="customerName" value="${result.value.customer.name }" class="inputauto" onclick="selectCustomer('customer')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectCustomer('customer')" style="cursor:pointer"/></td>
				        </tr>
				    </table>
			    </td>
			</tr>
			<tr>
			    <td class="layertdleft100" ><span class="psred">*</span>所属项目：</td>
				<td class="layerright">
					<input id="projectId" name="contract.projectId" value="${result.value.project.id }" type="hidden"/>
				    <input class="inputauto" name="projectName" value="${result.value.project.name }" readonly="readonly"/>
			    </td>
				<td class="layertdleft100">主要联系人：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="contactId" name="contract.contactId" value="${result.value.contact.id }" type="hidden"/>
				         	<input id="contact" value="${result.value.contact.realName }" class="inputauto" onclick="selectUser('contact')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('contact')" style="cursor:pointer"/></td>
				        </tr>
		    		</table>
		    	</td>
	       	</tr>
	       	<tr>
				<td class="layertdleft100" >责任人：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="dutyId" value="${result.value.duty.id }" name="contract.dutyId" type="hidden"/>
				          	<input id="duty" value="${result.value.duty.realName }" class="inputauto" onclick="selectUser('duty')" readonly="readonly"/></td>
				          <td width="21" align="center">
				          <img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('duty')" style="cursor:pointer"/></td>
				        </tr>
				    </table>
				</td>
			   	<td class="layertdleft100" >经手人：</td>
				<td class="layerright">
					<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="handlingId" name="contract.handlingId" value="${result.value.handling.id }" type="hidden"/>
				          	<input id="handling" value="${result.value.handling.realName }" class="inputauto" onclick="selectUser('handling')" readonly="readonly"/></td>
				          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('handling')" style="cursor:pointer"/></td>
				        </tr>
			    	</table>
			    </td>
	       	</tr>
	       	<tr>
	       		<td class="layertdleft100">合同类别：</td>
				<td class="layerright" >
					<dd:select name="contract.categoryId" checked="result.value.categoryId" key="contract.0002" />
			    </td>
				<td class="layertdleft100">合同形式：</td>
			    <td class="layerright">
					<enum:select name="contract.contractForm" checked="result.value.contractForm" type="com.wiiy.common.preferences.enums.ContractFormEnum" />
				</td>
	       	</tr>
	       	<tr>
			    <td class="layertdleft100">合同状态：</td>
				<td class="layerright" >
				<enum:select name="contract.contractStatus" checked="result.value.contractStatus" type="com.wiiy.common.preferences.enums.ContractStatusEnum" />
			    </td>
			    <td class="layertdleft100">货币种类：</td>
	       		<td class="layerright" >
					<dd:select name="contract.currencyTypeId" checked="result.value.currencyTypeId" key="project.0001" />
	       		</td>
	       	</tr>
	       	<tr>
			    <td class="layertdleft100">收付方式：</td>
	       		<td class="layerright" >
	       			<enum:select name="contract.payment" checked="result.value.payment" type="com.wiiy.common.preferences.enums.PaymentTypeEnum"/>
	       		</td>
	       		<td class="layertdleft100">结算方式：</td>
	       		<td class="layerright" >
	       			<enum:select name="contract.settlement" checked="result.value.settlement" type="com.wiiy.common.preferences.enums.SettlementTypeEnum"/>
	       		</td>	
	    	</tr>
	    	<tr>
	      		<td class="layertdleft100" ><span class="psred">*</span>合同生效时间：</td>
	     		<td class="layerright" >
		     		<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="startDate" name="contract.startDate" 
				          	value='<fmt:formatDate value="${result.value.startDate}" pattern="yyyy-MM-dd"/>' type="text" readonly="readonly" 
				          	class="inputauto" onclick="showCalendar('startDate')"/>
				          </td>
				          <td width="20" align="center">
				          	<img style="position:relative; left:-1px;" onclick="showCalendar('startDate')" src="core/common/images/timeico.gif" width="20" height="22" />
				          </td>
				        </tr>
				    </table>
			    </td>
			    <td class="layertdleft100" ><span class="psred">*</span>合同结束时间：</td>
	     		<td class="layerright" >
	     			<table width="143" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td>
				          	<input id="endDate" name="contract.endDate" type="text" readonly="readonly" 
				          	value='<fmt:formatDate value="${result.value.endDate}" pattern="yyyy-MM-dd"/>'
				          	class="inputauto" onclick="showCalendar('endDate')"/>
				          </td>
				          <td width="20" align="center">
				          	<img style="position:relative; left:-1px;" onclick="showCalendar('endDate')" src="core/common/images/timeico.gif" width="20" height="22" />
				          </td>
				        </tr>
			   		 </table>
			    </td>
	      	</tr>
	    	<tr>
				<td class="layertdleft100" ><span class="psred">*</span>合同签订日期：</td>
		      	<td class="layerright" ><table width="143" border="0" cellspacing="0" cellpadding="0">
		        	<tbody>
			          <tr>
			            <td>
			            	<input id="signDate" name="contract.signDate" readonly="readonly" type="text" 
				          	value='<fmt:formatDate value="${result.value.signDate}" pattern="yyyy-MM-dd"/>'
			            	class="inputauto" onclick="showCalendar('signDate')"/>
			            </td>
	          			<td width="20" align="center"><img style="position:relative; left:-1px;" onclick="showCalendar('signDate')" src="core/common/images/timeico.gif" width="20" height="22" /></td>
			          </tr>
		        	</tbody>
		     	</table></td>
			    <td class="layertdleft100" ><span class="psred">*</span>登记日期：</td>
		      	<td class="layerright" >
		      		<table width="143" border="0" cellspacing="0" cellpadding="0">
			        	<tbody>
				          <tr>
				            <td>
				            	<input id="receiveDate" name="contract.receiveDate" readonly="readonly" type="text" 
				          		value='<fmt:formatDate value="${result.value.receiveDate}" pattern="yyyy-MM-dd"/>'
				            	class="inputauto" onclick="showCalendar('receiveDate')"/>
				            </td>
		          			<td width="20" align="center">
		          				<img style="position:relative; left:-1px;" onclick="showCalendar('receiveDate')" src="core/common/images/timeico.gif" width="20" height="22" />
		          			</td>
				          </tr>
			        	</tbody>
		      		</table>
		      	</td>
	       	</tr>
	       	<tr>
	    	   	<td class="layertdleft100">总金额：</td>
		      	<td class="layerright">
		      		<input name="contract.contractAmount" 
		      		value='<fmt:formatNumber value="${result.value.contractAmount}" pattern="0.00" />'
		      		type="text" value="0.00" class="inputauto"/>
		      	</td>
	    	    <td class="layertdleft100">预计花费：</td>
		      	<td class="layerright" >
		      		<input name="contract.predictCost" 
		      		value='<fmt:formatNumber value="${result.value.predictCost}" pattern="0.00" />'
		      		type="text" value="0.00" class="inputauto"/>
		      	</td>
	    	</tr>
	    	<tr>
	    	    <td class="layertdleft100">实际花费：</td>
	    		<td class="layerright" >
	    			<input name="contract.actualCost" 
		      		value='<fmt:formatNumber value="${result.value.actualCost}" pattern="0.00" />'
	    			type="text" value="0.00" class="inputauto"/>
	    		</td>
	    		<td class="layerright" colspan="2">
					<label><input name="contract.audit" <c:if test="${result.value.audit eq 'YES'}">checked="checked"</c:if> type="checkbox" onclick="changeState(this);" value="NO" /><span>未审核</span></label>
					<label><input name="contract.finished" <c:if test="${result.value.finished eq 'YES'}">checked="checked"</c:if>type="checkbox" onclick="changeState(this);" value="NO" /><span>完成标志</span></label>
			   		<label><input name="contract.published" <c:if test="${result.value.published eq 'YES'}">checked="checked"</c:if>type="checkbox" onclick="changeState(this);" value="NO" />公开标志</label>
		      	</td>
	    	</tr>
	       	<tr>
	       	    <td class="layertdleft100">合同简介：</td>
		    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
		    		<textarea name="contract.introduction" class="inputauto" style="resize:none;height:40px;">${result.value.introduction }</textarea>
		    	</td>
	       	</tr>
	       	<tr>
	       	    <td class="layertdleft100">备注信息：</td>
		    	<td colspan="3" class="layerright" style="padding-bottom:3px;">
		    		<textarea name="contract.memo" class="inputauto" style="resize:none;height:40px;">${result.value.memo }</textarea>
		    	</td>
	       	</tr>
	  	</table>
	  </div>
	  
	  <!-- 合同文件 -->
	  <div class="divlays tabswitch" style="margin:0px;display:none;padding:0px;">
	  	<div class="basediv" style="margin: 0px;border-left:0px;border-right:0px;border-bottom:0px;">
			<div class="emailtop">
				<div class="leftemail">
					<ul>
						<%if(attAdd){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fileAdd(${result.value.id})"><span><img src="core/common/images/emailadd.gif" /></span>新建</li>
						<%}%>
						<%if(attDel){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fileDeleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
						<%} %>
					</ul>
				</div>
			</div>
			<table id="contractFile" width="100%">
				<tr>
					<td />
				</tr>
			</table>
		</div>
	  </div>
	  
	  <!-- 结算安排 -->
	  <div class="divlays tabswitch" style="margin:0px;display:none;padding:0px;">
	  	<div class="basediv" style="margin: 0px;border-left:0px;border-right:0px;border-bottom:0px;">
			<div class="emailtop">
				<div class="leftemail">
					<ul>
						<%if(add){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="addById(${result.value.id})"><span><img src="core/common/images/emailadd.gif" /></span>新建</li>
						<%}%>
						<%-- <%if(del){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
						<%} %> --%>
					</ul>
				</div>
			</div>
			<table id="contractRent" width="100%">
				<tr>
					<td />
				</tr>
			</table>
		</div>
	  </div>
	  
	  <!-- 实际结算 -->
	  <div class="divlays tabswitch" style="margin:0px;display:none;padding:0px;">
	  	<div class="basediv" style="margin: 0px;border-left:0px;border-right:0px;border-bottom:0px;">
			<%-- <div class="emailtop">
				<div class="leftemail">
					<ul>
						<%if(add){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="billAdd(${result.value.id})">
							<span><img src="core/common/images/emailadd.gif" /></span>新建
						</li>
						<%}%>
						<%if(del){ %>
						<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="billDeleteSelected()">
							<span><img src="core/common/images/emaildel.png"/></span>删除
						</li>
						<%} %>
					</ul>
				</div>
			</div> --%>
			<div></div>
			<table id="contractBill" width="100%">
				<tr>
					<td />
				</tr>
			</table>
		</div>
	  </div>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
 </div>
</form>
</body>
</html>

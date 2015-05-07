<%@page import="com.wiiy.commons.preferences.enums.BooleanEnum"%>
<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String uploadPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initList();
	});
	function initList(){
		var height = getTabContentHeight()-104;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#list").jqGrid({
			url:'<%=basePath%>projectLib!listAll.action',
			colModel: [
				{label:'编号',width:80, name:'orderId',align:"center"},
				{label:'孵化器', name:'incubator.name', align:"center",hidden:true}, 
				{label:'公司', name:'customerName',align:"center"}, 
				{label:'项目名称', name:'name',align:"center"}, 
				{label:'融资期限',width:80,name:'endTime',formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}, align:"center"}, 
				{label:'项目状态',name:'status.title', align:"center"}, 
				{label:'联系电话',name:'phone', align:"center"},
				{label:'所在城市',name:'city.dataValue', align:"center",hidden:true},
				{label:'所属行业',name:'industry.dataValue', align:"center",hidden:true},
				{label:'网址',name:'homePage', align:"center",hidden:true},
				{label:'邮编',name:'zipCode', align:"center",hidden:true},
				{label:'地址',name:'address', align:"center",hidden:true},
				{label:'成立时间',width:80,name:'setupTime',formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'},align:"center",hidden:true},
				{label:'员工人数',name:'employeeCnt', align:"center",hidden:true},
				{label:'公司简介',name:'companyMemo', align:"center",hidden:true},
				{label:'融资额', name:'amount', align:"center",hidden:true},
				{label:'目前状态', name:'status.title', align:"center",hidden:true},
				{label:'营收情况', name:'gainStatus.title', align:"center",hidden:true},
			    {label:'管理',width:70, name:'manager', align:"center", sortable:false, resizable:false}
			    
			],
			height: height,
			width: width,
			shrinkToFit: false,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_del")){%>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_edit")){%>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_generate")){%>
					content += "<img src=\"core/common/images/pageone.png\" width=\"14\" height=\"14\" title=\"生成onepage\" onclick=\"onepageById('"+id+"');\"  /> ";
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
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function onepageById(id){
		if(confirm("是否需要提交到项目中心")){
			window.open('<%=basePath%>projectLib!view.action?id='+id);
		}
	}
	function editById(id){
		fbStart('编辑项目信息','<%=basePath%>projectLib!edit.action?id='+id,600,495);
	}
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>projectLib!delete.action?id="+id,function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function deleteByIds(){
		var selectedRowIds =   
		$("#list").jqGrid("getGridParam","selarrrow"); 
		if(confirm("您确定要删除")){
			if(selectedRowIds==""){
				showTip("请选择删除项",2000);
			}else{
				$.post("<%=basePath%>projectLib!delete.action?ids="+selectedRowIds,function(data){
					showTip(data.result.msg,1000);
					$("#list").trigger("reloadGrid");
				});
			}
		}
	}
	
	function doSearch(){
		search(getSearchFilters());
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
	}
	
	function exportDate(){
		var columns = "{";
		$.each($("#list").getGridParam("colModel"),function(){
			if(this.label && this.name!="manager" && !this.hidden){
				columns += "\"" + this.name + "\"" + ":" + "\"" + this.label + "\",";
			}
		});
		columns = deleteLastCharWhenMatching(columns,",");
		columns += "}";
		$("#columns").val(columns);
		$("#exportForm").submit();
	}
</script>
</head>

<body>
<form action="<%=basePath%>projectLib!export.action" id="exportForm" method="post">
	<input type="hidden" id="columns" name="columns"/>
	<input type="hidden" id="filters" name="filters"/>
</form>
<!--emailtop-->
<div class="emailtop">
			<div class="leftemail">
				<ul>
				<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_add")){%>
					<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStartOnScroll('新建项目','<%=basePath %>projectLib!add.action',620,530);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
				<%} %>
				<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_del")){%>
					<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteByIds()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
				<%} %>
				<%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_exp")){%>
					<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="exportDate()"><span><img src="core/common/images/database_(start)_16x16.gif"/></span>导出</li>
				<%} %>
				</ul>
			</div>
		</div>
<!--//emailtop-->

<div class="searchdiv">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
      <tr>
 <%--        <td width="50" align="right">孵化器：</td>
        <td width="105">
        	<%if(BusinessActivator.getSessionUser(request).getAdmin().equals(BooleanEnum.YES)){%>
				<search:choose dataType="string" field="incubatorId" op="eq">
		      		<select class="data" id="incubator">
		      			<option value="">----请选择----</option>
		      			<c:forEach items="${incubators}" var="incubator">
					        <option value="${incubator.id}">${incubator.name}</option>
		      			</c:forEach>
	      			</select>
      			</search:choose>
			<%}else{ %>
				<search:choose dataType="string" field="incubatorId" op="eq">
		      		<select class="data" id="incubator">
		      			<c:forEach items="${incubators}" var="incubator">
					        <option value="${incubator.id}">${incubator.name}</option>
		      			</c:forEach>
	      			</select>
      			</search:choose>
			<%} %>	
   	 	</td> --%>
        <td width="65" align="right">项目名称：</td>
        <td width="120"><label>
        <search:input id="name" dataType="string" field="name" op="cn" inputClass="inputauto" />
        </label></td>
		<td width="65" align="right">融资期限：</td>
        <td width="105"><table width="100%" border="0" cellspacing="0" cellpadding="10">
          <tr>
            <td width="90">
            	<search:choose dataType="java.util.Date" field="endTime" op="eq">
            		<input id="endTime" class="data inputauto" onclick="return showCalendar('endTime')"/>
            	</search:choose>
            </td>
            <td width="20">
            	<img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer; relative; left:-1px;" onclick="return showCalendar('endTime');" />
            </td>
	      </tr>
        </table></td>
        <td width="85" align="center"> <input name="Submit" type="submit" class="searchbtn" value="" onclick="doSearch()"/></td>
        <td>&nbsp;</td>
      </tr>
    </table>
</div>
<!--container-->
<div id="container">
<div class="msglist" >
  <table id="list" class="jqGridList"><tr><td/></tr></table>
  <div id="pager"></div>
</div>
</div>
<!--//container-->
</body>
</html>

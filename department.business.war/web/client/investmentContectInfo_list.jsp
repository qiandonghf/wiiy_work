<%@page import="com.wiiy.business.activator.BusinessActivator"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="search" uri="http://www.wiiy.com/taglib/search" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
 <%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initTip();
		initList("list");
	});
	function initList(list){
		
/* 		var iDay = "${param.iDay}";
		var eLevel = "${param.enumLevel}";
		var sCommentType = "${param.sCommentType}";
*/
		var sMy = "${param.my}";
		
		
 		var para = "commentType=${param.sCommentType}";
 		para += "&day=${param.iDay}";
 		para += "&enumLevel=${param.enumLevel}";
 
		if( null != sMy && "" != sMy ){
			var url = "<%=basePath%>"+"investmentContectInfo!listMy.action?"+ para;
		}else{
			var url = "<%=basePath%>"+"investmentContectInfo!list.action?"+ para;	
		}
//		alert(url);
		
		var associate = "${param.associate }";
		if(null != associate && "" != associate){
			url += url + "&associate="+associate;
		}
		
		var height = getTabContentHeight()-105;
		var width = window.parent.document.documentElement.clientWidth-(window.screenLeft-window.parent.screenLeft);
		$("#"+list).jqGrid({
	    	url:url,
			colModel: [
				{label:'姓名', name:'name', align:"center",width:180}, 
				{label:'联系电话', name:'phone', align:"center",width:100}, 
				{label:'日期', name:'startTime',width:140,formatter:'date',formatoptions:{srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, width:120, align:"center"}, 
				{label:'接待人员', name:'receiver.realName',width:100, align:"center"}, 
				{label:'产业', name:'pruduct', width:100, align:"center",hidden:true},
				{label:'需求', name:'demand', width:100, align:"center",hidden:true},
				{label:'线索等级', name:'level.title', width:60, align:"center"},
				{label:'是否关联项目', name:'associate.title', width:80, align:"center"},
				{label:'回访提醒', name:'returnVisit', width:140,formatter:'date',formatoptions:{srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, width:120, align:"center",hidden:true},
				{label:'需求', name:'demand', width:100, align:"center",hidden:true},
				{label:'状态', width:'100' ,name:'contectInfoStatus.title', align:"center",formatter:checkStatus}, 
			    {label:'管理', name:'manager', align:"center", sortable:false, resizable:false,width:100}
			    
			],
			shrinkToFit: false,
			height: height,
			width: width,
			gridComplete: function(){
				var ids = $(this).jqGrid('getDataIDs');
				for(var i = 0 ; i < ids.length; i++){
					var id = ids[i];
					var content = "";
					var stateTxt = $(this).getCell(id,'contectInfoStatus.title');
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_changeState")){%>
					if(stateTxt == '开启')
						content += "<img src=\"core/common/images/back.png\" width=\"14\" height=\"14\" title=\"关闭\" onclick=\"changeState('"+id+"');\"  /> ";
					else
						content += "<img src=\"core/common/images/uploadicon.png\" width=\"14\" height=\"14\" title=\"开启\" onclick=\"changeState('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_view")){ %>
					content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_edit")){ %>
					content += "<img src=\"core/common/images/edit.gif\" width=\"14\" height=\"14\" title=\"编辑\" onclick=\"editById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_delete")){ %>
					content += "<img src=\"core/common/images/del.gif\" width=\"14\" height=\"14\" title=\"删除\" onclick=\"deleteById('"+id+"');\"  /> ";
					<%}%>
					<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_investmentRepeatedViste_add")){ %>
						content += "<img src=\"core/common/images/emailadd.gif\" width=\"14\" height=\"14\" title=\"增加回访\" onclick=\"addById('"+id+"');\"  /> ";
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
	
	function checkStatus(cellvalue,options,rowObject){
		if(cellvalue =='开启'){
			return "开启";
		}
		return "关闭";
	}
	
	function changeState(id){
		$.post("<%=basePath%>investmentContectInfo!changeState.action?id="+id,function(data){
			showTip(data.result.msg,2000);
			if(data.result.success){
				$("#list").trigger("reloadGrid");
			}
		});
	}
	
	function reloadList(){
		$("#list").trigger("reloadGrid");
	}
	function addById(id){
		fbStart('新建回访','<%=basePath %>web/client/investmentRepeatedViste_add.jsp?infoId=${result.value.id }'+id,480,200);
	}
	function viewById(id){
		fbStart('查看','<%=basePath%>investmentContectInfo!view.action?id='+id,600,500);
	}
	function editById(id){
		fbStart('编辑','<%=basePath%>investmentContectInfo!edit.action?id='+id,630,275);
	}
	function deleteById(id){
		if(confirm("您确定要删除")){
			$.post("<%=basePath%>investmentContectInfo!delete.action?id="+id,function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function deleteByIds(){
		var selectedRowIds =   
		$("#list").jqGrid("getGridParam","selarrrow");
		if(selectedRowIds==""){
			showTip('请先选择',2000);
		}else if(confirm("您确定要删除")){
			$.post("<%=basePath%>investmentContectInfo!delete.action?ids="+selectedRowIds,function(data){
				showTip(data.result.msg,1000);
				$("#list").trigger("reloadGrid");
			});
		}
	}
	function doSearch(){
		search(getSearchFilters());
	}
	function addCustomTab(title,icon,url){
		parent.addTab(title,icon,url);
	}
	function search(filters){
		$("#list").jqGrid('setGridParam',{page:1,postData:{filters:filters}}).trigger('reloadGrid');
		/* $("#startTime1").val("");
		$("#startTime2").val(""); */
	}
</script>

</head>

<body>

<div class="emailtop">
	<div class="leftemail">
		<ul>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_add")){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''"onclick="fbStart('新建线索','<%=basePath %>web/client/investmentContectInfo_add.jsp',630,250);"><span><img src="core/common/images/emailadd.gif" width="15" height="13" /></span>新建</li>
		<%} %>
		<%if(BusinessActivator.getHttpSessionService().isInResourceMap("investment_contectInfo_delete")){ %>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteByIds()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
		<%} %>
		</ul>
	</div>
	<!--//leftemail-->
</div>
<div class="searchdivkf">
    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
     <tr>
       <td width="10">关键字： </td>
        <td width="110"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<td>
            	 <search:input  dataType="string" field="infoAll" op="cn" inputClass="inputauto" />
          	</td>
          </tr>
        </table>       
        </td>
		<td width="50" align="right" style="padding-left: 10px">来电来访时间：</td>
         <td width="230">
	        <table width="100%" border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="startTime" op="ge">
	            		<input id="startTime1" class="data inputauto" onclick="return showCalendar('startTime1')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime1');" />
	            </td>
	            <td>--</td>
	            <td width="90">
	            	<search:choose dataType="java.util.Date" field="startTime" op="le">
	            		<input id="startTime2" class="data inputauto" onclick="return showCalendar('startTime2')"/>
	            	</search:choose>
	            </td>
	            <td width="20">
	            	<img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('startTime2');" />
	            </td>
	          </tr>
	        </table>
	    </td>
	    <td width="70" align="right">线索等级：</td>
		<td width="105">
			<search:choose id="level"  dataType="com.wiiy.business.preferences.enums.LevelEnum" field="level" op="eq" >
				<enum:select styleClass="data" type="com.wiiy.business.preferences.enums.LevelEnum" defaultValue="${param.type }"/>
			</search:choose>
		</td>
        <td width="75" align="center"><label><img src="core/common/images/search.jpg" width="75" height="22" border="0" usemap="#Map" /></label></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
</div>
<div id="container">
	<div class="msglist" id="msglist">
	  <table id="list" class="jqGridList"><tr><td/></tr></table>
	  <div id="pager"></div>
	</div>
</div>
<map name="Map" id="Map">
<area shape="rect" coords="3,2,53,22"  href="javascript:vide(0)" onclick="doSearch()" />
<area shape="rect" coords="56,1,73,20" href="javascript:vido(0)" onclick="fbSearch('高级搜索','<%=basePath %>web/client/investmentContectInfo_search.jsp',500,165);" />
</map></body>
</html>

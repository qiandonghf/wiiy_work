<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.core.preferences.enums.ContactTypeEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String url = BaseAction.rootLocation+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="core/common/style/base.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/smartMenu.css" />
<link rel="stylesheet" type="text/css" href="parkmanager.pb/web/style/merchants.css"/>
<style type="text/css">
			.highlight {
				background: #f4f4f4
			}
</style>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/righth.js"></script>
<script type="text/javascript" src="core/common/js/layertable.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript"> 
	function pageheight(nameid1,height1,nameid2,height2){//获取屏幕高度
		var bodyh=window.parent.document.documentElement.clientHeight-height1;
		var bodyh1=window.parent.document.documentElement.clientHeight-height2;
		var bodyw=window.parent.document.documentElement.clientWidth-590;
		document.getElementById(nameid1).style.height=bodyh+"px";
		document.getElementById(nameid2).style.height=bodyh1+"px";
		//document.getElementById(nameid2).style.width=bodyw+"px";
	}
	$(document).ready(function() {
		pageheight('resizable',88,'pm_msglist',91);
		$('#contactdiv').css('height',getTabContentHeight()-58);
		initTip();
		afterList();
		 //右击菜单
		initMenu();
	});
 
	function initMenu(){
		var imageMenuData = [
		                  	  [{
		                  			text: "打开",
		                  			classname: "smarty_menu_view",
		                  			func: function() {
		                  				var type = $(this).find("input").val();
		                  				var id = $(this).find("input").next().val();
		                  				openRight(type,id);
		                  			}
		                  		},{
		                  			text: "打印",
		                  			classname: "smarty_print",
		                  			func: function() {
		                  				var type = $(this).find("input").val();
		                  				var id = $(this).find("input").next().val();
		                  				print(type,id);
		                  			}
		                  		}
		                  		
		                  		]];
		   	
		   	$(".contact").smartMenu(imageMenuData,{name:'table'});
	}
	
	function print(type,id){
		if('<%=ContactTypeEnum.INVESTMENTCONTACT%>' == type){
			location.href = "<%=url%>parkmanager.pb/investmentContact!print.action?id="+id;
		}else if('<%=ContactTypeEnum.RENTOUTCONTACT%>' == type){
			location.href = "<%=url%>parkmanager.pb/rentOutContact!print.action?id="+id;
		}else if('<%=ContactTypeEnum.BUSINESSCENTERCONTACT%>' == type){
			location.href = "<%=url%>parkmanager.pb/businessCenterContact!print.action?id="+id;
		}else if('<%=ContactTypeEnum.TENEMENTCENTERCONTACT%>' == type){
			location.href = "<%=url%>parkmanager.pb/tenementCenterContact!print.action?id="+id;
		}else if('<%=ContactTypeEnum.FINANCECONTACT%>' == type){
			location.href = "<%=url%>parkmanager.pb/financeContact!print.action?id="+id;
		}else if('<%=ContactTypeEnum.CARPORTOUTCONTACT%>' == type){
			location.href = "<%=url%>parkmanager.pb/carportOutContact!print.action?id="+id;
		}else if('<%=ContactTypeEnum.CUSTOMERSERVICECONTACT%>' == type){
			location.href = "<%=url%>parkmanager.pb/customerServiceContact!print.action?id="+id;
		}
	}
	
	function afterList(){
		if($("#afterType").val()!='' && $("#afterId").val()!=''){
			openRight($("#afterType").val(),$("#afterId").val());
		}
	}
	function openRight(type,id){
		var currentTime = new Date().getTime();
		var src = "<%=basePath%>contact!view.action?type="+type+"& id="+id+"&currentTime="+currentTime;
		$("#pm_msglist").attr("src",src);
		/* var src = $("#pm_msglist").attr("src");
		alert(src); */
	}
	function edit(type,id){
		if(type == '<%=ContactTypeEnum.INVESTMENTCONTACT%>'){
			fbStart('编辑联系单','/parkmanager.pb/investmentContact!edit.action?id='+id,700,538);
		}
	}
	function highlight(el){
		$(el).addClass("highlight");
		$(el).siblings().removeClass("highlight").css("background","#fff");
	}
	function keepHighlight(el){
		if($(el).hasClass("highlight")){
			$(el).css("background","#f4f4f4");
		}
	}
	function slide(id){
		if($('#'+id).is(':hidden')){
			$('#'+id).slideDown();
		}else{
			$('#'+id).slideUp();
		}	   
	}
 	function jumpPage(page){
		var url = "<%=basePath%>contact!listMyAll.action";
		url += "?page="+page;
		url = encodeURI(url);
		location.href=url;
	}
 	function reloadList(type,id){
 		var currentTime = new Date().getTime();
 		var src = "<%=basePath%>contact!myDidCountersignList.action?type="+type+"& id ="+id+"& currentTime="+currentTime;
 		this.location.href=src;
 	}
 	function setSelectedUser(user){
		var type = $("#afterType").val();
		var id= $("#afterId").val();
		var receiveId = user.id;
		var approvalType = $("#approvalType").val();
		if(type == 'INVESTMENTCONTACT'){
			var url="<%=url%>parkmanager.pb/investmentContact!send.action?id="+id+"&receiveId="+receiveId+"&approvalType="+approvalType;
			$.post(url,function(data){
				showTip(data.result.msg,2000);
				location.reload();
			});
		}
	}
 	function refresh(){
 		this.location.href="<%=basePath%>contact!myDidCountersignList.action";
 	}
</script>
<jsp:include page="investment_contact_menu.jsp" />
</head>
 
<body>
<div class="emailtop">
	<!--leftemail-->
	<div class="leftemail">
	<input id="approvalType" type="hidden" name="approvalType"/>
	<input id="afterType" value="${type }" type="hidden" />
	<input id="afterId" value="${id }"  type="hidden"/>
		<ul>
			<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="refresh()"><span><img src="core/common/images/refresh3.png" /></span>刷新</li>
        </ul>
	</div>
	<!--//leftemail-->
</div>
<!--container-->
<div id="container">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="355" valign="top" id="fee_lefts">
	  <div class="write_list" style="border-right:1px solid #ddd; border-bottom:none; width:355px;" id="resizable">
	  	<div class="searchdiv">
          <form id="form2" name="form2" method="post" action="">
            <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                 <tr><td width="65">客户名称：</td>
              <td width="200"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody><tr>
                  <td><input name="textfield" type="text" class="inputauto"></td>
                  <td width="25" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="fbStart('输入客户名称或点击‘筛选’','parkmanager.pb/web/client/pm_customer_list_xz.html',520,400);"></td>
                </tr>
              </tbody></table></td>
              
              <td align=""><input name="Submit" type="button" class="search_cx" value=" " /></td>
            </tr>
          </tbody></table>
          </form>
	  	  </div>
        <!--merter_fee-->
        <div id="contactdiv" style="overflow-x: hidden;overflow-y:auto;">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="lsittable">
              <tr>
                <td class="tdcenterL">联系单</td>
                <td align="center" class="tdleftc">发起人</td>
                <td align="center" class="tdleftc">最后处理时间</td>
                <td width="35" class="tdrightc"><img src="core/common/images/rightgray.png" width="7" height="7"></td>
              </tr>
              <c:forEach items="${dtoList }" var="dto">
	              <tr onmouseover="this.style.background='#f4f4f4'"  onmouseout="this.style.background='#fff';keepHighlight(this)" style="cursor: pointer;" onclick="openRight('${dto.type}',${dto.id });highlight(this);" class="contact">
	                <td class="lefttd">${dto.type.title }<input type="hidden" id="type" value="${dto.type }"/><input type="hidden" id="id" value="${dto.id }"/></td>
	                <td align="center" class="centertd">${dto.userName }</td>
	                <td align="center" class="centertd"><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.modify_time }"/></td>
	                <td class="centertd">
	                	<c:if test="${dto.status eq 'SUSPEND' }">
	                		<img src="core/common/images/gth.png" width="7" height="7" title="挂起" alt="挂起"/>
	                	</c:if>
	                	<%-- <c:if test="${dto.status eq 'ACCEPT' }">
	                		<img src="core/common/images/rightgray.png" width="7" height="7" title="受理" alt="受理"/>
	                	</c:if> --%>
	                	<c:if test="${dto.status eq 'CLOSE' }">
	                		<img src="core/common/images/rightgreen.png" width="7" height="7" title="完成" alt="完成"/>
	                	</c:if>
	                </td>
	              </tr>
              </c:forEach>
            </table>
			<!--分页开始-->
			<%@include file="../pager.jsp" %>
			<!--分页结束-->
			</div>
	    </div>
 	  </td>
      <td valign="top">
	  	<!--table切换开始-->
			<!--//table切换开始-->
			<div class="msglist" style=" border-color:#fff;">
			<iframe src="core/web/contact/contact_index.jsp"  frameborder="0" id="pm_msglist" width="100%" name="app"></iframe>
			</div>
	  </td>
    </tr>
  </table>
</div>
 <!--//container-->
</body>
 
</html>

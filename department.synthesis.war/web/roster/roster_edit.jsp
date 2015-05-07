<%--@elvariable id="enums" type="java.util.Map<String, Map<String, String>>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.wiiy.commons.action.BaseAction"%>
<%@ page import="com.wiiy.commons.preferences.enums.EntityStatus"%>
<%@ page import="com.wiiy.core.entity.User"%>
<%@ page import="com.wiiy.synthesis.entity.Archives"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@page import="com.wiiy.synthesis.activator.SynthesisActivator"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/calendar/calendar.css" rel="stylesheet" type="text/css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="core/common/style/ui.multiselect.css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="core/common/js/ui.multiselect.js"></script>
<script type="text/javascript" src="core/common/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="core/common/js/jquery.jqGrid.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
			initTip();
	   		initUploadify("fileUpload");
	   		initRoleList();
	   		initGroupList();
	   		$(".changeDiv").height(172);
	   		$(".msglist").css("padding-bottom","0px");
	   		$(".msglist").css("border-top","#c9c9c9 1px solid");
	   		initList();
	   		initList1();
	   		initList3();
			initForm();
        });
        function initRoleList(){
        	$("#roleList > li").each(function(index,li){
            	  $(this).click(function(e){
           		  $(this).find("a").toggleClass("bgli");
           	  }); 
           	  $(this).dblclick(function(e){
           		  swap("#selectedRoles", "#roleList", this);
           	  }); 
              });
             $("#selectedRoles > li").each(function(index,li){
             	  $(this).click(function(e){
           		  $(this).find("a").toggleClass("bgli");
           	  }); 
           	  $(this).dblclick(function(e){
           		  swap("#roleList", "#selectedRoles", this);
           	  }); 
              });
             $("#goLeft").click(function(e){
          	   $("#roleList .bgli").each(function (index, a){
             		  swap("#selectedRoles", "#roleList", a.parentNode);
          	   });
               });
             $("#goRight").click(function(e){
          	   $("#selectedRoles .bgli").each(function (index, a){
             		  swap("#roleList", "#selectedRoles", a.parentNode);
          	   });
               });
        }
        
        function initGroupList(){
        	$("#groupList > li").each(function(index,li){
          	  $(this).click(function(e){
         		  $(this).find("a").toggleClass("bgli");
         	  }); 
         	  $(this).dblclick(function(e){
         		  swap("#selectedGroups", "#groupList", this);
         	  }); 
            });
           $("#selectedGroups > li").each(function(index,li){
           	  $(this).click(function(e){
         		  $(this).find("a").toggleClass("bgli");
         	  }); 
         	  $(this).dblclick(function(e){
         		  swap("#groupList", "#selectedGroups", this);
         	  }); 
            });
           $("#goGroupLeft").click(function(e){
        	   $("#groupList .bgli").each(function (index, a){
           		  swap("#selectedGroups", "#groupList", a.parentNode);
        	   });
             });
           $("#goGroupRight").click(function(e){
        	   $("#selectedGroups .bgli").each(function (index, a){
           		  swap("#groupList", "#selectedGroups", a.parentNode);
        	   });
             });
        }
    	function reloadList(){
    		$("#list").trigger("reloadGrid");
    	}
    	function reloadList3(){
    		$("#list3").trigger("reloadGrid");
    	}
        function initForm() {
    		$("#userForm").validate({
    			rules: {
    				"user.username":"required",
    				"user.realName":"required",
    				"user.userType":"required",
    				"user.email" : {"required":true, "email":true},
    				"user_org_name" : "required",
        			"user.msn" : "email",
        			"archives.name" : "required"
    			},
    			messages: {
    				"user.username":"请输入用户名",
    				"user.realName":"请输入用户真实姓名",
    				"user.userType":"请选择用户类型",
    				"user.email" : {"required":"请输入email", "email":"email格式不对"},
    				"user_org_name" : "请选择所属机构",
    				"user.msn" : "msn格式不对",
    				"archives.name":"请输入员工姓名"
    			},
    			errorPlacement: function(error, element){
    				showTip(error.html());
    			},
    			submitHandler: function(form){
   	         	   $("#user_imagery").val($("#user_imagery_img").attr("src"));
    				$('#userForm').ajaxSubmit({ 
    			        dataType: 'json',		        
     					beforeSubmit:function(formData, jqForm, options) {
     						$("#selectedRoles > li").each(function(index, li){
     							formData[formData.length]={name:"user.roleRefList["+index+"].role.id", value:$(li).attr("roleId")};
     						});
     						return true;
     					},
     					success:function(root, statusText) {
     						if (root.result.success) {
     						    getOpener().refreshDataTables();
     						   // parent.$.showMessage("保存成功！");
     						    fb.end();
     						} else {
     						    showTip(root.result.msg);
     						}
     	                }
    			    });
    			}
    		});
        }
    	function initUploadify(id){
    		$("#"+id).uploadify( {
    			'auto'				: true, //是否自动开始 默认true
    			//'multi'				: false, //是否支持多文件上传 默认true
    			'formData'			: {'module':'core','directory':uploadify.directory.images},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
    			'uploader'			: "<%=BaseAction.rootLocation%>core/upload.action",
    			'swf'				: uploadify.swf,//上传组件swf
    			'width'				: "30",//按钮图片的长度
    			'height'			: uploadify.height,//按钮图片的高度
    			'buttonText'		: "上传",
    			'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
    			'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
    			'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
    			'fileTypeDesc'		: uploadify.images.fileTypeDesc,//选择文件对话框文件类型描述信息
    			'fileTypeExts'		: uploadify.images.fileTypeExts,//可上传上的文件类型
    			'onUploadSuccess'	: onUploadSuccess//上传成功回调函数 有三个参数 file(可取文件名  注：上传前的文件名不是服务器端重写的文件名) data(服务器端写到response里面的信息) response(true,false)
    		});
    	}
        function initList(){
        	var height = $(".changeDiv").height()-112;
        	var width = $(this).width()-15;
        	$("#list").jqGrid({
            	url:'<%=basePath%>laborContract!list.action?id=${user.id}',
        		datatype: 'json',
        		prmNames: {search: "search"},
        		jsonReader: {root:"root",repeatitems: false},
        		colModel: [
        		    {label:'员工姓名', name:'name', index:'name',width:60, align:"center"}, 
        		    {label:'签订日期', name:'signingDate', index:'signingDate', width:110, align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
        		    {label:'合同开始时间', name:'startTime', index:'startTime', width:110, align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
        		    {label:'合同结束时间', name:'endTime', index:'endTime', width:110, align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
        		    {label:'管理', name:'manager', width:92, align:"center", title:false, sortable:false, resizable:false} 
        		],
        		height: height,
        		width: width,
        		sortname:'createTime',
        		sortorder:'asc',
        		rowNum: 3,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
        		rowList: [10,20,30],//用来调整表格显示的记录数
        		multiselect: true,//是否可以多选
        		viewrecords: true,//是否显示总记录数
        		rownumbers: true,//显示行顺序号，从1开始递增。此列名为'rn'
        		loadui:'disable',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
        		pager: '#pager',//指定分页栏对象
        		gridComplete: function(){
        			var ids = $(this).jqGrid('getDataIDs');
        			for(var i = 0 ; i < ids.length; i++){
        				var id = ids[i];
        				var content = "";
        				content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" alt=\"查看\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
        				$(this).jqGrid('setRowData',id,{manager:content});
        			}
        		},
        		gridview: true
        	})
        	.navGrid('#pager',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager',{
        	    caption : "列选择",
        	    title : "选择要显示的列",
        	    onClickButton : function(){
        	        $(this).jqGrid('columnChooser');
        	    }
        	});
        }
        function initList1(){
        	var height = $(".changeDiv").height()-85;
        	var width = $(this).width()-15;
        	$("#list1").jqGrid({
            	url:'<%=basePath%>laborContract!list.action?id=${user.id}',
        		datatype: 'json',
        		prmNames: {search: "search"},
        		jsonReader: {root:"root",repeatitems: false},
        		colModel: [
        		    {label:'员工姓名', name:'name', index:'name',width:120, align:"center"}, 
        		    {label:'考勤日期', name:'signingDate', index:'signingDate', width:212, align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
        		    {label:'考勤状态', name:'name', index:'name',width:120, align:"center"}, 
        		    {label:'管理', name:'manager', width:60, align:"center", title:false, sortable:false, resizable:false} 
        	],
        		height: height,
        		width: width,
        		sortname:'createTime',
        		sortorder:'asc',
        		rowNum: 4,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
        		rowList: [10,20,30],//用来调整表格显示的记录数
        		multiselect: false,//是否可以多选
        		viewrecords: true,//是否显示总记录数
        		rownumbers: true,//显示行顺序号，从1开始递增。此列名为'rn'
        		loadui:'disable',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
        		pager: '#pager1',//指定分页栏对象
        		gridComplete: function(){
        			var ids = $(this).jqGrid('getDataIDs');
        			for(var i = 0 ; i < ids.length; i++){
        				var id = ids[i];
        				var content = "";
        				content += "<img src=\"core/common/images/viewbtn.gif\" width=\"14\" height=\"14\" alt=\"查看\" title=\"查看\" onclick=\"viewById('"+id+"');\"  /> ";
        				$(this).jqGrid('setRowData',id,{manager:content});
        			}
        		},
        		gridview: true
        	})
        	.navGrid('#pager1',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager1',{
        	    caption : "列选择",
        	    title : "选择要显示的列",
        	    onClickButton : function(){
        	        $(this).jqGrid('columnChooser');
        	    }
        	});
        }
          function initList3(){
          	var height = $(".changeDiv").height()-112;
        	var width = $(this).width()-15;
        	$("#list3").jqGrid({
            	url:'<%=basePath%>rosterAtt!list.action?id=${user.id}',
        		datatype: 'json',
        		prmNames: {search: "search"},
        		jsonReader: {root:"root",repeatitems: false},
        		colModel: [
        		    {label:'附件名称', name:'name', index:'name',width:120, align:"center"}, 
        		    {label:'上传时间', name:'createTime', index:'createTime', width:182, align:"center",formatter:'date',formatoptions:{srcformat:'Y-m-d',newformat:'Y-m-d'}},
        		    {label:'上传人', name:'creator', index:'creator',width:120, align:"center"}, 
        			{label:'路径', name:'newName', align:"center",hidden:true}, 
        		    {label:'操作', name:'manager', width:60, align:"center", title:false, sortable:false, resizable:false} 
        	],
        		height: height,
        		width: width,
        		sortname:'createTime',
        		sortorder:'asc',
        		rowNum: 3,//设置表格中显示的记录数如果服务器返回记录数大于此值则只显示rowNum条 -1不检查此项
        		rowList: [10,20,30],//用来调整表格显示的记录数
        		multiselect: true,//是否可以多选
        		viewrecords: true,//是否显示总记录数
        		rownumbers: true,//显示行顺序号，从1开始递增。此列名为'rn'
        		loadui:'disable',//当执行ajax请求时 启用Loading提示，但是阻止其他操作
        		pager: '#pager3',//指定分页栏对象
        		gridComplete: function(){
        			var ids = $(this).jqGrid('getDataIDs');
        			for(var i = 0 ; i < ids.length; i++){
        				var id = ids[i];
        				var content = "";
 						content += "<img src=\"core/common/images/down.png\" width=\"14\" height=\"14\" title=\"下载\" onclick=\"downLoad('"+id+"');\"  /> "; 
        				$(this).jqGrid('setRowData',id,{manager:content});
        			}
        		},
        		gridview: true
        	})
        	.navGrid('#pager3',{add: false, edit: false, del: false, search: false}).navButtonAdd('#pager3',{
        	    caption : "列选择",
        	    title : "选择要显示的列",
        	    onClickButton : function(){
        	        $(this).jqGrid('columnChooser');
        	    }
        	});
        } 
      	function downLoad(id){
      		var path = $("#list3").getCell(id,5);
 			var name = $("#list3").getCell(id,2);
 			location.href="<%=BaseAction.rootLocation%>/core/resources/"+path+"?name="+name;
    	}
    	function viewById(id){
    		fbStart('查看','<%=basePath %>laborContract!view.action?id='+id,650,307);
    	}
        function swap(targetUl, sourceUl, li) {
        	$(li).remove();
        	$(targetUl).append($(li));
        	$(li).find("a").removeClass("bgli");
         	$(li).click(function(e){
         		$(this).find("a").toggleClass("bgli");
         		}); 
        	$(li).dblclick(function(e) {
        		swap(sourceUl, targetUl, this);
        		});
        }
        function setSelectedOrg(selectedOrg) {
        	$("#user_org_id").val(selectedOrg.id);
        	$("#user_org_name").val(selectedOrg.name);
        }
    	function onUploadSuccess(file, data, response) {
    		$("#user_imagery_img").attr("src", "core/resources/"+$.parseJSON(data).path);
 		}
    	function startUpload(id){
    		$("#"+id).uploadify("upload");
    	}
    	function removeImagery() {
    		var userImagery = $("#user_imagery_img").attr("src");
    		$("#user_imagery_img").attr("src", userImagery + "-d");
    		$("#user_imagery").val("core/common/images/topxiao.gif");
    		$("#user_imagery_img").attr("src", "core/common/images/topxiao.gif");
    	}
    	function deleteSelected(){
    		if(confirm("确定要删吗")){
    			var ids = $("#list").jqGrid("getGridParam","selarrrow");
    			$.post("<%=basePath%>laborContract!delete.action?ids="+ids,function(data){
    				showTip(data.result.msg,2000);
    	        	if(data.result.success){
    	        		$("#list").trigger("reloadGrid");
    	        	}
    			});
    		}
    	}
    	function deleteSelected1(){
    		if(confirm("确定要删吗")){
    			var ids = $("#list3").jqGrid("getGridParam","selarrrow");
    			$.post("<%=basePath%>rosterAtt!delete.action?ids="+ids,function(data){
    				showTip(data.result.msg,2000);
    	        	if(data.result.success){
    	        		$("#list3").trigger("reloadGrid");
    	        	}
    			});
    		}
    	}
    </script>
</head>

<body>
<form id="userForm" name="userForm" method="post" action="<%=basePath%>roster!update.action">
<input type="hidden" name="user.id" value="${user.id}" />
<input type="hidden" name="archives.id" value="${archives.id}" />
<%-- <input type="hidden" name="laborContract.userId" value="${laborContract.userId}"/> --%>
<div id="scrollDiv" style=" position:relative;">			
<div class="basediv">
	<!--titlebg-->
	<div class="layertitle">基本信息</div>
	<!--//titlebg-->
	<!--divlay-->
	<div class="divlays">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="50%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="layertdleft"><span class="psred">*</span>用户名：</td>
              <td><label>
                <input name="user.username" type="text" class="input200" value="${user.username}"/>
              </label></td>
            </tr>
            <tr>
              <td class="layertdleft"><span class="psred">*</span>真实姓名：</td>
              <td><input name="user.realName" type="text" class="input200" value="${user.realName }"/></td>
            </tr>
            <tr>
              <td class="layertdleft"><span class="psred">*</span>管理员：</td>
              <td>&nbsp;
                 <enum:radio name="user.admin" type="com.wiiy.commons.preferences.enums.BooleanEnum" checked="user.admin" />
              </td>
            </tr>
            <tr>
              <td class="layertdleft">出生年月：</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="170"><input name="user.birthday" type="text" class="input170" id="user.birthday" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${user.birthday}"/>' /></td>
                  <td width=20><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left: -1px;" onclick="return showCalendar('user.birthday', 'y-mm-dd');"/></td>
			      <td>&nbsp;</td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td class="layertdleft">移动电话：</td>
              <td><input name="user.mobile" type="text" class="input200" value="${user.mobile}"/></td>
            </tr>
          </table>
          </td>
          <td valign="top">
          <table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
			<tr>
              <td width="96">
              <c:if test="${user.imagery != null}">
              <img id="user_imagery_img" src="${user.imagery}" width="107" height="110" class="touxian" />
              </c:if>
              <c:if test="${user.imagery == null}">
              <img id="user_imagery_img" src="core/common/images/topxiao.gif" width="107" height="110" class="touxian" />
              </c:if>
              </td>
              <td width="15" align="center" valign="bottom"><img src="core/common/images/xtopico3.png" width="13" height="13" onclick="removeImagery()" /></td>
              <td valign="top" style="padding-top:12px;">
				<input type="file" id="fileUpload" />
		      	<input  type="hidden" id="user_imagery" name="user.imagery" value="${user.imagery}"/>
		      </td>
            </tr>
          </table>
          </td>
        </tr>
      </table>
	  
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="50%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="layertdleft">固定电话：</td>
              <td><input name="user.telephone" type="text" class="input200" value="${user.telephone}"/></td>
            </tr>
            <tr>
              <td class="layertdleft"><span class="psred">*</span>Email：</td>
              <td><label>
                <input name="user.email" type="text" class="input200" value="${user.email}"/>
              </label></td>
            </tr>
            <tr>
              <td class="layertdleft">MSN：</td>
              <td><input name="user.msn" type="text" class="input200" value="${user.msn}"/></td>
            </tr>

          </table></td>
          <td valign="top"><table width="99%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td class="layertdleft">性别：</td>
              <td><label>
				&nbsp;<enum:radio name="user.gender" type="com.wiiy.commons.preferences.enums.GenderEnum" checked="user.gender" />
              </label></td>
            </tr>
            <tr>
              <td class="layertdleft"><span class="psred">*</span>所属机构：</td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                  <input id="user_org_id" name="user.org.id" type="hidden" value="${user.org.id}"/>
                  <input id="user_org_name" name="user_org_name" type="text" class="input170" value="${user.org.name}" readonly="readonly"/>
                  </td>
                  <td align="right"><a href="javascript:void(0);" onclick="fbStart('选择机构','<%=BaseAction.rootLocation%>/core/org!select.action',520,462);"><img src="core/common/images/outdiv.gif" width="20" height="22" /></a></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td class="layertdleft">状态：</td>
              <td style="padding-left:6px;"><label>
                <enum:select name="user.entityStatus" styleClass="data" type="com.wiiy.commons.preferences.enums.EntityStatus" checked="user.entityStatus"/>
              </label></td>
            </tr>
          </table></td>
        </tr>
      </table>
	</div>
	<div class="hackbox"></div>
</div>
<!--//basediv-->
<!--//tabList-->
<div style="">
	<div class="apptab" id="tableid">
			<ul>
				<%int flag=-1;
				if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_role")){
				flag++;
				%>
				<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">角色信息</li>
				<%} %>
				<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_archives")){flag++; %>
				<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">员工档案</li>
				<%} %>
				<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_contract")){ flag++;%>
				<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">劳动合同</li>
				<%} %>
				<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_attendance")){flag++; %>
				<li id="apptab" class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">考勤记录</li>
				<%} %>
				<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_accessory")){ flag++;%>
				<li class="apptabli<%if(flag==0){ %>over<%} %>" onclick="tabSwitch('apptabli','apptabliover','tabswitch',<%=flag%>)">附件</li>
				<%} %>
			</ul>
		</div>
<!-- 角色 -->
		<%int flag2=-1;
		if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_role")){flag2++; %>
	<div class="basediv tabswitch" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
		<div class="divlays" style="margin:0px;">
		 	 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="align:right;">
				<tr>
					<td>
					<div class="roleleft">
					<div class="roletitle">所有角色</div>
					<div class="rolelist" style="padding-top:5px;height:130px;overflow:scroll;">
						<ul id="roleList">
							<c:forEach var="role" items="${roleList}">
								<c:if test="${empty selectedRoleIds[role.id]}">
								<li roleId="${role.id}"><a href="javascript:void(0);">${role.name}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					</div>
					</td>
					<td style="align:center;">
					<div class="rolebtn" style="height:50px;"><img id="goLeft" src="core/common/images/left.png" width="16" height="16"/><img id="goRight" src="core/common/images/right.png" width="16" height="16" /></div>
					</td>
					<td>
					<div class="roleleft">
					<div class="roletitle">已选角色</div>
					<div class="rolelist" style="padding-top:5px;height:130px;overflow:scroll;">
						<ul id="selectedRoles">
							<c:forEach var="userRoleRef" items="${user.roleRefs}">
							<li roleId="${userRoleRef.role.id}"><a href="javascript:void(0);">${userRoleRef.role.name}</a></li>
							</c:forEach>
						</ul>
					</div>
					</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<%} %>
	<!-- 员工档案 -->
	<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_archives")){ flag2++;%>
		<div class="basediv tabswitch" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
			 <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				    <td class="layertdleft100" ><span class="psred">*</span>姓名：</td>
		       		<td class="layerright" ><input name="archives.name" type="text" class="inputauto" value="${archives.name}"/></td>
				   	<td class="layertdleft100">民族：</td>
					<td class="layerright">
						<dd:select name="archives.ethnicId" key="oa.0007" />
				    </td>
				    <td class="layertdleft100">政治面貌：</td>
				   	<td class="layerright">
						<dd:select name="archives.politicalId" key="crm.0029" />
				    </td>
		       	</tr>
		       	<tr>
		      		<td class="layertdleft100" >国籍：</td>
				   	<td class="layerright" >
						<dd:select name="archives.nationalityId" key="oa.0006" />
				    </td>
				    <td class="layertdleft100" >籍贯：</td>
					<td class="layerright"><input name="archives.homeTown" type="text" class="inputauto" value="${archives.homeTown }"/></td>
		            <td class="layertdleft">状态：</td>
		            <td>&nbsp;
		                 <enum:radio name="archives.status" type="com.wiiy.synthesis.preferences.enums.PositionConditionEnum" checked="archives.status" />
		            </td>
		      	</tr>
		      	<tr>
		      		<td class="layertdleft100">电话号码：</td>
		       		<td class="layerright"><input onKeyUp="value=value.replace(/\D+/g,'')" name="archives.phone" type="text" class="inputauto" value="${archives.phone }"/></td>
		      		<td class="layertdleft100">身份证号：</td>
		       		<td class="layerright"><input name="archives.idNo" type="text" class="inputauto" value="${archives.idNo }"/></td>
		      		<td class="layertdleft100">家庭邮编：</td>
		       		<td class="layerright"><input onKeyUp="value=value.replace(/\D+/g,'')" name="archives.zipCode" type="text" class="inputauto" value="${archives.zipCode }"/></td>
				</tr>
				<tr>
				    <td class="layertdleft100">出生日期：</td>
		     		<td class="layerright" >
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><input id="birthDay" name="archives.birthDay" readonly="readonly" value="<fmt:formatDate value="${archives.birthDay }" pattern="yyyy-MM-dd"/>" type="text" class="inputauto" onclick="return showCalendar('birthDay');"/></td>
								<td width="20"><img src="core/common/images/timeico.gif" width="20" height="22" style="position: relative;left:-1px;" onclick="return showCalendar('birthDay');"/></td>
							</tr>
						</table>
					</td>
				     <td class="layertdleft100">家庭地址：</td>
		       		<td class="layerright"><input name="archives.homeAddr" type="text" class="inputauto" value="${archives.homeAddr }"/></td>
		            <td class="layertdleft">婚配：</td>
		            <td>&nbsp;
		                 <enum:radio name="archives.marriage" type="com.wiiy.commons.preferences.enums.BooleanEnum" checked="archives.marriage" />
		            </td>
		       	</tr>
		       	<tr>
		       	    <td class="layertdleft100" >学历：</td>
					<td class="layerright"><input name="archives.degree" type="text" class="inputauto" value="${archives.degree }"/></td>
		       	    <td class="layertdleft100" >毕业学校：</td>
		       		<td class="layerright"><input name="archives.school" type="text" class="inputauto" value="${archives.school }"/></td>
		       	    <td class="layertdleft100" >专业：</td>
		       		<td class="layerright"><input name="archives.profession" type="text" class="inputauto" value="${archives.profession }"/></td>
		       	</tr>
		       <tr>
		       	    <td class="layertdleft100" >职务：</td>
					<td class="layerright"><input name="archives.position" type="text" class="inputauto" value="${archives.position }"/></td>
		       <!--   	<td class="layertdleft100">备注：</td>
			    	<td class="layerright" style="padding-bottom:3px;"colspan="3">
			    		<textarea name="archives.memo" class="inputauto" style="resize:none;height:40px;"></textarea>
			    	</td> -->
			    	<td class="layertdleft100">备注：</td>
						<td colspan="3" class="layerright">
						<textarea name="archives.memo"  class="textareaauto"  style="height:40px;">${archives.memo}</textarea>
					</td>
		       	</tr>
		  	</table>
		</div>
		<%} %>
		<!-- 劳动合同 -->
		<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_contract")){ flag2++;%>
		<div class="basediv tabswitch changeDiv" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
  			<div class="divlays" style="margin:0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	 <tr>
            <td width="100%" valign="top">
                <!--msglist-->
                <div class="msglist" id="msglist">
                    <!--titlebg-->
                    <div class="titlebg">劳动合同信息</div>
                    <div class="emailtop">
						<!--leftemail-->
						<div class="emailtop">
							<div class="leftemail">
								<ul>
									<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建','<%=basePath%>laborContract!add.action?id=${user.id }',650,306);"><span><img src="core/common/images/emailadd.gif"/></span>添加</li>
								    <li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>							</ul>
							</div>
						</div>
					</div>
                    <!--//titlebg-->
						<table id="list" class="jqGridList"><tr><td/></tr></table>
						<div id="pager"></div>
                    </div>
                <!--//msglist-->        
                </td>
        </tr>
    </table>
    		</div>
		</div>
	<%} %>
		<!-- 考勤管理 -->
		<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_attendance")){ flag2++;%>
		<div class="basediv tabswitch changeDiv" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
  			<div class="divlays" style="margin:0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
       	 <tr>
            <td width="100%" valign="top">
            <div class="msglist" id="msglist">
                <div class="titlebg">考勤记录</div>
				<table id="list1" class="jqGridList"><tr><td/></tr></table>
				<div id="pager1"></div>
			</div>
            </td>
        </tr>
    </table>
    		</div>
		</div>
	<%} %>
		<!-- 附件 -->
		<%if(SynthesisActivator.getHttpSessionService().isInResourceMap("personnel_roster_edit_accessory")){ flag2++;%>
		<div class="basediv tabswitch changeDiv" style="margin-top:0px;<%if(flag2!=0){ %>display:none;<%} %>" id="textname">
  			<div class="divlays" style="margin:0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		       	 <tr>
		            <td width="100%" valign="top">
						<div class="msglist" id="msglist">
		                    <!--titlebg-->
		                    <div class="titlebg">其它附件</div>
		                    <div class="emailtop">
								<!--leftemail-->
								<div class="emailtop">
									<div class="leftemail">
										<ul>
											<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="fbStart('新建','<%=basePath%>rosterAtt!add.action?id=${user.id }',650,175);"><span><img src="core/common/images/addnode.gif"/></span>添加</li>
											<li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="deleteSelected1()"><span><img src="core/common/images/emaildel.png"/></span>删除</li>
										</ul>
									</div>
								</div>
							</div>
		                    <!--//titlebg-->
							<table id="list3" class="jqGridList"><tr><td/></tr></table>
							<div id="pager3"></div>
		               </div>
		            </td>
		        </tr>
    			</table>
    		</div>
		</div>
	<%} %>
</div>
<!--//tabList-->
<div class="buttondiv">
  <label>
  <input name="Submit" type="submit" class="savebtn" value="" />
  </label>
  <input name="Submit2" type="button" class="cancelbtn" value="" onclick="fb.end()"/>
</div>
</div>
</form>
</body>
</html>

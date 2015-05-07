<%@page import="com.wiiy.common.preferences.enums.DepartmentEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String uploadPath = BaseAction.rootLocation+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<title>无标题文档</title>
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/calendar/calendar.css" rel="stylesheet" type="text/css" />
<link href="core/common/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initTip();
		initUploadify("fileUpload");
		initForm();
		initDoubleFormat();
	});
	function initUploadify(id){
		$("#"+id).uploadify( {
			'auto'				: true, //是否自动开始 默认true
			'multi'				: false, //是否支持多文件上传 默认true
			'formData'			: {'module':'pb','directory':uploadify.directory.images},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
			'uploader'			: "<%=uploadPath %>core/upload.action",//文件服务器路径
			'swf'				: uploadify.swf,//上传组件swf
			'width'				: uploadify.width,//按钮图片的长度
			'height'			: uploadify.height,//按钮图片的高度
			'buttonText'		: uploadify.buttonText, //按钮上的文字
			'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
			'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
			'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
			'fileTypeDesc'		: uploadify.images.fileTypeDesc,//选择文件对话框文件类型描述信息
			'fileTypeExts'		: uploadify.images.fileTypeExts,//可上传上的文件类型
			'onUploadSuccess'	: onUploadSuccess//上传成功回调函数 有三个参数 file(可取文件名  注：上传前的文件名不是服务器端重写的文件名) data(服务器端写到response里面的信息) response(true,false)
		});
	}
	function onUploadSuccess(file, data, response) {
		$("#size").val($.parseJSON(data).size);
		$("#imageList").append($("<td width='60'><input type='hidden' value='"+$.parseJSON(data).originalName+"' class='roomAttName' /><input type='hidden' value='"+$.parseJSON(data).path+"' class='roomimg' /></td>").append("<img src='core/resources/"+$.parseJSON(data).path+"' width='50' height='50'/>"));
		$("#imageList").append($("<td width='25' valign='bottom'></td>").append($("<img />",{src:'core/common/images/locking.jpg',click:function(){
			$(this).attr("src","core/resources/"+$.parseJSON(data).path+"-d");
			$(this).parent().prev().remove();
			$(this).parent().remove();
			}})));
	}
	function startUpload(id){
		$("#"+id).uploadify("upload");
	}
	function initForm(){
		$("#form1").validate({
			rules: {
				"room.floorId":"required",
				"room.name":"required",
				"room.kindId":"required",
				"room.status":"required",
				"room.realArea":"required",
				"room.buildingArea":"required"
			},
			messages: {
				"room.floorId":"请选择所在楼层",
				"room.name":"请输入房间名称",
				"room.kindId":"请选择房间性质",
				"room.status":"请选择房间状态",
				"room.realArea":"请输入实用面积",
				"room.buildingArea":"请输入建筑面积"
				
			},
			errorPlacement: function(error, element){
				showTip(error.html());
			},
			submitHandler: function(form){
				$("input[name='fileName']").val(getAttsList());
				$('#form1').ajaxSubmit({ 
			        dataType: 'json',		        
			        success: function(data){
		        		showTip(data.result.msg,2000);
			        	if(data.result.success){
			        		setTimeout("getOpener().reloadRoomList();fb.end();", 2000);
			        	}
			        } 
			    });
			}
		});
	}

	function getAttsList(){
		var topicPaths = '[';
		var obj = $("#imageList").children();
		$(obj).each(function (i){
			if(i%2 == 0){
				var child = $(this).children();
				var oldName = $(child).eq(0).val();
				oldName = encodeURIComponent(oldName);
				oldName = encodeURIComponent(oldName);
				var str = "{\"filePath\" : \""+$(child).eq(1).val()+"\",";
				str += "\"fileName\" : \""+oldName+"\"}";
				topicPaths += str+",";
			}
		});
		if(topicPaths.lastIndexOf(",") == topicPaths.length-1)
			topicPaths = topicPaths.substr(0,topicPaths.length-1);
		return topicPaths+"]";
	}
</script>
<style>
	#mainTable{
		table-layout:fixed;
	}
</style>
</head>

<body>
<form action="<%=basePath %>room!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="fileName"/>
<input type="hidden" name="room.id" value="${result.value.id}"/>
<input type="hidden" name="room.buildingId" value="${result.value.building.id}"/>
<!--basediv-->
<div class="basediv">
<div class="divlays">
	<table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td class="layertdleft100">所在园区：</td>
      		<td class="layerright">${result.value.building.park.name}</td>
			<td class="layertdleft100">所在楼宇：</td>
			<td class="layerright" name="building">${result.value.building.name}</td>
			<td class="layertdleft100"><span class="psred">*</span>所在楼层：</td>
			<td class="layerright">
				<select id="floor" name="room.floorId">
					<option value="">----请选择----</option>
					<c:forEach items="${floorList}" var="floor">
					<option value="${floor.id }" <c:if test="${floor.id eq result.value.floorId}">selected</c:if>>${floor.name }</option>
					</c:forEach>
				</select>
			</td>
	    </tr>
	    <tr>
	    	<td class="layertdleft100"><span class="psred">*</span>房间名称：</td>
			<td class="layerright">
		      <label>
		        <input id="roomname" name="room.name" type="text" value="${result.value.name }" class="inputauto" onblur="checkNameUnique()"/>
		      </label>
      		</td>
			<td class="layertdleft100"><span class="psred">*</span>状态：</td>
			<td class="layerright">
				<enum:select id="status" name="room.status" checked="result.value.status" type="com.wiiy.common.preferences.enums.RoomStatusEnum"/>
			</td>
			<td class="layertdleft100">业务划分：</td>
	      	<td class="layerright">
	      		<select name="room.department">
	      			<option value="">----请选择----</option>
	      			<option value="<%=DepartmentEnum.SALE %>" <c:if test="${result.value.department eq 'SALE' }">selected</c:if>><%=DepartmentEnum.SALE.getTitle() %></option>
	      			<option value="<%=DepartmentEnum.BUSINESS %>" <c:if test="${result.value.department eq 'BUSINESS' }">selected</c:if>><%=DepartmentEnum.BUSINESS.getTitle() %></option>
	      		</select>
	      	</td>
	   	</tr>
	   	<tr>
	   		<td class="layertdleft100"><span class="psred">*</span>性质：</td>
      		<td class="layerright">
				<dd:select name="room.kindId" key="pb.0007" checked="result.value.kindId"/>
			</td>
	   		<td class="layertdleft100">房屋户型：</td>
			<td class="layerright">
				<dd:select key="room.0003" name="room.houseTypeId" checked="result.value.houseTypeId"/>
			</td>
			<td class="layertdleft100">是否虚拟：</td>
			<td class="layerright">
				<enum:select type="com.wiiy.commons.preferences.enums.BooleanEnum" checked="result.value.virtual" name="room.virtual" />
			</td>
		</tr>
	    <tr>
	    	
      		<td class="layertdleft100">用途：</td>
      		<td class="layerright">
      			<dd:select name="room.typeId" key="pb.0006" checked="result.value.typeId"/>
			</td>
			<td class="layertdleft100">层高：</td>
			<td class="layerright">
	      		<input name="room.height" type="text" value="${result.value.height }" class="input100 doubleformat"/>&nbsp;(米)
			</td>
			<td class="layertdleft100">房屋状态：</td>
		  <td class="layerright">
		  	<enum:radio name="room.state" checked="result.value.state" type="com.wiiy.common.preferences.enums.RoomStateEnum" />
		  </td>
		</tr>
	    <tr>
			<td class="layertdleft100">单价：</td>
			<td class="layerright">
				<table>
					<tr>
						<td><input id="priceRent" name="room.priceRent"
						value='<fmt:formatNumber value="${result.value.priceRent }" pattern="0.00" />' type="text" class="inputauto doubleformat" size="8"/></td>
						<td width="60">&nbsp;(元/平米)</td>
					</tr>
				</table>
			</td>
			<td class="layertdleft100">总价：</td>
			<td class="layerright"><input id="totalRent" name="room.totalRent"
			value='<fmt:formatNumber value="${result.value.totalRent }" pattern="0.00" />' type="text" class="input100 doubleformat" size="8"/>&nbsp;(元)</td>
			<td class="layertdleft100">朝向：</td>
			<td class="layerright">
		  		<dd:select key="room.0001"  name="room.directionId" checked="result.value.directionId"/>
		  	</td>
	    </tr>
	    <tr>
	      <td class="layertdleft100"><span class="psred">*</span>建筑面积：</td>
	      <td class="layerright">
			<input id="buildingArea" name="room.buildingArea" 
			value='<fmt:formatNumber value="${result.value.buildingArea }" pattern="0.00" />' type="text" class="input100 doubleformat" />&nbsp;(㎡)
	      </td>
	      <td class="layertdleft100"><span class="psred">*</span>实用面积：</td>
	      <td class="layerright">
	      	<input id="realArea" name="room.realArea" 
	      	value='<fmt:formatNumber value="${result.value.realArea }" pattern="0.00" />' type="text" class="input100 doubleformat"/>&nbsp;(㎡)
		  </td>
		  <td class="layertdleft100">公摊面积：</td>
		  <td class="layerright">
			<input id="poolArea" name="room.poolArea"
			value='<fmt:formatNumber value="${result.value.poolArea }" pattern="0.00" />' type="text" class="input100 doubleformat" />&nbsp;(㎡)
		  </td>
	    </tr>
	    <tr>
		  <td class="layertdleft100">单元：</td>
		  <td class="layerright"><input id="unit" name="room.unit" value="${result.value.unit }" type="text" class="inputauto" size="8"/></td>
	      <td class="layertdleft100">位置：</td>
	      <td class="layerright"><input name="room.location" value="${result.value.location }" type="text" class="inputauto" /></td>
	      <td class="layertdleft100">显示权值：</td>
	      <td class="layerright"><input id="displayOrder" value="${result.value.displayOrder }" name="room.displayOrder" type="text" class="inputauto" /></td>
		</tr>
	    <tr>
	      <td class="layertdleft100">优惠说明：</td>
	      <td colspan="5" class="layerright">
	        <textarea name="room.discountRate" style="height:40px;resize:none;" class="inputauto">${result.value.discountRate }</textarea>
	      </td>
	    </tr>
	    <tr>
	      <td class="layertdleft100">照片：</td>
	      <td class="layerright">
	      	<input type="file" id="fileUpload" />
	      </td>
	    </tr>
	    <tr>
		    <td class="layertdleft100">图片浏览：</td>
		    <td class="layerright" colspan="5">
		    	 <div style="width:100%; overflow-x:scroll; overflow-y:hidden; height:80px; margin-bottom:2px;">
				      <table border="0" cellspacing="0" cellpadding="0">
				      	<tr id="imageList">
							<c:forEach items="${roomAttList }" var="att">
				      		<td width="60">
				      			<input type="hidden" value="${att.name }" class="roomAttName" />
				      			<input type="hidden" value="${att.newName }" class="roomimg" />
				      			<img src="core/resources/${att.newName }" width="50" height="50" />
				      		</td>
				      		<td width="25" valign="bottom"><img src="core/common/images/locking.jpg" /></td>
				      		</c:forEach>
				      	</tr>
				      </table>
			     </div>
		     </td>
	    </tr>
		<tr>
	      <td class="layertdleft100">房间介绍：</td>
	      <td colspan="5" class="layerright">
	        <textarea name="room.summary" style="height:60px;resize:none;" class="inputauto">${result.value.summary }</textarea>
	      </td>
	    </tr>
	</table>
</div>
</div>

<div class="buttondiv">
  <label><input name="Submit" type="submit" class="savebtn" value=""/></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
  </div>
</form>
</body>
</html>

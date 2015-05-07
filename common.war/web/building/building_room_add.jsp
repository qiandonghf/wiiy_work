<%@page import="com.wiiy.common.preferences.enums.DepartmentEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
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
	function selectBuilding(){
		var id = $("#park").val();
		$.post("<%=basePath%>room!loadByParkId.action?id="+id,function(data){
			if(data.result.success){
				var list = data.result.value;
				var contectId = $("#building");
				contectId.empty();
				contectId.append($("<option></option>",{value:""}).append("请选择所在楼宇"));
				for(var i = 0; i < list.length; i++){
					var contect = list[i];
					contectId.append($("<option></option>",{value:contect.id}).append(contect.name));
				}
			} else {
				showTip(data.result.msg,2000);
			}
		});
	}
	function selectFloor(){
		var id = $("#building").val();
		$.post("<%=basePath%>room!loadByBuildingId.action?id="+id,function(data){
			if(data.result.success){
				var list = data.result.value;
				var contectId = $("#floor");
				contectId.empty();
				contectId.append($("<option></option>",{value:""}).append("请选择所在楼层"));
				for(var i = 0; i < list.length; i++){
					var contect = list[i];
					contectId.append($("<option></option>",{value:contect.id}).append(contect.name));
				}
			} else {
				showTip(data.result.msg,2000);
			}
		});
	}

	var chongfu = true;
	$(document).ready(function() {
		initTip();
		initUploadify("fileUpload");
		initForm();
		initDoubleFormat();
		/* $("select").each(function(){
			$(this).children().eq(1).selected();
		}); */
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
				if(chongfu){
					showTip("房间名称重复",2000);
					return;
				}
				var path;
				var pathString = "";
				$(".roomimg").each(function(){
					path = $(this).attr("value")+",";
					pathString += path;
				});
				$("#photos").val(pathString);
				var attName = "";
				var attString = "";
				$(".roomAttName").each(function(){
					attName = $(this).attr("value")+ ",";
					attString += attName;
				});
				$("#attname").val(attString);
				if(!checkRange("priceRent", "totalRent", "价格")){
					showTip("单价不能高于总价");
					return;
				}
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
	
	function checkRange(id1,id2,text){
		if ($("#priceRent").val()!="") {
			if(checkDouble("priceRent","单价")==false){
				return;
			}
		}
		if ($("#totalRent").val()!="") {
			if(checkDouble("rentEnd","总价")==false){
				return;
			}
		}
		if(Number($("#"+id1).attr("value"))<Number($("#"+id2).attr("value"))){
			return false;
		}else{
			return true;
		}
	}
	
	function checkNameUnique(){
		var name = $("#roomname").val();
		name = trim(name);
		if(name == ''){
			$("#roomname").val(name);
			return;
		}
		var id = "${building.id}";
		$.post(
			  "<%=basePath %>room!checkNameUnique.action",
			  {name:name,id:id},
			  function(data) {
			    if(data.unique){
			    	chongfu = false;
			   	 	return true;
			    }else{
			    	showTip("房间名称重复",2000);
			    	chongfu = true;
			    	return false;
			    }
			  }
		);
	} 

	function getAttsList(){
		var topicPaths = '[';
		var obj = $("#imageList").children();
		$(obj).each(function (i){
			if(i%2 == 0){
				var child = $(this).children();
				var oldName = $(child).eq(1).val();
				var str = "{\"filePath\" : \""+$(child).eq(1).val()+"\"}";
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
<form action="<%=basePath %>room!save.action" method="post" name="form1" id="form1">
<input type="hidden" name="fileName"/>
<input type="hidden" name="room.buildingId" value="${building.id}"/>
<!--basediv-->
<div class="basediv">
<div class="divlays">
	<table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td class="layertdleft100"><span class="psred">*</span>所在园区：</td>
			<td class="layerright">
			 <select id="park" onchange="selectBuilding();">
			 	 <option value="">请选择所在园区</option>
			     <c:forEach items="${result.value}" var="park">
			     <option value="${park.id }">${park.name }</option>
			    </c:forEach>
			 </select>
			</td>
			<td class="layertdleft100"><span class="psred">*</span>所在楼宇：</td>
			<td class="layerright">
			 <select id="building" name="room.buildingId" onchange="selectFloor();">
			 	 <option value="">请选择所在楼宇</option>
			 </select>
			</td>
			<td class="layertdleft100"><span class="psred">*</span>所在楼层：</td>
			<td class="layerright">
				<select id="floor" name="room.floorId">
				 	 <option value="">请选择所在楼层</option>
				 </select>
			</td>
	    </tr>
	    <tr>
	    	<td class="layertdleft100"><span class="psred">*</span>房间名称：</td>
			<td class="layerright">
		      <label>
		        <input id="roomname" name="room.name" type="text" class="inputauto" onblur="checkNameUnique()"/>
		      </label>
      		</td>
			<td class="layertdleft100"><span class="psred">*</span>状态：</td>
			<td class="layerright">
				<enum:select id="status" name="room.status" type="com.wiiy.common.preferences.enums.RoomStatusEnum"/>
			</td>
			<td class="layertdleft100">业务划分：</td>
	      	<td class="layerright">
	      		<c:if test="${empty param.department }">
		      		<select name="room.department">
		      			<option value="">----请选择----</option>
		      			<option value="<%=DepartmentEnum.SALE %>"><%=DepartmentEnum.SALE.getTitle() %></option>
		      			<option value="<%=DepartmentEnum.BUSINESS %>"><%=DepartmentEnum.BUSINESS.getTitle() %></option>
		      		</select>
	      		</c:if>
	      		<c:if test="${not empty param.department }">
		        <input name="room.department" type="hidden" class="inputauto" value="${department.title }"/>
	      		<span>${department.title }</span>
	      		</c:if>
	      	</td>
	   	</tr>
	   	<tr>
			<td class="layertdleft100"><span class="psred">*</span>性质：</td>
      		<td class="layerright">
				<dd:select name="room.kindId" key="pb.0007"/>
			</td>
	   		<td class="layertdleft100">房屋户型：</td>
			<td class="layerright">
				<dd:select key="room.0003"  name="room.houseTypeId"/>
			</td>
			<td class="layertdleft100">是否虚拟：</td>
			<td class="layerright">
				<enum:select type="com.wiiy.commons.preferences.enums.BooleanEnum" defaultValue="NO" name="room.virtual" />
			</td>
		</tr>
	    <tr>
      		<td class="layertdleft100">用途：</td>
      		<td class="layerright">
      			<dd:select name="room.typeId" key="pb.0006"/>
			</td>
			<td class="layertdleft100">层高：</td>
			<td class="layerright">
	      		<input id="realArea" name="room.height" type="text" class="input100 doubleformat"/>&nbsp;(米)
			</td>
			<td class="layertdleft100">房屋状态：</td>
		  <td class="layerright">
		  	<enum:radio name="room.state" type="com.wiiy.common.preferences.enums.RoomStateEnum" />
		  </td>
		</tr>
	    <tr>
			<td class="layertdleft100">单价：</td>
			<td class="layerright">
				<table>
					<tr>
						<td><input id="priceRent" name="room.priceRent" type="text" class="inputauto doubleformat" size="8"/></td>
						<td width="60">&nbsp;(元/平米)</td>
					</tr>
				</table>
			</td>
			<td class="layertdleft100">总价：</td>
			<td class="layerright"><input id="totalRent" name="room.totalRent" type="text" class="input100 doubleformat" size="8"/>&nbsp;(元)</td>
			<td class="layertdleft100">朝向：</td>
			<td class="layerright">
		  		<dd:select key="room.0001"  name="room.directionId"/>
		  	</td>
	    </tr>
	    <tr>
	      <td class="layertdleft100"><span class="psred">*</span>建筑面积：</td>
	      <td class="layerright">
			<input id="buildingArea" name="room.buildingArea" type="text" class="input100 doubleformat" />&nbsp;(㎡)
	      </td>
	      <td class="layertdleft100"><span class="psred">*</span>实用面积：</td>
	      <td class="layerright">
	      	<input id="realArea" name="room.realArea" type="text" class="input100 doubleformat"/>&nbsp;(㎡)
		  </td>
		  <td class="layertdleft100">公摊面积：</td>
		  <td class="layerright">
			<input id="buildingArea" name="room.poolArea" type="text" class="input100 doubleformat" />&nbsp;(㎡)
		  </td>
	    </tr>
	    <tr>
	      <td class="layertdleft100">单元：</td>
		  <td class="layerright"><input id="unit" name="room.unit" type="text" class="inputauto" size="8"/></td>
	      <td class="layertdleft100">位置：</td>
	      <td class="layerright"><input name="room.location" type="text" class="inputauto" /></td>
	      <td class="layertdleft100">显示权值：</td>
	      <td class="layerright"><input id="displayOrder" name="room.displayOrder" type="text" class="inputauto" /></td>
	    </tr>
	    <tr>
	      <td class="layertdleft100">优惠说明：</td>
	      <td colspan="5" class="layerright">
	        <textarea name="room.discountRate" style="height:40px;resize:none;" class="inputauto"></textarea>
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
				      	<tr id="imageList"></tr>
				      </table>
			     </div>
		     </td>
	    </tr>
		<tr>
	      <td class="layertdleft100">房间介绍：</td>
	      <td colspan="5" class="layerright">
	        <textarea name="room.summary" style="height:60px;resize:none;" class="inputauto"></textarea>
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

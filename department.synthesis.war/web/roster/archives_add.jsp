<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<link rel="stylesheet" type="text/css" href="core/common/style/content.css" />
	<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css" />
	<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
	<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css" />
	<link rel="stylesheet" type="text/css" href="parkmanager.association/web/style/assciation.css" />
	
	
	<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
	<script type="text/javascript" src="core/common/js/tools.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
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
			initUploadify("fileUpload");
		});
		function changeState(obj){
			if($(obj).val() == 'NO'){
				$(obj).val("YES");
			}else{
				$(obj).val("NO");
			}
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
			<%Calendar c = Calendar.getInstance();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = s.format(c.getTime());	
			c.add(Calendar.YEAR, 1);
			c.add(Calendar.DATE, -1);
			String endTime = s.format(c.getTime());
			%>
			var startTime = '<%=startTime%>';
			var endTime = '<%=endTime%>';
			$("#startTime").val(startTime);
			$("#signDate").val(startTime);
			$("#endTime").val(endTime);
			$("#receiveDate").val(startTime);
			
		}
		function selectUser(id){
			var title = '选择';
			clicked = id;
			switch(id){
			case 'handling':
				title += "经手人";
				break;
			case 'supplier':
				title += "供应商";
				break;
			case 'customer':
				title += "客户";
				break;
			case 'contactName':
				title += "主要联系人";
				break;
			case 'dutyMember':
				title += "责任人";
				break;
			}
			fbStart(title,'<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
		} 
		function initUploadify(id){
			var root = '<%=BaseAction.rootLocation %>/';
			$("#"+id).uploadify( {
				'auto'				: true, //是否自动开始 默认true
				'multi'				: false, //是否支持多文件上传 默认true
				'formData'			: {'module':'cms','directory': uploadify.directory.images},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
				'uploader'			: root+"core/upload.action",
				'swf'				: uploadify.swf,//上传组件swf
				'width'				: "80",//按钮图片的长度
				'height'			: "18",//按钮图片的高度
				'buttonText'		: "照片上传",
				'fileObjName'		: uploadify.fileObjName, //和以下input的name属性一致 提交到服务器的属性
				'fileSizeLimit'		: uploadify.fileSizeLimit,//控制上传文件的大小，单位byte
				'removeCompleted'	: uploadify.removeCompleted, //上传完成移除出队列 默认true
				'fileTypeDesc'		: uploadify.images.fileTypeExts,//选择文件对话框文件类型描述信息
				'fileTypeExts'		: uploadify.images.fileTypeExts,//可上传上的文件类型
				'onUploadSuccess'	: onUploadSuccess//上传成功回调函数 有三个参数 file(可取文件名  注：上传前的文件名不是服务器端重写的文件名) data(服务器端写到response里面的信息) response(true,false)
			});
		}
		function setSelectedUser(contect){
			$("#"+clicked).val(contect.name);
			$("#"+clicked+"Id").val(contect.id);
		}
		
	    function checkDouble(el){
	    	$(el).val($(el).val().replace(/[^\d\.]/g,''));
	    } 
		function initForm(){
			$("#form1").validate({
				rules: {
					"contact.contactName":"required",
					"contact.customerId":"required",
					"contact.company":"required",
					"contact.denger":"required",
					"contact.signDate":"required"
				},
				messages: {
					"contact.contactName":"请输入联系人名称",
					"contact.customerId":"请选择客户",
					"contact.company":"请输入单位名称",
					"contact.denger":"请选择性别",
					"contact.receiveDate":"请选择登记日期"
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
				        		setTimeout("getOpener().reloadList();parent.fb.end()", 2000);
				        	}
				        } 
				    });
				}
			});
		}
 		function getCalendarScrollTop(){
			return $("#scrollDiv").scrollTop();
		} 
	</script>
</head>
<body>
<form action="<%=basePath %>contract!save.action" method="post" name="form1" id="form1">
<input type="hidden" name="contract.type" value="1"/>
<div class="basediv">
<div class="titlebg" style="background:#f0f0f0; border-bottom-color:#D9D9D9;">档案信息</div>
<div class="divlays" style="margin:0px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td class="layertdleft100"><span class="psred">*</span>姓名：</td>
			<td class="layerright"><input name="contact.contactId" type="text" class="inputauto" value="" /></td>
      		<td class="layertdleft100"><span class="psred">*</span>用户名：</td>
			<td class="layerright" >
				<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="customerId" name="contact.customerId" type="hidden"/>
			          	<input id="supplier" name="contact.supplier" class="inputauto" onclick="selectUser('supplier')" readonly="readonly"/></td>
			          <td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="selectUser('supplier')" style="cursor:pointer"/></td>
			        </tr>
		   		</table>
		   	</td>
       	</tr>
       	<tr>
       	    <td class="layertdleft100">政治面貌：</td>
       	    <td class="layerright">
      			<select>
      			<option>主要联系人</option>
      			<option>次要联系人</option>
      			</select>
      		</td>
      		<td class="layertdleft100" >国籍：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>重要</option>
					<option>不重要</option>
				</select>
			</td>
      	</tr>
      	<tr>
      		<td class="layertdleft100">民族：</td>
      		<td class="layerright">
      			<select>
      			<option>--请选择--</option>
      			<option>汉族</option>
      			<option>阿桑族</option>
      			</select>
      		</td>
      		<td class="layertdleft100">身份证号：</td>
       		<td class="layerright"><input name="contact.idcard" type="text" class="inputauto" value="83775026"/></td>
      		
		</tr>
		<tr>
			<td class="layertdleft100" >学历：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>本科</option>
					<option>大专</option>
				</select>
			</td>		    
			<td class="layertdleft100" >出生日期：</td>
     		<td class="layerright" >
	     		<table width="143" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td>
			          	<input id="birthday" name="contact.birthday" type="text" readonly="readonly" class="inputauto" onclick="showCalendar('birthday')"/>
			          </td>
			          <td width="20" align="center">
			          	<img style="position:relative; left:-1px;" onclick="showCalendar('birthday')" src="core/common/images/timeico.gif" width="20" height="22" />
			          </td>
			        </tr>
			    </table>
		    </td>
       	</tr>
       	<tr>
       	   <td class="layertdleft100" >国籍：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>主管</option>
					<option>副主任</option>
				</select>
			</td>
       	   <td class="layertdleft100" >籍贯：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>助教</option>
					<option>工程师</option>
				</select>
			</td>
       	</tr>
       	<tr>
       	   <td class="layertdleft100" >职务：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>主管</option>
					<option>副主任</option>
				</select>
			</td>
       	   <td class="layertdleft100" >婚配：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>助教</option>
					<option>工程师</option>
				</select>
			</td>
       	</tr>
       	<tr>
       		<td class="layertdleft100"><span class="psred">*</span>电话号码：</td>
       		<td class="layerright"><input name="contact.officephone" type="text" class="inputauto" value="0571-88356626"/></td>
       		<td class="layertdleft100"><span class="psred">*</span>家庭邮编：</td>
       		<td class="layerright"><input name="contact.officephone" type="text" class="inputauto" value="0571-88356626"/></td>
       	</tr>
       	<tr>
       		<td class="layertdleft100"><span class="psred">*</span>家庭地址：</td>
       		<td class="layerright"><input name="contact.unit" type="text" class="inputauto" value="中国电信"/></td>
       	</tr>
       	  	<tr>
       	   <td class="layertdleft100" >毕业学校：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>主管</option>
					<option>副主任</option>
				</select>
			</td>
       	   <td class="layertdleft100" >专业：</td>
			<td class="layerright">
				<select>
					<option>--请选择--</option>
					<option>助教</option>
					<option>工程师</option>
				</select>
			</td>
       	</tr>
       <tr>
       	    <td class="layertdleft100">备注：</td>
	    	<td class="layerright" colspan="3" style="padding-bottom:3px;">
	    		<textarea name="contact.info" class="inputauto" style="resize:none;height:40px;"></textarea>
	    	</td>
       	</tr>
  	</table>
  </div>
<div class="hackbox"></div>
</div>
<div class="buttondiv">
  <label>&nbsp; 
  <input name="Submit" type="submit" class="savebtn" value="" /></label>
  <label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
 </div>
</form>
</body>
</html>

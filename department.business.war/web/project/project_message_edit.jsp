<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="dd" uri="http://www.wiiy.com/taglib/datadict" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.wiiy.business.activator.BusinessActivator" %>
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
<link rel="stylesheet" type="text/css" href="core/common/style/content.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/layerdiv.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css"/>
<link rel="stylesheet" type="text/css" href="core/common/uploadify-v3.1/uploadify.css" />
<link rel="stylesheet" type="text/css" href="department.business/web/style/assciation.css"/>

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
		if($("#photos").val()!=""){
			var arr = $("#photos").val();
			$("#imageList").append($("<td width='60'><img src='core/resources/"+arr+"' width='50' height='50' />"));
			$("#imageList").append($("<td width='25' valign='bottom'></td>").append($("<img />",{src:'core/common/images/locking.jpg',click:function(){
				deletePic();
				/* $(this).attr("src","core/resources/"+arr+"-d");
				$(this).parent().prev().remove();
				$(this).parent().remove(); */
			}})));
		}
		var leaders = $("#leaders").val();
		leaders = eval("(" + leaders + ")");
		for(var i=0; i<leaders.length; i++){
			$("#leader"+(i+1)).val(leaders[i].name);
     		$("#position"+(i+1)).val(leaders[i].position);
  		}
		var questionMemo = $("#questionMemo").val();
		questionMemo = eval("(" + questionMemo + ")");
		$("#business").val(questionMemo.business);
		$("#team").val(questionMemo.team);
		$("#demand").val(questionMemo.demand);
		$("#market").val(questionMemo.market);
		$("#customer").val(questionMemo.customer);
		$("#strategy").val(questionMemo.strategy);
		$("#model").val(questionMemo.model);
		$("#power").val(questionMemo.power);
		$("#superiority").val(questionMemo.superiority);

		var gainMemo = $("#gainMemo").val();
		gainMemo = eval("(" + gainMemo + ")");
		for(var i=0; i<gainMemo.length; i++){
     		$("#in"+i).val(gainMemo[i].inMoney);
     		$("#out"+i).val(gainMemo[i].out);
     		$("#profit"+i).val(gainMemo[i].profit);
  		}
	});
	
	function initUploadify(id){
		$("#"+id).uploadify( {
			'auto'				: true, //是否自动开始 默认true
			'multi'				: false, //是否支持多文件上传 默认true
			'formData'			: {'module':'association','directory':uploadify.directory.images},//提交到后台的数据 module 模块名 directory：文件夹名  images attachments
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
			'onUploadSuccess'	: onUploadSuccess,//上传成功回调函数 有三个参数 file(可取文件名  注：上传前的文件名不是服务器端重写的文件名) data(服务器端写到response里面的信息) response(true,false)
			'onSelect' : function(file) {
				deletePic();
	        }
		});
	}
	function onUploadSuccess(file, data, response) {
		$("#photos").val($.parseJSON(data).path);
		$("#imageList").append($("<td width='60'><img src='core/resources/"+$.parseJSON(data).path+"' width='50' height='50'/>"));
		$("#imageList").append($("<td width='25' valign='bottom'></td>").append($("<img />",{src:'core/common/images/locking.jpg',click:function(){
			$(this).attr("src","core/resources/"+$.parseJSON(data).path+"-d");
			$(this).parent().prev().remove();
			$(this).parent().remove();
		}})));
	}
	function deletePic(){
		$("#imageList").empty();
		$.post("<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>/core/resources/"+$("#photos").val()+"-d");
		$("#photos").val("");
	}
	function checkForm(){
		if(notNull("customerName","公司名称")&&checkSelect("cityId","城市")
				&&notNull("phone","联系电话")&&notNull("name","项目名称")
				&&notNull("amout","融资额")
				&&checkDouble("amout","融资额")&&checkSelect("industryId","所属行业")&&checkSelect("gainStatus","当前营收状态")){
			if($("#zipCode").val()!=""){
				checkPost("zipCode","邮编");
			}
			var leaders = "[";
			for(var i = 1 ;i<4;i++){
				if(i==3){
					leaders += "{name:'"+$("#leader"+i).val()+"',"+"position:'"+$("#position"+i).val()+"'}]";
				}else{
					leaders += "{name:'"+$("#leader"+i).val()+"',"+"position:'"+$("#position"+i).val()+"'},";
				}
			}
			$("#leaders").val(leaders);
			
			var questionMemo = "{business:'"+$("#business").val()+"',"+"team:'"+$("#team").val()+"',"+"demand:'"+$("#demand").val()+"',"+"market:'"+$("#market").val()+"',"+"customer:'"+$("#customer").val()+"',"+"strategy:'"+$("#strategy").val()+"',"+"model:'"+$("#model").val()+"',"+"power:'"+$("#power").val()+"',"+"superiority:'"+$("#superiority").val()+"'}";
			$("#questionMemo").val(questionMemo);
			
			var gainMemo = "[";
			for(var i = 0 ;i<5;i++){
				if(i==4){
					gainMemo += "{year:'"+$("#year"+i).val()+"',"+"inMoney:'"+$("#in"+i).val()+"',"+"out:'"+$("#out"+i).val()+"',"+"profit:'"+$("#profit"+i).val()+"'}]";
				}else{
					gainMemo += "{year:'"+$("#year"+i).val()+"',"+"inMoney:'"+$("#in"+i).val()+"',"+"out:'"+$("#out"+i).val()+"',"+"profit:'"+$("#profit"+i).val()+"'},";
				}
			}
			$("#gainMemo").val(gainMemo);
			
			$("#form1").ajaxSubmit({
		        dataType: 'json',		        
		        success: function(data){
	        		showTip(data.result.msg,2000);
		        	if(data.result.success){
		        		setTimeout("getOpener().reloadList();fb.end();", 2000);
		        	}
		        } 
		    });
		}
	}
	function notNull(id,msg){
		if(getOb(id)!=null && getOb(id).value!=''){
			return true;
		} else {
			showTip("请输入"+msg);
			return false;
		}
	}
	function checkSelect(id,msg){
		if(getOb(id)!=null && getOb(id).value!=''){
			return true;
		} else {
			showTip("请选择"+msg);
			return false;
		}
	}
	
</script>
</head>

<body>
<form action="<%=basePath %>projectLib!update.action" method="post" name="form1" id="form1">
<input type="hidden" name="projectLib.id" value="${result.value.id}"/>
<!--table切换开始-->
<div class="apptab" id="tableid">
    <ul>
    <%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_edit_base")){%>
        <li class="apptabliover" onclick="tabSwitch('apptabli','apptabliover','tabswitch',0)">基本信息</li>
    <%} %>
    <%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_edit_question")){%>
        <li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',1)">问题</li>
    <%} %>
    <%if(BusinessActivator.getHttpSessionService().isInResourceMap("customer_customrInfo_archives_OnePage_edit_condition")){%>
        <li class="apptabli" onclick="tabSwitch('apptabli','apptabliover','tabswitch',2)">财务状况</li>
    <%} %>
    </ul>
</div>
<!--//table切换开始-->
<div class="basediv tabswitch" style="margin-top:0px; padding:0px 10px;" id="textname">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td class="assoction_title">填写公司联系信息(<span class="psred">*</span>为必填)</td>
    </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
	     	<td class="assoction_td" style="padding-top: 5px;"><span class="psred">*</span>公司名称：</td>
			<td class="layerright">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td ><input id="customerId" value="${result.value.customerName}" name="projectLib.customerId" type="hidden" /><input id="customerName" name="projectLib.customerName" value="" type="text" class="inputauto" /></td>
					<td width="21" align="center"><img src="core/common/images/outdiv.gif" width="20" height="22" onclick="fbStart('选择企业','<%=basePath %>customer!select.action',520,400);" style="cursor:pointer"/></td>
				</tr>
			</table>
			</td>
          </tr>
           <tr>
          	<td class="assoction_td">地址：</td>
          	<td><input id="address" name="projectLib.address" value="${result.value.address}" type="text" class="inputauto" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
    	<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;">编号：</td>
	        <td><input id="orderId" name="projectLib.orderId" value="${result.value.orderId }" type="text" class="inputauto" /></td>
          </tr>
        </table></td>
    </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="assoction_td">网址：</td>
	        <td width="150"><input name="projectLib.homePage" value="${result.value.homePage}"  type="text" class="inputauto" /></td>
	        <td class="assoction_td"><span class="psred">*</span>城市：</td>
	        <td><label for="select">
	          <dd:select id="cityId" name="projectLib.cityId" key="business.0034" checked="result.value.cityId"/>
	          </label></td>
          </tr>
	      <tr>
	        <td class="assoction_td"><span class="psred">*</span>联系电话：</td>
	        <td><input id="phone" name="projectLib.phone" value="${result.value.phone}" type="text" class="inputauto" /></td>
	        <td class="assoction_td">邮编：</td>
	        <td><input id="zipCode" name="projectLib.zipCode" value="${result.value.zipCode}" type="text" class="inputauto" /></td>
          </tr>
          <tr>
		      <td class="assoction_td">LOGO：</td>
		      <td class="layerright">
		      <table border="0" cellspacing="0" cellpadding="0">
			      <tr>
				      <td height="60" width="100"> <input type="file" id="fileUpload" /> <div id="fileQueue"></div> </td>
				      <td>
					      <table  border="0" cellspacing="0" cellpadding="0">
					      	<tr id="imageList"></tr>
					      </table>
					      <input type="hidden" id="photos" value="${result.value.logo}" name="projectLib.logo" />
				      </td>
			      </tr>
		      </table>
		      </td>
		   </tr>
        </table></td>
    </tr>
	  <tr>
	    <td class="assoction_title">公司状况(<span class="psred">*</span>为必填)</td>
    </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <input id="leaders" type="hidden" name="projectLib.leaders" value="${result.value.leaders}"/>
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;">负责人1：</td>
	        <td width="150"><input id="leader1" type="text" class="inputauto" /></td>
	        <td class="assoction_td">职务1：</td>
	        <td><input type="text" id="position1" class="inputauto"/></td>
          </tr>
	      <tr>
	        <td class="assoction_td">负责人2：</td>
	        <td><input type="text" id="leader2" class="inputauto" /></td>
	        <td class="assoction_td">职务2：</td>
	        <td><input type="text" id="position2" class="inputauto" /></td>
          </tr>
	      <tr>
	        <td class="assoction_td">负责人3：</td>
	        <td><input type="text" id="leader3" class="inputauto" /></td>
	        <td class="assoction_td">职务3：</td>
	        <td><input type="text" id="position3" class="inputauto" /></td>
          </tr>
        </table></td>
    </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;">成立时间：</td>
	        <td><table border="0" cellspacing="0" cellpadding="10">
		        <tr>
		          <td><input name="projectLib.setupTime" value="<fmt:formatDate value="${result.value.setupTime }" pattern="yyyy-MM-dd"/>" readonly="readonly" type="text" class="inputauto" id="setupTime" onclick="return showCalendar('setupTime');" /></td>
		          <td width="20" align="right"><img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer;relative; left:-1px;" onclick="return showCalendar('setupTime');" /></td>
		        </tr>
		      </table>
		    </td>
          </tr>
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;">现员工人数：</td>
	        <td><table border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td><input name="projectLib.employeeCnt" value="${result.value.employeeCnt}" type="text" class="inputauto" /></td>
	            <td width="20" align="center">个</td>
	            <td>&nbsp;</td>
	            </tr>
	          </table></td>
          </tr>
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;">公司简介：</td>
	        <td><label for="textarea"></label>
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td><textarea name="projectLib.companyMemo" rows="3" class="textareaauto" id="textarea">${result.value.companyMemo} </textarea></td>
                </tr>
	            <tr>
	              <td class="assoction_gray">描述您公司简介（框架、股权模式、主营业务等）</td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
	  <tr>
	    <td class="assoction_title">项目信息(<span class="psred">*</span>为必填)</td>
    </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;"><span class="psred">*</span>项目名称：</td>
	        <td><input id="name" name="projectLib.name" value="${result.value.name}" type="text" class="inputauto" /></td>
            <td class="assoction_td">项目来源：</td>
     		<td><input id="name" name="projectLib.incubatorName" value="${result.value.incubatorName}" type="text" class="inputauto" /></td>
          </tr>
        </table></td>
    </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;"><span class="psred">*</span>融资额：</td>
	        <td><table border="0" cellspacing="0" cellpadding="10">
	          <tr>
	            <td><input id="amout" name="projectLib.amount" value="<fmt:formatNumber value="${result.value.amount}" pattern="#0.00"/>" type="text" class="inputauto" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
	            <td width="26" align="center">万元</td>
	            </tr>
            </table></td>
	        <td class="assoction_td">融资期限：</td>
	        <td><table border="0" cellspacing="0" cellpadding="10">
	          <tr>
		          <td width="126"><input name="projectLib.endTime" value="<fmt:formatDate value="${result.value.endTime }" pattern="yyyy-MM-dd"/>" readonly="readonly" type="text" class="inputauto" id="endTime" onclick="return showCalendar('endTime');" /></td>
		          <td width="20" align="right"><img src="core/common/images/timeico.gif" width="20" height="22" style="cursor:pointer;relative; left:-1px;" onclick="return showCalendar('endTime');" /></td>
		        </tr>
            </table></td>
          </tr>
        </table></td>
    </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;"><span class="assoction_td" style="padding-top:5px;"><span class="psred">*</span>所属行业：</span></td>
	        <td>
				<dd:select id="industryId" name="projectLib.industryId" key="business.0035" checked="result.value.industryId"/>
			</td>
	        <td></td>
          </tr>
	      <tr>
	        <td class="assoction_td" style="padding-top:5px;">目前状态：</td>
	        <td>
	        	<enum:select id="status" checked="result.value.status" name="projectLib.status" type="com.wiiy.business.preferences.enums.ProjectStatusEnum"/>
	        </td>
	        <td></td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>
<div class="basediv tabswitch" style="margin-top:0px; height:430px;overflow-X:hidden; overflow-Y:scroll; padding:0px 10px;display: none;" id="textname">
    <input id="questionMemo" type="hidden" name="projectLib.questionMemo" value="${result.value.questionMemo}"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top" style="padding-top:15px;" class="assoction_td" >公司业务：</td>
        <td style="padding-top:15px;">
       	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td><textarea id="business"  rows="6" class="textareaauto" id="textarea"></textarea></td>
                </tr>
	            <tr>
	              <td class="assoction_gray">描述您公司的业务，250字以内</td>
                </tr>
              </table>
        </td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">团队优势：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="team" rows="6" class="textareaauto" id="textarea2"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">描述您的管理团队从事该业务的优势，250字以内</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">客户需求：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="demand" rows="6" class="textareaauto" id="textarea3"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">描述您客户需求250字以内</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">目标市场及&nbsp;&nbsp;<br />
        潜在市场：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="market" rows="6" class="textareaauto" id="textarea4"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">描述示市场及潜在市场（包括市场规模、市场前景、客户群分布、市场结构等）</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">潜在客户：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="customer" rows="6" class="textareaauto" id="textarea6"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">描述潜在客户，250字以内</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">市场战略：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="strategy" rows="6" class="textareaauto" id="textarea5"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">解释您获取及拓展客户群的市场战略，250字以内</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">商业模式、<br />
        盈利模式：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="model" rows="6" class="textareaauto" id="textarea7"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">描述商业模式、盈利模式，250字以内</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">公司竞争力及&nbsp;&nbsp;<br />
        主要竞争对手：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="power" rows="6" class="textareaauto" id="textarea8"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">描述您公司公司竞争力及竞争对手，250字以内</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="assoction_td">竞争优势及&nbsp;&nbsp;<br />市场进入简况：</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea id="superiority" rows="6" class="textareaauto" id="textarea9"></textarea></td>
          </tr>
          <tr>
            <td class="assoction_gray">描述竞争优势及市场进入简况（包括所获专利及领先技术）。250字以内</td>
          </tr>
        </table></td>
      </tr>
    </table>
</div>
<div class="basediv tabswitch" style="margin-top:0px; height:430px;overflow-X:hidden; overflow-Y:scroll; padding:0px 10px;display: none;" id="textname">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="assoction_td" style="padding-top:15px;"><span class="psred">*</span>当前营收状态：</td>
	        <td style="padding-top:15px;"><label for="select3"></label>
	        	<enum:select id="gainStatus" name="projectLib.gainStatus" type="com.wiiy.business.preferences.enums.ProjectGainStatusEnum" checked="result.value.gainStatus"/>
	        </td>
          </tr>
        </table></td>
  </tr>
  <tr>
    <td class="assoction_title">例年财务状况及预期收入(单位：万元)</td>
    <input id="gainMemo" type="hidden" name="projectLib.gainMemo" value="${result.value.gainMemo}"/>
  </tr>
  <tr>
    <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="3">
      	<tr>
	        <td class="assoction_td60">&nbsp;</td>
	        <td height="30" align="center">收入</td>
	        <td align="center">支出</td>
	        <td height="30" align="center">净利润</td>
		</tr>
		<c:forEach items="${years}" var="year" varStatus="status">
			<tr>
				<td class="assoction_td60">${year}年<input id="year${status.index}" value="${year}" type="hidden"/></td>
				<td align="center"><input id="in${status.index}" type="text" class="input120" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
		        <td align="center"><input id="out${status.index}" type="text" class="input120" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
		        <td align="center"><input id="profit${status.index}" type="text" class="input120" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			</tr>
		</c:forEach>
    </table></td>
  </tr>
</table>
</div>
<div class="buttondiv">
<label><input name="Submit" type="button" class="savebtn" value="" onclick="checkForm()"/></label>
<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
</div>
</form>
</body>
</html>

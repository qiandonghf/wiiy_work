<%@page import="com.wiiy.pf.entity.Leave"%>
<%@ page import="com.wiiy.pf.preferences.enums.LeaveTypeEnum"%>
<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.pf.activator.PfActivator"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 
Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="parkmanager.pf/style/base.css" rel="stylesheet" type="text/css" />
<link href="parkmanager.pf/style/content.css" rel="stylesheet" type="text/css" />
<link href="parkmanager.pf/style/floatbox.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/layertable.js"></script>
<script src="core/common/js/jquery.js"></script>

<script type="text/javascript">
	function pageheight(nameid1,height1,nameid2,height2){//获取屏幕高度
		var bodyh=window.parent.document.documentElement.clientHeight-height1;
		var bodyh1=window.parent.document.documentElement.clientHeight-height2;
		var bodyw=window.parent.document.documentElement.clientWidth-890;
		document.getElementById(nameid1).style.height=bodyh+"px";
		document.getElementById(nameid2).style.height=bodyh1+"px";
		//document.getElementById(nameid2).style.width=bodyw+"px";
	}
	$(document).ready(function() {
		pageheight('resizable',89,'pm_msglist',121);
		
		 //右击菜单
	  var imageMenuData = [
	  [{
			text: "打开",
			classname: "smarty_menu_view",
			func: function() {
			  
			}
		}
		],[{
			text: "编辑新流程",
			classname: "smarty_back",
			func: function() {
				
			}
		},
		{
			text: "关闭新建流程",
			classname: "smarty_menu_finish",
			func: function() {
				
			}
		}]
		,[
	  {
			text: "报送审批",
			classname: "smarty_audit",
			data: [[{
            text: "行政部",
			classname: "smarty_audit",
            func: function() {
               
            }
        }, {
            text: "财务部",
			classname: "smarty_audit",
            func: function() {
               
            }
        }, {
            text: "招商部",
			classname: "smarty_audit",
            func: function() {
               
            }
        }, {
            text: "客户服务部",
			classname: "smarty_audit",
            func: function() {               
            }
        }]]
		}],
		[
	  {
			text: "删除",
			classname: "smarty_menu_ico2",
			func: function() {
				 confirm("你确定要删除");
			}
		}
		
		]
	];
	
	$("#lsittable").smartMenu(imageMenuData,{name:'table'});

	});
	
	

</script>
</head>

<body>
<div class="emailtop"> 
  <!--leftemail-->
  <div class="leftemail" id="navlist">
    <ul>
      <li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="companylabel();"  class=""><span><img src="core/common/images/emailadd.gif" width="15" height="13"></span>新建 <em class="em"><img src="core/common/images/arrow.png"></em> 
        
        <!--弹层-->
        <div class="company_label newProcessForm  cont" id="company_label" style="display:none;left:5px; top:26px;">
          <div class="titlebg"><span style="float:right"><img src="core/common/images/companyclose.gif" style="cursor:pointer;" /></span>新建流程</div>
          <ul class="shourul">
            <h1>行政类</h1>
            <li> 
            <a onclick="fbStart('档案借阅','parkmanager.pb/web/process/administration_leave_application.html',810,500);" >档案借阅</a> 
            <a onclick="fbStart('外出申请','parkmanager.pb/web/process/administration_egress_application.html',810,450);">外出申请</a> 
            <a onclick="fbStart('请假申请','parkmanager.pb/web/process/administration_leave_application.html',810,500);">请假申请</a> 
            <a onclick="fbStart('加班申请','parkmanager.pb/web/process/administration_overtime_application.html',810,450);">加班申请</a> 
            <a onclick="fbStart('用印申请','parkmanager.pb/web/process/administration_using_application.html',810,450);">用印申请</a> 
            <a onclick="fbStart('用车申请','parkmanager.pb/web/process/administration_vehicleapplications.html',810,450);">用车申请</a></li>
          </ul>
          <ul>
            <h1>财务类</h1>
            <li>
            <a onclick="fbStart('差旅费用报销单','parkmanager.pb/web/process/finance_trave_expense_reimbursement.html',810,420);">差旅费用报销单</a>
            <a onclick="fbStart('日常费用报销单','parkmanager.pb/web/process/finance_dailyexpense_reimbursement.html',810,420);">日常费用报销单</a>
            <a onclick="fbStart('现金借款单','parkmanager.pb/web/process/finance_cash_loanform.html',810,420);">现金借款单</a>
            <a onclick="fbStart('付款申请单','parkmanager.pb/web/process/finance_paymentrequest.html',810,420);">付款申请单</a>
            <a onclick="fbStart('开票申请单','parkmanager.pb/web/process/finance_applicationform.html',810,420);">开票申请单</a>
            <a onclick="fbStart('日常费用申请','parkmanager.pb/web/process/finance_daily_expense.html',810,420);">日常费用申请</a></li>
          </ul>
          <ul>
            <h1>招商类</h1>
            <li>
            <a onclick="fbStart('招商项目审批单','parkmanager.pb/web/process/business_projectapproval.html',810,420);">招商项目审批单</a>
            <a onclick="fbStart('入驻审批单','parkmanager.pb/web/process/business_approvedlist.html',810,420);">入驻审批单</a>
            <a onclick="fbStart('退房联系单','parkmanager.pb/web/process/business_checklist.html',810,420);">退房联系单</a>
            <a onclick="fbStart('车位交付联系单','parkmanager.pb/web/process/business_deliverylist.html',810,420);">车位交付联系单</a>
            <a onclick="fbStart('车位退租联系单','parkmanager.pb/web/process/business_parkingspaces.html',810,420);">车位退租联系单</a></li>
          </ul>
          <ul>
            <h1>客户服务类</h1>
            <li>
            <a onclick="fbStart('物业联系单','parkmanager.pb/web/process/service_propertylist.html',810,420);">物业联系单</a>
            <a onclick="fbStart('客户服务联系单','parkmanager.pb/web/process/service_customer_service_contactlist.html',810,420);">客户服务联系单</a>
            <a onclick="fbStart('财务联系单','parkmanager.pb/web/process/finance_cash_loanform.html',810,420);">财务联系单</a>
            <a onclick="fbStart('付款申请单','parkmanager.pb/web/process/service_customer_service_contactlist.html',810,420);">付款申请单</a>
            <a onclick="fbStart('开票申请单','parkmanager.pb/web/process/service_financial_contactlist.html',810,420);">开票申请单</a>
            <a onclick="fbStart('日常费用申请','parkmanager.pb/web/process/service_payment_request.html',810,420);">日常费用申请</a></li>
          </ul>
        </div>
      </li>
      <li onmouseover="this.className='libg'" onmouseout="this.className=''" onclick="" class=""><span><img src="core/common/images/refresh2.gif"></span>刷新<em class="em"></em> </li>
    </ul>
  </div>
  <!--//leftemail--> 
</div>

<!--分类导航开始结束--> 

<!--container-->

<div id="container">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="500" valign="top"  id="lsittable"><div class="write_list" style="border-right:1px solid #ddd; width:500px;" id="resizable"> 
          <!--merter_fee-->
          
          <table width="100%" border="0" cellspacing="0" cellpadding="0"  id="lsittable">
            <tr>
              <td class="tdleftc"  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="tdcenterL" width="90">流程名称</td>
              <td class="tdcenterL" width="80"><img src="core/common/images/rightgray.png" width="7" height="7" /> 申请人</td>
              <td class="tdcenterL"  width="90">申请日期</td>
              <td class="tdcenterL" width="110">当前节点</td>
              <td class="tdrightc" width="80">操作</td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,720);" >办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td  class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,420);" >办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td  class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,420);" >办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td  class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,420);">办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td  class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,420);">办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td  class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,420);">办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td  class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,420);">办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td  class="centertd "  width="30"><img src="core/common/images/redxqz.gif" width="9" height="10" /></td>
              <td class="lefttd"  width="90">请假报告</td>
              <td class="lefttd"  width="80">Jonathan</td>
              <td class="centertd"  width="90">2014/7/10</td>
              <td class="centertd" width="110">部门领导审批</td>
              <td class="centertd" width="80"><a href="#">签收</a> <a onclick="fbStart('办理','parkmanager.pb/web/process/process_banli.html',810,420);">办理</a></td>
            </tr>
            <tr onmouseover="this.style.background='#f4f4f4'" onmouseout="this.style.background='#fff'">
              <td class="lefttd">&nbsp;</td>
              <td class="lefttd">&nbsp;</td>
              <td class="centertd">&nbsp;</td>
              <td class="centertd">&nbsp;</td>
              <td class="centertd">&nbsp;</td>
              <td class="centertd">&nbsp;</td>
            </tr>
          </table>
          <!--分页开始-->
          <div class="page" style="border-top:none;">
            <ul>
              <li> <span class="first"></span><span class="pre"></span><span>显示1-6 </span><span class="next"></span><span class="last"></span>共10条</li>
            </ul>
          </div>
          <!--分页结束--> 
        </div></td>
      <td valign="top">
      
      <!--table切换开始-->        
        <div class="apptab" id="tableid">
          <ul>
            <li class="apptabliover"  onclick="parktab(0)" ><a href="parkmanager.pb/web/process/process_flow3.html" target="app">会签单</a></li>
            <li class="apptabli"  onclick="parktab(1)" ><a href="parkmanager.pb/web/process/process_all.html" target="app">流程图</a></li>
            <li class="apptabli" onclick="parktab(2)" ><a href="parkmanager.pb/web/process/process_saomiao.html" target="app">扫描件</a></li>
          </ul>
        </div>        
        <!--//table切换结束-->
            
        <div class="pm_msglist"  style="overflow-x:hidden;">
          <iframe src="parkmanager.pb/web/process/process_flow3.html"  scrolling="yes" frameborder="0" id="pm_msglist"width="100%" name="app"></iframe>
        </div>
       </td>
    </tr>
  </table>
</div>
<!--//container--> 

</body>
</html>

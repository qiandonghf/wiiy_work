<%@page import="com.wiiy.commons.action.BaseAction"%>
<%@page import="com.wiiy.core.dto.ResourceDto"%>
<%@page import="java.util.Map"%>
<%@page import="com.wiiy.commons.preferences.enums.UserTypeEnum"%>
<%@page import="com.wiiy.core.activator.CoreActivator"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--<base href="file://e:\hzsl\"/>-->
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>园区动力_新</title>
<link href="core/common/style/base.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="core/common/style/smartMenu.css" rel="stylesheet" type="text/css" />
<link href="core/web/newdesktop/style/task.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/index.css" rel="stylesheet" type="text/css" />
<link href="core/web/newdesktop/style/index.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/tab.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/menu.js"></script>
<script type="text/javascript" src="core/common/js/righth.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="core/common/js/tab.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#nav li").each(function(index){
		$("#nav li").eq(index).click(function(){
			$("#nav li").removeClass("nav2").addClass("nav1");
			$("#nav li").eq(index).removeClass("nav1").addClass("nav2")
		});
	});
		
});
</script>
<script src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $(".current").mouseover(function(){
  	$(".current").animate({opacity: "0.3"},"slow")  
  }).mouseout(function(){
  	$(".current").animate({opacity: "1"},"slow")  
  })
  
});
</script>
</head>
<!--<body mousedown-1066583751="function(k){try{fb.BK=k.clientX;fb.BL=k.clientY;fb.BJ=k.target;fb.I(function(){try{fb.BK=fb.BL=fb.BJ=c}catch(l){}},250)}catch(k){}}" onmousedown-1066583751="function(s){if(!s){var i=h[fb.UJ];s=i&&i[fb.UP]&&i[fb.UP].event}if(s&&!s.target){s.target=s.srcElement}try{if(h&&h[o]){return h[o](s)}}catch(r){}}">-->
<body>
<!--header-->
<div id="header" class="top "> 
  <!--topnav-->
  <div id="topnav">
    <div class="leftnav"> <span class="span1"><a href="#">园区导航</a></span> <span class="username">管理员 [<a href="#">userName@163.com</a>]</span> <span class="toplink"><a href="#">首页</a>|</span> <span class="setup"><a href="javascript:void(0);" onclick="parent.window.frames['iframemain'].useredit()">账户设置</a></span> </div>
    <div class="rightnav"><a href="#">关于</a>|<a href="javascript:void(0)" onclick="fbStart('修改密码','core/web/core_pwd_edit.html',350,150);">修改密码</a>|<a href="javascript:void(0)" onclick="fblockScreen('锁屏','core/web/lockScreen.html',350,215);">锁屏</a>|<a href="javascript:void(0)">退出</a></div>
  </div>
  <!--//topnav--> 
  <!--headerdown-->
  <div id="headerdown" class="headerdown"> 
    <!--logo-->
    <div id="logo2"><img src="customer/images/loginlogo.png" height="31" /></div>
    <!--//logo--> 
    <!--nav-->
    <div id="nav_new">
      <ul id="mini_nav"><li class="nav1"><a  href="core/web/newdesktop/indexv2_xinxi.html" title="信息"></a></li>
        <li class="nav2"><a href="#" title="汇报"></a></li>
        <li class="nav3"><a href="core/web/newdesktop/indexv2_liucheng.html" title="流程"></a></li>
        <li class="nav4"><a href="#" title="工作"></a></li>
        <li class="nav5"><a href="#" title="文档"></a></li>
        <li class="nav6"><a href="#" title="邮件"></a></li>
        <li class="nav7"><a href="#" title="考勤"></a></li>
        <li class="nav8"><a href="#" title="搜索"></a></li>
      </ul>
      <div class="hackbox"></div>
    </div>
    <!--//nav--> 
    <!--rightpic-->
    <div id="rightpic">
      <div class="time"> 
        <script language=JavaScript>

today=new Date();

function initArray(){

this.length=initArray.arguments.length

for(var i=0;i<this.length;i++)

this[i+1]=initArray.arguments[i] }

var d=new initArray(

" 星期日",

" 星期一",

" 星期二",

" 星期三",

" 星期四",

" 星期五",

" 星期六");

document.write(

"<font color=#7f7f7f style='font-size:9pt;font-family: 宋体'> ",

today.getYear(),"年",

today.getMonth()+1,"月",

today.getDate(),"日",

d[today.getDay()+1],

"</font>" );

</script> 
      </div>
      <ul id="rightpics">
        <li class="pic1"><a href="#" title="主页"></a></li>
        <li class="pic2"><a href="#" title="刷新"></a></li>
        <li class="pic3"><a href="#" title="帮助"></a></li>
        <li class="pic4"><a href="#" title="锁定"></a></li>
      </ul>
    </div>
    <!--rightpic--> 
  </div>
  <!--//headerdown--> 
</div>
<!--//header-->

<div id="contant2">

<div id="sub2" style="display:block;"> 
        <!--subnav-->
        <div id="subnav2" style="background:#f5f5f5;" > 
          <!--topico-->
          <div id="topico">
            <ul>
              <li> <span class="ico1" onmousemove="this.className='ico1over'" onmouseout="this.className='ico1'"></span><span class="ico2" onmousemove="this.className='ico2over'" onmouseout="this.className='ico2'"></span><span class="ico3" onmousemove="this.className='ico3over'" onmouseout="this.className='ico3'"></span><span class="ico4" onmousemove="this.className='ico4over'" onmouseout="this.className='ico4'"></span><span class="ico5" onmousemove="this.className='ico5over'" onmouseout="this.className='ico5'"></span><span class="ico6" onmousemove="this.className='ico6over'" onmouseout="this.className='ico6'"></span><span class="ico8" onmousemove="this.className='ico8over'" onmouseout="this.className='ico8'" onclick="layermenu()"></span> </li>
            </ul>
          </div>
          <!--//topico--> 
          <!--navlist-->
          <div id="wyears"><img src="core/web/newdesktop/images/wnl.gif" width="200" height="175" /> </div>
          <div id="navlist02">
            <ul>
              <li><a href="#">日程管理</a></li>
              <li class="on"><a href="#">任务分配</a></li>
            </ul>
          </div>
          <!--//navlist--> 
        </div>
        <!--//subnav--> 
        <!--sildebottom-->
        <div  class="sildebottom2">
          <div class="leftarrow"><!--<img src="core/common/images/indexarrowleft.png" />--></div>
          <div class="centercon">
            <div class="centerconlist2">
              <ul>
                <li> <a href="core/web/newdesktop/indexv1_01.html"><img src="core/web/newdesktop/images/dongli_ico.png" border="0" title="园区动力" /></a> </li>
                <li class="current"><a href="core/web/newdesktop/indexv2_01.html"><img title="任务分配" src="core/web/newdesktop/images/renwu_ico.png" /></a></li>
                <li><a href="core/web/newdesktop/indexv3_01.html"><img title="业务功能" src="core/web/newdesktop/images/yewsu_ico.png" /></a></li>
              </ul>
            </div>
          </div>
          <div class="rightarrow"><!--<img src="core/common/images/indexarrowright.png" />--></div>
        </div>
        <!--//sildebottom--> 
      </div>


      <div id="subscroll"><img src="core/common/images/scrollleft.gif" width="7" height="45" id="disbtn" onclick="displays();" /></div>
            <div id="news_contenter"  class="main">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_left">
          <tr>
            <td width="351">
            <div class="tasksidebar">
              <div class="task_qiucknav">
                <ul>
                  <li class="taskactive"><span class="taskactive_arrow"></span><a href="core/web/newdesktop/indexv2_01.html">签收与派出的工作</a></li>
                  <li class="tasklink"><a href="core/web/newdesktop/indexv2_02.html">已完成的工作</a></li>
                  </ul>
                </div>
              <!--todowork-->
              <div class="todowork">
                <div class="task_title"><img src="core/web/newdesktop/images/todoWork.png" /></div>
                <ul>
                  <li><span><a href="javascript:void(0)">签收</a></span><a href="javascript:void(0)">·别人给我的我未签收的工作一</a><em>5月30日张庆华派出</em></li>
                  <li><span><a href="javascript:void(0)">签收</a></span><a href="javascript:void(0)">·文字截取最多15个中文汉字</a><em>5月30日张庆华派出</em></li>
                  <li><span><a href="javascript:void(0)">签收</a></span><a href="javascript:void(0)">·文字截取最多15个中文汉字</a><em>5月30日张庆华派出</em></li>
                  <li><span><a href="javascript:void(0)">签收</a></span><a href="javascript:void(0)">·文字截取最多15个中文汉字</a><em>5月30日张庆华派出</em></li>
                  </ul>
                </div>
              
              </div></td>
            <td valign="top"><div class="tasklist">
              <div class="tasklisttitle">
                <div class="tasksearch">
                  <input name="" type="button" class="task_add" onmouseover="this.className='task_add_over'" onmouseout="this.className='task_add'" onclick="fbStart('新建任务','core/web/newdesktop/workmanage/task_add.html',650,430);" />
                  </div>
                <img src="core/web/newdesktop/images/work.png" /></div>
              <div class="taskcontet">
                <ul>
                  <li onmouseover="$(this).addClass('trover')" onmouseout="$(this).removeClass('trover')">
                    <table  width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="15" align="center"><img src="core/common/images/gth.png" width="4" height="10" /></td>
                            <td width="15" align="center"><img src="core/common/images/uploadfj.gif" width="7" height="12" /></td>
                            <td>我自己创建给自己的任务</td>
                            <td width="80"class="taskfnormal">5天后到期</td>
                            </tr>
                          </table></td>
                        </tr>
                      <tr>
                        
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="15" align="center">&nbsp;</td>
                            <td width="15" align="center">&nbsp;</td>
                            <td width="120" class="taskgray">设计部</td>
                            <td class="taskgraypr">开始时间：5月15日  完成情况：<img src="core/web/newdesktop/images/b75.gif" /></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table>
                    </li>
                  <li onmouseover="$(this).addClass('trover')" onmouseout="$(this).removeClass('trover')">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="15" align="center"><img src="core/common/images/gth.png" width="4" height="10" /></td>
                            <td width="15" align="center"><img src="core/common/images/uploadfj.gif" width="7" height="12" /></td>
                            <td>别人给我我已签收的任务 <span class="taskgray">5月22日签收</span></td>
                            <td width="80"class="taskfred">已逾期5天</td>
                            </tr>
                          </table></td>
                        </tr>
                      <tr>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="15" align="center">&nbsp;</td>
                            <td width="15" align="center">&nbsp;</td>
                            <td width="120" class="taskgray">设计部</td>
                            <td class="taskgraypr">开始时间：5月15日  完成情况：<img src="core/web/newdesktop/images/b75.gif" /></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table>
                    </li>
                  </ul>
                </div>
              
              
              
              <div class="quotes"><span class="disabled"> &lt; </span><span class="current">1</span><a href="#?page=2">2</a><a href="#?page=3">3</a><a href="#?page=4">4</a><a href="#?page=5">5</a><a href="#?page=6">6</a><a href="#?page=7">7</a>...<a href="#?page=199">199</a><a href="#?page=200">200</a><a href="#?page=2"> &gt; </a></div>
              
              </div></td>
            </tr>
  </table>
  
        <div class="tasktab tab_right">
          <ul>
            <li class="livisited"><a href="#" target="task_main">我<br/>
              的<br/>
              工<br/>
              作</a></li>
            <li class="lilink"><a href="#" target="task_main">部<br/>
              门<br/>
              工<br/>
              作</a></li>
            <li class="lilink"><a href="#" target="task_main">项<br/>
              目<br/>
              工<br/>
              作</a></li>
            <li class="lilink"><a href="#" target="task_main">员<br/>
              工<br/>
              工<br/>
              作</a></li>
          </ul>
        </div>
      </div>



</div>

<div id="footer"  class="bottom">Copyright ©2010 www.complay.com  |   Tel: 0571-88881234</div>
</body>
</html>

<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=BaseAction.rootLocation %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>园区管理系统</title>
<link rel="stylesheet" type="text/css" href="core/common/style/index.css"/>
<link rel="stylesheet" type="text/css" href="core/common/floatbox/floatbox.css"/>
<link rel="stylesheet" type="text/css" href="core/common/style/tab.css"/>
<link rel="stylesheet" type="text/css" href="core/common/jquery-easyui-1.2.6/themes/default/easyui.css"/>

<script type="text/javascript" src="core/common/js/index.js"></script>
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/tab.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initHeight();
		initTab();
		initMenu();
	});
</script>
</head>

<body>
<div id="contant">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td rowspan="2" valign="top">
				<div id="sub" style="display:block;">
					<div id="subnav">
						<div id="topico">
							<ul>
								<li>
								<span class="ico1" onmousemove="this.className='ico1over'" onmouseout="this.className='ico1'"></span><span class="ico2" onmousemove="this.className='ico2over'" onmouseout="this.className='ico2'"></span><span class="ico3" onmousemove="this.className='ico3over'" onmouseout="this.className='ico3'"></span><span class="ico4" onmousemove="this.className='ico4over'" onmouseout="this.className='ico4'"></span><span class="ico5" onmousemove="this.className='ico5over'" onmouseout="this.className='ico5'"></span><span class="ico6" onmousemove="this.className='ico6over'" onmouseout="this.className='ico6'"></span><span class="ico7" onmousemove="this.className='ico7over'" onmouseout="this.className='ico7'"></span>					</li>
							</ul>
						</div>
						<div id="navlist">
							<ul>
								<li class="li1"><em id="em1" class="b"></em><a href="<%=basePath %>action!service.action" class="tt">服务中心</a>
									<div name="menuid" class="pmmenu"  id="div1" style="display:none; height:0px;"></div>
								</li>
								<li class="li1"><em id="em1" class="b"></em><a href="<%=basePath %>web/messageCenter/write_list.html" class="tt">消息中心</a>
									<div name="menuid" class="pmmenu"  id="div1" style="display:none; height:0px;"></div>
								</li>
							</ul>
						</div>
					</div>
				</div>		
			</td>
			<td rowspan="2"><div id="subscroll"><img id="disbtn" src="core/common/images/scrollleft.gif" onclick="leftHandle();" /></div></td>
		</tr>
		<tr>
			<td valign="top" id="content">
				<div id="tt" class="easyui-tabs"></div>
			</td>
   		</tr>
	</table>
	<div id="footer">Copyright ©2010 www.complay.com  |   Tel: 0571-88881234</div>
</div>
</body>
</html>

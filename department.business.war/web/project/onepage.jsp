<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>onePage</title>
<link href="department.business/web/style/onepage.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery-ui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		if($("#photos").val()!=""){
			var arr = $("#photos").val();
			$("#logo").append($("<img src='core/resources/"+arr+"' width='101' height='81' />"));
		}
		var leaders = $("#leaders").val();
		leaders = eval("(" + leaders + ")");
		for(var i=0; i<leaders.length; i++){
			$("#member"+(i+1)).html(leaders[i].name+" : "+leaders[i].position);
  		}
		var questionMemo = $("#questionMemo").val();
		questionMemo = eval("(" + questionMemo + ")");
		$("#business").html(questionMemo.business);
		$("#demand").html(questionMemo.demand);
		$("#market").html(questionMemo.market);
		$("#customer").html(questionMemo.customer);
		$("#strategy").html(questionMemo.strategy);
		$("#model").html(questionMemo.model);
		$("#power").html(questionMemo.power);
		$("#superiority").html(questionMemo.superiority);

		var gainMemo = $("#gainMemo").val();
		gainMemo = eval("(" + gainMemo + ")");
		for(var i=0; i<gainMemo.length; i++){
     		$("#in"+i).text(gainMemo[i].inMoney);
     		$("#out"+i).text(gainMemo[i].out);
     		$("#profit"+i).text(gainMemo[i].profit);
  		}
	});
</script>
</head>

<body>
<input id="leaders" type="hidden" value="${result.value.leaders}"/>
<input id="gainMemo" type="hidden" value="${result.value.gainMemo}"/>
<input id="questionMemo" type="hidden" value="${result.value.questionMemo}"/>
<input id="photos" type="hidden" value="${result.value.logo}"/>
<div class="onepage">
	<div id="title">${result.value.name }</div>
    <div id="main">
    	<div class="main_con"><h1>业务描述</h1>
        	<ul>
            	<li id="business"></li>
            </ul>
        </div>
        <div class="main_con"><h1>客户需求</h1>
        	<ul>
            	<li id="demand"></li>
            </ul>
        </div>
        <div class="main_con"><h1>产品方案</h1>
        	<ul>
            	<li id="market"></li>
            </ul>
        </div>
        <div class="main_con"><h1>潜在客户</h1>
        	<ul>
            	<li id="customer"></li>
            </ul>
        </div>
        <div class="main_con"><h1>销售策略</h1>
        	<ul>
            	<li id="strategy"></li>
            </ul>
        </div>
        <div class="main_con"><h1>商业模式</h1>
        	<ul>
            	<li id="model"></li>
            </ul>
        </div>
        <div class="main_con"><h1>公司竞争力</h1>
        	<ul>
            	<li id="power"></li>
            </ul>
        </div>
        <div class="main_con"><h1>竞争优势</h1>
        	<ul>
            	<li id="superiority"></li>
            </ul>
        </div>
    </div>
    <div id="line">
    	<div class="line_top"></div>
        <div class="line_bottom"></div>
    </div>
    <div id="info">
    	<div id="logo"></div>
        <div class="infolist">
        	<h1>公司基本信息</h1>
            <ul>
            	<li>
                    <p>公司名称：${result.value.customerName}</p>
                    <p>网址：${result.value.homePage}</p>
                    <p>所属行业：${result.value.industry.dataValue}</p>
                    <p>公司规模：${result.value.employeeCnt}人</p>
                    <p>公司成立时间：<fmt:formatDate value="${result.value.setupTime }" pattern="yyyy年MM月dd日"/></p>
				</li>
            </ul>
        </div>
        
        <div class="infolist">
        	<h1>联系方式</h1>
            <ul>
            	<li>
                    <p>地址：${result.value.address}</p>
                    <p>联系电话：${result.value.phone}</p>
				</li>
            </ul>
        </div>
        
        <div class="infolist">
        	<h1>团队成员</h1>
            <ul>
            	<li>
                    <p id="member1"></p>
                    <p id="member2"></p>
                    <p id="member3"></p>
				</li>
            </ul>
        </div>
        
        <div class="infolist">
        	<h1>项目信息</h1>
            <ul>
            	<li>
                    <p>项目融资额：<fmt:formatNumber value="${result.value.amount}" pattern="#0.00"/>万元</p>
                    <p>融资期限：<fmt:formatDate value="${result.value.endTime }" pattern="yyyy-MM-dd"/></p>
                    <p>目前状态：${result.value.status.title}</p>
				</li>
            </ul>
        </div>
    </div>
    <div class="hackbox"></div>
</div>
<div class="tablediv">
	<h1><span>当前营收状况：<em>${result.value.gainStatus.title}</em>  单位：万元</span>财务报表</h1>
     <table class="tableborder" width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="th">年度</td>
                        <c:forEach items="${years}" var="year">
                        	<td class="th">${year}</td>
                        </c:forEach>
                        <td class="th">&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="td">收入</td>
                        <c:forEach items="${years}" var="year" varStatus="status">
                        	<td class="td" id="in${status.index}">&nbsp;</td>
                        </c:forEach>
                        <td class="td">&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="td">支出</td>
                        <c:forEach items="${years}" var="year" varStatus="status">
                        	<td class="td" id="out${status.index}">&nbsp;</td>
                        </c:forEach>
                        <td class="td">&nbsp;</td>
                      </tr>
                       <tr>
                        <td class="td">净收益</td>
                        <c:forEach items="${years}" var="year" varStatus="status">
                        	<td class="td" id="profit${status.index}">&nbsp;</td>
                        </c:forEach>
                        <td class="td">&nbsp;</td>
                      </tr>
                    </table>
</div>
</body>
</html>

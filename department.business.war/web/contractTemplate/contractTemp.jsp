<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
int order1 = 1;int order2 = 1;
%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=Generator content="Microsoft Word 12 (filtered)">
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:黑体;
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:"Arial Unicode MS";
	panose-1:2 11 6 4 2 2 2 2 2 4;}
@font-face
	{font-family:华文中宋;
	panose-1:2 1 6 0 4 1 1 1 1 1;}
@font-face
	{font-family:仿宋_GB2312;
	panose-1:2 1 6 9 3 1 1 1 1 1;}
@font-face
	{font-family:楷体_GB2312;
	panose-1:2 1 6 9 3 1 1 1 1 1;}
@font-face
	{font-family:"\@Arial Unicode MS";
	panose-1:2 11 6 4 2 2 2 2 2 4;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"\@华文中宋";
	panose-1:2 1 6 0 4 1 1 1 1 1;}
@font-face
	{font-family:"\@黑体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"\@仿宋_GB2312";
	panose-1:2 1 6 9 3 1 1 1 1 1;}
@font-face
	{font-family:"\@楷体_GB2312";
	panose-1:2 1 6 9 3 1 1 1 1 1;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";
	border:none;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	font-size:9.0pt;
	font-family:"Arial Unicode MS","sans-serif";
	color:black;
	border:none;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{margin:0cm;
	margin-bottom:.0001pt;
	font-size:9.0pt;
	font-family:"Arial Unicode MS","sans-serif";
	color:black;
	border:none;}
a:link, span.MsoHyperlink
	{text-decoration:underline;}
a:visited, span.MsoHyperlinkFollowed
	{color:fuchsia;
	text-decoration:underline;}
p.A, li.A, div.A
	{mso-style-name:"正文 A";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:10.5pt;
	font-family:"Times New Roman","serif";
	color:black;
	border:none;}
span.htmlval1
	{mso-style-name:html_val1;
	color:blue;}
.MsoChpDefault
	{font-size:10.0pt;
	border:none;}
 /* Page Definitions */
 @page Section1
	{size:595.0pt 842.0pt;
	margin:72.0pt 90.0pt 72.0pt 90.0pt;}
div.Section1
	{page:Section1;}
-->
</style>

</head>

<body lang=ZH-CN link="#000000" vlink=fuchsia>

<div class=Section1>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:20.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:20.0pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:20.0pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style='font-size:
14.0pt;font-family:宋体'>合同编号：<u><span lang=EN-US>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;${result.value.code}
&nbsp;&nbsp;&nbsp;&nbsp;</span></u></span><u><span style='font-size:14.0pt;
font-family:宋体;color:white'>一</span></u><u><span lang=EN-US style='font-size:
14.0pt;font-family:宋体'>&nbsp; </span></u><span lang=EN-US style='font-size:
14.0pt;font-family:宋体'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:27.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:27.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:27.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:27.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:27.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:27.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.1pt;line-height:
36.0pt'><b><span style='font-size:22.0pt;font-family:华文中宋'>杭州华业高科技产业园有限公司</span></b></p>

<p class=A align=center style='text-align:center;text-indent:24.1pt;line-height:
36.0pt'><span lang=EN-US style='font-size:35.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.1pt;line-height:
36.0pt'><span style='font-size:36.0pt;font-family:黑体'>房屋租赁合同</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:20.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;text-indent:24.0pt;line-height:
22.0pt'><span lang=EN-US style='font-size:20.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='line-height:22.0pt'><span lang=EN-US style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A align=center style='text-align:center;line-height:22.0pt'><span
style='font-size:14.0pt'>年 </span><span lang=EN-US style='font-size:14.0pt'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span
style='font-size:14.0pt'>月</span><span lang=EN-US style='font-size:14.0pt'>&nbsp;&nbsp;&nbsp;
</span><span lang=EN-US style='font-size:14.0pt'>&nbsp;</span><span
style='font-size:14.0pt'>日</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:22.0pt'><span lang=EN-US
style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='line-height:22.0pt'><span lang=EN-US style='font-size:12.0pt'>&nbsp;</span></p>

<p class=A style='text-indent:153.65pt;line-height:30.0pt'><b><span lang=ZH-TW
style='font-size:18.0pt;font-family:黑体'>房屋租赁合同</span></b></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>本合同当事人：</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>出租方（以下简称甲方）：杭州华业高科技产业园有限公司</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>承租方（以下简称乙方）：</span><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp; ${result.value.customerName }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></u><u><span lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp;&nbsp;</span></u><span
style='font-size:12.0pt;font-family:仿宋_GB2312;color:white'>一</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>根据《中华人民共和国合同法》及相关法律法规的规定，甲、乙双方在平等、自愿、协商一致的基础上，就乙方承租甲方房屋事宜，订立本合同。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第一条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 甲方保证所出租的房屋符合国家对租赁房屋的有关规定。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第二条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 房屋的坐落、面积、装修、设施情况</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方出租给乙方的房屋位于杭州市滨江区</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'> </span><u><span
lang=EN-US style='letter-spacing:-.5pt'>${result.value.address }&nbsp; </span></u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>。</span></p>

<p class=A style='text-indent:21.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、出租房屋建筑面积共</span><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp; </span><span
lang=EN-US>${result.value.overallFloorage}</span></u><u><span lang=ZH-TW style='font-size:
12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp; </span></u><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>平方米（承租部分以现场所示为准，含公摊面积）。</span></p>

<p class=A style='text-indent:21.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方已实地察看现状，了解认可房屋结构、周边环境及承租目的相关的全部现状。</span></p>

<p class=A style='line-height:21.0pt'><span lang=ZH-TW style='font-size:12.0pt;
font-family:仿宋_GB2312'>　　<b>第三条</b>甲方应提供房产证、营业执照等文件，乙方应提供身份证明、营业执照等文件。双方验证后可复印对方文件备存。所有复印件仅供本次租赁使用。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第四条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 租赁期限、用途</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、该房屋租赁期共</span><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp; ${result.value.contractDate }&nbsp;&nbsp;
</span></u><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>年，</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312;color:black'>自</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312;color:black'></span><span
lang=EN-US style='font-size:10.0pt;font-family:"Courier New";color:black'><fmt:formatDate value="${result.value.startDate}" type="date" pattern="yyyy-MM-dd"/></span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312;color:black'>起至</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312;color:black'></span><span
lang=EN-US style='font-size:10.0pt;font-family:"Courier New";color:black'><fmt:formatDate value="${result.value.endDate}" type="date" pattern="yyyy-MM-dd"/></span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312;color:black'>止。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（装修期为</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312;color:black'></span><span
lang=EN-US style='font-size:10.0pt;font-family:"Courier New";color:black'><fmt:formatDate value="${result.value.decorateStartDate}" type="date" pattern="yyyy-MM-dd"/>
</span><span lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>― </span><span
lang=EN-US style='font-size:10.0pt;font-family:"Courier New";color:black'><fmt:formatDate value="${result.value.decorateEndDate}" type="date" pattern="yyyy-MM-dd"/></span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>）</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方向甲方承诺，租赁该房屋仅作为办公、研发及相应的仓储使用，不得用作其他用途。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第五条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 租金及支付方式</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、该房屋第一年每日每平方米租金￥</span><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp; ${result.value.rentPrice}&nbsp;&nbsp;&nbsp;&nbsp;
</span></u><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>元，每年租金在上一年的价格基础上逐年递增</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>5%</span><span
style='font-size:12.0pt;font-family:仿宋_GB2312'>。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>付款的时间及方式：先付后用，</span><u><span
lang=EN-US style='font-family:宋体'>${result.value.payType}</span></u><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>，</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>每期租金按照实际天数结算。乙方在双方签订租房合同之日起三天内付清第一期租金，金额（大写）</span><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>${result.value.realFeeZJ2}&nbsp; </span></u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>（小写</span><span
style='font-size:12.0pt;font-family:仿宋_GB2312'>）</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>￥</span><u><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp; &nbsp;&nbsp;&nbsp;${result.value.realFeeZJ}&nbsp;&nbsp;&nbsp;
</span></u><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>元；后一期的租金应在前期已付租金对应的租赁期限届满前一个月一次性付清。租金的支付，由甲方开具发票给乙方。</span></p>

<p class=A style='text-indent:28.5pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、 定金支付方式如下：</span></p>

<p class=A style='margin-left:6.0pt;text-indent:24.0pt;line-height:21.0pt'><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>在签订本合同三</span><span
style='font-size:12.0pt;font-family:仿宋_GB2312'>天</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>内，乙方向甲方支付约年租金</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>20%</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>的金额（大写）</span></p>

<p class=A style='margin-left:6.0pt;line-height:21.0pt'><u><span
style='font-size:12.0pt;font-family:仿宋_GB2312;color:white'>一</span></u><u><span
style='font-size:12.0pt;font-family:仿宋_GB2312'> </span></u><u><span
style='font-size:12.0pt;font-family:仿宋_GB2312;color:white'>二</span></u><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp; &nbsp;${result.value.realFeeDJ2}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></u><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>（小写）￥</span><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp;
${result.value.realFeeDJ}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></u><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>元作为定金。</span></p>

<p class=A style='text-indent:23.6pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第六条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 租赁期间，乙方应按时交纳自行负担的费用（包括但不限于该房屋水电费及公共能耗费、宽带费、电话费、有线电视费、物业管理费、自备车辆停车费等使用过程中产生的费用）。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第七条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 房屋修缮与使用</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、在租赁期内，该房屋及所属设施、设备的修缮、维修责任，均由乙方负责（房屋结构及公共部分由甲方负责修缮维护），乙方应当自行保障租赁房屋及附属设施、设备处于安全、正常可以使用的状态。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方对租赁区域的消防、安全生产等负全责，必须严格执行国家和政府职能部门的消防条例和规范，接受甲方（或物业服务公司）对安全生产的要求，否则由此造成的一切后果均由乙方负经济和法律责任（包括对甲方及第三方造成的损失）。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方（包括乙方员工或顾客、代理人）应合理使用并爱护其所承租的房屋及其附属设施、设备（含公共区域设施、设备），产生损坏、故障或使用不当造成房屋及设施、设备损坏的，乙方应立即负责修复或经济赔偿（智能空调统一由甲方指定的维保单位维修，产生的费用由乙方承担）。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>4</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方应配合甲方或物业服务公司进入该房屋进行例行的检查、养护、维修，甲方应尽力减少对乙方工作的影响。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>5</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方需对房屋装饰装修或者增设附属设施、设备的，应事先征得甲方或物业服务公司的书面同意，按照规定须向有关部门报批的，应取得批准后，方可施工。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>6</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方在房屋分隔或装饰装修时，不得损坏和改变原建筑结构、公共设施（<b>严禁在玻璃幕墙铝合金框打孔</b>）。未经甲方或物业服务公司认可不得擅自改装、移装或加装房屋内外的空调、通风、消防等设施和管线。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>7</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方在装饰装修设计和施工时，必须严格遵守甲方和物业服务公司制定的有关制度和规定。在装饰装修或退租恢复标准装修时，如有对建筑结构、设施设备及公共部位或第三者造成损害的，乙方应承担修复和赔偿责任。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>8</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、未经甲方书面同意，乙方不得在该房屋以外的任何部位竖立、张贴任何文字、图案等标识或广告等宣传物品。</span></p>

<p class=A style='text-indent:28.5pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>9</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、租赁期满或因乙方责任导致退租、终止或者解除合同或经双方协商一致退租、终止或者解除合同的，由乙方负责恢复到双方初次交房时的标准装修状态（</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:楷体_GB2312'>中央空调、吊顶、水泥砂浆地坪、电线入户）</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>。如委托甲方恢复的要先行支付相应费用；乙方退房但未恢复前，仍须计付租金。如乙方承租属作纯办公使用，且以玻璃铝合金作隔断的（离吊顶</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>30</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>厘米以上），退租时乙方</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>对原房屋进行装饰装修的墙面（含门窗）、隔断、窗帘、地板地毯及依附于墙面地上的管线等无破坏性拆除，处于正常可使用状态的，可不恢复标准装修状态，但装饰装修物均无偿归甲方所有。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>10</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、房屋的使用必须符合设计的要求，每平方米的使用荷载不得超过</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>200</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>公斤；乙方用电量应控制在每平方米</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>40</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>伏安内。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第八条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 房屋的转让与转租</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、租赁期间，甲方有权依照法定程序转让该出租的房屋，转让后，本合同对新的房屋所有人和乙方继续有效。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方出售房屋，须在一个月前书面通知乙方，在同等条件下，乙方有优先购买权。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、未经甲方书面同意，乙方不得转租、转借该承租房屋。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第九条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 合同的变更、解除与终止</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲乙双方可以协商变更或终止本合同，如需提前收回房屋或退租的，提出方需提前</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>个月书面通知对方。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方不能提供房屋或所提供房屋不符合约定条件、严重影响使用的，乙方有权解除合同。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、房屋租赁期间，乙方有下列行为之一的，甲方有权解除合同，收回出租房屋：</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）未经甲方书面同意，转租、转借或者部分转租、转借承租房屋。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）未经甲方书面同意，擅自拆改、变动房屋结构或甲方提供的内部设施、设备的。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）损坏承租房屋，在甲方提出的合理期限内仍未修复的。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>4</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）未经甲方书面同意，改变或者部分改变本合同约定的房屋租赁用途。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>5</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）乙方涉及易燃易爆、有毒等危险物品未得到政府部门允许存放或存在严重安全隐患的。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>6</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）乙方所经营项目不符合国家法律、地方法规规定而违法经营的。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>7</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）拖欠房屋租金累计一个月以上或者逾期支付租金及其他费用一个月以上的。</span></p>

<p class=A style='text-indent:18.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>8</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）乙方发生经济困难，甲方可合理预见其可能不能及时支付租金、物业管理费用或甲方其他垫付款项等费用；乙方遇重大诉讼（诉讼标的50万元人民币以上）或者多起诉讼，进入破产程序，或乙方经营资格被政府主管部门宣布吊销、中止或被采取其他类似措施的。</span></p>

<p class=A style='text-indent:21.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>9</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）乙方未持有生产许可证，不符合消防、环保、治安、防疫要求的。</span></p>

<p class=A style='text-indent:18.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（</span><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>10</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>）乙方损害甲方、物业管理方对出租房屋的管理秩序，损害承租甲方房屋的第三方正当权益的。</span></p>

<p class=A style='text-indent:18.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>（11）乙方违反本</span><span
style='font-size:12.0pt;font-family:仿宋_GB2312'>合同</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>的其他约定，在甲方要求其纠正后在15日内仍未纠正的。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>4</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、租赁期满本合同自然终止。乙方要继续租赁的，同等条件下，乙方享有优先承租权。但应当在租赁期满三个月前书面通知甲方，否则视为乙方放弃优先承租权。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>5</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、因不可抗力因素导致合同无法履行的，合同终止。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 房屋交付及收回的验收</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方应保证交付的租赁房屋本身及附属设施、设备处于能够正常可使用状态。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、验收时双方共同参与，如对房屋、设施设备及装修有异议应当场提出。当场难以检测判断的，应于十日内向对方主张。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方应于房屋租赁期满时，将承租房屋及附属设施、设备交还甲方。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>4</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方交还甲方房屋应当保持房屋（含原有装修）及设施、设备的完好、清洁状态，不得留存物品或影响房屋的正常使用。租赁期满或提前终止后对未经甲方书面同意留存的物品，视同乙方放弃对物品的所有权，甲方有权以废弃物处理该等物品。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十一条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>甲方的违约责任</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方因不能提供本合同约定的房屋而解除合同的，应返还乙方支付的定金，并赔偿乙方定金等量的违约金。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方违反本合同约定，提前收回房屋的，应按实退还乙方未使用时段已交的租金及定金，并赔偿乙方定金等量的违约金。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方延期交付房屋的，每逾期一天，则应按乙方已付租金的5‰向乙方支付给违约金。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>4.</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>乙方为实现债权的费用（包括但不限于公证费、律师费、评估费、诉讼费）由甲方承担（为双方权利）</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十二条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 乙方的违约责任</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、租赁期内，由于乙方提出，虽经双方协商一致解除合同的，乙方应向甲方赔偿定金等量的违约金。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、租赁期间，当出现乙方违约而甲方有权解除合同的情形时，甲方有权终止合同，收回该房屋，乙方已交付的租金及相关费用不予退还，没收定金并有权要求乙方赔偿损失。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、在租赁期内，乙方逾期交纳本合同约定应由乙方负担的费用的，每逾期一天，则应按上述未交费用总额的5‰向甲方支付给滞纳金。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>4</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、在租赁期内，乙方未经甲方同意，中途擅自退租的，本合同终止，已交付的租金及相关费用不予退还，并没收定金，上述金额不足以弥补甲方损失的，甲方有权要求乙方赔偿。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>5</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、租赁期满或因乙方责任导致退租、终止或者解除合同或经双方协商一致退租、终止或者解除合同时，乙方应如期交还该房屋，并按约定恢复原状。不恢复原状或者迟延恢复原状的，视为乙方未交还房屋，乙方仍需按约支付租金。甲方对附着于房屋的装饰装修不作任何补偿及赔偿。乙方逾期交还，则每逾期一日应向甲方支付原日租金两倍的违约金。乙方逾期超过</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>15</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>天以上未归还房屋，甲方有权强行收回该房屋，并没收履约保证金，乙方所留装饰装修及物品无条件由甲方处理，且甲方无需支付乙方任何补偿及赔付。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>6</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、甲方为实现债权的费用（包括但不限于公证费、律师费、评估费、诉讼费）由乙方承担。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十三条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 免责条件</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、因不可抗力原因或者因为房屋被政府部门征收等原因致使本合同不能继续履行的，合同终止履行。甲、乙双方互不承担违约、赔偿责任。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、因上述原因而终止合同的，租金按照实际使用时间计算，多退少补。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十四条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 本合同未尽事宜，经甲、乙双方协商一致，可订立补充条款。补充条款及附件均为本合同组成部分，与本合同具有同等法律效力。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十五条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 争议解决</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>本合同项下发生的争议，由双方当事人协商或申请调解；协商或调解解决不成的，依法向本合同项下房屋所在地人民法院提起诉讼。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十六条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 其他约定事项</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>1</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、房屋所在地在行政和财政上属于高新区（滨江）长河街道管辖，乙方应按《统计法》要求，每月定期将相关的财务统计数据上网申报（网址：</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>http</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>：</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>//tjj.hhtz.gov.cn</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>）或与甲方财务部联系。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>2</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、乙方使用的水、电结算费用：按水费</span><u><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>3.90</span></u><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>元</span></u><u><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>/M<sup>3</sup></span></u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>，电费</span><u><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>0.995</span></u><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>元</span></u><u><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>/kwh</span></u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>，公摊能耗费每平方米每月</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>1.3</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>元计算收取。若遇国家政策调整水、电费价格，则以上价格同等调整。</span></p>

<p class=A style='text-indent:30.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>3</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、为加强园区管理，甲方委托指定的物业服务公司进行物业管理，由物业服务公司收取（或代收）房屋租赁费、水电费、物业管理费、自备车辆停车费等费用。乙方需要与物业服务公司签订物业服务合同和物业使用公约等。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>4</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、如乙方工商登记注册在甲方所在地的，乙方必须在租期届满（包括解除、终止）后十五日内无条件地变更工商登记地点、办理注销登记手续。对此，甲方无须对乙方作出任何补偿或者赔付。乙方逾期办理住所地变更或者注销登记手续的，视为乙方违约，乙方应按年租金总额的每日千分之五向甲方支付违约金。影响甲方房屋出租的，还应当赔偿甲方租金损失。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>5</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>、租赁期满，乙方按本合同约定如期交还该房屋，并无违约行为，且办理完工商注册登记变更手续（如有）后，甲方在</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>10</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>个工作日内将乙方的定金无息退还乙方。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>6、本合同空格内填写的文字与印刷文字同样有效。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>7、对于本合同全部条款以及甲乙双方的权利义务及责任，甲方已经在订立合同前充分提示乙方注意，乙方对于本合同全部条款的内容及含义作了充分的了解，不存在任何异议，并自愿与甲方订立本合同。</span></p>

<p class=A style='text-indent:24.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>8</span><span style='font-size:
12.0pt;font-family:仿宋_GB2312'>、</span><span lang=ZH-TW style='font-size:12.0pt;
font-family:仿宋_GB2312'>乙方在本合同项下接受甲方</span><span lang=ZH-TW style='font-size:
12.0pt;font-family:仿宋_GB2312'>文件的联系地址</span><u><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp; &nbsp;${result.value.customerAddress}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span></u><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>，</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'> </span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>联系人</span><u><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
${result.value.customerContact} &nbsp;&nbsp;&nbsp;</span></u><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>，联系电话</span><u><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${result.value.phone}&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></u><span lang=ZH-TW style='font-size:
12.0pt;font-family:仿宋_GB2312'>。如发生变化的，应立即通知甲方，否则甲方仍按本条约定地址送达，一经甲方邮寄，即视为有效送达。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十七条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'> 本合同自甲、乙双方签署后生效。</span></p>

<p class=A style='text-indent:24.1pt;line-height:21.0pt'><b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>第十八条</span></b><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>本合同及附件一式四份，由甲</span><span
style='font-size:12.0pt;font-family:仿宋_GB2312'>、</span><span lang=ZH-TW
style='font-size:12.0pt;font-family:仿宋_GB2312'>乙</span><span style='font-size:
12.0pt;font-family:仿宋_GB2312'>双</span><span lang=ZH-TW style='font-size:12.0pt;
font-family:仿宋_GB2312'>方各执二份。具有同等法律效力。</span></p>

<p class=A style='text-indent:48.0pt;line-height:21.0pt'><span lang=EN-US
style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;</span></p>

<p class=A style='line-height:21.0pt'><span lang=ZH-TW style='font-size:12.0pt;
font-family:仿宋_GB2312'>甲方（盖章）：</span><span lang=ZH-TW style='font-size:12.0pt;
font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>乙方（盖章）：</span></p>

<p class=A style='line-height:21.0pt'><span lang=ZH-TW style='font-size:12.0pt;
font-family:仿宋_GB2312'>代表签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;代表签字：</span></p>

<p class=A style='line-height:21.0pt'><span lang=ZH-TW style='font-size:12.0pt;
font-family:仿宋_GB2312'>联系电话：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;&nbsp;联系电话：</span></p>

<p class=A align=right style='text-align:right;text-indent:24.0pt;line-height:
21.0pt'><span lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;</span></p>

<p class=A align=right style='text-align:right;text-indent:24.0pt;line-height:
18.0pt'><span lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>&nbsp;</span></p>

<p class=A align=left style='text-align:left;line-height:18.0pt'><span
lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>收款单位：杭州华业高科技产业园有限公司</span></p>

<p class=A align=left style='margin-right:24.0pt;text-align:left;line-height:
18.0pt'><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>账号：</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>19045301040003575</span></p>

<p class=A align=left style='margin-right:24.0pt;text-align:left;line-height:
18.0pt'><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>开户银行：农行杭州高新支行</span></p>

<p class=A align=left style='margin-right:24.0pt;text-align:left;line-height:
18.0pt'><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>税号：</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>330107742029339</span></p>

<p class=A align=left style='margin-right:24.0pt;text-align:left;line-height:
18.0pt'><span lang=ZH-TW style='font-size:12.0pt;font-family:仿宋_GB2312'>营业执照号码：</span><span
lang=EN-US style='font-size:12.0pt;font-family:仿宋_GB2312'>330108000025399</span></p>

</div>

</body>

</html>

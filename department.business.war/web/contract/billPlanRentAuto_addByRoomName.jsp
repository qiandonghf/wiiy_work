<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="enum" uri="http://www.wiiy.com/taglib/enum" %>
<%@page import="java.text.SimpleDateFormat"%>
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
<link href="core/common/style/content.css" rel="stylesheet" type="text/css" />
<link href="core/common/style/layerdiv.css" rel="stylesheet" type="text/css" />
<link href="core/common/floatbox/floatbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="core/common/calendar/calendar.css" />
<script type="text/javascript" src="core/common/floatbox/floatbox.js"></script>
<script type="text/javascript" src="core/common/js/jquery.js"></script>
<script type="text/javascript" src="core/common/js/tools.js"></script>
<script type="text/javascript" src="core/common/js/jquery.form.js"></script>
<script type="text/javascript" src="core/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="core/common/calendar/calendar-zh.js"></script>
<script type="text/javascript">
		$(function(){
			initTip();
			initForm(); 
			returnValue();
		});
		 function initForm(){
			$("#form1").validate({
				errorPlacement: function(error, element){
					showTip(error.html());
				},
				submitHandler: function(form){
					if($("#endDate").val()<$("#startDate").val()){
						showTip("计费开始日期不能小于计费结束日期",3000);
						return;
					}
					var rent = getMap();
					for(var i=0;i<$(".roomIds").length;i++){
							rent.put("roomId",$(".roomIds").eq(i).val());
							rent.put("roomName",$(".roomNames").eq(i).val());
							rent.put("feeType",'BUSINESS_ZJ');
							rent.put("planFee",$(".planFees").eq(i).val());
							rent.put("realFee",$(".realFees").eq(i).val());
							rent.put("startDate",$(".startDates").eq(i).val());
							rent.put("endDate",$(".endDates").eq(i).val());
							rent.put("planPayDate",$(".planPayDates").eq(i).val());
							parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().appendBillPlanRent(rent);
					}
					
					parent.fb.end();
				}
			});
		}
		
		function getMap() {//初始化map_,给map_对象增加方法，使map_像Map    
	         var map_ = new Object();    
	         map_.put = function(key, value) {    
	             map_[key+'_'] = value;    
	         };    
	         map_.get = function(key) {    
	             return map_[key+'_'];    
	         };    
	         map_.remove = function(key) {    
	             delete map_[key+'_'];    
	         };    
	         map_.keyset = function() {    
	             var ret = "";    
	             for(var p in map_) {    
	                 if(typeof p == 'string' && p.substring(p.length-1) == "_") {    
	                     ret += ",";    
	                     ret += p.substring(0,p.length-1);    
	                 }    
	             }    
	             if(ret == "") {    
	                 return ret.split(",");    
	             } else {    
	                 return ret.substring(1).split(",");    
	             }    
	         };    
	         return map_;    
		} 
		
		function setSelectedRoom(room){
			if(room["kind.dataValue"]!='出租' || room["status.title"]!='空闲'){
				if(!confirm("该房间状态不是空闲 或 性质不是出租 是否继续")){
					return false;
				}
			}
			$("#roomId").val(room.id);
			$("#roomName").val(room["building.park.name"]+room["building.name"]+" "+room.name);
		}
		 function  DateDiff(sDate1,sDate2){    
		       var aDate, oDate1, oDate2, iDays ; 
		       aDate  =  sDate1.split("-");  
		       oDate1  =  new Date(aDate[1] + '-' + aDate[2].substr(0,2) + '-' + aDate[0]);   
		       aDate  =  sDate2.split("-");
		       oDate2  =  new Date(aDate[1] + '-' + aDate[2].substr(0,2) + '-' + aDate[0]);  
		       iDays  =  parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24);  
		       return  iDays  
		   }      
 		function betweenDate(st,endTimes,type){
 				var dt=parseDate(st);
				var date = new Date(dt);
				var nYear = date.getFullYear();
				var nMonth = date.getMonth()+1;
				var nDay = date.getDate();
				var nt = date.getTime();
				if(type == 'SIXMONTH'){
					var nextMonth=nMonth+6
				}
				if(type == 'THREEMONTH'){
					var	nextMonth= nMonth+3;
				}
				if(nextMonth <=12){
					date.setMonth(nextMonth -1);
				}else{
					date.setYear(nYear+1);
					date.setMonth(nextMonth-13);
				}
				var nextT = date.getTime();
				if(st<endTimes){
					var dayAmount=((nextT-nt)/(24*3600*1000));
					date.setTime(nextT-(24*3600*1000));
					var nextDate=(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
				}else{
					alert("未测试出的BUG");
				}
			return dayAmount+","+nextDate; 
		}    
 		function checkDate(dateStr){
 			var mm=dateStr.split("-");
			return (mm[1]<10 && mm[2]<10)? (jdEndTimes=mm[0]+"-0"+mm[1]+"-0"+mm[2]) : ((mm[1]>=10 && mm[2]>=10)?dateStr:((mm[1]>=10)?(jdEndTimes=mm[0]+"-"+mm[1]+"-0"+mm[2]):(jdEndTimes=mm[0]+"-0"+mm[1]+"-"+mm[2])));
 		}
 		function payCost(days,area,rentPrice){
 			var amount=parseInt(days)*area*rentPrice;
 			return amount;
 		} 
 		 function dayAddauto(jdEndTimes){
 			var dd=parseDate(jdEndTimes); 
 			var date=new Date(dd);
 			var nYear = date.getFullYear();
			var nMonth = date.getMonth()+1;
			var nDay = date.getDate();
			var nt=date.getTime();
 			date.setTime(nt+(24*3600*1000));
 			var nextDate=(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
 			return nextDate;
 		 } 
		   
 		function countDays(yearNum,startTimes,jdEndTimes){
			var date=new Date(startTimes);
			date.setFullYear(date.getFullYear()+yearNum);
			if(yearNum>0){
				date.setDate(date.getDate()-1);
			}
			var newDate=(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
			var addDays=DateDiff(newDate,jdEndTimes);//天数
			return addDays;
		}
		function returnValue(){
			alert("自动生成资金计划");
			if(parent.fb.instances.length>1){
				var rate=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().rateValue();
				var year=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().yearValue();
				var room = parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().roomValue();
			}else{
				return;
			}
		    var type = $("#payIds").val();
			var roomNames;
			var dateStr;
			var jdEndTimes;
			var area;
			var days;
			var str;
			var payAmount;
			var rentPrice;
			if(type == 'THREEMONTH'){
				for(var i = 0;i<room.length;i++){
					var flag = true;
					var	startTimes =parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().startValue(room[i]);
					var	endTimes =parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().endValue(room[i]);//合同结束时间
					roomNames=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().roomName(room[i]);
					area=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().areaValue(room[i]);//平方
					rentPrice=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().rentValue(room[i]);//单价
					var st=startTimes;
					var adds=parseInt(1);
					var addsArray=1;
			 		var yearSta=startTimes.substr(0,4);//租赁开始的年
					var yearNum=0;
					var arr=1;
					if(year!=null&&year.length>0){
							var j=0;
							while(j<year.length){
								adds=(adds*(1+rate[j]/100)).toFixed(4);//1.02 1.071 
								addsArray +=","+adds;
								j++;// 1 2
							}
							arr=addsArray.split(",");
					}
					do{
						var nextY=st.substr(0,4);
						yearNum=parseInt(nextY)-parseInt(yearSta);
						dateStr=betweenDate(st,endTimes,type); //传开始时间 得到一季度后的时间 和之间的天数
						str=dateStr.split(",");//转成数组
						jdEndTimes=checkDate(str[1]);//格式化后自增一季度的时间
						if(jdEndTimes>endTimes){
							 jdEndTimes=endTimes;
							 days=DateDiff(st,endTimes)+1;
						 }else{
							 days=DateDiff(st,jdEndTimes)+1;//
						 }
     					var dd=countDays(yearNum,startTimes,jdEndTimes);//第几年，开始时间，每次起始时间  用于计算出是否下一年
						if(arr.length>=2 && year[year.length-1]>=yearNum){
							if(dd>=0 && yearNum!=0){
								if(dd==0){
									var k=0;
									for(var y=0;y<year.length;y++){
										if(year[y]==yearNum){
											k=y;
										}
									}
									payAmount=days*area*rentPrice*arr[k];//取之前的增长率
								}else{
									var k=0;
									for(var y=0;y<year.length;y++){
										if(year[y]<=yearNum){
											k++;
										}
									}
									payAmount=days*area*rentPrice*arr[k];//第几年的年份在 增长率里有 
								}
							}else{
								payAmount=days*area*rentPrice*arr[0];
							}
						}else{
							payAmount=days*area*rentPrice*arr;
						}
						var html="<tr>";
						if(flag){
							html +="<td height=\"25px\"><input style=\"text-align:center;width:64px; \" type=\"checkbox\" name=\"checkbox\" checked=\"checked\" \/></td>";	
							flag= false;
						}else{
							html +="<td height=\"25px\"><input style=\"text-align:center;width:64px; \" type=\"checkbox\" name=\"checkbox\" \/></td>";	
						}
						html +="<td height=\"25px\"><input style=\"text-align:center;border:1;\" id=\"roomName\" class=\"roomNames\" value=\""+roomNames+"\" type=\"hidden\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\"><input style=\"text-align:center;border:1;width:42px;\" id=\"roomId\" class=\"roomIds\" value=\""+room[i]+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\"><input style=\"text-align:center;border:1;width:128px; \" id=\"startDate\" class=\"startDates\" value=\""+st+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:128px; \" id=\"endDate\" class=\"endDates\"  value=\""+jdEndTimes+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:42px; \" id=\"days\" class=\"days\"  value=\""+days+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:85px; \" id=\"planFee\" class=\"planFees\"  value=\""+(payAmount).toFixed(2)+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:85px; \" id=\"realFee\" class=\"realFees\" value=\""+(payAmount).toFixed(2)+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:85px; \" id=\"planPayDate\" class=\"planPayDates\"  value=\""+st+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
					    html +="</tr>"
					    $("#noData").remove();
						$("#planListTBody").append(html);
						var tt= dayAddauto(jdEndTimes);
						var tt=checkDate(tt);
						st=tt;//每次把时间作为开始时间
					}while(st<=endTimes)
				}
			}else if(type='SIXMONTH'){
				for(var i = 0;i<room.length;i++){
					var flag = true;
					var	startTimes =parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().startValue(room[i]);
					var	endTimes =parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().endValue(room[i]);//合同结束时间
					roomNames=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().roomName(room[i]);
					area=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().areaValue(room[i]);//平方
					rentPrice=parent.fb.instances[parent.fb.instances.length-2].getIframeWindow().rentValue(room[i]);//单价
					var st=startTimes;
					var adds=parseInt(1);
					var addsArray=1;
			 		var yearSta=startTimes.substr(0,4);//租赁开始的年
					var yearNum=0;
					var arr=1;
					if(year!=null&&year.length>0){
							var j=0;
							while(j<year.length){
								adds=(adds*(1+rate[j]/100)).toFixed(4);//1.02 1.071 
								addsArray +=","+adds;
								j++;// 1 2
							}
							arr=addsArray.split(",");
					}
					do{
						var nextY=st.substr(0,4);
						yearNum=parseInt(nextY)-parseInt(yearSta);
						dateStr=betweenDate(st,endTimes,type); //传开始时间 得到一季度后的时间 和之间的天数
						str=dateStr.split(",");//转成数组
						jdEndTimes=checkDate(str[1]);//格式化后自增一季度的时间
						if(jdEndTimes>endTimes){
							 jdEndTimes=endTimes;
							 days=DateDiff(st,endTimes)+1;
						 }else{
							 days=DateDiff(st,jdEndTimes)+1;//
						 }
     					var dd=countDays(yearNum,startTimes,jdEndTimes);//第几年，开始时间，每次起始时间  用于计算出是否下一年
						if(arr.length>=2 && year[year.length-1]>=yearNum){
							if(dd>=0 && yearNum!=0){
								if(dd==0){
									var k=0;
									for(var y=0;y<year.length;y++){
										if(year[y]==yearNum){
											k=y;
										}
									}
									payAmount=days*area*rentPrice*arr[k];//取之前的增长率
								}else{
									var k=0;
									for(var y=0;y<year.length;y++){
										if(year[y]<=yearNum){
											k++;
										}
									}
									payAmount=days*area*rentPrice*arr[k];//第几年的年份在 增长率里有 
								}
							}else{
								payAmount=days*area*rentPrice*arr[0];
							}
						}else{
							payAmount=days*area*rentPrice*arr;
						}
						var html="<tr>";
						if(flag){
							html +="<td height=\"25px\"><input style=\"text-align:center;width:64px; \" type=\"checkbox\" name=\"checkbox\" checked=\"checked\" \/></td>";	
							flag= false;
						}else{
							html +="<td height=\"25px\"><input style=\"text-align:center;width:64px; \" type=\"checkbox\" name=\"checkbox\" \/></td>";	
						}
						html +="<td height=\"25px\"><input style=\"text-align:center;border:1;\" id=\"roomName\" class=\"roomNames\" value=\""+roomNames+"\" type=\"hidden\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\"><input style=\"text-align:center;border:1;width:42px;\" id=\"roomId\" class=\"roomIds\" value=\""+room[i]+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\"><input style=\"text-align:center;border:1;width:128px; \" id=\"startDate\" class=\"startDates\" value=\""+st+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:128px; \" id=\"endDate\" class=\"endDates\"  value=\""+jdEndTimes+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:42px; \" id=\"days\" class=\"days\"  value=\""+days+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:85px; \" id=\"planFee\" class=\"planFees\"  value=\""+(payAmount).toFixed(2)+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:85px; \" id=\"realFee\" class=\"realFees\" value=\""+(payAmount).toFixed(2)+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
						html +="<td height=\"25px\" ><input style=\"text-align:center;border:1;width:85px; \" id=\"planPayDate\" class=\"planPayDates\"  value=\""+st+"\" type=\"text\" readonly=\"readonly\" \/></td>";	
					    html +="</tr>"
					    $("#noData").remove();
						$("#planListTBody").append(html);
						var tt= dayAddauto(jdEndTimes);
						var tt=checkDate(tt);
						st=tt;//每次把时间作为开始时间
					}while(st<=endTimes)
				}
			}
		}
	</script>
</head>

<body>
<form action="<%=basePath %>billPlanRent!save.action" method="post" name="form1" id="form1">
<input type="hidden" id="roomIds" value="${param.roomIds }"/>
<input type="hidden" id="payIds" value="${param.settleType }"/>
<div class="basediv" >
	<div style="height:315px;overflow:auto">
	<div class="divlays" style="margin:0px;">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tbody  >
			<tr>
				<td class="tdcenter" width="60px;" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'" >是否首付</td>
				<td  class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">房间</td>
				<td  class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计费开始周期</td>
				<td  class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计费结束周期</td>
				<td  class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">天数</td>
				<td  class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">计划金额</td>
				<td  class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">实际金额</td>
				<td  class="tdcenter" onmousemove="this.className='tdcenterover'" onmouseout="this.className='tdcenter'">付费时间</td>
	        </tr>
        </tbody>
		</table>
	</div>
	<div>
		<table id="planListTBody"  width="100%"  border="0" cellspacing="0" cellpadding="0" >
				<tr id="noData" >
	          		<td colspan="4" align="center" height="25px" style="color: #aaaaaa"></td>
	          	</tr>
		</table>
	</div>	
	</div>
</div>
	<div class="buttondiv">
		<label><input name="Submit" type="submit" class="savebtn" value="" /></label>
		<label><input name="Submit2" type="button" class="cancelbtn" value="" onclick="parent.fb.end();"/></label>
	</div>
</form>
</body>
</html>

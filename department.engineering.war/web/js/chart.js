var doc=document;
var projectName;
var projectId;
var schedules;
var contractsData;
var projectDate;
var projectAmount;
var contractName;
var options = {
		chart : {
			events : {
				//load : onChartLoad
			}
		},
		xAxis : {
			type : 'datetime',
			minTickInterval : 365 * 24 * 36e5,
			labels : {
				align : 'left'
			},
			plotBands : [{
				from : Date.UTC(1970, 1, 1),
				to : new Date(),
				color : '#EFFFFF',
				label : {}
			}]
		},
		tooltip : {
			style : {
				width : '250px'
			}
		},
		plotOptions : {
			series : {
				marker : {
					enabled : false,
					symbol : 'circle',
					radius : 3
				},
				fillOpacity : 0.5
			},
			flags : {
				tooltip : {
					xDateFormat : '%B %e, %Y'
				}
			}
		},
		series : []
};

$(function(){
	initHeight();
	loadProjectList();
});


/* 处理返回的项目信息 */
function dealProjects(projects){
	var o = doc.createElement("select");
	o.options[0] = new Option("---选择项目---", "");
	for(var i = 0; i< projects.length; i++){
		var option = doc.createElement("option");
		option.innerHTML = projects[i].name;
		option.value =  projects[i].id;
		option.setAttribute("data", projects[i].schedules);
		option.setAttribute("time", projects[i].startDate);
		option.setAttribute("amount", projects[i].amount);
		$(o).append($(option));
	}
	o.onchange = function(){
		var id = $(this).val();
		if(!!id){
			projectName = $(this).find("option:selected").text();
			schedules = $(this).find("option:selected").attr("data");
			projectDate = $(this).find("option:selected").attr("time");
			projectAmount = $(this).find("option:selected").attr("amount"); 
			projectId = id;
			loadContractList(id);
		}else{
			projectName = "";
			projectId = "";
			schedules = "";
			contracts = "";
			contractsData = "";
			projectDate = "";
			projectAmount = "";
			contractName = "";
			removeChart();
		}
	}
	$(".select").append($(o));
}

/*处理返回的合同信息*/
function dealContracts(contracts){
	contractsData = contracts;
	var html = $(".contracts");
	$(html).html("");
	var ul = doc.createElement("ul");
	for(var i = 0; i< contracts.length; i++){
		var c = contracts[i];
		var li = doc.createElement("li");
		var label = doc.createElement("label");
		var input = doc.createElement("input");
		var span = doc.createElement("span");
		
		li.title= c.name;
		input.type="checkbox";
		input.className="checkbox";
		input.value = c.id;
		input.setAttribute("data", c.name);
		span.innerHTML = c.name;
		
		input.onclick = function(){
			var id= $(this).val();
			if($(this).is(':checked')){
				contractName = $(this).attr("data");
				loadBillPlanList(id);//付费计划
				loadBillList(id);//实际付费
			}else{
				contractName = "";
				if(options.series != null){
					for(var i= 0;i < options.series.length;i++){
						if(("planPay"+id) == options.series[i].id){
							options.series.splice(i,1);
							$('#container').highcharts(options);
						}else if(("pay"+id) == options.series[i].id){
							options.series.splice(i,1);
						}
					}
					$('#container').highcharts(options);
				}
			}
		}
		
		$(label).append($(input));
		$(label).append($(span));
		$(li).append($(label));
		$(ul).append($(li));
	}
	$(html).append($(ul));
	loadChart();
}

function loadChart(){
	options.yAxis = getYAxis();
	options.series = [];
	options.title = { text : projectName + "进度，总进度数："+schedules};
	showXContracts();//加载x轴节点
	loadPlanList(projectId);//显示计划进度
	loadFactList(projectId);//显示计划进度
	
	var url = (location.href).replace("index.jsp","");
	$(".src-center").load(url+"chart.jsp");
}

function removeChart(){
	$(".contracts").html("");
	$(".src-center").html("");
}

//初始化窗体高度
function initHeight(){
	$(".src-center").css("padding","5px");
	var pageH = getPageHeight();
	var bodyH = getBodyMargin($("body"));
	var srcLH = getBodyMargin($(".src-left")); 
	var srcCH = getBodyMargin($(".src-center")); 
	
	var pannelH = pageH-40;
	var srcLHeight = pannelH-srcLH;
	var srcCHeight = pannelH-srcLH;
	
	
	$(".easyui-panel").height(pannelH);
	$(".src-left").height(srcLHeight);
	$(".src-center").height(srcCHeight-10);
	var panelW = $(".easyui-panel").width();
	var leftW = $(".src-left").width();
	var leftM = $(".src-left").css("margin-left");
	leftM = getNum(leftM);
	$(".src-center").width(panelW-leftW-leftM-18);
	
	var leftH = $(".src-left").height();
	var sH = $(".select").height()+4;
	$(".contract").height(leftH-sH-30);
}

function getPageHeight(){
	return $(this).height();
}

function getColor(){
	var colors = ['0','1','2','3','4','5','6','7',
	              '8','9','a','b','c','d','e','f'];
	var color="";
	for(var i = 1; i<= 6 ;i++){
		var id = Math.ceil(Math.random()*(colors.length-1));
		color += ""+colors[id];
	}
	return color;
}

function getBodyMargin(tag){
	var marH = $(tag).css("margin-top");
	var marB = $(tag).css("margin-bottom");
	var padH = $(tag).css("padding-top");
	var padB = $(tag).css("padding-bottom");
	var bodH = $(tag).css("border-top-width");
	var bodB = $(tag).css("border-bottom-width");
	if(marH == 'auto') marH = 0;
	if(marB == 'auto') marB = 0;
	if(bodH == 'medium') bodH = 0;
	if(bodB == 'medium') bodB = 0;
	
	marH = getNum(marH);
	marB = getNum(marB);
	padH = getNum(padH);
	padB = getNum(padB);
	bodH = getNum(bodH);
	bodB = getNum(bodB);
	return parseInt(marH)+parseInt(marB)+
		parseInt(padH)+parseInt(padB)+
		parseInt(bodH)+parseInt(bodB);
}


function getNum(num){
	num = num+"";
	num = num.toLowerCase();
	num = num.replace("px","");
	return num;
}

// y轴方向信息
function getYAxis(){
	 var max = 15;
	 if(schedules > max) max = schedules;
	 var p = max % 5;
	 p = max - p;
	 var tickPositions = [];
	 for(var i = 0;i< max ;i = i+5){
		 tickPositions.push(i);
	 }
	 if(tickPositions.length > 0){
		 tickPositions.push(tickPositions[tickPositions.length-1]+5);
	 }
	 
	 var yAxis = [{
        labels: {
            enabled: false
        },
        title: {
            text: ''
        },
        gridLineColor: 'rgba(0, 0, 0, 0.07)'
    }, {
        allowDecimals: false,
        max: max,
        tickPositions: tickPositions,
        showFirstLabel: true, 
        min : 0,
        labels: {
            style: {
                color: Highcharts.getOptions().colors[2]
            }
        },
        title: {
            text: '项目进度数',
            style: {
                color: Highcharts.getOptions().colors[3],
            }
        },
        opposite: true,
        gridLineWidth: 0
    }];
	 return yAxis;
}

function showBillPlan(plans,id){
	if(!!plans && plans.length > 0){
		var data = [];
		var sum = 0;//金额要累加
		for(var i = 0;i< plans.length;i++){
			var plan = plans[i];
			var dt = plan.planPayDate;
			dt = getDateTime(dt);
			sum += plan.planFee;
			var amount = Math.round(sum/projectAmount*100);
			data.push({ x : dt, y : amount , name: '计划付费进度('+contractName+')', image: null});
		}
		if(data.length > 0){
			options.series.push({
	            name: '计划付费进度('+contractName+')',
	            id: 'planPay'+id,
	            type: 'spline',
	            marker:{ enabled:true },
	            yAxis: 1,
	            color:"#"+getColor(),
	            dashStyle: 'dash',
	            tooltip: {
	                headerFormat: '<span style="font-size: 11px;color:#666">{point.x:%B %e, %Y}</span><br>',
	                pointFormat: '{point.name}<br><b>{point.y}</b>',
	                valuePrefix: '计划付费',
	                valueSuffix: ' % of '+projectAmount
	            },
	            data:data
			});
			$('#container').highcharts(options);
		}
	}
}

function showBill(bills,id){
	if(!!bills && bills.length > 0){
		var data = [];
		var sum = 0;//金额要累加
		for(var i = 0;i< bills.length;i++){
			var bill = bills[i];
			var dt = bill.settlementDate;
			dt = getDateTime(dt);
			sum += bill.settlementFee;
			var amount = Math.round(sum/projectAmount*100);
			data.push({ x : dt, y : amount , name: '实际付费进度('+contractName+')', image: null});
		}
		if(data.length > 0){
			options.series.push({
				name: '实际付费进度('+contractName+')',
				id: 'Pay'+id,
				yAxis: 1,
				color:"#"+getColor(),
				type: 'spline',
				marker:{ enabled:true },
				tooltip: {
	                headerFormat: '<span style="font-size: 11px;color:#666">{point.x:%B %e, %Y}</span><br>',
	                pointFormat: '{point.name}<br><b>{point.y}</b>',
	                valuePrefix: '完成付费',
	                valueSuffix: ' % of '+projectAmount
	            },
				data:data
			});
			$('#container').highcharts(options);
		}
	}
}

function showFacts(facts){
	if(!!facts && facts.length > 0){
		var data = [];
		for(var i = 0;i< facts.length;i++){
			var fact = facts[i];
			var dt = fact.time;
			dt = getDateTime(dt);
			data.push({ x : dt, y : parseInt(fact.schedule) , name: ' ', image: null});
		}
		if(data.length > 0){
			options.series.push({
				yAxis: 1,
	            name: '项目实际进度',
	            id: 'projectFact',
	            type: 'spline',
	            color:"#22aadd",
	            step: 'right',
	            marker:{ enabled:true },
	            tooltip: {
	                headerFormat: '<span style="font-size: 11px;color:#666">{point.x:%B %e, %Y}</span><br>',
	                pointFormat: ' {point.name}<br><b>{point.y}</b>',
	                valuePrefix: '实际完成 '
	            },
	            data:data
	        });
			
			$('#container').highcharts(options);
		}
		
	}
}

function showPlans(plans){
	if(!!plans && plans.length > 0){
		var data = [];
		for(var i = 0;i< plans.length;i++){
			var plan = plans[i];
			var dt = plan.time;
			dt = getDateTime(dt);
			data.push({ x : dt, y : parseInt(plan.schedule) , name: ' ', image: null});
		}
		if(data.length > 0){
			options.series.push({
				yAxis: 1,
	            name: '项目计划进度',
	            id: 'projectPlan',
	            type: 'spline',
	            step: 'right',
	            color: '#45a843',
	            dashStyle: 'dash',
	            marker:{ enabled:true },
	            tooltip: {
	                headerFormat: '<span style="font-size: 11px;color:#666">{point.x:%B %e, %Y}</span><br>',
	                pointFormat: ' {point.name}<br><b>{point.y}</b>',
	                valuePrefix: '计划完成 '
	            },
	            data:data
			});
			$('#container').highcharts(options);
		}
	}
}

function getDateTime(datetime){
	var arr = datetime.split("T");
	if(arr.length > 0){
		var dt = arr[0];
		var d = dt.split("-");
		if(!d) d = dt.split("/");
		return Date.UTC(d[0], d[1], d[2]);
	}
}


//水平方向节点,每个节点表示签订了一个合同
//Highcharts.seriesTypes.flags
function showXContracts(){
	if(!!contractsData && contractsData.length > 0){
		for(var i = 0;i< contractsData.length;i++){
			var contract = contractsData[i];
			var dt =contract.signDate;
			dt = getDateTime(dt);
			var data = [{ x: dt,id:contract.id, text: contract.name, title:contract.name , shape: 'squarepin' }];
			options.series.push({
				type: 'flags',
				name: 'Cloud',
				color: '#333333',
				shape: 'spline',
				id:contract.id,
				marker:{ enabled:true },
				data: data,
				events :{
					click:function(event){
						viewById(event.point.id);
					}
				},
				showInLegend: false
			});
		}
	}
}

function onChartLoad() {}


var doc=document;
var projectName;
var projectId;
var schedules;
var contractsData;
var projectDate;
var projectAmount;
var contractName;

var options = {
		chart: {
		    plotBackgroundColor: null,
		    plotBorderWidth: null,
		    plotShadow: false,
		    renderTo:'container'
		},
		title: {
		    text: 'Browser market shares at a specific website, 2010'
		},
		tooltip: {
		  pointFormat: '{series.name}: <b>{point.y}</b>'
		},
		plotOptions : {
			series : {
				marker : {
					enabled : false,
					symbol : 'circle',
					radius : 2
				},
				fillOpacity : 0.5
			},
			flags : {
				tooltip : {
					xDateFormat : '%B %e, %Y'
				}
			}
		},
		 yAxis: [{
	            labels: {
	                enabled: false
	            },
	            title: {
	                text: ''
	            },
	            gridLineColor: 'rgba(0, 0, 0, 0.07)'
	        }, {
	            allowDecimals: true,
	            max: 44,
	            min: 0,
	            labels: {
	                style: {
	                    color: Highcharts.getOptions().colors[2]
	                }
	            },
	            title: {
	                text: 'Employees',
	                style: {
	                    color: Highcharts.getOptions().colors[2]
	                }
	            },
	            opposite: true,
	            gridLineWidth: 0
	        }],
		
		labels:{
			items:[{
				html:'<a href="http://www.52wulian.org" target="_blank">HighCharts</a>',
				style: {
					left:'532px',
					top:'160px',				
				}
			}],
			style:{
				color:'red',
				fontSize:45,
				fontWeight:'bold',
				zIndex:1000
			}
		},
		series: [{
			yAxis: 1,
		    name: 'Browser share',
		    step: 'right',
		    data: [
		        ['Firefox',   45.0],
		        ['IE',       26.8],
		        {
		            name: 'Chrome',
		            y: 12.8,
		            sliced: true,
		            selected: true
		        },
		        ['Safari',    8.5],
		        ['Opera',     6.2],
		        ['Others',   0.7]
		    ]
		}]
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
				//loadBillList(id);//实际付费
			}else{
				contractName = "";
				if(options.series != null){
					for(var i= 0;i < options.series.length;i++){
						if(("planPay"+id) == options.series[i].id){
							options.series.splice(i,1);
							$('#container').highcharts(options);
						}
					}
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
	/*options.yAxis = getYAxis();
	options.series = [];
	options.title = { text : projectName + "进度"};
	showXContracts();//加载x轴节点
	loadPlanList(projectId);//显示计划进度
	loadFactList(projectId);//显示计划进度
	 */	
	var url = (location.href).replace("index.jsp","");
	$(".src-center").load(url+"chart.jsp");
}

function removeChart(){
	$(".contracts").html("");
	$(".src-center").html("");
}

//初始化窗体高度
function initHeight(){
	var pageH = getPageHeight();
	$(".easyui-panel").height(pageH-40);
	var bodyH = getBodyMargin($("body"));
	var panelH = getBodyMargin($(".panel-body")); 
	var panelHeight = pageH-bodyH-panelH;
	var srcLH = getBodyMargin($(".src-left")); 
	var srcLHeight = panelHeight-srcLH;
	var srcCH = getBodyMargin($(".src-center")); 
	var srcCHeight = panelHeight-srcLH;
	
	$(".panel-body").height(panelHeight);
	$(".easyui-layout").height(panelHeight);
	$(".src-left").height(srcLHeight);
	$(".src-center").height(srcCHeight);
}

function getPageHeight(){
	return $(this).height();
}

function getBodyMargin(tag){
	var marH = $(tag).css("margin-top");
	var marB = $(tag).css("margin-bottom");
	var padH = $(tag).css("padding-top");
	var padB = $(tag).css("padding-bottom");
	var bodH = $(tag).css("border-top-width");
	var bodB = $(tag).css("border-bottom-width");
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
	 var yAxis = [{
        max: 100,
        labels: {
            enabled: false
        },
        title: {
            text: ''
        },
        gridLineColor: 'rgba(0, 0, 0, 0.07)'
    }, {
        allowDecimals: false,
        max: max,//项目进度数
        labels: {
            style: {
                color: Highcharts.getOptions().colors[2]
            }
        },
        title: {
            text: '项目进度数:'+schedules,
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
			var dt = new Date(plan.planPayDate);
			dt = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
			sum += plan.planFee;
			var amount = Math.round(sum/projectAmount*100);
			data.push({ x : dt, y : amount , name: contractName+'计划付费进度', image: null});
		}
		if(data.length > 0){
			/*var series = options.series;
			options = getOptions();*/
			options.series.push({
	            name: contractName+'计划付费进度',
	            id: 'planPay'+id,
	            type: 'spline',
	            tooltip: {
	                headerFormat: '<span style="font-size: 11px;color:#666">{point.x:%B %e, %Y}</span><br>',
	                pointFormat: '{point.name}<br><b>{point.y}</b>',
	                valuePrefix: '计划付费',
	                valueSuffix: ' % of '+projectAmount
	            },
	            data:data
			});
			/*options.series = series;
			options.yAxis = getYAxis();
			options.title = { text : projectName + "进度"};*/
			$('#container').highcharts(options);
		}
	}
}

function showBill(plans){
	if(!!plans && plans.length > 0){
		var data = [];
		var sum = 0;//金额要累加
		for(var i = 0;i< plans.length;i++){
			var plan = plans[i];
			var dt = new Date(plan.time);
			dt = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
			data.push({ x : dt, y : parseInt(plan.schedule) , name: ' ', image: null});
		}
		if(data.length > 0){
			options.series.push({
				name: '项目计划进度',
				id: 'projectPlan',
				type: 'spline',
				tooltip: {
					headerFormat: '<span style="font-size: 11px;color:#666">{point.x:%B %e, %Y}</span><br>',
					pointFormat: '{point.name}<br><b>{point.y}</b>',
					valuePrefix: '计划完成'
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
			var dt = new Date(fact.time);
			dt = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
			data.push({ x : dt, y : parseInt(fact.schedule) , name: ' ', image: null});
		}
		if(data.length > 0){
			options.series.push({
				yAxis: 1,
	            name: '项目实际进度',
	            id: 'projectFact',
	            type: 'spline',
	            step: 'right',
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
		/*var d = new Date(projectDate);
		d = Date.UTC(d.getFullYear(), d.getMonth(), d.getDate());*/
		var data = [];
		for(var i = 0;i< plans.length;i++){
			var plan = plans[i];
			var dt = new Date(plan.time);
			dt = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
			data.push({ x : dt, y : parseInt(plan.schedule) , name: ' ', image: null});
		}
		if(data.length > 0){
			options.series.push({
				yAxis: 1,
	            name: '项目计划进度',
	            id: 'projectPlan',
	            type: 'spline',
	            step: 'right',
	            dashStyle: 'dash',
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


//水平方向节点,每个节点表示签订了一个合同
//Highcharts.seriesTypes.flags
function showXContracts(){
	if(!!contractsData && contractsData.length > 0){
		for(var i = 0;i< contractsData.length;i++){
			var contract = contractsData[i];
			var dt = new Date(contract.signDate);
			dt = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
			var data = [{ x: dt,id:contract.id, text: contract.name, title:contract.name , shape: 'squarepin' }];
			options.series.push({
				type: 'flags',
				name: 'Cloud',
				color: '#333333',
				shape: 'spline',
				id:contract.id,
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


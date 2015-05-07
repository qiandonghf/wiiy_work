function graphTrace(options) {
	var opts = options;
    // 获取图片资源
    var imageUrl =  "/activiti/workflow!processInstance.action?pid=" + opts.pid + "&type=image";
    $.getJSON('/activiti/workflow!traceProcess.action?pid=' + opts.pid, function(res) {
    	
        var positionHtml = "";
        var infos=res.activityInfos;
        
        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: $.fn.qtip.zindex - 1
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: $.fn.qtip.zindex - 2
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });

        if ($('#workflowTraceDialog').length == 0) {
            $('<div/>', {
                id: 'workflowTraceDialog',
                html: "<div><img src='" + imageUrl + "' style='top:0px;' />" +
                "<div id='processImageBorder'>" +
                positionHtml +
                "</div>" +
                "</div>"
            }).appendTo('body');
        } else {
            $('#workflowTraceDialog img').attr('src', imageUrl);
            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
        }
        
        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });
        
        // 此处用于显示每个节点的信息，如果不需要可以删除
        $('.activity-attr').qtip({
            content: function() {
                var vars = $(this).data('vars');
                var tipContent = "<table class='need-border'>";
                $.each(vars, function(varKey, varValue) {
                    if (varValue) {
                        tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                    }
                });
                tipContent += "</table>";
                return tipContent;
            },
            position: {
                at: 'bottom left',
                adjust: {
                    x: 3
                }
            }
        });
    });
}

function getNode(options) {
	var opts = options;
    // 获取图片资源
    var imageUrl =  "/activiti/workflow!processInstance.action?pid=" + opts.pid + "&type=image";
    $.getJSON('/activiti/workflow!traceProcess.action?pid=' + opts.pid, function(res) {
    	
        var positionHtml = "";
        var infos=res.activityInfos;
        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: $.fn.qtip.zindex - 1
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x ),
                top: (v.y - 1),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: $.fn.qtip.zindex - 2
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });
        $('<div/>', {
            id: 'workflowTraceDialog',
            html: "<div><img src='" + imageUrl + "' style='margin:0px;padding:0px;padding-right:10px;' />" +
            "<div id='processImageBorder'>" +
            positionHtml +
            "</div>" +
            "</div>"
        }).appendTo('#workflowTraceDialog');
        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });
        
        
        // 此处用于显示每个节点的信息，如果不需要可以删除
        $('.activity-attr').qtip({
            content: function() {
                var vars = $(this).data('vars');
                var tipContent = "<table class='need-border'>";
                $.each(vars, function(varKey, varValue) {
                    if (varValue) {
                        tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                    }
                });
                tipContent += "</table>";
                return tipContent;
            },
            position: {
                at: 'bottom left',
                adjust: {
                    x: 3
                }
            }
        });
    });
}


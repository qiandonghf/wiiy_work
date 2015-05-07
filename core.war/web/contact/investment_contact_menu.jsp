<%@ page language="java" import="java.util.*,com.wiiy.commons.action.BaseAction" pageEncoding="UTF-8"%>
<%@page import="com.wiiy.core.preferences.enums.ContactTypeEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = BaseAction.rootLocation+path+"/";
String url = BaseAction.rootLocation+"/";
%>
<script type="text/javascript">
var imageMenuData1 = [
               	  [{
               			text: "打开",
               			classname: "smarty_menu_view",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               				openRight(type,id);
               			}
               		},{
               			text: "编辑",
               			classname: "smarty_menu_ico0",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               			  /* fbStart('编辑联系单','parkmanager.pb/web/client/applicationadd.html',700,538); */
               			  edit(type,id);
               			}
               		},{
               			text: "打印",
               			classname: "smarty_print",
               			func: function() {
               				var id = $(this).find("input").next().val();
               				var url="<%=url%>parkmanager.pb/investmentContact!print.action?id="+id;
               			 	location.href = url;
               			}
               		}
               		
               		],[
               		{
               			text: "受理",
               			classname: "smarty_menu_acceptance",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               				var url="<%=url%>parkmanager.pb/investmentContact!setStatus.action?id="+id+"&type="+type+"&status=ACCEPT";
               				$.post(url,function(data){
               					var msg = data.result.msg;
               					showTip(msg,2000);
               					if(data.result.success){
               						setTimeout(function(){		        			
    				        			location.reload();
    				        		},2000);
               					}
               				});
               			}
               		},
               		{
               			text: "关闭",
               			classname: "smarty_menu_close",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               				var url="<%=url%>parkmanager.pb/investmentContact!setStatus.action?id="+id+"&type="+type+"&status=CLOSE";
               				$.post(url,function(data){
               					var msg = data.result.msg;
               					showTip(msg);
               					if(data.result.success){
               						setTimeout(function(){		        			
    				        			location.reload();
    				        		},2000);
               					}
               				});
               			}
               		},
               		{
               			text: "挂起",
               			classname: "smarty_suspend",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               				var url="<%=url%>parkmanager.pb/investmentContact!setStatus.action?id="+id+"&type="+type+"&status=SUSPEND";
               				$.post(url,function(data){
               					var msg = data.result.msg;
               					showTip(msg);
               					if(data.result.success){
               						setTimeout(function(){		        			
    				        			location.reload();
    				        		},2000);
               					}
               				});
               			}
               		},
               		{
               			text: "发送",
               			classname: "smarty_send",
               			data: [[{
    						text: "招商人员意见",
    						classname: "smarty_audit",
    						func: function() {
    							$("#approvalType").val("attractOpinion");
    							$("#afterType").val("INVESTMENTCONTACT");
    							$("#afterId").val($(this).find("input").next().val());
    							fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
    						}
    					},{
    						text: "经办人员意见",
    						classname: "smarty_audit",
    						func: function() {
    							$("#approvalType").val("handleOpinion");
    							$("#afterType").val("INVESTMENTCONTACT");
    							$("#afterId").val($(this).find("input").next().val());
    							fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
    						}
    					},{
    						text: "投资促进部",
    						classname: "smarty_audit",
    						func: function() {
    							$("#approvalType").val("departmentOpinion");
    							$("#afterType").val("INVESTMENTCONTACT");
    							$("#afterId").val($(this).find("input").next().val());
    							fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
    						}
    					},{
    						text: "主任会议室",
    						classname: "smarty_audit",
    						func: function() {
    							$("#approvalType").val("headOpinion");
    							$("#afterType").val("INVESTMENTCONTACT");
    							$("#afterId").val($(this).find("input").next().val());
    							fbStart('选择用户','<%=BaseAction.rootLocation %>/core/user!select.action',520,400);
    						}
    					}]]
               		}
               		]
               	  <%-- ,[
               		{
               			text: "已解决",
               			classname: "smarty_menu_finish",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               				var url="<%=url%>parkmanager.pb/investmentContact!setSolved.action?id="+id+"&type="+type+"&resolveStatus=SOLVED";
               				$.post(url,function(data){
               					var msg = data.result.msg;
               					showTip(msg);
               					if(data.result.success){
               						setTimeout(function(){		        			
    				        			location.reload();
    				        		},2000);
               					}
               				});
               			}
               		},
               		{
               			text: "未解决",
               			classname: "smarty_menu_failed",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               				var url="<%=url%>parkmanager.pb/investmentContact!setSolved.action?id="+id+"&type="+type+"&resolveStatus=UNSOLVED";
               				$.post(url,function(data){
               					var msg = data.result.msg;
               					showTip(msg);
               					if(data.result.success){
               						setTimeout(function(){		        			
    				        			location.reload();
    				        		},2000);
               					}
               				});
               			}
               		},
               		{
               			text: "部分解决",
               			classname: "smarty_menu_part",
               			func: function() {
               				var type = $(this).find("input").val();
               				var id = $(this).find("input").next().val();
               				var url="<%=url%>parkmanager.pb/investmentContact!setSolved.action?id="+id+"&type="+type+"&resolveStatus=PARTSOLVED";
               				$.post(url,function(data){
               					var msg = data.result.msg;
               					showTip(msg);
               					if(data.result.success){
               						setTimeout(function(){		        			
    				        			location.reload();
    				        		},2000);
               					}
               				});
               			}
               		}
               		], --%>
               		,[
               	  {
               			text: "删除",
               			classname: "smarty_menu_ico2",
               			func: function() {
               				if(confirm("你确定要删除")){
               					var type = $(this).find("input").val();
               					var id = $(this).find("input").next().val();
               						$.post("<%=url%>parkmanager.pb/investmentContact!delete.action?id="+id,function(data){
               							showTip(data.result.msg,2000);
               							if(data.result.success){
               								setTimeout("location.reload()", 2000);
               							}
               						});
               				}
               			}
               		}
               		
               		]
               	];
</script>
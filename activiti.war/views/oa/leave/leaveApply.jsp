<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>请假申请</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="/activiti/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />

    <script src="/activiti/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="/activiti/js/common/plugins/jui/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
    <script src="/activiti/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="/activiti/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function() {
    	$('#startTime,#endTime').datetimepicker({
            stepMinute: 5
        });
    });
    </script>
</head>

<body>
	<div class="container showgrid">
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">${message}</div>
		<!-- 自动隐藏提示信息 -->
		<script type="text/javascript">
		setTimeout(function() {
			$('#message').hide('slow');
		}, 5000);
		</script>
	</c:if>
	<c:if test="${not empty error}">
		<div id="error" class="alert alert-error">${error}</div>
		<!-- 自动隐藏提示信息 -->
		<script type="text/javascript">
		setTimeout(function() {
			$('#error').hide('slow');
		}, 5000);
		</script>
	</c:if>
	<form id="inputForm" action="/activiti/leave!startWorkflow.action" method="post" class="form-horizontal">
			<div>请假申请</div>
			<table border="1">
			<tr>
				<td>请假类型：</td>
				<td>
					<select id="leaveType" name="leave.leaveType">
						<option>公休</option>
						<option>病假</option>
						<option>调休</option>
						<option>事假</option>
						<option>婚假</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>开始时间：</td>
				<td><input type="text" id="startTime" name="leave.startTime" /></td>
			</tr>
			<tr>
				<td>结束时间：</td>
				<td><input type="text" id="endTime" name="leave.endTime" /></td>
			</tr>
			<tr>
				<td>请假原因：</td>
				<td>
					<textarea name="leave.reason"></textarea>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<button type="submit">申请</button>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>

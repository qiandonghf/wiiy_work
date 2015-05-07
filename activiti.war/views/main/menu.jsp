<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul id="css3menu">
	<li class="topfirst"><a rel="main/welcome">首页</a></li>
	<li>
		<a rel="#">请假（普通表单）</a>
		<ul>
			<li><a rel="/activiti/leave!apply.action">请假申请(普通)</a></li>
			<li><a rel="/activiti/leave!taskList.action">请假办理(普通)</a></li>
			<li><a rel="/activiti/leave!runningList.action">运行中流程(普通)</a></li>
			<li><a rel="/activiti/leave!finishedList.action">已结束流程(普通)</a></li>
		</ul>
	</li>
	<li>
		<a rel="#">动态表单</a>
		<ul>
			<li><a rel="views/form/dynamic/process-list.jsp">流程列表(动态)</a></li>
			<li><a rel="views/form/dynamic/task/list.jsp">任务列表(动态)</a></li>
			<li><a rel="views/form/dynamic/process-instance/running/list.jsp">运行中流程表(动态)</a></li>
			<li><a rel="views/form/dynamic/process-instance/finished/list.jsp">已结束流程(动态)</a></li>
		</ul>
	</li>
	<li>
		<a rel="#">外置表单</a>
		<ul>
			<li><a rel="views/form/formkey/process-list.jsp">流程列表(外置)</a></li>
			<li><a rel="views/form/formkey/task/list.jsp">任务列表(外置)</a></li>
			<li><a rel="views/form/formkey/process-instance/running/list.jsp">运行中流程表(外置)</a></li>
			<li><a rel="views/form/formkey/process-instance/finished/list.jsp">已结束流程(外置)</a></li>
		</ul>
	</li>
    <li>
        <a rel='#' title="不区分表单类型，可以显示设计器设计后部署的流程">综合流程</a>
        <ul>
            <li><a rel="views/form/dynamic/process-list.action?processType=all">流程列表</a></li>
            <li><a rel="views/form/dynamic/task/list.action?processType=all">任务列表(综合)</a></li>
            <li><a rel="views/form/dynamic/process-instance/running/list.action?processType=all">运行中流程表(综合)</a></li>
            <li><a rel="views/form/dynamic/process-instance/finished/list.action?processType=all">已结束流程(综合)</a></li>
        </ul>
    </li>
	<li>
		<a rel='#'>流程管理</a>
		<ul>
			<li><a rel='/activiti/workflow!processList.action'>流程定义及部署管理</a></li>
			<li><a rel='/activiti/processinstance!running.action'>运行中流程</a></li>
			<li><a rel='views/workflow/model/list'>模型工作区</a></li>
		</ul>
	</li>
</ul>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会议流程审批</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/oameetingModel/">会议流程列表</a></li>
		<shiro:hasPermission name="oa:oameetingModel:edit"><li><a href="${ctx}/oa/oameetingModel/form">会议流程审批</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oameetingModel" action="${ctx}/oa/oameetingModel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>流程实例号：</label>
				<form:input path="procInsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>会议主题：</label>
				<form:input path="meetingDesc" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>会议成员：</label>
				<sys:treeselect id="meetingAttendee" name="meetingAttendee" value="${oameetingModel.meetingAttendee}" labelName="meetingAttendeenames" labelValue="${oameetingModel.meetingAttendeenames}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>会议开始时间：</label>
				<input name="meetingStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oameetingModel.meetingStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>会议结束时间：</label>
				<input name="meetingEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oameetingModel.meetingEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>会议室：</label>
				<form:select path="meetingRoom" class="input-medium">
					 <form:option value="" label=""/> 
					<form:options items="${fns:getDictList('oa_meeting_room')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>会议类型：</label>
				<form:select path="meetingType" class="input-medium">
			<form:option value="" label=""/> 
					<form:options items="${fns:getDictList('oa_meeting_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>会议管理员：</label>
				<sys:treeselect id="meetingManager" name="meetingManager" value="${oameetingModel.meetingManager}" labelName="" labelValue="${fns:getUserById(oameetingModel.meetingManager).name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>创建者：</label>
				<sys:treeselect id="createBy" name="createBy.id" value="${oameetingModel.createBy}" labelName="" labelValue="${fns: getUserById(oameetingModel.createBy).name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oameetingModel.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>修改者：</label>
				<sys:treeselect id="updateBy" name="updateBy.id" value="${oameetingModel.updateBy}" labelName="" labelValue="${fns: getUserById(oameetingModel.updateBy).name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>修改时间：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oameetingModel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>备注信息：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>删除标记：</label>
				<form:radiobuttons path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<!-- 	<th>编号</th>
				<th>流程实例号</th> -->
				<th>会议主题</th>
				<th>会议成员</th>
				<th>会议开始时间</th>
				<th>会议结束时间</th>
				<th>会议室</th>
				<th>会议类型</th>
				<th>会议管理员</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>修改者</th>
				<th>修改时间</th>
				<th>备注信息</th>
				<th>删除标记</th>
				<shiro:hasPermission name="oa:oameetingModel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oameetingModel">
			<tr>
				<%-- <td><a href="${ctx}/oa/oameetingModel/form?id=${oameetingModel.id}">
					${oameetingModel.id}
				</a></td>
				<td>
					${oameetingModel.procInsId}
				</td> --%>
				<td>
					${oameetingModel.meetingDesc}
				</td>
				<td>
					${oameetingModel.meetingAttendeenames}
				</td>
				<td>
					<fmt:formatDate value="${oameetingModel.meetingStartDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${oameetingModel.meetingEndDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(oameetingModel.meetingRoom, 'oa_meeting_room', '')}
				</td>
				<td>
					${fns:getDictLabel(oameetingModel.meetingType, 'oa_meeting_type', '')}
				</td>
				<td>
					${fns: getUserById(oameetingModel.meetingManager).name}
				</td>
				<td>
					${fns: getUserById(oameetingModel.createBy).name}
				</td>
				<td>
					<fmt:formatDate value="${oameetingModel.createDate}" pattern="yyyy-MM-dd"/>
					 <!-- HH:mm:ss -->
				</td>
				<td>
					${fns: getUserById(oameetingModel.updateBy).name}
				</td>
				<td>
					<fmt:formatDate value="${oameetingModel.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${oameetingModel.remarks}
				</td>
				<td>
					${fns:getDictLabel(oameetingModel.delFlag, 'del_flag', '')}
				</td>
				<shiro:hasPermission name="oa:oameetingModel:edit"><td>
    				<a href="${ctx}/oa/oameetingModel/form?id=${oameetingModel.id}">修改</a>
					<a href="${ctx}/oa/oameetingModel/delete?id=${oameetingModel.id}" onclick="return confirmx('确认要删除该会议流程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
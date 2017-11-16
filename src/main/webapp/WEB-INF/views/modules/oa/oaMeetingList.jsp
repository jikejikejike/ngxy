<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会议管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaMeeting/">会议列表</a></li>
		<shiro:hasPermission name="oa:oaMeeting:edit"><li><a href="${ctx}/oa/oaMeeting/form">会议添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaMeeting" action="${ctx}/oa/oaMeeting/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会议主题：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>会议内容：</label>
				<form:input path="meetingDesc" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>参加人员：</label>
				<sys:treeselect id="meetingAttendee" name="meetingAttendee" value="${oaMeeting.meetingAttendee}" labelName="meetingAttendeenames" labelValue="${oaMeeting.meetingAttendeenames}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<%-- <li><label>预计开始时间：</label>
				<input name="meetingStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaMeeting.meetingStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>预计结束时间：</label>
				<input name="meetingEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaMeeting.meetingEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> --%>
			<li><label>会议室：</label>
				<form:select path="meetingRoom" class="input-medium">
					<!-- <form:option value="" label=""/> -->
					<form:options items="${fns:getDictList('oa_meeting_room')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="meetingStatus" class="input-medium">
				<!-- 	<form:option value="" label=""/> -->
					<form:options items="${fns:getDictList('oa_meeting_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>会议类型：</label>
				<form:select path="meetingType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_meeting_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>会议管理员：</label>
				<sys:treeselect id="meetingManager" name="meetingManager" value="${oaMeeting.meetingManager}" labelName="" labelValue="${oaMeeting.meetingManager}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>创建人：</label>
				<sys:treeselect id="createBy" name="createBy.id" value="${oaMeeting.createBy}" labelName="" labelValue="${oaMeeting.createBy}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li> --%>
			<%-- <li><label>创建日期：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaMeeting.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>修改人：</label>
				<sys:treeselect id="updateBy" name="updateBy.id" value="${oaMeeting.updateBy}" labelName="" labelValue="${oaMeeting.updateBy}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>修改日期：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaMeeting.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> --%>
			<!-- <li><label>备注：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会议主题</th>
				<th>会议内容</th>
				<th>参加人员</th>
				<!-- <th>预计开始时间</th>
				<th>预计结束时间</th> -->
				<th>会议室</th>
				<th>状态</th>
				<th>会议类型</th>
				<th>会议管理员</th>
				<th>创建人</th>
			<!-- 	<th>创建日期</th> -->
				<th>修改人</th>
			<!-- 	<th>修改日期</th> -->
				<th>备注</th>
				<shiro:hasPermission name="oa:oaMeeting:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaMeeting">
			<tr>
				<td><a href="${ctx}/oa/oaMeeting/form?id=${oaMeeting.id}">
					${oaMeeting.name}
				</a></td>
				<td>
					${oaMeeting.meetingDesc}
				</td>
				<td>
					${oaMeeting.meetingAttendeenames}
				</td>
				<%-- <td>
					<fmt:formatDate value="${oaMeeting.meetingStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${oaMeeting.meetingEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> --%>
				<td>
					${fns:getDictLabel(oaMeeting.meetingRoom, 'oa_meeting_room', '')}
				</td>
				<td>
					${fns:getDictLabel(oaMeeting.meetingStatus, 'oa_meeting_status', '')}
				</td>
				<td>
					${fns:getDictLabel(oaMeeting.meetingType, 'oa_meeting_type', '')}
				</td>
				<td>
					${fns: getUserById(oaMeeting.meetingManager).name}
				</td>
				<td>
					${fns: getUserById(oaMeeting.createBy).name}
				</td>
				<%-- <td>
					<fmt:formatDate value="${oaMeeting.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> --%>
				<td>
					${fns: getUserById(oaMeeting.updateBy).name}
				</td>
				<%-- <td>
					<fmt:formatDate value="${oaMeeting.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> --%>
				<td>
					${oaMeeting.remarks}
				</td>
				<shiro:hasPermission name="oa:oaMeeting:edit"><td>
					<c:if test="${oaMeeting.meetingStatus eq 0}">
    				<a href="${ctx}/oa/oaMeeting/form?id=${oaMeeting.id}">修改</a>
    			     </c:if>
    			     <c:if test="${oaMeeting.meetingStatus eq 0}">
					<a href="${ctx}/oa/oaMeeting/delete?id=${oaMeeting.id}" onclick="return confirmx('确认要删除该会议吗？', this.href)">删除</a>
					 </c:if>
					  <c:if test="${oaMeeting.meetingStatus ne 0}">
					<a href="${ctx}/oa/oaMeeting/form?id=${oaMeeting.id}" , this.href)">查看</a>
					 </c:if>
					 
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
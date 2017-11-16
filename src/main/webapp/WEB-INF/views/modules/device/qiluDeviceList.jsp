<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备管理管理</title>
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
		<li class="active"><a href="${ctx}/device/qiluDevice/">设备管理列表</a></li>
		<shiro:hasPermission name="device:qiluDevice:edit"><li><a href="${ctx}/device/qiluDevice/form">设备管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qiluDevice" action="${ctx}/device/qiluDevice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>device_name：</label>
				<form:input path="deviceName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>device_detail：</label>
				<form:input path="deviceDetail" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>create_by：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>create_date：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${qiluDevice.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>device_name</th>
				<th>device_detail</th>
				<th>create_by</th>
				<th>create_date</th>
				<shiro:hasPermission name="device:qiluDevice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qiluDevice">
			<tr>
				<td><a href="${ctx}/device/qiluDevice/form?id=${qiluDevice.id}">
					${qiluDevice.deviceName}
				</a></td>
				<td>
					${qiluDevice.deviceDetail}
				</td>
				<td>
					${qiluDevice.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${qiluDevice.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="device:qiluDevice:edit"><td>
    				<a href="${ctx}/device/qiluDevice/form?id=${qiluDevice.id}">修改</a>
					<a href="${ctx}/device/qiluDevice/delete?id=${qiluDevice.id}" onclick="return confirmx('确认要删除该设备管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
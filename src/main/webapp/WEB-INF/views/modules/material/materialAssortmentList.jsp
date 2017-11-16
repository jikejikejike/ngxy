<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物资分类管理</title>
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
		<li class="active"><a href="${ctx}/material/materialAssortment/">物资分类列表</a></li>
		<shiro:hasPermission name="material:materialAssortment:edit"><li><a href="${ctx}/material/materialAssortment/form">物资分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="materialAssortment" action="${ctx}/material/materialAssortment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>物资名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>排序：</label>
				<form:input path="sort" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建人：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${materialAssortment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>修改人：</label>
				<form:input path="updateBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>修改时间：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${materialAssortment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>备注：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>物资名称</th>
				<th>排序</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="material:materialAssortment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="materialAssortment">
			<tr>
				<td><a href="${ctx}/material/materialAssortment/form?id=${materialAssortment.id}">
					${materialAssortment.id}
				</a></td>
				<td>
					${materialAssortment.name}
				</td>
				<td>
					${materialAssortment.sort}
				</td>
				<td>
					${materialAssortment.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${materialAssortment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${materialAssortment.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${materialAssortment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${materialAssortment.remarks}
				</td>
				<shiro:hasPermission name="material:materialAssortment:edit"><td>
    				<a href="${ctx}/material/materialAssortment/form?id=${materialAssortment.id}">修改</a>
					<a href="${ctx}/material/materialAssortment/delete?id=${materialAssortment.id}" onclick="return confirmx('确认要删除该物资分类吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
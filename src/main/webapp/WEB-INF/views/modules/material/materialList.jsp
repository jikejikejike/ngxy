<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物资领用管理</title>
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
        
       //领用方法
        function foruse(){
        //定义一个变量获取checkbox的value。
        var a="";
        //元素选择器获取checkbox的选择的值，单选。
           $(":checkbox:checked").each(function() {       
            b=$(this).val();
            a=b;            
});
       //领用方法跳转页面。
          window.location.replace('${ctx}'+"/material/material/foruse?materialId="+a)
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/material/material/">物资领用列表</a></li>
		<shiro:hasPermission name="material:material:edit"><li><a href="${ctx}/material/material/form">物资入库</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="material" action="${ctx}/material/material/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- <li><label>物资编号：</label>
				<form:input path="materialId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li> -->
			<li><label>物资名称：</label>
				<form:input path="materialName" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>物资型号：</label>
				<form:input path="materialModel" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>物资单位：</label>
				<form:select path="materialUnit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('material_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>物资库存：</label>
				<form:input path="materialNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<!-- <li><label>material_assortment_id：</label>
			</li> -->
			<%-- <li><label>创建人：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${material.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>修改人：</label>
				<form:input path="updateBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>修改时间：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${material.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> --%>
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
			   <tr><td></td><td></td><td></td><td></td><td></td><td></td><td align='right'><input id="btnCancel" class="btn btn-primary" type="button" value="领用" onclick="foruse()"/></td></tr>
			<tr>
			<!-- 	<th>物资编号</th> -->
				<th>物资名称</th>
				<th>物资型号</th>
				<th>物资单位</th>
				<th>物资库存</th>
				<th>物资分类</th>
				<!-- <th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th> -->
				<th>备注</th>
				<shiro:hasPermission name="material:material:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="material">
			<tr>
				<%-- <td><a href="${ctx}/material/material/form?id=${material.id}">
					${material.materialId}
				</a></td> --%>
				<td>
					 ${material.materialName}
				</td>
				<td>
					${material.materialModel}
				</td>
				<td>
					${fns:getDictLabel(material.materialUnit, 'material_unit', '')}
				</td>
				<td>
					${material.materialNumber}
				</td>
				<td>
					${material.materialAssortment.id}
				</td>
				<%-- <td>
					${material.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${material.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${material.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${material.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> --%>
				<td>
					${material.remarks}
				</td>			
				<td>
				 <input type="checkbox" name="forusebox" value="${material.id}"> 	
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
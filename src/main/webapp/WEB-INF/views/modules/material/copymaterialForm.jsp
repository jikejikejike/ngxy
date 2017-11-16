<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物资管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		
		
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/material/material/">物资领用列表</a></li>
		<li class="active"><a href="${ctx}/material/material/form?id=${material.id}">物资入库<shiro:hasPermission name="material:material:edit">${not empty material.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="material:material:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="material" action="${ctx}/material/material/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">物资编号：</label>
			<div class="controls">
				<form:input path="materialId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物资名称：</label>
			<div class="controls">
			<form:input path="materialName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
		</div>
		</div>
		<div class="control-group">
			<label class="control-label">物资型号：</label>
			<div class="controls">
				<form:input path="materialModel" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物资单位：</label>
			<div class="controls">
				<form:select path="materialUnit" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('material_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入库数量：</label>
			<div class="controls">
				<form:input path="materialNumber" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">物资分类：</label>
			<div class="controls">
			<sys:treeselect id="materialAssortment" name="materialAssortment" value="${materialAssortment.id}" labelName="" labelValue="${materialAssortment.name}"
					title="物资分类" url="/material/materialAssortment/treeData" cssClass="required" allowClear="true"  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oameetingModel:edit">		
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"  onclick="$('#flag').val('yes')" />&nbsp;</shiro:hasPermission>
			<c:if test="${not empty oameetingModel.id}">
					<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
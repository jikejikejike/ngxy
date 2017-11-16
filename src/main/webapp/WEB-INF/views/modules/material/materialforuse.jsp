<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物资申请管理</title>
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
		<li><a href="${ctx}/material/materialProc/">物资申请列表</a></li>
		<li class="active"><a href="${ctx}/material/materialProc/form?id=${materialProc.id}">物资领用申请表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="materialProc" action="${ctx}/material/materialProc/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>	
		<sys:message content="${message}"/>		
		<!-- <div class="control-group">
			<label class="control-label">流程编号：</label>
			<div class="controls">
				<form:input path="procInsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div> -->
	<!-- 	</div> -->
            <div class="control-group">
			<label class="control-label">物资编号：</label>
			<div class="controls">
				<form:input path="materialId" htmlEscape="false" maxlength="64" class="input-xlarge " readOnly="true" />
			</div>
		</div> 
		<%-- <div class="control-group">
			<label class="control-label">物资编号：</label>
			<div class="controls">
				<input type="text" name="materialId" value="${material.materialId}"/>
			</div>
		</div> --%>
	<div class="control-group">
			<label class="control-label">物资名称：</label>
			<div class="controls">
			     <input type="text" name="materialName" value="${material.materialName}"readOnly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物资型号：</label>
			<div class="controls">
			    <input type="text" name="materialModel" value="${material.materialModel}"readOnly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物资单位：</label>
			<div class="controls">
			<input type="text" name="materialModel" value="${fns:getDictLabel(material.materialUnit,'material_unit', '')}"readOnly="true" />			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物资库存数量：</label>
			<div class="controls">
			    <input type="text" name="materialNumber" value="${material.materialNumber}"readOnly="true" />
			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物资分类：</label>
			<div class="controls">		
				  <input type="text" name="materialAssortment" value="${material.materialAssortment.id}" readOnly="true" />			
			</div>
		</div>	 
		<div class="control-group">
			<label class="control-label">领用数量：</label>
			<div class="controls">
				<form:input   path="useNumber" htmlEscape="false" maxlength="64" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<input type="text" name="remarks" value="${material.remarks}" readOnly="true" />			
			</div>
		</div>
		<div class="form-actions">
		
			<shiro:hasPermission name="material:materialProc:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="$('#flag').val('yes')" value="申请领用"/>&nbsp;</shiro:hasPermission>
			<c:if test="${not empty materialProc.id}">
					<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
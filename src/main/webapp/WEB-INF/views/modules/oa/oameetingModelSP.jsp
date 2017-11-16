<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会议流程审批</title>
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
		<li><a href="${ctx}/oa/oameetingModel/">会议流程列表</a></li>
		<li class="active"><a href="#"><shiro:hasPermission name="oa:oameetingModel:edit">会议审批</shiro:hasPermission><shiro:lacksPermission name="oa:oameetingModel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oameetingModel" action="${ctx}/oa/oameetingModel/oameetingModelSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
		<!-- <div class="control-group">
			<label class="control-label">流程实例号：</label>
			<div class="controls"> 
				<form:input path="procInsId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		-->
		<div class="control-group">
			<label class="control-label">会议主题：</label>
			<div class="controls">
				<form:input path="meetingDesc" htmlEscape="false" maxlength="2000" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议成员：</label>
			<div class="controls">
				<sys:treeselect id="meetingAttendee" name="meetingAttendee" value="${oameetingModel.meetingAttendee}" labelName="meetingAttendeenames" labelValue="${oameetingModel.meetingAttendeenames}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议开始时间：</label>
			<div class="controls">
				<input name="meetingStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oameetingModel.meetingStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议结束时间：</label>
			<div class="controls">
				<input name="meetingEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${oameetingModel.meetingEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议室：</label>
			<div class="controls">
				<form:select path="meetingRoom" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_meeting_room')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议类型：</label>
			<div class="controls">
				<form:select path="meetingType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_meeting_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会议管理员：</label>
			<div class="controls">
				<sys:treeselect id="meetingManager" name="meetingManager" value="${oameetingModel.meetingManager}" labelName="" labelValue="${fns:getUserById(oameetingModel.meetingManager).name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oameetingModel:edit">
				<c:if test="${oameetingModel.act.taskDefKey eq 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="兑 现" onclick="$('#flag').val('yes')"/>&nbsp;
				</c:if>
				<c:if test="${oameetingModel.act.taskDefKey ne 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty oameetingModel.id}">
			<act:histoicFlow procInsId="${oameetingModel.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>
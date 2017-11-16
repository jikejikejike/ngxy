/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 会议流程Entity
 * 
 * @author wanyy
 * @version 2015-11-24
 */
public class OameetingModel extends ActEntity<OameetingModel> {

	private static final long serialVersionUID = 1L;
	private String procInsId; // 流程实例号
	private String meetingDesc; // 会议主题
	private String meetingAttendee; // 会议成员
	private Date meetingStartDate; // 会议开始时间
	private Date meetingEndDate; // 会议结束时间
	private String meetingRoom; // 会议室
	private String meetingType; // 会议类型
	private String meetingManager; // 会议管理员
	private String ids; // 会议的ID

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	private List<User> UserList = Lists.newArrayList();

	public String getMeetingAttendeenames() {

		return Collections3.extractToString(UserList, "name", ",");

	}

	public void setMeetingAttendeenames(String meetingAttendeenames) {

	}

	public OameetingModel() {
		super();
	}

	public OameetingModel(String id) {
		super(id);
	}

	@Length(min = 1, max = 64, message = "流程实例号长度必须介于 1 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	@Length(min = 1, max = 2000, message = "会议主题长度必须介于 1 和 2000 之间")
	public String getMeetingDesc() {
		return meetingDesc;
	}

	public void setMeetingDesc(String meetingDesc) {
		this.meetingDesc = meetingDesc;
	}

	@Length(min = 1, max = 2000, message = "会议成员长度必须介于 1 和 2000 之间")
	public String getMeetingAttendee() {
		return meetingAttendee;
	}

	public void setMeetingAttendee(String meetingAttendee) {
		this.meetingAttendee = meetingAttendee;
		System.out.println("流程中是否调用：" + meetingAttendee);
		this.UserList = Lists.newArrayList();
		for (String id : StringUtils.split(meetingAttendee, ",")) {
			this.UserList.add(UserUtils.get(id));
		}
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "会议开始时间不能为空")
	public Date getMeetingStartDate() {
		return meetingStartDate;
	}

	public void setMeetingStartDate(Date meetingStartDate) {
		this.meetingStartDate = meetingStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "会议结束时间不能为空")
	public Date getMeetingEndDate() {
		return meetingEndDate;
	}

	public void setMeetingEndDate(Date meetingEndDate) {
		this.meetingEndDate = meetingEndDate;
	}

	@Length(min = 1, max = 80, message = "会议室长度必须介于 1 和 80 之间")
	public String getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(String meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	@Length(min = 1, max = 1, message = "会议类型长度必须介于 1 和 1 之间")
	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	@Length(min = 1, max = 64, message = "会议管理员长度必须介于 1 和 64 之间")
	public String getMeetingManager() {
		return meetingManager;
	}

	public void setMeetingManager(String meetingManager) {
		this.meetingManager = meetingManager;
	}

}
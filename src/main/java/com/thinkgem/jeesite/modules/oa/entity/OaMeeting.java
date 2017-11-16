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

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 会议管理Entity
 * 
 * @author wanyy
 * @version 2015-11-24
 */
public class OaMeeting extends DataEntity<OaMeeting> {

	private static final long serialVersionUID = 1L;
	private String name; // 会议主题
	private String meetingDesc; // 会议内容
	private String meetingAttendee; // 参加人员
	private Date meetingStartDate; // 预计开始时间
	private Date meetingEndDate; // 预计结束时间
	private String meetingRoom; // 会议室
	private String meetingStatus; // 状态
	private String meetingType; // 会议类型
	private String meetingManager; // 会议管理员
	private String files; // 附件
	private String MeetingAttendeenameid; // 获取id
	private List<User> UserList = Lists.newArrayList();

	// private String meetingAttendeenames;

	public List<User> getUserList() {
		return UserList;
	}

	public void setUserList(List<User> userList) {
		UserList = userList;
	}

	// 解析ID为名字的方法
	public String getMeetingAttendeenames() {
		return Collections3.extractToString(UserList, "name", ",");
	}

	public void setMeetingAttendeenames(String meetingAttendeenames) {

	}

	public OaMeeting() {
		super();
	}

	public OaMeeting(String id) {
		super(id);
	}

	@Length(min = 1, max = 50, message = "会议主题长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 1, max = 2000, message = "会议内容长度必须介于 1 和 2000 之间")
	public String getMeetingDesc() {
		return meetingDesc;
	}

	public void setMeetingDesc(String meetingDesc) {
		this.meetingDesc = meetingDesc;
	}

	@Length(min = 1, max = 2000, message = "参加人员长度必须介于 1 和 2000 之间")
	public String getMeetingAttendee() {
		return meetingAttendee;
	}

	// 根据ID获取名字的方法的具体实现
	public void setMeetingAttendee(String meetingAttendee) {
		this.meetingAttendee = meetingAttendee;
		System.out.println("是否调用：" + meetingAttendee);
		this.UserList = Lists.newArrayList();
		for (String id : StringUtils.split(meetingAttendee, ",")) {
			this.UserList.add(UserUtils.get(id));
		}

	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "预计开始时间不能为空")
	public Date getMeetingStartDate() {
		return meetingStartDate;
	}

	public void setMeetingStartDate(Date meetingStartDate) {
		this.meetingStartDate = meetingStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "预计结束时间不能为空")
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

	@Length(min = 1, max = 1, message = "状态长度必须介于 1 和 1 之间")
	public String getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
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

	@Length(min = 0, max = 2000, message = "附件长度必须介于 0 和 2000 之间")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

}
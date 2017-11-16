/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaMeeting;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OameetingModel;
import com.thinkgem.jeesite.modules.oa.service.OaMeetingService;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;
import com.thinkgem.jeesite.modules.oa.service.OameetingModelService;
/**
 * 会议管理Controller
 * 
 * @author wanyy
 * @version 2015-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaMeeting")
public class OaMeetingController extends BaseController {

	@Autowired
	private OaMeetingService oaMeetingService;

	@Autowired
	private   OameetingModelController oameetingModelController;
	@Autowired
	private  OameetingModelService OameetingModelService;

	@ModelAttribute
	public OaMeeting get(@RequestParam(required = false) String id) {
		OaMeeting entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = oaMeetingService.get(id);
		}
		if (entity == null) {
			entity = new OaMeeting();
		}
		return entity;
	}

	@RequiresPermissions("oa:oaMeeting:view")
	@RequestMapping(value = { "list", "" })
	public String list(OaMeeting oaMeeting, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<OaMeeting> page = oaMeetingService.findPage(new Page<OaMeeting>(request, response), oaMeeting);
		model.addAttribute("page", page);
		return "modules/oa/oaMeetingList";
	}

	

	@RequiresPermissions("oa:oaMeeting:view")
	@RequestMapping(value = "form")
	public String form(OaMeeting oaMeeting, Model model) {
		model.addAttribute("oaMeeting", oaMeeting);
		return "modules/oa/oaMeetingForm";
	}

	@RequiresPermissions("oa:oaMeeting:edit")
	@RequestMapping(value = "save")
	public String save(OaMeeting oaMeeting, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaMeeting)) {
			return form(oaMeeting, model);
		}
		//马上进入流程，修改oaMeeting状态为不能修改
		oaMeeting.setMeetingStatus("3");
		//保存会议
		oaMeetingService.save(oaMeeting);
		//最好点击提交审批按钮，然后再提交审批。而不是直接保存后，就提交审批。	
		//保存会议后启动会议审批流程
		OameetingModel oam=new OameetingModel();
		//设置会议流程所需的属性。
		oam.setCreateBy(oaMeeting.getCreateBy());
		oam.setCreateDate(oaMeeting.getCreateDate());
		oam.setCurrentUser(oaMeeting.getCurrentUser());
		oam.setMeetingAttendee(oaMeeting.getMeetingAttendee());
		oam.setMeetingDesc(oaMeeting.getMeetingDesc());
		oam.setMeetingManager(oaMeeting.getMeetingManager());
		oam.setMeetingRoom(oaMeeting.getMeetingRoom());
		oam.setMeetingStartDate(oaMeeting.getMeetingStartDate());
		oam.setMeetingEndDate(oaMeeting.getMeetingEndDate());
		oam.setMeetingType(oaMeeting.getMeetingType());
		//把oaMeeting的id插入oam的IDS属性中存储
	    oam.setIds(oaMeeting.getId());	    
		addMessage(redirectAttributes, "保存会议成功");
		//启动流程，调用会议审批流程的Controller中的save方法
	  return     oameetingModelController.save(oam,model,redirectAttributes);
		
		
		
	}

	@RequiresPermissions("oa:oaMeeting:edit")
	@RequestMapping(value = "delete")
	public String delete(OaMeeting oaMeeting,	RedirectAttributes redirectAttributes) {
		oaMeetingService.delete(oaMeeting);
		addMessage(redirectAttributes, "删除会议成功");
		return "redirect:" + Global.getAdminPath() + "/oa/oaMeeting/?repage";
	}

}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

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
import com.thinkgem.jeesite.modules.oa.entity.OameetingModel;
import com.thinkgem.jeesite.modules.oa.entity.TestAudit;
import com.thinkgem.jeesite.modules.oa.service.OameetingModelService;

/**
 * 会议流程Controller
 * @author wanyy
 * @version 2015-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oameetingModel")
public class OameetingModelController extends BaseController {

	@Autowired
	private OameetingModelService oameetingModelService;
	
	@ModelAttribute
	public OameetingModel get(@RequestParam(required=false) String id) {
		OameetingModel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oameetingModelService.get(id);
		}
		if (entity == null){
			entity = new OameetingModel();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oameetingModel:view")
	@RequestMapping(value = {"list", ""})
	public String list(OameetingModel oameetingModel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OameetingModel> page = oameetingModelService.findPage(new Page<OameetingModel>(request, response), oameetingModel); 
		model.addAttribute("page", page);
		return "modules/oa/oameetingModelList";
	}

	@RequiresPermissions("oa:oameetingModel:view")
	@RequestMapping(value = "form")
	public String form(OameetingModel oameetingModel, Model model) {
		
		String taskDefKey = oameetingModel.getAct().getTaskDefKey();
		//定义一个页面：默认跳转
		String view = "oameetingModelForm";
		//根据接收到的流程节点名称，判断跳转页面
		if ("model2".equals(taskDefKey)){
			view = "oameetingModelSP";
		}
		model.addAttribute("oameetingModel", oameetingModel);
		return "modules/oa/"+view;
	}

	@RequiresPermissions("oa:oameetingModel:edit")
	@RequestMapping(value = "save")
	public String save(OameetingModel oameetingModel, Model model, RedirectAttributes redirectAttributes) {
		
		if (!beanValidator(model, oameetingModel)){
			return form(oameetingModel, model);
		}
		//保存会议流程
		oameetingModelService.save(oameetingModel);
		addMessage(redirectAttributes, "提交会议流程成功");
		//注册流程
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	/**
	 * 工单执行（完成任务）
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oameetingModel:edit")
	@RequestMapping(value = "oameetingModelSave")
	public String oameetingModelSave(OameetingModel oameetingModel, Model model) {
		if (StringUtils.isBlank(oameetingModel.getAct().getFlag())){;
			return form(oameetingModel, model);
		}
		String taskDefKey = oameetingModel.getAct().getTaskDefKey();
		oameetingModelService.oameetingModelSave(oameetingModel);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	@RequiresPermissions("oa:oameetingModel:edit")
	@RequestMapping(value = "delete")
	public String delete(OameetingModel oameetingModel, RedirectAttributes redirectAttributes) {
		oameetingModelService.delete(oameetingModel);
		addMessage(redirectAttributes, "删除会议流程成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oameetingModel/?repage";
	}

}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.web;

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
import com.thinkgem.jeesite.modules.device.entity.QiluDevice;
import com.thinkgem.jeesite.modules.device.service.QiluDeviceService;

/**
 * 设备管理Controller
 * @author 高文辉
 * @version 2017-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/device/qiluDevice")
public class QiluDeviceController extends BaseController {

	@Autowired
	private QiluDeviceService qiluDeviceService;
	
	@ModelAttribute
	public QiluDevice get(@RequestParam(required=false) String id) {
		QiluDevice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiluDeviceService.get(id);
		}
		if (entity == null){
			entity = new QiluDevice();
		}
		return entity;
	}
	
	@RequiresPermissions("device:qiluDevice:view")
	@RequestMapping(value = {"list", ""})
	public String list(QiluDevice qiluDevice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiluDevice> page = qiluDeviceService.findPage(new Page<QiluDevice>(request, response), qiluDevice); 
		model.addAttribute("page", page);
		return "modules/device/qiluDeviceList";
	}

	@RequiresPermissions("device:qiluDevice:view")
	@RequestMapping(value = "form")
	public String form(QiluDevice qiluDevice, Model model) {
		model.addAttribute("qiluDevice", qiluDevice);
		return "modules/device/qiluDeviceForm";
	}

	@RequiresPermissions("device:qiluDevice:edit")
	@RequestMapping(value = "save")
	public String save(QiluDevice qiluDevice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qiluDevice)){
			return form(qiluDevice, model);
		}
		qiluDeviceService.save(qiluDevice);
		addMessage(redirectAttributes, "保存设备管理成功");
		return "redirect:"+Global.getAdminPath()+"/device/qiluDevice/?repage";
	}
	
	@RequiresPermissions("device:qiluDevice:edit")
	@RequestMapping(value = "delete")
	public String delete(QiluDevice qiluDevice, RedirectAttributes redirectAttributes) {
		qiluDeviceService.delete(qiluDevice);
		addMessage(redirectAttributes, "删除设备管理成功");
		return "redirect:"+Global.getAdminPath()+"/device/qiluDevice/?repage";
	}

}
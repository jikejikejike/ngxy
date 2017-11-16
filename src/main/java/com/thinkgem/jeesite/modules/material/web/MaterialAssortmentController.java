/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.material.entity.Material;
import com.thinkgem.jeesite.modules.material.entity.MaterialAssortment;
import com.thinkgem.jeesite.modules.material.service.MaterialAssortmentService;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 物资分类Controller
 * 
 * @author wanyy
 * @version 2015-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/material/materialAssortment")
public class MaterialAssortmentController extends BaseController {

	@Autowired
	private MaterialAssortmentService materialAssortmentService;

	@ModelAttribute
	public MaterialAssortment get(@RequestParam(required = false) String id) {
		MaterialAssortment entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = materialAssortmentService.get(id);
		}
		if (entity == null) {
			entity = new MaterialAssortment();
		}
		return entity;
	}

	@RequiresPermissions("material:materialAssortment:view")
	@RequestMapping(value = { "list", "" })
	public String list(MaterialAssortment materialAssortment,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<MaterialAssortment> page = materialAssortmentService.findPage(
				new Page<MaterialAssortment>(request, response),
				materialAssortment);
		model.addAttribute("page", page);
		return "modules/material/materialAssortmentList";
	}

	@RequiresPermissions("material:materialAssortment:view")
	@RequestMapping(value = "form")
	public String form(MaterialAssortment materialAssortment, Model model) {
		model.addAttribute("materialAssortment", materialAssortment);
		return "modules/material/materialAssortmentForm";
	}

	@RequiresPermissions("material:materialAssortment:edit")
	@RequestMapping(value = "save")
	public String save(MaterialAssortment materialAssortment, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, materialAssortment)) {
			return form(materialAssortment, model);
		}
		materialAssortmentService.save(materialAssortment);
		addMessage(redirectAttributes, "保存物资分类成功");
		return "redirect:" + Global.getAdminPath()
				+ "/material/materialAssortment/?repage";
	}

	@RequiresPermissions("material:materialAssortment:edit")
	@RequestMapping(value = "delete")
	public String delete(MaterialAssortment materialAssortment,
			RedirectAttributes redirectAttributes) {
		materialAssortmentService.delete(materialAssortment);
		addMessage(redirectAttributes, "删除物资分类成功");
		return "redirect:" + Global.getAdminPath()
				+ "/material/materialAssortment/?repage";
	}

	/**
	 * 获取机构JSON数据。物资分类数据来源。
	 */
	//@RequiresPermissions("material")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) Boolean isAll,
			HttpServletResponse response) {
		//定义一个list
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取数据库中所有的物资分类 isall可以去除。
		List<MaterialAssortment> list = materialAssortmentService.findList(isAll);
		for (int i = 0; i < list.size(); i++) {
			MaterialAssortment e = list.get(i);
			//前台需要获取键值对的list
				Map<String, Object> map = Maps.newHashMap();
				//设置自己的ID，
				map.put("id", e.getId());
				//设置自己的直接父类ID
				map.put("pId", e.getParentId());
				//设置自己的所有父类ID
				map.put("pIds", e.getParentIds());
				//设置自己的名称
			    map.put("name", e.getName());
				mapList.add(map);
		}
		return mapList;
	}

}

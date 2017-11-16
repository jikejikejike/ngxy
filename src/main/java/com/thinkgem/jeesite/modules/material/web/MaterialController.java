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
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.material.dao.MaterialAssortmentDao;
import com.thinkgem.jeesite.modules.material.dao.MaterialDao;
import com.thinkgem.jeesite.modules.material.entity.Material;
import com.thinkgem.jeesite.modules.material.entity.MaterialAssortment;
import com.thinkgem.jeesite.modules.material.entity.MaterialProc;
import com.thinkgem.jeesite.modules.material.service.MaterialService;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 物资管理Controller
 * @author wanyy
 * @version 2015-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/material/material")
public class MaterialController extends BaseController {

	@Autowired
	private MaterialService materialService;

	private static MaterialDao materialDao = SpringContextHolder.getBean(MaterialDao.class);

	@ModelAttribute
	public Material get(@RequestParam(required=false) String id) {
		Material entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = materialService.get(id);
		}
		if (entity == null){
			entity = new Material();
		}
		return entity;
	}
	//页面list展示，无参和有参
	@RequiresPermissions("material:material:view")
	@RequestMapping(value = {"list", ""})
	public String list(Material material, HttpServletRequest request, HttpServletResponse response, Model model) {
		//创建一个对象，为了存物资分类的ID。方便后面校验，获取。
		                        material=new Material();
		                        MaterialAssortment  materialAssortment=new MaterialAssortment();    
		                        materialAssortment.setId(request.getParameter("material.id"));
		                        material.setMaterialAssortment(materialAssortment);
		                        
		Page<Material> page = materialService.findPage(new Page<Material>(request, response), material); 
		model.addAttribute("page", page);
		return "modules/material/materialList";
	}

	
	@RequiresPermissions("material:material:view")
	@RequestMapping(value = {"index"})
	public String index(Material material, Model model) {
		return "modules/material/materialIndex";
	}
	
	
	@RequiresPermissions("material:material:view")
	@RequestMapping(value = "form")
	public String form(Material material, Model model) {
		model.addAttribute("material", material);
		return "modules/material/materialForm";
	}

	@RequiresPermissions("material:material:edit")
	@RequestMapping(value = "foruse")
	public String foruse( Material material,Model model, RedirectAttributes redirectAttributes,@RequestParam(required=false) String materialId) {
	    material=materialDao.get(materialId);
	    MaterialProc  materialProc =new MaterialProc();
	    materialProc.setMaterialId(material.getMaterialId());   
		model.addAttribute("material", material);
		model.addAttribute("materialProc", materialProc);
		return  "modules/material/materialforuse";
	}
	
	
	@RequiresPermissions("material:material:edit")
	@RequestMapping(value = "save")
	public String save(Material material, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, material)){
			return form(material, model);
		}
		materialService.save(material);
		addMessage(redirectAttributes, "保存物资管理成功");
		return "redirect:"+Global.getAdminPath()+"/material/material/?repage";
	}
	
	@RequiresPermissions("material:material:edit")
	@RequestMapping(value = "delete")
	public String delete(Material material, RedirectAttributes redirectAttributes) {
		materialService.delete(material);
		addMessage(redirectAttributes, "删除物资管理成功");
		return "redirect:"+Global.getAdminPath()+"/material/material/?repage";
	}
	
	


}
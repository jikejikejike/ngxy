/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.web;

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
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.material.dao.MaterialDao;
import com.thinkgem.jeesite.modules.material.dao.MaterialProcDao;
import com.thinkgem.jeesite.modules.material.entity.Material;
import com.thinkgem.jeesite.modules.material.entity.MaterialProc;
import com.thinkgem.jeesite.modules.material.service.MaterialProcService;
import com.thinkgem.jeesite.modules.oa.entity.OameetingModel;

/**
 * 物资申请Controller
 * @author wanyy
 * @version 2015-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/material/materialProc")
public class MaterialProcController extends BaseController {

	@Autowired
	private MaterialProcService materialProcService;
	private static MaterialProcDao materialProcDao = SpringContextHolder.getBean(MaterialProcDao.class);
	private static MaterialDao materialDao = SpringContextHolder.getBean(MaterialDao.class);

	@ModelAttribute
	public MaterialProc get(@RequestParam(required=false) String id) {
		MaterialProc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = materialProcService.get(id);
		}
		if (entity == null){
			entity = new MaterialProc();
		}
		return entity;
	}
	
	@RequiresPermissions("material:materialProc:view")
	@RequestMapping(value = {"list", ""})
	public String list(MaterialProc materialProc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MaterialProc> page = materialProcService.findPage(new Page<MaterialProc>(request, response), materialProc); 
		model.addAttribute("page", page);
		return "modules/material/materialProcList";
	}

	@RequiresPermissions("material:materialProc:view")
	@RequestMapping(value = "form")
	public String form(MaterialProc materialProc, Model model) {
		String taskDefKey = materialProc.getAct().getTaskDefKey();
		//定义一个页面：默认跳转
		String view = "materialForm";
		//根据接收到的流程节点名称，判断跳转页面
		if ("check1".equals(taskDefKey)){
			//System.out.println("仓库管理员审批");
			view = "materialchick";
		}
		if ("check2".equals(taskDefKey)){
			//System.out.println("主管审批");
			view = "materialchick";
		}
		if ("check3".equals(taskDefKey)){
			//System.out.println("发起人确认");
			view = "materialchick";
		}
		if ("check4".equals(taskDefKey)){
			//System.out.println("发起人修改");
			view = "materialforuse";
		}
		//获取流程中materialid所对应的material对象。
		//先从数据库中查出所有的对象，过滤条件。
		List<Material> list =materialDao.findAllList(new Material());   	    
    	for (int i = 0; i < list.size(); i++) {
    		Material material = list.get(i);
    		//过滤条件。只有一个。
    		if(material.getMaterialId().equals(materialProc.getMaterialId())){
    			//设置进request中。方便页面获取
	            model.addAttribute("material", material);
    		      }
    		}
		model.addAttribute("materialProc", materialProc);
		return "modules/material/"+view;
	}

	@RequiresPermissions("material:materialProc:edit")
	@RequestMapping(value = "save")
	public String save(MaterialProc materialProc, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, materialProc)){
			return form(materialProc, model);
		}
		materialProcService.save(materialProc);
		addMessage(redirectAttributes, "提交物资流程成功");
		return  "redirect:" + adminPath + "/act/task/todo/";
	}
	
	/**
	 * 工单执行（完成任务）
	 * @param model
	 * @return
	 */
	@RequiresPermissions("material:materialProc:edit")
	@RequestMapping(value = "MaterialProcSave")
	public String MaterialProcSave(MaterialProc materialProc, Model model) {
		String taskDefKey = materialProc.getAct().getTaskDefKey();	
		materialProcService.materialProcServiceSave(materialProc);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("material:materialProc:edit")
	@RequestMapping(value = "delete")
	public String delete(MaterialProc materialProc, RedirectAttributes redirectAttributes) {
		materialProcService.delete(materialProc);
		addMessage(redirectAttributes, "删除物资申请成功");
		return "redirect:"+Global.getAdminPath()+"/material/materialProc/?repage";
	}

}
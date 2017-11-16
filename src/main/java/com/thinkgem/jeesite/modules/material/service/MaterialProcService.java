/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.material.entity.Material;
import com.thinkgem.jeesite.modules.material.entity.MaterialProc;
import com.thinkgem.jeesite.modules.material.dao.MaterialDao;
import com.thinkgem.jeesite.modules.material.dao.MaterialProcDao;
import com.thinkgem.jeesite.modules.oa.entity.OaMeeting;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;

/**
 * 物资申请Service
 * @author wanyy
 * @version 2015-12-01
 */
@Service
@Transactional(readOnly = true)
public class MaterialProcService extends CrudService<MaterialProcDao, MaterialProc> {

	private static MaterialDao materialDao = SpringContextHolder.getBean(MaterialDao.class);

	@Autowired
	private ActTaskService actTaskService;
	
	
	public MaterialProc get(String id) {
		return super.get(id);
	}
	
	public List<MaterialProc> findList(MaterialProc materialProc) {
		return super.findList(materialProc);
	}
	
	public Page<MaterialProc> findPage(Page<MaterialProc> page, MaterialProc materialProc) {
		return super.findPage(page, materialProc);
	}
	

	
	@Transactional(readOnly = false)
	public void save(MaterialProc materialProc) {

		// 因为插入会议流程的时候一定是在insert之后，所以只能把他的父类方法重写
		// 如果是新流程的话，insert
		if (materialProc.getIsNewRecord()) {
			//个人觉得是初始化修改人，修改时间属性什么的。
			materialProc.preInsert();
			//插入流程到数据库
			dao.insert(materialProc);
			// 启动流程：PD_MATERIAL_MATERIALPROC需要自己在ACTUTIlS中新增定义
			actTaskService.startProcess(ActUtils.PD_MATERIAL_MATERIALPROC[0],ActUtils.PD_MATERIAL_MATERIALPROC[1], materialProc.getId());
		} else {
			// 如果是旧流程，那么修改
			materialProc.preUpdate();
			dao.update(materialProc);

			// 提交流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass","yes".equals(materialProc.getAct().getFlag()) ? "1" : "0");
			actTaskService.complete(materialProc.getAct().getTaskId(),materialProc.getAct().getProcInsId(), materialProc.getAct().getComment(), vars);

		}
		
	}
	
	/**
	 * 审核审批保存
	 * 
	 * @param MaterialProc
	 */
	@Transactional(readOnly = false)
	public void materialProcServiceSave(MaterialProc materialProc) {
		// 设置意见
		materialProc.getAct().setComment(("yes".equals(materialProc.getAct().getFlag()) ? "[同意] ": "[驳回] ") + materialProc.getAct().getComment());
        //更新方法
		materialProc.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = materialProc.getAct().getTaskDefKey();
		// 审核环节,当流程模板中的节点名字等于获取的流程节点时
		if ("check1".equals(taskDefKey)){
			//判断如果审批开始了，那么从数据库中减去这部分库存。
			
			
			System.out.println(taskDefKey);
		}
		else if ("check2".equals(taskDefKey)){
			System.out.println(taskDefKey);
		}
		else if ("check3".equals(taskDefKey)){
			System.out.println(taskDefKey);
		}
		else if ("check4".equals(taskDefKey)){
			//判断如果最终审批没通过了，那么把这部分库存，还回数据库
//		     if("no".equals(materialProc.getAct().getFlag())){
//		    		List<Material> list =materialDao.findAllList(new Material());   	    
//		        	for (int i = 0; i < list.size(); i++) {
//		        		Material e = list.get(i);
//		        		if(e.getMaterialId().equals(materialProc.getMaterialId())){
//		        			e.setMaterialNumber((Integer.parseInt(e.getMaterialNumber())-Integer.parseInt(materialProc.getUseNumber()))+"");
//		        			e.preUpdate();
//		        			materialDao.update(e);		
//		        		}
//		        		}
//		    	 
//		    	 
//		    	 
//		     }
		}
	//其他，直接返回
		else {
			return;
		}
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(materialProc.getAct().getFlag()) ? "1": "0");
		actTaskService.complete(materialProc.getAct().getTaskId(),materialProc.getAct().getProcInsId(), materialProc.getAct().getComment(), vars);
	
	}

	
	
	
	@Transactional(readOnly = false)
	public void delete(MaterialProc materialProc) {
		super.delete(materialProc);
	}
	
}
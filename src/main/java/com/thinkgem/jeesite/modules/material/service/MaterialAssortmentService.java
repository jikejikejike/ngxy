/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.material.entity.Material;
import com.thinkgem.jeesite.modules.material.entity.MaterialAssortment;
import com.thinkgem.jeesite.modules.material.dao.MaterialAssortmentDao;
import com.thinkgem.jeesite.modules.oa.dao.OaMeetingDao;
import com.thinkgem.jeesite.modules.material.dao.MaterialDao;
/**
 * 物资分类Service
 * @author wanyy
 * @version 2015-12-01
 */
@Service
@Transactional(readOnly = true)
public class MaterialAssortmentService extends CrudService<MaterialAssortmentDao, MaterialAssortment> {

	private static MaterialAssortmentDao MaterialAssortmentDao = SpringContextHolder.getBean(MaterialAssortmentDao.class);

	
	
	public MaterialAssortment get(String id) {
		return super.get(id);
	}
	
	public List<MaterialAssortment> findList(MaterialAssortment materialAssortment) {
		return super.findList(materialAssortment);
	}
	
	public Page<MaterialAssortment> findPage(Page<MaterialAssortment> page, MaterialAssortment materialAssortment) {
		return super.findPage(page, materialAssortment);
	}
	
	@Transactional(readOnly = false)
	public void save(MaterialAssortment materialAssortment) {
		super.save(materialAssortment);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaterialAssortment materialAssortment) {
		super.delete(materialAssortment);
	}

	public List<MaterialAssortment> findList(Boolean isAll) {
		
		return MaterialAssortmentDao.findAllList(new MaterialAssortment());
	}
	
}
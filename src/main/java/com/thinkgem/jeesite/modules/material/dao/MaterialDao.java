/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.material.entity.Material;

/**
 * 物资管理DAO接口
 * @author wanyy
 * @version 2015-12-01
 */
@MyBatisDao
public interface MaterialDao extends CrudDao<Material> {
	public List<Material> findMaterialById(String materialId);
}
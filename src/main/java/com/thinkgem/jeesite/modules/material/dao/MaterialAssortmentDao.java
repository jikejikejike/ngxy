/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.material.entity.MaterialAssortment;

/**
 * 物资分类DAO接口
 * @author wanyy
 * @version 2015-12-01
 */
@MyBatisDao
public interface MaterialAssortmentDao extends CrudDao<MaterialAssortment> {
	
}
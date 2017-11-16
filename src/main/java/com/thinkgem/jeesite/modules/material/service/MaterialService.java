/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.material.entity.Material;
import com.thinkgem.jeesite.modules.material.entity.MaterialAssortment;
import com.thinkgem.jeesite.modules.material.dao.MaterialDao;
import com.thinkgem.jeesite.modules.material.dao.MaterialAssortmentDao;
/**
 * 物资管理Service
 * @author wanyy
 * @version 2015-12-01
 */
@Service
@Transactional(readOnly = true)
public class MaterialService extends CrudService<MaterialDao, Material> {

private static MaterialDao materialDao = SpringContextHolder.getBean(MaterialDao.class);
	
	
	public Material get(String id) {
		return super.get(id);
	}
	
	public List<Material> findList(Material material) {
		return super.findList(material);
	}
	
	public Page<Material> findPage(Page<Material> page, Material material) {
		  material.setPage(page);
		//如果传过来的物资分类的ID是存在的，那么查询
		if(material.getMaterialAssortment().getId()!=null){		
			//创建一个集合用来获取从数据库里查到的所有对象。
			List<Material> list =materialDao.findMaterialById(material.getMaterialAssortment().getId());
			//System.out.println("调用");
		 //List<Material> list =materialDao.findAllList(new Material());   
		 //创建一个集合用来保存符合要求的对象，为了解决返回的ist只有一个对象的问题。
//		 List<Material> lists =new ArrayList();
//		 //循环这个集合，获取符合要求的对象。
//	    	for (int i = 0; i < list.size(); i++) {
//	    		Material e = list.get(i);
//	    		//从我获得的所有Material取值，如果Material的物资分类的编号等于传送过来的物资编号。那么把这个Material加入ｌｉｓｔ
//	    	    if(e.getMaterialAssortment().getId().equals(material.getMaterialAssortment().getId())){ 
	    	    	//把符合要求的对象加入集合。
//	    		lists.add(e);
//	    	     }
//	    	    }
	    	//设置page中的集合
	    	page.setList(list);    		
	      }else{
	    	  System.out.println("查无数据");
	    	  //如果查无数据，那么设为null
	    	  page.setList(dao.findList(material));    		
	      }
		return page;
	}
	
	@Transactional(readOnly = false)
	//保存物资，并且进行逻辑验证，如果编号和型号一致，那么修改他的库存
	public void save(Material material) {
		//如果是新数据，那么新增
		if (material.getIsNewRecord()){
			//创建一个集合用来获取从数据库里查到的所有对象。
	    	   List<Material> list =materialDao.findAllList(new Material());   
		    	for (int i = 0; i < list.size(); i++) {
		    		Material e = list.get(i);
		    		//当编号和型号一致，那么修改他的库存数量
		    		if(e.getMaterialId().equals(material.getMaterialId())&&e.getMaterialModel().endsWith(material.getMaterialModel())){
		    			//因为修改会根据ID修改，所以把materal的id设置
		    			material.setId(e.getId());
		    			//增加库存数量。
		    			material.setMaterialNumber((Integer.parseInt(e.getMaterialNumber())+Integer.parseInt(material.getMaterialNumber()))+"");
		    			material.preUpdate();
		    			dao.update(material);
		    			//数据库中只会有一个相同的编号，所以，只会查到一个，当查到之后，直接跳出。
		    			return;
		    		    }
		           }    	
		    	material.preInsert();
				dao.insert(material);
				//如果是旧数据。那么修改。
		}else{
			material.preUpdate();
			dao.update(material);
		}
		
		
	//super.save(material);
	}
	
	@Transactional(readOnly = false)
	public void delete(Material material) {
		super.delete(material);
	}

	
	
}
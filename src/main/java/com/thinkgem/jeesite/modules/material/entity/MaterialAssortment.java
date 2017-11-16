/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 物资分类Entity
 * @author wanyy
 * @version 2015-12-01
 */
public class MaterialAssortment extends TreeEntity<MaterialAssortment> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 物资名称
	//private MaterialAssortment parent;		// 物资分类父类编码
    //	private String parentIds;		// 所有物资分类父类编码
    //	private Integer sort;		// 排序
	private String materialId;		// 物资编码ID
	
	public MaterialAssortment() {
		super();
	}

	public MaterialAssortment(String id){
		super(id);
	}

	@Length(min=0, max=2000, message="物资名称长度必须介于 0 和 2000 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//@JsonBackReference
	public MaterialAssortment getParent() {

		return parent;
	}

	public void setParent(MaterialAssortment parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=64, message="所有物资分类父类编码长度必须介于 0 和 64 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=64, message="排序长度必须介于 0 和 64 之间")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=64, message="物资编码ID长度必须介于 0 和 64 之间")
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
}
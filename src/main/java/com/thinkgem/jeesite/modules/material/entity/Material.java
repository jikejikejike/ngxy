/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 物资管理Entity
 * @author wanyy
 * @version 2015-12-01
 */
public class Material extends DataEntity<Material> {
	
	private static final long serialVersionUID = 1L;
	private String materialId;		// 物资编号
	private String materialName;		// 物资名称
	private String materialModel;		// 物资型号
	private String materialUnit;		// 物资单位
	private String materialNumber;		// 物资库存数量
	private MaterialAssortment materialAssortment;		// material_assortment_id
	
	public Material() {
		super();
	}

	public Material(String id){
		super(id);
	}

	@Length(min=0, max=64, message="物资编号长度必须介于 0 和 64 之间")
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	@Length(min=0, max=2000, message="物资名称长度必须介于 0 和 2000 之间")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@Length(min=0, max=2000, message="物资型号长度必须介于 0 和 2000 之间")
	public String getMaterialModel() {
		return materialModel;
	}

	public void setMaterialModel(String materialModel) {
		this.materialModel = materialModel;
	}
	
	@Length(min=0, max=64, message="物资单位长度必须介于 0 和 64 之间")
	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	
	@Length(min=0, max=64, message="物资库存数量长度必须介于 0 和 64 之间")
	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}
	
	@JsonBackReference
	public MaterialAssortment getMaterialAssortment() {
		return materialAssortment;
	}

	public void setMaterialAssortment(MaterialAssortment materialAssortment) {
		this.materialAssortment = materialAssortment;
	}
	
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 物资申请Entity
 * @author wanyy
 * @version 2015-12-01
 */
public class MaterialProc extends ActEntity<MaterialProc> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String materialId;		// 物资编号
	private String useNumber;		// 领用数量
	
	public MaterialProc() {
		super();
	}

	public MaterialProc(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=64, message="物资编号长度必须介于 0 和 64 之间")
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	@Length(min=0, max=64, message="领用数量长度必须介于 0 和 64 之间")
	public String getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(String useNumber) {
		this.useNumber = useNumber;
	}
	
}
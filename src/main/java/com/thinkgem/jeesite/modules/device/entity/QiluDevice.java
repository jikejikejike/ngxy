/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 设备管理Entity
 * @author 高文辉
 * @version 2017-07-27
 */
public class QiluDevice extends DataEntity<QiluDevice> {
	
	private static final long serialVersionUID = 1L;
	private String deviceName;		// device_name
	private String deviceDetail;		// device_detail
	
	public QiluDevice() {
		super();
	}

	public QiluDevice(String id){
		super(id);
	}

	@Length(min=0, max=50, message="device_name长度必须介于 0 和 50 之间")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	@Length(min=0, max=50, message="device_detail长度必须介于 0 和 50 之间")
	public String getDeviceDetail() {
		return deviceDetail;
	}

	public void setDeviceDetail(String deviceDetail) {
		this.deviceDetail = deviceDetail;
	}
	
}
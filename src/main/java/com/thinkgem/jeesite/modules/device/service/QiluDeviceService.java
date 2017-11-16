/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.device.entity.QiluDevice;
import com.thinkgem.jeesite.modules.device.dao.QiluDeviceDao;

/**
 * 设备管理Service
 * @author 高文辉
 * @version 2017-07-27
 */
@Service
@Transactional(readOnly = true)
public class QiluDeviceService extends CrudService<QiluDeviceDao, QiluDevice> {

	public QiluDevice get(String id) {
		return super.get(id);
	}
	
	public List<QiluDevice> findList(QiluDevice qiluDevice) {
		return super.findList(qiluDevice);
	}
	
	public Page<QiluDevice> findPage(Page<QiluDevice> page, QiluDevice qiluDevice) {
		return super.findPage(page, qiluDevice);
	}
	
	@Transactional(readOnly = false)
	public void save(QiluDevice qiluDevice) {
		super.save(qiluDevice);
	}
	
	@Transactional(readOnly = false)
	public void delete(QiluDevice qiluDevice) {
		super.delete(qiluDevice);
	}
	
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaMeeting;
import com.thinkgem.jeesite.modules.oa.dao.OaMeetingDao;

/**
 * 会议管理Service
 * @author wanyy
 * @version 2015-11-24
 */
@Service
@Transactional(readOnly = true)
public class OaMeetingService extends CrudService<OaMeetingDao, OaMeeting> {

	public OaMeeting get(String id) {
		return super.get(id);
	}
	
	public List<OaMeeting> findList(OaMeeting oaMeeting) {
		return super.findList(oaMeeting);
	}
	
	public Page<OaMeeting> findPage(Page<OaMeeting> page, OaMeeting oaMeeting) {
		return super.findPage(page, oaMeeting);
	}
	
	@Transactional(readOnly = false)
	public void save(OaMeeting oaMeeting) {
		super.save(oaMeeting);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaMeeting oaMeeting) {
		super.delete(oaMeeting);
	}
	
}
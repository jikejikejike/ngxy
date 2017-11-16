/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaMeeting;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OameetingModel;
import com.thinkgem.jeesite.modules.oa.entity.TestAudit;
import com.thinkgem.jeesite.modules.oa.dao.OaMeetingDao;
import com.thinkgem.jeesite.modules.oa.dao.OameetingModelDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;

/**
 * 会议流程Service
 * 
 * @author wanyy
 * @version 2015-11-24
 */
@Service
@Transactional(readOnly = true)
public class OameetingModelService extends
		CrudService<OameetingModelDao, OameetingModel> {

	private static OaMeetingDao OaMeetingDAO = SpringContextHolder
			.getBean(OaMeetingDao.class);

	private static OameetingModelDao OameetingModelDao = SpringContextHolder
			.getBean(OameetingModelDao.class);
	
	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private OaMeetingService OaMeetingService;

	@Autowired
	private OaNotifyService OaNotifyService;

	public OameetingModel get(String id) {
		return super.get(id);
	}

	public List<OameetingModel> findList(OameetingModel oameetingModel) {
		return super.findList(oameetingModel);
	}

	public Page<OameetingModel> findPage(Page<OameetingModel> page,
			OameetingModel oameetingModel) {
		return super.findPage(page, oameetingModel);
	}

	@Transactional(readOnly = false)
	public void save(OameetingModel oameetingModel) {

		// 因为插入会议流程的时候一定是在insert之后，所以只能把他的父类方法重写
		// 如果是新流程的话，insert
		if (oameetingModel.getIsNewRecord()) {
			//个人觉得是初始化修改人，修改时间属性什么的。
			oameetingModel.preInsert();
			//插入流程到数据库
			dao.insert(oameetingModel);
			// 启动流程：PD_OA_MEETING需要自己在ACTUTIlS中新增定义
			actTaskService.startProcess(ActUtils.PD_OA_MEETING[0],ActUtils.PD_OA_MEETING[1], oameetingModel.getId());

		} else {
			// 如果是旧流程，那么修改
			oameetingModel.preUpdate();
			dao.update(oameetingModel);

			// 提交流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass","yes".equals(oameetingModel.getAct().getFlag()) ? "1" : "0");
			actTaskService.complete(oameetingModel.getAct().getTaskId(),oameetingModel.getAct().getProcInsId(), oameetingModel.getAct().getComment(), vars);

		}

	}

	/**
	 * 审核审批保存
	 * 
	 * @param oameetingModel
	 */
	@Transactional(readOnly = false)
	public void oameetingModelSave(OameetingModel oameetingModel) {

		// 设置意见
		oameetingModel.getAct().setComment(("yes".equals(oameetingModel.getAct().getFlag()) ? "[同意] ": "[驳回] ") + oameetingModel.getAct().getComment());
        //更新方法
		oameetingModel.preUpdate();

		// 对不同环节的业务逻辑进行操作
		String taskDefKey = oameetingModel.getAct().getTaskDefKey();
		// 审核环节,当流程模板中的节点名字等于获取的流程节点时
		if ("model2".equals(taskDefKey)) {
			// 初始化一个ids用来存放oameeting的id
			String ids = "";
			// 根据传递的参数（OameetingModel的ID）获取OameetingModel对象--数据库
			OameetingModel oaml = OameetingModelDao.get(oameetingModel.getId());
			//根据OameetingModel对象，取其之前存在数据库的oaMeeting的ID
			ids = oaml.getIds();
			//根据oaMeeting的ID获取oaMeeting对象--数据库
			OaMeeting oam = OaMeetingDAO.get(ids);
			//修改oaMeeting的状态为已批准
			oam.setMeetingStatus("1");
			//修改oaMeeting
			OaMeetingService.save(oam);
			// 保存会议之后，新增会议通知
			OaNotify oaNo = new OaNotify();
			// 把需要的值放入通知中，然后存储
			oaNo.setContent(oam.getMeetingDesc());
			// 插入通知类型：会议通知
			oaNo.setType("1");
			//设置其附件URL
			oaNo.setFiles(oam.getFiles());
			// 插入通知标题：会议标题
			oaNo.setTitle(oam.getName());
			//设置参与人员
			oaNo.setOaNotifyRecordIds(oam.getMeetingAttendee());
			// 设置会议状态：已发布
			oaNo.setStatus("1");
			//保存会议
			OaNotifyService.save(oaNo);
		}
	//其他，直接返回
		else {
			return;
		}

		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(oameetingModel.getAct().getFlag()) ? "1": "0");
		actTaskService.complete(oameetingModel.getAct().getTaskId(),oameetingModel.getAct().getProcInsId(), oameetingModel.getAct().getComment(), vars);

	}

	@Transactional(readOnly = false)
	public void delete(OameetingModel oameetingModel) {
		super.delete(oameetingModel);
	}

}
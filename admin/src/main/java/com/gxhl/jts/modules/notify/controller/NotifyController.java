package com.gxhl.jts.modules.notify.controller;

import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.oa.domain.NotifyDO;
import com.bootdo.oa.domain.NotifyRecordDO;
import com.bootdo.oa.service.NotifyRecordService;
import com.bootdo.oa.service.NotifyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知通告
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-05 17:11:16
 */

@Controller
@RequestMapping("/oa/notify")
public class NotifyController extends BaseController {
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private NotifyRecordService notifyRecordService;
	@Autowired
	private DictService dictService;

	@GetMapping()
	@RequiresPermissions("notify:notify:notify")
	String oaNotify() {
		return "notify/notify/notify";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("notify:notify:notify")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<NotifyDO> notifyList = notifyService.list(query);
		int total = notifyService.count(query);
		PageUtils pageUtils = new PageUtils(notifyList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("notify:notify:add")
	String add() {
		return "notify/notify/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("notify:notify:edit")
	String edit(@PathVariable("id") Long id, Model model) {
		NotifyDO notify = notifyService.get(id);
		List<DictDO> dictDOS = dictService.listByType("oa_notify_type");
		String type = notify.getType();
		for (DictDO dictDO:dictDOS){
			if(type.equals(dictDO.getValue())){
				dictDO.setRemarks("checked");
			}
		}
		model.addAttribute("oaNotifyTypes",dictDOS);
		model.addAttribute("notify", notify);
		return "notify/notify/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("notify:notify:add")
	public R save(NotifyDO notify) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		notify.setCreateBy(getUserId());
		if (notifyService.save(notify) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("notify:notify:edit")
	public R update(NotifyDO notify) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		notifyService.update(notify);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("notify:notify:remove")
	public R remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (notifyService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("notify:notify:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		notifyService.batchRemove(ids);
		return R.ok();
	}

	@ResponseBody
	@GetMapping("/message")
	PageUtils message() {
		Map<String, Object> params = new HashMap<>(16);
		params.put("offset", 0);
		params.put("limit", 3);
		Query query = new Query(params);
        query.put("userId", getUserId());
        query.put("isRead",Constant.OA_NOTIFY_READ_NO);
		return notifyService.selfList(query);
	}

	@GetMapping("/selfNotify")
	String selefNotify() {
		return "notify/notify/selfNotify";
	}

	@ResponseBody
	@GetMapping("/selfList")
	PageUtils selfList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		query.put("userId", getUserId());

		return notifyService.selfList(query);
	}

	@GetMapping("/read/{id}")
	@RequiresPermissions("notify:notify:edit")
	String read(@PathVariable("id") Long id, Model model) {
		NotifyDO notify = notifyService.get(id);
		//更改阅读状态
		NotifyRecordDO notifyRecordDO = new NotifyRecordDO();
		notifyRecordDO.setNotifyId(id);
		notifyRecordDO.setUserId(getUserId());
		notifyRecordDO.setReadDate(new Date());
		notifyRecordDO.setIsRead(Constant.OA_NOTIFY_READ_YES);
		notifyRecordService.changeRead(notifyRecordDO);
		model.addAttribute("notify", notify);
		return "notify/notify/read";
	}



}

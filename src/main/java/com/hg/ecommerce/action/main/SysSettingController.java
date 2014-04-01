package com.hg.ecommerce.action.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hg.ecommerce.model.SysSetting;
import com.hg.ecommerce.service.SysSettingService;
import com.hg.ecommerce.util.Util;

@RestController
@RequestMapping("/webservice/sys_setting")
public class SysSettingController {
	
	@Autowired
	private SysSettingService sysSettingService;
	
	@ModelAttribute("sysSetting")
	public SysSetting assembleSysSetting(@RequestParam long propId
			, @RequestParam String propKey
			, @RequestParam String propValue
			, @RequestParam String remark
			, @RequestParam long createperson
			, @RequestParam Date createtime
			, @RequestParam long editperson
			, @RequestParam Date edittime){
		SysSetting setting = new SysSetting();
		setting.setPropId(propId);
		setting.setPropKey(propKey);
		setting.setPropValue(propValue);
		setting.setRemark(remark);
		setting.setCreateperson(createperson);
		setting.setCreatetime(createtime);
		setting.setEditperson(editperson);
		setting.setEdittime(edittime);
		
		return setting;
	}
	
	@RequestMapping(value="/add",produces="application/json")
	public String addSysSetting(@ModelAttribute("sysSetting") SysSetting setting){
		sysSettingService.save(setting);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		
		return Util.getJsonObject(map).toString();
	}
	
	@RequestMapping(value="/update",produces="application/json")
	public String updateSysSetting(@ModelAttribute("sysSetting") SysSetting setting){
		sysSettingService.update(setting);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		
		return Util.getJsonObject(map).toString();
	}
	
	@RequestMapping(value="/fetch",produces="application/json")
	public String fetchSysSetting(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", sysSettingService.selectAll());
		
		return Util.getJsonObject(map).toString();
	}
	
	@RequestMapping(value="/remove",produces="application/json")
	public String remove(@ModelAttribute("sysSetting") SysSetting setting){
		sysSettingService.delete(setting);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		
		return Util.getJsonObject(map).toString();
	}
}

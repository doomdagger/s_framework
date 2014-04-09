package com.hg.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hg.ecommerce.dao.impl.SysSettingDao;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.model.SysSetting;
import com.hg.ecommerce.service.SysSettingService;

@Component("sysSettingService")
public class SysSettingServiceImpl implements SysSettingService {

	@Autowired
	private  SysSettingDao sysSettingDao;
	
	@Override
	public Object save(SysSetting sysSetting) {
		//if(sysSettingDao.add(sysSetting)){
			//return true;
		//}
		return sysSettingDao.add(sysSetting);
	}

	@Override
	public boolean saveByWrapper(SQLWrapper wrapper) {
		if(sysSettingDao.addByWrapper(wrapper)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(SysSetting sysSetting) {
		if(sysSettingDao.delete(sysSetting)){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByWrapper(SQLWrapper wrapper) {
		if(sysSettingDao.deleteByWrapper(wrapper)){
			return true;
		}
		return false;
	}

	@Override
	public boolean update(SysSetting sysSetting) {
		if(sysSettingDao.update(sysSetting)){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateByWrapper(SQLWrapper wrapper) {
		if(sysSettingDao.updateByWrapper(wrapper)){
			return true;
		}
		return false;
	}
	
	@Override
	public SysSetting selectById(Object...id) {
		return sysSettingDao.findOneById(id);
	}

	@Override
	public List<SysSetting> selectAll() {
		return sysSettingDao.findAll();
	}

	@Override
	public List<SysSetting> selectByWrapper(SQLWrapper wrapper) {
		return sysSettingDao.findAllByWrapper(wrapper);
	}

}

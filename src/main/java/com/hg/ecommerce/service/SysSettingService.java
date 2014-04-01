package com.hg.ecommerce.service;

import java.util.List;

import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.model.SysSetting;

public interface SysSettingService {
	
	public boolean save(SysSetting sysSetting);
	
	public boolean saveByWrapper(SQLWrapper wrapper);
	
	public boolean delete(SysSetting sysSetting);
	
	public boolean deleteByWrapper(SQLWrapper wrapper);
	
	public boolean update(SysSetting sysSetting);
	
	public boolean updateByWrapper(SQLWrapper wrapper);
	
	public SysSetting selectById(Object...id);
	
	public List<SysSetting> selectAll();
	
	public List<SysSetting> selectByWrapper(SQLWrapper wrapper);
}

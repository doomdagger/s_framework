package com.hg.ecommerce.action.extension.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.hg.ecommerce.model.SysSetting;

/**
 * <p>simple demo to introduce how to write your own property editor</p>
 * 
 * <p>In this demo, I implement getAsText() and setAsText() to provide conversion
 * policy for spring mvc, and bind this editor to SysSetting.class in AppController,
 * Spring MVC will automatically make use of this editor when such conversion is needed</p>
 * @author Li He
 *
 */
public class SysSettingEditor extends PropertyEditorSupport{

	private final boolean allowEmpty;
	
	public SysSettingEditor(){
		this(true);
	}
	
	public SysSettingEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}else {
			long propId = Long.valueOf(text);
			SysSetting setting = new SysSetting();
			setting.setPropId(propId);
			setValue(setting);
		}
	}
	
	@Override
	public String getAsText() {
		return ((SysSetting)getValue()).getPropId()+"";
	}
}

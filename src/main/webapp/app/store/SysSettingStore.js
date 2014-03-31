Ext.define('MyDesktop.store.SysSettingStore', {
	extend : 'Ext.data.Store',
	autoDestroy: true,
	model : 'MyDesktop.model.SysSettingModel',
	proxy : {
		type : 'ajax',
		url : '/webservice/sys_setting',
		reader : {
			type : 'json',
			root : 'sys_settings'
		}
	},
	sorters: [{
        property: 'propId',
        direction: 'ASC'
    }],
	autoLoad : true
});
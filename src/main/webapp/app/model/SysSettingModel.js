Ext.define('MyDesktop.model.SysSettingModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'propId',  type: 'int'},
        {name: 'propKey',  type: 'string'},
        {name: 'propValue',  type: 'string'},
        {name: 'remark',  type: 'string'},
        {name: 'createperson',  type: 'int'},
        {name: 'createtime',  type: 'date', dateFormat: 'yyyy-MM-dd'},
        {name: 'editperson',  type: 'int'},
        {name: 'edittime',  type: 'date', dateFormat: 'yyyy-MM-dd'}
    ]
});
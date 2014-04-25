package com.hg.ecommerce.model;
import com.hg.ecommerce.model.support.annotation.Table;
import com.hg.ecommerce.model.support.annotation.Column;
import com.hg.ecommerce.model.support.EntityObject;
import ch.rasc.extclassgenerator.Model;
import java.util.Date;
import ch.rasc.extclassgenerator.ModelField;
import com.hg.ecommerce.model.support.annotation.Id;
/**
 * Description:No Description Available
 * <p>from schema <strong>demo</strong> in table <strong>sys_setting</strong></p>
 * <p>Do not modify the source code randomly</p>
 * 
 *  @author auto_generate model
 */
 @Table("sys_setting")
 @Model(value="MyDesktop.model.SysSetting") 
public class SysSetting extends EntityObject{
	/**
	 *  serialVersionUID, dedicated to object serialize.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "prop_id"</li>
	 * 	 <li>Field Type  : "BIGINT"</li>
	 * 	 <li>Field Length: "19"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Id
	@Column("prop_id")
	private long propId;
	public long getPropId() {
		return propId;
	}
	public void setPropId(long propId) {
		this.propId = propId;
	}
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "prop_key"</li>
	 * 	 <li>Field Type  : "VARCHAR"</li>
	 * 	 <li>Field Length: "40"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Column("prop_key")
	private String propKey;
	public String getPropKey() {
		return propKey;
	}
	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "prop_value"</li>
	 * 	 <li>Field Type  : "VARCHAR"</li>
	 * 	 <li>Field Length: "40"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Column("prop_value")
	private String propValue;
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "remark"</li>
	 * 	 <li>Field Type  : "VARCHAR"</li>
	 * 	 <li>Field Length: "40"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Column("remark")
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "createperson"</li>
	 * 	 <li>Field Type  : "BIGINT"</li>
	 * 	 <li>Field Length: "19"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Column("createperson")
	private long createperson;
	public long getCreateperson() {
		return createperson;
	}
	public void setCreateperson(long createperson) {
		this.createperson = createperson;
	}
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "createtime"</li>
	 * 	 <li>Field Type  : "DATETIME"</li>
	 * 	 <li>Field Length: "19"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Column("createtime")
	@ModelField(dateFormat="Y-m-d H:i:s")
	private Date createtime;
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "editperson"</li>
	 * 	 <li>Field Type  : "BIGINT"</li>
	 * 	 <li>Field Length: "19"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Column("editperson")
	private long editperson;
	public long getEditperson() {
		return editperson;
	}
	public void setEditperson(long editperson) {
		this.editperson = editperson;
	}
	/**
	 * Description:
	 * <p>
	 * Mapped field info in table:
	 * <ul>
	 * 	 <li>Field Name  : "edittime"</li>
	 * 	 <li>Field Type  : "DATETIME"</li>
	 * 	 <li>Field Length: "19"</li>
	 *   <li>Refer Info  : ""</li>
	 * </ul>
	 * </p>
	 */
	@Column("edittime")
	@ModelField(dateFormat="Y-m-d H:i:s")
	private Date edittime;
	public Date getEdittime() {
		return edittime;
	}
	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}
}

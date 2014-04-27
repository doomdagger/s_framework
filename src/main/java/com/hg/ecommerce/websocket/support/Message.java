package com.hg.ecommerce.websocket.support;

import java.util.Date;
import java.util.List;

import com.hg.ecommerce.model.support.EntityObject;

/**
 * Message类，content内容不确定，可能为纯文本，也可能为一段音频，视频或图片
 * @author Li He
 *
 * @param <T> message内容不确定
 */
public class Message<T> extends EntityObject{

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2590053637441476430L;
	
	private List<String> to;
	
	private String from;
	
	private boolean groupMessage;
	
	private String groupId;
	
	private T content;
	
	private Date date;

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public boolean isGroupMessage() {
		return groupMessage;
	}

	public void setGroupMessage(boolean groupMessage) {
		this.groupMessage = groupMessage;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}

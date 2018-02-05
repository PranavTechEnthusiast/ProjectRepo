package org.newbieApps.messenger.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Comment {
	private long commentId;
	private String commentText;
	private String author;
	private Date created;
	
	public Comment(long commentId, String commentText, String author) {
		this.commentId = commentId;
		this.commentText = commentText;
		this.author = author;
		this.created = new Date();
	}
	
	public Comment() {
	}
	
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
}

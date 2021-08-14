package com.finalproject.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class Comment {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="created")
	private LocalDateTime created;
	
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="post_id")
	private int postId;
	
	@Column(name="content")
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}

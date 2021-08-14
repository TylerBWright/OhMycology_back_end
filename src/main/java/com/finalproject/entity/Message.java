package com.finalproject.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="message")

public class Message {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="created")
	private LocalDateTime created;
	
	@JoinColumn(name="user_id")
	@OneToOne(cascade = CascadeType.ALL)
	private User user1;
	
	@JoinColumn(name="user_id_other")
	@OneToOne(cascade = CascadeType.ALL)
	private User user2;
	
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

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}

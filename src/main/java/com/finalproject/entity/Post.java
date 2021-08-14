package com.finalproject.entity;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="post")
@JsonIgnoreProperties(value = { "image" })
public class Post {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="created")
	private LocalDateTime created;
	
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	
	@Transient
	private List<Comment> comments;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="color")
	private String color;
	
	@Column(name="height_in_cm")
	private double height;
	
	@Column(name="cap")
	private String cap;
	
	@Column(name="stem")
	private String stem;
	
	@Column(name="underside")
	private String underside;
	
	@Column(name="spore_print")
	private String sporePrint;
	
	@Column(name="texture")
	private String texture;
	
	@Column(name="substrate")
	private String substrate;
	
	@Column(name="diameter_in_cm")
	private double diameter;
	
	@Column(name="location")
	private String location;
	
	@Column(name="description")
	private String description;
	
	@Column(name="family")
	private String family;
	
	@Column(name="genus")
	private String genus;
	
	@Column(name="common_names")
	private String commonNames;
	
	@Column(name="edibility")
	private String edibility;
	
	@Column(name="image")
	@Lob
	private Blob image;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public String getUnderside() {
		return underside;
	}

	public void setUnderside(String underside) {
		this.underside = underside;
	}

	public String getSporePrint() {
		return sporePrint;
	}

	public void setSporePrint(String sporePrint) {
		this.sporePrint = sporePrint;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getSubstrate() {
		return substrate;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getGenus() {
		return genus;
	}

	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getCommonNames() {
		return commonNames;
	}

	public void setCommonNames(String commonNames) {
		this.commonNames = commonNames;
	}

	public String getEdibility() {
		return edibility;
	}

	public void setEdibility(String edibility) {
		this.edibility = edibility;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}

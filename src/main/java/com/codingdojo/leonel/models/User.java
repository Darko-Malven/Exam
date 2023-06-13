package com.codingdojo.leonel.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@NotNull(message="First name is obligatory")
	@Size(min=3,message="First name must be at least 3 characters")
	private String name;
	@NotNull(message="Last name is obligatory")
	@Size(min=3,message="Last name must be at least 3 characters")
	private String lastName;
	@NotEmpty(message="Email is obligatory")
	@Email
	private String email;
	@NotNull(message="Password is obligatory")
	@Size(min=6,message="Password must be at least 6 characters")
	private String password;
	@Transient
	@NotNull(message="Confirm your password")
	@Size(min=6,message="Password must be at least 6 characters")
	private String confirm;
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;

	@OneToMany(mappedBy="joined",fetch=FetchType.LAZY)
	private List<Course> myCourses;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="course_has_user",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="course_id")
			)
	private List<Course> courseJoined;
	
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public List<Course> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<Course> myCourses) {
		this.myCourses = myCourses;
	}

	public List<Course> getCourseJoined() {
		return courseJoined;
	}

	public void setCourseJoined(List<Course> courseJoined) {
		this.courseJoined = courseJoined;
	}
	
	
}

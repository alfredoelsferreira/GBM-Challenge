package com.gbm.fullstack.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="login_attempt")
public class LoginAttempt {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="attempt_id")
    private Long attemptId;
	
	
	@OneToOne(mappedBy = "loginAttempt")
    private User user;
	
	
	@Column(name="last_modified")
	private Timestamp lastModified = new Timestamp(System.currentTimeMillis());
	
	@Column(name="attempts", nullable=false)
    private Integer attempts;
	
	


	public LoginAttempt() {
		super();
		attempts = 0;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	
	

}

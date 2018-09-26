package com.gbm.fullstack.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Alfredo Ferreira
 * @version 1.0 (current version number of application)
 * @since 1.0  
 *
 */
public class UserAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;

	private AccountCredentials user;
	
	private String authority;

	/**
	 * @param user
	 */
	public UserAuthority(AccountCredentials user) {
		this.user = user;
	}
	
	/**
	 * 
	 */
	public UserAuthority() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		return this.authority;
	}

	/**
	 * @return
	 */
	public AccountCredentials getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(AccountCredentials user) {
		this.user = user;
	}

	/**
	 * @param authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.authority;
	}
	
}

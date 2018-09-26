package com.gbm.fullstack.security;

import java.util.HashSet;
import java.util.Set;

import com.gbm.fullstack.model.User;

public class AccountCredentials {
	private String email;
	private String password;
	private Set<UserAuthority> authorities;
	private long expires;
	
	
	
	public AccountCredentials() {
	}
	
	public AccountCredentials(User user) {
		email = user.getEmail();
		password = user.getPassword();
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
	
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<UserAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public long getExpires() {
		return expires;
	}
	
	public void setExpires(long expires) {
		this.expires = expires;
	}  
	
	public void grantRole(String role) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(this.asAuthorityFor(role));
	}
	
	public void revokeRole(String role) {
		if (authorities != null) {
			authorities.remove(this.asAuthorityFor(role));
		}
	}
	
	public void addAuthority(UserAuthority authority) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(authority);
	}
	
	private UserAuthority asAuthorityFor(String role) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority(role);
		authority.setUser(this);
		return authority;
	}
	
	public String getAuthoritiesAsString() {
		StringBuilder b = new StringBuilder();
		if (authorities != null) {
			String sep = "";
			for (UserAuthority a : authorities) {
				b.append(sep);
				b.append(a.toString());
				sep = ",";
				
			}
		}
		return b.toString();
	}
}


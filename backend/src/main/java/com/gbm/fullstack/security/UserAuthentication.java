package com.gbm.fullstack.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gbm.fullstack.model.User;
import com.gbm.fullstack.security.jwt.TokenProvider;

public class UserAuthentication implements Authentication {

	private TokenProvider tokenProvider;
	
	private static final long serialVersionUID = 1L;
	private final AccountCredentials user;
	private boolean authenticated = true;

	public UserAuthentication(AccountCredentials user, TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
		this.user = user;
	}
	
	@Override
	public String getName() {
		return user.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public AccountCredentials getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		if (user != null) {
			return user.getEmail();
		}
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public AccountCredentials getUser() {
		return user;
	}
	

	public static String getExtendedTokenFromUser(){
		String token = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		 if( auth != null && auth.getClass().equals(UserAuthentication.class) && auth.isAuthenticated() )
			token = ((UserAuthentication)auth).tokenProvider.extendTokenFromUser( ((UserAuthentication)auth).getUser() );
		return token;
	}
}

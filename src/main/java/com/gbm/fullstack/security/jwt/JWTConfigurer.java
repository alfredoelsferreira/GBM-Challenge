package com.gbm.fullstack.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	public static final String AUTH_HEADER_NAME = "X-GBM-TOKEN";
	public static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
	public static final String ISSUER = "GBM";

	@Autowired
	private JWTFilter jwtFilter;
	

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JWTFilter customFilter = this.jwtFilter;
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

package com.gbm.fullstack.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gbm.fullstack.security.jwt.JWTConfigurer;
import com.gbm.fullstack.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailServiceImpl userDetailsService; 

	
	@Autowired
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;
	
	@Autowired
	private UserDetailServiceImpl customUserDetailsService; 
	
	@Autowired
	private JWTConfigurer jwtConfig;
	

	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
//	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		config.applyPermitDefaultValues();
  
		source.registerCorsConfiguration("/v1/**", config);
		return source;
	}	

	
	
	/**
	 * 
	 */
	protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
		.and()
			.csrf()
			.disable()
			.headers()
	        .frameOptions()
	        .disable()
	    .and()
	    	.cors()
	    .and()
	        .authorizeRequests()
	        .antMatchers("/v1/login").permitAll()
	        .antMatchers("/v1/**").authenticated()
		.and()
    		.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        	.apply(securityConfigurerAdapter());
	}
	
	private JWTConfigurer securityConfigurerAdapter() {
        return this.jwtConfig;
    }
}
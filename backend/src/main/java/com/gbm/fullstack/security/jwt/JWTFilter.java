package com.gbm.fullstack.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.gbm.fullstack.security.UserAuthentication;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTFilter extends GenericFilterBean {
	
	private final Logger log = LoggerFactory.getLogger(JWTFilter.class);
	
	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {
	    log.debug("In doFilter");
	    try {
	    	long id = System.currentTimeMillis();
	    	HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
	    	log.debug(id + ": http request " + httpServletRequest.getRequestURI());
	        String jwt = resolveToken(httpServletRequest);
	        UserAuthentication authentication = null;
	
			//not every request require authentication. no jwt, no auth.
	        if( StringUtils.hasText(jwt) ) {
				//This function should throw an error if there is anything wrong in the JWT
	            this.tokenProvider.validateToken(jwt);
	            authentication = this.tokenProvider.getAuthentication(jwt);
	        }
	        
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        log.debug(id + ": http response " + ((HttpServletResponse) servletResponse).getStatus() + " finished in " + (System.currentTimeMillis() - id) + "ms");
	    } 
	    catch ( ExpiredJwtException|UnsupportedJwtException|SignatureException|MalformedJwtException|IllegalArgumentException eje) {
	    	servletRequest.setAttribute("FilterException", eje);
	    	if( eje.getClass().isInstance(ClaimJwtException.class))
	    	{
	    		ClaimJwtException claimExc = ((ClaimJwtException)eje);
	    		log.info("Security exception for user {} - {}", claimExc.getClaims().getSubject(), claimExc.getMessage());
	    	}
	        ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        SecurityContextHolder.getContext().setAuthentication(null);
	    }
	    
	
	    filterChain.doFilter(servletRequest, servletResponse);
	}
    
    private String resolveToken(HttpServletRequest request){
        String jwt = request.getHeader(JWTConfigurer.AUTH_HEADER_NAME);
        return jwt;
    }

}

package com.gbm.fullstack.security.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gbm.fullstack.model.User;
import com.gbm.fullstack.security.AccountCredentials;
import com.gbm.fullstack.security.UserAuthentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class TokenProvider {
	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
	
	@Value("${gbm.jwtTokenValidityInSeconds}")
	private long tokenValidityInSeconds;
	
	@Value("${gbm.secrectkey}")
	private String secretKey;
	
	@PostConstruct
    public void init() {
		this.tokenValidityInSeconds = 1000 * this.tokenValidityInSeconds;
		log.debug("TokenProvider::init set token valid to:" + tokenValidityInSeconds);
    }
	
	public AccountCredentials parseUserFromToken(String token) {
		try {
            Claims body = Jwts.parser()
                    .setSigningKey(this.secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            //String issuer = body.getIssuer();
            AccountCredentials user = new AccountCredentials();
            user.setEmail(body.getSubject());
            user.setExpires(body.getExpiration().getTime());
//            user.setName((String)body.get("name"));
            String str = (String) body.get("authorities");

            List<String> authorities = new ArrayList<String>(Arrays.asList(str.split(",")));
            
            for (String authority : authorities) {
            	user.grantRole(authority);
			}

            return user;

        } catch (JwtException | ClassCastException e) {
            return null;
        }

	}
	
	public String addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		long now = (new Date()).getTime();

		final AccountCredentials user = authentication.getDetails();
		user.setExpires(now + this.getTokenValidityInSeconds());
		String token = createTokenForUser(user);
		if(response !=null)
			response.addHeader(JWTConfigurer.AUTH_HEADER_NAME, token);
		
		return token;
	}

	public String createTokenForUser(AccountCredentials user) {
		
		Date date = null;
		Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put("authorities", user.getAuthoritiesAsString());
		claims.put("email", user.getEmail());
		
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setIssuer(JWTConfigurer.ISSUER)
				.signWith(SignatureAlgorithm.HS512, this.secretKey)
				.setExpiration(new Date(user.getExpires())) 
				.compact();
		
	}
	
	public UserAuthentication getAuthentication(String token) {
		AccountCredentials user = this.parseUserFromToken(token);
		if (user != null) {
			return new UserAuthentication(user, this);
		}
		return null;
		
    }

	
	public void validateToken(String authToken) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
    }

	public long getTokenValidityInSeconds() {
        return this.tokenValidityInSeconds;
	}

	public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
		this.tokenValidityInSeconds = tokenValidityInSeconds;
	}
	
	public String extendTokenFromUser( AccountCredentials user ){
		long now = (new Date()).getTime();
		user.setExpires(now + this.getTokenValidityInSeconds());
		return this.createTokenForUser(user);
	}
}


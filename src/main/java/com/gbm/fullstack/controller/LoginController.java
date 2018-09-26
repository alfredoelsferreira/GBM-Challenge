package com.gbm.fullstack.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbm.fullstack.dto.GBMApiResponse;
import com.gbm.fullstack.dto.GBMApiResponse.ApiError;
import com.gbm.fullstack.dto.GBMApiResponse.Status;
import com.gbm.fullstack.dto.JWTToken;
import com.gbm.fullstack.dto.LoginDTO;
import com.gbm.fullstack.dto.error.ErrorCode;
import com.gbm.fullstack.model.Role;
import com.gbm.fullstack.model.User;
import com.gbm.fullstack.security.AccountCredentials;
import com.gbm.fullstack.security.UserAuthentication;
import com.gbm.fullstack.security.jwt.TokenProvider;
import com.gbm.fullstack.service.LoginAttemptService;
import com.gbm.fullstack.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/v1/login")
@Api(tags={ "Authentication" }, description = "Authentication Operation")
public class LoginController {

	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginAttemptService loginAttemptService;
	
	/**
	 * 
	 * @param loginDTO
	 * @param response
	 * @return
	 */
	@ApiOperation(
			value = "Authenticates users", 
			//notes = "For valid response try integer IDs with value < 1000. Anything above 1000 or nonintegers will generate API errors",
			consumes = "application/json", produces = "application/json",
			response = GBMApiResponse.class
	)
	@ApiResponses(
		value = { 
				@ApiResponse(
						code = ErrorCode.HttpStatus.ERROR_NOT_FOUND, 
						message = ErrorCode.Message.ERROR_NOT_FOUND, 
						response = ApiError.class
				)
		}
	)
	@RequestMapping (method = RequestMethod.POST)
	public ResponseEntity<?> login(
			@ApiParam(name="User", value = "User to authenticate.", required=true )
			@RequestBody LoginDTO loginDTO,  
			HttpServletResponse response) {
		
		log.info("In log()...");

		userService.addObserver(loginAttemptService);
		User login = userService.getUserByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		userService.removeObserver(loginAttemptService);
		
		if (login == null) {
			return new ResponseEntity<>(
					new GBMApiResponse<LoginDTO>(Status.ERROR, null, new ApiError(ErrorCode.ERROR_NOT_FOUND), "login"),
					ErrorCode.getHttpStatus(ErrorCode.ERROR_NOT_FOUND)
			);
		}
		
		JWTToken token = this.generateToken(this.getUserFromLoginEntity(login), true, response);
		log.trace("Leaving auth()...");
		return new ResponseEntity<>(new GBMApiResponse<>(Status.OK, token, "login"), HttpStatus.OK);
	}
	
	/**
	 * @param user
	 * @param bRedirectURL
	 * @param response
	 * @return
	 */
	private JWTToken generateToken(AccountCredentials user, boolean bRedirectURL, HttpServletResponse response){
		log.info("In generateToken()...");
		JWTToken JWToken = null;
		
		UserAuthentication authentication = new UserAuthentication(user, tokenProvider);
		SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.addAuthentication(response, authentication);
        JWToken = new JWTToken(jwt);
        
        return JWToken;
	}
	

	/**
	 * 
	 * @param login
	 * @return
	 */
	private AccountCredentials getUserFromLoginEntity(User login) {
		AccountCredentials user = new AccountCredentials();
		user.setEmail(login.getEmail());
		user.setPassword(login.getPassword());
		Collection<Role> roles = login.getRoles();
		for (Role role: roles) {
			user.grantRole(role.getName());
		}
		
		return user;
	}



}

package com.gbm.fullstack.dto.error;

import java.util.HashMap;
import java.util.Map;



public enum ErrorCode {
	ERROR_FIELDS ,
	ERROR_NOT_FOUND,
	ERROR_JWT_EXCEPTION,
	ERROR_JWT_EXPIRED,
	ERROR_USER_NOT_FOUND,
    ERROR_USER_NOT_ACTIVATED,
    ERROR_EMAIL_IN_USE,
    ERROR_PASSWORD_REQUIRED,
	ERROR_PASSWORD_SIZE,
    ERROR_EMAIL_NOT_REGISTER,
	ERROR_DATA_EXIST,
	ERROR_USER_ALREADY_LOGIN,
	ERROR_AUTHENTICATION_REQUIRED,
	ERROR_BAD_EMAIL_PASSWORD;

    public class Code{
		public static final String ERROR_FIELDS             = "GBM-4000";
		public static final String ERROR_NOT_FOUND          = "GBM-4001";
		public static final String ERROR_JWT_EXCEPTION      = "GBM-4002";
		public static final String ERROR_JWT_EXPIRED        = "GBM-4003";
		public static final String ERROR_USER_NOT_FOUND     = "GBM-4004";
        public static final String ERROR_USER_NOT_ACTIVATED = "GBM-4005";
        public static final String ERROR_EMAIL_IN_USE       = "GBM-4006";
        public static final String ERROR_PASSWORD_REQUIRED  = "GBM-4007";
		public static final String ERROR_PASSWORD_SIZE      = "GBM-4008";
        public static final String ERROR_EMAIL_NOT_REGISTER = "GBM-4009";
		public static final String ERROR_DATA_EXIST         = "GBM-4010";
		public static final String ERROR_USER_ALREADY_LOGIN = "GBM-4011";
		public static final String ERROR_AUTHENTICATION_REQUIRED = "GBM-4013";
		public static final String ERROR_BAD_EMAIL_PASSWORD = "GBM-4014";
	}
	
	public class Message{
		public static final String ERROR_FIELDS               = "Required field(s) missing.";
		public static final String ERROR_NOT_FOUND            = "Item was not found";
		public static final String ERROR_JWT_EXCEPTION        = "Unhandled JWT Exception";
		public static final String ERROR_JWT_EXPIRED          = "JWT Expired";
		public static final String ERROR_USER_NOT_FOUND       = "User email was not found";
        public static final String ERROR_USER_NOT_ACTIVATED   = "User was not activated";
        public static final String ERROR_EMAIL_IN_USE         = "Email already in use, please choose another email address";
        public static final String ERROR_PASSWORD_REQUIRED    = "Password is required";
        public static final String ERROR_PASSWORD_SIZE        = "Password does not meet complexity requirements";
        public static final String ERROR_EMAIL_NOT_REGISTER   = "Email address not registered";
		public static final String ERROR_DATA_EXIST  	      = "Data exits!";
		public static final String ERROR_USER_ALREADY_LOGIN   = "User already logged in";
		public static final String ERROR_AUTHENTICATION_REQUIRED = "Resource Requires User Authentication";
		public static final String ERROR_BAD_EMAIL_PASSWORD   = "Oops, we didn't recognize that email or password.";
		
	}
	
	public class HttpStatus{
		public static final int ERROR_FIELDS             = CommonStatusCodes.BAD_REQUEST;
		public static final int ERROR_NOT_FOUND          = CommonStatusCodes.NOT_FOUND;
		public static final int ERROR_JWT_EXCEPTION	     = CommonStatusCodes.UNAUTHORIZED;
		public static final int ERROR_JWT_EXPIRED	     = CommonStatusCodes.UNAUTHORIZED;
		public static final int ERROR_USER_NOT_FOUND     = CommonStatusCodes.UNAUTHORIZED;
        public static final int ERROR_USER_NOT_ACTIVATED = CommonStatusCodes.UNAUTHORIZED;
        public static final int ERROR_EMAIL_IN_USE       = CommonStatusCodes.BAD_REQUEST;
        public static final int ERROR_PASSWORD_REQUIRED  = CommonStatusCodes.NOT_ACCEPTABLE;
        public static final int ERROR_PASSWORD_SIZE      = CommonStatusCodes.NOT_ACCEPTABLE;
        public static final int ERROR_EMAIL_NOT_REGISTER = CommonStatusCodes.NOT_ACCEPTABLE;
		public static final int ERROR_DATA_EXIST         = CommonStatusCodes.NOT_ACCEPTABLE;
		public static final int ERROR_USER_ALREADY_LOGIN = CommonStatusCodes.UNAUTHORIZED;
		public static final int ERROR_AUTHENTICATION_REQUIRED = CommonStatusCodes.UNAUTHORIZED;
		public static final int ERROR_BAD_EMAIL_PASSWORD      = CommonStatusCodes.UNAUTHORIZED;
	}
	
	class CommonStatusCodes{
		public static final int BAD_REQUEST =				400;
		public static final int UNAUTHORIZED =				401;
		public static final int PAYMENT_REQUIRED =			402;
		public static final int FORBIDDEN =					403;
		public static final int NOT_FOUND =					404;
		public static final int METHOD_NOT_ALLOWED =		405;
		public static final int NOT_ACCEPTABLE =			406;
		public static final int PROXY_AUTHENTICATION_REQUIRED =	407;
		public static final int REQUEST_TIMEOUT =				408;
		public static final int CONFLICT =						409;
		public static final int GONE =							410;
		public static final int LENGTH_REQUIRED =				411;
		public static final int PRECONDITION_FAILED =			412;
		public static final int PAYLOAD_TOO_LARGE =				413;
		public static final int URI_TOO_LONG =					414;
		public static final int UNSUPPORTED_MEDIA_TYPE =		415;
		public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 	416;
		public static final int EXPECTATION_FAILED =				417;
		public static final int I_AM_A_TEAPOT =						418;
		public static final int UNPROCESSABLE_ENTITY =				422;
		public static final int LOCKED =							423;
		public static final int FAILED_DEPENDENCY =					424;
		public static final int UPGRADE_REQUIRED =					426;
		public static final int PRECONDITION_REQUIRED =				428;
		public static final int TOO_MANY_REQUESTS =					429;
		public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 	431;
	}
	
	
	public static final Map<ErrorCode, GBMError> errorMap = new HashMap<ErrorCode, GBMError>();
	
	
	private String makeCompoundKey(String errorCode, String errorDescription) {
		  return errorCode + "|" + errorDescription;
	}
	
	
	
	static{
		ErrorCode[] codes = ErrorCode.class.getEnumConstants();
		for(ErrorCode e : codes)
		{
			String message = null;
			try {
				message = (String)Message.class.getField(e.name()).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String code = null;
			try {
				code = (String)Code.class.getField(e.name()).get(null);
			} 
			catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Integer status = null;
			
			try {
				status = (int)HttpStatus.class.getField(e.name()).get(null);
			} 
			catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if( message == null || code == null || status == null) {
				throw new RuntimeException("ERROR CODES NOT CORRECT! MUST FIX NOW!!");
			}
			errorMap.put(e, new GBMError( code, message, status )  ); 
		}
	}
	
	
	
	/**
	 * Returns the error code message for a errorCode
     *
     * @param errorCode
	 * @return String
	 */
	public static String getErrorMsg(ErrorCode errorCode) {
		return errorMap.get(errorCode).message;
	}
	
	public static String getErrorCode(ErrorCode errorCode) {
		return errorMap.get(errorCode).code;
	}
	
	public static int getStatusCode(ErrorCode errorCode) {
		return errorMap.get(errorCode).httpStatus;
	}
	
	public static org.springframework.http.HttpStatus 
						getHttpStatus(ErrorCode errorCode) {
		return org.springframework.http.HttpStatus.valueOf( 
				errorMap.get(errorCode).httpStatus );
	}
}

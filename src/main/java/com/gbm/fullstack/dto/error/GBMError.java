package com.gbm.fullstack.dto.error;

/**
 * @author Alfredo Ferreira
 * @version 1.0 (current version number of application)
 * @since 1.0   
 */
public class GBMError {
	public String code;
	public String message;
	public int httpStatus;
	
	GBMError( String c, String m, int h) {
		code = c;
		message = m;
		httpStatus = h;
	}

}

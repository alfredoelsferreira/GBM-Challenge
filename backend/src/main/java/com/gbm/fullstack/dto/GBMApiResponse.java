package com.gbm.fullstack.dto;

import com.gbm.fullstack.dto.error.ErrorCode;

/**
 * @author Alfredo Ferreira
 * @version 1.0 (current version number of application)
 * @since 1.0   (the version of the package this class was first added to)
 *
 */
public class GBMApiResponse <T>{
	public static enum Status {
		OK,
		ERROR
	}
	
	public static final class ApiError {

		/**
		 * Custom error code (GBM-4001)
		 */
		private final String errorCode;
		
		
		/**
		 * Description of the error
		 */
		private final String description;

		/**
		 * @param errorCode
		 * @param description
		 */
		public ApiError(String errorCode, String description) {
			this.errorCode = errorCode;
			this.description = description;
		}
		
		/**
		 * @param errorCode
		 */
		public ApiError(ErrorCode errorCode) {
			this.errorCode = ErrorCode.getErrorCode(errorCode);
			this.description = ErrorCode.getErrorMsg(errorCode);
		}

		/**
		 * @return
		 */
		public String getErrorCode() {
			return errorCode;
		}

		/**
		 * @return
		 */
		public String getDescription() {
			return description;
		}

	}
	
	private final Status status;
	private final T data;
	private final ApiError error;
	private final String token;
	private final String type;

	/**
     *
	 * @param status
	 * @param data
	 */
	public GBMApiResponse(Status status, T data, String type) {
		this(status, data, null, type);
	}

	/**
     * 
     *
	 * @param status
	 * @param data
	 * @param error
	 */
	public GBMApiResponse(Status status, T data, ApiError error, String type) {
		this.status = status;
		this.data   = data;
		this.error  = error;
		this.type   = type;
		this.token = null;
	}

	/**
	 * @return
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @return
	 */
	public ApiError getError() {
		return error;
	}

	/**
	 * @return
	 */
	public String getToken() {
		return token;
	}

    public String getType() {
        return type;
    }
}

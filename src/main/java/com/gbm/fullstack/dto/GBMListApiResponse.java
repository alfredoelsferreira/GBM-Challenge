package com.gbm.fullstack.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.gbm.fullstack.dto.GBMApiResponse.ApiError;
import com.gbm.fullstack.dto.GBMApiResponse.Status;

/**
 * @author Alfredo Ferreira
 * @version 1.0 (current version number of application)
 * @since 1.0   
 */
public class GBMListApiResponse <T> {
	private final Status status;
	private final java.util.List<T> data;
	private final ApiError error;
	private final long totalElements;
	private final int totalPages;
	private final boolean last;
	private final int size;
	private final int number;
	private final int numberOfElements;
	private final boolean first ;
	private final String type;



	/**
	 * Constructor to return a list of data, that doesn't really have paging
	 * 
	 * @param status
	 * @param data
	 */
	public GBMListApiResponse ( Status status, List<T> data, String type ) {
		this.status = status;
		this.data = data;
		this.error = null;
		this.totalElements = data.size();
		this.totalPages = 1;
		this.last = true;
		this.size = data.size();
		this.number = 0;
		this.numberOfElements = data.size();
		this.first = true;
		this.type = type;
	}


	/**
	 * @param status
	 * @param data
	 * @param error
	 * @param page
	 */
	public GBMListApiResponse(Status status, List<T> data, ApiError error, Page<T> page, String type) {
		this.status = status;
		this.data = data;
		this.error = error;
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.last = page.isLast();
		this.size = page.getSize();
		this.number = page.getNumber();
		this.numberOfElements = page.getNumberOfElements();
		this.first = page.isFirst();
		this.type = type;
	}



	/**
	 * @param status
	 * @param page
	 * @param error
	 */
	public GBMListApiResponse(Status status, Page<T> page, ApiError error, String type) {
		this.status     = status;
		this.type       = type;
		if (null == error) {
			java.util.List<T> tlist = page.getContent();
			this.data             = tlist;
			this.error            = error;
			this.totalElements    = page.getTotalElements();
			this.totalPages       = page.getTotalPages();
			this.last             = page.isLast();
			this.size             = page.getSize();
			this.number           = page.getNumber();
			this.numberOfElements = page.getNumberOfElements();
			this.first            = page.isFirst();
		}
		else {
			this.data             = null;
			this.error            = error;
			this.totalElements    = 0;
			this.totalPages       = 0;
			this.last             = false;
			this.size             = 0;
			this.number           = 0;
			this.numberOfElements = 0;
			this.first            = false;
		}
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
	public List<T> getData() {
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
	public long getTotalElements() {
		return totalElements;
	}

	/**
	 * @return
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @return
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return
	 */
	public int getNumberOfElements() {
		return numberOfElements;
	}

	/**
	 * @return
	 */
	public boolean isFirst() {
		return first;
	}


	public String getType() {
		return type;
	}

}

package com.forgot.response;

import org.springframework.http.HttpStatus;


public class APIResponse<T> {

    private Boolean status;
    private Integer success;
    private Object data;
    private Object error;

    public APIResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public APIResponse(Boolean status, Integer success, Object data, Object error) {
        this.success = success;
        this.status = status;
        this.data = data;
        this.error = error;
    }

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}


} 

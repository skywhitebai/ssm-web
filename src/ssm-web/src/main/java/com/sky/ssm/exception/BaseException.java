package com.sky.ssm.exception;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 异常码
	 */
	private String code;
	
	public BaseException(String code, String msg) {
		super(msg);
		this.code = code;
	}
	
	public BaseException(String msg) {
		super(msg);
	}
	
	public BaseException(Throwable throwable) {
		super(throwable);
	}
	
	public BaseException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

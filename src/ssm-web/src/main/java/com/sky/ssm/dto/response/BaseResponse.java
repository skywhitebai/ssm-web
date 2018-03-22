package com.sky.ssm.dto.response;

import java.io.Serializable;

import com.sky.ssm.constant.ResponseCodeConstant;

import net.sf.json.JSONObject;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 200表示成功，其他自定义编码
	 */
	private String code;

	/**
	 * 信息
	 */
	private String message;

	/**
	 * 详细信息
	 */
	private String detailMessage;

	/**
	 * 响应其他数据
	 */
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseResponse [" + JSONObject.fromObject(this) + "]";
	}

	public static BaseResponse success() {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ResponseCodeConstant.SUCCESS_CODE);
		baseResponse.setMessage(ResponseCodeConstant.msgMap
				.get(ResponseCodeConstant.SUCCESS_CODE));
		return baseResponse;
	}

	public static BaseResponse success(String message, Object data) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ResponseCodeConstant.SUCCESS_CODE);
		baseResponse.setMessage(message);
		baseResponse.setData(data);
		return baseResponse;
	}

	public static BaseResponse successMessage(String message) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ResponseCodeConstant.SUCCESS_CODE);
		baseResponse.setMessage(message);
		return baseResponse;
	}

	public static BaseResponse successData(Object data) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ResponseCodeConstant.SUCCESS_CODE);
		baseResponse.setMessage(ResponseCodeConstant.msgMap
				.get(ResponseCodeConstant.SUCCESS_CODE));
		baseResponse.setData(data);
		return baseResponse;
	}

	public static BaseResponse fail() {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ResponseCodeConstant.FAIL_CODE);
		baseResponse.setMessage(ResponseCodeConstant.msgMap
				.get(ResponseCodeConstant.FAIL_CODE));
		return baseResponse;
	}

	public static BaseResponse failMessage(String message) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ResponseCodeConstant.FAIL_CODE);
		baseResponse.setMessage(message);
		return baseResponse;
	}

	public static BaseResponse fail(String code, String message) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(code);
		baseResponse.setMessage(message);
		return baseResponse;
	}
}

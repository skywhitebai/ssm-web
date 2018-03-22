package com.sky.ssm.constant;

public enum LoginExceptionEnum {
	LOGIN_PARAMS_USERNAME_EMPTY(ResponseCodeConstant.PARAMETER_EMPETY_CODE, "用户名不能为空"),
	LOGIN_PARAMS_PASSWORD_EMPTY(ResponseCodeConstant.PARAMETER_EMPETY_CODE, "密码不能为空"),
	LOGIN_ACCOUNT_ERRO("LOGIN_ACCOUNT_ERRO", "用户名或密码错误"),
	LOGIN_DISABLE("401", "登录超时,请重新登录");
	
	private LoginExceptionEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	private String code;
	
	private String label;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}

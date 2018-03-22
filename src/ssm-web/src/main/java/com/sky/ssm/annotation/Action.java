package com.sky.ssm.annotation;

public enum Action {
	/** 正常（默认） */
	Normal("0", "执行登录验证"),
	
	/** 跳过 */
	Skip("1", "跳过登录验证");

	/** 主键 */
	private final String key;

	/** 描述 */
	private final String desc;

	Action(final String key, final String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return this.key;
	}

	public String getDesc() {
		return this.desc;
	}

}
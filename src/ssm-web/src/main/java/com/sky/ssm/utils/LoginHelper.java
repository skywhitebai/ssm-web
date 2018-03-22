package com.sky.ssm.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.ssm.constant.BaseConstant;
import com.sky.ssm.interceptor.LoginInterceptor;
import com.sky.ssm.model.User;

public class LoginHelper {
	private static final Logger logger =LoggerFactory.getLogger(LoginInterceptor.class);

	public static User getCurrentUserInfo(HttpServletRequest request) {
		HttpSession httpSession=request.getSession();
		Object object= httpSession.getAttribute(BaseConstant.LOGIN_NAME);
		if(object==null){
			return null;
		}
		try{
			return (User)object;
		}
		catch(Exception  ex){
			ex.printStackTrace();
			logger.error("获取session错误,{}",ex);
			return null;
		}
	}

}

package com.sky.ssm.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sky.ssm.annotation.Action;
import com.sky.ssm.annotation.Login;
import com.sky.ssm.constant.LoginExceptionEnum;
import com.sky.ssm.dto.response.BaseResponse;
import com.sky.ssm.model.User;
import com.sky.ssm.utils.HttpUtil;
import com.sky.ssm.utils.LoginHelper;

/**
 * 登录拦截器，做登录校验
 * @author Administrator
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger =LoggerFactory.getLogger(LoginInterceptor.class);
	/**
	 * 登录权限验证
	 * <p>
	 * 方法拦截 Controller 处理之前进行调用。
	 * </p>
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * 处理 Controller 方法
		 * <p>
		 * 判断 handler 是否为 HandlerMethod 实例
		 * </p>
		 */
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Login login = method.getAnnotation(Login.class);
			if (login != null) {
				if (login.action() == Action.Skip) {
					/**
					 * 忽略拦截
					 */
					return true;
				}
			}

			/**
			 * 正常执行 判断是否登录
			 */
			User currentUserInfo=LoginHelper.getCurrentUserInfo(request);
			if(currentUserInfo==null){
				if ( HttpUtil.isAjax(request) ) {
					getUnLoginReponse(response);
				}
				else{
					//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
					String contextPath= request.getContextPath();
					if(StringUtils.isEmpty(contextPath)){
						response.sendRedirect("/account/login");
					}
					else{
						response.sendRedirect(contextPath+"/account/login");
					}
				}
				return false;
			}
			return true;
		}

		/**
		 * 通过拦截
		 */
		return true;
	}
	
	private void getUnLoginReponse(HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		response.setStatus(401);
		PrintWriter out = response.getWriter();
		//BaseException baseException
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setCode(LoginExceptionEnum.LOGIN_DISABLE.getCode());
		baseResponse.setMessage(LoginExceptionEnum.LOGIN_DISABLE.getLabel());
		out.print(JSONObject.fromObject(baseResponse));
		out.flush();
	}

}

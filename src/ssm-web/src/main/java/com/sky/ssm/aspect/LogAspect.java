package com.sky.ssm.aspect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import com.sky.ssm.annotation.Log;
import com.sky.ssm.constant.ResponseCodeConstant;
import com.sky.ssm.dto.response.BaseResponse;
import com.sky.ssm.exception.BaseException;
import com.sky.ssm.model.User;
import com.sky.ssm.utils.DateUtil;
import com.sky.ssm.utils.HttpUtil;
import com.sky.ssm.utils.LoginHelper;

/**
 * 暂时不使用
 * @author Administrator
 *
 */
public class LogAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 * @Description: 方法调用前触发 记录开始时间
	 * @author fei.lei
	 * @date 2016年11月23日 下午5:10
	 * @param joinPoint
	 */
	public void before(JoinPoint joinPoint) {
		
	}

	/**
	 * 
	 * @Description: 方法调用后触发 记录结束时间
	 * @author fei.lei
	 * @date 2016年11月23日 下午5:10
	 * @param joinPoint
	 */
	public void after(JoinPoint joinPoint) {
		
	}

	/**
	 * @Description: 获取request
	 * @author fei.lei
	 * @date 2016年11月23日 下午5:10
	 * @param
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getHttpServletRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}

	/**
	 * 
	 * @Title：around
	 * @Description: 环绕触发
	 * @author fei.lei
	 * @date 2016年11月23日 下午5:10
	 * @param joinPoint
	 * @return Object
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Date startTime; // 开始时间
		Date endTime; // 结束时间
		User user = null;
		HttpServletRequest request = null;
		String operName = "";
		startTime = new Date(); // 记录方法开始执行的时间
		request = getHttpServletRequest();
		user = LoginHelper.getCurrentUserInfo(request);
		// 获取class和method
		Signature signature = joinPoint.getSignature();
		Method method = ((MethodSignature) signature).getMethod();
		Log log = method.getAnnotation(Log.class);
		if (log != null) {
			operName = log.value();
		}

		StringBuilder sb = new StringBuilder();

		sb.append("-----------------------")
				.append(DateUtil.getFormatSSS(startTime))
				.append("-------------------------------------\n");
		sb.append("json     : ").append(JSONArray.fromObject(joinPoint.getArgs()))
				.append("\n");
		sb.append("Controller: ").append(joinPoint.getTarget().getClass().getName())
				.append("\n");
		sb.append("Method    : ").append(method.getName()).append("\n");
		sb.append("Params    : ")
				.append(JSONObject.fromObject(request.getParameterMap()))
				.append("\n");
		sb.append("URI       : ").append(request.getRequestURI()).append("\n");
		sb.append("IP        : ").append(HttpUtil.getIp(request)).append("\n");
		if (user != null) {
			sb.append("DealUser  : ").append("userId:")
					.append(user.getUserId()).append(",userName:")
					.append(user.getUserName()).append("\n");
		}
		if (!StringUtils.isEmpty(operName)) {
			sb.append("operName  : ").append(operName).append("\n");
		}
		logger.info(sb.toString());
		Object result = joinPoint.proceed();
		// try {
		// //result = joinPoint.proceed();
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// logger.error("异常:{}", ex.getMessage(), ex);
		// BaseResponse baseResponse;
		// if (ex instanceof BaseException) {
		// baseResponse = BaseResponse.success(
		// ((BaseException) ex).getCode(), ex.getMessage());
		// } else {
		// baseResponse = BaseResponse.successMessage("系统异常，请稍后重试或联系客服");
		// }
		// result = JSONObject.fromObject(baseResponse).toString();
		// }
		endTime = new Date();
		StringBuilder sbAfter = new StringBuilder();
		sbAfter.append("CostTime  : ")
				.append(endTime.getTime() - startTime.getTime()).append("ms")
				.append("\n");
		if (result != null && result instanceof BaseResponse) {
			sbAfter.append("Response  : ")
					.append(JSONObject.fromObject(result)).append("\n");

		}
		sbAfter.append("-----------------------")
				.append(DateUtil.getFormatSSS(endTime))
				.append("-------------------------------------\n");

		logger.info(sbAfter.toString());
		return result;
	}

	/**
	 * 
	 * @Title：printOptLog
	 * @Description: 输出日志
	 * @author fei.lei
	 * @date 2016年11月23日 下午5:10
	 */
	/*
	 * private void printOptLog() { Gson gson = new Gson(); //
	 * 需要用到google的gson解析包 String startTime = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis); String
	 * endTime = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTimeMillis);
	 * logger.info("user :" +user.getName()+ " start_time: " + startTime
	 * +" end_time: "+endTime); }
	 */

}

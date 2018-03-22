package com.sky.ssm.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.sky.ssm.constant.ResponseCodeConstant;

public class DefaultExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		/* 使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常 */
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>();
		if (ex instanceof BaseException) {
			attributes.put("code", ((BaseException) ex).getCode());
			attributes.put("message", ex.getMessage());
		} else {
			attributes.put("code", ResponseCodeConstant.FAIL_CODE);
			attributes.put("message", "系统异常，请稍后重试或联系客服");
		}
		attributes.put("detailMessage", null);
		attributes.put("data", null);
		view.setAttributesMap(attributes);
		mv.setView(view);
		logger.error("异常:{}",ex.getMessage(), ex);
		return mv;
	}
}

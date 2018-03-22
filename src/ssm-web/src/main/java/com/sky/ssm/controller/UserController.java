package com.sky.ssm.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.ssm.annotation.Action;
import com.sky.ssm.annotation.Login;
import com.sky.ssm.dto.response.BaseResponse;
import com.sky.ssm.exception.DefaultExceptionHandler;
import com.sky.ssm.model.User;

@Controller
@RequestMapping("user")
public class UserController extends SuperController {
	private  Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
	@RequestMapping("currentUserInfo")
	public String currentUserInfo(Model model){
		User user=getCurrentUserInfo();
		user.setPassword(null);
		model.addAttribute("user", user);
		return "User/CurrentUserInfo";
	}
	
	@RequestMapping("excepition")
	@Login(action = Action.Skip)
	@ResponseBody
	public BaseResponse excepition(Model model){
		logger.info("异常测试:{}",new Date());
		int a=0;
		int b=10/a;
		return BaseResponse.success();
	}
}

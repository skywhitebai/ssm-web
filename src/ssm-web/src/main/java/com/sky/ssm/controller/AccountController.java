package com.sky.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sky.ssm.annotation.Action;
import com.sky.ssm.annotation.Login;
import com.sky.ssm.constant.BaseConstant;
import com.sky.ssm.constant.LoginExceptionEnum;
import com.sky.ssm.dto.request.LoginRequest;
import com.sky.ssm.dto.response.BaseResponse;
import com.sky.ssm.model.User;
import com.sky.ssm.service.IUserService;

@Controller
@RequestMapping("account")
public class AccountController extends SuperController {

	@Autowired
	IUserService userService;

	@RequestMapping("login2")
	@Login(action = Action.Skip)
	public BaseResponse login2(LoginRequest params) {
		if (StringUtils.isEmpty(params.getUserName())) {
			return BaseResponse.fail(
					LoginExceptionEnum.LOGIN_PARAMS_USERNAME_EMPTY.getCode(),
					LoginExceptionEnum.LOGIN_PARAMS_USERNAME_EMPTY.getLabel());
		}
		if (StringUtils.isEmpty(params.getPassword())) {
			return BaseResponse.fail(
					LoginExceptionEnum.LOGIN_PARAMS_PASSWORD_EMPTY.getCode(),
					LoginExceptionEnum.LOGIN_PARAMS_PASSWORD_EMPTY.getLabel());
		}
		User user = userService.select(params.getUserName(),
				params.getPassword());
		if (user == null) {
			return BaseResponse.fail(
					LoginExceptionEnum.LOGIN_ACCOUNT_ERRO.getCode(),
					LoginExceptionEnum.LOGIN_ACCOUNT_ERRO.getLabel());
		}
		// 把用户数据保存在session域对象中
		session.setAttribute(BaseConstant.LOGIN_NAME, user);
		return BaseResponse.successMessage("登陆成功");
	}
	@RequestMapping("login")
	@Login(action = Action.Skip)
	public String login(LoginRequest params,Model model) {
		StringBuilder sbErro=new StringBuilder();
		
		if (StringUtils.isEmpty(params.getUserName())) {
			sbErro.append("用户名不能为空,");
		}
		
		if (StringUtils.isEmpty(params.getPassword())) {
			sbErro.append("密码不能为空,");
		}
		if(sbErro.length()>0){
			model.addAttribute("user", params);
			model.addAttribute("erroMessage", sbErro.substring(0, sbErro.length()-1));
			return "Account/Login";			
		}
		User user = userService.select(params.getUserName(),
				params.getPassword());
		if (user == null) {
			model.addAttribute("user", params);
			model.addAttribute("erroMessage", "用户名密码错误");
			return "Account/Login";		
		}		
		// 把用户数据保存在session域对象中
		session.setAttribute(BaseConstant.LOGIN_NAME, user);
		return "redirect:/user/currentUserInfo";	
	}

	@RequestMapping("login3")
	@ResponseBody
	@Login(action = Action.Skip)
	public BaseResponse login3(@RequestBody LoginRequest params) {
		StringBuilder sbErro=new StringBuilder();

		if (StringUtils.isEmpty(params.getUserName())) {
			sbErro.append("用户名不能为空,");
		}

		if (StringUtils.isEmpty(params.getPassword())) {
			sbErro.append("密码不能为空,");
		}
		if(sbErro.length()>0){
			return BaseResponse.failMessage(sbErro.substring(0, sbErro.length()-1));
		}
		User user = userService.select(params.getUserName(),
				params.getPassword());
		if (user == null) {
			return BaseResponse.failMessage("用户名密码错误");
		}
		// 把用户数据保存在session域对象中
		session.setAttribute(BaseConstant.LOGIN_NAME, user);
		return BaseResponse.successData(user);
	}
}

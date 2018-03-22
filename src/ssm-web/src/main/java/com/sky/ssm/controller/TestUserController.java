package com.sky.ssm.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.ssm.annotation.Action;
import com.sky.ssm.annotation.Log;
import com.sky.ssm.annotation.Login;
import com.sky.ssm.dto.response.BaseResponse;
import com.sky.ssm.model.TestUser;
import com.sky.ssm.service.ITestUserService;

@Controller
@RequestMapping("/testUser")
public class TestUserController {

	@Autowired
	ITestUserService testUserService;

	@RequestMapping("/getTestUser")
	public String getTestUser(Long userId, Model model) {
		if (userId != null) {
			TestUser testUser = testUserService.getUserById(userId);
			model.addAttribute("testUser", testUser);
		}
		return "TestUser/TestUserInfo";
	}
	@RequestMapping("/getTestUserInfo")
	@Login(action = Action.Skip)
	@Log("获取测试用户信息")
	@ResponseBody
	public BaseResponse getTestUserInfo(Long userId) {
		if (userId != null) {
			TestUser testUser = testUserService.getUserById(userId);
			return BaseResponse.successData(testUser);
		}
		return BaseResponse.failMessage("用户不存在");
	}
	@RequestMapping("/addTestUser")
	@Login(action = Action.Skip)
	@ResponseBody
	public String addTestUser() {
		TestUser testUser=new TestUser();
		testUser.setPassword("123");
		testUser.setStatus(1);
		testUser.setUserName("test1");
		testUser.setCreateTime(new Date());
		testUserService.addTestUser(testUser);
		return "success";
	}
}

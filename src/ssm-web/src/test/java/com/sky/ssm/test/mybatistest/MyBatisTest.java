package com.sky.ssm.test.mybatistest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.sky.ssm.model.TestUser;
import com.sky.ssm.service.ITestUserService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class MyBatisTest {
	private static Logger logger = LoggerFactory.getLogger(MyBatisTest.class);
	@Resource
	ITestUserService testUserService;

	@Test
	public void test() {
		TestUser testUser = testUserService.getUserById(1L);
		logger.info(JSONObject.toJSONString(testUser));
	}
}

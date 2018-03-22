package com.sky.ssm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.ssm.dao.TestUserMapper;
import com.sky.ssm.model.TestUser;
import com.sky.ssm.service.ITestUserService;

@Service
public class TestUserService implements ITestUserService {

	@Resource
	TestUserMapper testUserMapper;
	public TestUser getUserById(Long userId) {		
		return testUserMapper.selectByPrimaryKey(userId);
	}
	@Transactional
	public void addTestUser(TestUser testUser) {
		testUserMapper.insert(testUser);
		testUserMapper.insert(testUser);
		throw new RuntimeException( "事务测试");
			
	}

}

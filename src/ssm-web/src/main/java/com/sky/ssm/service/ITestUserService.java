package com.sky.ssm.service;

import com.sky.ssm.model.TestUser;

public interface ITestUserService {

	TestUser getUserById(Long userId);
	void addTestUser(TestUser testUser);

}

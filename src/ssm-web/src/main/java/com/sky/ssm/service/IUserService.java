package com.sky.ssm.service;

import com.sky.ssm.model.User;

public interface IUserService {

	User select(String userName, String password);

}

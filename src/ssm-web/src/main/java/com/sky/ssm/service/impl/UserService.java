package com.sky.ssm.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sky.ssm.dao.UserMapper;
import com.sky.ssm.model.User;
import com.sky.ssm.model.UserExample;
import com.sky.ssm.service.IUserService;
@Service
public class UserService implements IUserService {
	Logger Logger=LoggerFactory.getLogger(UserService.class);
	@Autowired
	UserMapper userMapper;
	public User select(String userName, String password) {	
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		UserExample userExample=new UserExample();
		userExample.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(password);
		List<User> list=userMapper.selectByExample(userExample);
		if(list.size()>0){
			if(list.size()>1){
				Logger.error("通过用户账号和密码查询数据，单行查询返回多行数据，参数：userName:{},password:{}",userName,password);
			}
			return list.get(0);
		}
		return null;
	}

}

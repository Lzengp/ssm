package cn.hnust.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hnust.mapper.UserMapper;
import cn.hnust.po.User;
import cn.hnust.service.UserService;

@Service("userService")  
public class UserServiceImpl implements UserService {
	

	    @Resource  
	    private UserMapper userMapper;  
	    
	
		@Override
		public void insertUser(User user) {
			userMapper.insertUser(user);
		}  

		@Override
		public User findByUserName(String username) {
			
			return userMapper.findByUserName(username);
		} 
		
		@Override
		public User findUserByEmail(String email) {
			
			return userMapper.findUserByEmail(email);
		} 
		@Override
		public  void updateActivated(User user) {
			 userMapper.updateActivated(user);
		} 
		@Override
		public User findUserById(int idInt) {
			return userMapper.findUserById(idInt);
			
		}
}

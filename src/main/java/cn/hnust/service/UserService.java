package cn.hnust.service;

import cn.hnust.po.User;

public interface UserService {
	
	public void insertUser(User user);

	public User findByUserName(String username);
	
	public User findUserByEmail(String email);

	public User findUserById(int idInt);

	public void updateActivated(User user);

}

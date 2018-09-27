package cn.hnust.mapper;


import cn.hnust.po.User;

public interface UserMapper {
	
	//注册
	public void  insertUser(User user);

	//通过名字查询
	public User findByUserName(String username);

	//查询邮箱是否存在
	public User findUserByEmail(String email);
	
	//根据id查询
	public User findUserById(int id);
	
	//更新状态码
	public void updateActivated(User user);
 
}
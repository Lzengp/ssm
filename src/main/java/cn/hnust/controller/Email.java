package cn.hnust.controller;


import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hnust.po.User;
import cn.hnust.service.UserService;
import cn.hnust.util.EmailUtils;
import cn.hnust.util.GenerateLinkUtils;

 
@Controller
@RequestMapping("/email")
public class Email {
	
	@Resource
	private UserService userService;
	
 
	@RequestMapping("/toregist")
	public String toemail() {
		return "regist";
	}
	
	@RequestMapping("/regist")
	public void regist(User user,HttpSession httpSession,HttpServletResponse response) {
		
		user.setActivated(false);  //刚注册默认是没有激活状态
		user.setCodeUrl(UUID.randomUUID().toString());
		
		//注册用户
		if(userService.findUserByEmail(user.getEmail()) == null) {
			
			userService.insertUser(user);
			//userService.saveUser(user);
		} else {
			throw new RuntimeException("该邮箱已注册");
			
		}
		
		//查看是否注册成功，为实体类User的id赋值
		User findUser = userService.findUserByEmail(user.getEmail());
		
		if(findUser != null) {
			user.setId(findUser.getId());
		} else {
			throw new RuntimeException("注册用户失败");
		}
		
		//注册成功后，发送账户激活链接
		httpSession.setAttribute("user", user);
		EmailUtils.sendAccountActivateEmail(user);
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("激活邮件已经发送，请注意提醒查收");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/activate")
	public void activate(String id,String checkCode,HttpServletResponse response) {
		
		int idInt = Integer.parseInt(id);
		
		//根据用户id查找用户
		User user = userService.findUserById(idInt);
		
		//验证无误，状态更改为1，即激活
		if(GenerateLinkUtils.verifyCheckcode(user,checkCode)) {
			
			//修改状态
			user.setActivated(true);
			user.setId(idInt);
			userService.updateActivated(user);
			user.setActivated(true);
			try {
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write("恭喜，激活成功！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
}

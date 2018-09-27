package cn.hnust.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.hnust.po.User;
import cn.hnust.service.UserService;

 
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/registPage")
	public String registPage() {
		return "regist";
	}
	@RequestMapping("/regist")
	public String regist(User user,HttpSession httpSession,HttpServletResponse response) {
		userService.insertUser(user);	
		return "index";
	}
	
	@RequestMapping("/success")
	public String success(@RequestParam("username") String username,
			@RequestParam("password") String password,HttpServletResponse response) throws IOException {
		User user = userService.findByUserName(username);
		String name = user.getUsername();
		String pwd = user.getPassword();
		System.out.println(name+pwd);
		 if(username.equals(name) || password.equals(pwd)){
			return "success";
		}else {	
			return "index";
		}
	}
	
}


package com.intell.lesson.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户注册的Controller.
 * 
 * @author yr
 */
@Controller
@RequestMapping(value = "/signup")
public class RegisterController {



	@RequestMapping(method = RequestMethod.GET)
	public String register(RedirectAttributes redirectAttributes) {
		return "signup";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "/registor",method = RequestMethod.POST)
	public String checkLoginName(@RequestParam("username") String username) {
		return "redirect:/login";
	}
}

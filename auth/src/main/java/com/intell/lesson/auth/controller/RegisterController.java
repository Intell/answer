package com.intell.lesson.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户注册的Controller.
 * 
 * @author yr
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {



	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
	public String register(HttpServletRequest request, RedirectAttributes redirectAttributes) {

		return "redirect:/login";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("username") String username) {
		return "";
	}
}

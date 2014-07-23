package com.intell.lesson.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户修改自己资料的Controller.
 * 
 * @author yr
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {



	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
	public String updateForm(Model model) {
		return "account/profile";
	}

	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
	public String update(@RequestParam String userName,@RequestParam String password) {
        Map<String,Object> user = new HashMap<String,Object>();
        user.put("userName",userName);
        user.put("plainPassword",password);
		return "redirect:/";
	}



}

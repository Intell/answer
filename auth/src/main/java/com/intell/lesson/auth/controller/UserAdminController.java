package com.intell.lesson.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员管理用户的Controller.
 * 
 * @author yr
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserAdminController {


	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {


		return "adminUserList";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {

		return "account/adminUserForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, RedirectAttributes redirectAttributes) {

		return "redirect:/admin/user";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        return "redirect:/admin/user";
	}


}

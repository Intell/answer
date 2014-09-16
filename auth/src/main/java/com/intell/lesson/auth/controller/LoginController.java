package com.intell.lesson.auth.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * <p>
 * 真正登录的POST请求由Filter完成,
 *
 * @author yr
 */
@Controller
@RequestMapping
public class LoginController {

    private  final static String loginPage = "login";

    private  final static String homePage = "home";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return homePage;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            return loginPage;
        }
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Model fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        return model;
    }

}

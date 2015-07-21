package com.brave.resupply.controller;

import com.brave.resupply.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dcohen on 2/13/15.
 */
@Controller
public class PageController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("loggedIn", isLoggedIn());
        if (isLoggedIn()) {
            return "index";
        } else {
            return "login";
        }
    }

    @RequestMapping("/new")
    public String newCharacter(Model model) {
        model.addAttribute("loggedIn", isLoggedIn());
        if (isLoggedIn()) {
            return "edit";
        } else {
            return "login";
        }
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("loggedIn", isLoggedIn());
        return "login";
    }
}

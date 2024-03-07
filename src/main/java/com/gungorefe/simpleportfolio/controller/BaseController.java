package com.gungorefe.simpleportfolio.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController implements ErrorController {
    @GetMapping("/{pageName:^about$|^contact$|^home$|^login$|^admin$}")
    public ModelAndView pages() {
        return new ModelAndView("forward:/", HttpStatus.SEE_OTHER);
    }

    @RequestMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("forward:/");
    }
}

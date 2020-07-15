package com.javaguru.onlineshop.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class MainPageController {

    @GetMapping("/")
    public ModelAndView getGreeting() {
        ModelAndView view = new ModelAndView();
        view.setViewName("greeting");
        return view;
    }

    @GetMapping("/main")
    public ModelAndView getMain(Principal principal){
        ModelAndView view = new ModelAndView();
        String name = principal.getName();
        view.addObject("username", name);
        view.setViewName("main");
        return view;
    }

    @PostMapping("/main")
    public ModelAndView postMain(@RequestParam(name="username") String name){
        ModelAndView view = new ModelAndView();
        view.addObject("username", name);
        view.setViewName("main");
        return view;
    }

}

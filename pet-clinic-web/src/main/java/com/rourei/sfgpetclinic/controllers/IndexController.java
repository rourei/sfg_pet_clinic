package com.rourei.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // mark as Spring managed component
public class IndexController {

    @RequestMapping({"", "/", "index", "index.html"}) // Return 'index' template if request to root context occurs
    public String index(){
        return "index"; // make Thymeleaf look for a template called 'index'
    }

    @RequestMapping("/oups") // Placeholder for simulating ru,time error later on
    public String oupsHandler(){
        return "notimplemented"; // make Thymeleaf look for a template called 'notimplemented'
    }
}

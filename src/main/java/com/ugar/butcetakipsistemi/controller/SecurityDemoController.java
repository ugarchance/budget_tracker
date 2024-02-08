package com.ugar.butcetakipsistemi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityDemoController {

    @GetMapping("/login")
    public String showHome(){
        return "homepage";
    }

    //add a request mapping for /leaders
    @GetMapping("/leaders")
    public String showLeaders(){
        return "leaders";
    }
    @GetMapping("/systems")
    public String showSystems(){
        return "systems";
    }

}

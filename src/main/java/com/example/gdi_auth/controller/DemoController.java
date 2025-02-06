package com.example.gdi_auth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello(){
        var u = SecurityContextHolder.getContext().getAuthentication();
        u.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));
        return "hello";
    }
}

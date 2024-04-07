package net.prateekdazz.springsecurityproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")

    public String displayHome()
    {
        return("welcome to home page!!!!!");
    }
}

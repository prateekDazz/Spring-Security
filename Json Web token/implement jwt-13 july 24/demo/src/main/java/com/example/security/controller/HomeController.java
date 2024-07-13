package com.example.security.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
   @GetMapping("/hello")
   @PreAuthorize("hasRole('ADMIN')")

    public String hello()
    {
        return "hello Good Morning";
    }
   @PreAuthorize("hasRole('USER')")
   @GetMapping("/user")
   public String user()
   {
	   return ("hello User");
   }
   @PreAuthorize("hasRole('ADMIN')")

   @GetMapping("/admin")
   public String admin()
   {
	   return ("hello admin!!!");
   }
}

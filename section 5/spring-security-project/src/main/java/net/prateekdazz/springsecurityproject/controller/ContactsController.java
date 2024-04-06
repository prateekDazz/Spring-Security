package net.prateekdazz.springsecurityproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {

    @GetMapping("/myContacts")
    public String getContactsDetails()
    {
        return("Here are the contacts details from the Db");
    }
}

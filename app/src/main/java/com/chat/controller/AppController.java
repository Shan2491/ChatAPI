package com.chat.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.Contact;
import com.chat.service.AppService;

@RestController
public class AppController {
	
	@Autowired
	private AppService appService;


    @RequestMapping("/")
    String hello() {
        return "Welcome to chat app";
    }
    
    @GetMapping("/contact/{userId}")
    public ArrayList<Contact> getContactList(@PathVariable("userId") int userId) {
    	return appService.contactList(userId);
    }
    
    @PostMapping("/users")
    public boolean createUser(@RequestBody Map<String, String> req) throws Exception {

    	return appService.createUser(req);
    }

}
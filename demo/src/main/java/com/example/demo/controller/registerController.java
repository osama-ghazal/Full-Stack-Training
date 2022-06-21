package com.example.demo.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.registered;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class registerController {
    @Autowired
    UserService regService;

    @RequestMapping(value="/regi", method=RequestMethod.POST)
    public registered createUser(@RequestBody registered user) {
        return regService.createUser(user);
    }

    @RequestMapping(value="/regi", method=RequestMethod.GET)
    public List<registered> readUsers() {
        return regService.getUsers();
    }
    @RequestMapping(value="/regi/{ID}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "ID") Long id) {
        regService.deleteUser(id);
    }
}
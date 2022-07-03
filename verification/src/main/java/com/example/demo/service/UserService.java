package com.example.demo.service;

import com.example.demo.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.registered;
import com.example.demo.repository.userRepositery;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    userRepositery regRepository;

    public registered createUser(registered user) {
        return regRepository.save(user);
    }

    // READ
    public List<registered> getUsers() {
        return regRepository.findAll();
    }

    // DELETE
    public void deleteUser(Long userID) {
        confirmationTokenRepository.deleteById(userID);
        regRepository.deleteById(userID);
    }
}
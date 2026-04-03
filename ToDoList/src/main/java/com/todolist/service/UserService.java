package com.todolist.service;

import com.todolist.dto.LoginRequest;
import com.todolist.dto.SignupRequest;
import com.todolist.entity.User;

public interface UserService {

    public User registerUser(SignupRequest request);
    String login(LoginRequest request);
}

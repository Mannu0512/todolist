package com.todolist.controller;

import com.todolist.dto.LoginRequest;
import com.todolist.dto.SignUpResponseDto;
import com.todolist.dto.SignupRequest;
import com.todolist.entity.User;
import com.todolist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@Valid @RequestBody SignupRequest request){

        User user = userService.registerUser(request);

        SignUpResponseDto response = new SignUpResponseDto(user.getId(), user.getUsername(), user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){

        String token = userService.login(request);

        return ResponseEntity.ok(token);
    }

}
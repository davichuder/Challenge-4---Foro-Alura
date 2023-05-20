package com.david.foro_alura.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.foro_alura.dto.login.LoginRequest;

@RestController
@RequestMapping("/login")
public class LoginController {
    // @PostMapping("")
    // public ResponseEntity<LoginRequest> login(){
    // }
}

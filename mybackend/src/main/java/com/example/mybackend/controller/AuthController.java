package com.example.mybackend.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybackend.dto.CreateUserRequestDTO;
import com.example.mybackend.dto.LoginRequestDTO;
import com.example.mybackend.dto.UserResponseDTO;
import com.example.mybackend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
@Tag(name="Auth", description="Login and register a user")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }
    
    @Operation(summary = "Register a user")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody CreateUserRequestDTO request) {
        UserResponseDTO user = userService.registerUser(request);
        URI location = URI.create("/auth/register/" + user.getId());
        return ResponseEntity.created(location).body(user);
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO request) {
        String token = userService.loginUser(request);
        return ResponseEntity.ok(Map.of("accessToken", token));
    }
}

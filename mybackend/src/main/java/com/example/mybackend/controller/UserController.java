package com.example.mybackend.controller;

import com.example.mybackend.dto.CreateUserRequestDTO;
import com.example.mybackend.dto.LoginRequestDTO;
import com.example.mybackend.dto.UserResponseDTO;
import com.example.mybackend.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.mybackend.service.UserService;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth") // for swagger jwt token 
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User-related operations")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns a list of sample users")
    @GetMapping
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return this.userService.getAllUsers(pageable);
    }

    @Operation(summary = "Get user with id", description = "Returns a list of a user with id")
    @GetMapping("/{id}")
    public UserResponseDTO getUserByID(@PathVariable int id){
        return this.userService.getUserByID(id);
    }

    //Api best practices use ResponseEntity - send back - body, status code and headers. Users can check for status codes, eg
    //if 204 then blank content returned, user can refresh another get call
    @Operation(summary = "Add a user")
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody CreateUserRequestDTO setUser){
        UserResponseDTO createdUser = this.userService.addUser(setUser);
        URI location = URI.create("api/users/" + createdUser.getId());//location added if user want to navigate to the desired page after user added
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable int id, @Valid @RequestBody CreateUserRequestDTO user){
        UserResponseDTO updatedUser = this.userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test/user")
    public ResponseEntity<UserResponseDTO> getTestUser(){
        User user = this.userService.createSampleUser();
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getName(), user.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody CreateUserRequestDTO request){
        UserResponseDTO createdUser = this.userService.registerUser(request);
        URI location = URI.create("/api/users/" + createdUser.getId());
        return ResponseEntity.created(location).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO request){
        String token = userService.loginUser(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponseDTO> getUserDetails(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userdetails = userService.getUserDetails(email);
        return ResponseEntity.ok(new UserResponseDTO(userdetails.getId(), userdetails.getName(), userdetails.getEmail()));
    }
}
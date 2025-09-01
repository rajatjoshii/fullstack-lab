package com.example.mybackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequestDTO {
    @NotBlank(message = "name cannot be blank")
    @Size(min=2, max=50, message="Name must have 2-50 characters")
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    public CreateUserRequestDTO() {}

    public CreateUserRequestDTO(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}

package com.example.mybackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequestDTO {
    @NotBlank(message = "name cannot be blank")
    @Size(min=2, max=50, message="Name must have 2-50 characters")
    private String name;

    private String password;
    public CreateUserRequestDTO() {}

    public CreateUserRequestDTO(String name, String password){
        this.name = name;
        this.password = password;
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
        return this.password;
    }
}

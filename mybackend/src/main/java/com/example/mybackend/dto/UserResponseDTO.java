package com.example.mybackend.dto;

public class UserResponseDTO {
    private int id;
    private String name;

    public UserResponseDTO(){}

    public UserResponseDTO(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

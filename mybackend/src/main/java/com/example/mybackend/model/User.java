package com.example.mybackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "users")  // ✅ avoid reserved word
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private int id;
    private String name;
    private String password;

    // Default constructor (JPA needs this)
    public User() {}

    //public User(int id, String name){}

    public User(int id, String name){
		this.id = id;
		this.name = name;
    } 

    public void setId(int id){
        this.id = id;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }
}

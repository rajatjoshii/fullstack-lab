package com.example.mybackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mybackend.repository.UserRepository;
import com.example.mybackend.security.JwtUtils;

import jakarta.security.auth.message.AuthException;

import com.example.mybackend.dto.CreateUserRequestDTO;
import com.example.mybackend.dto.LoginRequestDTO;
import com.example.mybackend.dto.UserResponseDTO;
import com.example.mybackend.model.User;
import com.example.mybackend.exception.UserNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        Page<User> userPage= userRepository.findAll(pageable);
        return userPage.map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail()));
    }

    public UserResponseDTO getUserByID(int id){
        User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id" + id));
        
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public UserResponseDTO addUser(CreateUserRequestDTO newUser){
        User user = new User();
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        User saved = userRepository.save(user);
        return new UserResponseDTO(saved.getId(), saved.getName(), saved.getEmail());
    }

    public UserResponseDTO updateUser(int id, CreateUserRequestDTO userUpdate){
        User existingUser =  userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id" + id));

        existingUser.setName(userUpdate.getName());
        existingUser.setEmail(userUpdate.getEmail());
        User saved = userRepository.save(existingUser);
        return new UserResponseDTO(saved.getId(), saved.getName(), saved.getEmail());
    }

    public void deleteUser(int id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User not found with id" + id);
        }
        userRepository.deleteById(id);
    }

    public User createSampleUser(){
        User newSampleUser = new User();
        newSampleUser.setName("Rajat");
        newSampleUser.setEmail("rajat@gmail.com");
        String rawPassword = "querty123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        newSampleUser.setPassword(encodedPassword);
        return userRepository.save(newSampleUser);
    }

    public UserResponseDTO registerUser(CreateUserRequestDTO request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email is already registered");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        String rawPassord = request.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassord);
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public String loginUser(LoginRequestDTO request){
        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(()-> new IllegalArgumentException("Invalid email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){ //order is important here since matches function implicitely decodes the hash values and compares
            throw new IllegalArgumentException("Invalid password");
        }

        return jwtUtils.generateToken(user.getEmail());        
    }

    public User getUserDetails(String email) {
        return userRepository.findByEmail(email)
        .orElseThrow(()-> new IllegalArgumentException("User not found"));
    }
}

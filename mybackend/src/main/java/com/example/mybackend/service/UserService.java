package com.example.mybackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mybackend.repository.UserRepository;
import com.example.mybackend.dto.CreateUserRequestDTO;
import com.example.mybackend.dto.UserResponseDTO;
import com.example.mybackend.model.User;
import com.example.mybackend.exception.UserNotFoundException;

@Service
public class UserService {
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}

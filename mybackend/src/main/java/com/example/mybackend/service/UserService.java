package com.example.mybackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.mybackend.repository.UserRepository;
import com.example.mybackend.dto.CreateUserRequestDTO;
import com.example.mybackend.dto.UserResponseDTO;
import com.example.mybackend.model.User;
import com.example.mybackend.exception.UserNotFoundException;

@Service
public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        Page<User> userPage= userRepository.findAll(pageable);
        return userPage.map(user -> new UserResponseDTO(user.getId(), user.getName()));
        // List<User> users = userRepository.findAll();
        // List<UserResponseDTO> newusers = new ArrayList<>();
        // for(User user: users){
        //     newusers.add(new UserResponseDTO(user.getId(),user.getName()));
        // }
        // return newusers;
    }

    public UserResponseDTO getUserByID(int id){
        User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id" + id));
        
        return new UserResponseDTO(user.getId(), user.getName());
    }

    public UserResponseDTO addUser(CreateUserRequestDTO newUser){
        User user = new User();
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());
        User saved = userRepository.save(user);
        return new UserResponseDTO(saved.getId(), saved.getName());
    }

    public UserResponseDTO updateUser(int id, CreateUserRequestDTO userUpdate){
        User existingUser =  userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id" + id));

        existingUser.setName(userUpdate.getName());
        User saved = userRepository.save(existingUser);
        return new UserResponseDTO(saved.getId(), saved.getName());
    }

    public void deleteUser(int id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User not found with id" + id);
        }
        userRepository.deleteById(id);
    }
}

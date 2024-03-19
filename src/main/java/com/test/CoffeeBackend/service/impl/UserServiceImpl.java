package com.test.CoffeeBackend.service.impl;

import com.test.CoffeeBackend.configs.security.JwtUtils;
import com.test.CoffeeBackend.dto.RegisterUserDTO;
import com.test.CoffeeBackend.entity.AppUser;
import com.test.CoffeeBackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl
{
    final JwtUtils jwtUtils;
    final UserRepository userRepository;
    final ModelMapper modelMapper;
    final PasswordEncoder passwordEncoder;

    public UserServiceImpl(JwtUtils jwtUtils, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<RegisterUserDTO> getAllUsers()
    {
        List<AppUser> users = userRepository.findAll();
        List<RegisterUserDTO> usersDTO = new ArrayList<>();

        users.forEach(user ->
                usersDTO.add(modelMapper.map(user, RegisterUserDTO.class)));
        return usersDTO;
    }

    public RegisterUserDTO getUserFromToken(String token)
    {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        return getUserFromEmail(email);
    }

    public RegisterUserDTO getUserFromEmail(String email)
    {
        Optional<AppUser> userOptional = userRepository.findByPhone(email);
        return userOptional.map(appUser -> modelMapper.map(appUser, RegisterUserDTO.class)).orElse(null);
    }

    public ResponseEntity<?> updateUser(String email, RegisterUserDTO registerUserDTO)
    {
        Optional<AppUser> userOptional = userRepository.findByPhone(email);
        if(userOptional.isEmpty())
            return ResponseEntity.badRequest().build();
        var user = userOptional.get();
        if(registerUserDTO.getFullName()!=null)
            user.setFullName(registerUserDTO.getFullName());
        if(registerUserDTO.getPassword()!=null)
            user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteUser(String email)
    {
        Optional<AppUser> appUserOptional = userRepository.findByPhone(email);
        if(appUserOptional.isPresent())
        {
            userRepository.delete(appUserOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.test.CoffeeBackend.controller;

import com.test.CoffeeBackend.configs.security.JwtUtils;
import com.test.CoffeeBackend.dto.LoginUserDTO;
import com.test.CoffeeBackend.dto.RegisterUserDTO;
import com.test.CoffeeBackend.dto.AuthResponseDTO;
import com.test.CoffeeBackend.entity.AppUser;
import com.test.CoffeeBackend.repository.UserRepository;
import com.test.CoffeeBackend.service.impl.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authentication")
public class AuthController
{
    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;
    final PasswordEncoder encoder;
    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> createNewUser(@RequestBody RegisterUserDTO request)
    {
        if(!Objects.equals(request.getPassword(), request.getRetypePassword())){
            return ResponseEntity.badRequest().body("Error: retype password doesn't matched");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account
        AppUser user = new AppUser();
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setPassword(encoder.encode(request.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO request)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            var userDetail = (UserDetailsImpl)authentication.getPrincipal();
            return ResponseEntity.ok(new AuthResponseDTO(userDetail.getUsername(), jwt));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }
}

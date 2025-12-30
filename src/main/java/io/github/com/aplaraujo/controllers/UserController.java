package io.github.com.aplaraujo.controllers;

import io.github.com.aplaraujo.entities.AuthRequest;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.services.JwtService;
import io.github.com.aplaraujo.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl service;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    @PostMapping("/create-user")
    public String addNewUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getEmail());
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}

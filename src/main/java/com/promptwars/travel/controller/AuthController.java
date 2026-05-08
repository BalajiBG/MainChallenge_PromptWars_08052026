package com.promptwars.travel.controller;

import com.promptwars.travel.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) throws Exception {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("Invalid username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return Map.of("token", jwt);
    }
}

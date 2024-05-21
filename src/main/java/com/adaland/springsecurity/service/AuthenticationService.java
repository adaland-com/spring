package com.adaland.springsecurity.service;

import com.adaland.springsecurity.config.JwtService;
import com.adaland.springsecurity.exception.EntityAlreadyExistsException;
import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.model.auth.AuthenticationRequest;
import com.adaland.springsecurity.model.auth.AuthenticationResponse;
import com.adaland.springsecurity.model.auth.User;
import com.adaland.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public AuthenticationResponse register(User request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException(EntityAlreadyExistsException.ENTITY_AlREADY_EXISTS_MESSAGE, "user withusername:" + request.getUsername());
        }
        var user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        String token = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponse(token);
    }


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        );
        authenticationManager.authenticate(authToken);
        if (userRepository.findByUsername(authenticationRequest.getUsername()).isEmpty()) {
            throw new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "user " + authenticationRequest.getUsername());
        }
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponse(jwt);
    }


    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        return extraClaims;
    }
}

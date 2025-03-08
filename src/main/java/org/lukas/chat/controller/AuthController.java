package org.lukas.chat.controller;

import org.lukas.chat.dto.TokenRequest;
import org.lukas.chat.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthController(TokenService tokenService, AuthenticationManager authManager) {
        this.tokenService = tokenService;
        this.authManager = authManager;
    }

    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody TokenRequest tokenRequest) {
        LOGGER.debug("Token requested for user '{}'", tokenRequest.getEmail());
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.getEmail(), tokenRequest.getPassword()));
        return ResponseEntity.ok(tokenService.generateToken(auth));
    }
}

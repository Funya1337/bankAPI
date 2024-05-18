package com.example.bankAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankAPI.entity.Account;
import com.example.bankAPI.service.AccountService;
import com.example.bankAPI.service.TokenService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
public class TokenController {
    private final TokenService tokenService;
    private final AccountService accountService;

    @Autowired
    public TokenController(TokenService tokenService, AccountService accountService) {
        this.tokenService = tokenService;
        this.accountService = accountService;
    }
    
    @PostMapping("/auth")
    @ResponseBody
    public ResponseEntity<String> generateToken(@RequestParam(name = "hash") String hash) {
        Account account = accountService.findByHash(hash).orElseThrow(EntityNotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        String token = account.getToken();
        if (token == null) {
            tokenService.setTokenToAccount(hash);
            return new ResponseEntity<>(token, headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(token, headers, HttpStatus.OK);
    }
}

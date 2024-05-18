package com.example.bankAPI.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankAPI.entity.Account;
import com.example.bankAPI.repo.AccountRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TokenServiceImpl implements TokenService {

    private final AccountService accountService;
    private final TransactionService transactionService;
    
    @Autowired
    public TokenServiceImpl(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void setTokenToAccount(String hash) {
        Account account = accountService.findByHash(hash).orElseThrow(EntityNotFoundException :: new);
        String token = generateToken();
        if (account.getToken() == null) {
            account.setToken(token);
        }
        accountService.save(account);
    }

    

    
    
}

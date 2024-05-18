package com.example.bankAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bankAPI.entity.Transaction;

public interface TokenService {
    public String generateToken();
    public void setTokenToAccount(String hash);
    
}

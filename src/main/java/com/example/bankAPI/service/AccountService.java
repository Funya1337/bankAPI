package com.example.bankAPI.service;

import java.util.List;
import java.util.Optional;
import com.example.bankAPI.entity.Account;


public interface AccountService {
    public Account createAccount(Account account);
    public Account getAccountDetailsByAccountNumber(Long accountNumber);
    public List<Account> getAllAccountDetails();
    public Account depositAmount(Long accountNumber, Double amount);
    public Account withdrawAmount(Long accountNumber, Double amount);
    public void closeAccount(Long accountNumber);
    public boolean isTokenSet(Long id);
    public Optional<Account> findByHash(String hash);
    public Optional<Account> findByToken(String token);
    public void save(Account account);
    public Double getBalance(String token);   
}

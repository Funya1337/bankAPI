package com.example.bankAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankAPI.entity.Account;
import com.example.bankAPI.entity.Transaction;
import com.example.bankAPI.repo.AccountRepository;
import com.example.bankAPI.repo.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    
    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public List<Transaction> getTransactions(String token) {
        Account account = accountService.findByToken(token).orElseThrow(EntityNotFoundException::new);
        if (account != null) {
            return transactionRepository.findByAccount(account);
        }
        return null;
    }
    
}

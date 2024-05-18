package com.example.bankAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankAPI.entity.Account;
import com.example.bankAPI.repo.AccountRepository;

import jakarta.persistence.EntityNotFoundException;



@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        Account account_saved = accountRepository.save(account);
        return account_saved;
    }

    @Override
    public Account getAccountDetailsByAccountNumber(Long accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isEmpty()) {
            throw new RuntimeException("Account is not presented");
        }
        Account account_found = account.get();
        return account_found;
    }

    @Override
    public List<Account> getAllAccountDetails() {
        List<Account> listOfAccounts = accountRepository.findAll();
        return listOfAccounts;
    }

    @Override
    public Account depositAmount(Long accountNumber, Double amount) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isEmpty()) {
            throw new RuntimeException("Account is not presented");
        }
        Account presentedAccount = account.get();
        Double totalBalance = presentedAccount.getBalance() + amount;
        presentedAccount.setBalance(totalBalance);
        accountRepository.save(presentedAccount);
        return presentedAccount;
    }

    @Override
    public Account withdrawAmount(Long accountNumber, Double amount) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isEmpty()) {
            throw new RuntimeException("Account is not presented");
        }
        Account presentedAccount = account.get();
        Double accountBalance = presentedAccount.getBalance() - amount;
        presentedAccount.setBalance(accountBalance);
        accountRepository.save(presentedAccount);
        return presentedAccount;
    }

    @Override
    public void closeAccount(Long accountNumber) {
        getAccountDetailsByAccountNumber(accountNumber);
        accountRepository.deleteById(accountNumber);
    }

    @Override
    public boolean isTokenSet(Long id) {
        // Account account = accountRepository.findById(id).orElseThrow(EntityNotFoundException :: new);
        String token = accountRepository.getTokenById(id);// посмотри глеб норм ли так делать
        if (token != null) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<Account> findByHash(String hash) {
        Optional<Account> account = accountRepository.findByHash(hash);
        return account;
    }

    @Override
    public Optional<Account> findByToken(String token) {
        Optional<Account> account = accountRepository.findByToken(token);
        return account;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }
    
    @Override
    public Double getBalance(String token) {
        Account account = accountRepository.findByToken(token).orElseThrow(EntityNotFoundException::new);
        Double balance = account.getBalance();
        return balance;
    }
}

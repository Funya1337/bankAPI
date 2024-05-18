package com.example.bankAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankAPI.entity.Account;
import com.example.bankAPI.entity.Transaction;
import com.example.bankAPI.service.AccountService;
import com.example.bankAPI.service.TransactionService;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    private final AccountService accountService;
    private final TransactionService transactionService;
    
    @Autowired
    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAccount);
    }

    @GetMapping("/transactions")
    @ResponseBody
    public ResponseEntity<List<Transaction>> getAccountTransactions(@RequestParam(name = "token") String token) {
        List<Transaction> transactions = transactionService.getTransactions(token);
        if (transactions == null || transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/balance")
    @ResponseBody
    public ResponseEntity<Double> getAccountBalance(@RequestParam(name = "token") String token) {
        Double balance = accountService.getBalance(token);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        
        return new ResponseEntity<>(balance, headers, HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    @ResponseBody
    public Account getAccountByAccountNumber(@PathVariable Long accountNumber) {
        Account account = accountService.getAccountDetailsByAccountNumber(accountNumber);
        return account;
    }

    @GetMapping("/get_all_accounts")
    @ResponseBody
    public List<Account> getAllAccountDetails() {
        List<Account> allAccountDetails = accountService.getAllAccountDetails();
        return allAccountDetails;
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    @ResponseBody
    public Account depositAccount(@PathVariable Long accountNumber, @PathVariable Double amount) {
        Account account = accountService.depositAmount(accountNumber, amount);
        return account;
    }

    @PutMapping("/withdraw/{accountNumber}/{amount}")
    @ResponseBody
    public Account withdrawAccount(@PathVariable Long accountNumber, @PathVariable Double amount) {
        Account account = accountService.withdrawAmount(accountNumber, amount);
        return account;
    }

    @DeleteMapping("/delete/{accountNumber}")
    @ResponseBody
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber) {
        accountService.closeAccount(accountNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Account closed");
    }
}

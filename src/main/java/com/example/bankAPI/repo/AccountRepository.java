package com.example.bankAPI.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankAPI.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByHash(String hash);
    Optional<Account> findByToken(String token);
    Optional<Account> findById(Long id);
    String getTokenById(Long id);
    Double getBalanceByToken(String token);
    
}

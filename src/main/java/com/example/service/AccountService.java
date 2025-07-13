package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public boolean accountExists(Integer accountId) {
        return accountRepository.existsById(accountId);
    }

    public Account registerAccount(Account account) {
       if (account.getUsername() == null ||
           account.getUsername().trim().isEmpty() ||
           account.getPassword().trim().length() < 4) {
            return null;
           }
    
       Account existingAccount = accountRepository.findByUsername(account.getUsername());
       if (existingAccount != null){
        return null;
       }
       return accountRepository.save(account);
    }

    public Account login(Account account) {
        if (account.getUsername() == null || 
            account.getPassword() == null) {
                return null;
            }

        Account existingAccount = accountRepository.findByUsername(account.getUsername());
            if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
                return existingAccount;
            }

            return null;

    }

}


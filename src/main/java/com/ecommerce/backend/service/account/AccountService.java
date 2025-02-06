package com.ecommerce.backend.service.account;

import com.ecommerce.backend.entity.account.Account;
import com.ecommerce.backend.repository.account.AccountRepository;
import com.ecommerce.backend.service.auth.UserDetailsImpl;
import com.ecommerce.backend.service.auth.UserDetailsServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public Account getAccount() {
        UserDetailsImpl userDetails = userDetailsService.getPrincipal();
        return accountRepository.findById(userDetails.getId()).orElseThrow(
                () -> new ServiceException("user not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        return accountRepository.findById(id).map(account -> {
            account.setUsername(updatedAccount.getUsername());
            account.setPassword(updatedAccount.getPassword());
            account.setStatus(updatedAccount.getStatus());
            account.setEmail(updatedAccount.getEmail());
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}

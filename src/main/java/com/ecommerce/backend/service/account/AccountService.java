package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.AccountDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.entity.account.Account;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.mapper.AccountMapper;
import com.ecommerce.backend.repository.account.AccountRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.service.auth.UserDetailsImpl;
import com.ecommerce.backend.service.auth.UserDetailsServiceImpl;
import com.ecommerce.backend.specification.GenericSpecification;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class AccountService extends BaseService<Account, AccountDto> {
    private final AccountRepository accountRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    public AccountService(JpaSpecificationExecutor<Account> repository, AccountRepository accountRepository,
                          UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder,
                          AccountMapper accountMapper) {
        super(repository, accountMapper::toDto);
        this.accountRepository = accountRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
    }

    public Optional<Account> getAccountByUsername(String userName) {
        return accountRepository.findByUsername(userName);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account getAccount() {
        UserDetailsImpl userDetails = userDetailsService.getPrincipal();
        return accountRepository.findById(userDetails.getId()).orElseThrow(
                () -> new ServiceException("user not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    public AccountDto createAccount(AccountDto accountDto) {

        // Check if email already exists
        checkExistEmail(accountDto.getEmail());

        // Check if username already exists
        checkExistUsername(accountDto.getUsername());

        // Check if phone already exists
        checkExistPhone(accountDto.getPhone());

        // Convert DTO to entity
        Account accountEntity = accountMapper.toEntity(accountDto);

        // Encode the password from DTO and set it on the entity if provided
        if (accountDto.getPassword() != null) {
            accountEntity.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        }

        // Save the account entity
        Account savedAccount = accountRepository.save(accountEntity);

        // Convert the saved entity back to DTO for response
        return accountMapper.toDto(savedAccount);
    }

    private void checkExistEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("email already exists.");
        }
    }

    private void checkExistUsername(String username) {
        if (accountRepository.existsByUsername(username)) {
            throw new ResourceAlreadyExistsException("username already exists.");
        }
    }

    private void checkExistPhone(String phone) {
        if (accountRepository.existsByPhone(phone)) {
            throw new ResourceAlreadyExistsException("phone already exists.");
        }
    }

    public Account updateAccount(UUID id, Account updatedAccount) {
        return accountRepository.findById(id).map(account -> {
            account.setUsername(updatedAccount.getUsername());
            account.setPassword(updatedAccount.getPassword());
            account.setStatus(updatedAccount.getStatus());
            account.setEmail(updatedAccount.getEmail());
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }


    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    protected Specification<Account> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchAccounts(SearchDto requestDto) {
        return search(requestDto, AccountDto.class);
    }

}

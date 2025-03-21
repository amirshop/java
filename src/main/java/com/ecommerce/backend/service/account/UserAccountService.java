package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.UserAccountDto;
import com.ecommerce.backend.entity.account.UserAccount;
import com.ecommerce.backend.enums.AccountStatus;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.account.AddressMapper;
import com.ecommerce.backend.mapper.account.UserAccountMapper;
import com.ecommerce.backend.repository.account.UserAccountRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.service.auth.UserDetailsImpl;
import com.ecommerce.backend.service.auth.UserDetailsServiceImpl;
import com.ecommerce.backend.specification.GenericSpecification;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserAccountService extends BaseService<UserAccount, UserAccountDto> {
    private final UserAccountRepository accountRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountMapper accountMapper;
    private final AddressMapper addressMapper;

    public UserAccountService(JpaSpecificationExecutor<UserAccount> repository, UserAccountRepository accountRepository,
                          UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder,
                          UserAccountMapper accountMapper, AddressMapper addressMapper) {
        super(repository, accountMapper::toDto);
        this.accountRepository = accountRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
        this.addressMapper = addressMapper;
    }

    public Optional<UserAccount> getAccountByUsername(String userName) {
        return accountRepository.findByUsername(userName);
    }

    public Optional<UserAccount> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public UserAccount getAccount() {
        UserDetailsImpl userDetails = userDetailsService.getPrincipal();
        return accountRepository.findById(userDetails.getId()).orElseThrow(
                () -> new ServiceException("user not found"));
    }

    public List<UserAccountDto> getAllAccounts() {
        List<UserAccount> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserAccountDto getAccountById(UUID id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id.toString()));
    }

    public UserAccountDto createAccount(UserAccountDto accountDto) {

        // Check if email already exists
        checkExistEmail(accountDto.getEmail());

        // Check if username already exists
        checkExistUsername(accountDto.getUsername());

        // Check if phone already exists
        checkExistPhone(accountDto.getPhone());

        // Convert DTO to entity
        UserAccount accountEntity = accountMapper.toEntity(accountDto);

        // Encode the password from DTO and set it on the entity if provided
        if (accountDto.getPassword() != null) {
            accountEntity.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        }

        if(accountDto.getStatus() == null) {
            accountEntity.setStatus(AccountStatus.UNKNOWN);
        }

        accountEntity.setCreatedAt(new Date());
        accountEntity.setUpdatedAt(new Date());
        // Save the account entity
        UserAccount savedAccount = accountRepository.save(accountEntity);

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

    public UserAccount updateAccount(UUID id, UserAccountDto updatedAccount) {
        return accountRepository.findById(id).map(account -> {
            account.setPhone(updatedAccount.getPhone());
            account.setStatus(updatedAccount.getStatus());
            account.setUpdatedAt(new Date());
//            account.setAddress(addressMapper.toEntity(updatedAccount.getAddress()));
//            account.setFirstname(updatedAccount.getFirstname());
//            account.setLastname(updatedAccount.getLastname());
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }


    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    protected Specification<UserAccount> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchAccounts(SearchDto requestDto) {
        return search(requestDto, UserAccountDto.class);
    }

}

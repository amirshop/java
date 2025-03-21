package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.UserProfileDto;
import com.ecommerce.backend.entity.account.UserProfile;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.account.UserProfileMapper;
import com.ecommerce.backend.repository.account.UserProfileRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends BaseService<UserProfile, UserProfileDto> {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileService(JpaSpecificationExecutor<UserProfile> repository,
                              UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {

        super(repository, userProfileMapper::toDto);
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    public List<UserProfileDto> getAllProfiles() {
        List<UserProfile> accounts = userProfileRepository.findAll();
        return accounts.stream()
                .map(userProfileMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserProfileDto getProfileById(UUID id) {
        return userProfileRepository.findById(id)
                .map(userProfileMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id.toString()));
    }

    public UserProfileDto createProfile(UserProfileDto accountDto) {

        // Convert DTO to entity
        UserProfile accountEntity = userProfileMapper.toEntity(accountDto);


        accountEntity.setCreatedAt(new Date());
        accountEntity.setUpdatedAt(new Date());
        // Save the account entity
        UserProfile savedAccount = userProfileRepository.save(accountEntity);

        // Convert the saved entity back to DTO for response
        return userProfileMapper.toDto(savedAccount);
    }

    public UserProfile updateProfile(UUID id, UserProfileDto updatedAccount) {
        return userProfileRepository.findById(id).map(account -> {
            account.setUpdatedAt(new Date());
            return userProfileRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }


    public void deleteProfile(UUID id) {
        userProfileRepository.deleteById(id);
    }

    @Override
    protected Specification<UserProfile> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchProfiles(SearchDto requestDto) {
        return search(requestDto, UserProfileDto.class);
    }

}

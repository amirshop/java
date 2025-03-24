package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.AddressDto;
import com.ecommerce.backend.dto.account.UserProfileDto;
import com.ecommerce.backend.entity.account.UserAccount;
import com.ecommerce.backend.entity.account.UserProfile;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.account.AddressMapper;
import com.ecommerce.backend.mapper.account.UserProfileMapper;
import com.ecommerce.backend.repository.account.UserProfileRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends BaseService<UserProfile, UserProfileDto> {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserAccountService userAccountService;
    private final AddressMapper addressMapper;

    public UserProfileService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper,
                              UserAccountService userAccountService, AddressMapper addressMapper) {

        super(userProfileRepository, userProfileMapper::toDto);
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.userAccountService = userAccountService;
        this.addressMapper = addressMapper;
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
                .orElseThrow(() -> new ResourceNotFoundException("profile", "id", id.toString()));
    }

    public UserProfileDto getProfileByAccountId(UUID accountId) {
        return userProfileRepository.findByUserId(accountId)
                .map(userProfileMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("profile", "id", accountId.toString()));
    }

    public UserProfileDto createProfile(UserProfileDto accountDto) {

        UserAccount userAccount = userAccountService.findById(accountDto.getUserId());
        checkExistUserAccount(accountDto.getUserId());

        // Convert DTO to entity
        UserProfile accountEntity = userProfileMapper.toEntity(accountDto);

        accountEntity.setUser(userAccount);

        accountEntity.setCreatedAt(new Date());
        accountEntity.setUpdatedAt(new Date());
        // Save the account entity
        UserProfile savedAccount = userProfileRepository.save(accountEntity);

        // Convert the saved entity back to DTO for response
        return userProfileMapper.toDto(savedAccount);
    }

    private void checkExistUserAccount(UUID userId) {
        if (userProfileRepository.existsByUserId(userId)) {
            throw new ResourceAlreadyExistsException("userAccount id already exists");
        }
    }

    public UserProfileDto updateProfile(UUID id, UserProfileDto updatedProfile) {
        return userProfileRepository.findById(id).map(profile -> {
            Optional.ofNullable(updatedProfile.getFirstname())
                    .filter(firstname -> !firstname.isBlank())
                    .ifPresent(profile::setFirstname);
            Optional.ofNullable(updatedProfile.getLastname())
                    .filter(lastname -> !lastname.isBlank())
                    .ifPresent(profile::setLastname);
            Optional.ofNullable(updatedProfile.getProfilePictureUrl())
                    .filter(profilePictureUrl -> !profilePictureUrl.isBlank())
                    .ifPresent(profile::setProfilePictureUrl);
            Optional.ofNullable(updatedProfile.getGender()).ifPresent(profile::setGender);
            profile.setAddress(addressMapper.toEntity(updatedProfile.getAddress()));
            profile.setUpdatedAt(new Date());
            UserProfile savedProfile = userProfileRepository.save(profile);
            return userProfileMapper.toDto(savedProfile);
        }).orElseThrow(() -> new RuntimeException("Profile not found"));
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

package com.ecommerce.backend.service.auth;

import com.ecommerce.backend.entity.account.Account;
import com.ecommerce.backend.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account user = userRepository.findByUsername(username)
        .orElseThrow(() -> new ServiceException("user not found"));
    return UserDetailsImpl.build(user);
  }

  public UserDetailsImpl getPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    checkAnonymousAuthentication(authentication);
    return (UserDetailsImpl) authentication.getPrincipal();
  }

  private void checkAnonymousAuthentication(Authentication authentication) {
    if (authentication instanceof AnonymousAuthenticationToken) {
      throw new ServiceException("anonymous authentication required");
    }
  }
}

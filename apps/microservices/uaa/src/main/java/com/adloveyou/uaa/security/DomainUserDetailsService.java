package com.adloveyou.uaa.security;

import com.adloveyou.uaa.domain.User;
import com.adloveyou.uaa.repository.UserRepository;
import com.adloveyou.uaa.repository.querydsl.UserQueryDslRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserQueryDslRepository userQueryDslRepository;

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserQueryDslRepository userQueryDslRepository, UserRepository userRepository) {
        this.userQueryDslRepository = userQueryDslRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<SimpleGrantedAuthority> grantedAuthorities = user.getProfiles().stream()
                .flatMap(profile -> profile.getRoles().stream())
                .flatMap(roles -> roles.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.getName() + ((!StringUtils.isEmpty(permission.getExtention())) ? "_" + permission.getExtention() : "")))
                .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
    }
}


package com.example.socialnetwork.auth.service;

import com.example.socialnetwork.dto.UserDto;
import com.example.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다"));
    }

    private UserDetails createUserDetails(UserDto userDto) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userDto.getAuthority().toString());
        return new User(userDto.getUserId()
                , userDto.getPassword()
                , Collections.singleton(grantedAuthority));
    }
}
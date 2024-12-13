package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService service;

    // コンストラクタインジェクション
    public UserDetailsServiceImpl(@Lazy UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MUser loginUser = service.getLoginUser(username);

        if (loginUser == null) {
            throw new UsernameNotFoundException("user not found");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(loginUser.getRole()));
        return new User(loginUser.getUserId(), loginUser.getPassword(), authorities);
    }
}



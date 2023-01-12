package com.divary.authenticationservice.service;

import com.divary.authenticationservice.dto.UserDetail;
import com.divary.authenticationservice.model.Account;
import com.divary.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    AccountService accountService;

    @Autowired
    public UserDetailService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetail loadUserByUsername(String username){

        Account account = accountService.findByUsername(username)
                .orElseThrow(() -> new ForbiddenException("User Not Found"));

        return UserDetail.build(account);
    }

}

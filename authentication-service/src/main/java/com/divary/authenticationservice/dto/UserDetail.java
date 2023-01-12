package com.divary.authenticationservice.dto;

import com.divary.authenticationservice.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetail implements UserDetails {
    private String username;
    @JsonIgnore
    private String password;

    private UserDetail(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserDetail build(Account account) {
        return new UserDetail(
                account.getUsername(),
                account.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

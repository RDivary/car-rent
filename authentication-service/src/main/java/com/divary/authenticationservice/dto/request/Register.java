package com.divary.authenticationservice.dto.request;

import lombok.Getter;

@Getter
public class Register {
    private String username;
    private String password;
    private String fullName;
}

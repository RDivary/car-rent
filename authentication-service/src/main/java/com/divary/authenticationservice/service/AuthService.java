package com.divary.authenticationservice.service;

import com.divary.authenticationservice.dto.UserDetail;
import com.divary.authenticationservice.dto.request.Login;
import com.divary.authenticationservice.dto.request.Register;
import com.divary.authenticationservice.dto.response.JwtResponse;
import com.divary.authenticationservice.model.Account;
import com.divary.authenticationservice.security.jwt.JwtUtils;
import com.divary.exception.BadRequestException;
import com.divary.exception.UnauthorizedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class AuthService {

    private static final Logger logger = LogManager.getLogger();

    private AccountService accountService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

//    private UserDetailService userDetailService;

    public AuthService(AccountService accountService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    public void register(Register form){
        if (accountService.isUsernameExist(form.getUsername())) throw new BadRequestException("This username isn't available. Please try another.");
        Account account = Account.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .fullName(form.getFullName())
                .build();
        accountService.save(account);
        logger.info("Username '{}' has bean created", account.getUsername());
    }

    public JwtResponse login(Login form){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword())
        );
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetail);
        return JwtResponse.builder()
                .username(userDetail.getUsername())
                .token(jwt)
                .build();

    }

    public boolean validateJwt(String jwt){
        if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ") && jwtUtils.validateJwtToken(jwt.substring(7))) {
            return true;
        }
        throw new UnauthorizedException("Token Not Valid");
    }
}

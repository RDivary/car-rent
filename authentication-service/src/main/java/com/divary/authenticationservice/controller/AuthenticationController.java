package com.divary.authenticationservice.controller;

import com.divary.authenticationservice.dto.request.Login;
import com.divary.authenticationservice.dto.request.Register;
import com.divary.authenticationservice.dto.response.JwtResponse;
import com.divary.authenticationservice.service.AuthService;
import com.divary.controller.BaseController;
import com.divary.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication-service/api/v1/auth")
public class AuthenticationController extends BaseController{

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Object>> register(@RequestBody Register form){

        authService.register(form);

        return getResponseCreated(null, "Register Success");
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<JwtResponse>> login(@RequestBody Login form){
        return getResponseOk(authService.login(form), "Login Success");
    }

    @GetMapping("/validate-token")
    public ResponseEntity<BaseResponse<Boolean>> validateToken(@RequestHeader("Authorization") String jwt){
        return getResponseOk(authService.validateJwt(jwt), "");
    }
}

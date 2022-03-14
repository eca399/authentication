package com.coremaker.authentication.controller;

import com.coremaker.authentication.model.SignUpModel;
import com.coremaker.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody final SignUpModel signUpModel)
    {
        return userService.signUp(signUpModel);
    }

    @GetMapping("/user-details")
    public ResponseEntity<Object> userDetails()
    {
        return userService.getUserDetails();
    }
}
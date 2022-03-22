package com.sardor.unsplash.controller;

import com.sardor.unsplash.payload.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sardor.unsplash.entity.User;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.LoginDto;
import com.sardor.unsplash.security.JwtProvider;
import com.sardor.unsplash.service.AuthService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class    AuthController {
    @Autowired
    AuthService authService;

    final
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    /**
     * LOGIN YO'LI
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//        User principal = (User) authenticate.getPrincipal();
//        String token = jwtProvider.generateToken(principal.getUsername(), principal.getRole());
//        return ResponseEntity.status(200).body(new ApiResponse(token,true,principal));

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        User principal = (User) authenticate.getPrincipal();
        String token = jwtProvider.generateToken(principal.getUsername(), principal.getRole());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody ProfileDto profileDto) {
        ApiResponse apiResponse = authService.registerUser(profileDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

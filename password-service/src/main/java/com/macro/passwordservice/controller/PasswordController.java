package com.macro.passwordservice.controller;

import com.macro.passwordservice.dto.PasswordRequest;
import com.macro.passwordservice.dto.PasswordResponse;
import com.macro.passwordservice.service.PasswordService;
import com.macro.passwordservice.util.AuthenticatedUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passwords")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @GetMapping
    public ResponseEntity<List<PasswordResponse>> getPasswords(){
        String email = authenticatedUserProvider.getCurrentUserEmail();
        List<PasswordResponse> allPasswords = passwordService.getAllPasswords(email);
        return ResponseEntity.ok(allPasswords);
    }

    @PostMapping
    public ResponseEntity<PasswordResponse> addService(@RequestBody @Valid PasswordRequest passwordRequest){
        PasswordResponse passwordResponse = passwordService.createPassword(passwordRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(passwordResponse);
    }

    // Future additions:
    // @PutMapping("/{id}")
    // @DeleteMapping("/{id}")
}

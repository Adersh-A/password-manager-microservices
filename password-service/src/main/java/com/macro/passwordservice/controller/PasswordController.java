package com.macro.passwordservice.controller;

import com.macro.passwordservice.dto.ServiceCredentials;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class PasswordController {

    @GetMapping
    public List<ServiceCredentials> getServices(){
        return List.of(ServiceCredentials.builder().serviceName("facebook").password("pass123").build());
    }
}

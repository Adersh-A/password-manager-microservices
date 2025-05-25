package com.macro.passwordservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServiceCredentials {
    private String serviceName;
    private String password;
}

package com.macro.passwordservice.mapper;

import com.macro.passwordservice.dto.PasswordRequest;
import com.macro.passwordservice.dto.PasswordResponse;
import com.macro.passwordservice.entity.PasswordEntity;
import org.springframework.stereotype.Component;

@Component
public class PasswordMapper {

    public PasswordResponse toDto(PasswordEntity entity) {
        PasswordResponse dto = new PasswordResponse();
        if(entity == null){
            return dto;
        }
        dto.setId(entity.getId());
        dto.setServiceName(entity.getServiceName());
        dto.setUserName(entity.getUserName());
        dto.setPassword(entity.getPassword());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public PasswordEntity toEntity(PasswordRequest dto) {
        PasswordEntity entity = new PasswordEntity();
        entity.setServiceName(dto.getServiceName());
        entity.setUserName(dto.getUserName());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}

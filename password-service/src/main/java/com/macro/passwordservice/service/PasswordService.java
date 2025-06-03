package com.macro.passwordservice.service;

import com.macro.passwordservice.dto.PasswordRequest;
import com.macro.passwordservice.dto.PasswordResponse;
import com.macro.passwordservice.entity.PasswordEntity;
import com.macro.passwordservice.exception.UnauthorizedAccessException;
import com.macro.passwordservice.mapper.PasswordMapper;
import com.macro.passwordservice.repository.PasswordRepository;
import com.macro.passwordservice.util.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordRepository passwordRepository;
    private final PasswordMapper passwordMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public PasswordResponse createPassword(PasswordRequest passwordRequest) {
        String email = authenticatedUserProvider.getCurrentUserEmail();
        if(email == null || email.isBlank()){
            throw new UnauthorizedAccessException("Email is empty");
        }
        PasswordEntity passwordEntity = passwordMapper.toEntity(passwordRequest);
        passwordEntity.setUserEmail(email);
        PasswordEntity password = passwordRepository.save(passwordEntity);
        return passwordMapper.toDto(password);
    }

    public List<PasswordResponse> getAllPasswords(String userEmail) {
        List<PasswordEntity> allPasswords = passwordRepository.findByUserEmail(userEmail);
        return allPasswords.stream().map(passwordMapper::toDto).
                toList();
    }

    public void deletePassword(UUID id) {
        passwordRepository.deleteById(id);
    }

    public PasswordEntity updatePassword(PasswordEntity updatedEntity) {
        // add validation logic before updating
        // check if entry already exists, if so check if email matches
        return passwordRepository.save(updatedEntity);
    }
}

package com.macro.passwordservice.repository;

import com.macro.passwordservice.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PasswordRepository extends JpaRepository<PasswordEntity,UUID> {
    List<PasswordEntity> findByUserEmail(String userEmail);
}

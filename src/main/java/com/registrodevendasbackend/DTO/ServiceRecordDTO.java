package com.registrodevendasbackend.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import com.registrodevendasbackend.model.User;

import jakarta.validation.constraints.NotBlank;

public record ServiceRecordDTO(UUID id, @NotBlank(message = "O campo título não pode ser vazio") String title, @NotBlank(message = "O campo descrição não pode ser vazio") String description, BigDecimal basePrice, UUID userId, User user, boolean deleted, Timestamp createdAt, Timestamp updatedAt) {

}

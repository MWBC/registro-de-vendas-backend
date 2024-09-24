package com.registrodevendasbackend.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import com.registrodevendasbackend.custom.annotations.UUIDPattern;
import com.registrodevendasbackend.custom.annotations.UserExist;
import com.registrodevendasbackend.model.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ServiceRecordDTO(UUID id, @NotBlank(message = "O campo título não pode ser vazio") String title, @NotBlank(message = "O campo descrição não pode ser vazio") String description, @NotNull(message = "O campo preço base não pode ser vazio") @PositiveOrZero(message = "O campo preço base deve ter valor positivo") BigDecimal basePrice, @UserExist(message = "Usuário não encontrado") @UUIDPattern(message = "Id de usuário inválido") String userId, User user, boolean deleted, Timestamp createdAt, Timestamp updatedAt) {

}

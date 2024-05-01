package com.registrodevendasbackend.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import com.registrodevendasbackend.model.User;

public record ServiceRecordoDTO(UUID id, String title, String description, BigDecimal basePrice, User user, boolean deleted, Timestamp createdAt, Timestamp updatedAt) {

}

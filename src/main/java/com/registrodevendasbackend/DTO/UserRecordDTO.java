package com.registrodevendasbackend.DTO;

import java.sql.Timestamp;
import java.util.UUID;

public record UserRecordDTO(UUID id, String name, String email, String password, Timestamp created_at, Timestamp updated_at) {

}

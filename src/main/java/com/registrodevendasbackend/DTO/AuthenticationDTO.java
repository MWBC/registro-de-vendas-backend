package com.registrodevendasbackend.DTO;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank(message = "O campo Usuário não pode ser vazio") String username, @NotBlank(message = "O campo Senha não pode ser vazio") String password) {

}

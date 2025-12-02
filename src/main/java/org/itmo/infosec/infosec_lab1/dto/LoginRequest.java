package org.itmo.infosec.infosec_lab1.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email должен быть заполнен")
        @Email(message = "Проверьте корректность Email") String email,
        @NotBlank(message = "Пароль должен быть заполнен") String password
) {}

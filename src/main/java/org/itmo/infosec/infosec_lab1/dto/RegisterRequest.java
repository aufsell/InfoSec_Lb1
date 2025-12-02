package org.itmo.infosec.infosec_lab1.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "Email должен быть заполнен")
        @Email(message = "Проверьте корректность Email") String email,
        @NotBlank(message = "Пароль должен быть заполнен") String password,
        @NotBlank(message = "Имя должно быть заполнено") String name
) {}
